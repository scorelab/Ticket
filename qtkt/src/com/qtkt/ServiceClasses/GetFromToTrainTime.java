package com.qtkt.ServiceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.qtkt.ServiceClasses.HelperClasses.DatabaseService;

public class GetFromToTrainTime {
	private DatabaseService dbs;
	private ResultSet rs;
	private JSONArray jarray;
	private JSONObject jobj;

	public GetFromToTrainTime() {
		dbs = new DatabaseService();
	}

	public JSONArray getFrom() {
		jarray = new JSONArray();
		String query = "SELECT * FROM fromstation";
		rs = dbs.getResultForQuery(query);

		try {
			int i = 0;
			while (rs.next()) {
				// jobj = new JSONObject();
				// jobj.put("from", rs.getString(2));
				jarray.add(i, rs.getString(2));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jarray;
	}

	public String getCurrentDate() {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// System.out.println(dateformat.format(date));
		return dateformat.format(date);
	}

	public JSONArray getTo(String value) {
		String query = "SELECT tostation FROM fromstation INNER JOIN fromto ON fromstation.sid=fromto.fromid WHERE fromstation.fstationname='"
				+ value + "'";
		jarray = new JSONArray();
		rs = dbs.getResultForQuery(query);

		int i = 0;
		try {
			while (rs.next()) {
				jarray.add(i, rs.getString(1));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jarray;
	}

	public void close() {
		dbs.closeConnection();
	}
}
