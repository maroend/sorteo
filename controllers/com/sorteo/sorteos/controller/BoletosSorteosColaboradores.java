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

import org.apache.commons.lang3.StringEscapeUtils;

import com.core.ESTADO_BOLETO;
import com.core.Factory;
import com.core.Global;
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mBoletosSorteosColaboradores;
import com.sorteo.sorteos.model.mBoletosSorteosNichos;

/**
 * Servlet implementation class BoletosSorteosColaboradores
 */
@WebServlet("/BoletosSorteosColaboradores")
public class BoletosSorteosColaboradores extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoletosSorteosColaboradores() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Factory vista = new Factory();
		SesionDatos sesion;
		mBoletosSorteosColaboradores model = new mBoletosSorteosColaboradores();


		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		String view = request.getParameter("view");
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		//int idSorteo = 0;

		if (view == null) {
			view = "";
		}

		switch (view) {

		case "getBoletosTalonarios":
			// int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			/*int idsector = Integer.valueOf(request.getParameter("idsector"));

			if (sesion.pkSector != idsector) {
				sesion.pkSector = idsector;
				sesion.guardaSesion();
			}*/

			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdNicho(sesion.pkNicho);
			model.setIdColaborador(sesion.pkColaborador);


			HTML = getBoletosTalonarios(model);
				break;

		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			model.setFiltrovendidos(Integer.valueOf(request.getParameter("filtervendido")));
			model.setFiltronovendidos(Integer.valueOf(request.getParameter("filternovendido")));
			model.setFiltrorobados(Integer.valueOf(request.getParameter("filterrobados")));
			model.setFiltroextraviados(Integer.valueOf(request.getParameter("filterextraviados")));

			int show = Integer.valueOf(request.getParameter("show"));
			String search = Global.decodeBase64(request.getParameter("search"));
			HTML = Buscar(request, pg, show, search, model, sesion);
			break;

		case "delete":
			// eliminarUsuario(request, response);
			break;

		default:

			model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
			model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
			model.setIdNicho(Integer.valueOf(request.getParameter("idnicho")));
			model.setIdColaborador(Integer.valueOf(request.getParameter("idcolaborador")));

			fullPath = "/WEB-INF/views/sorteos/boletos/BoletosSorteosColaboradores.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			if (sesion.pkColaborador != model.getIdColaborador()) {
				sesion.pkColaborador = model.getIdColaborador();
				sesion.guardaSesion();
			}

			HTML = HTML.replaceAll("#SORTEO#", model.consultaSorteo());
			HTML = HTML.replaceAll("#SECTOR#", model.consultaSector());
			HTML = HTML.replaceAll("#NICHO#", model.consultaNicho());
			HTML = HTML.replaceAll("#COLABORADOR#", model.consultaColaborador());

			HTML = HTML.replaceFirst("#ID_SORTEO#", "" + model.getIdSorteo());
			HTML = HTML.replaceFirst("#ID_SECTOR#", "" + model.getIdSector());
			HTML = HTML.replaceFirst("#ID_NICHO#", "" + model.getIdNicho());
			HTML = HTML.replaceFirst("#ID_COLABORADOR#", "" + model.getIdColaborador());

			HTML = HTML.replaceAll("#DISPLAY_MENU#", sesion.sorteoActivo ? "display" : "none");

			HTML = HTML.replaceAll("#BUTTONS_DISABLED#", sesion.sorteoActivo ? "" : "disabled");

			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}


	protected String getBoletosTalonarios(mBoletosSorteosColaboradores model){

		   String contenido = "";
		   contenido = model.getBoletosTalonariosColaborador();
		   return contenido;

	   }



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Factory vista = new Factory();
		SesionDatos sesion;
		mBoletosSorteosColaboradores model = new mBoletosSorteosColaboradores();


		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		String boletoscadena = request.getParameter("idboletos");
		String[] arrBoletos = boletoscadena.split(",");

		model.setIdSorteo(sesion.pkSorteo);
		model.setEstatus(request.getParameter("estatus").charAt(0));

		if(request.getParameter("estatus").charAt(0)== 'A'){

			model.setAbono( Double.parseDouble(request.getParameter("abono")) );
		}

		model.guardarEstatusBoleto(arrBoletos, model, sesion);



	}



	protected String Buscar(HttpServletRequest request,
			int pg, int show, String search,mBoletosSorteosColaboradores model,SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Folio digital"};
		String[] campos = { "FOLIO","PK_ESTADO","COSTO","ABONO","FOLIO_TALONARIO","FOLIODIGITAL" };

		model.setIdSorteo(Integer.valueOf(request.getParameter("id_sorteo")));
		model.setIdSector(Integer.valueOf(request.getParameter("id_sector")));
		model.setIdNicho(Integer.valueOf(request.getParameter("id_nicho")));
		model.setIdColaborador(Integer.valueOf(request.getParameter("id_colaborador")));
		int numeroregistros = model.contar(search);


		return CrearTabla(numeroregistros, model.paginacion(pg, show, search), columnas, campos, pg, show, sesion);
	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {
		// TODO: CrearTabla

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		StringBuilder sb = new StringBuilder(100000);
		sb
		.append("<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">")
		.append("<thead>")
		.append("<tr role=\"row\">")
		.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");
		for (String columna : campostable) {
			sb.append(columna);
		}

		sb
		.append(" </thead>")
		.append(" <tbody>");

		String estado= "";
		String estadotalonario ="";
		String electronico = "";

		try {
			if (Integer.valueOf(numreg) > 0) {
				while (rs.next()) {

					String NICHO = rs.getString("NICHO");
					String COLABORADOR = rs.getString("COLABORADOR");
					String ESTADO = rs.getString("ESTADO");
					String INCIDENCIA = rs.getString("INCIDENCIA_CVE").trim();
					String FOLIO = rs.getString("FOLIO");
					String PK_ESTADO = rs.getString("PK_ESTADO");
					String FOLIODIGITAL = rs.getString("FOLIODIGITAL");
					String COSTO = rs.getString("COSTO");
					String ABONO = rs.getString("ABONO");
					String ELECTRONICO = rs.getString("ELECTRONICO");
					String TALONARIO = rs.getString("FOLIO_TALONARIO");
					int PK_TALONARIO = rs.getInt("PK_TALONARIO");
					int TAL_NUM_BOLETOS = rs.getInt("TAL_NUM_BOLETOS");
					int TAL_BOLS_VENDIDOS = rs.getInt("TAL_BOLS_VENDIDOS");

					i++;
					sb
					.append("<tr class=\"gradeA odd\" role=\"row\">");

					// CONSECUTIVO
					sb
					.append("<td class=\"sorting_1\">").append(i).append("</td>");

					// FOLIO-BOLETO
					sb.append("<td class='sorting_1'>");
					if (INCIDENCIA.equals("N")) { sb.append("<span class='label label-primary' >").append(FOLIO).append("</span>"); }
					if (INCIDENCIA.equals("E")) { sb.append("<span class='label label-warning' >").append(FOLIO).append("</span>"); }
					if (INCIDENCIA.equals("R")) { sb.append("<span class='label label-danger'  >").append(FOLIO).append("</span>"); }
					sb.append("</td>");

					// ESTADO
					sb.append("<td class='sorting_1'>")
						.append("<span class='badge ").append(ESTADO_BOLETO.getEstilo(PK_ESTADO)).append(" badge-square' >").append(ESTADO).append("</span>")
						.append("</td>");

					// COSTO Y ABONO
					sb
					.append("<td class='sorting_1'>").append(COSTO).append("</td>")
					.append("<td class='sorting_1'>").append(ABONO).append("</td>");

					// ELECTRONICO
					electronico = (ELECTRONICO.compareTo("1") == 0)
							? "<i class='fa fa-credit-card'></i>"
							: "<i class='fa fa-pencil'></i>";

					// TALONARIO
					sb.append("<td class='sorting_1'><a href=\"javascript:ShowDetalleTalonario('").append(sesion.pkSorteo+"','").append(PK_TALONARIO).append("','").append(TALONARIO).append("')\">");

					if (0 < TAL_NUM_BOLETOS) {
						if (TAL_BOLS_VENDIDOS == TAL_NUM_BOLETOS)
							sb.append("<span class='label label-success'>");
						else if (TAL_BOLS_VENDIDOS == 0)
							sb.append("<span class='label label-inverse'>");
						else
							sb.append("<span class='label label-default'>");
					}
					sb.append(TALONARIO).append("</span>").append(" </a>&nbsp;").append(electronico).append("</td>");

					// FOLIO DIGITAL
					sb.append("<td class=\"sorting_1\">").append(FOLIODIGITAL == null ? "" : FOLIODIGITAL).append("</td>");
				}
			}
			else {
				sb
				.append("<tr> <td colspan=\"10\">")
				.append("<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Boletos o Talonarios</h1><p>Empiece por asignar o realice otra busqueda.</p></div>")
				.append("</td></tr>");

			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		sb
		.append("</tbody>")
		.append("</table>");

		String paginado = Factory.Paginado(numreg, show, pg);

		sb.insert(0, paginado);

		return sb.toString();

	}

	protected String CrearTabla2(int numreg, ResultSet rs, String[] columnas,
			String[] campos2, int pg, int show,SesionDatos sesion) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}
		//StringBuilder sb = new StringBuilder();

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" >";
		if(sesion.sorteoActivo)
			html += "<input type=\"checkbox\" onClick=\"seleccionarTodo('1b')\" id=\"checkboxall1b\" name=\"checkboxall\" />";
		html += "</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}

		html += " </thead>";
		html += " <tbody>";

		String estado= "";
		String estadotalonario ="";
		String electronico = "";

		try {

			if (Integer.valueOf(numreg) > 0) {
				String last_pk = null;
				while (rs.next()) {

					if (last_pk != null && last_pk.compareTo(rs.getString("PK_TALONARIO")) != 0) html += "<tr><td colspan='8' style='background-color:white; height: 3px;margin: 0;padding: 0;'></td></tr> <tr></tr>";
					last_pk = rs.getString("PK_TALONARIO");


					i++;

					html += "<tr class=\"gradeA odd\" role=\"row\">";

					html += "<td class=\"sorting_1\">";

					if(rs.getInt("RETORNADO")==1){
						html += "<i class=\"fa fa-2x fa-check-circle\"></i>";
					}else{
						html += "<input	 id=" + rs.getInt("PK1") + "  class=\"boletoschecked\"	 type=\"checkbox\" />";
					}
					html += "</td>";

					html += "<td class=\"sorting_1\">" + i + "</td>";

					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+rs.getString("PK_BOLETO")+"','"+rs.getString("FOLIO")+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"','"+rs.getString("ABONO")+"','"+rs.getString("COSTO")+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"','"+rs.getString("PK_COLABORADOR")+"')\">";

					if(rs.getString("INCIDENCIA").equals("N")){	 html += "<span class=\"label label-primary\" >"+rs.getString("FOLIO")+"</span>";}
					if(rs.getString("INCIDENCIA").equals("E")){	 html += "<span class=\"label label-warning\" >"+rs.getString("FOLIO")+"</span>";}
					if(rs.getString("INCIDENCIA").equals("R")){	 html += "<span class=\"label label-danger\" >"+rs.getString("FOLIO")+"</span>";}

					html += "</a></td>";


					if(rs.getString("VENDIDO").equals("N")){ estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>"; }
					if(rs.getString("VENDIDO").equals("V")){ estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>"; }
					if(rs.getString("VENDIDO").equals("P")){ estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>"; }

					html += "<td class=\"sorting_1\">" + estado + "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("COSTO") + "</td>";

					html += "<td class=\"sorting_1\">" + rs.getString("ABONO") + "</td>";

					if(rs.getString("VENDIDOTALONARIO").equals("N")){ estadotalonario = "<span class=\"label label-inverse\" >"+rs.getString("TALONARIO")+"</span>"; }
					if(rs.getString("VENDIDOTALONARIO").equals("V")){ estadotalonario = "<span class=\"label label-success\" >"+rs.getString("TALONARIO")+"</span>"; }
					if(rs.getString("VENDIDOTALONARIO").equals("P")){ estadotalonario = "<span class=\"label label-default\" >"+rs.getString("TALONARIO")+"</span>"; }

					// DIGITAL
					electronico = (rs.getString("DIGITAL").compareTo("1") == 0)
							? "<i class='fa fa-credit-card'></i>"
							: "<i class='fa fa-pencil'></i>";

					// TALONARIO
					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('"+sesion.pkSorteo+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"')\">" + estadotalonario
							+ "</a> "+electronico+"</td>";


					String folioDigital = rs.getString("FOLIODIGITAL");
					html += "<td class=\"sorting_1\">" + (folioDigital==null?"":folioDigital) + "</td>";

					html += "</tr>";
					/*html += "<td class=\"sorting_1\">" + ((rs.getString("SECTOR") == null) ?	"N/A" : "<a href=\"javascript:ShowDetalleSector('"+sesion.pkSorteo+"','"+rs.getString("PK_SECTOR")+"','"+StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))+"')\">"+StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))+"</a>")
							+ "</td>";*/

					/*html += "<td class=\"sorting_1\">" + ((rs.getString("NICHO") == null) ?  "N/A" : "<a href=\"javascript:ShowDetalleNicho('"+sesion.pkSorteo+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"','"+rs.getString("NICHO")+"')\">"+StringEscapeUtils.escapeHtml4(rs.getString("NICHO"))+"</a>")
							+ "</td>";*/

					/*html += "<td class=\"sorting_1\">" + ((rs.getString("COLABORADOR") == null) ?	 "N/A" : "<a href=\"javascript:ShowDetalleColaborador('"+sesion.pkSorteo+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"','"+rs.getString("PK_COLABORADOR")+"','"+StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR"))+"')\">"+StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR"))+"</a>")
							+ "</td>";	*/

				//	html += "<td class=\"sorting_1\"></td>";
				//	html += "<td class=\"sorting_1\"></td>";
				//	html += "<td class=\"sorting_1\"></td>";
					//System.out.println(rs.getString("SECTOR"));

				}
			} else {
				html += "<tr> <td colspan=\"10\">"
						+"<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Boletos o Talonarios</h1><p>Empiece por asignar o realice otra busqueda.</p></div>"
						+ "</td></tr>";
			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		html += "</tbody>";
		html += "</table>";


		String paginado = Factory.Paginado(numreg, show, pg);
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
		html = paginado + html;

		return html;

	}


}
