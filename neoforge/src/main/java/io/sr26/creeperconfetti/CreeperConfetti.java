package io.sr26.creeperconfetti;

import io.sr26.creeperconfetti.network.ConfettiExplosionPacket;
import io.sr26.creeperconfetti.network.ConfigSyncPacket;
import io.sr26.creeperconfetti.platform.NeoForgeConfig;
import io.sr26.creeperconfetti.platform.NeoForgeRegistryHelper;
import io.sr26.creeperconfetti.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Constants.MOD_ID)
public class CreeperConfetti {

    public CreeperConfetti(IEventBus eventBus) {
        NeoForgeRegistryHelper.SOUNDS.register(eventBus);
        net.neoforged.fml.ModLoadingContext.get().getActiveContainer().registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, NeoForgeConfig.SPEC);
        CommonClass.init();
        
        eventBus.addListener(this::onRegisterPayloads);
    }

    private void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Constants.MOD_ID);
        registrar.playToClient(ConfettiExplosionPacket.TYPE, ConfettiExplosionPacket.STREAM_CODEC, (payload, context) -> {
            context.enqueueWork(() -> {
                Entity entity = context.player().level().getEntity(payload.entityId());
                if (entity instanceof Creeper creeper) {
                    Services.getClientHelper().spawnConfettiParticles(creeper);
                }
            });
        });
        
        registrar.playToClient(ConfigSyncPacket.TYPE, ConfigSyncPacket.STREAM_CODEC, (payload, context) -> {});
    }
}