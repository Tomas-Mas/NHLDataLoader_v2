package statapi.v2.playermodel;

public enum PositionEnum {
	CENTER("C", "Center", "Forward", "C"),
	GOALIE("G", "Goalie", "Goalie", "G"),
	RIGHTWING("R", "Right Wing", "Forward", "RW"),
	LEFTWING("L", "Left Wing", "Forward", "LW"),
	DEFENSEMAN("D", "Defenseman", "Defenseman", "D");
	
	private String key;
	private String name;
	private String type;
	private String abbr;
	
	private PositionEnum(String key, String name, String type, String abbr) {
		this.key = key;
		this.name = name;
		this.type = type;
		this.abbr = abbr;
	}
	
	public static PositionEnum valueOfKey(String key) {
		for(PositionEnum p : values()) {
			if(p.key.equals(key))
				return p;
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getAbbr() {
		return abbr;
	}

}
