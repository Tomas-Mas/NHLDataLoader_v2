package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Position;
import main.HibernateUtil;

public class PositionDAOImpl implements PositionDAO {

	@Override
	public void insert(Position position) {
		Session session = HibernateUtil.getSession();
		session.save(position);
	}

	@Override
	public Position selectByData(Position position) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Position where name = :name and type = :type");
		query.setParameter("name", position.getName());
		query.setParameter("type", position.getType());
		return (Position) query.uniqueResult();
	}
}