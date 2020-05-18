package com.naive.phase.GUI;

import com.naive.phase.Base.Item.PhaseTool3Base.ContainerTool3Base;
import com.naive.phase.Base.Item.PhaseTool3Base.GuiTool3Base;
import com.naive.phase.Base.Item.PhaseTool3Base.InventoryTool3Base;
import com.naive.phase.Base.Item.PhaseTool3Base.PhaseTool3Base;
import com.naive.phase.Item.ItemColliculus.ContainerColliculus;
import com.naive.phase.Item.ItemColliculus.GuiColliculus;
import com.naive.phase.Item.ItemColliculus.InventoryColliculus;
import com.naive.phase.Item.ItemColliculus.ItemColliculus;
import com.naive.phase.Item.ItemColorPicker.GuiColorPicker;
import com.naive.phase.Item.ItemColorPicker.ItemColorPicker;
import com.naive.phase.Item.ItemMatrix.ContainerMatrix;
import com.naive.phase.Item.ItemMatrix.GuiMatrix;
import com.naive.phase.Item.ItemMatrix.InventoryMatrix;
import com.naive.phase.Item.ItemMatrix.ItemMatrix;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        EnumHand hand = x == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        ItemStack stack = player.getHeldItem(hand);

        switch (ID) {
            case GUILib.GUI_COLLICULUS:
                if (stack.getItem() == ItemColliculus.itemInst)
                    return new ContainerColliculus(player, new InventoryColliculus(stack));
            case GUILib.GUI_MATRIX:
                if (stack.getItem() instanceof ItemMatrix)
                    return new ContainerMatrix(player, new InventoryMatrix(stack));
            case GUILib.GUI_TOOL:
                if (stack.getItem() instanceof PhaseTool3Base)
                    return new ContainerTool3Base(player, new InventoryTool3Base(stack));
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        EnumHand hand = x == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        ItemStack stack = player.getHeldItem(hand);

        switch (ID) {
            case GUILib.GUI_COLLICULUS:
                if (stack.getItem() == ItemColliculus.itemInst)
                    return new GuiColliculus(player, new InventoryColliculus(stack));
            case GUILib.GUI_MATRIX:
                if (stack.getItem() == ItemMatrix.itemInst)
                    return new GuiMatrix(player, new InventoryMatrix(stack));
            case GUILib.GUI_PICKER:
                if (stack.getItem() == ItemColorPicker.itemInst)
                    return new GuiColorPicker(stack);
            case GUILib.GUI_TOOL:
                if (stack.getItem() instanceof PhaseTool3Base)
                    return new GuiTool3Base(player, new InventoryTool3Base(stack));
        }

        return null;
    }
}
