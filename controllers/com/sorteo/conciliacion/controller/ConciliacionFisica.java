package com.sorteo.conciliacion.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
import com.core.MENU;
import com.core.SesionDatos;
import com.core.SuperModel.OFFSET;
import com.sorteo.conciliacion.model.mConciliacionFisica;
import com.sorteo.poblacion.model.mNichos;
import com.sorteo.poblacion.model.mSectores;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/ConciliacionFisica")
public class ConciliacionFisica extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConciliacionFisica() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mConciliacionFisica model = new mConciliacionFisica();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		String fullPath ="";		
	
		
	//	fullPath = "/WEB-INF/views/listsorteosuser.html";
		
		String view = request.getParameter("view");
		if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){view = "errorCerrado";}
		
		
		if (view == null) {
			view = "";
			/*if(request.getParameter("sorteo")!=null||){
				  fullPath = "/WEB-INF/views/dashboard.html";
				  view = "Dashboard";
				  
				 }else{
				  view = "listsorteos";
				 }*/
		}
		
		switch (view) {
		
		
		 case "listsorteos":
	            fullPath = "/WEB-INF/views/listsorteosuser.html";
	            menu = vista.initMenu(0, false, 10, 111, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
				
				HTML = vista.getSorteosUsuarios(request,response,HTML, sesion);
				break; 
		
		
		case "errorCerrado":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 10, 111, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,
					sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			break;
		
		case "ExportExcel":
			
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
			
			int sector = 0;
			if(request.getParameter("idsectorb")!=null&&request.getParameter("idsectorb")!=""){
				sector = Integer.valueOf(request.getParameter("idsectorb"));}
			
			int nicho = 0;
			if(request.getParameter("idnichob")!=null&&request.getParameter("idnichob")!="")
				nicho = Integer.valueOf(request.getParameter("idnichob"));
			
			try {
				export(request, response, pg, show, search, sector, nicho, model, sesion);
				
				model.close();
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
		
		case "Buscar":
			pg = Integer.valueOf(request.getParameter("pg"));
			show = Integer.valueOf(request.getParameter("show"));
			search = Global.decodeBase64(request.getParameter("search"));
			model.setIdSector(Integer.valueOf(request.getParameter("idsectorb")));
			model.setIdNicho(Integer.valueOf(request.getParameter("idnichob")));
			
			HTML = Buscar(request, response, pg, show, search, model, sesion);
			break;

			/*
		case "BuscarSector":
			BuscarSector(request, response, model, sesion);
			break;

		case "buscarNichosSector":
			buscarNichosSector(request, response, model);
			break;
			*/
		case "BuscarSectorInicio":			
			BuscarSectorInicio(request, response, model, sesion);
			break;		
		
		case "buscarNichosSectorInicio":
			buscarNichosSectorInicio(request, response, model);
			break;
			
		case "actualizarAbono":
			actualizarAbono(request, response, model);
			break;
			
		default:
			
			fullPath = "/WEB-INF/views/conciliacion/ConciliacionFisica.html";
			menu = vista.initMenu(0, false, MENU.VENTAS, MENU.CONCILIACIÓN, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			model.setIdSorteo((int)sesion.pkSorteo);
			model.setIdusuario((int)sesion.pkUsuario);
			
			model.getSectorUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getIdSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( (int)sesion.pkUsuario));
			
			HTML = HTML.replaceFirst("__DISPLAY_BUTTON_IMPORT__", sesion.permisos.havePermiso(30122) ? "display" : "none");
			HTML = HTML.replaceFirst("__DISPLAY_BUTTON_EXPORT__", sesion.permisos.havePermiso(30123) ? "display" : "none");
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	protected String formatDateForSQL(String str_fecha){
		String[] array = str_fecha.split("/");
		try{
			if (Integer.valueOf(array[0])>0 &&
				Integer.valueOf(array[1])>0 &&
				Integer.valueOf(array[2])>2000)
				return array[2] + "-" + array[1] + "-" + array[0];
		}catch(Exception ex){}
		return null;
	}	
	
	protected void actualizarAbono(HttpServletRequest request,
			HttpServletResponse response, mConciliacionFisica model)
			throws ServletException, IOException {

		model.setId(Integer.valueOf(request.getParameter("idcolaborador")) );		
		model.setComision(Integer.valueOf(request.getParameter("abono")));			
		model.actualizarAbono(model);
	}

	// TODO DoPost
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		mConciliacionFisica model;
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		model.close();
		*/
	}

	/*
	protected void BuscarSector(HttpServletRequest request,
			HttpServletResponse response, mConciliacionFisica model, SesionDatos sesion) throws ServletException, IOException {
		
		   ResultSet rs = null;
		   String panelcontent = "";
		
		   model.setIdsorteo(sesion.pkSorteo);
		   model.setIdusuario(Integer.valueOf( request.getParameter("usuario")));        
		   model.setIdSector(Integer.parseInt(request.getParameter("idsectorU"))); 		
		   
		   
		if(model.isAdministrador()){
			
			panelcontent += "<option value=\"S\">Seleccionar Sector</option> ";

			 rs = model.getSectores();			
			
		} else{
			
			 rs = model.getSectoresUsuario(model);				
			
		}  		

		try {
			

			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("SECTOR") + "</option>";

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	*/
	
	protected void BuscarSectorInicio(HttpServletRequest request,
			HttpServletResponse response, mConciliacionFisica model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String panelcontent = "";
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdusuario(Integer.valueOf(request.getParameter("usuario")));
		model.setIdSector(Integer.parseInt(request.getParameter("idsectorU")));
		
		if (model.isAdministrador()){
			
			panelcontent += "<option value=\"0\">Todos</option> ";
			rs = model.getSectores();			
		} else{
			rs = model.getSectoresUsuario(model);
		}
		
		try {
			while (rs.next()) {
				
				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";
				panelcontent += ">" + rs.getString("SECTOR") + "</option>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);
		
	}
	/*
	protected void buscarNichosSector(HttpServletRequest request,
			HttpServletResponse response, mConciliacionFisica model) throws ServletException, IOException {

		model.setIdSector(Integer.valueOf(request.getParameter("idsector")));

		ResultSet rs = model.getNichos(Integer.valueOf(request
				.getParameter("idsector")));

		String panelcontent = "";

		try {
			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("NICHO") + "</option>";

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	*/
	
	protected void buscarNichosSectorInicio(HttpServletRequest request,
			HttpServletResponse response, mConciliacionFisica model) throws ServletException, IOException {

		model.setIdSector(Integer.valueOf(request.getParameter("idsector")));

		ResultSet rs = model.getNichos(Integer.valueOf(request.getParameter("idsector")));

		String panelcontent = "";

		try {
			
			panelcontent += "<option value=\"0\">Todos</option> ";
			
			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("NICHO") + "</option>";

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);
	}
	
	
	// TODO Buscar
	protected String Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mConciliacionFisica model, SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Clave", "Nombre", "Ref. Bancaria","MONTO","COMISION","ABONO","SALDO", "Controles"};
		String[] campos   = { "CLAVE", "NOM_COLABORADOR", "REFBANCARIA","MONTO" };

		if (sesion.sorteoActivo == false)
			columnas = Arrays.copyOfRange(columnas,0,6);

		model.setIdSorteo(sesion.pkSorteo);
		model.setIdusuario(sesion.pkUsuario);
		int numeroregistros = model.contar(search, sesion);

		return CrearTabla(numeroregistros,
				model.paginacion(pg, show, search, sesion, OFFSET.TRUE),
				columnas, campos, pg, show, sesion);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">");
		sb.append("<thead>");
		sb.append("<tr role=\"row\">");

		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" /></th>";
		sb.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");

		for (String columna : campostable) {

			sb.append(columna);
		}

		sb.append(" </thead>");
		sb.append(" <tbody>");

		try {
			
			if (Integer.valueOf(numreg) > 0)
			{
				DecimalFormat formatter = new DecimalFormat("#,###.00");
				//NumberFormat formatter = new DecimalFormat("#0.0000");
				
				while (rs.next()) {
	
					i++;
	
					sb.append("<tr class=\"gradeA odd\" role=\"row\">");
					//html += "<td class=\"sorting_1\"><input type=\"checkbox\" /></td>";
					sb.append("<td class=\"sorting_1\">").append(i).append("</td>");
					
					
					for (String campo : campos)
					{
						String value = rs.getString(campo);
						if (campo=="NOM_COLABORADOR")
							value = value.replaceAll(" ", "&nbsp;");
						if (campo=="MONTO"){
							if (value==null) {
								value = "<b>$0.00</b>"; }
							else{	
								value = "<b>$"+value+"</b>";
							}
							//str_campo = formatter.format(rs.getDouble(campo));
						}
						
						sb.append("<td class=\"sorting_1\">");
						if (value==null) { sb.append("N/A"); }
						else { sb.append(value); }
						sb.append("</td>");
					}
					
					
					//COMISION
					sb.append("<td class=\"sorting_1\">");
					double comision = rs.getDouble("COMISION");
					double monto = rs.getDouble("MONTO");
					comision = (monto * comision) / 100;
					comision = Math.round(comision);
					sb.append("<b>$").append(comision).append("</b>");
					sb.append("</td>");
					
					
					//ABONO
					sb
					.append("<td class=\"sorting_1\">")
					.append("<b>$").append(rs.getDouble("ABONO")).append("</b>")
					.append("</td>");
					
					
					//SALDO
					sb
					.append("<td class=\"sorting_1\">");
					/*double abono = rs.getDouble("ABONO");
					double saldo = (monto - comision)-abono;
					sb.append("<b>$").append(saldo).append("</b>");*/
					sb.append("<b>$").append(rs.getDouble("SALDO")).append("</b>");
					sb.append("</td>");
	
					if (sesion.sorteoActivo)
					{
						sb
						.append("<td class=\"sorting_1\">")
						.append("<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n")
		
						.append( "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">");	
								
									              
						if (sesion.permisos.havePermiso(30116)){
							sb.append("<li><a href=\"javascript:Abono(").append(rs.getString("PK1")).append(");\">Abono</a></li>");
						}
						
						sb.append("<li><a href='#' onclick=\"javascript:BuscarSeguimientoAbonos(").append(rs.getString("PK1")).append(");\">Detalle</a></li>")
							.append("</ul></div>")
		
						.append("</td>");
					}
				
					sb.append("</tr>");
	
				}
			} else {

				sb
				.append("<tr class=\"gradeA odd\" role=\"row\"><td colspan=\"11\">")

				.append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">")

				.append("<h1>No existen registros</h1>")
				.append("<p>Empiece importando un archivo.</p>")

				.append("</div>")

				.append("</td>")
				.append("</tr>");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sb
		.append("</tbody>")
		.append("</table>");

		
		String paginado = Factory.Paginado(numreg, show, pg);

		sb.insert(0, paginado);

		return sb.toString();

	}	
	
	// TODO export
	protected void export(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int sector, int nicho, mConciliacionFisica model, SesionDatos sesion) throws ServletException, IOException, SQLException {
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Conciliacion.xls");
		
		HashMap<Long, mSectores> sectores = mSectores.getMapSectores(sesion.pkSorteo, model);
		HashMap<Long, mNichos> nichos = mNichos.getMapNichos(sesion.pkSorteo, sector, model);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Lista Colaboradores");
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("No.");
		cell = row.createCell(1);
		cell.setCellValue("Clave");
		cell = row.createCell(2);
		cell.setCellValue("Nombre");
		cell = row.createCell(3);
		cell.setCellValue("Sector");
		cell = row.createCell(4);
		cell.setCellValue("Nicho");
		cell = row.createCell(5);
		cell.setCellValue("Ref. Bancaria");
		cell = row.createCell(6);
		cell.setCellValue("MONTO");
		cell = row.createCell(7);
		cell.setCellValue("COMISION");
		cell = row.createCell(8);
		cell.setCellValue("ABONO");
		cell = row.createCell(9);
		cell.setCellValue("SALDO");
		
		
		ResultSet rs = model.paginacion_excel(search, sector, nicho, sesion);
		
		int fila = 1;
		String concepto = null;
		double comision = 0;
		double monto = 0;
		double abono = 0;
		double saldo = 0;
		
		while (rs.next()) {
			row = sheet.createRow(fila);
			
			
				
			//No
				cell = row.createCell(0);
				cell.setCellValue(fila);
			//CLAVE
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("CLAVE"));
				//NOMBRE
				cell = row.createCell(2);
				cell.setCellValue(rs.getString("NOM_COLABORADOR"));			
				
				//SECTOR
				cell = row.createCell(3);
				//cell.setCellValue(rs.getString("NOM_SECTOR"));
				mSectores _sector = sectores.get(rs.getLong("PK_SECTOR"));
				cell.setCellValue(_sector==null ? "" : _sector.getSector());
				
				//NICHO
				cell = row.createCell(4);
				//cell.setCellValue(rs.getString("NOM_NICHO"));
				mNichos _nicho = nichos.get(rs.getLong("PK_NICHO"));
				cell.setCellValue(_nicho == null ? "" : _nicho.getNicho());
				
				//REF. BANCARIA
				cell = row.createCell(5);
				cell.setCellValue(rs.getString("REFBANCARIA"));
				
				
				//MONTO
				cell = row.createCell(6);
				concepto = rs.getString("MONTO"); 
				if (concepto==null) { concepto = "$0.00"; }else{	
					concepto = "$"+concepto;
					}
				
				cell.setCellValue(concepto);
				
				
				
				//COIMISON
				cell = row.createCell(7);
				comision = rs.getDouble("COMISION");
				monto = rs.getDouble("MONTO");
				comision = (monto * comision) / 100;
				comision = Math.round(comision);
				concepto = "$"+comision;
				cell.setCellValue(concepto);
				
				
				//ABONO
				cell = row.createCell(8);
				concepto = "$"+rs.getString("ABONO");
				cell.setCellValue(concepto);
				
				
				//SALDO
				cell = row.createCell(9);
				abono = rs.getDouble("ABONO");
				saldo = (monto - comision)-abono;
				concepto = "$"+saldo;
				cell.setCellValue(concepto);

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
