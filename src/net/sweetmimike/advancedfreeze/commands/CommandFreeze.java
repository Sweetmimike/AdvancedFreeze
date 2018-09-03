package net.sweetmimike.advancedfreeze.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.sweetmimike.advancedfreeze.Main;
import net.sweetmimike.advancedfreeze.events.FreezeListener;

public class CommandFreeze implements CommandExecutor {

	Main main;

	public CommandFreeze(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {



		if(args.length == 1) {

			if(args[0].equalsIgnoreCase("help")) {
				if(sender.hasPermission("af.help")) {
					onHelp(sender);
					return true;
				} else {
					sender.sendMessage("§4You do not have permission to do that");
					return true;
				}
			}

			if(args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission("af.reload")) {
					main.reloadConfig();
					sender.sendMessage("§3§lAdvanced§b§lFreeze §7>> §aConfig file reloaded");
					return true;
				} else {
					sender.sendMessage("§4You do not have permission to do that");
					return true;
				}
			}

			if(sender.hasPermission("af.freeze")) {
				String target = args[0];

				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(target.equalsIgnoreCase(p.getName())) {
						if(FreezeListener.frozenPlayers.contains(p.getName()) || FreezeListener.frozenPlayersTime.containsKey(p.getName())) {
							sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + p.getName() + " §cis already frozen");
							return true;
						}
						if(!(p.hasPermission("af.bypass"))) {
							FreezeListener.frozenPlayers.add(p.getName());
							if(isParticleEnable())
								onFreeze(p);
							if(isSoundEnable())
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
							sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + p.getName() + " §ahas been frozen");
							p.sendMessage("§3§lAdvanced§b§lFreeze §7>> " + main.getConfig().getString("notification_message.frozen").replace("&", "§"));
							return true;
						}
						sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§c You can't freeze this player cause he has af.bypass");
						return true;
					}
				}
				sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + target + " §cis offline");

				return true;
			} else {
				sender.sendMessage("§4You do not have permission to do that");
				return true;
			}
		}

		else if(args.length == 2) {

			if(sender.hasPermission("af.freeze")) {
				String target = args[0];
				long time;

				if(!isNumber(args[1])) {
					sender.sendMessage("§4Error : §c" + args[1] + " §4is not a number !");
					return true;
				}
				time = Long.parseLong(args[1]);

				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(target.equalsIgnoreCase(p.getName())) {
						if(FreezeListener.frozenPlayers.contains(p.getName()) || FreezeListener.frozenPlayersTime.containsKey(p.getName())) {
							sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + p.getName() + " §cis already frozen");
							return true;
						}
						if(!(p.hasPermission("af.bypass"))) {
							FreezeListener.frozenPlayersTime.put(p.getName(), (System.currentTimeMillis() / 1000) + time);
							if(isParticleEnable())
								onFreeze(p);
							if(isSoundEnable())
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
							sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + p.getName() + " §ahas been frozen for §2" + time + "§as");
							p.sendMessage("§3§lAdvanced§b§lFreeze §7>>§a You have been frozen for §2" + time + "§as");

							//au bout de 20 * time, remove
							new BukkitRunnable() {

								@Override
								public void run() {
									FreezeListener.frozenPlayersTime.remove(p.getName());
									p.sendMessage("§3§lAdvanced§b§lFreeze §7>>§a You have been unfrozen");
									
									if(!(FreezeListener.frozenPlayersTime.containsKey(p.getName()))) {
										cancel();
									}

								}
							}.runTaskLater(main, 20 * time);
							return true;
						}
						sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§c You can't freeze this player cause he has af.bypass");
						return true;

					}

				}
				sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + target + " §cis offline");
				return true;

			} else {
				sender.sendMessage("§4You do not have permission to do that");
				return true;
			}

		} else {
			onHelp(sender);
			return true;
		}
	}



	public void onFreeze(Player p) {

		World world = p.getWorld();
		Location loc = p.getLocation();
		new BukkitRunnable() {
			double angle = 2 * Math.PI / 16; // ou = math.toRadian(90);
			double radius = 1.5;
			double x,z;
			@Override
			public void run() {
				angle += 0.1;
				x = radius * Math.cos(angle);
				z = radius * Math.sin(angle);
				loc.add(x, 1, z);
				world.spawnParticle(Particle.SNOWBALL, loc, 2);
				loc.subtract(x, 1, z);

				if(!(FreezeListener.frozenPlayers.contains(p.getName())) && !(FreezeListener.frozenPlayersTime.containsKey(p.getName()))) {
					cancel();
				}
			}
		}.runTaskTimer(main, 0, (long)0.5);
	}

	public static void onHelp(CommandSender sender) {
		sender.sendMessage("§b§l******* §3§lAdvanced§b§lFreeze *******");
		sender.sendMessage("");
		sender.sendMessage("§3> §b/freeze <player> [time] §7- §bFreezes the player");
		sender.sendMessage("§3> §b/unfreeze <player> §7- §bUnfreezes the player");
		sender.sendMessage("§3> §b/freeze help §7- §bShows the help");
		sender.sendMessage("");
		sender.sendMessage("§b§l******* §3§lAdvanced§b§lFreeze *******");
	}

	public boolean isNumber(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch(NumberFormatException e) {
		}
		return false;
	}

	public boolean isParticleEnable() {
		return main.getConfig().getBoolean("particle_enable");
	}

	public boolean isSoundEnable() {
		return main.getConfig().getBoolean("sound_enable");
	}
	
	public CommandFreeze getInstance() {
		return this;
	}
}
