package com.sorteo.conciliacion.controller;

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
import com.core.Global;
import com.core.SesionDatos;
import com.sorteo.conciliacion.model.mRegistroVenta;
import com.sorteo.sorteos.model.mSorteosUsuarios;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/RegistroVenta")
public class RegistroVenta extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroVenta() {
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
		
		mRegistroVenta model = new mRegistroVenta();
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
		String search = null;		
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}		
		
		
		switch (view) {
		
		
		
		case "BuscarModal":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			//search = request.getParameter("search");
			search = Global.decodeBase64(request.getParameter("search"));
			BuscarModal(request, response, pg, show, search, model, sesion);
			break;
		

		case "getTalonario":
			getTalonario(request, response, model,sesion);
			break;
			
			
		case "obtenerTalonario":
			obtenerTalonario(request, response, model,sesion);
			break;				
	
			
		case "VenderTalonarioCompleto":
			VenderTalonarioCompleto(request, response, model,sesion);
			break;				
			
			
		case "getSoloComprador"://new
			getSoloComprador(request, response, model,sesion);
			break;					
			
			
		case "getComprador":
			getComprador(request, response, model,sesion);
			break;
			
			
		case "actualizarComision":
			actualizarComision(request, response, model);
			break;
			
		
			
			
		
			
		default:
			
			String fullPath = "/WEB-INF/views/conciliacion/RegistroVenta.html";
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
	
	
	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mRegistroVenta model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Nombre", "Apellidos", "Correo" };
		String[] campos = { "NOMBRE", "APELLIDOS", "CORREO" };

		int numeroregistros = model.contarModal(search);

		String HTML = CrearTablaModal(numeroregistros,
				model.paginacionModal(pg, show, search), columnas, campos, pg,
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
				
				idusuario = rs.getString("PK1");

				html += "<tr class=\"gradeA odd\" role=\"row\">";
				
				/*if(idusuario != null){		
					
					   html += "<td class=\"sorting_1\"><i class=\"fa fa-2x fa-check-circle\"></i></td>";					
					}
				else*/
					//html += "<td class=\"sorting_1\"><input name=\"selectusuario\"  id="+ rs.getInt("PK1")+ "  value="+ rs.getInt("PK1")+ "    type=\"checkbox\" /></td>";
				html += "<td class=\"sorting_1\"><input name=\"selectusuario\"  id="+ rs.getInt("PK1")+ "  value="+ rs.getInt("PK1")+ "    type=\"radio\" /></td>";


				
				
				
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {

					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";

				}

				
				html += "</tr>";

				//System.out.println(rs.getString("ROLE"));

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
	
	
	
	
	protected void actualizarComision(HttpServletRequest request,
			HttpServletResponse response, mRegistroVenta model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")) );		
		model.setComision(Integer.valueOf(request.getParameter("comision")));			
		//model.actualizarComision(model);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mRegistroVenta model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
		model.setClave(request.getParameter("clave"));
		*/
	}
	
	protected void getTalonario(HttpServletRequest request,
			HttpServletResponse response, mRegistroVenta model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		model.setClave(request.getParameter("folio"));
		result = model.getTalonario(model,sesion.pkSorteo);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	
	
	protected void obtenerTalonario(HttpServletRequest request,
			HttpServletResponse response, mRegistroVenta model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		model.setClave(request.getParameter("folio"));
		result = model.obtenerTalonario(model,sesion.pkSorteo);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	
	
	protected void VenderTalonarioCompleto(HttpServletRequest request,
			HttpServletResponse response, mRegistroVenta model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		model.setClave(request.getParameter("folio"));
		result = model.VenderTalonarioCompleto(model, sesion);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	
	//new
	protected void getSoloComprador(HttpServletRequest request,
			HttpServletResponse response, mRegistroVenta model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		//String boleto = request.getParameter("boleto");
		//int pkboleto = Integer.parseInt(request.getParameter("pkboleto"));
		int pkcomprador = Integer.parseInt(request.getParameter("pkcomprador"));
		result = model.consultaSoloComprador(pkcomprador);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	
	
	
	
	protected void getComprador(HttpServletRequest request,
			HttpServletResponse response, mRegistroVenta model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		String boleto = request.getParameter("boleto");
		int pkboleto = Integer.parseInt(request.getParameter("pkboleto"));
		result = model.consultaComprador(sesion.pkSorteo, boleto,pkboleto);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	
}
