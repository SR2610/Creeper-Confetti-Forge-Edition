package io.sr26.creeperconfetti.platform;

import io.sr26.creeperconfetti.ConfettiHandler;
import io.sr26.creeperconfetti.platform.services.IClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.world.entity.monster.Creeper;

import java.util.List;

public class NeoForgeClientHelper implements IClientHelper {
    @Override
    public void spawnConfettiParticles(Creeper creeper) {
        Minecraft.getInstance().particleEngine.add(new FireworkParticles.Starter((ClientLevel) creeper.level(), creeper.getX(), creeper.getY() + 0.5F,
                creeper.getZ(), 0, 0, 0, Minecraft.getInstance().particleEngine, List.of(ConfettiHandler.generateExplosion(creeper, false))));
        if (creeper.isPowered()) {
            Minecraft.getInstance().particleEngine.add(new FireworkParticles.Starter((ClientLevel) creeper.level(), creeper.getX(), creeper.getY() + 2.5F,
                    creeper.getZ(), 0, 0, 0, Minecraft.getInstance().particleEngine, List.of(ConfettiHandler.generateExplosion(creeper, true))));
        }
    }
}
