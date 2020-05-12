package com.naive.phase.Auxiliary.Magic.EVE.Interface;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IEVEStorageEntity extends IEVEStorage {
    EntityLivingBase getLiving();

    NBTTagCompound serializeNBT();

    void deserializeNBT(NBTTagCompound nbt);

}
