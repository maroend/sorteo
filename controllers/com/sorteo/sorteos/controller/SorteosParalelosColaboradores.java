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
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mSorteosParalelosColaboradores;

/**
 * Servlet implementation class AsignacionNichos
 */
@WebServlet("/SorteosParalelosColaboradores")
public class SorteosParalelosColaboradores extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SorteosParalelosColaboradores() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *		response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mSorteosParalelosColaboradores model = new mSorteosParalelosColaboradores();
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
		int idparalelo = 0;


		HTML += "SESION SECTOR:" + sesion.pkSector + ", ";
		HTML += "SESION NICHO:" + sesion.pkNicho;

		//String fullPathmenuprofile = "/WEB-INF/views/notificaciones.html";
		//String fullPathinfouser = "/WEB-INF/views/infouser.html";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String view = request.getParameter("view");
		
		//String view = "Buscar";

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
			out.println(HTML);
			break;

		case "Buscar":
			pg = 0; 
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			//System.out.println("pag:"+pg);

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
		//	search = Global.decodeBase64(search);
			System.out.println("search:"+search);

		//	show = Integer.valueOf(request.getParameter("show"));
			//search = "";
			//search = Global.decodeBase64(search);
			//search = request.getParameter("search");

			//cambio sin session
			int idsorteo = Integer.parseInt(request.getParameter("idsorteo"));
			idparalelo = Integer.parseInt(request.getParameter("idparalelo"));
			int idnicho = Integer.parseInt(request.getParameter("idnicho"));
			int idsector = Integer.parseInt(request.getParameter("idsector"));


			Buscar(request, response, pg, show, search,idsorteo,idparalelo,idnicho,idsector,model,sesion);
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
				//BuscarModal(request, response, pg, show, search, sesion.pkNicho,model,sesion);
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
			out.println(HTML);
			break;

		case "EliminaCarga":
			HTML = "" + model.eliminaCarga(sesion);
			out.println(HTML);
			break;

		case "EliminaColaborador":
			HTML = "" + model.eliminaColaborador(sesion);
			break;

		case "deleteColaboradorBoletos":
			
			int idColabortador = Integer.valueOf(request.getParameter("idColabortador"));			
			int idsortoparalelo = Integer.valueOf(request.getParameter("idsortoparalelo"));
			int idsorteonicho = Integer.valueOf(request.getParameter("idsorteonicho"));
			
			idsector = Integer.valueOf(request.getParameter("idsector"));
			idnicho = Integer.valueOf(request.getParameter("idnicho"));
			
			 model.deleteColaboradorBoletos(idColabortador, idsortoparalelo, idsorteonicho,idsector,idnicho);
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
			idsector = Integer.valueOf(request.getParameter("idsector"));
			idnicho = Integer.valueOf(request.getParameter("idnicho"));

			if (sesion.pkSorteo != idsorteo || sesion.pkNicho != idnicho) {
				sesion.pkSorteo = idsorteo;
				sesion.pkSector = idsector;
				sesion.pkNicho = idnicho;
				sesion.guardaSesion();
			}
			
			 idparalelo = Integer.valueOf(request.getParameter("idparalelo"));

			fullPath = "/WEB-INF/views/sorteos/SorteosParalelos/SorteosParalelosColaboradores.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			String contents = "";
			//
			// ________________________
			/*HTML = putCode(HTML, 10154, "#BTN_ASIGNAR_COLABORADOR#", "",
					"<a class=\"btn btn-primary m-r-5\" href=\"#\" onclick=\"AsignarColaborador()\">Asignar Colaborador</a>",sesion);*/

			HTML = putCode(HTML, 10170, "#BTN_ASIGNAR_TALONARIOS_COMPLETOS#", "<a href=\"#\" style=\"width: 100%;\" id=\"btncargaboletosfc3\" role=\"button\" disabled=\"disabled\" class=\"btn btn-success btn-lg\">  <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC3 - Asignar Talonarios Completos</a>",
					"<a href=\"javascript:asignaciontalonariosfc3('C')\" style=\"width: 100%;\" id=\"btncargaboletosfc3\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-barcode\"></i> FC3 - Asignar Talonarios Completos</a>",
					sesion);
			HTML = putCode(HTML, 10171, "#BTN_ASIGNAR_TALONARIOS_INCOMPLETOS#", "<a href=\"#\" style=\"width: 100%;\" id=\"btncargaboletosfc3b\" role=\"button\" disabled=\"disabled\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC3B - Asignar Talonarios Incompletos</a>",
					"<a href=\"javascript:asignaciontalonariosfc3('I')\" style=\"width: 100%;\" id=\"btncargaboletosfc3b\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-barcode\"></i> FC3B - Asignar Talonarios Incompletos</a>",
					sesion);
			HTML = putCode(HTML, 10173, "#ELIMINAR_ASIGNACION#", "<a href=\"#\" style=\"width: 100%;\" id=\"btndeletecargaboletos\" disabled=\"disabled\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Asignaci&oacute;n de Boletos</a>",
					"<a href=\"javascript:showFormatoEliminaTalonariosDeColaborador()\" style=\"width: 100%;\" id=\"btndeletecargaboletos\" role=\"button\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-history\"></i> Eliminar Asignaci&oacute;n de Boletos</a>",
					sesion);
			HTML = putCode(HTML, 10174, "#ELIMINAR_COLABORADOR#", "<a href=\"#\" style=\"width: 100%;\" id=\"btneliminarsorteo\" disabled=\"disabled\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Colaborador</a>",
					"<a href=\"javascript:showFormatoEliminaColaborador()\" style=\"width: 100%;\" id=\"btneliminarsorteo\" class=\"btn btn-success btn-lg\"> <i class=\"fa fa-2x fa-times\"></i> Eliminar Colaborador</a>",
					sesion);

			// ________________________


			String regex = "";

			//HTML = HTML.replaceAll("#SORTEO#", model.Sorteo(sesion.pkSorteo));
			//HTML = HTML.replaceAll("#SECTOR#", model.Sector(sesion.pkSector));
			//HTML = HTML.replaceAll("#NICHO#", model.Nicho(sesion.pkNicho));
			
			
			
			
			HTML = HTML.replaceAll("#SORTEO#", model.consultaSorteo(idsorteo));
			HTML = HTML.replaceAll("#ID_SORTEO#", "" + idsorteo);
			
			HTML = HTML.replaceAll("#PARALELO#", model.consultaParalelo(idparalelo));
			HTML = HTML.replaceAll("#ID_PARALELO#", "" + idparalelo);
			
			HTML = HTML.replaceAll("#NICHO#", model.consultaParaleloNichos(idparalelo,idnicho));
			//HTML = HTML.replaceAll("#ID_PARALELO#", "" + idparalelo);
			
		    url = "SorteosParalelos?idsorteo=" + idsorteo;
			HTML = HTML.replaceAll("#URL_SORTEOS_PARALELOS#", url);

		    url = "SorteosParalelosNichos?idsorteo="+idsorteo+"&idparalelo="+idparalelo;
			HTML = HTML.replaceAll("#URL_SORTEOS_PARALELOS_NICHOS#", url);
			
			 url = "SorteosParalelosColaboradores?idsorteo="+idsorteo+"&idparalelo="+idparalelo+"&idnicho="+idnicho;
			 HTML = HTML.replaceAll("#URL_SORTEOS_PARALELOS_COLABORADORES#", url);
		
			
			

			/*regex = "#URLSECTORES#";
			url = "SectoresAsignados?idsorteo="+sesion.pkSorteo;
			HTML = HTML.replaceAll(regex, url);

			regex = "#URLNICHOS#";
			url = "AsignacionNichos?idsorteo="+sesion.pkSorteo+"&idsector="+sesion.pkSector;
			HTML = HTML.replaceAll(regex, url);


			regex = "#URLCOLABORADORES#";
			url = "AsignacionColaboradores?idsorteo="+sesion.pkSorteo+"&idsector="+sesion.pkSector+"&&idnicho="+sesion.pkNicho;
			HTML = HTML.replaceAll(regex, url);


			contents = HTML.replaceAll("#IDNICHO#", ""+idnicho);*/
			//out.println(contents);

			break;
		}

		response.setContentType("text/html");
		PrintWriter out1 = response.getWriter();
		  out1.println(HTML);
		  model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *		response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Factory vista = new Factory();
		mSorteosParalelosColaboradores model = new mSorteosParalelosColaboradores();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		String cadenacolaboradores = request.getParameter("idcolaboradores");
		String[] colaboradores = cadenacolaboradores.split(",");
		// model.setSectores(sectores);

		model.setIdNicho(Integer.valueOf(request.getParameter("idnicho")));

		model.setIdSorteo(sesion.pkSorteo);
		//model.setIdsector(sesion.pkSector);
		model.guardarAsignacion(colaboradores, sesion);


		model.close();
	}

	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int idnicho,mSorteosParalelosColaboradores model,SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Clave", "Nombre", "Apellido Paterno" };
		String[] campos = { "CLAVE", "NOMBRE", "APATERNO" };

		int numeroregistros = model.contarModal(idnicho,search);

		String HTML = CrearTablaModal(numeroregistros,
				model.paginacionModal(pg, show, search, idnicho,sesion.pkSector,sesion.pkSorteo), columnas,
				campos, pg, show);
		PrintWriter out = response.getWriter();
		out.println(HTML);

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

	protected void Buscar(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search, int idsorteoi,int idparalelo, int idnichoi,int idsector,mSorteosParalelosColaboradores model,SesionDatos sesion)
			throws ServletException, IOException {

		//int numeroregistros = 100;

		int numeroregistros = model.contar(search,idparalelo,idnichoi,idsector);

			 //	  cadena +=	 model.contar(sesion,search);
				//cadena += " | BUSCAR contar SESION SECTOR:"+sesion.pkSector+ ", ";
				//cadena += " | BUSCAR contar SESION NICHO:"+sesion.pkNicho;

		String HTML = CrearTabla(numeroregistros,model.paginacion(pg, show, search, sesion,idsorteoi, idparalelo, idnichoi,idsector), pg, show, idsorteoi, idparalelo, idnichoi,idsector,model,sesion);
		PrintWriter out = response.getWriter();
		HTML = HTML+"#%#"+numeroregistros;
		out.println(HTML);
		out.flush();
		out.close();

	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, int idsorteoi, int idparaleloi, int idnichoi, int idsector,
			mSorteosParalelosColaboradores model, SesionDatos sesion) {

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		String html = "<ul class=\"result-list\">";

		int idsorteo = idsorteoi;
		int idparalelo = idparaleloi;
		int idnicho = idnichoi;
		ResultSet boletos;

		try {

			if (Integer.valueOf(numreg) > 0){
		//	if (rs != null) {
			//{
				while (rs.next())
				{
					int numerotalonarios = 0;
					int numeroboletos = 0;
					double monto = 0;
					Locale english = new Locale("en", "US");
					NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

					model.setIdColaborador(Integer.valueOf(rs.getString("PK1")));// id colab
					model.setIdSorteo(idsorteo);
					model.setIdSorteoParalelo(idparalelo);
					model.setIdNicho(idnicho);
					model.setIdsector(idsector);

					//numerotalonarios = model.numeroTalonarios(model);
					numeroboletos = model.numeroBoletos(model);					
					boletos = model.getBoletos(model);	
					
					

					//monto = (double) model.MontoColaboradores(model);

					html += "<li id=\"talonario" + rs.getString("PK1") + "\" style='"+(sesion.sorteoActivo?"":Global.bkground_cerrado)+"' >";

					html += "<div class=\"result-info\">";
					html += "<h4 class=\"title\"><span class=\"label label-primary\">"
							+ rs.getString("CLAVE") + "</span>  "
							//+ rs.getString("CLAVE") + "</span> <a href=\"BoletosSorteosColaboradores1?idcolaborador="+rs.getString("PK1")+"\"> "
							+ rs.getString("NOMBRE") + " "
							+ rs.getString("APATERNO") + " "
							+ rs.getString("AMATERNO") + "</a> &nbsp; <small>REF. BANCARIA: ";
							if(rs.getString("REFBANCARIA")==null){ html +="S/A"; }
							else {	html += rs.getString("REFBANCARIA"); }
					html +="</small>"
							+ "</h4>";


					html += "<p class=\"location\"><span class=\"badge badge-danger pull\">"
							+ "</span> BOLETOS: <span class=\"badge badge-danger pull\">"
							+ numeroboletos + "</span> </p>";

					html += "<p class=\"desc\">";
					html += rs.getString("DESCRIPCION");					
					html += "</p>";					
					
					
					html += "<p class=\"desc\">";
					
					while (boletos.next()) {						
								
								
					//html += "<a href=\"#\">";	
					html += "<span class=\"label etiqueta-retornados\">";
						//html += "<span class=\"label label-success\" >";
						html += boletos.getString("FOLIO"); 								
						html +="</span> ";							
					 
				}
					
					html += "</p>";
					
					html += "<div class=\"btn-row\">";
					
					html += "<a data-title=\"\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:borrarParalelosColaboradores('"
							+ rs.getString("PK1") + "','"
							+ rs.getString("PK_SORTEO_PARALELO") + "','"
							+ rs.getString("PK_SORTEO_PARALELO_NICHO")+ "','"
							+ rs.getString("PK_SECTOR")+ "','"
							+ rs.getString("PK_NICHO")				
							+ "');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>Eliminar</b></span></a>";
					

					html += "</div>";				
					

					html += "</div>";

					/*html += "<div class=\"result-price\">";
					html += englishFormat.format(monto)+" <small>MONTO</small>";
					html += "<a class=\"btn btn-inverse btn-block\" href=\"ColaboradorDetalle?sorteo="+sesion.pkSorteo+"&sector="+sesion.pkSector+"&nicho="+sesion.pkNicho+"&colaborador="+rs.getString("PK1")+"\">Ver Detalles</a>";
					html += "</div>";*/

					html += "</li>";

				}

				html += "</ul>";

			} else {

				html += "<li align= \"center\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

				html += "<h1>No existen Colaboradores Asignados</h1>";
				html += "<p>Asigne Colaboradores al Nicho.</p>";

				html += "</div>";

				html += "</li>";
				html += "</ul>";

			}

		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

		String paginado = Factory.paginado_2(numreg, show, pg);

		html = paginado + html + paginado;

		return html;

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
	private boolean ExisteCarga(mSorteosParalelosColaboradores model, SesionDatos sesion){

		model.setIdSorteo(sesion.pkSorteo);
		model.setIdsector(sesion.pkSector);
		model.setIdNicho(sesion.pkNicho);
		model.setIdColaborador(sesion.pkColaborador);
		return model.ExisteCarga();
	}

}



