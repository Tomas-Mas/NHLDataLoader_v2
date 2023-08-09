package hibernate.dao;

import hibernate.entities.Team;

public interface TeamDAO {

	public void insert(Team team);
	public void update(Team team);
	public Team selectByJsonId(int id);
}
