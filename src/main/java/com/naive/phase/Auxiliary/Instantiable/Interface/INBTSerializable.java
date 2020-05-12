package com.naive.phase.Auxiliary.Instantiable.Interface;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable {
    NBTTagCompound writeToNBT();

    void readFromNBT(NBTTagCompound tag);
}
