package io.sr26.creeperconfetti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
//import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "creeperconfetti", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {
	
    /*public static SoundEvent confetti;
	@SubscribeEvent
	public static void registerSound(RegistryEvent.Register<SoundEvent> e) {
		final ResourceLocation loc = new ResourceLocation("creeperconfetti","confetti");
		confetti = new SoundEvent(loc).setRegistryName(loc);
		e.getRegistry().register(confetti);
	}*/
}