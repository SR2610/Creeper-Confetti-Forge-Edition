package io.sr26.creeperconfetti.mixin;

import io.sr26.creeperconfetti.ConfettiHandler;
import io.sr26.creeperconfetti.mixin.CreeperAccessor;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class CreeperMixin {

    @Inject(method = "explodeCreeper", at = @At("HEAD"), cancellable = true)
    private void onExplode(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        if (!creeper.level().isClientSide() && ConfettiHandler.willExplodeToConfetti(creeper)) {
            ConfettiHandler.doConfetti(creeper);
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        if (creeper.level().isClientSide() && creeper.isAlive() && creeper.getSwellDir() > 0) {
            CreeperAccessor accessor = (CreeperAccessor) creeper;
            int swell = accessor.getSwell();
            int maxSwell = accessor.getMaxSwell();
            if (swell >= maxSwell - 1) {
                if (ConfettiHandler.willExplodeToConfetti(creeper)) {
                    ConfettiHandler.doConfetti(creeper);
                }
            }
        }
    }
}
