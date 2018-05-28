package com.sorteo.reportes.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

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
import com.core.ReadExcel;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.sorteo.herramientas.controller.ProgressBarCalc;
import com.sorteo.reportes.model.mColaboradoresTalonarios;

/**
 * Servlet implementation class ColaboradoresTalonarios
 */
@WebServlet("/ColaboradoresTalonarios")
public class ColaboradoresTalonarios extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ColaboradoresTalonarios() {
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
		
		mColaboradoresTalonarios model = new mColaboradoresTalonarios();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		int pg = 0;
		int show = 0;
		String search = "";
		int sector = 0;
		int nicho = 0;
		
		//                       Permiso para editar Colaboradores en menu "Poblacion"
		boolean editarColaboradores = sesion.permisos.havePermiso(30119) && sesion.sorteoActivo;
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
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
				sector = Integer.valueOf(request.getParameter("idsectorb"));
			}
			
			nicho = 0;
			if (request.getParameter("idnichob")!=null && request.getParameter("idnichob")!="")
				nicho = Integer.valueOf(request.getParameter("idnichob"));
			
			try {
				export(request, response, pg, show, search, sector, nicho, model, sesion);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.close();
			
			//break;
			return;
		
		
		case "LoadExcelFile":
			
			String fileName = request.getParameter("fileName");
			model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
			sesion.pkNicho = Integer.valueOf(request.getParameter("idnicho"));
			
			HTML += loadExcel(context, UploadFile.getPathOf(context, fileName), model, sesion);
			
			break;
			
		case "ProgressBar":
			
			HTML = "" + sesion.data1;
			
			break;
			
		case "Buscar":
			
			model.init();
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
				sector = Integer.valueOf(request.getParameter("idsectorb"));
			}
			
			nicho = 0;
			if(request.getParameter("idnichob")!=null&&request.getParameter("idnichob")!="")
				nicho = Integer.valueOf(request.getParameter("idnichob"));
			
			Buscar(request, response, pg, show, search, sector, nicho, model, sesion, editarColaboradores);
			break;

		case "BuscarSector":
			BuscarSector(request, response, model, sesion);
			break;	

		case "buscarNichosSector":
			buscarNichosSector(request, response, model);
			break;
			
		case "BuscarSectorInicio":
			BuscarSectorInicio(request, response, model, sesion);
			break;		
		
		case "buscarNichosSectorInicio":
			buscarNichosSectorInicio(request, response, model);
			break;

		case "BuscarEditar":
			BuscarEditar(request, response, model);
			break;
			
		case "getDireccion":
			getDireccion(request, response, model);
			break;
			
		case "Editar":
			Editar(request, response, model, sesion);
			break;
			
		case "actualizarComision":
			actualizarComision(request, response, model);
			break;
			
		case "Borrar":
			Borrar(request, response, model);
			break;
			
		default:
			
			String fullPath = "/WEB-INF/views/reportes/colaboradoresTalonarios.html";
			menu = vista.initMenu(0, false, 19, 1036, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdsorteo((int)sesion.pkSorteo);
			model.setIdusuario((int)sesion.pkUsuario);
			
			model.getSectorUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( (int)sesion.pkUsuario));
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", editarColaboradores ? "display" : "none");
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	protected void actualizarComision(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")) );		
		model.setComision(Integer.valueOf(request.getParameter("comision")));			
		model.actualizarComision(model);
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected String valida(String str)
	{
		return str.replaceAll("'", "");
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mColaboradoresTalonarios model = new mColaboradoresTalonarios();
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		model.setIdsorteo(sesion.pkSorteo);
		
		String accion = request.getParameter("action").trim();
		String DELETE = "DELETE";
		String INSERT = "INSERT";
		String UPDATE = "UPDATE";
		
		if(accion.compareTo(INSERT) == 0 || accion.compareTo(UPDATE) == 0) {
			
			model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
			model.setClave(valida(request.getParameter("clave")));
			
			model.setDescripcion(request.getParameter("descripcion"));
			model.setRbancaria(valida(request.getParameter("rbancaria")));
			model.setComision(9.090909090909091);
			
			model.setNombre(request.getParameter("nombre"));
			model.setApellidop(request.getParameter("apaterno"));
			model.setApellidom(request.getParameter("amaterno"));
			model.setEdad(valida(request.getParameter("edad")));
			model.setRfc(valida(request.getParameter("rfc")));
			
			// TELEFONOS
			model.setTelefonocasa(valida(request.getParameter("telefonocasa")));
			model.setTelefonooficina(valida(request.getParameter("telefonooficina")));
			model.setTelefonomovil(valida(request.getParameter("telefonomovil")));
			
			// DIRECCION
			model.setCp(Integer.valueOf(valida(request.getParameter("cp"))));
			model.setPais(valida(request.getParameter("pais")));
			model.setEstado(valida(request.getParameter("estado")));
			model.setMundel(valida(request.getParameter("delmun")));
			model.setColonia(valida(request.getParameter("colonia")));
			model.setCalle(valida(request.getParameter("calle")));
			model.setNumExterior(valida(request.getParameter("numero")));
			model.setNumInterior("0");
			
			// CORREO
			model.setCorreopersonal(valida(request.getParameter("correopersonal")));
			model.setCorreotrabajo(valida(request.getParameter("correotrabajo")));
			model.setCorreootro(valida(request.getParameter("correootro")));
			
			// REDES SOCIALES
			model.setFacebook(valida(request.getParameter("facebook")));
			model.setTwitter(valida(request.getParameter("twitter")));
			model.setRedotro(valida(request.getParameter("otrared")));
			// SECTOR INTERNO O EXTERNO ?
			model.setSect(valida(request.getParameter("sec")).charAt(0));
		
		}
		
		
		if(accion.compareTo(INSERT) == 0 ){
			model.registrar(model, sesion);
		}
		
		if(accion.compareTo(UPDATE) == 0 ){
			
            model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
			model.Editar(model, sesion);
			
		}
		
        if(accion.compareTo(DELETE) == 0 ){
			
        	String colaboradorescadena = request.getParameter("idcolaboradores");
			String[] colaboradores = colaboradorescadena.split(",");
			
			model.BorrarColaboradores(colaboradores, sesion);
			
		}
		
		
		model.close();
	}
	/**/
	

	//*/
	protected void BuscarEditar(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		model.BuscarEditar(model);
		model.getSectorNicho(model);
		
		//COLABORADOR
		/*	String datos = String.valueOf(model.getId()) + "#%#"
				+ model.getColaborador() + "#%#" + model.getSector() + "#%#"
				+ model.getNicho() + "#%#" + model.getClave() + "#%#"
				+ model.getDescripcion();*/
		
		System.out.println(">>>idNicho "+model.getNicho());
		
		//COLABORADOR   
		String datos = model.getId()+"#%#"+model.getClave()+"#%#"+model.getNombre()+"#%#"+model.getApellidop()+"#%#"+model.getApellidom()
		+"#%#"+model.getRfc()+"#%#"+model.getEdad()+"#%#"+model.getSect()+"#%#"+model.getDescripcion()+"#%#"+model.getSector()+"#%#"+model.getNicho()+"#%#"+model.getRbancaria();
		
		//TELEFONOS
		model.ObtenerTelefono(model);
		datos += "#%#"+model.getTelefonocasa()+"#%#"+model.getTelefonooficina()+"#%#"+model.getTelefonomovil();
		
		//DIRECCION
		model.ObtenerDireccion(model);
		// base de datos
		String colonias = model.Obtener_Colonia();// colonia de sepomex
		
		datos += "#%#" + model.getPais() + "#%#" + model.getEstado() + "#%#"
				+ model.getMundel() + "#%#" + colonias + "#%#"
				+ model.getCalle() + "#%#" + model.getNumExterior() + "#%#"
				+ model.getCp() + "#%#" + model.getColonia();
		
		// CORREOS
		model.ObtenerCorreo(model);
		datos += "#%#" + model.getCorreopersonal() + "#%#"
				+ model.getCorreotrabajo() + "#%#" + model.getCorreootro();
		
		//REDES
		model.ObtenerRedes(model);
		datos += "#%#"+model.getFacebook()+"#%#"+model.getTwitter()+"#%#"+model.getRedotro()+"#%#"+model.getEdad()+"#%#"+model.getRfc();
		
		model.init();	
		
		PrintWriter out = response.getWriter();
		out.println(datos);
	}
	
	protected void getDireccion(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setCp(Integer.valueOf(request.getParameter("cp")));
		
		model.Obtener_Direccion(model);
		
		String direccion = model.getPais() + "#%#" + model.getEstado() + "#%#"
				+ model.getMundel() + "#%#" + model.getColonia();
		
		PrintWriter out = response.getWriter();
		out.println(direccion);
		
	}
	
	protected void Editar(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
		model.setClave(request.getParameter("clave"));
		model.setColaborador(request.getParameter("colaborador"));
		model.setDescripcion(request.getParameter("descripcion"));

		model.Editar(model, sesion);

	}

	protected void Borrar(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		model.Borrar(model);

	}

	protected void BuscarSector(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String panelcontent = "";

		model.setIdsorteo(sesion.pkSorteo);
		model.setIdusuario(Integer.valueOf( request.getParameter("usuario")));
		model.setSector(Integer.parseInt(request.getParameter("idsectorU")));
		
		if(model.isAdministrador()){
			
		//	panelcontent += "<option value=\"\">Todos</option> ";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	
	protected void BuscarSectorInicio(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String panelcontent = "";
		
		model.setIdsorteo(sesion.pkSorteo);
		model.setIdusuario(Integer.valueOf(request.getParameter("usuario")));
		model.setSector(Integer.parseInt(request.getParameter("idsectorU")));
		
		if (model.isAdministrador()){
			
			panelcontent += "<option value=\"\">Todos</option> ";
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
	
	protected void buscarNichosSector(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model) throws ServletException, IOException {

		//System.out.println("-->> (1) idsector:"+request.getParameter("idsector"));
		model.setSector(Integer.valueOf(request.getParameter("idsector")));

		ResultSet rs = model.getNichos(Integer.valueOf(request.getParameter("idsector")));

		String panelcontent = "";

		try {
			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("NICHO") + "</option>";

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	               
	protected void buscarNichosSectorInicio(HttpServletRequest request,
			HttpServletResponse response, mColaboradoresTalonarios model) throws ServletException, IOException {

		//System.out.println("-->> (2) idsector:"+request.getParameter("idsector"));
		model.setSector(Integer.valueOf(request.getParameter("idsector")));

		ResultSet rs = model.getNichos(Integer.valueOf(request.getParameter("idsector")));

		String panelcontent = "";

		try {
			
			panelcontent += "<option value=\"\">Todos</option> ";
			
			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("NICHO") + "</option>";

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	
	

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int sector, int nicho, mColaboradoresTalonarios model, SesionDatos sesion, boolean editarColaboradores) throws ServletException, IOException {

	
		
		String[] columnas = { "Clave", "Nombre", "Sector", "Nicho","TALONARIO","NUMBOLETOS","MONTO","ABONO","ESTADO"};
		String[] campos   = { "CLAVE", "NOM_COLABORADOR", "NOM_SECTOR", "NOM_NICHO","PK_TALONARIO","NUMBOLETOS","MONTO","ABONO","ESTADO"};
		//String[] campos   = { "CLAVE", "NOM_COLABORADOR", "NOM_SECTOR", "NOM_NICHO"};

		if (editarColaboradores == false)
			columnas = Arrays.copyOfRange(columnas, 0, columnas.length-1);

		int numeroregistros = model.contar(search, sector, nicho, sesion);

		String HTML = CrearTabla(numeroregistros,
				model.paginacion(pg, show, search, sector, nicho, sesion),
				columnas, campos, pg, show, sesion, editarColaboradores);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion, boolean editarColaboradores) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		
		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		StringBuilder sb = new StringBuilder(1000);
		sb.append("<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">");
		sb.append("<thead>");
		sb.append("<tr role=\"row\">");
		if (editarColaboradores)
		
		sb.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");

		for (String columna : campostable) {

			sb.append(columna);
		}

		sb.append(" </thead>");
		sb.append(" <tbody>");

		try {
			
			if (Integer.valueOf(numreg) > 0)
			{
				NumberFormat formatter = new DecimalFormat("#0.0000");
				
				while (rs.next()) {
	
					i++;
	
					sb.append("<tr class=\"gradeA odd\" role=\"row\">");
					if (editarColaboradores)
					
					sb.append("<td class=\"sorting_1\">").append(i).append("</td>");
					for (String campo : campos)
					{
						String str_campo = rs.getString(campo);
						if (campo=="COMISION"){
							str_campo = formatter.format(rs.getDouble(campo));
						}
						
						sb.append("<td class=\"sorting_1\">");
						if (str_campo==null) { sb.append("N/A"); }
						else { sb.append(com.core.Global.acentos(str_campo)); }
						sb.append("</td>");
					}
	
				
	
					//i++;
					sb.append("</tr>");
	
				}
			} else {

				sb.append("<tr class=\"gradeA odd\" role=\"row\"><td colspan=\"9\">");

				sb.append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">");

				sb.append("<h1>No existen Colaboradores</h1>");
				sb.append("<p>Empiece creando un nuevo colaborador.</p>");

				sb.append("</div>");

				sb.append("</td>");
				sb.append("</tr>");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sb.append("</tbody>");
		sb.append("</table>");
		
		
		
		int numpag = Math.round(numreg / show);
		int denumpag = numpag + 1;

		String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando "
				+ pg + " de " + denumpag + " total " + numreg + " elementos</div>";
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
		int r = (pg - 1) % 5;
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
	
	
	// _________________________________________________________________________________________________________
	
	//                                             LECTURA DE EXCEL
	// _________________________________________________________________________________________________________
	
	
	protected String loadExcel(ServletContext context, String fileName, mColaboradoresTalonarios model, SesionDatos sesion)
	{
		System.out.println(fileName);
		String HTML = "";
		try {
			ReadExcel re = new ReadExcel(fileName);
			re.read("COLABORADORES");
			
			if (insertColaboradoresFromExcel(model, sesion, re) == 0){
				
				//HTML = "";
				HTML = re.content;
			}
			else{
				HTML = re.content;
			}
			
		} catch(Exception ex) { return ex.getMessage(); }
		return HTML;
	}
	
	protected int insertColaboradoresFromExcel(mColaboradoresTalonarios model, SesionDatos sesion, ReadExcel re)
	{
		System.out.println("Process sql...");
		ProgressBarCalc progress = new ProgressBarCalc(sesion);
		progress.prepare();
		
		model.setIdsorteo(sesion.pkSorteo);
		re.inserted = "";
		re.countInserts = 0;
		re.countUpdates = 0;
		int countErrors=0;
		int tanto = -1;
		progress.init(re.matriz.length); 
		for (int row=0; row<re.matriz.length; row++){
			try {
				//re.mensaje = "Leyendo filas. ";
				re.process = "";
				buildInsertQueryForColaborador(row, sesion.pkNicho, re, model, sesion);
				
				// Cada 500
				if (tanto != row/20){
					tanto = row/20;
					sesion.data1 = (int)(100.0 * (row+1) / (double)re.matriz.length);
					sesion.guardaSesion();
				}
				
			}catch(Exception ex) {
				//re.mensaje += " Error en linea:" + (row+2) + ". Error en :" + re.process;
				
				re.content += " Error en linea:" + (row+2) + ". Error en :" + re.process + "#~#";
				countErrors++;
			}
			progress.progress();
		}
		
		progress.complete();
		
		return countErrors;
	}
	
	protected int buildInsertQueryForColaborador(int row, int pkNicho, ReadExcel re, mColaboradoresTalonarios model, SesionDatos sesion) throws Exception{
		model.setNicho(pkNicho);
		
		re.process = "CLAVE";
		String clave = re.getString(row, "CLAVE");
		if (clave!=null && clave=="")
		{
			re.content += "X#~#";
			return -1;
		}
		model.setClave(clave);
		
		model.setDescripcion("");
		
		re.process = "REFBANCARIA";
		model.setRbancaria(re.getString(row,"REFBANCARIA"));
		re.process = "NOMBRE";
		model.setNombre(re.getString(row,"NOMBRE"));
		re.process = "APATERNO";
		model.setApellidop(re.getString(row,"APATERNO"));
		re.process = "AMATERNO";
		model.setApellidom(re.getString(row,"AMATERNO"));
		re.process = "EDAD";
		model.setEdad(re.getString(row,"EDAD"));
		re.process = "RFC";
		model.setRfc(re.getString(row,"RFC"));
		
		re.process = "COMISION";
		model.setComision(re.getDouble(row, "COMISION"));
		
		// TELEFONOS
		re.process = "TELEFONO";
		model.setTelefonocasa(re.getString(row,"TELEFONO"));
		re.process = "TELEFONO2";
		model.setTelefonooficina(re.getString(row,"TELEFONO2"));
		re.process = "TELEFONO3";
		model.setTelefonomovil(re.getString(row,"TELEFONO3"));
		
		// DIRECCION
		re.process = "CP";
		model.setCp(re.getString(row,"CP"));
		re.process = "PAIS";
		model.setPais(re.getString(row,"PAIS"));
		re.process = "ESTADO";
		model.setEstado(re.getString(row,"ESTADO"));
		re.process = "MUNDEL";
		model.setMundel(re.getString(row,"MUNDEL"));
		re.process = "COLONIA";
		model.setColonia(re.getString(row,"COLONIA"));
		re.process = "CALLE";
		model.setCalle(re.getString(row,"CALLE"));
		re.process = "NUMEXT";
		model.setNumExterior(re.getString(row,"NUMEXT"));
		re.process = "NUMINT";
		model.setNumInterior(re.getString(row,"NUMINT"));
		
		// CORREO
		re.process = "CORREO";
		model.setCorreopersonal(re.getString(row,"CORREO"));
		re.process = "CORREO2";
		model.setCorreotrabajo(re.getString(row,"CORREO2"));
		re.process = "CORREO3";
		model.setCorreootro(re.getString(row,"CORREO3"));
		
		
		// REDES SOCIALES
		model.setFacebook("");
		model.setTwitter("");
		model.setRedotro("");
		// SECTOR INTERNO O EXTERNO ?
		model.setSect('I');
		
		
		
		// ____________________________________________________________
		String content = ""
				+(row+1)+"#&#"
				+model.getClave()+"#&#"
				+model.getNombre() + " " + model.getApellidop() + " " + model.getApellidom() +"#&#"
				+model.getRbancaria()+"#&#"
				+model.getComision()+"#&#"
				;
		
		/**/
		
		// Consultamos el idColaborador por sorteo, clave y nicho
		int idColaborador = model.consultaIdColaborador();
		//String clave = re.getString(row, "CLAVE");
		int res;
		boolean update = idColaborador != -1;
		
		if(row%25==0)
			System.out.println();
		if (update)
		{
			model.setId(idColaborador);
			res = model.Editar(model, sesion);
			if(res==0){
				re.process = getColumnError(model, res);
				throw new Exception("");
			}
				
			
			//System.out.println(", UPDATE " + clave );
			System.out.print(" U");
			if (res == 1){
				re.countUpdates++;
			}
			else
				re.countErrors++;
		}
		else{
			res = model.registrar(model, sesion);
			if(res==0){
				re.process = getColumnError(model, res);
				throw new Exception("");
			}
			
			//System.out.println(", INSERT " + clave);
			System.out.print(" I");
			if (res > 0)
				re.countInserts++;
			else
				re.countErrors++;
		}
		
		re.content += ""
			+ (res==0?0:1) + "#&#"
			+ (update?1:0) + "#&#"
			+ content + "#~#";
		
		return 0;
	}
	
	public String getColumnError(mColaboradoresTalonarios model, int res){
		if(model.results[0]==0) return "<colaborador>";
		if(model.results[1]==0) return "TELEFONO";
		if(model.results[2]==0) return "TELEFONO2";
		if(model.results[3]==0) return "TELEFONO3";

		if(model.results[4]==0) return "DIRECCION";
		
		if(model.results[5]==0) return "CORREO";
		if(model.results[6]==0) return "CORREO2";
		if(model.results[7]==0) return "CORREO3";
		
		if(model.results[8]==0) return "RED 1";
		if(model.results[9]==0) return "RED 2";
		if(model.results[10]==0) return "RED 3";
		
		return "?";
	}
	
	
	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,int sector,int nicho,
			mColaboradoresTalonarios model, SesionDatos sesion) throws ServletException, IOException, SQLException {
		
		model.setIdsorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Colaboradores_Taonarios.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("COLABORADORES TALONARIOS");	
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("CLAVE");
		cell = row.createCell(1);
		cell.setCellValue("NOMBRE");
		cell = row.createCell(2);
		cell.setCellValue("SECTOR");
		cell = row.createCell(3);
		cell.setCellValue("NICHO");
		cell = row.createCell(4);
		cell.setCellValue("TALONARIO");
		cell = row.createCell(5);
		cell.setCellValue("NUMBOLETOS");
		cell = row.createCell(6);
		cell.setCellValue("MONTO");	
		cell = row.createCell(7);
		cell.setCellValue("ABONO");	
		cell = row.createCell(7);
		cell.setCellValue("ESTADO");	
		
		
		model.setExisteExcel(true);
	
		ResultSet rs = model.paginacion(pg, show, search,sector,nicho,sesion);
		
		int fila = 1;		
		
		while (rs.next()) {
			row = sheet.createRow(fila);			
				
			//No
				//cell = row.createCell(0);
				//cell.setCellValue(fila);
			//CLAVE
				cell = row.createCell(0);
				cell.setCellValue(rs.getString("CLAVE"));
				//NOMBRE
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("NOM_COLABORADOR"));			
				
				//NOM_SECTOR
				cell = row.createCell(2);
				cell.setCellValue(rs.getString("NOM_SECTOR"));
				
				
				//NOM_NICHO
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("NOM_NICHO"));
				
				
				//PK_TALONARIO
				cell = row.createCell(4);
				cell.setCellValue(rs.getString("PK_TALONARIO"));
				
				
				//NUMBOLETOS
				cell = row.createCell(5);
				cell.setCellValue(rs.getString("NUMBOLETOS"));
				
				//MONTO
				cell = row.createCell(6);
				cell.setCellValue(rs.getString("MONTO"));
				
				
				//ABONO
				cell = row.createCell(7);
				cell.setCellValue(rs.getString("ABONO"));	
				//ESTADO
				cell = row.createCell(7);
				cell.setCellValue(rs.getString("ESTADO"));	

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
