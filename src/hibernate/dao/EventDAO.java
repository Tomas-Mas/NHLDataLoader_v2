package hibernate.dao;

import hibernate.entities.Event;

public interface EventDAO {

	public void insert(Event event);
	public Event selectByData(Event event);
}
