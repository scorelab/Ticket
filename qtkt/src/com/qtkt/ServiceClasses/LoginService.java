package com.qtkt.ServiceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;

import com.qtkt.ServiceClasses.HelperClasses.DatabaseService;

public class LoginService {
	private DatabaseService dbs;
	private String query = "";
	private ResultSet rs;
	private Cookie cookie;

	public LoginService() {
		dbs = new DatabaseService();
	}

	public boolean checkAuthentication(String username, String password) {
		boolean status = false;
		query = "SELECT * FROM user WHERE username='" + username
				+ "' AND password='" + password + "'";
		rs = dbs.getResultForQuery(query);

		try {
			if (!rs.next()) {
				status = false;
			} else {
				status = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbs.closeConnection();
		return status;
	}
	
	public Cookie setCookie(String key, String value){
		cookie = new Cookie(key, value);
		cookie.setMaxAge(60*60*24*30);
		
		return cookie;
	}
}
