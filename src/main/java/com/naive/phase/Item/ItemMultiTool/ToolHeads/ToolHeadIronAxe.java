package com.naive.phase.Item.ItemMultiTool.ToolHeads;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Item.PhaseItemBase;
import com.naive.phase.Item.ItemMultiTool.IToolHead;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ToolHeadIronAxe extends PhaseItemBase implements IToolHead {

    @Registry.ItemInst
    public static ToolHeadIronAxe itemInst;

    public ToolHeadIronAxe() {
        super("toolhead_ironaxe");
        setMaxDamage(512);
    }

    @Override
    public boolean canHeadHarvestBlock(ItemStack tool, IBlockState blockState) {
        if (blockState == null)
            return false;
        Material blockMat = blockState.getMaterial();
        return blockMat == Material.CACTUS || blockMat == Material.WOOD || blockMat == Material.VINE;
    }

    @Override
    public float getHeadDestroySpeed(ItemStack tool, IBlockState blockState) {
        return canHeadHarvestBlock(tool, blockState) ? 5 : 0;
    }

    @Override
    public int getHeadHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        return canHeadHarvestBlock(stack, blockState) ? 2 : 0;
    }

    @Override
    public int getDamageOnBlock(IBlockState state) {
        return 1;
    }

    @Override
    public int getOverlayType() {
        return 2;
    }
}
