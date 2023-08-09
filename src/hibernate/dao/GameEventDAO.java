package hibernate.dao;

import hibernate.entities.Game;
import hibernate.entities.GameEvent;

public interface GameEventDAO {

	public void insert(GameEvent event);
	public void update(GameEvent event);
	public GameEvent selectByData(Game game, int eventId);
}
