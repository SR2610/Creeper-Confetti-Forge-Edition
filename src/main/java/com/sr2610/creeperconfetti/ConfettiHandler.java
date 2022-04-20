package com.sr2610.creeperconfetti;

import java.io.Console;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ConfettiHandler {

	@SubscribeEvent
	public void creeperExplodeEvent(LivingEvent.LivingUpdateEvent event) {
		Creeper creeper = null;
		if (event.getEntityLiving() instanceof Creeper)
			creeper = (Creeper) event.getEntityLiving();
		else
			return;
		if (creeper != null && creeper.getSwellDir()>0) {
			int ignitedTime = ObfuscationReflectionHelper.getPrivateValue(Creeper.class, creeper,
					"f_32270_");
			int fuseTime = ObfuscationReflectionHelper.getPrivateValue(Creeper.class, creeper, "f_32271_");
			if (ignitedTime >= fuseTime - (creeper.level.isClientSide?2:1)){
				if (willExplodeToConfetti(creeper)) {
					if (ConfigHandler.GENERAL.DamagePlayers.get())
						damagePlayers(creeper);
					Random rand = new Random();
					if (rand.nextInt(100) < 5)
						creeper.level.playLocalSound(creeper.position().x, creeper.position().y, creeper.position().z, ModSounds.confetti, SoundSource.HOSTILE,2F,1F, false);
					creeper.level.playLocalSound(creeper.position().x, creeper.position().y, creeper.position().z, SoundEvents.FIREWORK_ROCKET_TWINKLE, SoundSource.HOSTILE, 1F,1F, false);
					if(creeper.level.isClientSide)
					spawnParticles(creeper);
					if(!creeper.level.isClientSide)
					creeper.remove(Entity.RemovalReason.KILLED); // Removes the creeper from the world, as if it was dead
				} else {
					Method explode = ObfuscationReflectionHelper.findMethod(Creeper.class, "m_32315_");
					try {
						explode.invoke(creeper, (Object[]) null);
					} catch (IllegalAccessException e) { // Should handle this better but oh well
					} catch (IllegalArgumentException | InvocationTargetException ignored) {
					}
				}
			}

		}
	}

	private boolean willExplodeToConfetti(Creeper creeper) {
		Random rand = new Random(creeper.getUUID().getMostSignificantBits() & Long.MAX_VALUE);
		int randomNum = rand.nextInt(100);
		//System.out.println(creeper.world.isRemote + " is " + randomNum + "is " + (creeper.getUUID().getMostSignificantBits() & Long.MAX_VALUE));
		return randomNum < ConfigHandler.GENERAL.ConfettiChance.get()
				&& ConfigHandler.GENERAL.ConfettiChance.get() != 0;
	}

	private void damagePlayers(Creeper creeper) {

		if (!creeper.level.isClientSide) {
			Explosion.BlockInteraction explosion$mode = Explosion.BlockInteraction.NONE;
			float f = creeper.isPowered() ? 2.0F : 1.0F;
			Explosion explosion = new Explosion(creeper.level, creeper, null, null, creeper.position().x, creeper.position().y, creeper.position().z, 3 * f, false, explosion$mode);
			explosion.explode();
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void spawnParticles(Creeper creeper) {
		Minecraft.getInstance().particleEngine
				.add(new FireworkParticles.Starter((ClientLevel)creeper.level, creeper.position().x, creeper.position().y + 0.5F,
						creeper.position().z, 0, 0, 0, Minecraft.getInstance().particleEngine, generateTag(creeper, false)));
		if (creeper.isPowered())
			Minecraft.getInstance().particleEngine
					.add(new FireworkParticles.Starter((ClientLevel)creeper.level, creeper.position().x, creeper.position().y + 2.5F,
							creeper.position().z, 0, 0, 0, Minecraft.getInstance().particleEngine, generateTag(creeper, true)));
	}

	private CompoundTag generateTag(Creeper creeper, boolean powered) {
		CompoundTag fireworkTag = new CompoundTag();
		CompoundTag fireworkItemTag = new CompoundTag();
		ListTag nbttaglist = new ListTag();
		List<Integer> list = Lists.<Integer>newArrayList();
		Random rand = new Random();
		list.add(0xE67E22);
		list.add(0x00E0FF);
		list.add(0x0FFF00);
		for (int i = 0; i < rand.nextInt(3) + 3; i++)
			list.add(rand.nextInt(0xffffff + 1));
		int[] colours = new int[list.size()];
		for (int i = 0; i < colours.length; i++)
			colours[i] = list.get(i).intValue();
		fireworkTag.putIntArray("Colors", colours);
		fireworkTag.putBoolean("Flicker", true);
		fireworkTag.putByte("Type", (byte) (powered ? 3 : 4));
		nbttaglist.add((Tag) fireworkTag);
		fireworkItemTag.put("Explosions", nbttaglist);
		return fireworkItemTag;
	}
}
