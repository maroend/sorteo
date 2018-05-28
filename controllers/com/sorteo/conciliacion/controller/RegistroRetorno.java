package com.sorteo.conciliacion.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.poblacion.model.mColaboradores;

/**
 * Servlet implementation class Colaboradores
 */
@WebServlet("/RegistroRetorno")
public class RegistroRetorno extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroRetorno() {
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
		
		mColaboradores model = new mColaboradores();
		Factory vista = new Factory();
		SesionDatos sesion;
		
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		ServletContext context = getServletContext();
		
		String menu = "";
		String notificaciones = "";
		String infouser = "";
		String HTML = "";
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		
		switch (view) {
		/*
		case "Buscar":
			int pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
			
			int sector = 0;
			if(request.getParameter("idsectorb")!=null&&request.getParameter("idsectorb")!=""){
				sector = Integer.valueOf(request.getParameter("idsectorb"));}
			
			int nicho = 0;
			if(request.getParameter("idnichob")!=null&&request.getParameter("idnichob")!="")
				nicho = Integer.valueOf(request.getParameter("idnichob"));
			
			Buscar(request, response, pg, show, search, sector, nicho, model, sesion);
			break;

		case "BuscarSector":
			BuscarSector(request, response, model, sesion);
			break;
			
		case "buscarNichosSector":
			buscarNichosSector(request, response, model);
			break;
			
		case "BuscarSectorInicio":			
			BuscarSectorInicio(request, response, model, sesion);
			break;
		
		case "buscarNichosSectorInicio":
			buscarNichosSectorInicio(request, response, model);
			break;

		case "BuscarEditar":
			BuscarEditar(request, response, model);
			break;
			
		case "getDireccion":
			getDireccion(request, response, model);
			break;
			
		case "Editar":
			Editar(request, response, model, sesion);
			break;
			
		case "actualizarComision":
			actualizarComision(request, response, model);
			break;
			
		case "Borrar":
			Borrar(request, response, model, sesion);
			break;
			*/
		default:
			
			String fullPath = "/WEB-INF/views/conciliacion/RegistroRetorno.html";
			menu = vista.initMenu(0, false, 10, 110, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdSorteo((int)sesion.pkSorteo);
			model.setIdUsuario((int)sesion.pkUsuario);
			
			model.consultaPoblacionUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getIdSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( (int)sesion.pkUsuario));
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", sesion.sorteoActivo ? "display" : "none");
			
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
	
	/*
	protected void actualizarComision(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")) );		
		model.setComision(Integer.valueOf(request.getParameter("comision")));			
		model.actualizarComision(model);
	}
	*/
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model) throws ServletException, IOException {
		/*
		// TODO Auto-generated method stub
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
		model.setClave(request.getParameter("clave"));
		// model.setColaborador(request.getParameter("colaborador"));
		model.setDescripcion(request.getParameter("descripcion"));
		model.setRbancaria(request.getParameter("rbancaria"));
		
		model.setNombre(request.getParameter("nombre"));
		model.setApellidop(request.getParameter("apaterno"));
		model.setApellidom(request.getParameter("amaterno"));
		model.setEdad(request.getParameter("edad"));
		model.setRfc(request.getParameter("rfc"));
		
		// TELEFONOS
		model.setTelefonocasa(request.getParameter("telefonocasa"));
		model.setTelefonooficina(request.getParameter("telefonooficina"));
		model.setTelefonomovil(request.getParameter("telefonomovil"));
		
		// DIRECCION
		model.setCp(Integer.valueOf(request.getParameter("cp")));
		model.setPais(request.getParameter("pais"));
		model.setEstado(request.getParameter("estado"));
		model.setMundel(request.getParameter("delmun"));
		model.setColonia(request.getParameter("colonia"));
		model.setCalle(request.getParameter("calle"));
		model.setNumExterior(request.getParameter("numero"));
		model.setNumInterior("0");
		
		// CORREO
		model.setCorreopersonal(request.getParameter("correopersonal"));
		model.setCorreotrabajo(request.getParameter("correotrabajo"));
		model.setCorreootro(request.getParameter("correootro"));
		
		// REDES SOCIALES
		model.setFacebook(request.getParameter("facebook"));
		model.setTwitter(request.getParameter("twitter"));
		model.setRedotro(request.getParameter("otrared"));
		// SECTOR INTERNO O EXTERNO ?
		model.setSect(request.getParameter("sec").charAt(0));
		
		String valida = "editar";
		
		if (valida.equals(request.getParameter("accion"))) {
			System.out.println("editar");
			
			model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
			
			model.Editar(model, sesion);
			
		}else{
			System.out.println("guardar");
			model.registrar(model, sesion);
		}
		
		model.close();
		*/
	}
	/*
	protected void BuscarEditar(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		model.BuscarEditar(model);
		model.getSectorNicho(model);
		
		//COLABORADOR

		
		System.out.println(">>>idNicho "+model.getNicho());
		
		//COLABORADOR   
		String datos = model.getId()+"#%#"+model.getClave()+"#%#"+model.getNombre()+"#%#"+model.getApellidop()+"#%#"+model.getApellidom()
		+"#%#"+model.getRfc()+"#%#"+model.getEdad()+"#%#"+model.getSect()+"#%#"+model.getDescripcion()+"#%#"+model.getSector()+"#%#"+model.getNicho()+"#%#"+model.getRbancaria();
		
		//TELEFONOS
		model.ObtenerTelefono(model);
		datos += "#%#"+model.getTelefonocasa()+"#%#"+model.getTelefonooficina()+"#%#"+model.getTelefonomovil();
		
		//DIRECCION
		model.ObtenerDireccion(model);
		// base de datos
		String colonias = model.Obtener_Colonia();// colonia de sepomex
		
		datos += "#%#" + model.getPais() + "#%#" + model.getEstado() + "#%#"
				+ model.getMundel() + "#%#" + colonias + "#%#"
				+ model.getCalle() + "#%#" + model.getNumExterior() + "#%#"
				+ model.getCp() + "#%#" + model.getColonia();
		
		// CORREOS
		model.ObtenerCorreo(model);
		datos += "#%#" + model.getCorreopersonal() + "#%#"
				+ model.getCorreotrabajo() + "#%#" + model.getCorreootro();
		
		//REDES
		model.ObtenerRedes(model);
		datos += "#%#"+model.getFacebook()+"#%#"+model.getTwitter()+"#%#"+model.getRedotro()+"#%#"+model.getEdad()+"#%#"+model.getRfc();
		
		model.init();	
		
		PrintWriter out = response.getWriter();
		out.println(datos);
	}
	
	protected void getDireccion(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setCp(Integer.valueOf(request.getParameter("cp")));
		
		model.Obtener_Direccion(model);
		
		String direccion = model.getPais() + "#%#" + model.getEstado() + "#%#"
				+ model.getMundel() + "#%#" + model.getColonia();
		
		PrintWriter out = response.getWriter();
		out.println(direccion);
		
	}
	
	protected void Editar(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		model.setNicho(Integer.valueOf(request.getParameter("idnicho")));
		model.setClave(request.getParameter("clave"));
		model.setColaborador(request.getParameter("colaborador"));
		model.setDescripcion(request.getParameter("descripcion"));

		model.Editar(model, sesion);

	}

	protected void Borrar(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idcolaborador")));
		String[] colaboradores = new String[]{"" + model.getId()};
		model.BorrarColaboradores(colaboradores, sesion);

	}

	protected void BuscarSector(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		
		   ResultSet rs = null;
		   String panelcontent = "";
		
		   model.setIdsorteo(sesion.pkSorteo);
		   model.setIdusuario(Integer.valueOf( request.getParameter("usuario")));        
		   model.setSector(Integer.parseInt(request.getParameter("idsectorU"))); 		
		   
		   
		if(model.isAdministrador()){
			
		//	panelcontent += "<option value=\"\">Todos</option> ";
			panelcontent += "<option value=\"S\">Seleccionar Sector</option> ";

			 rs = model.getSectores();			
			
		} else{
			
			 rs = model.getSectoresUsuario(model);				
			
		}  		

	

		try {
			

			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("SECTOR") + "</option>";

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	
	
	protected void BuscarSectorInicio(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model, SesionDatos sesion) throws ServletException, IOException {
		
		ResultSet rs = null;
		String panelcontent = "";
		
		model.setIdsorteo(sesion.pkSorteo);
		model.setIdusuario(Integer.valueOf(request.getParameter("usuario")));
		model.setSector(Integer.parseInt(request.getParameter("idsectorU")));
		
		if (model.isAdministrador()){
			
			panelcontent += "<option value=\"\">Todos</option> ";
			rs = model.getSectores();			
		} else{
			rs = model.getSectoresUsuario(model);
		}
		
		try {
			while (rs.next()) {
				
				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";
				panelcontent += ">" + rs.getString("SECTOR") + "</option>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);
		
	}
	
	protected void buscarNichosSector(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model) throws ServletException, IOException {

		model.setSector(Integer.valueOf(request.getParameter("idsector")));

		ResultSet rs = model.getNichos(Integer.valueOf(request
				.getParameter("idsector")));

		String panelcontent = "";

		try {
			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("NICHO") + "</option>";

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	               
	protected void buscarNichosSectorInicio(HttpServletRequest request,
			HttpServletResponse response, mColaboradores model) throws ServletException, IOException {

		model.setSector(Integer.valueOf(request.getParameter("idsector")));

		ResultSet rs = model.getNichos(Integer.valueOf(request.getParameter("idsector")));

		String panelcontent = "";

		try {
			
			panelcontent += "<option value=\"\">Todos</option> ";
			
			while (rs.next()) {

				panelcontent += "<option value=\"" + rs.getInt("PK1") + "\"";

				panelcontent += ">" + rs.getString("NICHO") + "</option>";

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(panelcontent);

	}
	
	
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			int sector, int nicho, mColaboradores model, SesionDatos sesion) throws ServletException, IOException {

		String[] columnas = { "Clave", "Nombre", "Sector", "Nicho", "Ref. Bancaria","Comisi&oacute;n(%)", "Controles"};
		String[] campos   = { "CLAVE", "NOM_COLABORADOR", "NOM_SECTOR", "NOM_NICHO", "REFBANCARIA","COMISION" };

		if (sesion.sorteoActivo == false)
			columnas = Arrays.copyOfRange(columnas,0,6);

		int numeroregistros = model.contar(search, sector, nicho, sesion);

		String HTML = CrearTabla(numeroregistros,
				model.paginacion(pg, show, search, sector, nicho, sesion),
				columnas, campos, pg, show, sesion);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";

		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}

		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";

		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" /></th>";
		html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";

		for (String columna : campostable) {

			html += columna;
		}

		html += " </thead>";
		html += " <tbody>";

		try {
			
			if (Integer.valueOf(numreg) > 0)
			{
				NumberFormat formatter = new DecimalFormat("#0.0000");
				
				while (rs.next()) {
	
					i++;
	
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					html += "<td class=\"sorting_1\"><input type=\"checkbox\" /></td>";
					html += "<td class=\"sorting_1\">" + i + "</td>";
					for (String campo : campos)
					{
						String str_campo = rs.getString(campo);
						if (campo=="COMISION"){
							str_campo = formatter.format(rs.getDouble(campo));
						}
						
						html += "<td class=\"sorting_1\">";
						if (str_campo==null) { html += "N/A"; }
						else { html += str_campo; }
						html += "</td>";
					}
	
					if (sesion.sorteoActivo)
					{
						html += "<td class=\"sorting_1\">";
						html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"
		
								+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
								+ "<li><a href=\"javascript:Comision("+ rs.getString("PK1") + ");\">Comisión</a></li>"
								+ "<li><a href=\"javascript:BuscarEditar(" + rs.getString("PK1")	+ ");\">Editar</a></li>"
								+ "<li><a href=\"javascript:Borrar(" + rs.getString("PK1") + ");\">Borrar</a><li>"
								+ "<li class=\"divider\">"
								+ "</li><li>"
								+ "</ul></div>";
		
						html += "</td>";
					}
	
					i++;
					html += "</tr>";
	
				}
			} else {

				html += "<tr class=\"gradeA odd\" role=\"row\"><td colspan=\"9\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

				html += "<h1>No existen Colaboradores</h1>";
				html += "<p>Empiece creando un nuevo colaborador.</p>";

				html += "</div>";

				html += "</td>";
				html += "</tr>";

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		html += "</tbody>";
		html += "</table>";

		int numpag = Math.round(numreg / show);
		int denumpag = numpag + 1;

		String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando "
				+ pg
				+ " de "
				+ denumpag
				+ " total "
				+ numreg
				+ " elementos</div>";
		paginado += "<div class=\"dataTables_paginate paging_simple_numbers\" id=\"data-table_paginate\">";

		if (pg > 1) {
			// <a href="?pg=<%=pg-1 %>">Anterior</a>

			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPag("
					+ pagante
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

		} else {

			paginado += "<a class=\"paginate_button previous disabled\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

		}

		paginado += "<span>";

		// Calcular el inicio del arreglo
		  int inipg = 0;
		  int r = (pg-1) % 5;
		  int sumpag = 0;

		  if (r == 0) {

		   inipg = pg - 1;
		  } else {
		   inipg = ((pg - 1) / 5) * 5;
		  }

		  for (int j = inipg; j < 5 + inipg; j++) {
			if (j < numpag + 1) {

				sumpag = j + 1;

				if (sumpag == pg) {
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPag("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}

			}
		}

		paginado += "</span>";

		if (pg <= numpag) {
			int numeropag = pg + 1;

			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPag("
					+ numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";

		html = paginado + html;

		return html;

	}
	*/

}
