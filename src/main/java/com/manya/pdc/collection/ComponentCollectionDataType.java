package com.manya.pdc.collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;


import java.util.Collection;
import java.util.stream.Collector;

public class ComponentCollectionDataType<A, Z extends Collection<Component>> extends CollectionDataType<A, Z, Component, String> {
    private final GsonComponentSerializer serializer;
    private final JsonParser parser = new JsonParser();
    public ComponentCollectionDataType(Collector<Component, A, Z> collector, GsonComponentSerializer serializer) {
        super(collector, String.class);
        this.serializer = serializer;
    }
    public ComponentCollectionDataType(Collector<Component, A, Z> collector) {
        this(collector, GsonComponentSerializer.gson());
    }


    @Override
    public @NotNull String toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        JsonArray array = new JsonArray();
        for(Component component : complex) {
            array.add(serializer.serializeToTree(component));
        }
        return array.toString();
    }

    @Override
    public @NotNull Z fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        A container = getCollector().supplier().get();
        JsonArray array = parser.parse(primitive).getAsJsonArray();
        for(JsonElement element : array) {
            getCollector().accumulator().accept(container, serializer.deserializeFromTree(element));
        }
        return getCollector().finisher().apply(container);
    }
}
