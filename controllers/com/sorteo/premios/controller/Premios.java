package com.sorteo.premios.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.core.Factory;
import com.core.SesionDatos;
import com.core.UploadFile;
import com.sorteo.premios.model.mPremios;

/**
 * Servlet implementation class Premios
 */
@WebServlet("/Premios")
public class Premios extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Premios() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Factory vista = new Factory();
		SesionDatos sesion;
		mPremios model = new mPremios();
		
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		int pg = 0;
		int show = 0;
		String search = "";

		String view = request.getParameter("view");
		
		if (!sesion.permisos.havePermiso(20099)) { view = "error"; }

		if (view == null) {
			view = "";
			if (request.getParameter("idsorteo")==null) {
				view = "ListaDePremios";
			}
		}
		System.out.println("view="+view);

		switch (view) {
		case "ListaDePremios":
			SorteoPredeterminado(request, response, model, sesion);
			break;

		case "Agregar":
			fullPath = "/WEB-INF/views/premios/agregar_premio.html";
			menu = vista.initMenu(0, false, 16, 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			model.setIdsorteo(sesion.pkSorteo);
			int next = model.consultaSiguienteNumeroPremio();
			System.out.println("  next=" + next);
			HTML = HTML.replaceAll("#NUM_PREMIO#", ""+next);
			break;

		case "Buscar":
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null)
				pg = 1;
			else
				pg = Integer.valueOf(request.getParameter("pg"));
			
			if (request.getParameter("show") == null)
				show = 50;
			else
				show = Integer.valueOf(request.getParameter("show"));
			
			search = request.getParameter("search");
			if (search == null)
				search = "";
			
			HTML = Buscar(request, response, pg, show, search, model, sesion);

			break;
			
		case "BuscarNumeroPremio":
			HTML = cuentaNumeroPremio(request, response, model, sesion);
			
			break;

		case "Borrar":
			borraPremio(request, response, model, sesion);
			
			return;
			//break;
			
		case "error":
			
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 16 : 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		default:
			
			int idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
			if (sesion.pkSorteo != idsorteo) {
				sesion.pkSorteo = idsorteo;
				sesion.guardaSesion();
			}

			fullPath = "/WEB-INF/views/premios/premios.html";
			menu = vista.initMenu(0, false, 16, 17, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			HTML = putCode(HTML, 20102,"#BTN_AGREGAR_PREMIO#", "",
					"<a class=\"btn btn-primary m-r-5\" href=\"Premios?view=Agregar\"><i class='fa fa-plus'></i> Agregar premio</a>",
					sesion);
			
			model.setIdsorteo(sesion.pkSorteo);
			model.consultaSorteo();
			HTML = HTML.replaceFirst("#SORTEO#", model.getSorteo());
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

	private String putCode(String HTML, int pkPermiso, String mark,
			String if_not_have, String if_have, SesionDatos sesion) {
		String str_put;
		if (sesion.permisos.havePermiso(pkPermiso))
			str_put = if_have;
		else
			str_put = if_not_have;
		return HTML.replaceAll(mark, str_put);
	}
	
	private void borraPremio(HttpServletRequest request,
			HttpServletResponse response, mPremios model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setIdsorteo(sesion.pkSorteo);
		int idPremio = Integer.valueOf(request.getParameter("idPremio"));
		model.borraPremio(idPremio, sesion);

		model.close();
		
		response.sendRedirect("Premios?idsorteo=" + model.getIdsorteo());
	}
	
	private String cuentaNumeroPremio(HttpServletRequest request,
			HttpServletResponse response, mPremios model, SesionDatos sesion) throws ServletException, IOException {
		model.setIdsorteo(sesion.pkSorteo);
		int numeroPremio = Integer.valueOf(request.getParameter("numeroPremio"));
		return "" + model.cuentaNumeroDePremio(numeroPremio);
	}
	
	private String Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mPremios model, SesionDatos sesion) {
		model.setIdsorteo(sesion.pkSorteo);
		int numeroregistros = model.contarPremios();
		return CrearTabla(numeroregistros, model.paginacion(pg, show, search), pg, show, model, sesion);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, mPremios model, SesionDatos sesion) {
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);
		StringBuilder sb = new StringBuilder();
		int count = 0;
		try {
			if (rs != null) {
			
				while (rs.next()) {
					int clasificacion = rs.getInt("CLASIFICACION");
					String caption = "";
					if (clasificacion == 1 || clasificacion == 3)
						caption = "" + clasificacion + "er PREMIO";
					else
						caption = "" + clasificacion + "&#176; PREMIO";
					
					sb
					.append("<div class=\"image gallery-group-").append(clasificacion).append("\">\n")
					.append(" <div class=\"image-inner\">\n")
					.append("  <a data-lightbox=\"gallery-group-").append(clasificacion).append("\" href=\"uploads/").append(rs.getString("IMAGEN").trim()).append("\"><img src=\"uploads/").append(rs.getString("IMAGEN").trim()).append("\" alt=\"\" /> </a>\n")
					.append("  <p class=\"image-caption\">").append(caption).append("</p>\n")
					.append(" </div>\n")
					.append(" <div class=\"image-info\">\n")
					// --- nombre
					.append("  <h5 class=\"title\">").append(rs.getString("NOMBRE").trim()).append("</h5>\n")
					// --- valor monetario
					.append("  <div class=\"pull-right\"><a href=\"javascript:;\">"+englishFormat.format(rs.getDouble("VALOR"))+"</a></div>\n");
					// --- estrellas
					int estrellas = rs.getInt("ESTRELLAS");
					sb.append("  <div class=\"rating\">");
					for (int i = 1; i <= 5; i++)
						if (i <= estrellas)
							sb.append(" <span class=\"star active\"></span>");
						else
							sb.append(" <span class=\"star\"></span>");
					// --- Descripcion
					sb.append("</div>\n  <div class=\"desc\">").append(rs.getString("DESCRIPCION").trim()).append("</div> <br/>\n");
					
					sb.append("  <div class=\"btn-row\">");
					if (sesion.permisos.havePermiso(20103)) { // permiso para borrar premios
						sb
						.append("<a data-title=\"Eliminar premio\" data-container=\"body\" data-toggle=\"tooltip\" href=\"javascript:showModal(").append(rs.getString("PK1")).append(");\"")
						.append(" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-times-circle\"></i><span style=\"font-size:11px;\">Borra premio</span></a>");
					}
					sb.append("  <div class=\"pull-right\"><span style=\"color:#336633;\">Num premio: ").append(rs.getString("NUM_PREMIO")).append("<span></div>\n");
					sb.append("</div>\n </div>\n</div>\n\n");
					count++;
				}
			}
		}catch (SQLException ex) { Factory.Error(ex, ""); }
		if (count == 0) {
			sb.setLength(0);
			sb.append("<ul class= \"result-list\">");
			sb.append("<li align= \"center\">");
			sb.append("  <div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">");
			sb.append("    <h1>No existen Premios</h1>");
			sb.append("    <p>Empieze por agregar un nuevo premio.</p>");
			sb.append("  </div>");
			sb.append("</li>");
			sb.append("</ul>");
		}

		return sb.toString();
	}

	protected void SorteoPredeterminado(HttpServletRequest request, HttpServletResponse response, mPremios model, SesionDatos sesion) throws ServletException, IOException {
		
		model.setIdUsuario(sesion.pkUsuario);
		model.consultaUsuarioSorteo();

		if (model.getIdsorteo() != 0) {
			response.sendRedirect("Premios?idsorteo=" + model.getIdsorteo());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO doPost()
		
		
		      mPremios model = new mPremios();
				
		      SesionDatos sesion;

		      Factory.prepareError(request);
				if ((sesion = SesionDatos.start(request, response, false, model)) == null)
					return;

		String accion = request.getParameter("accion");

		if ("insertar".equals(accion)) {
			
			model.setNombre(request.getParameter("nombre"));
			model.setNumeroPremio(request.getParameter("numero_premio"));
			model.setClasificacion(request.getParameter("clasificacion"));
			model.setEstrellas(request.getParameter("estrellas"));
			model.setValor(request.getParameter("valor"));
			model.setDescripcion(request.getParameter("descripcion"));
			model.setImagen(request.getParameter("imagen"));
			model.registrar(sesion);
		}
		/*
		else if (borrar.equals(request.getParameter("eliminar"))) {

			model.setId(Integer.parseInt(request.getParameter("sorteo")));
			model.eliminar(model, sesion);

		}*/
		/*
		else if (cargar.equals(request.getParameter("cargar"))) {

			model.setId(Integer.parseInt(request.getParameter("sorteo")));
			
			if(model.ExisteCarga(model))
				return;
			
			model.cargartalonarios(model, sesion);
			model.cargarboletos(model, sesion);
			model.setCarga(model, sesion);
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String boletosprocesados =  model.getNumTalonarios()+":"+model.getNumBoletos();
			out.println(boletosprocesados);
			
		}
		*/
		else {
			
			String fullPathinfouser = "/uploads/";
			String fullPathtemp = "/temp/";
			
			ServletContext context = getServletContext();
			
			String filePath = context.getRealPath(fullPathinfouser);
			String Pathtemp = context.getRealPath(fullPathtemp);
			
			UploadFile upload = new UploadFile();
			upload.uploadFile(request, Pathtemp, filePath);
		}
		
		model.close();
	}
	
}

