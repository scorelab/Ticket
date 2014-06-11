package com.qtkt.ServiceClasses.HelperClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Database Name qtkt 
 * Username qtktadmin 
 * Password admin
 */
public class DatabaseService {
	private Connection con;
	private Statement st;
	private ResultSet rs;

	public DatabaseService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/qtkt",
					"qtktadmin", "admin");
		} catch (Exception e) {
			System.out.println("Exception " + e);
			System.exit(-1);// System.exit("Exception " + e);
			// Change Made
		}
	}

	public ResultSet getResultForQuery(String query) {
		rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
