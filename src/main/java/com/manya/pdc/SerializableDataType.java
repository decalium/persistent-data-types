package com.manya.pdc;

import org.apache.commons.lang.SerializationException;
import org.apache.commons.lang.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public final class SerializableDataType<Z> implements PersistentDataType<byte[], Z> {
    private final Class<Z> complexType;

    public SerializableDataType(Class<Z> complexType) {
        this.complexType = complexType;
    }
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Z> getComplexType() {
        return complexType;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(complex);
            oos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("failed to serialize " + complex, e);
        }

    }

    @Override
    public @NotNull Z fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(primitive);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Z complex = complexType.cast(ois.readObject());
            ois.close();
            return complex;
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("failed to deserialize " + complexType, e);
        }
    }
}
