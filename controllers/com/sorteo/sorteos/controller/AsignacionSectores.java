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

import org.apache.commons.lang3.StringEscapeUtils;

import com.core.Factory;
import com.core.Global;
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mAsignacionSectores;

/**
 * Servlet implementation class AsignacionSectores
 */
@WebServlet("/SectoresAsignados")
public class AsignacionSectores extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AsignacionSectores() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mAsignacionSectores model = new mAsignacionSectores();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		// SessionListener.SessionStart(request, response,false);

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String url = "";

		String view = request.getParameter("view");
		
		//10109 ACCESO A LA LISTA DE SECTOR		
		 if (!sesion.permisos.havePermiso(10095)){view = "error";}
		 else{
			 
			 if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){view = "errorCerrado";}
			 
		 }
		 

		if (view == null) {
			view = "";
		}

		switch (view) {
		
		case "errorCerrado":
			fullPath = "/WEB-INF/views/error_sinpermiso.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
		

		case "Agregar":
			fullPath = "/WEB-INF/views/sorteos/agregar.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
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
			Buscar(request, response, pg, show, search, model, sesion);

			break;

		case "BuscarModal":
			int pg2 = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg2 = 1;
			} else {
				pg2 = Integer.valueOf(request.getParameter("pg"));
			}

			int show2 = Integer.valueOf(request.getParameter("show"));
			String search2 = request.getParameter("search");
			BuscarModal(request, response, pg2, show2, search2, model, sesion);
			break;


		case "ExisteCarga":
			
			int idSector = Integer.valueOf(request.getParameter("idSector"));
			if (sesion.pkSector != idSector) {
				sesion.pkSector = idSector;
				sesion.guardaSesion();
				System.out.println("<sector guardado>");
			}
			if (this.ExisteCarga(model, sesion)) {
				HTML = "TRUE";
			}

			break;
			
		case "EliminaCarga":
			HTML = "" + model.eliminaCarga(sesion);
			break;

		case "EliminaSector":
			HTML = "" + model.eliminaSector(sesion);
			break;
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
			
		default:
			
			String str_idsorteo = request.getParameter("idsorteo");
			if (str_idsorteo != null) {
				int idsorteo = Integer.valueOf(str_idsorteo);
				if (sesion.pkSorteo != idsorteo) {
					sesion.pkSorteo = idsorteo;
					sesion.guardaSesion();
				}
			}
			
			fullPath = "/WEB-INF/views/sorteos/Asignacion/AsignacionSectores.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			 
			String regex = "#SORTEO#";
			HTML = HTML.replaceAll(regex, model.Sorteo(sesion.pkSorteo));
			
		    regex = "#URLSECTORES#";
		    url = "SectoresAsignados?idsorteo="+sesion.pkSorteo;
			HTML = HTML.replaceAll(regex, url);
			
			//PERMISO BOTON
			
			String opcionboton = "<a href=\"#\" disabled=\"disabled\" "
				    		+ "style=\"width: 100%;\" id=\"btncargaboletosfc1\" role=\"button\" class=\"btn btn-success btn-lg\"> "
				    		+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC1 - Distribuir Talonarios Completos</a>";
			 
			if (sesion.permisos.havePermiso(10120)) {

				opcionboton = "<a href=\"javascript:asignarboletosfc1()\" "
						+ "style=\"width: 100%;\" id=\"btncargaboletosfc1\" role=\"button\" class=\"btn btn-success btn-lg\"> "
						+ "<i class=\"fa fa-2x fa-barcode\"></i> FC1 - Distribuir Talonarios Completos</a>";
			}
			
			
			HTML = HTML.replaceAll("#OPCIONFC1#", opcionboton);
			
			
			/****************************INICIO****************************************************/
			
			
			
			  /* opcionboton ="<a href=\"#\"  disabled=\"disabled\"  "
				    		+ "style=\"width: 100%;\" id=\"btndevolucion\" role=\"button\" class=\"btn btn-success btn-lg\"> "
				    		+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC5-FC5B - DEVOLUCION DE BOLETOS NO VENDIDIOS O NO VENDIDOS PARCIALMENTE ELECTRONICOS</a>";
			 
			if (sesion.permisos.havePermiso(10120)){	
				 
								
				opcionboton = "<a href=\"#\"  onclick=\" DevolucionBoletosMasivaModal()\"   "
						+ "style=\"width: 100%;\" id=\"btndevolucion\" role=\"button\" class=\"btn btn-success btn-lg\"> "
						+ "<i class=\"fa fa-2x fa-barcode\"></i> FC5-FC5B - DEVOLUCION DE BOLETOS NO VENDIDIOS O NO VENDIDOS PARCIALMENTE - ELECTR”NICOS</a>";
			}
			
			HTML = HTML.replaceAll( "#OPCIONDEVOLUCIONES#", opcionboton);*/
			HTML = HTML.replaceAll( "#OPCIONDEVOLUCIONES#", "");
			
			
			
			/********************************************************************************/
			
			
			
			
			/* opcionboton = "<a href=\"#\" disabled=\"disabled\" "
		    		+ "style=\"width: 100%;\" id=\"btncargaboletosfc1\" role=\"button\" class=\"btn btn-success btn-lg\"> "
		    		+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC1 - Distribuir Talonarios Completos</a>";
	 
			if (sesion.permisos.havePermiso(10120)) {
		
				opcionboton = "<a href=\"javascript:asignarboletosfc1()\" "
						+ "style=\"width: 100%;\" id=\"btncargaboletosfc1\" role=\"button\" class=\"btn btn-success btn-lg\"> "
						+ "<i class=\"fa fa-2x fa-barcode\"></i> FC1 - Distribuir Talonarios Completos</a>";
			}*/
			
						

			opcionboton = "<a href=\"#\"  disabled=\"disabled\" "
					+ "style=\"width: 100%;\" id=\"btndeletecargaboletos\"	role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Asignaci√≥n de Talonarios y Boletos	</a>";

			if (sesion.permisos.havePermiso(10121)) {

				opcionboton = "<a href=\"javascript:showFormatoElimina()\" "
						+ "style=\"width: 100%;\" id=\"btndeletecargaboletos\"	role=\"button\" class=\"btn btn-success btn-lg\">"
						+ "<i class=\"fa fa-2x fa-history\"></i> Eliminar Asignaci&oacute;n de Talonarios y Boletos	</a>";
			}

			HTML = HTML.replaceAll("#OPCIONELIMINAR#", opcionboton);

			/*opcionboton = "<a href=\"javascript:#\"  disabled=\"disabled\" style=\"width: 100%;\"	"
					+ "id=\"btneliminarsorteo\" role=\"button\" class=\"btn btn-success btn-lg\">"
					+ " <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Sector</a>";

			if (sesion.permisos.havePermiso(10122)) {

				opcionboton = "<a href=\"javascript:showFormatoEliminaSector()\" style=\"width: 100%;\"	"
						+ "id=\"btneliminarsorteo\" role=\"button\" class=\"btn btn-success btn-lg\">"
						+ " <i class=\"fa fa-2x fa-times\"></i> Eliminar Sector</a>";
			}
			HTML = HTML.replaceAll("#OPCIONELIMINARSECTOR#", opcionboton);*/
			HTML = HTML.replaceAll("#OPCIONELIMINARSECTOR#", "");

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

		mAsignacionSectores model = new mAsignacionSectores();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		// model.setSector(request.getParameter("id"));

		String sectorcadena = request.getParameter("idsectores");
		String[] sectores = sectorcadena.split(",");
		// model.setSectores(sectores);

		model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
		model.guardarAsignacionSorteoSector(sectores, model, sesion);

		// PrintWriter out = response.getWriter();
		// out.println(sectorcadena);

		model.close();
	}

	/*
	protected void EliminarSector(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
		model.setIdsorteo(Integer.valueOf(request.getParameter("idsorteo")));
		model.Borrar(model);

	}
	//*/

	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mAsignacionSectores model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Clave", "Sector", "Descripcion" };
		String[] campos = { "CLAVE", "SECTOR", "DESCRIPCION" };

		int numeroregistros = model.contarModal(search,sesion.pkSorteo);

		String HTML = CrearTablaModal(numeroregistros,
				model.paginacionModal(pg, show, search,sesion.pkSorteo), columnas, campos, pg,
				show);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTablaModal(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show) {

		int i = (pg - 1) * show;
		String idsector = "";

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
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onclick=\"seleccionarTodo('S1')\" id=\"checkboxallS1\" name=\"checkboxall\"/></th>";
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
					
					
					idsector = rs.getString("PK_SECTOR");
					
					
	
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					
					
					if(idsector != null){		
						html += "<td class=\"sorting_1\"><i class=\"fa fa-2x fa-check-circle\"></i></td>";					
					}
					else
						html += "<td class=\"sorting_1\"><input id=" + rs.getInt("PK1")	+ " type=\"checkbox\" /></td>";
					
	
					/*html += "<td class=\"sorting_1\"><input id=" + rs.getInt("PK1")
							+ " type=\"checkbox\" /></td>";*/
					html += "<td class=\"sorting_1\">" + i + "</td>";
	
					for (String campo : campos) {
						html += "<td class=\"sorting_1\">" + rs.getString(campo) + "</td>";
					}
				
					html += "</tr>";
				}
			} else {
				html += "<tr> <td colspan=\"5\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Sectores Asignados</h1>";
				html += "<p>Asigne Sectores al Sorteo.</p>";
				html += "</div>";
				html += "</td></tr>";
			}
			
		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

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
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModalSector("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModalSector("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModalSector("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModalSector("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";

		html = paginado + html;

		return html;

	}

	//

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mAsignacionSectores model, SesionDatos sesion) throws ServletException, IOException {

		model.setIdSorteo(sesion.pkSorteo);
		
		int numeroregistros = model.contar(sesion,search);
		model.setTotalregistros(numeroregistros);

		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search, sesion), pg, show, model, sesion);
		PrintWriter out = response.getWriter();
		HTML = HTML+"#%#"+model.getTotalregistros();
		out.println(HTML);

	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show,
			mAsignacionSectores model, SesionDatos sesion) {

		//int i = (pg - 1) * show;
		
		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";
		int numerotalonarios = 0;
		int numeroboletos = 0;
		double monto = 0;
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		String html = "<ul class=\"result-list\">";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {

					//i++;
					
					model.setIdSector(Integer.valueOf(rs.getString("PK1")));
					numerotalonarios = model.numeroTalonarios(model);
					numeroboletos = model.numeroBoletos(model);

					monto = (double) model.MontoSector(model);

					html += "<li id=\"sector" + model.getIdSector() + "\" style='"+(sesion.sorteoActivo?"":Global.bkground_cerrado)+"'>";

					html += "<div class=\"result-info\">";
					html += "<h4 class=\"title\"> <span class=\"label label-primary\">"
							+ rs.getString("CLAVE")
							+ "</span>  <a href=\"BoletosSorteosSectores?idsorteo="+model.getIdSorteo()+"&idsector="+model.getIdSector()+"\">"
							+ StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))  + "</a></h4>";
					
					html += "<p class=\"location\">TALONARIOS: <span class=\"badge badge-danger pull\">"
							+ numerotalonarios
							+ "</span> BOLETOS: <span class=\"badge badge-danger pull\">"
							+ numeroboletos + "</span> </p>";

					html += "<p class=\"desc\">";
					html += "&nbsp;"; //rs.getString("DESCRIPCION");
					html += "</p>";
					

					html += "<div class=\"btn-row\">";
					//html += "<a data-title=\"Eliminar Sectores\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:eliminarSectores('"+ rs.getString("PK1")	+ "','"	+ rs.getString("SECTOR")+ "','"	+ idsorteo+ "');\"  data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">Eliminar</span></a>";
					html += "<a data-title=\"Responsables Asignados\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SorteosSectUsuarios?idsorteo="+ model.getIdSorteo()+ "&idsector="	+ model.getIdSector() + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-child\"></i><span style=\"font-size:11px;\">Responsables</span></a>";
					html += "<a data-title=\"Asigna talonarios\" data-container=\"body\" data-toggle=\"tooltip\" href=\"AsignacionTalonarios?idsorteo="	+ model.getIdSorteo()+ "&idsector="+ model.getIdSector() + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-tags\"></i> <span style=\"font-size:11px;\">Talonarios</span></a>";
					html += "<a data-title=\"Formatos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"FormatosSectores?idsorteo="+ model.getIdSorteo()+ "&idsector=" + model.getIdSector() + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tasks\"></i><span style=\"font-size:11px;\">Formatos</span></a>";
					if(sesion.sorteoActivo)
						html += "<a data-title=\"Opciones\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showOptions("+ rs.getInt("PK1") + ");\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-cog\"></i><span style=\"font-size:11px;\">Opciones</span></a>";
					
					if (sesion.permisos.havePermiso(10108)){
						 
						 html += "<a data-title=\"Muestra las estadisticas y datos de avance del sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"Dashboard.do?sorteo="+ model.getIdSorteo()+ "&sector="	+ model.getIdSector() + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tachometer\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";
								
					}else{
						html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tasks\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";						 
					}	
					
					
					
					html += "<a data-title=\"Revise que sectores estan asignados en este sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"AsignacionNichos?idsorteo="+ model.getIdSorteo()+"&idsector=" + model.getIdSector() + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-user\"></i><span style=\"font-size:11px;\">Nichos</span></a>";
					html += "</div>";

					html += "</div>";

					html += "<div class=\"result-price\">";
					html += englishFormat.format(monto)	+ " <small>MONTO</small>";
					html += "<a class=\"btn btn-inverse btn-block\" href=\"SectorDetalle?sorteo="+model.getIdSorteo()+"&sector="+model.getIdSector()+"\">Ver Detalles</a>";
					html += "</div>";

					html += "</li>";

				}

				html += "</ul>";

			} else {

				
				html += "<li>";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";

				html += "<h1>No existen Sectores Asignados</h1>";
				html += "<p>Asigne Sectores al Sorteo.</p>";

				html += "</div>";
				html += "</li>";
				
				html += "</ul>";

			}

		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

		
		String paginado = Factory.paginado_2(numreg, show, pg);
		
		/*
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
		*/

		html = paginado + html + paginado;

		return html;

	}

	// Para Configuracion
	private boolean ExisteCarga(mAsignacionSectores model, SesionDatos sesion){
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSector(sesion.pkSector);
		return model.ExisteCarga();	
	}

}
