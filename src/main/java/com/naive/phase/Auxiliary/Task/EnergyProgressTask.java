package com.naive.phase.Auxiliary.Task;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyProgressTask extends ProgressedTask {
    private EnergyStorage storage;

    public EnergyProgressTask(int maxProgress, TaskElement out) {
        super(0, maxProgress, out);
    }

    public void setStorage(EnergyStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean progress(int power) {
        int value = 0;
        if (storage != null) {
            value = storage.extractEnergy(power, false);
        }
        return super.progress(value);
    }

    @Override
    public AbstractTask copy() {
        return new EnergyProgressTask(maxProgress, out);
    }
}
