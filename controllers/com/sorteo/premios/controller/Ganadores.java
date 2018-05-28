package com.sorteo.premios.controller;

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
import com.core.SesionDatos;
import com.sorteo.premios.model.mGanadores;;

/**
 * Servlet implementation class Premios
 */
@WebServlet("/Ganadores")
public class Ganadores extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Ganadores() {
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
		mGanadores model = new mGanadores();
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String str_pg;
		String str_show;
		int pg = 0;
		int show = 0;
		String search = "";
		
		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(20099)) { view = "error"; }
		
		if (view == null) {
			view = "";
			if (request.getParameter("idsorteo")==null) {
				view = "Lista";
			}
		}
		System.out.println("view="+view);

		switch (view) {
		case "Lista":
			SorteoPredeterminado(request, response, model, sesion);
			break;

		case "Agregar":
			fullPath = "/WEB-INF/views/premios/agregar_ganador.html";
			menu = vista.initMenu(0, false, 16, 18, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			break;

		case "BuscarPremios":
			
			pg = (str_pg = request.getParameter("pg")) == null ? 1 : Integer.valueOf(str_pg);
			show = (str_show = request.getParameter("show")) == null ? 100 : Integer.valueOf(str_show);
			search = request.getParameter("search");
			if (search == null)
				search = "";
			HTML = BuscarPremio(request, response, pg, show, search, model, sesion);
			
			break;

		case "BuscarCompradores":
			pg = (str_pg = request.getParameter("pg")) == null ? 1 : Integer.valueOf(str_pg);
			show = (str_show = request.getParameter("show")) == null ? 100 : Integer.valueOf(str_show);
			search = request.getParameter("search");
			if (search == null)
				search = "";
			HTML = BuscarCompradores(request, response, pg, show, search, model, sesion);
			
			break;

		case "Buscar":
			pg = (str_pg = request.getParameter("pg")) == null ? 1 : Integer.valueOf(str_pg);
			show = (str_show = request.getParameter("show")) == null ? 100 : Integer.valueOf(str_show);
			search = request.getParameter("search");
			if (search == null)
				search = "";
			
			HTML = Buscar(request, response, pg, show, search, model, sesion);
			
			break;
			
		case "Borrar":
			borraGanador(request, response, model, sesion);
			
			return;
			//break;
			
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 16, 18, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
			
		default:
			
			int idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
			}
			
			fullPath = "/WEB-INF/views/premios/lista_ganadores.html";
			menu = vista.initMenu(0, false, 16, 18, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			HTML = putCode(
					HTML, 20102,
					"#BTN_AGREGAR_GANADOR#", "",
					"<a class=\"btn btn-primary m-r-5\" href=\"Ganadores?view=Agregar\">Agregar ganador</a>",
					sesion);
			
			model.setIdsorteo(sesion.pkSorteo);
			model.consultaSorteo();
			HTML = HTML.replaceFirst("#SORTEO#", model.getSorteo());

			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	private String putCode(String HTML, int pkPermiso, String mark, String if_not_have, String if_have, SesionDatos sesion){
		String str_put;
		if (sesion.permisos.havePermiso(pkPermiso))
			str_put = if_have;
		else
			str_put = if_not_have;
		return HTML.replaceAll(mark, str_put);
	}
	
	private void borraGanador(HttpServletRequest request,
			HttpServletResponse response, mGanadores model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setIdsorteo(sesion.pkSorteo);
		int idGanador = Integer.valueOf(request.getParameter("idGanador"));
		model.borraGanador(idGanador, sesion);
		
		model.close();
		
		response.sendRedirect("Ganadores?idsorteo=" + model.getIdsorteo());
	}
	
	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, mGanadores model, SesionDatos sesion) throws ServletException, IOException {
		
		model.setIdUsuario(sesion.pkUsuario);
		model.consultaUsuarioSorteo();

		if (model.getIdsorteo() != 0) {
			response.sendRedirect("Ganadores?idsorteo=" + model.getIdsorteo());
		}
	}
	
	private String Buscar(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search, mGanadores model, SesionDatos sesion)
	{
		model.setIdsorteo(sesion.pkSorteo);
		int numeroregistros = model.contarGanadores();
		return CrearTabla(numeroregistros, model.paginacionCompradoresGanadores(pg, show, search), pg, show);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show) {
		// TODO CrearTabla

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		double valor;
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

		String html = "<ul class=\"result-list\">";

		try {
			if (Integer.valueOf(numreg) > 0) {
				while (rs.next()) {
					valor = rs.getDouble("VALOR");
					// direccion
					String calle = rs.getString("CALLE");
					String numero = rs.getString("NUMERO");
					String colonia = rs.getString("COLONIA");
					String estado = rs.getString("ESTADO");
					if (calle == null) calle = "";
					if (numero == null) numero = "";
					if (colonia == null) colonia = "";
					if (estado == null) estado = "";

					// --- imagen
					html += ""
						+ "<li id=\"sorteo" + rs.getString("PK1") + "\">\n"
						+ "  <div class=\"result-image\" style=\"background-color:White\" >\n"
						+ "		<img alt=\"\" src=\"uploads/"+rs.getString("IMAGEN")+"\" />\n"
						+ "  </div>\n"

						// --- comprador, boleto y premio
						+ "  <div class=\"result-info\">\n"
						+ "    <h4 class=\"title\">\n"
						+ "      <div style=\"margin-bottom:6pt;\">" + rs.getString("COMPRADOR")
						+ "<small><div class=\"pull-right\">Boleto: <span class=\"badge badge-success badge-square\">" + rs.getString("FOLIO") + "</span></div></small>  </div>\n"
						+ "      <span class=\"label label-primary\">" + rs.getString("PREMIO") + "</span>\n"
						+ "    </h4>\n"
						
						// --- Direccion
						+ "    <div class=\"result-info\">\n"
						+ "      <br><b>DIRECCION:</b> " + calle + " N&#176; " + numero + ", Col. " + colonia + ", Edo. " + estado + "\n"
						+ "      <br><b>CONTACTO:</b>&nbsp;&nbsp;<b>Telefonos:</b>&nbsp;&nbsp;Casa: " + rs.getString("TELEFONOF") + ", Celular: " + rs.getString("TELEFONOM") + "&nbsp;&nbsp;<b>Correo:</b> " + rs.getString("CORREO") + "\n"
						+ "    </div>\n"
						
						// --- Barra de iconos

						+ "<br/>"
						+ "<div class=\"btn-row\">"
						+ "  <a href=\"javascript:showModal(" + rs.getInt("PK_GANADOR") + ");\" data-toggle=\"tooltip\" data-container=\"body\" data-title=\"Eliminar premio\">"
						+ "    <i class=\"fa fa-fw fa-times-circle\"></i>"
						+ "    <span style=\"font-size:11px;\">Borra ganador</span>"
						+ "  </a>"
						+ "</div>"

						+ "  </div>"
							
						;


					int clasificacion = rs.getInt("CLASIFICACION");
					String str_clasificacion = "" + clasificacion + ((clasificacion == 1 || clasificacion == 3) ? "er PREMIO" : "&#176; PREMIO");

					html += "<div class=\"result-price\">" + str_clasificacion + " <small>" + englishFormat.format(valor) + "</small>";
					html += "<a class=\"btn btn-inverse btn-block\" href=\"SorteoDetalle?sorteo="+rs.getString("PK1")+"\">Ver Detalles</a>";
					
					html += "</div>";

					html += "</li>";

					// System.out.println(rs.getString("NOMBRE"));

				}

			} else {

				html += "<li align= \"center\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";

				html += "<h1>No existen Ganadores</h1>";
				html += "<p>Empieze por agregar un nuevo ganador.</p>";

				html += "</div>";

				html += "</li>";

			}

			html += "</ul>";

		} catch (SQLException e) {
			Factory.Error(e, "rs="+rs);
		}

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
		int r = (pg) / show;
		int sumpag = 0;

		if (r == 0) {

			inipg = pg - 1;
		} else {
			inipg = ((pg - 1) / show) - show;
		}

		for (int j = 0; j < 10 + inipg + 1; j++) {
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
	
	private String BuscarPremio(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search, mGanadores model, SesionDatos sesion)
	{
		model.setIdsorteo(sesion.pkSorteo);
		int numeroregistros = model.contarPremios();
		return CrearTablaPremios(numeroregistros, model.paginacionPremios(pg, show, search), pg, show);
	}
	
	private String CrearTablaPremios(int numreg, ResultSet rs, int pg, int show) {
		// TODO CrearTablaPremios
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		StringBuilder sb = new StringBuilder();
		try {
			if (rs != null)
				while (rs.next()) {
					
					
					int pkPremio = -1;
					try{
						
						pkPremio = Integer.valueOf(rs.getString("PK_PREMIO_COLABORADOR"));
						
					}catch(Exception ex) { }
					
					if (pkPremio != -1) {
						int clasificacion = rs.getInt("CLASIFICACION");
						String caption = "";
						if (clasificacion == 1 || clasificacion == 3)
							caption = "" + clasificacion + "er PREMIO";
						else
							caption = "" + clasificacion + "&#176; PREMIO";
						
						sb
						.append("<div style=\"width:220pt; padding:10pt; float:left;\">")
						.append("  <div class=\"image-inner\">")
						.append("    <a href=\"uploads/").append(rs.getString("IMAGEN").trim()).append("\" data-lightbox=\"gallery-group-1\"><img style=\"width:200pt; height: 160pt; padding: 2pt; background-color:#EEEEEE;\" src=\"uploads/").append(rs.getString("IMAGEN").trim()).append("\" alt=\"\"></a>")
						.append("    <p class=\"image-caption\">").append(caption).append(",&#160;&#160;").append(englishFormat.format(rs.getDouble("VALOR"))).append("</p>")
						.append("  </div>")
						.append("  <a id=\"selectPremio_").append(rs.getInt("PK1")).append("\" href=\"javascript:selectPremio(").append(rs.getInt("PK1")).append(",").append(rs.getInt("PK_PREMIO_COLABORADOR")).append(");\" class=\"btn btn-primary m-r-5\" style=\"width:100%;\">").append(rs.getString("NOMBRE").trim()).append("</a>")
						.append("</div>")
						;
					}
				}
		}catch (SQLException ex) { Factory.Error(ex, ""); }
		
		return sb.toString();
	}

	private String BuscarCompradores(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search, mGanadores model, SesionDatos sesion)
	{
		String[] columnas = { "Boleto","Estatus","Talonario","Comprador","Sector","Nicho","Colaborador"};
		String[] campos = { "FOLIO","VENDIDO","TALONARIO","COMPRADOR","SECTOR","NICHO","COLABORADOR"};
		
		model.setIdsorteo(sesion.pkSorteo);
		int numeroregistros = model.contarCompradores(search);
		ArrayList<Integer> listPkGanadores = model.getListCompradoresGanadores();
		
		return CrearTablaCompradores(numeroregistros,
				model.paginacionCompradores(pg, show, search), columnas,
				campos, pg, show, listPkGanadores, model, sesion);
	}
		
	protected String CrearTablaCompradores(int numreg, ResultSet rs, String[] columnas,
				String[] campos, int pg, int show, ArrayList<Integer> listPkGanadores, mGanadores model, SesionDatos sesion) {
		// TODO CrearTablaCompradores

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo
		= "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 12px;\" ></th>";
	//	html += "<th styele=\"width:0pt;\"></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			html += columna;
		}

		html += "  </tr>";
		html += " </thead>";
		html += " <tbody>";
        
		String estado= "";
		String estadotalonario ="";
		
		try {
			if (Integer.valueOf(numreg) > 0) {
				//html += "<tr><td></td></tr>";
			
				while (rs.next()) {
					i++;
	
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					
					html += "<td class=\"sorting_1\">";
					int pkComprador = rs.getInt("PK_COMPRADOR");
					boolean ganador = false;
					for(Integer pkItem : listPkGanadores)
						if (pkItem.intValue() == pkComprador) {
							ganador = true;
							break;
						}
					if (ganador == false)
						html += "<input onclick=\"javascript:seleccionaComprador('" + pkComprador + "')\" type=\"radio\" name=\"radio_compradores\" style=\"width=30pt; height:30pt; \" />";
					html += "</td>";
					
					html += "<td class=\"sorting_1\">" + i + "</td>";
	
					  html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+rs.getString("PK_BOLETO")+"','"+rs.getString("FOLIO")+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"','"+rs.getString("ABONO")+"','"+rs.getString("COSTO")+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"','"+rs.getString("PK_COLABORADOR")+"')\">";
						
						
						if(rs.getString("INCIDENCIA").equals("N")){  html += "<span class=\"label label-primary\" >"+rs.getString("FOLIO")+"</span>";}
						if(rs.getString("INCIDENCIA").equals("E")){  html += "<span class=\"label label-warning\" >"+rs.getString("FOLIO")+"</span>";}
						if(rs.getString("INCIDENCIA").equals("R")){  html += "<span class=\"label label-danger\" >"+rs.getString("FOLIO")+"</span>";}
						
						html += "</a></td>";
						
						if(rs.getString("VENDIDO").equals("N")){  estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";}
						if(rs.getString("VENDIDO").equals("V")){  estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";}
						if(rs.getString("VENDIDO").equals("P")){  estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";}	
					
					html += "<td class=\"sorting_1\">" + estado + "</td>";
					
					if(rs.getString("VENDIDOTALONARIO").equals("N")){  estadotalonario = "<span class=\"label label-inverse\" >"+rs.getString("TALONARIO")+"</span>";}
					if(rs.getString("VENDIDOTALONARIO").equals("V")){  estadotalonario = "<span class=\"label label-success\" >"+rs.getString("TALONARIO")+"</span>";}
					if(rs.getString("VENDIDOTALONARIO").equals("P")){  estadotalonario = "<span class=\"label label-default\" >"+rs.getString("TALONARIO")+"</span>";}
					
					
					html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('"+sesion.pkSorteo+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"')\">" + estadotalonario
							+ "</a></td>";
					
					html += "<td class=\"sorting_1\">" + ((rs.getString("COMPRADOR") == null) ?  "N/A" : "<span>"+StringEscapeUtils.escapeHtml4(rs.getString("COMPRADOR"))+"</span>")
							+"</td>";
	
					html += "<td class=\"sorting_1\">" + ((rs.getString("SECTOR") == null) ?  "N/A" : "<a href=\"CompradoresSectores?idsorteo="+sesion.pkSorteo+"&idsector="+ rs.getString("PK_SECTOR")+  "\">"+StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))+"</a>")
							+ "</td>";
					
					html += "<td class=\"sorting_1\">" + ((rs.getString("NICHO") == null) ?  "N/A" : "<a href=\"CompradoresNichos?idsorteo="+sesion.pkSorteo+"&idsector="+ rs.getString("PK_SECTOR")+"&idnicho="+rs.getString("PK_NICHO")+  "\">"+StringEscapeUtils.escapeHtml4(rs.getString("NICHO"))+"</a>")
							+ "</td>";
					
					html += "<td class=\"sorting_1\">" + ((rs.getString("COLABORADOR") == null) ?  "N/A" : "<a href=\"CompradoresColaborador?idsorteo="+sesion.pkSorteo+"&idsector="+ rs.getString("PK_SECTOR")+"&idnicho="+rs.getString("PK_NICHO")+ "&idcolaborador="+rs.getString("PK_COLABORADOR")+ " \">"+StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR"))+"</a>")
							+ "</td>";
					
					html += "</tr>";
				}
			} else {
				html += "<tr> <td colspan=\"11\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "  <h1>No existen Compradores</h1>";
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
				+ pg + " de " + denumpag + " total " + numreg + " elementos</div>";
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

		html = paginado + html;

		return html;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response, mGanadores model, SesionDatos sesion) throws ServletException, IOException {
		// TODO doPost()
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String accion = request.getParameter("accion");
		
		if ("insertar".equals(accion)) {
			model.setIdPremio(Integer.valueOf(request.getParameter("idPremio")));
			model.setIdComprador(Integer.valueOf(request.getParameter("idComprador")));
			model.setIdPremioDeColaborador(Integer.valueOf(request.getParameter("idPremioColaborador")));
			
			if (model.registrar(sesion) == -1) {
				model.close();
				throw new ServletException("Error registrando boleto");
			}
		}
		model.close();
	}
	
}

