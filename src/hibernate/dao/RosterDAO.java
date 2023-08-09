package hibernate.dao;

import hibernate.entities.Game;
import hibernate.entities.Roster;

public interface RosterDAO {

	public void insert(Roster roster);
	public void update(Roster roster);
	public Roster selectByGameTeamPlayer(Roster roster);
	public Roster selectByGameJsonPlayer(Game game, int jsonIdPlayer);
}
