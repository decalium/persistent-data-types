package com.manya.pdc.base.array;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/**
 * Double Array Data Type. Uses LONG_ARRAY internally
 */
public class DoubleArrayDataType implements PersistentDataType<long[], double[]> {
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
