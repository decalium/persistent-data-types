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

/**
 * Short array data type. Uses BYTE_ARRAY internally.
 */
public final class ShortArrayDataType implements PersistentDataType<byte[], short[]> {
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
