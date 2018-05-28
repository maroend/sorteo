package com.sorteo.ventas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.ventas.model.mAsignacionNicho;

/**
 * Servlet implementation class AsignacionNicho
 */
@WebServlet("/AsignacionNicho")
public class AsignacionNicho extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsignacionNicho() {
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
		mAsignacionNicho model = new mAsignacionNicho();
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(20091)){view = "error";}
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int idSorteo = 0;
		int idSector = 0;
		

		if (view == null) {
			view = "";
			
			if(request.getParameter("sorteo")!=null){
				  fullPath = "/WEB-INF/views/dashboard.html";
				  view = "Dashboard";
				  System.out.println("entra en este noo");
					
				 }else{
				  view = "ListaSorteos";
				 }
		}
		
		switch (view) {
		
		case "ListaSorteos":
			SorteoPredeterminado(request,response,HTML, model, sesion);
       	
			break;
			/*
		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
			Buscar(request, response, pg, show, search);
			
			break;
			*/

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 19, 11, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			break;	

		default:
			fullPath = "/WEB-INF/views/ventas/seguimientonicho.html";
			menu = vista.initMenu(0, false, 19, 11, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			idSorteo = Integer.parseInt(request.getParameter("sorteo"));
			idSector = Integer.parseInt(request.getParameter("sector"));
			
			HTML = this.Contenido(HTML,idSorteo,idSector, model);
			
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
		
		model.close();
		
	}
	
	protected void SorteoPredeterminado(HttpServletRequest request,
			HttpServletResponse response, String HTML, mAsignacionNicho model,
			SesionDatos sesion) throws ServletException, IOException {

		model.setIdUsuario(sesion.pkUsuario);
		model.getUsuarioSorteo();

		long sorteo = model.getIdsorteo();
		long sector = model.getIdsector();
		long nicho = model.getIdnicho();

		System.out.println(sorteo);

					if(sorteo!=0 && sector==0 && nicho==0){
						response.sendRedirect("Asignacion?sorteo="+sorteo);	
					}else if(sorteo!=0  && sector!=0 && nicho==0){
						response.sendRedirect("AsignacionSector?sorteo="+sorteo+"&sector="+sector);
					}else if(sorteo!=0  && sector!=0 && nicho!=0){
						response.sendRedirect("AsignacionNicho?sorteo="+sorteo+"&sector="+sector+"&nicho="+nicho);
					}
	}

	private String Contenido(String HTML,int idsorteo, int idsector, mAsignacionNicho model){
		
		String contenido = HTML;
		String regex = null;
		String dato = null;
		int numtalonarios = 0;
		int numboletos = 0;
		int numtalonariosasignados = 0;
		//int numboletosasignados = 0;
		int totalboletosasignados = 0;
		
		
		//int boletosentregados = 0;
		int boletosxentregar = 0;
		int totalboletosretornados = 0;
		
		
		ResultSet rs = null;
		
		model.setId(idsorteo);
		model.setIdsector(idsector);
		
		model.Sorteo(model);
		
		dato = model.getSorteo();
		regex = "#SORTEO#";
		contenido = contenido.replaceAll(regex, dato);
		
		
		dato = model.getSector();
		regex = "#SECTOR#";
		contenido = contenido.replaceAll(regex, dato);
		
		
		model.Totaltalonarios(model);
		numtalonarios = model.getNumTalonarios();
		regex = "#TOTALTALONARIOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numtalonarios));
		
		model.Totalboletos(model);
		numboletos = model.getNumBoletos();
		regex = "#TOTALBOLETOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numboletos));
		
	
		model.TotaltalonariosAsignados(model);
		numtalonariosasignados = model.getNumTalonariosasignados();
		regex = "#TALONARIOSSEMBRADOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numtalonariosasignados));
		
		
		
		rs = model.Nichos(model);
		dato = "";
		try {
			while (rs.next()) {
				
				model.setIdnicho(Integer.parseInt(rs.getString("PK1")));

				//boletosentregados +=Integer.parseInt(model.TotalboletosNicho(model));
				  
				  boletosxentregar = Integer.parseInt(model.TotalboletosNicho(model)) - Integer.parseInt(model.TotalboletosNichoAsignados(model));
				  
				  totalboletosretornados += model.BoletosRetornados();
				  
				  totalboletosasignados += Integer.parseInt(model.TotalboletosNichoAsignados(model));
				  
				  dato += "<tr>";
				  dato += "<td><span style=\"display:none;\" id=\"sector"+rs.getString("PK1")+"\" onClick=\"showNichos("+rs.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span><a href=\"AsignacionNicho?sorteo="+idsorteo+"&sector="+rs.getString("PK1")+"\">"+rs.getString("NICHO")+"</a></td>";
				  dato += " <td><span class=\"price-money\">"+model.TotalboletosNicho(model)+"</span></td>";
				  dato += " <td><span class=\"price-money\">"+model.TotalboletosNichoAsignados(model)+"</span></td>";  
				  dato += "<td><span class=\"price-money\">"+boletosxentregar+"</span></td>";
				  dato += "<td><span class=\"price-money\">"+model.BoletosRetornados()+"</span></td>";
				  //dato += "<td>"+englishFormat.format(model.TotalboletosSectorVenta(model))+"</td>";  
				  dato += "</tr>";
				  
				  
				  //get nichos
				  /*rsnichos = model.Nichos(model);
				  
				  dato += "<tr><td colspan=\"6\"><div id=\"nichos"+rs.getString("PK1")+"\" style=\"display:none\"><table class=\"table table-invoice\"><thead>";
				  dato += "<tr>";
				  dato += "<th style=\"width:364px; min-width:364px; max-width:364px; \">NICHOS</th>";
				  dato += "<th style=\"width:120px;\">BOLETOS (Entregados al Nicho)</th>";
				  dato += "<th style=\"width:120px;\">BOLETOS (Asignados a Colaboradores)</th>";
				  dato += "<th style=\"width:120px;\">VENDIDOS</th>";
				  dato += "<th style=\"width:160px;\">MONTO</th>";
				  dato += "<th style=\"width:143px;\">VENTA</th>";
				  dato += "</tr>";
				  dato += "</thead><tbody>";
				  while (rsnichos.next()) {
					  
			      model.setIdnicho(Integer.parseInt(rsnichos.getString("PK1")));
				  dato += "<tr>";
				  dato += "<td><span id=\"nicho"+rsnichos.getString("PK1")+"\" onClick=\"showColaboradores("+rsnichos.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span>"+rsnichos.getString("NICHO")+"</td>";
				  dato += " <td>"+model.TotalboletosNicho(model)+"</td>";
				  dato += " <td>"+model.TotalboletosNichoAsignados(model)+"</td>";
				  dato += "<td>"+model.TotalboletosNichoVendidos(model)+"</td>";
				  dato += "<td>"+englishFormat.format(model.MontoNicho(model))+"</td>";
				  dato += "<td>"+englishFormat.format(model.TotalboletosNichoVenta(model))+"</td>";  
				  dato += "</tr>";
				  
				  
				  
				  //get colaboradores
				  rscolaboradores = model.Colaboradores(model);
				  
				  dato += "<tr><td colspan=\"6\"><div id=\"colaboradores"+rsnichos.getString("PK1")+"\" style=\"display:none\"><table class=\"table table-invoice\"><thead>";
				  dato += "<tr>";
				  dato += "<th style=\"width:349px; min-width:349px; max-width:349px;\">COLABORADORES</th>";
				  dato += "<th style=\"width:120px;\">BOLETOS</th>";
				  dato += "<th style=\"width:120px;\">ASIGNADOS</th>";
				  dato += "<th style=\"width:120px;\">VENDIDOS</th>";
				  dato += "<th style=\"width:160px;\">MONTO</th>";
				  dato += "<th style=\"width:130px;\">VENTA</th>";
				  dato += "</tr>";
				  dato += "</thead><tbody>";
				  
				  while (rscolaboradores.next()) {
					  
					  count++;
					  
					  model.setIdcolaborador(Integer.parseInt(rscolaboradores.getString("PK1")));
					  
					  dato += "<tr>";
					  dato += "<td>"+count+".- "+rscolaboradores.getString("CLAVE")+"-"+rscolaboradores.getString("NOMBRE")+"</td>";
					  dato += " <td>"+model.TotalboletosColaborador(model)+"</td>";
					  dato += " <td>"+model.TotalboletosColaborador(model)+"</td>";
					  dato += "<td>"+model.TotalboletosColaboradorVendidos(model)+"</td>";  
					  dato += "<td>"+englishFormat.format(model.MontoColaboradores(model))+"</td>";
					  dato += "<td>"+englishFormat.format(model.TotalboletosColaboradorVenta(model))+"</td>";  
					  dato += "</tr>";
					  
				  
				  }
				  
				  
				  dato += "</td></tr></tbody></table></div>";
				  
				  
				  }
				  
				  dato += "</td></tr></tbody></table></div>";
				*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		regex = "#BOLETOSSEMBRADOS#";
		contenido = contenido.replace(regex, String.valueOf(totalboletosasignados));
		
		
		regex = "#TOTALRETORNADOS#";
		contenido = contenido.replace(regex, String.valueOf(totalboletosretornados));
		
		regex = "#TABLA#";
		contenido = contenido.replace(regex, dato);
		
		return contenido;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
