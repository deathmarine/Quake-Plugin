package com.modcrafting.carmacking.quakeplugin.weaponry;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.modcrafting.carmacking.quakeplugin.tasks.Task;

public abstract class Weapon {
	WeaponType type;
	public int reload_time;

	public Weapon(String string, int reload) {
		this.type = WeaponType.valueOf(string.toUpperCase());
		setReloadTime(reload);
	}

	public Weapon(WeaponType type, int reload) {
		this.type = type;
		setReloadTime(reload);
	}

	public WeaponType getType() {
		return this.type;
	}

	public int getSlot() {
		return this.type.getSlot();
	}

	public int getReloadTime() {
		return this.reload_time;
	}

	public void setReloadTime(int reload_time) {
		this.reload_time = reload_time;
	}

	public abstract void fireEffect(Event paramEvent);

	public abstract void addTask(Task paramTask);

	public abstract void fire(Player paramPlayer);

	public abstract void melee(Player paramPlayer1, Player paramPlayer2);

	public static enum WeaponType {
		RAILGUN("railgun", 0), ROCKET_LAUNCHER("rocketlauncher", 1);

		String name;
		int slot;

		private WeaponType(String name, int slot) {
			this.name = name;
			this.slot = slot;
		}

		public int getSlot() {
			return this.slot;
		}
	}
}