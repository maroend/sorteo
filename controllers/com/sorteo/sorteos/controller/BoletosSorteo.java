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

import com.core.ESTADO_BOLETO;
import com.core.Factory;
import com.core.Global;
import com.core.MENU;
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mBoletosSorteo;

/**
 * Servlet implementation class BoletosSorteo
 */
@WebServlet("/BoletosSorteo")
public class BoletosSorteo extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoletosSorteo() {
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
		mBoletosSorteo model = new mBoletosSorteo();
		
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
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		
		//model.setIdComprador(Integer.parseInt(request.getParameter("idComprador")));
		
		/*case "Borrar":
			// int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			model.eliminarComprador(request, response);
			break;*/
		

		case "getBoletosTalonarios":
			// int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			HTML = getBoletosTalonarios(model, sesion);
			break;

		case "getMunicipios":
			int idestado = Integer.valueOf(request.getParameter("idestado"));
			HTML = getMunicipios(idestado, model, sesion);
			break;

		case "getBoletosTalonariosSector":
			int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			int idsector = Integer.valueOf(request.getParameter("sector"));
			HTML = getBoletosTalonariosSector(idsorteo, idsector, model, sesion);
			break;

		case "getBoletosTalonariosNicho":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			idsector = Integer.valueOf(request.getParameter("sector"));
			int idnicho = Integer.valueOf(request.getParameter("nicho"));
			HTML = getBoletosTalonariosNicho(idsorteo, idsector, idnicho,
					model, sesion);
			break;

		case "getBoletosTalonariosColaborador":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			idsector = Integer.valueOf(request.getParameter("sector"));
			idnicho = Integer.valueOf(request.getParameter("nicho"));
			int colaborador = Integer.valueOf(request.getParameter("colaborador"));
			HTML = getBoletosTalonariosColaborador(idsorteo, idsector, idnicho, colaborador, model, sesion);
			break;

		case "GetComprador":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			String folio = request.getParameter("boleto");
			String talonario = request.getParameter("talonario");
			HTML = GetComprador(idsorteo, folio, talonario, model, sesion);
			break;

		case "GetIncidenciaBoleto":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			folio = request.getParameter("boleto");
			talonario = request.getParameter("talonario");
			HTML = GetIncidenciaBoleto(idsorteo, folio, talonario, model, sesion);
			break;

		case "BuscarMontoAbonoTalonario":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			talonario = request.getParameter("talonario");
			HTML = BuscarMontoAbonoTalonario(idsorteo, talonario, model, sesion);
			break;

		case "BuscarBoletosTalonarios":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			talonario = request.getParameter("talonario");
			HTML = BuscarBoletosTalonarios(idsorteo, talonario, model, sesion);
			break;
			
		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			model.setFiltrovendidos(Integer.valueOf(request.getParameter("filtervendido")));
            model.setFiltronovendidos(Integer.valueOf(request.getParameter("filternovendido")));
            //model.setFiltroparciales(Integer.valueOf(request.getParameter("filterparcial")));
            model.setFiltrorobados(Integer.valueOf(request.getParameter("filterrobados")));
            model.setFiltroextraviados(Integer.valueOf(request.getParameter("filterextraviados")));
            
			int show = Integer.valueOf(request.getParameter("show"));
			//String search = request.getParameter("search");
			String search = Global.decodeBase64(request.getParameter("search"));

			HTML = Buscar(request, pg, show, search, model, sesion);
			break;
			
		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "ExportExcel":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			
			
