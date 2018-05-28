package com.sorteo.reportes.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.herramientas.controller.ProgressBarCalc;
import com.sorteo.reportes.model.mSeguimientoColaboradores;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/SeguimientoColaboradores.do")
public class SeguimientoColaboradores extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeguimientoColaboradores() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mSeguimientoColaboradores model = new mSeguimientoColaboradores();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		int sector = 0;
		int nicho = 0;
		
		//                       Permiso para editar Colaboradores en menu "Poblacion"
		//boolean editarColaboradores = sesion.permisos.havePermiso(30119) && sesion.sorteoActivo;
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		case "ExportExcel":
			
			sector = 0;
			if(request.getParameter("idsectorb")!=null&&request.getParameter("idsectorb")!=""){
				sector = Integer.valueOf(request.getParameter("idsectorb"));
			}
			
			nicho = 0;
			if(request.getParameter("idnichob")!=null&&request.getParameter("idnichob")!="")
				nicho = Integer.valueOf(request.getParameter("idnichob"));
			
			try {
				export(request, response, sector, nicho, model, sesion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			model.close();
			
			//break;
			return;
			
		default:
			sesion.idMenu=19;
			sesion.idSubMenu=1037;
			//sesion.data1 = 0;
			//sesion.guardaSesion();
			
			String fullPath = "/WEB-INF/views/reportes/SeguimientoColaboradores.html";
			menu = vista.initMenu(0, false, sesion.idMenu, sesion.idSubMenu, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdsorteo(sesion.pkSorteo);
			model.setIdusuario(sesion.pkUsuario);
			
			model.getSectorUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getSector()) ); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf(sesion.pkUsuario) );
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	/**/
	

	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, int sector,int nicho,
			mSeguimientoColaboradores model, SesionDatos sesion) throws ServletException, IOException, SQLException {

		ProgressBarCalc progress = new ProgressBarCalc(sesion);
		progress.prepare();
		
		model.setIdsorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Seguimiento.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("SEGUIMIENTO");
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		
		cell.setCellValue("No");
		cell = row.createCell(1);
		cell.setCellValue("UNIVERSIDAD");  //NOM_SECTOR
		cell = row.createCell(2);
		cell.setCellValue("NICHO");  //NOM_NICHO
		cell = row.createCell(3);
		cell.setCellValue("CLAVE");
		cell = row.createCell(4);
		cell.setCellValue("COLABORADOR");  //NOM_COLABORADOR
		cell = row.createCell(5);
		cell.setCellValue("CORREO");
		cell = row.createCell(6);
		cell.setCellValue("CORREO2");
		cell = row.createCell(7);
		cell.setCellValue("TELEFONO");
		cell = row.createCell(8);
		cell.setCellValue("TELEFONO2");
		cell = row.createCell(9);
		cell.setCellValue("TALONARIO");
		cell = row.createCell(10);
		cell.setCellValue("MONTO");
		cell = row.createCell(11);
		cell.setCellValue("ABONO");
		cell = row.createCell(12);
		cell.setCellValue("VENDIDO");
		cell = row.createCell(13);
		
		
		int MAX = model.contar(sector, nicho, sesion);
		progress.init(MAX);
		
		ResultSet rs = model.paginacion(sector, nicho, sesion);
		
		int fila = 1;
		
		while (rs.next())
		{
			row = sheet.createRow(fila);
				
				//No
				cell = row.createCell(0);
				cell.setCellValue(fila);
				//UNIVERSIDAD
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("NOM_SECTOR"));
				//NICHO
				cell = row.createCell(2);
				cell.setCellValue(rs.getString("NOM_NICHO"));
				//CLAVE
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("CLAVE"));
				//NOMBRE
				cell = row.createCell(4);
				cell.setCellValue(rs.getString("NOM_COLABORADOR"));			

				model.setId(rs.getInt("PK1"));
				model.ObtenerCorreo();
				model.ObtenerTelefono();
				
				//CORREO1
				cell = row.createCell(5);
				cell.setCellValue(model.getCorreopersonal());
				
				//CORREO2
				cell = row.createCell(6);
				cell.setCellValue(model.getCorreotrabajo());
				
				
				
				//TELEFONO CASA
				cell = row.createCell(7);
				cell.setCellValue(model.getTelefonocasa());
				
				//TELEFONO OFICINA
				cell = row.createCell(8);
				cell.setCellValue(model.getTelefonooficina());
				
				
				// FOLIO TALONARIO
				cell = row.createCell(9);
				cell.setCellValue(rs.getString("FOLIO"));
				
				// MONTO TALONARIO
				cell = row.createCell(10);
				cell.setCellValue(rs.getString("MONTO"));
				
				// ABONO TALONARIO
				cell = row.createCell(11);
				cell.setCellValue(rs.getString("TALONARIO_ABONO"));
				
				// VENDIDO
				cell = row.createCell(12);
				String vendido = rs.getString("VENDIDO");
				if(vendido==null) vendido = "N";
				cell.setCellValue(vendido.compareTo("V")==0 ? "SI" : "NO");
				
				fila++;
				
				progress.progress();
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);
		wb.close();

		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		
		progress.complete();
		
	}
	
}

