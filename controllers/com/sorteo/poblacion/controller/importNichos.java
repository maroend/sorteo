package com.sorteo.poblacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.sorteo.poblacion.model.mNichos;
import com.sorteo.poblacion.model.mImportNichos;
import com.sorteo.poblacion.model.mSectores;


/**
 * Servlet implementation class Premios
 */
@WebServlet("/ImportNichos")
public class importNichos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public importNichos() {
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
		mImportNichos model = new mImportNichos();
		
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
			int pk_sector = Integer.parseInt(request.getParameter("pk_sector"));
			mNichos modelNichos = new mNichos();
			modelNichos.setIdSector(pk_sector);
			String fileName = request.getParameter("fileName");
			registros = loadExcel(context, UploadFile.getPathOf(context, fileName), modelNichos, sesion);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(registros);
			break;
		
		case "Buscar":

			break;

		case "BuscarSector":
			HTML = BuscarSector(request, model, sesion);
			break;
			
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 16 : 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		default:
			
			fullPath = "/WEB-INF/views/poblacion/import_nichos.html";
			menu = vista.initMenu(0, false, 3, 5, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			
			model.setIdSorteo(sesion.pkSorteo);
			model.consultaSorteo();
			
			
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getIdSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( sesion.pkUsuario));
			
			
			HTML = HTML.replace("#SORTEO#", model.getSorteo());
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	

	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, mImportNichos model, SesionDatos sesion) throws ServletException, IOException {
		
		model.setIdUsuario(sesion.pkUsuario);
		model.consultaUsuarioSorteo();

		if (model.getIdSorteo() != 0) {
			sesion.pkSorteo = model.getIdSorteo();
			sesion.guardaSesion();
			if ((sesion = SesionDatos.start(request, response, false, model)) == null)
				return;
		}
	}
	
	protected String BuscarSector(HttpServletRequest request,
			mImportNichos model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String HTML = "";

		model.setIdSorteo(sesion.pkSorteo);
		model.setIdUsuario(Integer.valueOf( request.getParameter("usuario")));
		model.setIdSector(Integer.parseInt(request.getParameter("idsectorU")));
		
		if(sesion.permisos.esAdministrador()){
			
		//	panelcontent += "<option value=\"\">Todos</option> ";
			HTML += "<option value=\"S\">Seleccionar Sector</option> ";

			rs = model.getSectores();
			
		} else{
			
			rs = model.getSectoresUsuario();				
		}
		
		try {
			
			while (rs.next()) {

				HTML += "<option value=\"" + rs.getInt("PK1") + "\"";

				HTML += ">" + rs.getString("SECTOR") + "</option>";

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return HTML;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO doPost()
		
		mImportNichos model = new mImportNichos();
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
	
	
	protected String loadExcel(ServletContext context, String fileName, mNichos model, SesionDatos sesion)
	{
		String HTML="";
		try {
			ReadExcel re = new ReadExcel(fileName);
			re.read("NICHOS", 0);
			
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
						agregarNicho(row, re, model, sb);
						
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
	
	
	protected void agregarNicho(int row, ReadExcel re, mNichos model , StringBuilder sb) throws Exception{

		RESULT res;
		boolean update;
		
		String clave = re.getString(row, "CLAVE");

		model._mensaje = "";
		if (clave != null && !clave.equals(""))
		{
			model.setClave(clave);
			model.setNicho(re.getString(row,"NICHO"));
			model.setLimiteVenta(re.getString(row,"LIMITE VENTA"));
			model.setLimiteDeposito(re.getString(row,"LIMITE DEPOSITO"));

			re.process = "";
			
			// Consultamos el idNicho por clave
			int idNicho = model.consultaIdXClave();
			update = idNicho != -1;
			
			if (update)
			{
				model.setId(idNicho);
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
		.append(",\"nom\":\"").append(model.getNicho()).append("\"")
		.append(",\"ven\":\"").append(model.getLimiteVenta()).append("\"")
		.append(",\"dep\":\"").append(model.getLimiteDeposito()).append("\"")
		.append(",\"upd\":\"").append(update ? 1 : 0).append("\"")
		.append(",\"res\":\"").append(res).append("\"")
		.append(",\"msg\":\"").append(model._mensaje).append("\"")
		.append("},");
		//*/
	}	
	/*
	@SuppressWarnings("deprecation")
	protected String loadExcel(ServletContext context, String fileName, mImportNichos model, SesionDatos sesion)
	{
		
		String HTML = "";
		String clave ="";
		String nicho ="";
		String descripcion ="";
		//double comision = 0;
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
					model.setClaveNicho(clave);
					
					
					//SECTOR
					cell = row.getCell((short) 2);
					nicho = cell.getStringCellValue();
					model.setNicho(nicho);
					
					
					//DESCRIPCION
					cell = row.getCell((short) 3);
					descripcion = cell.getStringCellValue();
					model.setDescripcion(descripcion);
					
				
				
					ProcessExcel(model,sesion);
						
					HTML += "<tr class=\""+model.getOperacion()+"\">";
					HTML += "<td>"+r+"</td><td>"+clave+"</td><td>"+nicho+"</td><td>"+descripcion+"</td><td>"+model.getStatus()+"</td>"; 
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
	
	
	
	protected String ProcessExcel(mImportNichos model, SesionDatos sesion){
	
    String HTML = "";
              model.setIdUsuario(sesion.pkUsuario);
	          model.setIdSorteo(sesion.pkSorteo); 
              model.ProcessRows();
	
	return HTML;
		
		
	}
	//*/
	
}

