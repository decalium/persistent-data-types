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
package com.manya.pdc.base;


import com.manya.key.KeyFactory;
import com.manya.util.TypeUtilities;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collector;

import static java.util.Objects.*;

public final class MapDataType<A, M extends Map<K, V>, K, V> implements PersistentDataType<PersistentDataContainer[], M> {

    private static final NamespacedKey KEY = KeyFactory.DECALIUM.create("key");
    private static final NamespacedKey VALUE = KeyFactory.DECALIUM.create("value");

    private final Collector<Map.Entry<K, V>, A, M> collector;
    private final Class<M> complexType;
    private final PersistentDataType<?, K> keyDataType;
    private final PersistentDataType<?, V> valueDataType;

    public MapDataType(Collector<Map.Entry<K, V>, A, M> collector, PersistentDataType<?, K> keyDataType, PersistentDataType<?, V> valueDataType) {
        this.collector = requireNonNull(collector, "collector");
        this.complexType = TypeUtilities.getCollectionClass(collector);
        this.keyDataType = requireNonNull(keyDataType, "keyDataType");
        this.valueDataType = requireNonNull(valueDataType, "valueDataType");
    }


    @Override
    public @NotNull Class<PersistentDataContainer[]> getPrimitiveType() {
        return PersistentDataContainer[].class;
    }

    @Override
    public @NotNull Class<M> getComplexType() {
        return complexType;
    }

    @Override
    public PersistentDataContainer @NotNull [] toPrimitive(@NotNull M complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        PersistentDataContainer[] containers = new PersistentDataContainer[size];
        Iterator<Map.Entry<K, V>> iterator = complex.entrySet().iterator();
        for(int i = 0; i < size; i++) {
            Map.Entry<K, V> entry = iterator.next();
            PersistentDataContainer container = context.newPersistentDataContainer();
            container.set(KEY, keyDataType, entry.getKey());
            container.set(VALUE, valueDataType, entry.getValue());
            containers[i] = container;
        }
        return containers;
    }

    @Override
    public @NotNull M fromPrimitive(PersistentDataContainer @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = collector.supplier().get();
        for(PersistentDataContainer pdc : primitive) {
            K key = requireNonNull(pdc.get(KEY, keyDataType), "key must not be null");
            V value = requireNonNull(pdc.get(VALUE, valueDataType), "value most not be null");
            collector.accumulator().accept(container, new AbstractMap.SimpleEntry<>(key, value));
        }
        return collector.finisher().apply(container);
    }
}
