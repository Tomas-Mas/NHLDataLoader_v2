package hibernate.service;

import hibernate.dao.ConferenceDAO;
import hibernate.dao.ConferenceDAOImpl;
import hibernate.dao.ConferenceDivisionDAO;
import hibernate.dao.ConferenceDivisionDAOImpl;
import hibernate.dao.ConferenceTeamDAO;
import hibernate.dao.ConferenceTeamDAOImpl;
import hibernate.dao.DivisionDAO;
import hibernate.dao.DivisionDAOImpl;
import hibernate.dao.DivisionTeamDAO;
import hibernate.dao.DivisionTeamDAOImpl;
import hibernate.entities.Conference;
import hibernate.entities.ConferenceDivision;
import hibernate.entities.ConferenceDivisionPK;
import hibernate.entities.ConferencePK;
import hibernate.entities.ConferenceTeam;
import hibernate.entities.Division;
import hibernate.entities.DivisionPK;
import hibernate.entities.DivisionTeam;
import hibernate.entities.Team;

public class DivisionConferenceService {
	
	private DivisionDAO divisionDAO;
	private DivisionTeamDAO divisionTeamDAO;
	private ConferenceDAO conferenceDAO;
	private ConferenceTeamDAO conferenceTeamDAO;
	private ConferenceDivisionDAO conferenceDivisionDAO;
	
	public DivisionConferenceService() {
		this.divisionDAO = new DivisionDAOImpl();
		this.divisionTeamDAO = new DivisionTeamDAOImpl();
		this.conferenceDAO = new ConferenceDAOImpl();
		this.conferenceTeamDAO = new ConferenceTeamDAOImpl();
		this.conferenceDivisionDAO = new ConferenceDivisionDAOImpl();
	}
	
	public void save(Team team, int season, Division division, Conference conference) {
		//Division divisionDb = divisionDAO.selectByJsonId(division.getJsonId());
		Division divisionDb = divisionDAO.selectByName(division.getName());
		if(divisionDb == null) {
			divisionDAO.insert(division);
		} else {
			division = divisionDb;
		}
		
		DivisionTeam divisionTeamApi = buildDivisionTeam(division, team, season);
		DivisionTeam divisionTeamDb = divisionTeamDAO.selectById(divisionTeamApi.getDivisionPk());
		if(divisionTeamDb == null) {
			divisionTeamDAO.insert(divisionTeamApi);
		}
		
		//Conference conferenceDb = conferenceDAO.selectByJsonId(conference.getJsonId());
		Conference conferenceDb = conferenceDAO.selectByName(conference.getName());
		if(conferenceDb == null) {
			conferenceDAO.insert(conference);
		} else {
			conference = conferenceDb;
		}
		
		ConferenceTeam conferenceTeamApi = buildConferenceTeam(conference, team, season);
		ConferenceTeam conferenceTeamDb = conferenceTeamDAO.selectById(conferenceTeamApi.getConferencePk());
		if(conferenceTeamDb == null) {
			conferenceTeamDAO.insert(conferenceTeamApi);
		}
		
		ConferenceDivision confDivApi = buildConferenceDivision(conference, division, season);
		ConferenceDivision confDivDb = conferenceDivisionDAO.selectById(confDivApi.getConferenceDivisionPk());
		if(confDivDb == null) {
			conferenceDivisionDAO.insert(confDivApi);
		}
	}
	
	
	private DivisionTeam buildDivisionTeam(Division division, Team team, int season) {
		DivisionPK divisionPk = new DivisionPK();
		divisionPk.setTeam(team);
		divisionPk.setDivision(division);
		divisionPk.setSeason(season);
		DivisionTeam divisionTeam = new DivisionTeam();
		divisionTeam.setDivisionPk(divisionPk);
		return divisionTeam;
	}
	
	private ConferenceTeam buildConferenceTeam(Conference conference, Team team, int season) {
		ConferencePK conferencePk = new ConferencePK();
		conferencePk.setConference(conference);
		conferencePk.setTeam(team);
		conferencePk.setSeason(season);
		ConferenceTeam conferenceTeam = new ConferenceTeam();
		conferenceTeam.setConferencePk(conferencePk);
		return conferenceTeam;
	}
	
	private ConferenceDivision buildConferenceDivision(Conference conference, Division division, int season) {
		ConferenceDivisionPK id = new ConferenceDivisionPK();
		id.setConference(conference);
		id.setDivision(division);
		id.setSeason(season);
		ConferenceDivision conferenceDivision = new ConferenceDivision();
		conferenceDivision.setConferenceDivisionPk(id);
		return conferenceDivision;
	}
}
