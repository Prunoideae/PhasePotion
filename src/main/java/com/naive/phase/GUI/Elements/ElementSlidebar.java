package com.naive.phase.GUI.Elements;

import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Range;
import com.naive.phase.GUI.Interfaces.IGUICallback;
import com.naive.phase.GUI.Interfaces.IGUIElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ElementSlidebar extends GuiElementBase implements IGUIElement {
    private final float min;
    private final float max;
    private final Minecraft mc;
    private float percent;
    private IGUICallback callback;

    private static final ResourceLocation SLIDER_ELEMENT_TEXTURE = new ResourceLocation("phase:textures/elements/slider.png");


    public ElementSlidebar(int x, int y, int width, int height, float min, float max) {
        super(-1, x, y, width, height);
        this.min = min;
        this.max = max;
        this.mc = Minecraft.getMinecraft();
    }


    @Override
    public void setGUI(IGUICallback back) {
        this.callback = back;
    }

    @Override
    public void render(int x, int y, float partialTicks) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(SLIDER_ELEMENT_TEXTURE);
        //Background
        drawTexturedModalRect(this.x, this.y, 0, 0, 6, 10);
        drawRepeatedTexturedModalRect(this.x + 6, this.y, this.width - 12, this.height, 6, 0, 2, 10);
        drawTexturedModalRect(this.x + width - 6, this.y, 66, 0, 6, 10);
        //Slider
        drawTexturedModalRect(this.x + (int) ((width - 10) * percent), this.y + 1, 0, 10, 10, 8);
    }

    @Override
    public void renderForeground(int x, int y, float partialTicks) {

    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        int offset = mouseX - x;
        this.percent = Math.min(1, offset / (float) (this.width - 8));
    }

    @Override
    public void onClickedOutside(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public boolean onKeyInput(char key, int keyCode) {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public int getID() {
        return 0;
    }

    public float getValue() {
        return (max - min) * percent + min;
    }

    public void setValue(float value) {
        this.percent = (value - min) / (max - min);
    }
}
