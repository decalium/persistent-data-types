package com.manya.pdc.base.collection.primitive;

import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collector;

public class IntCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Integer, int[]> {
    public IntCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Integer, E> elementDataType) {
        super(collector, int[].class, elementDataType);
    }

    @Override
    public int @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int[] ints = new int[complex.size()];
        int cursor = 0;
        for(E element : complex) {
            ints[cursor] = elementDataType.toPrimitive(element, context);
            cursor++;
        }
        return ints;
    }

    @Override
    public @NotNull Z fromPrimitive(int @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = getCollector().supplier().get();
        for(int i : primitive) {
            getCollector().accumulator().accept(container, elementDataType.fromPrimitive(i, context));
        }
        return getCollector().finisher().apply(container);
    }
}
