package com.naive.phase.Item.ItemMatrix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IUpgrade {
    default boolean onDamageItem(World world, EntityPlayer player, ItemStack upgrade, ItemStack matrix, ItemStack tool, int damage) {
        return true;
    }

    void onUseItem(World world, EntityPlayer player, ItemStack upgrade, ItemStack matrix, ItemStack tool);

    int getEnergyCost();
}
