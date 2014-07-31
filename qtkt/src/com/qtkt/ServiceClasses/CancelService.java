package com.qtkt.ServiceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.qtkt.ServiceClasses.HelperClasses.DatabaseService;

public class CancelService {
	private DatabaseService dbs;
	// private JSONObject jobj;
	private ResultSet rs;
	private int as;
	private QrCodeService qrs;
	private TicketService ts;

	public int getAvailableSeats(int ticketno) {
		dbs = new DatabaseService();
		String query = "SELECT tseats FROM ticketdetails WHERE ticketno=?";
		rs = dbs.getResultForTicketQuery(query, ticketno);
		try {
			if (rs.next()) {
				as = rs.getInt("tseats");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbs.closeConnection();
		return as;
	}

	public JSONObject performCancel(int ticketno, int ct) {
		dbs = new DatabaseService();
		ts = new TicketService();
		String query = "SELECT * FROM ticketdetails WHERE ticketno=?";
		rs = dbs.getResultForTicketQuery(query, ticketno);

		try {
			if (rs.next()) {
				cancelAndUpdate(rs.getString("seatno"), ct,
						rs.getString("jdate"),
						rs.getString("tname") + "_" + rs.getString("class"),
						rs.getInt("ticketno"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rs = dbs.getResultForTicketQuery(query, ticketno);
		updateImageData(rs, ticketno);

		dbs.closeConnection();
		return ts.getTicket(ticketno + "");
	}

	private void cancelAndUpdate(String seats, int ct, String date,
			String tname, int ticketno) {
		//dbs = new DatabaseService();
		String seatsa[] = seats.split(",");
		int nseats[] = new int[seatsa.length];
		for (int i = 0; i < seatsa.length; i++)
			nseats[i] = Integer.parseInt(seatsa[i]);
		String query = "";
		for (int i = 0; i < ct; i++) {
			query = "UPDATE " + tname + " SET s" + nseats[i]
					+ "='f', fseats=fseats+1 WHERE bdate='" + date + "'";
			dbs.updateQuery(query);
		}
		if (ct == seatsa.length) {
			seats = "0";
			query = "UPDATE ticketdetails SET valid='f', seatno='" + seats
					+ "', tseats=tseats-" + ct + " WHERE ticketno=" + ticketno;
		} else {
			seats = seatsa[ct] + ",";
			for (int i = ct + 1; i < seatsa.length; i++)
				seats = seats + seatsa[i] + ",";
			query = "UPDATE ticketdetails SET valid='t', seatno='" + seats
					+ "', tseats=tseats-" + ct + " WHERE ticketno=" + ticketno;
		}
		dbs.updateQuery(query);
		//dbs.closeConnection();
	}

	private void updateImageData(ResultSet rs, int ticketno) {
		qrs = new QrCodeService();
		//dbs = new DatabaseService();
		// JSONObject njobj = new JSONObject();
		try {
			if (rs.next()) {
				String imdata = qrs.getImage(getNewJson(rs));
				String query = "UPDATE ticketdetails SET image='" + imdata
						+ "' WHERE ticketno=" + ticketno;
				dbs.updateQuery(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//dbs.closeConnection();
	}

	@SuppressWarnings("unchecked")
	private JSONObject getNewJson(ResultSet rs) {
		JSONObject njobj = new JSONObject();
		try {
			njobj.put("jd", rs.getString("jdate")); // Journey Date
			njobj.put("f", rs.getString("jfrom")); // From
			njobj.put("t", rs.getString("jto")); // To
			njobj.put("sc",
					rs.getString("class") + "_" + rs.getString("seatno")); // Seats
																			// with
																			// Class
			njobj.put("ty", rs.getString("btype")); // type =
													// R(Reserved)|B(Booked)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return njobj;
	}
}
