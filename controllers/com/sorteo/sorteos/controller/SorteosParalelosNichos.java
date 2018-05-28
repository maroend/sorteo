package com.sorteo.sorteos.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.core.Factory;
import com.core.Global;
import com.core.ParametersBase64;
import com.core.SesionDatos;
import com.sorteo.poblacion.model.mNichos;
import com.sorteo.poblacion.model.mSectores;
import com.sorteo.sorteos.model.mSorteosParalelos;
import com.sorteo.sorteos.model.mSorteosParalelosNichos;

/**
 * Servlet implementation class AsignacionNichos
 */
@WebServlet("/SorteosParalelosNichos")
public class SorteosParalelosNichos extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SorteosParalelosNichos() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Factory vista = new Factory();
		mSorteosParalelosNichos model = new mSorteosParalelosNichos();
		SesionDatos sesion;

		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;

		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		//String fullPathmenuprofile = "/WEB-INF/views/notificaciones.html";
		//String fullPathinfouser = "/WEB-INF/views/infouser.html";
		int pg = 0;
		int show = 0;
		String search = "";
		String url = "";
		String view = request.getParameter("view");
		
		//10109 ACCESO A LA LISTA DE NICHOS
		if (!sesion.permisos.havePermiso(10109)){view = "error";}
		else {
			if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){view = "errorCerrado";}
		}
		
		if (view == null)
			view = "";

		switch (view) {
			case "errorCerrado":
				fullPath = "/WEB-INF/views/error_sinpermiso.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
				break;

			case "Agregar":
				fullPath = "/WEB-INF/views/sorteos/agregar.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
				break;

			case "Buscar":
				pg = Integer.valueOf(request.getParameter("pg"));
				show = Integer.valueOf(request.getParameter("show"));
				search = request.getParameter("search");
				
				model.setIdSorteo(Integer.parseInt(request.getParameter("idsorteo")));
				model.setIdParalelo(Long.parseLong(request.getParameter("idparalelo")));
				Buscar(request, response, pg, show, search, model, sesion);
				break;

			case "makeSectoresHTML":
				model.setIdSorteo(sesion.pkSorteo);
				HTML = makeSectoresHTML(model);			
				break;
			
			case "makeNichosHTML":
				String aux = request.getParameter("idsector");
				int idSector = (aux == null) ? 0 : Integer.parseInt(aux);
	
				aux = request.getParameter("idparalelo");
				int idParalelo = (aux == null) ? 0 : Integer.parseInt(aux);
	
				//sesion.pkSector = idSector;
				model.setIdSorteo(sesion.pkSorteo);
				model.setIdSector(idSector);
				model.setIdParalelo(idParalelo);
				HTML = makeNichosHTML(model);
				break;

			case "error":
				 
				fullPath = "/WEB-INF/views/error.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
						infouser);		
				break;

			default:
				
				int idsorteo = Integer.valueOf(request.getParameter("idsorteo"));
				if (sesion.pkSorteo != idsorteo) {
					sesion.pkSorteo = idsorteo;
					sesion.guardaSesion();
					System.out.println("<SORTEO guardado>");
				}
				
				int idparalelo = Integer.valueOf(request.getParameter("idparalelo"));
	
				fullPath = "/WEB-INF/views/sorteos/SorteosParalelos/SorteosParalelosNichos.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
				
				HTML = HTML.replaceAll("#SORTEO#", model.consultaSorteo(idsorteo));
				HTML = HTML.replaceAll("#ID_SORTEO#", "" + idsorteo);
				
				HTML = HTML.replaceAll("#PARALELO#", model.consultaParalelo(idparalelo));
				HTML = HTML.replaceAll("#ID_PARALELO#", "" + idparalelo);
				
			    url = "SorteosParalelos?idsorteo=" + idsorteo;
				HTML = HTML.replaceAll("#URL_SORTEOS_PARALELOS#", url);
	
			    url = "SorteosParalelosNichos?idsorteo="+idsorteo+"&idparalelo="+idparalelo;
				HTML = HTML.replaceAll("#URL_SORTEOS_PARALELOS_NICHOS#", url);
				
				//PERMISO BOTON
				String boton = "";
				
				if (sesion.sorteoActivo && sesion.permisos.havePermiso(10123)){
					boton = "<a class='btn btn-primary m-r-5' href='#' onclick=\"modalEditarParalelo(" + idparalelo + ")\">Asignar Nichos</a>";	
				}
				
				HTML = HTML.replaceAll( "#BTNASIGNAR#", boton);
				
				String opcionboton ="<a href=\"#\"  disabled=\"disabled\"  "
					    		+ "style=\"width: 100%;\" id=\"btnasignacionn\" role=\"button\" class=\"btn btn-success btn-lg\"> "
					    		+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC2 - ASIGNACI&Oacute;N DE TALONARIOS COMPLETOS O INCOMPLETOS</a>";
				
				HTML = HTML.replaceAll( "#OPCIONFC2#", opcionboton);
				
				opcionboton = "<a href=\"#\" "
								+ "style=\"width: 100%;\" id=\"btndeletecargaboletos\" disabled=\"disabled\" role=\"button\" class=\"btn btn-success btn-lg\">"
								+"<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Asignaci&oacute;n de Boletos</a>";
				
				opcionboton = "<a href=\"()\" style=\"width: 100%;\"	disabled=\"disabled\" "
								+ "id=\"btneliminarsorteo\" role=\"button\" class=\"btn btn-success btn-lg\">"
								+ " <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Nicho</a>";
				break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO doPost

		mSorteosParalelosNichos model = new mSorteosParalelosNichos();
		SesionDatos sesion;
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			throw new ServletException("Error de sesion");
		
		HashMap<String, ParametersBase64> map = ParametersBase64.parse(request.getParameter("q"));
		ParametersBase64 parameter;

		if ((parameter = map.get("GUARDAR")) != null) {
			
			model.setIdSorteo(sesion.pkSorteo);
			
			parameter = map.get("idparalelo");
			model.setIdParalelo(Integer.parseInt(parameter.value));
			
			parameter = map.get("idsector");
			model.setIdSector(Integer.parseInt(parameter.value));
			
			parameter = map.get("nichos");			
			model.guardarNichosAsignados_(parameter.arrayValues, sesion);
			
			//model.guardarNichosAsignados(parameter.arrayValues, sesion);
			//model.guardarNichosAsignados2(parameter.value,parameter.arrayValues, sesion);
		}	
		
		
	if ((parameter = map.get("EJECUTAR")) != null) {
			
			model.setIdSorteo(sesion.pkSorteo);
			
			parameter = map.get("idparalelo");
			model.setIdParalelo(Integer.parseInt(parameter.value));
			
			parameter = map.get("flag");		
			model.EjecutarSPNichos(parameter.value,sesion);
			
			//model.guardarNichosAsignados(parameter.arrayValues, sesion);
			//model.guardarNichosAsignados2(parameter.value,parameter.arrayValues, sesion);
		}	
		
		
         if ((parameter = map.get("ACTUALIZAR")) != null) {
			
			model.setIdSorteo(sesion.pkSorteo);
			
			parameter = map.get("idparalelo");		
			model.setIdParalelo(Integer.parseInt(parameter.value));
			
			parameter = map.get("idparalelonicho");
			model.setIdParaleloNicho(Integer.parseInt(parameter.value));
			
			
			parameter = map.get("idsector");
			model.setIdSector(Integer.parseInt(parameter.value));
			
			
			parameter = map.get("nichos");
			model.ActualizarColaboradores_SP(parameter.arrayValues, sesion);
			
			//model.ActualizarColaboradores_SP(sesion);
		}	
         
         
		
		
		else if ((parameter = map.get("DELETE")) != null)
		{
			if (NumberUtils.isDigits(parameter.value))
			{
			
			  /* parameter = map.get("idparalelo");
			   model.setIdParalelo(Long.parseLong(parameter.value));			   
			  
			   parameter = map.get("idparaleloNicho");
			   model.setIdParaleloNicho(Long.parseLong(parameter.value));*/			
			
				model.setIdParalelo(Long.parseLong(parameter.value));				
				String valida = "";
				
				if(model.getExistParaleloColaboradores()){						
					valida = "EXISTECOLABO";					
				}else{					
					model.borrarParaleloNichos();
					valida = "NOEXISTECOLABO";					
				}
				
				PrintWriter out = response.getWriter();				
				out.println(valida);
				out.flush();
				out.close();				
				
			}
		}
		model.close();
	}

	protected void Buscar(HttpServletRequest request, HttpServletResponse response, int pg, int show, String search
			, mSorteosParalelosNichos model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub

		int numreg = model.contar(search);

		String HTML = CrearTabla(numreg,
				model.paginacion(pg, show,search),
				pg, show, model, sesion);
		
		
		//checa si existen colaboradores en sorteo paralelos, si existe desabilitara boton
		
		String existe ="NOEXISTE"; 
		if(model.getExistColaboradores()){existe ="EXISTE";}
		
		
		PrintWriter out = response.getWriter();
		HTML = HTML+"#%#"+numreg+"#%#"+existe+"";
		out.println(HTML);
		out.flush();
		out.close();

		
		
	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show,
			mSorteosParalelosNichos model, SesionDatos sesion) {

		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";

		int numerotalonarios = 0;
		int numeroboletos = 0;
		double monto = 0;
		Locale english = new Locale("en", "US");
		NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

		String Str = new String(filePath);

		filePath = Str.replace('\\', '/');

		String html = "<ul class=\"result-list\">";

		try {
			if (Integer.valueOf(numreg) > 0) {
				while (rs.next()) {
					html += "<li id=\"nicho" + rs.getString("PK1") + "\"  style='"+(sesion.sorteoActivo?"":Global.bkground_cerrado)+"'>";
					html += "<div class=\"result-info\">";
					html += "<h4 class=\"title\">"
							+ " <span class=\"label label-primary\">" + rs.getString("N_CLAVE") + "</span>"
							+ " <span>"+ rs.getString("NICHO") + " <small>("+rs.getString("SECTOR")+")</small>" + "</span>"
							+ "</h4>";
					html += "<p class=\"desc\">";
					html += "</p>";
					html += "<div class=\"btn-row\">";
					html += "<a data-title=\"Eliminar\" data-container=\"body\" data-toggle=\"tooltip\" href='#' onclick=\"borrarParalelosNichos(" + rs.getString("PK1") + ",'" + rs.getString("NICHO") + "','" + rs.getString("PK_SECTOR")+ "'," + rs.getString("PK_NICHO")+");\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-trash-o\"></i><span style=\"font-size:11px;\">Eliminar</span></a>";
					//html += "<a data-title=\"Actualizar\" data-container=\"body\" data-toggle=\"tooltip\" href='#' onclick=\"ActualizarColaboradores_SPNichos(" + rs.getString("PK1") + ",'" + rs.getString("NICHO") + "','" + rs.getString("PK_SECTOR")+ "'," + rs.getString("PK_NICHO")+");\" data-original-title=\"\" title=\"\"><i class=\"fa fa-cloud-upload\"></i><span style=\"font-size:11px;\"> Actualizar</span></a>";
					html += "<a data-title=\"Revise que Colaboradores estan asignados en este sorteo\" data-container=\"body\" data-toggle=\"tooltip\" href=\"SorteosParalelosColaboradores?idsorteo="+model.getIdSorteo()+"&idparalelo="+model.getIdParalelo()+"&idnicho=" + rs.getString("PK_NICHO") + "&idsector=" + rs.getString("PK_SECTOR") +"\" data-original-title=\"\" title=\"\"><i class=\"fa fa-fw fa-user\"></i><span style=\"font-size:11px;\">Colaboladores</span></a>";
					
					html += "</div>";
					html += "</div>";
					html += "<div class=\"result-price\">";
					html += "</div>";
					html += "</li>";
				}
				
				html += "</ul>";
			} else {
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";
				html += "<h1>No existen Nichos Asignados</h1>";
				html += "<p>Asigne Nichos al sorteo paralelo.</p>";
				html += "</div>";
				html += "</ul>";
			}
		} catch (SQLException e) { Factory.Error(e, "rs=" + rs); }

		String paginado = Factory.paginado_2(numreg, show, pg);

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";
		html = paginado + html + paginadoright;
		html = html + "<input id='numreg_id' type='hidden' value='"+numreg+"' >";
		return html;
	}
	
	private String makeSectoresHTML(mSorteosParalelosNichos model)
	{
		ArrayList<mSectores> list = mSectores.getListSectores(model.getIdSorteo(), model);
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (mSectores sector : list) {
			sb
				.append("<option value='").append(sector.getId()).append("'").append(first?" selected":"").append(">")
				.append(sector.getClave()).append(" - ").append(sector.getSector())
				.append("</option>\n");
			first = false;
		}
		return sb.toString();
	}
	
	private String makeNichosHTML(mSorteosParalelosNichos model)
	{
		ArrayList<mNichos> list = model.getNichosPorSector();
		StringBuilder sb = new StringBuilder();
		
		int contador = 0;
		int columnas = 1;
		for (int k = 0; k < columnas; k++)
		{
			int lim_1 = ((int)Math.ceil(list.size() / (double)columnas)) * k;
			int lim_2 = ((int)Math.ceil(list.size() / (double)columnas)) * (k + 1);
			if (lim_2 > list.size())
				lim_2 = list.size();
			
			sb.append("<div class='btn m-r-5' style='vertical-align: top;'>\n");
			for (int i = lim_1; i < lim_2; i++)
			{
				mNichos nicho = list.get(contador);
				String style = (nicho.getDato1().compareTo("0") == 0)
						? "fa fa-square-o" 
						: "fa fa-check-square";//fa fa-check-square-o		
				
				
				sb.append(
						"<a class='btn btn-white' style='width: 100%; text-align: left;' id='btn_nicho_")
						.append(contador).append("' onclick=\"marcarNicho(")
						.append(contador)//.append(",45,'").append(nicho.getId())
						.append(");\"><i id='icon_nicho_").append(contador).append("' ")					
						.append(" class='").append(style).append("' style='width:20px;'></i>")
						.append(nicho.getClave()).append(" - ")
						.append(StringEscapeUtils.escapeHtml4(nicho.getNicho())).append("</a><br/>\n");
				contador++;
			}
			sb.append("</div>\n");
		}

		// SCRIPT
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("arrayCheckNichos = [");
		boolean first=true;
		for (mNichos nicho : list) {
			if (first)
				first=false;
			else
				sb.append(",");
			sb.append("[").append((nicho.getDato1().compareTo("0") == 0) ? "false" : "false").append(",").append(nicho.getId()).append("]");
		}
		sb.append("];\n");
		sb.append("</script>\n");
		return sb.toString();
	}
	
}
