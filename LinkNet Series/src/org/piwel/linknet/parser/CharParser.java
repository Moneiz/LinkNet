package org.piwel.linknet.parser;

public class CharParser extends Parser {

	
	
	public CharParser(String data, double[] array) {
		super(data);
		if(array != null)
			this.datas = array;
		
		
		
	}
	public String unconvert() {
		
		String result = "";
		
		
		int curBinChr = 0;
		for(int j = 7; j >= 0; j--) {
			if((int)datas[j] == 1)
				curBinChr += Math.pow(2, j);
		}
			
		char curChr = (char) curBinChr;
		result += curChr;
		
		return result;
	}
	protected void convert(String data) {
		
		datas = new double[8];
		
		char curChr = data.charAt(0);
		int curBinChr = (int) curChr;
		
		int remain = curBinChr;
		
		for(int j = 7; j >= 0; j--) {
			int weight = (int) Math.pow(2, j);
			if(weight <= remain) {
				remain -= weight;
				datas[j] = 1;
			}
			else {
				datas[j] = 0;
			}
		}
			
			
		
		
	}
	public double getDatas(int index) {
		return datas[index];
	}

}
