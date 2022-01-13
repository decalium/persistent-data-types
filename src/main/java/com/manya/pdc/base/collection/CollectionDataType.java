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

import com.google.common.primitives.Primitives;
import com.manya.pdc.DataTypes;
import com.manya.pdc.gson.GsonDataType;
import com.manya.pdc.base.SerializableDataType;
import com.manya.pdc.base.collection.primitive.*;
import com.manya.pdc.gson.CollectionTypeAdapter;
import com.manya.util.TypeUtilities;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collector;

public abstract class CollectionDataType<A, Z extends Collection<E>, E, T> implements PersistentDataType<T, Z> {

    private final Class<Z> complexType;
    private final Class<T> primitiveType;
    protected final Collector<E, A, Z> collector;

    public CollectionDataType(Collector<E, A, Z> collector, Class<T> primitiveType) {
        this.collector = collector;
        this.complexType = TypeUtilities.getCollectionClass(collector);
        this.primitiveType = primitiveType;
    }


    protected A createContainer() {
        return collector.supplier().get();
    }

    protected void accumulate(A container, E element) {
        collector.accumulator().accept(container, element);
    }

    protected Z finish(A container) {
      return collector.finisher().apply(container);
    }



    @Override
    public @NotNull Class<Z> getComplexType() {
        return complexType;
    }
    @Override
    public @NotNull Class<T> getPrimitiveType() {
        return primitiveType;
    }


    @SuppressWarnings("unchecked")
    public static <A, Z extends Collection<E>, E> PersistentDataType<?, Z>
    of(Collector<E, A, Z> collector, PersistentDataType<?, E> elementDataType) {
        Objects.requireNonNull(collector, "collector");
        Objects.requireNonNull(elementDataType, "elementDataType");

        Class<?> primitive = Primitives.unwrap(elementDataType.getPrimitiveType());

        if(elementDataType instanceof GsonDataType) {
            GsonDataType<E> gsonDataType = (GsonDataType<E>) elementDataType;
            return new GsonDataType<>(new CollectionTypeAdapter<>(collector, gsonDataType.getAdapter()), TypeUtilities.getCollectionClass(collector));
        }

        if(elementDataType instanceof SerializableDataType) {
            return new SerializableCollectionDataType<>(collector, elementDataType.getComplexType());
        }
        if(primitive == PersistentDataContainer.class) {
            return new ArrayCollectionDataType<>(
                    collector,
                    (PersistentDataType<PersistentDataContainer, E>) elementDataType,
                    PersistentDataType.TAG_CONTAINER_ARRAY,
                    PersistentDataContainer[]::new
            );
        } else if(primitive == String.class) {
            return new ArrayCollectionDataType<>(
                    collector,
                    (PersistentDataType<String, E>) elementDataType,
                    DataTypes.STRING_ARRAY,
                    String[]::new
            );
        } else if(primitive == byte[].class) {
            return new ByteArraysCollectionDataType<>(collector, (PersistentDataType<byte[], E>) elementDataType);
        } else if(primitive == int.class) {
            return new IntCollectionDataType<>(
                    collector,
                    (PersistentDataType<Integer, E>) elementDataType
            );
        } else if(primitive == long.class) {
            return new LongCollectionDataType<>(
                    collector,
                    (PersistentDataType<Long, E>) elementDataType
            );
        } else if(primitive == byte.class) {
            return new ByteCollectionDataType<>(
                    collector,
                    (PersistentDataType<Byte, E>) elementDataType
            );
        }  else if(primitive == short.class) {
            return new ShortCollectionDataType<>(
                    collector,
                    (PersistentDataType<Short, E>) elementDataType
            );
        } else if(primitive == float.class) {
            return new FloatCollectionDataType<>(
                    collector,
                    (PersistentDataType<Float, E>) elementDataType
            );
        } else if(primitive == double.class) {
            return new DoubleCollectionDataType<>(
                    collector,
                    (PersistentDataType<Double, E>) elementDataType
            );
        }  else {
            return new DefaultCollectionDataType<>(collector, elementDataType);
        }

    }





}
