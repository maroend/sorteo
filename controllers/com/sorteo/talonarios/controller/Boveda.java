package com.sorteo.talonarios.controller;

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
import com.core.SesionDatos;
import com.sorteo.talonarios.model.mBoveda;

/**
 * Servlet implementation class Boveda
 */
@WebServlet("/Boveda.do")
public class Boveda extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Boveda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		Factory vista = new Factory();
		mBoveda model = new mBoveda();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		if (view == null) { view = ""; }

		String p_submenu = request.getParameter("sub");
       
		//10009 ACCESO A LA LISTA DE SORTEOS
		 //if (!Privileges.havePermiso(10009)){view = "error";}
			
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int idSorteo = 0;


		switch (view) {

		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
			Buscar(request, response, pg, show, search, p_submenu, model, sesion);
			break;
		
		case "ExisteCarga":
			
			idSorteo = Integer.valueOf(request.getParameter("sorteo"));
			if(this.ExisteCarga(idSorteo, model)){
				HTML = "TRUE";
			}
			
			break;
			
	        case "EliminarCarga":
			idSorteo = Integer.valueOf(request.getParameter("sorteo"));
			if(this.EliminarCarga(idSorteo, model)){
				HTML = "TRUE";
			}
			
			break;
		

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			String parametros = "";
			HTML = vista.CrearVista(context, fullPath,parametros);
			break;

		default:
			fullPath = "/WEB-INF/views/boveda/boveda.html";
			int subMenu = 9;
			if (p_submenu!=null)
				subMenu = Integer.valueOf(p_submenu);
			sesion.idMenu = 7;
			sesion.idSubMenu = subMenu;
			sesion.guardaSesion();
			menu = vista.initMenu(0, false, sesion.idMenu, sesion.idSubMenu, sesion);
			menu += "<input type='hidden' id='boveda_submenu_id' value='"+subMenu+"'>";

			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);

		model.close();
	}
	
	
	private boolean ExisteCarga(int idSorteo, mBoveda model){
		
		model.setId(idSorteo);
		return model.ExisteCarga(model);
	
	}
	
	
	
   private boolean EliminarCarga(int idSorteo, mBoveda model){
		
		model.setId(idSorteo);
		return model.EliminarCarga(model);
	
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mBoveda model) throws ServletException, IOException {

		String valida = "insertar";
		String activar = "activar";
		String borrar = "eliminar";
		String cargar = "cargar";

		if (valida.equals(request.getParameter("insertar"))) {

			model.setClave(request.getParameter("clave"));
			model.setSorteo(request.getParameter("sorteo"));
			model.setDescripcion(request.getParameter("descripcion"));
			model.setFechainico(request.getParameter("fechai"));
			model.setFechatermino(request.getParameter("fechat"));
			model.setImagen(request.getParameter("imagen"));
			
		} else if (activar.equals(request.getParameter("activar"))) {

		}

		else if (borrar.equals(request.getParameter("eliminar"))) {

			model.setId(Integer.parseInt(request.getParameter("sorteo")));
		}

		else if (cargar.equals(request.getParameter("cargar"))) {

			model.setId(Integer.parseInt(request.getParameter("sorteo")));
			
			if(model.ExisteCarga(model))
				return;
			
			
			
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String boletosprocesados =  model.getNumTalonarios()+":"+model.getNumBoletos();
			out.println(boletosprocesados);

		}
		model.close();
	}

	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			String submenu, mBoveda model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int numeroregistros = model.contar(sesion);
		
		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search, sesion), pg, show, submenu, model, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, String submenu, mBoveda model, SesionDatos sesion) {

		//int i = (pg - 1) * show;

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
					
					model.setId(Integer.parseInt(rs.getString("PK1")));
					numerotalonarios = model.numeroTalonarios(model);
					numeroboletos = model.numeroBoletos(model);
					
					monto = (double) model.montoSorteo();
					
					String style = rs.getInt("ACTIVO")==1?"":"background-color: hsl(55, 21%, 80%);";
					
					html += "<li id=\"sorteo" + rs.getString("PK1") + "\" style='"+style+"'>";
					
					html += "<div class=\"result-info\">";
					
					html += "<h4 class=\"title\">";
					if (rs.getInt("ACTIVO")==0)
						html += "  <span class=\"label label-warning\"><img style=\"height:30px; vertical-align: middle;\" src=\"assets/img/icono-candado-inv.png\"></span>";

					html += " <span class=\"label label-primary\">" + rs.getString("CLAVE");
					if (submenu.equals("8")){
						html += "</span> <a href=\"Talonarios?sorteo="+ rs.getString("PK1")+"\">" + rs.getString("SORTEO") + "</a>";
					}
					else if (submenu.equals("9")){
						html += "</span> <a href=\"Boletos?sorteo="+ rs.getString("PK1")+"\">" + rs.getString("SORTEO") + "</a>";
					}
					html += "</h4>";
					
					html += "<p class=\"location\">"
							+ " TALONARIOS: <span class=\"badge badge-danger pull\">" + numerotalonarios + "</span>"
							+ " BOLETOS: <span class=\"badge badge-danger pull\">" + numeroboletos + "</span>"
							+ "</p>";
					
					html += "<p class=\"desc\"></p>";

					html += "<div class=\"btn-row\">";
					if (submenu.equals("8")){
						// Boveda / Talonarios
						html += "<a data-title=\"Talonarios Asignados\" data-container=\"body\" data-toggle=\"tooltip\" href=\"Talonarios?sorteo="+rs.getString("PK1")+"\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tags\"></i><span style=\"font-size:11px;\"> Talonarios</span></a>";
					}
					else if (submenu.equals("9")){
						// Boveda / Boletos
						html += "<a data-title=\"Boletos Asignados\" data-container=\"body\" data-toggle=\"tooltip\" href=\"Boletos?sorteo="+rs.getString("PK1")+"\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tags\"></i><span style=\"font-size:11px;\"> Boletos</span></a>";
					}
					
					if(rs.getInt("ACTIVO")==1)
					{
						// Boton "Opciones"
						html += "<a data-title=\"Opciones\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:configuracion("
							+ rs.getString("PK1")
							+ ",'"+rs.getString("SORTEO")+"');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-cog\"></i><span style=\"font-size:11px;\">Opciones</span></a>";
					}
					
					//html += "<a data-title=\"Muestra las estadisticas y datos de avance del sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tachometer\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";
				//	html += "<a data-title=\"Sectores asignados al sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SectoresAsignados?idsorteo="
					//		+ rs.getString("PK1")
						//	+ "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-building\"></i><span style=\"font-size:11px;\">Sectores</span></a>";
					html += "</div>";

					html += "</div>";

					html += "<div class=\"result-price\">";
					html += englishFormat.format(monto)
							+ " <small>MONTO</small>";
					html += "<a class=\"btn btn-inverse btn-block\" href=\"SorteoDetalle?sorteo="+rs.getString("PK1")+"\">Ver Detalles</a>";
					html += "</div>";

					html += "</li>";

					// System.out.println(rs.getString("NOMBRE"));

				} 


			} else {

				html += "<li align= \"center\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";

				html += "<h1>No existen Sorteos y/o No se encuentra asignado como responsable al Sorteo</h1>";
				html += "<p>Empiece por agregar un nuevo sorteo y/o asignarse como responsable al Sorteo.</p>";

				html += "</div>";

				html += "</li>";

			}

			html += "</ul>";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int numpag = Math.round(numreg / show);
		//int denumpag = numpag + 1;

		String paginado = "<ul class=\"pagination pagination-without-border pull-right m-t-0\">";

		if (pg > 1) {

			int pagante = pg - 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPag("
					+ pagante + ");\">Anterior</a></li>";
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

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		html = paginado + html + paginadoright;

		return html;

	}

}
