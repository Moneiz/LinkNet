package org.piwel.linknet.graphic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.piwel.linknet.mlp.IHM;

public class Setting implements Serializable{

	private static final long serialVersionUID = -7567571565157L;
	public int width;
	public int height;
	public String token;
	public Setting() {
		
	}
	public Setting(String user,int width, int height) {
		this.token = user;
		this.width = width;
		this.height = height;
	}
	public static final void writeSettings(Setting s) {

		FileOutputStream fout;
		try {
			fout = new FileOutputStream("conf");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(s);
			IHM.info("Wrote settings file successfully");
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Setting readSettings() {
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("conf");
		    ObjectInputStream ois = new ObjectInputStream(fis);
		    Setting s = (Setting) ois.readObject();
		    IHM.info("Read settings file successfully");
		    ois.close();
		    return makeDefaultSetting(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return makeDefaultSetting(new Setting());
	}
	public static Setting makeDefaultSetting(Setting s) {
		Setting sDefault = new Setting(null, 1280, 720);
		
		if(s.width == 0)	s.width = sDefault.width;
		if(s.height == 0)	s.height = sDefault.height;
		if(s.token == null)	s.token = sDefault.token;
		
		return s;
	}
	
}
