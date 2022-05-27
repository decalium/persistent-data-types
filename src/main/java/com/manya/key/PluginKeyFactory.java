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
