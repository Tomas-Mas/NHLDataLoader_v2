package statapi.v2.utils;

import java.util.ArrayList;

import main.LogType;
import main.LogWriter;
import statapi.v2.teammodel.TeamApiData;

public class TeamModelStorage {

	private ArrayList<TeamApiData> teams;
	
	public TeamModelStorage(ArrayList<TeamApiData> teams) {
		this.teams = teams;
	}
	
	public TeamApiData getTeamData(String teamId) {
		TeamApiData team = findTeam(teamId);
		if(team == null) {
			LogWriter.writeLog(LogType.ERROR, "team id " + teamId + " not found in api");
		}
		return team;
	}
	
	private TeamApiData findTeam(String id) {
		for(TeamApiData team : teams) {
			if(team.getId().equals(id)) {
				return team;
			}
		}
		return null;
	}
}
