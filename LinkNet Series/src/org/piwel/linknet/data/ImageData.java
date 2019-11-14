package org.piwel.linknet.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.piwel.linknet.data.image.BMPFormatter;
import org.piwel.linknet.mlp.DataCollection;
import org.piwel.linknet.mlp.IHM;

public class ImageData extends SimpleData {

	public ImageData(JSONObject json) {
		super(json);
		nbNeuronIn *= 8;
		nbNeuronOut = 2;
		
	}

	@Override
	public void makeDataPoint(JSONArray json) {
		IHM.info("Entrance MakeDataPoint of " + name + " of type IMAGE");
		
		JSONObject o = (JSONObject) json.get(0);
		JSONArray in = (JSONArray) o.get("in");
		JSONArray out = (JSONArray) o.get("out");
		
		String link = in.get(0).toString();
		
		BMPFormatter img = new BMPFormatter(link);
		nbNeuronIn = img.imagesPixels.length;
		double[][] content = new double[json.size()][img.imagesPixels.length * 8 + nbNeuronOut*8];
		for(int i = 0; i < json.size();i++) {
			o = (JSONObject) json.get(i);
			in = (JSONArray) o.get("in");
			out = (JSONArray) o.get("out");
			
			img = new BMPFormatter(link);
			
			char a;
			
			
			for(int j = 0; j < img.imagesPixels.length;j++) {
				a = (char) img.imagesPixels[j];
				double[] bitsChar = charToArrayDouble(a);
				for(int k = 0; k < 8; k++) {
					content[i][j*8+k] = bitsChar[k];
				}
			}
			a = out.get(0).toString().charAt(0);
			content[i][nbNeuronIn*8] = a == 'o' ? 1.0f : 0.0f;
			content[i][nbNeuronIn*8+1] = a=='x' ? 0.0f : 1.0f;
			/*
			for(int j = 0; j < nbNeuronOut;j++) {
				a = out.get(j).toString().charAt(0);
				double[] bitsChar = charToArrayDouble(a);
				for(int k = 0; k < 8; k++) {
					content[i][j*8+k+nbNeuronIn*8] = bitsChar[k];
				}
			}*/
		}
		datapoints = new DataCollection(content, nbNeuronOut*8, 1.0f);
		
		IHM.info("Exit MakeDataPoint of " + name + " of type IMAGE");
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
