package com.sorteo.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.core.SuperModel;






import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.sorteo.login.model.mLogin;


/**
 * Servlet implementation class SSOffice365
 */
@WebServlet("/SSOffice365/")
public class SSOffice365 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SSOffice365() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String execute = request.getParameter("execute");
		if (execute == null) {
			execute = "";
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Factory vista = new Factory();
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		
		switch (execute) {
		
		case "VerifyToken":
			
			String id_token = request.getParameter("id_token");
			String args_extra = request.getParameter("args_extra");
			
			
			String[] jwt = id_token.split("\\.");
			String header = jwt[0];
			String payload = jwt[1];
			String key = jwt[2]; 
			
			
			byte[] decodedBytes = Base64.getDecoder().decode(payload);
			String jsonString = new String(decodedBytes);
			
			
			try {
				
				JSONParser parser = new JSONParser();
				
				 Object obj = parser.parse(jsonString);
				
				 JSONObject jsonObject = (JSONObject) obj;
				
				 String userID = (String) jsonObject.get("unique_name");
				 
				 
				 mLogin model = new mLogin();
				 
				 model.setUsername(userID);
				 
				 if(model.ValidarUsuario(model)){
					 
					 
					//SesionDatos sesion = SesionDatos.start(request, response, true, model);
					 
					 System.out.println("Logged");
					 SesionDatos sesionDatos = SesionDatos.afterLogIn(userID, model.db, request, response);
					 
					 Cookie cookie = new Cookie("datosdesesion", "" + sesionDatos.PK1 + "_" + sesionDatos.idSesion + "_" + sesionDatos.pkUsuario);
					 cookie.setPath("/");
					 cookie.setMaxAge(SesionDatos.limiteDeTiempo());
					 response.addCookie(cookie);
					 
	 
					
					 sesionDatos.creaNuevaSesion(request, response);
					 String encodedURL = response.encodeRedirectURL("../Dashboard.do");
					 System.out.println("Logged: "+encodedURL);
					 response.sendRedirect(encodedURL);
					 
					 model.close();
					 
					 
	 
				 }else{
					 
					 System.out.println("Acceso Incorrecto");
					 
					 fullPath = "/WEB-INF/views/AccesoDenegado.html";
					 HTML = vista.CrearVista(context, fullPath, args_extra);
					 out.println(HTML);
					 
					 model.close();
					 
				 }
				 
				//out.println(correo);
				
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			break;
		
		
		default:
			
		  break;
		}
		
  		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
