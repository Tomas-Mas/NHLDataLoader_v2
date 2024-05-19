package statapi.v2.utils;

import java.util.HashMap;
import java.util.Map;

import hibernate.entities.Roster;
import main.LogType;
import main.LogWriter;

public class RosterStorage {

	private Map<Integer, Roster> rosters;
	
	public RosterStorage() {
		rosters = new HashMap<Integer, Roster>();
	}
	
	public void reset() {
		rosters = new HashMap<Integer, Roster>();
	}
	
	public void addRoster(Roster r) {
		rosters.put(r.getPlayer().getJsonId(), r);
	}
	
	public Roster getByPlayerId(int id) {
		Roster roster = rosters.get(id);
		if(roster == null)
			LogWriter.writeLog(LogType.ALERT, id + " player id not found in roster storage");
		return roster;
	}
}
