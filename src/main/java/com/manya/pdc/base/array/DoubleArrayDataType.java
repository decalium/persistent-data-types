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
package com.manya.pdc.base.array;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/**
 * Double Array Data Type. Uses LONG_ARRAY internally
 */
public final class DoubleArrayDataType implements PersistentDataType<long[], double[]> {
    @Override
    public @NotNull Class<long[]> getPrimitiveType() {
        return long[].class;
    }

    @Override
    public @NotNull Class<double[]> getComplexType() {
        return double[].class;
    }

    @Override
    public long @NotNull [] toPrimitive(double @NotNull [] complex, @NotNull PersistentDataAdapterContext context) {
        long[] longs = new long[complex.length];
        for(int i = 0; i < complex.length; i++) {
            longs[i] = Double.doubleToLongBits(complex[i]);
        }
        return longs;
    }

    @Override
    public double @NotNull [] fromPrimitive(long @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        double[] result = new double[primitive.length];
        for(int i = 0; i < primitive.length; i++) {
            result[i] = Double.longBitsToDouble(primitive[i]);
        }
        return result;
    }
}
