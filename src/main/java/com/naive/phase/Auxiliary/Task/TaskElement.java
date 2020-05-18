package com.naive.phase.Auxiliary.Task;

import com.naive.phase.Auxiliary.Instantiable.Data.OredictItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class TaskElement {
    private FluidStack fluid;
    private ItemStack itemstack;
    private OredictItemStack oreitemstack;
    protected List<TaskElement> subElements;
    private boolean consumed = true;
    public Type elementType;


    public TaskElement(Object element) {
        if (element instanceof String) {
            this.elementType = Type.OREDICT;
            this.oreitemstack = new OredictItemStack((String) element, 1);
        } else if (element instanceof OredictItemStack) {
            this.elementType = Type.OREDICT;
            this.oreitemstack = (OredictItemStack) element;
        } else if (element instanceof ItemStack) {
            this.elementType = Type.ITEMSTACK;
            this.itemstack = (ItemStack) element;
        } else if (element instanceof FluidStack) {
            this.elementType = Type.FLUIDSTACK;
            this.fluid = (FluidStack) element;
        } else if (element instanceof List) {
            this.elementType = Type.COMPOSITE;
            this.subElements = new ArrayList<>();
            for (Object o : (List) element) {
                if (!(o instanceof TaskElement)) {
                    subElements.add(new TaskElement(o));
                } else subElements.add((TaskElement) o);
            }
        } else throw new UnsupportedOperationException("Unable to cast TaskElement!");
    }

    public Object unwrap() {
        switch (elementType) {
            case ITEMSTACK:
                return itemstack;
            case OREDICT:
                return oreitemstack;
            case COMPOSITE:
                return subElements;
            case FLUIDSTACK:
                return fluid;
            default:
                return null;
        }
    }

    public boolean match(Object stack) {
        switch (elementType) {
            case OREDICT:
                return stack instanceof ItemStack && oreitemstack.matches((ItemStack) stack);
            case ITEMSTACK:
                return stack instanceof ItemStack && ItemStack.areItemStacksEqual(this.itemstack, (ItemStack) stack) && ((ItemStack) stack).getCount() >= this.itemstack.getCount();
            case FLUIDSTACK:
                return stack instanceof FluidStack && FluidStack.areFluidStackTagsEqual((FluidStack) stack, fluid) && ((FluidStack) stack).amount >= this.fluid.amount;
            case COMPOSITE:
                for (TaskElement element : subElements) {
                    if (element.match(stack))
                        return true;
                }
                return false;
            default:
                return false;
        }
    }

    public FluidStack getFluid() {
        if (this.elementType != Type.FLUIDSTACK)
            throw new UnsupportedOperationException(String.format("Wrong cast of element type %s to Fluid!", elementType.toString()));
        return fluid.copy();
    }

    public ItemStack getItemstack() {
        if (this.elementType != Type.ITEMSTACK)
            throw new UnsupportedOperationException(String.format("Wrong cast of element type %s to ItemStack!", elementType.toString()));
        return itemstack.copy();
    }

    public List<TaskElement> getSubElements() {
        if (this.elementType != Type.COMPOSITE)
            throw new UnsupportedOperationException(String.format("Wrong cast of element type %s to Composite!", elementType.toString()));
        return subElements;
    }

    public OredictItemStack getOreitemstack() {
        if (this.elementType != Type.OREDICT)
            throw new UnsupportedOperationException(String.format("Wrong cast of element type %s to OreStack!", elementType.toString()));
        return oreitemstack;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    public static enum Type {
        ITEMSTACK, OREDICT, FLUIDSTACK, COMPOSITE
    }
}
