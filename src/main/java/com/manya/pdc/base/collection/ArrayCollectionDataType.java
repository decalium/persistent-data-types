package com.manya.pdc.base.collection;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.IntFunction;
import java.util.stream.Collector;

public final class ArrayCollectionDataType<A, Z extends Collection<E>, E, T, K> extends TypeBasedCollectionDataType<A, Z, E, T, K> {
    private final PersistentDataType<K, T[]> arrayDataType;
    private final IntFunction<T[]> arrayCreator;

    public ArrayCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<T, E> elementDataType, PersistentDataType<K, T[]> arrayDataType, IntFunction<T[]> arrayCreator) {
        super(collector, arrayDataType.getPrimitiveType(), elementDataType);
        this.arrayDataType = arrayDataType;
        this.arrayCreator = arrayCreator;
    }



    @Override
    public @NotNull K toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        T[] array = arrayCreator.apply(complex.size());
        int cursor = 0;
        for(E element : complex) {
            array[cursor] = elementDataType.toPrimitive(element, context);
            cursor++;
        }
        return arrayDataType.toPrimitive(array, context);
    }

    @Override
    public @NotNull Z fromPrimitive(@NotNull K primitive, @NotNull PersistentDataAdapterContext context) {
        T[] array = arrayDataType.fromPrimitive(primitive, context);
        A container = getCollector().supplier().get();
        for(T t : array) {
            getCollector().accumulator().accept(container, elementDataType.fromPrimitive(t, context));
        }
        return getCollector().finisher().apply(container);
    }
}
