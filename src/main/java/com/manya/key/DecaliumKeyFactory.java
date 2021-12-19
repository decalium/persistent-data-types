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
