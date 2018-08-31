package net.sweetmimike.advancedfreeze.events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {
	
	public static ArrayList<String> frozenPlayers = new ArrayList<>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(frozenPlayers.contains(p.getName()) ) {
			e.setCancelled(true);
			p.sendMessage("AdvancedFreeze >> You are frozen, you can't move !");
		}
	}

}
