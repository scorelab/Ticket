package com.qtkt.ServletClasses;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.qtkt.ServiceClasses.QrCodeService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Servlet implementation class QrServlet
 */
@WebServlet("/qr")
public class QrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QrServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		QrCodeService qcs = new QrCodeService();
		ByteArrayOutputStream bos = qcs.QrGeneration();

		//response.setContentType("image/png");
		//response.setContentLength(bos.size());
		response.setContentType("text/html");

		//OutputStream os = response.getOutputStream();
		PrintWriter pw = response.getWriter();
		//os.write(bos.toByteArray());
		//System.out.println(bos.toString());
		JSONObject jobj = new JSONObject();
		jobj.put("Image", "data:image/png;base64,"+Base64.encode(bos.toByteArray()));//bos.toByteArray()
		pw.print(jobj);
		

		//os.flush();
		//os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
