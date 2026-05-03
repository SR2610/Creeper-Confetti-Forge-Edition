package io.sr26.creeperconfetti;

import com.google.common.collect.Lists;
import io.sr26.creeperconfetti.platform.Services;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;

import java.util.List;
import java.util.Random;

public class ConfettiHandler {

    public static boolean willExplodeToConfetti(Creeper creeper) {
        Random rand = new Random(creeper.getUUID().getMostSignificantBits() & Long.MAX_VALUE);
        int randomNum = rand.nextInt(100);
        return randomNum < Services.CONFIG.getConfettiChance() && Services.CONFIG.getConfettiChance() != 0;
    }

    public static void doConfetti(Creeper creeper) {
        if (!creeper.level().isClientSide()) {
            if (Services.CONFIG.getDamagePlayers()) {
                damagePlayers(creeper);
            }

            RandomSource rand = creeper.level().getRandom();
            if (rand.nextInt(100) < Services.CONFIG.getCheerChance()) {
                creeper.level().playSound(null, creeper.getX(), creeper.getY(), creeper.getZ(), ModSounds.CONFETTI.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
            }
            creeper.level().playSound(null, creeper.getX(), creeper.getY(), creeper.getZ(), SoundEvents.FIREWORK_ROCKET_TWINKLE, SoundSource.HOSTILE, 1.0F, 1.0F);

            Services.NETWORK.sendConfettiExplosion(creeper);
            creeper.remove(Entity.RemovalReason.KILLED);
        } else {
            Services.getClientHelper().spawnConfettiParticles(creeper);
        }
    }

    private static void damagePlayers(Creeper creeper) {
        float f = creeper.isPowered() ? 2.0F : 1.0F;
        creeper.level().explode(creeper, null, null, creeper.getX(), creeper.getY(), creeper.getZ(), 3 * f, false, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
    }

    public static net.minecraft.world.item.component.FireworkExplosion generateExplosion(Creeper creeper, boolean powered) {
        RandomSource rand = creeper.level().getRandom();
        List<int[]> combos = Services.CONFIG.getColorCombos();
        int[] combo = combos.get(rand.nextInt(combos.size()));
        
        return new net.minecraft.world.item.component.FireworkExplosion(
                powered ? net.minecraft.world.item.component.FireworkExplosion.Shape.CREEPER : net.minecraft.world.item.component.FireworkExplosion.Shape.BURST,
                it.unimi.dsi.fastutil.ints.IntArrayList.wrap(combo),
                it.unimi.dsi.fastutil.ints.IntArrayList.of(),
                true,
                false
        );
    }
}
