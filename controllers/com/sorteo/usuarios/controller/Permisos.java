package com.sorteo.usuarios.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.usuarios.model.mPermisos;

/**
 * Servlet implementation class Permisos
 */
@WebServlet("/Permisos")
public class Permisos extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Permisos() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mPermisos model = new mPermisos();
		SesionDatos sesion;

		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String fullPathmenuprofile = "/WEB-INF/views/notificaciones.html";
		String fullPathinfouser = "/WEB-INF/views/infouser.html";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String view = request.getParameter("view");
		
		
		//10021 ACCESO A LA LISTA DE PERMISOS (PERMISO MISMO DE USUARIOS) 
		if (!sesion.permisos.havePermiso(10021)){view = "error";}	

		if (view == null) {
			view = "";
		}

		switch (view) {

		case "agregar":
			fullPath = "/WEB-INF/views/permisos/agregar.html";
			menu = vista.initMenu(0, false, 24, 26, sesion);
			notificaciones = vista.initNotificaciones(context,
					fullPathmenuprofile);
			infouser = vista.initInfoUser(context, fullPathinfouser,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			response.setContentType("text/html");
			out.println(HTML);
			break;

		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			// int show = Integer.valueOf(request.getParameter("show"));
			int show = 1;
			String search = request.getParameter("search");
			Buscar(request, response, pg, show, search);
			break;

		case "Borrar":
			Borrar(request, response, model);
			break;

		case "BuscarPermisos":
			BuscarPermisos(request, response, model);
			break;

		case "BuscarEditar":
			BuscarEditar(request, response, model);
			break;

		case "Editar":
			Editar(request, response, model);
			break;
			
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 24, 26, sesion);
			notificaciones = vista.initNotificaciones(context,
					fullPathmenuprofile);
			infouser = vista.initInfoUser(context, fullPathinfouser,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			break;	

		default:
			fullPath = "/WEB-INF/views/permisos/lista.html";
			menu = vista.initMenu(0 ,false,24,26,sesion);
    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
			
			
			String regex = "";
			String contents = "";

			model.setIdrole(Integer.valueOf(request.getParameter("idrole")));

			model.ObtenerRole(model);

			regex = "#ROLE#";
			contents = HTML.replaceAll(regex,
					"Privilegios del ROL: " + model.getRole());

			out.println(contents);
			// System.out.println("entra aqui default");
			break;
		}

		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		mPermisos model = new mPermisos();
		
		SesionDatos sesion;
	
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
	

		if (request.getParameter("permitirpermiso") != null) {

			String permitirpermiso = "true";
			String rol = request.getParameter("Rol");
			String permisos = request.getParameter("permisos").toString();

			if (permitirpermiso.equals(request.getParameter("permitirpermiso")
					.toString())) {

				model.permitirPermisos(rol, permisos);
			} else {
				model.restringirPermisos(rol, permisos);
			}

		} else {

			model.setPermiso(request.getParameter("permiso"));
			model.setTipo(request.getParameter("tipo").charAt(0));
			model.setDescripcion(request.getParameter("descripcion"));

			
			String usuario = sesion.nickName;
			model.registrar(model,usuario);

		}

		model.close();
	}

	protected void BuscarEditar(HttpServletRequest request,
			HttpServletResponse response, mPermisos model) throws ServletException, IOException {

		model.setId(Integer.valueOf(request.getParameter("idpermiso")));
		model.BuscarEditar(model);

		String datos = String.valueOf(model.getId()) + "#%#"
				+ model.getPermiso() + "#%#" + model.getTipo() + "#%#"
				+ model.getDescripcion();

		PrintWriter out = response.getWriter();
		out.println(datos);

	}

	protected void Editar(HttpServletRequest request,
			HttpServletResponse response, mPermisos model) throws ServletException, IOException {

		model.setId(Integer.valueOf(request.getParameter("idpermiso")));
		model.setPermiso(request.getParameter("permiso"));
		model.setTipo(request.getParameter("tipo").charAt(0));
		model.setDescripcion(request.getParameter("descripcion"));

		model.Editar(model);

	}

	protected void Borrar(HttpServletRequest request,
			HttpServletResponse response, mPermisos model) throws ServletException, IOException {

		model.setId(Integer.valueOf(request.getParameter("idpermiso")));
		model.Borrar(model);

	}

	protected void BuscarPermisos(HttpServletRequest request,
			HttpServletResponse response, mPermisos model) throws ServletException, IOException {

		String html = "";

		html = CrearCadenaPermisos(request, response, 'D', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'S', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'P', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'T', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'V', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'E', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'R', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'U', html, model);
		html += "#%#";
		html = CrearCadenaPermisos(request, response, 'H', html, model);

		// html = CrearCadenaPermisos(response,'U',html);
		// html +="#%#";
		// html = CrearCadenaPermisos(response,'H',html);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(html);

	}

	protected String CrearCadenaPermisos(HttpServletRequest request,
			HttpServletResponse response, char tipo, String html, mPermisos model)
			throws ServletException, IOException {

		ResultSet rs = model.obtenerPermisos(tipo);

		html += "<form name=\"form"
				+ tipo
				+ "\" id=\"form"
				+ tipo
				+ "\"><table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('"
				+ tipo
				+ "')\" id=\"checkboxall"
				+ tipo
				+ "\" name=\"checkboxall\" /></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">Permitidos</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">id Permiso</th>";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">Permisos.</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">Descripción.</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">Controles.</th>";
		html += " </thead>";
		html += " <tbody>";

		String permitido = null;

		try {
			ArrayList<HashMap<String, String>> list_map = new ArrayList<HashMap<String, String>>();
			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("PK1", rs.getString("PK1"));
				map.put("PERMISO", rs.getString("PERMISO"));
				map.put("DESCRIPCION", rs.getString("DESCRIPCION"));
				list_map.add(map);
			}
			rs.close();
			// while(rs.next()){
			for (HashMap<String, String> map : list_map) {

				permitido = "<span class=\"fa-stack fa-2x text-success\"><i class=\"fa fa-circle fa-stack-2x\"></i><i class=\"fa fa-check fa-stack-1x fa-inverse\"></i></span>";

				if (model.existePermiso(request.getParameter("Rol").toString(),
						map.get("PK1"))) {

					System.out.println("entra existe permisop");

					permitido = "<span class=\"fa-stack fa-2x text-success\"><i class=\"fa fa-circle fa-stack-2x\"></i><i class=\"fa fa-ban fa-stack-1x fa-inverse\"></i></span>";
				}

				html += "<tr class=\"gradeA odd\" role=\"row\">";

				html += "<td class=\"sorting_1\"><input type=\"checkbox\"  value=\""
						+ map.get("PK1") + "\" /></td>";

				html += "<td>  " + permitido + "  </td>";

				html += "<td class=\"sorting_1\">" + map.get("PK1") + "</td>";
				html += "<td class=\"sorting_1\">" + map.get("PERMISO")
						+ "</td>";
				html += "<td class=\"sorting_1\">" + map.get("DESCRIPCION")
						+ "</td>";
				html += "<td class=\"sorting_1\">";

				html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acción"

						+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
						+ "<li><a href=\"javascript:BuscarEditar("
						+ map.get("PK1")
						+ ");\">Editar</a></li>"
						+ "<li><a href=\"javascript:Borrar("
						+ map.get("PK1")
						+ ");\">Borrar</a><li>"
						+ "<li class=\"divider\">"
						+ "</li><li>" + "</ul></div>";

				html += "</td>";

				html += "</tr>";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		html += "</tbody>";
		html += "</table></form>";

		/*
		 * response.setContentType("text/html"); PrintWriter out =
		 * response.getWriter(); out.println(html);
		 */

		return html;

	}

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search)
			throws ServletException, IOException {

		//String[] columnas = { "Permiso", "Descripcion", };
		//String[] campos = { "PERMISO", "DESCRIPCION" };

		// int numeroregistros = model.contar_filas();
		String HTML = "";
		// String HTML =
		// CrearTabla(numeroregistros,model.paginacionUsuarios(pg,show,search),columnas,campos,pg,show);
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
			while (rs.next()) {

				i++;

				html += "<tr class=\"gradeA odd\" role=\"row\">";

				html += "<td class=\"sorting_1\">" + i + "</td>";
				for (String campo : campos) {

					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";

				}

				i++;
				html += "</tr>";

				System.out.println(rs.getString("PERMISO"));

			}
		} catch (SQLException e) {
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
