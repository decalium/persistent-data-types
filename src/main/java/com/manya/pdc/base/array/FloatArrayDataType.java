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
 * Float Array Data Type. Uses INT_ARRAY internally.
 */
public final class FloatArrayDataType implements PersistentDataType<int[], float[]> {
    @Override
    public @NotNull Class<int[]> getPrimitiveType() {
        return int[].class;
    }

    @Override
    public @NotNull Class<float[]> getComplexType() {
        return float[].class;
    }

    @Override
    public int @NotNull [] toPrimitive(float @NotNull [] complex, @NotNull PersistentDataAdapterContext context) {
        int[] ints = new int[complex.length];
        for(int i = 0; i < complex.length; i++) {
            ints[i] = Float.floatToIntBits(complex[i]);
        }
        return ints;
    }

    @Override
    public float @NotNull [] fromPrimitive(int @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        float[] result = new float[primitive.length];
        for(int i = 0; i < primitive.length; i++) {
            result[i] = Float.intBitsToFloat(primitive[i]);
        }
        return result;
    }
}
