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
import com.sorteo.poblacion.model.mColaboradores;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/Registro")
public class Registro extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public Registro() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mColaboradores model = new mColaboradores();
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
		
		if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){view = "errorCerrado";}
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		case "errorCerrado":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 10, 110, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;
		default:
			
			fullPath = "/WEB-INF/views/conciliacion/registro.html";
			menu = vista.initMenu(0, false, 10, 110, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdSorteo(sesion.pkSorteo);
			model.setIdUsuario(sesion.pkUsuario);
			
			model.consultaPoblacionUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getIdSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( (int)sesion.pkUsuario));
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", sesion.sorteoActivo ? "display" : "none");
			
			HTML = putCode(HTML, 30113, "#BTN_REGISTRO_VENTA#",
					""  /*"<p><a href=\"RegistroVenta\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\"  disabled=\"disabled\"> " +
					"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro venta de boletos</a></p>" */,
					"<p><a href=\"RegistroVenta\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\"> " +
					"<i class=\"fa fa-2x fa-tags\"></i>&nbsp;&nbsp;Registro de venta de boletos</a></p>",sesion);
			
			HTML = putCode(HTML, 30120, "#BTN_REGISTRO_COMPRADORES#",
					""  /*"<p><a href=\"RegistroComprador\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\" disabled=\"disabled\">" +
					"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro de compradores</a></p>"*/,
					"<p><a href=\"RegistroComprador\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\">" +
					"<i class=\"fa fa-2x fa-check\"></i>&nbsp;&nbsp;Registro de compradores &nbsp;&nbsp;&nbsp;&nbsp;</a></p>",sesion);
			
			System.out.println("permiso 30114:"+sesion.permisos.havePermiso(30114));
			HTML = putCode(HTML, 30114, "#BTN_REGISTRO_RETORNO#",
					""  /*"<p><a href=\"RegistroRetorno\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\"  disabled=\"disabled\">" +
					"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro retorno de boleto</a></p>"*/,
					"<p><a href=\"RegistroRetorno\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\">" +
					"<i class=\"fa fa-2x fa-undo\"></i>&nbsp;&nbsp;Registro de retorno de boleto</a></p>",sesion);
			
			HTML = putCode(HTML, 30121, "#BTN_INCIDENCIAS#",
					""  /*"<p><a href=\"RegistroRetorno\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\"  disabled=\"disabled\">" +
					"<i class=\"fa fa-u fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro retorno de boleto</a></p>"*/,
					"<p><a href=\"RegistroIncidencia\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\">" +
					"<i class=\"fa fa-2x fa-warning\"></i>&nbsp;&nbsp;Registro de incidencia de boleto</a></p>",sesion);
			
			
			HTML = putCode(HTML, 30114, "#BTN_RETORNOTAL_A_BOVEDA#",
					""  /*"<p><a href=\"RegistroRetorno\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\"  disabled=\"disabled\">" +
					"<i class=\"fa fa-u fa-stack-2x text-danger\"></i>&nbsp;&nbsp;Registro retorno de boleto</a></p>"*/,
					"<p><a href=\"DevolverTalonarios\" style=\"width:50%;\" role=\"button\" class=\"btn btn-success btn-lg\">" +
					"<i class=\"fa fa-2x fa-history\"></i>&nbsp;&nbsp;Devolver Talonarios FC5</a></p>",sesion);
			
        	
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
}
