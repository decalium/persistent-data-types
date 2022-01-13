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

import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.stream.Collector;

public abstract class TypeBasedCollectionDataType<A, Z extends Collection<E>, E, F, T> extends CollectionDataType<A, Z, E, T> {

    protected final PersistentDataType<F, E> elementDataType;

    public TypeBasedCollectionDataType(Collector<E, A, Z> collector, Class<T> primitiveType, PersistentDataType<F, E> elementDataType) {
        super(collector, primitiveType);
        this.elementDataType = elementDataType;
    }


}
