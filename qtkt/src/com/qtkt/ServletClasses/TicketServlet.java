package com.qtkt.ServletClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.qtkt.ServiceClasses.CancelService;
import com.qtkt.ServiceClasses.TicketService;

/**
 * Servlet implementation class TicketServlet
 */
@WebServlet("/auth/TicketServlet")
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TicketServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TicketService ts = new TicketService();
		PrintWriter pw = response.getWriter();
		JSONObject jobj;
		String ticketno = request.getParameter("ticketno");
		jobj = ts.getTicket(ticketno);
		pw.print(jobj);
		pw.flush();
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id, ct, tno;
		StringBuffer sb = new StringBuffer();
		CancelService cs = new CancelService();
		JSONParser parser = new JSONParser();
		JSONObject jobj = null;
		JSONObject njobj;
		PrintWriter pw = response.getWriter();

		BufferedReader reader = request.getReader();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		try {
			jobj = (JSONObject) parser.parse(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		id = Integer.parseInt(jobj.get("status") + "");
		ct = Integer.parseInt(jobj.get("cticketno") + "");
		tno = Integer.parseInt(jobj.get("ticketno") + "");

		if (id == 0) {
			if (ct <= cs.getAvailableSeats(tno) && ct > 0) {
				njobj = cs.performCancel(tno, ct);
				pw.print(njobj);
			}
		}
		pw.flush();
		pw.close();
	}

}
