package com.manya.pdc;

import com.google.gson.Gson;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class GsonDataType<T> implements PersistentDataType<String, T> {
    private final Gson gson;
    private final Class<T> targetClass;

    public GsonDataType(Gson gson, Class<T> targetClass) {
        this.gson = gson;
        this.targetClass = targetClass;
    }
    @NotNull
    public Gson getGson() {
        return gson;
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
        return gson.toJson(complex, targetClass);
    }

    @Override
    public @NotNull T fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        return gson.fromJson(primitive, targetClass);
    }

}
