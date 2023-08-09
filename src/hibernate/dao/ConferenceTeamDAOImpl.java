package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.ConferencePK;
import hibernate.entities.ConferenceTeam;
import main.HibernateUtil;

public class ConferenceTeamDAOImpl implements ConferenceTeamDAO {

	@Override
	public void insert(ConferenceTeam conferenceTeam) {
		Session session = HibernateUtil.getSession();
		session.save(conferenceTeam);
	}

	@Override
	public ConferenceTeam selectById(ConferencePK conferencePk) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from ConferenceTeam where conferencePk = :conferencePk");
		query.setParameter("conferencePk", conferencePk);
		return (ConferenceTeam) query.uniqueResult();
	}

}
