package me.cortex.voxy.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

// Simplified ModMenu integration for Sodium 0.5.x (1.20.2)
// The Sodium config API doesn't exist in 0.5.x, so we can't jump to a specific page

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        // For Sodium 0.5.x, we can't integrate with Sodium's config screen
        // Return null to indicate no custom config screen
        return parent -> null;
    }
}
