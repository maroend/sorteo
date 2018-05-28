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
import com.core.Global;
import com.core.MENU;
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mAsignacionNichos;

/**
 * Servlet implementation class AsignacionNichos
 */
@WebServlet("/AsignacionNichos")
public class AsignacionNichos extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AsignacionNichos() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mAsignacionNichos model = new mAsignacionNichos();
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
		//String fullPathmenuprofile = "/WEB-INF/views/notificaciones.html";
		//String fullPathinfouser = "/WEB-INF/views/infouser.html";
		int pg = 0;
		int show = 0;
		String search = "";
		String url = "";

		String view = request.getParameter("view");
		
		//10109 ACCESO A LA LISTA DE NICHOS		
		 if (!sesion.permisos.havePermiso(10109)){view = "error";}
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
			pg = Integer.valueOf(request.getParameter("pg"));
			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			Buscar(request, response, pg, show, search, model, sesion);

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
			BuscarModal(request, response, pg, show, search,
					Integer.valueOf(request.getParameter("idsector")), model, sesion);
			break;
			
		case "ExisteCarga":
			
			int idNicho = Integer.valueOf(request.getParameter("idNicho"));
			
			if (sesion.pkNicho != idNicho) {
				sesion.pkNicho = idNicho;
				sesion.guardaSesion();
				System.out.println("Actualizaci√≥n - " + sesion.toString());
			}
			if (this.ExisteCarga(model, sesion)) {
				HTML = "TRUE";
			}

			break;
			
	   case "EliminaCarga":
			HTML = "" + model.eliminaCarga(sesion);
			break;

	   case "EliminaNicho":
		   HTML = "" + model.eliminaNicho(sesion);
			break;
			

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "error":
			 
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);		
			break;		
			
			
			
		

		default:
			
			model.setIdsorteo(Integer.valueOf(request.getParameter("idsorteo")));
			model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
			if (sesion.pkSorteo != model.getIdSorteo() || sesion.pkSector != model.getIdSector()) {
				sesion.pkSorteo = model.getIdSorteo();
				sesion.pkSector = model.getIdSector();
				sesion.guardaSesion();
			}
			
			fullPath = "/WEB-INF/views/sorteos/Asignacion/AsignacionNichos.html";
			menu = vista.initMenu(0, false, MENU.SORTEOS, MENU.LISTA_SORTEOS, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			String regex = "#SORTEO#";
			HTML = HTML.replaceAll(regex, model.Sorteo(sesion.pkSorteo));
			
			regex = "#SECTOR#";
			HTML = HTML.replaceAll(regex, model.Sector(sesion.pkSector));
			
			regex = "#URLSECTORES#";
		    url = "SectoresAsignados?idsorteo="+sesion.pkSorteo;
			HTML = HTML.replaceAll(regex, url);
			
			regex = "#URLNICHOS#";
		    url = "AsignacionNichos?idsorteo="+sesion.pkSorteo+"&idsector="+sesion.pkSector;
			HTML = HTML.replaceAll(regex, url);
			
			
			//PERMISO BOTON
			
			 String boton = "";
			
			/* if (sesion.sorteoActivo && sesion.permisos.havePermiso(10123)){			 
				    boton = "<a class=\"btn btn-primary m-r-5\" href= \"#\" onclick=\"AsignarSector()\">Asignar Nicho</a>";	
			 }			
			 HTML = HTML.replaceAll( "#BTNASIGNAR#", boton);*/
			 HTML = HTML.replaceAll( "#BTNASIGNAR#", "");
			
			  String opcionboton ="<a href=\"#\"  disabled=\"disabled\"  "
				    		+ "style=\"width: 100%;\" id=\"btnasignacionn\" role=\"button\" class=\"btn btn-success btn-lg\"> "
				    		+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC2 - ASIGNACI&Oacute;N DE TALONARIOS COMPLETOS O INCOMPLETOS</a>";
			 
			if (sesion.permisos.havePermiso(10151)){	
				 
								
				opcionboton = "<a href=\"#\"  onclick=\" AsignarTalonario()\"   "
						+ "style=\"width: 100%;\" id=\"btnasignacionn\" role=\"button\" class=\"btn btn-success btn-lg\"> "
						+ "<i class=\"fa fa-2x fa-barcode\"></i> FC2 - ASIGNACI&Oacute;N DE TALONARIOS COMPLETOS O INCOMPLETOS</a>";
			}
			
			HTML = HTML.replaceAll( "#OPCIONFC2#", opcionboton);
			
			//nuevo
			if (sesion.permisos.havePermiso(10151)){	
				 
				
				opcionboton = "<a href=\"#\"  onclick=\" AsignarTalonarioFc3Colaborador()\"   "
						+ "style=\"width: 100%;\" id=\"btnasignacionn\" role=\"button\" class=\"btn btn-success btn-lg\"> "
						+ "<i class=\"fa fa-2x fa-barcode\"></i> FC3 - ASIGNACI&Oacute;N DE TALONARIOS COMPLETOS POR COLABORADOR</a>";
			}
			else
				opcionboton = "";
			
			HTML = HTML.replaceAll( "#BTN_ASIGNAR_TALONARIOS_SP#", opcionboton);
			
			
			
			
			
			/****************************INICIO****************************************************/
			
			
				
			 /*  opcionboton ="<a href=\"#\"  disabled=\"disabled\"  "
				    		+ "style=\"width: 100%;\" id=\"btndevolucion\" role=\"button\" class=\"btn btn-success btn-lg\"> "
				    		+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC5-FC5B - DEVOLUCION DE BOLETOS NO VENDIDIOS O NO VENDIDOS PARCIALMENTE ELECTRONICOS</a>";
			 
			if (sesion.permisos.havePermiso(10151)){	
				 
								
				opcionboton = "<a href=\"#\"  onclick=\" DevolucionBoletosMasivaModal()\"   "
						+ "style=\"width: 100%;\" id=\"btndevolucion\" role=\"button\" class=\"btn btn-success btn-lg\"> "
						+ "<i class=\"fa fa-2x fa-barcode\"></i> FC5-FC5B - DEVOLUCION DE BOLETOS NO VENDIDIOS O NO VENDIDOS PARCIALMENTE - ELECTR”NICOS</a>";
			}
			
			HTML = HTML.replaceAll( "#OPCIONDEVOLUCIONES#", opcionboton);*/
			HTML = HTML.replaceAll( "#OPCIONDEVOLUCIONES#", "");
			
			
			
			/********************************************************************************/
			
						
			
			opcionboton = "<a href=\"#\" "
							+ "style=\"width: 100%;\" id=\"btndeletecargaboletos\" disabled=\"disabled\" role=\"button\" class=\"btn btn-success btn-lg\">"
							+"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Asignaci&oacute;n de Boletos</a>";
			
			 if (sesion.permisos.havePermiso(10152)){	
				
				opcionboton = "<a href=\"javascript:showFormatoEliminaTalonariosDeNicho()\" "
							+ "style=\"width: 100%;\" id=\"btndeletecargaboletos\"	role=\"button\" class=\"btn btn-success btn-lg\">"
							+"<i class=\"fa fa-2x fa-history\"></i> Eliminar Asignaci&oacute;n de Boletos</a>";	
			}
			
			HTML = HTML.replaceAll( "#OPCIONELIMINAR#", opcionboton);
			
			/*opcionboton = "<a href=\"()\" style=\"width: 100%;\"	disabled=\"disabled\" "
							+ "id=\"btneliminarsorteo\" role=\"button\" class=\"btn btn-success btn-lg\">"
							+ " <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Nicho</a>";
			if (sesion.permisos.havePermiso(10153)){	
				
				opcionboton = "<a href=\"javascript:showFormatoEliminaNicho()\" style=\"width: 100%;\"	"
							+ "id=\"btneliminarsorteo\" role=\"button\" class=\"btn btn-success btn-lg\">"
							+ " <i class=\"fa fa-2x fa-times\"></i> Eliminar Nicho</a>";	
			}
			HTML = HTML.replaceAll( "#OPCIONELIMINARNICHO#", opcionboton);*/
			HTML = HTML.replaceAll( "#OPCIONELIMINARNICHO#", "");
			
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		mAsignacionNichos model = new mAsignacionNichos();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		String cadenanichos = request.getParameter("idnichos");
		String[] nichos = cadenanichos.split(",");
		
		model.setIdSector(sesion.pkSector);
		model.setIdsorteo(sesion.pkSorteo);
		model.guardarAsignacion(nichos, model, sesion);

		model.close();
	}

	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int idsector, mAsignacionNichos model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String[] columnas = { "Clave", "Nicho", "Descripcion" };
		String[] campos = { "CLAVE", "NICHO", "DESCRIPCION" };

		int numeroregistros = model.contarModal(idsector,search,sesion.pkSorteo);

		String HTML = CrearTablaModal(numeroregistros,
				model.paginacionModal(pg, show, search, idsector,sesion.pkSorteo), columnas,
				campos, pg, show, model, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTablaModal(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show, mAsignacionNichos model, SesionDatos sesion) {

		int i = (pg - 1) * show;
		
		String idnicho = "";

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
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onclick=\"seleccionarTodo('N1')\" id=\"checkboxallN1\" name=\"checkboxall\"/></th>";
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
				
				idnicho = rs.getString("PK_NICHO");

				html += "<tr class=\"gradeA odd\" role=\"row\">";
				
				
				if(idnicho != null)
				{
					html += "<td class=\"sorting_1\"><i class=\"fa fa-2x fa-check-circle\"></i></td>";					
				}
				else
					html += "<td class=\"sorting_1\"><input id=" + rs.getInt("PK1")	+ " type=\"checkbox\" /></td>";
				
			
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
					html += "<td class=\"sorting_1\">" + rs.getString(campo) + "</td>";
				}

				
				html += "</tr>";
			}
			
			  
			
			
        } else{
        	html +="<tr><td colspan=\"5\">"
    				+"<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Nichos</h1>"
    				+ "<p>Empiece por agregar un nuevo Nicho.</p></div>"
    				+ "</td></tr>";	
        }
			
			
			
			
			
			
			
		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

		html += "</tbody>";
		html += "</table>";

		int numpag = Math.round(numreg / show);
		int denumpag = numpag + 1;

		String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando " + pg + " de " + denumpag + " total " + numreg + " elementos</div>";
		paginado += "<div class=\"dataTables_paginate paging_simple_numbers\" id=\"data-table_paginate\">";

		if (pg > 1) {
			// <a href="?pg=<%=pg-1 %>">Anterior</a>

			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModal(" + pagante + ");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

		} else {

			paginado += "<a class=\"paginate_button previous disabled\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

		}

		paginado += "<span>";

		// Calcular el inicio del arreglo
		int inipg = 0;
		int r = (pg) / show;
		int sumpag = 0;

		if (r == 0) {

			inipg = pg - 1;
		} else {
			inipg = ((pg - 1) / show) - show;
		}

		for (int j = inipg; j < 10 + inipg + 1; j++) {
			if (j < numpag + 1) {

				sumpag = j + 1;

				if (sumpag == pg) {
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModal(" + sumpag + ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">" + sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModal(" + sumpag + ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">" + sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModal(" + numeropag + ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";

		html = paginado + html;

		return html;

	}

	//

	protected void Buscar(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search
			, mAsignacionNichos model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setIdsorteo(sesion.pkSorteo);
		model.setIdSector(sesion.pkSector);

		int numeroregistros = model.contar(sesion,search);

		String HTML = CrearTabla(numeroregistros,
				model.paginacion(pg, show,search, sesion),
				pg, show, model, sesion);
		PrintWriter out = response.getWriter();
		HTML = HTML+"#%#"+model.getTotalregistros();
		out.println(HTML);

	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show,
			mAsignacionNichos model, SesionDatos sesion) {

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

		//int idsorteo = sesion.pkSorteo;

		String html = "<ul class=\"result-list\">";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {
					model.setIdNicho(Integer.parseInt(rs.getString("PK1")));// id
																		// nicho
					model.setIdsorteo((sesion.pkSorteo));
					model.setIdSector(sesion.pkSector);

					numerotalonarios = model.numeroTalonarios(model);
					numeroboletos = model.numeroBoletos(model);

					monto = (double) model.MontoNicho(model);

					html += "<li id=\"nicho" + rs.getString("PK1") + "\"  style='"+(sesion.sorteoActivo?"":Global.bkground_cerrado)+"'>";

					html += "<div class=\"result-info\">";
					html += "<h4 class=\"title\"><span class=\"label label-primary\">" + rs.getString("CLAVE") + "</span>  <a href=\"BoletosSorteosNichos?idnicho="+rs.getString("PK1")+"\">"
					+ rs.getString("NICHO") + "</a></h4>";
					

					html += "<p class=\"location\">TALONARIOS: <span class=\"badge badge-danger pull\">" + numerotalonarios + "</span> BOLETOS: <span class=\"badge badge-danger pull\">" + numeroboletos + "</span> </p>";

					/*html += "<p class=\"desc\">";
					html += rs.getString("DESCRIPCION");
					html += "</p>";*/

					html += "<div class=\"btn-row\">";
				//	html += "<a data-title=\"Eliminar nichos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:eliminar('" + rs.getString("PK1") + "','" + rs.getString("NICHO") + "','" + idsorteo + "');\"  data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">Eliminar</span></a>";
					html += "<a data-title=\"Responsables Asignados\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SorteosNichoUsuarios?idsorteo=" + model.getIdSorteo() + "&idnicho=" + rs.getString("PK1") + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-child\"></i><span style=\"font-size:11px;\">Responsables</span></a>";
					html += "<a data-title=\"Asigna talonarios\" data-container=\"body\" data-toggle=\"tooltip\" href=\"AsignacionTalonariosNichos?idsorteo=" + model.getIdSorteo() + "&idnicho=" + rs.getString("PK1") + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-tags\"></i> <span style=\"font-size:11px;\">Talonarios</span></a>";
					html += "<a data-title=\"Formatos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"FormatosNichos?idsorteo="+ model.getIdSorteo()+ "&idnicho="	+ rs.getString("PK1")+ "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tasks\"></i><span style=\"font-size:11px;\">Formatos</span></a>";
					if(sesion.sorteoActivo)
						html += "<a data-title=\"Opciones\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showOptions("+ rs.getInt("PK1") + ");\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-cog\"></i><span style=\"font-size:11px;\">Opciones</span></a>";
					
                     if (sesion.permisos.havePermiso(10140)){			
						 
                    	 html += "<a data-title=\"Muestra las estadisticas y datos de avance del sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"Dashboard.do?sorteo=" + model.getIdSorteo() + "&sector="+model.getIdSector()+"&nicho=" + rs.getString("PK1") + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tachometer\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";
							 
					 }else{						
							
						 html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tasks\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";						 
														
					 }	
					
					
					
					
					html += "<a data-title=\"Revise que Colaboradores estan asignados en este sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"AsignacionColaboradores?idsorteo="+model.getIdSorteo()+"&idsector="+model.getIdSector()+"&idnicho=" + rs.getString("PK1") + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-user\"></i><span style=\"font-size:11px;\">Colaboladores</span></a>";
					html += "</div>";

					html += "</div>";

					html += "<div class=\"result-price\">";
					html += englishFormat.format(monto) + "<small>MONTO</small>";
					html += "<a class=\"btn btn-inverse btn-block\" href=\"NichoDetalle?sorteo="+model.getIdSorteo()+"&sector="+model.getIdSector()+"&nicho="+rs.getString("PK1")+"\">Ver Detalles</a>";
					html += "</div>";

					html += "</li>";

				}

				html += "</ul>";

			} else {

				
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

				html += "<h1>No existen Nichos Asignados</h1>";
				html += "<p>Asigne Nichos al Sector.</p>";

				html += "</div>";
				
				html += "</ul>";

			}

		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

		int numpag = Math.round(numreg / show);

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
					paginado += "<li class=\"active\"><a href=\"javascript:void(0)\" onclick=\"getPag(" + sumpag + ");\">" + sumpag + "</a>";
				} else {
					paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag(" + sumpag + ");\">" + sumpag + "</a>";
				}

			}
		}

		if (pg <= numpag) {
			int numeropag = pg + 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag(" + numeropag + ");\">Siguiente</a></li>";

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

	// Para Configuracion
	private boolean ExisteCarga(mAsignacionNichos model, SesionDatos sesion)
	{
		model.setIdsorteo(sesion.pkSorteo);
		model.setIdSector(sesion.pkSector);
		model.setIdNicho(sesion.pkNicho);
		return model.ExisteCarga();	
	}

}
