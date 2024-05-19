package statapi.v2.playermodel;

import java.util.ArrayList;

public class PlayerModelV2 {

	private ArrayList<PlayerDetail> homePlayers;
	private ArrayList<PlayerDetail> awayPlayers;

	public void findPlayers(TeamRosters rosters) {
		homePlayers = new ArrayList<PlayerDetail>();
		awayPlayers = new ArrayList<PlayerDetail>();
		ArrayList<PlayerThreadV2> homeThreads = new ArrayList<PlayerThreadV2>(rosters.getHome().size());
		ArrayList<PlayerThreadV2> awayThreads = new ArrayList<PlayerThreadV2>(rosters.getAway().size());
		
		for(PlayerBasic player : rosters.getHome()) {
			PlayerThreadV2 thread = new PlayerThreadV2(Integer.valueOf(player.getPlayerId()));
			thread.start();
			homeThreads.add(thread);
		}
		for(PlayerBasic player : rosters.getAway()) {
			PlayerThreadV2 thread = new PlayerThreadV2(Integer.valueOf(player.getPlayerId()));
			thread.start();
			awayThreads.add(thread);
		}
		
		for(PlayerThreadV2 thread : homeThreads) {
			try {
				thread.join();
				this.homePlayers.add(thread.getPlayer());
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(PlayerThreadV2 thread : awayThreads) {
			try {
				thread.join();
				this.awayPlayers.add(thread.getPlayer());
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void print() {
		for(PlayerDetail p : homePlayers) {
			p.print();
		}
		for(PlayerDetail p : awayPlayers) {
			p.print();
		}
	}
	
	public ArrayList<PlayerDetail> getHomePlayers() {
		return homePlayers;
	}

	public void setHomePlayers(ArrayList<PlayerDetail> homePlayers) {
		this.homePlayers = homePlayers;
	}

	public ArrayList<PlayerDetail> getAwayPlayers() {
		return awayPlayers;
	}

	public void setAwayPlayers(ArrayList<PlayerDetail> awayPlayers) {
		this.awayPlayers = awayPlayers;
	}

}
