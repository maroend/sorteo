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
import com.core.SesionDatos;
import com.sorteo.sorteos.model.mSectorDetalle;

/**
 * Servlet implementation class SectorDetalle
 */
@WebServlet("/SectorDetalle")
public class SectorDetalle extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SectorDetalle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Factory vista = new Factory();
		mSectorDetalle model = new mSectorDetalle();
		SesionDatos sesion;
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		//10109 ACCESO A LA LISTA DE NICHOS		
		 if (!sesion.permisos.havePermiso(10110)){view = "error";}
		
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String url = "";
		int idSorteo = 0;
		int idsector = 0;

		if (view == null) {
			view = "";
		}
		
		switch (view) {

		case "Agregar":
			
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
		//	Buscar(request, response, pg, show, search);
			//*/
			break;

		case "delete":
			// eliminarUsuario(request, response);
			break;
			
			
		 case "error":
			 
				fullPath = "/WEB-INF/views/error.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context,
						sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
						infouser);			
				break;		
		 	

		default:
			
			int idsorteo = Integer.valueOf(request.getParameter("sorteo"));
			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
				System.out.println("<SORTEO guardado>");
			}
			
			
			fullPath = "/WEB-INF/views/sorteos/Detalle/SectorDetalle.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			idSorteo = Integer.parseInt(request.getParameter("sorteo"));
			idsector = Integer.parseInt(request.getParameter("sector"));
			
			HTML = this.Contenido(HTML,idSorteo,idsector, model);
			
			
			String regex = "#URLSECTORES#";
		    url = "SectoresAsignados?idsorteo="+sesion.pkSorteo;
			HTML = HTML.replaceAll(regex, url);
			
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	
	private String Contenido(String HTML,int idsorteo,int idsector, mSectorDetalle model){
		
		String contenido = HTML;
		String regex = null;
		String dato = null;
		int numtalonarios = 0;
		int numboletos = 0;
		int numtalonariosasignados = 0;
		int numboletosasignados = 0;
		int numboletosvendidos = 0;
		double monto = 0;
		double vendido = 0;
		double resta = 0;
		double porcentaje = 0;
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		ResultSet rs = null;
		ResultSet rsnichos = null;
		ResultSet rscolaboradores = null;
		
		
		model.setId(idsorteo);
		model.Sorteo(model);
		
		dato = model.getSorteo();
		regex = "#SORTEO#";
		contenido = contenido.replaceAll(regex, dato);
		
		model.setIdsector(idsector);
		dato = model.Sector(model);
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
		
		
		model.TotalboletosVendidos(model);
		numboletosvendidos = model.getNumBoletosVendidos();
		regex = "#BOLETOSVENDIDOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numboletosvendidos));
		
		
		model.TotaltalonariosAsignados(model);
		numtalonariosasignados = model.getNumTalonariosasignados();
		regex = "#TALONARIOSSEMBRADOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numtalonariosasignados));
		
		model.TotalboletosAsignados(model);
		numboletosasignados = model.getNumBoletosasignados();
		regex = "#BOLETOSSEMBRADOS#";
		contenido = contenido.replaceAll(regex, Integer.toString(numboletosasignados));
		
		monto = (double) model.montoSorteo(model);
		regex = "#MONTOTOTAL#";
		contenido = contenido.replace(regex, englishFormat.format(monto));
		
		vendido = (double) model.TotalboletosVenta(model);
		regex = "#VENTATOTAL#";
		contenido = contenido.replace(regex, englishFormat.format(vendido));
		
		resta = monto - vendido;
		regex = "#RESTA#";
		contenido = contenido.replace(regex, englishFormat.format(resta));
		
		
		porcentaje = (monto * 100)/resta;
		regex = "#PORCENTAJE#";
		contenido = contenido.replace(regex, Double.toString(porcentaje));
		
		rsnichos = model.Nichos(model);
		dato = "";
		try {
			while (rsnichos.next()) {
				
				
				model.setIdnicho(Integer.parseInt(rsnichos.getString("PK1")));
				  dato += "<tr>";
				  dato += "<td><span id=\"nicho"+rsnichos.getString("PK1")+"\" onClick=\"showColaboradores("+rsnichos.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span>"+rsnichos.getString("NICHO")+"</td>";
				  dato += " <td>"+model.TotalboletosNicho(model)+"</td>";
				  dato += " <td>"+model.TotalboletosNichoAsignados(model)+"</td>";
				  dato += "<td>"+model.TotalboletosNichoVendidos(model)+"</td>";
				  dato += "<td>$0.00</td>";
				  dato += "<td>"+englishFormat.format(model.TotalboletosNichoVenta(model))+"</td>";  
				  dato += "</tr>";
				  
				  
				//get colaboradores
				  rscolaboradores = model.Colaboradores(model);
				  
				  dato += "<tr><td colspan=\"6\"><div id=\"colaboradores"+rsnichos.getString("PK1")+"\" style=\"display:none\"><table class=\"table table-invoice\"><thead>";
				  dato += "<tr>";
				  dato += "<th style=\"width:364px; min-width:364px; max-width:364px;\">COLABORADORES</th>";
				  dato += "<th style=\"width:120px;\">BOLETOS</th>";
				  dato += "<th style=\"width:120px;\">ASIGNADOS</th>";
				  dato += "<th style=\"width:120px;\">VENDIDOS</th>";
				  dato += "<th style=\"width:160px;\">MONTO</th>";
				  dato += "<th style=\"width:143px;\">VENTA</th>";
				  dato += "</tr>";
				  dato += "</thead><tbody>";
				  
				  
				  while (rscolaboradores.next()) {
					  
					  model.setIdcolaborador(Integer.parseInt(rscolaboradores.getString("PK1")));
					  
					  dato += "<tr>";
					  dato += "<td>"+rscolaboradores.getString("NOMBRE")+"</td>";
					  dato += " <td>"+model.TotalboletosColaborador(model)+"</td>";
					  dato += " <td>"+model.TotalboletosColaborador(model)+"</td>";
					  dato += "<td>"+model.TotalboletosColaboradorVendidos(model)+"</td>";  
					  dato += "<td>$0.00</td>";
					  dato += "<td>"+englishFormat.format(model.TotalboletosColaboradorVenta(model))+"</td>";  
					  dato += "</tr>";
					  
				  
				  }
				  
				  
				  dato += "</td></tr></tbody></table></div>";
				  
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Factory.Error(e, "rs="+rs);
		}
		
		
		regex = "#TABLA#";
		contenido = contenido.replace(regex, dato);
		
		return contenido;
		
	}

}
