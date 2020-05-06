package com.naive.phase.Registry;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.naive.phase.Phase;
import com.naive.phase.Potion.PotionPhase;

@EventBusSubscriber
public class PotionRegistry {
    public static final Potion POTION_PHASE = new PotionPhase();

    public static final PotionType POTION_TYPE_PHASE = new PotionType(Phase.MODID + ".phase",
            new PotionEffect(PotionRegistry.POTION_PHASE, 3600)).setRegistryName("phase");

    public static final PotionType POTION_TYPE_PHASE_LONG = new PotionType(Phase.MODID + ".phase",
            new PotionEffect(PotionRegistry.POTION_PHASE, 9600)).setRegistryName("long_phase");

    @SubscribeEvent
    public static void onPotionRegistry(RegistryEvent.Register<Potion> event) {
        IForgeRegistry<Potion> registry = event.getRegistry();

        // Start registering
        registry.register(POTION_PHASE);
    }

    @SubscribeEvent
    public static void onTypeRegistry(RegistryEvent.Register<PotionType> event) {
        IForgeRegistry<PotionType> registry = event.getRegistry();

        // Start registering
        registry.register(POTION_TYPE_PHASE);
        registry.register(POTION_TYPE_PHASE_LONG);
    }

    public static void init() {
        PotionHelper.addMix(POTION_TYPE_PHASE, Items.REDSTONE, POTION_TYPE_PHASE_LONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Items.CHORUS_FRUIT, POTION_TYPE_PHASE);
    }
}