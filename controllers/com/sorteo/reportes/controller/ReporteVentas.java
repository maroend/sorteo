package com.sorteo.reportes.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.poblacion.model.mColaboradores;
import com.sorteo.reportes.model.mReporteVentas;


/**
 * Servlet implementation class ReporteVentas
 */
@WebServlet("/ReporteVentas")
public class ReporteVentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteVentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		mReporteVentas model = new mReporteVentas();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		String fullPath ="";	
		
		String view = request.getParameter("view");
		
		if ( (!sesion.permisos.havePermiso(10181)) && (!sesion.sorteoActivo)){view = "errorCerrado";}
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		
		case "ExportExcel":		
		
			try {				
				export(request, response, model, sesion);	
				
				//HttpSession sesion2 = request.getSession();
				//sesion2.setAttribute("nombre_variable", "edgar"); 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		//	HTML = HTML.replaceAll("", "");	
			
			
			//break;
			return;	
			
		case "ExportExcel_RetornoTal":		
			
			try {				
				export_retornoTal(request, response, model, sesion);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		
			return;		
		
	
		default:
			
			fullPath = "/WEB-INF/views/reportes/ReporteVenta.html";
			menu = vista.initMenu(0, false, 19, 12, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			//System.out.println("id sorteo: "+sesion.pkSorteo);
			//System.out.println("id usuario: "+sesion.pkUsuario);			
		
			
		//	HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", sesion.sorteoActivo ? "display" : "none");
			
			HTML = putCode(HTML, 10181, "#BTN_VENTA_BOLETOS#",
					""  /*"<p><a href=\"RegistroVenta\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\"  disabled=\"disabled\"> " +
					"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro venta de boletos</a></p>" */,
					"<p><a href=\"Ventas\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\"> " +
					"<i class=\"fa fa-2x fa-tags\"></i>&nbsp;&nbsp;Ventas</a></p>",sesion);
			
			HTML = putCode(HTML, 10181, "#BTN_REPORTE_VENTA#",
					""  /*"<p><a href=\"RegistroComprador\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\" disabled=\"disabled\">" +
					"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro de compradores</a></p>"*/,
					"<p><a href=\"javascript:void(0)\"   onclick=\"ExportExcel();\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\">" +
					"<i class=\"fa fa-2x fa-check\"></i>&nbsp;&nbsp;Reporte Seguimiento FC4 App &nbsp;&nbsp;&nbsp;&nbsp;</a></p>",sesion);
			
			
			HTML = putCode(HTML, 10181, "#BTN_REPORTE_RETORNO_TALONARIOS#",
					""  /*"<p><a href=\"RegistroComprador\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\" disabled=\"disabled\">" +
					"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro de compradores</a></p>"*/,
					"<p><a href=\"javascript:void(0)\"   onclick=\"ExportExcel_RetornoTal();\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\">" +
					"<i class=\"fa fa-2x fa-check\"></i>&nbsp;&nbsp;Reporte Retorno de Talonarios&nbsp;&nbsp;&nbsp;&nbsp;</a></p>",sesion);
			
		//	System.out.println("permiso 30114:"+sesion.permisos.havePermiso(30114));		
        	
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		out.close();
		//model.close();	
		
		
		
	}
	
	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, mReporteVentas model,
		 SesionDatos sesion)
			throws ServletException, IOException, SQLException {
		
	//	model.setIdsorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Reporte_Seguimiento_FC4_App.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("REPORTE_Seguimiento_FC4_App");
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		
		cell= row.createCell(0);
		cell.setCellValue("ASIGNS");
		cell = row.createCell(1);
		cell.setCellValue("SECTOR");
		cell = row.createCell(2);
		cell.setCellValue("NICHO");
		cell = row.createCell(3);
		cell.setCellValue("CLAVE");
		cell = row.createCell(4);
		cell.setCellValue("NOMBRE");
		cell = row.createCell(5);
		cell.setCellValue("REFERENCIA");
		cell = row.createCell(6);
		cell.setCellValue("TALONARIOS");
		cell = row.createCell(7);
		cell.setCellValue("BOLETOS");
		cell = row.createCell(8);
		cell.setCellValue("Compradores");
		
		cell = row.createCell(9);
		cell.setCellValue("MONTO");
		cell = row.createCell(10);
		cell.setCellValue("ABONO");
		
		cell = row.createCell(11);
		cell.setCellValue("FC4");
		/*
		cell = row.createCell(10);
		cell.setCellValue("Todos compradores capturados");
		cell = row.createCell(11);
		cell.setCellValue("Venta completa");
*/
			
		
	//	System.out.println("antes sql controller");
		
		
		ResultSet rs = model.ventas(sesion);		
	
		
		int fila = 1;
		
		while (rs.next()) {
			row = sheet.createRow(fila);
			//System.out.println("entro excel");
			
			//No
				//cell = row.createCell(0);
				//cell.setCellValue(fila);
			//ASIGNS
			cell = row.createCell(0);
			cell.setCellValue(rs.getString("ASIGNS"));
			
			//SECTOR
			cell = row.createCell(1);
			cell.setCellValue(rs.getString("SECTOR"));			
			
			//NICHO
			cell = row.createCell(2);
			cell.setCellValue(rs.getString("NICHO"));
			
			//CLAVE
			cell = row.createCell(3);
			cell.setCellValue(rs.getString("CLAVE"));
			
			//NOMBRE
			cell = row.createCell(4);
			cell.setCellValue(rs.getString("NOMBRE"));
			
			//REFERENCIA
			cell = row.createCell(5);
			cell.setCellValue(rs.getString("REFERENCIA"));
			
			//TALONARIOS
			cell = row.createCell(6);
			cell.setCellValue(rs.getString("TALONARIOS"));
			
			//BOLETOS
			cell = row.createCell(7);
			cell.setCellValue(rs.getString("BOLETOS"));		
			
			//Compradores
			cell = row.createCell(8);
			cell.setCellValue(rs.getString("Compradores"));	
			//System.out.println("Compradores\n"+rs.getString("Compradores"));			
			
			//MONTO
			cell = row.createCell(9);
			cell.setCellValue(rs.getString("MONTO"));
			
			//ABONO
			cell = row.createCell(10);
			cell.setCellValue(rs.getString("ABONO"));
			
			//FC4
			cell = row.createCell(11);
			cell.setCellValue(rs.getString("FC4"));
			
			/*
			//Todos compradores capturados
			cell = row.createCell(10);
			cell.setCellValue(rs.getString("Todos compradores capturados"));			
			
			
			//venta completa
			cell = row.createCell(11);
			cell.setCellValue(rs.getString("Venta completa"));					
			*/
			
		
			fila++;
		}
		
		wb.close();
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);	
	

		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = response.getOutputStream();
	    outStream.write(outArray);
		outStream.flush();
		
	//	wb.close();
		//outStream
		////FacesContext.getCurrentInstance().responseComplete();
		
		
		
	}
	
	protected void export_retornoTal(HttpServletRequest request,
			HttpServletResponse response, mReporteVentas model,
		 SesionDatos sesion)
			throws ServletException, IOException, SQLException {
		
	//	model.setIdsorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Reporte_Retorno_Talonarios.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("REPORTE RETORNO TALONARIOS");
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("PK_TALONARIO");
		cell = row.createCell(1);
		cell.setCellValue("VENDIDO");
		cell = row.createCell(2);
		cell.setCellValue("ABONO");
		cell = row.createCell(3);
		cell.setCellValue("MONTO");
		cell = row.createCell(4);
		cell.setCellValue("FOLIO");
		cell = row.createCell(5);
		cell.setCellValue("FORMATO");
		cell = row.createCell(6);
		cell.setCellValue("BRETORNADOS");
		cell = row.createCell(7);
		cell.setCellValue("PK_SECTOR");
		cell = row.createCell(8);
		cell.setCellValue("SECTOR");
		cell = row.createCell(9);
		cell.setCellValue("PK_NICHO");
		cell = row.createCell(10);
		cell.setCellValue("NICHO");
		cell = row.createCell(11);
		cell.setCellValue("PK_COLABORADOR");
		cell = row.createCell(12);	
		cell.setCellValue("COLABORADOR");		
		
	//	System.out.println("antes sql controller");		
		
		ResultSet rs = model.export_retornoTal(sesion);			
		
		int fila = 1;
		
		while (rs.next()) {
			row = sheet.createRow(fila);
			//System.out.println("entro excel");			
			
			//PK_TALONARIO
			cell = row.createCell(0);
			cell.setCellValue(rs.getString("PK_TALONARIO"));
			
			//VENDIDO
			cell = row.createCell(1);
			cell.setCellValue(rs.getString("VENDIDO"));			
			
			//ABONO
			cell = row.createCell(2);
			cell.setCellValue(rs.getString("ABONO"));
			
			//MONTO
			cell = row.createCell(3);
			cell.setCellValue(rs.getString("MONTO"));
			
			//FOLIO
			cell = row.createCell(4);
			cell.setCellValue(rs.getString("FOLIO"));
			
			//FORMATO
			cell = row.createCell(5);
			cell.setCellValue(rs.getString("FORMATO"));
			
			//BRETORNADOS
			cell = row.createCell(6);
			cell.setCellValue(rs.getString("BRETORNADOS"));
			
			//PK_SECTOR
			cell = row.createCell(7);
			cell.setCellValue(rs.getString("PK_SECTOR"));		
			
			//SECTOR
			cell = row.createCell(8);
			cell.setCellValue(rs.getString("SECTOR"));	
			//System.out.println("Compradores\n"+rs.getString("Compradores"));			
			
			
			//PK_NICHO
			cell = row.createCell(9);
			cell.setCellValue(rs.getString("PK_NICHO"));		
			
			
			//NICHO
			cell = row.createCell(10);
			cell.setCellValue(rs.getString("NICHO"));			
			
			
			//PK_COLABORADOR
			cell = row.createCell(11);
			cell.setCellValue(rs.getString("PK_COLABORADOR"));	
			
			//COLABORADOR
			cell = row.createCell(12);
			cell.setCellValue(rs.getString("COLABORADOR"));				
		
			fila++;
		}
		
		wb.close();
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);	
	

		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = response.getOutputStream();
	    outStream.write(outArray);
		outStream.flush();		
		
		
	}

	
	
	private String putCode(String HTML, int pkPermiso, String mark, String if_not_have, String if_have, SesionDatos sesion)
	{
		String str_put;
		if (sesion.permisos.havePermiso(pkPermiso))
			str_put = if_have;
		else
			str_put = if_not_have;
		return HTML.replaceAll(mark, str_put);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
