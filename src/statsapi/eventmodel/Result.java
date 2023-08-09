package statsapi.eventmodel;

public class Result {

	private String event;
	private String secondaryType;
	private String penaltySeverity;
	private String penaltyMinutes;
	private Strength strength;
	private String emptyNet;
	
	public String getEventName() {
		return event;
	}
	
	public String getSecondaryType() {
		return secondaryType;
	}
	
	public String getPenaltySeverity() {
		return penaltySeverity;
	}
	
	public String getPenaltyMinutes() {
		return penaltyMinutes;
	}

	public Strength getStrength() {
		return strength;
	}
	
	public String getEmptyNet() {
		return emptyNet;
	}
}
