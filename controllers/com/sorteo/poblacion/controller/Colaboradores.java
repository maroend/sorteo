package com.sorteo.poblacion.controller;

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
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



/* *
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
//*/
//import java.awt.Color;
import com.core.Factory;
import com.core.Global;
import com.core.MENU;
import com.core.ParametersBase64;
import com.core.ReadExcel;
import com.core.ResponseMessage;
import com.core.SesionDatos;
import com.core.SuperModel.RESULT;
import com.core.UploadFile;
import com.lowagie.text.html.HtmlEncoder;
import com.sorteo.herramientas.controller.ProgressBarCalc;
import com.sorteo.poblacion.model.mColaboradores;
import com.sorteo.poblacion.model.mNichos;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/Colaboradores.do")
public class Colaboradores extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Colaboradores() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mColaboradores model = new mColaboradores();
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
		
		//                       Permiso para editar Colaboradores en menu "Poblacion"
		boolean editarColaboradores = (sesion.permisos.havePermiso(30119) && sesion.sorteoActivo) || sesion.permisos.esAdministrador();
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		case "ExportExcel":
			
			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			
			int sector = 0;
			if (request.getParameter("idsectorb") != null && request.getParameter("idsectorb") != "") {
				sector = Integer.valueOf(request.getParameter("idsectorb"));
			}
			
			int nicho = 0;
			if (request.getParameter("idnichob")!=null && request.getParameter("idnichob")!="")
				nicho = Integer.valueOf(request.getParameter("idnichob"));
			
			try {
				export(request, response, pg, show, search, sector, nicho, model, sesion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			model.close();
			/**/
			//break;
			return;
			// 
		
		case "LoadExcelFile":
			/**/
			String fileName = request.getParameter("fileName");
			model.setIdSector(request.getParameter("idsector"));
			model.setIdNicho(request.getParameter("idnicho"));

			HTML = loadExcel(context, UploadFile.getPathOf(context, fileName), model, sesion);
			/**/
			break;
			
		case "Nuevo":
			HTML = nuevaClave(model);
			break;
			
		case "Referencia":
			model.setClave(request.getParameter("clave"));
			HTML = nuevaReferencia(model);
			break;
			
		case "ProgressBar":
			
			HTML = "" + sesion.data1;
			
			break;
			
		case "Buscar":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			show = Integer.valueOf(request.getParameter("show"));
			/*
			search = request.getParameter("search");
			if (0 < search.length())
				search = ParametersBase64.decode(search, true);
				*/
			search = Global.decodeBase64(request.getParameter("search"));

			
			HTML = Buscar(request, response, pg, show, search, model, sesion, editarColaboradores);

			break;

		/*  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		case "BuscarSector":
			HTML = BuscarSector(request, model, sesion);
			break;
			*/
			
		case "buscarSectores":
			HTML = buscarSectores(request, model, sesion, "Todos");
			break;
			
		case "buscarNichos":
			HTML = buscarNichos(request, model);
			break;

		case "buscarSectoresModal":
			HTML = buscarSectores(request, model, sesion, "Seleccione el sector al que pertenece");
			break;

		case "buscarNichosModal":
			//HTML = buscarNichosModal(request, model, sesion);
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(request.getParameter("idsector"));
			model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
			HTML = makeNichosHTML(model);
			break;
			
		case "existenMasAsignaciones":
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(request.getParameter("idsector"));
			model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
			HTML = existenMasAsignaciones(model);
			break;
			

			
		case "buscarNichosXSector":
			HTML = "no function";//buscarNichosXSector(request, model, null);
			break;
			
			

		case "BuscarEditar":
			model.setIdSorteo(sesion.pkSorteo);
			HTML = BuscarEditar(request, model);
			break;
			
		case "getDireccion":
			HTML = getDireccion(request, model);
			break;
			
		case "getComision":
			HTML = getComision(request, model);
			break;
			/*
		case "Editar":
			Editar(request, response, model, sesion);
			break;
			*/
		case "actualizarComision":
			actualizarComision(request, response, model, sesion);
			break;
			
		case "VerAsignaciones":
			HTML = VerAsignaciones(request, response, model);
			break;

			/*
		case "BuscaDatos":
			String idColaborador = request.getParameter("idcolaborador");
			String clave = request.getParameter("clave");
			String rbancaria = request.getParameter("rbancaria");
			nicho = Integer.valueOf(request.getParameter("idnicho"));

			model.setIdsorteo(sesion.pkSorteo);
			model.setId(idColaborador==null ? 0 : Integer.valueOf(idColaborador));
			model.setClave(clave);
			model.setRbancaria(rbancaria);
			model.setNicho(nicho);
			
			HTML = model.buscaDatos();
			break;
			*/
			
		case "error":
			HTML = SinAcceso(vista, context, sesion);
			break;		

		default:
			
			String fullPath = "/WEB-INF/views/poblacion/colaboradores.html";
			menu = vista.initMenu(0, false, MENU.POBLACION, MENU.COLABORADORES, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdUsuario(sesion.pkUsuario);
			
			model.consultaPoblacionUsuarioActual();

			if (sesion.permisos.esAdministrador() == false && (model.getIdSorteo()<=0 /*|| model.getIdSector() <=0*/))
				HTML = SinAcceso(vista, context, sesion);
			else
			{
				HTML = HTML.replaceFirst("#SECTOR_USUARIO#", String.valueOf(model.getIdSector())); 	   
				HTML = HTML.replaceFirst("#NICHO_USUARIO#", String.valueOf(model.getIdNicho())); 	   
				HTML = HTML.replaceFirst("#USUARIO#", String.valueOf(model.getIdUsuario()));
				
				HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", editarColaboradores ? "display" : "none");
			}
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	protected String SinAcceso(Factory vista, ServletContext context, SesionDatos sesion) {
		String fullPath = "/WEB-INF/views/error.html";
		String menu = vista.initMenu(0, false, MENU.POBLACION, MENU.COLABORADORES, sesion);
		String notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
		String infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
		return vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
	}
	
	protected void actualizarComision(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model, SesionDatos sesion)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")) );		
		model.setComision(request.getParameter("comision"));			
		model.actualizarComision(sesion);
	}
	
	protected String VerAsignaciones(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		ResultSet res = model.VerAsignaciones();

		StringBuilder sb = new StringBuilder();
		int contador = 0;
		try {
			while (res.next()) {
				sb.append("<span>&#8226; ").append(res.getString("NICHO"))
						.append(" (").append(res.getString("SECTOR"))
						.append(")")
						.append("</span><br/>");
				contador++;
			}
		} catch (SQLException ex) { }

		if (contador == 0)
			return "El colaborador no est&aacute; asignado.";
		return sb.toString();
	}	
	
	
	protected String valida(String str)
	{
		return str.replaceAll("'", "");
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO doPost
		PrintWriter writer = response.getWriter();
		
		mColaboradores model = new mColaboradores();
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		model.setIdSorteo(sesion.pkSorteo);

		HashMap<String,ParametersBase64> params = ParametersBase64.parse(request.getParameter("q"), "[&]", "[=]", true);
		ParametersBase64 parameter;
		model.setUsuario(sesion.nickName);
		if ((parameter = params.get("DELETE")) != null)
		{
			model.BorrarColaboradores(parameter.arrayValues, sesion);
			ResponseMessage res;
			if (model._count_success > 0 && model._count_success == model._count_max){
				res = new ResponseMessage("success", "CORRECTO",
						"Colaborador(es) eliminado(s) satisfactoriamente.");
			}
			else if(model._count_success > 0){
				res = new ResponseMessage("warning", "ATENCION",
						"" + model._count_success + " colaborador(es) eliminado(s).");
			}
			else {
				res = new ResponseMessage("error", "ERROR",
						model._count_excluded==1
						? "El colaborador no se puede eliminar por que tiene talonarios asignados"
						: "" + model._count_excluded + " colaboradores no se pueden eliminar por que tienen talonarios asignados"
						);
			}
			
			writer.write(res.toJson());
		}
		if (params.get("SAVE") != null)
		{
			model.setId(Integer.valueOf(params.get("idcolaborador").value));
			model.setIdSector(params.get("idsector").value);
			model.setClave(valida(params.get("clave").value));
			model.setReferencia(Integer.valueOf((params.get("referencia").value)));
			model.setNumReferencia(params.get("num_referencia").value);
			
			model.setNombre(params.get("nombre").value);
			model.setApellidop(params.get("apaterno").value);
			model.setApellidom(params.get("amaterno").value);
			model.setComision(params.get("comision").value);
			model.setEdad(valida(params.get("edad").value));
			model.setRfc(valida(params.get("rfc").value));
			
			// DIRECCION
			model.setCP(params.get("cp").value);
			model.setEstado(valida(params.get("estado").value));
			model.setMundel(valida(params.get("mundel").value));
			model.setColonia(valida(params.get("colonia").value));
			model.setCalle(valida(params.get("calle").value));
			model.setNumInterior(valida(params.get("numero_int").value));
			model.setNumExterior(valida(params.get("numero_ext").value));
			
			// CORREO
			model.setCorreoP(valida(params.get("correo_p").value)); 
			model.setCorreoS(valida(params.get("correo_s").value));
			
			// TELEFONOS
			model.setTelefonoP(valida(params.get("telefono_p").value));
			model.setTelefonoS(valida(params.get("telefono_s").value));
			
			model.setUsuario(sesion.getUsuario());

			RESULT result = RESULT.OK;
			if (params.get("INSERT") != null) {
				result = model.validarClaveYGuardar();
				if (result == RESULT.OK)
					result = model.guardaAsignaciones(params.get("nichos").arrayValues);
			}
			else if (params.get("UPDATE") != null) {
	            result = model.actualizar();
				if (result == RESULT.OK)
					result = model.guardaAsignaciones(params.get("nichos").arrayValues);
			}
			
			writer.write("{\"result\":\"" + result + "\",\"msg\":\"" + model._mensaje + "\"}");
		}
		
		model.close();
	}
	/**/
	

	//*/
	protected String BuscarEditar(HttpServletRequest request, mColaboradores model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		model.BuscarEditar();
		model.str_asignaciones = model.asignacionesToJSON(model.consultaAsignaciones());
		
		return model.getJSON();
	}
	
	protected String getDireccion(HttpServletRequest request, mColaboradores model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setCP(request.getParameter("cp"));
		
		model.obtenerDireccionSEPOMEX();

		return (
			  "{'cp':'" + model.getCP() + "'"
			+ ",'estado':'" + model.getEstado() + "'"
			+ ",'mundel':'" + model.getMundel() + "'"
			+ ",'colonias_html':'" + model.getColonia() + "'"
			+ "}").replaceAll("'", "\"");
	}
	
	protected String getComision(HttpServletRequest request, mColaboradores model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		model.consultaComision();

		return (
			  "{'comision':'" + model.getComision() + "'"
			+ "}").replaceAll("'", "\"");
	}
	
	/*
	protected String Editar(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		//model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
		model.setClave(request.getParameter("clave"));
		//model.setColaborador(request.getParameter("colaborador"));
		//model.setDescripcion(request.getParameter("descripcion"));

		String result = model.Editar(model, sesion);
		
		return "{\"result\":\"" + result + "\",\"msg\":\"" + model.mensaje + "\"}";
	}
	*/


	/* *
	protected String BuscarSector(HttpServletRequest request,
			mColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String panelcontent = "";

		model.setIdsorteo(sesion.pkSorteo);
		model.setIdUsuario(Integer.valueOf( request.getParameter("usuario")));
		model.setSector(Integer.parseInt(request.getParameter("idsectorU")));
		
		if(sesion.permisos.esAdministrador()){
			
		//	panelcontent += "<option value=\"\">Todos</option> ";
			panelcontent += "<option value=\"S\">Seleccionar Sector</option> ";

			rs = model.getSectores();			
			
		} else{
			
			rs = model.getSectoresUsuario();				
		}
		
		try {
			
			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("SECTOR") + "</option>";

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return panelcontent;
	}
	/**/
	
	protected String buscarSectores(HttpServletRequest request,
			mColaboradores model, SesionDatos sesion, String firstOption) throws ServletException, IOException {
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSector(Integer.parseInt(request.getParameter("idsectorU")));
		model.setIdUsuario(request.getParameter("usuario"));
		//=20147
		
		if (sesion.permisos.esAdministrador() || model.esResposableDeSector()){
			if (firstOption != null)
				sb.append("<option value=''>").append(StringEscapeUtils.escapeHtml4(firstOption)).append("</option>\n");
			rs = model.getSectores();
		} else{
			rs = model.getSectoresUsuario();
		}
		try {
			while (rs.next()) {
				sb.append("<option value='").append(rs.getInt("PK1")).append("'>").append(StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))).append("</option>\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	protected String buscarNichos(HttpServletRequest request,
			mColaboradores model) throws ServletException, IOException {

		model.setIdUsuario(request.getParameter("idusuario"));
		model.setIdSector(request.getParameter("idsector")); // sector seleccionado
		model.setIdNicho(request.getParameter("idnichoU"));  // unico nicho que puede ver el usuario
		
		ResultSet rs = model.getNichos();

		StringBuilder sb = new StringBuilder();
		if (model.getIdNicho() == 0)
			sb.append("<option value=''>Todos</option>\n");
		try {
			while (rs.next()) {
				sb.append("<option value='").append(rs.getInt("PK1")).append("'>").append(StringEscapeUtils.escapeHtml4(rs.getString("NICHO"))).append("</option>");
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return sb.toString();
	}
	
	/*
	protected String buscarNichosModal(HttpServletRequest request,
			mColaboradores model, SesionDatos sesion) throws ServletException, IOException {

		model.setIdsorteo(sesion.pkSorteo);
		model.setSector(request.getParameter("idsector"));
		ResultSet rs = model.getNichosModal();

		StringBuilder sb = new StringBuilder();
		try {
			
			while (rs.next()) {
				sb
				.append("<option value='").append(rs.getInt("PK1"))
				.append("' data-check='").append(rs.getInt("CHECK"))
				.append("'>").append(rs.getString("NICHO"))
				.append("</option>\n");
			}
		} catch (SQLException e) { e.printStackTrace(); }		
		
		return sb.toString();
	}
	*/
	
	private String makeNichosHTML(mColaboradores model)
	{
		ArrayList<mNichos> list = model.getNichosPorSector();
		StringBuilder sb = new StringBuilder();
		
		int contador = 0;
		int columnas = 2;
		for (int k = 0; k < columnas; k++)
		{
			int lim_1 = ((int)Math.ceil(list.size() / (double)columnas)) * k;
			int lim_2 = ((int)Math.ceil(list.size() / (double)columnas)) * (k + 1);
			if (lim_2 > list.size())
				lim_2 = list.size();
			
			sb.append("<div class='btn m-r-5' style='vertical-align: top;'>\n");
			for (int i = lim_1; i < lim_2; i++)
			{
				mNichos nicho = list.get(contador);
				boolean asignado = nicho.getDato1().compareTo("0") != 0;
				boolean tieneTalonario = nicho.getDato2().compareTo("0") != 0;
				
				String style = asignado ? (tieneTalonario ? "fa fa-tags" : "fa fa-check-square-o") : "fa fa-square-o";
				
				sb.append("<a class='btn btn-white' style='width: 100%; text-align: left;' id='btn_nicho_")
						.append(contador).append("'");

				if (tieneTalonario)
					sb.append(" disabled");
				else
					sb.append(" onclick=\"marcarNicho(").append(contador).append(");\"");
				
				sb.append(" ><i id='icon_nicho_").append(contador).append("' ")					
						.append(" class='").append(style).append("' style='width:20px;'></i>")
						.append(nicho.getClave()).append(" - ")
						.append(StringEscapeUtils.escapeHtml4(nicho.getNicho()))
						//.append(tieneTalonario ? " <i class='fa fa-tags'></i>" :"")
						.append("</a><br/>\n");
				contador++;
			}
			sb.append("</div>\n");
		}
		for (int i = (int)((10 - Math.ceil(list.size() / (double)columnas))*1.5); i > 0; i--) {
			sb.append("<br/>\n");
		}

		// SCRIPT
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("arrayCheckNichos = [");
		boolean first=true;
		for (mNichos nicho : list) {
			if (first)
				first=false;
			else
				sb.append(",");
			boolean asignado = nicho.getDato1().compareTo("0") != 0;
			boolean tieneTalonario = nicho.getDato2().compareTo("0") != 0;
			
			sb
			.append("{ sector:").append(nicho.getIdSector())
			.append(", id:").append(nicho.getId())
			.append(", check:").append(asignado ? "true" : "false")
			.append(", talonarios:").append(tieneTalonario ? "true" : "false")
			.append("}");
		}
		sb.append("];\n");
		sb.append("</script>\n");
		return sb.toString();
	}
	
	private String existenMasAsignaciones(mColaboradores model) {
		boolean exist = model.existenMasAsignaciones();
		return "{\"result\":\"" + (exist ? 1 : 0) + "\"}";
	}
	

	protected String Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mColaboradores model, SesionDatos sesion, boolean editarColaboradores) throws ServletException, IOException {

		String[] columnas = { "Clave", "Nombre / referencia", "Nicho", "Sector", "Comisi&oacute;n(%)", "Controles"};
		String[] campos   = { "CLAVE", "NOM_COLABORADOR", "NICHO", "SECTOR", "COMISION" };

		if (editarColaboradores == false)
			columnas = Arrays.copyOfRange(columnas, 0, columnas.length-1);
		
		model.setIdSector(request.getParameter("idsectorb"));
		model.setIdNicho(request.getParameter("idnichob"));
		model.setIdSorteo(sesion.pkSorteo);
		model.setUsuario(sesion.nickName);
		int numeroregistros = model.contar(search, sesion);

		return
				CrearTabla(numeroregistros,
				model.paginacion(pg, show, search, sesion),
				columnas, campos, pg, show, sesion, editarColaboradores);
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
			sb.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onclick=\"seleccionarTodo('1t')\" id=\"checkboxall1t\" name=\"checkboxall\"/></th>");
		sb.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");

		for (String columna : campostable) {

			sb.append(columna);
		}

		sb.append(" </thead>");
		sb.append(" <tbody>");

		try {
			
			if (Integer.valueOf(numreg) > 0)
			{
				NumberFormat formatter = new DecimalFormat("#0.0000000000");
				
				int lastPK = -1;
				
				while (rs.next()) {
	
					i++;
					/*
					if (lastPK == rs.getInt("PK1"))
						sb.append("<tr class=\"gradeA odd\" role=\"row\" style='color:#555555' >");
					else*/
						sb.append("<tr class=\"gradeA odd\" role=\"row\" >");
					
					if (editarColaboradores)
						sb.append("<td class=\"sorting_1\"><input type=\"checkbox\" class=\"colaborador\" id=\"").append(rs.getInt("PK1")).append("\" /></td>");
					sb.append("<td class=\"sorting_1\">").append(i).append("</td>");
					for (String campo : campos)
					{
						String value = rs.getString(campo);
						if (campo=="COMISION"){
							value = formatter.format(rs.getDouble(campo));
						}
						if (campo=="SECTOR"){
							value = value == null ? "" : Global.cut(value);
						}
						if (campo=="NICHO"){
							value = value == null ? "" : value;
						}
						
						if (campo=="NOM_COLABORADOR"){
							int ref = rs.getInt("REFERENCIA");
							String num_ref = rs.getString("NUM_REFERENCIA");
							value = value.replaceAll(" ", "&nbsp;") + "<br/><span style='color:#2e9bd8;'>" + (ref == 1 && num_ref != null ? num_ref : "") + "</span>";
						}
						
						if((campo=="SECTOR" || campo=="NICHO") && lastPK == rs.getInt("PK1"))
							sb.append("<td class=\"sorting_1\" style='color:#779977'>");
						else
							sb.append("<td class=\"sorting_1\" >");
						if (value==null) { sb.append("N/A"); }
						else { sb.append(com.core.Global.acentos(value)); }
						sb.append("</td>");
					}
	
					if (editarColaboradores)
					{
						sb.append("<td class=\"sorting_1\">")
							.append("<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n")
							.append("<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">")
							//.append("<li><a href=\"javascript:getComision(").append(rs.getString("PK1")).append(");\">Comisi&oacute;n</a></li>")
							.append("<li><a href=\"javascript:ShowModalEditar(").append(rs.getString("PK1")).append(");\">Editar</a></li>")
							.append("<li><a href=\"javascript:Borrar(").append(rs.getString("PK1")).append(");\">Borrar</a><li>")
							//.append("<li><a href=\"javascript:VerAsignaciones(").append(rs.getString("PK1")).append(");\">Ver asignaciones</a><li>")
							.append("<li class=\"divider\">")
							.append("</li><li>")
							.append("</ul></div>")
							.append("</td>");
					}
	
					//i++;
					sb.append("</tr>");
					
					//break;
					
					lastPK = rs.getInt("PK1");
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
			e.printStackTrace();
		}

		sb.append("</tbody>");
		sb.append("</table>");
		
		String paginado = Factory.Paginado(numreg, show, pg);

		sb.insert(0, paginado);
		//sb.append(paginado);

		return sb.toString();

	}
	
	protected String nuevaClave(mColaboradores model){
		model.setClave(model.nuevaClave());

		return ("{'clave':'" + model.getClave() + "', 'comision':'9.090909090909092'}").replaceAll("'", "\"");
	}
	
	protected String nuevaReferencia(mColaboradores model){
		return ("{'result':'" + model.generarReferencia() + "', 'num_referencia':'"+model.getNumReferencia()+"'}").replaceAll("'", "\"");
	}
	
	
	
	// _________________________________________________________________________________________________________
	
	//                                             LECTURA DE EXCEL
	// _________________________________________________________________________________________________________
	
	/* */
	protected String loadExcel(ServletContext context, String fileName, mColaboradores model, SesionDatos sesion)
	{
		String HTML="";
		ProgressBarCalc progress = new ProgressBarCalc(sesion);
		progress.prepare();
		//System.out.println(fileName);
		try {
			ReadExcel re = new ReadExcel(fileName);
			re.read("COLABORADORES", 0);
			
			if (re.matriz != null)
				agregarColaboradoresDesdeExcel(model, sesion, re, progress);

			HTML = re.content;
		} catch(Exception ex) {
			String msg = Global.extractError(ex);
			HTML = ("[{'row':'1','res':'ERROR','msg':'" + msg + "'}]").replaceAll("'", "\"");
		}
		progress.complete();
		return HTML;
	}
	
	protected void agregarColaboradoresDesdeExcel(mColaboradores model, SesionDatos sesion, ReadExcel re, ProgressBarCalc progress) {
		StringBuilder sb = new StringBuilder();
		System.out.println("Process sql...");
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setUsuario(sesion.getUsuario());
		re.inserted = "";
		re.countInserts = 0;
		re.countUpdates = 0;

		sb.append("[");
		progress.init(re.matriz.length);
		for (int row = 0; row < re.matriz.length; row++) {
			try {
				re.process = "";
				agregarColaborador(row, re, model, sb);
				
			} catch(Exception ex) {
				String msg = (re.process != "") ? ("No se encuentra la columna: " + re.process) : "";
				sb.append("{\"row\":\"").append(row + 1).append("\",\"res\":\"").append(RESULT.ERROR).append("\",\"msg\":\"").append(msg).append("\"},");
				break;
			}
			progress.progress();
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]");

		re.content = sb.toString();
	}

	// TODO agregarColaborador
	protected void agregarColaborador(int row, ReadExcel re, mColaboradores model , StringBuilder sb) throws Exception{
		RESULT res;
		boolean update;
		
		String clave = re.getString(row, "CLAVE");
		//String mensaje="";
		model._mensaje = "";
		if (clave!=null && clave!="")
		{
			model.setClave(clave);
	
			model.setNombre(re.getString(row,"NOMBRE"));
			model.setApellidop(re.getString(row,"APATERNO"));
			model.setApellidom(re.getString(row,"AMATERNO"));
			model.setComision(9.090909090909091);
			model.setRfc(re.getString(row,"RFC"));
			model.setEdad(re.getString(row,"EDAD"));
			
			// CORREO
			model.setCorreoP(re.getString(row,"CORREO"));
			model.setCorreoS(re.getString(row,"CORREO_2"));
			
			// TELEFONOS
			model.setTelefonoP(re.getString(row,"TELEFONO"));
			model.setTelefonoS(re.getString(row,"TELEFONO_2"));
			
			// DIRECCION
			model.setCP(re.getString(row,"CP"));
			// Se obtiene la direccion de la tabla SEPOMEX y se asigna la colonia.
			model.obtenerDireccionSEPOMEX(re.getString(row,"COLONIA"));
			
			// Si no se obtubieron los datos de direccion ...
			if (model.getEstado().equals(""))
				model.setEstado(re.getString(row,"ESTADO"));
			if (model.getMundel().equals(""))
				model.setMundel(re.getString(row,"MUNDEL"));
			if (model.getColonia().equals(""))
				model.setColonia(re.getString(row,"COLONIA"));
			
			model.setCalle(re.getString(row,"CALLE"));
			model.setNumExterior(re.getString(row,"NUMEXT"));
			model.setNumInterior(re.getString(row,"NUMINT"));
			
			
			re.process = "";
			// ____________________________________________________________
			
			// Consultamos el idColaborador por sorteo, clave y nicho
			int idColaborador = model.consultaIdColaboradorXClave();
			update = idColaborador != -1;
			
			if (update)
			{
				model.setId(idColaborador);
				res = model.actualizar();
				if (res == RESULT.OK)
					re.countUpdates++;
			}
			else {
				res = model.insertar();
				if (res == RESULT.OK)
					re.countInserts++;
			}
			

			// Si se guardo el colaborador ... Se guarda la asignacion
			if (res == RESULT.OK) {
				res = model.agregaAsignaciones("" + model.getIdNicho());
				if (res == RESULT.ERROR) {
					re.countErrors++;
				}
			}
			else {
				re.countErrors++;
			}
		}
		else {
			update = false;
			res = RESULT.ERROR;
			model._mensaje = "La clave de colaborador no es v&aacute;lida. ";
		}
		
		if (model._mensaje.equals("") == false)
			model._mensaje = "Ocurrio un error en la l&iacute;nea: " + (re.matriz[row][0].rowIndex + 1) + ". " + model._mensaje;
		
		sb
		.append("{\"row\":\"").append(row + 1).append("\"")
		.append(",\"cve\":\"").append(model.getClave()).append("\"")
		.append(",\"nom\":\"").append(model.getNombre()).append(", ").append(model.getApellidop()).append(" ").append(model.getApellidom()).append("\"")
		.append(",\"upd\":\"").append(update ? 1 : 0).append("\"")
		.append(",\"res\":\"").append(res).append("\"")
		.append(",\"msg\":\"").append(model._mensaje).append("\"")
		.append("},");
	}

	/*
	public String getColumnError(mColaboradores model, int res){
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
	*/


	/**/
	protected void export(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int sector, int nicho, mColaboradores model, SesionDatos sesion)
			throws ServletException, IOException, SQLException {
		
		model.setIdSorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Colaboradores.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("COLABORADORES");
		
		HSSFRow row = sheet.createRow(0);
		//HSSFCell cell;
		
		(row.createCell(0)).setCellValue("CLAVE");
		(row.createCell(1)).setCellValue("NOMBRE");
		(row.createCell(2)).setCellValue("APATERNO");
		(row.createCell(3)).setCellValue("AMATERNO");
		(row.createCell(4)).setCellValue("COMISION");
		(row.createCell(5)).setCellValue("RFC");
		(row.createCell(6)).setCellValue("EDAD");
		(row.createCell(7)).setCellValue("CORREO");
		(row.createCell(8)).setCellValue("CORREO_2");
		(row.createCell(9)).setCellValue("TELEFONO");
		(row.createCell(10)).setCellValue("TELEFONO_2");
		(row.createCell(11)).setCellValue("CP");
		(row.createCell(12)).setCellValue("ESTADO");
		(row.createCell(13)).setCellValue("MUNDEL");
		(row.createCell(14)).setCellValue("COLONIA");
		(row.createCell(15)).setCellValue("CALLE");
		(row.createCell(16)).setCellValue("NUMEXT");
		(row.createCell(17)).setCellValue("NUMINT");
		(row.createCell(18)).setCellValue("SECTOR");
		(row.createCell(19)).setCellValue("NICHO");
		
		

		/* *
		HSSFCellStyle cellStyle = wb.createCellStyle();

        HSSFFont hSSFFont = wb.createFont();
        hSSFFont.setFontName("Calibri");
        hSSFFont.setFontHeightInPoints((short) 11);
        hSSFFont.setColor(HSSFColor.GREEN.index);
        
        cellStyle.setFont(hSSFFont);
		/**/
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSector(sector);
		model.setIdNicho(nicho);
		model.setUsuario(sesion.getUsuario());
		ResultSet rs = model.consultaEXCEL(pg, show, search, sesion);
		
		int fila = 1;
		
		while (rs.next()) {
			model.EditFrom(rs);
			model.EditAddressFrom(rs);
			
			row = sheet.createRow(fila);
			
			(row.createCell(0)).setCellValue(model.getClave());
			(row.createCell(1)).setCellValue(model.getNombre());
			(row.createCell(2)).setCellValue(model.getApellidop());
			(row.createCell(3)).setCellValue(model.getApellidom());
			(row.createCell(4)).setCellValue(model.getComision());
			(row.createCell(5)).setCellValue(model.getRfc());
			(row.createCell(6)).setCellValue(model.getEdad());
			(row.createCell(7)).setCellValue(model.getCorreoP());
			(row.createCell(8)).setCellValue(model.getCorreoS());
			(row.createCell(9)).setCellValue(model.getTelefonoP());
			(row.createCell(10)).setCellValue(model.getTelefonoS());
			
			(row.createCell(11)).setCellValue(model.getCP());
			(row.createCell(12)).setCellValue(model.getEstado());
			(row.createCell(13)).setCellValue(model.getMundel());
			(row.createCell(14)).setCellValue(model.getColonia());
			(row.createCell(15)).setCellValue(model.getCalle());
			(row.createCell(16)).setCellValue(model.getNumExterior());
			(row.createCell(17)).setCellValue(model.getNumInterior());

			(row.createCell(18)).setCellValue(rs.getString("SECTOR"));
			(row.createCell(19)).setCellValue(rs.getString("NICHO"));
			/*
			//No
				//cell = row.createCell(0);
				//cell.setCellValue(fila);
			//CLAVE
			cell = row.createCell(0);
			cell.setCellValue(rs.getString("CLAVE"));
			
			//NOMBRE
			cell = row.createCell(1);
			cell.setCellValue(rs.getString("NOMBRE"));			
			
			//APATERNO
			cell = row.createCell(2);
			cell.setCellValue(rs.getString("APATERNO"));
			
			//AMATERNO
			cell = row.createCell(3);
			cell.setCellValue(rs.getString("AMATERNO"));
			
			//COMISION
			cell = row.createCell(4);
			cell.setCellValue(rs.getString("COMISION"));
			
			//RFC
			cell = row.createCell(5);
			cell.setCellValue(rs.getString("RFC"));
			
			//EDAD
			cell = row.createCell(6);
			cell.setCellValue(rs.getString("EDAD"));
			
			model.setId(rs.getInt("PK1"));
			
			//CORREO1
			cell = row.createCell(8);
			cell.setCellValue(model.getCorreopersonal());
			
			//CORREO2
			cell = row.createCell(9);
			cell.setCellValue(model.getCorreotrabajo());
			
			//CORREO3
			cell = row.createCell(10);
			cell.setCellValue(model.getCorreootro());
			
			//TELEFONO CASA
			cell = row.createCell(11);
			cell.setCellValue(model.getTelefonocasa());
			
			//TELEFONO OFICINA
			cell = row.createCell(12);
			cell.setCellValue(model.getTelefonooficina());
			
			//TELEFONO MOVIL
			cell = row.createCell(13);
			cell.setCellValue(model.getTelefonomovil());
			
			//PAIS
			cell = row.createCell(14);
			cell.setCellValue(model.getPais());
			
			//CP
			cell = row.createCell(15);
			cell.setCellValue(model.getCp());
			
			//ESTADO
			cell = row.createCell(16);
			cell.setCellValue(model.getEstado());
			
			//MUNDEL
			cell = row.createCell(17);
			cell.setCellValue(model.getMundel());
			
			//COLONIA
			cell = row.createCell(18);
			cell.setCellValue(model.getColonia());
			
			//CALLE
			cell = row.createCell(19);
			cell.setCellValue(model.getCalle());
			
			//NUMERO EXT
			cell = row.createCell(20);
			cell.setCellValue(model.getNumExterior());
			
			//NUMERO INT
			cell = row.createCell(21);
			cell.setCellValue(model.getNumInterior());
			*/
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
	/**/
	
	
	
}
