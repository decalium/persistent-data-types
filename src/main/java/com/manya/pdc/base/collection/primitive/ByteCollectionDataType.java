package com.manya.pdc.base.collection.primitive;

import com.google.common.base.MoreObjects;
import com.manya.pdc.base.collection.TypeBasedCollectionDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;


public class ByteCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, Byte, byte[]> {
    public ByteCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<Byte, E> elementDataType) {
        super(collector, byte[].class, elementDataType);
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        byte[] bytes = new byte[size];
        Iterator<E> iterator = complex.iterator();
        for(int i = 0; i < size; i++) {
            bytes[i] = elementDataType.toPrimitive(iterator.next(), context);
        }
        return bytes;
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = createContainer();
        for(byte b : primitive) {
            accumulate(container, elementDataType.fromPrimitive(b, context));
        }

        return finish(container);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }


}
