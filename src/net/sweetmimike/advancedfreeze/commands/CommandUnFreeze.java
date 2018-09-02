package net.sweetmimike.advancedfreeze.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.sweetmimike.advancedfreeze.events.FreezeListener;

public class CommandUnFreeze implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length == 1) {
			if(sender.hasPermission("af.unfreeze")) {
			String target = args[0];

			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(target.equalsIgnoreCase(p.getName())) {
					if(FreezeListener.frozenPlayers.contains(p.getName())) {
						FreezeListener.frozenPlayers.remove(target);
						sender.sendMessage("AdvancedFreeze >> " + p.getName() + " is unfrozen");
						p.sendMessage("AdvancedFreeze >> You are unfrozen");
						return true;
					}
					sender.sendMessage("AdvancedFreeze >> You can't unfreeze this player");
					
					return true;
				}
			}
			sender.sendMessage("AdvancedFreeze >> " + target + " is offline");

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
