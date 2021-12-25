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

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public record IdentityDataType<T>(Class<T> target) implements PersistentDataType<T, T> {

    @Override
    public @NotNull
    Class<T> getPrimitiveType() {
        return target;
    }

    @Override
    public @NotNull
    Class<T> getComplexType() {
        return target;
    }

    @Override
    public @NotNull
    T toPrimitive(@NotNull T complex, @NotNull PersistentDataAdapterContext context) {
        return complex;
    }

    @Override
    public @NotNull
    T fromPrimitive(@NotNull T primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive;
    }
}
