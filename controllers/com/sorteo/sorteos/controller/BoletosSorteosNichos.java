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
import com.core.MENU;
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mBoletosSorteosNichos;

/**
 * Servlet implementation class BoletosSorteosSectores
 */
@WebServlet("/BoletosSorteosNichos")
public class BoletosSorteosNichos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoletosSorteosNichos() {
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
		mBoletosSorteosNichos model = new mBoletosSorteosNichos();
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		//10179 ACCESO A LA LISTA DE NICHO		
		 if (!sesion.permisos.havePermiso(10179)){view = "error";}
		 
		
		
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
			
				
			HTML = getBoletosTalonarios(model, sesion);
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
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
	

		default:
			
			int idnicho = Integer.valueOf(request.getParameter("idnicho"));
			
			fullPath = "/WEB-INF/views/sorteos/boletos/BoletosSorteosNichos.html";
			menu = vista.initMenu(0, false, MENU.SORTEOS, MENU.LISTA_SORTEOS, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			if (sesion.pkNicho != idnicho) {
				sesion.pkNicho = idnicho;
				sesion.guardaSesion();
			}
			
			model.setIdNicho(sesion.pkNicho);
			model.consultaSorteoSector();
			
			HTML = HTML.replaceAll("#SORTEO#", model.consultaSorteo());
			HTML = HTML.replaceAll("#SECTOR#", model.consultaSector());
			HTML = HTML.replaceAll("#NICHO#", model.consultaNicho());
			
			HTML = HTML.replaceFirst("#ID_SORTEO#", "" + model.getIdSorteo());
			HTML = HTML.replaceFirst("#ID_SECTOR#", "" + model.getIdSector());
			HTML = HTML.replaceFirst("#ID_NICHO#", "" + model.getIdNicho());
			
			HTML = HTML.replaceAll("#DISPLAY_MENU#", sesion.sorteoActivo ? "display" : "none");
			
			HTML = HTML.replaceAll("#BUTTONS_DISABLED#", sesion.sorteoActivo ? "" : "disabled");			
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	protected String getBoletosTalonarios(mBoletosSorteosNichos model, SesionDatos sesion){
		   
		   String contenido = "";
		   contenido = model.getBoletosTalonariosNicho();
		   return contenido;
		   
	   }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SesionDatos sesion;
		mBoletosSorteosNichos model = new mBoletosSorteosNichos();

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
			int pg, int show, String search, mBoletosSorteosNichos model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Folio digital","Colaborador"};
		String[] campos = { "FOLIO","PK_ESTADO","COSTO","ABONO","FOLIO_TALONARIO","FOLIODIGITAL","COLABORADOR" };

		model.setIdSorteo(Integer.valueOf(request.getParameter("id_sorteo")));
		model.setIdSector(Integer.valueOf(request.getParameter("id_sector")));
		model.setIdNicho(Integer.valueOf(request.getParameter("id_nicho")));
		int numeroregistros = model.contar(search);

		return CrearTabla(numeroregistros, model.paginacion(pg, show, search), columnas, campos, pg, show, model, sesion);
	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, mBoletosSorteosNichos model, SesionDatos sesion) {
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
					//sb.append("<td class='sorting_1'><a href=\"javascript:ShowDetalleTalonario('").append(sesion.pkSorteo+"','").append(PK_TALONARIO).append("','").append(TALONARIO).append("')\">");
					sb.append("<td class='sorting_1'>");
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
					
					// COLABORADOR
					sb.append("<td class='sorting_1'>");
					if (COLABORADOR == null)
						sb.append("N/A");
					else
						sb.append(StringEscapeUtils.escapeHtml4(COLABORADOR).replaceAll(" ", "&nbsp;"));
					sb.append("</td>");
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
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
