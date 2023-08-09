package statsapi.gamemodel;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import main.GsonWorker;
import main.LogType;
import main.LogWriter;
import statsapi.eventmodel.Event;
import statsapi.eventmodel.EventModel;
import statsapi.eventmodel.EventPlayer;
import statsapi.playermodel.Player;
import statsapi.playermodel.PlayerModel;
import statsapi.playerstatsmodel.PlayerStats;
import statsapi.teammodel.TeamData;
import statsapi.teammodel.TeamModel;

public class GameModel {
	
	private String gamePk;
	private String gameType;
	private String season;
	private String gameDate;
	private Status status;
	private PlayingTeams teams;
	private Venue venue;
	
	private TeamModel awayTeam;
	private TeamModel homeTeam;
	
	private PlayerModel playerModel = new PlayerModel();
	private EventModel eventModel;
	private ArrayList<PlayerStats> playerStats;
	
	public String getGamePk() {
		return gamePk;
	}
	
	public String getGameType() {
		return gameType;
	}
	
	public String getSeason() {
		return season;
	}
	
	public String getGameDate() {
		return gameDate;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Venue getVenue() {
		return venue;
	}
	
	public String getAwayScore() {
		return teams.getAwayTeam().getScore();
	}
	
	public String getHomeScore() {
		return teams.getHomeTeam().getScore();
	}
	
	public TeamData getAwayTeam() {
		return awayTeam.getTeamData();
	}
	
	public TeamData getHomeTeam() {
		return homeTeam.getTeamData();
	}
	
	public PlayerModel getPlayerModel() {
		return playerModel;
	}
	
	public EventModel getEventModel() {
		return eventModel;
	}
	
	public ArrayList<PlayerStats> getPlayerStats() {
		return playerStats;
	}
	
	public void fillGameDetails() {
		try {
			System.out.print(" .");
			JSONObject liveData = new JSONObject();
			liveData = GsonWorker.getJsonObjectWithGameDetail(gamePk);
			System.out.print(".");
			
			awayTeam = GsonWorker.getTeamModelById(teams.getAwayTeam().getTeamDetail().getJsonId());
			homeTeam = GsonWorker.getTeamModelById(teams.getHomeTeam().getTeamDetail().getJsonId());
			System.out.print(".");
			
			playerModel.findPlayers(gamePk, liveData);
			System.out.print(".");
			
			eventModel = GsonWorker.getEventsData(liveData);
			System.out.print(".");
			
			playerStats = GsonWorker.getPlayerStats(liveData);
			System.out.print(".");
		} catch(Exception e) {
			String dateTime = new Date().toString();
			String message = dateTime + "; game id: " + this.gamePk + "; error: " + e.getMessage();
			LogWriter.writeLog(LogType.ERROR, message);
		}
	}

	public void print() {
		System.out.println("gameData");
		System.out.println("gamePk: " + gamePk + ", gameType: " + gameType + ", season: " + season + ", gameDate: " + gameDate + 
				", statusCode: " + status.getStatusCode() + ", detailedState: " + status.getDetailedState() + 
				", venueJsonId: " + venue.getJsonId() + ", venueName: " + venue.getName());
		System.out.println("playing teams");
		System.out.println("awayId: " + teams.getAwayTeam().getTeamDetail().getJsonId() + ", name: " + teams.getAwayTeam().getTeamDetail().getName() + 
				", score: " + teams.getAwayTeam().getScore() + ", leagueRecordType: " + teams.getAwayTeam().getLeagueRecord().getType());
		System.out.println("homeId: " + teams.getHomeTeam().getTeamDetail().getJsonId() + ", name: " + teams.getHomeTeam().getTeamDetail().getName() + 
				", score: " + teams.getHomeTeam().getScore() + ", leagueRecordType: " + teams.getHomeTeam().getLeagueRecord().getType());
		
		System.out.println("\nAway team model");
		System.out.println("jsonId: " + getAwayTeam().getJsonId() + ", name: " + getAwayTeam().getName() + ", teamName: " + getAwayTeam().getTeamName() + 
				", abbreviation: " + getAwayTeam().getAbbreviation() + ", shortName: " + getAwayTeam().getShortName() + ", active: " + getAwayTeam().getActive() +
				", venueName: " + getAwayTeam().getVenue().getName() + ", city: " + getAwayTeam().getVenue().getCity() + 
				", offset: " + getAwayTeam().getVenue().getTimeZone().getOffset() + 
				", divisionId: " + getAwayTeam().getDivision().getJsonId() + ", divisionName: " + getAwayTeam().getDivision().getName() + 
				", conferenceId: " + getAwayTeam().getConference().getJsonId() + ", conferenceName: " + getAwayTeam().getConference().getName() + 
				", franchiseName: " + getAwayTeam().getFranchise().getName());
		System.out.println("Home team model");
		System.out.println("jsonId: " + getHomeTeam().getJsonId() + ", name: " + getHomeTeam().getName() + ", teamName: " + getHomeTeam().getTeamName() + 
				", abbreviation: " + getHomeTeam().getAbbreviation() + ", shortName: " + getHomeTeam().getShortName() + ", active: " + getHomeTeam().getActive() +
				", venueName: " + getHomeTeam().getVenue().getName() + ", city: " + getHomeTeam().getVenue().getCity() + 
				", offset: " + getHomeTeam().getVenue().getTimeZone().getOffset() +
				", divisionId: " + getHomeTeam().getDivision().getJsonId() + ", divisionName: " + getHomeTeam().getDivision().getName() + 
				", conferenceId: " + getHomeTeam().getConference().getJsonId() + ", conferenceName: " + getHomeTeam().getConference().getName() + 
				", franchiseName: " + getHomeTeam().getFranchise().getName());
		
		System.out.println("\nplayer model");
		System.out.println("away player name: " + playerModel.getAwayPlayers().get(5).getPlayerData().getFirstName() + 
				" " + playerModel.getAwayPlayers().get(5).getPlayerData().getLastName() +
				", team: " + playerModel.getAwayPlayers().get(5).getPlayerData().getCurrentTeam().getName() + 
				", position: " + playerModel.getAwayPlayers().get(5).getPlayerData().getPosition().getName() + 
				" " + playerModel.getAwayPlayers().get(5).getPlayerData().getPosition().getType());
		System.out.println("home player name: " + playerModel.getHomePlayers().get(4).getPlayerData().getFirstName() + 
				" " + playerModel.getHomePlayers().get(4).getPlayerData().getLastName() +
				", team: " + playerModel.getHomePlayers().get(4).getPlayerData().getCurrentTeam().getName() + 
				", position: " + playerModel.getHomePlayers().get(4).getPlayerData().getPosition().getName() + 
				" " + playerModel.getHomePlayers().get(4).getPlayerData().getPosition().getType());
		
		System.out.println("\neventModel");
		System.out.println("player: " + eventModel.getEvents().get(46).getPlayers().get(0).getPlayerData().getFullName() + 
				", type: " + eventModel.getEvents().get(46).getPlayers().get(0).getPlayerType() +
				", event name: " + eventModel.getEvents().get(46).getResult().getEventName());
		
		System.out.println("\nplayer stats");
		System.out.println("player stats id: " + playerStats.get(4).getJsonId());
		
		System.out.println("event players");
		for(Event event : eventModel.getEvents()) {
			for(EventPlayer player : event.getPlayers()) {
				System.out.println("player: " + player.getPlayerData().getFullName() + " id: " + player.getPlayerData().getJsonId() + " - type: " + player.getPlayerData());
			}
		}
		
		for(Player player: playerModel.getAwayPlayers()) {
			System.out.println(player.getPlayerData().getPosition().getName() + " - " + player.getPlayerData().getPosition().getType());
			System.out.println(player.getPlayerData().getBirthDate());
		}
	}
	
	public void printPlayerStats() {
		for(PlayerStats ps : playerStats) {
			if(ps.getJsonId() == 8474145) {
				System.out.println("goalie: " + ps.getGoalieStats().getPim() + "skater: " + ps.getSkaterStats().getTimeOnIce());
			}
		}
	}
	
	public PlayerStats getPlayerStats(int jsonId) {
		for(PlayerStats stats : playerStats) {
			if(stats.getJsonId() == jsonId)
				return stats;
		}
		return null;
	}
}
