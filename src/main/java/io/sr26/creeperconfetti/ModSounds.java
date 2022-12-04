package io.sr26.creeperconfetti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
//import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = "creeperconfetti", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {
	
    public static SoundEvent confetti;
	@SubscribeEvent
	public static void registerSound(RegisterEvent e) {
		final ResourceLocation loc = new ResourceLocation("creeperconfetti","confetti");
		confetti = new SoundEvent(loc);
		e.register(ForgeRegistries.Keys.SOUND_EVENTS, registry -> {
			registry.register("confetti", confetti);
		});
	}
}