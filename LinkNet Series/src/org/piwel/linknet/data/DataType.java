package org.piwel.linknet.data;

import java.util.HashMap;
import java.util.Map;

public class DataType {
	@SuppressWarnings("rawtypes")
	public static Map<String, Class> TYPE = new HashMap<String, Class>();
	public void initType() {
		TYPE.put("binary", BinaryData.class);
	}
}
