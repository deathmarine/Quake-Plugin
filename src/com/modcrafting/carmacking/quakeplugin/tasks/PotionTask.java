package com.modcrafting.carmacking.quakeplugin.tasks;

import com.modcrafting.carmacking.quakeplugin.QuakePlugin;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionTask extends Task {
	World world;

	public PotionTask(QuakePlugin quake, World world) {
		super(quake, 60L);
		this.world = world;
	}

	public void run() {
		for (Player player : this.world.getPlayers())
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
					9999, 1));
	}

	public void kill() {
		this.task.cancel();
	}
}