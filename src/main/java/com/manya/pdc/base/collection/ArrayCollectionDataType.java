package com.manya.pdc.base.collection;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
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
        int size = complex.size();
        T[] array = arrayCreator.apply(size);
        Iterator<E> iterator = complex.iterator();
        for(int i = 0; i < size; i++) {
            array[i] = elementDataType.toPrimitive(iterator.next(), context);
        }

        return arrayDataType.toPrimitive(array, context);
    }

    @Override
    public @NotNull Z fromPrimitive(@NotNull K primitive, @NotNull PersistentDataAdapterContext context) {
        T[] array = arrayDataType.fromPrimitive(primitive, context);
        A container = createContainer();
        for(T t : array) {
            accumulate(container, elementDataType.fromPrimitive(t, context));
        }
        return finish(container);
    }
}
