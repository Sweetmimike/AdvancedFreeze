package net.sweetmimike.advancedfreeze;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.sweetmimike.advancedfreeze.events.FreezeListener;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		
		Bukkit.getPluginManager().registerEvents(new FreezeListener(), this);
		
		getCommand("freeze").setExecutor(new CommandFreeze());
		getCommand("unfreeze").setExecutor(new CommandUnFreeze());
		super.onEnable();
	}
	
}
