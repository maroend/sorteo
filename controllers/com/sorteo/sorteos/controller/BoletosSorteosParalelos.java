package com.sorteo.sorteos.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.reportes.model.mReporteVentas;
import com.sorteo.sorteos.model.mBoletosSorteosParalelos;


/**
 * Servlet implementation class BoletosSorteosParalelos
 */
@WebServlet("/BoletosSorteosParalelos")
public class BoletosSorteosParalelos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoletosSorteosParalelos() {
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
		mBoletosSorteosParalelos model = new mBoletosSorteosParalelos();
		
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
		int idsorteoparalelo = 0;
		int idsorteo = 0;
		
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {

		/**********************/
		case "getBoletosColab_SorteosParalelos":
			 idsorteoparalelo = Integer.valueOf(request.getParameter("idsorteop"));
				// System.out.println("buscar: idsorteoparalelo: "+idsorteoparalelo);
			HTML = getBoletosSorteosColaborador(model, sesion, idsorteoparalelo);
			break;			
			
		case "exportToExcel":		
			
			try {	
				
				
				 idsorteoparalelo = Integer.valueOf(request.getParameter("idsorteop"));
				 System.out.println("ExportExcel: idsorteoparalelo: "+idsorteoparalelo);
				 export(request, response, model, sesion,idsorteoparalelo);	
				
				//HttpSession sesion2 = request.getSession();
				//sesion2.setAttribute("nombre_variable", "edgar"); 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		//	HTML = HTML.replaceAll("", "");	
			
			
			//break;
			return;	
				
			
		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
		
            
			int show = Integer.valueOf(request.getParameter("show"));
			
			 idsorteoparalelo = Integer.valueOf(request.getParameter("idsorteop"));
			// System.out.println("buscar: idsorteoparalelo: "+idsorteoparalelo);
			String search = request.getParameter("search");
			Buscar(request, response, pg, show, search, model, sesion,idsorteoparalelo);
			break;
			
		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		default:
			idsorteo = sesion.pkSorteo; //Integer.valueOf(request.getParameter("idsorteo"));
			
			fullPath = "/WEB-INF/views/sorteos/SorteosParalelos/BoletosSorteosParalelos.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			model.setIdSorteo(idsorteo);		
			
			if (sesion.pkSorteo != idsorteo || sesion.sorteoActivo != model.getSorteoActivo()) {
				sesion.pkSorteo = idsorteo;
				sesion.sorteoActivo = model.getSorteoActivo();
				sesion.guardaSesion();
			}
			
			System.out.println("Cambio a sorteo: "+idsorteo+"  ("+sesion.sorteoActivo+")");
			
			
		//**********************************************/
			//ID SORTEOPARALELO
			
			 idsorteoparalelo = Integer.valueOf(request.getParameter("idsorteop"));
			model.setIdSorteoP(idsorteoparalelo);	
			 model.SorteoParalelo(model);
		//	System.out.println("idsorteoparalelo: "+idsorteoparalelo);           
            
		
			
			HTML = HTML.replaceAll("#SORTEO#", model.getSorteo());
			
			HTML = HTML.replaceAll("#DISPLAY_MENU#", sesion.sorteoActivo ? "display" : "none");
			
			HTML = HTML.replaceAll("#BUTTONS_DISABLED#", sesion.sorteoActivo ? "" : "disabled");
			
			//actualiza el id de sorteo en la bd de usuario
			model.setIdUsuario(sesion.pkUsuario);
			model.setPredeterminadoSorteo();
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
		
		
		
		
	}
	
	

	protected String getBoletosSorteosColaborador(mBoletosSorteosParalelos model, SesionDatos sesion,int idsorteop) {

		String contenido = "";
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSorteoP(idsorteop);
		contenido = model.getBoletosSorteosColaborador();
		return contenido;

	}
	
	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, mBoletosSorteosParalelos model,
		 SesionDatos sesion,int idsorteop)
			throws ServletException, IOException, SQLException {
		
	//	model.setIdsorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Reporte_BoletosSorteoParalelo.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("REPORTE_SORTEOS_PARALELOS"); 
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);		
		cell.setCellValue("COLABORADOR");
		cell = row.createCell(1);
		cell.setCellValue("FOLIO");
		cell = row.createCell(2);
		cell.setCellValue("SECTOR");
		cell = row.createCell(3);
		cell.setCellValue("NICHO");
	
		
	//	System.out.println("antes sql controller");		
		
