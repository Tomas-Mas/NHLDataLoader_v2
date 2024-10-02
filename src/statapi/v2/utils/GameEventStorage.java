package statapi.v2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hibernate.entities.Event;
import hibernate.entities.EventPlayer;
import hibernate.entities.GameEvent;

public class GameEventStorage {
	
	private Map<Integer, List<EventPlayer>> eventPlayersMap;
	private Map<Integer, List<GameEvent>> gameEventsMap;
	private Map<String, List<Event>> eventsMap;
	
	public GameEventStorage() {
		eventPlayersMap = new HashMap<Integer, List<EventPlayer>>();
		gameEventsMap = new HashMap<Integer, List<GameEvent>>();
		eventsMap = new HashMap<String, List<Event>>();
	}
	
	public void setPlayers(List<EventPlayer> players) {
		eventPlayersMap = new HashMap<Integer, List<EventPlayer>>();
		for(EventPlayer player : players) {
			int key = buildEventPlayerKey(player);
			List<EventPlayer> mapValue = eventPlayersMap.get(key);
			if(mapValue == null) {
				List<EventPlayer> list = new ArrayList<EventPlayer>();
				list.add(player);
				eventPlayersMap.put(key, list);
			} else {
				mapValue.add(player);
			}
		}
	}
	
	public void setGameEvents(List<GameEvent> events) {
		gameEventsMap = new HashMap<Integer, List<GameEvent>>();
		for(GameEvent event : events) {
			int key = buildGameEventKey(event);
			List<GameEvent> mapValue = gameEventsMap.get(key);
			if(mapValue == null) {
				List<GameEvent> list = new ArrayList<GameEvent>();
				list.add(event);
				gameEventsMap.put(key, list);
			} else {
				mapValue.add(event);
			}
		}
	}
	
	public EventPlayer findEventPlayer(EventPlayer player) {
		int key = buildEventPlayerKey(player);
		
		if(eventPlayersMap.get(key) == null)
			return null;
		
		for(EventPlayer p : eventPlayersMap.get(key)) {
			if(p.getId().equals(player.getId())) {
				return p;
			}
		}
		return null;
	}
	
	public GameEvent findGameEvent(GameEvent event) {
		int key = buildGameEventKey(event);
		
		if(gameEventsMap.get(key) == null) {
			return null;
		}
		
		for(GameEvent e : gameEventsMap.get(key)) {
			if(e.getJsonId() == event.getJsonId() && e.getGame().getJsonId() == event.getGame().getJsonId()) {
				return e;
			}
		}
		return null;
	}
	
	public boolean areEventsEmpty() {
		if(eventsMap.size() < 1) 
			return true;
		
		return false;
	}
	
	public void loadEvents(List<Event> events) {
		for(Event event : events) {
			addEvent(event);
		}
	}
	
	public void addEvent(Event event) {
		String key = buildEventKey(event);
		if(eventsMap.get(key) == null) {
			List<Event> list = new ArrayList<Event>();
			list.add(event);
			eventsMap.put(key, list);
		} else {
			eventsMap.get(key).add(event);
		}
	}
	
	public Event getEvent(Event event) {
		String key = buildEventKey(event);
		
		if(eventsMap.get(key) == null)
			return null;
		
		for(Event e : eventsMap.get(key)) {
			if(
					nullableStringsEquals(e.getSecondaryType(), event.getSecondaryType()) &&
					nullableStringsEquals(e.getStrength(), event.getStrength()) &&
					nullableStringsEquals(e.getEmptyNet(), event.getEmptyNet()) &&
					nullableStringsEquals(e.getPenaltySeverity(), event.getPenaltySeverity()) &&
					e.getPenaltyMinutes() == event.getPenaltyMinutes()
			) {
				return e;
			}
		}
		return null;
	}
	
	private int buildEventPlayerKey(EventPlayer player) {
		return Integer.valueOf((player.getId().getEvent().getId() + player.getId().getRoster().getId()) % 10);
	}
	
	private int buildGameEventKey(GameEvent event) {
		return event.getJsonId() % 10;
	}
	
	private String buildEventKey(Event event) {
		return event.getName();
	}
	
	private boolean nullableStringsEquals(String a, String b) {
		if(a == null && b == null)
			return true;
		
		if(a == null || b == null)
			return false;
		
		if(a.equals(b)) {
			return true;
		} else {
			return false;
		}
	}

}
