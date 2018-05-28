package com.sorteo.ventas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
import com.sorteo.dashboard.model.mDashboard;
import com.sorteo.ventas.model.mVentas;

/**
 * Servlet implementation class Ventas
 */
@WebServlet("/Ventas")
public class Ventas extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public Ventas() {
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
		mVentas model = new mVentas();

		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(20092)){view = "error";}
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int idSorteo = 0;

		
		fullPath = "/WEB-INF/views/listsorteosuser.html";
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
		
         case "listsorteos":
			
			menu = vista.initMenu(0, false, 19, 12, sesion);
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
		//	Buscar(request, response, pg, show, search);
			break;
			*/

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 19, 12, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;	

		default:
			fullPath = "/WEB-INF/views/ventas/ventasdetalle.html";
			menu = vista.initMenu(0, false, 19, 12, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			idSorteo = Integer.parseInt(request.getParameter("sorteo"));
			HTML = this.Contenido(HTML,idSorteo, model);
			
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
		   
		   long sorteo = modelsorteos.getIdSorteo();
		   long sector = modelsorteos.getIdSector();
		   long nicho = modelsorteos.getIdNicho();
		 
		   
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

	
	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, String HTML, mVentas model, SesionDatos sesion) throws ServletException, IOException {
		
		   model.setIdUsuario(sesion.pkUsuario);
		   model.getUsuarioSorteo();
		  
		   long sorteo = model.getIdsorteo();
		   long sector = model.getIdsector();
		   long nicho = model.getIdnicho();
		   System.out.println(sorteo);

					if(sorteo!=0 && sector==0 && nicho==0){
						response.sendRedirect("Ventas?sorteo="+sorteo);	
					}else if(sorteo!=0  && sector!=0 && nicho==0){
						response.sendRedirect("VentasSector?sorteo="+sorteo+"&sector="+sector);
					}else if(sorteo!=0  && sector!=0 && nicho!=0){
						response.sendRedirect("VentasNicho?sorteo="+sorteo+"&sector="+sector+"&nicho="+nicho);
					}else{
						
						response.sendRedirect("Ventas?view=listsorteos");
					}
	}
	
	private String Contenido(String HTML,int idsorteo, mVentas model){
		
		String contenido = HTML;
		String regex = null;
		String dato = null;
		int numtalonarios = 0;
		int numboletos = 0;
		int numboletosvendidos = 0;
		double monto = 0;
		double resta = 0;
		double porcentaje = 0;
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		ResultSet rs = null;
		
		
		model.setId(idsorteo);
		model.Sorteo(model);
		
		dato = model.getSorteo();
		
		regex = "#SORTEO#";
		contenido = contenido.replaceAll(regex, dato);
		
		model.Totaltalonarios(model);
		numtalonarios = model.getNumTalonarios();
		regex = "#TOTALTALONARIOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numtalonarios));
		
		model.Totalboletos(model);
		numboletos = model.getNumBoletos();
		regex = "#TOTALBOLETOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numboletos));
		
		
		model.TotalboletosVendidos(model);
		numboletosvendidos = model.getNumBoletosVendidos();
		regex = "#BOLETOSVENDIDOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numboletosvendidos));
		
		
		regex = "#TALONARIOSVENDIDOS#";
		contenido = contenido.replaceAll(regex, model.getTalonariosVendidos());
				
		//vendido = (double) model.TotalboletosVenta(model);
		
		porcentaje = (monto * 100)/resta;
		regex = "#PORCENTAJE#";
		contenido = contenido.replace(regex, Double.toString(porcentaje));
		
		rs = model.Sectores(model);
		dato = "";
		
		double total_entregado = 0.0;
		double total_por_vender = 0.0;
		double total_vendido = 0.0;
		try {
			while (rs.next()) {
				
				model.setIdsector(Integer.parseInt(rs.getString("PK1")));
				
				int    boletosEntregados_numero = model.TotalboletosSector(model);
				double boletosEntregados_monto  = model.MontoSector(model);
				int    boletosVendidos_numero   = model.TotalboletosSectorVendidos(model);
				double boletosVendidos_monto    = model.TotalboletosSectorVenta(model);
				int    boletosXVender_numero    = boletosEntregados_numero - boletosVendidos_numero;
				double boletosXVender_monto     = boletosEntregados_monto - boletosVendidos_monto; 
				
				
				DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
			    simbolo.setDecimalSeparator('.');
			    simbolo.setGroupingSeparator(',');
				DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
				
				
				dato += "<tr>";
				dato += "<td><span style=\"display:none;\" id=\"sector"+rs.getString("PK1")+"\" onClick=\"showNichos("+rs.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span><a href=\"VentasSector?sorteo="+idsorteo+"&sector="+rs.getString("PK1")+"\">"+rs.getString("SECTOR")+"</a></td>";
				
				// Boletos entregados
				dato += "<td bgcolor=\"#F4FAFD\" align=\"center\"><span class=\"price-money\">"+formateador.format(boletosEntregados_numero)+"</span></td>";
				//dato += "<td bgcolor=\"#BADCF2\" style=\"border-right:5px solid #fff;\"><span class=\"price-money\">"+englishFormat.format(boletosEntregados_monto)+"</span></td>";
				
				
				// Boletos por vender
				dato += "<td bgcolor=\"#FEF3E2\" align=\"center\"><span class=\"price-money\">"+formateador.format(boletosXVender_numero)+"</span></td>";
				//dato += "<td bgcolor=\"#FBD295\" style=\"border-right:5px solid #fff;\"><span class=\"price-money\">"+englishFormat.format(boletosXVender_monto)+"</span></td>";
				
				// Boletos vendidos  
				dato += "<td bgcolor=\"#E9FCED\" align=\"center\"><span class=\"price-money\">"+formateador.format(boletosVendidos_numero)+"</span></td>";
				//dato += "<td bgcolor=\"#7AE993\" ><span class=\"price-money\">"+englishFormat.format(boletosVendidos_monto)+"</span></td>";

				dato += "</tr>";

				total_entregado += boletosEntregados_monto;
				total_por_vender += boletosXVender_monto;
				total_vendido += boletosVendidos_monto;
			}
			
			
			contenido = contenido.replace("#MONTO_BOLETOS_ENTREGADOS#", englishFormat.format(total_entregado));
			
			contenido = contenido.replace("#MONTO_RESTANTE#", englishFormat.format(total_por_vender));
			
			contenido = contenido.replace("#MONTO_VENTA#", englishFormat.format(total_vendido));
			
			
		} catch (SQLException e) { e.printStackTrace(); }
		
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
