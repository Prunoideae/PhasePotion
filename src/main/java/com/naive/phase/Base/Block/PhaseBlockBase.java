package com.naive.phase.Base.Block;

import com.naive.phase.Phase;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class PhaseBlockBase extends Block {

    public PhaseBlockBase(String registryName, Material blockMaterialIn) {
        this(registryName, blockMaterialIn, null);
    }

    public PhaseBlockBase(String registryName, Material blockMaterialIn, MapColor mapColorIn) {
        super(blockMaterialIn, mapColorIn);
        setRegistryName(registryName);
        setUnlocalizedName(Phase.MODID + "." + registryName);
    }

}