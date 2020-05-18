package com.naive.phase.Base.Item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public interface IItemColorable {
    void setColor(ItemStack stack, float h, float s, float b);

    Vec3d getAppliedColor(ItemStack stack);

    boolean isHSB();
}
