package com.naive.phase.Auxiliary.Magic.EVE.Interface;

import com.naive.phase.Auxiliary.Instantiable.Data.Math.Range;
import net.minecraft.nbt.NBTTagCompound;

public interface IARadCenter {

    Range getHueRange();

    Range getBrightnessRange();

    NBTTagCompound serializeNBT();

    void deserializeNBT(NBTTagCompound nbt);
}
