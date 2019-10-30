package org.piwel.linknet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.piwel.linknet.data.DataType;
import org.piwel.linknet.data.SimpleData;

public class DatasetFileProvider {
	public final String path;
	public SimpleData datas;
	public DatasetFileProvider(String path) {
		this.path = path;
		this.datas = readJson(path);
	}
	@SuppressWarnings("rawtypes")
	public SimpleData readJson(String path) {
		JSONParser jsonParser = new JSONParser();
		Class dataTypeClass;
		Constructor constructor;
		try {
			JSONObject jsonArray = (JSONObject) jsonParser.parse(new FileReader(path));
			dataTypeClass = DataType.TYPE.get((String) jsonArray.get(""));
			constructor = dataTypeClass.getConstructor(JSONObject.class);
			return (SimpleData) constructor.newInstance(jsonArray);
		}catch (ParseException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
		
}
