package hibernate.dao;

import org.hibernate.Session;

import hibernate.entities.GoalieStats;
import main.HibernateUtil;

public class GoalieStatsDAOImpl implements GoalieStatsDAO {

	@Override
	public void insert(GoalieStats goalieStats) {
		Session session = HibernateUtil.getSession();
		session.save(goalieStats);
	}

	@Override
	public void update(GoalieStats goalieStats) {
		Session session = HibernateUtil.getSession();
		session.update(goalieStats);
	}

}
