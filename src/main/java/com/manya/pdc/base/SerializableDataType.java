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
import org.apache.commons.lang.SerializationException;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

public final class SerializableDataType<Z> implements PersistentDataType<byte[], Z> {
    private final Class<Z> complexType;

    public SerializableDataType(Class<Z> complexType) {
        this.complexType = Objects.requireNonNull(complexType, "complexType");
    }
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Z> getComplexType() {
        return complexType;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(complex);
            oos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("failed to serialize " + complex, e);
        }

    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(primitive);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Z complex = complexType.cast(ois.readObject());
            ois.close();
            return complex;
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("failed to deserialize " + complexType.getName(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializableDataType<?> that = (SerializableDataType<?>) o;
        return complexType.equals(that.complexType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complexType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("complexType", complexType)
                .toString();
    }
}
