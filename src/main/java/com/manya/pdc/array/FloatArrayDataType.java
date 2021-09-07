package com.manya.pdc.array;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/**
 * Float Array Data Type. Uses INT_ARRAY internally.
 */
public class FloatArrayDataType implements PersistentDataType<int[], float[]> {
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
