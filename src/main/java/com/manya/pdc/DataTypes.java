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

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.manya.key.KeyFactory;
import com.manya.pdc.base.*;
import com.manya.pdc.base.array.*;
import com.manya.pdc.base.collection.*;
import com.manya.pdc.gson.GsonDataType;
import com.manya.pdc.minecraft.ItemStackDataType;
import com.manya.pdc.minecraft.LocationDataType;
import com.manya.pdc.minecraft.NamespacedKeyDataType;
import com.manya.util.MapCollectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Server;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Objects.*;

/**
 * A class containing useful Persistent data types and factory methods to create them
 */
public final class DataTypes {
    private DataTypes() {
        throw new UnsupportedOperationException("no"); // instantiation is not supported, really
    }

    /**
     * UUID data type.
     */
    public static final UuidDataType UUID = new UuidDataType();
    /**
     * ItemStack data type.
     */
    public static final ItemStackDataType ITEM_STACK = new ItemStackDataType();
    /**
     * Namespaced key data type.
     */
    public static final NamespacedKeyDataType NAMESPACED_KEY = new NamespacedKeyDataType();
    /**
     * Adventure's Component data type.
     */
    public static final GsonDataType<Component> COMPONENT = new GsonDataType<>(GsonComponentSerializer.gson().serializer().getAdapter(Component.class), Component.class);


    /**
     * Boolean data type.
     */
    public static final BooleanDataType BOOLEAN = new BooleanDataType();
    /**
     * Boolean array data type. Uses bit set
     */
    public static final BooleanArrayDataType BOOLEAN_ARRAY = new BooleanArrayDataType();
    /**
     * String array data type.
     */
    public static final StringArrayDataType STRING_ARRAY = stringArray(StandardCharsets.UTF_8);
    /**
     * Float array data type.
     */
    public static final FloatArrayDataType FLOAT_ARRAY = new FloatArrayDataType();
    /**
     * Double array data type.
     */
    public static final DoubleArrayDataType DOUBLE_ARRAY = new DoubleArrayDataType();

    /**
     * Short array data type.
     */
    public static final ShortArrayDataType SHORT_ARRAY = new ShortArrayDataType();


    /**
     * Instant data type.
     */
    public static final InstantDataType INSTANT = new InstantDataType();
    /**
     * Duration data type.
     */
    public static final DurationDataType DURATION = new DurationDataType();


    /**
     * Creates a new data type for given enum class.
     *
     * @param enumClass enum class
     * @param <E>       enum type
     * @return enum data type
     */
    public static <E extends Enum<E>> EnumDataType<E> enumType(@NotNull Class<E> enumClass) {
        return new EnumDataType<>(enumClass);
    }

    /**
     * Creates the most efficient collection data type for given element type.
     * <pre> {@code
     *
     *  PersistentDataType<?, ImmutableList<ItemStack>> ITEM_LIST = DataTypes.collection(ImmutableList.toImmutableList(), DataTypes.ITEM_STACK);
     *  PersistentDataType<?, Set<Component>> COMPONENT_SET = DataTypes.collection(Collectors.toSet(), DataTypes.COMPONENT);
     *
     *  }</pre>
     *
     * @param collector       collection's collector
     * @param elementDataType persistent data type of element
     * @param <A>             collector's container type
     * @param <Z>             collection type
     * @param <E>             element type
     * @return collection data type
     */
    public static <A, Z extends Collection<E>, E> PersistentDataType<?, Z>
    collection(@NotNull Collector<E, A, Z> collector, @NotNull PersistentDataType<?, E> elementDataType) {
        return CollectionDataType.of(collector, elementDataType);
    }


    /**
     * Creates a new data type for map.
     * @param collector map collector
     * @param keyDataType key's data type
     * @param valueDataType value's data type
     * @param <A> collector's container type
     * @param <M> map type
     * @param <K> key type
     * @param <V> value type
     * @return map data type
     */
    public static <A, M extends Map<K, V>, K, V> MapDataType<A, M, K, V> map(@NotNull Collector<Map.Entry<K, V>, A, M> collector,
                                                                             @NotNull PersistentDataType<?, K> keyDataType,
                                                                             @NotNull PersistentDataType<?, V> valueDataType) {
        return new MapDataType<>(collector, keyDataType, valueDataType);
    }

