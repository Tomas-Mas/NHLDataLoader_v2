package statsapi.playermodel;

public class PlayerData {

	private String id;
	private String firstName;
	private String lastName;
	private String primaryNumber;
	private String birthDate;
	private String birthCountry;
	private PlayerTeam currentTeam;
	private Position primaryPosition;
	
	public String getJsonId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPrimaryNumber() {
		return primaryNumber;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public String getBirthCountry() {
		return birthCountry;
	}
	
	public PlayerTeam getCurrentTeam() {
		return currentTeam;
	}
	
	public Position getPosition() {
		return primaryPosition;
	}
}
