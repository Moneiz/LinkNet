package org.piwel.linknet.data;


import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.piwel.linknet.util.ParserUtil;

public abstract class SimpleData {

	public final String name;
	public final int nbNeuronIn;
	public final int nbNeuronOut;
	public final int[] nbMiddleHiddenNeuron;
	public String[] datapoints;
	
	public SimpleData(JSONObject json) {
		
		this.name = json.get("name").toString();
		
		
		JSONArray array = (JSONArray) json.get("nbHN");
		this.nbMiddleHiddenNeuron = new int[array.size()];
		for(int i = 0; i < array.size();i++) {
			this.nbMiddleHiddenNeuron[i] = ParserUtil.objectToInt(array.get(i));
		}
		this.nbNeuronIn = ParserUtil.objectToInt(json.get("nbIN"));
		this.nbNeuronOut = ParserUtil.objectToInt(json.get("nbOUT"));
		makeDataPoint();
	}
	public abstract void makeDataPoint();
}
