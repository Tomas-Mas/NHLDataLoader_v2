package statsapi.playerstatsmodel;

public class SkaterStats {

	private String timeOnIce;
	private int powerPlayGoals;
	private int powerPlayAssists;
	private int penaltyMinutes;
	private int shortHandedGoals;
    private int shortHandedAssists;
	private int plusMinus;
	private String powerPlayTimeOnIce;
	private String shortHandedTimeOnIce;
	
	public String getTimeOnIce() {
		return timeOnIce;
	}
	
	public int getPowerPlayGoals() {
		return powerPlayGoals;
	}
	
	public int getPowerPlayAssists() {
		return powerPlayAssists;
	}
	
	public int getPenaltyMinutes() {
		return penaltyMinutes;
	}
	
	public int getShortHandedGoals() {
		return shortHandedGoals;
	}
	
	public int getShortHandedAssists() {
		return shortHandedAssists;
	}
	
	public int getPlusMinus() {
		return plusMinus;
	}
	
	public String getPowerPlayTimeOnIce() {
		return powerPlayTimeOnIce;
	}
	
	public String getShortHandedTimeOnIce() {
		return shortHandedTimeOnIce;
	}
}
