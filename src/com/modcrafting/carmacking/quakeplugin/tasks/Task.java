package com.modcrafting.carmacking.quakeplugin.tasks;

import com.modcrafting.carmacking.quakeplugin.QuakePlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

public abstract class Task implements Runnable {
	QuakePlugin quake;
	World world;
	BukkitTask task;

	public Task(QuakePlugin quake, long time) {
		this.task = Bukkit.getScheduler().runTaskTimer(quake, this, 0L, time);
		this.quake = quake;
	}

	public abstract void kill();

	public void setWorld(World world) {
		this.world = world;
	}
}