package com.sorteo.usuarios.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.usuarios.model.mRoles;
import com.sorteo.usuarios.model.mUsuarios;

/**
 * Servlet implementation class Roles
 */
@WebServlet("/Roles.do")
public class Roles extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Roles() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		mRoles model = new mRoles();
		Factory vista = new Factory();
		SesionDatos sesion;

		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu ="";
		String notificaciones ="";
		String infouser ="";

		String view = request.getParameter("view");

		if (view == null) {
			view = "";
		}

		switch (view) {

		case "Agregar":
			fullPath = "/WEB-INF/views/roles/agregar.html";
			menu = vista.initMenu(0 ,false,24,26,sesion);
    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
			break;

		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
			Buscar(request, response, pg, show, search, model);
			break;

		case "BuscarRole":
			BuscarRole(request, response, model);
			break;

		case "Editar":
			Editar(request, response, model);
			break;

		case "Borrar":
			int res = Borrar(request, response, model);
			HTML = Integer.toString(res);	   
			
			break;
			
		  case "BorrarDependencias":	   	    	
	        	 BorrarDependencias(request, response, model);		    	
		    	break;	

		default:
			fullPath = "/WEB-INF/views/roles/lista.html";
			menu = vista.initMenu(0 ,false,24,26,sesion);
    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);

		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		mRoles model = new mRoles();
		SesionDatos sesion;
		
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String usuario = sesion.nickName;
		

		model.setRole(request.getParameter("role"));
		model.setDescripcion(request.getParameter("descripcion"));
		int idrole = model.registrar(model,usuario);
		
		PrintWriter out = response.getWriter();
		out.println(idrole);
		
		model.close();
	}

	protected void BuscarRole(HttpServletRequest request,
			HttpServletResponse response, mRoles model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idrole")));
		// USUARIO
		model.ObtenerRole(model);

		String datos = model.getRole() + "#%#" + model.getDescripcion() + "#%#"
				+ model.getId();

		PrintWriter out = response.getWriter();
		out.println(datos);
	}

	protected void Editar(HttpServletRequest request,
			HttpServletResponse response, mRoles model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idrole")));
		model.setRole(request.getParameter("role"));
		model.setDescripcion(request.getParameter("descripcion"));

		model.Editar(model);
	}

	protected int Borrar(HttpServletRequest request,
			HttpServletResponse response, mRoles model) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idrole")));
		int res = model.Borrar(model);
		
		return res;

	}
	
	
	protected void BorrarDependencias(HttpServletRequest request,
			HttpServletResponse response, mRoles model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idrole")));		
		model.BorrarDependencias(model);	

	}

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mRoles model) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String[] columnas = { "Role", "Descripcion", "Controles" };
		String[] campos = { "ROLE", "DESCRIPCION" };

		int numeroregistros = model.contar_filas(search);

		String HTML = CrearTabla(numeroregistros,
				model.paginacion(pg, show, search), columnas, campos, pg, show);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;
		}

		html += " </thead>";
		html += " <tbody>";

		try {
			
			
	//if (Integer.valueOf(numreg) > 0) {	
			
			while (rs.next()) {

				i++;

				html += "<tr class=\"gradeA odd\" role=\"row\">";

				html += "<td class=\"sorting_1\">" + i + "</td>";
				for (String campo : campos) {

					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";

				}

				html += "<td class=\"sorting_1\">";

				html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"

						+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"

						+ "<li><a href=\"Permisos?idrole="
						+ rs.getString("PK1")
						+ "\">Permisos</a></li>"
						+ "<li><a href=\"javascript:Editar("
						+ rs.getString("PK1")
						+ ");\">Editar</a></li>"
						+ "<li><a href=\"javascript:Borrar("
						+ rs.getString("PK1")
						+ ");\">Borrar</a><li>"
						+ "<li class=\"divider\">"
						+ "</li><li>"
						+ "</ul></div>";

				html += "</td>";

				i++;
				html += "</tr>";

				System.out.println(rs.getString("ROLE"));

			}
			
			
	 /* } else {

		   html += "<li align= \"center\">";

		   html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

		   html += "<h1>No existen Roles</h1>";
		  // html += "<p>Asigne Colaboradores al Nicho.</p>";

		   html += "</div>";

		   html += "</li>";
		   html += "</ul>";

	   }	*/
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		html += "</tbody>";
		html += "</table>";

		int numpag = Math.round(numreg / show);
		int denumpag = numpag + 1;

		String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando "
				+ pg
				+ " de "
				+ denumpag
				+ " total "
				+ numreg
				+ " elementos</div>";
		paginado += "<div class=\"dataTables_paginate paging_simple_numbers\" id=\"data-table_paginate\">";

		if (pg > 1) {
			// <a href="?pg=<%=pg-1 %>">Anterior</a>

			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPag("
					+ pagante
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

		} else {

			paginado += "<a class=\"paginate_button previous disabled\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

		}

		paginado += "<span>";

		// Calcular el inicio del arreglo
		 int inipg = 0;
		  int r = (pg-1) % 5;
		  int sumpag = 0;

		  if (r == 0) {

		   inipg = pg - 1;
		  } else {
		   inipg = ((pg - 1) / 5) * 5;
		  }

		  for (int j = inipg; j < 5 + inipg; j++) {
			if (j < numpag + 1) {

				sumpag = j + 1;

				if (sumpag == pg) {
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPag("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";

		html = paginado + html;

		return html;

	}

}
