package statapi.v2.eventmodel;

import main.LogType;
import main.LogWriter;

public enum PenaltyType {

	MAJOR("MAJ", "Major"),
	MINOR("MIN", "Minor"),
	MISCONDUCT("MIS", "Misconduct"),
	BENCH("BEN", "Bench"),
	PENALTYSHOT("PS", "Penalty Shot"),
	GAMEMISCONDUCT("GAM", "Game Misconduct"),
	MATCH("MAT", "Match"),
	DEFAULT("D", "Unknown");
	
	private String key;
	private String name;
	
	private PenaltyType(String key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public static PenaltyType valueOfKey(String key, int gameId) {
		for(PenaltyType p : values()) {
			if(p.key.equals(key))
				return p;
		}
		LogWriter.writeLog(LogType.ALERT, "Unmapped type code in penalty type for key: " + key + " in game id: " + gameId);
		return PenaltyType.DEFAULT;
	}
	
	public String getName() {
		return name;
	}
}
