package me.cortex.voxy.client.compat;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

// Flashback compatibility disabled for 1.20.2 - mod not available
public class FlashbackCompat {
    public static final boolean FLASHBACK_INSTALLED = false;

    public static Path getReplayStoragePath() {
        return null;
    }
}
