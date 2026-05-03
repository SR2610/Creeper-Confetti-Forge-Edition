package io.sr26.creeperconfetti.platform.services;

import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper {
    /**
     * Sends the confetti explosion packet to all players tracking the creeper.
     */
    void sendConfettiExplosion(Creeper creeper);

    /**
     * Sends the configuration sync packet to a specific player.
     */
    void sendConfigSync(ServerPlayer player);
}
