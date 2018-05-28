package com.sorteo.conciliacion.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.conciliacion.model.mRegistroIncidencia;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/RegistroIncidencia")
public class RegistroIncidencia extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroIncidencia() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mRegistroIncidencia model = new mRegistroIncidencia();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		case "getTalonario":
			getTalonario(request, response, model,sesion);
			break;
			
		case "actualizarComision":
			actualizarComision(request, response, model);
			break;
			
		default:
			
			String fullPath = "/WEB-INF/views/conciliacion/RegistroIncidencia.html";
			menu = vista.initMenu(0, false, 10, 110, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdsorteo(sesion.pkSorteo);
			model.setIdusuario(sesion.pkUsuario);
			
			model.getSectorUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf(sesion.pkUsuario));
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", sesion.sorteoActivo ? "display" : "none");
			
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
	
	
	protected void actualizarComision(HttpServletRequest request,
			HttpServletResponse response, mRegistroIncidencia model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")) );		
		model.setComision(Integer.valueOf(request.getParameter("comision")));			
		//model.actualizarComision(model);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mRegistroIncidencia model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
		model.setClave(request.getParameter("clave"));
		*/
	}
	
	protected void getTalonario(HttpServletRequest request,
			HttpServletResponse response, mRegistroIncidencia model,SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		model.setClave(request.getParameter("folio"));
		result = model.getTalonario(model,sesion.pkSorteo);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	
}
