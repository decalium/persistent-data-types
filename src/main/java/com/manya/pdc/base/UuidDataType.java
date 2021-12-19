package com.manya.pdc.base;

import com.manya.pdc.ByteArrayDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import com.manya.util.UuidUtil;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;
import java.util.UUID;

public final class UuidDataType implements ByteArrayDataType<UUID> {

    @Override
    public @NotNull Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Override
    public int getFixedLength() {
        return UuidUtil.BYTES;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull UUID complex, @NotNull PersistentDataAdapterContext context) {
        return UuidUtil.toByteArray(complex);
    }

    @Override
    public @NotNull UUID fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        return UuidUtil.fromByteArray(primitive);
    }
}
