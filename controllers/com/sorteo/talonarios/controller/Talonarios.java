package com.sorteo.talonarios.controller;

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
import com.core.SuperModel.RESULT;
import com.sorteo.talonarios.model.mTalonarios;

/**
 * Servlet implementation class Talonarios
 */
@WebServlet("/Talonarios")
public class Talonarios extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Talonarios() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mTalonarios model = new mTalonarios();
		SesionDatos sesion;

		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";

		String view = request.getParameter("view");

		if (view == null) {
			view = "";
		}

		switch (view) {

		case "Agregar":
			fullPath = "/WEB-INF/views/talonarios/agregar.html";
			menu = vista.initMenu(0, false, 2, 34, sesion);
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
			
		case "BuscarVistaColumna":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			
			BuscarVistaColumna(request, response, pg, show, search, model, sesion);
			break;	

		case "delete":
			// eliminarUsuario(request, response);
			break;

		default:
			String str_sorteo = request.getParameter("sorteo");
			if (str_sorteo != null)
			{
				int pkSorteo = Integer.valueOf(str_sorteo);
				boolean activo = model.Sorteo(pkSorteo);
				
				if (sesion.pkSorteo != pkSorteo || sesion.sorteoActivo != activo) {
					sesion.pkSorteo = pkSorteo;
					sesion.sorteoActivo = activo;
					sesion.guardaSesion();
				}
			}
			
			fullPath = "/WEB-INF/views/boveda/talonarios.html";
			menu = vista.initMenu(0, false, 7, 8, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);

			HTML = HTML.replaceAll("#SORTEOS#", listaSorteos(model));

			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);

		model.close();
	}

	
	
	protected void BuscarVistaColumna(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mTalonarios model, SesionDatos sesion)
			throws ServletException, IOException {

		/*String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Sector","Nicho","Colaborador"};
		String[] campos = { "FOLIO","VENDIDO","COSTO","ABONO","TALONARIO","SECTOR","NICHO","COLABORADOR" };*/
		
		String[] columnas = { "Folio","Boletos","Monto","Formato","Vendidos",};
		String[] campos = { "FOLIO","NUMBOLETOS","COSTO","FORMATO","VENDIDOS", };

		int numeroregistros = model.contarVistaColumna(sesion.pkSorteo,search);

		
		String HTML = CrearTablaVistaColumna(numeroregistros, model.paginacionVistaColumna(pg, show, search,sesion.pkSorteo), columnas, campos, pg, show);
		PrintWriter out = response.getWriter();
		
		System.out.println(HTML);
		
		out.println(HTML);
	}

	protected String CrearTablaVistaColumna(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show) {
		
		System.out.println(">>>ENTRO: ");
		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer invoice\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 12px;\" ></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}
		
	/*	html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 115px;\" >Sector</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 115px;\" >Nicho</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 115px;\" >Colabolador</th>";*/

		html += " </thead>";
		html += " <tbody>";

		try {
			while (rs.next()) {

				i++;
				
				
				/*model.setIdtalonario(rs.getInt("PK1"));
				model.setFolioTalonario(rs.getString("FOLIO"));
				model.setSorteo(rs.getString("SORTEO"));
				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				model.setIdnicho(sesion.pkNicho);
				System.out.println(">>>nicho: "+sesion.pkNicho);
				ResultSet talonarioasignado = model.obtenerTalonario(model);
					*/
				

				html += "<tr class=\"gradeA odd\" role=\"row\">";
				
			//	html += "<td class=\"sorting_1\"><input  id=" + rs.getInt("PK1") + "  type=\"checkbox\" /></td>";
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
					
					if (rs.getString(campo) == null) {
						html += "<td class=\"sorting_1\">NA</td>";
						
					} else {
						html += "<td class=\"sorting_1\">" + rs.getString(campo)
								+ "</td>";
					}
				}

			//	html += "<td class=\"sorting_1\"></td>";
			//	html += "<td class=\"sorting_1\"></td>";
			//	html += "<td class=\"sorting_1\"></td>";
				//System.out.println(rs.getString("SECTOR"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		html += "</tbody>";
		html += "</table>";

		/*
		int numpag = Math.round(numreg / show);
		//int denumpag = numpag + 1;

		System.out.println(">>> numreg=" + numreg + ",  show=" + show
				+ ",  numpag=" + numpag);

		String paginado = "<ul class=\"pagination pagination-without-border pull-right m-t-0\">";

		if (pg > 1) {

			int pagante = pg - 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
					+ pagante + ");\">Anterior</a></li>";
		} else {
			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Anterior</a></li>";

		}

		// Calcular el inicio del arreglo
		int inipg = 0;
		int r = (pg - 1) % 5;
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
					paginado += "<li class=\"active\"><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
							+ sumpag + ");\">" + sumpag + "</a>";
				} else {
					paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
							+ sumpag + ");\">" + sumpag + "</a>";
				}

			}
		}

		if (pg <= numpag) {
			int numeropag = pg + 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
					+ numeropag + ");\">Siguiente</a></li>";

		} else {

			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Siguiente</a></li>";
		}

		paginado += "</ul>";
		*/
		String paginado = Factory.paginado_2(numreg, show, pg);

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		html = paginado + html + paginadoright;

		return html;
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		mTalonarios model = new mTalonarios();
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		if (sesion == null)
			return;
		
		int pktalonario = 0;
		
		String operacion = "";
		String JSON = "";
		
		if (request.getParameter("operacion") != null) {
			operacion = request.getParameter("operacion");
		}
		
		if (operacion.equals("eliminar")) {
			
			model.setId(Integer.valueOf(request.getParameter("id")));
			model.setFolio(request.getParameter("folio"));
			model.setIdsorteo(request.getParameter("numsorteo"));
			model.setFormato(request.getParameter("formato"));
			RESULT result = model.Borrar(model);
			
			JSON = "{\"result\":\"" + result + "\",\"msg\":\"" + model._mensaje + "\"}";
			
		} else {
			
			model.setFolio(request.getParameter("folio"));
			model.setNumeroboletos(Integer.valueOf(request.getParameter("numboletos")));
			model.setIdsorteo(request.getParameter("numsorteo"));
			model.setFormato(request.getParameter("formato"));
			model.setMonto(Double.parseDouble(request.getParameter("monto")));

			pktalonario = model.registrar(model);

			JSON = "" + pktalonario;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(JSON);
		
		model.close();
	}

	protected String listaSorteos(mTalonarios model) {

		ResultSet rs = model.getSorteos();

		String panelcontent = "";
		//String contents = "";

		try {
			while (rs.next()) {
				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";
				panelcontent += ">" + rs.getString("SORTEO") + "</option>";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return panelcontent;
	}

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mTalonarios model, SesionDatos sesion) throws ServletException, IOException {

		model.setIdsorteo(""+sesion.pkSorteo);

		int numeroregistros = model.contar(search);
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search), pg, show, model, sesion);
		PrintWriter out = response.getWriter();
		//HTML = HTML+"#%#"+model.getTotalregistros();
		HTML = HTML+"#%#"+numeroregistros;
		
		out.println(HTML);

	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, mTalonarios model, SesionDatos sesion) {

		//int i = (pg - 1) * show;

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		ResultSet boletos;
		double monto = 0;
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

		String html = "<ul class=\"result-list\">";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {

					//model.setFolio(rs.getString("FOLIO"));
					model.setId(rs.getInt("PK1"));
					model.setIdsorteo(rs.getString("PK_SORTEO"));
					model.setFormato(rs.getString("FORMATO"));
					boletos = model.getBoletos(model);

					//i++;

					html += "<li id=\"talonario" + rs.getString("PK1") + "\">";

					html += "<div class=\"result-info\">";
					html += "<h4 class=\"title\">TALONARIO FOLIO: <span class=\"badge badge-warning badge-square\">"
							+ rs.getString("FOLIO") + "</span></h4>";
					html += "<p class=\"location\"> BOLETOS: <span class=\"badge badge-danger pull\">"
							+ rs.getString("NUMBOLETOS") + "</span> </p>";
					html += "<p class=\"desc\">";

					while (boletos.next()) {

						if(boletos.getString("PK_ESTADO").trim().equals("N")){  html += "<span class=\"label label-inverse\" >"+boletos.getString("FOLIO")+"</span> ";}
						if(boletos.getString("PK_ESTADO").trim().equals("V")){  html += "<span class=\"label label-success\" >"+boletos.getString("FOLIO")+"</span> ";}
					//	if(rs.getString("PK_ESTADO").trim().equals("P")){  html += "<span class=\"label label-default\" >"+boletos.getString("FOLIO")+"</span> ";}

					}
					boletos.close();

					html += "</p>";

					if (sesion.sorteoActivo)
					{
						html += "<div class=\"btn-row\">";
						html += "<a data-title=\"Eliminar Talonario y Boletos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:eliminarTalonario('"
								+ rs.getString("PK1")
								+ "','"
								+ rs.getString("FOLIO")
								+ "','"
								+ rs.getString("PK_SORTEO")
								+ "','"
								+ rs.getString("FORMATO")
								+ "');\"  data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">Eliminar</span></a>";
					
						/*html += "<a data-title=\"Configuraci&oacute;n\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-cog\"></i><span style=\"font-size:11px;\">Configuraci&oacute;n</span></a>";
						html += "<a data-title=\"Muestra las estadisticas y datos de avance del sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tachometer\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";
						html += "<a data-title=\"Revise que sectores estan asignados en este sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SectoresAsignados?clave="
							+ rs.getString("PK1")
							+ "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-user\"></i><span style=\"font-size:11px;\">Sectores</span></a>";*/
						html += "</div>";
					}

					html += "</div>";

					monto = Double.parseDouble(rs.getString("COSTO"));

					//System.out.println("US: " + englishFormat.format(monto));

					html += "<div class=\"result-price\">";
					html += englishFormat.format(monto)
							+ " <small>MONTO</small>";
					html += "<a class=\"btn btn-inverse btn-block\" href=\"SeguimientoTalonarios?idsorteo="+rs.getString("PK_SORTEO")+"&idtalonario="+rs.getString("PK1")+"\">Ver Detalles</a>";
					html += "</div>";

					html += "</li>";

					// System.out.println(rs.getString("NOMBRE"));

				}

			} else {

				html += "<li align= \"center\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";

				html += "<h1>No existen talonarios</h1>";
				html += "<p>Empieze por agregar su carga inicial de Talonarios y Boletos.</p>";

				html += "</div>";

				html += "</li>";

			}

			html += "</ul>";

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		int numpag = (int) Math.ceil(numreg / (double)show);
		//int denumpag = numpag + 1;
		//System.out.println("numreg="+numreg+", show="+show+", numpag="+numpag);

		String paginado = "<ul class=\"pagination pagination-without-border pull-right m-t-0\">";

		if (pg > 1) {

			int pagante = pg - 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag(" + pagante + ");\">Anterior</a></li>";
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
			if (j < numpag) {

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
		*/
		String paginado = Factory.paginado_2(numreg, show, pg);

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		html = paginado + html + paginadoright;

		return html;

	}

}
