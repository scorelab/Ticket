package com.qtkt.ServiceClasses.HelperClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList;

/*
 * Database Name qtkt 
 * Username qtktadmin 
 * Password admin
 */
public class DatabaseService {
	private Connection con;
	private Statement st;
	private PreparedStatement ps;
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

	public ResultSet getResultForLoginQuery(String query,
			ArrayList<Object> parameters) {
		rs = null;
		try {
			// st = con.createStatement();
			// rs = st.executeQuery(query);
			ps = con.prepareStatement(query);
			ps.setString(1, (String) parameters.get(0));
			ps.setString(2, (String) parameters.get(1));
			rs = ps.executeQuery();
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

	public Connection getCon() {
		return con;
	}
}
