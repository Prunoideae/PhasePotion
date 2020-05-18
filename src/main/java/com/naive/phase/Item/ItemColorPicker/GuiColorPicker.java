package com.naive.phase.Item.ItemColorPicker;

import com.naive.phase.GUI.Elements.ElementSlidebar;
import com.naive.phase.GUI.GuiBase;
import com.naive.phase.GUI.Interfaces.IGUIElement;
import com.naive.phase.Network.NetworkHandler;
import com.naive.phase.Network.Packets.PacketUpdatePicker;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class GuiColorPicker extends GuiBase {
    private final ItemStack picker;
    private static final ResourceLocation COLORPICKER_GUI_TEXTURE = new ResourceLocation("phase:textures/gui/colorpicker.png");
    private ElementSlidebar[] slidebars = new ElementSlidebar[3];

    public GuiColorPicker(ItemStack picker) {
        this.picker = picker;
        this.width = 86;
        this.height = 35;
        this.xSize = 176;
        this.ySize = 100;
    }

    @Override
    public void initGui() {
        super.initGui();
        Vec3d color = ItemColorPicker.getColor(picker);

        for (int i = 0; i < 3; i++) {
            slidebars[i] = new ElementSlidebar(16 + this.guiX, 18 + i * 16 + this.guiY, 96, 10, 0.0f, 1.0f);
            addElement(slidebars[i]);
        }
        slidebars[0].setValue((float) color.x);
        slidebars[1].setValue((float) color.y);
        slidebars[2].setValue((float) color.z);
    }

    @Override
    protected ResourceLocation getTexture() {
        return COLORPICKER_GUI_TEXTURE;
    }

    @Override
    public void update(IGUIElement element) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        float h = slidebars[0].getValue();
        float s = slidebars[1].getValue();
        float b = slidebars[2].getValue();
        NetworkHandler.sendToServer(new PacketUpdatePicker(h, s, b));
    }
}
