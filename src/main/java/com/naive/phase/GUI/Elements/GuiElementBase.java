package com.naive.phase.GUI.Elements;

import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Range;
import com.naive.phase.GUI.Interfaces.IGUICallback;
import com.naive.phase.GUI.Interfaces.IGUIElement;
import net.minecraft.client.gui.Gui;

public class GuiElementBase extends Gui implements IGUIElement {
    private final int id;
    public int x, y;
    protected final int width, height;
    protected IGUICallback callback;

    public GuiElementBase(int id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    @Override
    public void setGUI(IGUICallback back) {

    }

    @Override
    public void render(int x, int y, float partialTicks) {

    }

    @Override
    public void renderForeground(int x, int y, float partialTicks) {

    }

    @Override
    public Pairs<Range, Range> getClickAwareRange() {
        return new Pairs<>(new Range(x, x + width), new Range(y, y + height));
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {

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
        return id;
    }

    public void drawRepeatedTexturedModalRect(int x, int y, int width, int height, int texX, int texY, int texWidth, int texHeight) {
        for (int offX = 0; offX < width; offX += texWidth) {
            for (int offY = 0; offY < height; offY += texHeight) {
                drawTexturedModalRect(x + offX, y + offY, texX, texY, texWidth, texHeight);
            }
        }
        if (width % texWidth != 0 || height % texHeight != 0) {
            drawTexturedModalRect(x + width - (width % texWidth), y, texX, texY, width % texWidth, texHeight);
            drawTexturedModalRect(x, y + height - (height % texHeight), texX, texY, texWidth, height % texHeight);
            drawTexturedModalRect(x + width - (width % texWidth), y + height - (height % texHeight), texX, texY, width % texWidth, height % texHeight);
        }
    }
}
