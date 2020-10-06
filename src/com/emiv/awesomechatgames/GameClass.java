package com.emiv.awesomechatgames;

import java.util.concurrent.ThreadLocalRandom;

public class GameClass {

	MathGames mathCls = new MathGames(this);
	WordAnagrams wordCls = new WordAnagrams(this);
	Questions questionCls = new Questions(this);
	NumberGuesser numberCls = new NumberGuesser(this);
	
	public boolean gameOn = false;
	public String gameAns = null;
	
	public Main plugin;
	public GameClass(Main main) {
		plugin = main;
	}

	public void startGame() {
		String[] gameNames = {"MathGames", "WordAnagrams", "Questions", "NumberGuesser"};		
		int randomNum = ThreadLocalRandom.current().nextInt(0, gameNames.length);
		String nextGame = gameNames[randomNum];
		if (nextGame.equals("MathGames")) {
			if (plugin.getConfig().getBoolean("MathGames"))
			{
				mathCls.MathGame();
			} else {
				startGame();
			}
		} else if (nextGame.equals("WordAnagrams")) {
			if (plugin.getConfig().getBoolean("WordAnagrams"))
			{
				wordCls.WordAnagram();
			} else {
				startGame();
			}
		} else if (nextGame.equals("Questions")) {
			if (plugin.getConfig().getBoolean("Questions"))
			{
				questionCls.Question();
			} else {
				startGame();
			}
		} else if (nextGame.equals("NumberGuesser")) {
			if (plugin.getConfig().getBoolean("NumberGuesser"))
			{
				numberCls.NumberGuess();
			} else {
				startGame();
			}
		}
	}
	
}
