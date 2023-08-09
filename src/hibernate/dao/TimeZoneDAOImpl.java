package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.TimeZone;
import main.HibernateUtil;

public class TimeZoneDAOImpl implements TimeZoneDAO {

	@Override
	public void insert(TimeZone timeZone) {
		Session session = HibernateUtil.getSession();
		session.save(timeZone);
	}

	@Override
	public void update(TimeZone timeZone) {
		Session session = HibernateUtil.getSession();
		session.update(timeZone);
	}

	@Override
	public TimeZone selectByData(String name, int offset) {
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("from TimeZone where name = :name and offset = :offset");
		q.setParameter("name", name);
		q.setParameter("offset", offset);
		return (TimeZone) q.uniqueResult();
	}

}
