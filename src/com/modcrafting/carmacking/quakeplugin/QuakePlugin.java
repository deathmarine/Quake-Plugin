package com.modcrafting.carmacking.quakeplugin;

import com.modcrafting.carmacking.quakeplugin.tasks.RailPositionTask;
import com.modcrafting.carmacking.quakeplugin.tasks.RocketPositionTask;
import com.modcrafting.carmacking.quakeplugin.weaponry.RailGun;
import com.modcrafting.carmacking.quakeplugin.weaponry.RocketLauncher;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class QuakePlugin extends JavaPlugin {
	private GunManager gun;
	private static QuakePlugin quake;

	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}

	public void onEnable() {
		quake = this;
		this.gun = new GunManager(this);
		getServer().getPluginManager().registerEvents(this.gun, this);
		this.getDataFolder().mkdir();
		this.saveDefaultConfig();
		this.gun.registerWeapon(
				Material.getMaterial(this.getConfig().getInt("RailGun", 290)),
				new RailGun(), new RailPositionTask(this));
		this.gun.registerWeapon(Material.getMaterial(this.getConfig().getInt(
				"RocketLauncher", 271)), new RocketLauncher(),
				new RocketPositionTask(this));
	}

	public static QuakePlugin getInstance() {
		return quake;
	}
}