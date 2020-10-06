package com.emiv.awesomechatgames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class onChat implements Listener {

	Main plugin;
	public onChat(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onChatPlayer(AsyncPlayerChatEvent e) {
		if (plugin.gameCls.gameOn == true) {
			if (e.getMessage().toLowerCase().equals(plugin.gameCls.gameAns.toLowerCase())) {
				plugin.gameCls.gameOn = false;
				Long timeInTicks = (long) (20 * plugin.getConfig().getInt("WaitBetweenGames"));
				new BukkitRunnable() {
					@Override
					public void run() {
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ServerPrefix") + " " + plugin.getMYaml().getString("CorrectAnswer").replace("%player%", e.getPlayer().getName()).replace("%answer%", plugin.gameCls.gameAns)));
						}
						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ServerPrefix") + " " + plugin.getMYaml().getString("RewardReceived").replace("%reward%", plugin.getConfig().getString("RewardName"))));
						String command = plugin.getConfig().getString("RewardConsoleCommand").replace("%player%", e.getPlayer().getName());
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
					}
				}.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames"), 15);
				new BukkitRunnable() {
					@Override
					public void run() {
						plugin.gameCls.startGame();
					}
				}.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames"), timeInTicks);
			}
		}
	}
	
}
