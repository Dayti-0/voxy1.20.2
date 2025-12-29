package me.cortex.voxy.client.mixin.sodium;

// NOTE: This mixin is disabled for Sodium 0.5.x (1.20.2) as the config structure is completely different
// The Page/OptionPage classes don't exist in Sodium 0.5.x
// TODO: Rewrite for Sodium 0.5.x API if needed

/*
import me.cortex.voxy.client.config.IConfigPageSetter;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Sodium 0.5.x uses SodiumOptionsGUI instead of VideoSettingsScreen
@Mixin(value = SodiumOptionsGUI.class, remap = false)
public abstract class MixinVideoSettingsScreen implements IConfigPageSetter {
    @Unique
    private Object voxyJumpPage;

    public void voxy$setPageJump(Object page) {
        this.voxyJumpPage = page;
    }
}
*/

// Placeholder class to prevent compilation errors - mixin is disabled
public class MixinVideoSettingsScreen {
}
