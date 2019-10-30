package org.piwel.linknet.data;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class SimpleData {
	public final int nbNeuronIn;
	public final int nbNeuronOut;
	public final int[] nbMiddleHiddenNeuron;
	public SimpleData(JSONObject json) {
		JSONArray array = (JSONArray) json.get("nbHN");
		this.nbMiddleHiddenNeuron = new int[array.size()];
		for(int i = 0; i < array.size();i++) {
			this.nbMiddleHiddenNeuron[i] = (int)array.get(i);
		}
		this.nbNeuronIn = (int) json.get("nbIN");
		this.nbNeuronOut = (int) json.get("nbON");
		makeDataPoint();
	}
	public abstract void makeDataPoint();
}
