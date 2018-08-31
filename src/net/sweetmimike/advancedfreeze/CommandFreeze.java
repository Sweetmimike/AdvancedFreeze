package net.sweetmimike.advancedfreeze;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.sweetmimike.advancedfreeze.events.FreezeListener;

public class CommandFreeze implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length == 1) {
			
			String target = args[0];
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(target.equalsIgnoreCase(p.getName())) {
					if(FreezeListener.frozenPlayers.contains(p.getName())) {
						sender.sendMessage("AdvancedFreeze >> " + p.getName() + " is already frozen");
						return true;
					}
					FreezeListener.frozenPlayers.add(p.getName());
					sender.sendMessage("AdvancedFreeze >> " + p.getName() + " has been frozen");
					p.sendMessage("AdvancedFreeze >> You have been frozen");
					return true;
				}
			}
			sender.sendMessage("AdvancedFreeze >> " + target + " is offline");
			
			return true;
		}
		
		return false;
	}

}
