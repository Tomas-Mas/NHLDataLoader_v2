package hibernate.service;

import hibernate.dao.GameDAO;
import hibernate.dao.GameDAOImpl;
import hibernate.dao.GameStatusDAO;
import hibernate.dao.GameStatusDAOImpl;
import hibernate.dao.VenueDAO;
import hibernate.dao.VenueDAOImpl;
import hibernate.entities.Game;
import hibernate.entities.GameStatus;
import hibernate.entities.Venue;
import main.LogType;
import main.LogWriter;

public class GameService {

	private GameDAO gameDAO;
	private VenueDAO venueDAO;
	private GameStatusDAO statusDAO;
	
	public GameService() {
		this.gameDAO = new GameDAOImpl();
		this.venueDAO = new VenueDAOImpl();
		this.statusDAO = new GameStatusDAOImpl();
	}
	
	public Game save(Game game) {
		GameStatus statusApi = game.getGameStatus();
		GameStatus statusDb = statusDAO.selectById(statusApi.getCode());
		if(statusDb == null) {
			statusDAO.insert(statusApi);
		} else {
			//status options should never change so only make sure it didnt here
			statusDifferenceExists(statusDb, statusApi);
			game.setGameStatus(statusDb);
		}
		
		Venue venueApi = game.getVenue();
		Venue venueDb = venueDAO.selectByValues(venueApi.getName(), venueApi.getCity());
		if(venueDb == null) {
			venueDAO.insert(venueApi);
		} else {
			if(venueDifferenceExists(venueDb, venueApi)) {
				venueDb = venueApi;
				venueDAO.update(venueDb);
			}
			game.setVenue(venueDb);
		}
		
		Game gameDb = gameDAO.selectByJsonId(game.getJsonId());
		if(gameDb == null) {
			gameDAO.insert(game);
		} else {
			if(gameDifferenceExists(gameDb, game)) {
				gameDAO.update(gameDb);
			}
			game = gameDb;
		}
		return game;
	}
	
	private boolean statusDifferenceExists(GameStatus statusDb, GameStatus statusApi) {
		if(statusDb.getCode() != statusApi.getCode()) {
			LogWriter.writeLog(LogType.ALERT, statusDb.getCode() + " " + statusDb.getName() + ": gamestatus changed code");
			return true;
		}
		if(!statusDb.getName().equals(statusApi.getName())) {
			LogWriter.writeLog(LogType.ALERT, statusDb.getCode() + " " + statusDb.getName() + ": gamestatus changed status name");
			return true;
		}
		return false;
	}
	
	private boolean gameDifferenceExists(Game gameDb, Game gameApi) {
		boolean res = false;
		if(gameDb.getGameDate().compareTo(gameApi.getGameDate()) != 0) {
			gameDb.setGameDate(gameApi.getGameDate());
			res = true;
		}
		if(gameDb.getHomeScore() != gameApi.getHomeScore() || gameDb.getAwayScore() != gameApi.getAwayScore()) {
			gameDb.setHomeScore(gameApi.getHomeScore());
			gameDb.setAwayScore(gameApi.getAwayScore());
			res = true;
		}
		if(statusDifferenceExists(gameDb.getGameStatus(), gameApi.getGameStatus())) {
			gameDb.setGameStatus(gameApi.getGameStatus());
			res = true;
		}
		if(venueDifferenceExists(gameDb.getVenue(), gameApi.getVenue())) {
			gameDb.setVenue(gameApi.getVenue());
			res = true;
		}
		
		if(gameDb.getJsonId() != gameApi.getJsonId())
			LogWriter.writeLog(LogType.ALERT, gameDb.getJsonId() + " game has changed jsonId!");
		if(!gameDb.getGameType().equals(gameApi.getGameType()))
			LogWriter.writeLog(LogType.ALERT, gameDb.getJsonId() + " game has changed game type!");
		if(gameDb.getSeason() != gameApi.getSeason())
			LogWriter.writeLog(LogType.ALERT, gameDb.getJsonId() + " game has changed season!");
		if(gameDb.getAwayTeam().getJsonId() != gameApi.getAwayTeam().getJsonId())
			LogWriter.writeLog(LogType.ALERT, gameDb.getJsonId() + " game changed away team!");
		if(gameDb.getHomeTeam().getJsonId() != gameApi.getHomeTeam().getJsonId())
			LogWriter.writeLog(LogType.ALERT, gameDb.getJsonId() + " game changed home team!");
		
		return res;
	}
	
	private boolean venueDifferenceExists(Venue venueDb, Venue venueApi) {
		if(!venueDb.getName().equals(venueApi.getName()) || ((venueDb.getCity() != null && venueApi.getCity() != null) && !venueDb.getCity().equals(venueApi.getCity())))
			return true;
		else
			return false;
	}
}