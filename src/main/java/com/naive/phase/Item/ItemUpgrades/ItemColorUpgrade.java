package com.naive.phase.Item.ItemUpgrades;

import com.naive.phase.Auxiliary.Helper.NBTHelper;
import com.naive.phase.Base.Item.IItemColorable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;

public class ItemColorUpgrade extends ItemUpgradeBlank implements IItemColorable {
    private static final String TAG_COLOR = "color";

    @Override
    public void setColor(ItemStack stack, float h, float s, float b) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setFloat("r", h);
        tag.setFloat("g", s);
        tag.setFloat("b", b);
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag(TAG_COLOR, tag);
    }

    @Override
    public Vec3d getAppliedColor(ItemStack stack) {
        NBTTagCompound tag = NBTHelper.getSafe(stack.getTagCompound(), TAG_COLOR);
        return new Vec3d(
                NBTHelper.getOrDefault(tag, "r", 1f),
                NBTHelper.getOrDefault(tag, "g", 1f),
                NBTHelper.getOrDefault(tag, "b", 1f));
    }

    @Override
    public boolean isHSB() {
        return false;
    }

}
