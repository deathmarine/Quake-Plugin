package com.modcrafting.carmacking.quakeplugin.tasks;

import com.modcrafting.carmacking.quakeplugin.QuakePlugin;
import com.modcrafting.carmacking.quakeplugin.util.Utilities;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

public class RailPositionTask extends Task {
	public HashMap<UUID, String> chm = new HashMap<UUID, String>();

	public RailPositionTask(QuakePlugin quake) {
		super(quake, 5L);
	}

	public void run() {
		if (this.world != null) {
			Set<String> temp = new HashSet<String>();
			for (Firework fw : this.world.getEntitiesByClass(Firework.class)) {
				if (this.chm.containsKey(fw.getUniqueId())) {
					for (Entity e : fw.getNearbyEntities(1.0D, 1.0D, 1.0D)) {
						if ((e instanceof Player)) {
							Player player = (Player) e;
							if ((!temp.contains(player.getName()))
									&& (player.getHealth() > 0)) {
								Utilities.jibPlayer(player);
								this.chm.remove(fw.getUniqueId());
							}
						}
					}
				}
			}
			temp.clear();
		} else {
			setWorld((World) Bukkit.getWorlds().get(0));
		}
	}

	public void kill() {
		this.chm.clear();
		this.task.cancel();
	}
}