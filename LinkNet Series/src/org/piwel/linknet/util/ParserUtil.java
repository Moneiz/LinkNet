package org.piwel.linknet.util;


public class ParserUtil {
	
	public static int objectToInt(Object o) {
		try {
			return Integer.parseInt(o.toString());
		}catch(NumberFormatException e) {
			System.err.print(e + " - Can't convert an object into Int");
		}
		return 0;
	}
}
