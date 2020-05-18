package com.naive.phase.Base.Item.PhaseTool3Base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTool3Base extends GuiContainer {
    private static final ResourceLocation TOOL_GUI_TEXTURE = new ResourceLocation("phase:textures/gui/tool3.png");

    public GuiTool3Base(EntityPlayer player, InventoryTool3Base tool) {
        super(new ContainerTool3Base(player, tool));
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
        mc.getTextureManager().bindTexture(TOOL_GUI_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
