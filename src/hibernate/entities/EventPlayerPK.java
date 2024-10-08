package hibernate.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class EventPlayerPK implements Serializable {
	private static final long serialVersionUID = -8486669463720979112L;
	
	private GameEvent event;
	private Roster roster;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	public GameEvent getEvent() {
		return event;
	}
	public void setEvent(GameEvent event) {
		this.event = event;
	}
	
	@ManyToOne
	@JoinColumn(name = "roster_id")
	public Roster getRoster() {
		return roster;
	}
	public void setRoster(Roster roster) {
		this.roster = roster;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		EventPlayerPK pk = (EventPlayerPK) obj;
		return this.event.getJsonId() == pk.getEvent().getJsonId() && this.roster.getId() == pk.getRoster().getId();
	}
	
}
