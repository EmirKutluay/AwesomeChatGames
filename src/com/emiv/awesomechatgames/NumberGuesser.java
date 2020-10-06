package com.emiv.awesomechatgames;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class NumberGuesser {
	
	GameClass gameCls;
	public NumberGuesser(GameClass cls) {
		gameCls = cls;
	}
	
	public void NumberGuess() {
		gameCls.gameOn = true;
		int randomOne = ThreadLocalRandom.current().nextInt(0, 10);
		int randomTwo = ThreadLocalRandom.current().nextInt(0, 10);
		while (randomOne == randomTwo) {
			randomTwo = ThreadLocalRandom.current().nextInt(0, 10);
		}
		int firstNumber = randomOne * 10;
		int secondNumber = randomTwo * 10;
		int answer = 0;
		if (firstNumber < secondNumber) {
			answer = ThreadLocalRandom.current().nextInt(firstNumber, secondNumber);
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("NumberGuesserStartMsg").replace("%numberOne%", String.valueOf(firstNumber)).replace("%numberTwo%", String.valueOf(secondNumber))));
			}
		} else {
			answer = ThreadLocalRandom.current().nextInt(secondNumber, firstNumber);
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("NumberGuesserStartMsg").replace("%numberOne%", String.valueOf(secondNumber)).replace("%numberTwo%", String.valueOf(firstNumber))));
			}
		}
		gameCls.gameAns = String.valueOf(answer);
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
