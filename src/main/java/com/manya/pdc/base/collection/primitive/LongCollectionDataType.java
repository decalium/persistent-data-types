package com.manya.pdc.base.collection.primitive;


import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public class LongCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Long, long[]> {
    public LongCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Long, E> elementDataType) {
        super(collector, long[].class, elementDataType);
    }

    @Override
    public long @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        long[] longs = new long[size];
        Iterator<E> iterator = complex.iterator();
        for(int i = 0; i < size; i++) {
            longs[i] = elementDataType.toPrimitive(iterator.next(), context);
        }
        return longs;
    }

    @Override
    public @NotNull Z fromPrimitive(long @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = createContainer();
        for(long l : primitive) {
            accumulate(container, elementDataType.fromPrimitive(l, context));
        }
        return finish(container);
    }
}
