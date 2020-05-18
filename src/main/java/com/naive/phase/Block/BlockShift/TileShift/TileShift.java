package com.naive.phase.Block.BlockShift.TileShift;

import com.naive.phase.Auxiliary.Helper.NBTHelper;
import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Tile.TileBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

@Registry.Tile("shift")
public class TileShift extends TileBase {
    private EnumFacing input = EnumFacing.DOWN;

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing == null)
            return false;

        TileEntity other = null;
        if (facing != this.input)
            other = world.getTileEntity(pos.offset(input));
        else {
            EnumFacing off = EnumFacing.getFront((int) (world.getWorldTime() % 6));
            if (off != this.input)
                other = world.getTileEntity(pos.offset(off));
        }


        if (other == null || other instanceof TileShift)
            return false;

        return other.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing == null)
            return null;

        TileEntity other = null;
        if (facing != this.input)
            other = world.getTileEntity(pos.offset(input));
        else {
            EnumFacing off = EnumFacing.getFront((int) (world.getWorldTime() % 6));
            if (off != this.input)
                other = world.getTileEntity(pos.offset(off));
        }


        if (other == null || other instanceof TileShift)
            return null;

        return other.getCapability(capability, facing);
    }


    @Override
    public void writeSyncNBT(NBTTagCompound compound) {
        compound.setInteger("input", input != null ? input.getIndex() : 1);
    }

    @Override
    public void readSyncNBT(NBTTagCompound compound) {
        this.input = EnumFacing.getFront(NBTHelper.getOrDefault(compound, "input", 0));
    }

    public void setFacing(EnumFacing facing) {
        this.input = facing;
    }
}
