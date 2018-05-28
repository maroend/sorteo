package com.sorteo.reportes.controller;

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

import org.apache.commons.lang3.StringEscapeUtils;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.dashboard.model.mDashboard;
import com.sorteo.reportes.model.mBoletosTransito;

/**
 * Servlet implementation class RetornoBoletos
 */
@WebServlet("/BoletosTransito")
public class BoletosTransito extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoletosTransito() {
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
		SesionDatos sesion;
		mBoletosTransito model = new mBoletosTransito();

		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		String view = request.getParameter("view");

		if (!sesion.permisos.havePermiso(20098)) {
			view = "error";
		}

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int pg = 0;
		int show = 0;
		String search = "";

		fullPath = "/WEB-INF/views/listsorteosuser.html";
		if (view == null) {
			view = "";

			if (request.getParameter("i") != null) {

				System.out.println("entra default");

			} else {
				view = "ObtenerUsuario";
			}
		}

		switch (view) {

		case "listsorteos":

			menu = vista.initMenu(0, false, 19, 23, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,
					sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);

			HTML = getSorteosUsuarios(request, response, HTML, sesion);
			break;

		case "ObtenerUsuario":

			ObtenerUsuario(request, response, model, sesion);

			break;

		case "getBoletosTalonarios":

			System.out.println("getBoletosTalonarios: ");
			System.out.println("idsorteo: " + sesion.pkSorteo);
			System.out.println("idsector: " + sesion.pkSector);
			System.out.println("idnicho: " + sesion.pkNicho);

			// SORTEO
			if (sesion.pkSorteo != -1 && sesion.pkSector == -1
					&& sesion.pkNicho == -1) {

				model.setIdSorteo( sesion.pkSorteo);
				HTML = model.getBoletosTalonarios();
			}

			// GET SECTOR
			else if (sesion.pkSorteo != -1 && sesion.pkSector != -1
					&& sesion.pkNicho == -1) {

				model.setIdSorteo( sesion.pkSorteo);
				model.setIdsector( sesion.pkSector);
				HTML = model.getBoletosTalonariosSector();

			}

			// GET NICHO
			else if (sesion.pkSorteo != -1 && sesion.pkSector != -1
					&& sesion.pkNicho != -1) {

				model.setIdSorteo( sesion.pkSorteo);
				model.setIdsector( sesion.pkSector);
				model.setIdnicho( sesion.pkNicho);
				HTML = model.getBoletosTalonariosNicho();
			}

			break;

		case "getBoletosTalonariosSector":
			int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			int idsector = Integer.valueOf(request.getParameter("sector"));
			// HTML = getBoletosTalonariosSector(idsorteo,idsector);
			break;

		case "getBoletosTalonariosNicho":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			idsector = Integer.valueOf(request.getParameter("sector"));
			int idnicho = Integer.valueOf(request.getParameter("nicho"));
			// HTML = getBoletosTalonariosNicho(idsorteo,idsector,idnicho);
			break;

		case "getBoletosTalonariosColaborador":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			idsector = Integer.valueOf(request.getParameter("sector"));
			idnicho = Integer.valueOf(request.getParameter("nicho"));
			// int colaborador =
			// Integer.valueOf(request.getParameter("colaborador"));
			// HTML = getBoletosTalonariosNicho(idsorteo,idsector,idnicho);
			break;

		case "GetComprador":
			System.out.println("GetComprador");
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			String folio = request.getParameter("boleto");
			String talonario = request.getParameter("talonario");
			HTML = GetComprador(idsorteo, folio, talonario, model);

			break;

		case "GetIncidenciaBoleto":
			System.out.println("GetIncidenciaBoleto");
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			folio = request.getParameter("boleto");
			talonario = request.getParameter("talonario");
			HTML = GetIncidenciaBoleto(idsorteo, folio, talonario, model);

			break;

		case "BuscarMontoAbonoTalonario":
			System.out.println("BuscarMontoAbonoTalonario");
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			talonario = request.getParameter("talonario");
			// HTML = BuscarMontoAbonoTalonario(idsorteo,talonario);
			break;

		case "BuscarBoletosTalonarios":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			talonario = request.getParameter("talonario");
			// HTML = BuscarBoletosTalonarios(idsorteo,talonario);
			break;

		case "Buscar":
			System.out.println("Buscar");

			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");

			// SORTEO
			if (sesion.pkSorteo != -1 && sesion.pkSector == -1
					&& sesion.pkNicho == -1/* &&sesion.pkColaborador==-1 */) {
				System.out.println("buscar x sorteo");
				Buscar(request, response, pg, show, search, model, sesion);
			}

			// GET SECTOR
			else if (sesion.pkSorteo != -1 && sesion.pkSector != -1
					&& sesion.pkNicho == -1/* &&sesion.pkColaborador==-1 */) {
				System.out.println("buscar x sector");
				BuscarXSector(request, response, pg, show, search, model, sesion);

			}
			// solo por evento y no al inicio(excepto cuando se inicia con
			// usauario pk_ nicho = 1 )
			// GET NICHO
			else if (sesion.pkSorteo != -1 && sesion.pkSector != -1
					&& sesion.pkNicho != -1 && sesion.pkColaborador == -1) {
				System.out.println("buscar x nicho");

				BuscarXNichos(request, response, pg, show, search, model, sesion);
			}

			else if (sesion.pkSorteo != -1 && sesion.pkSector != -1
					&& sesion.pkNicho != -1 && sesion.pkColaborador != -1) {
				System.out.println("buscar x colaborador");

				BuscarXColaborador(request, response, pg, show, search, model, sesion);
			}

			break;

		case "BuscarSector":
			System.out.println("BuscarSector");
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");

			// idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			idsector = Integer.valueOf(request.getParameter("idsector"));

			if (sesion.pkSector != idsector) {
				sesion.pkSector = idsector;
				sesion.guardaSesion();
			}

			BuscarXSector(request, response, pg, show, search, model, sesion);

			break;

		case "BuscarNichos":
			System.out.println("BuscarNichos");
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");

			// idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			idsector = Integer.valueOf(request.getParameter("idsector"));
			idnicho = Integer.valueOf(request.getParameter("idnicho"));

			if (sesion.pkSector != idsector) {
				sesion.pkSector = idsector;
				sesion.guardaSesion();
			}

			if (sesion.pkNicho != idnicho) {
				sesion.pkNicho = idnicho;
				sesion.guardaSesion();
			}

			BuscarXNichos(request, response, pg, show, search, model, sesion);

			break;

		case "BuscarColaborador":
			System.out.println("BuscarNichos");
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");

			// idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			idsector = Integer.valueOf(request.getParameter("idsector"));
			idnicho = Integer.valueOf(request.getParameter("idnicho"));
			int idcolaborador = Integer.valueOf(request
					.getParameter("idcolaborador"));

			if (sesion.pkSector != idsector) {
				sesion.pkSector = idsector;
				sesion.guardaSesion();
			}

			if (sesion.pkNicho != idnicho) {
				sesion.pkNicho = idnicho;
				sesion.guardaSesion();
			}

			if (sesion.pkColaborador != idcolaborador) {
				sesion.pkColaborador = idcolaborador;
				sesion.guardaSesion();
			}

			BuscarXColaborador(request, response, pg, show, search, model, sesion);

			break;

		case "delete":
			// eliminarUsuario(request, response);
			break;

		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 19, sesion.misSorteos == 0 ? 23
					: 19, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,
					sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			break;

		default:

			System.out.println("default");
			idsorteo = Integer.valueOf(request.getParameter("idsorteo"));

			fullPath = "/WEB-INF/views/reportes/transitoboletos.html";
			menu = vista.initMenu(0, false, 19, 23, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,
					sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);

			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
			}

