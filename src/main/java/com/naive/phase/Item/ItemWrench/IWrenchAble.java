package com.naive.phase.Item.ItemWrench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchAble {
    void onWrench(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing);

    void onShiftWrench(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing);
}
