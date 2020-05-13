package com.naive.phase.Block.BlockSpectrum;

import com.naive.phase.Auxiliary.Helper.VectorHelper;
import com.naive.phase.Auxiliary.Register.Registry.BlockInst;
import com.naive.phase.Auxiliary.Register.Registry.ItemInst;
import com.naive.phase.Base.Block.PhaseBlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSpectrum extends PhaseBlockBase {

    @BlockInst
    public static BlockSpectrum blockInst;

    @ItemInst
    public static ItemBlock itemInst;

    public BlockSpectrum() {
        super("spectrum_block", Material.SNOW);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack iStack = playerIn.getHeldItem(hand);

        IBlockState target = worldIn.getBlockState(pos.offset(facing));

        if (iStack.getItem() instanceof ItemBlock && target.getBlock().isReplaceable(worldIn, pos.offset(facing))
                && !worldIn.isRemote && !(((ItemBlock) iStack.getItem()).getBlock() instanceof BlockSpectrum)) {

            worldIn.setBlockToAir(pos);

            for (BlockPos offPos : VectorHelper.getSurrounded(pos, 2, facing)) {
                if (worldIn.getBlockState(offPos).getBlock() instanceof BlockSpectrum) {
                    IBlockState blockState = worldIn.getBlockState(offPos);
                    onBlockActivated(worldIn, offPos, blockState, playerIn, hand, facing, hitX, hitY, hitZ);
                }

            }

            worldIn.setBlockState(pos.offset(facing), blockInst.getDefaultState(), 3);
            iStack.getItem().onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }

        return true;
    }
}