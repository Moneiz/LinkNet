package org.piwel.linknet.util.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.piwel.linknet.mlp.IHM;
import org.piwel.linknet.util.RequestPOST;

public class Session {
	private static final String WS_LOGIN = "http://piwelengine.eu/stratos/WS/LOGIN.php";
	private static final String WS_CHECK_TOKEN = "http://piwelengine.eu/stratos/WS/VALID_TOKEN.php";
	private final String token;
	private boolean valid_authent = false;
	
	/**
	 * 
	 * 
	 * 
	 * @param mail
	 * @param password
	 * @throws SessionException
	 */
	public Session(String mail, String password) throws SessionException {
		
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
	public static boolean WSCheckSession(String token) {
		if(token == null || token.equals("")) return false;
		Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);
		String result = "";
		try {
			result = RequestPOST.post(WS_CHECK_TOKEN, params);	
		}catch(IOException ex) {
			IHM.error(ex.getMessage() + " NOT FOUND");
		}
		if(result.equals("OK")) {
			return true;
		}else {
			IHM.info("Need a new token");
		}
		return false;
	}
	public String getToken() {
		return token;
	}
	public boolean isAuthentValid() {
		return valid_authent;
	}
	public class SessionException extends Exception{
		private static final long serialVersionUID = -2916815656483001844L;
		private String cause;
		public SessionException(String cause) {
			this.cause = cause;
		}
		public String getCauseMessage() {
			return cause;
		}

	}
}
