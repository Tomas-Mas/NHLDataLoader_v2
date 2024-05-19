package hibernate.service;

import hibernate.dao.PlayerDAO;
import hibernate.dao.PlayerDAOImpl;
import hibernate.dao.PositionDAO;
import hibernate.dao.PositionDAOImpl;
import hibernate.dao.TeamDAO;
import hibernate.dao.TeamDAOImpl;
import hibernate.entities.Player;
import hibernate.entities.Position;
import hibernate.entities.Team;

public class PlayerService {
	
	private PositionDAO positionDAO;
	private PlayerDAO playerDAO;
	private TeamDAO teamDAO;
	
	public PlayerService() {
		this.positionDAO = new PositionDAOImpl();
		this.playerDAO = new PlayerDAOImpl();
		this.teamDAO = new TeamDAOImpl();
	}

	public Player savePlayer(Player player, Team team) {
		Position positionDb = positionDAO.selectByData(player.getPosition());
		if (positionDb == null) {
			positionDAO.insert(player.getPosition());
		} else {
			player.setPosition(positionDb);
		}
		
		if(player.getCurrentTeam() != null) {
			if(player.getCurrentTeam().getJsonId() == team.getJsonId()) {
				player.setCurrentTeam(team);
			} else {
				//current team can be team, that hasn't been saved to db before, if that happens just ignore it for now
				//as more data is stored it will catch up anyway
				Team teamDb = teamDAO.selectByJsonId(player.getCurrentTeam().getJsonId());
				if(teamDb != null) {
					player.setCurrentTeam(teamDb);
				} else {
					player.setCurrentTeam(null);
				}
			}
		}
		
		Player playerDb = playerDAO.selectByJsonId(player.getJsonId());
		if(playerDb == null) {
			playerDAO.insert(player);
		} else {
			if(playerDifferenceExists(playerDb, player)) {
				playerDAO.update(playerDb);
			} 
			player = playerDb;
		}
		return player;
	}
	
	/*@Deprecated
	public void save(GameModelMapper mapper, GameModel gameModel, ArrayList<Player> players, Team team, Game game) {
		for(Player player : players) {
			Position positionDb = positionDAO.selectByData(player.getPosition());
			if(positionDb == null) {
				positionDAO.insert(player.getPosition());
			} else {
				player.setPosition(positionDb);
			}
			
			if(player.getCurrentTeam() != null) {
				if(player.getCurrentTeam().getJsonId() == team.getJsonId()) {
					player.setCurrentTeam(team);
				} else {
					//current team can be team, that hasn't been saved to db before, if that happens just ignore it for now
					//as more data is stored it will catch up anyway
					Team teamDb = teamDAO.selectByJsonId(player.getCurrentTeam().getJsonId());
					if(teamDb != null) {
						player.setCurrentTeam(teamDb);
					} else {
						player.setCurrentTeam(null);
					}
				}
			}
			
			Player playerDb = playerDAO.selectByJsonId(player.getJsonId());
			if(playerDb == null) {
				playerDAO.insert(player);
			} else {
				if(playerDifferenceExists(playerDb, player)) {
					playerDAO.update(playerDb);
				} 
				player = playerDb;
			}
			
			statsapi.playerstatsmodel.PlayerStats playerStatsApi = gameModel.getPlayerStats(player.getJsonId());
			SkaterStats skaterStats = new SkaterStats();
			GoalieStats goalieStats = new GoalieStats();
			if(playerStatsApi == null) {
				skaterStats = null;
				goalieStats = null;
			} else {
				skaterStats = mapper.mapPlayerStats(playerStatsApi);
				goalieStats = mapper.mapGoalieStats(playerStatsApi);
			}
			
			Roster roster = new Roster();
			roster.setGame(game);
			roster.setTeam(team);
			roster.setPlayer(player);
			if(skaterStats != null) {
				roster.setSkaterStats(skaterStats);
			}
			if(goalieStats != null) {
				roster.setGoalieStats(goalieStats);
			}
			
			Roster rosterDb = rosterDAO.selectByGameTeamPlayer(roster);
			if(rosterDb == null) {
				if(roster.getGoalieStats() != null)
					goalieStatsDAO.insert(roster.getGoalieStats());
				if(roster.getSkaterStats() != null)
					skaterStatsDAO.insert(roster.getSkaterStats());
				rosterDAO.insert(roster);
				
			} else { 
				//goalie stats update
				if(rosterDb.getGoalieStats() != null && roster.getGoalieStats() == null) {
					LogWriter.writeLog(LogType.ALERT, "game id: " + game.getJsonId() + " roster id: " + rosterDb.getId() + ": goalie stats was somehow lost");
				}
				if(rosterDb.getGoalieStats() != null && roster.getGoalieStats() != null) {
					if(goalieStatsDifferenceExists(rosterDb.getGoalieStats(), roster.getGoalieStats())) {
						goalieStatsDAO.update(rosterDb.getGoalieStats());
					}
					roster.setGoalieStats(rosterDb.getGoalieStats());
				}
				//skater stats update
				if(rosterDb.getSkaterStats() != null && roster.getSkaterStats() == null) {
					LogWriter.writeLog(LogType.ALERT, "game id: " + game.getJsonId() + " roster id: " + rosterDb.getId() + ": skater stats was somehow lost");
				}
				if(rosterDb.getSkaterStats() != null && roster.getSkaterStats() != null) {
					if(skaterStatsDifferenceExists(rosterDb.getSkaterStats(), roster.getSkaterStats())) {
						skaterStatsDAO.update(rosterDb.getSkaterStats());
					}
					roster.setSkaterStats(rosterDb.getSkaterStats());
				}
			}
		}
	}*/
	
