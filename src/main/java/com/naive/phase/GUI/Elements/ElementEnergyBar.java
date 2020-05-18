package com.naive.phase.GUI.Elements;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ElementEnergyBar extends GuiElementBase {
    private int energy;
    private int maxEnergy;
    final Minecraft mc = Minecraft.getMinecraft();

    private static final ResourceLocation ENERGY_ELEMENT_TEXTURE = new ResourceLocation("phase:textures/elements/energy.png");


    public ElementEnergyBar(int x, int y, int width, int height, int maxEnergy) {
        super(-1, x, y, width, height);
        this.maxEnergy = maxEnergy;
    }

    @Override
    public void render(int x, int y, float partialTicks) {
        super.render(x, y, partialTicks);
        int length = (int) ((width - 12) * (energy / (float) maxEnergy));
        mc.getTextureManager().bindTexture(ENERGY_ELEMENT_TEXTURE);
        drawTexturedModalRect(this.x, this.y, 0, 0, 11, 6);
        drawRepeatedTexturedModalRect(this.x + 6, this.y + 1, this.width - 12, 5, 6, 1, 20, 5);
        drawTexturedModalRect(this.x + width - 11, this.y, 80, 0, 11, 6);
        drawRect(this.x + 6, this.y + 2, this.x + 6 + length, this.y + 2, 0xFFBA9D79);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }
}
