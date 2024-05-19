package statapi.v2.teammodel;

public class TeamApi {

	private String id;
	private String franchiseId;
	private String fullName;
	private String triCode;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFranchiseId() {
		return franchiseId;
	}
	
	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getTriCode() {
		return triCode;
	}
	
	public void setTriCode(String triCode) {
		this.triCode = triCode;
	}
}
