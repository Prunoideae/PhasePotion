package com.naive.phase.Recipes;

import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;
import com.naive.phase.Auxiliary.Task.EnergyProgressTask;
import com.naive.phase.Auxiliary.Task.TaskElement;
import com.naive.phase.Auxiliary.Task.TaskPredicate;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CrusherRecipes {
    private static final HashMap<String, Pairs<TaskPredicate, EnergyProgressTask>> registry = new HashMap<>();

    public static void register(Object[] input, Object[] output, int energy) {
        register(new TaskPredicate(new ArrayList<Object>() {{
            this.addAll(Arrays.asList(input));
        }}), new TaskElement(new ArrayList<Object>() {{
            this.addAll(Arrays.asList(output));
        }}), energy);
    }

    public static void register(TaskPredicate input, TaskElement output, int totalEnergy) {
        register("crusher_recipe_" + registry.size(), input, output, totalEnergy);
    }

    public static void register(String index, TaskPredicate input, TaskElement output, int totalEnergy) {
        registry.put(index, new Pairs<>(input, new EnergyProgressTask(totalEnergy, output)));
    }

    public static void init() {
        register(new Object[]{new ItemStack(Items.GUNPOWDER, 1), new FluidStack(FluidRegistry.LAVA, 1000)}, new Object[]{new ItemStack(Items.REDSTONE)}, 100);
    }
}
