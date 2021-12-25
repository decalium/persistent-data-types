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
package com.manya.util;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;

public final class Wrappers {
    private Wrappers() {
        throw new UnsupportedOperationException("no need lol");
    }
    private static final ImmutableBiMap<Class<?>, Class<?>> WRAPPERS = new ImmutableBiMap.Builder<Class<?>, Class<?>>()
            .put(boolean.class, Boolean.class)
            .put(byte.class, Byte.class)
            .put(short.class, Short.class)
            .put(char.class, Character.class)
            .put(int.class, Integer.class)
            .put(float.class, Float.class)
            .put(long.class, Long.class)
            .put(double.class, Double.class)
            .put(void.class, Void.class)
            .build();

    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrap(Class<T> primitive) {
        if(primitive.isArray()) {
            return (Class<T>) Array.newInstance(wrap(primitive.getComponentType()), 0).getClass();
        }

        return (Class<T>) WRAPPERS.getOrDefault(primitive, primitive);
    }
    @SuppressWarnings("unchecked")
    public static <T> Class<T> unwrap(Class<T> wrapper) {
        if(wrapper.isArray()) {
            return (Class<T>) Array.newInstance(unwrap(wrapper.getComponentType()), 0).getClass();
        }
        return (Class<T>) WRAPPERS.inverse().getOrDefault(wrapper, wrapper);
    }

}
