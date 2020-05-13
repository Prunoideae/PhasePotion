package com.naive.phase.Proxy;

import com.naive.phase.Auxiliary.Magic.EVE.CapabilityEVE;
import com.naive.phase.Auxiliary.Register.AutomaticRegister;
import com.naive.phase.Network.NetworkHandler;
import com.naive.phase.Phase;
import com.naive.phase.Potion.PotionRegistry;
import com.naive.phase.Render.GuiHandler;
import com.naive.phase.Render.Mistuned.MistuneRenderManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        // Freeze ASM data for latter usage
        AutomaticRegister.init(event.getAsmData());
        CapabilityEVE.register();
    }

    public void init(FMLInitializationEvent event) {
        PotionRegistry.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Phase.inst, new GuiHandler());
        try {
            NetworkHandler.init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}