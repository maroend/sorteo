package com.sorteo.sorteos.controller;

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
import com.sorteo.sorteos.model.mSorteoDetalle;

/**
 * Servlet implementation class SorteoDetalle
 */
@WebServlet("/SorteoDetalle")
public class SorteoDetalle extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SorteoDetalle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Factory vista = new Factory();
		mSorteoDetalle model = new mSorteoDetalle();
		SesionDatos sesion;
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(10096)){view = "error";}
		
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int idSorteo = 0;

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
			fullPath = "/WEB-INF/views/sorteos/Detalle/SorteoDetalle.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			idSorteo = Integer.parseInt(request.getParameter("sorteo"));
			HTML = this.Contenido(HTML, idSorteo, model);
			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
		
		model.close();
	}
	
	
	
	
	private String Contenido(String HTML,int idsorteo, mSorteoDetalle model){
		
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
		
		
		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
	    simbolo.setDecimalSeparator('.');
	    simbolo.setGroupingSeparator(',');
		DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
		
		
		ResultSet rs = null;
		ResultSet rsnichos = null;
		//ResultSet rscolaboradores = null;
		
		
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
		
		rs = model.Sectores(model);
		dato = "";
		try {
			while (rs.next()) {
				
				  model.setIdsector(Integer.parseInt(rs.getString("PK1")));
				  dato += "<tr>";
				  dato += "<td><span id=\"sector"+rs.getString("PK1")+"\" onClick=\"showNichos("+rs.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span>"+rs.getString("SECTOR")+"</td>";
				  dato += " <td>"+formateador.format(Integer.valueOf((String)model.TotalboletosSector(model)))+"</td>";
				  dato += " <td>"+formateador.format(Integer.valueOf((String)model.TotalboletosSectorAsignados(model)))+"</td>";  
				  dato += "<td>"+formateador.format(Integer.valueOf((String)model.TotalboletosSectorVendidos(model)))+"</td>";
				  //dato += "<td>"+englishFormat.format((String)model.TotalboletosSectorMonto(model))+"</td>";
				  dato += "<td>"+englishFormat.format(model.TotalboletosSectorVenta(model))+"</td>";  
				  dato += "</tr>";
				  
				  
				  //get nichos
				  rsnichos = model.Nichos(model);
				  
				  dato += "<tr><td colspan=\"6\"><div id=\"nichos"+rs.getString("PK1")+"\" style=\"display:none\"><table class=\"table table-invoice\"><thead>";
				  dato += "<tr>";
				  dato += "<th style=\"width:364px; min-width:364px; max-width:364px; \">NICHOS</th>";
				  dato += "<th style=\"width:120px;\">BOLETOS</th>";
				  dato += "<th style=\"width:120px;\">ASIGNADOS</th>";
				  dato += "<th style=\"width:120px;\">VENDIDOS</th>";
				 // dato += "<th style=\"width:160px;\">MONTO</th>";
				  dato += "<th style=\"width:143px;\">VENTA</th>";
				  dato += "</tr>";
				  dato += "</thead><tbody>";
				  while (rsnichos.next()) {
					  
			      model.setIdnicho(Integer.parseInt(rsnichos.getString("PK1")));
				  dato += "<tr>";
				  //<span id=\"nicho"+rsnichos.getString("PK1")+"\" onClick=\"showColaboradores("+rsnichos.getString("PK1")+")\"><i class=\"fa fa-lg fa-plus-square\"></i></span>
				  dato += "<td>"+rsnichos.getString("NICHO")+"</td>";
				  dato += " <td>"+model.TotalboletosNicho(model)+"</td>";
				  dato += " <td>"+model.TotalboletosNichoAsignados(model)+"</td>";
				  dato += "<td>"+model.TotalboletosNichoVendidos(model)+"</td>";
				  //dato += "<td>$0.00</td>";
				  dato += "<td>"+englishFormat.format(model.TotalboletosNichoVenta(model))+"</td>";  
				  dato += "</tr>";
				  
				  
				  //get colaboradores
				 // rscolaboradores = model.Colaboradores(model);
				  
				  dato += "<tr><td colspan=\"6\"><div id=\"colaboradores"+rsnichos.getString("PK1")+"\" style=\"display:none\"><table class=\"table table-invoice\" style=\"display:none\"><thead>";
				  dato += "<tr>";
				  dato += "<th style=\"width:349px; min-width:349px; max-width:349px;\">COLABORADORES</th>";
				  dato += "<th style=\"width:120px;\">BOLETOS</th>";
				  dato += "<th style=\"width:120px;\">ASIGNADOS</th>";
				  dato += "<th style=\"width:120px;\">VENDIDOS</th>";
				  //dato += "<th style=\"width:160px;\">MONTO</th>";
				  dato += "<th style=\"width:130px;\">VENTA</th>";
				  dato += "</tr>";
				  
				  dato += "</thead><tbody>";
				  
				 /* while (rscolaboradores.next()) {
					  
					  model.setIdcolaborador(Integer.parseInt(rscolaboradores.getString("PK1")));
					  
					  dato += "<tr>";
					  dato += "<td>"+rscolaboradores.getString("NOMBRE")+"</td>";
					  dato += " <td>"+model.TotalboletosColaborador(model)+"</td>";
					  dato += " <td>"+model.TotalboletosColaborador(model)+"</td>";
					  dato += "<td>"+model.TotalboletosColaboradorVendidos(model)+"</td>";  
					  dato += "<td>$0.00</td>";
					  dato += "<td>"+englishFormat.format(model.TotalboletosColaboradorVenta(model))+"</td>";  
					  dato += "</tr>";
					  
				  
				  }*/
				  
				  
				  dato += "</td></tr></tbody></table></div>";
				  
				  
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
