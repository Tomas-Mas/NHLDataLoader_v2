package hibernate.dao;

import hibernate.entities.Venue;

public interface VenueDAO {

	public void insert(Venue venue);
	public Venue selectByName(String name);
	public void update(Venue venue); 
}
