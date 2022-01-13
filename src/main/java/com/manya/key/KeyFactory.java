/*
 * persistent-data-types
 * Copyright © 2022 Lesya Morozova
 *
 * persistent-data-types is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * persistent-data-types is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with persistent-data-types. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Lesser General Public License.
 */
package com.manya.key;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


/**
 * an interface that represents factory of NamespacedKeys. I hate typing long constructor calls!
 */
@FunctionalInterface
public interface KeyFactory {
    /**
     * key factory implementation with minecraft namespace
     */
    MinecraftKeyFactory MINECRAFT = new MinecraftKeyFactory();
    KeyFactory DECALIUM = new DecaliumKeyFactory();

    /**
     * Creates an namespacedKey with given value
     * @param value - the value
     * @return created key
     */
    @NotNull
    NamespacedKey create(@NotNull String value);



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
