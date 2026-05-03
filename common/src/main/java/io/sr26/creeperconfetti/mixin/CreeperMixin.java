package io.sr26.creeperconfetti.mixin;

import io.sr26.creeperconfetti.ConfettiHandler;
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
}
