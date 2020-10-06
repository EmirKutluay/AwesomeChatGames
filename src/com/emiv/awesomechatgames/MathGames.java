package com.emiv.awesomechatgames;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class MathGames {
	
	GameClass gameCls;
	public MathGames(GameClass cls) {
		gameCls = cls;
	}
	
	public void MathGame() {
		gameCls.gameOn = true;
		String[] mathGameTypes = {"Addition", "Subtraction", "Multiplication", "Division"};
		String type = mathGameTypes[ThreadLocalRandom.current().nextInt(0, mathGameTypes.length)];
		if (type.equals("Addition")) {
			AdditionGame();
		} else if (type.equals("Subtraction")) {
			SubtractionGame();
		} else if (type.equals("Multiplication")) {
			MultiplicationGame();
		} else if (type.equals("Division")) {
			DivisionGame();
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
	
	public void AdditionGame() {
		int randomOne = ThreadLocalRandom.current().nextInt(0, 500);
		int randomTwo = ThreadLocalRandom.current().nextInt(0, 500);
		gameCls.gameAns = String.valueOf(randomOne + randomTwo);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("MathGameAdditionStartMsg").replace("%numberOne%", String.valueOf(randomOne)).replace("%numberTwo%", String.valueOf(randomTwo))));
		}
	}
	
	public void SubtractionGame() {
		int randomOne = ThreadLocalRandom.current().nextInt(0, 1000);
		int randomTwo = ThreadLocalRandom.current().nextInt(0, 1000);
		if (randomOne > randomTwo) {
			gameCls.gameAns = String.valueOf(randomOne - randomTwo);
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("MathGameSubtractionStartMsg").replace("%numberOne%", String.valueOf(randomOne)).replace("%numberTwo%", String.valueOf(randomTwo))));
			}
		} else {
			gameCls.gameAns = String.valueOf(randomTwo - randomOne);
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("MathGameSubtractionStartMsg").replace("%numberTwo%", String.valueOf(randomOne)).replace("%numberOne%", String.valueOf(randomTwo))));
			}
		}
	}
	
	public void MultiplicationGame() {
		int randomOne = ThreadLocalRandom.current().nextInt(0, 100);
		int randomTwo = ThreadLocalRandom.current().nextInt(0, 100);
		gameCls.gameAns = String.valueOf(randomOne * randomTwo);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("MathGameMultiplicationStartMsg").replace("%numberOne%", String.valueOf(randomOne)).replace("%numberTwo%", String.valueOf(randomTwo))));
		}
	}
	
	public void DivisionGame() {
		int randomOne = ThreadLocalRandom.current().nextInt(0, 100);
		int randomTwo = ThreadLocalRandom.current().nextInt(0, 100);
		gameCls.gameAns = String.valueOf(randomTwo);
		int multi = randomOne * randomTwo;
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', gameCls.plugin.getConfig().getString("ServerPrefix") + " " + gameCls.plugin.getMYaml().getString("MathGameDivisionStartMsg").replace("%numberOne%", String.valueOf(multi)).replace("%numberTwo%", String.valueOf(randomOne))));
		}
	}
	
}
