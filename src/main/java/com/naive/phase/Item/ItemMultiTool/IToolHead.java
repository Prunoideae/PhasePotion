package com.naive.phase.Item.ItemMultiTool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IToolHead {
    boolean canHeadHarvestBlock(ItemStack tool, IBlockState blockState);

    float getHeadDestroySpeed(ItemStack tool, IBlockState blockState);

    int getHeadHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState);

    int getDamageOnBlock(IBlockState state);

    int getOverlayType();
}
