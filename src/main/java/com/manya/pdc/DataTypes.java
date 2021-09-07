package com.manya.pdc;

import com.manya.pdc.array.DoubleArrayDataType;
import com.manya.pdc.array.FloatArrayDataType;
import com.manya.pdc.array.ShortArrayDataType;
import com.manya.pdc.array.StringArrayDataType;
import com.manya.pdc.collection.*;
import com.manya.pdc.collection.primitive.*;
import net.kyori.adventure.text.Component;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class DataTypes {
    private DataTypes() {
        throw new UnsupportedOperationException("no");
    }


    public static final UuidDataType UUID = new UuidDataType();
    public static final ItemStackDataType ITEM_STACK = new ItemStackDataType();
    public static final NamespacedKeyDataType NAMESPACED_KEY = new NamespacedKeyDataType();
    public static final ComponentDataType COMPONENT = new ComponentDataType();
    public static final BooleanDataType BOOLEAN = new BooleanDataType();


    public static final StringArrayDataType STRING_ARRAY = stringArray(StandardCharsets.UTF_8);
    public static final FloatArrayDataType FLOAT_ARRAY = new FloatArrayDataType();
    public static final DoubleArrayDataType DOUBLE_ARRAY = new DoubleArrayDataType();
    public static final ShortArrayDataType SHORT_ARRAY = new ShortArrayDataType();


    public static <E extends Enum<E>> EnumDataType<E> enumType(Class<E> enumClass) {
        return new EnumDataType<>(enumClass);
    }

    @SuppressWarnings("unchecked")
    public static <A, Z extends Collection<E>, E> CollectionDataType<A, Z, E, ?>
    collection(Collector<E, A, Z> collector, PersistentDataType<?, E> elementDataType) {
        Class<?> primitive = elementDataType.getPrimitiveType();
        if(primitive == PersistentDataContainer.class) {
            return new ArrayCollectionDataType<>(
                    collector,
                    castType(elementDataType),
                    PersistentDataType.TAG_CONTAINER_ARRAY,
                    PersistentDataContainer[]::new
            );
        } else if(primitive == String.class) {
            return new ArrayCollectionDataType<>(
                    collector,
                    (PersistentDataType<String, E>) elementDataType,
                    STRING_ARRAY,
                    String[]::new
            );
        } else if(primitive == byte[].class || primitive == Byte[].class) {
            if(!(elementDataType instanceof ByteArrayDataType)) {
                return new ByteArraysCollectionDataType<>(collector, (PersistentDataType<byte[], E>) elementDataType);
            }
            ByteArrayDataType<E> dt = (ByteArrayDataType<E>) elementDataType;
            OptionalInt length = dt.getFixedLength();

            return length.isPresent() ? new ByteArraysCollectionDataType<>(collector, dt, length.getAsInt()) :
                    new ByteArraysCollectionDataType<>(collector, dt);

        } else if(primitive == int.class || primitive == Integer.class) {
            return new IntCollectionDataType<>(
                    collector,
                    castType(elementDataType)
            );
        } else if(primitive == long.class || primitive == Long.class) {
            return new LongCollectionDataType<>(
                    collector,
                    castType(elementDataType)
            );
        } else if(primitive == byte.class || primitive == Byte.class) {
            return new ByteCollectionDataType<>(
                    collector,
                    castType(elementDataType)
            );
        }  else if(primitive == short.class || primitive == Short.class) {
            return new ShortCollectionDataType<>(
                    collector,
                    castType(elementDataType)
                    );
        } else if(primitive == float.class || primitive == Float.class) {
            return new FloatCollectionDataType<>(
                    collector,
                    castType(elementDataType)
            );
        } else if(primitive == double.class || primitive == Double.class) {
            return new DoubleCollectionDataType<>(
                    collector,
                    castType(elementDataType)
            );
        }  else {
            return new DefaultCollectionDataType<>(collector, elementDataType);
        }

    }

    public static <T> SerializableDataType<T> serializable(Class<T> target) {
        return new SerializableDataType<>(target);
    }

    public static <A, E> CollectionDataType<A, List<E>, E, ?> list(PersistentDataType<?, E> elementDataType) {
        return collection(Collectors.toList(), elementDataType);
    }
    public static <A, E> CollectionDataType<A, Set<E>, E, ?> set(PersistentDataType<?, E> elementDataType) {
        return collection(Collectors.toSet(), elementDataType);
    }
    public static <A, Z extends Collection<Component>> ComponentCollectionDataType<A, Z> componentCollection(Collector<Component, A, Z> collector) {
        return new ComponentCollectionDataType<>(collector);
    }
    public static <A> ComponentCollectionDataType<A, List<Component>> componentList() {
        return componentCollection(Collectors.toList());
    }
    public static <A> ComponentCollectionDataType<A, Set<Component>> componentSet() {
        return componentCollection(Collectors.toSet());
    }
    public static StringArrayDataType stringArray(Charset charset) {
        return new StringArrayDataType(charset);
    }

    public static LocationDataType location(Plugin plugin) {
        return new LocationDataType(plugin);
    }

    @SuppressWarnings("unchecked")
    private static <T, E> PersistentDataType<T, E> castType(PersistentDataType<?, E> type) {
        return (PersistentDataType<T, E>) type;
    }










}
