package com.manya.pdc.base.array;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class BooleanArrayDataType implements PersistentDataType<byte[], boolean[]> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<boolean[]> getComplexType() {
        return boolean[].class;
    }

    @Override
    public byte @NotNull [] toPrimitive(boolean @NotNull [] complex, @NotNull PersistentDataAdapterContext context) {
        BitSet set = new BitSet(complex.length);
        for(int i = 0; i < complex.length; i++) {
            set.set(i, complex[i]);
        }
        byte[] bytes = set.toByteArray();
        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES + bytes.length);
        buf.putInt(complex.length);
        buf.put(bytes);
        return buf.array();

    }

    @Override
    public boolean @NotNull [] fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buf = ByteBuffer.wrap(primitive);
        int length = buf.getInt();
        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        boolean[] booleans = new boolean[length];
        BitSet set = BitSet.valueOf(bytes);
        for(int i = 0; i < length; i++) {
            booleans[i] = set.get(i);
        }
        return booleans;
    }
}
