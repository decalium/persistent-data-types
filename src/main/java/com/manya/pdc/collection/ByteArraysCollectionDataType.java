package com.manya.pdc.collection;

import com.google.common.base.Preconditions;
import com.manya.pdc.ByteArrayDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.OptionalInt;
import java.util.stream.Collector;


public final class ByteArraysCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, byte[], byte[]> {
    private final BytePackager bytePackager;


    public ByteArraysCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<byte[], E> type) {
        this(collector, type, new DynamicLengthBytePackager());
    }
    public ByteArraysCollectionDataType(Collector<E, A, Z> collector, ByteArrayDataType<E> type) {
        super(collector, byte[].class, type);
        OptionalInt length = type.getFixedLength();
        this.bytePackager = length.isPresent() ? new FixedLengthBytePackager(length.getAsInt()) : new DynamicLengthBytePackager();
    }

    public ByteArraysCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<byte[], E> type, int elementByteLength) {
        this(collector, type, new FixedLengthBytePackager(elementByteLength));
    }


    private ByteArraysCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<byte[], E> type, BytePackager packager) {
        super(collector, byte[].class, type);
        this.bytePackager = packager;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        Iterator<E> iterator = complex.iterator();
        byte[][] bytes = new byte[complex.size()][];
        for(int i = 0; i < complex.size(); i++) {
            bytes[i] = elementDataType.toPrimitive(iterator.next(), context);
        }
        return bytePackager.pack(bytes);
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        byte[][] bytes = bytePackager.unpack(primitive);
        A container = getCollector().supplier().get();
        for(byte[] b : bytes) {
            getCollector().accumulator().accept(container, elementDataType.fromPrimitive(b, context));
        }
        return getCollector().finisher().apply(container);

    }
    private interface BytePackager {
        byte[] pack(byte[][] bytes);
        byte[][] unpack(byte[] bytes);

    }
    private static class FixedLengthBytePackager implements BytePackager {
        private final int length;
        private FixedLengthBytePackager(int length) {
            this.length = length;
        }
        @Override
        public byte[] pack(byte[][] bytes) {
            ByteBuffer buffer = ByteBuffer.allocate(length * bytes.length);
            for(byte[] b : bytes) {
                Preconditions.checkState(b.length == length, "error caused, array length isnt equals to the fixed one");
                buffer.put(b);
            }
            return buffer.array();
        }

        @Override
        public byte[][] unpack(byte[] bytes) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            Preconditions.checkState(bytes.length % length == 0);
            int size = bytes.length / length;
            byte[][] result = new byte[size][];
            for(int i = 0; i < size; i++) {
                byte[] b = new byte[length];
                buffer.get(b);
                result[i] = b;
            }
            return result;
        }

    }
    private static class DynamicLengthBytePackager implements BytePackager {
        private static final byte[][] EMPTY_2D_BYTE_ARRAY = new byte[0][];

        @Override
        public byte[] pack(byte[][] bytes) {
            int totalSize = 0;
            for(byte[] b : bytes) {
                totalSize += b.length; // actual byte array length + int that represents array size
            }
            totalSize += Integer.BYTES * bytes.length;
            ByteBuffer buffer = ByteBuffer.allocate(totalSize);
            for(byte[] b : bytes) {
                buffer.putInt(b.length);
                buffer.put(b);
            }
            return buffer.array();
        }

        @Override
        public byte[][] unpack(byte[] bytes) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            ArrayList<byte[]> byteList = new ArrayList<>();
            while(buffer.remaining() >= Integer.BYTES) {
                int size = buffer.getInt();
                byte[] b = new byte[size];
                buffer.get(b);
                byteList.add(b);
            }
            return byteList.toArray(EMPTY_2D_BYTE_ARRAY);
        }
    }

}
