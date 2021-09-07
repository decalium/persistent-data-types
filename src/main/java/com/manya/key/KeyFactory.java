package com.manya.key;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * an interface that represents factory of NamespacedKeys. I hate typing long constructor calls!
 */
@FunctionalInterface
public interface KeyFactory extends Function<String, NamespacedKey> {
    /**
     * key factory implementation with minecraft namespace
     */
    MinecraftKeyFactory MINECRAFT = new MinecraftKeyFactory();

    /**
     * Creates an namespacedKey with given value
     * @param value - the value
     * @return created key
     */
    @NotNull
    NamespacedKey create(@NotNull String value);

    /**
     * does the same thing as create
     *
     */
    @Override
    default NamespacedKey apply(String value) {
        return create(value);
    }

    /**
     * Creates a PluginKeyFactory based on given plugin
     * @param plugin - a plugin to use
     * @return factory
     */
    static PluginKeyFactory plugin(@NotNull Plugin plugin) {
        return new PluginKeyFactory(plugin);
    }

    /**
     * returns a key factory with minecraft namespace.
     * @return key factory.
     */
    static MinecraftKeyFactory minecraft() {
        return KeyFactory.MINECRAFT;
    }

}
