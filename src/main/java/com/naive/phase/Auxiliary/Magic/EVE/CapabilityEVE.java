package com.naive.phase.Auxiliary.Magic.EVE;

import com.naive.phase.Auxiliary.Helper.MathHelper;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Range;
import com.naive.phase.Auxiliary.Magic.EVE.Interface.IARadCenter;
import com.naive.phase.Auxiliary.Magic.EVE.Interface.IEVEStorage;
import com.naive.phase.Auxiliary.Magic.EVE.Interface.IEVEStorageEntity;
import com.naive.phase.Auxiliary.Magic.EVE.Interface.IEVEStorageItem;
import com.naive.phase.Auxiliary.Magic.EVE.Storage.ARadCenterStorage;
import com.naive.phase.Auxiliary.Magic.EVE.Storage.EVEStorage;
import com.naive.phase.Auxiliary.Magic.EVE.Storage.EVEStorageItem;
import com.naive.phase.Auxiliary.Magic.EVE.Storage.EVEStorageLiving;
import com.naive.phase.Item.ItemPrism.ItemPrism;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityEVE {

    public static final String EVE_UNIQ = "eve_storage";

    @CapabilityInject(IEVEStorage.class)
    public static Capability<IEVEStorage> EVE = null;

    @CapabilityInject(IEVEStorageItem.class)
    public static Capability<IEVEStorageItem> EVE_ITEM = null;

    @CapabilityInject(IEVEStorageEntity.class)
    public static Capability<IEVEStorageEntity> EVE_ENTITY = null;

    public static Capability<IARadCenter> EVE_CHUNK = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEVEStorage.class, new Capability.IStorage<IEVEStorage>() {
                    @Nullable
                    @Override
                    public NBTBase writeNBT(Capability<IEVEStorage> capability, IEVEStorage instance, EnumFacing side) {
                        return instance.getEVE().writeToNBT();
                    }

                    @Override
                    public void readNBT(Capability<IEVEStorage> capability, IEVEStorage instance, EnumFacing side, NBTBase nbt) {
                        instance.getEVE().readFromNBT((NBTTagCompound) nbt);
                    }
                },
                () -> new EVEStorage(100));

        CapabilityManager.INSTANCE.register(IEVEStorageItem.class, new Capability.IStorage<IEVEStorageItem>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IEVEStorageItem> capability, IEVEStorageItem instance, EnumFacing side) {
                return instance.getEVE().writeToNBT();
            }

            @Override
            public void readNBT(Capability<IEVEStorageItem> capability, IEVEStorageItem instance, EnumFacing side, NBTBase nbt) {
                instance.getEVE().readFromNBT((NBTTagCompound) nbt);
            }
        }, () -> new EVEStorageItem(new ItemStack(ItemPrism.itemPrism), new Range(0, 1), new Range(0, 1), 100));

        CapabilityManager.INSTANCE.register(IEVEStorageEntity.class, new Capability.IStorage<IEVEStorageEntity>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IEVEStorageEntity> capability, IEVEStorageEntity instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IEVEStorageEntity> capability, IEVEStorageEntity instance, EnumFacing side, NBTBase nbt) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }, () -> new EVEStorageLiving(null, null, null, 0));

        CapabilityManager.INSTANCE.register(IARadCenter.class, new Capability.IStorage<IARadCenter>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IARadCenter> capability, IARadCenter instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IARadCenter> capability, IARadCenter instance, EnumFacing side, NBTBase nbt) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }, () -> new ARadCenterStorage(null, MathHelper.randRange(), MathHelper.randRange()));
    }
}