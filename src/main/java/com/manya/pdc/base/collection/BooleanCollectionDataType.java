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
package com.manya.pdc.base.collection;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public final class BooleanCollectionDataType<A, Z extends Collection<Boolean>> extends CollectionDataType<A, Z, Boolean, byte[]> {
    public BooleanCollectionDataType(Collector<Boolean, A, Z> collector) {
        super(collector, byte[].class);
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        Iterator<Boolean> iterator = complex.iterator();
        BitSet set = new BitSet(size);
        for(int i = 0; i < size; i++) {
            set.set(i, iterator.next());
        }
        byte[] bytes = set.toByteArray();
        return ByteBuffer.allocate(Integer.BYTES + bytes.length).putInt(size).put(bytes).array();
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        int size = buffer.getInt();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        BitSet set = BitSet.valueOf(bytes);
        A container = createContainer();
        for(int i = 0; i < size; i++) {
            accumulate(container, set.get(i));
        }
        return finish(container);
    }
}