		ResultSet rs = model.exportSorteoParalelos(sesion,idsorteop);			
		
		int fila = 1;
		
		while (rs.next()) {
			row = sheet.createRow(fila);
			//System.out.println("entro excel");	
			
			//COLABORADOR		
			
			//COLABORADOR
			cell = row.createCell(0);
			cell.setCellValue(rs.getString("COLABORADOR"));
			
			//FOLIO
			cell = row.createCell(1);
			cell.setCellValue(rs.getString("FOLIOCEROS"));
			
			cell = row.createCell(2);
			cell.setCellValue(rs.getString("SECTOR"));
			
			//NICHO
			cell = row.createCell(3);
			cell.setCellValue(rs.getString("NICHO"));			
			
				
		
			fila++;
		}
		
		wb.close();
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);	
	

		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = response.getOutputStream();
	    outStream.write(outArray);
		outStream.flush();		
		
		
	}
	
		
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SesionDatos sesion;
		mBoletosSorteosParalelos model = new mBoletosSorteosParalelos();

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		
		
		
	}
	
	
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mBoletosSorteosParalelos model, SesionDatos sesion,int idsorteoparalelo)
			throws ServletException, IOException {

		String[] columnas = { "Nombre","Boleto","Sectores","Nicho"};
		String[] campos = { "COLABORADOR","FOLIO","SECTOR","NICHO" };

		int numeroregistros = model.contar(sesion.pkSorteo,search,idsorteoparalelo);

		
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search,sesion.pkSorteo,idsorteoparalelo), columnas, campos, pg, show, model, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
	}
	
	/**/
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, mBoletosSorteosParalelos model, SesionDatos sesion) {
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
		.append("<tr role=\"row\">");
		
		
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
			//if (rs != null) {
				
				while (rs.next()) {
	
					i++;
	
					sb
					.append("<tr class=\"gradeA odd\" role=\"row\"  >")				
					.append("<td class=\"sorting_1\">" + i + "</td>");
					
					
					
					// COLABORADOR
					sb.append("<td class=\"sorting_1\">");
					if (rs.getString("COLABORADOR") == null)
						sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleColaborador('").append(sesion.pkSorteo).append("','").append(rs.getString("PK_SECTOR")).append("','").append(rs.getString("PK_NICHO")).append("','").append(rs.getString("PK_COLABORADOR")).append("','").append(StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR"))).append("')\">").append(StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR"))).append("</a>");
					sb.append("</td>");					
					
					
					sb.append("<td class=\"sorting_1\"><span class=\"label label-primary\"  >").append(rs.getString("FOLIOCEROS")).append("</span>");
					
				
					sb.append("</td>");
					
				
				
					sb.append("<td class=\"sorting_1\">");
					if(rs.getString("SECTOR") == null)
						sb.append("N/A");
					else
						sb.append("<a>").append(StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))).append("</a>");
					sb.append("</td>");	
						
						//sb.append("<a href=\"javascript:ShowDetalleNicho('").append(sesion.pkSorteo).append("','").append(rs.getString("PK_SECTOR")).append("','").append(rs.getString("PK_NICHO")).append("','").append(rs.getString("NICHO")).append("')\">").append(StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))).append("</a>");
				
					
					
					
					sb.append("<td class=\"sorting_1\">");
					if(rs.getString("NICHO") == null)
						sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleNicho('").append(sesion.pkSorteo).append("','").append(rs.getString("PK_SECTOR")).append("','").append(rs.getString("PK_NICHO")).append("','").append(rs.getString("NICHO")).append("')\">").append(StringEscapeUtils.escapeHtml4(rs.getString("NICHO"))).append("</a>");
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

		sb.append("</tbody>");
		sb.append("</table>");

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

		//html = paginado + html;
		sb.insert(0, paginado);

		return sb.toString();

	}//*/

}
