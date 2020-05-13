package com.naive.phase.Proxy;

import com.naive.phase.Render.ItemColorManager;
import com.naive.phase.Render.Mistuned.MistuneRenderManager;
import com.naive.phase.Render.ShaderLib;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ShaderLib.init();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ItemColorManager.register();

    }
}