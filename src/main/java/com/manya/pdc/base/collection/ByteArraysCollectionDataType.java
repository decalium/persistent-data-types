/*
 * persistent-data-types
 * Copyright © 2021 Lesya Morozova
 *
 * persistent-data-types is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * persistent-data-types is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with persistent-data-types. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Lesser General Public License.
 */
package com.manya.pdc.base.collection;

import com.google.common.base.Preconditions;
import com.manya.pdc.ByteArrayDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;


public final class ByteArraysCollectionDataType<A, Z extends Collection<E>, E> extends TypeBasedCollectionDataType<A, Z, E, byte[], byte[]> {
    private static final BytePackager DYNAMIC = new DynamicLengthBytePackager();
    private final BytePackager bytePackager;


    public ByteArraysCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<byte[], E> type) {
        this(collector,
                type,
                type instanceof ByteArrayDataType ?
                        new FixedLengthBytePackager(((ByteArrayDataType<?>) type).getFixedLength()) : DYNAMIC);
    }

    public ByteArraysCollectionDataType(Collector<E, A, Z> collector, ByteArrayDataType<E> type) {
        this(collector, type, new FixedLengthBytePackager(type.getFixedLength()));
    }

    public ByteArraysCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<byte[], E> type, BytePackager bytePackager) {
        super(collector, byte[].class, type);
        this.bytePackager = bytePackager;
    }


    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        Iterator<E> iterator = complex.iterator();
        byte[][] bytes = new byte[complex.size()][];
        for (int i = 0; i < complex.size(); i++) {
            bytes[i] = elementDataType.toPrimitive(iterator.next(), context);
        }
        return bytePackager.pack(bytes);
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        byte[][] bytes = bytePackager.unpack(primitive);
        A container = createContainer();
        for (byte[] b : bytes) {
            accumulate(container, elementDataType.fromPrimitive(b, context));
        }
        return finish(container);

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
            for (byte[] b : bytes) {
                Preconditions.checkState(b.length == length, "error caused, array length doesn't equal to the fixed one");
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
            for (int i = 0; i < size; i++) {
                byte[] b = new byte[length];
                buffer.get(b);
                result[i] = b;
            }
            return result;
        }

    }

    private static class DynamicLengthBytePackager implements BytePackager {

        @Override
        public byte[] pack(byte[][] bytes) {
            int totalSize = Integer.BYTES + bytes.length * Integer.BYTES;
            for(byte[] b : bytes) {
                totalSize += b.length;
            }
            ByteBuffer buffer = ByteBuffer.allocate(totalSize);
            buffer.putInt(bytes.length);
            for(byte[] b : bytes) {
                buffer.putInt(b.length).put(b);
            }
            return buffer.array();
        }

        @Override
        public byte[][] unpack(byte[] bytes) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            int len = buffer.getInt();
            byte[][] result = new byte[len][];
            for(int i = 0; i < len; i++) {
                int size = buffer.getInt();
                byte[] b = new byte[size];
                buffer.get(b);
                result[i] = b;
            }
            return result;
        }
    }

}
