package io.sr26.creeperconfetti;

import io.sr26.creeperconfetti.network.ConfettiExplosionPacket;
import io.sr26.creeperconfetti.network.ConfigSyncPacket;
import io.sr26.creeperconfetti.platform.FabricConfig;
import io.sr26.creeperconfetti.platform.Services;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;

public class CreeperConfettiClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ConfettiExplosionPacket.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                if (context.client().level != null) {
                    Entity entity = context.client().level.getEntity(payload.entityId());
                    if (entity instanceof Creeper creeper) {
                        Services.getClientHelper().spawnConfettiParticles(creeper);
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPacket.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                FabricConfig.setSyncedValues(payload.confettiChance(), payload.damagePlayers());
            });
        });
    }
}
