package hibernate.dao;

import hibernate.entities.ConferencePK;
import hibernate.entities.ConferenceTeam;

public interface ConferenceTeamDAO {

	public void insert(ConferenceTeam conferenceTeam);
	public ConferenceTeam selectById(ConferencePK conferencePk);
}
