package io.sr26.creeperconfetti;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;


//@Mod.EventBusSubscriber(modid = "creeperconfetti", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {


    public static final DeferredRegister<SoundEvent> SOUND_REGISTER = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CreeperConfetti.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> CONFETTI_CHEER = SOUND_REGISTER.register("confetti",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreeperConfetti.MOD_ID, "confetti")));



    //public static SoundEvent confetti;
	/*@SubscribeEvent
	public static void registerSound(RegisterEvent e) {
		final ResourceLocation loc = new ResourceLocation("creeperconfetti","confetti");
		confetti = SoundEvent.createVariableRangeEvent(loc);
		e.register(NeoForgeRegistries.Keys.Keys.SOUND_EVENTS, registry -> {
			registry.register("confetti", confetti);
		});
	}*/



	/*@SubscribeEvent
	public void register(RegisterEvent event) {

		final ResourceLocation loc = new ResourceLocation("creeperconfetti","confetti");
		confetti = SoundEvent.createVariableRangeEvent(loc);
		event.register(
				// This is the registry key of the registry.
				// Get these from BuiltInRegistries for vanilla registries,
				// or from NeoForgeRegistries.Keys for NeoForge registries.
				Registries.SOUND_EVENT,
				// Register your objects here.
				registry -> {
					registry.register(loc,confetti);
				}
		);
	}*/
}