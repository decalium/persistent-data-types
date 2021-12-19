package com.manya.pdc.base.collection.primitive;

import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public class IntCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Integer, int[]> {
    public IntCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Integer, E> elementDataType) {
        super(collector, int[].class, elementDataType);
    }

    @Override
    public int @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        int[] ints = new int[size];
        Iterator<E> iterator = complex.iterator();
        for(int i = 0; i < size; i++) {
            ints[i] = elementDataType.toPrimitive(iterator.next(), context);
        }
        return ints;
    }

    @Override
    public @NotNull Z fromPrimitive(int @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = createContainer();
        for(int i : primitive) {
            accumulate(container, elementDataType.fromPrimitive(i, context));
        }
        return finish(container);
    }
}
