package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.ConferenceDivision;
import hibernate.entities.ConferenceDivisionPK;
import main.HibernateUtil;

public class ConferenceDivisionDAOImpl implements ConferenceDivisionDAO {

	@Override
	public void insert(ConferenceDivision conferenceDivision) {
		Session session = HibernateUtil.getSession();
		session.save(conferenceDivision);
	}

	@Override
	public ConferenceDivision selectById(ConferenceDivisionPK conferenceDivisionPk) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from ConferenceDivision where conferenceDivisionPk = :conferenceDivisionPk");
		query.setParameter("conferenceDivisionPk", conferenceDivisionPk);
		return (ConferenceDivision) query.uniqueResult();
	}

}
