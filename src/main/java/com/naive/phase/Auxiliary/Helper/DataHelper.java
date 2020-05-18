package com.naive.phase.Auxiliary.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.naive.phase.Auxiliary.Instantiable.Data.Eve.Eve;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Range;
import com.naive.phase.Auxiliary.Magic.EVE.CapabilityEVE;
import com.naive.phase.Auxiliary.Magic.EVE.Interface.IEVEStorage;
import com.naive.phase.Auxiliary.Magic.EVE.Interface.IEVEStorageItem;
import com.naive.phase.Item.ItemMatrix.IUpgrade;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import scala.Predef;

public class DataHelper {
    public static <K, V> Map<K, V> pairsToMap(List<Pairs<K, V>> pairs) {
        Map<K, V> result = new HashMap<>();

        for (Pairs<K, V> pair : pairs) {
            result.put(pair.getFirst(), pair.getSecond());
        }

        return result;
    }

    //Blend two EVEs
    public static Pairs<Eve, Eve> blend(Eve first, Eve second) {
        if (first.getPhase() != second.getPhase())
            return new Pairs<>(first, second);

        float blendedHue = MathHelper.weightedAverage(first.getHue(), first.getCapacity(), second.getHue(), second.getCapacity());
        float blendedBright = MathHelper.weightedAverage(first.getBrightness(), first.getCapacity(), second.getBrightness(), second.getCapacity());
        float blendedSaturation = MathHelper.weightedAverage(first.getSaturation(), first.getCapacity(), second.getSaturation(), second.getCapacity());

        return new Pairs<>(new Eve(first.getPhase(), first.getCapacity(), blendedHue, blendedBright, blendedSaturation),
                new Eve(second.getPhase(), second.getCapacity(), blendedHue, blendedBright, blendedSaturation));
    }

    public static Eve partialEve(Eve eve, float partial) {
        Eve par = eve.copy();
        par.setCapacity((int) (par.getCapacity() * partial));
        return par;
    }

    //Blend partials of EVEs
    public static Pairs<Eve, Eve> blendPartial(Eve first, Eve second, float partial) {
        Pairs<Eve, Eve> partialBlend = blend(partialEve(first, partial), partialEve(second, partial));
        return new Pairs<>(
                blend(partialEve(first, 1 - partial), partialBlend.getFirst()).getFirst(),
                blend(partialEve(second, 1 - partial), partialBlend.getSecond()).getFirst()
        );
    }

    public static Eve loadEVEfromNBT(NBTTagCompound tag) {
        Eve eve = new Eve(0, 100, 0.5f, 0.5f, 0.5f);
        return eve.readFromNBT(tag);
    }

    public static Eve getEVEFromStack(ItemStack stack) {
        return getEVEHandlerFromStack(stack).getEVE();
    }

    public static IEVEStorageItem getEVEHandlerFromStack(ItemStack stack) {
        IEVEStorageItem e = stack.getCapability(CapabilityEVE.EVE_ITEM, null);
        if (e != null)
            return e;
        else
            throw new IllegalArgumentException("Stack is not registered as an EVE capable item!");
    }

    public static void blendTwoEVEs(IEVEStorage eve1, IEVEStorage eve2) {
        Eve result = eve1.blendEVE(eve2.getEVE(), false);
        eve2.setEVE(result);
    }

    public static List<ItemStack> getUpgradesMatching(ItemStack matrix, Class clazz) {
        IItemHandler handler = matrix.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        List<ItemStack> stacks = new ArrayList<>();
        if (handler != null) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (clazz.isInstance(stack.getItem())) {
                    stacks.add(stack);
                }
            }

        }
        return stacks;
    }

    public static ItemStack getFirstUpgrade(ItemStack matrix, Class clazz) {
        IItemHandler handler = matrix.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler != null) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (clazz.isInstance(stack.getItem()))
                    return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean hasUpgrade(ItemStack matrix, Class clazz) {
        IItemHandler handler = matrix.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler != null) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (clazz.isInstance(stack.getItem()))
                    return true;
            }
        }
        return false;
    }

    public static int getExtraEnergyCost(ItemStack matrix, World world, EntityLivingBase living, ItemStack tool) {
        IItemHandler handler = matrix.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int cost = 0;
        if (handler != null) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof IUpgrade) {
                    cost += ((IUpgrade) stack.getItem()).getEnergyCost(world, living instanceof EntityPlayer ? (EntityPlayer) living : null, stack, matrix, tool);
                }
            }
        }
        return cost;
    }
}