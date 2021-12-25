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
package com.manya.pdc.base;

import com.google.common.base.MoreObjects;
import com.manya.pdc.ByteArrayDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import com.manya.util.UuidUtil;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class UuidDataType implements ByteArrayDataType<UUID> {

    @Override
    public @NotNull Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Override
    public int getFixedLength() {
        return UuidUtil.BYTES;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull UUID complex, @NotNull PersistentDataAdapterContext context) {
        return UuidUtil.toByteArray(complex);
    }

    @Override
    public @NotNull UUID fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        return UuidUtil.fromByteArray(primitive);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UuidDataType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).toString();
    }
}
