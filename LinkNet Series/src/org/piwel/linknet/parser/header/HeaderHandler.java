package org.piwel.linknet.parser.header;

public class HeaderHandler {

	private String[] rawHeader;
	
	private String[] ioCell;
	private String type;
	
	private boolean hasIOCell, hasType;
	
	public HeaderHandler(String[] header) throws HeaderEvaluationException{
		this.rawHeader = header;
		
		if(!evaluate()) {
			throw new HeaderEvaluationException();
		}
		
	}
	protected boolean evaluate(){
		
		for(String line : rawHeader) {
			if(line.charAt(0) != '#') {
				System.err.println("Invalid key of field : " + line.split("\t")[0]);
				return false;
			}
			String[] fragmentLine = line.split("\t");
			if(fragmentLine[0].equals("#iocell")) {
				hasIOCell = true;
				ioCell = new String[fragmentLine.length-1];
				for(int i = 1; i < fragmentLine.length; i++) {
					ioCell[i-1] = fragmentLine[i];
				}
			}
			else if(fragmentLine[0].equals("#type")) {
				hasType = true;
				type = fragmentLine[1];
			}
			else {
				System.err.println("Invalid field : " + line.split("\t")[0]);
				return false;
			}
			
		}
		return true;
		
	} 
	
	public String getType() {
		return type;
	}
	public String[] getIOCell() {
		return ioCell;
	}
	
}
