package statsapi.teammodel;

public class TeamData {

	private String id;
	private String name;
	private TeamVenue venue;
	private String abbreviation;
	private String teamName;
	private String locationName;
	private String firstYearOfPlay;
	private Division division;
	private Conference conference;
	private Franchise franchise;
	private String shortName;
	private String officialSiteUrl;
	private String active;
	
	public String getJsonId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public TeamVenue getVenue() {
		return venue;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public String firstYearOfPlay() {
		return firstYearOfPlay;
	}
	
	public Division getDivision() {
		return division;
	}
	
	public Conference getConference() {
		return conference;
	}
	
	public Franchise getFranchise() {
		return franchise;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public String getOfficialSiteUrl() {
		return officialSiteUrl;
	}
	
	public String getActive() {
		return active;
	}
}
