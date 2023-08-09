package statsapi.eventmodel;

import java.util.ArrayList;

public class Event {

	private ArrayList<EventPlayer> players = new ArrayList<EventPlayer>();
	private Result result;
	private About about;
	private Coordinates coordinates;
	
	public ArrayList<EventPlayer> getPlayers() {
		return players;
	}
	
	public Result getResult() {
		return result;
	}
	
	public About getAbout() {
		return about;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
}
