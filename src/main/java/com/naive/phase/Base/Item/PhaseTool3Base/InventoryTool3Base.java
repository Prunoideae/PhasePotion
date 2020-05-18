package com.naive.phase.Base.Item.PhaseTool3Base;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class InventoryTool3Base implements IItemHandlerModifiable {
    private final IItemHandlerModifiable inv;
    final ItemStack tool;

    public InventoryTool3Base(ItemStack tool) {
        this.inv = (IItemHandlerModifiable) tool.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.tool = tool;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        inv.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return inv.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return inv.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return inv.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return inv.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return inv.getSlotLimit(slot);
    }
}
