package hibernate.dao;

import hibernate.entities.Conference;

public interface ConferenceDAO {

	public void insert(Conference conference);
	public void update(Conference conference);
	public Conference selectByJsonId(int jsonId);
}
