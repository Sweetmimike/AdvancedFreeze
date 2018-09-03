package net.sweetmimike.advancedfreeze.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.sweetmimike.advancedfreeze.Main;
import net.sweetmimike.advancedfreeze.events.FreezeListener;

public class CommandUnFreeze implements CommandExecutor {

	Main main;
	CommandFreeze freeze;

	public CommandUnFreeze(Main main, CommandFreeze freeze) {
		this.main = main;
		this.freeze = freeze.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length == 1) {
			if(sender.hasPermission("af.unfreeze")) {
				String target = args[0];

				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(target.equalsIgnoreCase(p.getName())) {
						if(FreezeListener.frozenPlayers.contains(p.getName())) {
							FreezeListener.frozenPlayers.remove(target);
							sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + p.getName() + " §ais unfrozen");
							if(freeze.isSoundEnable())
								p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
							p.sendMessage("§3§lAdvanced§b§lFreeze §7>> " + main.getConfig().getString("notification_message.unfrozen").replace("&", "§"));
							return true;
						}
						if(FreezeListener.frozenPlayersTime.containsKey(p.getName())) {
							FreezeListener.frozenPlayersTime.remove(target);
							sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§e " + p.getName() + " §ais unfrozen");
							if(freeze.isSoundEnable())
								p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
							p.sendMessage("§3§lAdvanced§b§lFreeze §7>> " + main.getConfig().getString("notification_message.unfrozen").replace("&", "§"));
							return true;
						}
						sender.sendMessage("§3§lAdvanced§b§lFreeze §7>>§c You can't unfreeze this player");

						return true;
					}
				}
				sender.sendMessage("§3Advanced§bFreeze §7>>§e " + target + " §cis offline");

				return true;
			} else {
				sender.sendMessage("§4You do not have permission to do that");
				return true;
			}
		} else {
			CommandFreeze.onHelp(sender);
			return true;
		}

	}

}
