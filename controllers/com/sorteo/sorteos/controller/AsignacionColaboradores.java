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
import com.sorteo.sorteos.model.mAsignacionColaboradores;

/**
 * Servlet implementation class AsignacionNichos
 */
@WebServlet("/AsignacionColaboradores")
public class AsignacionColaboradores extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AsignacionColaboradores() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *		response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mAsignacionColaboradores model = new mAsignacionColaboradores();
		SesionDatos sesion;


		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return; System.out.println("Sesion Sector return:"+sesion.pkSector);

		ServletContext context = getServletContext();
		String HTML = "";

		HTML += "USUARIO SESION: "+sesion.pkUsuario+ ", NICKNAME:"+ sesion.nickName;

		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int pg = 0;
		int show = 0;
		String search = "";
		String url = "";


		HTML += "SESION SECTOR:" + sesion.pkSector + ", ";
		HTML += "SESION NICHO:" + sesion.pkNicho;

		//String fullPathmenuprofile = "/WEB-INF/views/notificaciones.html";
		//String fullPathinfouser = "/WEB-INF/views/infouser.html";

		String view = request.getParameter("view");

		//10141 ACCESO A LA LISTA DE COLABORADOR
		if (!sesion.permisos.havePermiso(10141)){view = "error";}


		if (view == null) {
			view = "";
		}

		switch (view) {

		case "Agregar":
			fullPath = "/WEB-INF/views/sorteos/agregar.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);

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


			//cambio sin session
			int idsorteo = Integer.parseInt(request.getParameter("idsorteo"));
			int idsector = Integer.parseInt(request.getParameter("idsector"));
			int idnicho = Integer.parseInt(request.getParameter("idnicho"));


			HTML = Buscar(request, response, pg, show, search,idsorteo,idsector,idnicho,model,sesion);
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
			search = Global.decodeBase64(search);

			/*HashMap<String,ParametersBase64> map = ParametersBase64.parse(request.getParameter("q"), true);
			ParametersBase64 parameter;
			if ((parameter = map.get("search")) != null)
			{
				search = parameter.value;
				*/
			HTML = BuscarModal(request, response, pg, show, search, sesion.pkNicho,model,sesion);
			//}
			break;

		case "ExisteCarga":

			int idColaborador = Integer.valueOf(request.getParameter("idColaborador"));

			if (sesion.pkColaborador != idColaborador) {
				sesion.pkColaborador = idColaborador;
				sesion.guardaSesion();
			}

			if (this.ExisteCarga( model, sesion)) {
				HTML = "TRUE";
			}
			break;

		case "EliminaCarga":
			HTML = "" + model.eliminaCarga(sesion);
			break;

		case "EliminaColaborador":
			HTML = "" + model.eliminaColaborador(sesion);
			break;

		case "delete":
			// eliminarUsuario(request, response);
			break;

		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, MENU.SORTEOS, MENU.LISTA_SORTEOS, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		default:
			idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
			idsector = Integer.valueOf(request.getParameter("idsector"));
			idnicho = Integer.valueOf(request.getParameter("idnicho"));

			if (sesion.pkSorteo != idsorteo || sesion.pkSector != idsector || sesion.pkNicho != idnicho) {
				sesion.pkSorteo = idsorteo;
				sesion.pkSector = idsector;
				sesion.pkNicho = idnicho;
				sesion.guardaSesion();
			}

			fullPath = "/WEB-INF/views/sorteos/Asignacion/AsignacionColaboradores.html";
			menu = vista.initMenu(0, false, MENU.SORTEOS, MENU.LISTA_SORTEOS, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);

			// ________________________
			/*
			HTML = putCode(HTML, 10154, "#BTN_ASIGNAR_COLABORADOR#", "",
					"<a class=\"btn btn-primary m-r-5\" href=\"#\" onclick=\"AsignarColaborador()\">Asignar Colaborador</a>",
					sesion);
					*/
			HTML = putCode(HTML, 10170, "#BTN_ASIGNAR_TALONARIOS_COMPLETOS#",
					"<a href=\"#\" style=\"width: 100%;\" id=\"btncargaboletosfc3\" role=\"button\" disabled=\"disabled\" class=\"btn btn-success btn-lg\">  <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC2 - Asignar Talonarios Completos</a>",
					"<a href=\"javascript:asignaciontalonariosfc3('C')\" style=\"width: 100%;\" id=\"btncargaboletosfc3\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-barcode\"></i> FC2 - Asignar Talonarios Completos</a>",
					sesion);
			HTML = putCode(HTML, 10171, "#BTN_ASIGNAR_TALONARIOS_INCOMPLETOS#",
					"<a href=\"#\" style=\"width: 100%;\" id=\"btncargaboletosfc3b\" role=\"button\" disabled=\"disabled\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC5B - Asignar Talonarios Incompletos</a>",
					"<a href=\"javascript:asignaciontalonariosfc3('I')\" style=\"width: 100%;\" id=\"btncargaboletosfc3b\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-barcode\"></i> FC5B - Asignar Talonarios Incompletos</a>",
					sesion);
			HTML = putCode(HTML, 10173, "#ELIMINAR_ASIGNACION#",
					"<a href=\"#\" style=\"width: 100%;\" id=\"btndeletecargaboletos\" disabled=\"disabled\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Asignaci&oacute;n de Boletos</a>",
					"<a href=\"javascript:showFormatoEliminaTalonariosDeColaborador()\" style=\"width: 100%;\" id=\"btndeletecargaboletos\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-history\"></i> Eliminar Asignaci&oacute;n de Boletos</a>",
					sesion);
			
			/*HTML = putCode(HTML, 10174, "#ELIMINAR_COLABORADOR#",
					"<a href=\"#\" style=\"width: 100%;\" id=\"btneliminarsorteo\" disabled=\"disabled\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Colaborador</a>",
					"<a href=\"javascript:showFormatoEliminaColaborador()\" style=\"width: 100%;\" id=\"btneliminarsorteo\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-times\"></i> Eliminar Colaborador</a>",
					sesion);*/
			
			
			HTML = HTML.replaceAll("#ELIMINAR_COLABORADOR#", "");
			// ________________________


			String regex = "";

			HTML = HTML.replaceAll("#SORTEO#", model.Sorteo(sesion.pkSorteo));
			HTML = HTML.replaceAll("#SECTOR#", model.Sector(sesion.pkSector));
			HTML = HTML.replaceAll("#NICHO#", model.Nicho(sesion.pkNicho));
			HTML = HTML.replaceAll("#TOTAL#", "" + model.contar(sesion, "", idsorteo, idsector, idnicho));

			regex = "#URLSECTORES#";
			url = "SectoresAsignados?idsorteo=" + sesion.pkSorteo;
			HTML = HTML.replaceAll(regex, url);

			regex = "#URLNICHOS#";
			url = "AsignacionNichos?idsorteo=" + sesion.pkSorteo + "&idsector=" + sesion.pkSector;
			HTML = HTML.replaceAll(regex, url);

			regex = "#URLCOLABORADORES#";
			url = "AsignacionColaboradores?idsorteo=" + sesion.pkSorteo + "&idsector=" + sesion.pkSector + "&idnicho=" + sesion.pkNicho;
			HTML = HTML.replaceAll(regex, url);

			HTML = HTML.replaceAll("#IDNICHO#", "" + idnicho);
			
			break;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		out.close();

		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *		response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Factory vista = new Factory();
		mAsignacionColaboradores model = new mAsignacionColaboradores();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		String cadenacolaboradores = request.getParameter("idcolaboradores");
		String[] colaboradores = cadenacolaboradores.split(",");
		// model.setSectores(sectores);

		model.setIdNicho(Integer.valueOf(request.getParameter("idnicho")));

		model.setIdSorteo(sesion.pkSorteo);
		model.setIdsector(sesion.pkSector);
		model.guardarAsignacion(colaboradores, sesion);


		model.close();
	}

	protected String BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int idnicho,mAsignacionColaboradores model,SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Clave", "Nombre", "Apellido Paterno" };
		String[] campos = { "CLAVE", "NOMBRE", "APATERNO" };

		int numeroregistros = model.contarModal(idnicho,search);

		String HTML = CrearTablaModal(numeroregistros,
				model.paginacionModal(pg, show, search, idnicho,sesion.pkSector,sesion.pkSorteo), columnas,
				campos, pg, show);
		
		return HTML;
	}

	protected String CrearTablaModal(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show) {

		int i = (pg - 1) * show;
		String idcolaborador = "";

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
				+ "1c"
				+ "')\" id=\"checkboxall"
				+ "1c"
				+ "\" name=\"checkboxall\" /></th>";


		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ></th>";
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

					idcolaborador = rs.getString("PK_COLABORADOR");


					html += "<tr class=\"gradeA odd\" role=\"row\">";

					if(idcolaborador!= null){

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

			} else {

				html += "<tr> <td colspan=\"5\">"
						+"<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Colaboradores</h1><p>Empiece por agregar un nuevo colaborador.</p></div>"
						+ "</td></tr>";
			}

		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

		html += "</tbody>";
		html += "</table>";

		String paginado = Factory.Paginado(numreg, show, pg, "getPagModal");
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
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModal("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModal("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModal("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModal("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";
		//*/

		html = paginado + html;

		return html;

	}

	//

	protected String Buscar(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search, int idsorteoi,int idsectori, int idnichoi,mAsignacionColaboradores model,SesionDatos sesion)
			throws ServletException, IOException {

		//int numeroregistros = 100;

		//int maxRegistros = model.contar(sesion,"", idsorteoi, idsectori, idnichoi);
		int numreg = model.contar(sesion,search, idsorteoi, idsectori, idnichoi);

			 //	  cadena +=	 model.contar(sesion,search);
				//cadena += " | BUSCAR contar SESION SECTOR:"+sesion.pkSector+ ", ";
				//cadena += " | BUSCAR contar SESION NICHO:"+sesion.pkNicho;

		return CrearTabla(numreg, model.paginacion(pg, show, search, sesion,idsorteoi, idsectori, idnichoi), pg, show, idsorteoi, idsectori, idnichoi,model,sesion);
	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, int idsorteoi, int idsectori, int idnichoi,
			mAsignacionColaboradores model, SesionDatos sesion) {

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		StringBuilder sb = new StringBuilder();

		int idsorteo = idsorteoi;
		int idsector = idsectori;
		int idnicho = idnichoi;

		sb.append("<ul class=\"result-list\">");
		try {

			if (Integer.valueOf(numreg) > 0)
			{
				while (rs.next())
				{
					int numerotalonarios = 0;
					int numeroboletos = 0;
					double monto = 0;
					Locale english = new Locale("en", "US");
					NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

					model.setIdColaborador(Integer.valueOf(rs.getString("PK1")));// id colab
					model.setIdSorteo(idsorteo);
					model.setIdsector(idsector);
					model.setIdNicho(idnicho);

					/*
					numerotalonarios = model.numeroTalonarios(model);
					numeroboletos = model.numeroBoletos(model);
					monto = (double) model.MontoColaboradores(model);
					*/
					numerotalonarios = rs.getInt("TALONARIOS");
					numeroboletos = rs.getInt("BOLETOS");
					monto = rs.getDouble("MONTO");

					sb.append("<li id=\"talonario").append(rs.getString("PK1")).append("\" style='").append((sesion.sorteoActivo?"":Global.bkground_cerrado)).append("' >");

					sb.append("<div class=\"result-info\">");
					sb.append("<h4 class=\"title\"><span class=\"label label-primary\">"
							).append( rs.getString("CLAVE")).append("</span> <a href=\"BoletosSorteosColaboradores?")
							.append("idsorteo=").append(model.getIdSorteo())
							.append("&idsector=").append(model.getIdsector())
							.append("&idnicho=").append(model.getIdNicho())
							.append("&idcolaborador=").append(rs.getString("PK1"))
							.append("\"> "
							).append( rs.getString("NOMBRE")).append(" "
							).append( rs.getString("APATERNO")).append(" "
							).append( rs.getString("AMATERNO")).append("</a>");
					sb.append("</small>").append( "</a></h4>");


					sb.append("<br/><p class=\"location\">TALONARIOS: <span class=\"badge badge-danger pull\">"
							).append( numerotalonarios
							).append( "</span> BOLETOS: <span class=\"badge badge-danger pull\">"
							).append( numeroboletos).append("</span> </p>");

					sb.append("<p class=\"desc\">");

						sb.append("<div class=\"btn-row\">");

						sb.append("<a data-title=\"Talonarios\" data-container=\"body\" data-toggle=\"tooltip\" href=\"AsignacionTalonariosColaboradores?idsorteo=").append(idsorteoi).append("&idsector=").append(idsectori).append("&idnicho=").append(idnichoi).append("&idcolaborador=").append(rs.getString("PK1")).append("\" data-original-title=\"\" title=\"\"><i class=\"fa fa-tags\"></i><span style=\"font-size:11px;\">Talonarios</span></a>");
						sb.append("<a data-title=\"Reportes\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tasks\"></i><span style=\"font-size:11px;\">Reportes</span></a>");
						sb.append("<a data-title=\"Formatos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"FormatosColaborador?idsorteo=").append( idsorteo).append( "&idcolaborador="	).append( rs.getString("PK1")).append( "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tasks\"></i><span style=\"font-size:11px;\">Formatos</span></a>");
						if(sesion.sorteoActivo)
							sb.append("<a data-title=\"Opciones de borrado.\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showOptions(").append(rs.getString("PK1")).append(");\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-cog\"></i><span style=\"font-size:11px;\">Opciones</span></a>");
	
						sb.append("<a data-title=\"Muestra las estadisticas y datos de avance del sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"Dashboard.do?sorteo=").append(idsorteo).append("&sector=").append(idsector).append("&nicho=").append(idnicho).append("&colaborador=").append(rs.getString("PK1")).append("\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tachometer\"></i><span style=\"font-size:11px;\">Dashboard</span></a>");
	
						sb.append("</div>");
					sb.append("</p>");

					sb.append("</div>");

					sb.append("<div class=\"result-price\">");
					sb.append(englishFormat.format(monto)).append(" <small>MONTO</small>");
					sb.append("<a class=\"btn btn-inverse btn-block\" href=\"ColaboradorDetalle?sorteo=").append(sesion.pkSorteo).append("&sector=").append(sesion.pkSector).append("&nicho=").append(sesion.pkNicho).append("&colaborador=").append(rs.getString("PK1")).append("\">Ver Detalles</a>");
					sb.append("</div>");

					sb.append("</li>");

				}

				sb.append("</ul>");

			} else {

				sb.append("<li align= \"center\">");

				sb.append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">");

				sb.append("<h1>No existen Colaboradores Asignados</h1>");
				sb.append("<p>Asigne Colaboradores al Nicho.</p>");

				sb.append("</div>");

				sb.append("</li>");
				sb.append("</ul>");

			}

		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

		String paginado = Factory.paginado_2(numreg, show, pg,"getPag" , "colaborador(es)");

		sb.insert(0, paginado);
		sb.append(paginado);
		
		return sb.toString();
	}

	private String putCode(String HTML, int pkPermiso, String mark, String if_not_have, String if_have, SesionDatos sesion){
		String str_put;
		if (sesion.sorteoActivo && sesion.permisos.havePermiso(pkPermiso))
			str_put = if_have;
		else
			str_put = if_not_have;
		return HTML.replaceAll(mark, str_put);
	}

	// Para Configuracion
	private boolean ExisteCarga(mAsignacionColaboradores model, SesionDatos sesion){

		model.setIdSorteo(sesion.pkSorteo);
		model.setIdsector(sesion.pkSector);
		model.setIdNicho(sesion.pkNicho);
		model.setIdColaborador(sesion.pkColaborador);
		return model.ExisteCarga();
	}

}



