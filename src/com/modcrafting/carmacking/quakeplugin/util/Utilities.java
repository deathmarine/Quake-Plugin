package com.modcrafting.carmacking.quakeplugin.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.FireworkMeta;

public class Utilities {
	public static Random gen = new Random();

	private static Method getMethod(Class<?> cl, String method) {
		for (Method m : cl.getMethods())
			if (m.getName().equals(method))
				return m;
		return null;
	}

	public static UUID trail(Location loc) throws Exception {
		World world = loc.getWorld();
		Firework fw = (Firework) world.spawn(loc, Firework.class);
		UUID ud = fw.getUniqueId();
		fw.remove();
		Method d2 = getMethod(fw.getClass(), "getHandle");
		d2.invoke(fw, new Object(){});
		Method d0 = getMethod(world.getClass(), "getHandle");
		Object o3 = d0.invoke(world, new Object(){});
		Method d1 = getMethod(o3.getClass(), "addParticle");
		d1.invoke(
				o3,
				new Object[] { "fireworksSpark", Double.valueOf(loc.getX()),
						Double.valueOf(loc.getY()), Double.valueOf(loc.getZ()),
						Double.valueOf(gen.nextGaussian() * 0.05D),
						Double.valueOf(-(loc.getZ() * 1.15D) * 0.5D),
						Double.valueOf(gen.nextGaussian() * 0.05D) });
		return ud;
	}

	public static Firework rocket(Location start) {
		Firework fw = (Firework) start.getWorld().spawn(start, Firework.class);
		fw.setVelocity(start.getDirection());
		return fw;
	}

	public static Firework[] rocket(Location start, int amount) {
		List<Firework> fw = new ArrayList<Firework>();
		for (int i = amount; i > 0; i--) {
			fw.add(rocket(start));
		}
		return (Firework[]) fw.toArray();
	}

	public static void playExplosion(Location loc, int size) {
		for (int i = size; i > 0; i--)
			loc.getWorld().createExplosion(
					new Location(loc.getWorld(), loc.getX()
							+ gen.nextGaussian() * 0.05D, loc.getY()
							+ gen.nextGaussian() * 0.05D, loc.getZ()
							+ gen.nextGaussian() * 0.05D, loc.getPitch(),
							loc.getYaw()), 0.0F);
	}

	public static void playExplosion(Location loc) {
		loc.getWorld().createExplosion(loc, 0.0F);
	}

	public static UUID playFirework(Location loc, FireworkMeta data)
			throws Exception {
		Firework fw = (Firework) loc.getWorld().spawn(loc, Firework.class);
		UUID ud = fw.getUniqueId();
		fw.setFireworkMeta(data);
		Method d0 = getMethod(loc.getWorld().getClass(), "getHandle");
		Method d2 = getMethod(fw.getClass(), "getHandle");
		Object o3 = d0.invoke(loc.getWorld(), new Object(){});
		Object o4 = d2.invoke(fw, new Object(){});
		Method d1 = getMethod(o3.getClass(), "broadcastEntityEffect");
		d1.invoke(o3, new Object[] { o4, Byte.valueOf((byte) 17) });
		fw.remove();
		return ud;
	}

	public static void jibPlayer(LivingEntity le) {
		try {
			playFirework(le.getLocation(), jibFirework(false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		le.getWorld().createExplosion(le.getLocation(), 0.0F);
		if (le.getHealth() > 0)
			le.setHealth(0);
	}

	public static FireworkMeta jibFirework(boolean b) {
		FireworkMeta meta = (FireworkMeta) Bukkit.getItemFactory().getItemMeta(
				Material.FIREWORK);
		Color clr = Color.fromRGB(gen.nextInt(255), gen.nextInt(255),
				gen.nextInt(255));
		if (!b)
			clr = Color.RED;
		meta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST)
				.flicker(true).trail(false).withColor(clr).withFade(clr)
				.build());
		return meta;
	}
}