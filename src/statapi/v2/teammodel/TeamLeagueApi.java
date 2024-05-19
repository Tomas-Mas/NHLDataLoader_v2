package statapi.v2.teammodel;

import statapi.v2.DefaultName;

public class TeamLeagueApi {
	
	private String conferenceName;
	private String divisionName;
	private DefaultName teamAbbrev;
	
	public String getConferenceName() {
		return conferenceName;
	}
	
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}
	
	public String getDivisionName() {
		return divisionName;
	}
	
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	
	public String getTeamAbbrev() {
		return teamAbbrev.getName();
	}
	
	public void setTeamAbbrev(DefaultName teamAbbrev) {
		this.teamAbbrev = teamAbbrev;
	}
}
