package com.manya.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;
import java.util.function.Function;

public interface ByteArrayDataType<Z> extends PersistentDataType<byte[], Z> {
    @Override
    @NotNull default Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    int getFixedLength();

}
