package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import statsapi.datamodel.DataModel;
import statsapi.eventmodel.Event;
import statsapi.eventmodel.EventModel;
import statsapi.playermodel.Player;
import statsapi.playerstatsmodel.GoalieStats;
import statsapi.playerstatsmodel.PlayerStats;
import statsapi.playerstatsmodel.SkaterStats;
import statsapi.teammodel.TeamModel;

public class GsonWorker {

	private static Gson gson;
	
	public GsonWorker() {
		gson = new Gson();
	}
	
	//game model
	
	public DataModel getDataByDates(String startDate, String endDate) {
		String url = "http://statsapi.web.nhl.com/api/v1/schedule?teamId=&startDate=" + startDate + "&endDate=" + endDate;
		String jsonText = GsonWorker.readURL(url);
		DataModel dataModel = new DataModel();
		dataModel = gson.fromJson(jsonText, DataModel.class);
		return dataModel;
	}
	
	public static JSONObject getJsonObjectWithGameDetail(String gameId) {
		String url = "http://statsapi.web.nhl.com/api/v1/game/" + gameId + "/feed/live";
		JSONObject data = new JSONObject(GsonWorker.readURL(url));
		JSONObject liveData = data.getJSONObject("liveData");
		return liveData;
	}
	
	//team model
	
	public static TeamModel getTeamModelById(String id) {
		String url = "http://statsapi.web.nhl.com/api/v1/teams/" + id;
		String teamData = GsonWorker.readURL(url);
		TeamModel tm = new TeamModel();
		tm = gson.fromJson(teamData, TeamModel.class);
		return tm;
	}
	
	//player model
	
	public static Player getPlayerDataByPlayerId(String playerId) {
		String url = "http://statsapi.web.nhl.com/api/v1/people/" + playerId;
		String playerData = GsonWorker.readURL(url);
		Player p = new Player();
		p = gson.fromJson(playerData, Player.class);
		return p;
	}
	
	public static HashMap<String, ArrayList<Integer>> getListOfTeamPlayersIds(String gameId, JSONObject liveData) {
		HashMap<String, ArrayList<Integer>> ret = new HashMap<String, ArrayList<Integer>>();
		JSONObject jsonTeam = liveData.getJSONObject("boxscore").getJSONObject("teams");
		ret.put("home", getIdsFromOneTeam("home", jsonTeam));
		ret.put("away", getIdsFromOneTeam("away", jsonTeam));
		return ret;
	}
	
	private static ArrayList<Integer> getIdsFromOneTeam(String team, JSONObject jsonTeam) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		JSONArray json = new JSONArray();
		
		json = jsonTeam.getJSONObject(team).getJSONArray("skaters");
		for(int i = 0; i < json.length(); i++) 
			players.add(json.getInt(i));
		json = jsonTeam.getJSONObject(team).getJSONArray("goalies");
		for(int i = 0; i < json.length(); i++)
			players.add(json.getInt(i));
		
