package main;

import hibernate.entities.Game;
import hibernate.entities.Team;
import hibernate.service.DivisionConferenceService;
import hibernate.service.GameEventService;
import hibernate.service.GameService;
import hibernate.service.PlayerService;
import hibernate.service.TeamService;
import statsapi.gamemodel.GameModel;
import statsapihibernate.mapping.GameModelMapper;

public class GameController {

	private GameModelMapper mapper;
	private GameService gameService;
	private TeamService teamService;
	private DivisionConferenceService divConfService;
	private PlayerService playerService;
	private GameEventService eventService;
	
	public GameController() {
		this.mapper = new GameModelMapper();
		this.gameService = new GameService();
		this.teamService = new TeamService();
		this.divConfService = new DivisionConferenceService();
		this.playerService = new PlayerService();
		this.eventService = new GameEventService();
	}
	
	public void saveGame(GameModel gameModel) {
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
	}
}
