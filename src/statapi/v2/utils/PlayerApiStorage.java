package statapi.v2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import statapi.v2.playermodel.PlayerDetail;

public class PlayerApiStorage {

	private static Map<Integer, List<PlayerDetail>> playerMap = new HashMap<Integer, List<PlayerDetail>>();
	private static int foundPlayers = 0;
	
	public static void addPlayer(PlayerDetail player) {
		int key = Integer.valueOf(player.getPlayerId()) % 30;
		if(playerMap.get(key) == null) {
			List<PlayerDetail> l = new ArrayList<PlayerDetail>();
			l.add(player);
			playerMap.put(key, l);
		} else {
			playerMap.get(key).add(player);
		}
	}
	
	public static PlayerDetail getPlayer(int id) {
		int key = id % 30;
		
		if(playerMap.get(key) == null)
			return null;
		
		for(PlayerDetail player : playerMap.get(key)) {
			if(Integer.valueOf(player.getPlayerId()) == id) {
				foundPlayers++;
				return player;
			}
		}
		return null;
	}
	
	public static int getCount() {
		int count = 0;
		for(int i : playerMap.keySet()) {
			count += playerMap.get(i).size();
		}
		return count;
	}
	
	public static void resetFoundCount() {
		foundPlayers = 0;
	}
	public static int getFoundCount() {
		return foundPlayers;
	}
}
