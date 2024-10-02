package main;

import java.util.ArrayList;

import hibernate.entities.Game;
import hibernate.entities.Player;
import hibernate.service.DivisionConferenceService;
import hibernate.service.GameEventService;
import hibernate.service.GameService;
import hibernate.service.PlayerService;
import hibernate.service.RosterService;
import hibernate.service.TeamService;
import statapi.v2.GameModelV2;
import statapi.v2.eventmodel.EventModelV2;
import statapi.v2.playermodel.PlayerBasic;
import statapi.v2.playermodel.PlayerDetail;
import statapi.v2.utils.GameEventStorage;
import statapi.v2.utils.RosterStorage;
import statsapihibernate.mapping.GameModelMapperV2;

public class GameController {

	private GameModelMapperV2 mapper;
	private GameService gameService;
	private TeamService teamService;
	private DivisionConferenceService divConfService;
	private PlayerService playerService;
	private RosterService rosterService;
	private GameEventService eventService;
	
	private RosterStorage rosterStorage;
	private GameEventStorage gameEventStorage;
	
	public GameController() {
		this.mapper = new GameModelMapperV2();
		this.gameService = new GameService();
		this.teamService = new TeamService();
		this.divConfService = new DivisionConferenceService();
		this.playerService = new PlayerService();
		this.rosterService = new RosterService();
		this.eventService = new GameEventService();
		
		this.rosterStorage = new RosterStorage();
		this.gameEventStorage = new GameEventStorage();
	}
	
	public void saveGame(GameModelV2 gameModel) {
		Game game = mapper.getMappedGame(gameModel);
		
		try {
			if(gameEventStorage.areEventsEmpty()) {
				gameEventStorage.loadEvents(eventService.selectAllEvents());
			}
			
			game.setAwayTeam(teamService.save(game.getAwayTeam()));
			game.setHomeTeam(teamService.save(game.getHomeTeam()));
			
			divConfService.save(game.getAwayTeam(), Integer.valueOf(game.getSeason()), 
					mapper.getMappedDivision(gameModel.getAway().getDivision()), mapper.getMappedConference(gameModel.getAway().getConference()));
			divConfService.save(game.getHomeTeam(), Integer.valueOf(game.getSeason()), 
					mapper.getMappedDivision(gameModel.getHome().getDivision()), mapper.getMappedConference(gameModel.getHome().getConference()));
			
			System.out.print(".");
			
			Game persistedGame = gameService.save(game);
			
			System.out.print(".");
			
			Player player = new Player();
			for(PlayerDetail p : gameModel.getPlayerModel().getHomePlayers()) {
				player = playerService.savePlayer(mapper.getMappedPlayer(p), persistedGame.getHomeTeam());
				rosterStorage.addRoster(
						rosterService.save(
								mapper.getMappedRoster(persistedGame, persistedGame.getHomeTeam(), player, findPlayerGameInfo(player, gameModel.getRosters().getHome()))));
			}
			for(PlayerDetail p : gameModel.getPlayerModel().getAwayPlayers()) {
				player = playerService.savePlayer(mapper.getMappedPlayer(p), persistedGame.getAwayTeam());
				rosterStorage.addRoster(
						rosterService.save(
								mapper.getMappedRoster(persistedGame, persistedGame.getAwayTeam(), player, findPlayerGameInfo(player, gameModel.getRosters().getAway()))));
			}
			
			System.out.print(".");
			
			gameEventStorage.setPlayers(eventService.selectAllEventPlayersByGameId(persistedGame.getId()));
			gameEventStorage.setGameEvents(eventService.selectAllGameEventsByGame(persistedGame));
			
			for(EventModelV2 e : gameModel.getEvents()) {
				eventService.saveEvent(mapper.getMappedEvent(e, gameModel.getGoals(), persistedGame, rosterStorage), gameEventStorage);
			}
			
			System.out.print(".");
			
			rosterStorage.reset();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			LogWriter.writeLog(LogType.ERROR, gameModel.getId() + " game has some oopsie\n" + e.getMessage());
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeSession();
		}
	}
	
	private PlayerBasic findPlayerGameInfo(Player player, ArrayList<PlayerBasic> rosters) {
		for(PlayerBasic p : rosters) {
			if(player.getJsonId() == Integer.valueOf(p.getPlayerId()))
				return p;
		}
		LogWriter.writeLog(LogType.ERROR, "player not found in the roster");
		return null;
	}
	
	/*public void saveGame(GameModel gameModel) {
		Game game = mapper.getMappedGame(gameModel);
		try {
			Team persistedAwayTeam = teamService.save(game.getAwayTeam());
			Team persistedHomeTeam = teamService.save(game.getHomeTeam());
			game.setAwayTeam(persistedAwayTeam);
			game.setHomeTeam(persistedHomeTeam);
			System.out.print(".");
			
			divConfService.save(game.getAwayTeam(), Integer.valueOf(gameModel.getSeason()), 
					mapper.mapDivision(gameModel.getAwayTeam()), mapper.mapConference(gameModel.getAwayTeam()));
			divConfService.save(game.getHomeTeam(), Integer.valueOf(gameModel.getSeason()), 
					mapper.mapDivision(gameModel.getHomeTeam()), mapper.mapConference(gameModel.getHomeTeam()));
			System.out.print(".");
			
			Game persistedGame = gameService.save(game);
			System.out.print(".");
			
			playerService.save(mapper, gameModel, mapper.getMappedAwayPlayerList(gameModel), persistedAwayTeam, persistedGame);
			playerService.save(mapper, gameModel, mapper.getMappedHomePlayerList(gameModel), persistedHomeTeam, persistedGame);
			System.out.print(".");
			
			eventService.saveEvents(persistedGame, game.getEvents());
			System.out.print(".");
			
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			LogWriter.writeLog(LogType.ERROR, gameModel.getGamePk() + " game has some oopsie\n" + e.getMessage());
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeSession();
		}
	}*/
}
