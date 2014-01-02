package com.modcrafting.carmacking.quakeplugin.tasks;

import com.modcrafting.carmacking.quakeplugin.QuakePlugin;
import java.util.Random;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworksTask extends Task {
	Random gen = new Random();
	int i = 15;
	String s;

	public FireworksTask(QuakePlugin quake, String p) {
		super(quake, 10L);
		this.s = p;
	}

	public void run() {
		if (this.quake.getServer().getPlayer(this.s) != null) {
			Player player = this.quake.getServer().getPlayer(this.s);
			for (int i = 5; i > 0; i--) {
				Location loc = player.getLocation();
				Firework item = (Firework) loc.getWorld().spawnEntity(loc,
						EntityType.FIREWORK);
				FireworkMeta fwm = item.getFireworkMeta();
				fwm.setPower(this.gen.nextInt(4) + 1);
				fwm.addEffect(FireworkEffect
						.builder()
						.with(org.bukkit.FireworkEffect.Type.values()[this.gen
								.nextInt(org.bukkit.FireworkEffect.Type
										.values().length)])
						.flicker(this.gen.nextBoolean())
						.trail(this.gen.nextBoolean())
						.withColor(
								Color.fromRGB(this.gen.nextInt(255),
										this.gen.nextInt(255),
										this.gen.nextInt(255)))
						.withFade(
								Color.fromRGB(this.gen.nextInt(255),
										this.gen.nextInt(255),
										this.gen.nextInt(255))).build());
				item.setFireworkMeta(fwm);
			}
			this.i -= 1;
			if (this.i <= 0)
				kill();
		}
	}

	public void kill() {
		this.task.cancel();
	}
}