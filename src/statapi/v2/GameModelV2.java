package statapi.v2;

import java.util.ArrayList;

import main.GsonWorker;
import main.LogType;
import main.LogWriter;
import statapi.v2.eventmodel.EventModelV2;
import statapi.v2.eventmodel.GoalDetail;
import statapi.v2.playermodel.PlayerModelV2;
import statapi.v2.playermodel.TeamRosters;
import statapi.v2.teammodel.TeamApiData;
import statapi.v2.utils.TeamModelStorage;

public class GameModelV2 {

	private String id;
	private String season;
	private String gameType;
	private String startTimeUTC;
	private String gameState;
	private String gameStateName;
	private String gameScheduleState;
	
	private TeamV2 awayTeam;
	private TeamV2 homeTeam;
	
	private DefaultName venue;
	private String neutralSite;
	private String venueUTCOffset;
	private String venueTimezone;
	
	private TeamApiData home;
	private TeamApiData away;
	
	private TeamRosters rosters;
	
	private PlayerModelV2 playerModel;
	
	private ArrayList<EventModelV2> events;
	private ArrayList<GoalDetail> goals;
	
	
	public void fillGameData(TeamModelStorage teamStorage) {
		apiV2Fixes();
		
		home = teamStorage.getTeamData(homeTeam.getId());
		away = teamStorage.getTeamData(awayTeam.getId());
		if(neutralSite.equals("false") && home.getVenueName().equals("Unknown")) {
			home.setVenueName(venue.getName());
			home.setTimeZoneName(venueTimezone);
			home.setTimeZoneOffset(venueUTCOffset);
		}
		
		System.out.print(".");
		
		rosters = GsonWorker.getGameRosters(id);
		playerModel = new PlayerModelV2();
		playerModel.findPlayers(rosters);
		
		System.out.print(".");
		
		events = GsonWorker.getEvents(id);
		goals = GsonWorker.getGoals(id);
		
		System.out.print(".");
	}
	
	private void apiV2Fixes() {
		//game type in db "R", "P" in api 2, 3
		if(gameType.equals("2"))
			gameType = "R";
		if(gameType.equals("3"))
			gameType = "P";
		
		//game status
		if(gameState.equals("OFF")) {
			gameState = "7";
			gameStateName = "Final";
		} else {
			LogWriter.writeLog(LogType.ERROR, "game: " + id + "has unmapped game state");
		}
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSeason() {
		return season;
	}
	
	public void setSeason(String season) {
		this.season = season;
	}
	
	public String getGameType() {
		return gameType;
	}
	
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	
	public String getStartTimeUTC() {
		return startTimeUTC;
	}
	
	public void setStartTimeUTC(String startTimeUTC) {
		this.startTimeUTC = startTimeUTC;
	}
	
	public String getGameState() {
		return gameState;
	}
	
	public void setGameState(String gameState) {
		this.gameState = gameState;
	}
	
	public String getGameStateName() {
		return gameStateName;
	}
	
	public void setGameStateName(String gameStateName) {
		this.gameStateName = gameStateName;
	}
	
	public String getGameScheduleState() {
		return gameScheduleState;
	}
	
	public void setGameScheduleState(String gameScheduleState) {
		this.gameScheduleState = gameScheduleState;
	}
	
	public TeamV2 getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(TeamV2 awayTeam) {
		this.awayTeam = awayTeam;
	}

	public TeamV2 getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(TeamV2 homeTeam) {
		this.homeTeam = homeTeam;
	}

	public DefaultName getVenue() {
		return venue;
	}
	
	public void setVenue(DefaultName venue) {
		this.venue = venue;
	}

	public String getNeutralSite() {
		return neutralSite;
	}

	public void setNeutralSite(String neutralSite) {
		this.neutralSite = neutralSite;
	}

	public String getVenueUTCOffset() {
		return venueUTCOffset;
	}

	public void setVenueUTCOffset(String venueUTCOffset) {
		this.venueUTCOffset = venueUTCOffset;
	}

	public String getVenueTimezone() {
		return venueTimezone;
	}
	
	public TeamApiData getAway() {
		return away;
	}
	
	public TeamApiData getHome() {
		return home;
	}
	
	public TeamRosters getRosters() {
		return rosters;
	}

	public void setVenueTimezone(String venueTimezone) {
		this.venueTimezone = venueTimezone;
	}
	
	public PlayerModelV2 getPlayerModel() {
		return playerModel;
	}
	
	public void setEvents(ArrayList<EventModelV2> events) {
		this.events = events;
	}
	
	public ArrayList<EventModelV2> getEvents() {
		return events;
	}
	
	public void setGoals(ArrayList<GoalDetail> goals) {
		this.goals = goals;
	}
	
	public ArrayList<GoalDetail> getGoals() {
		return goals;
	}
	
	public void printTeam(TeamApiData t) {
		System.out.println(t.getId() + ";name: " + t.getFullName() + ";abb: " + t.getAbbreviation() + ";teamName: " + t.getTeamName() 
				+ ";shortName: " + t.getShortName() + ";venueName: " + t.getVenueName() + ";venueCity: " + t.getVenueCity() 
				+ ";location: " + t.getLocation() + ";firstYear: " + t.getFirstYear() + ";tzName: " + t.getTimeZoneName() 
				+ ";tzOffset: " + t.getTimeZoneOffset());
	}
}
