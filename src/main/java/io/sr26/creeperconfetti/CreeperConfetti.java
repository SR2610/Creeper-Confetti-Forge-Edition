package io.sr26.creeperconfetti;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CreeperConfetti.MOD_ID)
public class CreeperConfetti
{

	public static final String MOD_ID = "creeperconfetti";

	public CreeperConfetti(IEventBus eventBus) {
	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, io.sr26.creeperconfetti.ConfigHandler.spec);
		NeoForge.EVENT_BUS.register(new ConfettiHandler());

		ModSounds.SOUND_REGISTER.register(eventBus);

	}

}