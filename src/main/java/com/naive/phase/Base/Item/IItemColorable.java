package com.naive.phase.Base.Item;

import net.minecraft.item.ItemStack;

public interface IItemColorable {
    void setColor(ItemStack stack, float h, float s, float b);
}
