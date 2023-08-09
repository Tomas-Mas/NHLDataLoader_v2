package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Team;
import main.HibernateUtil;

public class TeamDAOImpl implements TeamDAO {

	@Override
	public void insert(Team team) {
		Session session = HibernateUtil.getSession();
		session.save(team);
	}

	@Override
	public void update(Team team) {
		Session session = HibernateUtil.getSession();
		session.update(team);
	}

	@Override
	public Team selectByJsonId(int id) {
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("select t from Team t where t.jsonId = :id");
		q.setParameter("id", id);
		return (Team) q.uniqueResult();
	}

}
