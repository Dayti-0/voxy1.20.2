package me.cortex.voxy.client.core.rendering.util;

import static org.lwjgl.opengl.GL33.glBindSampler;
import static org.lwjgl.opengl.GL45.glBindTextureUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;

public class LightMapHelper {
    public static void bind(int lightingIndex) {
        glBindSampler(lightingIndex, 0);
        // In 1.20.2, LightTexture has a lightTexture field of type DynamicTexture
        LightTexture lt = Minecraft.getInstance().gameRenderer.lightTexture();
        glBindTextureUnit(lightingIndex, lt.lightTexture.getId());
    }
}
