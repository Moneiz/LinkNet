package org.piwel.linknet.parser;

public abstract class Parser {
	
	protected int lenghtData;
	protected double datas[];
	
	public Parser(String data) {
		lenghtData = data.length();
		convert(data);
	}
	
	protected abstract void convert(String data);
	protected abstract String unconvert();
	
}
