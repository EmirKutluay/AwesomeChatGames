package com.emiv.awesomechatgames;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin{

	//Messages
	private File mFile;
	private YamlConfiguration mYaml;
	
	//Questions
	private File qFile;
	private YamlConfiguration qYaml;
	
	GameClass gameCls = new GameClass(this);
	
	@Override
	public void onEnable() {
		
		this.saveDefaultConfig();
		final FileConfiguration config = this.getConfig();
		
		Bukkit.getPluginManager().registerEvents(new onChat(this), this);
		
		getCommand("acg").setExecutor(new ACGCommand(this));
		
		try {
			initiateFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setMsg();
		setQuestions();
		Save();
		
		Long timeInTicks = (long) (4 * config.getInt("WaitBetweenGames"));
		new BukkitRunnable() {
		        
		    @Override
		    public void run() {
				gameCls.startGame();
		    }
		}.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames"), timeInTicks);
	}
	
	public void Save() {
		try {
			mYaml.save(mFile);
			qYaml.save(qFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setMsg() {
		if (!mYaml.contains("NoPermission")) {
			mYaml.set("NoPermission", "&cYou don't have permission.");
		}
		if (!mYaml.contains("ConfigsReloaded")) {
			mYaml.set("ConfigsReloaded", "&eSuccessfully reloaded all configurations.");
		}
		if (!mYaml.contains("CorrectAnswer")) {
			mYaml.set("CorrectAnswer", "&5%player% &ewon! Correct answer was &a%answer%");
		}
		if (!mYaml.contains("RewardReceived")) {
			mYaml.set("RewardReceived", "&eYou received &d%reward% &efor answering correctly!");
		}
		if (!mYaml.contains("NoAnswers")) {
			mYaml.set("NoAnswers", "&eNo one answered the question, time is up!");
		}
		if (!mYaml.contains("MathGameAdditionStartMsg")) {
			mYaml.set("MathGameAdditionStartMsg", "&eHow much is &a%numberOne% &e+ &a%numberTwo%&e?");
		}
		if (!mYaml.contains("MathGameSubtractionStartMsg")) {
			mYaml.set("MathGameSubtractionStartMsg", "&eHow much is &a%numberOne% &e- &a%numberTwo%&e?");
		}
		if (!mYaml.contains("MathGameMultiplicationStartMsg")) {
			mYaml.set("MathGameMultiplicationStartMsg", "&eHow much is &a%numberOne% &e* &a%numberTwo%&e?");
		}
		if (!mYaml.contains("MathGameDivisionStartMsg")) {
			mYaml.set("MathGameDivisionStartMsg", "&eHow much is &a%numberOne% &e/ &a%numberTwo%&e?");
		}
		if (!mYaml.contains("WordAnagramStartMsg")) {
			mYaml.set("WordAnagramStartMsg", "&eCan you solve the anagram &c%word%&e?");
		}
		if (!mYaml.contains("QuestionStartMsg")) {
			mYaml.set("QuestionStartMsg", "&e%question%");
		}
		if (!mYaml.contains("NumberGuesserStartMsg")) {
			mYaml.set("NumberGuesserStartMsg", "&eGuess a number between &a%numberOne% &eand &a%numberTwo%");
		}
	}
	
	public void setQuestions() {
		if (qYaml.getKeys(true).size() == 0) {
			qYaml.set("How much is a stack in Minecraft?", "64");
			qYaml.set("What is the animal that creepers are afraid of?", "Ocelot");
			qYaml.set("How tall is enderman in blocks?", "3");
			qYaml.set("How long is a Minecraft day in minutes?", "20");
			qYaml.set("How tall is a Ghast (not including the tentacles) in blocks?", "4");
		}
	}
	
	public YamlConfiguration getMYaml() { return mYaml; }
	public File getMFile() { return mFile; }
	public YamlConfiguration getQYaml() { return qYaml; }
	public File getQFile() { return qFile; }
	
	public void initiateFiles() throws IOException {
		mFile = new File(Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames").getDataFolder(), "messages.yml");
		if (!mFile.exists()) {
			mFile.createNewFile();
		}
		
		mYaml = YamlConfiguration.loadConfiguration(mFile);
		
		qFile = new File(Bukkit.getServer().getPluginManager().getPlugin("AwesomeChatGames").getDataFolder(), "questions.yml");
		if (!qFile.exists()) {
			qFile.createNewFile();
		}
		
		qYaml = YamlConfiguration.loadConfiguration(qFile);
	}
}
