package com.emiv.awesomechatgames;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Questions {

	GameClass gameCls;
	public Questions(GameClass cls) {
		gameCls = cls;
	}
	
	public void Question() {
		Set<String> keys = gameCls.plugin.getQYaml().getKeys(true);
		int index = ThreadLocalRandom.current().nextInt(0, keys.size());
		String[] keyArray = new String[keys.size()];
		keys.toArray(keyArray);
		gameCls.gameOn = true;
		String question = keyArray[index];
		String answer = gameCls.plugin.getQYaml().getString(keyArray[index]);
		gameCls.gameAns = answer;
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("QuestionStartMsg").replace("%question%", question)));
		}
		Long timeInTicks = (long) (20 * gameCls.plugin.getConfig().getInt("WaitBetweenGames"));
		new BukkitRunnable() {
			@Override
			public void run() {
				if (gameCls.gameOn) {
					gameCls.gameOn = false;
					for (Player p: Bukkit.getOnlinePlayers()){
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("NoAnswers")));
					}
					new BukkitRunnable() {
						@Override
						public void run() {
							gameCls.startGame();
						}
					}.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames"), (long) (timeInTicks * 0.2));
				}
			}
		}.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames"), timeInTicks);
	}
	
	
}
