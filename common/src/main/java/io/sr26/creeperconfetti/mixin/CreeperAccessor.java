package io.sr26.creeperconfetti.mixin;

import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Creeper.class)
public interface CreeperAccessor {
    @Accessor("swell")
    int getSwell();

    @Accessor("maxSwell")
    int getMaxSwell();
}
