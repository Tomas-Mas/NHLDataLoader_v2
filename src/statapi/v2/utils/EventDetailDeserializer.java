package statapi.v2.utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EventDetailDeserializer implements JsonDeserializer<Map<String, Object>> {

	@Override
	public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Map<String, Object> detailMap = new HashMap<String, Object>();
		JsonObject jsonObject = json.getAsJsonObject();
		
		for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			String key = entry.getKey();
			JsonElement value = entry.getValue();
			Object parsedValue = context.deserialize(value, Object.class);
			detailMap.put(key, parsedValue);
		}
		
		return detailMap;
	}
}
