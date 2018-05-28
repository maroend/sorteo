package com.sorteo.herramientas.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.herramientas.model.mMenus;


/**
 * Servlet implementation class Menus
 */
@WebServlet("/Menus")
public class Menus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Menus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Factory vista = new Factory();
		mMenus model = new mMenus();
		SesionDatos sesion;
		
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
		
		//                       Permiso para editar Sectores en menu "Poblacion"
	//	boolean editarSectores = sesion.permisos.havePermiso(30117) && sesion.sorteoActivo;
		
		boolean editarSectores = true;
		
		String view = request.getParameter("view");
		
		if (view == null) {
			view = "";
		}
		switch (view) {		
       		
		
		case "Buscar":
			
			pg = 0; // pagina a mostrar
			if (request.getParameter("pg") == null) {
				pg = 1;
			} else {
				pg = Integer.valueOf(request.getParameter("pg"));
			}
			
			show = Integer.valueOf(request.getParameter("show"));
			search = request.getParameter("search");
			Buscar(request, response, pg, show, search, model, sesion, editarSectores);
			break;
			
		case "BuscarEditar":
			BuscarEditar(request, response, model);
			break;			
			
		case "Editar":
			Editar(request, response, model);
			break;
			
		case "Borrar":
			Borrar(request, response, model);
			break;			
	
			
		default:
			fullPath = "/WEB-INF/views/herramientas/menu.html";
			menu = vista.initMenu(0, false, 27, 42, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);			
			
			
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", editarSectores ? "display" : "none");
			
			String Padres = model.Obtener_Padre();
			HTML = HTML.replaceAll("#PADRES#", Padres);		
			
			
			String Permisos = model.Obtener_Permisos();
			HTML = HTML.replaceAll("#PERMISOS#", Permisos);		
			
			
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		
		SesionDatos sesion;
		mMenus model = new mMenus();
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		model.setIdSorteo(sesion.pkSorteo);
		
		String accion = request.getParameter("action").trim();	
		String INSERT = "INSERT";		
		
		if(accion.compareTo(INSERT) == 0 ){
			model.setNombre(request.getParameter("nombre"));
			model.setUrl(request.getParameter("url"));
			model.setPadre(Integer.parseInt(request.getParameter("padre")));
			model.setOrden(Integer.parseInt(request.getParameter("orden")));
			model.setPermiso(Integer.parseInt(request.getParameter("permiso")));
			model.setDisponible(Integer.parseInt(request.getParameter("disponible")));
			
		
		    model.registrar(model, sesion.pkUsuario);
		    model.close();
		}
		
	
		
	}
    
    
    
    
	protected void BuscarEditar(HttpServletRequest request,
			HttpServletResponse response, mMenus model) throws ServletException, IOException {
		
		model.setId(Integer.valueOf(request.getParameter("id")));
		model.BuscarEditar(model);
		
		String datos = model.getNombre() + "#%#"
				+ model.getUrl() + "#%#" + model.getPadre() + "#%#"
				+ model.getOrden()+ "#%#"+ model.getDisponible()+"#%#"+ model.getPermiso();
		
		PrintWriter out = response.getWriter();
		out.println(datos);
		
	}
	
	protected void Editar(HttpServletRequest request,
			HttpServletResponse response, mMenus model) throws ServletException, IOException {

		model.setId(Integer.valueOf(request.getParameter("id")));
		model.setNombre(request.getParameter("nombre"));
		model.setUrl(request.getParameter("url"));
		model.setPadre(Integer.parseInt(request.getParameter("padre")));
		model.setOrden(Integer.parseInt(request.getParameter("orden")));
		model.setPermiso(Integer.parseInt(request.getParameter("permiso")));
		model.setDisponible(Integer.parseInt(request.getParameter("disponible")));
		
		model.Editar(model);
	}
	
	protected void Borrar(HttpServletRequest request,
			HttpServletResponse response, mMenus model) throws ServletException, IOException {
		
		model.setId(Integer.valueOf(request.getParameter("id")));
		model.Borrar(model);
	}
	
	protected void Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mMenus model, SesionDatos sesion, boolean editarSectores) throws ServletException, IOException {
		
		String[] columnas = { "Id", "Titulo", "Url", "Padre" , "Orden", "Permiso","Disponible","Controles"};
		String[] campos = { "PK1", "MENU","URL" ,"PADRE","ORDEN","PK_PERMISO","DISPONIBLE"};
		
		if (editarSectores == false)
			columnas = Arrays.copyOfRange(columnas, 0, columnas.length - 1);
		
		model.setIdSorteo(sesion.pkSorteo);
		int numeroregistros = model.contar(search);
		String HTML = CrearTabla(
				numeroregistros, model.paginacion(pg, show, search),
				columnas, campos, pg, show, sesion, editarSectores);
		PrintWriter out = response.getWriter();
		out.println(HTML);
	}
	
	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas,
			String[] campos, int pg, int show, SesionDatos sesion, boolean editarSectores) {
		
		int i = (pg - 1) * show;
		ArrayList<String> campostable = new ArrayList<String>();
		String htmlcampo = "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"\" aria-label=\"Browser: activate to sort column ascending\">";
		
		for (String columna : columnas) {
			String campotable = htmlcampo + columna + "</th>";
			campostable.add(campotable);
		}
		
		String html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
		html += "<thead>";
		html += "<tr role=\"row\">";
		if(editarSectores)
			//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ><input type=\"checkbox\" onclick=\"seleccionarTodo('1t')\" id=\"checkboxall1t\" name=\"checkboxall\" /></th>";
		//html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		for (String columna : campostable) {
			
			html += columna;
		}
		html += " </thead>";
		html += " <tbody>";
		try {
			
			if (Integer.valueOf(numreg) > 0)
			{
				while (rs.next())
				{
					i++;
					
					html += "<tr class=\"gradeA odd\" role=\"row\">";
					if(editarSectores)
					//	html += "<td class=\"sorting_1\"><input type=\"checkbox\" id=\""+rs.getInt("PK1")+"\" /></td>";
					//html += "<td class=\"sorting_1\">" + i + "</td>";
					
					for (String campo : campos)
					{
						html += "<td class=\"sorting_1\">" + rs.getString(campo) + "</td>";
						
					}
					
					if(editarSectores)
					{
						html += "<td class=\"sorting_1\">";
						html += "<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"
							+ "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"							
							+ "<li><a href=\"javascript:BuscarEditar("
							+ rs.getString("PK1")
							+ ");\">Editar</a></li>"
							+ "<li><a href=\"javascript:Borrar("
							+ rs.getString("PK1")
							+ ");\">Borrar</a><li>"
							+ "<li class=\"divider\">"
							+ "</li><li>"
							+ "</ul></div>";
						
						html += "</td>";
					}
					
	
					//i++;
					html += "</tr>";
	
					System.out.println(rs.getString("MENU"));
	
				}
			} else {

				html += "<tr class=\"gradeA odd\" role=\"row\"><td colspan=\"7\">";

				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:100%; \">";

				html += "<h1>No existen Sectores</h1>";
				html += "<p>Empiece creando un nuevo sector.</p>";

				html += "</div>";

				html += "</td>";
				html += "</tr>";

			}
		} catch (SQLException e) {
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
	

}
