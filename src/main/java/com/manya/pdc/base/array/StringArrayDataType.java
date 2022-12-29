/*
 * persistent-data-types
 * Copyright © 2022 Lesya Morozova
 *
 * persistent-data-types is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * persistent-data-types is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with persistent-data-types. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Lesser General Public License.
 */
package com.manya.pdc.base.array;

import com.google.common.base.Preconditions;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;


public final class StringArrayDataType implements PersistentDataType<byte[], String[]> {
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
        byte[][] serializedStrings = new byte[complex.length][];
        int totalSize = 0;
        for(int i = 0; i < serializedStrings.length; i++) {
            byte[] b = complex[i].getBytes(charset);
            totalSize += Integer.BYTES + b.length;
            serializedStrings[i] = b;
        }

        ByteBuffer buffer = ByteBuffer.allocate(totalSize + Integer.BYTES);
        buffer.putInt(complex.length);
        for(byte[] b : serializedStrings) {
            buffer.putInt(b.length);
            buffer.put(b);
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
