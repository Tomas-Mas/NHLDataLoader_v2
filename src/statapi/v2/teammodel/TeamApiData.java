package statapi.v2.teammodel;

import statapi.v2.utils.TeamLocationMapper;

public class TeamApiData {

	private TeamApi team;
	private FranchiseApi franchise;
	private TeamLeagueApi league;
	
	private String season;
	private String venueName;
	private String timeZoneName;
	private String timeZoneOffset;
	
	public void setTeam(TeamApi team) {
		this.team = team;
	}
	
	public void setFranchise(FranchiseApi franchise) {
		this.franchise = franchise;
	}
	
	public void setLeague(TeamLeagueApi league) {
		this.league = league;
	}
	
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	
	public void setTimeZoneName(String timeZoneName) {
		this.timeZoneName = timeZoneName;
	}
	
	public void setTimeZoneOffset(String timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}
	
	public String getId() {
		return team.getId();
	}
	
	public String getFullName() {
		return team.getFullName();
	}
	
	public String getAbbreviation() {
		return team.getTriCode();
	}
	
	public String getTeamName() {
		return franchise.getTeamCommonName();
	}
	
	public String getShortName() {
		return franchise.getTeamPlaceName();
	}
	
	public String getLocation() {
		return TeamLocationMapper.getLocation(getShortName());
	}
	
	public String getFirstYear() {
		return season;
	}
	
	public String getDivision() {
		return league.getDivisionName();
	}
	
	public String getConference() {
		return league.getConferenceName();
	}
	
	public String getVenueName() {
		if(venueName == null)
			return "Unknown";
		return venueName;
	}
	
	public String getVenueCity() {
		if(!getVenueName().equals("Unknown"))
			return getLocation();
		return null;
	}
	
	public String getTimeZoneName() {
		if(timeZoneName == null)
			return "Unknown";
		return timeZoneName;
	}
	
	public String getTimeZoneOffset() {
		if(timeZoneOffset == null)
			return "0";
		return timeZoneOffset.substring(0, timeZoneOffset.indexOf(":"));
	}
	
	public String getFranchiseId() {
		return team.getFranchiseId();
	}
}
