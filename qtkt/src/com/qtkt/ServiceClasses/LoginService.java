package com.qtkt.ServiceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;

import com.qtkt.ServiceClasses.HelperClasses.DatabaseService;
//comment
public class LoginService {
	private DatabaseService dbs;
	private String query = "";
	private ResultSet rs;
	private Cookie cookie;
	private ArrayList<Object> parameters;

	public LoginService() {
		dbs = new DatabaseService();
	}

	public boolean checkAuthentication(String username, String password) {
		boolean status = false;
		parameters = new ArrayList<>();
		parameters.add(username);
		parameters.add(password);
		query = "SELECT * FROM user WHERE username=? AND password=?";
		rs = dbs.getResultForLoginQuery(query, parameters);

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

	public Cookie setCookie(String key, String value) {
		cookie = new Cookie(key, value);
		cookie.setMaxAge(60 * 60 * 24 * 30);

		return cookie;
	}
}
