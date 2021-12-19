package com.manya.pdc.gson;

import com.destroystokyo.paper.util.SneakyThrow;
import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

public class GsonDataType<T> implements PersistentDataType<String, T> {

    private final TypeAdapter<T> adapter;
    private final Class<T> targetClass;

    public GsonDataType(TypeAdapter<T> adapter, Class<T> targetClass) {
        this.adapter = Objects.requireNonNull(adapter, "adapter");
        this.targetClass = Objects.requireNonNull(targetClass, "targetClass");
    }

    @NotNull
    public TypeAdapter<T> getAdapter() {
        return adapter;
    }
    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<T> getComplexType() {
        return targetClass;
    }

    @Override
    public @NotNull String toPrimitive(@NotNull T complex, @NotNull PersistentDataAdapterContext context) {
        return adapter.toJson(complex);
    }

    @Override
    public @NotNull T fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            return adapter.fromJson(primitive);
        } catch (IOException e) {
            SneakyThrow.sneaky(e);
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GsonDataType<?> that = (GsonDataType<?>) o;
        return adapter.equals(that.adapter) && targetClass.equals(that.targetClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adapter, targetClass);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("adapter", adapter)
                .add("targetClass", targetClass)
                .toString();
    }
}
