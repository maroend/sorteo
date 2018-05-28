package com.sorteo.login.controller;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.core.Factory;
import com.core.Security;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.sorteo.login.model.mLogin;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login.do")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		
		// Si el Login NO fue invocado desde la sesion.
		if (action.equals("reset")){
			SuperModel model = new SuperModel();
			SesionDatos sesion = SesionDatos.start(request, response, true, model);
			if (sesion != null)
				sesion.cierra();
		}
		else
		{
			SuperModel model = new SuperModel();
			if (SesionDatos.start(request, response, false, model) == null)
				return; // En este punto el Login es invocado desde la sesion
			try {
				response.sendRedirect("Dashboard.do");
			}catch(Exception ex) { }
			return; // En este punto se rederirecciona al Dashboard si ya existe una sesion.
		}
		
		Factory vista = new Factory();
		String args_extra = request.getParameter("args");
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";

		String access = request.getParameter("access");
		if(access == null){access = "";}
		
		switch(access){
		
		case "incorrect":
			fullPath = "/WEB-INF/views/loginfault.html";
			HTML = vista.CrearVista(context, fullPath, args_extra);
		break;
		
		default:
			fullPath = "/WEB-INF/views/login.html";
			HTML = vista.CrearVista(context, fullPath, args_extra);
			break;
			
		}
		
		out.println(HTML);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		mLogin model = new mLogin();
		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		String args_extra = request.getParameter("args_extra");
		
		model.setUsername(user);
		model.setPassword(pwd);
		
		if(model.ValidarUsuario(model)){
			
			String userID = model.getUsername();
			String password = model.getPassword();
			
			try {
				password = Security.decrypt(password);
				System.out.println(" pwd:"+password);
			} catch (Exception e) {
				System.out.println("Error de encriptacion:" + e.getMessage());
				//e.printStackTrace();
			}
			if(userID.equals(user) && password.equals(pwd)){
				System.out.println("Logged");
				SesionDatos sesionDatos = SesionDatos.afterLogIn(userID, model.db, request, response);
				
				// Una vez logueado se crea una cookie con el PK1,id de sesion y el PK usuario.
				
				Cookie cookie = new Cookie("datosdesesion", "" + sesionDatos.PK1 + "_" + sesionDatos.idSesion + "_" + sesionDatos.pkUsuario);
				cookie.setMaxAge(SesionDatos.limiteDeTiempo());
				response.addCookie(cookie);
				
				if(sesionDatos.checaParametros(args_extra)) {
					sesionDatos.creaNuevaSesion(request, response);
					
					String encodedURL = response.encodeRedirectURL(sesionDatos.servlet);
					response.sendRedirect(encodedURL);
				}
				else {
					sesionDatos.creaNuevaSesion(request, response);
					//Get the encoded URL string
					String encodedURL = response.encodeRedirectURL("Dashboard.do");
					response.sendRedirect(encodedURL);
				}
			}
			else{
				 response.sendRedirect("Login.do?access=incorrect");
			}
		}
		else{
			response.sendRedirect("Login.do?access=incorrect");
		}
		
		model.close();
	}

}
