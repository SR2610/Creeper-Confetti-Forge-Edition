package io.sr26.creeperconfetti.platform;

import io.sr26.creeperconfetti.network.ConfettiExplosionPacket;
import io.sr26.creeperconfetti.network.ConfigSyncPacket;
import io.sr26.creeperconfetti.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Creeper;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkHelper implements INetworkHelper {
    @Override
    public void sendConfettiExplosion(Creeper creeper) {
        PacketDistributor.sendToPlayersTrackingEntity(creeper, new ConfettiExplosionPacket(creeper.getId()));
    }

    @Override
    public void sendConfigSync(ServerPlayer player) {
        // NeoForge natively syncs the ModConfig.Type.COMMON automatically.
        // We do not need to send our own config sync packet.
    }
}
