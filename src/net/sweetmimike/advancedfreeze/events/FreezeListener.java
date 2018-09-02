package net.sweetmimike.advancedfreeze.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.sweetmimike.advancedfreeze.Main;

public class FreezeListener implements Listener {

	Main main;
	public static ArrayList<String> frozenPlayers = new ArrayList<>();
	public static Map<String, Double> frozenPlayersTime = new HashMap<>();
	public int i = 0;

	public FreezeListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		Player p = e.getPlayer();

		if(frozenPlayers.contains(p.getName()) ) {
			e.setCancelled(true);
			p.sendMessage("AdvancedFreeze >> You are frozen, you can't move !");
		}
	}
}
