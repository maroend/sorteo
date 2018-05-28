package com.sorteo.herramientas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.core.Database;
import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.herramientas.model.mLogs;

/**
 * Servlet implementation class Logs
 */
@WebServlet("/Logs.do")
public class Logs extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logs()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		
		Map<Integer, String> dict_usuarios = new HashMap<Integer, String>();
		
		Factory vista = new Factory();
		mLogs model = new mLogs();
		SesionDatos sesion;
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		
		String view = request.getParameter("view");
		
		if (view == null) { view = ""; }
		
		switch (view)
		{
			case "Buscar":
				
				int pg = 0; //pagina a mostrar
				if (request.getParameter("pg") == null)
				{
					pg = 1;
				}
				else
				{
					pg = Integer.valueOf(request.getParameter("pg"));
				}
				
				int show = Integer.valueOf(request.getParameter("show"));
				String search = request.getParameter("search");
				BuscarLogs(request, response, pg, show, search, model, dict_usuarios);
				break;
				/*
			case "copiar":
				model.f2();
				break;
				*/
			default:
				fullPath = "/WEB-INF/views/logs/lista.html";
				menu = vista.initMenu(0, false, 27, 28, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}
	
	protected void consultaUsuarios(Map<Integer, String> dict_usuarios)
	{
		dict_usuarios.clear();
		try {
			String query = "SELECT TOP 1000 [PK1],[USUARIO] FROM USUARIOS";
			Database db = new Database();
			
			ResultSet res = db.getDatos(query);
			if (res != null) {
				while(res.next()) {
					Integer key = res.getInt("PK1");
					String value = "" + key + " - " + res.getString("USUARIO");
					dict_usuarios.put(key, value);
				}
			}
		}catch(SQLException ex) { }
	}
	
	protected void BuscarLogs(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search, mLogs model, Map<Integer, String> dict_usuarios) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		
		String[] columnas = { "Usuario", "Actividad", "Detalle", "Fecha" };
		String[] campos = { "PK_USUARIO", "ACTIVIDAD", "DETALLE", "FECHA_R" };
		
		int numeroDeRegistros = model.contar(search);
		if (0 < numeroDeRegistros) {
			consultaUsuarios(dict_usuarios);
		}
		
		/*
		try {
			String str = null;
			str.charAt(0);
		}catch(Exception ex) { Factory.Error(ex, ""); }
		//*/
		
		String HTML = CrearTablaDeLogs(numeroDeRegistros, model.paginacion(pg, show, search), columnas, campos, pg, show, dict_usuarios);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}
	
	protected String CrearTablaDeLogs(int numreg, ResultSet rs, String[] columnas, String[] campos, int pg, int show, Map<Integer, String> dict_usuarios)
	{
		int i = (pg - 1) * show;
		ArrayList<String> camposTable = new ArrayList<String>();
		
		for (String columna : columnas)
		{
			int tam = 300;
			String campotable;
			switch(columna) {
			case "Usuario": tam = 100; break;
			case "Actividad": tam = 60; break;
			case "Fecha": tam = 140; break;
			}
			campotable =
					"<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: " + tam + "px;\" aria-label=\"Browser: activate to sort column ascending\">"
					+ columna + "</th>";
			
			camposTable.add(campotable);
		}
		
		StringBuilder sb_html = new StringBuilder();
		sb_html.append("<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">");
		sb_html.append("<thead>");
		sb_html.append("<tr role=\"row\">");
		
		sb_html.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");
		for (String columna : camposTable)
		{
			sb_html.append(columna);
		}
		
		sb_html.append(" </thead>");
		sb_html.append(" <tbody>");
		
		try
		{
			if (Integer.valueOf(numreg) > 0) {	
				while (rs.next())
				{
					i++;
					sb_html.append("<tr class=\"gradeA odd\" role=\"row\">");
					sb_html.append("<td class=\"sorting_1\">" + i + "</td>");
					for (String campo : campos)
					{
						if(campo == "PK_USUARIO") {
							Integer key = rs.getInt(campo);
							String id_y_usuario;
							id_y_usuario = dict_usuarios.get(key);
							if(id_y_usuario == null)
								id_y_usuario = key.toString();
							sb_html.append("<td class=\"sorting_1\">" + id_y_usuario + "</td>");
						}
						else
							sb_html.append("<td class=\"sorting_1\">" + rs.getString(campo) + "</td>");
					}
					//i++;
					sb_html.append("</tr>");
					//rs.close();
				}
			}else {			
				sb_html.append("<tr> <td colspan=\"10\">");
				sb_html.append("<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen logs</h1><p>Realice otra busqueda.</p></div>");
				sb_html.append("</td></tr>");			
			}
		}
		
		catch (SQLException e)
		{
			Factory.Error(e, "i=" + i);
		}
		
		sb_html.append("</tbody>");
		sb_html.append("</table>");
		
		int numpag = Math.round(numreg / show);
		int denumpag = numpag + 1;
		
		StringBuilder sb_paginado = new StringBuilder();
		sb_paginado.append("<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando ").append(pg).append(" de ").append(denumpag).append(" total ").append(numreg).append(" elementos</div>");
		sb_paginado.append("<div class=\"dataTables_paginate paging_simple_numbers\" id=\"data-table_paginate\">");
		
		if (pg > 1)
			sb_paginado.append("<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPag(").append(pg - 1).append(");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>");
		else
			sb_paginado.append("<a class=\"paginate_button previous disabled\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>");
		
		sb_paginado.append("<span>");
		
		// Calcular el inicio del arreglo
		int inipg = 0;
		int r = (pg - 1) % 5;
		int sumpag = 0;
		
		if (r == 0) {
			inipg = pg - 1;
		} else {
			inipg = ((pg - 1) / 5) * 5;
		}
		
		for (int j = inipg; j < 5 + inipg; j++) {
			if (j < numpag + 1)
			{
				sumpag = j + 1;
				
				if (sumpag == pg)
					sb_paginado.append("<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPag(").append(sumpag).append(");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">").append(sumpag).append("</a>");
				else
					sb_paginado.append("<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPag(").append(sumpag).append(");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">").append(sumpag).append("</a>");
			}
		}
		
		sb_paginado.append("</span>");
		
		if (pg <= numpag)
			sb_paginado.append("<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPag(" + (pg + 1) + ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>");
		else
			sb_paginado.append("<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>");
		
		sb_paginado.append("</div>");
		
		sb_html.insert(0, sb_paginado.toString());
		
		return sb_html.toString();
	}
	
}
