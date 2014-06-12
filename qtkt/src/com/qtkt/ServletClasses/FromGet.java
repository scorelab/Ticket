package com.qtkt.ServletClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.qtkt.ServiceClasses.GetFromToTrainTime;

/**
 * Servlet implementation class FromGet
 */
@WebServlet("/auth/FromGet")
public class FromGet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FromGet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int id;
		StringBuffer sb = new StringBuffer();
		JSONParser parser = new JSONParser();
		JSONObject jobj = null;
		GetFromToTrainTime gfttt = new GetFromToTrainTime();
		PrintWriter pw = response.getWriter();
		JSONArray jarray;

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
		//System.out.println(sb.toString());

		id = Integer.parseInt((String) jobj.get("id"));
		response.setContentType("text/html");

		if (id == 0) {
			pw.print(gfttt.getCurrentDate());
		} else if (id == 1) {
			jarray = new JSONArray();
			jarray = gfttt.getFrom();
			pw.print(jarray);
		} else if (id == 2) {
			jarray = new JSONArray();
			jarray = gfttt.getTo((String) jobj.get("selectedFrom"));
			pw.print(jarray);
		}

		gfttt.close();
		pw.flush();
		pw.close();
	}
}
