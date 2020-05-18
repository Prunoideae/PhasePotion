package com.naive.phase.Item.ItemColliculus;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiColliculus extends GuiContainer {

    private static final ResourceLocation COLLICULUS_GUI_TEXTURE = new ResourceLocation("phase:textures/gui/colliculus.png");

    public GuiColliculus(EntityPlayer player, InventoryColliculus colliculus) {
        super(new ContainerColliculus(player, colliculus));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(COLLICULUS_GUI_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
