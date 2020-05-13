package com.naive.phase.Item;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;
import com.naive.phase.Auxiliary.Register.AutomaticRegister;
import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Block.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class ItemRegistry {
    public static Map<Class<?>, Item> itemInsts = new HashMap<>();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) throws IllegalAccessException, InstantiationException {
        IForgeRegistry<Item> registry = event.getRegistry();

        List<Pairs<Class<?>, Field>> annotated = AutomaticRegister.scanFor(Registry.ItemInst.class);

        // Registering ItemBlocks
        for (Pairs<Class<?>, Field> item : annotated) {
            Class<?> clazz = item.getFirst();
            Field inst = item.getSecond();

            Block blockInst = (Block) BlockRegistry.blockInsts.get(clazz);
            if (blockInst != null) {
                final ItemBlock itemInst = new ItemBlock((Block) blockInst);
                itemInst.setRegistryName(blockInst.getRegistryName());
                itemInsts.put(clazz, itemInst);
                registry.register(itemInst);
                inst.set(null, itemInst);
            } else {
                final Item itemInst = (Item) clazz.newInstance();
                registry.register(itemInst);
                itemInsts.put(clazz, itemInst);
                inst.set(null, itemInst);
            }


        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        for (Item item : itemInsts.values()) {
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
        }
    }
}