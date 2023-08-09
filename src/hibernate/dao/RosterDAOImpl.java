package hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.entities.Game;
import hibernate.entities.Roster;
import main.HibernateUtil;

public class RosterDAOImpl implements RosterDAO {

	@Override
	public void insert(Roster roster) {
		Session session = HibernateUtil.getSession();
		session.save(roster);
	}

	@Override
	public void update(Roster roster) {
		Session session = HibernateUtil.getSession();
		session.update(roster);
	}

	@Override
	public Roster selectByGameTeamPlayer(Roster roster) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("select r from Roster r join r.game g join r.team t join r.player p " +
				"where r.game.id = :gameId and r.team.id = :teamId and r.player.id = :playerId");
		query.setParameter("gameId", roster.getGame().getId());
		query.setParameter("teamId", roster.getTeam().getId());
		query.setParameter("playerId", roster.getPlayer().getId());
		return (Roster) query.uniqueResult();
	}

	@Override
	public Roster selectByGameJsonPlayer(Game game, int jsonIdPlayer) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("select r from Roster r join r.game g join r.player p " +
				"where r.game.id = :gameId and r.player.jsonId = :jsonId");
		query.setParameter("gameId", game.getId());
		query.setParameter("jsonId", jsonIdPlayer);
		return (Roster) query.uniqueResult();
	}

}
