package hibernate.dao;

import hibernate.entities.Player;

public interface PlayerDAO {

	public void insert(Player player);
	public void update(Player player);
	public Player selectByJsonId(int jsonId);
}
