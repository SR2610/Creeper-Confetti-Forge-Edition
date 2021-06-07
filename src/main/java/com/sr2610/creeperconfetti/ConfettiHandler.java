package com.sr2610.creeperconfetti;

import java.io.Console;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Explosion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ConfettiHandler {
	private Method explode;

	@SubscribeEvent
	public void creeperExplodeEvent(LivingEvent.LivingUpdateEvent event) {

		CreeperEntity creeper = null;
		if (event.getEntityLiving() instanceof CreeperEntity)
			creeper = (CreeperEntity) event.getEntityLiving();
		else
			return;
		if (creeper != null && creeper.getSwellDir()>0) {
			int ignitedTime = ObfuscationReflectionHelper.getPrivateValue(CreeperEntity.class, creeper,
					"field_70833_d");

			int fuseTime = ObfuscationReflectionHelper.getPrivateValue(CreeperEntity.class, creeper, "field_82225_f");
			if (ignitedTime >= fuseTime - 1) {
				if (willExplodeToConfetti(creeper)) {
					if (ConfigHandler.GENERAL.DamagePlayers.get())
						damagePlayers(creeper);
					Random rand = new Random();
					if (rand.nextInt(100) < 5)
						creeper.level.playLocalSound(creeper.position().x, creeper.position().y, creeper.position().z, ModSounds.confetti, SoundCategory.HOSTILE,2F,1F, false);
					creeper.level.playLocalSound(creeper.position().x, creeper.position().y, creeper.position().z,SoundEvents.FIREWORK_ROCKET_TWINKLE, SoundCategory.HOSTILE, 1F,1F, false);
					if(creeper.level.isClientSide)
					spawnParticles(creeper);
					creeper.remove(); // Removes the creeper from the world, as if it was dead
				} else {
					explode = ObfuscationReflectionHelper.findMethod(CreeperEntity.class, "func_146077_cc");
					try {
						explode.invoke(creeper, (Object[]) null);
					} catch (IllegalAccessException e) { // Should handle this better but oh well
					} catch (IllegalArgumentException e) {
					} catch (InvocationTargetException e) {
					}
				}
			}

		}
	}

	private boolean willExplodeToConfetti(CreeperEntity creeper) {
		Random rand = new Random(creeper.getUUID().getMostSignificantBits() & Long.MAX_VALUE);
		int randomNum = rand.nextInt(100);
		//System.out.println(creeper.world.isRemote + " is " + randomNum + "is " + (creeper.getUUID().getMostSignificantBits() & Long.MAX_VALUE));
		if (!(randomNum < ConfigHandler.GENERAL.ConfettiChance.get())
				|| ConfigHandler.GENERAL.ConfettiChance.get() == 0)
			return false;
		else
			return true;
	}

	private void damagePlayers(CreeperEntity creeper) {

		if (!creeper.level.isClientSide) {
			Explosion.Mode explosion$mode = Explosion.Mode.NONE;
			float f = creeper.isPowered() ? 2.0F : 1.0F;
			Explosion explosion = new Explosion(creeper.level, creeper, null, null, creeper.position().x, creeper.position().y, creeper.position().z, 3 * f, false, explosion$mode);
			explosion.explode();
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void spawnParticles(CreeperEntity creeper) {
		Minecraft.getInstance().particleEngine
				.add(new FireworkParticle.Starter((ClientWorld)creeper.level, creeper.position().x, creeper.position().y + 0.5F,
						creeper.position().z, 0, 0, 0, Minecraft.getInstance().particleEngine, generateTag(creeper, false)));
		if (creeper.isPowered())
			Minecraft.getInstance().particleEngine
					.add(new FireworkParticle.Starter((ClientWorld)creeper.level, creeper.position().x, creeper.position().y + 2.5F,
							creeper.position().z, 0, 0, 0, Minecraft.getInstance().particleEngine, generateTag(creeper, true)));
	}

	private CompoundNBT generateTag(CreeperEntity creeper, boolean powered) {
		CompoundNBT fireworkTag = new CompoundNBT();
		CompoundNBT fireworkItemTag = new CompoundNBT();
		ListNBT nbttaglist = new ListNBT();
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
		nbttaglist.add((INBT) fireworkTag);
		fireworkItemTag.put("Explosions", nbttaglist);
		return fireworkItemTag;
	}
}
