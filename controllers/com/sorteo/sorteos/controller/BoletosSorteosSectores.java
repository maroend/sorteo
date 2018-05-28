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
import com.sorteo.sorteos.model.mBoletosSorteoSector;

/**
 * Servlet implementation class BoletosSorteosSectores
 */
@WebServlet("/BoletosSorteosSectores")
public class BoletosSorteosSectores extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoletosSorteosSectores() {
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
		mBoletosSorteoSector model = new mBoletosSorteoSector();
		
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		//10109 ACCESO A LA LISTA DE NICHO		
		 if (!sesion.permisos.havePermiso(10178)){view = "error";}
		 
		 
		 
		 
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
			HTML = Buscar(request, response, pg, show, search,model,sesion);
			break;

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			break;		

		default:
			
			model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
			model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
			
			fullPath = "/WEB-INF/views/sorteos/boletos/BoletosSorteosSectores.html";
			menu = vista.initMenu(0, false, MENU.SORTEOS, MENU.LISTA_SORTEOS, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			if (sesion.pkSector != model.getIdSector()) {
				sesion.pkSector = model.getIdSector();
				sesion.guardaSesion();
			}
			
			HTML = HTML.replaceFirst("#SORTEO#", model.consultaSorteo());
			HTML = HTML.replaceFirst("#SECTOR#", model.consultaSector());

			HTML = HTML.replaceFirst("#ID_SORTEO#", "" + model.getIdSorteo());
			HTML = HTML.replaceFirst("#ID_SECTOR#", "" + model.getIdSector());

			/*
			model.Totalboletos(model);
			int numboletos = model.getNumBoletos();
			System.out.println("numboletos: "+numboletos);
			
			regex = "#TOTALBOLETOS#";
			HTML = HTML.replaceAll(regex, Integer.toString(numboletos));

			model.TotalboletosAsignados(model);
			int numboletosasignados = model.getNumBoletosasignados();
			regex = "#BOLETOSSEMBRADOS#";
			HTML = HTML.replaceAll(regex, Integer.toString(numboletosasignados));
			*/
			HTML = HTML.replaceAll("#DISPLAY_MENU#", sesion.sorteoActivo ? "display" : "none");
			
			HTML = HTML.replaceAll("#BUTTONS_DISABLED#", sesion.sorteoActivo ? "" : "disabled");
			
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	protected String getBoletosTalonarios(mBoletosSorteoSector model){
		   
		   String contenido = "";
		   contenido = model.getBoletosTalonariosSector();
		   return contenido;
		   
	   }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Factory vista = new Factory();
		SesionDatos sesion;
		mBoletosSorteoSector model = new mBoletosSorteoSector();
		
		
		
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
			HttpServletResponse response, int pg, int show, String search,mBoletosSorteoSector model,SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Folio digital","Nicho","Colaborador"};
		String[] campos = { "FOLIO","PK_ESTADO","COSTO","ABONO","FOLIO_TALONARIO","FOLIODIGITAL","NICHO","COLABORADOR" };

		model.setIdSorteo(Integer.valueOf(request.getParameter("id_sorteo")));
		model.setIdSector(Integer.valueOf(request.getParameter("id_sector")));
		int numeroregistros = model.contar(search);
		//model.cargaNichos(sesion.pkSector);

		return CrearTabla(numeroregistros, model.paginacion(pg, show, search), columnas, campos, pg, show, sesion);
	}

	/**/
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
		.append("<table class='table table-striped table-bordered dataTable no-footer' id='data-table' role='grid' aria-describedby='data-table_info'>")
		.append("<thead>")
		.append("<tr role='row'>");
		
		/*
		if (sesion.sorteoActivo)
			sb
			.append("<th class='sorting' tabindex='0' aria-controls='data-table' rowspan='1' colspan='1' style='width: 50px;' >")
			.append("<input type='checkbox' onClick=\"seleccionarTodo('1b')\" id='checkboxall1b' name='checkboxall' />")
			.append("</th>");
		*/
		
