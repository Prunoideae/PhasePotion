package com.naive.phase.Item.ItemWrench;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Item.PhaseItemBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends PhaseItemBase {

    @Registry.ItemInst
    public static ItemWrench itemInst;

    public ItemWrench() {
        super("wrench");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState blockState = worldIn.getBlockState(pos);
        if (blockState.getBlock() instanceof IWrenchAble) {

            if (player.isSneaking()) {
                ((IWrenchAble) blockState.getBlock()).onShiftWrench(player, worldIn, pos, hand, facing);
            } else {
                ((IWrenchAble) blockState.getBlock()).onShiftWrench(player, worldIn, pos, hand, facing);
            }
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.isSneaking()) {
            playerIn.getHeldItem(handIn).setTagCompound(new NBTTagCompound());
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
