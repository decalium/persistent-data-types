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
import org.jetbrains.annotations.NotNull;

final class DecaliumKeyFactory implements KeyFactory { // this is bad.
    static final String DECALIUM = "decalium";
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull NamespacedKey create(@NotNull String value) {
        return new NamespacedKey(DECALIUM, value);
    }


}
