package com.naive.phase.Base.Item.PhaseTool3Base;

import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;
import com.naive.phase.Base.Item.PhaseToolBase;
import com.naive.phase.GUI.GUILib;
import com.naive.phase.Item.ItemMatrix.ItemMatrix;
import com.naive.phase.Item.ItemRFBattery.ItemRFBattery;
import com.naive.phase.Phase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * A public class for all items that have a same three modifier slots.
 * Have three slots, first slot represent the basis of the tool,
 * the second is battery, and the third is the Matrix.
 */

public class PhaseTool3Base extends PhaseToolBase {

    private static final String TAG_ITEMS = "InvItems";
    private final Class toolHead;

    public PhaseTool3Base(String registryName, Class toolHead) {
        super(registryName);
        this.toolHead = toolHead;
    }

    @Override
    public ItemStack getMatrix(ItemStack stack) {
        IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler != null) {
            return handler.getStackInSlot(1);
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getItemIn(ItemStack stack, ItemType type) {
        IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler != null) {
            return handler.getStackInSlot(type.ordinal());
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ToolInventory(toolHead);
    }

    @Override
    public IItemHandler getGenXInventory(ItemStack stack) {
        return null;
    }

    private static class ToolInventory implements ICapabilitySerializable<NBTBase> {
        private final Class acceptableToolHead;
        private final IItemHandler inv = new ItemStackHandler(4) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                switch (slot) {
                    case 0:
                        if (!acceptableToolHead.isInstance(stack.getItem()))
                            return stack;
                        else break;
                    case 1:
                        if (!(stack.getItem() instanceof ItemMatrix))
                            return stack;
                        else break;
                    case 2:
                        if (!(stack.getItem() instanceof ItemRFBattery))
                            return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };

        private ToolInventory(Class acceptableToolHead) {
            this.acceptableToolHead = acceptableToolHead;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv) : null;
        }

        @Override
        public NBTBase serializeNBT() {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inv, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inv, null, nbt);
        }
    }

    public enum ItemType {
        TOOL_HEAD, MATRIX, BATTERY
    }

    public ActionResult<ItemStack> onRightClick(World world, EntityPlayer player, EnumHand hand) {
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }


    public ActionResult<ItemStack> onShiftRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.openGui(Phase.inst, GUILib.GUI_TOOL, world, hand == EnumHand.OFF_HAND ? 1 : 0, 0, 0);
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public final ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!playerIn.isSneaking()) {
            return onRightClick(worldIn, playerIn, handIn);
        } else {
            return onShiftRightClick(worldIn, playerIn, handIn);
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (!Minecraft.getMinecraft().player.isSneaking()) {
            ItemStack toolHead = getItemIn(stack, ItemType.TOOL_HEAD);
            if (!toolHead.isEmpty())
                return toolHead.getItem().getDurabilityForDisplay(toolHead);
        } else {
            IEnergyStorage storage = getItemIn(stack, ItemType.BATTERY).getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null)
                return (storage.getMaxEnergyStored() - storage.getEnergyStored()) / (double) (storage.getMaxEnergyStored());
        }
        return 0.0;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return !Minecraft.getMinecraft().player.isSneaking() ? super.getRGBDurabilityForDisplay(stack) : Color.HSBtoRGB(0, 1, 1);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getDurabilityForDisplay(stack) != 0.0;
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        NBTTagCompound tag = super.getNBTShareTag(stack);
        if (tag == null)
            tag = new NBTTagCompound();

        IItemHandler iItemHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (iItemHandler != null)
            tag.setTag(TAG_ITEMS, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, iItemHandler, null));

        return tag;
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        super.readNBTShareTag(stack, nbt);
        if (nbt == null)
            return;
        NBTBase tag = nbt.getTag(TAG_ITEMS);
        IItemHandler iItemHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, iItemHandler, null, tag);
    }
}
