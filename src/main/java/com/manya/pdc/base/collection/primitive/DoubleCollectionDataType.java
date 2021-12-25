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
package com.manya.pdc.base.collection.primitive;

import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public class DoubleCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Double, long[]> {

    public DoubleCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Double, E> elementDataType) {
        super(collector, long[].class, elementDataType);
    }

    @Override
    public long @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        long[] longs = new long[size];
        Iterator<E> iterator = complex.iterator();
        for(int i = 0; i < size; i++) {
            longs[i] = Double.doubleToLongBits(elementDataType.toPrimitive(iterator.next(), context));
        }
        return longs;
    }

    @Override
    public @NotNull Z fromPrimitive(long @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = createContainer();
        for(long l : primitive) {
            accumulate(container, elementDataType.fromPrimitive(Double.longBitsToDouble(l), context));
        }
        return finish(container);
    }
}
