package statapi.v2.playermodel;

import java.util.ArrayList;

public class TeamRosters {

	private ArrayList<PlayerBasic> home;
	private ArrayList<PlayerBasic> away;
	
	public ArrayList<PlayerBasic> getHome() {
		return home;
	}
	
	public void setHome(ArrayList<PlayerBasic> home) {
		this.home = home;
	}
	
	public ArrayList<PlayerBasic> getAway() {
		return away;
	}
	
	public void setAway(ArrayList<PlayerBasic> away) {
		this.away = away;
	}
}
