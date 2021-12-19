package com.manya.pdc.base;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.time.Duration;

public final class DurationDataType implements PersistentDataType<byte[], Duration> {

    private static final int DURATION_BYTES = 12;

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Duration> getComplexType() {
        return Duration.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Duration complex, @NotNull PersistentDataAdapterContext context) {
        return ByteBuffer.allocate(DURATION_BYTES)
                .putLong(complex.getSeconds())
                .putInt(complex.getNano())
                .array();
    }

    @Override
    public @NotNull Duration fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        long seconds = buffer.getLong();
        int nanos = buffer.getInt();
        return Duration.ofSeconds(seconds, nanos);
    }
}
