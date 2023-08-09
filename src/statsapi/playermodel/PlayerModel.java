package statsapi.playermodel;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import main.GsonWorker;

public class PlayerModel {

	private ArrayList<Player> homePlayers = new ArrayList<Player>();
	private ArrayList<Player> awayPlayers = new ArrayList<Player>();
	
	public ArrayList<Player> getHomePlayers() {
		return homePlayers;
	}
	
	public ArrayList<Player> getAwayPlayers() {
		return awayPlayers;
	}
	
	public void findPlayers(String gameId, JSONObject liveData) {
		HashMap<String, ArrayList<Integer>> playersId = GsonWorker.getListOfTeamPlayersIds(gameId, liveData);
		ArrayList<PlayerThread> threadsWithHomePlayers = new ArrayList<PlayerThread>();
		ArrayList<PlayerThread> threadsWithAwayPlayers = new ArrayList<PlayerThread>();
		
		for(int i : playersId.get("home")) {
			PlayerThread t = new PlayerThread(i);
			t.start();
			threadsWithHomePlayers.add(t);
		}
		for(int i : playersId.get("away")) {
			PlayerThread t = new PlayerThread(i);
			t.start();
			threadsWithAwayPlayers.add(t);
		}
		for(PlayerThread thread : threadsWithHomePlayers) {
			try {
				thread.join();
				this.homePlayers.add(thread.getPlayer());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(PlayerThread thread : threadsWithAwayPlayers) {
			try {
				thread.join();
				this.awayPlayers.add(thread.getPlayer());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
