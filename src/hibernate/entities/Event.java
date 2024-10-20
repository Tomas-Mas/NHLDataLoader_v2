package hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Events")
public class Event {

	private int id;
	private String name;
	private String secondaryType;
	private String strength;
	private String emptyNet;
	private String penaltySeverity;
	private int penaltyMinutes;
	
	@Id
	@Column(name = "e_id")
	@SequenceGenerator(name = "eventIdGenerator", sequenceName = "SEQ_EVENTS_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventIdGenerator")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(length = 15)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "secondary_type", length = 50)
	public String getSecondaryType() {
		return secondaryType;
	}
	public void setSecondaryType(String secondaryType) {
		this.secondaryType = secondaryType;
	}
	
	@Column(length = 20)
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	
	@Column(name = "empty_net", length = 10)
	public String getEmptyNet() {
		return emptyNet;
	}
	public void setEmptyNet(String emptyNet) {
		this.emptyNet = emptyNet;
	}
	
	@Column(name = "penalty_severity", length = 30)
	public String getPenaltySeverity() {
		return penaltySeverity;
	}
	public void setPenaltySeverity(String penaltySeverity) {
		this.penaltySeverity = penaltySeverity;
	}
	
	@Column(name = "penalty_minutes")
	public int getPenaltyMinutes() {
		return penaltyMinutes;
	}
	public void setPenaltyMinutes(int penaltyMinutes) {
		this.penaltyMinutes = penaltyMinutes;
	}
}
