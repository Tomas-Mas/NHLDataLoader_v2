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
	public Venue selectByValues(String name, String city) {
		Session session = HibernateUtil.getSession();
		Query query = null;
		if(city == null) {
			query = session.createQuery("from Venue where name = :name and city is null");
			query.setParameter("name", name);
		} else {
			query = session.createQuery("from Venue where name = :name and city = :city");
			query.setParameter("name", name);
			query.setParameter("city", city);
		}
		return (Venue) query.uniqueResult();
	}

	@Override
	public void update(Venue venue) {
		Session session = HibernateUtil.getSession();
		session.update(venue);
	}

}