			try {
				export(request, response, pg, show, search,model, sesion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			model.close();
			
			//break;
			return;	
			
		default:
			model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
			
			fullPath = "/WEB-INF/views/sorteos/boletos/BoletosSorteo.html";
			menu = vista.initMenu(0, false, MENU.SORTEOS,  MENU.LISTA_SORTEOS, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
            model.consultaSorteo(model);
            
			if (sesion.pkSorteo != model.getIdSorteo() || sesion.sorteoActivo != model.getSorteoActivo()) {
				sesion.pkSorteo = model.getIdSorteo();
				sesion.sorteoActivo = model.getSorteoActivo();
				sesion.guardaSesion();
			}
			
			HTML = HTML.replaceFirst("#SORTEO#", model.getSorteo());
			HTML = HTML.replaceFirst("#ID_SORTEO#", "" + model.getIdSorteo());
			HTML = HTML.replaceFirst("#DISPLAY_MENU#", sesion.sorteoActivo ? "display" : "none");
			HTML = HTML.replaceFirst("#BUTTONS_DISABLED#", sesion.sorteoActivo ? "" : "disabled");
			
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
	
	protected String getBoletosTalonarios(mBoletosSorteo model, SesionDatos sesion)
	{
		model.setIdSorteo(sesion.pkSorteo);
		model.consultaBoletosTalonarios();
		return ("{"
				+ " 'talonarios':" + model.talonarios
				+ ",'talonarios_asignados':" + model.talonarios_asignados
				+ ",'talonarios_parcial_vendidos':" + model.talonarios_parcial_vendidos
				+ ",'talonarios_vendidos':" + model.talonarios_vendidos
				
				+ ",'boletos':" + model.boletos
				+ ",'talonarios_asignados':" + model.talonarios_asignados
				+ ",'boletos_vendidos':" + model.boletos_vendidos
				+ ",'boletos_extraviados':" + model.boletos_extraviados
				+ ",'boletos_robados':" + model.boletos_robados
				
				+ "}").replaceAll("'", "\"");
	}
	
	protected String getMunicipios(int idestado, mBoletosSorteo model, SesionDatos sesion){
		   
		String contenido = "";
		ResultSet rs = null;

		rs = model.getMunicipios(idestado);
	   		
		try {
			while (rs.next()) {
				
				contenido += "<option value='"+rs.getString("c_mnpio")+"'>"+rs.getString("D_mnpio")+"</option>";
			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }
		
		return contenido;
	}
	
	protected String getBoletosTalonariosSector(int idsorteo, int idsector, mBoletosSorteo model, SesionDatos sesion){

		String contenido = "";
		model.setIdSorteo(idsorteo);
		model.setIdSector(idsector);
		contenido = model.getBoletosTalonariosSector();
		return contenido;

	}

	protected String getBoletosTalonariosNicho(int idsorteo, int idsector,int idnicho, mBoletosSorteo model, SesionDatos sesion){
		   
		String contenido = "";
		model.setIdSorteo(idsorteo);
		model.setIdSector(idsector);
		model.setIdNicho(idnicho);
		contenido = model.getBoletosTalonariosNicho();
		return contenido;

	}

	protected String getBoletosTalonariosColaborador(int idsorteo, int idsector, int idnicho, int idcolaborador,
			mBoletosSorteo model, SesionDatos sesion) {

		String contenido = "";
		model.setIdSorteo(idsorteo);
		model.setIdSector(idsector);
		model.setIdNicho(idnicho);
		model.setIdColaborador(idcolaborador);
		contenido = model.getBoletosTalonariosColaborador();
		return contenido;

	}
	
	protected String GetComprador(int idsorteo, String boleto, String talonario, mBoletosSorteo model, SesionDatos sesion){
		
		String contenido = "";

		ResultSet rs = null;

		model.setBoleto(boleto);
		model.setIdSorteo(idsorteo);
		rs = model.getComprador();

		try {
			if (rs.next()) {
			                         //ABONO
				contenido = "|"+rs.getString("BOLETO_ABONO")+"|"+rs.getString("NOMBRE")+"|"+rs.getString("APELLIDOS")+"|"+rs.getString("TELEFONOF")+"|"+rs.getString("TELEFONOM")+"|"+rs.getString("CORREO")+"|"+rs.getString("CALLE")+"|"+rs.getString("NUMERO")+"|"+rs.getString("COLONIA")+"|"+rs.getString("ESTADO")+"|"+rs.getString("MUNDEL");
				
			}else{
				
				contenido="NULL";
			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }
		
		return contenido;
	}

	protected String GetIncidenciaBoleto(int idsorteo, String boleto, String talonario, mBoletosSorteo model, SesionDatos sesion){
		
		String contenido = "";
		
		ResultSet rs = null;
		
		model.setBoleto(boleto);
		model.setIdSorteo(idsorteo);
		model.setIdtalonario(Integer.parseInt(talonario));
		
		rs = model.GetIncidenciaBoleto();
		
		try {
			if (rs.next()) {
			                         //ABONO
				contenido = "|"+rs.getString("FORMATO")+"|"+rs.getString("INCIDENCIA")+"|"+rs.getString("FOLIOMP")+"|"+rs.getString("DETALLES");
				
			}else{
				
				contenido="NULL";
			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }
		
		return contenido;
	}

	protected String BuscarMontoAbonoTalonario(int idsorteo, String talonario, mBoletosSorteo model,
			SesionDatos sesion) {

		String contenido = "";

		ResultSet rs = null;

		model.setIdSorteo(idsorteo);
		model.setIdtalonario(Integer.parseInt(talonario));

		rs = model.BuscarMontoAbonoTalonario();

		try {
			if (rs.next()) {
   			                         //ABONO
   				contenido = "|"+rs.getString("MONTO")+"|"+rs.getString("ABONO")+"|"+rs.getString("NUMBOLETOS");

			} else {

				contenido = "NULL";
			}
		} catch (SQLException ex) {
			Factory.Error(ex, "rs=" + rs);
		}

		return contenido;

	}
	
	protected String BuscarBoletosTalonarios(int idsorteo, String talonario, mBoletosSorteo model, SesionDatos sesion){
		String contenido = "";
		ResultSet rs = null;
		String estado = "";
		String retornado = "";
		
		model.setIdSorteo(idsorteo);
		model.setIdtalonario(Integer.parseInt(talonario));
		
		rs = model.BuscarBoletosTalonarios();
		
		try {
			double acumulado=0.0;
			while (rs.next()) {
				
				if(rs.getString("RETORNADO").equals("1")){  retornado = "<i class=\"fa fa fa-check\"></i>";}else{ retornado = ""; }
				if(rs.getString("VENDIDO").equals("N")){  estado = "<span class=\"label label-inverse\" >"+retornado+" "+rs.getString("FOLIO")+"</span>";}
				if(rs.getString("VENDIDO").equals("V")){  estado = "<span class=\"label label-success\" >"+retornado+" "+rs.getString("FOLIO")+"</span>";}
				if(rs.getString("VENDIDO").equals("P")){  estado = "<span class=\"label label-default\" >"+retornado+" "+rs.getString("FOLIO")+"</span>";}
				
				contenido += "<a href=\"javascript:void(0)\" onClick=\"ShowDetalleBoleto('"+rs.getString("SORTEO")+"','"+rs.getString("PK_BOLETO")+"','"+rs.getString("FOLIO")+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"','"+rs.getString("ABONO")+"','"+rs.getString("COSTO")+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"','"+rs.getString("PK_COLABORADOR")+"','Hola')\">"+estado+"</a>&nbsp;";
				
				acumulado += (rs.getDouble("ABONO") == Double.NaN) ? 0.0 :  rs.getDouble("ABONO");
			}
			contenido += "<input id='input_monto_id' type='hidden' value='"+acumulado+"'></input>";
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }  
		
		return contenido;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SesionDatos sesion;
		mBoletosSorteo model = new mBoletosSorteo();

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		
		String operacion = request.getParameter("operacion");
		
		switch (operacion) {
		
			
		
		case "InsertSoloComprador":
			
			model.setNombre(request.getParameter("nombre"));
			model.setApellidos(request.getParameter("apellidos"));
			model.setTelefonof(request.getParameter("telefonofijo"));
			model.setTelefonom(request.getParameter("telefonomovil"));
			model.setCorreo(request.getParameter("correo"));
			model.setCalle(request.getParameter("calle"));
			model.setNumero(request.getParameter("numero"));
			model.setColonia(request.getParameter("colonia"));
			model.setEstado(request.getParameter("estado"));
			model.setMunicipio(request.getParameter("municipio"));
			model.setCP(request.getParameter("cp"));
			model.setUsuario(sesion.nickName);
			
			model.setIdComprador(Integer.parseInt(request.getParameter("idComprador")));
			
			model.registrar_Solo_Comprador(sesion);
		
			break;
		

		case "InsertComprador":
			String extra = request.getParameter("extra");
			if(extra == null)
				extra="";
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("sorteo")));
			/*
			model.setPktalonario(Integer.parseInt(request.getParameter("pktalonario")));
			model.setIdtalonario(Integer.parseInt(request.getParameter("talonario")));
			model.setBoleto(request.getParameter("boleto"));
			*/
			model.setIdBoleto(Integer.parseInt(request.getParameter("pkboleto")));
			/*
			if (request.getParameter("pksector").equals("null")) {model.setIdsector(0);}else{model.setIdsector(Integer.parseInt(request.getParameter("pksector")));}
			if (request.getParameter("pknicho").equals("null")) {model.setIdnicho(0);}else{model.setIdnicho(Integer.parseInt(request.getParameter("pknicho")));}
			if (request.getParameter("pkcolaborador").equals("null")) {model.setIdColaborador(0);}else{model.setIdColaborador(Integer.parseInt(request.getParameter("pkcolaborador")));}
			
			model.setCosto(Double.parseDouble(request.getParameter("costo")));
			*/
			model.setAbono(Double.parseDouble(request.getParameter("abono")));
			
			model.consultaBoleto();

			if(extra=="" || extra.compareTo("SoloComprador")==0)
			{			
				
				model.setNombre(request.getParameter("nombre"));
				model.setApellidos(request.getParameter("apellidos"));
				model.setTelefonof(request.getParameter("telefonofijo"));
				model.setTelefonom(request.getParameter("telefonomovil"));
				model.setCorreo(request.getParameter("correo"));
				model.setCalle(request.getParameter("calle"));
				model.setNumero(request.getParameter("numero"));
				model.setColonia(request.getParameter("colonia"));
				model.setEstado(request.getParameter("estado"));
				model.setMunicipio(request.getParameter("municipio"));
				model.setCP(request.getParameter("cp"));
				
				
			}
			model.setUsuario(sesion.nickName);
			
			if(extra=="")
			{
				// venta y comprador
				model.setComprador(sesion);
			}
			else if (extra.compareTo("SoloVenta")==0)
			{
				model.registrarSoloVenta(sesion);
			}
			else if (extra.compareTo("SoloComprador")==0)
			{				
				model.setIdCompradorA(Integer.parseInt(request.getParameter("idCompradorA")));
				model.setIdComprador(Integer.parseInt(request.getParameter("idCompradorN")));
				model.registrarSoloComprador(sesion);
				
			}
			
			break;
			
			
   
			
		case "eliminarVenta":
			
            model.setIdBoleto(Integer.parseInt(request.getParameter("pkboleto")));
            model.setPktalonario(Integer.parseInt(request.getParameter("pktalonario")));
            
			if (request.getParameter("pksector").equals("null")) {model.setIdSector(0);}else{model.setIdSector(Integer.parseInt(request.getParameter("pksector")));}
			if (request.getParameter("pknicho").equals("null")) {model.setIdNicho(0);}else{model.setIdNicho(Integer.parseInt(request.getParameter("pknicho")));}
			if (request.getParameter("pkcolaborador").equals("null")) {model.setIdColaborador(0);}else{model.setIdColaborador(Integer.parseInt(request.getParameter("pkcolaborador")));}
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("sorteo")));
			model.setIdtalonario(Integer.parseInt(request.getParameter("talonario")));
			model.setBoleto(request.getParameter("boleto"));
			model.setCosto(Double.parseDouble(request.getParameter("costo")));
			model.setUsuario(sesion.nickName);
			model.deleteVenta(sesion);
			
			break;
			
		case "InsertIncidencia":
			
			 model.setIdBoleto(Integer.parseInt(request.getParameter("pkboleto")));
            
			if (request.getParameter("pksector").equals("null")) {model.setIdSector(0);}else{model.setIdSector(Integer.parseInt(request.getParameter("pksector")));}
			if (request.getParameter("pknicho").equals("null")) {model.setIdNicho(0);}else{model.setIdNicho(Integer.parseInt(request.getParameter("pknicho")));}
			if (request.getParameter("pkcolaborador").equals("null")) {model.setIdColaborador(0);}else{model.setIdColaborador(Integer.parseInt(request.getParameter("pkcolaborador")));}
			
			model.setCosto(Double.parseDouble(request.getParameter("costo")));
			model.setAbono(Double.parseDouble(request.getParameter("abono")));
			
			model.setIdSorteo(Integer.parseInt(request.getParameter("sorteo")));
			
			//model.setIdtalonario(Integer.parseInt(request.getParameter("talonario")));			
			model.setIdtalonario(Integer.parseInt(request.getParameter("pktalonario")));
		
			model.setBoleto(request.getParameter("boleto"));
			model.setIncidencia(request.getParameter("incidencia").charAt(0));
			model.setFormatofc8(request.getParameter("formatofc8"));
			model.setFolioactamp(request.getParameter("folioactamp"));
			model.setDetallesincidencia(request.getParameter("detallesincidencia"));
			model.setUsuario(sesion.nickName);
			
			model.RegistrarIncidenciaBoleto(sesion);
			break;
			
		case "EliminarIncidencia":
			model.setIdSorteo(Integer.parseInt(request.getParameter("sorteo")));
			//model.setIdtalonario(Integer.parseInt(request.getParameter("talonario")));
			
			model.setIdtalonario(Integer.parseInt(request.getParameter("pktalonario")));
			
			model.setBoleto(request.getParameter("boleto"));
			model.setIdBoleto(Integer.parseInt(request.getParameter("pkboleto")));				
			
			model.deleteIncidenciaBoleto(sesion);
			
			break;
			
			
		case "RetornoBoletosV":
			
			//model.setIdBoleto(Integer.parseInt(request.getParameter("idboletos")));
				
			String boletoscadena = request.getParameter("idboletos");	
			  System.out.println("cadenabol: "+boletoscadena);
			String[] arrBoletos = boletoscadena.split(",");
		
			model.setIdSorteo(sesion.pkSorteo);
		//	model.setFormato(request.getParameter("folio"));
		
		    model.guardarRetornoBoletos(arrBoletos, model, sesion);
			
			break;	
			
			
	case "RetornoBoletosVTalonarios":					
			
			model.setIdtalonario(Integer.parseInt(request.getParameter("idtalonario")));
			// System.out.println("cadenabol: "+idtalonario);			
		
			model.setIdSorteo(sesion.pkSorteo);
		//	model.setFormato(request.getParameter("folio"));
		
		    model.guardarRetornoBoletosTalonario(model, sesion);
			
			break;	
			
		
		default:
		
		}
		
		
		
	}
	
	
	
	protected String Buscar(HttpServletRequest request, int pg, int show, String search, mBoletosSorteo model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Sector","Nicho","Colaborador" };
		String[] campos = { "FOLIO","PK_ESTADO","COSTO","ABONO","FOLIO_TALONARIO","SECTOR","NICHO","COLABORADOR" };

		model.setIdSorteo(Integer.valueOf(request.getParameter("id_sorteo")));
		int numeroregistros = model.contar(search);
		
		return CrearTabla(numeroregistros, model.paginacion(pg, show, search), columnas, campos, pg, show, sesion);
	}
	
	/**/
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {
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
		.append("<table class='table table-striped table-bordered dataTable no-footer' id='data-table' role='grid' aria-describedby='data-table_info'>")
		.append("<thead>")
		.append("<tr role='row'>");
		
		/*
		if (sesion.sorteoActivo)
			sb
			.append("<th class='sorting' tabindex='0' aria-controls='data-table' rowspan='1' colspan='1' style='width: 50px;' >")
			.append("<input type='checkbox' onClick=\"seleccionarTodo('1b')\" id='checkboxall1b' name='checkboxall' />")
			.append("</th>");
		*/
		
		sb.append("<th class='sorting' tabindex='0' aria-controls='data-table' rowspan='1' colspan='1' style='width: 15px;' aria-label='Browser: activate to sort column ascending'>No.</th>");
		for (String columna : campostable) {
			sb.append(columna);
		}

		sb
		.append(" </thead>")
		.append(" <tbody>");
        
		String electronico = "";
		
		try {
			if (Integer.valueOf(numreg) > 0) {
				while (rs.next()) {
					//String PK_SECTOR = rs.getString("PK_SECTOR");
					//String PK_NICHO = rs.getString("PK_NICHO");
					//String PK_COLABORADOR = null;// rs.getString("PK_COLABORADOR");
					String SECTOR = rs.getString("SECTOR");
					String NICHO = rs.getString("NICHO");
					String COLABORADOR = rs.getString("COLABORADOR");
					String ESTADO = rs.getString("ESTADO");
					

					i++;

					sb
					.append("<tr class='gradeA odd' role='row' >");
					
					// CHECKBOX
					/*if (rs.getInt("RETORNADO") == 1) {
						sb.append("<td class='sorting_1'> <i class='fa fa-2x fa-check-circle'></i> </td>");
					} else {
						sb.append("<td class='sorting_1'> <input  id=").append(rs.getInt("PK1")).append("  class='boletoschecked'  type='checkbox'/> </td>");	
					}
					*/
					
					// CONSECUTIVO
					sb
					.append("<td class='sorting_1'>").append(i).append("</td>");
					
					// FOLIO-BOLETO
					sb.append("<td class='sorting_1'>");
					/*
					sb.append("  <a href=\"javascript:");
					if (PK_COLABORADOR != null)
						sb.append("ShowDetalleBoleto('").append(sesion.pkSorteo).append("','").append(rs.getString("PK1")).append("','").append(rs.getString("FOLIO")).append("','").append(rs.getString("PK_TALONARIO")).append("','").append(rs.getString("FOLIO_TALONARIO")).append("','").append(rs.getString("ABONO")).append("','").append(rs.getString("COSTO")).append("','-H-');");
					else
						sb.append("void(0);");
					sb.append("\">");
					*/
					String INCIDENCIA = rs.getString("INCIDENCIA_CVE").trim();
					if (INCIDENCIA.equals("N")) { sb.append("<span class='label label-primary' >").append(rs.getString("FOLIO")).append("</span>"); }
					if (INCIDENCIA.equals("E")) { sb.append("<span class='label label-warning' >").append(rs.getString("FOLIO")).append("</span>"); }
					if (INCIDENCIA.equals("R")) { sb.append("<span class='label label-danger'  >").append(rs.getString("FOLIO")).append("</span>"); }
					
					//sb.append("</a>");
					sb.append("</td>");
					
					// ESTADO
					sb.append("<td class='sorting_1'>")
						.append("<span class='badge ").append(ESTADO_BOLETO.getEstilo(rs.getString("PK_ESTADO"))).append(" badge-square' >").append(ESTADO).append("</span>")
						.append("</td>");
					
					// COSTO Y ABONO
					sb
					.append("<td class='sorting_1'>").append(rs.getString("COSTO")).append("</td>")
					.append("<td class='sorting_1'>").append(rs.getString("ABONO")).append("</td>");

					// ELECTRONICO 
					electronico = (rs.getString("ELECTRONICO").compareTo("1") == 0)
							? "<i class='fa fa-credit-card'></i>"
							: "<i class='fa fa-pencil'></i>";
					
					// TALONARIO
				//	sb.append("<td class='sorting_1'><a href=\"javascript:ShowDetalleTalonario('").append(sesion.pkSorteo+"','").append(rs.getString("PK_TALONARIO")).append("','").append(rs.getString("FOLIO_TALONARIO")).append("')\">");
					
					
					sb.append("<td class='sorting_1'>");
					
					int TAL_NUM_BOLETOS = rs.getInt("TAL_NUM_BOLETOS");
					int TAL_BOLS_VENDIDOS = rs.getInt("TAL_BOLS_VENDIDOS");
					
					if (0 < TAL_NUM_BOLETOS) {
						if (TAL_BOLS_VENDIDOS == TAL_NUM_BOLETOS)
							sb.append("<span class='label label-success'>");
						else if (TAL_BOLS_VENDIDOS == 0)
							sb.append("<span class='label label-inverse'>");
						else
							sb.append("<span class='label label-default'>");
					}
					sb.append(rs.getString("FOLIO_TALONARIO")).append("</span>").append(" </a>&nbsp;").append(electronico).append("</td>");
					
					// SECTOR
					sb.append("<td class='sorting_1'>");
					if(SECTOR == null)
						sb.append("N/A");
					else
						sb.append(Global.cut(StringEscapeUtils.escapeHtml4(SECTOR)));
						//sb.append("<a href=\"javascript:ShowDetalleSector('").append(sesion.pkSorteo).append("','").append(PK_SECTOR).append("','").append(StringEscapeUtils.escapeHtml4(SECTOR)).append("')\">").append(Global.cut(StringEscapeUtils.escapeHtml4(SECTOR))).append("</a>");
					sb.append("</td>");
					
					// NICHO
					sb.append("<td class='sorting_1'>");
					if(NICHO == null)
						sb.append("N/A");
					else
						sb.append(Global.cut(StringEscapeUtils.escapeHtml4(NICHO)));
						//sb.append("<a href=\"javascript:ShowDetalleNicho('").append(sesion.pkSorteo).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(NICHO).append("')\">").append(StringEscapeUtils.escapeHtml4(NICHO)).append("</a>");
					sb.append("</td>");
					
					// COLABORADOR
					sb.append("<td class='sorting_1'>");
					if (COLABORADOR == null)
						sb.append("N/A");
					else
						sb.append(Global.cut(StringEscapeUtils.escapeHtml4(COLABORADOR)));
						//sb.append("<a href=\"javascript:ShowDetalleColaborador('").append(sesion.pkSorteo).append("','").append(PK_SECTOR).append("','").append(PK_NICHO).append("','").append(PK_COLABORADOR).append("','").append(StringEscapeUtils.escapeHtml4(COLABORADOR)).append("')\">").append(Global.cut(StringEscapeUtils.escapeHtml4(COLABORADOR))).append("</a>");
					sb.append("</td>");
					
					// BOLETO ELECTRONICO
					/*
					sb.append("<td class='sorting_1'>");
					if (rs.getString("DIGITAL").compareTo("1") == 0)
						sb.append("<i class='fa fa-credit-card'></i>");
					else
						sb.append("_<i class='fa fa-pencil'></i>");
					sb.append("</td>");
					//*/
				}
			}
			else {
				sb
				.append("<tr> <td colspan='10'>")
				.append("<div style='width:100%;' class='jumbotron m-b-0 text-center'><h1>No existen Boletos o Talonarios</h1><p>Empiece por asignar o realice otra busqueda.</p></div>")
				.append("</td></tr>");
			}
		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		sb.append("</tbody>");
		sb.append("</table>");

		String paginado = Factory.Paginado(numreg, show, pg);

		sb.insert(0, paginado);

		return sb.toString();
	}
	
	protected String vendido(int num_boletos, int vendidos) {
		if (vendidos == num_boletos && 0 < num_boletos)
			return "V";
		//else if (vendidos == num_boletos && 0 < num_boletos)
		return "N";
	}
	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mBoletosSorteo model, SesionDatos sesion)
			throws ServletException, IOException, SQLException {
		
		//model.setIdsorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Boletos.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("BOLETOS");	
		
		
		//String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Sector","Nicho","Colaborador" };
		//String[] campos = { "FOLIO","VENDIDO","COSTO","ABONO","TALONARIO","SECTOR","NICHO","COLABORADOR" };		
		
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("Boleto");
		cell = row.createCell(1);
		cell.setCellValue("Estatus");
		cell = row.createCell(2);
		cell.setCellValue("Costo");
		cell = row.createCell(3);
		cell.setCellValue("Abono");
		cell = row.createCell(4);
		cell.setCellValue("Talonario");
		cell = row.createCell(5);
		cell.setCellValue("Sector");
		cell = row.createCell(6);
		cell.setCellValue("Nicho");
		cell = row.createCell(7);
		cell.setCellValue("Colaborador");				
	
		
		String electronico="";
		model.setIdSorteo(sesion.pkSorteo);
		ResultSet rs = model.paginacion(pg, Integer.MAX_VALUE, search);
		
		int fila = 1;
		
		while (rs.next()) {
			try{
				row = sheet.createRow(fila);
				
				//String[] campos = { "FOLIO","VENDIDO","COSTO","ABONO","TALONARIO","SECTOR","NICHO","COLABORADOR" };		
				//CLAVE
				cell = row.createCell(0);
				cell.setCellValue(rs.getString("FOLIO"));
				
				//NOMBRE
				cell = row.createCell(1);
				cell.setCellValue(vendido(rs.getInt("TAL_NUM_BOLETOS"), rs.getInt("TAL_BOLS_VENDIDOS")));			
				
				//APATERNO
				cell = row.createCell(2);
				cell.setCellValue(rs.getString("COSTO"));
				
				//AMATERNO
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("ABONO"));
				
				// DIGITAL 
				electronico = (rs.getString("ELECTRONICO").compareTo("1") == 0)
						? "e"
						: "f";
				
				
				//COMISION
				cell = row.createCell(4);
				cell.setCellValue(rs.getString("FOLIO_TALONARIO")+" "+electronico);
				
				//RFC
				cell = row.createCell(5);
				cell.setCellValue(rs.getString("SECTOR"));
				
				//EDAD
				cell = row.createCell(6);
				cell.setCellValue(rs.getString("NICHO"));
				
				//REFBANCARIA
				cell = row.createCell(7);
				cell.setCellValue(rs.getString("COLABORADOR"));		
				
				fila++;
			}
			catch(Exception ex) {
				cell = row.createCell(8);
				cell.setCellValue("---Limite de renglones ---");
				break;
			}
		}
		
		wb.close();

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);

		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		
		
	}
	
}
