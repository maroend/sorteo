package com.core;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import com.sorteo.dashboard.model.mDashboard;



//import org.apache.commons.lang3.StringEscapeUtils;


public class Factory {

	static final boolean for_linux = false;

	public Factory() {
		// TODO Auto-generated constructor stub
		
	}
	
	public String CrearVista(ServletContext context,String file,String menu,String notificaciones,String infouser){
	  
		Scanner scanner = null;
		String fullPath = context.getRealPath(file);
		

		try {
			scanner = new Scanner(new File(fullPath), "UTF-8").useDelimiter("\\Z");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		String contents = scanner.next();
		String regex = "#MENU#";
		contents = contents.replaceAll(regex, menu);
		
	    regex = "#NOTIFICACIONES#";
		contents = contents.replaceAll(regex, notificaciones);
		
		regex = "#INFOUSER#";
		contents = contents.replaceAll(regex, infouser);
	
	   return contents;
	
	}
	
	public String CrearVista(ServletContext context, String file, String parametros){

		Scanner scanner = null;
		String fullPath = context.getRealPath(file);

		try {
			scanner = new Scanner(new File(fullPath), "UTF-8").useDelimiter("\\Z");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String contents = scanner.next();
		if (parametros == null)
			parametros = "";
		contents = contents.replaceAll("#ARGS_EXTRA#", parametros);
		
		return contents;
	}
	
	public String initNotificaciones(ServletContext context, String nombre){
		
		String file = "/WEB-INF/views/notificaciones.html";
		
		Scanner scanner = null;
		String fullPath = context.getRealPath(file);
		String contents;

		try {
			scanner = new Scanner(new File(fullPath), "UTF-8").useDelimiter("\\Z");
			contents = scanner.next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			contents = "<li class=\"dropdown navbar-user\"><a href=\"javascript:;\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"> <img src=\"assets/img/user-13.jpg\" alt=\"\" /> <span class=\"hidden-xs\">#NOMBREUSUARIO#</span> <b class=\"caret\"></b> </a> <ul class=\"dropdown-menu animated fadeInLeft\"> <li class=\"arrow\"></li> <li><a href=\"Profile\">Editar Informaci&oacute;n</a></li> <li><a href=\"Password\">Cambiar Contraseña</a></li> <li class=\"divider\"></li> <li><a href=\"Logout.do\">Cerrar Sesi&oacute;n</a></li> </ul></li>";
		}
		
		String regex = "#NOMBREUSUARIO#";
		contents = contents.replaceAll(regex, nombre);
		
		return contents;
	}
	
	public String initInfoUser(ServletContext context, String nombre, String rol){
		   
		String file = "/WEB-INF/views/infouser.html";
		  
		Scanner scanner = null;
		String fullPath = context.getRealPath(file);

		try {
			scanner = new Scanner(new File(fullPath), "UTF-8").useDelimiter("\\Z");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    
		
		String contents = scanner.next();	    

		if (nombre.length()>14)
			nombre = nombre.substring(0, 14);
		contents = contents.replaceAll("#NOMBREUSUARIO#", nombre);
		
		contents = contents.replaceAll("#ROL#", rol);
		
		return contents;
	}
	
	public String initMenu(int nivel,boolean submenu,int idmenu,int idsubmenu, SesionDatos sesion){
		
		
		String lista = "<ul ";
		if(!submenu){ lista += "class=\"nav\">"; }else{ lista += "class=\"sub-menu\">"; }
		String sql = "SELECT PK1, MENU,URL,ICONO, PADRE,PK_PERMISO FROM MENU WHERE PADRE = '"+nivel+"' AND DISPONIBLE = 1 ORDER BY ORDEN"; 

		// Database db = new Database();
		ResultSet rs = sesion.db.getDatos(sql);

		try {
			while (rs.next()) {
				// Abrimos el nodo con el nombre del primer dependiente
		        
		        // Utilizaremos esta variable para ver si seguimos consultado la BDD
				int tiene_dependientes = 0;
				int id = Integer.valueOf(rs.getString("PK1"));
				String sqlchild = "SELECT * FROM MENU WHERE PADRE = '" + id + "' AND DISPONIBLE=1";
				tiene_dependientes = sesion.db.ContarFilas(sqlchild);
				String bullet = "<b class=\"caret pull-right\"></b>";
				String classstyle = " class=\"has-sub\" ";
				String classstyleactive = " class=\"has-sub active\" ";
				String urlweb = rs.getString("URL");
		        
		        if(tiene_dependientes > 0){ urlweb="javascript:;"; }else{bullet=""; classstyle=""; classstyleactive=" class=\"active\" "; }

				if (!submenu) {// Agregamos las Fichas superiores del Menu

					// Validamos si tiene permiso
					if (sesion.permisos.havePermiso(rs.getInt("PK_PERMISO"))
							|| rs.getInt("PK_PERMISO") == 0) {
						String icono = rs.getString("ICONO");

						lista += "<li ";
						
						if (idmenu == Integer.valueOf(rs.getString("PK1"))) {
							lista += classstyleactive +"><a href=\""+urlweb+"\">"+bullet+icono+"<span>"+rs.getString("MENU")+"</span></a>";
						} else{
							lista += classstyle+"><a href=\""+urlweb+"\">"+bullet+icono+"<span>"+rs.getString("MENU")+ "</span></a>";
						}

					} // END Validamos si tiene permiso
				}
				else
				{
					// Validamos si tiene permiso
					// if(this->passport->privilegios->hasPrivilege($r['PK_PERMISO'])){
					// 10009 ACCESO A LA LISTA DE SORTEOS
					if (sesion.permisos.havePermiso(rs.getInt("PK_PERMISO"))
							|| rs.getInt("PK_PERMISO") == 0) {

						lista += "<li ";

						if(idsubmenu==Integer.valueOf(rs.getString("PK1"))){ lista += " class=\"active\"><a href=\""+rs.getString("URL")+"\">"+rs.getString("MENU")+ "</a>";}
						else{ lista += "><a href=\""+rs.getString("URL")+"\">"+rs.getString("MENU")+ "</a>"; }
					}
				}

				// Si tiene dependientes, ejecutamos recursivamente
				// tomando como parÃ¡metro el nuevo nivel
				if (tiene_dependientes > 0) {

					// if(idmenu==Integer.valueOf(rs.getString("PK1"))){

					lista += initMenu(Integer.valueOf(rs.getString("PK1")),
							true, idmenu, idsubmenu, sesion);

					// }
				}

				// Cerramos el nodo
				lista += "</li>";

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Cerramos la lista
		lista += "</ul>";

		return lista;
	}
	
	
	
	public String getSorteosUsuarios(HttpServletRequest request,
			HttpServletResponse response, String HTML, SesionDatos sesion) throws ServletException,
			IOException {
		   
		String contenido = "<div  class=\"jumbotron m-b-0 text-center\"><h1>No existen Sorteos</h1><p>Empieze por agregar un nuevo sorteo.</p></div>";

		String regex = "#SORTEOSUSUARIOS#";
		ResultSet rs = null;

		mDashboard modelsorteos = new mDashboard();

		modelsorteos.setIdUsuario(sesion.pkUsuario);
		modelsorteos.cosultaPredeterminados();

		long sorteo = modelsorteos.getIdSorteo();
		long sector = modelsorteos.getIdSector();
		long nicho = modelsorteos.getIdNicho();
		   
		// EXISTE USUARIO NIVEL SORTEO
		if (modelsorteos.ContarSorteosUsuarios() != 0) {

			rs = modelsorteos.getSorteosUsuario();
			try {
				while (rs.next()) {
					
					contenido += "<p>";
					contenido +="<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSorteo('"+rs.getString("PK1")+"')\"  role=\"button\" class=\"btn btn-success btn-lg\">";

					if (sorteo == rs.getInt("PK1")) {
						contenido += "<i class=\"fa fa-2x fa-check-circle\"></i> ";
					}

					contenido += rs.getString("SORTEO") + "</a> ";
					contenido += "</<p>";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// NIVEL SECTORES USUARIOS
		} else if (modelsorteos.ContarSectoresUsuarios()!=0){
			   
			   rs = modelsorteos.getSectoresUsuario();
			   
			   try {
					while (rs.next()) {
						
						   contenido += "<p>";
						   contenido +="<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardSector('"+rs.getString("PK_SORTEO")+"','"+rs.getString("PK_SECTOR")+"')\"  role=\"button\" class=\"btn btn-success btn-lg\">";
						  
						   if(sector==rs.getInt("PK_SECTOR")){
							   contenido +="<i class=\"fa fa-2x fa-check-circle\"></i> ";
							   }
						   
						   contenido += rs.getString("SORTEO")+" <br> <small>"+rs.getString("SECTOR")+"</small></a> ";
						   contenido += "</<p>";
					   
					   }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			   //NIVEL NICHOS USUARIOS
		   }else if(modelsorteos.ContarNichosUsuarios()!=0){
			   
			   rs = modelsorteos.getNichosUsuario();
			   
			   try {
					while (rs.next()) {
						
						   contenido += "<p>";
						   contenido +="<a href=\"javascript:void(0)\" style=\"width:100%;\" id=\"btncargaboletos\"  onClick=\"RedireccionarDashboardNicho('"+rs.getString("PK_SORTEO")+"','"+rs.getString("PK_SECTOR")+"','"+rs.getString("PK_NICHO")+"')\"  role=\"button\" class=\"btn btn-success btn-lg\">";
						   if(nicho==rs.getInt("PK_NICHO")){
							   contenido +="<i class=\"fa fa-2x fa-check-circle\"></i> ";
							   }
						   contenido += rs.getString("SORTEO")+"<br> <small>"+rs.getString("NICHO")+"</small></a> ";
						   contenido += "</<p>";
					   
					   }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			   
		   }
		   
		   HTML = HTML.replaceAll(regex, contenido);
		   
		   return HTML;
	   }
	
	
	// __________________________________________________________
	//                           LOG
	// __________________________________________________________
	public enum LOG{
		DEFAULT
		, CONSULTA_LOG
		, BORRADO_LOG
		, REGISTRO
		, CONSULTA
		, EDITADO
		, BORRADO
		, ASIGNACION
		;
		public String toString() {
			switch(this) {
			case CONSULTA_LOG: return "Consulta LOG";
			case BORRADO_LOG: return "Borrado LOG";
			case REGISTRO: return "Registro";
			case CONSULTA: return "Consulta";
			case EDITADO: return "Editado";
			case BORRADO: return "Borrado";
			case ASIGNACION: return "Asignacion";
			//case ACTUALIZACION: return "Actualizacion";
			
			default : return "Default";
			}
		}
	}


	// __________________________________________________________

	private static String path_errors = null;
	
	public static void prepareError(HttpServletRequest request) {
		if (path_errors == null) {
			path_errors = request.getServletContext().getRealPath("/LOG_EXCEPTIONS");
			if (path_errors != null && path_errors.length() > 0) {
				if (for_linux) {
					if (path_errors.charAt(path_errors.length() - 1) != '/')
						path_errors += "/";
				} else {
					if (path_errors.charAt(path_errors.length() - 1) != '\\')
						path_errors += "\\";
				}
			}
		}
	}
	
	public static String getPathErrors() {
		return Factory.path_errors;
	}

	private static boolean _debug = true;
	public static void Error(Exception ex, String sql) {
		try {
			if (_debug)
				ex.printStackTrace();
			
			DateFormat format;
			
			// PASO 1: Solicitar el nombre de la carpeta y crearlo en caso de que no exita.
			Date now = Calendar.getInstance().getTime();// new Date(0);
			/*
			DateFormat format = new SimpleDateFormat("yyyy-MM");
			String folderName = path + "\\" + format.format(now);
			File folder = new File(folderName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			//*/
			
			// PASO 2: Checar si existe el archivo LOG del dia actual.
			
			format = new SimpleDateFormat("yyyy-MM-dd");
			String fullFileName = path_errors + format.format(now) + ".txt";
			File file = new File(fullFileName);
			if (!file.exists())
				file.createNewFile();
			// PASO 3. Construir la cadena de error.
			format = new SimpleDateFormat("hh:mm:ss");
			/**/
			StringBuilder sb = new StringBuilder();
			if (ex != null){
				StackTraceElement[] elements = ex.getStackTrace();
				sb.append(format.format(now));
				if (sql != null)
					sb.append(" Text: ").append(sql).append("\r\n");
				sb.append(" {\t").append(ex).append("\r\n");
				if (elements != null) {
					int i;
					for (i = 0; i < elements.length && i < 5; i++)
						sb.append("\tat ").append(elements[i].getClassName()).append("(").append(elements[i].getMethodName()).append(",").append(elements[i].getLineNumber()).append(")\r\n");
					if (i < elements.length)
						sb.append("\t...").append(elements.length-i).append(" mas\r\n");
				}
				//sb.append("}  {").append(ex == null ? "" : ex).append("}");
				sb.append("}");
			}
			//*/
			// PASO 4. Se guarda la cadena de error en el archivo de texto
			FileWriter fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(sb.toString());
			//pw.print(format.format(now) + "  {");
			//ex.printStackTrace(pw);
			//pw.println("}");
			
			pw.flush();
			fw.close();
		}catch(Exception unused) { }
	}//<end>
	
	public static String comillaSQL(String sql) {
		
		if (0<=sql.indexOf("'")) {
			sql = sql.replaceAll("'", "''");
		}
		return sql;
	}
	
	public static String Paginado(int numreg, int show, int pg){
		return Paginado(numreg, show, pg, "getPag");
	}
	
	public static String Paginado(int numreg, int show, int pg, String f_pag){
		int numpag = (int)Math.ceil((double)numreg / show);
		int denumpag = numpag;// + 1;

		String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando "
				+ pg + " de " + denumpag + ", total " + numreg + " elementos</div>";
		paginado += "<div class=\"dataTables_paginate paging_simple_numbers\" id=\"data-table_paginate\">";

		if (pg > 1) {
			int pagante = pg - 1;
			paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"" + f_pag + "(" + pagante
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";
		} else {

			paginado += "<a class=\"paginate_button previous disabled\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

		}

		paginado += "<span>";

		// Calcular el inicio del arreglo
		int inipg = 0;
		//int r = (pg - 1) % 5;
		int sumpag = 0;

		/*
		if (r == 0) {

			inipg = pg - 1;
		} else {
		*/
		inipg = ((pg - 1) / 5) * 5;
		//}

		for (int j = inipg; j < inipg + 5; j++) {
			if (j < numpag) {
				sumpag = j + 1;
				if (sumpag == pg) {
					paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"" + f_pag + "("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				} else {
					paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"" + f_pag + "("
							+ sumpag
							+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"
							+ sumpag + "</a>";
				}
			}
		}

		paginado += "</span>";

		if (pg < numpag) {
			int numeropag = pg + 1;
			paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"" + f_pag + "(" + numeropag
					+ ");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		} else {
			paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
		}

		paginado += "</div>";
		
		return paginado;
	}
	public static String paginado_2(int numreg, int show, int pg) {
		return paginado_2(numreg, show, pg, "getPag", "elementos");
	}
	public static String paginado_2(int numreg, int show, int pg, String f_pag, String elementos) {
		int numpag = (int)Math.ceil((double)numreg / show);

		//String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\" style=\"color:#334433; margin-left:5px;\">Total: " + numreg + " " + elementos + "</div>";
		String paginado = "<div class='dataTables_info' id='data-table_info' role='status' aria-live='polite' style='color:#334433; margin:10px; float: left;'>Total: " + numreg + " " + elementos + "</div>";
		
		paginado += "<ul class=\"pagination pagination-without-border pull-right m-t-0\">";

		if (pg > 1) {
			int pagante = pg - 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\""+f_pag+"(" + pagante + ");\">Anterior</a></li>";
		} else {
			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Anterior</a></li>";
		}

		// Calcular el inicio del arreglo
		int inipg = 0;
		int r = (pg - 1) % 5;
		int sumpag = 0;

		if (r == 0) {
			inipg = pg - 1;
		} else {
			inipg = ((pg - 1) / 5) * 5;
		}

		for (int j = inipg; j < 5 + inipg; j++) {

			if (j < numpag) {

				sumpag = j + 1;

				if (sumpag == pg) {
					paginado += "<li class=\"active\"><a href=\"javascript:void(0)\" onclick=\""+f_pag+"("
							+ sumpag + ");\">" + sumpag + "</a>";
				} else {
					paginado += "<li><a href=\"javascript:void(0)\" onclick=\""+f_pag+"("
							+ sumpag + ");\">" + sumpag + "</a>";
				}
			}
		}

		if (pg < numpag) {
			int numeropag = pg + 1;
			paginado += "<li><a href=\"javascript:void(0)\" onclick=\""+f_pag+"("
					+ numeropag + ");\">Siguiente</a></li>";

		} else {

			paginado += "<li class=\"disabled\"><a href=\"javascript:void(0)\">Siguiente</a></li>";
		}

		paginado += "</ul>";

		String paginadoright = "<div class=\"clearfix\" style=\"margin-top:20px;\">";
		paginadoright += paginado;
		paginadoright += "</div>";

		return paginadoright;
	}
	
}

