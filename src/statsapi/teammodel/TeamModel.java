package statsapi.teammodel;

import java.util.ArrayList;

public class TeamModel {

	private ArrayList<TeamData> teams = new ArrayList<TeamData>();
	
	public TeamData getTeamData() {
		return teams.get(0);
	}
}
