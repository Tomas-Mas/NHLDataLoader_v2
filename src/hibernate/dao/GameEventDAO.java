package hibernate.dao;

import java.util.List;

import hibernate.entities.Game;
import hibernate.entities.GameEvent;

public interface GameEventDAO {

	public void insert(GameEvent event);
	public void update(GameEvent event);
	public GameEvent selectByData(Game game, int eventId);
	public List<GameEvent> selectAllByGame(Game game);
}
