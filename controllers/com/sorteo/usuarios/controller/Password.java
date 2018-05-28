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
import com.core.Security;
import com.core.SesionDatos;
import com.sorteo.usuarios.model.mPassword;

/**
 * Servlet implementation class Password
 */
@WebServlet("/Password")
public class Password extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Password() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Factory vista = new Factory();
		SesionDatos sesion;
		mPassword model = new mPassword();
		
		// TODO Auto-generated method stub
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(10096)){view = "error";}
		
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		//int idSorteo = 0;

		if (view == null) {
			view = "";
		}
		
		switch (view) {

		case "EditaPassword":
			System.out.println(">>>controller EditaPassword");
			
			try {
				
				String password = Security.encrypt(request.getParameter("password"));
				model.setUsuario(request.getParameter("usuario"));
				
				//model.setIdusuario(sesion.pkUsuario);
				model.ActualizaPassword(password,Security.encrypt(request.getParameter("passwordactual")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			break;
			
			
       case "ValidaPassword":
    	   
    		

			
			String passwordactual =  request.getParameter("passwordactual");
			String usuario =  request.getParameter("usuario");
			if(ValidaPassword(passwordactual, model, sesion,usuario)==true){
				HTML = "true";
			}else{
				HTML = "false";
			}
			
			break;	
	
			
		case "error":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			break;	

		default:
			fullPath = "/WEB-INF/views/usuarios/password.html";
			menu = vista.initMenu(0, false, 0, 1, sesion);
			notificaciones = vista.initNotificaciones(context,
					sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
					infouser);
			
			model.setIdusuario(sesion.pkUsuario);
			String regex = "#USUARIO#";
			HTML = HTML.replaceAll(regex,model.getUsuario());

			break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		
		
		model.close();
		
	}
	
	
	
	
	
	protected boolean ValidaPassword(String password, mPassword model, SesionDatos sesion,String usuario){
		
		boolean validado = false;
		
		model.setUsuario(usuario);
		//System.out.println(">>>usr1: "+sesion.nickName);
		
		try {
			if(model.ValidaPassword(Security.encrypt(password))==true){
				validado = true;
			}else{
				validado = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return validado;
	}
	
	
/*protected boolean guardaPassword(String password, mPassword model, SesionDatos sesion,String usuario){
		
	System.out.println(">>>dopost: ");

	try {
		
		String password = Security.encrypt(request.getParameter("password"));
		model.setUsuario(request.getParameter("usuario"));
		
		//model.setIdusuario(sesion.pkUsuario);
		model.ActualizaPassword(password,Security.encrypt(request.getParameter("passwordactual")));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mPassword model, SesionDatos sesion)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println(">>>dopost: ");

		try {
			
			String password = Security.encrypt(request.getParameter("password"));
			model.setUsuario(request.getParameter("usuario"));
			
			//model.setIdusuario(sesion.pkUsuario);
			model.ActualizaPassword(password,Security.encrypt(request.getParameter("passwordactual")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

