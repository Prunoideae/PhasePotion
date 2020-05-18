package com.naive.phase.Block.BlockShift;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Block.PhaseTiledBase;
import com.naive.phase.Block.BlockShift.TileShift.TileShift;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockShift extends PhaseTiledBase {

    @Registry.BlockInst
    public static BlockShift blockInst;

    @Registry.ItemInst
    public static ItemBlock itemInst;

    public BlockShift() {
        super("shift", Material.ROCK);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileShift();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            ((TileShift) worldIn.getTileEntity(pos)).setFacing(facing);
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
