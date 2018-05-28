package com.sorteo.sorteos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.core.Factory;
import com.core.Global;
import com.core.ParametersBase64;
import com.core.SesionDatos;
import com.sorteo.poblacion.model.mNichos;
import com.sorteo.poblacion.model.mSectores;
import com.sorteo.sorteos.model.mBoletosSorteosParalelos;
import com.sorteo.sorteos.model.mSorteosParalelos;

/**
 * Servlet implementation class AsignacionSectores
 */
@WebServlet("/SorteosParalelos")
public class SorteosParalelos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SorteosParalelos() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Factory vista = new Factory();
		mSorteosParalelos model = new mSorteosParalelos();
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
		String url = "";

		String view = request.getParameter("view");
		//System.out.println("\\o/ -> Vamos a ver a que vista entramos: " + view);
		
		//10109 ACCESO A LA LISTA DE SECTOR
		if (!sesion.permisos.havePermiso(10095)){view = "error";}
		else {
			if ( (!sesion.permisos.havePermiso(30110)) && (!sesion.sorteoActivo)){view = "errorCerrado";}
		}

		if (view == null) {
			view = "";
		}

		switch (view) {
			case "errorCerrado":
				fullPath = "/WEB-INF/views/error_sinpermiso.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
				break;
			case "editarParalelo":
				model.setIdParalelo(Long.parseLong(request.getParameter("idSorteoParalelo")));
				HTML = model.consultarSorteoParalelo();//"NOMBRE_PARALELO:" + model.getNombre() /*+ " (" + model.getZona() + ")"*/;			
				break;
				/*
			case "Agregar":
				fullPath = "/WEB-INF/views/sorteos/agregar.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context,
						sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones,
						infouser);
				break;
				*/
			case "Buscar":
				int pg; // pagina a mostrar
				if (request.getParameter("pg") == null) {
					pg = 1;
				} else {
					pg = Integer.valueOf(request.getParameter("pg"));
				}
				sesion.pkSorteo = Integer.parseInt(request.getParameter("idsorteo"));
	
				int show = Integer.valueOf(request.getParameter("show"));
				String search = request.getParameter("search");
				HTML = Buscar(request, response, pg, show, search, model, sesion);
				break;
			
			case "error":
				fullPath = "/WEB-INF/views/error.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto, sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
				break;
				
			case "exportToExcel":		
				
				try {				
				
					 export(request, response, sesion);	
					
					//HttpSession sesion2 = request.getSession();
					//sesion2.setAttribute("nombre_variable", "edgar"); 
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			//	HTML = HTML.replaceAll("", "");	
				
				
				//break;
				return;	
				
				
			default:
				String str_idsorteo = request.getParameter("idsorteo");
				if (str_idsorteo != null) {
					int idsorteo = Integer.valueOf(str_idsorteo);
					if (sesion.pkSorteo != idsorteo) {
						sesion.pkSorteo = idsorteo;
						sesion.guardaSesion();
					}
				}
			
				fullPath = "/WEB-INF/views/sorteos/SorteosParalelos/SorteosParalelos.html";
				menu = vista.initMenu(0, false, 2, sesion.misSorteos==0 ? 34 : 33, sesion);
				notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
				infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
				HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
				
				HTML = HTML.replaceAll("#SORTEO#", model.consultaSorteo(sesion.pkSorteo));
				HTML = HTML.replaceAll("#ID_SORTEO#", "" + sesion.pkSorteo);
				
			    url = "SorteosParalelos?idsorteo=" + sesion.pkSorteo;
				HTML = HTML.replaceAll("#URL_SORTEOS_PARALELOS#", url);
			
				//PERMISO BOTON
				String boton = "";
				
				if (sesion.sorteoActivo && sesion.permisos.havePermiso(10103)) {
					boton = "<a class='btn btn-primary m-r-5' href='#' onclick='modalAgregarParalelo();'>Agregar sorteo paralelo</a>\n";	
				}
				HTML = HTML.replaceAll( "#BTN_AGREGAR_MINI_SORTEO#", boton);
			
				//
				boton = "";
				//boton += "<a class='btn btn-primary m-r-5' href='#' onclick=''><i class='fa fa-plus'></i> Agregar Zona</a>\n";
				//boton += "<a class='btn btn-default m-r-5' href='#' onclick=''>Zona 1</a>\n";
				//boton += "<a class='btn btn-warning m-r-5' href='#' onclick=''>Zona 2</a>\n";
				HTML = HTML.replaceAll( "#BTN_AGREGAR_ZONA#", boton);
			
				//
				String opcionboton = "<a href=\"#\" disabled=\"disabled\" "
					    		+ "style=\"width: 100%;\" id=\"btncargaboletosfc1\" role=\"button\" class=\"btn btn-success btn-lg\"> "
					    		+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> FC1 - Distribuir Talonarios Completos</a>";
				 
				if (sesion.permisos.havePermiso(10120)) {
					opcionboton = "<a href=\"javascript:asignarboletosfc1()\" "
							+ "style=\"width: 100%;\" id=\"btncargaboletosfc1\" role=\"button\" class=\"btn btn-success btn-lg\"> "
							+ "<i class=\"fa fa-2x fa-barcode\"></i> FC1 - Distribuir Talonarios Completos</a>";
				}
				HTML = HTML.replaceAll("#OPCIONFC1#", opcionboton);

				opcionboton = "<a href=\"#\"  disabled=\"disabled\" "
						+ "style=\"width: 100%;\" id=\"btndeletecargaboletos\"	role=\"button\" class=\"btn btn-success btn-lg\">"
						+ "<i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Asignación de Talonarios y Boletos	</a>";
	
				if (sesion.permisos.havePermiso(10121)) {
	
					opcionboton = "<a href=\"javascript:showFormatoElimina()\" "
							+ "style=\"width: 100%;\" id=\"btndeletecargaboletos\"	role=\"button\" class=\"btn btn-success btn-lg\">"
							+ "<i class=\"fa fa-2x fa-history\"></i> Eliminar Asignaci&oacute;n de Talonarios y Boletos	</a>";
				}
				HTML = HTML.replaceAll("#OPCIONELIMINAR#", opcionboton);

				opcionboton = "<a href=\"javascript:#\"  disabled=\"disabled\" style=\"width: 100%;\"	"
						+ "id=\"btneliminarsorteo\" role=\"button\" class=\"btn btn-success btn-lg\">"
						+ " <i class=\"fa fa-ban fa-stack-2x text-danger\"></i> Eliminar Sector</a>";
	
				if (sesion.permisos.havePermiso(10122)) {
	
					opcionboton = "<a href=\"javascript:showFormatoEliminaSector()\" style=\"width: 100%;\"	"
							+ "id=\"btneliminarsorteo\" role=\"button\" class=\"btn btn-success btn-lg\">"
							+ " <i class=\"fa fa-2x fa-times\"></i> Eliminar Sector</a>";
				}
				HTML = HTML.replaceAll("#OPCIONELIMINARSECTOR#", opcionboton);
	
				break;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);

		model.close();
	}

	
	
	protected void export(HttpServletRequest request,
			HttpServletResponse response, 
		 SesionDatos sesion)
			throws ServletException, IOException, SQLException {
		
		
		mBoletosSorteosParalelos model = new mBoletosSorteosParalelos();
	//	model.setIdsorteo(sesion.pkSorteo);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Reporte_SorteoParalelos.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("REPORTE_SORTEOS_PARALELOS_ALL"); 
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);		
		cell.setCellValue("Nombre");
		cell = row.createCell(1);
		cell.setCellValue("Sorteo Participante");
		cell = row.createCell(2);
		cell.setCellValue("Folio");
	
	
		
	//	System.out.println("antes sql controller");		
		
		ResultSet rs = model.exportSorteoParalelos_all(sesion);			
		
		int fila = 1;
		
		while (rs.next()) {
			row = sheet.createRow(fila);
			//System.out.println("entro excel");	
			
			//COLABORADOR		
			
			//COLABORADOR
			cell = row.createCell(0);
			cell.setCellValue(rs.getString("COLABORADOR"));
			
			//FOLIO
			cell = row.createCell(1);
			cell.setCellValue(rs.getString("NOMBRE"));
			
			cell = row.createCell(2);
			cell.setCellValue(rs.getString("FOLIOCEROS"));
			
				
			
				
		
			fila++;
		}
		
		wb.close();
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);	
	

		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = response.getOutputStream();
	    outStream.write(outArray);
		outStream.flush();		
		
		
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO doPost

		mSorteosParalelos model = new mSorteosParalelos();
		SesionDatos sesion;
		Factory.prepareError(request);
		if ((sesion = SesionDatos.start(request, response, false, model)) == null)
			throw new ServletException("Error de sesion");
		
		HashMap<String, ParametersBase64> map = ParametersBase64.parse(request.getParameter("q"));
		ParametersBase64 parameter;
		if ((parameter = map.get("INSERT")) != null)
		{
			parameter = map.get("nombre");
			model.setNombre(parameter.value);
			parameter = map.get("fechaLimPago");
			model.setFechaLimPago(parameter.value);
			
			model.insertParalelo(sesion);
		}
		
	    if ((parameter = map.get("ACTUALIZAR")) != null) {
			
				model.setIdSorteo(sesion.pkSorteo);
				
				parameter = map.get("idparalelo");		
				model.setIdParalelo(Integer.parseInt(parameter.value));
				
			
				model.ActualizarColaboradores_SP(sesion);
				
				//model.ActualizarColaboradores_SP(sesion);
			}			
		
		
		
		
		else if ((parameter = map.get("DELETE")) != null)
		{			
			
			if (NumberUtils.isDigits(parameter.value))
			{
				model.setIdParalelo(Long.parseLong(parameter.value));
				
                String valida = "";
				
				if(model.getExistParaleloNicho()){						
					valida = "EXISTENICHO";					
				}else{					
					model.borrarParalelo();
					valida = "NOEXISTENICHO";					
				}
				
				PrintWriter out = response.getWriter();				
				out.println(valida);
				out.flush();
				out.close();				
				
				
			}
			
			
			
			
		}
		else if ((parameter = map.get("EDIT")) != null)
		{
			parameter = map.get("sorteoId");
			if (NumberUtils.isDigits(parameter.value))
			{
				model.setIdParalelo(Long.parseLong(parameter.value));
				parameter = map.get("nombre");
				model.setNombre(parameter.value);
				parameter = map.get("fechaLimPago");
				model.setFechaLimPago(parameter.value);
				model.editarSorteoParalelo(sesion);
			}
		}
		/*
		// model.setSector(request.getParameter("id"));

		String sectorcadena = request.getParameter("idsectores");
		String[] sectores = sectorcadena.split(",");
		// model.setSectores(sectores);

		model.setIdsorteo(Integer.valueOf(request.getParameter("idsorteo")));
		model.guardarSorteoParalelo(sectores, model, sesion);

		*/
		model.close();
	}

	protected String Buscar(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mSorteosParalelos model, SesionDatos sesion) throws ServletException, IOException {

		model.setIdSorteo(sesion.pkSorteo);
		
		int numeroregistros = model.contar(sesion,search);

		String HTML = CrearTabla(numeroregistros, model.paginacion(pg, show, search, sesion), pg, show, sesion, model);
		//HTML = HTML+"#%#"+model.getTotalregistros();

		return HTML;
		//PrintWriter out = response.getWriter();
		//out.println(HTML);
	}

	protected String CrearTabla(int numreg, ResultSet rs, int pg, int show, SesionDatos sesion, mSorteosParalelos model) {
		String fullPathinfouser = "/WEB-INF/uploads/";
		ServletContext context = getServletContext();
		String filePath = context.getRealPath(fullPathinfouser);
		filePath += "\\";
		//Locale english = new Locale("en", "US");
		//NumberFormat englishFormat = NumberFormat.getCurrencyInstance(english);

		String Str = new String(filePath);
		filePath = Str.replace('\\', '/');

		String html = "<ul class=\"result-list\">";

		try {
			if (Integer.valueOf(numreg) > 0) {
				while (rs.next()) {
					html += "<li style='" + (sesion.sorteoActivo ? "" : Global.bkground_cerrado) + "'>";
					html += "<div class=\"result-info\">";
					html += "<h4 class=\"title\">"
							//+ " <span class=\"label label-primary\">" + rs.getString("ZONA") + "</span>"
							+ "  <span href='#' onclick=''>"
							//+ rs.getString("NOMBRE") 
							+ "</span>"
				            + "<a href=\"BoletosSorteosParalelos?idsorteo="+rs.getString("PK_SORTEO")+"&idsorteop="+rs.getString("PK1")+"&idzona="+rs.getString("PK_ZONA")+"\">" + rs.getString("NOMBRE") + "</a>"
							
					
						    + "</h4>";

					/*
					model.setIdParalelo(rs.getLong("PK1"));
					ArrayList<String> nichos = model.getNichosAgrupados();
					
					html += "<div class='btn-row'>\n";
					for (String nicho : nichos)
						html += "<span><i class='FS-16'>&nbsp;</i><i class='label label-inverse'>" + nicho + "</i></span>\n";
					html += "</div>\n";
					*/
					html += "<p class=\"desc\">&nbsp;</p>";
					html += "<div class=\"btn-row\">";
					html += "  <a data-title='Borrar sorteo paralelo' data-container='body' data-toggle='tooltip' href='#' onclick=\"borrarParalelo(" + rs.getString("PK1")+ ",'"+rs.getString("NOMBRE")+"');\" data-original-title='' title='Borrar sorteo paralelo'><i class='fa fa-fw fa-trash-o'></i><span style='font-size:11px;'>Borrar</span></a>";
					//html += "<a data-title=\"Actualizar\" data-container=\"body\" data-toggle=\"tooltip\" href='#' onclick=\"ActualizarColaboradores_SP(" + rs.getString("PK1") + ",'"+rs.getString("NOMBRE")+"');\" data-original-title=\"\" title=\"\"><i class=\"fa fa-cloud-upload\"></i><span style=\"font-size:11px;\"> Actualizar</span></a>";
					html += "  <a data-title='Asignar nichos' data-container='body' data-toggle='tooltip' href='SorteosParalelosNichos?idsorteo="+sesion.pkSorteo+"&idparalelo="+rs.getString("PK1")+"' data-original-title='Asignar nichos' title='Asignar nichos a este sorteo paralelo'><i class='fa fa-fw fa-user'></i><span style='font-size:11px;'>Nichos</span></a>";
					html += "  <a data-title='Editar sorteo paralelo' data-container='body' data-toggle='tooltip' href='#' onclick=\"modalEditarParalelo(" + rs.getString("PK1") + ")\" data-original-title='Editar' title='Editar este sorteo paralelo'><i class='fa fa-pencil-square-o'></i><span style='font-size:11px;'>Editar</span></a>";
					html += "</div>";
					html += "</div>";
					html += "<div class=\"result-price\">";
					html += "</div>";
					html += "</li>";
				}

				html += "</ul>";
			} else {
				html += "<li>";
				html += "<div class=\"jumbotron m-b-0 text-center\" style=\"width:1%; \">";
				html += "<h1>No existen Sorteos paralelos asignados</h1>";
				html += "<p>Comienze por crear un sorteo paralelo.</p>";
				html += "</div>";
				html += "</li>";
				html += "</ul>";
			}

		} catch (SQLException e) { Factory.Error(e, "rs="+rs); }
		
		String paginado = Factory.paginado_2(numreg, show, pg);
		
		html = paginado + html + paginado;
		html = html + "<input id='numreg_id' type='hidden' value='"+numreg+"' >";
		
		return html;
	}
}