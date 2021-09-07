package com.manya.pdc.collection;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collector;

public final class DefaultCollectionDataType<A, Z extends Collection<E>, E, F> extends TypeBasedCollectionDataType<A, Z, E, F, PersistentDataContainer[]> {
    private final PersistentDataType<?, E> dataType;
    private static final NamespacedKey KEY = NamespacedKey.minecraft("element");
    public DefaultCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<F, E> dataType) {
        super(collector, PersistentDataContainer[].class, dataType);
        this.dataType = dataType;
    }

    @Override
    public PersistentDataContainer @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        PersistentDataContainer[] containers = new PersistentDataContainer[complex.size()];
        int cursor = 0;
        for(E element : complex) {
            PersistentDataContainer container = context.newPersistentDataContainer();
            container.set(KEY, dataType, element);
            containers[cursor] = container;
            cursor++;
        }
        return containers;
    }

    @Override
    public @NotNull Z fromPrimitive(PersistentDataContainer @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = getCollector().supplier().get();
        for(PersistentDataContainer c : primitive) {
            getCollector().accumulator().accept(container, c.get(KEY, dataType));
        }
        return getCollector().finisher().apply(container);
    }
}
