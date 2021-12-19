package com.manya.test;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FakePersistentDataContainer implements PersistentDataContainer {

    private static final PersistentDataAdapterContext CONTEXT = new FakeAdapterContext();

    private final Map<NamespacedKey, Object> map = new LinkedHashMap<>();

    @Override
    public <T, Z> void set(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value) {
        map.put(key, type.toPrimitive(value, CONTEXT));

    }

    @Override
    public <T, Z> boolean has(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return map.containsKey(key);
    }

    @Override
    public <T, Z> @Nullable Z get(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        Object obj = map.get(key);
        @SuppressWarnings("unchecked")
        T t = (T) obj;
        return type.fromPrimitive(t, CONTEXT);
    }

    @Override
    public <T, Z> @NotNull Z getOrDefault(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z defaultValue) {
        Z value = get(key, type);
        return value == null ? defaultValue : value;
    }

    @Override
    public @NotNull Set<NamespacedKey> getKeys() {
        return map.keySet();
    }

    @Override
    public void remove(@NotNull NamespacedKey key) {
        map.remove(key);

    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public @NotNull PersistentDataAdapterContext getAdapterContext() {
        return CONTEXT;
    }

    static class FakeAdapterContext implements PersistentDataAdapterContext {

        @Override
        public @NotNull PersistentDataContainer newPersistentDataContainer() {
            return new FakePersistentDataContainer();
        }
    }
}
