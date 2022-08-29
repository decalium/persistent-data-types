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
package com.manya.pdc;

import com.google.common.base.MoreObjects;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public final class SimplePersistentDataType<T, Z> implements PersistentDataType<T, Z> {

    private final Class<T> primitive;
    private final Class<Z> complex;
    private final Function<Z, T> toPrimitive;
    private final Function<T, Z> fromPrimitive;

    public SimplePersistentDataType(Class<T> primitive, Class<Z> complex, Function<Z, T> toPrimitive, Function<T, Z> fromPrimitive) {

        this.primitive = primitive;
        this.complex = complex;
        this.toPrimitive = toPrimitive;
        this.fromPrimitive = fromPrimitive;
    }

    @Override
    public @NotNull Class<T> getPrimitiveType() {
        return this.primitive;
    }

    @Override
    public @NotNull Class<Z> getComplexType() {
        return this.complex;
    }

    @Override
    public @NotNull T toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        return this.toPrimitive.apply(complex);
    }

    @Override
    public @NotNull Z fromPrimitive(@NotNull T primitive, @NotNull PersistentDataAdapterContext context) {
        return this.fromPrimitive.apply(primitive);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePersistentDataType<?, ?> that = (SimplePersistentDataType<?, ?>) o;
        return primitive.equals(that.primitive) && complex.equals(that.complex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primitive, complex);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("primitive", primitive)
                .add("complex", complex)
                .toString();
    }
}
