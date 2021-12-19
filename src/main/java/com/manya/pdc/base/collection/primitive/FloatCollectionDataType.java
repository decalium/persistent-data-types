package com.manya.pdc.base.collection.primitive;

import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public class FloatCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Float, int[]> {
    public FloatCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Float, E> elementDataType) {
        super(collector, int[].class, elementDataType);
    }

    @Override
    public int @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        int[] ints = new int[size];
        Iterator<E> iterator = complex.iterator();
        for(int i = 0; i < size; i++) {
            ints[i] = Float.floatToIntBits(elementDataType.toPrimitive(iterator.next(), context));
        }
        return ints;
    }

    @Override
    public @NotNull Z fromPrimitive(int @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = createContainer();
        for(int i : primitive) {
            accumulate(container, elementDataType.fromPrimitive(Float.intBitsToFloat(i), context));
        }
        return finish(container);
    }
}
