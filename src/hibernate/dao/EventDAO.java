package hibernate.dao;

import java.util.List;

import hibernate.entities.Event;

public interface EventDAO {

	public void insert(Event event);
	public Event selectByData(Event event);
	public List<Event> selectAll();
}
