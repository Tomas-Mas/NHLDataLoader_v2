package statapi.v2.utils;

public class TeamLocationMapper {
	
	public static String getLocation(String shortName) {
		if(shortName.equals("NY Rangers"))
			return "New York";
		else if(shortName.equals("St Louis"))
			return "St. Louis";
		else if(shortName.equals("NY Islanders"))
			return "New York";
		else
			return shortName;
	}
}
