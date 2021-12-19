package com.manya.key;

import com.google.common.base.MoreObjects;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PluginKeyFactory implements KeyFactory {
    private final Plugin plugin;
    PluginKeyFactory(Plugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public @NotNull NamespacedKey create(@NotNull String value) {
        return new NamespacedKey(plugin, value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginKeyFactory that = (PluginKeyFactory) o;
        return plugin.equals(that.plugin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("plugin", plugin)
                .toString();
    }
}
