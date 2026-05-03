package io.sr26.creeperconfetti.platform.services;

import net.minecraft.sounds.SoundEvent;
import java.util.function.Supplier;

public interface IRegistryHelper {
    Supplier<SoundEvent> registerSound(String name);
}
