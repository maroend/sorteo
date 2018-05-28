package com.sorteo.herramientas.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import com.sorteo.herramientas.model.mInformesDeError;

/**
 * Servlet implementation class Logs
 */
@WebServlet("/InformesDeError.do")
public class InformesDeError extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
	 */
	public InformesDeError()
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
		mInformesDeError model = new mInformesDeError();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		//String realPath = request.getServletContext().getRealPath("");
		

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
			case "Leer":
				HTML = Leer(request);
				break;
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
				BuscarErrores(request, response, pg, show, search, model, dict_usuarios);
				break;

			default:
				fullPath = "/WEB-INF/views/herramientas/informesdeerror.html";
				menu = vista.initMenu(0, false, 27, 29, sesion);
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
	
	protected String Leer(HttpServletRequest request) {
		String fileName = request.getParameter("file");
		String path = Factory.getPathErrors() + fileName;
		File file = new File(path);
		if (file.canRead())
			try{
				StringBuilder sb = new StringBuilder();
				FileReader fr = new FileReader(path);
				
				BufferedReader br = new BufferedReader(fr);
				
				System.out.println("noused:"+request.getParameter("noused"));
				
				String line;
				while((line = br.readLine())!=null) {
					sb.append(line).append("<br/>");
					System.out.println("##         :"+line);
				}
				
				br.close();
				fr.close();
				
				return sb.toString();
			}catch(Exception ex) { }
		return "";
	}
	
	protected void consultaUsuarios(Map<Integer, String> dict_usuarios)
	{
		dict_usuarios.clear();
		try {
			String query = "SELECT TOP 1000 [PK1],[USUARIO] FROM [sorteos].[dbo].[USUARIOS]";
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

	protected void BuscarErrores(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search, mInformesDeError model, Map<Integer, String> dict_usuarios) throws ServletException, IOException
	{
		// TODO Auto-generated method stub

		String[] columnas = { "Archivo" };

		model.listarArchivos(request);
		int numeroDeRegistros = model.contar();
		if (0 < numeroDeRegistros) {
			consultaUsuarios(dict_usuarios);
		}

		String HTML = CrearTablaDeLogs(numeroDeRegistros, model.paginacion(pg, show, search), columnas, pg, show);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTablaDeLogs(int numreg, ArrayList<String> sublist, String[] columnas, int pg, int show)
	{
		int i = (pg - 1) * show;
		ArrayList<String> camposTable = new ArrayList<String>();

		for (String columna : columnas)
		{
			int tam = 300;
			String campotable;
			switch(columna) {
			case "Usuario": tam = 150; break;
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


		for(int j=0; j<sublist.size(); j++) {
			
			i++;
			sb_html.append("<tr class=\"gradeA odd\" role=\"row\">");
			sb_html.append("<td class=\"sorting_1\">").append(i).append("</td>");
			{
				sb_html.append("<td class=\"sorting_1\">")
					.append("<a href=\"javascript: muestraContenido('").append(sublist.get(j)).append("')\" >").append(sublist.get(j)).append("</a>")
					.append("</td>");
			}
			sb_html.append("</tr>");
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


		//Calcular el inicio del arreglo
		int inipg = 0;
		int r = (pg) / show;
		int sumpag = 0;

		if (r == 0)
			inipg = pg - 1;
		else
			inipg = ((pg - 1) / show) - show;

		for (int j = 0; j < 10 + inipg + 1; j++)
		{
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
