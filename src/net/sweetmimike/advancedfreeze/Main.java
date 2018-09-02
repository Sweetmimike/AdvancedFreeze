package net.sweetmimike.advancedfreeze;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.sweetmimike.advancedfreeze.commands.CommandFreeze;
import net.sweetmimike.advancedfreeze.commands.CommandUnFreeze;
import net.sweetmimike.advancedfreeze.events.FreezeListener;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		
		Bukkit.getPluginManager().registerEvents(new FreezeListener(this), this);
		
		getCommand("freeze").setExecutor(new CommandFreeze(this));
		getCommand("unfreeze").setExecutor(new CommandUnFreeze());
		super.onEnable();
	}
	
}
