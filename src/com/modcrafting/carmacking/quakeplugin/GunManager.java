package com.modcrafting.carmacking.quakeplugin;

import com.modcrafting.carmacking.quakeplugin.tasks.Task;
import com.modcrafting.carmacking.quakeplugin.weaponry.Weapon;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GunManager implements Listener {
	QuakePlugin plugin;
	HashMap<Material, Weapon> map = new HashMap<Material, Weapon>();

	public GunManager(QuakePlugin quake) {
		this.plugin = quake;
	}

	public void registerWeapon(Material mat, Weapon weapon) {
		this.map.put(mat, weapon);
	}

	public void registerWeapon(Material mat, Weapon weapon, Task task) {
		weapon.addTask(task);
		this.map.put(mat, weapon);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (this.map.containsKey(event.getMaterial()))
			((Weapon) this.map.get(event.getMaterial())).fireEffect(event);
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		if (this.map.containsKey(event.getPlayer().getItemInHand().getType()))
			((Weapon) this.map.get(event.getPlayer().getItemInHand().getType()))
					.fireEffect(event);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
	}
}