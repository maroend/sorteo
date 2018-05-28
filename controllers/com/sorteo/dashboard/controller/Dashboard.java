package com.sorteo.dashboard.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.dashboard.model.mDashboard;
import com.sorteo.dashboard.model.mDashboard.Estadisticas;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard.do")
public class Dashboard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO doGet
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( "(1)"+sdf.format(cal.getTime()) );
        
		
		mDashboard model = new mDashboard();
		Factory  vista = new Factory();
		SesionDatos sesion;
		
		//SessionListener.SessionStart(request, response,false);
		//SuperModel supermodel = new SuperModel();
		if((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String fullPath = "/WEB-INF/views/listsorteosuser.html";
		
		ServletContext context = getServletContext();
		
		String view = request.getParameter("view");
		
		/*if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){view = "error";}*/
		System.out.println("DASHB activo:"+sesion.sorteoActivo);
		System.out.println("sesion pksorteo: "+sesion.pkSorteo);
		
		String sorteo =  request.getParameter("sorteo");
		
		if(sorteo!=null){
			
			sesion.pkSorteo = Integer.valueOf(sorteo);
			sesion.guardaSesion();
			
			if((sesion = SesionDatos.start(request, response, false, model)) == null)
				return;
			
			if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){view = "error";}
			
			
		}
		
		
		
		if (view == null) {
			view = "";
			if (request.getParameter("sorteo") != null) {
				fullPath = "/WEB-INF/views/dashboard/dashboard.html";
				view = "Dashboard";
			} else {
				view = "ListaSorteos";
			}
		}
		
		

		menu = vista.initMenu(0 ,false, 1, 0, sesion);
		notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
		infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
		String HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		switch (view) {
		
		
		case "error":
			fullPath = "/WEB-INF/views/error_sinpermiso.html";
			menu = vista.initMenu(0, false, 1, 1, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,
					sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			break;

		case "Predeterminado":
			
			HTML = "";
			setPredeterminado(request, response, model, sesion);
			break;
		
		case "GraphDonut":
			
			HTML = "";
			
			break;
			
		case "listsorteos":
			HTML = getSorteosUsuarios(request,response,HTML, model, sesion);
			break;
			
        case "ListaSorteos":
			
        	SorteoPredeterminado(request, response, HTML, model, sesion);
			break;
			
		case "Dashboard":
			
			//GET DASHBOARD SORTEO 
			if (request.getParameter("sorteo")!=null && request.getParameter("sector")==null &&
				request.getParameter("nicho")==null && request.getParameter("colaborador")==null){
				
				int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
				if (sesion.pkSorteo != idsorteo) {
					sesion.pkSorteo = idsorteo;
					sesion.guardaSesion();
				}
				HTML = getDashboardSorteo(request, response, HTML, model, sesion);
			}
			
			//GET DASHBOARD SECTOR 
	        else if(request.getParameter("sorteo")!=null && request.getParameter("sector")!=null &&
	        		request.getParameter("nicho")==null && request.getParameter("colaborador")==null){
				int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
				int idsector = Integer.valueOf(request.getParameter("sector"));

				if (sesion.pkSorteo != idsorteo || sesion.pkSector != idsector) {
					sesion.pkSorteo = idsorteo;
					sesion.pkSector = idsector;
					sesion.guardaSesion();
				}
				HTML = getDashboardSector(request, response, HTML, model, sesion);
			}
			
			//GET DASHBOARD NICHO
			else if(request.getParameter("sorteo")!=null && request.getParameter("sector")!=null &&
	        		request.getParameter("nicho")!=null && request.getParameter("colaborador")==null)
			{
				int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
				int idsector = Integer.valueOf(request.getParameter("sector"));
				int idnicho = Integer.valueOf(request.getParameter("nicho"));

				if (sesion.pkSorteo != idsorteo ||
					sesion.pkSector != idsector ||
					sesion.pkNicho != idnicho)
				{
					sesion.pkSorteo = idsorteo;
					sesion.pkSector = idsector;
					sesion.pkNicho = idnicho;
					sesion.guardaSesion();
				}
				HTML = getDashboardNicho(request, response, HTML, model, sesion);
			}
			/* *
			//GET DASHBOARD COLABORADOR
	        else if(request.getParameter("sorteo")!=null && request.getParameter("sector")!=null &&
	        		request.getParameter("nicho")!=null && request.getParameter("colaborador")!=null)
	        {
				int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
				int idsector = Integer.valueOf(request.getParameter("sector"));
				int idnicho = Integer.valueOf(request.getParameter("nicho"));
				int idcolaborador = Integer.valueOf(request.getParameter("colaborador"));

				if (sesion.pkSorteo != idsorteo || sesion.pkSector != idsector ||
					sesion.pkNicho != idnicho || sesion.pkColaborador != idcolaborador)
				{
					sesion.pkSorteo = idsorteo;
					sesion.pkSector = idsector;
					sesion.pkNicho = idnicho;
					sesion.pkColaborador = idcolaborador;
					sesion.guardaSesion();
				}
				HTML = getDashboardColaborador(request, response, HTML, model, sesion);
			}
			//*/
			break;
		
		default:
			break;
		}
		
		System.out.println("view="+view);
		out.println(HTML);
		model.close();
		System.out.println( "(4)"+sdf.format(cal.getTime()) );
	}
	
	
	protected void setPredeterminado(HttpServletRequest request,
			HttpServletResponse response, mDashboard model, SesionDatos sesion)
			throws ServletException, IOException {

		model.setIdUsuario(sesion.pkUsuario);
		model.cosultaPredeterminados();

		if (request.getParameter("psorteo") != null) {
			model.setIdSorteo(Integer.parseInt(request.getParameter("psorteo")));
		} else {
			model.setIdSorteo(0);
		}

		if (request.getParameter("psector") != null) {
			model.setIdSector(Integer.parseInt(request.getParameter("psector")));
		} else {
			model.setIdSector(0);
		}

		if (request.getParameter("pnicho") != null) {
			model.setIdNicho(Integer.parseInt(request.getParameter("pnicho")));
		} else {
			model.setIdNicho(0);
		}

		model.setPredeterminadoSorteo();

	}

	protected void SorteoPredeterminado(HttpServletRequest request,
			HttpServletResponse response, String HTML, mDashboard model,
			SesionDatos sesion) throws ServletException, IOException {

		model.setIdUsuario(sesion.pkUsuario);
		model.cosultaPredeterminados();

		int idsorteo = model.getIdSorteo();
		int idsector = model.getIdSector();
		int idnicho = model.getIdNicho();

		if (idsorteo != 0 && idsector == 0 && idnicho == 0) {
			response.sendRedirect("Dashboard.do?sorteo=" + idsorteo);
		} else if (idsorteo != 0 && idsector != 0 && idnicho == 0) {
			response.sendRedirect("Dashboard.do?sorteo=" + idsorteo + "&sector=" + idsector);
		} else if (idsorteo != 0 && idsector != 0 && idnicho != 0) {
			response.sendRedirect("Dashboard.do?sorteo=" + idsorteo + "&sector=" + idsector + "&nicho=" + idnicho);
		} else {
			response.sendRedirect("Dashboard.do?view=listsorteos");
		}
		/*
		// *** ANTES ***
		if (idsorteo != 0 && idsector == 0 && idnicho == 0) {
			response.sendRedirect("Dashboard.do?sorteo=" + idsorteo);
		} else if (idsorteo != 0 && idsector != 0 && idnicho == 0) {
			response.sendRedirect("Dashboard.do?sorteo=" + idsorteo + "&sector=" + idsector);
		} else if (idsorteo != 0 && idsector != 0 && idnicho != 0) {
			response.sendRedirect("Dashboard.do?sorteo=" + idsorteo + "&sector=" + idsector + "&nicho=" + idnicho);
		} else {
			response.sendRedirect("Dashboard.do?view=listsorteos");
		}
		*/
	}
	
	protected String getSorteosUsuarios(HttpServletRequest request,
			HttpServletResponse response, String HTML, mDashboard model,
			SesionDatos sesion) throws ServletException, IOException {

		String contenido = "";

		ResultSet rs = null;

		model.setIdUsuario(sesion.pkUsuario);
		model.cosultaPredeterminados();

		int idsorteo = model.getIdSorteo();
		int idsector = model.getIdSector();
		int idnicho = model.getIdNicho();

		// EXISTE USUARIO NIVEL SORTEO
		if (model.ContarSorteosUsuarios() != 0) {

			rs = model.getSorteosUsuario();
			try {
				while (rs.next()) {

					contenido += "<p>";
					contenido += "<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSorteo('"
							+ rs.getString("PK1") + "')\"  role=\"button\" class=\"btn btn-success btn-lg\">";

					if (idsorteo == rs.getInt("PK1")) {
						contenido += "<i class=\"fa fa-2x fa-check-circle\"></i> ";
					}

					contenido += rs.getString("SORTEO") + "</a> ";
					contenido += "</<p>";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// NIVEL SECTORES USUARIOS
		} else if (model.ContarSectoresUsuarios() != 0) {

			rs = model.getSectoresUsuario();

			try {
				while (rs.next()) {

					contenido += "<p>";
					contenido += "<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSector('"
							+ rs.getString("PK_SORTEO")
							+ "','"
							+ rs.getString("PK_SECTOR")
							+ "')\"  role=\"button\" class=\"btn btn-success btn-lg\">";

					if (idsector == rs.getInt("PK_SECTOR")) {
						contenido += "<i class=\"fa fa-2x fa-check-circle\"></i> ";
					}

					contenido += rs.getString("SORTEO") + " <br> <small>"
							+ rs.getString("SECTOR") + "</small></a> ";
					contenido += "</<p>";

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// NIVEL NICHOS USUARIOS
		} else if (model.ContarNichosUsuarios() != 0) {

			rs = model.getNichosUsuario();

			try {
				while (rs.next()) {

					contenido += "<p>";
					contenido += "<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardNicho('"
							+ rs.getString("PK_SORTEO")
							+ "','"
							+ rs.getString("PK_SECTOR")
							+ "','"
							+ rs.getString("PK_NICHO")
							+ "')\"  role=\"button\" class=\"btn btn-success btn-lg\">";
					if (idnicho == rs.getInt("PK_NICHO")) {
						contenido += "<i class=\"fa fa-2x fa-check-circle\"></i> ";
					}
					contenido += rs.getString("SORTEO") + "<br> <small>"
							+ rs.getString("NICHO") + "</small></a> ";
					contenido += "</<p>";

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {

			contenido = "<div  class=\"jumbotron m-b-0 text-center\"><h1>No existen Sorteos</h1><p>Empieze por agregar un nuevo sorteo.</p></div>";
		}

		HTML = HTML.replaceAll("#SORTEOSUSUARIOS#", contenido);

		return HTML;
	}
	
	
	private String cutWords(String str, int limit){
		String newString = "";
		while(limit < str.length()){
			int index = 1 + str.lastIndexOf(' ', limit);
			if (index==0)
				index = limit;
			newString += str.substring(0, index) + "<br>&nbsp;&nbsp;";
			str = str.substring(index);
		}
		return newString + str;
	}
	
	// TODO listaDeSectores
	protected String listaDeSectores(HttpServletRequest request, HttpServletResponse response, mDashboard model)
			throws ServletException, IOException {
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		
		String[] myStringArray = { "danger", "warning", "success", "primary", "default", "inverse" };
		int idx = 0;
		String randomColor = null;
		
		//rs = model.Sectores(); 
		ArrayList<Estadisticas> list = model.getEstadisticasDashboardSector();
		
		StringBuilder sb = new StringBuilder();

		mDashboard.Estadisticas globales = model.creaEstadisticas();
		//                                                                     [ SECTOR ]
	//	sb.append("<table class='table table-valign-middle m-b-0'><thead><tr>  <th>SECTOR</th><th>Emisi&oacute;n</th><th>FC2</th><th>FC4</th><th>Bancos</th><th>Monto Total</th></tr></thead><tbody>\n");
		sb.append("<table class='table table-valign-middle m-b-0'><thead><tr>  <th>SECTOR</th><th>Boletos en Sector</th><th>Boletos en Colaboradores</th><th>FC4</th><th>Bancos</th><th>Monto Total</th></tr></thead><tbody>\n");
		
		for (Estadisticas estadisticas : list) {
			
			idx = new Random().nextInt(myStringArray.length);
			randomColor = (myStringArray[idx]);
			model.setIdSector(estadisticas.id);
			
			sb.append("\t<tr>");
			sb.append(" <td><label class=\"label label-").append(randomColor).append("\"><a href=\"Dashboard.do?sorteo=").append(model.getIdSorteo()).append("&sector=").append(model.getIdSector())
				.append("\" style=\"color: #FFF;\">").append(cutWords(estadisticas.nombre, 30)).append("</a></label></td>");

			//mDashboard.Estadisticas estadisticas = model.getEstadisticasXSector();
			sb.append(" <td>").append(estadisticas.total).append("</td>");
			sb.append(" <td>").append(estadisticas.FC2).append("</td>");
			sb.append(" <td>").append(estadisticas.FC4).append("</td>");
			sb.append(" <td>").append(englishFormat.format(estadisticas.bancos)).append("</td>");
			sb.append(" <td>").append(englishFormat.format(estadisticas.monto_total)).append("</td>");

			globales.acumula(estadisticas);

			sb.append("</tr>\n");
		}
		sb.append("<tr>\n");
		sb.append(" <td>Totales:</td>");
		sb.append(" <td>").append(globales.total).append("</td>");
		sb.append(" <td>").append(globales.FC2).append("</td>");
		sb.append(" <td>").append(globales.FC4).append("</td>");
		sb.append(" <td>").append(englishFormat.format(globales.bancos)).append("</td>");
		sb.append(" <td>").append(englishFormat.format(globales.monto_total)).append("</td>");
		sb.append("</tr>\n");
		
		sb.append("</tbody></table>");
		
		return sb.toString();
	}
	
	// TODO listaDeNichos
	protected String listaDeNichos(HttpServletRequest request, HttpServletResponse response, mDashboard model)
			throws ServletException, IOException {
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		
		String[] myStringArray = {"danger","warning","success","primary","default","inverse"};
		int idx = 0;
		String randomColor = null;
		
		//rs = model.Nichos();
		ArrayList<Estadisticas> list = model.getEstadisticasDashboardNicho();
		
		StringBuilder sb = new StringBuilder();
		
			mDashboard.Estadisticas globales = model.creaEstadisticas();
			//                                                                   [ NICHO ]
			sb.append("<table class='table table-valign-middle m-b-0'><thead><tr>  <th>NICHO</th><th>Emisi&oacute;n</th><th>FC2</th><th>FC4</th><th>Bancos</th><th>Monto Total</th></tr></thead><tbody>\n");
			for (Estadisticas estadisticas : list) {
				
				idx = new Random().nextInt(myStringArray.length);
				randomColor = (myStringArray[idx]);
				model.setIdNicho(estadisticas.id);
				
				sb.append("\t<tr>");
				sb.append("<td><label class=\"label label-").append(randomColor).append("\"><a href=\"Dashboard.do?sorteo=").append(model.getIdSorteo()).append("&sector=").append(model.getIdSector()).append("&nicho=").append(estadisticas.id)
						.append("\" style=\"color: #FFF;\">").append(cutWords(estadisticas.nombre, 30)).append("</a></label></td>");

				//mDashboard.Estadisticas estadisticas = model.getEstadisticasXNicho();
				sb.append(" <td>").append(estadisticas.total).append("</td>");
				sb.append(" <td>").append(estadisticas.FC2).append("</td>");
				sb.append(" <td>").append(estadisticas.FC4).append("</td>");
				sb.append(" <td>").append(englishFormat.format(estadisticas.bancos)).append("</td>");
				sb.append(" <td>").append(englishFormat.format(estadisticas.monto_total)).append("</td>");
				//sb.append(" <td>").append(estadisticas).append("</td>");

				globales.acumula(estadisticas);

				sb.append("</tr>\n");
			}
			sb.append("<tr>\n");
			sb.append("<td>Totales:</td>");
			sb.append(" <td>").append(globales.total).append("</td>");
			sb.append(" <td>").append(globales.FC2).append("</td>");
			sb.append(" <td>").append(globales.FC4).append("</td>");
			sb.append(" <td>").append(englishFormat.format(globales.bancos)).append("</td>");
			sb.append(" <td>").append(englishFormat.format(globales.monto_total)).append("</td>");
			sb.append("</tr>\n");
			
			sb.append("</tbody></table>");
		
		return sb.toString();
	}

	
	/*
	// TODO listaDeColaboradores
	protected String listaDeColaboradores(HttpServletRequest request, HttpServletResponse response, mDashboard model, SesionDatos sesion)
			throws ServletException, IOException {
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSector(sesion.pkSector);
		model.setIdNicho(sesion.pkNicho);
		
		//Locale english = new Locale("en", "US");
		//NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		// Paginado
				int pg=0; //pagina a mostrar
				if(request.getParameter("pg")==null){
					pg=1;
				}else{
					pg =Integer.valueOf(request.getParameter("pg"));
				}
				
		ResultSet rs = null;
		
		String[] myStringArray = {"danger","warning","success","primary","default","inverse"};
		int idx = 0;
		String randomColor = null;
		
		//ArrayList<Object> array;
		int numreg = model.contarColaboradores();
		int show = Integer.valueOf(request.getParameter("show"));
		rs = model.Colaboradores(pg, show);
		
		StringBuilder sb = new StringBuilder();
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		try {
			mDashboard.Estadisticas globales = model.creaEstadisticas();
			//                                                                   [ COLABORADOR ]
			sb.append("<table class='table table-valign-middle m-b-0'><thead><tr><th>Colaborador</th><th>Boletos Entregados</th><th>Boletos Asignados</th><th>Boletos Vendidos</th><th>Boletos Retornados</th><th>Monto Total</th><th>Bancos</th></tr></thead><tbody>\n");
			while (rs.next()) {
				
				idx = new Random().nextInt(myStringArray.length);
				randomColor = (myStringArray[idx]);
				model.setIdColaborador(rs.getInt("PK1"));
				
				sb.append("\t<tr>");
				sb.append("<td><label class=\"label label-").append(randomColor).append("\"><a href=\"#\" style=\"color: #FFF;\">").append(cutWords(rs.getString("NOMBRE_COMPLETO"), 30)).append("</a></label></td>");

				mDashboard.Estadisticas estadisticas = model.getEstadisticasXColaborador();
				sb.append(" <td>").append(estadisticas.boletos_total).append("</td>");
				sb.append(" <td>").append(estadisticas.boletos_entregados).append("</td>");
				sb.append(" <td>").append(estadisticas.boletos_vendidos).append("</td>");
				sb.append(" <td>").append(estadisticas.boletos_retornados).append("</td>");
				sb.append(" <td>").append(englishFormat.format(estadisticas.monto_total)).append("</td>");
				sb.append(" <td>").append(englishFormat.format(estadisticas.venta)).append("</td>");
				//sb.append(" <td>").append(estadisticas).append("</td>");

				globales.acumula(estadisticas);

				sb.append("</tr>\n");
			}
			sb.append("</tbody></table>");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String paginado = paginado(pg, numreg, show);
		sb.insert(0, paginado);
		//html = paginado + html;
		
		return sb.toString();
	}
	
	public String paginado(int pg, int numreg, int show){
		int numpag = (int)Math.ceil(numreg / (double)show);
		
		String paginado = "<br/><p>" + numreg + " resultados. Mostrando " + pg + " de " + numpag + " p&aacute;ginas. </p> <ul class=\"pagination pagination-without-border pull-left m-t-0\">";

		if (pg > 1) {
			//paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPag(" + pagante + ");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";
			
			paginado += "<li ><a href='javascript:void(0)' onclick='getPag("+(pg-1)+");'>Anterior</a></li>";
		} else {
			//paginado += "<a class=\"paginate_button previous disabled\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";
			
			paginado += "<li class='disabled'><a href='javascript:void(0)'>Anterior</a></li>";
		}
		
		// Calcular el inicio del arreglo
		int inipg = 0;
		int sumpag = 0;

		inipg = pg - 5;
		if(inipg<0)
			inipg=0;
		

		for (int j = inipg; j < inipg + 9; j++) {
			if (j < numpag) {
				sumpag = j + 1;

				if (sumpag == pg)
					paginado += "<li class='active'><a href='javascript:void(0)' >" + sumpag + "</a></li>";
				else
					paginado += "<li><a onclick='getPag(" + sumpag + ");' href='javascript:void(0)'>" + sumpag + "</a></li>";
			}
		}

		if (pg < numpag)
			paginado += "<li ><a href='javascript:void(0)' onclick='getPag("+(pg+1)+");'>Siguiente</a></li>";
		else
			paginado += "<li class='disabled'><a href='javascript:void(0)'>Siguiente</a></li>";

		paginado += "</ul><br/><br/>";
		
		return paginado;
	}
	*/

	// _________________________________________________________________________________________________________
	
	
	// TODO getDashboardSorteo
	protected String getDashboardSorteo(HttpServletRequest request, HttpServletResponse response, String HTML, mDashboard model, SesionDatos sesion) throws ServletException, IOException {
		model.setIdSorteo(sesion.pkSorteo);
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		
		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
	    simbolo.setDecimalSeparator('.');
	    simbolo.setGroupingSeparator(',');
		DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
		
		HTML = HTML.replace("#HIDDEN_NIVEL#", "Sorteo");
		HTML = HTML.replaceAll("#SORTEO#", model.Sorteo(sesion.pkSorteo));
		HTML = HTML.replaceAll("#SECTOR#", "");
		HTML = HTML.replaceAll("#NICHO#", "");
		HTML = HTML.replaceAll("#COLABORADOR#", "");

		Estadisticas estadisticas = model.getEstadisticasXSORTEO();
		
		HTML = HTML.replace("#EMISION#", formateador.format(estadisticas.emision));
		HTML = HTML.replaceAll("#ASIGNADOS#", "Bols en Sectores: " + formateador.format(estadisticas.asignacion));
		HTML = HTML.replaceAll("#FC2#", formateador.format(estadisticas.FC2));
		HTML = HTML.replace("#FC4#", formateador.format(estadisticas.FC4));
		HTML = HTML.replace("#BANCOS#", englishFormat.format(estadisticas.bancos));
		HTML = HTML.replace("#MONTO_TOTAL#", englishFormat.format(estadisticas.monto_total));
		
		HTML = HTML.replaceAll("#POBLACION#", "An&aacute;lisis por SECTOR");
		
		return HTML;
	}
	
	// TODO getDashboardSector
	protected String getDashboardSector(HttpServletRequest request, HttpServletResponse response, String HTML, mDashboard model, SesionDatos sesion) throws ServletException, IOException {
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSector(sesion.pkSector);
		
		HTML = HTML.replace("#HIDDEN_NIVEL#", "Sector");
		HTML = HTML.replaceAll("#SORTEO#", model.Sorteo(sesion.pkSorteo));
		HTML = HTML.replaceAll("#SECTOR#", model.Sector(sesion.pkSector));
		HTML = HTML.replaceAll("#NICHO#", "");
		HTML = HTML.replaceAll("#COLABORADOR#", "");
		
		//ArrayList<Object> array = model.getEstadisticasXSector();
		
		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
	    simbolo.setDecimalSeparator('.');
	    simbolo.setGroupingSeparator(',');
		DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
		
		Estadisticas estadisticas = model.getEstadisticasXSector();
		
		HTML = HTML.replace("#EMISION#", formateador.format(estadisticas.emision));
		HTML = HTML.replaceAll("#ASIGNADOS#", "Bols en Nichos: " + formateador.format(estadisticas.asignacion));
		HTML = HTML.replaceAll("#FC2#", formateador.format(estadisticas.FC2));
		HTML = HTML.replace("#FC4#", formateador.format(estadisticas.FC4));
		HTML = HTML.replace("#BANCOS#", englishFormat.format(estadisticas.bancos));
		HTML = HTML.replace("#MONTO_TOTAL#", englishFormat.format(estadisticas.monto_total));

		HTML = HTML.replaceAll("#POBLACION#", "An&aacute;lisis por NICHO");
	
		return HTML;
		
	}
	
	// TODO getDashboardNicho
	protected String getDashboardNicho(HttpServletRequest request, HttpServletResponse response, String HTML, mDashboard model, SesionDatos sesion) throws ServletException, IOException {
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSector(sesion.pkSector);
		model.setIdNicho(sesion.pkNicho);

		HTML = HTML.replace("#HIDDEN_NIVEL#", "Nicho");
		HTML = HTML.replaceAll("#SORTEO#", model.Sorteo(sesion.pkSorteo));
		HTML = HTML.replaceAll("#SECTOR#", model.Sector(sesion.pkSector));
		HTML = HTML.replaceAll("#NICHO#", model.Nicho(sesion.pkNicho));
		HTML = HTML.replaceAll("#COLABORADOR#", "");

		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
	    simbolo.setDecimalSeparator('.');
	    simbolo.setGroupingSeparator(',');
		DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
		
		Estadisticas estadisticas = model.getEstadisticasXNicho();
		
		HTML = HTML.replace("#EMISION#", formateador.format(estadisticas.emision));
		HTML = HTML.replaceAll("#ASIGNADOS#", "Bols en colaboradores: " + formateador.format(estadisticas.asignacion));
		HTML = HTML.replaceAll("#FC2#", formateador.format(estadisticas.FC2));
		HTML = HTML.replace("#FC4#", formateador.format(estadisticas.FC4));
		HTML = HTML.replace("#BANCOS#", englishFormat.format(estadisticas.bancos));
		HTML = HTML.replace("#MONTO_TOTAL#", englishFormat.format(estadisticas.monto_total));
		
		HTML = HTML.replaceAll("#POBLACION#", "Colaboradores");

		return HTML;
	}
	
	
	/*
	// TODO getDashboardColaborador
	protected String getDashboardColaborador(HttpServletRequest request, HttpServletResponse response, String HTML, mDashboard model, SesionDatos sesion) throws ServletException, IOException {
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		model.setIdSorteo(sesion.pkSorteo);
		model.setIdSector(sesion.pkSector);
		model.setIdNicho(sesion.pkNicho);
		model.setIdColaborador(sesion.pkColaborador);
		
		HTML = HTML.replace("#HIDDEN_NIVEL#", "Colaborador");
		HTML = HTML.replaceAll("#SORTEO#", model.Sorteo(sesion.pkSorteo));
		HTML = HTML.replaceAll("#SECTOR#", model.Sector(sesion.pkSector));
		HTML = HTML.replaceAll("#NICHO#", model.Nicho(sesion.pkNicho));
		HTML = HTML.replaceAll("#COLABORADOR#", model.Colaborador(sesion.pkColaborador));


		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
	    simbolo.setDecimalSeparator('.');
	    simbolo.setGroupingSeparator(',');
		DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
		
		Estadisticas estadisticas = model.getEstadisticasXColaborador();
		
		HTML = HTML.replace("#TOTALBOLETOS#", formateador.format(estadisticas.boletos_total));
		HTML = HTML.replaceAll("#BOLETOSENTREGADOS#", formateador.format(estadisticas.boletos_entregados));
		HTML = HTML.replaceAll("#BOLETOSVENDIDOS#", formateador.format(estadisticas.boletos_vendidos));
		HTML = HTML.replace("#VENTABOLETOS#", englishFormat.format(""+estadisticas.venta));
		HTML = HTML.replace("#MONTO_TOTAL#", englishFormat.format(""+estadisticas.monto_total));
		
		HTML = HTML.replaceAll("#POBLACION#", "&nbsp;");
		
		return HTML;
	}
	*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO doPost
		mDashboard model = new mDashboard();
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		if(sesion == null)
			return;
		
		String HTML="";
		String view = request.getParameter("view");
		switch (view) {
		
	        case "Sectores":
				// --- GET DASHBOARD X SORTEO 
	        	model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
				HTML = listaDeSectores(request, response, model);
	        	break;
	    		
	        case "Nichos":
				// --- GET DASHBOARD X Sector
				model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
				model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
				HTML = listaDeNichos(request, response, model);
	        	break;
	    		
	        case "Colaboradores":
	        	/*
				// --- GET DASHBOARD X Nicho
				sesion.pkSorteo = Integer.valueOf(request.getParameter("idsorteo"));
				sesion.pkSector = Integer.valueOf(request.getParameter("idsector"));
				sesion.pkNicho = Integer.valueOf(request.getParameter("idnicho"));
				HTML = listaDeColaboradores(request, response, model, sesion);
				*/
	        	break;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

}
