package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Division;
import main.HibernateUtil;

public class DivisionDAOImpl implements DivisionDAO {

	@Override
	public void insert(Division division) {
		Session session = HibernateUtil.getSession();
		session.save(division);
	}

	@Override
	public void update(Division division) {
		Session session = HibernateUtil.getSession();
		session.update(division);
	}

	@Override
	public Division selectByJsonId(int jsonId) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Division where jsonId = :jsonId");
		query.setParameter("jsonId", jsonId);
		return (Division) query.uniqueResult();
	}
	
	@Override
	public Division selectByName(String name) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Division where name = :name");
		query.setParameter("name", name);
		return (Division) query.uniqueResult();
	}

}
