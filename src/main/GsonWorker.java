package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import statapi.v2.DataModelV2;
import statapi.v2.GameWeek;
import statapi.v2.eventmodel.EventModelV2;
import statapi.v2.eventmodel.GoalDetail;
import statapi.v2.playermodel.PlayerBasic;
import statapi.v2.playermodel.PlayerDetail;
import statapi.v2.playermodel.TeamRosters;
import statapi.v2.teammodel.FranchiseApi;
import statapi.v2.teammodel.TeamApi;
import statapi.v2.teammodel.TeamApiData;
import statapi.v2.teammodel.TeamLeagueApi;
import statapi.v2.utils.EventDetailDeserializer;
import statapi.v2.utils.PlayerApiStorage;
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
	//v1
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
	
	//v2
	public DataModelV2 getDataModel2BySeasonStartYear(String year) {
		DataModelV2 model = new DataModelV2();
		String url = "https://api-web.nhle.com/v1/schedule/" + year + "-12-12";
		model = gson.fromJson(GsonWorker.readURL(url), DataModelV2.class);
		return model;
	}
	
	public GameWeek getGameWeekByStartDate(String weekStart) {
		String url = "https://api-web.nhle.com/v1/schedule/" + weekStart;
		return gson.fromJson(GsonWorker.readURL(url), GameWeek.class);
	}
	
	//team model
	//v1
	public static TeamModel getTeamModelById(String id) {
		String url = "http://statsapi.web.nhl.com/api/v1/teams/" + id;
		String teamData = GsonWorker.readURL(url);
		TeamModel tm = new TeamModel();
		tm = gson.fromJson(teamData, TeamModel.class);
		return tm;
	}
	
	//v2
	public static ArrayList<TeamApiData> getTeamApiData(String regulationStartDate, String season) {
		ArrayList<TeamApiData> teamModels = new ArrayList<TeamApiData>();
		ArrayList<TeamApi> teams = new ArrayList<TeamApi>();
		ArrayList<FranchiseApi> franchises = new ArrayList<FranchiseApi>();
		ArrayList<TeamLeagueApi> league = new ArrayList<TeamLeagueApi>();
		
		//fetching data
		JSONArray dataArr = new JSONObject(GsonWorker.readURL("https://api.nhle.com/stats/rest/en/team")).getJSONArray("data");
		for(Object o : dataArr) {
			teams.add(gson.fromJson(o.toString(), TeamApi.class));
		}
		dataArr = new JSONObject(GsonWorker.readURL("https://api.nhle.com/stats/rest/en/franchise")).getJSONArray("data");
		for(Object o : dataArr) {
			franchises.add(gson.fromJson(o.toString(), FranchiseApi.class));
		}
		dataArr = new JSONObject(GsonWorker.readURL("https://api-web.nhle.com/v1/standings/" + regulationStartDate)).getJSONArray("standings");
		for(Object o : dataArr) {
			league.add(gson.fromJson(o.toString(), TeamLeagueApi.class));
		}
		
		//building list
		for(TeamLeagueApi l : league) {
			TeamApiData team = new TeamApiData();
			team.setLeague(l);
			for(TeamApi t : teams) {
				if(t.getTriCode().equals(l.getTeamAbbrev())) {
					team.setTeam(t);
					break;
				}
			}
			for(FranchiseApi f : franchises) {
				if(team.getFranchiseId().equals(f.getId())) {
					team.setFranchise(f);
					break;
				}
			}
			teamModels.add(team);
		}
		
		return teamModels;
	}
	
	//player model
	//v1
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
	
	//v2
	public static TeamRosters getGameRosters(String gameId) {
		TeamRosters rosters = new TeamRosters();
		String url = "https://api-web.nhle.com/v1/gamecenter/" + gameId + "/boxscore";
		JSONObject rostersData = new JSONObject(GsonWorker.readURL(url));
		rosters.setAway(getPlayerList(rostersData.getJSONObject("playerByGameStats").getJSONObject("awayTeam")));
		rosters.setHome(getPlayerList(rostersData.getJSONObject("playerByGameStats").getJSONObject("homeTeam")));
		return rosters;
	}
	
	public static PlayerDetail getPlayerDetail(int playerId) {
		PlayerDetail player = PlayerApiStorage.getPlayer(playerId);
		if(player == null) {
			String playerData = GsonWorker.readURL("https://api-web.nhle.com/v1/player/" + playerId + "/landing");
			player = gson.fromJson(playerData, PlayerDetail.class);
			PlayerApiStorage.addPlayer(player);
		}
		return player;
	}
	
	private static ArrayList<PlayerBasic> getPlayerList(JSONObject teamRoster) {
		ArrayList<PlayerBasic> players = new ArrayList<PlayerBasic>();
		for(Object o : teamRoster.getJSONArray("forwards")) {
			players.add(gson.fromJson(o.toString(), PlayerBasic.class));
		}
		for(Object o : teamRoster.getJSONArray("defense")) {
			players.add(gson.fromJson(o.toString(), PlayerBasic.class));
		}
		for(Object o : teamRoster.getJSONArray("goalies")) {
			players.add(gson.fromJson(o.toString(), PlayerBasic.class));
		}
		return players;
	}
	
	//event model
	//v1
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
	
	//v2
	public static ArrayList<EventModelV2> getEvents(String gameId) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(), new EventDetailDeserializer());
		Gson fancyGson = gsonBuilder.create();
		
		ArrayList<EventModelV2> events = new ArrayList<EventModelV2>();
		String url = "https://api-web.nhle.com/v1/gamecenter/" + gameId + "/play-by-play";
		JSONArray apiEvents = new JSONObject(GsonWorker.readURL(url)).getJSONArray("plays");
		for(Object apiEvent : apiEvents) {
			EventModelV2 event = fancyGson.fromJson(apiEvent.toString(), EventModelV2.class);
			events.add(event);
		}
		
		return events;
	}
	
	public static ArrayList<GoalDetail> getGoals(String gameId) {
		ArrayList<GoalDetail> goals = new ArrayList<GoalDetail>();
		String url = "https://api-web.nhle.com/v1/wsc/game-story/" + gameId;
		JSONArray scoring = new JSONObject(GsonWorker.readURL(url)).getJSONObject("summary").getJSONArray("scoring");
		
		for(Object s : scoring) {
			int period = ((JSONObject) s).getJSONObject("periodDescriptor").getInt("number");
			JSONArray goalArr = ((JSONObject) s).getJSONArray("goals");
			for(Object g : goalArr) {
				JSONObject goalObj = (JSONObject) g;
				goals.add(new GoalDetail(period, goalObj.getString("timeInPeriod"), goalObj.getString("strength"), goalObj.getString("goalModifier")));
			}
		}
		
		return goals;
	}
	
	//player stats
	//v1
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
