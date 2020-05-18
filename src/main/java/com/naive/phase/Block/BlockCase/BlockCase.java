package com.naive.phase.Block.BlockCase;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Block.PhaseTiledBase;
import com.naive.phase.Item.ItemWrench.IWrenchAble;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCase extends PhaseTiledBase implements IWrenchAble {

    @Registry.BlockInst
    public static BlockCase blockInst;

    @Registry.ItemInst
    public static ItemBlock itemInst;

    public BlockCase() {
        super("case", Material.IRON);
    }

    @Override
    public void onWrench(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing) {

    }

    @Override
    public void onShiftWrench(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing) {

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCase();
    }
}
