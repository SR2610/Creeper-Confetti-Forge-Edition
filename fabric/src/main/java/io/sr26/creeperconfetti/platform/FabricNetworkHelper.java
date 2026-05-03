package io.sr26.creeperconfetti.platform;

import io.sr26.creeperconfetti.network.ConfettiExplosionPacket;
import io.sr26.creeperconfetti.network.ConfigSyncPacket;
import io.sr26.creeperconfetti.platform.services.INetworkHelper;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Creeper;

public class FabricNetworkHelper implements INetworkHelper {
    @Override
    public void sendConfettiExplosion(Creeper creeper) {
        ConfettiExplosionPacket packet = new ConfettiExplosionPacket(creeper.getId());
        for (ServerPlayer trackingPlayer : PlayerLookup.tracking(creeper)) {
            ServerPlayNetworking.send(trackingPlayer, packet);
        }
    }

    @Override
    public void sendConfigSync(ServerPlayer player) {
        ServerPlayNetworking.send(player, new ConfigSyncPacket(
                Services.CONFIG.getConfettiChance(),
                Services.CONFIG.getDamagePlayers()
        ));
    }
}
