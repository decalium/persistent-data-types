package com.manya.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class EnumDataType<E extends Enum<E>> implements PersistentDataType<String, E> {
    private final Class<E> enumClass;

    public EnumDataType(Class<E> enumClass) {

        this.enumClass = enumClass;
    }
    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<E> getComplexType() {
        return enumClass;
    }

    @Override
    public @NotNull String toPrimitive(@NotNull E complex, @NotNull PersistentDataAdapterContext context) {
        return complex.name();
    }

    @Override
    public @NotNull E fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        return Enum.valueOf(enumClass, primitive);
    }
}
