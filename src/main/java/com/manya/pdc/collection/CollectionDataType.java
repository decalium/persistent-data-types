package com.manya.pdc.collection;

import com.google.common.reflect.TypeToken;
import com.manya.pdc.ByteArrayDataType;
import com.manya.pdc.DataTypes;
import com.manya.pdc.GsonDataType;
import com.manya.pdc.collection.primitive.*;
import com.manya.util.Wrappers;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collector;

public abstract class CollectionDataType<A, Z extends Collection<E>, E, T> implements PersistentDataType<T, Z> {

    private final Class<Z> complexType;
    private final Class<T> primitiveType;
    private final Collector<E, A, Z> collector;

    public CollectionDataType(Collector<E, A, Z> collector, Class<T> primitiveType) {
        this.collector = collector;
        this.complexType = getCollectionClass(collector);
        this.primitiveType = primitiveType;
    }


    protected Collector<E, A, Z> getCollector() {
        return collector;
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
    private static <T, A, R> Class<R> getCollectionClass(Collector<T, A, R> collector) {
        return (Class<R>) collector.finisher().apply(collector.supplier().get());
    }

    @SuppressWarnings("unchecked")
    public static <A, Z extends Collection<E>, E> PersistentDataType<?, Z>
    of(Collector<E, A, Z> collector, PersistentDataType<?, E> elementDataType) {
        Class<?> primitive = Wrappers.wrap(elementDataType.getPrimitiveType());
        if(elementDataType instanceof GsonDataType) {
            return new GsonDataType<>(
                    ((GsonDataType<?>) elementDataType).getGson(),
                    getCollectionClass(collector)
            );
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
        } else if(primitive == byte[].class || primitive == Byte[].class) {
            if(!(elementDataType instanceof ByteArrayDataType)) {
                return new ByteArraysCollectionDataType<>(collector, (PersistentDataType<byte[], E>) elementDataType);
            }
            ByteArrayDataType<E> dt = (ByteArrayDataType<E>) elementDataType;
            return new ByteArraysCollectionDataType<>(collector, dt);

        } else if(primitive == Integer.class) {
            return new IntCollectionDataType<>(
                    collector,
                    (PersistentDataType<Integer, E>) elementDataType
            );
        } else if(primitive == Long.class) {
            return new LongCollectionDataType<>(
                    collector,
                    (PersistentDataType<Long, E>) elementDataType
            );
        } else if(primitive == Byte.class) {
            return new ByteCollectionDataType<>(
                    collector,
                    (PersistentDataType<Byte, E>) elementDataType
            );
        }  else if(primitive == Short.class) {
            return new ShortCollectionDataType<>(
                    collector,
                    (PersistentDataType<Short, E>) elementDataType
            );
        } else if(primitive == Float.class) {
            return new FloatCollectionDataType<>(
                    collector,
                    (PersistentDataType<Float, E>) elementDataType
            );
        } else if(primitive == Double.class) {
            return new DoubleCollectionDataType<>(
                    collector,
                    (PersistentDataType<Double, E>) elementDataType
            );
        }  else {
            return new DefaultCollectionDataType<>(collector, elementDataType);
        }

    }





}
