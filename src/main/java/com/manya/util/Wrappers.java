package com.manya.util;

import com.google.common.collect.ImmutableBiMap;

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
        return (Class<T>) WRAPPERS.getOrDefault(primitive, primitive);
    }
    @SuppressWarnings("unchecked")
    public static <T> Class<T> unwrap(Class<T> wrapper) {
        return (Class<T>) WRAPPERS.inverse().getOrDefault(wrapper, wrapper);
    }

}
