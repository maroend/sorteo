package com.sorteo.herramientas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.core.Factory;
import com.core.Global;
import com.sorteo.herramientas.model.mBoletosSorteoEstres;


/**
 * Servlet implementation class BoletosSorteo
 */
@WebServlet("/BoletosSorteoEstres")
public class BoletosSorteoEstres extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoletosSorteoEstres() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mBoletosSorteoEstres model = new mBoletosSorteoEstres();
		
		Factory.prepareError(request);
		
		String view = request.getParameter("view");
		String HTML = "";
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
			
		case "Buscar":
			model.setFiltrovendidos(0);
            model.setFiltronovendidos(0);
            model.setFiltroparciales(0);
            model.setFiltrorobados(0);
            model.setFiltroextraviados(0);
            
			int pg = Integer.valueOf(request.getParameter("pg"));
			int show = Integer.valueOf(request.getParameter("show"));
			Buscar(request, response, pg, show, "", model);
			break;
		
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mBoletosSorteoEstres model)
			throws ServletException, IOException {

		String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Sector","Nicho","Colaborador" };
		String[] campos = { "FOLIO","VENDIDO","COSTO","ABONO","TALONARIO","SECTOR","NICHO","COLABORADOR" };

		int numeroregistros = model.contar(search);

		
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search), columnas, campos, pg, show, model);
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, mBoletosSorteoEstres model) {
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
		.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" >");

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
					String PK_SECTOR = rs.getString("PK_SECTOR");
					String PK_NICHO = rs.getString("PK_NICHO");
					String PK_COLABORADOR = rs.getString("PK_COLABORADOR");
					String SECTOR = rs.getString("SECTOR");
					String NICHO = rs.getString("NICHO");
					String COLABORADOR = rs.getString("COLABORADOR");

					i++;

					sb
					.append("<tr class=\"gradeA odd\" role=\"row\"  >")
					.append("<td class=\"sorting_1\">");
					
					if(rs.getInt("RETORNADO")==1){	
						sb.append("<i class=\"fa fa-2x fa-check-circle\"></i>");
					}else{
						sb.append("<input  id=").append(rs.getInt("PK1")).append("  class=\"boletoschecked\"   type=\"checkbox\" />");	
					}
					sb
					.append("</td>")
					.append("<td class=\"sorting_1\">" + i + "</td>")
					.append("<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('").append(2).append("','").append(rs.getString("PK_BOLETO")).append("','").append(rs.getString("FOLIO")).append("','").append(rs.getString("PK_TALONARIO")).append("','").append(rs.getString("TALONARIO")).append("','").append(rs.getString("ABONO")).append("','").append(rs.getString("COSTO")).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(PK_COLABORADOR).append("','-H-')\">");
					
					
					if(rs.getString("INCIDENCIA").equals("N")){ sb.append("<span class=\"label label-primary\" >").append(rs.getString("FOLIO")).append("</span>"); }
					if(rs.getString("INCIDENCIA").equals("E")){ sb.append("<span class=\"label label-warning\" >").append(rs.getString("FOLIO")).append("</span>"); }
					if(rs.getString("INCIDENCIA").equals("R")){ sb.append("<span class=\"label label-danger\"  >").append(rs.getString("FOLIO")).append("</span>"); }
					
					sb.append("</a></td>");
					
					if(rs.getString("VENDIDO").equals("N")){  estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";}
					if(rs.getString("VENDIDO").equals("V")){  estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";}
					if(rs.getString("VENDIDO").equals("P")){  estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";}
					
					sb.append("<td class=\"sorting_1\">").append(estado).append("</td>");
					
					sb.append("<td class=\"sorting_1\">").append(rs.getString("COSTO")).append("</td>");
					
					sb.append("<td class=\"sorting_1\">" + rs.getString("ABONO")).append("</td>");
					
					System.out.print(",VENDIDOTALONARIO="+rs.getString("VENDIDOTALONARIO"));
					System.out.print(",TALONARIO="+rs.getString("TALONARIO"));
					if("N".equals(rs.getString("VENDIDOTALONARIO"))){  estadotalonario = "<span class=\"label label-inverse\" >"+rs.getString("TALONARIO")+"</span>";}
					if("V".equals(rs.getString("VENDIDOTALONARIO"))){  estadotalonario = "<span class=\"label label-success\" >"+rs.getString("TALONARIO")+"</span>";}
					if("P".equals(rs.getString("VENDIDOTALONARIO"))){  estadotalonario = "<span class=\"label label-default\" >"+rs.getString("TALONARIO")+"</span>";}
					
					
					// DIGITAL 
					electronico = (rs.getString("DIGITAL").compareTo("1") == 0)
							? "<i class='fa fa-credit-card'></i>"
							: "<i class='fa fa-pencil'></i>";
					
					// TALONARIO
					sb.append("<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('").append(2+"','").append(rs.getString("PK_TALONARIO")).append("','").append(rs.getString("TALONARIO")).append("')\">").append(estadotalonario).append(" </a>&nbsp;").append(electronico).append("</td>");
					
					sb.append("<td class=\"sorting_1\">");
					if(SECTOR == null)
						sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleSector('").append(2).append("','").append(PK_SECTOR).append("','").append(StringEscapeUtils.escapeHtml4(SECTOR)).append("')\">").append(Global.cut(StringEscapeUtils.escapeHtml4(SECTOR))).append("</a>");
					sb.append("</td>");
					
					sb.append("<td class=\"sorting_1\">");
					if(NICHO == null)
						sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleNicho('").append(2).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(NICHO).append("')\">").append(StringEscapeUtils.escapeHtml4(NICHO)).append("</a>");
					sb.append("</td>");
					
					// COLABORADOR
					sb.append("<td class=\"sorting_1\">");
					if (COLABORADOR == null)
						sb.append("N/A");
					else
						sb.append("<a href=\"javascript:ShowDetalleColaborador('").append(2).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(PK_COLABORADOR).append("','").append(StringEscapeUtils.escapeHtml4(COLABORADOR)).append("')\">").append(Global.cut(StringEscapeUtils.escapeHtml4(COLABORADOR))).append("</a>");
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

		sb.insert(0, paginado);

		return sb.toString();

	}

}
