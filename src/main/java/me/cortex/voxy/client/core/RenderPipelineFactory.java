package me.cortex.voxy.client.core;

import me.cortex.voxy.client.core.rendering.hierachical.AsyncNodeManager;
import me.cortex.voxy.client.core.rendering.hierachical.HierarchicalOcclusionTraverser;
import me.cortex.voxy.client.core.rendering.hierachical.NodeCleaner;
import me.cortex.voxy.client.core.util.IrisUtil;

import java.util.function.BooleanSupplier;

// Iris pipeline support disabled for 1.20.2 - API incompatible
public class RenderPipelineFactory {
    public static AbstractRenderPipeline createPipeline(AsyncNodeManager nodeManager, NodeCleaner nodeCleaner, HierarchicalOcclusionTraverser traversal, BooleanSupplier frexSupplier) {
        // Iris pipeline disabled for 1.20.2, always use normal pipeline
        return new NormalRenderPipeline(nodeManager, nodeCleaner, traversal, frexSupplier);
    }
}
