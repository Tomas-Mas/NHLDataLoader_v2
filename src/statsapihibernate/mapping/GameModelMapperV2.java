package statsapihibernate.mapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import hibernate.entities.Conference;
import hibernate.entities.Division;
import hibernate.entities.Event;
import hibernate.entities.EventPlayer;
import hibernate.entities.EventPlayerPK;
import hibernate.entities.Game;
import hibernate.entities.GameEvent;
import hibernate.entities.GameStatus;
import hibernate.entities.Player;
import hibernate.entities.Position;
import hibernate.entities.Roster;
import hibernate.entities.Team;
import hibernate.entities.TimeZone;
import hibernate.entities.Venue;
import main.LogType;
import main.LogWriter;
import statapi.v2.GameModelV2;
import statapi.v2.eventmodel.EventModelV2;
import statapi.v2.eventmodel.EventNameMappingEnum;
import statapi.v2.eventmodel.GoalDetail;
import statapi.v2.eventmodel.GoalStrengthMapping;
import statapi.v2.eventmodel.PenaltyType;
import statapi.v2.playermodel.PlayerBasic;
import statapi.v2.playermodel.PlayerDetail;
import statapi.v2.playermodel.PositionEnum;
import statapi.v2.teammodel.TeamApiData;
import statapi.v2.utils.RosterStorage;

public class GameModelMapperV2 {

	public Game getMappedGame(GameModelV2 gameModel) {
		Game game = mapGame(gameModel);
		game.setHomeTeam(mapTeam(gameModel.getHome()));
		game.setAwayTeam(mapTeam(gameModel.getAway()));
		game.setVenue(mapVenue(gameModel));
		game.setGameStatus(mapGameStatus(gameModel));
		return game;
	}
	
	public Division getMappedDivision(String divisionName) {
		Division div = new Division();
		div.setName(divisionName);
		return div;
	}
	
	public Conference getMappedConference(String conferenceName) {
		Conference conf = new Conference();
		conf.setName(conferenceName);
		return conf;
	}
	
	public Player getMappedPlayer(PlayerDetail player) {
		Position position = new Position();
		PositionEnum posEnum = PositionEnum.valueOfKey(player.getPosition());
		position.setName(posEnum.getName());
		position.setType(posEnum.getType());
		position.setAbbreviation(posEnum.getAbbr());
		
		Player p = new Player();
		p.setJsonId(Integer.valueOf(player.getPlayerId()));
		p.setFirstName(player.getFirstName().getName());
		p.setLastName(player.getLastName().getName());
		if(player.getSweaterNumber() == null)
			p.setPrimaryNumber(null);
		else
			p.setPrimaryNumber(Integer.valueOf(player.getSweaterNumber()));
		p.setAge(player.getAge());
		try {
			p.setBirthDate(new SimpleDateFormat("yyyy-mm-dd").parse(player.getBirthDate()));
		} catch (Exception e) {	e.printStackTrace(); }
		p.setBirthCountry(player.getBirthCountry());
		
		Team team = new Team();
		if(player.getCurrentTeamId() != null)
			team.setJsonId(Integer.valueOf(player.getCurrentTeamId()));
		else
			team = null;
		
		p.setCurrentTeam(team);
		p.setPosition(position);
		
		return p;
	}
	
	public Roster getMappedRoster(Game game, Team team, Player player, PlayerBasic playerGameData) {
		Position position = new Position();
		PositionEnum posEnum = PositionEnum.valueOfKey(playerGameData.getPosition());
		position.setName(posEnum.getName());
		position.setType(posEnum.getType());
		position.setAbbreviation(posEnum.getAbbr());
		
		Roster roster = new Roster();
		roster.setGame(game);
		roster.setTeam(team);
		roster.setPlayer(player);
		roster.setPosition(position);
		roster.setTimeOnIce(playerGameData.getToi());
		roster.setPlusMinus(playerGameData.getPlusMinus());
		return roster;
	}
	
