package io.sr26.creeperconfetti;

import io.sr26.creeperconfetti.Constants;
import io.sr26.creeperconfetti.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as NeoForge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {

    public static void init() {
        ModSounds.init();
        Constants.LOG.info("Creeper Confetti initialized on {}!", Services.PLATFORM.getPlatformName());
    }
}