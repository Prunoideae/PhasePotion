package com.naive.phase.Item.ItemRFBattery;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Item.PhaseItemBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

//TODO: More battery.
public class ItemRFBattery extends PhaseItemBase {

    @Registry.ItemInst
    public static ItemRFBattery itemInst;

    private final int capacity;
    private final int power;

    public ItemRFBattery() {
        this("rf_battery", 100000, 100);
    }

    public ItemRFBattery(String registryName, int capacity, int power) {
        super(registryName);
        this.capacity = capacity;
        this.power = power;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new EnergyHandler(capacity, power);
    }

    private static class EnergyHandler implements ICapabilitySerializable<NBTBase> {
        private final EnergyStorage storage;

        private EnergyHandler(int capacity, int power) {
            this.storage = new EnergyStorage(capacity, power);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityEnergy.ENERGY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(storage) : null;
        }

        @Override
        public NBTBase serializeNBT() {
            return CapabilityEnergy.ENERGY.writeNBT(storage, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityEnergy.ENERGY.readNBT(storage, null, nbt);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return Color.HSBtoRGB(0, 1, 1);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if (storage != null) {
            return (storage.getMaxEnergyStored() - storage.getEnergyStored()) / (double) storage.getMaxEnergyStored();
        }
        return 1;
    }
}
