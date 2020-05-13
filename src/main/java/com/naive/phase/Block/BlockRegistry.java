package com.naive.phase.Block;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.naive.phase.Phase;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;
import com.naive.phase.Auxiliary.Register.AutomaticRegister;
import com.naive.phase.Auxiliary.Register.Registry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class BlockRegistry {
    public static Map<Class<?>, Block> blockInsts = new HashMap<>();

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        // Registering Blocks
        List<Pairs<Class<?>, Field>> annotated = AutomaticRegister.scanFor(Registry.BlockInst.class);
        for (Pairs<Class<?>, Field> block : annotated) {
            Class<?> clazz = block.getFirst();
            Field inst = block.getSecond();
            try {
                Block blockInst = (Block) clazz.newInstance();
                registry.register(blockInst);
                inst.set(null, blockInst);
                blockInsts.put(clazz, blockInst);

            } catch (Exception e) {
                Phase.logger.error("Unable to register block {} into {} !", clazz.getName(), inst.getName());
                e.printStackTrace();
            }
        }

        Set<ASMData> annotatedTiles = AutomaticRegister.scanRaw(Registry.Tile.class);
        for (ASMData pair : annotatedTiles) {
            try {
                Class<? extends TileEntity> clazz = (Class<? extends TileEntity>) Class.forName(pair.getClassName());
                String registryName = (String) pair.getAnnotationInfo().get("value");

                GameRegistry.registerTileEntity(clazz, new ResourceLocation(Phase.MODID, registryName));
            } catch (Exception e) {
                Phase.logger.error("Unable to register tile {} !", pair.getClassName());
            }

        }
    }
}