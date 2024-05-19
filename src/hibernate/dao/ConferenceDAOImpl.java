package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Conference;
import main.HibernateUtil;

public class ConferenceDAOImpl implements ConferenceDAO {

	@Override
	public void insert(Conference conference) {
		Session session = HibernateUtil.getSession();
		session.save(conference);
	}

	@Override
	public void update(Conference conference) {
		Session session = HibernateUtil.getSession();
		session.update(conference);
	}

	@Override
	public Conference selectByJsonId(int jsonId) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Conference where jsonId = :id");
		query.setParameter("id", jsonId);
		return (Conference) query.uniqueResult();
	}
	
	@Override
	public Conference selectByName(String name) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Conference where name = :name");
		query.setParameter("name", name);
		return (Conference) query.uniqueResult();
	}

}
