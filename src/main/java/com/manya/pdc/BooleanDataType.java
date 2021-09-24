package com.manya.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BooleanDataType implements PersistentDataType<Byte, Boolean> {
    private static final byte TRUE = 1, FALSE = 0;
    @Override
    public @NotNull Class<Byte> getPrimitiveType() {
        return byte.class;
    }

    @Override
    public @NotNull Class<Boolean> getComplexType() {
        return boolean.class;
    }

    @Override
    public @NotNull Byte toPrimitive(@NotNull Boolean complex, @NotNull PersistentDataAdapterContext context) {
        return complex ? TRUE : FALSE;
    }

    @Override
    public @NotNull Boolean fromPrimitive(@NotNull Byte primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive == TRUE;
    }
}
