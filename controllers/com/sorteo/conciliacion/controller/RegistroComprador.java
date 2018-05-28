package com.sorteo.conciliacion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.Global;
import com.core.SesionDatos;
import com.core.SuperModel.RESULT;
import com.sorteo.conciliacion.model.mRegistroComprador;
import com.sorteo.poblacion.model.mSectores;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/RegistroComprador")
public class RegistroComprador extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroComprador() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mRegistroComprador model = new mRegistroComprador();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		
		int pg = 0;
		int show = 0;
		String search = "";
		
    //    Permiso para editar compradores en menu "ventas"
         boolean editarComprodores = sesion.permisos.havePermiso(40136) && sesion.sorteoActivo;
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		case "getTalonario":
			getTalonario(request, response, model,sesion);
			break;
			
		case "actualizarComision":
			actualizarComision(request, response, model);
			break;
			
			
	case "Borrar":
			HTML = Borrar(request, response, model);
			break;
			
	case "Buscar":
			
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			show = Integer.valueOf(request.getParameter("show"));
			
			search = Global.decodeBase64(request.getParameter("search"));
			//search = request.getParameter("search");
			HTML = Buscar(request, pg, show, search, model, sesion, editarComprodores);
			break;
			
		default:
			
			String fullPath = "/WEB-INF/views/conciliacion/RegistroComprador.html";
			menu = vista.initMenu(0, false, 10, 110, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdsorteo((int)sesion.pkSorteo);
			model.setIdusuario((int)sesion.pkUsuario);
			
			model.getSectorUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( (int)sesion.pkUsuario));
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", sesion.sorteoActivo ? "display" : "none");
			
			
			
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
	
	protected String Borrar(HttpServletRequest request,
			HttpServletResponse response, mRegistroComprador model) throws ServletException, IOException {
		
		model.setId(Integer.valueOf(request.getParameter("idComprador")));
		RESULT result = model.Borrar();
		
		return "{\"result\":\"" + result + "\",\"msg\":\"" + model._mensaje + "\"}";
	}
	
		
	
	protected String Buscar(HttpServletRequest request,
			int pg, int show, String search,
			mRegistroComprador model, SesionDatos sesion, boolean editarCompradores) throws ServletException, IOException {
		
		String[] columnas = { "Nombre", "Apellidos", "Correo", "Telefono fijo","Telefono Movil","CP","Controles" };
		String[] campos = { "NOMBRE", "APELLIDOS", "CORREO", "TELEFONO_F","TELEFONO_M","CP" };
		
		if (editarCompradores == false)
			columnas = Arrays.copyOfRange(columnas, 0, columnas.length - 1);
		
	//	model.setIdSorteo(sesion.pkSorteo);
		int numeroregistros = model.contar(search);
		return CrearTabla(
				numeroregistros, model.paginacion(pg, show, search),
				columnas, campos, pg, show, sesion, editarCompradores);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion, boolean editarCompradores) {
		
		int i = (pg - 1) * show;
		ArrayList<String> campostable = new ArrayList<String>();
		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"\" aria-label=\"Browser: activate to sort column ascending\">";
		
		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}
		
		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";
		if(editarCompradores)
			//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onclick=\"seleccionarTodo('1t')\" id=\"checkboxall1t\" name=\"checkboxall\" /></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			
			html += columna;
		}
		html += " </thead>";
		html += " <tbody>";
		try {
			
			if (Integer.valueOf(numreg) > 0)
			{
				while (rs.next())
				{
					i++;
					
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					if(editarCompradores)
						//html += "<td class=\"sorting_1\"><input type=\"checkbox\" id=\""+rs.getInt("PK1")+"\" /></td>";
					html += "<td class=\"sorting_1\">" + i + "</td>";
					
					for (String campo : campos)
					{
						html += "<td class=\"sorting_1\">" + rs.getString(campo) + "</td>";
					}
					
					if(editarCompradores)
					{
						html += "<td class=\"sorting_1\">";
						html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"
							+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
							+ "<li><a href=\"javascript:BuscarEditar("
							+ rs.getString("PK1")
							+ ");\">Editar</a></li>"
							+ "<li><a href=\"javascript:Borrar("
							+ rs.getString("PK1")
							+ ");\">Borrar</a><li>"
							+ "<li class=\"divider\">"
							+ "</li><li>"
							+ "</ul></div>";
						
						html += "</td>";
					}
					
	
					//i++;
					html += "</tr>";
	
					//System.out.println(rs.getString("SECTOR"));
	
				}
			} else {

				html += "<tr class=\"gradeA odd\" role=\"row\"><td colspan=\"8\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

				html += "<h1>No existen Compradores</h1>";
				html += "<p>Empiece creando un nuevo Comprador.</p>";

				html += "</div>";

				html += "</td>";
				html += "</tr>";

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		html += "</tbody>";
		html += "</table>";

		/*
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
		*/
		String paginado = Factory.Paginado(numreg, show, pg);

		html = paginado + html;

		return html;

	}

	
	
	protected void actualizarComision(HttpServletRequest request,
			HttpServletResponse response, mRegistroComprador model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")) );		
		model.setComision(Integer.valueOf(request.getParameter("comision")));			
		//model.actualizarComision(model);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mRegistroComprador model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
		model.setClave(request.getParameter("clave"));
		*/
	}
	
	protected void getTalonario(HttpServletRequest request,
			HttpServletResponse response, mRegistroComprador model,SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		model.setClave(request.getParameter("folio"));
		result = model.getTalonario(model,sesion.pkSorteo);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	
}
