package hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Event_Players")
public class EventPlayer {

	private EventPlayerPK id;
	private String role;
	
	@Id
	public EventPlayerPK getId() {
		return id;
	}
	public void setId(EventPlayerPK id) {
		this.id = id;
	}
	
	@Column(length = 50)
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
