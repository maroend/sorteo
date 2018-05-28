package com.sorteo.reportes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.reportes.model.mReporteCompactoBoletos;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

/**
 * Servlet implementation class BoletosVendidos
 */
@WebServlet("/ReporteCompactoBoletos")
public class ReporteCompactoBoletos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteCompactoBoletos() {
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

		if (view == null) {
			view = "";
			if (request.getParameter("idsorteo") != null) {
				fullPath = "/WEB-INF/views/dashboard.html";
				view = "Dashboard";
			}
		}
		
		
		switch (view) {

		case "BuscarBoletosVendidos":
			HTML = BuscarBoletosVendidos(request, response, model, sesion);
			break;

		case "BuscarBoletosTransito":
			HTML = BuscarBoletosTransito(request, response, model, sesion);
			break;

		case "BuscarIncidencias":
			HTML = BuscarIncidencias(request, response, model, sesion);
			break;
			
		case "BuscarNoVendidos":
			HTML = BuscarNoVendidos(request, response, model, sesion);
			break;
			
		case "BuscarElectronicos":
			HTML = BuscarElectronicos(request, response, model, sesion);
			break;

		default:

			fullPath = "/WEB-INF/views/reportes/reporte_compacto_boletos.html";
			menu = vista.initMenu(0, false, 19, 5, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);

			String opcion = request.getParameter("opcion");
			if (opcion == null)
				opcion = "";
			HTML = HTML.replaceAll("#VIEW_BOLETOS#", opcion);
			
			model.setIdSorteo((int) sesion.pkSorteo);
			HTML = HTML.replaceAll("#SORTEO#", model.Sorteo());
			//System.out.println("<aqui 2>");


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
	
	
	
	protected String BuscarBoletosVendidos(HttpServletRequest request,
			HttpServletResponse response, mReporteCompactoBoletos model, SesionDatos sesion)
			throws ServletException, IOException {
		
		model.setIdSorteo(sesion.pkSorteo);
		
		return CrearTabla(
				model.cuentaBoletosVendidosYEntregados(),
				model.consultaBoletosVendidosYEntregados(),
				"Boletos Vendidos");
	}
	
	protected String BuscarBoletosTransito(HttpServletRequest request,
			HttpServletResponse response, mReporteCompactoBoletos model, SesionDatos sesion)
			throws ServletException, IOException {
		
		model.setIdSorteo(sesion.pkSorteo);
		
		return CrearTabla(
				model.cuentaBoletosEnTransito(),
				model.consultaBoletosEnTransito(),
				"Boletos en Transito");
		
	}
	
	protected String BuscarIncidencias(HttpServletRequest request,
			HttpServletResponse response, mReporteCompactoBoletos model, SesionDatos sesion)
			throws ServletException, IOException {
		
		model.setIdSorteo(sesion.pkSorteo);
		
		return CrearTabla(
				model.cuentaBoletosConIncidencia(),
				model.consultaBoletosConIncidencia(),
				"Boletos con Incidencias");
	}
	
	protected String BuscarNoVendidos(HttpServletRequest request,
			HttpServletResponse response, mReporteCompactoBoletos model, SesionDatos sesion)
			throws ServletException, IOException {
		
		model.setIdSorteo(sesion.pkSorteo);
		
		return CrearTabla(
				model.cuentaBoletosNoVendidos(),
				model.consultaBoletosNoVendidos(),
				"Boletos no vendidos");
	}
	
	protected String BuscarElectronicos(HttpServletRequest request,
			HttpServletResponse response, mReporteCompactoBoletos model, SesionDatos sesion)
			throws ServletException, IOException {
		
		model.setIdSorteo(sesion.pkSorteo);
		
		return CrearTabla(
				model.cuentaBoletosElectronicos(),
				model.consultaBoletosElectronicos(),
				"Boletos electronicos", false);
	}

	
	
	
	
	class BoletoReporte{
		String folio;
		int folioDigital;
		boolean electronico;
		public BoletoReporte(ResultSet res) throws SQLException {
			this.folio = res.getString("FOLIO");
			this.folioDigital = res.getInt("FOLIODIGITAL");
			this.electronico = (res.getInt("DIGITAL") == 1);
		}
	}
	
	protected String CrearTabla(int max, ResultSet rs, String title) {
		return CrearTabla(max, rs, title, true);
	}
	
	protected String CrearTabla(int max, ResultSet rs, String title, boolean ordenar)
	{
		StringBuilder sb = new StringBuilder();
		int contador=0;
		int ncolumnas = 5;
		try {
			sb.append("<p style=\"font-size: 12pt;\"><strong>").append(title).append(" (").append(max).append(")</strong></p>");
			
			sb.append("<table style=\"width: 100%; border-color: #FFFFFF; font-size: 10pt;\"><tbody> <tr>");
			
			sb.append("<tr> ");
			for (int i = 0; i < ncolumnas; i++)
				sb.append("<td>&nbsp;&nbsp;&nbsp;boleto-f. digital</td>");
			sb.append("</tr> ");
			
			ArrayList<BoletoReporte> list = new ArrayList<BoletoReporte>(max);
			while (rs.next())
				list.add(new BoletoReporte(rs));
			rs.close();
			if (ordenar)
			{
				java.util.Collections.sort(list, new Comparator<BoletoReporte>(){
					public int compare(BoletoReporte b1, BoletoReporte b2){
						return b1.folio.compareTo(b2.folio);
					}
				});
			}
			
			for (BoletoReporte bol : list)
			{
				if (contador%ncolumnas==0 && contador!=0)
				{
					sb.append("</tr><tr>");
				}
				
				sb
				.append("<td style=\"padding: 1pt;\"><strong>")
				.append(bol.electronico ? "e-" : "f-")
				.append(bol.folio);
				if (bol.folioDigital > 0)
					sb.append("-").append(bol.folioDigital);
				sb.append("</strong></td>");
				
				contador++;
			}
			
		}catch(SQLException ex) { }

		sb.append("</tr></tbody></table><br/><br/>");
		
		return sb.toString();
	}

}
