package com.manya.pdc.base.array;

import com.google.common.base.Preconditions;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

/**
 * Short array data type. Uses BYTE_ARRAY internally.
 */
public class ShortArrayDataType implements PersistentDataType<byte[], short[]> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<short[]> getComplexType() {
        return short[].class;
    }

    @Override
    public byte @NotNull [] toPrimitive(short @NotNull [] complex, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.allocate(complex.length * Short.BYTES);
        for(short s : complex) {
            buffer.putShort(s);
        }
        return buffer.array();
    }

    @Override
    public short @NotNull [] fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        Preconditions.checkState(primitive.length % Short.BYTES == 0);
        int size = primitive.length / Short.BYTES;
        short[] result = new short[size];
        for(int i = 0; i < size; i++) {
            result[i] = buffer.getShort();
        }
        return result;
    }
}
