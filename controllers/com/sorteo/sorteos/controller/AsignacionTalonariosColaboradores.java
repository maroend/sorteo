package com.sorteo.sorteos.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mAsignacionTalonariosColaboradores;

/**
 * Servlet implementation class AsignacionTalonariosColaboradores
 */
@WebServlet("/AsignacionTalonariosColaboradores")
public class AsignacionTalonariosColaboradores extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AsignacionTalonariosColaboradores() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mAsignacionTalonariosColaboradores model = new mAsignacionTalonariosColaboradores();
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
		int pg = 0;
		int show = 0;
		String search = null;
		String url = "";
		int tipo_talonario;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String view = request.getParameter("view");

		if (view == null) {
			view = "";
		}
		
		if (!sesion.permisos.havePermiso(10155)) { view = "error"; }

		switch (view) {

		case "Agregar":
			fullPath = "/WEB-INF/views/sorteos/agregar.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);
			out.println(HTML);
			break;

		case "Buscar":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			//int idSorteo = Integer.valueOf(request.getParameter("idsorteo"));
			//int idSector = Integer.valueOf(request.getParameter("idsector"));
			BuscarTalonariosXColaborador(request, response, pg, show, search, model, sesion);
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
			BuscarModalTalonarios(request, response, pg, show, search, model, sesion);
			break;

		case "BuscarModalBoletos":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			//int idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
			//int idsector = Integer.valueOf(request.getParameter("idsector"));
			BuscarModalBoletos(request, response, pg, show, search, sesion.pkSorteo, sesion.pkSector, model, sesion);
			break;
		case "validaFisico_Digital_Colaborador":			
			
			    model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
				model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
				model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
				model.setIdColaborador(Integer.parseInt(request.getParameter("idcolaborador")));				
				tipo_talonario = Integer.parseInt(request.getParameter("tipo_talonario"));					
			
				String valida = model.validaFisico_Digital_Colaborador(tipo_talonario);			
				out.println(valida);
			
			break;		
			
			
			
		case "mostrarTalonariosColaboradores":			
			
		    model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
			model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
			model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
			//model.setIdColaborador(Integer.parseInt(request.getParameter("idcolaborador")));				
			tipo_talonario = Integer.parseInt(request.getParameter("tipo_talonario"));					
		
			String valida_ = model.mostrarTalonariosColaboradores(tipo_talonario);			
			out.println(valida_);
		
		break;		
		

		case "delete":
			// eliminarUsuario(request, response);
			break;

		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);
			out.println(HTML);
			break;

		default:
			
			String idcolaborador = request.getParameter("idcolaborador");
			if (idcolaborador != null) {
				int pkColaborador = Integer.valueOf(idcolaborador);
				if (sesion.pkColaborador != pkColaborador) {
					sesion.pkColaborador = pkColaborador;
					sesion.guardaSesion();
				}
			}
			fullPath = "/WEB-INF/views/sorteos/Talonarios/AsignacionTalonariosColaboradores.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);

			// PERMISO PARA AGREGAR
			/*HTML = putCode(HTML, 10163, "#BTN_ASIGNAR_TALONARIO#", "",
					sesion.sorteoActivo == false ? "" :
					"<a class=\"btn btn-primary m-r-5\" disabled href=\"#\" onclick=\"AsignarTalonario()\">Asignar Talonario</a>"
					, sesion);*/
			
			
			 HTML = HTML.replaceAll( "#BTN_ASIGNAR_TALONARIO#", "");
			
			HTML = HTML.replaceAll("#SECTOR#", model.consultaSector(sesion.pkSector));
			HTML = HTML.replaceAll("#NICHO#", model.consultaNicho(sesion.pkNicho));
			HTML = HTML.replaceAll("#COLABORADOR#", model.consultaColaborador(sesion.pkColaborador));

			url = "SectoresAsignados?idsorteo=" + sesion.pkSorteo;
			HTML = HTML.replaceAll("#URLSECTORES#", url);

			url = "AsignacionNichos?idsorteo=" + sesion.pkSorteo + "&idsector="
					+ sesion.pkSector;
			HTML = HTML.replaceAll("#URLNICHOS#", url);

			url = "AsignacionColaboradores?idsorteo=" + sesion.pkSorteo
					+ "&idsector=" + sesion.pkSector + "&idnicho="
					+ sesion.pkNicho;
			HTML = HTML.replaceAll("#URLCOLABORADORES#", url);

			url = "AsignacionTalonariosColaboradores?idcolaborador="
					+ sesion.pkColaborador;
			HTML = HTML.replaceAll("#URLTALONARIO#", url);

			String contents = "";
			model.setIdSorteo( sesion.pkSorteo);
			model.consultaSorteo(model);
			contents = HTML.replaceAll("#IDSORTEO#", Integer.toString(model.getIdSorteo()));
			contents = contents.replaceAll("#SORTEO#", model.getSorteo());
			
			//_____________
			


			out.println(contents);
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
		// TODO  doPost()

		//Factory vista = new Factory();
		mAsignacionTalonariosColaboradores model = new mAsignacionTalonariosColaboradores();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String accion = request.getParameter("accion");
		if (accion == null)
			accion = "";
		
		
		System.out.print(" entro servlet post: ");
		
	/*	System.out.print(" session sorteo "+ sesion.pkSorteo);
		System.out.print(" session sector: "+sesion.pkSector);
		System.out.print(" session nicho: "+sesion.pkNicho);
		System.out.print(" session colaborador: "+sesion.pkColaborador);*/
		
		
		
		
		 if (accion.equals("aboletos")) {
			
			System.out.println(" entro boletos: ");
			
			String boletoscadena = request.getParameter("idboletos");		
			String[] arrBoletos = boletoscadena.split(",");
		
			/*model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdNicho(sesion.pkNicho);
			model.setIdColaborador(sesion.pkColaborador);*/
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
			model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
			model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
			model.setIdColaborador(Integer.parseInt(request.getParameter("idcolaborador")));			
			
			
			model.setFormato(request.getParameter("folio"));			
		   // model.guardarAsignacionBoletos(arrBoletos, model,sesion);			
			 model.guardarAsignacionColaboradoresBoletos_SP(boletoscadena, model,sesion);
			 
			
			

		}	
		
		 else if (accion.equals("asignacion_colaboradores")) {			
			
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
			model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
			model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
						
			int tipo_talonario = Integer.parseInt(request.getParameter("tipo_talonario"));
			int numtal_colab = Integer.parseInt(request.getParameter("numtal_colab"));
		
			model.setFormato(request.getParameter("folio"));			
			String result = model.AsignacionTalonarioCompleto_Colaborador_SP(tipo_talonario, numtal_colab, model, sesion);	
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(result);			
			
			
		}		
		
		
	
		else if (accion.equals("retornotalonariosfc4")) {			
		
			
			String foliofc4 = request.getParameter("foliofc4");
			model.setFormato(foliofc4);
			
			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			int folioTalonario = Integer.valueOf(request.getParameter("folio"));
			
			/*model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdNicho(sesion.pkNicho);
			model.setIdColaborador(sesion.pkColaborador);*/
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
			model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
			model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
			model.setIdColaborador(Integer.parseInt(request.getParameter("idcolaborador")));
			
			
			model.setIdtalonario(idTalonario);
			
		    model.retornotalonariosfc4(folioTalonario, sesion);
		
			
		}
        else if (accion.equals("devolvertalonariofc5")) {			
			
        	System.out.println(" entro fc5");
        	
        	System.out.println(" session sorteo "+request.getParameter("idsorteo"));
			System.out.println(" session sector: "+request.getParameter("idsector"));
			System.out.println(" session nicho: "+request.getParameter("idnicho"));
			System.out.println(" session colaborador: "+request.getParameter("idcolaborador"));		
        	
        	
			
			String foliofc5 = request.getParameter("foliofc5");
			model.setFormato(foliofc5);
			
			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			int folioTalonario = Integer.valueOf(request.getParameter("folio"));
			
			/*model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdNicho(sesion.pkNicho);
			model.setIdColaborador(sesion.pkColaborador);*/
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
			model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
			model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
			model.setIdColaborador(Integer.parseInt(request.getParameter("idcolaborador")));
			
			model.setIdtalonario(idTalonario);
			
			model.devolvertalonariosfc5(folioTalonario, sesion);
			
			
		}
        else if (accion.equals("devolvertalonariofc5B")) {	
    	 
	
    	    String foliofc5 = request.getParameter("foliofc5B");
			model.setFormato(foliofc5);
	
			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			int folioTalonario = Integer.valueOf(request.getParameter("folio"));
	
			/*model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdNicho(sesion.pkNicho);
			model.setIdColaborador(sesion.pkColaborador);*/
			model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
			model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
			model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
			model.setIdColaborador(Integer.parseInt(request.getParameter("idcolaborador")));
			
			model.setIdtalonario(idTalonario);
			
			model.devolvertalonariosfc5B(folioTalonario, sesion);
	
	
        }
		else if (accion.equals("eliminaboleto")) {
			
			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			//int folioTalonario = Integer.valueOf(request.getParameter("folio"));
			int pkBoleto = Integer.valueOf(request.getParameter("pkBoleto"));
			
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdNicho(sesion.pkNicho);
			model.setIdColaborador(sesion.pkColaborador);
			model.setIdtalonario(idTalonario);
			model.setIdBoleto(pkBoleto);
			//model.eliminaBoleto(folioTalonario);
		}
		else{
			
			System.out.print(" entro talonarios: ");
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")) );
			model.setIdSector(Integer.parseInt(request.getParameter("idsector")));
			model.setIdNicho(Integer.parseInt(request.getParameter("idnicho")));
			model.setIdColaborador(Integer.parseInt(request.getParameter("idcolaborador")));			
			
			
			
			String talonescadena = request.getParameter("idsTalonarios");
			String[] arrTalonarios = talonescadena.split(",");
						
			
			/*model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdNicho(sesion.pkNicho);
			model.setIdColaborador(sesion.pkColaborador);*/			
			
			model.setFormato(request.getParameter("folio"));			
			//model.guardarAsignacionTalonarioColaborador(arrTalonarios, model, sesion);
			model.guardarAsignacionTalonarioColaborador_SP(talonescadena, model, sesion);

		}

		model.close();
	}

	protected void BuscarModalBoletos(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int idsorteo, int idsector, mAsignacionTalonariosColaboradores model, SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Folio", "Formato", "Costo", "Talonario" };
		String[] campos = { "FOLIO", "FORMATO", "COSTO", "TALONARIO" };

		int numeroregistros = model.contarModalBoletos(sesion,search);

		String HTML = CrearTablaModalBoletos(numeroregistros,model.paginacionModalBoletos(pg, show, search,sesion), columnas,campos, pg, show, idsorteo, idsector, model, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTablaModalBoletos(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show, int idsorteo,
			int idsector, mAsignacionTalonariosColaboradores model, SesionDatos sesion) {
		// TODO Auto-generated method stub
		int i = (pg - 1) * show;
		// ResultSet boletos = null;
		// String html2 = "";
		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";
		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";
		// <input id=\"marcarTodo\" type=\"checkbox\" />
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('"
				+ "1b"
				+ "')\" id=\"checkboxall"
				+ "1b"
				+ "\" name=\"checkboxall\" /></th>";
		
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			html += columna;
		}

		html += " </thead>";
		html += " <tbody id=\"Habilitados\">";

		boolean existe = false;

		try {
			
			
		if (Integer.valueOf(numreg) > 0) {			
			
			while (rs.next()) {

				model.setIdBoleto(rs.getInt("PK1"));
				//model.setIdSorteo(idsorteo);
			//	model.setIdSector(idsector);
				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				model.setIdNicho(sesion.pkNicho);
				//existe = model.estaBoletoAsignadoColaborador(model);
				//System.out.print(" existe: " + existe);

				i++;

				html += "<tr class=\"gradeA odd\" role=\"row\">";

				if (rs.getString("ASIGNADO").equals("1")) {

					html += "<td ><i class=\"fa fa-2x fa-check-circle\"></i></td>";

					

				} else {

					
					//+ "#%#" + rs.getString("TALONARIO") +
					
					html += "<td class=\"sorting_1\"><input id=" + rs.getInt("PK1")	+ " type=\"checkbox\" class=\"cboletos\"/></td>";

				}

				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";
				}
				
				
				/*
				 * 
				 * 
				 * 
				 * 
				 * 	// DIGITAL 
				electronico = (rs.getString("DIGITAL").compareTo("1") == 0)
						? "<i class='fa fa-credit-card'></i>"
						: "<i class='fa fa-pencil'></i>";
				
				// TALONARIO
				html += "<td class=\"sorting_1\">" + electronico + "</td>";
				 */

			

				html += "</tr>";

			}
			
		 } else {
				
				html += "<tr> <td colspan=\"7\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Boletos</h1>";
				html += "<p>Asigne Talonarios a los Colaboradores.</p>";
				html += "</div>";
				html += "</td></tr>";
		 }	
		
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		html += "</tbody>";
		html += "</table>";
		// html += html2;

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
			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";
		html = paginado + html;

		return html;
	}

	protected void BuscarModalTalonarios(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mAsignacionTalonariosColaboradores model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Folio", "Num boletos", "Formato", "Monto","Boletos Disponibles" };
		String[] campos = { "FOLIO", "NUMBOLETOS", "FORMATO", "MONTO","DISPONIBLES" };

		int numeroregistros = model.contarModal(sesion,search);

		String HTML = CrearTablaModal(numeroregistros,
				model.paginacionModal(pg, show, search,sesion), columnas, campos, pg,
				show, model, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTablaModal(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show, mAsignacionTalonariosColaboradores model, SesionDatos sesion) {
		String electronico = "";
		// TODO Auto-generated method stub
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
		// <input id=\"marcarTodo\" type=\"checkbox\" />
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('"
				+ "1t"
				+ "')\" id=\"checkboxall"
				+ "1t"
				+ "\" name=\"checkboxall\" /></th>";
		
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			html += columna;
		}
		html += "<th class=\"sorting\" style=\"width: 40px;\" >Tipo</th>";
		
	//	html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" >Boletos Disponibles</th>";
		html += " </thead>";
		html += " <tbody id=\"Habilitados\">";

		try {
			
		if (Integer.valueOf(numreg) > 0) {		
			
			while (rs.next()) {

				i++;
				
				
				model.setIdtalonario(rs.getInt("PK1"));
				model.setFolioTalonario(rs.getString("FOLIO"));
				model.setSorteo(rs.getString("PK_SORTEO"));
				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				model.setIdNicho(sesion.pkNicho);					
				model.setIdColaborador(sesion.pkColaborador);
				model.setFormato(rs.getString("FORMATO"));
				
				System.out.println(">>>sorteo: "+sesion.pkSorteo);
				System.out.println(">>>sector: "+sesion.pkSector);
				System.out.println(">>>nicho: "+sesion.pkNicho);
				System.out.println(">>>colaborador: "+sesion.pkColaborador);
				
				
				
				
				/*ResultSet talonarioasignado = model.obtenerTalonario(model);
			    int disponibles = 0;
				    if (talonarioasignado.next()){           
					
				    	disponibles = talonarioasignado.getInt("DISPONIBLES");	 

				    }else{
				    	disponibles = rs.getInt("NUMBOLETOS");
					
				    }*/
						
				

				html += "<tr class=\"gradeA odd\" role=\"row\">";
				if(/*rs.getString("ASIGNADO").equals("1")&&*/rs.getInt("DISPONIBLES")==0){		
					
					   html += "<td class=\"sorting_1\"><i class=\"fa fa-2x fa-check-circle\"></i></td>";					
					}
				else
					html += "<td class=\"sorting_1\"><input id=" + rs.getInt("PK1")	+ "#%#" + rs.getString("DIGITAL") +" type=\"checkbox\" /></td>";
				//html += "<td class=\"sorting_1\"><input id=" + rs.getString("PK1")	+ " type=\"checkbox\" /></td>";
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";
				}
				// DIGITAL 
				electronico = (rs.getString("DIGITAL").compareTo("1") == 0)
						? "<i class='fa fa-credit-card'></i>"
						: "<i class='fa fa-pencil'></i>";
				
				// TALONARIO
				html += "<td class=\"sorting_1\">" + electronico + "</td>";

				html += "</tr>";
			}
			
		 } else {
				
				html += "<tr> <td colspan=\"7\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Talonarios Asignados a los Colaboradores</h1>";
				html += "<p>Asigne Talonarios a los Colaboradores.</p>";
				html += "</div>";
				html += "</td></tr>";
		 }	
			
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

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
			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";
		html = paginado + html;

		return html;
	}

	protected void BuscarTalonariosXColaborador(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mAsignacionTalonariosColaboradores model, SesionDatos sesion) throws ServletException, IOException {

		int numeroregistros = model.contarTalonariosXColaborador(sesion);

		String HTML = CrearTabla(numeroregistros, model.paginacionTalonariosXColaborador(pg, show, search,sesion), pg,show, model, sesion);
		PrintWriter out = response.getWriter();
		
		HTML = HTML+"#%#"+model.getTotalregistros();
		
		out.println(HTML);
	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, mAsignacionTalonariosColaboradores model, SesionDatos sesion) {
		// TODO Auto-generated method stub

		//int i = (pg - 1) * show;

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		ResultSet boletos;
		boolean boletodigComprador = false;

		String html = "<ul class=\"result-list\">";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {
					 boletodigComprador = false;
					
					
					model.setIdtalonario(rs.getInt("PK1"));
					model.setFolioTalonario(rs.getString("FOLIO"));
					model.setSorteo(rs.getString("PK_SORTEO"));
					model.setFormato(rs.getString("FORMATO"));
					
					model.setIdSorteo(sesion.pkSorteo);
					model.setIdSector(sesion.pkSector);
					model.setIdNicho(sesion.pkNicho);
					model.setIdColaborador(sesion.pkColaborador);	
					
					
					// DIGITAL
					String electronico = (rs.getString("ELECTRONICO").compareTo("1") == 0)
							? "&nbsp;&nbsp;<i class='fa fa-credit-card' style='font-size:10pt;'></i>"
							: "&nbsp;&nbsp;<i class='fa fa-pencil' style='font-size:10pt;'></i>";
					
					
					boolean ttalretornados = model.getTalCompletoVendidosRetornados();
					boolean totalmenteVendidostal = model.getTalonariosCompletamenteVendidos();
					boolean totalmenteNoVendidostal = model.getTalonariosCompletamenteNoVendidos();	
					boolean totalmenteVendidostalDig_sc = model.getTalonariosDigitalesCompletamenteVendidos_sc();//nuevo
					boolean algunBoletoNoVendidoColab = model.ExisteBoletosColaboradoresNV();//nuevo
					//boolean talParcialmenteVendidos = model.getTalParcialmenteVendidos();
					
					
				

					boletos = model.getBoletos(model);			
					

					//i++;

					html += "<li id=\"talonario" + rs.getString("PK1") + "\">";

					html += "<div class=\"result-info\">";
					
					
					html += "<h4 class=\"title\"><a href=\"javascript:;\">TALONARIO FOLIO: <span class=\"badge badge-warning badge-square\">"
							+ rs.getString("FOLIO") + "</span></a>"+electronico+"</h4>";
					
					if(ttalretornados){html += "<i class=\"fa fa-2x fa-check-circle\"></i>";}
					
					
					
					html += "<p class=\"location\"> BOLETOS: <span class=\"badge badge-danger pull\">"
							+ rs.getString("NUMBOLETOS") + "</span> </p>";
					html += "<p class=\"desc\">";
					
				
					
					while (boletos.next()) {
						
						
							if(boletos.getString("VENDIDO").trim().equals("N")){ 								
								
								/*html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
							    		+boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
							    		+boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"
							    		+boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','N')\">";*/
								
							
								/*if((boletos.getString("FOLIODIGITAL")!= "" && boletos.getString("FOLIODIGITAL")!=null ) || boletos.getInt("COMPRADOR")==1 ){									
									html += "<span class=\"label etiqueta-b-electronicos\" >";//etiqueta-b-electronicos
									 boletodigComprador = true;
									
								}else{	html += "<span class=\"label label-inverse\" >"; }*/
								
								html += "<span class=\"label label-inverse\" >";
							
							
							}
							else if(boletos.getString("VENDIDO").trim().equals("V")){ 						
								
								
								if(boletos.getInt("RETORNADO")==1){
									
									
									/* html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
											 +boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
											 +boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"
											 +boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','R')\">";*/
									
									
									html += "<span class=\"label etiqueta-retornados\">";
									
								}else{	
									
									/* html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
											 +boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
											 +boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"
											 +boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','V')\">";*/
									
									
									
									html += "<span class=\"label label-success\" >";
									
								}								
								
								
							}
							else if(boletos.getString("VENDIDO").trim().equals("P")){//PENDIENTE D EPAGO
								
								/* html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
										 +boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
										 +boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"										 										
										 +boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','P')\">";*/				
								
								
								//html += "<span class=\"label etiqueta-b-electronicos\" >";//etiqueta-b-electronicos
								 boletodigComprador = true;
								
								html += "<span class=\"label label-default\" >";							
							
							
							
							}
									
							html += boletos.getString("FOLIO"); 
									
						   html +="</span></a> ";
					}

					html += "</p>";

					html += "<div class=\"btn-row\">";
					
					
										
					
					if(sesion.sorteoActivo)
					{
						//FC4
						if (sesion.permisos.havePermiso(10164) ) {                                                                                                                     
							
							
						 if(rs.getString("ELECTRONICO").compareTo("1") != 0){	
							
								if(totalmenteVendidostal){
									
								html += "<a data-title=\"Es el Retorno de un Talonario de boletos TOTALMENTE vendidos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModalRetornoTalonarioFC4('"
											+ rs.getString("PK1") + "','"
											+ rs.getString("FOLIO") + "','"
											+ rs.getString("PK_SORTEO") + "','"
											+ rs.getString("FORMATO")
											+ "');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>";
								}
								else{
									
								html += "<a data-title=\"El Talonario no esta TOTALMENTE vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeRetornarFC4();\" "
										+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>";
								}
								
						  }else{
							  
							/* html += "<a data-title=\"El Talonario es Electronico y No Retorna Talonarios\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeRetornarDigitalTalFC4();\" "
										+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>";*/
							  
						  }
						 
						}
						else
							html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC4 Retorno Vendidos</span></a> ";
						
						//FC5
	                    if (sesion.permisos.havePermiso(20108)) {                     	
	                    		                    		
	                    	
	                    	 if(rs.getString("ELECTRONICO").compareTo("1") == 0 && boletodigComprador == true)
	                    	 {//el talonario es dig y tiene por lo menos 1 boleto con comprador y folio dig, y esta P:pendiente de pago
	                    	
	                    		 html += "<a data-title=\"El Talonario es Electronico y tiene algun(os) boleto(s) con comprador, folio digital y esta pendiente de pago!\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverTalDigF5();\" "
		    								+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>";
	                    	
	                   
	                         }else{//puede hasta ser un tal dig pero no tiene comprador ni foliodigital
	                    		 
	                    		 
	                    		 if(totalmenteNoVendidostal){
			                    		
			                    		html += "<a data-title=\"Es la Devoluci&oacute;n de un talonario SIN vender ni un solo boleto\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModalDevolverTalonarioFc5('"
											+ rs.getString("PK1") + "','"
											+ rs.getString("FOLIO") + "','"
											+ rs.getString("PK_SORTEO") + "','"
											+ rs.getString("FORMATO")
											+ "');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>";
			                    	}
			                    	else{
			                    		html += "<a data-title=\"El Talonario no esta TOTALMENTE NO vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverF5();\" "
			    								+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>";
			                    	}
	                    		 
	                    		 
	                    	 }           	
		                    	
	                    	
	                    	
						}
						else
							html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC5 Devoluci&oacute;n No vendidos</span></a> ";
	                    
	                    //FC5B
	                    if (sesion.permisos.havePermiso(20109)) {//20103
	                    	
	                    	                  	
	                    	
	                    	 if( ((!totalmenteVendidostal) && (!totalmenteNoVendidostal) && (!totalmenteVendidostalDig_sc)&&(algunBoletoNoVendidoColab)) /*&& !talParcialmenteVendidos */){
	                    		 
	                    		 
							
							  html += "<a data-title=\"Es la Devoluci&oacute;n de boletos no vendidos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:ModalDevolTalonarioFc5b('"
								+ rs.getString("PK1") + "','"
								+ rs.getString("FOLIO") + "','"
								+ rs.getString("PK_SORTEO") + "','"
								+ rs.getString("FORMATO")
								+ "');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>";
							
	                    	 }
	                    	else{
	                    		html += "<a data-title=\"El Talonario esta TOTALMENTE vendido o TOTALMENTE NO vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverF5B();\" "
	    								+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>";
	                    	 }                  	
						}
						else
							html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC5B Devoluci&oacute;n No vendidos Parcialmente</span></a> ";
					}
					
					html += "</div>";

					html += "</div>";

					double monto = Double.parseDouble(rs.getString("MONTO"));

					Locale english = new Locale("en", "US");
					NumberFormat englishFormat = NumberFormat
							.getCurrencyInstance(english);
					System.out.println("US: " + englishFormat.format(monto));

					html += "<div class=\"result-price\">";
					html += englishFormat.format(monto) + " <small>MONTO</small>";					
					html += "<a class=\"btn btn-inverse btn-block\" href=\"SeguimientoTalonarios?idsorteo="+model.getIdSorteo()+"&idtalonario="+rs.getString("PK1")+"&menu=2\">Ver Detalles</a>";
					
						
					html += "</div>";

					html += "</li>";

					// System.out.println(rs.getString("NOMBRE"));

				}

				html += "</ul>";

			} else {

				html += "<li align= \"center\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";

				html += "<h1>No existen talonarios asignados al colaborador</h1>";
				html += "<p>Empiece por agregar talonarios o boletos al colaborador.</p>";

				html += "</div>";

				html += "</li>";

			}

		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		int numpag = Math.round(numreg / show);
		//int denumpag = numpag + 1;

		String paginado = "<ul class=\"pagination pagination-without-border pull-right m-t-0\">";

		if (pg > 1) {

			int pagante = pg - 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag("
					+ pagante + ");\">Anterior</a></li>";
		} else {
			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Anterior</a></li>";

		}

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
					paginado += "<li class=\"active\"><a href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag + ");\">" + sumpag + "</a>";
				} else {
					paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag + ");\">" + sumpag + "</a>";
				}

			}
		}

		if (pg <= numpag) {
			int numeropag = pg + 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag("
					+ numeropag + ");\">Siguiente</a></li>";

		} else {

			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Siguiente</a></li>";
		}

		paginado += "</ul>";

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		html = paginado + html + paginadoright;

		return html;

	}
	
	private String putCode(String HTML, int pkPermiso, String mark, String if_not_have, String if_have, SesionDatos sesion){
		String str_put;
		if (sesion.permisos.havePermiso(pkPermiso))
			str_put = if_have;
		else
			str_put = if_not_have;
		return HTML.replaceAll(mark, str_put);
	}
}
// cambio, Rogelio