package com.qtkt.ServiceClasses;

import java.sql.ResultSet;

import org.json.simple.JSONObject;

import com.qtkt.ServiceClasses.HelperClasses.DatabaseService;

public class TicketService {
	private JSONObject jobj;
	private DatabaseService dbs;
	private ResultSet rs;

	@SuppressWarnings("unchecked")
	public JSONObject getTicket(String ticketno) {
		dbs = new DatabaseService();
		jobj = new JSONObject();
		String query = "SELECT * FROM ticketdetails WHERE ticketno=?";
		rs = dbs.getResultForTicketQuery(query, Integer.parseInt(ticketno));

		try {
			if (!rs.next()) {
				jobj.put("status", 0);
			} else {
				jobj = getJsonForResult(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbs.closeConnection();
		return jobj;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getJsonForResult(ResultSet rs) {
		JSONObject njobj = new JSONObject();
		try {
			njobj.put("status", 1);
			njobj.put("ticketno", rs.getInt("ticketno"));
			njobj.put("selectedFrom", rs.getString("jfrom"));
			njobj.put("selectedTo", rs.getString("jto"));
			njobj.put("train", rs.getString("tname") + rs.getString("jtime"));
			njobj.put("journeydate", rs.getString("jdate"));
			njobj.put("btype", rs.getString("btype"));
			njobj.put("valid", rs.getString("valid"));
			njobj.put("nic", rs.getString("nic"));
			njobj.put("seatno", rs.getString("seatno"));
			njobj.put("seats", rs.getInt("tseats"));
			njobj.put("class", rs.getString("class"));
			njobj.put("emailid", rs.getString("emailid"));
			njobj.put("phoneno", rs.getString("phoneno"));
			njobj.put("image", rs.getString("image"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return njobj;
	}
}
