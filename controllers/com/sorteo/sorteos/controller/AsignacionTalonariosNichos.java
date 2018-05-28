package com.sorteo.sorteos.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mAsignacionTalonarios;
import com.sorteo.sorteos.model.mAsignacionTalonariosColaboradores;
import com.sorteo.sorteos.model.mAsignacionTalonariosNichos;

/**
 * Servlet implementation class AsignacionSectores
 */
@WebServlet("/AsignacionTalonariosNichos")
public class AsignacionTalonariosNichos extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AsignacionTalonariosNichos() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *		response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mAsignacionTalonariosNichos model = new mAsignacionTalonariosNichos();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String url = "";
		int pg = 0;
		int show = 0;
		String search = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String view = request.getParameter("view");

		//10109 ACCESO A LA LISTA DE NICHOS
		 if (!sesion.permisos.havePermiso(10125)){view = "error";}

		if (view == null) {
			view = "";
		}

		System.out.println("view=" + view);

		switch (view) {

		case "Agregar":
			fullPath = "/WEB-INF/views/sorteos/agregar.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);
			out.println(HTML);
			break;

		case "Buscar":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			System.out.println(request.getParameter("idsorteo"));
			int idSorteo = Integer.valueOf(request.getParameter("idsorteo"));
			int idnicho = Integer.valueOf(request.getParameter("idnicho"));
			BuscarTalonario(request, response, pg, show, search, idSorteo,idnicho, model, sesion);
			break;

		case "BuscarModal":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			BuscarModal(request, response, pg, show, search, model, sesion);
			break;

		case "BuscarModalBoletos":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");

			BuscarModalBoletos(request, response, pg, show, search, Integer.valueOf(request.getParameter("idsorteo")),sesion.pkNicho, model, sesion);
			break;
			
			
         case "BuscaIntervaloTalonarios":
			
        	 
           int idsector = Integer.valueOf(request.getParameter("idsector"));
			HTML = BuscaIntervaloTalonarios(request, response, sesion.pkSorteo, model, sesion, idsector);
			out.println(HTML);
			
			break;

			
			


		case "EliminarANicho":
			EliminarANicho(request, response, model, sesion);
			break;

		case "error":

			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);
			out.println(HTML);
			break;


		default:

			String str_idNicho = request.getParameter("idnicho");
			if (str_idNicho != null) {
				int pkNicho = Integer.valueOf(str_idNicho);
				if (sesion.pkNicho != pkNicho) {
					sesion.pkNicho = pkNicho;
					sesion.guardaSesion();
				}
			}

			fullPath = "/WEB-INF/views/sorteos/Talonarios/AsignacionTalonariosNichos.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);
			//sesion.pkSector;




			//String regex ="";

			HTML = HTML.replaceAll("#SECTOR#", model.Sector(sesion.pkSector));

			HTML = HTML.replaceAll("#NICHO#", model.Nicho(sesion.pkNicho));

			
			url = "SectoresAsignados?idsorteo="+sesion.pkSorteo;
			HTML = HTML.replaceAll("#URLSECTORES#", url);
			
			url = "AsignacionNichos?idsorteo="+sesion.pkSorteo+"&idsector="+sesion.pkSector;
			HTML = HTML.replaceAll("#URLNICHOS#", url);

			url = "AsignacionTalonariosNichos?idsorteo="+sesion.pkSorteo+"&idnicho="+sesion.pkNicho;
			HTML = HTML.replaceAll("#URLTALONARIO#", url);



			 String boton = "";
			/* if (sesion.sorteoActivo && sesion.permisos.havePermiso(10145)){
					boton = "<a class=\"btn btn-primary m-r-5\" disabled href=\"#\" onclick=\"AsignarTalonario()\">Asignar Talonario</a>";
			 }*/
			 HTML = HTML.replaceAll( "#BTNASIGNAR#", boton);



				String contents = "";
				model.setIdSorteo(sesion.pkSorteo);
				model.ObtenerSorteo(model);
				contents = HTML.replaceAll("#IDSORTEO#", Integer.toString(model.getIdSorteo()));
				contents = contents.replaceAll("#SORTEO#", model.getSorteo());


			out.println(contents);

			break;
		}

		model.close();
	}
	
	
	private String BuscaIntervaloTalonarios(HttpServletRequest request ,HttpServletResponse response,
			int pkSorteo, mAsignacionTalonariosNichos model, SesionDatos sesion, int idsector) throws IOException {
		String ERROR = "-1";
		ArrayList<String> Asignados = new ArrayList<String>();
		ArrayList<String> Disponibles = new ArrayList<String>();
		try{
			int limiteInf = Integer.parseInt(request.getParameter("limiteInf"));
			int limiteSup = Integer.parseInt(request.getParameter("limiteSup"));
			HashMap<Integer,Integer> map = model.buscaIntervaloTalonarios(limiteInf, limiteSup, pkSorteo,idsector);

			if (map != null) {
				for (Entry<Integer, Integer> entry : map.entrySet()) {
					Integer PK1 = entry.getKey();
					Integer DISPONIBLE = entry.getValue();
					//Integer ASIGNADO = entry.getValue();					
					if(DISPONIBLE == 0) //	if(ASIGNADO == 1)
						Asignados.add(PK1.toString());
					else
						Disponibles.add(PK1.toString());
				}
				ERROR = "0";
			}
		}
		catch(Exception ex) { ERROR = "-1"; }
		return
				"{ \"Asignados\"  :" + Arrays.toString(Asignados.toArray()) +
				", \"Disponibles\":" + Arrays.toString(Disponibles.toArray()) +
				", \"ERROR\"      :" + ERROR + "}";
	}

	
	
	

	protected void EliminarANicho(HttpServletRequest request,
		HttpServletResponse response, mAsignacionTalonariosNichos model, SesionDatos sesion) throws ServletException, IOException {

		model.setId(Integer.valueOf(request.getParameter("id")));
		model.setIdtalonario(Integer.valueOf(request.getParameter("idtalonario")));
		//model.setIdsorteo(Integer.valueOf(request.getParameter("idsorteo")));

		model.Borrar(model);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *		response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		mAsignacionTalonariosNichos model = new mAsignacionTalonariosNichos();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;


		String accion = request.getParameter("accion");
		if (accion == null)
			accion = "";

		if (accion.equals("aboletos")) {

			String boletoscadena = request.getParameter("idboletos");
			String[] arrBoletos = boletoscadena.split(",");

			model.setIdSorteo(sesion.pkSorteo);
			model.setFormato(request.getParameter("folio"));
			model.setIdSector(sesion.pkSector);
			model.setIdnicho(sesion.pkNicho);
			//model.guardarAsignacionNichoBoletos(arrBoletos, model,sesion);			
			model.guardarAsignacionNichoBoletos_SP(boletoscadena, model,sesion);
			

		}
		else if (accion.equals("retornotalonariosfc4")) {

			String foliofc4 = request.getParameter("foliofc4");
			model.setFormato(foliofc4);

			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			int folioTalonario = Integer.valueOf(request.getParameter("folio"));

			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdnicho(sesion.pkNicho);
			model.setIdtalonario(idTalonario);

			model.retornotalonariosfc4(folioTalonario, sesion);
		}
		 else if (accion.equals("devolvertalonariofc5")) {

				System.out.print(" entro devolvertalonariofc5 ");
				String foliofc5 = request.getParameter("foliofc5");
				model.setFormato(foliofc5);

				int idTalonario = Integer.valueOf(request.getParameter("pk"));
				int folioTalonario = Integer.valueOf(request.getParameter("folio"));

				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				model.setIdnicho(sesion.pkNicho);
				model.setIdtalonario(idTalonario);

				String valida = model.devolvertalonariosfc5(folioTalonario, sesion);

				PrintWriter out = response.getWriter();
				out.println(valida);

			}
			else if (accion.equals("devolvertalonariofc5B")) {

				System.out.print(" entro devolvertalonariofc5B ");
				String foliofc5 = request.getParameter("foliofc5B");
				model.setFormato(foliofc5);

				int idTalonario = Integer.valueOf(request.getParameter("pk"));
				int folioTalonario = Integer.valueOf(request.getParameter("folio"));

				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				model.setIdnicho(sesion.pkNicho);
				model.setIdtalonario(idTalonario);

				String valida = model.devolvertalonariosfc5B(folioTalonario, sesion);
				PrintWriter out = response.getWriter();
				out.println(valida);



			}
			else if (accion.equals("DevolucionMasiva")) {				
				
				//System.out.print(" entro devolucionMasiva nichos ");
				
				String foliofc5 = request.getParameter("foliofc5");
				//String foliofc5 = "P05";				
				String valida = DevolucionMasivaNichos(sesion.pkSorteo,sesion.pkSector,sesion.pkNicho,model,sesion,foliofc5);
				PrintWriter out = response.getWriter();
				out.println(valida);
			
			}
			
			else{

			String talonescadena = request.getParameter("idsTalonarios");
			String[] arrTalonarios = talonescadena.split(",");
			// model.setSectores(sectores);

			model.setIdSorteo(sesion.pkSorteo);
			model.setFormato(request.getParameter("folio"));
			model.setIdSector(sesion.pkSector);
			model.setIdnicho(sesion.pkNicho);
			//model.guardarAsignacionNichoTalonario(arrTalonarios, model,sesion);
			model.guardarAsignacionNichoTalonario_SP(talonescadena, model,sesion);
			

		}

		model.close();
	}
	
	
	
	protected String DevolucionMasivaNichos(int sorteo,int idsector, int idnicho,mAsignacionTalonariosNichos model, SesionDatos sesion,String foliofc5) {		
					
		String valida = "";
		
		boolean totalmenteVendidostal = true;
		boolean totalmenteNoVendidostal = true;
		boolean totalmenteVendidostalDig_sc= true;
		boolean algunBoletoNoVendidoNichos= false;
		boolean talParcialmenteVendidos = true;			
		
		mAsignacionTalonariosColaboradores modelColab = new  mAsignacionTalonariosColaboradores();
		
		model.setIdSorteo(sorteo);
		model.setIdSector(idsector);
		model.setIdnicho(idnicho);
		ResultSet rs = model.ObtenerTalonarios(sorteo,idsector,idnicho);		
		
		
		try {	
			
			
			if (rs.next()) {			
				
				do{
					
					 model.setIdtalonario(rs.getInt("PK1"));
					 model.setFolioTalonario(rs.getString("FOLIO"));
					 model.setSorteo(rs.getString("SORTEO"));
					// model.setFormato(rs.getString("FORMATO"));					
				
					 totalmenteVendidostal = model.getTalonariosCompletamenteVendidos();
					 totalmenteNoVendidostal = model.getTalonariosCompletamenteNoVendidos();
					 totalmenteVendidostalDig_sc = model.getTalonariosDigitalesCompletamenteVendidos_sc();//nuevo
					 algunBoletoNoVendidoNichos = model.ExisteBoletosNichoNV();//nuevo
					 talParcialmenteVendidos = model.getTalParcialmenteVendidos();											
						 
					 
					if( ((!totalmenteVendidostal) && (!totalmenteVendidostalDig_sc)&&(algunBoletoNoVendidoNichos)) && !talParcialmenteVendidos )
					 {							
						 
						model.setFormato(foliofc5);						
						int folioTalonario = Integer.parseInt(model.getFolioTalonario());						
						
						 modelColab.setFormato(foliofc5);
						 modelColab.setIdSorteo(model.getIdSorteo());
						 modelColab.setIdSector(model.getIdSector());
						 modelColab.setIdNicho(model.getIdnicho());
						 modelColab.setIdColaborador(rs.getInt("PK_COLABORADOR"));
						 modelColab.setIdtalonario(model.getIdtalonario());							
							
						  if(!totalmenteNoVendidostal){	//FC5B							  
							  
							  System.out.println("entro boletos parcialmente vendidos" );	
							  System.out.println("entro devolvertalonariofc5B masivo" );							
							
							//DEVOLVER BOLETOS EN COLABORADORES	FC5B 	
						     //if(model.ExisteCargaColaboradorBoletosNoV()){}				 
							  modelColab.devolvertalonariosfc5B( folioTalonario, sesion);							
														  					
							//DEVOLVER BOLETOS NICHOS FC5B							  						 
							   model.devolvertalonariosfc5B(folioTalonario, sesion);						
							  
								 
						  }else{//FC5	
								
								 System.out.println("totalmente no vendidos" );
								 System.out.println("devolvertalonariofc5 masivo" );
									  
							   //DEVOLVER BOLETOS EN COLABORADORES	FC5		 
							   // if(model.ExisteCargaColaborador()){}	
								modelColab.devolvertalonariosfc5(folioTalonario, sesion);
														
								//DEVOLVER BOLETOS NICHOS FC5						
								model.devolvertalonariosfc5(folioTalonario, sesion);					  
						  
						 
					      }			
						 
						 
						 
					}			
						 
						 
						 
				}while (rs.next());				
					
					
					
					
			}else{
			 System.out.println("entro 0 " );
			 valida = "NO_HAY";
			 
			 ;	
			}//No existen talonarios electronicos
			
			
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Factory.Error(ex, "rs="+rs);
			valida = "ERROR";
		}
		
		return valida;
		
		
	}



	protected void BuscarModalBoletos(HttpServletRequest request,HttpServletResponse response, int pg, int show, String search, int idsorteo, int idnicho
			, mAsignacionTalonariosNichos model, SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Folio", "Formato", "Costo", "Talonario" };
		String[] campos = { "FOLIO", "FORMATO_T", "COSTO", "TALONARIO" };

		int numeroregistros = model.contarModalBoletos(idsorteo,sesion.pkSector,search);

		String HTML = CrearTablaModalBoletos(numeroregistros,model.paginacionModalBoletos(pg, show, search,idsorteo,sesion.pkSector), columnas,	campos, pg, show, idsorteo, idnicho, model, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);

	}

	protected String CrearTablaModalBoletos(int numreg, ResultSet rs,String[] columnas, String[] campos, int pg, int show, int idsorteo,int idnicho
			, mAsignacionTalonariosNichos model, SesionDatos sesion) {
		int i = (pg - 1) * show;
		// ResultSet boletos = null;
		// String html2 = "";
		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";
		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";
		// <input id=\"marcarTodo\" type=\"checkbox\" />
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('"
				+ "1b"
				+ "')\" id=\"checkboxall"
				+ "1b"
				+ "\" name=\"checkboxall\" /></th>";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			html += columna;
		}

		html += " </thead>";
		html += " <tbody id=\"Habilitados\">";

		//boolean existe = false;

		try {

		if (Integer.valueOf(numreg) > 0) {


			while (rs.next()) {

				model.setIdBoleto(rs.getInt("PK_BOLETO"));
				model.setIdSorteo(idsorteo);
				model.setIdnicho(idnicho);
				//existe = model.estaBoletoAsignadoNicho(model);
				//System.out.print(" existe: " + existe);

				i++;

				html += "<tr class=\"gradeA odd\" role=\"row\">";

				if (rs.getString("ASIGNADO").equals("1")) {

					html += "<td ><i class=\"fa fa-2x fa-check-circle\"></i></td>";

					// html +=
					// "<td ><i class=\"ion-checkmark-circled fa-2x text-inverse\"></i></td>";

				} else {

					html += "<td class=\"sorting_1\"><input id=" + rs.getInt("PK_BOLETO") + " type=\"checkbox\" class=\"cboletos\"/></td>";

				}

				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";
				}



				html += "</tr>";

				/*
				 * html2 =
				 * "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">"
				 * ; html2 += "<thead>"; html2 +=
				 * "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">Folio</th>"
				 * ; html2 +=
				 * "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">Formato</th>"
				 * ; html2 +=
				 * "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">Costo</th>"
				 * ; html2 += " </thead>"; html2 += " <tbody id=\"\">";
				 *
				 * while(boletos.next()){
				 *
				 * //html += "<tr role=\"row\">"; html +=
				 * "<tr class=\"gradeA odd\" role=\"row\">"; //html +=
				 * "<td class=\"sorting_1\"></td>";
				 *
				 * html2 += "<td class=\"sorting_1\"><input id=" +
				 * boletos.getInt("PK1") + " type=\"checkbox\" /></td>"; html2
				 * += "<td class=\"sorting_1\">" + boletos.getString("FOLIO") +
				 * "</td>"; html2 += "<td class=\"sorting_1\">" +
				 * boletos.getString("FORMATO") + "</td>"; html2 +=
				 * "<td class=\"sorting_1\">" + boletos.getString("COSTO") +
				 * "</td>"; html2 += "</tr>"; }
				 *
				 * html2 += "</tbody>"; html2 += "</table>";
				 */

				// System.out.println(rs.getString("SECTOR"));
			}

		 } else {

				html += "<tr> <td colspan=\"7\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Boletos Asignados al Nicho</h1>";
				html += "<p>Asigne Talonarios al Nicho.</p>";
				html += "</div>";
				html += "</td></tr>";
		 }

		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

		html += "</tbody>";
		html += "</table>";
		// html += html2;

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
			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModalBoletos("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";
		html = paginado + html;

		return html;
	}

	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search
			, mAsignacionTalonariosNichos model, SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Folio", "Num boletos", "Formato", "Monto","Boletos Disponibles" };
		String[] campos = { "FOLIO", "NUMBOLETOS", "FORMATO", "MONTO","DISPONIBLES" };

		int numeroregistros = model.contarModal(sesion.pkSorteo,sesion.pkSector,search);

		String HTML = CrearTablaModal(numeroregistros,model.paginacionModal(pg, show, search,sesion.pkSorteo,sesion.pkSector), columnas, campos, pg,show, model, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTablaModal(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show
			, mAsignacionTalonariosNichos model, SesionDatos sesion) {
		String electronico = "";
		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";
		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";
		// <input id=\"marcarTodo\" type=\"checkbox\" />
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('"
				+ "1t"
				+ "')\" id=\"checkboxall"
				+ "1t"
				+ "\" name=\"checkboxall\" /></th>";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			html += columna;
		}
		html += "<th class=\"sorting\" style=\"width: 40px;\" >Tipo</th>";
		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" >Boletos Disponibles</th>";
		html += " </thead>";
		html += " <tbody id=\"Habilitados\">";

		try {

		if (Integer.valueOf(numreg) > 0) {


			while (rs.next()) {

				i++;


				model.setIdtalonario(rs.getInt("PK1"));
				model.setFolioTalonario(rs.getString("FOLIO"));
				//model.setSorteo(rs.getString("SORTEO"));
				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				model.setIdnicho(sesion.pkNicho);
				System.out.println(">>>nicho: "+sesion.pkNicho);

				model.setFormato(rs.getString("FORMATO"));


			/*	ResultSet talonarioasignado = model.obtenerTalonario(model);
				int disponibles = 0;
					if (talonarioasignado.next()){

						disponibles = talonarioasignado.getInt("DISPONIBLES");

					}else{
						disponibles = rs.getInt("NUMBOLETOS");//CHECAR

					}*/


				html += "<tr class=\"gradeA odd\" role=\"row\">";

				if(/*rs.getString("ASIGNADO").equals("1")&&*/rs.getInt("DISPONIBLES")==0){

					   html += "<td class=\"sorting_1\"><i class=\"fa fa-2x fa-check-circle\"></i></td>";
					}
				else
					html += "<td class=\"sorting_1\"><input id=" + rs.getInt("PK1")	+ " type=\"checkbox\" /></td>";

			//	html += "<td class=\"sorting_1\"><input id=" + rs.getString("PK1")	+ " type=\"checkbox\" /></td>";
				html += "<td class=\"sorting_1\">" + i + "</td>";

				for (String campo : campos) {
					html += "<td class=\"sorting_1\">" + rs.getString(campo)
							+ "</td>";
				}
				// DIGITAL
				electronico = (rs.getString("DIGITAL").compareTo("1") == 0)
						? "<i class='fa fa-credit-card'></i>"
						: "<i class='fa fa-pencil'></i>";

				// TALONARIO
				html += "<td class=\"sorting_1\">" + electronico + "</td>";

				html += "</tr>";
			}


		 } else {

				html += "<tr> <td colspan=\"7\"> ";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Talonarios Asignados a los Nichos</h1>";
				html += "<p>Asigne Talonarios a los Nichos.</p>";
				html += "</div>";
				html += "</td></tr>";
		 }


		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

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
			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModalTalonarios("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";
		html = paginado + html;

		return html;
	}

	protected void BuscarTalonario(HttpServletRequest request,	HttpServletResponse response, int pg, int show, String search,int idsorteo, int idnicho
			, mAsignacionTalonariosNichos model, SesionDatos sesion) throws ServletException, IOException {

		int numeroregistros = model.contarTalonarios(idnicho,sesion.pkSorteo,sesion.pkSector);

		String HTML = CrearTabla(numeroregistros, model.paginacionTalonarios(pg, show, search, idnicho,sesion.pkSorteo,sesion.pkSector), pg,	show,idnicho, model, sesion);
		PrintWriter out = response.getWriter();

		HTML = HTML+"#%#"+model.getTotalregistros();


		out.println(HTML);
	}


	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show,int idnicho, mAsignacionTalonariosNichos model, SesionDatos sesion) {

		//int i = (pg - 1) * show;

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		ResultSet boletos;
		boolean boletodigComprador = false;

		String html = "<ul class=\"result-list\">";

		try {

			if (Integer.valueOf(numreg) > 0) {

				while (rs.next()) {

					 boletodigComprador = false;
					
					
					model.setIdtalonario(rs.getInt("PK1"));
					model.setFolioTalonario(rs.getString("FOLIO"));
					model.setSorteo(rs.getString("PK_SORTEO"));
					model.setFormato(rs.getString("FORMATO"));

					model.setIdSorteo(sesion.pkSorteo);
					model.setIdSector(sesion.pkSector);
				//	model.setIdnicho(sesion.pkNicho);
					model.setIdnicho(idnicho);
					System.out.println(">>>nicho: "+sesion.pkNicho);


					boolean ttalretornados = model.getTalCompletoVendidosRetornados();
					boolean totalmenteVendidostal = model.getTalonariosCompletamenteVendidos();
					boolean totalmenteNoVendidostal = model.getTalonariosCompletamenteNoVendidos();
					boolean totalmenteVendidostalDig_sc = model.getTalonariosDigitalesCompletamenteVendidos_sc();//nuevo
					boolean algunBoletoNoVendidoColab = model.ExisteBoletosNichoNV();//nuevo
					//boolean talParcialmenteVendidos = model.getTalParcialmenteVendidos();

					// DIGITAL
					String electronico = (rs.getString("ELECTRONICO").compareTo("1") == 0)
							? "&nbsp;&nbsp;<i class='fa fa-credit-card' style='font-size:10pt;'></i>"
							: "&nbsp;&nbsp;<i class='fa fa-pencil' style='font-size:10pt;'></i>";

					boletos = model.getBoletos(model);


					html += "<li id=\"talonario" + rs.getString("PK1") + "\">";

					html += "<div class=\"result-info\">";
					html += "<h4 class=\"title\"><a href=\"javascript:;\">TALONARIO FOLIO: <span class=\"badge badge-warning badge-square\">"
							+ rs.getString("FOLIO") + "</span></a>" + electronico + "</h4>";

					if(ttalretornados){html += "<i class=\"fa fa-2x fa-check-circle\"></i>";}


					html += "<p class=\"location\"> BOLETOS: <span class=\"badge badge-danger pull\">"
							+ rs.getString("NUMBOLETOS") + "</span> </p>";
					html += "<p class=\"desc\">";

					while (boletos.next()) {						
						

						if(boletos.getString("VENDIDO").trim().equals("N")){

							/*html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
									+boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
									+boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"
									+boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','N')\">";*/
							
							
							/*if((boletos.getString("FOLIODIGITAL")!= "" && boletos.getString("FOLIODIGITAL")!=null ) || boletos.getInt("COMPRADOR")==1 ){									
								html += "<span class=\"label etiqueta-b-electronicos\" >";//etiqueta-b-electronicos
								 boletodigComprador = true;
								
							}else{	html += "<span class=\"label label-inverse\" >"; }	*/					

							html += "<span class=\"label label-inverse\" >"; 

						}
						else if(boletos.getString("VENDIDO").trim().equals("V")){

							if(boletos.getInt("RETORNADO")==1){

								/* html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
										 +boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
										 +boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"
										 +boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','R')\">";*/




								html += "<span class=\"label etiqueta-retornados\">";

							}else{

								/* html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
										 +boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
										 +boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"
										 +boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','V')\">";*/



								html += "<span class=\"label label-success\" >";

							}


						}
						else if(boletos.getString("VENDIDO").trim().equals("P")){

							 /*html += "<a href=\"javascript:ShowDetalleBoleto('"+sesion.pkSorteo+"','"+boletos.getString("PK_BOLETO")+"','"
									 +boletos.getString("FOLIO")+"','"+boletos.getString("PK_TALONARIO")+"','"+boletos.getString("TALONARIO")+"','"
									 +boletos.getString("ABONO")+"','"+boletos.getString("COSTO")+"','"+boletos.getString("PK_SECTOR")+"','"
									 +boletos.getString("PK_NICHO")+"','"+boletos.getString("PK_COLABORADOR")+"','P')\">";*/
                            
							
							 boletodigComprador = true;
							html += "<span class=\"label label-default\" >";


						}

						html += boletos.getString("FOLIO");

					   html +="</span></a> ";

					}

					html += "</p>";

					html += "<div class=\"btn-row\">";


				 /*	   if (sesion.permisos.havePermiso(10146)){

						 html += "<a data-title=\"Devolver Talonario completo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModalEliminarTalonario('"
									+ rs.getString("PK1")+ "','"
									+ rs.getString("FOLIO") + "','"
									+ rs.getString("PK_TALONARIO") + "','"
									+ rs.getString("SORTEO")+"');\"	 data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">Devolver</span></a>";


					}else{

						 html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"#\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">Devolver</span></a> ";

					}*/


					if(sesion.sorteoActivo)
					{
						//FC4
						if (sesion.permisos.havePermiso(10146) ) {							
							
							
				             if(rs.getString("ELECTRONICO").compareTo("1") != 0){	
							

									if(totalmenteVendidostal){
		
										html += "<a data-title=\"Es el Retorno de un Talonario de boletos TOTALMENTE vendidos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModalRetornoTalonarioFC4('"
												+ rs.getString("PK1") + "','"
												+ rs.getString("FOLIO") + "','"
												+ rs.getString("PK_SORTEO") + "','"
												+ rs.getString("FORMATO")
												+ "');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>";
									}
									else{
		
										html += "<a data-title=\"El Talonario no esta TOTALMENTE vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeRetornarFC4();\" "
											+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>";
									}
									
				             }else{
								  
								/*  html += "<a data-title=\"El Talonario es Electronico y No Retorna Talonarios\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeRetornarDigitalTalFC4();\" "
											+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>";*/
								  
							  }
							
							
							
						}
						else
							html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC4 Retorno Vendidos</span></a> ";

						//FC5
						if (sesion.permisos.havePermiso(20104)) {
							
															
							 if(rs.getString("ELECTRONICO").compareTo("1") == 0 && boletodigComprador == true)
	                    	 {//el talonario es dig y tiene por lo menos 1 boleto con comprador y folio dig
	                    	
	                    		 html += "<a data-title=\"El Talonario es Electronico y tiene algun(os) boleto(s) con comprador y folio digital!\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverTalDigF5();\" "
		    								+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>";
	                    	
	                   
	                         }else{							

									 if(totalmenteNoVendidostal){								 
										 
										 if(model.ExisteCargaColaborador()){
		
											html += "<a data-title=\"El Talonario existe en Colaboradores!\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeCargaNF5();\"	  "
												 + "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>";
		
										}else{
		
											 html += "<a data-title=\"Es la Devoluci&oacute;n de un talonario SIN vender ni un solo boleto\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModalDevolverTalonarioFc5('"
													+ rs.getString("PK1") + "','"
													+ rs.getString("FOLIO") + "','"
													+ rs.getString("PK_SORTEO") + "','"
													+ rs.getString("FORMATO")
													+ "');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>";
										 }
									 }
									else{
										html += "<a data-title=\"El Talonario no esta TOTALMENTE NO vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverF5();\" "
											+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>";
									 }							 
							 
							 
						      }
							 
						}
						else
							html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC5 Devoluci&oacute;n No vendidos</span></a> ";


						//FC5B
						if (sesion.permisos.havePermiso(20105)) {

							 if( ((!totalmenteVendidostal) && (!totalmenteNoVendidostal)&& (!totalmenteVendidostalDig_sc)&&(algunBoletoNoVendidoColab)) /*&& !talParcialmenteVendidos*/ ){

								  if(model.ExisteCargaColaboradorBoletosNoV()){

										html += "<a data-title=\"Existen Boletos No vendidos en Colaboradores!.\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeCargaNF5B();\" "
											+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>";
									}else{
										 html += "<a data-title=\"Es la Devoluci&oacute;n de boletos no vendidos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:ModalDevolTalonarioFc5b('"
												+ rs.getString("PK1") + "','"
												+ rs.getString("FOLIO") + "','"
												+ rs.getString("PK_SORTEO") + "','"
												+ rs.getString("FORMATO")
												+ "');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>";
									}

							 }
							else{
								html += "<a data-title=\"El Talonario esta TOTALMENTE vendido o TOTALMENTE NO vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverF5B();\" "
										+ "data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>";
							 }
						}
						else
							html += "<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC5B Devoluci&oacute;n No vendidos Parcialmente</span></a> ";
					}


					html += "<a data-title=\"\" data-container=\"body\" data-toggle=\"tooltip\" href=\"#\" data-original-title=\"\" title=\"\"><span style=\"font-size:11px;\"></span></a>";
					html += "</div>";

					html += "</div>";

					double monto = Double.parseDouble(rs.getString("MONTO"));

					Locale english = new Locale("en", "US");
					NumberFormat englishFormat = NumberFormat
							.getCurrencyInstance(english);
					System.out.println("US: " + englishFormat.format(monto));

					html += "<div class=\"result-price\">";
					html += englishFormat.format(monto)+ " <small>MONTO</small>";
					html += "<a class=\"btn btn-inverse btn-block\" href=\"SeguimientoTalonarios?idsorteo="+model.getIdSorteo()+"&idtalonario="+rs.getString("PK_TALONARIO")+"\">Ver Detalles</a>";
					html += "</div>";

					html += "</li>";

					// System.out.println(rs.getString("NOMBRE"));

				}

				html += "</ul>";

			} else {

				html += "<li align= \"center\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";

				html += "<h1>No existen talonarios asignados al nicho</h1>";
				html += "<p>Empiece por agregar talonarios o boletos al nicho.</p>";

				html += "</div>";

				html += "</li>";

			}

		} catch (SQLException ex) { Factory.Error(ex, "rs="+rs); }

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

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		html = paginado + html + paginadoright;

		return html;

	}
}
