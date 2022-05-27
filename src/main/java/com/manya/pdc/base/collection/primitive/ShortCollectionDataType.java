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
package com.manya.pdc.base.collection.primitive;

import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.stream.Collector;

public final class ShortCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Short, byte[]> {
    public ShortCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Short, E> elementDataType) {
        super(collector, byte[].class, elementDataType);
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.allocate(complex.size() * Short.BYTES);
        for(E element : complex) {
            buffer.putShort(elementDataType.toPrimitive(element, context));
        }
        return buffer.array();
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = createContainer();
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        while(buffer.remaining() >= Short.BYTES) {
            accumulate(container, elementDataType.fromPrimitive(buffer.getShort(), context));
        }
        return finish(container);
    }
}
