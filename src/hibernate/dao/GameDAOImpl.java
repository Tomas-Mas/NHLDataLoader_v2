package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Game;
import main.HibernateUtil;

public class GameDAOImpl implements GameDAO {
	
	@Override
	public void insert(Game game) {
		Session session = HibernateUtil.getSession();
		session.save(game);
	}

	@Override
	public void update(Game game) {
		Session session = HibernateUtil.getSession();
		session.update(game);
	}

	@Override
	public Game selectByJsonId(int jsonId) {
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("from Game where jsonId = :id");
		q.setParameter("id", jsonId);
		return (Game) q.uniqueResult();
	}
}
