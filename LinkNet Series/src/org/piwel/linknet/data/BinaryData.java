package org.piwel.linknet.data;

import org.json.simple.JSONObject;

public class BinaryData extends SimpleData{

	public BinaryData(JSONObject json) {
		super(json);
	}

	public void makeDataPoint() {
		System.out.print("INFO - Entrance MakeDataPoint of " + name + " of type binary");
	}

}
