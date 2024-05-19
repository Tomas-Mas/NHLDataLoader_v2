package statapi.v2.eventmodel;

import main.LogType;
import main.LogWriter;

public enum EventNameMappingEnum {
	PERIODSTART("period-start", "Period Start"),
	FACEOFF("faceoff", "Faceoff"),
	STOPPAGE("stoppage", "Stoppage"),
	HIT("hit", "Hit"),
	MISSEDSHOT("missed-shot", "Missed Shot"),
	SHOTONGOAL("shot-on-goal", "Shot On Goal"),
	BLOCKEDSHOT("blocked-shot", "Blocked Shot"),
	GIVEAWAY("giveaway", "Giveaway"),
	TAKEAWAY("takeaway", "Takeaway"),
	GOAL("goal", "Goal"),
	PENALTY("penalty", "Penalty"),
	PERIODEND("period-end", "Period End"),
	SHOOTOUTCOMPLETE("shootout-complete", "Shootout End"),
	GAMEEND("game-end", "Game End"),
	DEFAULT("default", "Undefined");
	
	private String key;
	private String name;
	
	private EventNameMappingEnum(String key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public static EventNameMappingEnum valueOfKey(String key) {
		for(EventNameMappingEnum e : values()) {
			if(e.key.equals(key))
				return e;
		}
		LogWriter.writeLog(LogType.ALERT, "no event name mapping for name: " + key + " ; mapped to default");
		return EventNameMappingEnum.DEFAULT;
	}
	
	public String getName() {
		return name;
	}
}
