package statsapihibernate.mapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

import hibernate.entities.Conference;
import hibernate.entities.Division;
import hibernate.entities.Event;
import hibernate.entities.EventPlayer;
import hibernate.entities.EventPlayerPK;
import hibernate.entities.Game;
import hibernate.entities.GameEvent;
import hibernate.entities.GameStatus;
import hibernate.entities.GoalieStats;
import hibernate.entities.Player;
import hibernate.entities.Position;
import hibernate.entities.Roster;
import hibernate.entities.SkaterStats;
import hibernate.entities.Team;
import hibernate.entities.TimeZone;
import hibernate.entities.Venue;
import main.LogType;
import main.LogWriter;
import statsapi.gamemodel.GameModel;
import statsapi.playerstatsmodel.PlayerStats;
import statsapi.teammodel.TeamData;
import statsapi.teammodel.TeamVenue;

public class GameModelMapper {

	public Game getMappedGame(GameModel gameModel) {
		
		Game game = mapGame(gameModel);
		GameStatus status = mapStatus(gameModel);
		Team awayTeam = mapAwayTeam(gameModel);
		Team homeTeam = mapHomeTeam(gameModel);
		
		Venue homeVenue = mapHomeVenue(gameModel);
		Venue awayVenue = mapAwayVenue(gameModel);
		Venue gameVenue = mapGameVenue(gameModel);
		
		
		awayTeam.setVenue(awayVenue);
		awayTeam.setTimeZone(mapTimeZone(gameModel.getAwayTeam().getVenue()));
		
		homeTeam.setVenue(homeVenue);
		homeTeam.setTimeZone(mapTimeZone(gameModel.getHomeTeam().getVenue()));
		
		game.setEvents(getMappedGameEvents(gameModel));
		
		game.setVenue(gameVenue);
		game.setGameStatus(status);
		game.setAwayTeam(awayTeam);
		game.setHomeTeam(homeTeam);
		
		
		return game;
	}
	
