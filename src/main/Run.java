package main;

import statapi.v2.DataModelV2;
import statapi.v2.GameDay;
import statapi.v2.GameModelV2;
import statapi.v2.GameWeek;
import statapi.v2.utils.TeamModelStorage;

public class Run {

	public static void main(String[] args) {
		String seasonYear = "2015";
		GsonWorker gson = new GsonWorker();
		GameController controller = new GameController();
		HibernateUtil.getSession();
		
		DataModelV2 season = new DataModelV2();
		//loads milestone dates for given season (season start/end, playoff start/end)
		season = gson.getDataModel2BySeasonStartYear(seasonYear);
		
		//prep things
		TeamModelStorage teamStorage = new TeamModelStorage(GsonWorker.getTeamApiData(season.getRegularSeasonStartDate(), seasonYear));
		
		String weekStart = season.getRegularSeasonStartDate();
		int weekCount = 0;
		while(ComparatorUtil.dateBeforeDate2(weekStart, season.getPlayoffEndDate())) {
			weekCount++;
			GameWeek week = gson.getGameWeekByStartDate(weekStart);
			System.out.println(weekCount + ". week with " + week.getGameWeek().size() + " days has " + week.getNumberOfGames() + " games; start date of week: " + weekStart);
			
			for(GameDay day : week.getGameWeek()) {
				System.out.println(day.getDate() + " - " + day.getNumberOfGames() + " games:");
				for(GameModelV2 game : day.getGames()) {
					if(!(game.getGameType().equals("2") || game.getGameType().equals("3")))
						continue;
					
					/*if(ComparatorUtil.dateBeforeDate2(game.getStartTimeUTC(), "2016-03-20")) {
						System.out.println( game.getStartTimeUTC() + " skipped");
						continue;
					}*/
					
					long sTime = System.currentTimeMillis();
					game.fillGameData(teamStorage);
					controller.saveGame(game);
					System.out.print((System.currentTimeMillis() - sTime));
					
					System.out.println("");
				}
			}
			
			weekStart = week.getNextStartDate();
		}
		
		/*DataModel data = new DataModel();
		data = gson.getDataByDates("2017-04-01", "2017-04-11");	//11-30 01-21
		
		int counter = 1;
		for(DataModelDates date : data.getDates()) {
			System.out.println("\n\ndate: " + date.getDate());
			counter = 1;
			for(GameModel game : date.getGames()) {
				System.out.print("\n" + counter + "/" + date.getTotalItems());
				counter ++;
				
				if(game.getGameType().equals("A")) {	//all stars game is useless
					continue;
				}
				
				game.fillGameDetails();
				controller.saveGame(game);
			}
		}
		OopsieCounter.print();*/
	}
}
