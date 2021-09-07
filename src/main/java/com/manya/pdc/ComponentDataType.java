package com.manya.pdc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class ComponentDataType implements PersistentDataType<String, Component> {
    private final GsonComponentSerializer serializer;
    public ComponentDataType(GsonComponentSerializer serializer) {
        this.serializer = serializer;
    }
    public ComponentDataType() {
        this(GsonComponentSerializer.gson());
    }
    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Component> getComplexType() {
        return Component.class;
    }

    @Override
    public @NotNull String toPrimitive(@NotNull Component complex, @NotNull PersistentDataAdapterContext context) {
        return serializer.serialize(complex);
    }

    @Override
    public @NotNull Component fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        return serializer.deserialize(primitive);
    }
}
