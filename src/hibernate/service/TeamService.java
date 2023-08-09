package hibernate.service;

import hibernate.dao.TeamDAO;
import hibernate.dao.TeamDAOImpl;
import hibernate.dao.TimeZoneDAO;
import hibernate.dao.TimeZoneDAOImpl;
import hibernate.dao.VenueDAO;
import hibernate.dao.VenueDAOImpl;
import hibernate.entities.Team;
import hibernate.entities.TimeZone;
import hibernate.entities.Venue;
import main.ComparatorUtil;
import main.LogType;
import main.LogWriter;

public class TeamService {

	private TeamDAO teamDAO;
	private VenueDAO venueDAO;
	private TimeZoneDAO timeZoneDAO;
	
	public TeamService() {
		this.teamDAO = new TeamDAOImpl();
		this.venueDAO = new VenueDAOImpl();
		this.timeZoneDAO = new TimeZoneDAOImpl();
	}
	
	public Team save(Team team) {
		Venue venueApi = team.getVenue();
		Venue venueDb = venueDAO.selectByValues(venueApi.getName(), venueApi.getCity());
		if(venueDb == null) {
			venueDAO.insert(venueApi);
		} else {
			if(venueDifferenceExists(venueDb, venueApi)) {
				//problem is with renaming venues I have no way of knowing if venue is renamed/built new one, so this is going to generate more data then needed
				//but since some venues have no id in api I have no other option
				LogWriter.writeLog(LogType.ALERT, team.getName() + " team has changed venue");
				venueDAO.insert(venueApi);
			} else {
				team.setVenue(venueDb);
			}
		}
		
		TimeZone timeZoneApi = team.getTimeZone();
		TimeZone timeZoneDb = timeZoneDAO.selectByData(timeZoneApi.getName(), timeZoneApi.getOffset());
		if(timeZoneDb == null) {
			timeZoneDAO.insert(timeZoneApi);
		} else {
			//time zone should not change, just making sure..
			timeZoneDifferenceExists(timeZoneDb, timeZoneApi);
			team.setTimeZone(timeZoneDb);
		}
		
		Team teamDb = teamDAO.selectByJsonId(team.getJsonId());
		if(teamDb == null) {
			teamDAO.insert(team);
		} else {
			if(teamDifferenceExists(teamDb, team)) {
				teamDAO.update(teamDb);
			}
			team = teamDb;
		}
		return team;
	}
	
	private boolean venueDifferenceExists(Venue venueDb, Venue venueApi) {
		if(!venueDb.getName().equals(venueApi.getName()))
			return true;
		if(!ComparatorUtil.nullableStringsEquals(venueDb.getCity(), venueApi.getCity()))
			return true;
		return false;
	}
	
	private void timeZoneDifferenceExists(TimeZone timeZoneDb, TimeZone timeZoneApi) {
		if(!timeZoneDb.getName().equals(timeZoneApi.getName()))
			LogWriter.writeLog(LogType.ALERT, timeZoneDb.getName() + " time zone changed name!");
		if(timeZoneDb.getOffset() != timeZoneApi.getOffset())
			LogWriter.writeLog(LogType.ALERT, timeZoneDb.getName() + " time zone changed offset!");
	}
	
	private boolean teamDifferenceExists(Team teamDb, Team teamApi) {
		boolean res = false;
		if(!teamDb.getAbbreviation().equals(teamApi.getAbbreviation()) || !teamDb.getName().equals(teamApi.getName()) ||
				!teamDb.getShortName().equals(teamApi.getShortName()) || !teamDb.getTeamName().equals(teamApi.getTeamName())) {
			teamDb.setAbbreviation(teamApi.getAbbreviation());
			teamDb.setName(teamApi.getName());
			teamDb.setShortName(teamApi.getShortName());
			teamDb.setTeamName(teamApi.getTeamName());
			res = true;
		}
		if(teamDb.getVenue().getId() != teamApi.getVenue().getId()) {
			LogWriter.writeLog(LogType.ALERT, teamDb.getName() + " team changed venue");
			teamDb.setVenue(teamApi.getVenue());
			res = true;
		}
		if(!teamDb.getLocation().equals(teamApi.getLocation())) {
			teamDb.setLocation(teamApi.getLocation());
			res = true;
		}
		if(!teamDb.getActive().equals(teamApi.getActive())) {
			teamDb.setActive(teamApi.getActive());
			res = true;
		}
		
		if(teamDb.getJsonId() != teamApi.getJsonId())
			LogWriter.writeLog(LogType.ALERT, teamDb.getName() + " team changed jsonId!");
		if(teamDb.getFirstYear() != teamApi.getFirstYear())
			LogWriter.writeLog(LogType.ALERT, teamDb.getName() + " team changed first year!");
		if(teamDb.getTimeZone().getId() != teamApi.getTimeZone().getId())
			LogWriter.writeLog(LogType.ALERT, teamDb.getName() + " team changed time zone!");
		
		return res;
	}
}
