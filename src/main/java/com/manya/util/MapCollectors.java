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
package com.manya.util;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * An utility class to work with map collectors. Use with DataTypes#map
 */
public final class MapCollectors {
    /**
     * Creates a map collector for Map.Entry elements.
     * @param mergeFunction merge function
     * @param factory map factory
     * @param <M> map type
     * @param <K> key type
     * @param <V> value type
     * @return map collector
     */
    public static <M extends Map<K, V>, K, V> Collector<Map.Entry<K, V>, M, M> toMap(BinaryOperator<V> mergeFunction, Supplier<M> factory) {
        BiConsumer<M, Map.Entry<K, V>> accumulator = (map, element) -> {
            map.merge(element.getKey(), element.getValue(), mergeFunction);
        };
        return Collector.of(factory, accumulator, mapMerger(mergeFunction));
    }

    /**
     * Creates a map collector for Map.Entry elements.
     * @param factory map factory
     * @param <M> map type
     * @param <K> key type
     * @param <V> value type
     * @return map collector
     */
    public static <M extends Map<K, V>, K, V> Collector<Map.Entry<K, V>, M, M> toMap(Supplier<M> factory) {
        return toMap((e, r) -> e, factory);
    }

    private static <K, V, M extends Map<K, V>> BinaryOperator<M> mapMerger(BinaryOperator<V> mergeFunction) {
        return (m1, m2) -> {
            for (Map.Entry<K, V> e : m2.entrySet()) {
                m1.merge(e.getKey(), e.getValue(), mergeFunction);
            }
            return m1;
        };
    }
}
