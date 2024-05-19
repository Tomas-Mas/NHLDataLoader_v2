package statapi.v2.playermodel;

import java.time.LocalDate;
import java.time.Period;

import statapi.v2.DefaultName;

public class PlayerDetail {

	private String playerId;
	private String currentTeamId;
	private DefaultName firstName;
	private DefaultName lastName;
	private String sweaterNumber;
	private String position;
	private String birthDate;
	private String birthCountry;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getCurrentTeamId() {
		return currentTeamId;
	}

	public void setCurrentTeamId(String currentTeamId) {
		this.currentTeamId = currentTeamId;
	}

	public DefaultName getFirstName() {
		return firstName;
	}

	public void setFirstName(DefaultName firstName) {
		this.firstName = firstName;
	}

	public DefaultName getLastName() {
		return lastName;
	}

	public void setLastName(DefaultName lastName) {
		this.lastName = lastName;
	}

	public String getSweaterNumber() {
		return sweaterNumber;
	}

	public void setSweaterNumber(String sweaterNumber) {
		this.sweaterNumber = sweaterNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}
	
	public int getAge() {
		LocalDate bDate = LocalDate.parse(birthDate);
		LocalDate now = LocalDate.now();
		Period period = bDate.until(now);
		return period.getYears();
	}

	public void print() {
		System.out.println(playerId + " - " + firstName.getName() + " " + lastName.getName());
	}
}
