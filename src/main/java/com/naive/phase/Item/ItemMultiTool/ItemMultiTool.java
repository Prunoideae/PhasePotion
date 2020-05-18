package com.naive.phase.Item.ItemMultiTool;

import com.naive.phase.Auxiliary.Helper.DataHelper;
import com.naive.phase.Auxiliary.Helper.MathHelper;
import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Item.PhaseTool3Base.PhaseTool3Base;
import com.naive.phase.Item.ItemUpgrades.ItemEnchantmentUpgrade;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.HashMap;

//TODO: Polish and finish the code after the framework is done.
public class ItemMultiTool extends PhaseTool3Base {

    @Registry.ItemInst
    public static ItemMultiTool itemInst;

    public ItemMultiTool() {
        super("multitool", IToolHead.class);
        addPropertyOverride(new ResourceLocation("head"), (stack, worldIn, entityIn) -> {
            ItemStack head = getItemIn(stack, ItemType.TOOL_HEAD);
            if (!head.isEmpty() && head.getItem() instanceof IToolHead) {
                return ((IToolHead) head.getItem()).getOverlayType();
            }
            return 0;
        });
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        ItemStack handler = getItemIn(stack, ItemType.TOOL_HEAD);
        if (!handler.isEmpty() && handler.getItem() instanceof IToolHead) {
            float speedBase = ((IToolHead) handler.getItem()).getHeadDestroySpeed(stack, state);
            IEnergyStorage energyStorage = getItemIn(stack, ItemType.BATTERY).getCapability(CapabilityEnergy.ENERGY, null);
            float percent = 0;
            if (energyStorage != null) {
                percent = energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored();
            }

            //Apply the energy boost
            speedBase *= percent == 0 ? 0 : 0.7 + percent * 1.8;

            //Apply the enchantment boost
            ItemStack enchantmentUpgrade = DataHelper.getFirstUpgrade(getMatrix(stack), ItemEnchantmentUpgrade.class);
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, enchantmentUpgrade);
            if (level > 0)
                speedBase *= 1f + level * level / 10f;

            return speedBase;
        } else return 0;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        ItemStack handler = getItemIn(stack, ItemType.TOOL_HEAD);
        if (!handler.isEmpty() && handler.getItem() instanceof IToolHead) {
            return ((IToolHead) handler.getItem()).getHeadHarvestLevel(stack, toolClass, player, blockState);
        } else return 0;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        ItemStack handler = getItemIn(stack, ItemType.TOOL_HEAD);
        if (!handler.isEmpty() && handler.getItem() instanceof IToolHead) {
            return ((IToolHead) handler.getItem()).canHeadHarvestBlock(stack, state);
        } else return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        //Damage item, take energy, and something alike.
        ItemStack head = getItemIn(stack, ItemType.TOOL_HEAD);
        ItemStack matrix = getItemIn(stack, ItemType.MATRIX);
        ItemStack battery = getItemIn(stack, ItemType.BATTERY);
        Item headItem = head.getItem();
        if (headItem instanceof IToolHead) {
            int damage = ((IToolHead) headItem).getDamageOnBlock(state);
            if (damage == 0)
                return false;

            int extra = DataHelper.getExtraEnergyCost(matrix, worldIn, entityLiving, stack);
            IEnergyStorage storage = battery.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null)
                storage.extractEnergy(10 + extra, false);

            ItemStack enchantUpgrade = DataHelper.getFirstUpgrade(matrix, ItemEnchantmentUpgrade.class);
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, enchantUpgrade);
            if (MathHelper.withChance(.5f) && MathHelper.withChance(1f - level * .25f)) {
                head.damageItem(damage, entityLiving);
                matrix.damageItem(1, entityLiving);
            }

        }
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (worldIn.getWorldTime() % 10 == 0) {
            ItemStack ench = DataHelper.getFirstUpgrade(getItemIn(stack, ItemType.MATRIX), ItemEnchantmentUpgrade.class);
            if (!ench.isEmpty()) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(ench), stack);
            } else {
                EnchantmentHelper.setEnchantments(new HashMap<>(), stack);
            }
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }
}
