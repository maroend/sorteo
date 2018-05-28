package com.sorteo.dashboard.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.SesionDatos;
import com.sorteo.dashboard.model.mDashboard;

/**
 * Servlet implementation class Grafica
 */
@WebServlet("/grafica")
public class Grafica extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Grafica() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        System.out.println("________________________________________________ GRAFICA");
		
		mDashboard model = new mDashboard();
		//Factory  vista = new Factory();
		SesionDatos sesion;
		
		if((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		String nivel = request.getParameter("nivel");
		String view = request.getParameter("view");
		String json = "";

		if ("lineas".equals(view)) {
			json = model.create_JSON(model.consultaBoletosVendidos(sesion, nivel));
		}
		else if ("pie".equals(view)) {
			json = model.create_JSON(model.consultaEstadisticas(sesion, nivel));
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();

		model.close();
	}

}

