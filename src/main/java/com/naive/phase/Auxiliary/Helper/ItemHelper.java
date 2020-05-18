package com.naive.phase.Auxiliary.Helper;

import net.minecraft.item.ItemStack;

public class ItemHelper {
    public static ItemStack resizeItemStack(ItemStack stack, int size) {
        ItemStack copy = stack.copy();
        copy.setCount(size);
        return copy;
    }
}
