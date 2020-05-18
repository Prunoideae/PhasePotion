package com.naive.phase.Base.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class PhaseToolBase extends PhaseItemBase implements IPhaseToolGenXPlus {
    public PhaseToolBase(String registryName) {
        super(registryName);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isFull3D() {
        return true;
    }
}
