package com.modcrafting.carmacking.quakeplugin.weaponry;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import com.modcrafting.carmacking.quakeplugin.tasks.RailPositionTask;
import com.modcrafting.carmacking.quakeplugin.tasks.Task;
import com.modcrafting.carmacking.quakeplugin.util.Utilities;

public class RailGun extends Weapon {
	FireworkMeta meta;
	RailPositionTask task;
	ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<String, Long>();

	public RailGun() {
		super(Weapon.WeaponType.RAILGUN, 1500);
	}

	@Override
	public void fireEffect(Event bevent) {
		if ((bevent instanceof PlayerInteractEvent)) {
			PlayerInteractEvent event = (PlayerInteractEvent) bevent;
			if (!event.getPlayer().hasPermission("quake.railgun"))
				return;
			switch (event.getAction()) {
			case LEFT_CLICK_AIR:
				break;
			case LEFT_CLICK_BLOCK:
				break;
			case PHYSICAL:
				break;
			case RIGHT_CLICK_AIR:
				if (!this.map.containsKey(event.getPlayer().getName())) {
					fire(event.getPlayer());
				} else if (System.currentTimeMillis()
						- this.map.get(event.getPlayer().getName()).longValue() > getReloadTime()) {
					this.map.remove(event.getPlayer().getName());
					fire(event.getPlayer());
				}
				break;
			case RIGHT_CLICK_BLOCK:
				break;
			default:
				break;
			}

		}

		if ((bevent instanceof PlayerInteractEntityEvent)) {
			PlayerInteractEntityEvent event = (PlayerInteractEntityEvent) bevent;
			if (!event.getPlayer().hasPermission("quake.railgun"))
				return;
			if (!this.map.containsKey(event.getPlayer().getName())) {
				fire(event.getPlayer());
			} else if (System.currentTimeMillis()
					- this.map.get(event.getPlayer().getName()).longValue() > getReloadTime()) {
				this.map.remove(event.getPlayer().getName());
				fire(event.getPlayer());
			}
		}

	}

	@Override
	public void addTask(Task task) {
		if (!(task instanceof RailPositionTask))
			throw new RuntimeException("InvalidTaskAssignment");
		this.task = ((RailPositionTask) task);
	}

	@Override
	public void fire(Player player) {
		this.map.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
		UUID uuid = null;
		Set<String> temp = new HashSet<String>();
		for (Block loc : player.getLineOfSight(null, 50)) {
			try {
				for (Player pl : loc.getLocation().getWorld()
						.getEntitiesByClass(Player.class)) {
					if ((pl.getLocation().distance(loc.getLocation()) < 2.0D)
							&& (!pl.getName().equals(player.getName()))
							&& (!temp.contains(pl.getName()))
							&& (pl.getHealth() > 0)) {
						Utilities.jibPlayer(pl);
						temp.add(pl.getName());
					}
				}
				uuid = Utilities.trail(loc.getLocation());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ((uuid != null) && (temp.size() < 0))
			this.task.chm.put(uuid, player.getName());

		for (Player players : player.getWorld().getPlayers())
			players.playSound(player.getLocation(), Sound.BLAZE_HIT, 2.0F, 4.0F);
	}

	@Override
	public void melee(Player attacker, Player victim) {

	}
}
