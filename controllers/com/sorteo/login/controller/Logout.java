package com.sorteo.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;

import javax.servlet.http.Cookie;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout.do")
public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String args_extra = request.getParameter("args");
		ServletContext context = getServletContext();
		Factory vista = new Factory();
		String HTML = "";
		String fullPath = "";
		fullPath = "/WEB-INF/views/logout.html";
		HTML = vista.CrearVista(context, fullPath, args_extra);
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("JSESSIONID")){
					System.out.println("JSESSIONID="+cookie.getValue());
					break;
				}
			}
		}
		// invalidate the session if exists
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		
		SesionDatos sesionDatos = SesionDatos.start(request, response, true, new SuperModel());
		if (sesionDatos != null)
			sesionDatos.cierra();
		
		//response.sendRedirect("Login.do");
		out.println(HTML);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
}
