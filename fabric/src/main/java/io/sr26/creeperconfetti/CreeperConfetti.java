package io.sr26.creeperconfetti;

import io.sr26.creeperconfetti.network.ConfettiExplosionPacket;
import io.sr26.creeperconfetti.network.ConfigSyncPacket;
import io.sr26.creeperconfetti.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class CreeperConfetti implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonClass.init();
        
        PayloadTypeRegistry.clientboundPlay().register(ConfettiExplosionPacket.TYPE, ConfettiExplosionPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ConfigSyncPacket.TYPE, ConfigSyncPacket.STREAM_CODEC);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Services.NETWORK.sendConfigSync(handler.player);
        });
    }
}
