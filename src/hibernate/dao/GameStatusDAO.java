package hibernate.dao;

import hibernate.entities.GameStatus;

public interface GameStatusDAO {

	public void insert(GameStatus gameStatus);
	public GameStatus selectById(int id);
}
