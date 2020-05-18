package com.naive.phase.Block.BlockTransmitter;

import com.naive.phase.Auxiliary.Helper.NBTHelper;
import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Block.BlockTransmitter.TileTransmitter.TileFETransmitter;
import com.naive.phase.Item.ItemWrench.IWrenchAble;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlockFluxTransmitter extends BlockTransmitter implements IWrenchAble {

    @Registry.BlockInst
    public static BlockFluxTransmitter blockInst;

    @Registry.ItemInst
    public static ItemBlock itemInst;

    public BlockFluxTransmitter() {
        super("rf_transmitter");
    }

    @Override
    public void onWrench(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing) {

    }

    @Override
    public void onShiftWrench(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing) {

    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        String uuid = NBTHelper.getOrDefault(stack.getTagCompound(), "uuid", UUID.randomUUID().toString());
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null) {
            ((TileFETransmitter) te).setUUID(uuid);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFETransmitter();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null) {
                String uuid = ((TileFETransmitter) te).getUUID();
                worldIn.setBlockState(pos, state.withProperty(SENDER, state.getValue(SENDER) == 0 ? 1 : 0), 2);
                te = worldIn.getTileEntity(pos);
                if (te != null)
                    ((TileFETransmitter) te).setUUID(uuid);
            }
            return true;
        } else {
            Item it = playerIn.getHeldItem(hand).getItem();
            if (it instanceof ItemBlock && ((ItemBlock) it).getBlock() instanceof BlockFluxTransmitter) {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null) {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setString("uuid", ((TileFETransmitter) te).getUUID());
                    playerIn.getHeldItem(hand).setTagCompound(tag);
                    return true;
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
