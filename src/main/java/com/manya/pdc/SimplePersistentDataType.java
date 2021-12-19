package com.manya.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public record SimplePersistentDataType<T, Z>(Class<T> primitive, Class<Z> complex,
                                             BiFunction<Z, PersistentDataAdapterContext, T> serializer,
                                             BiFunction<T, PersistentDataAdapterContext, Z> deserializer)
        implements PersistentDataType<T, Z> {

    @Override
    public @NotNull
    Class<T> getPrimitiveType() {
        return primitive;
    }

    @Override
    public @NotNull
    Class<Z> getComplexType() {
        return complex;
    }

    @Override
    public @NotNull
    T toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        return serializer.apply(complex, context);
    }

    @Override
    public @NotNull
    Z fromPrimitive(@NotNull T primitive, @NotNull PersistentDataAdapterContext context) {
        return deserializer.apply(primitive, context);
    }
}
