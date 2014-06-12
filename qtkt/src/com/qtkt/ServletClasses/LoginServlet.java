package com.qtkt.ServletClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.qtkt.ServiceClasses.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out = response.getWriter();
		 out.println("GetWork");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONParser parser = new JSONParser();
		JSONObject joUser = null;
		try {
			joUser = (JSONObject) parser.parse(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String user = (String) joUser.get("username");
		String pass = (String) joUser.get("password");

		LoginService ls = new LoginService();
		if (ls.checkAuthentication(user.toLowerCase(), pass)) {
			Cookie cookie= ls.setCookie("q_user", user);
			response.addCookie(cookie);

			response.setContentType("text/html");
			// PrintWriter out = response.getWriter();
			out.write(user);
			out.flush();
			out.close();
		}
		/*
		 * Cookie[] cookies = request.getCookies(); if (cookies == null) {
		 * System.out.println("no cookie"); } else {
		 * System.out.println(cookies[0].getName() + cookies[0].getValue()); }
		 */
	}

}
