package com.naive.phase.Block.BlockTransmitter.TileTransmitter;

import com.naive.phase.Auxiliary.Helper.NBTHelper;
import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Auxiliary.Tech.FENetworkManager;
import com.naive.phase.Auxiliary.Tech.IEnergyTransmitter;
import com.naive.phase.Base.Tile.TileBase;
import com.naive.phase.Block.BlockTransmitter.BlockTransmitter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.UUID;

@Registry.Tile("rf_transmitter")
public class TileFETransmitter extends TileBase implements IEnergyTransmitter, ITickable {
    private static final String TAG_UID = "uuid";
    private String uuid = UUID.randomUUID().toString();

    @Override
    public void writeSyncNBT(NBTTagCompound compound) {
        compound.setString(TAG_UID, uuid);
    }

    @Override
    public void readSyncNBT(NBTTagCompound compound) {
        uuid = NBTHelper.getOrDefault(compound, TAG_UID, UUID.randomUUID().toString());
    }

    @Override
    public IEnergyStorage getStorage() {
        EnumFacing facing = world.getBlockState(pos).getValue(BlockTransmitter.FACING);
        TileEntity teAttached = world.getTileEntity(pos.offset(facing.getOpposite()));
        return teAttached != null ? teAttached.getCapability(CapabilityEnergy.ENERGY, facing) : null;
    }

    @Override
    public boolean isSender() {
        int sender = world.getBlockState(pos).getValue(BlockTransmitter.SENDER);
        return sender == 0;
    }

    @Override
    public void update() {
        if (!this.isSender()) {
            IEnergyStorage storage = this.getStorage();
            if (storage != null && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                int toInsert = storage.receiveEnergy(storage.getMaxEnergyStored() - storage.getEnergyStored(), true);
                storage.receiveEnergy(FENetworkManager.instance.extractEnergy(this.uuid, toInsert, false), false);
            }
        }
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void validate() {
        FENetworkManager.instance.register(this.uuid, this);
        super.validate();
    }

    @Override
    public void invalidate() {
        FENetworkManager.instance.remove(uuid, this);
        super.invalidate();
    }
}
