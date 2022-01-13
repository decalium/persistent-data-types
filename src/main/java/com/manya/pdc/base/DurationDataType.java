/*
 * persistent-data-types
 * Copyright © 2022 Lesya Morozova
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
package com.manya.pdc.base;

import com.google.common.base.MoreObjects;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.time.Duration;

public final class DurationDataType implements PersistentDataType<byte[], Duration> {

    private static final int DURATION_BYTES = 12;

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Duration> getComplexType() {
        return Duration.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Duration complex, @NotNull PersistentDataAdapterContext context) {
        return ByteBuffer.allocate(DURATION_BYTES)
                .putLong(complex.getSeconds())
                .putInt(complex.getNano())
                .array();
    }

    @Override
    public @NotNull Duration fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        long seconds = buffer.getLong();
        int nanos = buffer.getInt();
        return Duration.ofSeconds(seconds, nanos);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DurationDataType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}
