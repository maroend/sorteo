package com.sorteo.poblacion.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
import com.core.Global;
import com.core.SesionDatos;
import com.core.SuperModel.RESULT;
import com.sorteo.poblacion.model.mNichos;

/**
 * Servlet implementation class Nichos
 */
@WebServlet("/Nichos.do")
public class Nichos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Nichos() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mNichos model = new mNichos();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int pg = 0;
		int show = 0;
		String search = "";
		int sector = 0;
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
		boolean editarNichos = sesion.permisos.havePermiso(30117) && sesion.sorteoActivo;
		
		switch (view) {
		
		
		case "ExportExcel":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			
			sector = 0;
			if(request.getParameter("idsectorb")!=null&&request.getParameter("idsectorb")!=""){
			sector = Integer.valueOf(request.getParameter("idsectorb"));}
			
			try { 
				export(request, response, pg, show, search, sector, model, sesion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return;	
			//break;
		
		
		
		case "Buscar":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			show = Integer.valueOf(request.getParameter("show"));
			search = Global.decodeBase64(request.getParameter("search"));
			
			sector = 0;
			if (request.getParameter("idsectorb") != null && request.getParameter("idsectorb") != "")
			{
				sector = Integer.valueOf(request.getParameter("idsectorb"));
			}
			
			Buscar(request, response, pg, show, search, sector, model, sesion, editarNichos);
			break;
			
		case "BuscarSector":
			BuscarSector(request, response, model, sesion);
			break;
			
		case "BuscarSectorInicio":
			BuscarSectorInicio(request, response, model, sesion);
			break;
			
			
		case "BuscarEditar":
			BuscarEditar(request, response, model);
			break;
			
		case "Editar":
			//Editar(request, response, model);
			break;
			
		case "Borrar":
			HTML = Borrar(request, response, model);
			break;
			
		default:
			
			fullPath = "/WEB-INF/views/poblacion/nichos.html";
			menu = vista.initMenu(0, false, 3, 5, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			
			model.setIdSorteo((int)sesion.pkSorteo);
			model.setIdusuario((int)sesion.pkUsuario);			
			
			model.getSectorUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getIdSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( (int)sesion.pkUsuario));
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", editarNichos ? "display" : "none");
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mNichos model = new mNichos();
		SesionDatos sesion;
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		RESULT result = RESULT.OK;
		model._mensaje = "";

		model.setIdSorteo(sesion.pkSorteo);

		String accion = request.getParameter("action");

		if ("INSERT".compareTo(accion) == 0) {
			model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
			model.setClave(request.getParameter("clave"));
			model.setNicho(Global.decodeBase64(request.getParameter("nicho")));
			model.setLimiteVenta(request.getParameter("limiteventa"));
			model.setLimiteDeposito(request.getParameter("limitedeposito"));
			model.setUsuario(sesion.nickName);

			//model.insertar();
			
			int idNicho = model.consultaIdXClave();
			
			// si no se encontro la clave de sector...
			if (idNicho==-1)
				result = model.insertar();
			else {
				result = RESULT.ERROR;
				model._mensaje = "La clave de nicho ya existe, escriba una nueva clave";
			}
			
		}
		else if ("UPDATE".equals(accion)) {

			model.setId(Integer.valueOf(request.getParameter("idnicho")));
			model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
			model.setClave(request.getParameter("clave"));
			model.setNicho(Global.decodeBase64(request.getParameter("nicho")));
			model.setLimiteVenta(request.getParameter("limiteventa"));
			model.setLimiteDeposito(request.getParameter("limitedeposito"));
			model.setUsuario(sesion.nickName);
			
			if (model.existeNicho())
				result = model.actualizar();
			else{
				result = RESULT.ERROR;
				model._mensaje = "Ocurri&oacute; un error al momento de actualizar el nicho. Es posible que otro usuario lo haya borrado.";
			}
		}
		else if ("DELETE".compareTo(accion) == 0) {

			String nichocadena = request.getParameter("idsnichos");
			String[] nichos = nichocadena.split(",");

			result = model.BorrarNichos(nichos);
		}

		PrintWriter writer = response.getWriter();
		String JSON="{\"result\":\"" + result + "\",\"msg\":\"" + model._mensaje + "\"}";
		writer.println(JSON);
		model.close();
	}
	
	
	protected void BuscarSectorInicio(HttpServletRequest request,
			HttpServletResponse response, mNichos model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String panelcontent = "";
		
		model.setIdusuario(Integer.valueOf( request.getParameter("usuario")));
		model.setIdSector(Integer.parseInt(request.getParameter("idsectorU"))); 
		
		if(model.isAdministrador()){
			panelcontent += "<option value=\"\">Todos</option> ";
			rs = model.getSectores(sesion);
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
	
	protected void BuscarEditar(HttpServletRequest request,
			HttpServletResponse response, mNichos model) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idnicho")));
		model.BuscarEditar(model);

		String datos = String.valueOf(model.getId()) + "#%#" + model.getNicho()
				+ "#%#" + model.getIdSector() + "#%#" + model.getClave() + "#%#"
				+ model.getLimiteVenta()+ "#%#"+model.getLimiteDeposito();

		PrintWriter out = response.getWriter();
		out.println(datos);
	}

	/*
	protected void Editar(HttpServletRequest request,
			HttpServletResponse response, mNichos model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setId(Integer.valueOf(request.getParameter("idnicho")));
		model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
		model.setClave(request.getParameter("clave"));
		model.setNicho(request.getParameter("nicho"));
	//	model.setDescripcion(request.getParameter("descripcion"));
		model.setlimiteventa(request.getParameter("limiteventa"));
		model.setlimitedeposito(request.getParameter("limitedeposito"));
		model.Editar(model);
	}
	*/

	protected String Borrar(HttpServletRequest request,
			HttpServletResponse response, mNichos model) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idnicho")));
		RESULT result = model.Borrar();
		
		return "{\"result\":\"" + result + "\",\"msg\":\"" + model._mensaje + "\"}";
	}

	protected void BuscarSector(HttpServletRequest request,
			HttpServletResponse response, mNichos model, SesionDatos sesion) throws ServletException, IOException {
		
		// model.setSector(Integer.valueOf( request.getParameter("idsector")));
		
		ResultSet rs = model.getSectores(sesion);
		
		String panelcontent = "";
		
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
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int sector, mNichos model, SesionDatos sesion, boolean editarNichos) throws ServletException, IOException
	{
		String[] columnas = { "Clave", "Nicho", "Sector", "Limite Venta","Limite Deposito",/*"Asignado",*/ "Controles" };
		String[] campos = { "CLAVE", "NICHO", "SECTOR", "LIMITE_VENTA","LIMITE_DEPOSITO"/*,"ASIGNACIONES"*/ };
		
		if (editarNichos == false)
			columnas = Arrays.copyOfRange(columnas, 0, columnas.length-1);
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdusuario(sesion.pkUsuario);
		model.setIdSector(sector);
		
		int numeroregistros = model.contar(search);
		
		String HTML = CrearTabla(numeroregistros,
				model.paginacion(pg, show, search),
				columnas, campos, pg, show, sesion, editarNichos);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion, boolean editarNichos) {
		
		int i = (pg - 1) * show;
		
		ArrayList<String> campostable = new ArrayList<String>();
		
		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";
		
		for (String columna : columnas) {
			String campotable = htmlcampo + StringEscapeUtils.escapeHtml4(columna) + "</th>";
			campostable.add(campotable);
		}
		
		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";
		if (editarNichos)
			html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onclick=\"seleccionarTodo('1t')\" id=\"checkboxall1t\" name=\"checkboxall\"/></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			html += columna;
		}
		
		html += " </thead>";
		html += " <tbody>";
		
		try {
			if (Integer.valueOf(numreg) > 0)
			{
				
				while (rs.next()) {
					i++;
					
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					if (editarNichos)
						html += "<td class=\"sorting_1\"><input type=\"checkbox\" id=\""+rs.getInt("PK1")+"\" /></td>";
					html += "<td class=\"sorting_1\">" + i + "</td>";
					for (String campo : campos) {
						String str = StringEscapeUtils.escapeHtml4(rs.getString(campo));
						str = str.replaceAll(" ", "&nbsp;");
						
						/*if (campo.equals("ASIGNACIONES")){
							if (str.compareTo("0") == 0) str = "";
							else str = "<i class=\"fa fa-check\"></i>";
						}*/
						
						html += "<td class=\"sorting_1\">" + str + "</td>";
					}
					
					
					if (editarNichos)
					{
						html += "<td class=\"sorting_1\">";
						html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"
							+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
							+ "<li><a href=\"javascript:BuscarEditar("
							+ rs.getString("PK1")
							+ ");\">Editar</a></li>"
							+ "<li><a href=\"javascript:Borrar("
							+ rs.getString("PK1")
							+ ");\">Borrar</a><li>"
							+ "<li class=\"divider\">"
							+ "</li><li>"
							+ "</ul></div>";
						
						html += "</td>";
					}
					html += "</tr>";
					
					System.out.println(rs.getString("NICHO"));
				}
			} else {

				html += "<tr class=\"gradeA odd\" role=\"row\"><td colspan=\"8\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

				html += "<h1>No existen Nichos</h1>";
				html += "<p>Empiece creando un nuevo nicho.</p>";

				html += "</div>";

				html += "</td>";
				html += "</tr>";

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
	
	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,int sector,
			mNichos model, SesionDatos sesion) throws ServletException, IOException, SQLException {
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdusuario(sesion.pkUsuario);
		model.setIdSector(sector);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Nichos.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Nichos");
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;

		cell = row.createCell(0);
		cell.setCellValue("CLAVE");
		cell = row.createCell(1);
		cell.setCellValue("NICHO");
		cell = row.createCell(2);
		cell.setCellValue("LIMITE VENTA");
		cell = row.createCell(3);
		cell.setCellValue("LIMITE DEPOSITO");
		cell = row.createCell(4);
		cell.setCellValue("SECTOR");
		
	
		ResultSet rs = model.paginacion(pg, show, search, false);
		
		int fila = 1;
		
		
		while (rs.next()) {
			row = sheet.createRow(fila);

			// CLAVE
			cell = row.createCell(0);
			cell.setCellValue(rs.getString("CLAVE"));
			
			// SECTOR
			cell = row.createCell(1);
			cell.setCellValue(rs.getString("NICHO"));

			// 
			cell = row.createCell(2);
			cell.setCellValue(rs.getString("LIMITE_VENTA"));

			// 
			cell = row.createCell(3);
			cell.setCellValue(rs.getString("LIMITE_DEPOSITO"));

			// DESCRIPCION
			cell = row.createCell(4);
			cell.setCellValue(rs.getString("SECTOR"));

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
