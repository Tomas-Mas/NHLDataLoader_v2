package statsapi.datamodel;

import java.util.ArrayList;

import statsapi.gamemodel.GameModel;

public class DataModelDates {

	private String date;
	private String totalItems;
	private ArrayList<GameModel> games = new ArrayList<GameModel>();
	
	public String getDate() {
		return date;
	}
	
	public String getTotalItems() {
		return totalItems;
	}
	
	public ArrayList<GameModel> getGames() {
		return games;
	}
}
