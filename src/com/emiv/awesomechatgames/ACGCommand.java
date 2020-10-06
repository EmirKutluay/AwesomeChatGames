package com.emiv.awesomechatgames;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class ACGCommand implements CommandExecutor {

	public Main plugin;
	public ACGCommand(Main main) {
		plugin = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String correctUse = "&8----" + plugin.getConfig().getString("ServerPrefix") + "&8----" + "\n" + "&d/acg reload &8- &eReload all configurations";
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', correctUse));
			} else if (args.length == 1) {
				if (args[0].equals("reload")) {
					if (p.hasPermission("awesomechatgames.reload")) {
						reloadCommand();
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ServerPrefix") + " " + plugin.getMYaml().getString("ConfigsReloaded")));
					} else {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ServerPrefix") + " " + plugin.getMYaml().getString("NoPermission")));
					}
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', correctUse));
				}
			} else {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', correctUse));
			}
		} else {
			if (args.length == 0) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', correctUse));
			} else if (args.length == 1) {
				if (args[0].equals("reload")) {
					reloadCommand();
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ServerPrefix") + " " + plugin.getMYaml().getString("ConfigsReloaded")));
				} else {
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', correctUse));
				}
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', correctUse));
			}
		}
		return false;
	}
	
	public void reloadCommand() {
		Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames");
		plugin.reloadConfig();
		pl.getPluginLoader().disablePlugin(pl);
		pl.getPluginLoader().enablePlugin(pl);
	}

}
