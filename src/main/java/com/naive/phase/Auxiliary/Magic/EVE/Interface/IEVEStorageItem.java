package com.naive.phase.Auxiliary.Magic.EVE.Interface;

import com.naive.phase.Auxiliary.Magic.EVE.Interface.IEVEStorage;
import net.minecraft.item.ItemStack;

public interface IEVEStorageItem extends IEVEStorage {
    ItemStack getContainer();
}
