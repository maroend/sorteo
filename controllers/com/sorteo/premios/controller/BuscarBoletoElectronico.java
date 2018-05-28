package com.sorteo.premios.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.Global;
import com.core.ParametersBase64;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.sorteo.premios.model.mBuscarBoletoElectronico;


//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.util.Base64;
import java.util.HashMap;

/**
 * Servlet implementation class Busqueda
 */
@WebServlet("/BuscarBoletoElectronico")
public class BuscarBoletoElectronico extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarBoletoElectronico() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SesionDatos sesion;
		Factory vista = new Factory();
		//SesionDatos sesion;
		mBuscarBoletoElectronico model = new mBuscarBoletoElectronico();
		
		
		// TODO Auto-generated method stub
		Factory.prepareError(request);

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		
		String view = request.getParameter("view");
		if (view == null) {
			view = "";
		}
		
		
		// ___________________________________________________________________________
		HashMap<String,ParametersBase64> params = ParametersBase64.parse(request.getParameter("q"), "[&]", "[=]", true);
		ParametersBase64 parameter;
		if ((parameter = params.get("PRINT")) != null)
		{
			if ((sesion = SesionDatos.start(request, response, false, model)) == null)
				return;
			fullPath = "/WEB-INF/views/premios/buscar_boleto_formato.html";
			HTML = vista.CrearVista(context, fullPath, "", "", "");
			
			String folioBoleto = parameter.value;
			parameter = params.get("idsorteo");
			int idSorteo = Integer.parseInt(parameter.value);
			HTML = print(HTML, model, idSorteo, folioBoleto);
			view = "NOUSED";
		}
		// ___________________________________________________________________________
		
			
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
		case "NOUSED": break;

		default:

			fullPath = "/WEB-INF/views/premios/buscar_boleto_electronico.html";
			
			HTML = vista.CrearVista(context, fullPath, "", "", "");

			//SesionDatos sesion;
			if ((sesion = SesionDatos.start(request, response, false, model)) == null)
				return;

			HTML = HTML.replace("#PK_SORTEO#", "" + sesion.pkSorteo);

			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(HTML);
		model.close();
	}
	
	protected String Buscar(long idSorteo, mBuscarBoletoElectronico model, String folioBoleto) {
		
		model.setIdsorteo(idSorteo);
		ResultSet res = model.buscarComprador(folioBoleto);

		try
		{
			if(res.next())
			{
				creaBoleto(folioBoleto);
				
				int pkBoleto = res.getInt("PK_BOLETO");
				model.consultaDatos(pkBoleto);
				
				return ("{\n"
					+ " 'found':'true'"
					+ ",'pkboleto': '" + pkBoleto + "'\n"
					+ ",'talonario': '" + res.getString("TALONARIO") + "'\n"
					+ ",'electronico': '" + res.getString("ELECTRONICO") + "'\n"
					+ ",'vendido': '" + res.getString("VENDIDO") + "'\n"
					+ ",'retornado': '" + res.getString("RETORNADO") + "'\n"
					+ ",'incidencia': '" + res.getString("INCIDENCIA") + "'\n"
					+ ",'asignado': '" + res.getString("ASIGNADO") + "'\n"
					
					+ ",'nombre': '" + res.getString("NOMBRE") + ", " + res.getString("APELLIDOS") + "'\n"
					+ ",'telefono_fijo': '" + res.getString("TELEFONOF") + "'\n"
					+ ",'telefono_movil': '" + res.getString("TELEFONOM") + "'\n"
					+ ",'correo': '" + res.getString("CORREO") + "'\n"
					+ ",'domicilio': '" + (res.getString("CALLE") + " Num " + res.getString("NUMERO") + ", Col. " + res.getString("COLONIA") + ", " + res.getString("MUNDEL") + ", Edo. " + res.getString("ESTADO")).replaceAll("'", "").replaceAll("\"", "") + "'\n"
					/*
					+ ",'calle': '" + res.getString("CALLE") + "'\n"
					+ ",'numero': '" + res.getString("NUMERO") + "'\n"
					+ ",'colonia': '" + res.getString("COLONIA") + "'\n"
					+ ",'estado': '" + res.getString("ESTADO") + "'\n"
					+ ",'mundel': '" + res.getString("MUNDEL") + "'\n"*/
					+ ",'autogenerado': '" + res.getString("AUTOGENERADO") + "'\n"
					
					+ ",'url_boleto': '" + this.urlBoleto + "'\n"
					+ ",'referencia': '" + model.referencia + "'\n"
					+ ",'url_comprador': '" + model.URL_comprador + "'\n"
					+ "}"
					).replaceAll("'", "\"");
			}
			else
				return "{'found':'false'}".replaceAll("'", "\"");
		} catch (Exception e) { }

		return "-1";
	}
	
	private String urlBoleto = null;
	
	public void creaBoleto(String folio) throws IOException {
		this.urlBoleto = "";
		
		String uriBoleto = "/assets/img/boleto.png";
		String uriUploads = "/uploads/";
		
		ServletContext context = getServletContext();
		
		String pathBoleto = context.getRealPath(uriBoleto); // "C:/Anahuac/boleto.png"
		String pathUploads = context.getRealPath(uriUploads) + "\\";
	

		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File(pathBoleto));
		} catch (IOException e) { return; }

		//String key = "007625";
		int x = bufferedImage.getWidth() - 225;
		int y = 72;

        Graphics graphics = bufferedImage.getGraphics();
        graphics.setFont(new Font("Tahoma", Font.BOLD, 45));

        graphics.setColor(new Color(188, 157, 136));
		graphics.drawString(folio, x + 1, y + 1);
		graphics.setColor(new Color(176, 73, 4));
        graphics.drawString(folio, x, y);

		//ImageIO.write(bufferedImage, "png", new File("C:/Anahuac/" + key + ".png"));
        
        
        
        String key = Global.createRandomID();
		ImageIO.write(bufferedImage, "png", new File(pathUploads + key + ".png"));
		
		this.urlBoleto = "uploads/" + key + ".png";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private String print(String HTML, mBuscarBoletoElectronico model, int idSorteo, String folioBoleto) {
		model.setIdsorteo(idSorteo);
		ResultSet res = model.buscarComprador(folioBoleto);
		try
		{
			if(res.next())
			{
				int pkBoleto = res.getInt("PK_BOLETO");
				model.consultaDatos(pkBoleto);
				//creaBoleto(folioBoleto);
				HTML = HTML
						.replaceAll("<!--BOLETO-->", folioBoleto)
						.replaceFirst("<!--NOMBRE-->", res.getString("NOMBRE") + ", " + res.getString("APELLIDOS"))
						.replaceFirst("<!--CALLE-->", res.getString("CALLE"))
						.replaceFirst("<!--NUMERO-->", res.getString("NUMERO"))
						.replaceFirst("<!--DELMUN-->", res.getString("MUNDEL"))
						.replaceFirst("<!--ESTADO-->", res.getString("ESTADO"))
						.replaceFirst("<!--TEL_FIJO-->", res.getString("TELEFONOF"))
						.replaceFirst("<!--TEL_MOVIL-->", res.getString("TELEFONOM"))
						.replaceFirst("<!--CORREO-->", res.getString("CORREO"))
						.replaceFirst("<!--CUENTA_BANCARIA-->", model.cuenta_deposito)
						.replaceFirst("<!--REF_BANCARIA-->", model.referencia)
						//.replaceFirst("", )
						;

			}
			else{
				HTML = "<html><body>Comprador no encontrado</body></html>";
				/*
						.replaceFirst("<!--BOLETO-->", "-----")
						.replaceFirst("<!--NOMBRE-->", "-----")
						.replaceFirst("<!--CALLE-->", "-----")
						.replaceFirst("<!--NUMERO-->", "-----")
						.replaceFirst("<!--DELMUN-->", "-----")
						.replaceFirst("<!--ESTADO-->", "-----")
						.replaceFirst("<!--TEL_FIJO-->", "-----")
						.replaceFirst("<!--TEL_MOVIL-->", "-----")
						.replaceFirst("<!--CORREO-->", "-----")
						.replaceFirst("<!--CUENTA_BANCARIA-->", "-----")
						.replaceFirst("<!--REF_BANCARIA-->", "-----")
						;
						*/
			}
		} catch (Exception e) { }
		return HTML;
	}

}

