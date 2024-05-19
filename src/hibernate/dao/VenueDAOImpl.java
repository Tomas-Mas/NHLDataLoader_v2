package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Venue;
import main.HibernateUtil;

public class VenueDAOImpl implements VenueDAO {

	@Override
	public void insert(Venue venue) {
		Session session = HibernateUtil.getSession();
		session.save(venue);
	}

	@Override
	public Venue selectByName(String name) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Venue where name = :name");
		query.setParameter("name", name);
		return (Venue) query.uniqueResult();
	}

	@Override
	public void update(Venue venue) {
		Session session = HibernateUtil.getSession();
		session.update(venue);
	}

}
