package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Event;
import main.HibernateUtil;

public class EventDAOImpl implements EventDAO {

	@Override
	public void insert(Event event) {
		Session session = HibernateUtil.getSession();
		session.save(event);
	}

	@Override
	public Event selectByData(Event event) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Event where " +
				"(:name is null and name is null or :name is not null and name = :name) and " +
				"(:secondaryType is null and secondaryType is null or :secondaryType is not null and secondaryType = :secondaryType) and " + 
				"(:strength is null and strength is null or :strength is not null and strength = :strength) and " +
				"(:emptyNet is null and emptyNet is null or :emptyNet is not null and emptyNet = :emptyNet) and " +
				"(:penaltySeverity is null and penaltySeverity is null or :penaltySeverity is not null and penaltySeverity = :penaltySeverity) and " +
				"(:penaltyMinutes is null and penaltyMinutes is null or :penaltyMinutes is not null and penaltyMinutes = :penaltyMinutes)");
		query.setParameter("name", event.getName());
		query.setParameter("secondaryType", event.getSecondaryType());
		query.setParameter("strength", event.getStrength());
		query.setParameter("emptyNet", event.getEmptyNet());
		query.setParameter("penaltySeverity", event.getPenaltySeverity());
		query.setParameter("penaltyMinutes", event.getPenaltyMinutes());
		return (Event) query.uniqueResult();
	}

	
}
