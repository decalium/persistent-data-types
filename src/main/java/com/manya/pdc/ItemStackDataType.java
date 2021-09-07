package com.manya.pdc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

public final class ItemStackDataType implements ByteArrayDataType<ItemStack> {

    @Override
    public @NotNull Class<ItemStack> getComplexType() {
        return ItemStack.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull ItemStack complex, @NotNull PersistentDataAdapterContext context) {
        return complex.serializeAsBytes();
    }

    @Override
    public @NotNull ItemStack fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        return ItemStack.deserializeBytes(primitive);
    }
}
