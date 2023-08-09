package hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Conference_teams")
public class ConferenceTeam {

	private ConferencePK conferencePk;

	@Id
	public ConferencePK getConferencePk() {
		return conferencePk;
	}
	public void setConferencePk(ConferencePK conferencePk) {
		this.conferencePk = conferencePk;
	}
}
