package com.manya.util;

import com.google.common.collect.ImmutableList;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.bukkit.persistence.PersistentDataType.*;

public final class PersistentDataUtils {
    private static final ImmutableList<PersistentDataType<?, ?>> PRIMITIVES =
            ImmutableList.of(BYTE_ARRAY, STRING, TAG_CONTAINER, TAG_CONTAINER_ARRAY, INTEGER, LONG, DOUBLE, FLOAT, BYTE, INTEGER_ARRAY, LONG_ARRAY); // do the most popular ones first

    private PersistentDataUtils() {}
    @Nullable
    public static Object getRaw(@NotNull PersistentDataContainer container, @NotNull NamespacedKey key) {
        for(PersistentDataType<?, ?> dataType : PRIMITIVES) {
            Object val = container.get(key, dataType);
            if(val != null) return val;
        }
        return null;
    }




    public static void copy(PersistentDataContainer from, PersistentDataContainer to) {
        for(NamespacedKey key : from.getKeys()) {
            for(PersistentDataType<?, ?> type : PRIMITIVES) {
                Object val = from.get(key, type);
                if(val != null) {
                    unsafeSet(to, key, type, val);
                    break;
                }
            }
        }
    }

    private static <V> void unsafeSet(PersistentDataContainer container, NamespacedKey key,  PersistentDataType<?, V> type, Object value) {
        container.set(key, type, type.getComplexType().cast(value));
    }
}
