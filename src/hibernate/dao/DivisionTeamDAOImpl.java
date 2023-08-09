package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.DivisionPK;
import hibernate.entities.DivisionTeam;
import main.HibernateUtil;

public class DivisionTeamDAOImpl implements DivisionTeamDAO {

	@Override
	public void insert(DivisionTeam divisionTeam) {
		Session session = HibernateUtil.getSession();
		session.save(divisionTeam);
	}

	@Override
	public DivisionTeam selectById(DivisionPK divisionPK) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from DivisionTeam where divisionPk = :divisionPK");
		query.setParameter("divisionPK", divisionPK);
		return (DivisionTeam) query.uniqueResult();
	}

}