	public GameEvent getMappedEvent(EventModelV2 event, ArrayList<GoalDetail> goals, Game game, RosterStorage rosters) {
		GameEvent gameEventEntity = new GameEvent();
		Event eventEntity = new Event();
		gameEventEntity.setPlayers(new ArrayList<EventPlayer>());
		
		//event name
		EventNameMappingEnum eventNameEnum = EventNameMappingEnum.valueOfKey(event.getTypeDescKey());
		
		//print event data
		/*String test = eventNameEnum.getName();
		if(test.equals("Period End")) {
			if(event.getDetails() != null)
				test += "; code: " + event.getSituationCode() + ";gameId - " + game.getJsonId() + "; details: " + event.detailsToString();
			System.out.println(test);
		}*/
		
		eventEntity.setName(eventNameEnum.getName());
		gameEventEntity.setJsonId(Integer.valueOf(event.getEventId()));
		gameEventEntity.setPeriodNumber(event.getPeriodDescriptor().getNumber());
		gameEventEntity.setPeriodType(event.getPeriodDescriptor().getPeriodType());
		gameEventEntity.setPeriodTime(event.getTimeInPeriod());
		gameEventEntity.setEvent(eventEntity);
		gameEventEntity.setGame(game);
		
		switch(eventNameEnum) {
		case FACEOFF:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("winningPlayerId")), "Winner");
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("losingPlayerId")), "Loser");
			/*System.out.println("size of players array: " + gameEventEntity.getPlayers().size() + "playerData: " + 
					gameEventEntity.getPlayers().get(0).getId().getRoster().getPlayer().getJsonId() + " - " + gameEventEntity.getPlayers().get(0).getRole());*/
			break;
		case STOPPAGE:
			eventEntity.setSecondaryType(formatString(event.getDetails().get("reason").toString()));
			//System.out.println(eventEntity.getName() + " - " + eventEntity.getSecondaryType());
			break;
		case HIT:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("hittingPlayerId")), "Hitter");
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("hitteePlayerId")), "Hittee");
			/*System.out.println("size of players array: " + gameEventEntity.getPlayers().size() + "playerData: " + 
					gameEventEntity.getPlayers().get(0).getId().getRoster().getPlayer().getJsonId() + " - " + gameEventEntity.getPlayers().get(0).getRole());*/
			break;
		case MISSEDSHOT:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			if(event.getDetails().get("reason") != null)
				eventEntity.setSecondaryType(formatString(event.getDetails().get("reason").toString()));
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("shootingPlayerId")), "Shooter");
			if(event.getDetails().get("goalieInNetId") != null)
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("goalieInNetId")), "Goalie");
			/*System.out.println("size of players array: " + gameEventEntity.getPlayers().size() + "playerData: " + 
					gameEventEntity.getPlayers().get(1).getId().getRoster().getPlayer().getJsonId() + " - " + gameEventEntity.getPlayers().get(1).getRole());*/
			break;
		case SHOTONGOAL:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			if(event.getDetails().get("shotType") == null)
				eventEntity.setSecondaryType("Undefined");
			else
				eventEntity.setSecondaryType(formatString(event.getDetails().get("shotType").toString()));
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("shootingPlayerId")), "Shooter");
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("goalieInNetId")), "Goalie");
			//System.out.println("size of players array: " + gameEventEntity.getPlayers().size() + "coord: " + gameEventEntity.getCoordX() + "x" + gameEventEntity.getCoordY());
			break;
		case BLOCKEDSHOT:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("shootingPlayerId")), "Shooter");
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("blockingPlayerId")), "Blocker");
			break;
		case GIVEAWAY:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("playerId")), null);
			break;
		case TAKEAWAY:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("playerId")), null);
			break;
		case GOAL:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			eventEntity.setSecondaryType(event.getDetails().get("shotType").toString());
			
			GoalDetail goalDet = null;
			for(GoalDetail g : goals) {
				if(g.getPeriodNumber() == event.getPeriodDescriptor().getNumber() && g.getPeriodTime().equals(event.getTimeInPeriod())) {
					goalDet = g;
				}
			}
			//goalDet.getGoalModifier() has own goal option is it ruining stats?
			
			if(goalDet == null && event.getPeriodDescriptor().getPeriodType().equals("SO"))
				goalDet = new GoalDetail(0, "", "ev", "none");
			if(goalDet == null)
				LogWriter.writeLog(LogType.ERROR, "not paired goals in game: " + game.getJsonId() + " - period type: " + event.getPeriodDescriptor().getPeriodType());
			
			eventEntity.setStrength(GoalStrengthMapping.valueOfKey(goalDet.getStrength()).getName());
			if(goalDet.getGoalModifier().equals("empty-net") || goalDet.getGoalModifier().equals("own-goal-empty-net") || goalDet.getGoalModifier().equals("awarded-empty-net")) {
				eventEntity.setEmptyNet("true");
			} else {
				eventEntity.setEmptyNet("false");
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("goalieInNetId")), "Goalie");
			}
			
			addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("scoringPlayerId")), "Scorer");
			if(event.getDetails().get("assist1PlayerId") != null)
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("assist1PlayerId")), "Assist");
			if(event.getDetails().get("assist2PlayerId") != null)
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("assist2PlayerId")), "Assist");
			if(event.getDetails().get("assist3PlayerId") != null)
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("assist3PlayerId")), "Assist");
			/*System.out.println(gameEventEntity.getCoordX() + "x" + gameEventEntity.getCoordY() + " - " + eventEntity.getName() + " - " + eventEntity.getSecondaryType() + " - " + 
					eventEntity.getStrength() + " - players: " + gameEventEntity.getPlayers().size());*/
			break;
		case PENALTY:
			gameEventEntity.setCoordX(objToInt(event.getDetails().get("xCoord")));
			gameEventEntity.setCoordY(objToInt(event.getDetails().get("yCoord")));
			eventEntity.setSecondaryType(formatString(event.getDetails().get("descKey").toString()));
			eventEntity.setPenaltySeverity(PenaltyType.valueOfKey(event.getDetails().get("typeCode").toString(), game.getJsonId()).getName());
			eventEntity.setPenaltyMinutes(objToInt(event.getDetails().get("duration")));
			if(event.getDetails().get("drawnByPlayerId") != null)
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("drawnByPlayerId")), "DrewBy");
			if(event.getDetails().get("committedByPlayerId") != null)
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("committedByPlayerId")), "PenaltyOn");
			if(event.getDetails().get("servedByPlayerId") != null)
				addPlayerToEvent(gameEventEntity, rosters, objToInt(event.getDetails().get("servedByPlayerId")), "ServedBy");
			/*System.out.println(eventEntity.getName() + " - " + eventEntity.getSecondaryType() + " - " + eventEntity.getPenaltySeverity() + " - " + eventEntity.getPenaltyMinutes());
			for(EventPlayer p : gameEventEntity.getPlayers()) {
				System.out.println(p.getId().getRoster().getPlayer().getJsonId() + " - " + p.getRole());
			}*/
			break;
		default:
			;
		}
		
		return gameEventEntity;
	}
	
	private void addPlayerToEvent(GameEvent ge, RosterStorage rosters, int playerId, String role) {
		List<EventPlayer> players = ge.getPlayers();
		
		EventPlayerPK playerPK = new EventPlayerPK();
		playerPK.setEvent(ge);
		playerPK.setRoster(rosters.getByPlayerId(playerId));
		
		EventPlayer player = new EventPlayer();
		player.setId(playerPK);
		player.setRole(role);
		
		players.add(player);
	}
	
	private int objToInt(Object o) {
		if(o == null)
			return 0;
		
		return (int) Float.parseFloat(o.toString());
	}
	
	private String formatString(String s) {
		s = s.substring(0, 1).toUpperCase() + s.substring(1);
		s = s.replace("-", " ");
		return s;
	}
	
	private Team mapTeam(TeamApiData teamModel) {
		Team team = new Team();
		team.setJsonId(Integer.valueOf(teamModel.getId()));
		team.setName(teamModel.getFullName());
		team.setAbbreviation(teamModel.getAbbreviation());
		team.setTeamName(teamModel.getTeamName());
		team.setShortName(teamModel.getShortName());
		team.setLocation(teamModel.getLocation());
		if(teamModel.getFirstYear() != null)
			team.setFirstYear(Integer.valueOf(teamModel.getFirstYear()));
		
		Venue venue = new Venue();
		venue.setName(teamModel.getVenueName());
		venue.setCity(teamModel.getVenueCity());
		
		TimeZone timeZone = new TimeZone();
		timeZone.setName(teamModel.getTimeZoneName());
		timeZone.setOffset(Integer.valueOf(teamModel.getTimeZoneOffset()));
		
		team.setVenue(venue);
		team.setTimeZone(timeZone);
		return team;
	}
	
	private Game mapGame(GameModelV2 gameModel) {
		Game game = new Game();
		game.setJsonId(Integer.valueOf(gameModel.getId()));
		game.setGameType(gameModel.getGameType());
		game.setSeason(Integer.valueOf(gameModel.getSeason()));
		game.setGameDate(new Timestamp(Instant.parse(gameModel.getStartTimeUTC()).toEpochMilli()));
		game.setAwayScore(Integer.valueOf(gameModel.getAwayTeam().getScore()));
		game.setHomeScore(Integer.valueOf(gameModel.getHomeTeam().getScore()));
		return game;
	}
	
	private Venue mapVenue(GameModelV2 gameModel) {
		Venue venue = new Venue();
		venue.setName(gameModel.getVenue().getName());
		venue.setCity(null);
		return venue;
	}
	
	private GameStatus mapGameStatus(GameModelV2 gameModel) {
		GameStatus status = new GameStatus();
		status.setCode(Integer.valueOf(gameModel.getGameState()));
		status.setName(gameModel.getGameStateName());
		return status;
	}
}
