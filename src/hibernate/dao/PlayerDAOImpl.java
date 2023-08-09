package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Player;
import main.HibernateUtil;

public class PlayerDAOImpl implements PlayerDAO {

	@Override
	public void insert(Player player) {
		Session session = HibernateUtil.getSession();
		session.save(player);
	}

	@Override
	public void update(Player player) {
		Session session = HibernateUtil.getSession();
		session.update(player);
	}

	@Override
	public Player selectByJsonId(int jsonId) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Player where jsonId = :jsonId");
		query.setParameter("jsonId", jsonId);
		return (Player) query.uniqueResult();
	}
	
}
