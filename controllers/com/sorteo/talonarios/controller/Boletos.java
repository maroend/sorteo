package com.sorteo.talonarios.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.ESTADO_BOLETO;
import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel.RESULT;
import com.sorteo.talonarios.model.mBoletos;


/**
 * Servlet implementation class Boletos
 */
@WebServlet("/Boletos")
public class Boletos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Boletos() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Factory  vista = new Factory();
		mBoletos model = new mBoletos();
		SesionDatos sesion;
		
		if((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu ="";
		String notificaciones ="";
		String infouser ="";
		
		
		String view = request.getParameter("view");

		if(view == null){view = "";}
		
	    switch(view){
	    
	   
	    case "RevisarBoletos":
	    	RevisarBoletos(request, response, model);
	    	break;
	    
	    case "RevisarTalonario":
	    	RevisarTalonario(request, response, model);
	    	break;
	    	
	    case "UpdateBoletosTalonario":
	    	UpdateBoletosTalonario(request, response, model);
	    	break;
	    
	    
	    case "Buscar":
	    	int pg=0; //pagina a mostrar
			if(request.getParameter("pg")==null){
				pg=1;
			}else{
				pg =Integer.valueOf(request.getParameter("pg"));
			}
			
			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
	    	Buscar(request, response,pg,show,search, model, sesion);
	    	break;
	    	
	    	
	    case "BuscarVistaColumna":
			 pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			
			BuscarVistaColumna(request, response, pg, show, search, model, sesion);
			break;		
	    	
	    case "delete":
	    	//eliminarUsuario(request, response);
	    	break;
	    	
	    	
    	default:
			String str_sorteo = request.getParameter("sorteo");
			if (str_sorteo!=null) {
				int pkSorteo = Integer.valueOf(str_sorteo);
				if (sesion.pkSorteo != pkSorteo) {
					sesion.pkSorteo = pkSorteo;
					sesion.guardaSesion();
				}
			}
    		fullPath = "/WEB-INF/views/boveda/boletos.html";
    		menu = vista.initMenu(0 ,false,7,9,sesion);
    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
    		break;
	    }
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	protected void BuscarVistaColumna(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search, mBoletos model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Boleto","Costo","Formato","Talonario"};
		String[] campos = { "FOLIO","COSTO","FORMATO","TALONARIO" };
		
			

		int numeroregistros = model.contarVistaColumna(sesion.pkSorteo,search);

		
		String HTML = CrearTablaVistaColumna(numeroregistros, model.paginacionVistaColumna(pg, show, search, sesion.pkSorteo), columnas, campos, pg, show);
		PrintWriter out = response.getWriter();
		
		//System.out.println(HTML);
		
		out.println(HTML);

	}

	protected String CrearTablaVistaColumna(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width:100px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		//String html = "<table class=\"table table-striped table-bordered dataTable no-footer invoice\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		StringBuilder sb = new StringBuilder("<table class=\"table table-striped table-bordered dataTable no-footer invoice\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">");
		sb.append("<thead>")
		.append("<tr role=\"row\">");

		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 12px;\" ></th>";
		sb.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 15px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");
		for (String columna : campostable) {

			sb.append(columna);

		}
		
	/*	html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 115px;\" >Sector</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 115px;\" >Nicho</th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 115px;\" >Colabolador</th>";*/

		sb.append(" </thead>")
		.append(" <tbody>");

		try {
			while (rs.next()) {

				i++;
				
				
				/*model.setIdtalonario(rs.getInt("PK1"));
				model.setFolioTalonario(rs.getString("FOLIO"));
				model.setSorteo(rs.getString("SORTEO"));
				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				model.setIdnicho(sesion.pkNicho);
				System.out.println(">>>nicho: "+sesion.pkNicho);
				ResultSet talonarioasignado = model.obtenerTalonario(model);
					*/
				
				
				
			

				sb.append("<tr class=\"gradeA odd\" role=\"row\">")
				
			//	html += "<td class=\"sorting_1\"><input  id=" + rs.getInt("PK1") + "  type=\"checkbox\" /></td>";
				.append("<td class=\"sorting_1\">" + i + "</td>");

				for (String campo : campos) {
                     
					if(rs.getString(campo)==null){
						sb.append("<td class=\"sorting_1\">NA</td>");
						
					}else{
						sb.append("<td class=\"sorting_1\">").append(rs.getString(campo)).append("</td>");
					}
				}		

			//	html += "<td class=\"sorting_1\"></td>";
			//	html += "<td class=\"sorting_1\"></td>";
			//	html += "<td class=\"sorting_1\"></td>";
				//System.out.println(rs.getString("SECTOR"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sb.append("</tbody>")
		.append("</table>");

		/* *
		int numpag = Math.round(numreg / show);
		//int denumpag = numpag + 1;

		System.out.println(">>> numreg=" + numreg + ",  show=" + show
				+ ",  numpag=" + numpag);

		String paginado = "<ul class=\"pagination pagination-without-border pull-right m-t-0\">";

		if (pg > 1) {

			int pagante = pg - 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
					+ pagante + ");\">Anterior</a></li>";
		} else {
			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Anterior</a></li>";

		}

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
					paginado += "<li class=\"active\"><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
							+ sumpag + ");\">" + sumpag + "</a>";
				} else {
					paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
							+ sumpag + ");\">" + sumpag + "</a>";
				}

			}
		}

		if (pg <= numpag) {
			int numeropag = pg + 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\"getPagViewColumn("
					+ numeropag + ");\">Siguiente</a></li>";

		} else {

			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Siguiente</a></li>";
		}

		paginado += "</ul>";
		/**
		/* */
		String paginado = Factory.paginado_2(numreg, show, pg, "getPagViewColumn","elementos");
		/**/

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		sb.insert(0, paginado);
		sb.append(paginadoright);
		return sb.toString();
		//html = paginado + html + paginadoright;	
		//return html;
	}

	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		mBoletos model = new mBoletos();
		String JSON="";
		if(request.getParameter("eliminar")==null){
			
			String boletosjoin = request.getParameter("boletos");
			String[] boletos = boletosjoin.split(",");
			double costototal = 0; 
			double costo = Double.parseDouble(request.getParameter("costo"));
			
			
			for (String boleto: boletos){
				model.setFolio(boleto);
				model.setFormato(request.getParameter("formato")); 
				model.setTalonario(request.getParameter("talonario"));
				model.setIdsorteo(request.getParameter("sorteo"));
				model.setCosto(costo);
				
				costototal += costo;
				model.registrar(model);	
			
			}
			JSON = String.valueOf(costototal);
			
		}else{
			//ELIMINAR BOLETO
			
			model.setId(Integer.valueOf(request.getParameter("id")));
			model.setFolio(request.getParameter("folio"));
			model.setFormato(request.getParameter("formato")); 
			model.setTalonario(request.getParameter("talonario"));
			model.setIdsorteo(request.getParameter("sorteo"));
			RESULT result = model.Borrar(model);
			
			JSON = "{\"result\":\"" + result + "\",\"msg\":\"" + model._mensaje + "\"}";
			
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(JSON);
		
		model.close();
	}
	
	
	
	protected void UpdateBoletosTalonario(HttpServletRequest request,
			HttpServletResponse response, mBoletos model) {
		
		model.setIdsorteo(request.getParameter("sorteo"));
		model.setTalonario(request.getParameter("talonario"));
		model.setFormato(request.getParameter("formato"));
		model.setIdtalonario(Integer.parseInt(request.getParameter("pktalonario")));
		
		model.updateBoletos(model);
	}
	
	protected void RevisarTalonario(HttpServletRequest request,
			HttpServletResponse response, mBoletos model) {
		
		String existe = "";
		
		model.setIdsorteo(request.getParameter("sorteo"));
		model.setTalonario(request.getParameter("talonario"));
		
		try {
		
			if(model.RevisarTalonario(model)!=0){
			   	
				existe="existe";
			}
			
			
		    PrintWriter out;
			out = response.getWriter();
			out.println(existe);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void RevisarBoletos(HttpServletRequest request, HttpServletResponse response, mBoletos model){
		
		String boletosjoin = request.getParameter("boletos");
		String[] boletos = boletosjoin.split(",");
		
		String boletosrepetidos = "";
		
		model.setIdsorteo(request.getParameter("sorteo"));
		
		try {
			for (String boleto: boletos){
			
				model.setFolio(boleto);
				
			
				if(model.revisarBoleto(model)>0){
	               				
					boletosrepetidos += boleto+",";
				}
		    }
			
		    PrintWriter out;
			out = response.getWriter();
			out.println(boletosrepetidos);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mBoletos model, SesionDatos sesion) throws ServletException, IOException {

		model.setIdsorteo(""+sesion.pkSorteo);
		int numeroregistros = model.contar(search);
		
	    String HTML = CrearTabla(numeroregistros,model.paginacion(pg,show, search), pg, show, sesion);
	    PrintWriter out = response.getWriter();
		out.println(HTML);
	
	}
	
	
	
	 protected String CrearTabla(int numreg,ResultSet rs,int pg, int show, SesionDatos sesion){
			
		//int i = (pg - 1) * show;

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		//ResultSet boletos;

		//String html = "<ul class=\"result-list\">";
		StringBuilder sb = new StringBuilder("<ul class=\"result-list\">");

		try {

			if (numreg > 0) {

				while (rs.next()) {

					//i++;

					sb.append("<li id=\"boleto").append("PK1").append("\">")
					.append("<div class=\"result-info\">")
					.append("<h4 class=\"title\">BOLETO FOLIO: ");
							
							if(rs.getString("ESTADO").equals(ESTADO_BOLETO.NO_VENDIDO)){  sb.append("<span class=\"label label-inverse\" >").append(rs.getString("FOLIO")).append("</span> ");}
							if(rs.getString("ESTADO").equals(ESTADO_BOLETO.VENDIDO)){  sb.append("<span class=\"label label-success\" >").append(rs.getString("FOLIO")).append("</span> ");}
						//	if(rs.getString("ESTADO").equals("P")){  html += "<span class=\"label label-default\" >"+rs.getString("FOLIO")+"</span> ";}
							
					sb.append("</h4>")
					.append("<p class=\"location\"> TALONARIO: <span class=\"badge badge-warning badge-square\">")
					.append(rs.getString("TALONARIO")).append("</span> </p>")
					.append("<p class=\"desc\">")
					.append("</p>")
					.append("<div class=\"btn-row\">");
					
					if(sesion.sorteoActivo)
					{
						sb.append("<a data-title=\"Eliminar Boleto\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:eliminarBoleto('")
							.append(rs.getString("PK1")).append("','")
							.append(rs.getString("FOLIO")).append("','")
							.append(rs.getString("PK_TALONARIO")).append("','")
							.append(rs.getString("PK_SORTEO")).append("','")
							.append(rs.getString("FORMATO"))
							.append("');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">Eliminar</span></a>");
					}
					/*html += "<a data-title=\"Configuración\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-cog\"></i><span style=\"font-size:11px;\">Configuraci&oacute;n</span></a>";
					html += "<a data-title=\"Muestra las estadisticas y datos de avance del sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:;\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-tachometer\"></i><span style=\"font-size:11px;\">Dashboard</span></a>";
					html += "<a data-title=\"Revise que sectores estan asignados en este sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SectoresAsignados?clave="
							+ rs.getString("PK1")
							+ "\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-user\"></i><span style=\"font-size:11px;\">Sectores</span></a>";*/
					sb.append("</div>")
					.append("</div>");

					double monto = Double.parseDouble(rs.getString("COSTO"));

					Locale english = new Locale("en", "US");
					NumberFormat englishFormat = NumberFormat
							.getCurrencyInstance(english);
					//System.out.println("US: " + englishFormat.format(monto));

					sb.append("<div class=\"result-price\">")
						.append(englishFormat.format(monto))
						.append(" <small>COSTO</small>")
						.append("<a class=\"btn btn-inverse btn-block\" href=\"SeguimientoBoletos?idsorteo=").append(rs.getString("PK_SORTEO")).append("&idboleto=").append(rs.getString("PK1")).append("\">Ver Detalles</a>")
						.append("</div>")
						.append("</li>");

				}

				sb.append("</ul>");

			} else {

				sb
				.append("<li align= \"center\">")
				.append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">")
				.append("<h1>No existen Boletos</h1>")
				.append("<p>Empieze por agregar su carga inicial de Talonarios y Boletos.</p>")
				.append("</div>")
				.append("</li>");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		/*
		int numpag = Math.round(numreg / show);
		//int denumpag = numpag + 1;

		System.out.println(">>> numreg=" + numreg + ",  show=" + show
				+ ",  numpag=" + numpag);

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
		*/
		String paginado = Factory.paginado_2(numreg, show, pg);

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";
		

		sb.insert(0, paginado);
		sb.append(paginadoright);
		//html = paginado + html + paginadoright;

		return sb.toString();
	}

}
