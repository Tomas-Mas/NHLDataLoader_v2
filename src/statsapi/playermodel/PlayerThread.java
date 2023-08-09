package statsapi.playermodel;

import main.GsonWorker;

public class PlayerThread extends Thread {

	int playerId;
	Player playerData;
	
	public PlayerThread(int id) {
		this.playerId = id;
		this.playerData = new Player();
	}
	
	public void run() {
		this.playerData = GsonWorker.getPlayerDataByPlayerId(String.valueOf(playerId));
	}
	public Player getPlayer() {
		return this.playerData;
	}
	
}
