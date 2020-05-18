package com.naive.phase.Item.ItemMultiTool.ToolHeads;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Item.PhaseItemBase;
import com.naive.phase.Item.ItemMultiTool.IToolHead;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ToolHeadIronPickaxe extends PhaseItemBase implements IToolHead {

    @Registry.ItemInst
    public static ToolHeadIronPickaxe itemInst;

    public ToolHeadIronPickaxe() {
        super("toolhead_ironpick");
        setMaxDamage(512);
    }

    @Override
    public boolean canHeadHarvestBlock(ItemStack tool, IBlockState blockState) {
        Material blockMat = blockState.getMaterial();
        return blockMat == Material.ROCK || blockMat == Material.IRON || blockMat == Material.ANVIL;
    }

    @Override
    public float getHeadDestroySpeed(ItemStack tool, IBlockState blockState) {
        Material mat = blockState.getMaterial();
        if (mat == Material.IRON || mat == Material.ANVIL || mat == Material.ROCK) {
            return 3;
        } else return 0f;
    }

    @Override
    public int getHeadHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        return toolClass.equals("pickaxe") ? 2 : 0;
    }

    @Override
    public int getDamageOnBlock(IBlockState state) {
        Material mat = state.getMaterial();
        if (mat == Material.GRASS)
            return 0;
        return (mat == Material.ROCK || mat == Material.ANVIL || mat == Material.IRON) ? 1 : 2;
    }

    @Override
    public int getOverlayType() {
        return 1;
    }
}
