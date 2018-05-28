package com.sorteo.premios.controller;

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
import com.sorteo.premios.model.mBuscarBoleto;

/**
 * Servlet implementation class Busqueda
 */
@WebServlet("/BuscarBoleto")
public class BuscarBoleto extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarBoleto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Factory vista = new Factory();
		//SesionDatos sesion;
		mBuscarBoleto model = new mBuscarBoleto();
		
		
		// TODO Auto-generated method stub
		Factory.prepareError(request);

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		
		String view = request.getParameter("view");
		if (view == null) {
			view = "";
		}
		
		switch (view)
		{
		case "Buscar":
			String folioBoleto = request.getParameter("boleto");
			if (folioBoleto == null)
				folioBoleto = "";

			long idSorteo = 0;
			try {
				idSorteo = Integer.parseInt(request.getParameter("idsorteo"));
			} catch (Exception ex) {
				idSorteo = 0;
			}

			HTML = Buscar(idSorteo, model, folioBoleto);
			
			break;

		default:

			fullPath = "/WEB-INF/views/premios/buscar_boleto.html";
			HTML = vista.CrearVista(context, fullPath, "", "", "");

			SesionDatos sesion;
			if ((sesion = SesionDatos.start(request, response, false, model)) == null)
				return;

			HTML = HTML.replace("#PK_SORTEO#", "" + sesion.pkSorteo);

			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	protected String Buscar(long idSorteo, mBuscarBoleto model, String folioBoleto) {
		
		model.setIdsorteo(idSorteo);
		ResultSet res = model.buscarTalonario(folioBoleto);

		try
		{
			if(res.next())
			{
				String talonario = res.getString("TALONARIO");
				String status = res.getString("VENDIDO");
				String incidencia = res.getString("INCIDENCIA");
				String retornado = res.getString("RETORNADO");
				String asignado = res.getString("ASIGNADO");
				String electronico = res.getString("ELECTRONICO");
				
				return "<datos>"
						+ idSorteo
						+ "~" + folioBoleto
						+ "~" + talonario
						+ "~" + status
						+ "~" + incidencia
						+ "~" + retornado
						+ "~" + asignado
						+ "~" + electronico
						+ "</datos>";
			}
		} catch (SQLException e) { }

		return "-1";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

