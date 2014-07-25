package com.qtkt.ServiceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

import com.qtkt.ServiceClasses.HelperClasses.DatabaseService;

public class GetFromToTrainTime {
	private DatabaseService dbs;
	private ResultSet rs;
	private JSONArray jarray;
	private DateFormat dateformat;

	// private JSONObject jobj;

	public GetFromToTrainTime() {
		dbs = new DatabaseService();
		dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public JSONArray getTrainandTime(String from, String to) {
		String query = "SELECT traindetails.tname,traindetails.ttime FROM trainfortravel INNER JOIN traindetails ON trainfortravel.tno=traindetails.tno WHERE trainfortravel.sfrom = '"
				+ from + "' AND trainfortravel.sto = '" + to + "'";
		jarray = new JSONArray();
		rs = dbs.getResultForQuery(query);

		int i = 0;
		try {
			while (rs.next()) {
				jarray.add(i, rs.getString(1) + " " + rs.getString(2));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jarray;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getClass(String train) {
		String values[];
		values = train.split(" ");
		String query = "SELECT classfortrain.cname FROM classfortrain INNER JOIN traindetails ON classfortrain.tno=traindetails.tno WHERE traindetails.tname='"
				+ values[0] + "'";
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

	// @SuppressWarnings("unchecked")
	public Boolean checkSeatAvailability(long seats, String date, String tc) {
		boolean availability = false;
		int fseats;
		String query = "SELECT fseats FROM " + tc + " WHERE bdate='" + date
				+ "'";
		// jarray = new JSONArray();
		rs = dbs.getResultForQuery(query);

		// int i = 0;
		try {
			if (rs.next()) {
				fseats = rs.getInt(1);
				if (seats <= fseats)
					availability = true;
				// jarray.add(i, rs.getInt(1));
				// i++;
			} else {
				availability = false;
				//System.out.println("Not Available");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return availability;
	}

	public float availableDay(String journeydatetime) {// 2014-07-25
		Date date = new Date();
		Date jdate = null;
		String today = dateformat.format(date);
		try {
			date = dateformat.parse(today);
			jdate = dateformat.parse(journeydatetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//System.out.println((float)(jdate.getTime() - date.getTime()) / (1000 * 3600 * 24));
		return (float) ((jdate.getTime() - date.getTime()) / (1000 * 3600 * 24));
	}

	public void close() {
		dbs.closeConnection();
	}
}
