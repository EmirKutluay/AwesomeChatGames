package com.emiv.awesomechatgames;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class WordAnagrams {

	GameClass gameCls;
	public WordAnagrams(GameClass cls) {
		gameCls = cls;
	}
	
	public void WordAnagram() {
		gameCls.gameOn = true;
		List<String> words = gameCls.plugin.getConfig().getStringList("WordsForWordAnagrams");
		String word = words.get(ThreadLocalRandom.current().nextInt(0, words.size()));
		gameCls.gameAns = word;
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("WordAnagramStartMsg").replace("%word%", shuffle(word))));
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
	
    public String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }
	
}
