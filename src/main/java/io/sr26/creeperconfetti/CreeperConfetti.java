package io.sr26.creeperconfetti;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod("creeperconfetti")
public class CreeperConfetti
{
	public CreeperConfetti() {
	ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, io.sr26.creeperconfetti.ConfigHandler.spec);
		MinecraftForge.EVENT_BUS.register(new ConfettiHandler());
	}

}