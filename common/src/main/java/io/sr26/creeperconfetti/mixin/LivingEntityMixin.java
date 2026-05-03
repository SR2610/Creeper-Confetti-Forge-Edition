package io.sr26.creeperconfetti.mixin;

import io.sr26.creeperconfetti.ConfettiHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
    private void onEntityEvent(byte id, CallbackInfo ci) {
        if (id == 110 && (Object)this instanceof Creeper creeper) {
            ConfettiHandler.doConfetti(creeper);
            ci.cancel();
        }
    }
}