		sb.append("<th class='sorting' tabindex='0' aria-controls='data-table' rowspan='1' colspan='1' style='width: 15px;' aria-label='Browser: activate to sort column ascending'>No.</th>");
		for (String columna : campostable) {
			sb.append(columna);
		}

		sb
		.append(" </thead>")
		.append(" <tbody>");
        
		String electronico = "";
		
		try {
			if (Integer.valueOf(numreg) > 0) {
				while (rs.next()) {
					//String PK_SECTOR = rs.getString("PK_SECTOR");
					//String PK_NICHO = rs.getString("PK_NICHO");
					//String PK_COLABORADOR = null;// rs.getString("PK_COLABORADOR");
					//String SECTOR = rs.getString("SECTOR");
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

					sb.append("<tr class='gradeA odd' role='row' >");
					
					// CONSECUTIVO
					sb.append("<td class='sorting_1'>").append(i).append("</td>");
					
					// FOLIO-BOLETO
					sb.append("<td class='sorting_1'>");
					if (INCIDENCIA.equals("N")) { sb.append("<span class='label label-primary' >").append(FOLIO).append("</span>"); }
					if (INCIDENCIA.equals("E")) { sb.append("<span class='label label-warning' >").append(FOLIO).append("</span>"); }
					if (INCIDENCIA.equals("R")) { sb.append("<span class='label label-danger'  >").append(FOLIO).append("</span>"); }
					
					//sb.append("</a>");
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
					
					// NICHO
					sb.append("<td class='sorting_1'>");
					if(NICHO == null)
						sb.append("N/A");
					else
						sb.append(Global.cut(StringEscapeUtils.escapeHtml4(NICHO)));
					sb.append("</td>");
					
					// COLABORADOR
					sb.append("<td class='sorting_1'>");
					if (COLABORADOR == null)
						sb.append("N/A");
					else
						sb.append(Global.cut(StringEscapeUtils.escapeHtml4(COLABORADOR)));
					sb.append("</td>");
				}
			}
			else {
				sb
				.append("<tr> <td colspan='10'>")
				.append("<div style='width:100%;' class='jumbotron m-b-0 text-center'><h1>No existen Boletos o Talonarios</h1><p>Empiece por asignar o realice otra busqueda.</p></div>")
				.append("</td></tr>");
			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		sb.append("</tbody>");
		sb.append("</table>");

		String paginado = Factory.Paginado(numreg, show, pg);

		sb.insert(0, paginado);

		return sb.toString();
	}


	/*
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion, HashMap<Long, mNichos> nichos) {

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
		.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" >");
		
		if(sesion.sorteoActivo)
			sb.append("<input type=\"checkbox\" onClick=\"seleccionarTodo('1b')\" id=\"checkboxall1b\" name=\"checkboxall\" />");
		sb
			.append("</th>")
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
	
					//int RETORNADO = rs.getInt("RETORNADO");
					int PK1 = rs.getInt("PK1");
					String PK_BOLETO = rs.getString("PK_BOLETO");
					String FOLIO = rs.getString("FOLIO");
					String PK_TALONARIO = rs.getString("PK_TALONARIO");
					String TALONARIO = rs.getString("TALONARIO");
					String ABONO = rs.getString("ABONO");
					String COSTO = rs.getString("COSTO");
					String PK_SECTOR = rs.getString("PK_SECTOR");
					//String PK_NICHO = rs.getString("PK_NICHO");
					String PK_COLABORADOR = rs.getString("PK_COLABORADOR");
					String INCIDENCIA = rs.getString("INCIDENCIA");
					String VENDIDO = rs.getString("VENDIDO");
					String VENDIDOTALONARIO = rs.getString("VENDIDOTALONARIO");
					String DIGITAL = rs.getString("DIGITAL");
					String COLABORADOR = rs.getString("COLABORADOR");
					
					long PK_NICHO = rs.getLong("PK_NICHO");
					mNichos nicho = nichos.get(PK_NICHO);
					//String NICHO = nichos.containsKey(PK_NICHO) ? nichos.get(PK_NICHO).getNicho() : null;
					
					i++;
					sb
					.append("<tr class=\"gradeA odd\" role=\"row\">")
					.append("<td class=\"sorting_1\">");
					
					if(rs.getInt("RETORNADO")==1){
						sb.append("<i class=\"fa fa-2x fa-check-circle\"></i>");
					}else if(sesion.sorteoActivo){
						sb.append("<input  id=").append(PK1).append("  class=\"boletoschecked\"   type=\"checkbox\" />");	
					}
					sb
					.append("</td>")
					.append("<td class=\"sorting_1\">" + i + "</td>")
					.append("<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('").append(sesion.pkSorteo).append("','").append(PK_BOLETO).append("','").append(FOLIO).append("','").append(PK_TALONARIO).append("','").append(TALONARIO).append("','").append(ABONO).append("','").append(COSTO).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(PK_COLABORADOR).append("')\">");
					
					if (INCIDENCIA.equals("N")) { sb.append("<span class=\"label label-primary\" >").append(FOLIO).append("</span>");}
					if (INCIDENCIA.equals("E")) { sb.append("<span class=\"label label-warning\" >").append(FOLIO).append("</span>");}
					if (INCIDENCIA.equals("R")) { sb.append("<span class=\"label label-danger\" >").append(FOLIO).append("</span>");}
					
					sb.append("</a></td>");
					
					if(VENDIDO.equals("N")){  estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";}
					if(VENDIDO.equals("V")){  estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";}
					if(VENDIDO.equals("P")){  estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";}
									
					sb.append("<td class=\"sorting_1\">").append(estado).append("</td>");
					
					sb.append("<td class=\"sorting_1\">").append(COSTO).append("</td>");
					
					sb.append("<td class=\"sorting_1\">").append(ABONO).append("</td>");
					
					if (VENDIDOTALONARIO.equals("N")) { estadotalonario = "<span class=\"label label-inverse\" >"+TALONARIO+"</span>";}
					if (VENDIDOTALONARIO.equals("V")) { estadotalonario = "<span class=\"label label-success\" >"+TALONARIO+"</span>";}
					if (VENDIDOTALONARIO.equals("P")) { estadotalonario = "<span class=\"label label-default\" >"+TALONARIO+"</span>";}
					
					// DIGITAL 
					electronico = (DIGITAL.compareTo("1") == 0)
							? "<i class='fa fa-credit-card'></i>"
							: "<i class='fa fa-pencil'></i>";
					
					// TALONARIO
					sb.append("<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('").append(sesion.pkSorteo+"','").append(PK_TALONARIO).append("','").append(TALONARIO).append("')\">").append(estadotalonario).append("</a> ").append(electronico).append("</td>");

					String FOLIODIGITAL = rs.getString("FOLIODIGITAL");
					sb.append("<td class=\"sorting_1\">").append(FOLIODIGITAL==null?"":FOLIODIGITAL).append("</td>");
					
					sb.append("<td class=\"sorting_1\">");
					if(nicho == null)
						sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleNicho('").append(sesion.pkSorteo).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(nicho.getNicho()).append("')\">").append(StringEscapeUtils.escapeHtml4(nicho.getNicho())).append("</a>").append("</td>");
					
					sb.append("<td class=\"sorting_1\">");
					if(COLABORADOR == null)
						sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleColaborador('").append(sesion.pkSorteo).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(PK_COLABORADOR).append("','").append(StringEscapeUtils.escapeHtml4(COLABORADOR)).append("')\">").append(StringEscapeUtils.escapeHtml4(COLABORADOR)).append("</a>").append("</td>");
					
				}
				
			}else {			
				
				sb.append("<tr> <td colspan=\"10\">").append("<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Boletos o Talonarios</h1><p>Empiece por asignar o realice otra busqueda.</p></div>").append("</td></tr>");
	
			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		sb
		.append("</tbody>")
		.append("</table>");

		String paginado = Factory.Paginado(numreg, show, pg);
		
		

		sb.insert(0, paginado);
		//html = paginado + html;
		

		return sb.toString();

	}
	*/
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
