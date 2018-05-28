package com.sorteo.talonarios.controller;

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

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.talonarios.model.mSeguimientoTalonarios;

/**
 * Servlet implementation class BoletosSorteo
 */
@WebServlet("/SeguimientoTalonarios")
public class SeguimientoTalonarios extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeguimientoTalonarios() {
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
		mSeguimientoTalonarios model = new mSeguimientoTalonarios();
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		System.out.println("************* SeguimientoTalonarios *************");
		
		String view = request.getParameter("view");
		
		//10119 ACCESO A LA LISTA DE USUARIOS RESPONSABLES 10169
		 if (!sesion.permisos.havePermiso(10119)){view = "error";}	
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";

		String p_menu = request.getParameter("menu");

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
			boolean guardar=false;
			String str_idsorteo;
			String str_idtalonario;

			if ((str_idsorteo = request.getParameter("idsorteo")) != null) {
				int idsorteo = Integer.valueOf(str_idsorteo);
				if (sesion.pkSorteo != idsorteo) {
					sesion.pkSorteo = idsorteo;
					guardar = true;
				}
			}
			int idtalonario = 0;
			if ((str_idtalonario = request.getParameter("idtalonario")) != null) {
				idtalonario = Integer.valueOf(str_idtalonario);
				/*if (sesion.pkTalonario != idtalonario) {
					sesion.pkTalonario = idtalonario;
					guardar = true;
				}*/
			}

			if(guardar)
				sesion.guardaSesion();
			
			fullPath = "/WEB-INF/views/boveda/SeguimientoTalonarios/seguimiento_talonarios.html";
			if (p_menu != null)
				menu = vista.initMenu(0, false, Integer.valueOf(p_menu), sesion.misSorteos==0 ? 34 : 33, sesion);
			else
				menu = vista.initMenu(0, false, 7, 8, sesion);

			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			String folio = model.folioTalonario(idtalonario);
			HTML = HTML.replaceFirst("#FOLIO#", folio);

			System.out.println("idSorteo: "+sesion.pkSorteo);
			
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mSeguimientoTalonarios model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Estado","Costo","Abono","boletos","Formato","Fecha","Acci&oacute;n","Detalle"};
		String[] campos = { "ESTATUS","COSTO","ABONO","NUMERO_BOLETOS","FORMATO","FECHA_R","ACCION","DETALLE" };
		//SEG.PK1,SEG.PK_SORTEO,SEG.PK_BOLETO,T.FOLIO,SEG.PK_TALONARIO,SEG.ACCION,SEG.STATUS,SEG.COSTO,SEG.ABONO,SEG.NUMERO_BOLETOS,SEG.FECHA_R,DESCRIPCION

		model.setIdSorteo(sesion.pkSorteo);
		//model.setIdtalonario(sesion.pkTalonario);
		//model.setFolio(search);
		int numeroregistros = model.contar(search);

		
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search), columnas, campos, pg, show, model);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, mSeguimientoTalonarios model) {

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

		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" /></th>";
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

				//html += "<td class=\"sorting_1\"><input type=\"checkbox\" /></td>";
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
					if (campo.equals("DETALLE")) {
						String nivel_nombre = "";
						String nivel = rs.getString("NIVEL").trim();

						if (nivel == null) nivel_nombre="-";
						else if (nivel.equals("Boveda")) nivel_nombre = "Boveda: " + model.Sorteo(rs.getInt("PK_SORTEO"));
						else if (nivel.equals("Sorteo")) nivel_nombre = "Sorteo: " + model.Sorteo(rs.getInt("PK_SORTEO"));
						else if (nivel.equals("Sector")) nivel_nombre = "Sector: " + model.Sector(rs.getInt("PK_SECTOR"));
						else if (nivel.equals("Nicho")) nivel_nombre = "Nicho: " + model.Nicho(rs.getInt("PK_NICHO"));
						else if (nivel.equals("Colaborador")) nivel_nombre = "Colaborador: " + model.Colaborador(rs.getInt("PK_COLABORADOR"));
						else nivel_nombre = "";

						if(rs.getString(campo)==null){
							html += "<td class=\"sorting_1\">NA</td>";
						}else{
							html += "<td class=\"sorting_1\" style=\"width:300pt;\">" + nivel_nombre + "</td>";
						}
					}
					else if (campo.equals("FECHA_R")) {
						String fecha = rs.getString(campo);
						int index = fecha.lastIndexOf(":");
						fecha = fecha.substring(0,index);
						if(rs.getString(campo)==null){
							html += "<td class=\"sorting_1\">NA</td>";
						}else{
							html += "<td class=\"sorting_1\" style=\"width:160pt;\">" + fecha + "</td>";
						}
					}
					else if (campo.equals("ESTATUS")) {
						String estado = rs.getString(campo);

						if(estado == null);
						
						else if (estado.equals("N")) estado = "<span class=\"badge badge-inverse badge-square\">No&#160;vendido</span>";
						else if (estado.equals("P")) estado = "<span class=\"badge badge-default badge-square\">Abono</span>";
						else if (estado.equals("V")) estado = "<span class=\"badge badge-success badge-square\">Vendido</span>";
						else if (estado.equals("R")) estado = "<span class=\"badge badge-danger badge-square\">Robado</span>";
						else if (estado.equals("E")) estado = "<span class=\"badge badge-warning badge-square\">Extraviado</span>";

						if(estado==null){
							html += "<td class=\"sorting_1\">NA</td>";
						}else{
							html += "<td class=\"sorting_1\">" + estado + "</td>";
						}
					}
					else {
						if(rs.getString(campo)==null){
							html += "<td class=\"sorting_1\">NA</td>";
						}else{
							html += "<td class=\"sorting_1\">" + rs.getString(campo) + "</td>";
						}
					}
				}		
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

		paginado += "</div>";

		html = paginado + html;

		return html;

	}
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
