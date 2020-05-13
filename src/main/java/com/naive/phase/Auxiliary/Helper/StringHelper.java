package com.naive.phase.Auxiliary.Helper;

import com.naive.phase.Phase;
import net.minecraft.util.ResourceLocation;

public class StringHelper {
    public static String getPrefixed(String str) {
        return Phase.MODID + ":" + str;
    }

    public static ResourceLocation getResourceLocation(String str) {
        return new ResourceLocation(Phase.MODID, str);
    }

    public static String getShader(String str) {
        if (str == null)
            return null;
        return "/assets/phase/shaders/" + str;
    }
}