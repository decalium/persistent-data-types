package com.manya.pdc.base;

import com.google.common.base.MoreObjects;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class EnumDataType<E extends Enum<E>> implements PersistentDataType<String, E> {
    private final Class<E> enumClass;

    public EnumDataType(Class<E> enumClass) {

        this.enumClass = Objects.requireNonNull(enumClass, "enumClass");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnumDataType<?> that = (EnumDataType<?>) o;
        return enumClass.equals(that.enumClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enumClass);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enumClass", enumClass)
                .toString();
    }
}
