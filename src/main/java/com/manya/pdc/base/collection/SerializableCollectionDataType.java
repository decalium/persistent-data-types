package com.manya.pdc.base.collection;

import com.destroystokyo.paper.util.SneakyThrow;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Collection;
import java.util.stream.Collector;

public final class SerializableCollectionDataType<A, Z extends Collection<E>, E> extends CollectionDataType<A, Z, E, byte[]> {


    private final Class<E> elementType;

    public SerializableCollectionDataType(Collector<E, A, Z> collector, Class<E> elementType) {
        super(collector, byte[].class);
        this.elementType = elementType;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeInt(complex.size());
            for(E element : complex) {
                oos.writeObject(element);
            }
            oos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            SneakyThrow.sneaky(e);
        }
        return null;
    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(primitive);
            ObjectInputStream ois = new ObjectInputStream(bis);
            int size = ois.readInt();
            A container = createContainer();
            for(int i = 0; i < size; i++) {
                accumulate(container, elementType.cast(ois.readObject()));
            }
            ois.close();
            return finish(container);
        } catch (IOException | ClassNotFoundException e) {
            SneakyThrow.sneaky(e);
        }
        return null;
    }

}
