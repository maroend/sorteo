package com.sorteo.reportes.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.reportes.model.mReporteCompactoBoletos;

/**
 * Servlet implementation class BoletosVendidos
 */
@WebServlet("/ReporteCompactoMenu.do")
public class ReporteCompactoMenu extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteCompactoMenu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Factory vista = new Factory();
		SesionDatos sesion;
		mReporteCompactoBoletos model = new mReporteCompactoBoletos();
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		
		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(20095)){view = "error";}
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";

		
		fullPath = "/WEB-INF/views/listsorteosuser.html";
		if (view == null) {
			view = "";

			if (request.getParameter("idsorteo") != null) {
				fullPath = "/WEB-INF/views/dashboard.html";
				view = "Dashboard";

			} else {
				view = "";
			}
		}
		
		
		
		switch (view) {

		default:

			//long idsorteo = Long.valueOf(request.getParameter("idsorteo"));

			fullPath = "/WEB-INF/views/reportes/reporte_compacto_menu.html";
			menu = vista.initMenu(0, false, 19, 1035, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);

			System.out.println("idSorteo: " + sesion.pkSorteo);

			model.setIdSorteo((int) sesion.pkSorteo);

			HTML = HTML.replaceAll("#SORTEO#", model.Sorteo());
			
			int retornados = model.cuentaBoletosVendidosYEntregados();
			int soloVendidos = model.cuentaBoletosSoloVendidos();
			int novendidos = model.cuentaBoletosNoVendidos();
			int transito = model.cuentaBoletosEnTransito();
			int incidencias = model.cuentaBoletosConIncidencia();
			
			int electronicos = model.cuentaBoletosElectronicos();
			
			HTML = HTML.replaceAll("#BOLETOS_RETORNADOS#", "" + retornados);
			HTML = HTML.replaceAll("#BOLETOS_VENDIDOS#", "" + soloVendidos);
			HTML = HTML.replaceAll("#BOLETOS_TRANSITO#", "" + transito);
			HTML = HTML.replaceAll("#BOLETOS_INCIDENCIAS#", "" + incidencias);
			HTML = HTML.replaceAll("#BOLETOS_NOVENDIDOS#", "" + novendidos);
			HTML = HTML.replaceAll("#BOLETOS_ELECTRONICOS#", "" + electronicos);
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
		
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
}
