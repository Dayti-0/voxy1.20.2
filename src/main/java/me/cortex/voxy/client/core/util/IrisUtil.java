package me.cortex.voxy.client.core.util;

import me.cortex.voxy.client.core.VoxyRenderSystem;
import me.cortex.voxy.client.core.rendering.Viewport;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkRenderMatrices;
import net.fabricmc.loader.api.FabricLoader;

// Iris shader support disabled for 1.20.2 - API incompatible
public class IrisUtil {

    public record CapturedViewportParameters(ChunkRenderMatrices matrices, Object parameters, double x, double y, double z) {
        public Viewport<?> apply(VoxyRenderSystem vrs) {
            return vrs.setupViewport(this.matrices, this.parameters, this.x, this.y, this.z);
        }
    }

    public static CapturedViewportParameters CAPTURED_VIEWPORT_PARAMETERS;

    public static final boolean IRIS_INSTALLED = FabricLoader.getInstance().isModLoaded("iris");
    public static final boolean SHADER_SUPPORT = false; // Disabled for 1.20.2

    public static boolean irisShadowActive() {
        return false;
    }

    public static void clearIrisSamplers() {
        // No-op - Iris integration disabled
    }

    public static void reload() {
        // No-op - Iris integration disabled
    }

    public static boolean irisShaderPackEnabled() {
        return false;
    }

    public static void disableIrisShaders() {
        // No-op - Iris integration disabled
    }
}
