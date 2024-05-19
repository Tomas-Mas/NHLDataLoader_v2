package statapi.v2.teammodel;

public class FranchiseApi {

	private String id;
	private String fullName;
	//teamName
	private String teamCommonName;
	//shortName
	private String teamPlaceName;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getTeamCommonName() {
		return teamCommonName;
	}
	
	public void setTeamCommonName(String teamCommonName) {
		this.teamCommonName = teamCommonName;
	}
	
	public String getTeamPlaceName() {
		return teamPlaceName;
	}
	
	public void setTeamPlaceName(String teamPlaceName) {
		this.teamPlaceName = teamPlaceName;
	}
}
