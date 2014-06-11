package com.qtkt.ServletClasses;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qtkt.ServiceClasses.QrCodeService;

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

		response.setContentType("image/png");
		response.setContentLength(bos.size());

		OutputStream os = response.getOutputStream();
		os.write(bos.toByteArray());
		

		os.flush();
		os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
