package org.piwel.linknet.data;

import java.util.HashMap;
import java.util.Map;

public class DataType {
	@SuppressWarnings("rawtypes")
	public static Map<String, Class> TYPE = new HashMap<String, Class>();
	static {
		initType();
	}
	public static void initType() {
		TYPE.put("binary", BinaryData.class);
		TYPE.put("char", CharData.class);
		TYPE.put("image", ImageData.class);
	}
}
