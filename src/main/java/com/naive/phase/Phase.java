package com.naive.phase;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.naive.phase.Registry.PotionRegistry;

import org.apache.logging.log4j.Logger;

@Mod(modid = Phase.MODID, name = Phase.NAME, version = Phase.VERSION)
public class Phase {
    public static final String MODID = "phase";
    public static final String NAME = "Phase Potion";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PotionRegistry.init();
    }

    public static String getPrefixed(String str) {
        return MODID + ":" + str;
    }
}
