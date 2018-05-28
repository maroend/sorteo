package com.sorteo.talonarios.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.core.Factory;
import com.core.ReadExcel;
//import com.core.ReadXLSX;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.sorteo.talonarios.model.mSubirBoletos;

/**
 * Servlet implementation class Premios
 */
@WebServlet("/SubirBoletos")
public class SubirBoletos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubirBoletos() {
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
		mSubirBoletos model = new mSubirBoletos();
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";

		String view = request.getParameter("view");
		if(view==null)
			view="";
		
		// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		//                 Especificar permiso para cargar boletos
		// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (!sesion.permisos.havePermiso(20099)) { view = "error"; }
		else{
			// No hay sorteo seleccionado
			if (sesion.pkSorteo == -1)
				SorteoPredeterminado(request, response, model, sesion);
		}
		
		switch (view) {
		/*
		case "xlsx":
			ReadXLSX xlsx = new ReadXLSX();
			xlsx.read();
			break;
		//*/
		case "LoadExcelFile":
			String fileName = request.getParameter("fileName");
			String formato = request.getParameter("formato");
			String costo = request.getParameter("costo");
			
			HTML = loadExcel(context, UploadFile.getPathOf(context, fileName), model, sesion, formato, costo); 
			break;
			
		case "PrepararBoveda":
			model.setIdsorteo(sesion.pkSorteo);
			System.out.println("Borrando ("+model.getIdsorteo()+")...");
			model.borraBoletosDeSorteo();
			sesion.data1=0;
			sesion.guardaSesion();

			break;
			
			
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 16 : 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
			
		default:
			
			fullPath = "/WEB-INF/views/boveda/subir_boletos.html";
			menu = vista.initMenu(0, false, sesion.idMenu, sesion.idSubMenu, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			HTML = HTML
					.replace("#DISPLAY_BOTON_TALS#", sesion.idSubMenu==8 ? "display" : "none")
					.replace("#DISPLAY_BOTON_BOLS#", sesion.idSubMenu==9 ? "display" : "none");
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, mSubirBoletos model, SesionDatos sesion) throws ServletException, IOException {
		
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
		mSubirBoletos model = new mSubirBoletos();
		Factory.prepareError(request);
		
		String JSON = "";
		String action = request.getParameter("action");
		if(action==null)
			action = "";
		
		switch(action){

		case "upload":
			String fullPathinfouser = "/uploads/";
			String fullPathtemp = "/temp/";
			
			ServletContext context = getServletContext();
			
			String filePath = context.getRealPath(fullPathinfouser);
			String Pathtemp = context.getRealPath(fullPathtemp);
			
			UploadFile upload = new UploadFile();
			upload.uploadFile(request, Pathtemp, filePath);
			
			JSON = "{\"filename\":\"" + upload.getFileName() + "\"}";

			break;
		}
		
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(JSON);
		
		model.close();
	}
	
	
	
	// _________________________________________________________________________________________________________
	
	//                                             LECTURA DE EXCEL
	// _________________________________________________________________________________________________________
	
	protected String loadExcel(ServletContext context, String fileName, mSubirBoletos model, SesionDatos sesion, String formato, String costo)
	{
		String HTML = "";
		try {
			
			ReadExcel re = new ReadExcel(fileName);
			model.contarBoletos=0;
			model.contarTalonarios=0;
			
			/*
			int sheet=1;
			while(true)
			{
				re.read("BOLETOS-" + sheet);
				
				if (re.endOfBook())
					break;
				
				re.content = "";
				
				model.insert(re, sesion, formato, costo);
				
				// Despues de procesar cada hoja se intenta liberar memoria.
				Runtime garbage = Runtime.getRuntime();
				garbage.gc();
				
				sheet++;
			}*/
			re.read("BOLETOS");
			model.insert(re, sesion, formato, costo);
			
			HTML += model.contarBoletos + "|" + model.contarTalonarios;
			/*
			if (insertBoletosFromExcel(model, sesion, re, formato, costo) == 0){
				
				HTML = re.content;
			}
			else{
				HTML = re.content;
			}
			//*/
		} catch(Exception ex) { return ex.getMessage(); }
		return HTML;
	}
	
	/*
	protected int insertBoletosFromExcel(mSubirBoletos model, SesionDatos sesion, ReadExcel re, String formato, String costo)
	{
		model.insert(re, sesion, formato, costo);
		
		return 0;
	}
	*/

}