		return players;
	}
	
	//event model
	
	public static EventModel getEventsData(JSONObject liveData) {
		//events: [Game Scheduled, Period Ready, Period Start, Faceoff, Hit, Stoppage, Shot, 
		//Blocked Shot, Missed Shot, Takeaway, Giveaway, Goal, Penalty, Period End, Period Official, Game End]
		ArrayList<String> eventTypes = new ArrayList<String>();
		eventTypes.add("Goal");
		eventTypes.add("Shot");
		eventTypes.add("Blocked Shot");
		eventTypes.add("Missed Shot");
		eventTypes.add("Penalty");
		eventTypes.add("Faceoff");
		eventTypes.add("Hit");
		eventTypes.add("Takeaway");
		eventTypes.add("Giveaway");
		EventModel gameEventModel = new EventModel();
		JSONArray allPlays = liveData.getJSONObject("plays").getJSONArray("allPlays");
		for(int i = 0; i<allPlays.length(); i++) {
			JSONObject play = allPlays.getJSONObject(i);
			String eventName = play.getJSONObject("result").getString("event");
			if(eventTypes.contains(eventName)) {
				Event event = gson.fromJson(play.toString(), Event.class);
				gameEventModel.addEvent(event);
			}
		}
		return gameEventModel;
	}
	
	//player stats
	
	public static ArrayList<PlayerStats> getPlayerStats(JSONObject liveData) {
		ArrayList<PlayerStats> playersStats = new ArrayList<PlayerStats>();
		JSONObject teams = liveData.getJSONObject("boxscore").getJSONObject("teams");
		JSONObject awayPlayers = teams.getJSONObject("away").getJSONObject("players");
		JSONObject homePlayers = teams.getJSONObject("home").getJSONObject("players");
		
		JSONObject stats = new JSONObject();
		if(awayPlayers.names() != null) {
			for(Object o : awayPlayers.names()) {
				String nodeName = o.toString();
				stats = awayPlayers.getJSONObject(nodeName).getJSONObject("stats");
				if(stats.has("skaterStats") || stats.has("goalieStats")) {
					PlayerStats playerStats = new PlayerStats();
					playerStats.setId(awayPlayers.getJSONObject(nodeName).getJSONObject("person").getInt("id"));
					if(stats.has("skaterStats") && !stats.has("goalieStats")) {
						SkaterStats skaterStats = gson.fromJson(stats.getJSONObject("skaterStats").toString(), SkaterStats.class);
						playerStats.setSkaterStats(skaterStats);
						playerStats.setGoalieStats(null);
					} else if(stats.has("goalieStats") && !stats.has("skaterStats")) {
						GoalieStats goalieStats = gson.fromJson(stats.getJSONObject("goalieStats").toString(), GoalieStats.class);
						playerStats.setGoalieStats(goalieStats);
						playerStats.setSkaterStats(null);
					} else {
						SkaterStats skaterStats = gson.fromJson(stats.getJSONObject("skaterStats").toString(), SkaterStats.class);
						playerStats.setSkaterStats(skaterStats);
						GoalieStats goalieStats = gson.fromJson(stats.getJSONObject("goalieStats").toString(), GoalieStats.class);
						playerStats.setGoalieStats(goalieStats);
					}
					playersStats.add(playerStats);
				}
			}
			for(Object o : homePlayers.names()) {
				String nodeName = o.toString();
				stats = homePlayers.getJSONObject(nodeName).getJSONObject("stats");
				if(stats.has("skaterStats") || stats.has("goalieStats")) {
					PlayerStats playerStats = new PlayerStats();
					playerStats.setId(homePlayers.getJSONObject(nodeName).getJSONObject("person").getInt("id"));
					if(stats.has("skaterStats") && !stats.has("goalieStats")) {
						SkaterStats skaterStats = gson.fromJson(stats.getJSONObject("skaterStats").toString(), SkaterStats.class);
						playerStats.setSkaterStats(skaterStats);
						playerStats.setGoalieStats(null);
					} else if(stats.has("goalieStats") && !stats.has("skaterStats")) {
						GoalieStats goalieStats = gson.fromJson(stats.getJSONObject("goalieStats").toString(), GoalieStats.class);
						playerStats.setGoalieStats(goalieStats);
						playerStats.setSkaterStats(null);
					} else {
						SkaterStats skaterStats = gson.fromJson(stats.getJSONObject("skaterStats").toString(), SkaterStats.class);
						playerStats.setSkaterStats(skaterStats);
						GoalieStats goalieStats = gson.fromJson(stats.getJSONObject("goalieStats").toString(), GoalieStats.class);
						playerStats.setGoalieStats(goalieStats);
					}
					playersStats.add(playerStats);
				}
			}
			return playersStats;
		} else {
			return null;
		}
	}
	
	private static String readURL(String url) {
		final String USER_AGENT = "Mozilla/5.0";
		StringBuffer response = new StringBuffer();
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			if(!con.getResponseMessage().equals("OK"))
				System.out.println(con.getResponseMessage());
			
			BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
	
			while ((inputLine = input.readLine()) != null) {
				response.append(inputLine);
				response.append("\n");
			}
			input.close();
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return response.toString();
	}
}
