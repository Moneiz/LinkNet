package org.piwel.linknet.data;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.piwel.linknet.mlp.DataCollection;
import org.piwel.linknet.mlp.IHM;
import org.piwel.linknet.util.ParserUtil;

public abstract class SimpleData {

	public final String name;
	public int nbNeuronIn;
	public int nbNeuronOut;
	public final int[] nbMiddleHiddenNeuron;
	protected DataCollection datapoints;
	
	public SimpleData(JSONObject json) {
		
		this.name = json.get("name").toString();
		
		
		JSONArray array = (JSONArray) json.get("nbHN");
		this.nbMiddleHiddenNeuron = new int[array.size()];
		for(int i = 0; i < array.size();i++) {
			this.nbMiddleHiddenNeuron[i] = ParserUtil.objectToInt(array.get(i));
		}
		this.nbNeuronIn = ParserUtil.objectToInt(json.get("nbIN"));
		this.nbNeuronOut = ParserUtil.objectToInt(json.get("nbOUT"));
		
		long time = System.currentTimeMillis();
		IHM.info("Entrance MakeDataPoint of " + name + " of type "+this.getClass());
		makeDataPoint((JSONArray) json.get("datas"));
		IHM.info("Exit MakeDataPoint in "+ (int) (System.currentTimeMillis()-time) + " ms");
	}
	public abstract void makeDataPoint(JSONArray array);
	public DataCollection getDatapoints() {
		return datapoints;
	}
}
