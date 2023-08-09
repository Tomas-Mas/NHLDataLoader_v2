package statsapi.playerstatsmodel;

public class PlayerStats {

	private int id;
	private SkaterStats skaterStats;
	private GoalieStats goalieStats;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setSkaterStats(SkaterStats skaterStats) {
		this.skaterStats = skaterStats;
	}
	
	public void setGoalieStats(GoalieStats goalieStats) {
		this.goalieStats = goalieStats;
	}
	
	public int getJsonId() {
		return id;
	}
	
	public SkaterStats getSkaterStats() {
		return skaterStats;
	}
	
	public GoalieStats getGoalieStats() {
		return goalieStats;
	}
}
