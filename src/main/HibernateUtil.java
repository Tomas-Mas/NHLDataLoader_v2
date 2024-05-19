package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import hibernate.entities.Conference;
import hibernate.entities.ConferenceDivision;
import hibernate.entities.ConferenceTeam;
import hibernate.entities.Division;
import hibernate.entities.DivisionTeam;
import hibernate.entities.Event;
import hibernate.entities.EventPlayer;
import hibernate.entities.Game;
import hibernate.entities.GameEvent;
import hibernate.entities.GameStatus;
import hibernate.entities.Player;
import hibernate.entities.Position;
import hibernate.entities.Roster;
import hibernate.entities.Team;
import hibernate.entities.TimeZone;
import hibernate.entities.Venue;

public class HibernateUtil {

	public static SessionFactory factory;
	
	public static Session session;
	
	public static Session getSession() {
		if(factory == null) {
			Configuration config = new Configuration();
			config.addAnnotatedClass(Game.class);
			config.addAnnotatedClass(GameStatus.class);
			config.addAnnotatedClass(Team.class);
			config.addAnnotatedClass(Venue.class);
			config.addAnnotatedClass(TimeZone.class);
			config.addAnnotatedClass(Division.class);
			config.addAnnotatedClass(DivisionTeam.class);
			config.addAnnotatedClass(Conference.class);
			config.addAnnotatedClass(ConferenceTeam.class);
			config.addAnnotatedClass(ConferenceDivision.class);
			config.addAnnotatedClass(Position.class);
			config.addAnnotatedClass(Player.class);
			config.addAnnotatedClass(GameEvent.class);
			config.addAnnotatedClass(Event.class);
			config.addAnnotatedClass(Roster.class);
			config.addAnnotatedClass(EventPlayer.class);
			config.configure();
		
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
			factory = config.buildSessionFactory(serviceRegistry);
		}
		if(session == null) {
			session = factory.openSession();
		}
		if(!session.isOpen()) {
			session = factory.openSession();
		}
		if(!session.getTransaction().isActive()) {
			session.beginTransaction();
		}
		return session;
	}
	
	/*public static Session beginTransaction() {
		Session session = getSession();
		session.beginTransaction();
		return session;
	}*/
	
	public static void commitTransaction() {
		Session s = getSession();
		s.getTransaction().commit();
	}
	
	public static void rollbackTransaction() {
		Session s = getSession();
		s.getTransaction().rollback();
	}
	
	public static void closeSession() {
		Session s = getSession();
		s.close();
	}
}
