package com.sorteo.ventas.controller;

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

import org.apache.commons.lang3.StringEscapeUtils;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.dashboard.model.mDashboard;
import com.sorteo.ventas.model.mCompradores;

/**
 * Servlet implementation class Compradores
 */
@WebServlet("/Compradores")
public class Compradores extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Compradores() {
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
		mCompradores model = new mCompradores();

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
		
		
		case "listsorteos":
			
			menu = vista.initMenu(0, false, 19, 15, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			
			HTML = getSorteosUsuarios(request,response,HTML, sesion);
			break; 
		
		
           case "ListaSorteos":
        	
        	SorteoPredeterminado(request,response,HTML, model, sesion);
        	
			break;
		

            case "getBoletosTalonarios":
           // int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			HTML = getBoletosTalonarios(model, sesion);
			break;
			
			
       case "getBoletosTalonariosSector":
                int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
                int idsector = Integer.valueOf(request.getParameter("sector"));
     			HTML = getBoletosTalonariosSector(idsorteo,idsector, model);
     			break;
     			
     			
       case "getBoletosTalonariosNicho":
            	idsorteo = Integer.valueOf(request.getParameter("sorteo"));
                idsector = Integer.valueOf(request.getParameter("sector"));
                int idnicho = Integer.valueOf(request.getParameter("nicho"));
     			HTML = getBoletosTalonariosNicho(idsorteo,idsector,idnicho, model);
            	break;
            	
       case "getBoletosTalonariosColaborador":
            	idsorteo = Integer.valueOf(request.getParameter("sorteo"));
                idsector = Integer.valueOf(request.getParameter("sector"));
                idnicho = Integer.valueOf(request.getParameter("nicho"));
                //int colaborador = Integer.valueOf(request.getParameter("colaborador"));
                HTML = getBoletosTalonariosNicho(idsorteo,idsector,idnicho, model);
            	break;
			
		
	   case "GetComprador":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			String folio = request.getParameter("boleto");
			String talonario = request.getParameter("talonario");
			HTML = GetComprador(idsorteo,folio,talonario, model);
			break;
			
		case "GetIncidenciaBoleto":
			
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			folio = request.getParameter("boleto");
			talonario = request.getParameter("talonario");
			
			HTML = GetIncidenciaBoleto(idsorteo,folio,talonario, model);
			break;
			
		
		case "BuscarMontoAbonoTalonario":
			
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			talonario = request.getParameter("talonario");
			HTML = BuscarMontoAbonoTalonario(idsorteo,talonario, model);
			break;
			
		case "BuscarBoletosTalonarios":
			idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			talonario = request.getParameter("talonario");
			HTML = BuscarBoletosTalonarios(idsorteo,talonario, model);
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
			menu = vista.initMenu(0, false, 19, 15, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			
			break;		

		default:
			
			idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
			
			fullPath = "/WEB-INF/views/ventas/compradores.html";
			menu = vista.initMenu(0, false, 19, 15, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
			}

			model.setIdSorteo(sesion.pkSorteo);
            model.Sorteo();
			String dato = model.getSorteo();
			
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
	
	
	protected String getSorteosUsuarios(HttpServletRequest request, HttpServletResponse response, String HTML, SesionDatos sesion) throws ServletException, IOException {
		   
		String contenido = "<div  class=\"jumbotron m-b-0 text-center\"><h1>No existen Sorteos</h1><p>Empieze por agregar un nuevo sorteo.</p></div>";
		   
		   String regex = "#SORTEOSUSUARIOS#";
		   ResultSet rs = null;
		   
		   mDashboard modelsorteos= new mDashboard(); 
		   
		   modelsorteos.setIdUsuario(sesion.pkUsuario);
		   modelsorteos.cosultaPredeterminados();
		   
		   int sorteo = modelsorteos.getIdSorteo();
		   int sector = modelsorteos.getIdSector();
		   int nicho = modelsorteos.getIdNicho();
		 
		   
		   //EXISTE USUARIO NIVEL SORTEO
		   if(modelsorteos.ContarSorteosUsuarios()!=0){
			        
			   rs = modelsorteos.getSorteosUsuario();
			   try {
				while (rs.next()) {
					
					   contenido += "<p>";
					   contenido +="<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSorteo('"+rs.getString("PK1")+"')\"  role=\"button\" class=\"btn btn-success btn-lg\">";
					  
					   
					      if(sorteo==rs.getInt("PK1")){
						   contenido +="<i class=\"fa fa-2x fa-check-circle\"></i> ";
						   }
					   
					   contenido += rs.getString("SORTEO")+"</a> ";
					   contenido += "</<p>";
				   
				   }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 //NIVEL SECTORES USUARIOS
		   }else if(modelsorteos.ContarSectoresUsuarios()!=0){
			   
			   System.out.println("entra aqui prueba");
			   
			   rs = modelsorteos.getSectoresUsuario();
			   
			   try {
					while (rs.next()) {
						
						   contenido += "<p>";
						   contenido +="<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSector('"+rs.getString("PK_SORTEO")+"','"+rs.getString("PK_SECTOR")+"')\"  role=\"button\" class=\"btn btn-success btn-lg\">";
						  
						   if(sector==rs.getInt("PK_SECTOR")){
							   contenido +="<i class=\"fa fa-2x fa-check-circle\"></i> ";
							   }
						   
						   contenido += rs.getString("SORTEO")+" <br> <small>"+rs.getString("SECTOR")+"</small></a> ";
						   contenido += "</<p>";
					   
					   }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			   //NIVEL NICHOS USUARIOS
		   }else if(modelsorteos.ContarNichosUsuarios()!=0){
			   
			   System.out.println("entra aqui prueba");
			   
			   rs = modelsorteos.getNichosUsuario();
			   
			   try {
					while (rs.next()) {
						
						   contenido += "<p>";
						   contenido +="<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardNicho('"+rs.getString("PK_SORTEO")+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"')\"  role=\"button\" class=\"btn btn-success btn-lg\">";
						   if(nicho==rs.getInt("PK_NICHO")){
							   contenido +="<i class=\"fa fa-2x fa-check-circle\"></i> ";
							   }
						   contenido += rs.getString("SORTEO")+"<br> <small>"+rs.getString("NICHO")+"</small></a> ";
						   contenido += "</<p>";
					   
					   }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			   
		   }
		   
		   HTML = HTML.replaceAll(regex, contenido);
		   
		   return HTML;
	   }
	
	 protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, String HTML, mCompradores model, SesionDatos sesion) throws ServletException, IOException {
			
		   model.setIdUsuario(sesion.pkUsuario);
		   model.getUsuarioSorteo();
		  
		   long sorteo = model.getIdSorteo();
		   long sector = model.getIdsector();
		   long nicho = model.getIdnicho();
		   System.out.println(sorteo);

					if(sorteo!=0 && sector==0 && nicho==0){
						response.sendRedirect("Compradores?idsorteo="+sorteo);	
					}else if(sorteo!=0  && sector!=0 && nicho==0){
						response.sendRedirect("CompradoresSectores?idsorteo="+sorteo+"&idsector="+sector);
					}else if(sorteo!=0  && sector!=0 && nicho!=0){
						response.sendRedirect("CompradoresNichos?idsorteo="+sorteo+"&idsector="+sector+"&idnicho="+nicho);
					}else{
						response.sendRedirect("Compradores?view=listsorteos");
					}
	   }
	
	
	
	 protected String getBoletosTalonarios(mCompradores model, SesionDatos sesion){
		   
		   String contenido = "";
		   model.setIdSorteo(sesion.pkSorteo);
		   contenido = model.getBoletosTalonarios();
		   return contenido;
		   
	   }
	   
	   
	   
	   
	   
   protected String getBoletosTalonariosSector(int idsorteo, int idsector, mCompradores model){
		   
		   String contenido = "";
		   model.setIdSorteo(idsorteo);
		   model.setIdsector(idsector);
		   contenido = model.getBoletosTalonariosSector();
		   return contenido;
		   
	   }
   
   
  protected String getBoletosTalonariosNicho(int idsorteo, int idsector,int idnicho, mCompradores model){
		   
		   String contenido = "";
		   model.setIdSorteo(idsorteo);
		   model.setIdsector(idsector);
		   model.setIdnicho(idnicho);
		   contenido = model.getBoletosTalonariosNicho();
		   return contenido;
		   
	}

	protected String getBoletosTalonariosColaborador(int idsorteo,
			int idsector, int idnicho, int idcolaborador, mCompradores model) {

		String contenido = "";
		model.setIdSorteo(idsorteo);
		model.setIdsector(idsector);
		model.setIdnicho(idnicho);
		model.setIcolaborador(idcolaborador);
		contenido = model.getBoletosTalonariosColaborador();
		return contenido;

	}

	protected String GetComprador(int idsorteo, String boleto,
			String talonario, mCompradores model) {

		String contenido = "";
		
		ResultSet rs = null;
		
		model.setBoleto(boleto);
		model.setIdSorteo(idsorteo);
		rs = model.getComprador();
		
		try {
			if (rs.next()) {
			                         //ABONO
				contenido = "|"+rs.getString("ABONO")+"|"+rs.getString("NOMBRE")+"|"+rs.getString("APELLIDOS")+"|"+rs.getString("TELEFONOF")+"|"+rs.getString("TELEFONOM")+"|"+rs.getString("CORREO")+"|"+rs.getString("CALLE")+"|"+rs.getString("NUMERO")+"|"+rs.getString("COLONIA")+"|"+rs.getString("ESTADO")+"|"+rs.getString("MUNDEL");
				
			}else{
				
				contenido="NULL";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return contenido;
	}
	
	
	
   protected String GetIncidenciaBoleto(int idsorteo, String boleto, String talonario, mCompradores model){
		
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return contenido;
	}
	
	
   
   
   protected String BuscarMontoAbonoTalonario(int idsorteo, String talonario, mCompradores model){
	   
	String contenido = "";
		
		ResultSet rs = null;
		
		model.setIdSorteo(idsorteo);
		model.setIdtalonario(Integer.parseInt(talonario));
		
		rs = model.BuscarMontoAbonoTalonario();
		
		try {
			if (rs.next()) {
			                         //ABONO
				contenido = "|"+rs.getString("MONTO")+"|"+rs.getString("ABONO")+"|"+rs.getString("NUMBOLETOS");
				
			}else{
				
				contenido="NULL";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return contenido;
	   
   }
	
   protected String BuscarBoletosTalonarios(int idsorteo, String talonario, mCompradores model){
	   String contenido = "";
	   ResultSet rs = null;
	   String estado = "";
	   
	   
		model.setIdSorteo(idsorteo);
		model.setIdtalonario(Integer.parseInt(talonario));
		
		rs = model.BuscarBoletosTalonarios();
		
		try {
			while (rs.next()) {
				
				
				if(rs.getString("VENDIDO").equals("N")){  estado = "<span class=\"label label-inverse\" >"+rs.getString("FOLIO")+"</span>";}
				if(rs.getString("VENDIDO").equals("V")){  estado = "<span class=\"label label-success\" >"+rs.getString("FOLIO")+"</span>";}
				if(rs.getString("VENDIDO").equals("P")){  estado = "<span class=\"label label-default\" >"+rs.getString("FOLIO")+"</span>";}
				
				contenido += "<a href=\"javascript:void(0)\" onClick=\"ShowDetalleBoleto('"+rs.getString("SORTEO")+"','"+rs.getString("PK_BOLETO")+"','"+rs.getString("FOLIO")+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"','"+rs.getString("ABONO")+"','"+rs.getString("COSTO")+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"','"+rs.getString("PK_COLABORADOR")+"')\">"+estado+"</a>&nbsp;";
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   
	   return contenido;
   }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mCompradores model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto","Estatus","Costo","Abono","Talonario","Comprador","Sector","Nicho","Colaborador"};
		String[] campos = { "FOLIO","VENDIDO","COSTO","ABONO","TALONARIO","COMPRADOR","SECTOR","NICHO","COLABORADOR"};

        int numeroregistros = model.contar(search,sesion.pkSorteo);

		
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search,sesion.pkSorteo), columnas, campos, pg, show, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {

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

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 12px;\" ></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {

			html += columna;

		}
		
	

		html += " </thead>";
		html += " <tbody>";
        
		String estado= "";
		String estadotalonario ="";
		
		
		try {
			
	if (Integer.valueOf(numreg) > 0) {		
			
			while (rs.next()) {

				i++;

				html += "<tr class=\"gradeA odd\" role=\"row\">";
				
				html += "<td class=\"sorting_1\"><input  id=" + rs.getInt("PK1") + "  type=\"checkbox\" /></td>";
				html += "<td class=\"sorting_1\">" + i + "</td>";

				  html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+rs.getString("PK_BOLETO")+"','"+rs.getString("FOLIO")+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"','"+rs.getString("ABONO")+"','"+rs.getString("COSTO")+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"','"+rs.getString("PK_COLABORADOR")+"')\">";
					
					
					if(rs.getString("INCIDENCIA").equals("N")){  html += "<span class=\"label label-primary\" >"+rs.getString("FOLIO")+"</span>";}
					if(rs.getString("INCIDENCIA").equals("E")){  html += "<span class=\"label label-warning\" >"+rs.getString("FOLIO")+"</span>";}
					if(rs.getString("INCIDENCIA").equals("R")){  html += "<span class=\"label label-danger\" >"+rs.getString("FOLIO")+"</span>";}
					
					html += "</a></td>";
					
					if(rs.getString("VENDIDO").equals("N")){  estado = "<span class=\"badge badge-inverse badge-square\" >NO VENDIDO</span>";}
					if(rs.getString("VENDIDO").equals("V")){  estado = "<span class=\"badge badge-success badge-square\" >VENDIDO</span>";}
					if(rs.getString("VENDIDO").equals("P")){  estado = "<span class=\"badge badge-default badge-square\" >VENDIDO (P)</span>";}	
				
				html += "<td class=\"sorting_1\">" + 
						estado
						+ "</td>";
				
				html += "<td class=\"sorting_1\">" + rs.getString("COSTO")
						+ "</td>";
				
				html += "<td class=\"sorting_1\">" + rs.getString("ABONO")
						+ "</td>";
				
				
				if(rs.getString("VENDIDOTALONARIO").equals("N")){  estadotalonario = "<span class=\"label label-inverse\" >"+rs.getString("TALONARIO")+"</span>";}
				if(rs.getString("VENDIDOTALONARIO").equals("V")){  estadotalonario = "<span class=\"label label-success\" >"+rs.getString("TALONARIO")+"</span>";}
				if(rs.getString("VENDIDOTALONARIO").equals("P")){  estadotalonario = "<span class=\"label label-default\" >"+rs.getString("TALONARIO")+"</span>";}
				
				
				html += "<td class=\"sorting_1\"><a href=\"javascript:ShowDetalleTalonario('"+sesion.pkSorteo+"','"+rs.getString("PK_TALONARIO")+"','"+rs.getString("TALONARIO")+"')\">" + estadotalonario
						+ "</a></td>";
				
				/*html += "<td class=\"sorting_1\">" + rs.getString("COMPRADOR")
						+ "</td>";*/
				
				html += "<td class=\"sorting_1\">" + ((rs.getString("COMPRADOR") == null) ?  "N/A" : "<span>"+StringEscapeUtils.escapeHtml4(rs.getString("COMPRADOR"))+"</span>")
						
						+"</td>";			
				

				html += "<td class=\"sorting_1\">" + ((rs.getString("SECTOR") == null) ?  "N/A" : "<a href=\"CompradoresSectores?idsorteo="+sesion.pkSorteo+"&idsector="+ rs.getString("PK_SECTOR")+  "\">"+StringEscapeUtils.escapeHtml4(rs.getString("SECTOR"))+"</a>")
						+ "</td>";
				
				html += "<td class=\"sorting_1\">" + ((rs.getString("NICHO") == null) ?  "N/A" : "<a href=\"CompradoresNichos?idsorteo="+sesion.pkSorteo+"&idsector="+ rs.getString("PK_SECTOR")+"&idnicho="+rs.getString("PK_NICHO")+  "\">"+StringEscapeUtils.escapeHtml4(rs.getString("NICHO"))+"</a>")
						+ "</td>";
				
				html += "<td class=\"sorting_1\">" + ((rs.getString("COLABORADOR") == null) ?  "N/A" : "<a href=\"CompradoresColaborador?idsorteo="+sesion.pkSorteo+"&idsector="+ rs.getString("PK_SECTOR")+"&idnicho="+rs.getString("PK_NICHO")+ "&idcolaborador="+rs.getString("PK_COLABORADOR")+ " \">"+StringEscapeUtils.escapeHtml4(rs.getString("COLABORADOR"))+"</a>")
						+ "</td>";

				
				
				

			}
			
			
			
		} else {
					
					html += "<tr> <td colspan=\"11\"> ";
					html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
					html += "<h1>No existen Compradores</h1>";
					//html += "<p>Asigne Talonarios al Sorteo.</p>";
					html += "</div>";
					html += "</td></tr>";
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
	
	
	
	
}
