package com.naive.phase.Auxiliary.Tech;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyTransmitter {
    IEnergyStorage getStorage();

    boolean isSender();

}
