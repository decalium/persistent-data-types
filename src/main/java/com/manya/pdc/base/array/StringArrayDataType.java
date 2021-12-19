package com.manya.pdc.base.array;

import com.google.common.base.Preconditions;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;


public class StringArrayDataType implements PersistentDataType<byte[], String[]> {
    private final Charset charset;
    public StringArrayDataType(Charset charset) {
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<String[]> getComplexType() {
        return String[].class;
    }

    @Override
    public byte @NotNull [] toPrimitive(String @NotNull [] complex, @NotNull PersistentDataAdapterContext context) {
        int totalSize = 0;
        for(String s : complex) totalSize += s.length();
        totalSize += Integer.BYTES * complex.length;
        ByteBuffer buffer = ByteBuffer.allocate(totalSize + Integer.BYTES);
        buffer.putInt(totalSize);
        for(String s : complex) {
            buffer.putInt(s.length());
            buffer.put(s.getBytes(charset));
        }
        return buffer.array();
    }

    @Override
    public String @NotNull [] fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        int totalSize = buffer.getInt();
        String[] result = new String[totalSize];
        for(int i = 0; i < totalSize; i++) {
            int length = buffer.getInt();
            Preconditions.checkState(buffer.remaining() >= length, "corrupted input");
            byte[] raw = new byte[length];
            buffer.get(raw);
            result[i] = new String(raw, charset);
        }
        return result;
    }

}
