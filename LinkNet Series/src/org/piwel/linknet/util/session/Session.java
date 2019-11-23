package org.piwel.linknet.util.session;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.piwel.linknet.mlp.IHM;
import org.piwel.linknet.util.RequestPOST;

public class Session {
	private final String WS_LOGIN = "http://piwelengine.eu/stratos/WS/LOGIN.php";
	private final String token;
	private final String mail;
	private boolean valid_authent = false;
	
	public Session(String mail, String password) throws SessionException {
		
		this.mail = mail;
		
		Map<String, String> params = new HashMap<String, String>();
        params.put("mail", mail);
        params.put("psw", password);
		String result = "";
		try {
			result = RequestPOST.post(WS_LOGIN, params);
			
		}catch(IOException ex) {
			IHM.error(ex.getMessage() + " NOT FOUND");
		}
		if (result == null || result == "") {
			this.token = null;
		}
		else if(result.startsWith("NOK")) {
			throw new SessionException(result);
		}else {
			this.token = result;
			this.valid_authent = true;
		}
		
	}
	public String setToken() {
		return token;
	}
	public boolean isAuthentValid() {
		return valid_authent;
	}
	public class SessionException extends Exception{
		private String cause;
		public SessionException(String cause) {
			this.cause = cause;
		}
		public String getCauseMessage() {
			return cause;
		}
	}
	
}