    /**
     * Creates a new data type for HashMap
     * @param keyDataType key's data type
     * @param valueDataType value's data type
     * @param <K> key type
     * @param <V> value type
     * @return map data type
     */
    public static <K, V> MapDataType<HashMap<K, V>, HashMap<K, V>, K, V> hashMap(
            @NotNull PersistentDataType<?, K> keyDataType,
            @NotNull PersistentDataType<?, V> valueDataType) {
        return map(MapCollectors.toMap(HashMap::new), keyDataType, valueDataType);
    }

    /**
     * Creates a new data type for LinkedHashMap
     * @param keyDataType key's data type
     * @param valueDataType value's data type
     * @param <K> key type
     * @param <V> value type
     * @return map data type
     */
    public static <K, V> MapDataType<LinkedHashMap<K, V>, LinkedHashMap<K, V>, K, V> linkedHashMap(
            @NotNull PersistentDataType<?, K> keyDataType,
            @NotNull PersistentDataType<?, V> valueDataType
    ) {
        return map(MapCollectors.toMap(LinkedHashMap::new), keyDataType, valueDataType);

    }

    /**
     * Creates a serializable data type for given class. Java byte serialization will be used here.
     *
     * @param target target class
     * @param <T>    target type
     * @return serializable data type
     */
    public static <T> SerializableDataType<T> serializable(@NotNull Class<T> target) {
        return new SerializableDataType<>(target);
    }

    /**
     * Creates the most efficient list data type for given element type
     *
     * @param elementDataType element data type
     * @param <E>             element type
     * @return data type for list
     */
    public static <E> PersistentDataType<?, List<E>> list(@NotNull PersistentDataType<?, E> elementDataType) {
        return collection(Collectors.toList(), elementDataType);
    }

    /**
     * Creates the most efficient set data type for given element type
     *
     * @param elementDataType element data type
     * @param <E>             element type
     * @return data type for set
     */
    public static <E> PersistentDataType<?, Set<E>> set(@NotNull PersistentDataType<?, E> elementDataType) {
        return collection(Collectors.toSet(), elementDataType);
    }

    /**
     * Creates a new gson data type. Objects will be serialized as strings.
     *
     * @param adapter     type adapter
     * @param targetClass target class
     * @param <T>         target type
     * @return gson data type
     */
    public static <T> GsonDataType<T> gson(@NotNull TypeAdapter<T> adapter, @NotNull Class<T> targetClass) {
        return new GsonDataType<>(adapter, targetClass);
    }

    /**
     * Creates a new gson data type. Objects will be serialized as strings.
     *
     * @param gson        gson
     * @param targetClass target class
     * @param <T>         target type
     * @return gson data type
     */
    public static <T> GsonDataType<T> gson(@NotNull Gson gson, @NotNull Class<T> targetClass) {
        return gson(gson.getAdapter(targetClass), targetClass);
    }

    /**
     * Creates a new String array data type for given charset.
     *
     * @param charset charset to serialize/deserialize string with
     * @return string array data type.
     */
    public static StringArrayDataType stringArray(@NotNull Charset charset) {
        return new StringArrayDataType(charset);
    }

    /**
     * Creates a location data type.
     *
     * @param plugin owning plugin.
     * @return location data type.
     */
    public static LocationDataType location(@NotNull Plugin plugin) {
        requireNonNull(plugin, "plugin cannot be null!");
        return location(plugin.getServer(), KeyFactory.plugin(plugin));
    }

    /**
     * Creates a location data type.
     *
     * @param server     server to get worlds from
     * @param keyFactory factory of keys. used to create keys
     * @return location data type
     */
    public static LocationDataType location(@NotNull Server server, @NotNull KeyFactory keyFactory) {
        return new LocationDataType(server, keyFactory);
    }


}
