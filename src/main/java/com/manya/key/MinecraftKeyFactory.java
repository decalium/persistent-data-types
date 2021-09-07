package com.manya.key;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public final class MinecraftKeyFactory implements KeyFactory {
    MinecraftKeyFactory() {}
    @Override
    public @NotNull NamespacedKey create(@NotNull String value) {
        return NamespacedKey.minecraft(value);
    }
}
