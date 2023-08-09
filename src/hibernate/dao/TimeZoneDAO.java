package hibernate.dao;

import hibernate.entities.TimeZone;

public interface TimeZoneDAO {

	public void insert(TimeZone timeZone);
	public void update(TimeZone timeZone);
	public TimeZone selectByData(String name, int offset);
}
