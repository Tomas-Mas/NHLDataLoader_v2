package hibernate.dao;

import org.hibernate.Session;

import hibernate.entities.SkaterStats;
import main.HibernateUtil;

public class SkaterStatsDAOImpl implements SkaterStatsDAO {

	@Override
	public void insert(SkaterStats skaterStats) {
		Session session = HibernateUtil.getSession();
		session.save(skaterStats);
	}

	@Override
	public void update(SkaterStats skaterStats) {
		Session session = HibernateUtil.getSession();
		session.update(skaterStats);
	}

}
