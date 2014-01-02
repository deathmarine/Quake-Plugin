package com.modcrafting.carmacking.quakeplugin.tasks;

import com.modcrafting.carmacking.quakeplugin.QuakePlugin;
import com.modcrafting.carmacking.quakeplugin.util.Utilities;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;

public class RocketPositionTask extends Task {
	public HashSet<UUID> chm = new HashSet<UUID>();

	public RocketPositionTask(QuakePlugin quake) {
		super(quake, 5L);
	}

	public void run() {
		if (this.world != null) {
			for (Firework fw : this.world.getEntitiesByClass(Firework.class)) {
				if (this.chm.contains(fw.getUniqueId()))
					for (Entity e : fw.getNearbyEntities(1.0D, 1.0D, 1.0D)) {
						if ((e instanceof LivingEntity)) {
							Utilities.jibPlayer((LivingEntity) e);
							fw.remove();
							this.chm.remove(e.getUniqueId());
						}
						if (fw.getVelocity().length() < 2.0D) {
							Utilities.playExplosion(e.getLocation());
							fw.remove();
							this.chm.remove(e.getUniqueId());
						}
					}
			}
		} else {
			setWorld((World) Bukkit.getWorlds().get(0));
		}
	}

	public void kill() {
		this.chm.clear();
		this.task.cancel();
	}
}