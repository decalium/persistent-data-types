package com.manya.pdc.base.collection;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public class BooleanCollectionDataType<A, Z extends Collection<Boolean>> extends CollectionDataType<A, Z, Boolean, byte[]> {
    public BooleanCollectionDataType(Collector<Boolean, A, Z> collector) {
        super(collector, byte[].class);
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        Iterator<Boolean> iterator = complex.iterator();
        BitSet set = new BitSet(size);
        for(int i = 0; i < size; i++) {
            set.set(i, iterator.next());
        }
        byte[] bytes = set.toByteArray();
        return ByteBuffer.allocate(Integer.BYTES + bytes.length).putInt(size).put(bytes).array();
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        int size = buffer.getInt();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        BitSet set = BitSet.valueOf(bytes);
        A container = createContainer();
        for(int i = 0; i < size; i++) {
            accumulate(container, set.get(i));
        }
        return finish(container);
    }
}
