package com.qtkt.ServiceClasses;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.qtkt.ServiceClasses.HelperClasses.DatabaseService;

public class ReserveService {
	private JSONObject jobj;
	private String snos;
	private DatabaseService dbs;
	private ResultSet rs;
	private ResultSetMetaData rsmd;

	public ReserveService() {
		dbs = new DatabaseService();
	}

	@SuppressWarnings("unchecked")
	public JSONObject reserveIt(JSONObject cr) {
		jobj = new JSONObject();
		jobj = cr;
		String value, values[];
		value = (String) jobj.get("trainTime");
		values = value.split(" ");
		snos = reserve((long) jobj.get("seats"),
				(String) jobj.get("journeydate"),
				values[0] + "_" + jobj.get("selectedClass"));

		jobj.put("seatNumbers", snos);
		// jobj.put("key2", "value2");
		return jobj;
	}

	private String reserve(long seats, String date, String tbl) {
		String value = "";
		String query = "SELECT * FROM " + tbl + " WHERE bdate='" + date + "'";
		rs = dbs.getResultForQuery(query);

		try {
			int i = 2;
			int count = 0;
			rsmd = rs.getMetaData();
			if (rs.next()) {
				while (count < seats && i < rsmd.getColumnCount()) {
					if (rs.getString(i).equalsIgnoreCase("f")) {
						value = value + (i - 1) + ",";
						++count;
						query = "UPDATE " + tbl + " SET "
								+ rsmd.getColumnName(i) + "='r' WHERE bdate='"
								+ date + "'";
						dbs.updateQuery(query);
					}
					i++;
				}
				query = "UPDATE " + tbl + " SET fseats=fseats-" + count
						+ " WHERE bdate='" + date + "'";
				dbs.updateQuery(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return value;
	}
}
