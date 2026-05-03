package io.sr26.creeperconfetti;


import io.sr26.creeperconfetti.platform.NeoForgeConfig;
import io.sr26.creeperconfetti.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class CreeperConfetti {

    public CreeperConfetti(IEventBus eventBus) {
        NeoForgeRegistryHelper.SOUNDS.register(eventBus);
        net.neoforged.fml.ModLoadingContext.get().getActiveContainer().registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, NeoForgeConfig.SPEC);
        CommonClass.init();
    }
}