package com.sorteo.dashboard.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.core.SesionDatos;
import com.sorteo.dashboard.model.mMapa;
import com.sorteo.dashboard.model.mMapa.Estado;

/**
 * Servlet implementation class Mapa
 */
@WebServlet("/Mapa")
public class Mapa extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Mapa() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        System.out.println("________________________________________________ MAPA");
		
		mMapa model = new mMapa();
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		if(sesion == null)
			return;
		
		String HTML = "";
		String view = request.getParameter("view");
		if (view == null)
			view = "";
		
		switch(view) {
		case "getJSON":
			String cve_edo = request.getParameter("cve_edo");
			HTML = getJSON(model, cve_edo);
			break;
			
		default:
			
			HTML = loadFile("/WEB-INF/views/dashboard/mapa.html");
			
			String array = makeArray(model);
			
			String regex="//VALUES";
			HTML = HTML.replaceFirst("//VALUES", array);
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	/*
	private String loadFile(String fileName) {
		ServletContext context = getServletContext();
		Scanner scanner = null;
		String fullPath = context.getRealPath(fileName);
		
		try {
			scanner = new Scanner(new File(fullPath), "UTF-8");
			scanner.useDelimiter("\\Z");
			String str = scanner.next();
			scanner.close();
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	*/
	
	private String makeArray(mMapa model) {
		StringBuilder sb = new StringBuilder();

		HashMap<String, Integer> map = model.consultaBoletosVendidos();

		Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
		sb.append("var values=[");
		while (it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();

			//sb.append("['mx-3622', 0],");

			String idSepomex = pair.getKey();
			Integer boletos = pair.getValue();
			
			Estado edo = mMapa.Estado.buscaXIdSepomex(idSepomex);

			sb.append("['").append(edo.clave).append("',").append(boletos).append("],\n");
		}
		sb.append("];\n");
		return sb.toString();
	}

	private String loadFile(String fileName) {
		ServletContext context = getServletContext();
		
		try {
			String fullPath = context.getRealPath(fileName);
			Scanner scanner = new Scanner(new File(fullPath), "UTF-8").useDelimiter("\\Z");
			String content = scanner.next();
			scanner.close();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getJSON(mMapa model, String cve_edo) {
		
		String json = loadFile("/countries/mx/" + cve_edo + ".json");
		
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(json);

			JSONArray data =
					((JSONArray)
					((JSONObject)
					((JSONArray)
					obj
					).get(0)
					).get("data")
					);
			((JSONObject)((JSONArray)obj).get(0)).put("color", "#FFFFFF");
			
			((JSONObject)data.get(0)).put("color", "#770000");
			
			return obj.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "[\"result\":\"error\"]";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
}
