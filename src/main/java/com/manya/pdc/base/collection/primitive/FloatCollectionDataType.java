package com.manya.pdc.base.collection.primitive;

import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collector;

public class FloatCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Float, int[]> {
    public FloatCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Float, E> elementDataType) {
        super(collector, int[].class, elementDataType);
    }

    @Override
    public int @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int[] ints = new int[complex.size()];
        int cursor = 0;
        for(E element : complex) {
            ints[cursor] = Float.floatToIntBits(elementDataType.toPrimitive(element, context));
            cursor++;
        }
        return ints;
    }

    @Override
    public @NotNull Z fromPrimitive(int @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = getCollector().supplier().get();
        for(int i : primitive) {
            getCollector().accumulator().accept(container, elementDataType.fromPrimitive(Float.intBitsToFloat(i), context));
        }
        return getCollector().finisher().apply(container);
    }
}
