package main;

import statapi.v2.DataModelV2;
import statapi.v2.GameDay;
import statapi.v2.GameModelV2;
import statapi.v2.GameWeek;
import statapi.v2.utils.TeamModelStorage;

public class Application {
	
	private GsonWorker gson;
	private GameController controller;
	private DataModelV2 season = new DataModelV2();
	private TeamModelStorage teamStorage;

	public Application() {
		this.gson = new GsonWorker();
		this.controller = new GameController();
		HibernateUtil.getSession();
		this.season = new DataModelV2();
		this.teamStorage = new TeamModelStorage();
	}
	
	public void initialize(String seasonYear) {
		//loads metadata (dates) for given season (season start/end, playoff start/end)
		season = gson.getDataModel2BySeasonStartYear(seasonYear);
		teamStorage = new TeamModelStorage(GsonWorker.getTeamApiData(season.getRegularSeasonStartDate(), seasonYear));
	}
	
	public void loadFromApiToDb(String fromDate, int firstXWeeks, int firstXDays, int firstXGames) {
		String weekStart = season.getRegularSeasonStartDate();
		int weekCount = 0;
		int dayCount = 0;
		int gameCount = 0;
		
		weekLoop:
		while(ComparatorUtil.dateBeforeDate2(weekStart, season.getPlayoffEndDate())) {
			
			//weeks check
			weekCount++;
			if(firstXWeeks > 0 && weekCount > firstXWeeks) {
				System.out.println("loading ended due to limit set on how many weeks to load! (only " + firstXWeeks + " weeks were set to load)");
				break weekLoop;
			}
			
			GameWeek week = gson.getGameWeekByStartDate(weekStart);
			System.out.println(weekCount + ". week with " + week.getGameWeek().size() + " days has " + week.getNumberOfGames() + " games; start date of week: " + weekStart);
			
			for(GameDay day : week.getGameWeek()) {
				//first x days check
				dayCount++;
				if(firstXDays > 0 && dayCount > firstXDays) {
					System.out.println("loading ended due to limit set on how many days to load! (only " + firstXDays + " days were set to load");
					break weekLoop;
				}
				
				System.out.println(day.getDate() + " - " + day.getNumberOfGames() + " games:");
				
				for(GameModelV2 game : day.getGames()) {
					//game check
					gameCount++;
					if(firstXGames > 0 && gameCount > firstXGames) {
						System.out.println("loading ended due to limit set on how many games to load! (only " + firstXGames + " games were set to load");
						break weekLoop;
					}
					
					//for now only interested in finished games
					if(!(game.getGameType().equals("2") || game.getGameType().equals("3")))
						continue;
					
					if(fromDate != null && ComparatorUtil.dateBeforeDate2(game.getStartTimeUTC(), fromDate)) {
						System.out.println( game.getStartTimeUTC() + " skipped due to app loads only games from date " + fromDate);
						continue;
					}
					
					long sTime = System.currentTimeMillis();
					
					//loads all the data from api
					game.fillGameData(teamStorage);
					
					//saves everything into db
					controller.saveGame(game);
					
					System.out.print((System.currentTimeMillis() - sTime));
					
					System.out.println("");
				}
			}
			weekStart = week.getNextStartDate();
		}
	}
	
}
