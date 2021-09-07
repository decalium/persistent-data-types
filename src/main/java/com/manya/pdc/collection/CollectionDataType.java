package com.manya.pdc.collection;

import com.google.common.reflect.TypeToken;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collector;

public abstract class CollectionDataType<A, Z extends Collection<E>, E, T> implements PersistentDataType<T, Z> {

    private final Class<Z> complexType;
    private final Class<T> primitiveType;
    private final Collector<E, A, Z> collector;

    @SuppressWarnings("unchecked")
    public CollectionDataType(Collector<E, A, Z> collector, Class<T> primitiveType) {
        this.collector = collector;
        this.complexType = (Class<Z>) new TypeToken<Z>(getClass()){}.getRawType();
        this.primitiveType = primitiveType;
    }


    protected Collector<E, A, Z> getCollector() {
        return collector;
    }

    @Override
    public @NotNull Class<Z> getComplexType() {
        return complexType;
    }
    @Override
    public @NotNull Class<T> getPrimitiveType() {
        return primitiveType;
    }





}
