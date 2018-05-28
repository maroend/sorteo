package com.sorteo.conciliacion.controller;

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
import com.core.Global;
import com.core.SesionDatos;
import com.sorteo.conciliacion.model.mDetalleConciliacion;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/DetalleConciliacion")
public class DetalleConciliacion extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetalleConciliacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mDetalleConciliacion model = new mDetalleConciliacion();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		//int idcolaborador = 0;
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		case "ExportExcel":
			
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
			
			int idSector = 0;
			if (request.getParameter("idsectorb") != null && request.getParameter("idsectorb") != "") {
				idSector = Integer.valueOf(request.getParameter("idsectorb"));}
			
			int idNicho = 0;
			if (request.getParameter("idnichob") != null && request.getParameter("idnichob") != "")
				idNicho = Integer.valueOf(request.getParameter("idnichob"));

			String filtroFecha = request.getParameter("filtroFecha");
			if (filtroFecha == null)
				filtroFecha = "";
			
			try {
				export(request, response, pg, show, search, idSector, idNicho, filtroFecha, model, sesion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case "BuscarDetalleColaborador":
			
		    //Integer idcolaborador = Integer.valueOf(request.getParameter("idcolaborador"));
			
			//BuscarDetalleColaborador(request, response, idcolaborador, model, sesion);
			break;
		
		
		case "Buscar":
			
			// pagina a mostrar
			pg = Global.valid(request.getParameter("pg"), 1);
			show = Global.valid(request.getParameter("show"), 10);
			search = Global.valid(request.getParameter("search"), "");
			idSector = Global.valid(request.getParameter("idsectorb"), 0);
			idNicho = Global.valid(request.getParameter("idnichob"), 0);
			filtroFecha = Global.decodeBase64(Global.valid(request.getParameter("filtroFecha"), ""));

			HTML = Buscar(request, response, pg, show, search, idSector, idNicho, filtroFecha, model, sesion);
			break;

		case "ListarFechas":
			search = "";
			idSector = 0;
			idNicho = 0;
			try{
				search = request.getParameter("search");
				idSector = Integer.valueOf(request.getParameter("idsectorb"));
				idNicho = Integer.valueOf(request.getParameter("idnichob"));
			} catch(Exception ex) { }

			ListarFechas(request, response, search, idSector, idNicho, model, sesion);
			break;

		case "ListarFechasModal":
			search = "";
			idSector = 0;
			idNicho = 0;
			try{
				search = request.getParameter("search");
				idSector = Integer.valueOf(request.getParameter("idsectorb"));
				idNicho = Integer.valueOf(request.getParameter("idnichob"));
			} catch(Exception ex) { }

			ListarFechasModal(request, response, search, model);
			break;
			
		case "Rollback":
			/*
			search = "";
			filtroFecha = "";
			idSector = 0;
			idNicho = 0;
			try{
				search = request.getParameter("search");
				filtroFecha = request.getParameter("filtroFecha");
				filtroFecha = (filtroFecha == null) ? "" : filtroFecha.trim();
				
				if (request.getParameter("idsectorb") != "")
					idSector = Integer.valueOf(request.getParameter("idsectorb"));
				if (request.getParameter("idnichob") != "")
					idNicho = Integer.valueOf(request.getParameter("idnichob"));
			}
			catch(Exception ex) { }
			model.setIdsorteo(sesion.pkSorteo);
			String msg = model.Rollback(idSector, idNicho, search, filtroFecha, sesion);
			
			HTML = "<span style='color:#0088cc;'>" + msg + "</span>";
			*/

			break;
		
		default:
			
			String fullPath = "/WEB-INF/views/conciliacion/DetalleConciliacion.html";
			menu = vista.initMenu(0, false, 10, 111, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: " + sesion.pkSorteo);
			System.out.println("id usuario: " + sesion.pkUsuario);
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#",
					(sesion.sorteoActivo)
					? "display" : "none");
			

			String boton;
			
			/*
			if (sesion.permisos.havePermiso(30122)) boton = "<a class='btn btn-sm btn-primary' href='ImportConciliacion'><i class='fa fa-plus m-r-5'></i> Importar</a>";
			else boton = "";
			HTML = HTML.replaceFirst("#BOTON_IMPORTAR#", boton);
			*/
			
			if (sesion.permisos.havePermiso(30123)) boton = "<a class='btn btn-sm btn-success' href='javascript:void(0)' onclick='ExportExcel();'><i class='fa fa-download m-r-5'></i> Exportar</a>";
			else boton = "";
			HTML = HTML.replaceFirst("#BOTON_EXPORTAR#", boton);
			
			if (sesion.permisos.havePermiso(30127)) boton = "<button class='btn btn-sm btn-primary' onclick='showHistorial()'>Historial</button>";
			else boton = "";
			HTML = HTML.replaceFirst("#BOTON_HISTORIAL#", boton);
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		out.flush();
		out.close();
		model.close();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mDetalleConciliacion model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected String Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int sector, int nicho, String filtroFecha, mDetalleConciliacion model, SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Folio", "Ref. Bancaria","Fecha del Banco", "Sucursal","Descripcion","Importe", "Referencia","Fecha Reg." };
		String[] campos   = { "PK_FOLIO", "REFBANCARIA", "FECHA_HORA", "SUCURSAL","DESCRIPCION","IMPORTE","REFERENCIA","FORMAT" };

		/*
		if (sesion.sorteoActivo == false)
			columnas = Arrays.copyOfRange(columnas,0,6);
		*/

		int numeroregistros = model.contar(search, filtroFecha);

		return
		//String HTML = 
				CrearTabla(numeroregistros,
				model.paginacion(pg, show, search, filtroFecha, true),
				columnas, campos, pg, show, sesion);
		//PrintWriter out = response.getWriter();
		//out.println(HTML);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna.replace(" ", "&nbsp;") + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" /></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";

		for (String columna : campostable) {

			html += columna;
		}

		html += " </thead>";
		html += " <tbody>";

		try {
			
			if (Integer.valueOf(numreg) > 0)
			{
				//NumberFormat formatter = new DecimalFormat("#0.0000");
				
				while (rs.next()) {
	
					i++;
	
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					//html += "<td class=\"sorting_1\"><input type=\"checkbox\" /></td>";
					html += "<td class=\"sorting_1\">" + i + "</td>";
					for (String campo : campos)
					{
						String str_campo = rs.getString(campo);
						
						if (campo=="IMPORTE"){
							if (str_campo==null)
								str_campo = "<b>$0.00</b>";
							else
								str_campo = "<b>$"+str_campo+"</b>";
							//str_campo = formatter.format(rs.getDouble(campo));
						}
						if (campo=="FECHA_HORA")
							str_campo = formatFechaHora(str_campo);
						
						html += "<td class=\"sorting_1\">";
						if (str_campo==null) { html += "N/A"; }
						else { html += str_campo.replaceAll(" ", "&nbsp;"); }
						html += "</td>";
					}
					
						
					//ABONO
					/*html += "<td class=\"sorting_1\">";
					html += "<b>$"+rs.getString("ABONO")+"</b>";
					html += "</td>";*/
					
	
					/*
					if (sesion.sorteoActivo)
					{
						html += "<td class=\"sorting_1\">";
						html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"
		
								+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
								+ "<li><a href=\"javascript:Abono("+ rs.getString("PK_COLABORADOR") + ");\">Importe</a></li>"
								+ "<li><a href=\"javascript:ModalEliminarAbono(" + rs.getString("PK_COLABORADOR")	+ ");\">Eliminar</a></li>"
								+ "</ul></div>";
		
						html += "</td>";
					}
					//*/
				
					html += "</tr>";
	
				}
			} else {

				html += "<tr class=\"gradeA odd\" role=\"row\"><td colspan=\"12\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

				html += "<h1>No existen registros</h1>";
				html += "<p>Empiece importando un archivo.</p>";

				html += "</div>";

				html += "</td>";
				html += "</tr>";

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		html += "</tbody>";
		html += "</table>";
		
		String paginado = Factory.Paginado(numreg, show, pg);

		
		html = paginado + html;

		return html;

	}
	
	protected void ListarFechas(HttpServletRequest request,
			HttpServletResponse response, String search,
			int sector, int nicho, mDetalleConciliacion model, SesionDatos sesion) throws ServletException, IOException {

		ResultSet rs = model.paginacionFechas(search);
		String HTML = "";
		try {
			boolean first = true;
			HTML += "<option value=''>Todos</option>\n";
			while (rs.next()) {
				HTML += "<option value='"+rs.getString("FECHA_R")+"'>" + rs.getString("FORMAT") + (first?" &nbsp;&nbsp;&nbsp;(Mas reciente)":"")+"</option>\n";
				
				first=false;
			}
		} catch (Exception ex) { }
		
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}
	
	protected void ListarFechasModal(HttpServletRequest request,
			HttpServletResponse response, String search,
			mDetalleConciliacion model) throws ServletException, IOException {

		String HTML = CrearTablaConciliacionModal(model.paginacionFechas(search));
		
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}
	
	protected String CrearTablaConciliacionModal(ResultSet rs){
		// TODO CrearTablaConciliacionModal
		
		String HTML = "";
		try {
			int counter=1;
			HTML += "<tr><th>  No.  </th><th>  Fecha de registro  </th><th>  N&uacute;mero de registros  </th><th>  Ventas  </th><th>  CF4  </th><th>  Acci&oacute;n &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </th></tr>";
			while (rs.next()) {
				String td_id = "td_id_" + counter;
				HTML += "<tr>\n";
				HTML += "<td>  "
						+ counter
						+ "  </td><td>  "
						+ rs.getString("FORMAT")
						+ (counter == 1 ? "&nbsp;&nbsp;&nbsp;(Mas Reciente)" : "")
						+ "  </td>"
						+ "<td>  " + rs.getString("RECORDS") + "  </td>"
						+ "<td>  " + rs.getString("VENTAS") + "  </td>"
						+ "<td>  " + rs.getString("RETORNOS") + "  </td>"
						+ "<td style='width:200pt;' id='"
						+ td_id
						+ "'> <button id='btnBorrar_"
						+ counter
						+ "' class='btn btn-sm "
						+ (counter == 1 ? "btn-warning" : "btn-white disabled")
						+ "' onclick=\"BorrarConciliacion('"
						+ rs.getString("FECHA_R") + "','" + td_id
						+ "','btnBorrar_" + (counter + 1)
						+ "')\" >Borrar</button> </td>\n";
				HTML += "</tr>\n";
				counter++;
			}
		} catch (Exception ex) { }
		return HTML;
	}
	
	private String formatFechaHora(String fecha_hora){
		try{
			String dia = fecha_hora.substring(0, 2);
			String mes = fecha_hora.substring(2, 4);
			String anio = fecha_hora.substring(4, 8);
			String hora = fecha_hora.substring(8, 10);
			String minuto = fecha_hora.substring(10, 12);
			return dia + "/" + mes + "/" + anio + ", " + hora + ":" + minuto;
		}catch(Exception ex){}
		return fecha_hora;
	}

	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int sector, int nicho, String filtroFecha, mDetalleConciliacion model, SesionDatos sesion) throws ServletException, IOException, SQLException {
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Depositos.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Lista Depositos");
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("Cuenta");
		cell = row.createCell(1);
		cell.setCellValue("Fecha");
		cell = row.createCell(2);
		cell.setCellValue("Hora");
		cell = row.createCell(3);
		cell.setCellValue("Suc");
		cell = row.createCell(4);
		cell.setCellValue("Descripcion");
		cell = row.createCell(5);
		cell.setCellValue("Cargo/Abono");
		cell = row.createCell(6);
		cell.setCellValue("Importe");
		cell = row.createCell(7);
		cell.setCellValue("Saldo");
		cell = row.createCell(8);
		cell.setCellValue("Referencia");
		cell = row.createCell(9);
		cell.setCellValue("Concepto / Referencia Interbancaria");
		
		
		ResultSet rs = model.paginacion(pg, show, search, filtroFecha, true);
		
		int fila = 1;
		/*
		String concepto = null;
		double comision = 0;
		double monto = 0;
		double abono = 0;
		double saldo = 0;
		//*/
		while (rs.next()) {
			row = sheet.createRow(fila);
				
			
			    
				//CUENTA
				cell = row.createCell(0);
				cell.setCellValue(rs.getString("CUENTA"));
				
				//FECHA
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("FECHA"));			
				
				//HORA
				cell = row.createCell(2);
				cell.setCellValue(rs.getString("HORA"));
				
				//SUCURSAL
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("SUCURSAL"));	
				
				//DESCRIPCION
				cell = row.createCell(4);
				cell.setCellValue(rs.getString("DESCRIPCION"));
				

				//CARGO
				cell = row.createCell(5);
				cell.setCellValue(rs.getString("CARGO"));
				
				
				
				//IMPORTE
				cell = row.createCell(6);
				cell.setCellValue(rs.getDouble("IMPORTE"));
				
				
				//SALDO
				cell = row.createCell(7);
				cell.setCellValue(rs.getDouble("SALDO"));
				
				//REFERENCIA
				cell = row.createCell(8);
				cell.setCellValue(rs.getString("REFERENCIA"));
				
				
				//REFERENCIA
				cell = row.createCell(9);
				cell.setCellValue(rs.getString("CONCEPTO"));

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
