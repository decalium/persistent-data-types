package com.manya.key;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class PluginKeyFactory implements KeyFactory {
    private final Plugin plugin;
    PluginKeyFactory(Plugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public @NotNull NamespacedKey create(@NotNull String value) {
        return new NamespacedKey(plugin, value);
    }
}
