package main;

public class Run {

	public static void main(String[] args) {
		Application app = new Application();
		
		//first year of loaded season (example: if you want to load season 2014/2015 you want to put 2014 here)
		String seasonYear = "2015";
		
		//only loads games from given date (including given date) to end of season for given season (format: yyyy-mm-dd); null to start from start of season
		String fromDate = null;
		
		//only loads games played first week of season (mostly for testing purposes, firstXWeeks <= 0 loads whole season)
		int firstXWeeks = 1;
		
		//same as weeks but days
		int firstXDays = 0;
		
		//same as weeks/days but games
		int firstXGames = 0;
		
		app.initialize(seasonYear);
		
		app.loadFromApiToDb(fromDate, firstXWeeks, firstXDays, firstXGames);
		
		OopsieCounter.print();
		
	}
}
