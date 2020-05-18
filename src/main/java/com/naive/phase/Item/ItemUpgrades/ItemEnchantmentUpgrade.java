package com.naive.phase.Item.ItemUpgrades;

import com.naive.phase.Auxiliary.Register.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnchantmentUpgrade extends ItemUpgradeBlank {

    @Registry.ItemInst
    public static ItemEnchantmentUpgrade itemInst;

    public ItemEnchantmentUpgrade() {
        super("upgrade_enchantment");
    }

    @Override
    public int getEnergyCost(World world, EntityPlayer player, ItemStack upgrade, ItemStack matrix, ItemStack tool) {
        int sum = 20;
        for (Integer i : EnchantmentHelper.getEnchantments(upgrade).values())
            sum += i;
        return sum;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return !stack.isItemEnchanted();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return !stack.isItemEnchanted();
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return !stack.isItemEnchanted();
    }
}
