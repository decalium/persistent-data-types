package com.manya.pdc.base.collection;

import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.stream.Collector;

public abstract class TypeBasedCollectionDataType<A, Z extends Collection<E>, E, F, T> extends CollectionDataType<A, Z, E, T> {

    protected final PersistentDataType<F, E> elementDataType;

    public TypeBasedCollectionDataType(Collector<E, A, Z> collector, Class<T> primitiveType, PersistentDataType<F, E> elementDataType) {
        super(collector, primitiveType);
        this.elementDataType = elementDataType;
    }


}
