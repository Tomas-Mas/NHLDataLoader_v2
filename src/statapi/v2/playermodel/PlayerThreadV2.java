package statapi.v2.playermodel;

import main.GsonWorker;

public class PlayerThreadV2 extends Thread {
	
	int id;
	PlayerDetail player;
	
	public PlayerThreadV2(int id) {
		this.id = id;
	}
	
	@Override 
	public void run() {
		this.player = GsonWorker.getPlayerDetail(id);
	}
	
	public PlayerDetail getPlayer() {
		return player;
	}
}
