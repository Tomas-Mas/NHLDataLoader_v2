package hibernate.dao;

import hibernate.entities.EventPlayer;
import hibernate.entities.EventPlayerPK;

public interface EventPlayerDAO {

	public void insert(EventPlayer ep);
	public void update(EventPlayer ep);
	public EventPlayer selectById(EventPlayerPK id);
}
