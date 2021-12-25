
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
