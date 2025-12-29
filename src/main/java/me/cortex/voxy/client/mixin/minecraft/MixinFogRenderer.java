package me.cortex.voxy.client.mixin.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.FogShape;
import me.cortex.voxy.client.config.VoxyConfig;
import me.cortex.voxy.client.core.IGetVoxyRenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FogRenderer.class, remap = true)
public class MixinFogRenderer {
    @Shadow private static float fogStart;
    @Shadow private static float fogEnd;

    @Inject(method = "setupFog", at = @At("TAIL"))
    private static void voxy$modifyFog(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean thickFog, float partialTick, CallbackInfo ci) {
        if (!(VoxyConfig.CONFIG.enableRendering && VoxyConfig.CONFIG.enabled)) return;

        var vrs = IGetVoxyRenderSystem.getNullable();
        if (vrs == null) return;

        fogStart = 999999999;
        fogEnd = 999999999;
    }
}
