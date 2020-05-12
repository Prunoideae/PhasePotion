package com.naive.phase.Auxiliary.Helper;

import net.minecraft.client.model.ModelRenderer;

public class RenderHelper {
    public static void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
