package io.sr26.creeperconfetti;

import io.sr26.creeperconfetti.platform.Services;
import net.minecraft.sounds.SoundEvent;
import java.util.function.Supplier;

public class ModSounds {
    public static final Supplier<SoundEvent> CONFETTI = Services.REGISTRY.registerSound("confetti");

    public static void init() {
        // Just to trigger class loading
    }
}
