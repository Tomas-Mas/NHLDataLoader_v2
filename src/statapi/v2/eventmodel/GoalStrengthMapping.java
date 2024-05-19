package statapi.v2.eventmodel;

import main.LogType;
import main.LogWriter;

public enum GoalStrengthMapping {

	EVEN("ev", "Even"),
	POWERPLAY("pp", "Power Play"),
	SHORTHANDED("sh", "Short Handed"),
	DEFAULT("d", "Unknown");
	
	private String key;
	private String name;
	
	private GoalStrengthMapping(String key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public static GoalStrengthMapping valueOfKey(String key) {
		for(GoalStrengthMapping g : values()) {
			if(g.key.equals(key))
				return g;
		}
		LogWriter.writeLog(LogType.ALERT, "no goal strength name mapping for key: " + key + " ; mapped to default");
		return GoalStrengthMapping.DEFAULT;
	}
	
	public String getName() {
		return name;
	}
}
