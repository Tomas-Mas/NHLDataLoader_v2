package statsapi.playerstatsmodel;

public class GoalieStats {

	private String timeOnIce;
	private int pim;
	private int shots;
	private int saves;
	private int powerPlaySaves;
	private int shortHandedSaves;
	private int shortHandedShotsAgainst;
	private int powerPlayShotsAgainst;
	
	public String getTimeOnIce() {
		return timeOnIce;
	}
	
	public int getPim() {
		return pim;
	}
	
	public int getShots() {
		return shots;
	}
	
	public int getSaves() {
		return saves;
	}
	
	public int getPowerPlaySaves() {
		return powerPlaySaves;
	}
	
	public int getShortHandedSaves() {
		return shortHandedSaves;
	}
	
	public int getShortHandedShotsAgainst() {
		return shortHandedShotsAgainst;
	}
	
	public int getPowerPlayShotsAgainst() {
		return powerPlayShotsAgainst;
	}
}
