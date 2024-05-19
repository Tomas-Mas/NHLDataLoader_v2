package statapi.v2;

public class DataModelV2 {
	
	private String preSeasonStartDate;
	private String regularSeasonStartDate;
	private String regularSeasonEndDate;
	private String playoffEndDate;

	public String getPreSeasonStartDate() {
		return preSeasonStartDate;
	}

	public void setPreSeasonStartDate(String preSeasonStartDate) {
		this.preSeasonStartDate = preSeasonStartDate;
	}

	public String getRegularSeasonStartDate() {
		return regularSeasonStartDate;
	}

	public void setRegularSeasonStartDate(String regularSeasonStartDate) {
		this.regularSeasonStartDate = regularSeasonStartDate;
	}

	public String getRegularSeasonEndDate() {
		return regularSeasonEndDate;
	}

	public void setRegularSeasonEndDate(String regularSeasonEndDate) {
		this.regularSeasonEndDate = regularSeasonEndDate;
	}

	public String getPlayoffEndDate() {
		return playoffEndDate;
	}

	public void setPlayoffEndDate(String playoffEndDate) {
		this.playoffEndDate = playoffEndDate;
	}
}
