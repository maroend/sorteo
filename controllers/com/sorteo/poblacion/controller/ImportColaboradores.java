package com.sorteo.poblacion.controller;

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

import com.core.Factory;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.sorteo.poblacion.model.mImportColaboradores;

/**
 * Servlet implementation class Premios
 */
@WebServlet("/ImportColaboradores")
public class ImportColaboradores extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportColaboradores() {
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
		mImportColaboradores model = new mImportColaboradores();
		
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
		//                 Especificar permiso para cargar colaboradores
		// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		if (!sesion.permisos.havePermiso(20099)) { view = "error"; }
		else{
			// No hay sorteo seleccionado
			if (sesion.pkSorteo == -1)
				SorteoPredeterminado(request, response, model, sesion);
		}

		switch (view) {
		
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 16 : 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
			
		case "consultaSectores":
			HTML = consultaSectores(request, model, sesion);
			break;
			
		case "consultaNichos":
			HTML = consultaNichos(request, model);
			break;

		default:
			
			fullPath = "/WEB-INF/views/poblacion/import_colaboradores.html";
			menu = vista.initMenu(0, false, 3, 6, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			model.setIdSorteo(sesion.pkSorteo);
			
			
			model.getSectorUsuarioActual(sesion);
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getIdsector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( sesion.pkUsuario));
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(HTML);
		model.close();
	}

	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, mImportColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		
		model.setIdUsuario(sesion.pkUsuario);
		model.consultaUsuarioSorteo();

		if (model.getIdSorteo() != 0) {
			sesion.pkSorteo = model.getIdSorteo();
			sesion.guardaSesion();
			if ((sesion = SesionDatos.start(request, response, false, model)) == null)
				return;
		}
	}
	
	protected String consultaSectores(HttpServletRequest request,
			mImportColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String panelcontent = "";

		model.setIdSorteo(sesion.pkSorteo);
		model.setIdUsuario(Integer.valueOf( request.getParameter("usuario")));
		model.setIdsector(Integer.parseInt(request.getParameter("idsectorU")));
		
		if(sesion.permisos.esAdministrador())
		{
			panelcontent += "<option>Seleccionar Sector</option>\n";

			rs = model.getSectores();
		}
		else
			rs = model.getSectoresUsuario();				
		
		try {
			while (rs.next()) {
				panelcontent += "<option value='" + rs.getInt("PK1") + "'>" + rs.getString("SECTOR") + "</option>\n";
			}
		} catch (SQLException e) { e.printStackTrace(); }

		return panelcontent;
	}
	
	protected String consultaNichos(HttpServletRequest request,
			mImportColaboradores model) throws ServletException, IOException {

		model.setIdsector(Integer.valueOf(request.getParameter("idsector")));
		ResultSet rs = model.getNichos();

		String panelcontent = "";

		try {
			while (rs.next()) {
				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\">" + rs.getString("NICHO") + "</option>\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return  panelcontent;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO doPost()
		
		mImportColaboradores model = new mImportColaboradores();
		Factory.prepareError(request);
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		if (sesion == null)
			return;
		
		String fullPathinfouser = "/uploads/";
		String fullPathtemp = "/temp/";

		ServletContext context = getServletContext();

		String filePath = context.getRealPath(fullPathinfouser);
		String Pathtemp = context.getRealPath(fullPathtemp);

		UploadFile upload = new UploadFile();
		upload.uploadFile(request, Pathtemp, filePath);

		String HTML = "{\"file\":\"" + upload.getFileName() + "\"}";
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
		model.close();
	}
	
}

