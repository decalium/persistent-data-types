package com.manya.pdc.base;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public record IdentityDataType<T>(Class<T> target) implements PersistentDataType<T, T> {

    @Override
    public @NotNull
    Class<T> getPrimitiveType() {
        return target;
    }

    @Override
    public @NotNull
    Class<T> getComplexType() {
        return target;
    }

    @Override
    public @NotNull
    T toPrimitive(@NotNull T complex, @NotNull PersistentDataAdapterContext context) {
        return complex;
    }

    @Override
    public @NotNull
    T fromPrimitive(@NotNull T primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive;
    }
}
