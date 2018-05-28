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
import com.sorteo.ventas.model.mVentasSector;

/**
 * Servlet implementation class VentasSector
 */
@WebServlet("/VentasSector")
public class VentasSector extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VentasSector() {
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
		mVentasSector model = new mVentasSector();

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

		case "Buscar":
			/*
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}

			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
			Buscar(request, response, pg, show, search);
			*/
			break;

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
	
	
	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, String HTML, mVentasSector model, SesionDatos sesion) throws ServletException, IOException {
		
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
	
	
private String Contenido(String HTML,int idsorteo, int idsector, mVentasSector model){
		
		String contenido = HTML;
		String regex = null;
		String dato = null;
		int numtalonarios = 0;
		int numboletos = 0;
		//int numtalonariosasignados = 0;
		int numboletosasignados = 0;
		int numboletosvendidos = 0;
		//int count = 0;
		//double comision = 0;
		//double monto = 0;
		//double vendido = 0;
		//double resta = 0;
		//double porcentaje = 0;
		//double totalapagar = 0;
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		ResultSet rs = null;
		//ResultSet rsnichos = null;
		//ResultSet rscolaboradores = null;
		
		
		model.setId(idsorteo);
		model.setIdsector(idsector);
		
		model.Sorteo(model);
		regex = "#SORTEO#";
		contenido = contenido.replaceAll(regex, model.getSorteo());
		
		model.consultaSector(model);
		regex = "#SECTOR#";
		contenido = contenido.replaceAll(regex, model.getSector());
		
		// Panel superior
		
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
		
		model.TotalboletosAsignados(model);
		numboletosasignados = model.getNumBoletosasignados();
		regex = "#BOLETOSSEMBRADOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numboletosasignados));
		
		//double totalcomision = 0;
		//double totalSector = 0;
		rs = model.Sectores(model);
		dato = "";
		/*
		try {
			if (rs.next()) {
				  
				model.setIdsector(Integer.parseInt(rs.getString("PK1")));
                
                double ventasMenosComisionDeColaborador = model.TotalVentaXSectorMenosComision();
                double porcentajeDeComision = rs.getDouble("COMISION");
                double comisionDeSector = ventasMenosComisionDeColaborador * porcentajeDeComision / 100.0;
                
                
                totalapagar = ventasMenosComisionDeColaborador - comisionDeSector;

                dato += "<tr>";
					dato += "<td><span style=\"display:none;\" id=\"sector"+rs.getString("PK1")+"\" onClick=\"showNichos("+rs.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span><a href=\"VentasNichos?sorteo="+idsorteo+"&sector="+rs.getString("PK1")+"\">"+rs.getString("SECTOR")+"</a></td>";
					
					dato += "<td><span class=\"price-money\">"+model.TotalboletosSector(model)+"</span></td>";
					dato += "<td><span class=\"price-money\">"+englishFormat.format(model.MontoSector(model))+"</span></td>";
					dato += "<td><span class=\"price-money\">"+englishFormat.format(model.TotalboletosSectorVenta(model))+"</span></td>";
					dato += "<td><span class=\"price-money\">"+englishFormat.format(ventasMenosComisionDeColaborador)+"</span></td>";
					dato += "<td><span class=\"price-money\">"+englishFormat.format(comisionDeSector)+" <small>("+porcentajeDeComision+"%)</small></span></td>";
					dato += "<td><span class=\"price-money\">"+englishFormat.format(totalapagar)+"</span></td>";
				dato += "</tr>";

				// ------ totales
				vendido += ventasMenosComisionDeColaborador;
                totalcomision += comisionDeSector;
                totalSector += totalapagar;
                
                
                model.setComision(porcentajeDeComision);
			}
			rs.close();
		
			regex = "#VENTASINGRESOS#";
			contenido = contenido.replace(regex, englishFormat.format(vendido));
			
	        regex = "#PORCENTAJECOMISION#";
			contenido = contenido.replace(regex, String.valueOf(model.getComision()));
			
			regex = "#COMISIONSECTOR#";
			contenido = contenido.replace(regex, englishFormat.format(totalcomision));

			regex = "#VENTATOTAL#";
			contenido = contenido.replace(regex, englishFormat.format(totalSector));


		} catch (SQLException e) { e.printStackTrace(); }
		//*/
		
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
				
				dato += "<tr>";
				dato += "<td><span style=\"display:none;\" id=\"sector"+rs.getString("PK1")+"\" onClick=\"showNichos("+rs.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span><a href=\"VentasNichos?sorteo="+idsorteo+"&sector="+rs.getString("PK1")+"\">"+rs.getString("SECTOR")+"</a></td>";
				
				// Boletos entregados
				dato += "<td bgcolor=\"#F4FAFD\" align=\"center\"><span class=\"price-money\">"+boletosEntregados_numero+"</span></td>";
				//dato += "<td><span class=\"price-money\">"+englishFormat.format(boletosEntregados_monto)+"</span></td>";
				
				// Boletos por vender
				dato += "<td bgcolor=\"#FEF3E2\" align=\"center\"><span class=\"price-money\">"+boletosXVender_numero+"</span></td>";
				//dato += "<td><span class=\"price-money\">"+englishFormat.format(boletosXVender_monto)+"</span></td>";
				
				// Boletos vendidos
				dato += "<td bgcolor=\"#E9FCED\" align=\"center\"><span class=\"price-money\">"+boletosVendidos_numero+"</span></td>";
				//dato += "<td><span class=\"price-money\">"+englishFormat.format(boletosVendidos_monto)+"</span></td>";

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
