package statsapi.datamodel;

import java.util.ArrayList;

public class DataModel {

	private String totalItems;
	private ArrayList<DataModelDates> dates = new ArrayList<DataModelDates>();
	
	public String getTotalItems() {
		return totalItems;
	}
	
	public ArrayList<DataModelDates> getDates() {
		return dates;
	}
}
