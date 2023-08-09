package statsapi.eventmodel;

import java.util.ArrayList;

public class EventModel {

	private ArrayList<Event> events = new ArrayList<Event>();
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public ArrayList<Event> getEvents() {
		return events;
	}
}
