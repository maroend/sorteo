package com.sorteo.herramientas.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.SesionDatos;
import com.core.SuperModel;

/**
 * Servlet implementation class AsignacionNichos
 */
@WebServlet("/ProgressBar")
public class ServiceProgress extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServiceProgress() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		

		SuperModel model = new SuperModel();
		SesionDatos sesion;
		
		String contenido = "";
		//Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) != null){ 
			contenido = ""+sesion.data1;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();


		out.print(contenido);

		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}



