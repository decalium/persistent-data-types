package com.manya.pdc.base.map;

import com.google.gson.reflect.TypeToken;
import com.manya.key.KeyFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collector;

import static java.util.Objects.*;

public final class MapDataType<A, M extends Map<K, V>, K, V> implements PersistentDataType<PersistentDataContainer[], M> {
    private final PersistentDataType<?, K> keyDataType;
    private final PersistentDataType<?, V> valueDataType;

    private final Collector<Map.Entry<K, V>, A, M> collector;

    private final NamespacedKey keyKey, valueKey;
    private final Class<M> complex;
    @SuppressWarnings("unchecked")
    public MapDataType(@NotNull KeyFactory keyFactory,
                       @NotNull PersistentDataType<?, K> keyDataType,
                       @NotNull PersistentDataType<?, V> valueDataType,
                       Collector<Map.Entry<K, V>, A, M> collector) {

        this.keyDataType = keyDataType;
        this.valueDataType = valueDataType;
        this.keyKey = keyFactory.create("key");
        this.valueKey = keyFactory.create("value");
        this.collector = collector;
        this.complex = (Class<M>) new TypeToken<M>(){}.getRawType();
    }
    @Override
    public @NotNull Class<PersistentDataContainer[]> getPrimitiveType() {
        return PersistentDataContainer[].class;
    }

    @Override
    public @NotNull Class<M> getComplexType() {
        return complex;
    }

    @Override
    public PersistentDataContainer @NotNull [] toPrimitive(@NotNull M complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        PersistentDataContainer[] containers = new PersistentDataContainer[size];
        Iterator<Map.Entry<K, V>> iterator = complex.entrySet().iterator();

        for(int i = 0; i < size; i++) {
            Map.Entry<K, V> entry = iterator.next();
            PersistentDataContainer container = context.newPersistentDataContainer();
            container.set(keyKey, keyDataType, entry.getKey());
            container.set(valueKey, valueDataType, entry.getValue());
            containers[i] = container;
        }

        return containers;
    }

    @Override
    public @NotNull M fromPrimitive(PersistentDataContainer @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = collector.supplier().get();
        for(PersistentDataContainer pdc : primitive) {
            K key = requireNonNull(pdc.get(keyKey, keyDataType));
            V value = requireNonNull(pdc.get(valueKey, valueDataType));
            collector.accumulator().accept(container, Map.entry(key, value));
        }
        return collector.finisher().apply(container);
    }
}
