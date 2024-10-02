package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.EventPlayer;
import hibernate.entities.EventPlayerPK;
import main.HibernateUtil;

public class EventPlayerDAOImpl implements EventPlayerDAO {

	@Override
	public void insert(EventPlayer ep) {
		Session session = HibernateUtil.getSession();
		session.save(ep);
	}
	
	@Override
	public void update(EventPlayer ep) {
		Session session = HibernateUtil.getSession();
		session.update(ep);
	}

	@Override
	public EventPlayer selectById(EventPlayerPK id) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from EventPlayer where id = :id");
		query.setParameter("id", id);
		return (EventPlayer) query.uniqueResult();
	}
	
	@Override
	public List<EventPlayer> selectAllByGameId(int gameId) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("select p from EventPlayer p join fetch p.id pid inner join pid.event e inner join e.game g where g.id = :gameId");
		query.setParameter("gameId", gameId);
		@SuppressWarnings("unchecked")
		List<EventPlayer> players = query.list();
		return players;
	}

}
