package com.sorteo.sorteos.controller;

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
import com.sorteo.sorteos.model.mSorteosUsuarios;

/**
 * Servlet implementation class SorteosUsuarios
 */
@WebServlet("/SorteosUsuarios")
public class SorteosUsuarios extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SorteosUsuarios() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		mSorteosUsuarios model = new mSorteosUsuarios();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		String view = request.getParameter("view");
		if (!sesion.permisos.havePermiso(10092)){view = "error";}

		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int idsorteo;
		//String fullPathmenuprofile = "/WEB-INF/views/notificaciones.html";
		//String fullPathinfouser = "/WEB-INF/views/infouser.html";

		int pg = 0;
		int show = 0;
		String search = null;

		if (view == null) {
			view = "";
		}

		switch (view) {

		case "Buscar":
			
			idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
			
			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
			}
			
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			
			
			
			//System.out.println("idsorteo "+sesion.pkSorteo);
			
			BuscarUsuarios(request, response, pg, show, search,sesion.pkSorteo, model, sesion);
			break;

		case "BuscarModal":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			BuscarModal(request, response, pg, show, search, model, sesion);
			break;

		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		default:
			idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
				// Sera necesario volver a cargar la sesion para verificar que el sorteo esta activo.
				if ((sesion = SesionDatos.start(request, response, false, model)) == null)
					return;
			}
			
			fullPath = "/WEB-INF/views/sorteos/usuarios/SorteosUsuarios.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			HTML = HTML.replaceFirst("#DISPLAY_BOTON_ASIGNAR_USUARIO#", sesion.sorteoActivo ? "display" : "none");
			
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
			HttpServletResponse response) throws ServletException, IOException {

		mSorteosUsuarios model = new mSorteosUsuarios();
		SesionDatos sesion;

		Factory.prepareError(request);
		if((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		if ("insertar".equals(request.getParameter("action"))) {

			String usuariosjoin = request.getParameter("valores");
			String[] usuarios = usuariosjoin.split(",");

			for (String usuario : usuarios) {

				System.out.println(usuario);

				model.setIdsorteo(Integer.parseInt(request
						.getParameter("idsorteo")));
				model.setIdusuario(Integer.parseInt(usuario));
				model.AgregarUsuarioSorteo(model, sesion);

			}

		} else {

			 model.setIdusuario(Integer.valueOf(request.getParameter("idusuario")));
			 model.setIdsorteo(sesion.pkSorteo);
			 model.BorrarUsuario(model, sesion);

		}

		model.close();
	}

	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mSorteosUsuarios model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Usuario", "Nombre", "Rol" };
		String[] campos = { "USUARIO", "NOMBREC", "ROLE" };

		int numeroregistros = model.contarModal(search);

		String HTML = CrearTablaModal(numeroregistros,
				model.paginacionModal(pg, show, search,sesion.pkSorteo), columnas, campos, pg,
				show);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTablaModal(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show) {

		int i = (pg - 1) * show;
		String idusuario = "";

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}

		html += " </thead>";
		html += " <tbody id=\"Habilitados\">";

		try {
			if (Integer.valueOf(numreg) > 0) {	
			while (rs.next()) {

				i++;
				
				idusuario = rs.getString("PK_USUARIO");

				html += "<tr class=\"gradeA odd\" role=\"row\">";
				
				if(idusuario != null){		
					
					   html += "<td class=\"sorting_1\"><i class=\"fa fa-2x fa-check-circle\"></i></td>";					
					}
				else
					html += "<td class=\"sorting_1\"><input name=\"selectusuario\"  id="+ rs.getInt("PK1")+ "  value="+ rs.getInt("PK1")+ "    type=\"checkbox\" /></td>";


				
				
				
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {

					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";

				}

				
				html += "</tr>";

				System.out.println(rs.getString("ROLE"));

			}
			} else {			
				
				html += "<tr> <td colspan=\"5\">"
						+"<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Usuarios</h1><p>Empiece por agregar un nuevo usuario.</p></div>"
						+ "</td></tr>";			

			}
		} catch (SQLException e) {
			Factory.Error(e, "rs="+rs);
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
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModalUsuario("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModalUsuario("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModalUsuario("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModalUsuario("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";

		html = paginado + html;

		return html;

	}

	protected void BuscarUsuarios(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int idsorteo, mSorteosUsuarios model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Usuario", "Nombre", "Rol", "Controles" };
		String[] campos = { "USUARIO", "NOMBREC", "ROLE" };
		
		columnas[3] = sesion.sorteoActivo ? "Controles" : "";

		int numeroregistros = model.contar(idsorteo,search);

		String HTML = CrearTabla(numeroregistros,
				model.paginacion(pg, show, search,idsorteo), columnas, campos, pg, show, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {

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
			
			if (Integer.valueOf(numreg) > 0) {	
			while (rs.next()) {

				i++;

				html += "<tr class=\"gradeA odd\" role=\"row\">";

				html += "<td class=\"sorting_1\">" + i + "</td>";
				for (String campo : campos) {

					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";

				}

				html += "<td class=\"sorting_1\">";

				if (sesion.sorteoActivo)
				{
					html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"
						+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
						+ "<li><a href=\"EditUsuarios?idusuario="
						+ rs.getString("PK1")
						+ "\">Editar</a></li>"
						+ "<li><a href=\"javascript:CambiarPassword("
						+ rs.getString("PK1")
						+ ");\">Cambiar Contrase&ntilde;a</a><li>"
						+ "<li><a href=\"javascript:AsignarRoles("
						+ rs.getString("PK1")
						+ ");\">Cambiar Rol</a></li>"
						+ "<li><a href=\"javascript:Borrar("
						+ rs.getString("PK1")
						+ ");\">Eliminar del sorteo</a><li>"
						+ "<li class=\"divider\">"
						+ "</li><li>"
						+ "</ul></div>";
				}

				html += "</td>";

				i++;
				html += "</tr>";

			}
		}else{
			
			html += "<tr> <td colspan=\"5\">"
					+"<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Usuarios</h1><p>Empiece por agregar un nuevo usuario.</p></div>"
					+ "</td></tr>";	
		
		}
		} catch (SQLException e) {
			Factory.Error(e, "rs="+rs);
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
