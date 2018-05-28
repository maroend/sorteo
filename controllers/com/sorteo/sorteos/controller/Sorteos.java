package com.sorteo.sorteos.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.Global;
import com.core.MENU;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.sorteo.herramientas.controller.ProgressBarCalc;
import com.sorteo.sorteos.model.mSorteos;

/**
 * Servlet implementation class Sorteos
 */
@WebServlet("/Sorteos.do")
public class Sorteos extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sorteos() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mSorteos model = new mSorteos();
		SesionDatos sesion;
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;		
		
		
		String view = null;
		
		
	
		 model.setIdUsuario(sesion.pkUsuario);
		 int idrole = model.ObtenerRole();	
		 
		 if(idrole == -1){
			 view = "error";
			System.out.println("no tiene rol");
			System.out.println("view: "+view);		 
		 
		 }
		 else{			 
			 if(idrole != 2 && !sesion.permisos.havePermiso(40135)){//si no tiene permiso para ver todo la lista de sorteo			 
				 try {
					    sesion.misSorteos = 1;
						sesion.guardaSesion();					
								
				} catch(Exception ex) { Factory.Error(ex, "MisSorteos.do"); }
			 }else{		//SI ROL ES igual a ADMIN(2) o si tiene permiso para ver todo 			 
				 try {			 
					    sesion.misSorteos = 0;
						sesion.guardaSesion();	
						//setPredeterminado(request, response, model, sesion);
					} catch(Exception ex) { Factory.Error(ex, "TodosSorteos"); } 			 
			 }			 
			 
		 }	
		
		
	
		view = request.getParameter("view");
		
		if (view == null) {view = "";}
		
		//String p_missorteos = request.getParameter("missorteos");
		//int missorteos = p_missorteos == null ? 0 : Integer.valueOf(p_missorteos);
       
		//10013 ACCESO A LA LISTA DE SORTEOS
		
		
		
			if (!sesion.permisos.havePermiso(10013))
			{
				view = "error";
				System.out.println("no tiene permisos");
				System.out.println("view: "+view);
				
			}else{
				
				System.out.println("<--------------SORTEO_activo:-----------> "+sesion.sorteoActivo);
				 
				 if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){
					 
					 view = "errorCerrado";
					 
				 }
				 
			 }
		
		
		
		
		/*if (sesion.misSorteos == 1){
			if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){
				
				view = "errorCerrado";
				
			}
		}
		
		
		else {
			if (!sesion.permisos.havePermiso(10013))
			{
				view = "error";
				System.out.println("no tiene permisos");
				System.out.println("view: "+view);
				
			}else{
				
				System.out.println("<--------------SORTEO_activo:-----------> "+sesion.sorteoActivo);
				 
				 if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){
					 
					 view = "errorCerrado";
					 
				 }
				 
			 }
		}*/
		 
		System.out.println("view2: "+view);
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int idSorteo = 0;
		

		switch (view) {
		
		case "ProgressBar":
			
			HTML = "" + model.getProcentajeCargaBoletos(sesion.pkSorteo);
			
	    break;
		
		
		case "errorCerrado":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
		

		case "Agregar":
			fullPath = "/WEB-INF/views/sorteos/agregar.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
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
		
		case "ExisteCarga":
			
			idSorteo = Integer.valueOf(request.getParameter("sorteo"));
			
			if (sesion.pkSorteo != idSorteo) {
				sesion.pkSorteo = idSorteo;
				sesion.guardaSesion();
				System.out.println(sesion.toString());
			}
			
			if(this.ExisteCarga(idSorteo, model)){
				HTML = "TRUE";
			}
			
			break;
			
	        case "EliminarCarga":
			idSorteo = Integer.valueOf(request.getParameter("sorteo"));
			if(this.EliminarCarga(idSorteo, model, sesion)){
				HTML = "TRUE";
			}
			
			break;
		

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "error":
			
			System.out.println("error");
			 
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("error1221");	
			 
			break;

		default:

			/*if (sesion.idMenu != MENU.SORTEOS || sesion.idSubMenu != MENU.LISTA_SORTEOS) {
				sesion.idMenu = MENU.SORTEOS;
				sesion.idSubMenu = MENU.LISTA_SORTEOS;
				sesion.guardaSesion();
			}*/			
			
			
              int idsubmenu;				
			
			if (sesion.misSorteos == 1)
				idsubmenu = MENU.MIS_SORTEOS; // mis sorteos 33
			else
				idsubmenu = MENU.LISTA_SORTEOS; // todos 34
			

			if (sesion.idMenu != MENU.SORTEOS || sesion.idSubMenu != idsubmenu) {
				sesion.idMenu = MENU.SORTEOS;
				sesion.idSubMenu = idsubmenu;
				//sesion.misSorteos = missorteos;
				sesion.guardaSesion();
			}
			
				if (redirecciona(response, model, sesion)) {
				model.close();
				return;
			}


			fullPath = "/WEB-INF/views/sorteos/sorteos.html";
			menu = vista.initMenu(0, false, sesion.idMenu, sesion.idSubMenu, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			//_________________________________________
			//PERMISO BOTON
			
			HTML = putCode(HTML, 10091, "#BTN_AGREGAR_SORTEO#", "",
					"<a class=\"btn btn-primary m-r-5\" href=\"Sorteos.do?view=Agregar\">Agregar Sorteo</a>"
					,sesion);
			/*
			HTML = putCode(HTML, 10099, "#BTN_CARGA_BOLETOS_BOVEDA#",
					"<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\" disabled=\"disabled\"  role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>"
					+ "Carga boletos Boveda</a>",
					"<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"mostrarFormato('cargaboletos')\"  role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-2x fa-exchange\" style=\"margin-right:10pt;\"></i>"
					+ "Carga boletos Boveda</a>"
					,sesion);
			*/
			HTML = putCode(HTML, 10100, "#BTN_INICIO_CIERRE#",
					"<a href=\"javascript:void(0)\" style=\"width:100%;\"   disabled=\"disabled\" role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> "
					+ "Dar inicio/Cierre Sorteo</a>", 
					"<a href=\"javascript:void(0)\" style=\"width:100%;\"  onClick=\"mostrarFormato('iniciocierre');\"  role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-2x fa-bullhorn\" style=\"margin-right:10pt;\"></i> "
					+ "Dar inicio/Cierre Sorteo</a>"
					,sesion);
			/*
			HTML = putCode(HTML, 10101, "#BTN_ELIMINAR_ASIGNACION#",
					"<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btndeletecargaboletos\"   disabled=\"disabled\" role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>"
					+ "Eliminar Asignación de Boletos</a>",
					"<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btndeletecargaboletos\"   onClick=\"mostrarFormato('deletecargaboletos')\"  role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-2x fa-history\" style=\"margin-right:10pt;\"></i>"
					+ "Eliminar Asignaci&oacute;n de Boletos</a>"
					,sesion);
			HTML = putCode(HTML, 30114, "#BTN_ELIMINAR_SORTEO#",
					"<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btneliminarsorteo\"  disabled=\"disabled\"  role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>"
					+ "Eliminar Sorteo</a>",
					"<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btneliminarsorteo\"  onClick=\"mostrarFormato('deletesorteo')\"  role=\"button\" class=\"btn btn-success btn-lg\">"
					+ "<i class=\"fa fa-2x fa-times\" style=\"margin-right:10pt;\"></i>"
					+ "Eliminar Sorteo</a>"
					,sesion);
			*/
			HTML = HTML
					.replaceFirst("#BTN_CARGA_BOLETOS_BOVEDA#", "")
					.replaceFirst("#BTN_ELIMINAR_ASIGNACION#", "")
					.replaceFirst("#BTN_ELIMINAR_SORTEO#", "");

			break;

		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	private String putCode(String HTML, int pkPermiso, String mark, String if_not_have, String if_have, SesionDatos sesion){
		
		String str_put;
		if (sesion.permisos.havePermiso(pkPermiso))
			str_put = if_have;
		
		else
			str_put = if_not_have;
		return HTML.replaceAll(mark, str_put);
		
	}
	
	
	private boolean ExisteCarga(int idSorteo, mSorteos model){
		
		model.setId(idSorteo);
		return model.ExisteCarga(model);
	
	}
	
	
	
   private boolean EliminarCarga(int idSorteo, mSorteos model, SesionDatos sesion){
		
		model.setId(idSorteo);
		return model.EliminarCarga(model, sesion);
	
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		UploadFile Upload = new UploadFile();
		mSorteos model = new mSorteos();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String insertar = request.getParameter("insertar") == null ? "" : request.getParameter("insertar");
		String eliminar = request.getParameter("eliminar") == null ? "" : request.getParameter("eliminar");
		String activar = request.getParameter("activar") == null ? "" : request.getParameter("activar");
		String cargar = request.getParameter("cargar") == null ? "" : request.getParameter("cargar");
		
		if (insertar.equals("insertar")) {
			
			model.setClave(request.getParameter("clave"));
			model.setSorteo(request.getParameter("sorteo"));
			model.setDescripcion(request.getParameter("descripcion"));
			model.setFechainico(request.getParameter("fechai"));
			model.setFechatermino(request.getParameter("fechat"));
			model.setImagen(request.getParameter("imagen"));
			model.registrar(model, sesion);
		}
		
		else if (activar.equals("activar")) {
			model.setId(Integer.parseInt(request.getParameter("idsorteo")));
			int res = model.activarSorteo(true, sesion);
			
			PrintWriter out = response.getWriter();
			out.println("<result>"+res+"</result>");
		}
		
		else if (activar.equals("desactivar")) {
			model.setId(Integer.parseInt(request.getParameter("idsorteo")));
			int res = model.activarSorteo(false, sesion);
			
			PrintWriter out = response.getWriter();
			out.println("<result>"+res+"</result>");
		}
		
		else if (eliminar.equals("eliminar")) {
			
			model.setId(Integer.parseInt(request.getParameter("sorteo")));
			model.eliminar(model, sesion);
			
		}

		else if (cargar.equals("cargar")) {
			ProgressBarCalc progress = new ProgressBarCalc(sesion);
			progress.prepare();
			
			model.setId(Integer.parseInt(request.getParameter("sorteo")));
			
			if(model.ExisteCarga(model))
				return;
			
			int maxItems =
					model.numeroTalonariosBoveda(model) +
					model.numeroBoletosBoveda(model);
			
			progress.init(maxItems);
			
			model.cargartalonarios(model, sesion, progress);
			model.cargarboletos(model, sesion, progress);
			model.setCarga(model, sesion);
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String boletosprocesados =  model.getNumTalonarios()+":"+model.getNumBoletos();
			out.println(boletosprocesados);
			
		}

		else {

			String fullPathinfouser = "/uploads/";
			String fullPathtemp = "/temp/";

			ServletContext context = getServletContext();

			String filePath = context.getRealPath(fullPathinfouser);
			String Pathtemp = context.getRealPath(fullPathtemp);
			

			Upload.uploadFile(request, Pathtemp, filePath);

		}

		model.close();
	}

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mSorteos model, SesionDatos sesion)
			throws ServletException, IOException {
		// Primero se asigna el id de usuario para saber si se tiene que filtrar por usuario.
		if (sesion.misSorteos == 0)
			model.setIdUsuario(-1);               // todos
		else
			model.setIdUsuario(sesion.pkUsuario); // mis sorteos
		
		int numeroregistros = model.contar();
		
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search, sesion), pg, show, model);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, mSorteos model)
	{
		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		int numerotalonarios = 0;
		int numeroboletos = 0;
		double monto = 0;
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

		String html = "<ul class=\"result-list\">";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {
					int PK_SORTEO = rs.getInt("PK1");
					int ACTIVO = rs.getInt("BLOQUEO");//ACTIVO

					model.setId(PK_SORTEO);
					numerotalonarios = model.numeroTalonarios(model);
					numeroboletos = model.numeroBoletos(model);

					monto = (double) model.montoSorteo(model);

					html += "<li id=\"sorteo" + PK_SORTEO + "\" style='" + (ACTIVO==1?"":Global.bkground_cerrado) + "' >";
					html += "<div class=\"result-image\" ";//style=\"width:20% !important;\">";
					
					html += "<a href='javascript:;' style='width:189px;'><img alt='' style='width:189px;' src='assets/img/sorteo.jpg'\\></a>";
					html += "</div>";
					
					
					html += "<div class=\"result-info \">";
					html += "<h4 class=\"title\">";
					if (ACTIVO==0)
						html += "  <span class=\"label label-warning\"><img style=\"height:30px; vertical-align: middle;\" src=\"assets/img/icono-candado-inv.png\"></span>";
					html += "  <span class=\"label label-primary\">" + rs.getString("CLAVE") + "</span>";
					html += "  <a href=\"BoletosSorteo?idsorteo=" + PK_SORTEO + "\">" + rs.getString("SORTEO") + "</a>";
					html += "</h4>";
					html += "<p class=\"location\">TALONARIOS: <span class=\"badge badge-danger pull\">"
						+ numerotalonarios
						+ "</span> BOLETOS: <span class=\"badge badge-danger pull\">"
						+ numeroboletos + "</span> </p>";
					
					html += "<p style='height:10px;'>&nbsp;</p>";

					html += "<div class=\"btn-row\">";
					html += "<a data-title=\"Responsables Asignados\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SorteosUsuarios?idsorteo=" + PK_SORTEO +                         "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-child\"></i><span style=\"font-size:11px;\">Responsables</span></a>";
					html += "<a data-title=\"Sorteos especiales\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SorteosParalelos?idsorteo=" + PK_SORTEO +                            "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-pie-chart\"></i> <span style=\"font-size:11px;\">Sorteos Especiales</span></a>";
					html += "<a data-title=\"Opciones del Sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:configuracion(" + PK_SORTEO + "," + ACTIVO +           ");\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-cog\"></i><span style=\"font-size:11px;\">Opciones</span></a>";
					html += "<a data-title=\"Muestra las estadisticas y datos de avance del sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"Dashboard.do?sorteo="+PK_SORTEO + "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tachometer\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";
					html += "<a data-title=\"Sectores asignados al sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SectoresAsignados?idsorteo=" + PK_SORTEO +                 "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-building\"></i><span style=\"font-size:11px;\">Sectores</span></a>";
					html += "</div>";

					html += "</div>";

					html += "<div class=\"result-price\">";
					html += englishFormat.format(monto) + " <small>MONTO</small>";					
					html += "<a class=\"btn btn-inverse btn-block\" href=\"SorteoDetalle?sorteo="+PK_SORTEO+"\">Ver Detalles</a>";
					
					html += "</div>";

					html += "</li>";

					//System.out.println("###"+rs.getString("SORTEO"));
					
				}
				
			} else {
				html += "<li align= \"center\">";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";
				html += "<h1>No existen Sorteos</h1>";
				html += "<p>Empiece por agregar un nuevo sorteo.</p>";
				html += "</div>";
				html += "</li>";
			}

			html += "</ul>";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Factory.Error(e, "rs="+rs);
		}

		/**
		int numpag = Math.round(numreg / show);
		//int denumpag = numpag + 1;

		String paginado = "<ul class=\"pagination pagination-without-border pull-right m-t-0\">";

		if (pg > 1) {
			int pagante = pg - 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag(" + pagante + ");\">Anterior</a></li>";
		} else {
			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Anterior</a></li>";
		}

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
					paginado += "<li class=\"active\"><a href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag + ");\">" + sumpag + "</a>";
				} else {
					paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag + ");\">" + sumpag + "</a>";
				}

			}
		}

		if (pg <= numpag) {
			int numeropag = pg + 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag("
					+ numeropag + ");\">Siguiente</a></li>";

		} else {

			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Siguiente</a></li>";
		}

		paginado += "</ul>";
		/**/
		String paginado = Factory.paginado_2(numreg, show, pg);

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		html = paginado + html + paginadoright;

		return html;

	}
	
	public boolean redirecciona(HttpServletResponse response, mSorteos model, SesionDatos sesion) {
		
		
		// mis sorteos
		if (sesion.idMenu == 2 && sesion.idSubMenu == 33 ) {
			model.setIdUsuario(sesion.pkUsuario);

			// PASO 1)  Contar SORTEOS
			
			if (0 == model.contarSorteosAsignados()) {

				// PASO 2)  Contar Sectores

				if (0 < model.contarSectoresAsignados()) {

					// Si hay sectores, obtenemos el id de sorteo ligado
					sesion.pkSorteo = model.consultaIdSorteoDesdeSectores();
					sesion.guardaSesion();
					try {
						response.sendRedirect("SectoresAsignados?idsorteo=" + sesion.pkSorteo);
						return true;
					} catch(Exception ex) { }
				}
				else {

					// PASO 3)  Contar Nichos
					
					if (0 < model.contarNichosAsignados()) {
						
						// Si hay nichos, obtenemos los ids de sorteo y sector ligados
						sesion.pkSorteo = model.consultaIdSorteoDesdeNichos();
						sesion.pkSector = model.consultaIdSectorDesdeNichos();
						sesion.guardaSesion();
						try {
							System.out.println("//////////////// REDIRECCIONAR A AsignacionNichos");
							response.sendRedirect("AsignacionNichos?idsorteo=" + sesion.pkSorteo + "&idsector=" + sesion.pkSector);
							return true;
						} catch(Exception ex) { }
					}
				}
			}
		}
		return false; // NO redirecciones
	}
	
	
	
	protected void setPredeterminado(HttpServletRequest request,
			HttpServletResponse response, mSorteos model, SesionDatos sesion)
			throws ServletException, IOException {

		model.setIdUsuario(sesion.pkUsuario);
		//model.cosultaPredeterminados();		
		model.setId(sesion.pkSorteo); 		
		model.setPredeterminadoSorteo();

	/*	if (model.gatId() != null) {
			model.setIdSorteo(Integer.parseInt(request.getParameter("psorteo")));
		} else {
			model.setIdSorteo(0);
		}*/

		/*if (request.getParameter("psector") != null) {
			model.setIdSector(Integer.parseInt(request.getParameter("psector")));
		} else {
			model.setIdSector(0);
		}

		if (request.getParameter("pnicho") != null) {
			model.setIdNicho(Integer.parseInt(request.getParameter("pnicho")));
		} else {
			model.setIdNicho(0);
		}*/

	

	}
	
	

}




