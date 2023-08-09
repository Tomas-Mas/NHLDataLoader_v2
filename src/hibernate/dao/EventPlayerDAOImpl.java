package hibernate.dao;

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

}
