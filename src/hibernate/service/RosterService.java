package hibernate.service;

import hibernate.dao.PositionDAO;
import hibernate.dao.PositionDAOImpl;
import hibernate.dao.RosterDAO;
import hibernate.dao.RosterDAOImpl;
import hibernate.entities.Position;
import hibernate.entities.Roster;

public class RosterService {

	private RosterDAO rosterDAO;
	private PositionDAO positionDAO;
	
	public RosterService() {
		this.rosterDAO = new RosterDAOImpl();
		this.positionDAO = new PositionDAOImpl();
	}
	
	public Roster save(Roster roster) {
		Position positionDb = positionDAO.selectByData(roster.getPosition());
		if (positionDb == null) {
			positionDAO.insert(roster.getPosition());
		} else {
			roster.setPosition(positionDb);
		}
		
		Roster rosterDb = rosterDAO.selectByGameTeamPlayer(roster);
		
		if(rosterDb == null) {
			rosterDAO.insert(roster);
		} else {
			if(!rosterDb.getTimeOnIce().equals(roster.getTimeOnIce())) {
				rosterDb.setTimeOnIce(roster.getTimeOnIce());
			}
			if(rosterDb.getPlusMinus() != roster.getPlusMinus()) {
				rosterDb.setPlusMinus(roster.getPlusMinus());
			}
			roster = rosterDb;
		}
		return roster;
	}
}
