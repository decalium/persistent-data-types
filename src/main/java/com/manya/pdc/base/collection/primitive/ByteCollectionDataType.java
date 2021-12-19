package com.manya.pdc.base.collection.primitive;

import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collector;

/**
 * A data type for collections with Byte element.
 * @param <A> - Mutable Container of Collection
 * @param <Z> - Collection itself
 * @param <E> - Element
 */
public class ByteCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Byte, byte[]> {
    public ByteCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Byte, E> elementDataType) {
        super(collector, byte[].class, elementDataType);
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        byte[] bytes = new byte[complex.size()];
        int cursor = 0;
        for(E element : complex) {
            bytes[cursor] = elementDataType.toPrimitive(element, context);
            cursor++;
        }
        return bytes;
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = getCollector().supplier().get();
        for(byte b : primitive) {
            getCollector().accumulator().accept(container, elementDataType.fromPrimitive(b, context));
        }

        return getCollector().finisher().apply(container);
    }
}
