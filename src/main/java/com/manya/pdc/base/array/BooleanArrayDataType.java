/*
 * persistent-data-types
 * Copyright © 2021 Lesya Morozova
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
