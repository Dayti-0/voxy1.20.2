package me.cortex.voxy.client.mixin.iris;

import me.cortex.voxy.client.core.IGetVoxyRenderSystem;
import me.cortex.voxy.client.core.util.IrisUtil;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkRenderMatrices;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11C.glViewport;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "renderLevel", at = @At("HEAD"), order = 100)
    private void voxy$injectIrisCompat(
            com.mojang.blaze3d.vertex.PoseStack poseStack,
            float partialTick,
            long finishNanoTime,
            boolean renderBlockOutline,
            Camera camera,
            net.minecraft.client.renderer.GameRenderer gameRenderer,
            net.minecraft.client.renderer.LightTexture lightTexture,
            Matrix4f projectionMatrix,
            CallbackInfo ci) {
        if (IrisUtil.irisShaderPackEnabled()) {
            var renderer = ((IGetVoxyRenderSystem) this).getVoxyRenderSystem();
            if (renderer != null) {
                //Fix the fucking viewport dims, fuck iris
                glViewport(0,0,Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height);

                var pos = camera.getPosition();
                Matrix4f modelView = new Matrix4f(poseStack.last().pose());
                IrisUtil.CAPTURED_VIEWPORT_PARAMETERS = new IrisUtil.CapturedViewportParameters(new ChunkRenderMatrices(projectionMatrix, modelView), null, pos.x, pos.y, pos.z);
            }
        }
    }
}
