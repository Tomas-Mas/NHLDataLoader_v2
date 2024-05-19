package statapi.v2.eventmodel;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class EventModelV2 {

	@SerializedName("sortOrder")
	private String eventId;
	private Period periodDescriptor;
	private String timeInPeriod;
	private String situationCode;
	private String typeDescKey;
	private Map<String, Object> details;
	
	public String getEventId() {
		return eventId;
	}
	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public Period getPeriodDescriptor() {
		return periodDescriptor;
	}
	
	public void setPeriodDescriptor(Period periodDescriptor) {
		this.periodDescriptor = periodDescriptor;
	}
	
	public String getTimeInPeriod() {
		return timeInPeriod;
	}
	
	public void setTimeInPeriod(String timeInPeriod) {
		this.timeInPeriod = timeInPeriod;
	}
	
	public String getSituationCode() {
		return situationCode;
	}

	public void setSituationCode(String situationCode) {
		this.situationCode = situationCode;
	}

	public String getTypeDescKey() {
		return typeDescKey;
	}
	
	public void setTypeDescKey(String typeDescKey) {
		this.typeDescKey = typeDescKey;
	}
	
	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}
	
	public Map<String, Object> getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "EventModelV2 [eventId=" + eventId + ", period=" + periodDescriptor.getNumber() + "-" + periodDescriptor.getPeriodType() + ", timeInPeriod="
				+ timeInPeriod + ", typeDescKey=" + typeDescKey + "]";
	}
	
	public String detailsToString() {
		String ret = "";
		
		if(details == null)
			return ret;
		
		for(String s : details.keySet()) {
			ret += " - " + s + ":" + details.get(s);
		}
		return ret;
	}
}
