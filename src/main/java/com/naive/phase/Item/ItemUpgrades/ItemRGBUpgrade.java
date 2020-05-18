package com.naive.phase.Item.ItemUpgrades;

import com.naive.phase.Auxiliary.Register.Registry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRGBUpgrade extends ItemUpgradeBlank {

    @Registry.ItemInst
    public static ItemRGBUpgrade itemInst;

    public ItemRGBUpgrade() {
        super("upgrade_rgb");
    }

    @Override
    public int getEnergyCost(World world, EntityPlayer player, ItemStack upgrade, ItemStack matrix, ItemStack tool) {
        return 20;
    }
}
