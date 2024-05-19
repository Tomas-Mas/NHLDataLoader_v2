package statapi.v2;

import com.google.gson.annotations.SerializedName;

public class DefaultName {

	@SerializedName("default")
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
