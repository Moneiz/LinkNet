package org.piwel.linknet.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.piwel.linknet.mlp.DataCollection;
import org.piwel.linknet.mlp.IHM;
import org.piwel.linknet.util.ParserUtil;

public class BinaryData extends SimpleData{

	public BinaryData(JSONObject json) {
		super(json);
	}

	public void makeDataPoint(JSONArray json) {
		
		double[][] content = new double[json.size()][nbNeuronIn+nbNeuronOut];
		for(int i = 0; i < json.size();i++) {
			JSONObject o = (JSONObject) json.get(i);
			JSONArray in = (JSONArray) o.get("in");
			JSONArray out = (JSONArray) o.get("out");
			
			for(int j = 0; j < nbNeuronIn;j++) {
				content[i][j] = ParserUtil.objectToInt(in.get(j));
			}
			for(int j = 0; j < nbNeuronOut;j++) {
				content[i][j+nbNeuronIn] = ParserUtil.objectToInt(out.get(j));
			}
		}
		datapoints = new DataCollection(content, nbNeuronOut, 1.0f);
	}

}
