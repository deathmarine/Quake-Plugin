package com.modcrafting.carmacking.quakeplugin.weaponry;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.modcrafting.carmacking.quakeplugin.QuakePlugin;
import com.modcrafting.carmacking.quakeplugin.tasks.RocketPositionTask;
import com.modcrafting.carmacking.quakeplugin.tasks.Task;
import com.modcrafting.carmacking.quakeplugin.util.Utilities;

public class RocketLauncher extends Weapon {
	RocketPositionTask task;
	ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<String, Long>();

	public RocketLauncher() {
		super(Weapon.WeaponType.ROCKET_LAUNCHER, 500);
	}

	@Override
	public void fireEffect(Event bevent) {
		if ((bevent instanceof PlayerInteractEvent)) {
			PlayerInteractEvent event = (PlayerInteractEvent) bevent;
			if (!event.getPlayer().hasPermission("quake.rocketlauncher"))
				return;
			if ((event.getPlayer().isSneaking())
					&& (event.getPlayer()
							.hasPermission("quake.rocketlauncher.altfire"))) {
				boolean fired = false;

				if (fired)
					return;
			}
			if (!this.map.containsKey(event.getPlayer().getName()))
				switch (event.getAction()) {
				case LEFT_CLICK_AIR:
					break;
				case LEFT_CLICK_BLOCK:
					break;
				case PHYSICAL:
					break;
				case RIGHT_CLICK_AIR:
					fire(event.getPlayer());
					break;
				case RIGHT_CLICK_BLOCK:
					fire(event.getPlayer());
					break;
				default:
					break;
				}
			else if (System.currentTimeMillis()
					- this.map.get(event.getPlayer().getName()).longValue() > this.reload_time) {
				this.map.remove(event.getPlayer().getName());
			}

		}

		if ((bevent instanceof PlayerInteractEntityEvent)) {
			PlayerInteractEntityEvent event = (PlayerInteractEntityEvent) bevent;
			if (!event.getPlayer().hasPermission("quake.rocketlauncher.melee"))
				return;
			if ((event.getRightClicked() instanceof Player))
				melee(event.getPlayer(), (Player) event.getRightClicked());
		}
	}

	@Override
	public void addTask(Task task) {
		if (!(task instanceof RocketPositionTask))
			throw new RuntimeException("InvalidTaskAssignment");
		this.task = ((RocketPositionTask) task);
	}

	@Override
	public void fire(Player player) {
		for (Player players : player.getWorld().getPlayers())
			players.playSound(player.getLocation(), Sound.CREEPER_HISS, 2.0F,
					2.0F);
		final Firework fw = Utilities.rocket(player.getLocation());
		Bukkit.getScheduler().runTaskLater(QuakePlugin.getInstance(),
				new Runnable() {
					@Override
					public void run() {
						RocketLauncher.this.task.chm.add(fw.getUniqueId());
					}
				}, 5L);
		this.map.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
	}

	@Override
	public void melee(Player attacker, Player victim) {

	}
}