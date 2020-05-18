package com.naive.phase.Block.BlockCase;

import com.naive.phase.Auxiliary.Instantiable.Data.Math.Range;
import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Tile.TileBase;
import com.naive.phase.Item.ItemRFBattery.ItemRFBattery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

@Registry.Tile("case_basic")
public class TileCase extends TileBase {
    private CaseStorage inv;

    public TileCase() {
        inv = new CaseStorage(1);
    }

    public TileCase(int size) {
        inv = new CaseStorage(size);
    }


    @Override
    public void writeSyncNBT(NBTTagCompound compound) {

    }

    @Override
    public void readSyncNBT(NBTTagCompound compound) {

    }

    private class CaseStorage extends ItemStackHandler {
        private int size;

        public CaseStorage(int size) {
            super(size * 2 + 2); // input-n core-1 energy-1 output-n
            this.size = size;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (getInputSlots().in(slot)) {

            } else if (getCoreSlot() == slot) {
                if (!(stack.getItem() instanceof ICoreComponent)) {
                    return stack;
                }
            } else if (getEnergySlot() == slot) {
                if (!(stack.getItem() instanceof ItemRFBattery))
                    return stack;
            } else if (getOutputSlots().in(slot)) {

            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }

        public Range getInputSlots() {
            return new Range(0, this.size - 1);
        }

        public int getCoreSlot() {
            return this.size;
        }

        public int getEnergySlot() {
            return this.size + 1;
        }

        public Range getOutputSlots() {
            return new Range(this.size + 2, this.size * 2 + 1);
        }
    }
}
