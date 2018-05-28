package com.sorteo.sorteos.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
@WebServlet("/AsignacionTalonarios")
public class AsignacionTalonarios extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AsignacionTalonarios() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		Factory vista = new Factory();
		mAsignacionTalonarios model = new mAsignacionTalonarios();
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
		 String contents="";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String view = request.getParameter("view");
		
		
		//10026 ACCESO A LA LISTA DE TALONARIOS
		if (!sesion.permisos.havePermiso(10105)){view = "error";}
		

		if (view == null) {
			view = "";
		}

		System.out.println("view=" + view);

		switch (view) {

		case "Agregar":
			fullPath = "/WEB-INF/views/sorteos/agregar.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 34, sesion);
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
						
			BuscarTalonario(request, response, pg, show, search, sesion.pkSorteo, sesion.pkSector,model,sesion);
			
			
			
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
			BuscarModal(request, response, pg, show, search,model,sesion);
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
			
			BuscarModalBoletos(request, response, pg, show, search, sesion.pkSorteo, sesion.pkSector, model, sesion);
			break;
			
		case "BuscaIntervaloTalonarios":
			
			HTML = BuscaIntervaloTalonarios(request, response, sesion.pkSorteo, model, sesion);
			out.println(HTML);
			
			break;

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		 case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 34, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);
			out.println(HTML);
			break;	
			
		default:
			
			int idsector = Integer.valueOf(request.getParameter("idsector"));
			fullPath = "/WEB-INF/views/sorteos/Talonarios/AsignacionTalonarios.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 34, sesion);
			notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,infouser);
			if (sesion.pkSector != idsector) {
				sesion.pkSector = idsector;
				sesion.guardaSesion();
			}
			
			String regex ="";
			
			HTML = HTML.replaceAll("#SECTOR#", model.Sector(sesion.pkSector));
			
			regex = "#URLSECTORES#";
			url = "SectoresAsignados?idsorteo="+sesion.pkSorteo;
			HTML = HTML.replaceAll(regex, url);
			
			regex = "#URLTALONARIO#";
			url = "AsignacionTalonarios?idsorteo="+sesion.pkSorteo+"&idsector="+sesion.pkSector;
			HTML = HTML.replaceAll(regex, url);
			
			String boton = "";
			
			/*if (sesion.sorteoActivo && sesion.permisos.havePermiso(10113)){
				
				boton = "<a class=\"btn btn-primary m-r-5\" disabled href=\"#\" onclick=\"AsignarTalonario()\">Asignar Talonario</a>";
			}*/
			
			HTML = HTML.replaceAll( "#BTNASIGNA#", boton);
			
			regex = "#SORTEO#";
			contents = "";
			model.setIdSorteo(sesion.pkSorteo);
			model.consultaSorteo();
			contents = HTML.replaceAll("#IDSORTEO#", Integer.toString(model.getIdSorteo()));
			contents = contents.replaceAll("#SORTEO#", model.getSorteo());
			
			out.println(contents);
			break;
		}
		model.close();

	}

	private String BuscaIntervaloTalonarios(HttpServletRequest request ,HttpServletResponse response,
			int pkSorteo, mAsignacionTalonarios model, SesionDatos sesion) throws IOException {
		String ERROR = "-1";
		ArrayList<String> Asignados = new ArrayList<String>();
		ArrayList<String> Disponibles = new ArrayList<String>();
		try{
			int limiteInf = Integer.parseInt(request.getParameter("limiteInf"));
			int limiteSup = Integer.parseInt(request.getParameter("limiteSup"));
			HashMap<Integer,Integer> map = model.buscaIntervaloTalonarios(limiteInf, limiteSup, pkSorteo);

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//Factory vista = new Factory();
		mAsignacionTalonarios model = new mAsignacionTalonarios();
		SesionDatos sesion;
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		
		String accion = request.getParameter("accion");

		if (accion == null)
			accion = "";
		if (accion.equals("aboletos")) {
			
			//asignaboletos
			String boletoscadena = request.getParameter("idboletos");		
			String[] arrBoletos = boletoscadena.split(",");
		
			model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
			model.setFormato(request.getParameter("folio"));
			model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
		    model.guardarAsignacionSectorBoletos_SP(boletoscadena, sesion);
		  //  model.guardarAsignacionSectorBoletos(arrBoletos, sesion);

		}
       else if (accion.equals("retornotalonariosfc4")) {			
		
			
			String foliofc4 = request.getParameter("foliofc4");
			model.setFormato(foliofc4);
			
			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			int folioTalonario = Integer.valueOf(request.getParameter("folio"));
			
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);			
			model.setIdtalonario(idTalonario);
			
			 model.retornotalonariosfc4(folioTalonario, sesion);
			
			
			//String valida =  model.retornotalonariosfc4(folioTalonario, sesion);
			
		/*	PrintWriter out = response.getWriter();
			out.println(valida);*/
			
		
			
		}
        else if (accion.equals("devolvertalonariofc5")) {			
			
			
			String foliofc5 = request.getParameter("foliofc5");
			model.setFormato(foliofc5);
			
			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			int folioTalonario = Integer.valueOf(request.getParameter("folio"));
			
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);			
			model.setIdtalonario(idTalonario);
			
			model.devolvertalonariosfc5(folioTalonario, sesion);
			
			//String valida = model.devolvertalonariosfc5(folioTalonario, sesion);
			
			//PrintWriter out = response.getWriter();
		//	out.println(valida);
			
			
			
		}
        else if (accion.equals("devolvertalonariofc5B")) {	
    	 
	
    	    String foliofc5 = request.getParameter("foliofc5B");
			model.setFormato(foliofc5);
	
			int idTalonario = Integer.valueOf(request.getParameter("pk"));
			int folioTalonario = Integer.valueOf(request.getParameter("folio"));
	
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdSector(sesion.pkSector);
			model.setIdtalonario(idTalonario);
			
			//model.devolvertalonariosfc5B(folioTalonario, sesion);
			
			String valida = model.devolvertalonariosfc5B(folioTalonario, sesion);
			PrintWriter out = response.getWriter();
		    out.println(valida);
	
	
        }
		
        else if (accion.equals("DevolucionMasiva")) {				
			
			System.out.print(" entro devolucionMasiva sectores ");
			
			String foliofc5 = request.getParameter("foliofc5");
			//String foliofc5 = "P05";				
			String valida = DevolucionMasivaSectores(sesion.pkSorteo,sesion.pkSector,model,sesion,foliofc5);
			PrintWriter out = response.getWriter();
			out.println(valida);
		
		}
		else{
			
			String electronico = request.getParameter("electronico");
            
			//asignatalonarios
			String talonescadena = request.getParameter("idsTalonarios");
			String[] arrTalonarios = talonescadena.split(",");		

			model.setIdSorteo(Integer.valueOf(request.getParameter("idsorteo")));
			model.setFormato(request.getParameter("folio"));
			model.setIdSector(Integer.valueOf(request.getParameter("idsector")));
			//model.guardarAsignacionSectorTalonario(arrTalonarios, sesion, electronico);			
			model.guardarAsignacionSectorTalonario_SP(talonescadena, sesion, electronico);
			
			

		}
        //model.close();***VERIFICAR
	}
	
	
	protected String DevolucionMasivaSectores(int sorteo,int idsector,mAsignacionTalonarios model, SesionDatos sesion,String foliofc5) {		
		
		String valida = "";
		
		boolean totalmenteVendidostal = true;
		boolean totalmenteNoVendidostal = true;
		boolean totalmenteVendidostalDig_sc= true;
		boolean algunBoletoNoVendidoSectores= true;
		//boolean algunBoletoNoVendidoNichos= false;
		boolean talParcialmenteVendidos = true;			
		
		mAsignacionTalonariosNichos modelNichos = new  mAsignacionTalonariosNichos();
		mAsignacionTalonariosColaboradores modelColab = new  mAsignacionTalonariosColaboradores();
		
		model.setIdSorteo(sorteo);
		model.setIdSector(idsector);
		//model.setIdnicho(idnicho);
		ResultSet rs = model.ObtenerTalonarios(sorteo,idsector);				
		
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
					 algunBoletoNoVendidoSectores = model.ExisteBoletosSectoresNV();//nuevo
					 talParcialmenteVendidos = model.getTalParcialmenteVendidos();											
						 
					 
					if( ((!totalmenteVendidostal) && (!totalmenteVendidostalDig_sc)&&(algunBoletoNoVendidoSectores)) && !talParcialmenteVendidos )
					 {				
						
						
						// System.out.println("entro VALIDA" );
						//param sectores
						model.setFormato(foliofc5);						
						int folioTalonario = Integer.parseInt(model.getFolioTalonario());				
						
						
						//param nicho
						modelNichos.setFormato(foliofc5);
						modelNichos.setIdSorteo(model.getIdSorteo());
						modelNichos.setIdSector(model.getIdSector());						
						modelNichos.setIdnicho(rs.getInt("PK_NICHO"));					
						modelNichos.setIdtalonario(model.getIdtalonario());	
						 
						//param colaboradores
						 modelColab.setFormato(foliofc5);
						 modelColab.setIdSorteo(model.getIdSorteo());
						 modelColab.setIdSector(model.getIdSector());
						 modelColab.setIdNicho(rs.getInt("PK_NICHO"));
						 modelColab.setIdColaborador(rs.getInt("PK_COLABORADOR"));
						 modelColab.setIdtalonario(model.getIdtalonario());	
							
						  if(!totalmenteNoVendidostal){	//FC5B							  
							  
							  System.out.println("entro boletos parcialmente vendidos" );	
							  System.out.println("entro devolvertalonariofc5B masivo" );							
							
							//DEVOLVER BOLETOS EN COLABORADORES	FC5B 	
						     //if(model.ExisteCargaColaboradorBoletosNoV()){}				 
							  modelColab.devolvertalonariosfc5B( folioTalonario, sesion);							
														  					
							//DEVOLVER BOLETOS NICHOS FC5B							  						 
							  modelNichos.devolvertalonariosfc5B(folioTalonario, sesion);
							   
							   
							 //DEVOLVER BOLETOS SECTORES FC5B							  						 
							  model.devolvertalonariosfc5B(folioTalonario, sesion);						
							  
								 
						  }else{//FC5	
								
								 System.out.println("totalmente no vendidos" );
								 System.out.println("devolvertalonariofc5 masivo" );
									  
							   //DEVOLVER BOLETOS EN COLABORADORES	FC5		 
							   // if(model.ExisteCargaColaborador()){}	
								  modelColab.devolvertalonariosfc5(folioTalonario, sesion);
														
								//DEVOLVER BOLETOS NICHOS FC5						
								  modelNichos.devolvertalonariosfc5(folioTalonario, sesion);	
								
								//DEVOLVER BOLETOS SECTORES FC5							  						 
								  model.devolvertalonariosfc5(folioTalonario, sesion);	
						 
					      }			
						 
						 
						 
					}			
						 
					// System.out.println("salida" ); 
						 
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

	protected void BuscarModalBoletos(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int idsorteo, int idsector,mAsignacionTalonarios model,SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Folio", "Formato", "Costo", "Talonario" };
		String[] campos = { "FOLIO", "FORMATO", "COSTO", "FOLIO_TALONARIO" };
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS");
		System.out.println(":::>" + sdf.format(cal.getTime()) );

		int numeroregistros = model.contarModalBoletos(idsorteo,search);

		cal = Calendar.getInstance();
		System.out.println(":::>" + sdf.format(cal.getTime()) );

		ResultSet res = model.paginacionModalBoletos(pg, show, search,idsorteo);
		

		cal = Calendar.getInstance();
		System.out.println(":::>" + sdf.format(cal.getTime()) );
		
		String HTML = CrearTablaModalBoletos(numeroregistros,res, columnas,
				campos, pg, show, idsorteo, idsector,model,sesion);

		cal = Calendar.getInstance();
		System.out.println(":::>" + sdf.format(cal.getTime()) );
		
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTablaModalBoletos(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show, int idsorteo,
			int idsector,mAsignacionTalonarios model,SesionDatos sesion) {
        
		// TODO Auto-generated method stub
		int i = (pg - 1) * show;
		// ResultSet boletos = null;
		// String html2 = "";
		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";
		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		//String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		StringBuilder sb = new StringBuilder(50000);
		sb.append("<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">")
		.append("<thead>")
		.append("<tr role=\"row\">")
		.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onClick=\"seleccionarTodo('")
				.append("1b")
				.append("')\" id=\"checkboxall")
				.append("1b")
				.append("\" name=\"checkboxall\" /></th>")
		
		.append("<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>");
		for (String columna : campostable) {
			sb.append(columna);
		}

		sb.append(" </thead>");
		sb.append(" <tbody id=\"Habilitados\">");

		try {
			
			
			if (Integer.valueOf(numreg) > 0) {	
				
				while (rs.next()) {
	
					model.setIdBoleto(rs.getInt("PK1"));
					model.setIdSorteo(idsorteo);
					model.setIdSector(idsector);
					//existe = model.estaBoletoAsignadoSector(model);
					//System.out.print(" existe: " + existe);
	
					i++;
	
					sb.append("<tr class=\"gradeA odd\" role=\"row\">");
	
					if (rs.getString("ASIGNADO").equals("1")) {
	
						sb.append("<td ><i class=\"fa fa-2x fa-check-circle\"></i></td>");
					} else {
						//sb.append("<td class=\"sorting_1\"><input id=").append(rs.getInt("PK1")).append("#%#").append(rs.getString("PK_TALONARIO")).append(" type=\"checkbox\" class=\"cboletos\"/></td>");
						sb.append("<td class=\"sorting_1\"><input id=").append(rs.getInt("PK1")).append(" type=\"checkbox\" class=\"cboletos\"/></td>");
					}
	
					sb.append("<td class=\"sorting_1\">").append(i).append("</td>");
	
					for (String campo : campos) {
						sb.append("<td class=\"sorting_1\">").append(rs.getString(campo)).append("</td>");
					}
					sb.append("</tr>");
	
				}
			} else {
				sb
				 .append( "<tr> <td colspan=\"6\"> ")
				 .append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">")
				 .append("<h1>No existen Boletos</h1>")
				 .append("<p>Asigne Talonarios al Sorteo.</p>")
				 .append("</div>")
				 .append("</td></tr>");
			}
		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

		sb
		.append("</tbody>")
		.append("</table>");
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
		//html = paginado + html;
		
		sb.insert(0, paginado);
		//sb.insert(0,"<p>Hola</p>");

		return sb.toString();
	}

	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,mAsignacionTalonarios model,SesionDatos sesion)
			throws ServletException, IOException {

		String[] columnas = { "Folio", "Num boletos","Formato", "Monto", "Boletos Disponibles"};
		String[] campos = { "FOLIO", "NUMBOLETOS", "FORMATO", "MONTO", "DISPONIBLES" };

		int numeroregistros = model.contarModal( sesion.pkSorteo,search);

		String HTML = CrearTablaModal(numeroregistros,model.paginacionModal(pg, show, search,sesion.pkSorteo), columnas, campos, pg,
				show,model,sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}

	protected String CrearTablaModal(int numreg, ResultSet rs,
			String[] columnas, String[] campos, int pg, int show,mAsignacionTalonarios model,SesionDatos sesion) {
		String electronico = "";
		// TODO Auto-generated method stub
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
					model.setIdSorteo(sesion.pkSorteo);
					model.setIdSector(sesion.pkSector);				
					model.setFormato(rs.getString("FORMATO"));
					
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					
					html += "<td class=\"sorting_1\">";
					
					if(/*rs.getString("ASIGNADO").equals("1") &&*/ rs.getInt("DISPONIBLES")==0){
					   html += "<i class=\"fa fa-2x fa-check-circle\"></i>";					
					}
					else
					   html += "<input id=" + rs.getInt("PK1")	+ " type=\"checkbox\" />";
					
					html += "</td>";
					html += "<td class=\"sorting_1\">" + i + "</td>";
	
					for (String campo : campos) {
						html += "<td class=\"sorting_1\">" + rs.getString(campo) + "</td>";
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
					html += "<h1>No existen Talonarios</h1>";
					html += "<p>Asigne talonarios al Sorteo.</p>";
					html += "</div>";
					html += "</td></tr>";
			}			


		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }

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
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModal("
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
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModal("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModal("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModal("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";
		html = paginado + html;

		return html;
	}

	protected void BuscarTalonario(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int idsorteo, int idsector,mAsignacionTalonarios model,SesionDatos sesion) throws ServletException, IOException {

		int numeroregistros = model.contarTalonarios(idsector,idsorteo);
	

		String HTML = CrearTabla(numeroregistros,	model.paginacionTalonarios(pg, show, search, idsector,idsorteo), pg,
				show,model,sesion);
		PrintWriter out = response.getWriter();
		
		HTML = HTML+"#%#"+model.getTotalregistros();
		
		out.println(HTML);
	}
	
	
	/*function  getTalCompletoVendidosRetornados (){
		
		model.talonario =
				
				model.cola
		boolean ttalretornados = model.getTalCompletoVendidosRetornados();
		
		
	}*/
	
	

	protected String CrearTabla(int numreg, ResultSet talonarios, int pg, int show,mAsignacionTalonarios model,SesionDatos sesion) {
		// TODO Auto-generated method stub

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		ResultSet boletos;
		boolean boletodigComprador = false;		
		

		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"result-list\">");
		//String html = "<ul class=\"result-list\">";

		try {

			if (Integer.valueOf(numreg) > 0) {
				
				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(sesion.pkSector);
				
				//model.cargaSectores();
				//model.cargaNichos();

				while (talonarios.next()) {
					
					 boletodigComprador = false;
					
					model.setIdtalonario(talonarios.getInt("PK1"));//PK_TALONARIO AS PK1 
					model.setFolioTalonario(talonarios.getString("FOLIO"));
					model.setFormato(talonarios.getString("FORMATO"));
					model.consultaNichoColaboradorDeTalonario();
					
					//String TalonarioVendido = rs.getString("VENDIDO");
					
					//model.setIdSorteo(sesion.pkSorteo);
					//model.setIdSector(sesion.pkSector);
					
					boolean ttalretornados = model.getTalCompletoVendidosRetornados();
					boolean totalmenteVendidostal = model.getTalonariosCompletamenteVendidos();
					boolean totalmenteNoVendidostal = model.getTalonariosCompletamenteNoVendidos();
					boolean totalmenteVendidostalDig_sc = model.getTalonariosDigitalesCompletamenteVendidos_sc();//nuevo
					boolean algunBoletoNoVendidoSectores = model.ExisteBoletosSectoresNV();//nuevo
					//boolean talParcialmenteVendidos = model.getTalParcialmenteVendidos();
					
				

					// DIGITAL 
					String electronico = (talonarios.getString("DIGITAL").compareTo("1") == 0)
							? "&nbsp;&nbsp;<i class='fa fa-credit-card' style='font-size:10pt;'></i>"
							: "&nbsp;&nbsp;<i class='fa fa-pencil' style='font-size:10pt;'></i>";
					
					boletos = model.consultaBoletos();

					sb
						.append("<li id=\"talonario").append(talonarios.getString("PK1")).append("\">")
						.append("<div class=\"result-info\">")
						.append("<h4 class=\"title\"><a href=\"javascript:;\">TALONARIO FOLIO: <span class=\"badge badge-warning badge-square\"></i>")
						.append(talonarios.getString("FOLIO")).append("</span></a>").append(electronico).append("</h4>");
					
					if(ttalretornados){sb.append("<i class=\"fa fa-2x fa-check-circle\"></i>");}
					
					sb
						.append("<p class=\"location\"> BOLETOS: <span class=\"badge badge-danger pull\">")
						.append(talonarios.getString("NUMBOLETOS")).append("</span> </p>")
						.append("<p class=\"desc\">");

					while (boletos.next())
					{
						String flag = "";
						
						if(boletos.getString("VENDIDO").trim().equals("N")){
							flag = "N";
							
							/*if((boletos.getString("FOLIODIGITAL")!= "" && boletos.getString("FOLIODIGITAL")!=null ) || boletos.getInt("COMPRADOR")==1 ){
								 boletodigComprador = true;
								 flag = "ND";
								
							}*/
							
						
						}
						else if(boletos.getString("VENDIDO").trim().equals("V")){
							if(boletos.getInt("RETORNADO")==1)
								flag = "R";
							else
								flag = "V";
						}
						if(boletos.getString("VENDIDO").trim().equals("P")){
							flag = "P";
						
						   boletodigComprador = true;
						  // flag = "ND";

						/*sb
							.append("<a href=\"javascript:ShowDetalleBoleto('").append(model.getIdSorteo()).append("','").append(boletos.getString("PK_BOLETO")).append("','")
							.append(boletos.getString("FOLIO")).append("','").append(model.getIdtalonario()).append("','").append(model.getFolioTalonario()).append("','")
							.append(boletos.getString("ABONO")).append("','").append(boletos.getString("COSTO")).append("','").append(model.getIdSector()).append("','")
							.append(model.getIdnicho()).append("','").append(model.getIdColaborador())
							.append("','").append(flag).append("')\">")
							;*/
						}
						
						
						
						
						switch(flag){
							case "N": sb.append("<span class=\"label label-inverse\" >"); break;
							//case "ND": sb.append("<span class=\"label etiqueta-b-electronicos\" >"); break;
							case "R": sb.append("<span class=\"label label-retornados\" >"); break;
							case "V": sb.append("<span class=\"label label-success\" >"); break;
							case "P": sb.append("<span class=\"label label-default\" >"); break;
						}
						
						sb.append(boletos.getString("FOLIO")); 
						sb.append("</span></a> ");
					}
					
					sb.append("</p>");
					
					sb.append("<div class=\"btn-row\">");
					
					if(sesion.sorteoActivo)
					{
						//FC4
						if (sesion.permisos.havePermiso(10114) ) {
							
							
							
							  if(talonarios.getString("DIGITAL").compareTo("1") != 0)
							  {                      				
							
								
									if(totalmenteVendidostal){
											
										sb.append("<a data-title=\"Es el Retorno de un Talonario de boletos TOTALMENTE vendidos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModalRetornoTalonarioFC4('")
													.append(talonarios.getString("PK1") + "','")
													.append(talonarios.getString("FOLIO") + "','")
													.append(talonarios.getString("PK_SORTEO") + "','")
													.append(talonarios.getString("FORMATO"))
													.append("');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>");
										}
										else										
											sb.append("<a data-title=\"El Talonario no esta TOTALMENTE vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeRetornarFC4();\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>");
																	
							
	                          }else	{
	                        	  
	                        	  //sb.append("<a data-title=\"El Talonario es Electronico y No Retorna Talonarios\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeRetornarDigitalTalFC4();\" 	data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC4 Retorno Vendidos</b></span></a>");
	                        	  
	                          }
							  
						
						}
						else
							sb.append("<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC4 Retorno Vendidos</span></a> ");
					
						//FC5
						if (sesion.permisos.havePermiso(40134)){//20106 no existia							
							
							
							if(talonarios.getString("DIGITAL").compareTo("1") == 0 && boletodigComprador == true)
	                    	 {//el talonario es dig y tiene por lo menos 1 boleto con comprador y folio dig
	                    	
								sb.append("<a data-title=\"El Talonario es Electronico y tiene algun(os) boleto(s) con comprador y folio digital!\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverTalDigF5();\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>");
	                    	
	                   
	                         }else{								
							
									if(totalmenteNoVendidostal){
			                    	                    		 
			                    		   if(model.ExisteCargaNichos()){	                    			
			                    			   sb.append("<a data-title=\"El Talonario existe en Nichos!\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeCargaNF5();\"  data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>");
			                        			                    			
			                    		 	}else{
			                    			
										 		sb.append("<a data-title=\"Es la Devoluci&oacute;n de un talonario SIN vender ni un solo boleto\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModalDevolverTalonarioFc5('")
										 				.append(talonarios.getString("PK1")).append("','")
										 				.append(talonarios.getString("FOLIO")).append("','")
										 				.append(talonarios.getString("PK_SORTEO")).append("','")
										 				.append(talonarios.getString("FORMATO"))
										 				.append("');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>");
			                    			
			                    			
			                    		 }  						 
									  
									
			                       }else	                    		
			                    	   sb.append("<a data-title=\"El Talonario no esta TOTALMENTE NO vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverF5();\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5 Devoluci&oacute;n No vendidos</b></span></a>");
			                    		
	                    								
						  }
							
	                    	
							
						}else
	                    	sb.append("<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC5 Devoluci&oacute;n No vendidos</span></a> ");
						
	                    
	                    //FC5B
						if (sesion.permisos.havePermiso(20107)) {							
							
							
							
							 if( ((!totalmenteVendidostal) && (!totalmenteNoVendidostal)&&(!totalmenteVendidostalDig_sc)&&(algunBoletoNoVendidoSectores))/* && !talParcialmenteVendidos */){
	                    	
							        		                     		 
	                    		if(model.ExisteCargaNichosBoletosNoV()){	                      			
	                  			                          		
	                    			  sb.append("<a data-title=\"Existen Boletos No vendidos en Nichos!.\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeCargaNF5B();\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>");
	                      		
	                  			
	                  		 	}else{	                  		 	
	         						
								 		sb.append("<a data-title=\"Es la Devoluci&oacute;n de boletos no vendidos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:ModalDevolTalonarioFc5b('")
						 				.append(talonarios.getString("PK1")).append("','")
						 				.append(talonarios.getString("FOLIO")).append("','")
						 				.append(talonarios.getString("PK_SORTEO")).append("','")
						 				.append(talonarios.getString("FORMATO"))
						 				.append("');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>");
	                  			
	                  		 	    } 		  
							  
							
	                    	}
	                    	else{
	                    		
	                    		sb.append("<a data-title=\"El Talonario esta TOTALMENTE vendido o TOTALMENTE NO vendido\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:mensajeDevolverF5B();\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\"><b>FC5B Devoluci&oacute;n No vendidos Parcialmente</b></span></a>");
	                    		
	                    		
	                    	 }                  	
	                    	
	                    	
							
						}
						else
							sb.append("<a data-title=\"Sin Permisos\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:void(-1);\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">FC5B Devoluci&oacute;n No vendidos Parcialmente</span></a> ");

					}
					
					sb
					.append("<a data-title=\"\" data-container=\"body\" data-toggle=\"tooltip\" href=\"#\" data-original-title=\"\" title=\"\"></span></a>")
					.append("</div>")
					.append("</div>");

					double monto = Double.parseDouble(talonarios.getString("MONTO"));

					Locale english = new Locale("en", "US");
					NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
					System.out.println("US: " + englishFormat.format(monto));

					sb
					.append("<div class=\"result-price\">")
					.append(englishFormat.format(monto))
					.append(" <small>MONTO</small>")
					.append("<a class=\"btn btn-inverse btn-block\" href=\"SeguimientoTalonarios?")
					.append("idsorteo=").append(model.getIdSorteo())
					.append("&idtalonario=").append(talonarios.getString("PK_TALONARIO"))
					.append("&menu=2\">Ver Detalles</a>")
					.append("</div>")
					.append("</li>");

					// System.out.println(rs.getString("NOMBRE"));

				}

				sb.append( "</ul>");

			} else {
				sb

				.append("<li align= \"center\">")
				.append("<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">")
				.append("<h1>No existen talonarios asignados al sector</h1>")
				.append("<p>Empieze por agregar talonarios o boletos al sector.</p>")
				.append("</div>")
				.append("</li>");
			}

		} catch (SQLException e) { Factory.Error(e, "rs="+talonarios); }

		/* *
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
		*/
		
		String paginado = Factory.paginado_2(numreg, show, pg); 
		sb.insert(0, paginado).append(paginado);

		/*
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

		//html = paginado + html + paginadoright;
		sb.insert(0, paginado);
		sb.append(paginadoright);
		//*/
		/*
		String paginado =
				"<div class=\"clearfix\" style=\"margin-top:20px; margin-\">" +
				Factory.Paginado(numreg, show, pg) +
				"</div>";
		
		sb.insert(0, paginado);
		sb.append(paginado);
		//*/

		return sb.toString();
	}
	
}



