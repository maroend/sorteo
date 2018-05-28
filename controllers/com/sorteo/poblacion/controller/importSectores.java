package com.sorteo.poblacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

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
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.core.Factory;
import com.core.Global;
import com.core.ReadExcel;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.core.SuperModel.RESULT;
import com.sorteo.herramientas.controller.ProgressBarCalc;
import com.sorteo.poblacion.model.mColaboradores;
import com.sorteo.poblacion.model.mSectores;
import com.sorteo.poblacion.model.mImportSectores;


/**
 * Servlet implementation class Premios
 */
@WebServlet("/ImportSectores")
public class importSectores extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public importSectores() {
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
		mImportSectores model = new mImportSectores();
		
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
			mSectores modelSectores = new mSectores();
			registros = loadExcel(context, UploadFile.getPathOf(context, fileName), modelSectores, sesion);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(registros);
			break;
		
		case "Buscar":
			

			break;
			
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 16 : 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		default:
			
			fullPath = "/WEB-INF/views/poblacion/import_sectores.html";
			menu = vista.initMenu(0, false, 3, 4, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println(">>>>pksorteo:"+sesion.pkSorteo);
			model.setIdsorteo(sesion.pkSorteo);
			model.consultaSorteo();
			System.out.println(">>>>sorteo:"+(model.getSorteo()==null ? "null" : model.getSorteo()));
			
			
			HTML = HTML.replace("#SORTEO#", model.getSorteo());
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	

	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, mImportSectores model, SesionDatos sesion) throws ServletException, IOException {
		
		model.setIdUsuario(sesion.pkUsuario);
		model.consultaUsuarioSorteo();

		if (model.getIdsorteo() != 0) {
			sesion.pkSorteo = model.getIdsorteo();
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
		
		mImportSectores model = new mImportSectores();
		Factory.prepareError(request);
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		if (sesion == null)
			return;
		
		//String accion = request.getParameter("accion");
		String JSON = "";
		
		String fullPathinfouser = "/uploads";
		String fullPathtemp = "/temp";
		
		ServletContext context = getServletContext();
		
		String filePath = context.getRealPath(fullPathinfouser);
		String Pathtemp = context.getRealPath(fullPathtemp);
		
		UploadFile upload = new UploadFile();
		upload.uploadFile(request, Pathtemp, filePath);
		
		JSON += "{\"filename\":\"" + upload.getFileName() + "\"}";
			
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(JSON);
		
		model.close();
	}
	
	
	protected String loadExcel(ServletContext context, String fileName, mSectores model, SesionDatos sesion)
	{
		String HTML="";
		try {
			ReadExcel re = new ReadExcel(fileName);
			re.read("SECTORES", 0);
			
			if (re.matriz != null)
			{
				StringBuilder sb = new StringBuilder();
				
				model.setIdSorteo(sesion.pkSorteo);
				model.setUsuario(sesion.getUsuario());
				re.inserted = "";
				re.countInserts = 0;
				re.countUpdates = 0;

				for (int row = 0; row < re.matriz.length; row++) {
					try {
						re.process = "";
						agregarSector(row, re, model, sb);
						
					} catch(Exception ex) {
						String msg = (re.process != "") ? ("No se encuentra la columna: " + re.process) : "";
						sb.append("{\"row\":\"").append(row + 1).append("\",\"res\":\"").append(RESULT.ERROR).append("\",\"msg\":\"").append(msg).append("\"},");
						break;
					}
				}
				
				sb.delete(sb.length() - 1, sb.length());
				
				sb.insert(0, "[{"
						+ "\"records\":\"" + (re.countInserts+re.countUpdates) + "\","
						+ "\"warnings\":\"0\","
						+ "\"errors\":\""+re.countErrors+"\"},"
								);
				sb.append("]");

				re.content = sb.toString();
			}
			//agregarColaboradoresDesdeExcel(model, sesion, re);

			HTML = re.content;
		} catch(Exception ex) {
			String msg = Global.extractError(ex);
			HTML = ("[{'row':'1','res':'ERROR','msg':'" + msg + "'}]").replaceAll("'", "\"");
		}
		return HTML;
	}
	/*
	protected void agregarSectoresDesdeExcel(mSectores model, SesionDatos sesion, ReadExcel re) {
		StringBuilder sb = new StringBuilder();
		System.out.println("Process sql...");
		
		model.setIdSorteo(sesion.pkSorteo);
		model.setUsuario(sesion.getUsuario());
		re.inserted = "";
		re.countInserts = 0;
		re.countUpdates = 0;

		sb.append("[");
		for (int row = 0; row < re.matriz.length; row++) {
			try {
				re.process = "";
				agregarColaborador(row, re, model, sb);
				
			} catch(Exception ex) {
				String msg = (re.process != "") ? ("No se encuentra la columna: " + re.process) : "";
				sb.append("{\"row\":\"").append(row + 1).append("\",\"res\":\"").append(RESULT.ERROR).append("\",\"msg\":\"").append(msg).append("\"},");
				break;
			}
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]");

		re.content = sb.toString();
	}
	//*/
	protected void agregarSector(int row, ReadExcel re, mSectores model , StringBuilder sb) throws Exception{

		RESULT res;
		boolean update;
		
		String clave = re.getString(row, "CLAVE");

		model._mensaje = "";
		if (clave != null && !clave.equals(""))
		{
			model.setClave(clave);
			model.setSector(re.getString(row,"SECTOR"));
			model.setDescripcion(re.getString(row,"DESCRIPCION"));

			re.process = "";
			
			// Consultamos el idSector por clave
			int idSector = model.consultaIdXClave();
			update = idSector != -1;
			
			if (update)
			{
				model.setId(idSector);
				res = model.actualizar();
				if (res == RESULT.OK)
					re.countUpdates++;
			}
			else {
				res = model.insertar();
				if (res == RESULT.OK)
					re.countInserts++;
			}
		}
		else {
			update = false;
			res = RESULT.ERROR;
			model._mensaje = "La clave de sector no es v&aacute;lida. ";
		}
		
		if(res== RESULT.ERROR && !model._mensaje.equals("")){
			model._mensaje = "Ocurrio un error en la l&iacute;nea: " + (re.matriz[row][0].rowIndex + 1) + ". " + model._mensaje;
			re.countErrors++;
		}
		
		sb
		.append("{\"row\":\"").append(row + 1).append("\"")
		.append(",\"cve\":\"").append(model.getClave()).append("\"")
		.append(",\"nom\":\"").append(model.getSector()).append("\"")
		.append(",\"des\":\"").append(model.getDescripcion()).append("\"")
		.append(",\"upd\":\"").append(update ? 1 : 0).append("\"")
		.append(",\"res\":\"").append(res).append("\"")
		.append(",\"msg\":\"").append(model._mensaje).append("\"")
		.append("},");
		//*/
	}
	/*
	protected String loadExcel(ServletContext context, String fileName, mImportSectores model, SesionDatos sesion)
	{
		
		String HTML = "";
		String clave ="";
		String sector ="";
		String descripcion ="";
		double comision = 0;
		try {
		
			File file = new File(fileName);
			POIFSFileSystem fs;
			fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			model.setNumerrores(0);
			model.setNumregistrados(0);
			model.setNumwarning(0);
			
			for (int r = 1; r < rows ; r++) {
				
				row = sheet.getRow(r);
				if (row != null) {
					
					
					//CLAVE
					cell = row.getCell((short) 1);
					clave = cell.getStringCellValue();
					model.setClaveSector(clave);
					
					
					//SECTOR
					cell = row.getCell((short) 2);
					sector = cell.getStringCellValue();
					model.setSector(sector);
					
					
					//DESCRIPCION
					cell = row.getCell((short) 3);
					descripcion = cell.getStringCellValue();
					model.setDescripcion(descripcion);
					
					
					//COMISION
					cell = row.getCell((short) 4);
					comision = cell.getNumericCellValue();
					model.setComision(comision);
				
					ProcessExcel(model,sesion);
						
					HTML += "<tr class=\""+model.getOperacion()+"\">";
					HTML += "<td>"+r+"</td><td>"+clave+"</td><td>"+sector+"</td><td>"+descripcion+"</td><td>"+comision+"</td><td>"+model.getStatus()+"</td>"; 
					HTML += "</tr>#|#";
					
	
				}
				
			}
			
			wb.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 HTML += model.getNumregistrados()+","+model.getNumwarning()+","+model.getNumerrores();
		
		 //System.out.println("ERRORES::: "+model.getNumwarning());
		return HTML;
	
	}
	*/
	
	
	protected String ProcessExcel(mImportSectores model, SesionDatos sesion){
	
    String HTML = "";
              model.setIdUsuario(sesion.pkUsuario);
	          model.setIdsorteo(sesion.pkSorteo); 
              model.ProcessRows();
	
	return HTML;
		
		
	}
	
}

