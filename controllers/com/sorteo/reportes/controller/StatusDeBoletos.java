package com.sorteo.reportes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.reportes.model.mStatusDeBoletos;

/**
 * Servlet implementation class BoletosVendidos
 */
@WebServlet("/StatusDeBoletos")
public class StatusDeBoletos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusDeBoletos() {
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
		mStatusDeBoletos model = new mStatusDeBoletos();
		
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
				view = "ListaSorteos";
			}
		}
		
		
		
		switch (view) {
		case "Buscar":

			Buscar(request, response, model, sesion);
			break;

		default:
			fullPath = "/WEB-INF/views/reportes/statusboletos.html";
			menu = vista.initMenu(0, false, 19, 5, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);

			System.out.println("idSorteo: " + sesion.pkSorteo);

			String regex = "";

			model.setIdSorteo((int) sesion.pkSorteo);

			//model.Sorteo();

			String dato = "";// model.getSorteo();

			regex = "#SORTEO#";
			HTML = HTML.replaceAll(regex, dato);

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
	
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, mStatusDeBoletos model, SesionDatos sesion)
			throws ServletException, IOException {

		StringBuilder sb = new StringBuilder();
		
		CrearTablaIncidencias(sb, model.consultaBoletosConIncidencia(sesion.pkSorteo));
		CrearTablaNoVendidos(sb, model.consultaBoletosNoVendidos(sesion.pkSorteo));
		
		System.out.println("sb.length="+sb.length()); 

		PrintWriter out = response.getWriter();
		out.println(sb.toString());
	}

	protected String getNombreIncidencia(String incidencia) {
		switch(incidencia){
		case "N": return "";
		case "E": return "Extraviado"; 
		case "R": return "Robado";
		}
		return "Incidencia";
	}

	protected void CrearTablaIncidencias(StringBuilder sb, ResultSet rs) {

		int contador=0;
		try {
			sb.append("<p style=\"font-size: 12pt;\"><strong>Boletos con incidencias.</strong></p>");
			
			sb.append("<table style=\"width: 100%; border-color: #FFFFFF; font-size: 10pt;\"><tbody> <tr>");
			while (rs.next())
			{
				if (contador%5==0 && contador!=0)
				{
					sb.append("</tr><tr>");
				}
				/*
				if(contador%50==0 && contador!=0){
					sb.append("</tr></tbody></table>");
					sb.append("<div class=\"saltopagina\"></div>");
					sb.append("<table style=\"width: 100%; border-color: #FFFFFF; font-size: 10pt;\"><tbody> <tr>");
				}
				//*/
				
				sb.append("<td style=\"padding: 1pt;\"><strong>").append(rs.getString("FOLIO")).append("</strong></td>");
				sb.append("<td>").append(getNombreIncidencia(rs.getString("INCIDENCIA"))).append("</td>");
				
				contador++;
				
			}
		}catch(SQLException ex) { }
		sb.append("</tr></tbody></table><br/><br/>");
	}

	protected void CrearTablaNoVendidos(StringBuilder sb, ResultSet rs) {

		int contador=0;
		try {
			sb.append("<p style=\"font-size: 12pt;\"><strong>Boletos no vendidos.</strong></p>");
			
			sb.append("<table style=\"width: 100%; border-color: #FFFFFF; font-size: 10pt;\"><tbody> <tr>");
			while (rs.next())
			{
				if (contador%10==0 && contador!=0)
				{
					sb.append("</tr><tr>");
				}
				/*
				if(contador%50==0 && contador!=0){
					sb.append("</tr></tbody></table>");
					sb.append("<div class=\"saltopagina\"></div>");
					sb.append("<table style=\"width: 100%; border-color: #FFFFFF; font-size: 10pt;\"><tbody> <tr>");
				}
				//*/
				
				sb.append("<td style=\"padding: 1pt;\">").append(rs.getString("FOLIO")).append("</td>");
				sb.append("<td>").append(getNombreIncidencia(rs.getString("INCIDENCIA"))).append("</td>");
				
				contador++;
				
			}
		}catch(SQLException ex) { }
		sb.append("</tr></tbody></table><br/><br/>");
	}

}
