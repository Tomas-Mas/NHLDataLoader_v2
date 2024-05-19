package statapi.v2;

import java.util.ArrayList;

public class GameWeek {

	private String nextStartDate;
	private ArrayList<GameDay> gameWeek;
	private String numberOfGames;
	
	public String getNextStartDate() {
		return nextStartDate;
	}
	
	public void setNextStartDate(String nextStartDate) {
		this.nextStartDate = nextStartDate;
	}
	
	public ArrayList<GameDay> getGameWeek() {
		return gameWeek;
	}
	
	public void setGameWeek(ArrayList<GameDay> gameWeek) {
		this.gameWeek = gameWeek;
	}
	
	public String getNumberOfGames() {
		return numberOfGames;
	}
	
	public void setNumberOfGames(String numberOfGames) {
		this.numberOfGames = numberOfGames;
	}
}
