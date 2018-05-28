package com.sorteo.usuarios.controller;

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
import com.sorteo.usuarios.model.mProfile;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Factory  vista = new Factory();
		SesionDatos sesion;
		mProfile model= new mProfile();
		
		if((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu ="";
		String notificaciones ="";
		String infouser ="";
		
		
		
	   if(request.getParameter("idusuario")==null){ response.sendRedirect("Profile?idusuario="+sesion.pkUsuario);   }
		
		
		String view = request.getParameter("view");
		
		if(view == null){view = "";}
		
	    switch(view){	
	    
	    case "AgregarUsuario":
    		fullPath = "/WEB-INF/views/usuarios/agregar.html";
    		menu = vista.initMenu(0 ,false,24,25,sesion);
    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
	    	break;
	    
	    
	    case "getDireccion":
	    	//getDireccion(request, response);
	    	break;
	    	
	    case "BuscarUsuario":
	    	//BuscarUsuario(request, response);
	    	break;	
	    
	    	default:
	    		fullPath = "/WEB-INF/views/usuarios/profile.html";
	    		menu = vista.initMenu(0 ,false,1,1,sesion);
	    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
	    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
	    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
	    		break;
	    }
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
	
		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
