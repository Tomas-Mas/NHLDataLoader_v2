package statapi.v2;

import java.util.ArrayList;

public class GameDay {

	private String date;
	private String numberOfGames;
	
	private ArrayList<GameModelV2> games;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNumberOfGames() {
		return numberOfGames;
	}

	public void setNumberOfGames(String numberOfGames) {
		this.numberOfGames = numberOfGames;
	}
	
	public ArrayList<GameModelV2> getGames() {
		return games;
	}
	
	public void setGames(ArrayList<GameModelV2> games) {
		this.games = games;
	}
}
