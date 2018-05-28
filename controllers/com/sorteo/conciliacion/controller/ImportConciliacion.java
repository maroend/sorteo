package com.sorteo.conciliacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.MENU;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.sorteo.conciliacion.model.mImportConciliacion;
import com.sorteo.herramientas.controller.ProgressBarCalc;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


/**
 * Servlet implementation class ImportConciliacion
 */
@WebServlet("/ImportConciliacion")
public class ImportConciliacion extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportConciliacion() {
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
		mImportConciliacion model = new mImportConciliacion();
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String registros = "";

		String view = request.getParameter("view");
		if(view==null)
			view="";
		
		// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		//                 Especificar permiso para cargar colaboradores
		// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (!sesion.permisos.havePermiso(20099)) { view = "error"; }
		else{
			// No hay sorteo seleccionado
			if (sesion.pkSorteo == -1)
				SorteoPredeterminado(request, response, model, sesion);
		}

		switch (view) {
		
        case "LoadExcelFile":
			
			String fileName = request.getParameter("fileName");
			registros = loadExcel(context, UploadFile.getPathOf(context, fileName), model, sesion);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(registros);
			break;
		
		case "Buscar":

			break;
			
		case "ProgressBar":
			HTML = "" + sesion.data1;
			break;
			
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 16 : 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		default:
			
			fullPath = "/WEB-INF/views/conciliacion/ImportConciliacion.html";
			menu = vista.initMenu(0, false, MENU.VENTAS, MENU.CONCILIACIÓN, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			//System.out.println(">>>>pksorteo:"+sesion.pkSorteo);
			model.setIdSorteo(sesion.pkSorteo);
			model.consultaSorteo();
			//System.out.println(">>>>sorteo:"+(model.getSorteo()==null ? "null" : model.getSorteo()));
			
			
			HTML = HTML.replace("#SORTEO#", model.getSorteo());
			
			if(sesion.data1!=0){
				sesion.data1=0;
				sesion.guardaSesion();
			}
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	

	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, mImportConciliacion model, SesionDatos sesion) throws ServletException, IOException {
		
		model.setIdUsuario(sesion.pkUsuario);
		model.consultaUsuarioSorteo();

		if (model.getIdSorteo() != 0) {
			sesion.pkSorteo = model.getIdSorteo();
			sesion.guardaSesion();
			if ((sesion = SesionDatos.start(request, response, false, model)) == null)
				return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO doPost()
		
		mImportConciliacion model = new mImportConciliacion();
		Factory.prepareError(request);
		/*
		SesionDatos sesion;
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		*/
		
		//String accion = request.getParameter("accion");
		String HTML = "";
		
			String fullPathinfouser = "/uploads";
			String fullPathtemp = "/temp";
			
			ServletContext context = getServletContext();
			
			String filePath = context.getRealPath(fullPathinfouser);
			String Pathtemp = context.getRealPath(fullPathtemp);
			
			UploadFile upload = new UploadFile();
			upload.uploadFile(request, Pathtemp, filePath);
			
			HTML += upload.getFileName();
			
		//}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
		model.close();
	}
	
	@SuppressWarnings("deprecation")
	private ArrayList<HSSFCell> getListCells(HSSFRow row, int tantos) {
		if (row == null)
			return null;
		ArrayList<HSSFCell> list = new ArrayList<HSSFCell>();

		HSSFCell cell;
		for (short i = 0; i < tantos; i++) {
			cell = row.getCell(i);
			if (cell == null)
				return null;
			list.add(cell);
		}
		return list;
	}

	protected String loadExcel(ServletContext context, String fileName, mImportConciliacion model, SesionDatos sesion)
	{
		StringBuilder sb = new StringBuilder();
		//String HTML = "";
		try {
			ProgressBarCalc pbar = new ProgressBarCalc(sesion);
			pbar.prepare();
			
			
			File file = new File(fileName);
			POIFSFileSystem fs;
			fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			model.setNumErrores(0);
			model.setNumRegistrados(0);
			model.setNumWarning(0);
			model.resetFechaRegistro();
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdUsuario(sesion.pkUsuario);
			pbar.init(rows);
			
			ArrayList<HSSFCell> listCells = new ArrayList<HSSFCell>();
			
			for (int r = 1; r < rows ; r++) {
				
				row = sheet.getRow(r);
				if (row != null)
					try{
						
						if ((listCells = getListCells(row, 10)) == null)
							continue;
						
						//CUENTA
						cell = listCells.get(0);
						String cuenta = cell.getStringCellValue();
						model.setCuenta(cuenta);
						System.out.println("Loading..."+cuenta);
						
						//FECHA
						//cell = row.getCell((short) 1);
						cell = listCells.get(1);
						String fecha = cell.getStringCellValue();
						model.setFecha(fecha);
						System.out.println("Loading..."+fecha);
						
						//HORA
						//cell = row.getCell((short) 2);
						cell = listCells.get(2);
						String hora = cell.getStringCellValue();
						model.setHora(hora);
						System.out.println("Loading..."+hora);
						
						//SUCURSAL
						//cell = row.getCell((short) 3);
						cell = listCells.get(3);
						String sucursal = cell.getStringCellValue();
						model.setSucursal(sucursal);
						System.out.println("Loading..."+sucursal);
						
						
						//DESCRIPCION
						//cell = row.getCell((short) 4);
						cell = listCells.get(4);
						String descripcion = cell.getStringCellValue();
						model.setDescripcion(descripcion);
						System.out.println("Loading..."+descripcion);
						
						
						//CARGO
						//cell = row.getCell((short) 5);
						cell = listCells.get(5);
						String cargo = cell.getStringCellValue();
						model.setCargo(cargo);
						System.out.println("Loading..."+cargo);
						
						//IMPORTE
						//cell = row.getCell((short) 6);
						cell = listCells.get(6);
						double importe = cell.getNumericCellValue();
						model.setImporte(importe);
						System.out.println("Loading..."+importe);
						
						
						//SALDO
						//cell = row.getCell((short) 7);
						cell = listCells.get(7);
						double saldo = cell.getNumericCellValue();
						model.setSaldo(saldo);
						System.out.println("Loading..."+saldo);
						
						
						//REFERENCIA
						//cell = row.getCell((short) 8);
						cell = listCells.get(8);
						String referencia = cell.getStringCellValue();
						model.setReferencia(referencia);
						System.out.println("Loading..."+referencia);
						
						
						//REFERENCIA INTERBANCARIA
						//cell = row.getCell((short) 9);
						cell = listCells.get(9);
						String referenciaIB = cell.getStringCellValue();
						model.setReferenciaIB(referenciaIB);
						model.setConcepto(referenciaIB);
						System.out.println("Loading..."+referenciaIB);

						referenciaIB = model.getReferenciaIB();
						
						model.processRow(sesion);
						
						String referenciaIB_css = referenciaIB;
						if (referenciaIB.length()==20) {
							referenciaIB_css = 
								referenciaIB.substring( 0, 11) +
								"<span class='resaltar'>" + referenciaIB.substring(11, 19) + "</span>" +
								referenciaIB.substring(19);
						}
	
						sb.append("<tr class='").append(model.getOperacion()).append("'>")
								.append("<td>").append(r).append("</td>")
								.append("<td>").append(cuenta).append("</td>")
								.append("<td>").append(fecha).append("</td>")
								.append("<td>").append(hora).append("</td>")
								.append("<td>").append(sucursal).append("</td>")
								.append("<td>").append(descripcion.replaceAll(" ", "&nbsp;")).append("</td>")
								.append("<td>").append(cargo).append("</td>")
								.append("<td style='text-align: right;'>").append(importe).append("</td>")
								.append("<td style='text-align: right;'>").append(saldo).append("</td>")
								.append("<td>").append(referencia).append("</td>")
								.append("<td>").append(referenciaIB_css).append("</td>")
								.append("<td>").append(model.getStatus()).append("</td>") 
								.append("</tr>#|#");
						
					} catch (Exception ex) {
						sb.append("<tr><td colspan=\"12\">Error en el registro: ").append(r).append("</td></tr>#|#");
						System.out.println("--");
					}
				
				pbar.progress();
				//System.out.println("-->" + sesion.data1 + " %");
			}

			System.out.println("wb.close");
			wb.close();
			System.out.println("fs.close");
			fs.close();
			System.out.println("finish 100%");
			
			pbar.complete();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	//	HTML += model.getNumRegistrados() + "," + model.getNumWarning() + "," + model.getNumErrores();
		sb.append(model.getNumRegistrados()).append(",").append(model.getNumWarning()).append(",").append(model.getNumErrores());
		
		//System.out.println("ERRORES::: "+model.getNumwarning());
		return sb.toString();
	}
	
	/*
	protected String processRow(mImportConciliacion model, SesionDatos sesion){
	
    String HTML = "";
	          model.setIdsorteo(sesion.pkSorteo); 
              model.processRow();
	
	return HTML;
		
		
	}
	//*/
}

