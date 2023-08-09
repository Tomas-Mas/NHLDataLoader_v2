package hibernate.dao;

import hibernate.entities.Venue;

public interface VenueDAO {

	public void insert(Venue venue);
	public Venue selectByValues(String name, String city);
	public void update(Venue venue); 
}
