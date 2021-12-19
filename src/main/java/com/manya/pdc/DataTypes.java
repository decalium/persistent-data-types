package com.manya.pdc;

import com.google.gson.GsonBuilder;
import com.manya.key.KeyFactory;
import com.manya.pdc.base.*;
import com.manya.pdc.base.array.*;
import com.manya.pdc.base.collection.*;
import com.manya.pdc.base.map.MapDataType;
import com.manya.pdc.minecraft.ItemStackDataType;
import com.manya.pdc.minecraft.LocationDataType;
import com.manya.pdc.minecraft.NamespacedKeyDataType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;


import javax.management.InstanceNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.*;

public final class DataTypes {
    private DataTypes() {
        throw new UnsupportedOperationException("no");
    }


    public static final UuidDataType UUID = new UuidDataType();
    public static final ItemStackDataType ITEM_STACK = new ItemStackDataType();
    public static final NamespacedKeyDataType NAMESPACED_KEY = new NamespacedKeyDataType();
    public static final GsonDataType<Component> COMPONENT = new GsonDataType<>(gson().populator().apply(new GsonBuilder()).create(), Component.class);
    public static final BooleanDataType BOOLEAN = new BooleanDataType();



    public static final BooleanArrayDataType BOOLEAN_ARRAY = new BooleanArrayDataType();
    public static final StringArrayDataType STRING_ARRAY = stringArray(StandardCharsets.UTF_8);
    public static final FloatArrayDataType FLOAT_ARRAY = new FloatArrayDataType();
    public static final DoubleArrayDataType DOUBLE_ARRAY = new DoubleArrayDataType();
    public static final ShortArrayDataType SHORT_ARRAY = new ShortArrayDataType();

    public static final InstantDataType INSTANT = new InstantDataType();
    public static final DurationDataType DURATION = new DurationDataType();


    public static <E extends Enum<E>> EnumDataType<E> enumType(Class<E> enumClass) {
        return new EnumDataType<>(enumClass);
    }

    public static <A, Z extends Collection<E>, E> PersistentDataType<?, Z>
    collection(Collector<E, A, Z> collector, PersistentDataType<?, E> elementDataType) {
        return CollectionDataType.of(collector, elementDataType);
    }

    public static <T> SerializableDataType<T> serializable(Class<T> target) {
        return new SerializableDataType<>(target);
    }

    public static <E> PersistentDataType<?, List<E>> list(PersistentDataType<?, E> elementDataType) {
        return collection(Collectors.toList(), elementDataType);
    }
    public static <E> PersistentDataType<?, Set<E>> set(PersistentDataType<?, E> elementDataType) {
        return collection(Collectors.toSet(), elementDataType);
    }

    /* public static <A, M extends Map<K, V>, K, V> PersistentDataType<?, M> map(PersistentDataType<?, K> keyDataType, PersistentDataType<?, V> valueDataType) {
        return new MapDataType<>(KeyFactory.minecraft(), keyDataType, valueDataType, Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    } */

    public static StringArrayDataType stringArray(Charset charset) {
        return new StringArrayDataType(charset);
    }

    public static LocationDataType location(Plugin plugin) {
        return new LocationDataType(plugin);
    }











}
