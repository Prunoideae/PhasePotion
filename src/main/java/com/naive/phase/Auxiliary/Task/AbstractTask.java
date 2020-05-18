package com.naive.phase.Auxiliary.Task;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTask {
    protected final TaskElement out;

    protected AbstractTask(TaskElement out) {
        this.out = out.elementType == TaskElement.Type.COMPOSITE ? out : new TaskElement(new ArrayList<TaskElement>() {{
            add(out);
        }});
    }


    public List<ItemStack> getOutputStacks() {
        List<ItemStack> result = new ArrayList<>();
        for (TaskElement output : out.getSubElements()) {
            switch (output.elementType) {
                case OREDICT:
                    result.add(output.getOreitemstack().getSomething());
                case ITEMSTACK:
                    result.add(output.getItemstack());
            }
        }
        return null;
    }

    public List<FluidStack> getOutputFluids() {
        return null;
    }

    public abstract AbstractTask copy();
}
