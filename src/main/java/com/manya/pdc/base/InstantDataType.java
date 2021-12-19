package com.manya.pdc.base;

import com.google.common.base.MoreObjects;
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof InstantDataType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}
