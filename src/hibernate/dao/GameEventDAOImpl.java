package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Game;
import hibernate.entities.GameEvent;
import main.HibernateUtil;

public class GameEventDAOImpl implements GameEventDAO {

	@Override
	public void insert(GameEvent event) {
		Session session = HibernateUtil.getSession();
		session.save(event);
	}

	@Override
	public void update(GameEvent event) {
		Session session = HibernateUtil.getSession();
		session.update(event);
	}

	@Override
	public GameEvent selectByData(Game game, int eventId) {
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("select ge from GameEvent ge where ge.game = :game and ge.jsonId = :jsonId");
		q.setParameter("game", game);
		q.setParameter("jsonId", eventId);
		return (GameEvent) q.uniqueResult();
	}
	
	@Override
	public List<GameEvent> selectAllByGame(Game game) {
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("select ge from GameEvent ge join fetch ge.event e join fetch ge.game g where ge.game = :game");
		q.setParameter("game", game);
		@SuppressWarnings("unchecked")
		List<GameEvent> events = q.list();
		return events;
	}

}
