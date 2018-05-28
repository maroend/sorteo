package com.sorteo.conciliacion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.core.Factory;
import com.core.ParametersBase64;
import com.core.SesionDatos;
import com.sorteo.conciliacion.model.mVentaElectronica;
import com.sorteo.poblacion.model.mNichos;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/VentaElectronica")
public class VentaElectronica extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VentaElectronica() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mVentaElectronica model = new mVentaElectronica();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		String fullPath ="";
		
		String view = request.getParameter("view");
		if ( (!sesion.permisos.havePermiso(30126)) || (!sesion.sorteoActivo)){view = "errorCerrado";}
		
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		
		case "errorCerrado":
			fullPath = "/WEB-INF/views/error.html";
			menu = vista.initMenu(0, false, 10, 1038, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			break;

		case "makeNichosHTML":
			model.setIdSorteo(sesion.pkSorteo);
			HTML = makeNichosHTML(model);
			break;

		default:
			
			fullPath = "/WEB-INF/views/conciliacion/VentaElectronica.html";
			menu = vista.initMenu(0, false, 10, 1038, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			String sorteo=model.getNombreSorteo(sesion.pkSorteo);
			
			HTML = HTML.replaceFirst("#SORTEO#", sorteo);
			HTML = HTML.replaceFirst("__ID_SORTEO__", ""+sesion.pkSorteo);
			
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		mVentaElectronica model = new mVentaElectronica();
		SesionDatos sesion;
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			throw new ServletException("Error de sesion");
		String content = "";
		HashMap<String, ParametersBase64> map = ParametersBase64.parse(request.getParameter("q"));
		ParametersBase64 parameter;
		if (map.get("REGISTRAR") != null)
		{
			parameter = map.get("idsorteo");
			model.setIdSorteo(Integer.valueOf(parameter.value));
			model.setUsuario(sesion.nickName);
			model.RegistrarVentaFC4(sesion);
			content = (
					"{"
					+ " 'result': 'ok' "
					+ ", 'count_venta': '" + model.count_venta + "'"
					+ ", 'count_fc4': '" + model.count_fc4 + "'"
					+ ", 'count_max': '" + model._count_max + "'"
					+ ", 'count_error': '" + model._count_error + "'"
					+ "}")
					.replaceAll("'", "\"");
		}
		else if (map.get("LIMPIAR") != null)
		{
			parameter = map.get("idsorteo");
			model.setIdSorteo(Integer.valueOf(parameter.value));
			model.setUsuario(sesion.nickName);
			model.limpiarNoasignados(sesion);
			content = "ok";
		}
		else if (map.get("COMPLETAR_COMPRADORES") != null){
			parameter = map.get("idsorteo");
			model.setIdSorteo(Integer.valueOf(parameter.value));
			model.setUsuario(sesion.nickName);
			parameter = map.get("nichos");
			boolean ok = model.completarCompradores(sesion, parameter.arrayValues);
			content = "ok";
			content = (
					"{"
					+ " 'result': '" + (ok ? "ok" : "error") + "'"
					+ ", 'count_process': '" + model._count_process + "'"
					+ ", 'count_success': '" + model._count_success + "'"
					+ ", 'count_error': '" + model._count_error + "'"
					+ ", 'count_talonarios': '" + model.count_talonarios + "'"
					+ "}"
					).replaceAll("'", "\"");
		}
		else if (map.get("REFOLEAR") != null){
			int idSorteo = Integer.parseInt(map.get("idsorteo").value);
			model.setIdSorteo(idSorteo);
			int result = model.refolearBoletosElectronicos(sesion);
			
			content =(
					"{"
					+ " 'result': '" + (result==0?"ok":"error") + "'"
					//+ " 'msg': '" + ((model.count_process==model.count_max) ? "ok" : "error") + "'"
					+ ", 'count_process': '" + model._count_process + "'"
					+ ", 'count_max': '" + model._count_max + "'"
					+ "}"
					).replaceAll("'", "\"");
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(content);
		
		model.close();
	}
	
	protected String makeNichosHTML(mVentaElectronica model) {
		
		ArrayList<mNichos> list = model.getClaveNichos();
		StringBuilder sb = new StringBuilder();
		
		int contador = 0;
		int columnas = 2;
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
						: "fa fa-check-square-o";
				sb.append(
						"<a class='btn btn-white' style='width: 100%; text-align: left;' id='btn_nicho_")
						.append(contador).append("' onclick=\"marcarNicho(")
						.append(contador)//.append(",45,'").append(nicho.getId())
						.append(");\"><i id='icon_nicho_").append(contador)
						.append("' class='").append(style).append("' style='width:20px;'></i>")
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
			if (!first) sb.append(",");
			sb.append("{'id':'").append(nicho.getClave()).append("', 'checked':false}");
			//sb.append("[").append((nicho.getDescripcion().compareTo("0") == 0) ? "false" : "true").append(",").append(nicho.getClave()).append("]");
			first=false;
		}
		sb.append("];\n");
		sb.append("</script>\n");
		
		return sb.toString();
	}


}
