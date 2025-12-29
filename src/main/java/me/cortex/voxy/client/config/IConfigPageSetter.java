package me.cortex.voxy.client.config;

// NOTE: Sodium config API doesn't exist in Sodium 0.5.x (1.20.2)
// This interface is disabled for 1.20.2

public interface IConfigPageSetter {
    // Disabled for Sodium 0.5.x compatibility
    default void voxy$setPageJump(Object page) {}
}
