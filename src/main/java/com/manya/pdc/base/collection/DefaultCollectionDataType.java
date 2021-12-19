package com.manya.pdc.base.collection;

import com.manya.key.KeyFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public final class DefaultCollectionDataType<A, Z extends Collection<E>, E, F> extends TypeBasedCollectionDataType<A, Z, E, F, PersistentDataContainer[]> {
    private final PersistentDataType<?, E> dataType;
    private static final NamespacedKey KEY = KeyFactory.DECALIUM.create("element");
    public DefaultCollectionDataType(Collector<E, A, Z> collector, PersistentDataType<F, E> dataType) {
        super(collector, PersistentDataContainer[].class, dataType);
        this.dataType = dataType;
    }

    @Override
    public PersistentDataContainer @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        int size = complex.size();
        PersistentDataContainer[] containers = new PersistentDataContainer[size];
        Iterator<E> iterator = complex.iterator();
        for(int i = 0; i < size; i++) {
            PersistentDataContainer container = context.newPersistentDataContainer();
            container.set(KEY, elementDataType, iterator.next());
            containers[i] = container;
        }
        return containers;
    }

    @Override
    public @NotNull Z fromPrimitive(PersistentDataContainer @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        A container = createContainer();
        for(PersistentDataContainer c : primitive) {
            accumulate(container, c.get(KEY, dataType));
        }
        return finish(container);
    }
}
