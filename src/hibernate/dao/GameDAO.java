package hibernate.dao;

import hibernate.entities.Game;

public interface GameDAO {

	public void insert(Game game);
	public void update(Game game);
	public Game selectByJsonId(int jsonId);
}