	private boolean playerDifferenceExists(Player playerDb, Player playerApi) {
		boolean res = false;
		if(!playerDb.getFirstName().equals(playerApi.getFirstName()) || !playerDb.getLastName().equals(playerApi.getLastName())) {
			playerDb.setFirstName(playerApi.getFirstName());
			playerDb.setLastName(playerApi.getLastName());
			res = true;
		}
		if(!nullableIntsEquals(playerDb.getPrimaryNumber(), playerApi.getPrimaryNumber())) {
			playerDb.setPrimaryNumber(playerApi.getPrimaryNumber());
			res = true;
		}
		if(!nullableTeamsEquals(playerDb.getCurrentTeam(), playerApi.getCurrentTeam())) {
			playerDb.setCurrentTeam(playerApi.getCurrentTeam());
			res = true;
		}
		if((playerDb.getPosition().getName() != playerApi.getPosition().getName()) || (playerDb.getPosition().getType() != playerApi.getPosition().getType())) {
			playerDb.setPosition(playerApi.getPosition());
			res = true;
		}
		return res;
	}
	
	/*@Deprecated
	private boolean goalieStatsDifferenceExists(GoalieStats gsDb, GoalieStats gsApi) {
		boolean res = false;
		if(gsDb.getTimeOnIce().equals(gsApi.getTimeOnIce())) {
			gsDb.setTimeOnIce(gsApi.getTimeOnIce());
			res = true;
		}
		if(gsDb.getPenaltyMinutes() != gsApi.getPenaltyMinutes()) {
			gsDb.setPenaltyMinutes(gsApi.getPenaltyMinutes());
			res = true;
		}
		if(gsDb.getShots() != gsApi.getShots()) {
			gsDb.setShots(gsApi.getShots());
			res = true;
		}
		if(gsDb.getSaves() != gsApi.getSaves()) {
			gsDb.setSaves(gsApi.getSaves());
			res = true;
		}
		if(gsDb.getPowerPlayShots() != gsApi.getPowerPlayShots()) {
			gsDb.setPowerPlayShots(gsApi.getPowerPlayShots());
			res = true;
		}
		if(gsDb.getPowerPlaySaves() != gsApi.getPowerPlaySaves()) {
			gsDb.setPowerPlaySaves(gsApi.getPowerPlaySaves());
			res = true;
		}
		if(gsDb.getShortHandedShots() != gsApi.getShortHandedShots()) {
			gsDb.setShortHandedShots(gsApi.getShortHandedShots());
			res = true;
		}
		if(gsDb.getShortHandedSaves() != gsApi.getShortHandedSaves()) {
			gsDb.setShortHandedSaves(gsApi.getShortHandedSaves());
			res = true;
		}
		return res;
	}*/
	
	/*@Deprecated
	private boolean skaterStatsDifferenceExists(SkaterStats sDb, SkaterStats sApi) {
		boolean res = false;
		if(!sDb.getTimeOnIce().equals(sApi.getTimeOnIce())) {
			sDb.setTimeOnIce(sApi.getTimeOnIce());
			res = true;
		}
		if(!sDb.getPowerPlayTimeOnIce().equals(sApi.getPowerPlayTimeOnIce())) {
			sDb.setPowerPlayTimeOnIce(sApi.getPowerPlayTimeOnIce());
			res = true;
		}
		if(!sDb.getShortHandedTimeOnIce().equals(sApi.getShortHandedTimeOnIce())) {
			sDb.setShortHandedTimeOnIce(sApi.getShortHandedTimeOnIce());
			res = true;
		}
		if(sDb.getPenaltyMinutes() != sApi.getPenaltyMinutes()) {
			sDb.setPenaltyMinutes(sApi.getPenaltyMinutes());
			res = true;
		}
		if(sDb.getPlusMinus() != sApi.getPlusMinus()) {
			sDb.setPlusMinus(sApi.getPlusMinus());
			res = true;
		}
		return res;
	}*/
	
	private boolean nullableIntsEquals(Integer iDb, Integer iApi) {
		if(iDb == null && iApi == null) 
			return true;
		if((iDb == null && iApi != null) || (iDb != null && iApi == null))
			return false;
		if(iDb.intValue() == iApi.intValue())
			return true;
		else
			return false;
	}
	
	private boolean nullableTeamsEquals(Team tDb, Team tApi) {
		if(tDb == null && tApi == null)
			return true;
		if((tDb == null && tApi != null) || (tDb != null && tApi == null))
			return false;
		if(tDb.getJsonId() == tApi.getJsonId())
			return true;
		else
			return false;
	}
}