	public ArrayList<Player> getMappedAwayPlayerList(GameModel gameModel) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(statsapi.playermodel.Player p : gameModel.getPlayerModel().getAwayPlayers()) {
			players.add(mapPlayer(gameModel.getGamePk(), p));
		}
		return players;
	}
	
	public ArrayList<Player> getMappedHomePlayerList(GameModel gameModel) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(statsapi.playermodel.Player p : gameModel.getPlayerModel().getHomePlayers()) {
			players.add(mapPlayer(gameModel.getGamePk(), p));
		}
		return players;
	}
	
	public SkaterStats mapPlayerStats(PlayerStats stats) {
		SkaterStats skaterStats = new SkaterStats();
		if(stats.getSkaterStats() != null) {
			skaterStats.setTimeOnIce(stats.getSkaterStats().getTimeOnIce());
			skaterStats.setPowerPlayTimeOnIce(stats.getSkaterStats().getPowerPlayTimeOnIce());
			skaterStats.setShortHandedTimeOnIce(stats.getSkaterStats().getShortHandedTimeOnIce());
			skaterStats.setPenaltyMinutes(stats.getSkaterStats().getPenaltyMinutes());
			skaterStats.setPlusMinus(stats.getSkaterStats().getPlusMinus());
		} else {
			skaterStats = null;
		}
		return skaterStats;
	}
	
	public GoalieStats mapGoalieStats(PlayerStats stats) {
		GoalieStats goalieStats = new GoalieStats();
		if(stats.getGoalieStats() != null) {
			if(stats.getGoalieStats().getTimeOnIce() == null)
				goalieStats.setTimeOnIce("00:00");
			else
				goalieStats.setTimeOnIce(stats.getGoalieStats().getTimeOnIce());
			
			goalieStats.setPenaltyMinutes(Integer.valueOf(stats.getGoalieStats().getPim()));
			goalieStats.setShots(stats.getGoalieStats().getShots());
			goalieStats.setSaves(stats.getGoalieStats().getSaves());
			goalieStats.setPowerPlayShots(stats.getGoalieStats().getPowerPlayShotsAgainst());
			goalieStats.setPowerPlaySaves(stats.getGoalieStats().getPowerPlaySaves());
			goalieStats.setShortHandedShots(stats.getGoalieStats().getShortHandedShotsAgainst());
			goalieStats.setShortHandedSaves(stats.getGoalieStats().getShortHandedSaves());
		} else {
			goalieStats = null;
		}
		return goalieStats;
	}
	
	private Player mapPlayer(String gameId, statsapi.playermodel.Player p) {
		Position position = new Position();
		position.setName(p.getPlayerData().getPosition().getName());
		position.setType(p.getPlayerData().getPosition().getType());
		position.setAbbreviation(p.getPlayerData().getPosition().getAbbreviation());
		
		Player player = new Player();
		player.setJsonId(Integer.valueOf(p.getPlayerData().getJsonId()));
		player.setFirstName(p.getPlayerData().getFirstName());
		player.setLastName(p.getPlayerData().getLastName());
		
		if(p.getPlayerData().getPrimaryNumber() != null) {
			player.setPrimaryNumber(Integer.valueOf(p.getPlayerData().getPrimaryNumber()));
		} else {
			player.setPrimaryNumber(null);
		}
		try {
			player.setBirthDate(new SimpleDateFormat("yyyy-mm-dd").parse(p.getPlayerData().getBirthDate()));
		} catch (Exception e) {
			LogWriter.writeLog(LogType.ERROR, "game: " + gameId + " " + p.getPlayerData().getJsonId() + ": birth date format error");
		}
		player.setBirthCountry(p.getPlayerData().getBirthCountry());
		
		Team team = new Team();
		if(p.getPlayerData().getCurrentTeam() != null)
			team.setJsonId(Integer.valueOf(p.getPlayerData().getCurrentTeam().getJsonId()));
		else
			team = null;
		
		player.setCurrentTeam(team);
		player.setPosition(position);
		return player;
	}
	
	private ArrayList<GameEvent> getMappedGameEvents(GameModel gameModel) {
		ArrayList<GameEvent> events = new ArrayList<GameEvent>();
		for(statsapi.eventmodel.Event e : gameModel.getEventModel().getEvents()) {
			Event event = new Event();
			if(e.getResult().getEventName() != null)
				event.setName(e.getResult().getEventName());
			if(e.getResult().getSecondaryType() != null)
				event.setSecondaryType(e.getResult().getSecondaryType());
			if(e.getResult().getStrength() != null && e.getResult().getStrength().getName() != null)
				event.setStrength(e.getResult().getStrength().getName());
			if(e.getResult().getEmptyNet() != null)
				event.setEmptyNet(e.getResult().getEmptyNet());
			if(e.getResult().getPenaltySeverity() != null)
				event.setPenaltySeverity(e.getResult().getPenaltySeverity());
			if(e.getResult().getPenaltyMinutes() != null)
				event.setPenaltyMinutes(Integer.valueOf(e.getResult().getPenaltyMinutes()));
			
			GameEvent gameEvent = new GameEvent();
			gameEvent.setJsonId(Integer.valueOf(e.getAbout().getEventId()));
			gameEvent.setEvent(event);
			gameEvent.setPeriodNumber(Integer.valueOf(e.getAbout().getPeriod()));
			gameEvent.setPeriodType(e.getAbout().getPeriodType());
			gameEvent.setPeriodTime(e.getAbout().getPeriodTime());
			if(e.getCoordinates() != null) {
				if(e.getCoordinates().getX() != null)
					gameEvent.setCoordX(Integer.valueOf(e.getCoordinates().getX()));
				if(e.getCoordinates().getY() != null)
					gameEvent.setCoordY(Integer.valueOf(e.getCoordinates().getY()));
			}
			
			gameEvent.setPlayers(new ArrayList<EventPlayer>());
			
			for(statsapi.eventmodel.EventPlayer eplayer : e.getPlayers()) {
				EventPlayer eventPlayer = new EventPlayer();
				eventPlayer.setRole(eplayer.getPlayerType());
				Player p = new Player();
				p.setJsonId(Integer.valueOf(eplayer.getPlayerData().getJsonId()));
				hibernate.entities.Roster r = new Roster();
				r.setPlayer(p);
				hibernate.entities.EventPlayerPK id = new EventPlayerPK();
				id.setRoster(r);
				eventPlayer.setId(id);
				gameEvent.getPlayers().add(eventPlayer);
			}
			events.add(gameEvent);
		}
		return events;
	}
	
	private Game mapGame(GameModel gameModel) {
		Game game = new Game();
		game.setJsonId(Integer.valueOf(gameModel.getGamePk()));
		game.setGameType(gameModel.getGameType());
		game.setSeason(Integer.valueOf(gameModel.getSeason()));
		game.setGameDate(new Timestamp(Instant.parse(gameModel.getGameDate()).toEpochMilli()));
		game.setAwayScore(Integer.valueOf(gameModel.getAwayScore()));
		game.setHomeScore(Integer.valueOf(gameModel.getHomeScore()));
		return game;
	}

	private GameStatus mapStatus(GameModel gameModel) {
		GameStatus status = new GameStatus();
		status.setCode(Integer.valueOf(gameModel.getStatus().getStatusCode()));
		status.setName(gameModel.getStatus().getDetailedState());
		return status;
	}
	
	private Team mapAwayTeam(GameModel gameModel) {
		Team awayTeam = new Team();
		awayTeam.setJsonId(Integer.valueOf(gameModel.getAwayTeam().getJsonId()));
		awayTeam.setName(gameModel.getAwayTeam().getName());
		awayTeam.setAbbreviation(gameModel.getAwayTeam().getAbbreviation());
		awayTeam.setTeamName(gameModel.getAwayTeam().getTeamName());
		awayTeam.setShortName(gameModel.getAwayTeam().getShortName());
		awayTeam.setLocation(gameModel.getAwayTeam().getLocationName());
		awayTeam.setFirstYear(Integer.valueOf(gameModel.getAwayTeam().firstYearOfPlay()));
		awayTeam.setActive(gameModel.getAwayTeam().getActive());
		return awayTeam;
	}
	
	private Team mapHomeTeam(GameModel gameModel) {
		Team homeTeam = new Team();
		homeTeam.setJsonId(Integer.valueOf(gameModel.getHomeTeam().getJsonId()));
		homeTeam.setName(gameModel.getHomeTeam().getName());
		homeTeam.setAbbreviation(gameModel.getHomeTeam().getAbbreviation());
		homeTeam.setTeamName(gameModel.getHomeTeam().getTeamName());
		homeTeam.setShortName(gameModel.getHomeTeam().getShortName());
		homeTeam.setLocation(gameModel.getHomeTeam().getLocationName());
		homeTeam.setFirstYear(Integer.valueOf(gameModel.getHomeTeam().firstYearOfPlay()));
		homeTeam.setActive(gameModel.getHomeTeam().getActive());
		return homeTeam;
	}
	
	private Venue mapHomeVenue(GameModel gameModel) {
		Venue venue = new Venue();
		venue.setCity(gameModel.getHomeTeam().getVenue().getCity());
		venue.setName(gameModel.getHomeTeam().getVenue().getName());
		return venue;
	}
	
	private Venue mapAwayVenue(GameModel gameModel) {
		Venue venue = new Venue();
		venue.setCity(gameModel.getAwayTeam().getVenue().getCity());
		venue.setName(gameModel.getAwayTeam().getVenue().getName());
		return venue;
	}
	
	private Venue mapGameVenue(GameModel gameModel) {
		if(gameModel.getVenue().getName().equals(gameModel.getHomeTeam().getVenue().getName()))
			return mapHomeVenue(gameModel);
		
		Venue venue = new Venue();
		venue.setName(gameModel.getVenue().getName());
		venue.setCity(null);
		return venue;
	}
	
	private TimeZone mapTimeZone(TeamVenue venue) {
		TimeZone timeZone = new TimeZone();
		timeZone.setName(venue.getTimeZone().getId());
		timeZone.setOffset(Integer.valueOf(venue.getTimeZone().getOffset()));
		return timeZone;
	}
	
	public Division mapDivision(TeamData team) {
		Division division = new Division();
		division.setJsonId(Integer.valueOf(team.getDivision().getJsonId()));
		division.setName(team.getDivision().getName());
		return division;
	}
	
	public Conference mapConference(TeamData team) {
		Conference conference = new Conference();
		conference.setJsonId(Integer.valueOf(team.getConference().getJsonId()));
		conference.setName(team.getConference().getName());
		return conference;
	}
}