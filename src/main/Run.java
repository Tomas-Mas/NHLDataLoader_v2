package main;

import statsapi.datamodel.DataModel;
import statsapi.datamodel.DataModelDates;
import statsapi.gamemodel.GameModel;

public class Run {

	public static void main(String[] args) {
		GsonWorker gson = new GsonWorker();
		GameController controller = new GameController();
		HibernateUtil.getSession();
		
		//("2015-10-07", "2016-06-20");	//2015/2016
		
		DataModel data = new DataModel();
		data = gson.getDataByDates("2016-04-13", "2016-06-15");
		
		
		/*GameModel testGame = data.getDates().get(0).getGames().get(0);
		testGame.fillGameDetails();
		controller.saveGame(testGame);*/
		
		//testGame.print();
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
		OopsieCounter.print();
	}

}