			String sector = "";
			String nicho = "";
			String colaborador = "";

			// GET sorteo
			model.setIdSorteo( sesion.pkSorteo);
			model.consultaSorteo();
			String dato = model.getSorteo();
			HTML = HTML.replaceAll("#SORTEO#", dato);

			// GET SECTOR
			if (sesion.pkSorteo != -1 && sesion.pkSector != -1 && sesion.pkNicho == -1) {
				sector = model.Sector(sesion.pkSector);
			}

			// GET NICHO
			if (sesion.pkSorteo != -1 && sesion.pkSector != -1 && sesion.pkNicho != -1) {
				sector = model.Sector(sesion.pkSector);
				nicho = model.Nicho(sesion.pkNicho);
			}

			// String colaborador = model.getColaborador();
			HTML = HTML.replaceAll("#COLABORADOR#", colaborador);

			HTML = HTML.replaceAll("#SECTOR#", sector);
			HTML = HTML.replaceAll("#NICHO#", nicho);

			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

	protected String getSorteosUsuarios(HttpServletRequest request,
			HttpServletResponse response, String HTML, SesionDatos sesion)
			throws ServletException, IOException {

		String contenido = "<div  class=\"jumbotron m-b-0 text-center\"><h1>No existen Sorteos</h1><p>Empieze por agregar un nuevo sorteo.</p></div>";

		String regex = "#SORTEOSUSUARIOS#";
		ResultSet rs = null;

		mDashboard modelDashboard = new mDashboard();

		modelDashboard.setIdUsuario(sesion.pkUsuario);
		modelDashboard.cosultaPredeterminados();

		int sorteo = modelDashboard.getIdSorteo();
		int sector = modelDashboard.getIdSector();
		int nicho = modelDashboard.getIdNicho();

		// EXISTE USUARIO NIVEL SORTEO
		if (modelDashboard.ContarSorteosUsuarios() != 0) {

			rs = modelDashboard.getSorteosUsuario();
			try {
				while (rs.next()) {

					contenido += "<p>";
					contenido += "<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSorteo('"
							+ rs.getString("PK1")
							+ "')\"  role=\"button\" class=\"btn btn-success btn-lg\">";

					if (sorteo == rs.getInt("PK1")) {
						contenido += "<i class=\"fa fa-2x fa-check-circle\"></i> ";
					}

					contenido += rs.getString("SORTEO") + "</a> ";
					contenido += "</<p>";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// NIVEL SECTORES USUARIOS
		} else if (modelDashboard.ContarSectoresUsuarios() != 0) {

			System.out.println("entra aqui prueba");

			rs = modelDashboard.getSectoresUsuario();

			try {
				while (rs.next()) {

					contenido += "<p>";
					contenido += "<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSector('"
							+ rs.getString("PK_SORTEO")
							+ "','"
							+ rs.getString("PK_SECTOR")
							+ "')\"  role=\"button\" class=\"btn btn-success btn-lg\">";

					if (sector == rs.getInt("PK_SECTOR")) {
						contenido += "<i class=\"fa fa-2x fa-check-circle\"></i> ";
					}

					contenido += rs.getString("SORTEO") + " <br> <small>"
							+ rs.getString("SECTOR") + "</small></a> ";
					contenido += "</<p>";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// NIVEL NICHOS USUARIOS
		} else if (modelDashboard.ContarNichosUsuarios() != 0) {

			System.out.println("entra aqui prueba");

			rs = modelDashboard.getNichosUsuario();

			try {
				while (rs.next()) {

					contenido += "<p>";
					contenido += "<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardNicho('"
							+ rs.getString("PK_SORTEO")
							+ "','"
							+ rs.getString("PK_SECTOR")
							+ "','"
							+ rs.getString("PK_NICHO")
							+ "')\"  role=\"button\" class=\"btn btn-success btn-lg\">";
					if (nicho == rs.getInt("PK_NICHO")) {
						contenido += "<i class=\"fa fa-2x fa-check-circle\"></i> ";
					}
					contenido += rs.getString("SORTEO") + "<br> <small>"
							+ rs.getString("NICHO") + "</small></a> ";
					contenido += "</<p>";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		HTML = HTML.replaceAll(regex, contenido);

		return HTML;
	}

	protected void ObtenerUsuario(HttpServletRequest request,
			HttpServletResponse response, mBoletosTransito model,
			SesionDatos sesion) throws ServletException, IOException {

		model.setIdUsuario(sesion.pkUsuario);
		model.getUsuarioSorteo();

		int idsorteo = model.getIdSorteo();
		int idsector = model.getIdsector();
		int idnicho = model.getIdnicho();

		if (sesion.pkSorteo != idsorteo || sesion.pkSector != idsector
				|| sesion.pkNicho != idnicho) {

			sesion.pkSorteo = idsorteo;
			sesion.pkSector = idsector;
			if (idsector == 0) {
				sesion.pkSector = -1;
			} else {
				sesion.pkSector = idsector;
			}
			if (idnicho == 0) {
				sesion.pkNicho = -1;
			} else {
				sesion.pkNicho = idnicho;
			}

			sesion.guardaSesion();
		}

		sesion.pkColaborador = -1;
		sesion.guardaSesion();

		if (idsorteo != 0 && idsector == 0 && idnicho == 0) {

			response.sendRedirect("BoletosTransito?i=1&idsorteo=" + idsorteo);

		} else if (idsorteo != 0 && idsector != 0 && idnicho == 0) {

			response.sendRedirect("BoletosTransito?i=1&idsorteo=" + idsorteo
					+ "&sector=" + idsector);

		} else if (idsorteo != 0 && idsector != 0 && idnicho != 0) {

			// el mismo no preciso entrar nichos desde el comienzo
			response.sendRedirect("BoletosTransito?i=1&idsorteo=" + idsorteo
					+ "&sector=" + idsector);
		} else {

			response.sendRedirect("BoletosTransito?view=listsorteos");
		}

		model.init();
	}

	protected String GetComprador(int idsorteo, String boleto, String talonario, mBoletosTransito model) {

		String contenido = "";

		ResultSet rs = null;

		model.setBoleto(boleto);
		model.setIdSorteo( idsorteo);
		rs = model.getComprador();

		try {
			if (rs.next()) {
				// ABONO
				contenido = "|" + rs.getString("ABONO") + "|"
						+ rs.getString("NOMBRE") + "|"
						+ rs.getString("APELLIDOS") + "|"
						+ rs.getString("TELEFONOF") + "|"
						+ rs.getString("TELEFONOM") + "|"
						+ rs.getString("CORREO") + "|"
						+ rs.getString("CALLE") + "|"
						+ rs.getString("NUMERO") + "|"
						+ rs.getString("COLONIA") + "|"
						+ rs.getString("ESTADO") + "|" + rs.getString("MUNDEL");

			} else {

				contenido = "NULL";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contenido;
	}

	protected String GetIncidenciaBoleto(int idsorteo, String boleto,
			String talonario, mBoletosTransito model) {

		String contenido = "";

		ResultSet rs = null;

		model.setBoleto(boleto);
		model.setIdSorteo( idsorteo);
		model.setIdtalonario(Integer.parseInt(talonario));

		rs = model.GetIncidenciaBoleto();

		try {
			if (rs.next()) {
				// ABONO
				contenido = "|" + rs.getString("FORMATO") + "|"
						+ rs.getString("INCIDENCIA") + "|"
						+ rs.getString("FOLIOMP") + "|"
						+ rs.getString("DETALLES");

			} else {

				contenido = "NULL";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contenido;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void BuscarXNichos(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mBoletosTransito model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto", "Estatus", "Costo", "Abono", "Talonario", "Colaborador" };
		String[] campos = { "FOLIO", "VENDIDO", "COSTO", "ABONO", "TALONARIO", "COLABORADOR" };

		int numeroregistros = model.contarXNicho(search,  sesion.pkSorteo,
				 sesion.pkSector,  sesion.pkNicho);

		String HTML = CrearTablaXNichos(numeroregistros, model.paginacionXNichos(
				pg, show, search,  sesion.pkSorteo,  sesion.pkSector,
				 sesion.pkNicho), columnas, campos, pg, show, sesion);
		PrintWriter out = response.getWriter();
		String sector = model.Sector(sesion.pkSector);
		String nicho = model.Nicho(sesion.pkNicho);
		HTML = HTML + "#%#" + sector + "#%#" + nicho;
		out.println(HTML);

	}

	protected String CrearTablaXNichos(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show, SesionDatos sesion) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('"
				+ "1b"
				+ "')\" id=\"checkboxall"
				+ "1b"
				+ "\" name=\"checkboxall\" /></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}

		html += " </thead>";
		html += " <tbody>";

		String estado = "";
		String estadotalonario = "";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {

					i++;

					html += "<tr class=\"gradeA odd\" role=\"row\">";

					html += "<td class=\"sorting_1\">";

					/*
					 * if(rs.getInt("RETORNADO")==1){ html +=
					 * "<i class=\"fa fa-2x fa-check-circle\"></i>"; }else{
					 */
					html += "<input  id="
							+ rs.getInt("PK1")
							+ "  class=\"boletoschecked\"   type=\"checkbox\" />";
					// }
					html += "</td>";
					html += "<td class=\"sorting_1\">" + i + "</td>";

					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('"
							+ sesion.pkSorteo
							+ "','"
							+ rs.getString("PK_BOLETO")
							+ "','"
							+ rs.getString("FOLIO")
							+ "','"
							+ rs.getString("PK_TALONARIO")
							+ "','"
							+ rs.getString("TALONARIO")
							+ "','"
							+ rs.getString("ABONO")
							+ "','"
							+ rs.getString("COSTO")
							+ "','"
							+ rs.getString("PK_SECTOR")
							+ "','"
							+ rs.getString("PK_NICHO")
							+ "','"
							+ rs.getString("PK_COLABORADOR") + "')\">";

					if (rs.getString("INCIDENCIA").equals("N")) {
						html += "<span class=\"label label-primary\" >"
								+ rs.getString("FOLIO") + "</span>";
					}
					if (rs.getString("INCIDENCIA").equals("E")) {
						html += "<span class=\"label label-warning\" >"
								+ rs.getString("FOLIO") + "</span>";
					}
					if (rs.getString("INCIDENCIA").equals("R")) {
						html += "<span class=\"label label-danger\" >"
								+ rs.getString("FOLIO") + "</span>";
					}

					html += "</a></td>";

					if (rs.getString("VENDIDO").equals("N")) {
						estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("V")) {
						estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("P")) {
						estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";
					}

					html += "<td class=\"sorting_1\">" + estado + "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("COSTO")
							+ "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("ABONO")
							+ "</td>";

					if (rs.getString("VENDIDOTALONARIO").equals("N")) {
						estadotalonario = "<span class=\"label label-inverse\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("V")) {
						estadotalonario = "<span class=\"label label-success\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("P")) {
						estadotalonario = "<span class=\"label label-default\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}

					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('"
							+ sesion.pkSorteo
							+ "','"
							+ rs.getString("PK_TALONARIO")
							+ "','"
							+ rs.getString("TALONARIO")
							+ "')\">"
							+ estadotalonario + "</a></td>";

					/*
					 * html += "<td class=\"sorting_1\">" +
					 * ((rs.getString("SECTOR") == null) ? "N/A" :
					 * "<a href=\"javascript:ShowDetalleSector('"
					 * +sesion.pkSorteo
					 * +"','"+rs.getString("PK_SECTOR")+"','"+StringEscapeUtils
					 * .escapeHtml4
					 * (rs.getString("SECTOR"))+"')\">"+StringEscapeUtils
					 * .escapeHtml4(rs.getString("SECTOR"))+"</a>") + "</td>";
					 */

					/*
					 * html += "<td class=\"sorting_1\">" +
					 * ((rs.getString("NICHO") == null) ? "N/A" :
					 * "<a href=\"javascript:ShowDetalleNicho('"
					 * +sesion.pkSorteo+
					 * "','"+rs.getString("PK_SECTOR")+"','"+rs.
					 * getString("PK_NICHO"
					 * )+"','"+rs.getString("NICHO")+"')\">"+
					 * StringEscapeUtils.escapeHtml4
					 * (rs.getString("NICHO"))+"</a>") + "</td>";
					 */

					html += "<td class=\"sorting_1\">"
							+ ((rs.getString("COLABORADOR") == null) ? "N/A"
									: "<a href=\"javascript:ShowDetalleColaborador('"
											+ sesion.pkSorteo
											+ "','"
											+ rs.getString("PK_SECTOR")
											+ "','"
											+ rs.getString("PK_NICHO")
											+ "','"
											+ rs.getString("PK_COLABORADOR")
											+ "','"
											+ StringEscapeUtils.escapeHtml4(rs
													.getString("COLABORADOR"))
											+ "')\">"
											+ StringEscapeUtils.escapeHtml4(rs
													.getString("COLABORADOR"))
											+ "</a>") + "</td>";

				}

			} else {

				html += "<tr> <td colspan=\"11\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Boletos en Transito en el Nicho </h1>";
				html += "</div>";
				html += "</td></tr>";
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

	protected void BuscarXColaborador(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mBoletosTransito model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto", "Estatus", "Costo", "Abono", "Talonario" };
		String[] campos = { "FOLIO", "VENDIDO", "COSTO", "ABONO", "TALONARIO" };

		int numeroregistros = model.contarXColaborador(search,
				 sesion.pkSorteo,  sesion.pkSector,
				 sesion.pkNicho,  sesion.pkColaborador);

		String HTML = CrearTablaColaborador(numeroregistros,
				model.paginacionXColaborador(pg, show, search,
						 sesion.pkSorteo,  sesion.pkSector,
						 sesion.pkNicho,  sesion.pkColaborador),
				columnas, campos, pg, show, sesion);
		PrintWriter out = response.getWriter();
		String sector = model.Sector(sesion.pkSector);
		String nicho = model.Nicho(sesion.pkNicho);
		String colaborador = model.Colaborador(sesion.pkColaborador);
		HTML = HTML + "#%#" + sector + "#%#" + nicho + "#%#" + colaborador;

		out.println(HTML);

	}

	protected String CrearTablaColaborador(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show, SesionDatos sesion) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('"
				+ "1b"
				+ "')\" id=\"checkboxall"
				+ "1b"
				+ "\" name=\"checkboxall\" /></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}

		html += " </thead>";
		html += " <tbody>";

		String estado = "";
		String estadotalonario = "";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {

					i++;

					html += "<tr class=\"gradeA odd\" role=\"row\">";

					html += "<td class=\"sorting_1\">";

					/*
					 * if(rs.getInt("RETORNADO")==1){ html +=
					 * "<i class=\"fa fa-2x fa-check-circle\"></i>"; }else{
					 */
					html += "<input  id="
							+ rs.getInt("PK1")
							+ "  class=\"boletoschecked\"   type=\"checkbox\" />";
					// }
					html += "</td>";

					html += "<td class=\"sorting_1\">" + i + "</td>";

					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('"
							+ sesion.pkSorteo
							+ "','"
							+ rs.getString("PK_BOLETO")
							+ "','"
							+ rs.getString("FOLIO")
							+ "','"
							+ rs.getString("PK_TALONARIO")
							+ "','"
							+ rs.getString("TALONARIO")
							+ "','"
							+ rs.getString("ABONO")
							+ "','"
							+ rs.getString("COSTO")
							+ "','"
							+ rs.getString("PK_SECTOR")
							+ "','"
							+ rs.getString("PK_NICHO")
							+ "','"
							+ rs.getString("PK_COLABORADOR") + "')\">";

					if (rs.getString("INCIDENCIA").equals("N")) {
						html += "<span class=\"label label-primary\" >"
								+ rs.getString("FOLIO") + "</span>";
					}
					if (rs.getString("INCIDENCIA").equals("E")) {
						html += "<span class=\"label label-warning\" >"
								+ rs.getString("FOLIO") + "</span>";
					}
					if (rs.getString("INCIDENCIA").equals("R")) {
						html += "<span class=\"label label-danger\" >"
								+ rs.getString("FOLIO") + "</span>";
					}

					html += "</a></td>";

					if (rs.getString("VENDIDO").equals("N")) {
						estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("V")) {
						estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("P")) {
						estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";
					}

					html += "<td class=\"sorting_1\">" + estado + "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("COSTO")
							+ "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("ABONO")
							+ "</td>";

					if (rs.getString("VENDIDOTALONARIO").equals("N")) {
						estadotalonario = "<span class=\"label label-inverse\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("V")) {
						estadotalonario = "<span class=\"label label-success\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("P")) {
						estadotalonario = "<span class=\"label label-default\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}

					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('"
							+ sesion.pkSorteo
							+ "','"
							+ rs.getString("PK_TALONARIO")
							+ "','"
							+ rs.getString("TALONARIO")
							+ "')\">"
							+ estadotalonario + "</a></td>";

				}

			} else {

				html += "<tr> <td colspan=\"11\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Boletos en Transito en el Colaborador </h1>";
				html += "</div>";
				html += "</td></tr>";
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

	protected void BuscarXSector(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mBoletosTransito model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto", "Estatus", "Costo", "Abono", "Talonario", "Nicho", "Colaborador" };
		String[] campos = { "FOLIO", "VENDIDO", "COSTO", "ABONO", "TALONARIO", "NICHO", "COLABORADOR" };

		int numeroregistros = model.contarXSector(search,  sesion.pkSorteo,
				 sesion.pkSector);

		String HTML = CrearTablaXSector(numeroregistros,
				model.paginacionXSector(pg, show, search,  sesion.pkSorteo,
						 sesion.pkSector), columnas, campos, pg, show, sesion
						 );

		String sector = model.Sector(sesion.pkSector);
		HTML = HTML + "#%#" + sector;

		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTablaXSector(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show, SesionDatos sesion) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 12px;\" ></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}

		html += " </thead>";
		html += " <tbody>";

		String estado = "";
		String estadotalonario = "";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {

					i++;

					html += "<tr class=\"gradeA odd\" role=\"row\">";

					html += "<td class=\"sorting_1\"><input  id="
							+ rs.getInt("PK1") + "  type=\"checkbox\" /></td>";
					html += "<td class=\"sorting_1\">" + i + "</td>";

					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('"
							+ sesion.pkSorteo
							+ "','"
							+ rs.getString("PK_BOLETO")
							+ "','"
							+ rs.getString("FOLIO")
							+ "','"
							+ rs.getString("PK_TALONARIO")
							+ "','"
							+ rs.getString("TALONARIO")
							+ "','"
							+ rs.getString("ABONO")
							+ "','"
							+ rs.getString("COSTO")
							+ "','"
							+ rs.getString("PK_SECTOR")
							+ "','"
							+ rs.getString("PK_NICHO")
							+ "','"
							+ rs.getString("PK_COLABORADOR") + "')\">";

					if (rs.getString("INCIDENCIA").equals("N")) {
						html += "<span class=\"label label-primary\" >"
								+ rs.getString("FOLIO") + "</span>";
					}
					if (rs.getString("INCIDENCIA").equals("E")) {
						html += "<span class=\"label label-warning\" >"
								+ rs.getString("FOLIO") + "</span>";
					}
					if (rs.getString("INCIDENCIA").equals("R")) {
						html += "<span class=\"label label-danger\" >"
								+ rs.getString("FOLIO") + "</span>";
					}

					html += "</a></td>";

					if (rs.getString("VENDIDO").equals("N")) {
						estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("V")) {
						estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("P")) {
						estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";
					}

					html += "<td class=\"sorting_1\">" + estado + "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("COSTO")
							+ "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("ABONO")
							+ "</td>";

					if (rs.getString("VENDIDOTALONARIO").equals("N")) {
						estadotalonario = "<span class=\"label label-inverse\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("V")) {
						estadotalonario = "<span class=\"label label-success\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("P")) {
						estadotalonario = "<span class=\"label label-default\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}

					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('"
							+ sesion.pkSorteo
							+ "','"
							+ rs.getString("PK_TALONARIO")
							+ "','"
							+ rs.getString("TALONARIO")
							+ "')\">"
							+ estadotalonario + "</a></td>";

					/*
					 * html += "<td class=\"sorting_1\">" +
					 * ((rs.getString("SECTOR") == null) ? "N/A" :
					 * "<a href=\"javascript:ShowDetalleSector('"
					 * +sesion.pkSorteo
					 * +"','"+rs.getString("PK_SECTOR")+"','"+StringEscapeUtils
					 * .escapeHtml4
					 * (rs.getString("SECTOR"))+"')\">"+StringEscapeUtils
					 * .escapeHtml4(rs.getString("SECTOR"))+"</a>") + "</td>";
					 */

					html += "<td class=\"sorting_1\">"
							+ ((rs.getString("NICHO") == null) ? "N/A"
									: "<a href=\"javascript:ShowDetalleNicho('"
											+ sesion.pkSorteo
											+ "','"
											+ rs.getString("PK_SECTOR")
											+ "','"
											+ rs.getString("PK_NICHO")
											+ "','"
											+ rs.getString("NICHO")
											+ "')\">"
											+ StringEscapeUtils.escapeHtml4(rs
													.getString("NICHO"))
											+ "</a>") + "</td>";

					html += "<td class=\"sorting_1\">"
							+ ((rs.getString("COLABORADOR") == null) ? "N/A"
									: "<a href=\"javascript:ShowDetalleColaborador('"
											+ sesion.pkSorteo
											+ "','"
											+ rs.getString("PK_SECTOR")
											+ "','"
											+ rs.getString("PK_NICHO")
											+ "','"
											+ rs.getString("PK_COLABORADOR")
											+ "','"
											+ StringEscapeUtils.escapeHtml4(rs
													.getString("COLABORADOR"))
											+ "')\">"
											+ StringEscapeUtils.escapeHtml4(rs
													.getString("COLABORADOR"))
											+ "</a>") + "</td>";

				}

			} else {

				html += "<tr> <td colspan=\"11\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Boletos en Transito en el Sector </h1>";
				html += "</div>";
				html += "</td></tr>";
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

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mBoletosTransito model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto", "Estatus", "Costo", "Abono",
				"Talonario", "Sector", "Nicho", "Colaborador" };
		String[] campos = { "FOLIO", "VENDIDO", "COSTO", "ABONO",
				"TALONARIO", "SECTOR", "NICHO", "COLABORADOR" };

		int numeroregistros = model.contar(search,  sesion.pkSorteo);

		HashMap<Long,String> sectores = model.consultaTodosSectores(sesion.pkSorteo);
		HashMap<Long,String> nichos = model.consultaTodosNichos(sesion.pkSorteo);
		String HTML = CrearTabla(numeroregistros,
				model.paginacion(pg, show, search,  sesion.pkSorteo),
				columnas, campos, pg, show, sesion, sectores, nichos);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion,
			HashMap<Long,String> sectores,
			HashMap<Long,String> nichos) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		StringBuilder sb = new StringBuilder(1000);
		sb.append("<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">")
		.append("<thead>")
		.append("<tr role=\"row\">")

		//.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 12px;\" ></th>")
		.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");
		for (String columna : campostable) {
			sb.append(columna);
		}

		sb
		.append(" </thead>")
		.append(" <tbody>");

		String estado = "";
		String estadotalonario = "";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {
					Long PK_SECTOR = rs.getLong("PK_SECTOR");
					Long PK_NICHO = rs.getLong("PK_NICHO");
					String SECTOR = (PK_SECTOR != null && sectores.containsKey(PK_SECTOR)) ? sectores.get(PK_SECTOR) : null;
					String NICHO = (PK_NICHO != null && nichos.containsKey(PK_NICHO)) ? nichos.get(PK_NICHO) : null;

					i++;

					sb
					.append("<tr class=\"gradeA odd\" role=\"row\">")
					//.append("<td class=\"sorting_1\"><input  id=\"").append(rs.getInt("PK1")).append("\"  type=\"checkbox\" /></td>")
					.append("<td class=\"sorting_1\">").append(i).append("</td>");

					sb
					.append("<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('")
							.append(sesion.pkSorteo)
							.append("','")
							.append(rs.getString("PK_BOLETO"))
							.append("','")
							.append(rs.getString("FOLIO"))
							.append("','")
							.append(rs.getString("PK_TALONARIO"))
							.append("','")
							.append(rs.getString("TALONARIO"))
							.append("','")
							.append(rs.getString("ABONO"))
							.append("','")
							.append(rs.getString("COSTO"))
							.append("','")
							.append(PK_SECTOR)
							.append("','")
							.append(PK_NICHO)
							.append("','")
							.append(rs.getString("PK_COLABORADOR"))
							.append("')\">");

					// NOTA: Sio esta en transito no tiene incidencias.
					/*
					if (rs.getString("INCIDENCIA").equals("N")) {
						sb.append("<span class=\"label label-primary\" >")
								.append(rs.getString("FOLIO")).append("</span>");
					}
					if (rs.getString("INCIDENCIA").equals("E")) {
						sb.append("<span class=\"label label-warning\" >")
								.append(rs.getString("FOLIO")).append("</span>");
					}
					if (rs.getString("INCIDENCIA").equals("R")) {
						sb.append("<span class=\"label label-danger\" >")
								.append(rs.getString("FOLIO")).append("</span>");
					}
					//*/
					sb
						.append("<span class=\"label label-primary\" >")
						.append(rs.getString("FOLIO")).append("</span>");

					sb.append("</a></td>");

					if (rs.getString("VENDIDO").equals("N")) {
						estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("V")) {
						estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";
					}
					if (rs.getString("VENDIDO").equals("P")) {
						estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";
					}

					sb.append("<td class=\"sorting_1\">").append(estado).append("</td>");
					sb.append("<td class=\"sorting_1\">").append(rs.getString("COSTO")).append("</td>");
					sb.append("<td class=\"sorting_1\">").append(rs.getString("ABONO")).append("</td>");

					if (rs.getString("VENDIDOTALONARIO").equals("N")) {
						estadotalonario = "<span class=\"label label-inverse\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("V")) {
						estadotalonario = "<span class=\"label label-success\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}
					if (rs.getString("VENDIDOTALONARIO").equals("P")) {
						estadotalonario = "<span class=\"label label-default\" >"
								+ rs.getString("TALONARIO") + "</span>";
					}

					sb.append("<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('")
							.append(sesion.pkSorteo)
							.append("','")
							.append(rs.getString("PK_TALONARIO"))
							.append("','")
							.append(rs.getString("TALONARIO"))
							.append("')\">")
							.append(estadotalonario).append("</a></td>");

					sb.append("<td class=\"sorting_1\">");
							//+ ((rs.getString("SECTOR") == null) ? "N/A"
					if (PK_SECTOR == null) sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleSector('")
											.append(sesion.pkSorteo)
											.append("','")
											//+ rs.getString("PK_SECTOR")
											.append(PK_SECTOR)
											.append("','")
											//+ StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))
											.append(StringEscapeUtils.escapeHtml4(SECTOR))
											.append("')\">")
											.append(StringEscapeUtils.escapeHtml4(SECTOR))
											.append("</a>");
					sb.append("</td>");

					sb.append("<td class=\"sorting_1\">");
							//+ ((rs.getString("NICHO") == null) ? "N/A"
					if(PK_NICHO == null) sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleNicho('")
											.append(sesion.pkSorteo)
											.append("','")
											//.append(rs.getString("PK_SECTOR")
											.append(PK_SECTOR)
											.append("','")
											//.append(rs.getString("PK_NICHO")
											.append(PK_NICHO)
											.append("','")
											//.append(rs.getString("NICHO")
											.append(NICHO)
											.append("')\">")
											//.append(StringEscapeUtils.escapeHtml4(rs.getString("NICHO"))
											.append(StringEscapeUtils.escapeHtml4(NICHO))
											.append("</a>");
					sb.append("</td>");

					sb.append("<td class=\"sorting_1\">");
					if (rs.getString("COLABORADOR") == null) sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleColaborador('")
											.append(sesion.pkSorteo)
											.append("','")
											//.append(rs.getString("PK_SECTOR")
											.append(PK_SECTOR)
											.append("','")
											//.append(rs.getString("PK_NICHO")
											.append(PK_NICHO)
											.append("','")
											.append(rs.getString("PK_COLABORADOR"))
											.append("','")
											.append(StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR")))
											.append("')\">")
											.append(StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR")))
											.append("</a>");
					sb.append("</td>");
				}

			} else {
				sb
				.append("<tr> <td colspan=\"11\"> ")
				.append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">")
				.append("<h1>No existen Boletos en Transito </h1>")
				.append("</div>")
				.append("</td></tr>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sb
		.append("</tbody>")
		.append("</table>");


		String paginado = getPaginado(numreg, show, pg);
		sb.insert(0, paginado);
		//html = paginado + html;
		sb.append(show);

		return sb.toString();

	}
	
	protected String getPaginado(int numreg, int show, int pg){
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
		
		return paginado;
	}

}
