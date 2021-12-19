package com.manya.pdc.base;

import com.manya.pdc.ByteArrayDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.time.Instant;

public final class InstantDataType implements ByteArrayDataType<Instant> {
    private static final int INSTANT_BYTES = 12; // 8 (long) + 4 (int)
    @Override
    public int getFixedLength() {
        return INSTANT_BYTES;
    }

    @Override
    public @NotNull Class<Instant> getComplexType() {
        return Instant.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Instant complex, @NotNull PersistentDataAdapterContext context) {
        return ByteBuffer.allocate(INSTANT_BYTES)
                .putLong(complex.getEpochSecond())
                .putInt(complex.getNano())
                .array();
    }

    @Override
    public @NotNull Instant fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buf = ByteBuffer.wrap(primitive);
        long seconds = buf.getLong();
        int nanos = buf.getInt();
        return Instant.ofEpochSecond(seconds, nanos);
    }
}
