package statapi.v2.playermodel;

public class PlayerBasic {

	private String playerId;
	private String position;
	private String toi;
	private int plusMinus;
	
	public String getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getToi() {
		return toi;
	}
	
	public void setToi(String toi) {
		this.toi = toi;
	}
	
	public int getPlusMinus() {
		return plusMinus;
	}
	
	public void setPlusMinus(int plusMinus) {
		this.plusMinus = plusMinus;
	}
}
