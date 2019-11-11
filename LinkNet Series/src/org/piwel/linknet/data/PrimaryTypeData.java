package org.piwel.linknet.data;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.piwel.linknet.mlp.DataCollection;
import org.piwel.linknet.util.ParserUtil;

public abstract class PrimaryTypeData extends SimpleData{

	public PrimaryTypeData(JSONObject json) {
		super(json);
	}

	/*
	public String[][] getIOJsonDatas(JSONObject json){
		String[][] content = new String[json.size()][];
		for(int i = 0; i < json.size();i++) {
			JSONObject o = (JSONObject) json.get(i);
			JSONArray in = (JSONArray) o.get("in");
			JSONArray out = (JSONArray) o.get("out");
		
			content[i] = new String[nbNeuronIn+nbNeuronOut];
			
			for(int j = 0; j < nbNeuronIn;j++) {
				content[i][j] = in.get(j).toString();
			}
			for(int j = 0; j < nbNeuronOut;j++) {
				content[i][j+nbNeuronIn] = out.get(j).toString();
			}
		}
		return content;
	}
	*/
}
