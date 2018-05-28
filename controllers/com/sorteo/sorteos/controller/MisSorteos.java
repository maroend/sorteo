package com.sorteo.sorteos.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;


/**
 * Servlet implementation class MisSorteos
 */
@WebServlet("/MisSorteos.do")
public class MisSorteos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MisSorteos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SesionDatos sesion;
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, new SuperModel())) == null)
			return;
		try {
			sesion.misSorteos = 1;
			sesion.guardaSesion();
			response.sendRedirect("Sorteos.do");
		} catch(Exception ex) { Factory.Error(ex, "MisSorteos.do"); }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.sendRedirect("Sorteos.do?missorteos=1");
		} catch(Exception ex) { Factory.Error(ex, "MisSorteos.do"); }

	}
	
}
