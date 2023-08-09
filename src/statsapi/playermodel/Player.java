package statsapi.playermodel;

import java.util.ArrayList;

public class Player {

	private ArrayList<PlayerData> people = new ArrayList<PlayerData>();
	
	public PlayerData getPlayerData() {
		return people.get(0);
	}
}
