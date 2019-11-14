package org.piwel.linknet.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.piwel.linknet.mlp.DataCollection;
import org.piwel.linknet.mlp.IHM;
import org.piwel.linknet.util.ParserUtil;

public class CharData extends SimpleData {

	public CharData(JSONObject json) {
		super(json);
		nbNeuronIn *= 8;
		nbNeuronOut *= 8;
		
	}

	@Override
	public void makeDataPoint(JSONArray json) {

		double[][] content = new double[json.size()][nbNeuronIn*8+nbNeuronOut*8];
		for(int i = 0; i < json.size();i++) {
			JSONObject o = (JSONObject) json.get(i);
			JSONArray in = (JSONArray) o.get("in");
			JSONArray out = (JSONArray) o.get("out");
			
			char a;
			
			for(int j = 0; j < nbNeuronIn;j++) {
				a = in.get(j).toString().charAt(0);
				double[] bitsChar = charToArrayDouble(a);
				for(int k = 0; k < 8; k++) {
					content[i][j+k] = bitsChar[k];
				}
			}
			for(int j = 0; j < nbNeuronOut;j++) {
				a = out.get(j).toString().charAt(0);
				double[] bitsChar = charToArrayDouble(a);
				for(int k = 0; k < 8; k++) {
					content[i][j+k+nbNeuronIn*8] = bitsChar[k];
				}
			}
		}
		datapoints = new DataCollection(content, nbNeuronOut*8, 1.0f);
		
	}

	public double[] charToArrayDouble(char a) {
		int aInt = a;
		double[] result = new double[8];
		for(int i = 8; i >= 1;i--) {
			int step = 1;
			int tmp = 0;
			for(int j = 1; j < i; j++) {
				step <<= 1;
			}
			if(step <= aInt) {
				tmp = 1;
				aInt -= step;
			}
			result[8-i] = tmp;
		}
		return result;
	}
}
