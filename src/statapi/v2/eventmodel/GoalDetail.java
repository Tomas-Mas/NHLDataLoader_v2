package statapi.v2.eventmodel;

import main.LogType;
import main.LogWriter;

public class GoalDetail {

	private int periodNumber;
	private String periodTime;
	private String strength;
	private String goalModifier;
	
	public GoalDetail(int periodNumber, String periodTime, String strength, String goalModifier) {
		this.periodNumber = periodNumber;
		this.periodTime = periodTime;
		this.strength = strength;
		this.goalModifier = goalModifier;
		
		if(!(goalModifier.equals("none") || goalModifier.equals("penalty-shot") || goalModifier.equals("empty-net") || goalModifier.equals("own-goal") || 
				goalModifier.equals("own-goal-empty-net") || goalModifier.equals("awarded-empty-net"))) {
			LogWriter.writeLog(LogType.ALERT, "found new goal modifier in Goal Detail: " + goalModifier);
		}
	}

	public int getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(int periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(String periodTime) {
		this.periodTime = periodTime;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getGoalModifier() {
		return goalModifier;
	}

	public void setGoalModifier(String goalModifier) {
		this.goalModifier = goalModifier;
	}
}
