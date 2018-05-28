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
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mFormatosNichos;

/**
 * Servlet implementation class BoletosSorteosSectores
 */
@WebServlet("/FormatosNichos")
public class FormatosNichos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormatosNichos() {
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
		mFormatosNichos model = new mFormatosNichos();
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		
		//10176 ACCESO A LA LISTA DE FORMATOS		
		 if (!sesion.permisos.havePermiso(10176)){view = "error";}
		
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

		case "Agregar":
			
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
			
			int idnicho = Integer.valueOf(request.getParameter("idnicho"));
			
			fullPath = "/WEB-INF/views/sorteos/formatos/FormatosNichos.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 34, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			if (sesion.pkNicho != idnicho) {
				sesion.pkNicho = idnicho;
				sesion.guardaSesion();
			}

			System.out.println("idSorteo: "+sesion.pkSorteo);
			System.out.println("idSector: "+sesion.pkSector);	
			System.out.println("idNicho: "+sesion.pkNicho);
			//String dato = null;
			
			//String regex = "";
			
            model.setIdSorteo(sesion.pkSorteo);				
			model.setIdsector(sesion.pkSector);	 
			model.setIdnicho(sesion.pkNicho);	
			
			model.obtenerNicho(model);	
			
			String nicho = model.getNicho();
			
			HTML = HTML.replaceAll( "#NICHO#" , nicho );
			
			
			
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		
	}
	
	
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mFormatosNichos model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Folio","Id boleto","Id Sector","id Nicho","Estado","Usuario","Fecha Registro"};
		String[] campos = { "FOLIO","PK_BOLETO","PK_SECTOR","PK_NICHO","PK_ESTADO","USUARIO","FECHA_R" };

		int numeroregistros = model.contar(sesion.pkSorteo,sesion.pkSector,sesion.pkNicho);

		
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search,sesion.pkSorteo,sesion.pkSector,sesion.pkNicho), columnas, campos, pg, show);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show) {

		int i = (pg - 1) * show;
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		double monto = 0;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 12px;\" ></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}
		
	
		html += " </thead>";
		html += " <tbody>";

		try {
			while (rs.next()) {

				i++;
				
				
				html += "<tr class=\"gradeA odd\" role=\"row\">";
				
		
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
                     
                  /* if(campo.equals("MONTO")){						
						
						monto = Double.parseDouble(rs.getString(campo));						
						html += "<td class=\"sorting_1\">"+ englishFormat.format(monto) +"</td>";
						
					}else{*/
						
						html += "<td class=\"sorting_1\">" + rs.getString(campo) + "</td>";
						
					//}
					

				}		

				//html += "<td class=\"sorting_1\"><a>Ver</a></td>";
				//html += "<td class=\"sorting_1\"><a>Ver</a></td>";

			}
		} catch (SQLException e) {
			Factory.Error(e, "rs="+rs);
		}

		html += "</tbody>";
		html += "</table>";

		/*int numpag = Math.round(numreg / show);
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

		paginado += "</div>";*/
		String paginado = Factory.Paginado(numreg, show, pg);

		html = paginado + html;

		return html;

	}
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
