package com.sorteo.ventas.controller;

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
import com.sorteo.ventas.model.mVentasComisionNicho;

/**
 * Servlet implementation class Ventas
 */
@WebServlet("/VentasComisionNicho")
public class VentasComisionNicho extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public VentasComisionNicho() {
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
		mVentasComisionNicho model = new mVentasComisionNicho();

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
		int idSector = 0;
		int idNicho = 0;

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

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 19, 14, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		default:
			fullPath = "/WEB-INF/views/ventas/ventascomisiondetalle.html";
			menu = vista.initMenu(0, false, 19, 14, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			idSorteo = Integer.parseInt(request.getParameter("sorteo"));
			idSector = Integer.parseInt(request.getParameter("sector"));
			idNicho  = Integer.parseInt(request.getParameter("nicho"));
			
			HTML = this.Contenido(HTML, idSorteo, idSector, idNicho, model);
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
		
		model.close();
		
	}

	
	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, String HTML, mVentasComisionNicho model, SesionDatos sesion) throws ServletException, IOException {
		
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
					}
	}
	
	private String Contenido(String HTML, int idsorteo, int idsector, int idnicho, mVentasComisionNicho model){
		
		String contenido = HTML;
		String dato = null;
		
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		ResultSet rs = null;
		
		
		model.setIdsorteo(idsorteo);
		model.setIdsector(idsector);
		model.setIdnicho(idnicho);

		model.Sorteo(model);
		model.consultaSector(model);
		model.consultaNicho(model);
		
		contenido = contenido.replaceAll("#SORTEO#", model.getSorteo() + "<br/>");
		contenido = contenido.replaceAll("#SECTOR#", model.getSector() + "<br/>");
		contenido = contenido.replaceAll("#NICHO#", model.getNicho());

		
		model.consultaTalonarios(model);
		contenido = contenido.replaceAll("#TOTALTALONARIOS#", Integer.toString(model.getNumTalonarios()));
		
		model.Totalboletos(model);
		contenido = contenido.replaceAll("#TOTALBOLETOS#", Integer.toString(model.getNumBoletos()));

		model.TotalboletosVendidos(model);
		contenido = contenido.replaceAll("#BOLETOSVENDIDOS#", Integer.toString(model.getNumBoletosVendidos()));
		
		model.consultaTalonariosVendidos();
		contenido = contenido.replaceAll("#TALONARIOSVENDIDOS#", Integer.toString(model.getNumTalonariosVendidos()));
		
		contenido = contenido.replaceAll("#HEADER_POBLACION#", "NICHOS");
		contenido = contenido.replaceAll("#HEADER_COMISION#", "");
		
		rs = model.Nicho(model);
		dato = "";
		
		
		double totalVentasBrutas = 0.0;
		double totalComisiones = 0.0;
		double totalVentasNetas = 0.0;
		try {
			while (rs.next()) {
				
				model.setIdnicho(rs.getInt("PK1"));
				
				double ventasBrutas        = model.ventaBruta(model);
				double comisionColaborador = model.SumaComisionesDeColaboradoresXNicho();
				double ventasNetas         = model.TotalVentaXNichoMenosComision();
				//double comisionSector_porc = rs.getDouble("COMISION");
				//double comisionSector      = comisionSector_porc * ventasNetas / 100.0;
				
				dato += "<tr>";
				dato += "<td><span style=\"display:none;\" id=\"sector"+rs.getString("PK1")+"\" ><i class=\"fa fa-lg fa-plus-square\"></i></span><a href=\"javascript:;\">"+rs.getString("NICHO")+"</a></td>";
				
				dato += "<td bgcolor=\"#EEEEEE\"><span class=\"price-money\">"+englishFormat.format(ventasBrutas)+"</span></td>";
				dato += "<td bgcolor=\"#EEEEEE\"><span class=\"price-money\">"+englishFormat.format(comisionColaborador)+"</span></td>";
				dato += "<td bgcolor=\"#EEEEEE\"><span class=\"price-money\">"+englishFormat.format(ventasNetas)+"</span></td>";
				//dato += "<td bgcolor=\"#FFFFFF\"><span class=\"price-money\" style=\"color:#66AAAA;\">"+englishFormat.format(comisionSector)+"<small style=\"color:#888888\"> ("+Double.toString(comisionSector_porc)+"%)</small></span></td>";

				dato += "</tr>";

				totalVentasBrutas += ventasBrutas;
				totalComisiones += comisionColaborador;
				totalVentasNetas += ventasNetas;
			}
			
			
			contenido = contenido.replace("#TOTAL_VENTAS_BRUTAS#", englishFormat.format(totalVentasBrutas));
			
			contenido = contenido.replace("#TOTAL_COMISIONES#", englishFormat.format(totalComisiones));
			
			contenido = contenido.replace("#TOTAL_VENTAS_NETAS#", englishFormat.format(totalVentasNetas));
			
			
		} catch (SQLException e) { e.printStackTrace(); }


		contenido = contenido.replace("#TABLA#", dato);
		/*
		rs = model.Nicho(model);
		dato = "";
		
		double total_entregado = 0.0;
		double total_por_vender = 0.0;
		double total_vendido = 0.0;
		try {
			if (rs.next()) {
				
				model.setIdnicho(rs.getLong("PK1"));
				
				int    boletosEntregados_numero = model.TotalboletosNicho(model);
				double boletosEntregados_monto  = model.MontoNicho(model);
				int    boletosVendidos_numero   = model.TotalBoletosVendidosNicho(model);
				double boletosVendidos_monto    = model.TotalMontoVendidoXNicho(model);
				int    boletosXVender_numero    = boletosEntregados_numero - boletosVendidos_numero;
				double boletosXVender_monto     = boletosEntregados_monto - boletosVendidos_monto; 
				
				dato += "<tr>";
				dato += "<td><span style=\"display:none;\" id=\"sector"+rs.getString("PK1")+"\"><i class=\"fa fa-lg fa-plus-square\"></i></span><a href=\"javascript:;\">"+rs.getString("NICHO")+"</a></td>";
				
				// Boletos entregados
				dato += "<td><span class=\"price-money\">"+boletosEntregados_numero+"</span></td>";
				dato += "<td><span class=\"price-money\">"+englishFormat.format(boletosEntregados_monto)+"</span></td>";
				
				// Boletos por vender
				dato += "<td><span class=\"price-money\">"+boletosXVender_numero+"</span></td>";
				dato += "<td><span class=\"price-money\">"+englishFormat.format(boletosXVender_monto)+"</span></td>";
				
				// Boletos vendidos
				dato += "<td><span class=\"price-money\">"+boletosVendidos_numero+"</span></td>";
				dato += "<td><span class=\"price-money\">"+englishFormat.format(boletosVendidos_monto)+"</span></td>";

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
		*/
		return contenido;
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
