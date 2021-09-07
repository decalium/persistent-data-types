package com.manya.pdc.collection.primitive;


import com.manya.pdc.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collector;

public class LongCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Long, long[]> {
    public LongCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Long, E> elementDataType) {
        super(collector, long[].class, elementDataType);
    }

    @Override
    public long @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        long[] longs = new long[complex.size()];
        int cursor = 0;
        for(E element : complex) {
            longs[cursor] = elementDataType.toPrimitive(element, context);
            cursor++;
        }
        return longs;
    }

    @Override
    public @NotNull Z fromPrimitive(long @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = getCollector().supplier().get();
        for(long l : primitive) {
            getCollector().accumulator().accept(container, elementDataType.fromPrimitive(l, context));
        }
        return getCollector().finisher().apply(container);
    }
}
