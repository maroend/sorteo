package com.sorteo.reportes.controller;
/*
public class ReporteAccesos {

}
*/

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.core.Factory;
import com.core.MENU;
import com.core.SesionDatos;
import com.core.SuperModel.OFFSET;
import com.sorteo.reportes.model.mReporteAccesos;

/**
 * Servlet implementation class Compradores
 */
@WebServlet("/ReporteAccesos")
public class ReporteAccesos extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteAccesos() {
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
		mReporteAccesos model = new mReporteAccesos();

		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(20094)){view = "error";}
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String search;
	
		fullPath = "/WEB-INF/views/listsorteosuser.html";
		if (view == null) {
			view = "";

			if (request.getParameter("idsorteo") != null) {
				fullPath = "/WEB-INF/views/dashboard.html";
				view = "Dashboard";
			} else {
				view = "ListaSorteos";
			}
		}
		
		switch (view) {
		
		case "ExportExcel":
			search = request.getParameter("search");
			model.setFecha_ini(request.getParameter("fecha_ini"));
			model.setFecha_fin(request.getParameter("fecha_fin"));
			try {
				export(request, response, search, model, sesion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			model.close();

			return;
			
		case "Buscar":
			int pg = Integer.valueOf(request.getParameter("pg"));
			int show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			model.setFecha_ini(request.getParameter("fecha_ini"));
			model.setFecha_fin(request.getParameter("fecha_fin"));

			HTML = Buscar(request, pg, show, search, model, sesion);
			break;
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";			
			menu = vista.initMenu(0, false, 19, 15, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			
			break;		

		default:
			
			int idsorteo = sesion.pkSorteo;
			
			fullPath = "/WEB-INF/views/reportes/ReporteAccesos.html";
			menu = vista.initMenu(0, false, MENU.REPORTES, MENU.ACCESOS, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
			}

			model.setIdSorteo(sesion.pkSorteo);
            
			String dato = model.consultaClaveNombreSorteo(sesion.pkSorteo);
			
			HTML = HTML.replaceAll("#SORTEO#", dato);
			String boton_abono = "<a class=\"btn btn-success btn-block\" href=\"javascript:void(0)\" onClick=\"AbonarBoleto();\"><i class=\"fa fa-barcode\"></i> Abonar venta de boleto</a>";
			String boton_reporte = "<a class=\"btn btn-success btn-block\" href=\"javascript:void(0)\" onClick=\"ReportedeExtravioBoleto()\"><i class=\"fa fa-barcode\"></i> FC8 Reporte de extravio o Robo de Boleto</a>";
			HTML = HTML.replaceAll("#BOTON_ABONAR_VENTA_DE_BOLETO#", sesion.sorteoActivo ? boton_abono : "");
			HTML = HTML.replaceAll("#BOTON_REPORTE_DE_EXTRAVIO_O_ROBO#", sesion.sorteoActivo ? boton_reporte : "");
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	protected String Buscar(HttpServletRequest request,
			int pg, int show, String search, mReporteAccesos model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Clave","Nombre","Sector","Nicho","Talonarios elects.","Accesos"};
		String[] campos = { "CLAVE","NOM_COMPLETO","SECTOR","NICHO","TALS_ELEC","ACCESOS"};

        int numeroregistros = model.contar(search,sesion.pkSorteo);

        model.setIdSorteo(sesion.pkSorteo);
		
		return CrearTabla(numeroregistros, model.paginacion(pg, show, search, OFFSET.TRUE), columnas, campos, pg, show, sesion);
	}

	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		StringBuilder sb = new StringBuilder();
		sb
		.append("<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\" style='margin-right:10px;'>")
		.append("<thead>")
		.append(" <tr role=\"row\">")
		.append("  <th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");
		for (String columna : campostable) {
			sb.append(columna);
		}
		sb
		.append(" </th>")
		.append("</thead>")
		.append("<tbody>");

		try {

			if (Integer.valueOf(numreg) > 0) {

				for (int i = 1; rs.next(); i++) {

					sb
					.append("<tr class=\"gradeA odd\" role=\"row\">")
					.append("<td class=\"sorting_1\">").append(i).append("</td>");
					for (String campo : campos) {
						String value = rs.getString(campo);
						sb.append("<td class=\"sorting_1\">").append(value).append("</td>");
					}

					sb.append("</tr>");
				}

			} else {
				sb
				.append("<tr> <td colspan=\"11\"> ")
				.append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">")
				.append("<h1>No existen Compradores</h1>")
				.append("</div>")
				.append("</td></tr>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		sb.append("</tbody>");
		sb.append("</table>");

		String paginado = Factory.Paginado(numreg, show, pg);

		sb.insert(0, paginado);

		return sb.toString();
	}
	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, String search,
			mReporteAccesos model, SesionDatos sesion) throws ServletException, IOException, SQLException {
		
		model.setIdSorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=ReporteAccesos.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Accesos");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		
		cell = row.createCell(0);
		cell.setCellValue("Clave");
		cell = row.createCell(1);
		cell.setCellValue("Nombre");
		cell = row.createCell(2);
		cell.setCellValue("Sector");
		cell = row.createCell(3);
		cell.setCellValue("Nicho");
		cell = row.createCell(4);
		cell.setCellValue("Talonarios");
		cell = row.createCell(5);
		cell.setCellValue("Accesos");
		
		ResultSet rs = model.paginacion(0, 0, search, OFFSET.FALSE);
		
		int fila = 1;
		while (rs.next()) {
			row = sheet.createRow(fila);
			
			//CLAVE
			cell = row.createCell(0);
			cell.setCellValue(rs.getString("CLAVE"));

			//NOMBRE
			cell = row.createCell(1);
			cell.setCellValue(rs.getString("NOM_COMPLETO"));			

			//SECTOR
			cell = row.createCell(2);
			cell.setCellValue(rs.getString("SECTOR"));			

			//NICHO
			cell = row.createCell(3);
			cell.setCellValue(rs.getString("NICHO"));			

			//TALONARIOS
			cell = row.createCell(4);
			cell.setCellValue(rs.getString("TALS_ELEC"));			
			
			//ACCESOS
			cell = row.createCell(5);
			cell.setCellValue(rs.getString("ACCESOS"));
			
			fila++;
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);

		wb.close();

		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
	}
	
	
}
