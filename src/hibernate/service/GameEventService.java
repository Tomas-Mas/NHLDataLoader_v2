package hibernate.service;

import java.util.ArrayList;
import java.util.List;

import hibernate.dao.EventDAO;
import hibernate.dao.EventDAOImpl;
import hibernate.dao.EventPlayerDAO;
import hibernate.dao.EventPlayerDAOImpl;
import hibernate.dao.GameEventDAO;
import hibernate.dao.GameEventDAOImpl;
import hibernate.dao.RosterDAO;
import hibernate.dao.RosterDAOImpl;
import hibernate.entities.Event;
import hibernate.entities.EventPlayer;
import hibernate.entities.EventPlayerPK;
import hibernate.entities.Game;
import hibernate.entities.GameEvent;
import hibernate.entities.Roster;
import main.LogType;
import main.LogWriter;
import statapi.v2.utils.GameEventStorage;

public class GameEventService {
	
	private EventDAO eventDAO;
	private GameEventDAO gameEventDAO;
	private RosterDAO rosterDAO;
	private EventPlayerDAO eventPlayerDAO;

	public GameEventService() {
		eventDAO = new EventDAOImpl();
		gameEventDAO = new GameEventDAOImpl();
		rosterDAO = new RosterDAOImpl();
		eventPlayerDAO = new EventPlayerDAOImpl();
	}
	
	public void saveEvent(GameEvent event, GameEventStorage gameEventStorage) {
		Event eventDb = gameEventStorage.getEvent(event.getEvent());
		if(eventDb == null) {
			eventDAO.insert(event.getEvent());
			gameEventStorage.addEvent(event.getEvent());
		} else {
			event.setEvent(eventDb);
		}
		
		List<EventPlayer> players = event.getPlayers();
		
		GameEvent gameEventDb = gameEventStorage.findGameEvent(event);
		if(gameEventDb == null) {
			gameEventDAO.insert(event);
		} else {
			if(gameEventChanged(event, gameEventDb)) {
				gameEventDAO.update(gameEventDb);
			}
			event = gameEventDb;
		}
		
		if(players.size() == 0) {
			return;
		}
		
		for(EventPlayer p : players) {
			EventPlayer ePlayer = new EventPlayer();
			EventPlayerPK eId = new EventPlayerPK();
			//Roster roster = rosterDAO.selectByGameJsonPlayer(event.getGame(), p.getId().getRoster().getPlayer().getJsonId());
			eId.setEvent(event);
			eId.setRoster(p.getId().getRoster());
			ePlayer.setId(eId);
			ePlayer.setRole(p.getRole());
			
			EventPlayer ePlayerDb = gameEventStorage.findEventPlayer(ePlayer);
			
			if(ePlayerDb == null) {
				eventPlayerDAO.insert(ePlayer);
			} else {
				if(ePlayerDb.getRole() != null && !ePlayerDb.getRole().equals(ePlayer.getRole())) {
					ePlayerDb.setRole(ePlayer.getRole());
					eventPlayerDAO.update(ePlayerDb);
				}
			}
		}
	}
	
	public List<EventPlayer> selectAllEventPlayersByGameId(int gameId) {
		return eventPlayerDAO.selectAllByGameId(gameId);
	}
	
	public List<GameEvent> selectAllGameEventsByGame(Game game) {
		return gameEventDAO.selectAllByGame(game);
	}
	
	public List<Event> selectAllEvents() {
		return eventDAO.selectAll();
	}
	
	/*
	 * Replaced by saveEvent(GameEvent event, GameEventStorage gameEventStorage) due to new api version release
	 */
	@Deprecated
	public void saveEvents(Game game, List<GameEvent> events) {
		for(GameEvent gameEvent : events) {
			//didnt realize coaches can get penalty too, but since I dont store them in db I have to skip this for now
			if(gameEvent.getEvent().getSecondaryType() != null && gameEvent.getEvent().getSecondaryType().equals("Game Misconduct - Head coach")) {
				LogWriter.writeLog(LogType.ALERT, "game id: " + game.getId() + " - Game Misconduct - Head coach skipped");
				continue;
			}
			
			List <EventPlayer> eventPlayers = new ArrayList<EventPlayer>(gameEvent.getPlayers());
			gameEvent.setPlayers(null);
			
			gameEvent.setGame(game);
			Event eventApi = gameEvent.getEvent();
			Event eventDb = eventDAO.selectByData(eventApi);
			if(eventDb == null) {
				eventDAO.insert(eventApi);
			} else {
				gameEvent.setEvent(eventDb);
			}
			
			GameEvent gameEventDb = gameEventDAO.selectByData(gameEvent.getGame(), gameEvent.getJsonId());
			if(gameEventDb == null) {
				gameEvent.setGame(game);
				gameEventDAO.insert(gameEvent);
			} else {
				if(gameEventChanged(gameEvent, gameEventDb)) {
					gameEventDAO.update(gameEventDb);
				}
				gameEvent = gameEventDb;
			}
			
			if(eventPlayers.size() == 0) {
				continue;
			}
			
			for(EventPlayer eventPlayer : eventPlayers) {
				Roster roster = rosterDAO.selectByGameJsonPlayer(game, eventPlayer.getId().getRoster().getPlayer().getJsonId());
				EventPlayer ePlayer = new EventPlayer();
				EventPlayerPK epId = new EventPlayerPK();
				epId.setEvent(gameEvent);
				epId.setRoster(roster);
				ePlayer.setId(epId);
				if(eventPlayer.getRole() == null || eventPlayer.getRole().equals("PlayerID")) {
					ePlayer.setRole(null);
				} else {
					ePlayer.setRole(eventPlayer.getRole());
				}
				
				EventPlayer ePlayerDb = eventPlayerDAO.selectById(ePlayer.getId());
				if(ePlayerDb == null) {
					eventPlayerDAO.insert(ePlayer);
				} else {
					if(ePlayerDb.getRole() != null && !ePlayerDb.getRole().equals(ePlayer.getRole())) {
						ePlayerDb.setRole(ePlayer.getRole());
						eventPlayerDAO.update(ePlayerDb);
					}
				}
			}
		}
	}
	
	private boolean gameEventChanged(GameEvent geApi, GameEvent geDb) {
		boolean res = false;
		if(geApi.getEvent().getId() != geDb.getEvent().getId()) {
			LogWriter.writeLog(LogType.ALERT, geDb.getId() + " event changed its event type");
			geDb.setEvent(geApi.getEvent());
			res = true;
		}
		if(!geApi.getPeriodTime().equals(geDb.getPeriodTime())) {
			geDb.setPeriodTime(geApi.getPeriodTime());
			res = true;
		}
		if(geApi.getCoordX() != geDb.getCoordX() || geApi.getCoordY() != geDb.getCoordY()) {
			geDb.setCoordX(geApi.getCoordX());
			geDb.setCoordY(geApi.getCoordY());
			res = true;
		}
		return res;
	}
}
