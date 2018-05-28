package com.sorteo.usuarios.controller;

import java.io.IOException;
import java.io.PrintWriter;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.core.Factory;
import com.core.Security;
import com.core.SesionDatos;
import com.sorteo.usuarios.model.mUsuarios;

/**
 * Servlet implementation class Usuarios
 */
@WebServlet("/Usuarios.do")
public class Usuarios extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Usuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mUsuarios model = new mUsuarios();
		Factory  vista = new Factory();
		SesionDatos sesion;
		
		if((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		
		ServletContext context = getServletContext();
		String HTML = "";
		String fullPath = "";
		String menu ="";
		String notificaciones ="";
		String infouser ="";
		
		
		String view = request.getParameter("view");
		
		if(view == null){view = "";}
		
	    switch(view){
	    
	    case "AgregarUsuario":
    		fullPath = "/WEB-INF/views/usuarios/agregar.html";
    		menu = vista.initMenu(0 ,false,24,25,sesion);
    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
	    	break;
	    	
	    case "BuscarUsuarios":
	    	int pg=0; //pagina a mostrar
			if(request.getParameter("pg")==null){
				pg=1;
			}else{
				pg =Integer.valueOf(request.getParameter("pg"));
			}
			
			int show = Integer.valueOf(request.getParameter("show"));
			String search = request.getParameter("search");
	    	BuscarUsuarios(request, response,pg,show,search, model);
	    	break;
	    	
	    	
	    case "ExisteUsuario":
	    	ExisteUsuario(request, response, model);
	    	break; 	
	    	
	    	
	    case "BuscarUsuario":
	    	BuscarUsuario(request, response, model);
	    	break;	
	    	
	    	
	    case "EditarPassword":
	    	EditarPassword(request, response, model);
	    	break;	
	    	
	    	
	    case "BuscarModal":
	    	int pg2=0; //pagina a mostrar
			if(request.getParameter("pg")==null){
				pg2=1;
			}else{
				pg2 =Integer.valueOf(request.getParameter("pg"));
			}
			
			int show2 = Integer.valueOf(request.getParameter("show"));
			String search2= request.getParameter("search");
			BuscarModal(request, response,pg2,show2,search2, model);
	    	break;
	    	
	    
	    case "AgregarRoleUsuario":
	    	AgregarRoleUsuario(request, response, model);
	    	break; 		
	    	
	    		
	    case "ExisteRoleUsuario":
	    	ExisteRoleUsuario(request, response, model);
	    	break; 		
	    	
	    
	    case "Borrar":	    	
	    	
	    	int res = Borrar(request, response, model);	    	
	    	HTML = Integer.toString(res);	    	
	    	
	    	break;
	    	
	    	
	    	
         case "BorrarDependencias":	   	    	
        	 BorrarDependencias(request, response, model);		    	
	    	break;
	    	
	    	
	    case "getDireccion":
	    	getDireccion(request, response, model);
	    	break;
	    	
	    	default:
	    		fullPath = "/WEB-INF/views/usuarios/lista.html";
	    		menu = vista.initMenu(0 ,false,24,25,sesion);
	    		notificaciones = vista.initNotificaciones(context,sesion.nombreCompleto);
	    		infouser = vista.initInfoUser(context,sesion.nombreCompleto,sesion.role);
	    		HTML = vista.CrearVista(context, fullPath,menu,notificaciones,infouser);
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
	
	protected void AgregarRoleUsuario(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		model.setIdrole(Integer.valueOf(request.getParameter("idrole")));	
		
		model.setId(Integer.valueOf(request.getParameter("idusuario")));
		
		model.AgregarRoleUsuario(model);
		
	
	}
	
	protected void ExisteRoleUsuario(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {		
		
		model.setId(Integer.valueOf(request.getParameter("idusuario")));
		
		model.ExisteRoleUsuario(model);		 
		    
		PrintWriter out = response.getWriter();
		out.println(model.getIdrole());
		model.init();
		
		
	
	}
	
	
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		mUsuarios model = new mUsuarios();
		SesionDatos sesion;
		if((sesion = SesionDatos.start(request, response, false, model)) == null)
			return;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (model.ExisteUsuario(request.getParameter("username"))) {

			out.println("existe");

		} else {

			model.setNombre(request.getParameter("nombre"));
			model.setApellidop(request.getParameter("apaterno"));
			model.setApellidom(request.getParameter("amaterno"));
			model.setEdad(Integer.valueOf(request.getParameter("edad")));
			model.setRfc(request.getParameter("rfc"));

			// TELEFONOS
			model.setTelefonocasa(request.getParameter("telefonocasa"));
			model.setTelefonooficina(request.getParameter("telefonooficina"));
			//model.setTelefonomovil(request.getParameter("telefonomovil"));

			// DIRECCION
			model.setCp(Integer.valueOf(request.getParameter("cp")));
			model.setPais(request.getParameter("pais"));
			model.setEstado(request.getParameter("estado"));
			model.setMundel(request.getParameter("delmun"));
			model.setColonia(request.getParameter("colonia"));
			model.setCalle(request.getParameter("calle"));
			model.setNumero(request.getParameter("numero"));

			// CORREO
			model.setCorreopersonal(request.getParameter("correopersonal"));
			model.setCorreotrabajo(request.getParameter("correotrabajo"));
			//model.setCorreootro(request.getParameter("correootro"));

			// REDES SOCIALES
			/*model.setFacebook(request.getParameter("facebook"));
			model.setTwitter(request.getParameter("twitter"));
			model.setRedotro(request.getParameter("otrared"));*/

			model.setUsuario(request.getParameter("username"));

			try {
				model.setPassword(Security.encrypt(request
						.getParameter("password")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int id = model.registrar(model, sesion);
			//id usuario
			out.println(id);
		}

		model.close();
	}
	
	
	protected void BuscarModal(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mUsuarios model) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String[] columnas = {"Role","Descripcion"};
		String[] campos = {"ROLE","DESCRIPCION"};
		
		
		int numeroregistros = model.contarModal();
		
	    String HTML = CrearTablaModal(numeroregistros,model.paginacionModal(pg,show,search),columnas,campos,pg,show);
	    PrintWriter out = response.getWriter();
		out.println(HTML);
		
	
	}
	
	protected String CrearTablaModal(int numreg,ResultSet rs,String[] columnas,String[] campos,int pg, int show){
			
			int i = (pg-1)*show;
			
			
			ArrayList<String> campostable = new ArrayList<String>();
			
			String htmlcampo =  "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";
			 
			for (String columna : columnas) {
				  String campotable = htmlcampo+columna+"</th>";
			      campostable.add(campotable); 
			  } 
			
			String  html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
			        html +="<thead>";
			        html +="<tr role=\"row\">";
			        //<input    id=\"marcarTodo\"   type=\"checkbox\"    />
			        html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" ></th>";
			        html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
			    	for (String columna : campostable) {
			    		 
			    		 html += columna;
			    		
					  } 
			        
			        html +=" </thead>";
			        html +=" <tbody id=\"Habilitados\">";
			        
			        try {
						while(rs.next()){
							
							i++;
					
					html +="<tr class=\"gradeA odd\" role=\"row\">";
			                
					
					html +="<td class=\"sorting_1\"><input name=\"idrole\"  id="+rs.getInt("PK1")+"  value="+rs.getInt("PK1")+"    type=\"radio\" /></td>";
					html +="<td class=\"sorting_1\">"+i+"</td>";
				 	
	                          for (String campo : campos) {                     	  
	                        	
	                        	  
	                           html +="<td class=\"sorting_1\">"+rs.getString(campo)+"</td>";
	                           
	                      
	                        	  
	                		  } 
	   
	      
	                          
	                          
	                        
	                html +="</tr>";
							
					System.out.println(rs.getString("ROLE"));
						   
						   }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        
			        		
	                html +="</tbody>";
	                html +="</table>";
	                
	                
	                int numpag = Math.round(numreg/show);
	                int denumpag = numpag+1;
	                
	                
	                String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando "+pg+" de "+denumpag+" total "+numreg+" elementos</div>";
	                       paginado += "<div class=\"dataTables_paginate paging_simple_numbers\" id=\"data-table_paginate\">";
	                       
	                       if(pg>1){
	                    	   //<a href="?pg=<%=pg-1 %>">Anterior</a>
	                    	   
	                    	   int pagante = pg-1;
	                    	   paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPagModal("+pagante+");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

	                    	   }else{
	                    		   
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
	                       	if(j<numpag+1){
	                       		
	                       		sumpag = j+1;
	                       		
	                       		if(sumpag==pg){
	                       			paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPagModal("+sumpag+");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"+sumpag+"</a>";
	                       		}else{
	                       			paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPagModal("+sumpag+");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"+sumpag+"</a>";
	                       		}
	                       		
	                        }
	                       }
	                       
	                       
	                       
	                       paginado += "</span>";
	                       
	                       if(pg<=numpag){
	                    	   int numeropag = pg+1;
	                    	   
	                    	   paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPagModal("+numeropag+");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
	                    	   }else{
	                    	   paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";	   
	                    	   }

	                           paginado += "</div>";
	                       
	                       
	                html = paginado+html;
	                       
	                                        
	        
			       return html;
			
		   }	
	
	
	
	
	protected int Borrar(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idusuario")));		
		int res = model.Borrar(model);
		
		return res;
		
	//	response.setContentType("text/html");
	//	PrintWriter out = response.getWriter();
	//	out.println(res);			
	

	}
	
	
	
	protected void BorrarDependencias(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idusuario")));		
		model.BorrarDependencias(model);	

	}
	
	
	

	protected void EditarPassword(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idusuario")));
		model.setUsuario(request.getParameter("username"));

		// model.setPassword(request.getParameter("password"));

		try {
			model.setPassword(Security.encrypt(request.getParameter("password")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.EditarPassword(model);

	}

	protected void BuscarUsuario(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setId(Integer.valueOf(request.getParameter("idusuario")));
		// USUARIO
		model.ObtenerUsuario(model);

		String datos = model.getUsuario() + "#%#" + model.getPassword() + "#%#"
				+ model.getId();

		PrintWriter out = response.getWriter();
		out.println(datos);

	}

	protected void BuscarUsuarios(HttpServletRequest request,
			HttpServletResponse response, int pg, int show, String search,
			mUsuarios model) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String[] columnas = {"Usuario","Nombre","Role","Controles"};
		String[] campos = {"USUARIO","NOMBRE","ROLE"};
		
		
		int numeroregistros = model.contar(search);
		
	    String HTML = CrearTabla(numeroregistros,model.paginacion(pg,show,search),columnas,campos,pg,show);
	    PrintWriter out = response.getWriter();
		out.println(HTML);
		
	
	}
	
	
	
	
	protected void getDireccion(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		model.setCp(Integer.valueOf(request.getParameter("cp")));

		model.Obtener_Direccion(model);

		String direccion = model.getPais() + "#%#" + model.getEstado() + "#%#"
				+ model.getMundel() + "#%#" + model.getColonia();

		PrintWriter out = response.getWriter();
		out.println(direccion);

	}

	protected void ExisteUsuario(HttpServletRequest request,
			HttpServletResponse response, mUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();

		if (model.ExisteUsuario(request.getParameter("username"))) {

			out.println("existe");

		} else {
		}

	}

	protected String CrearTabla(int numreg, ResultSet rs, String[] columnas, String[] campos, int pg, int show) {

		int i = (pg - 1) * show;

		ArrayList<String> campostable = new ArrayList<String>();

		String htmlcampo =  "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 200px;\" aria-label=\"Browser: activate to sort column ascending\">";
		 
		for (String columna : columnas) {
			
			String campotable = htmlcampo+columna+"</th>";
			campostable.add(campotable);
		}
		
		String  html = "<table class=\"table table-striped table-bordered dataTable no-footer\" id=\"data-table\" role=\"grid\" aria-describedby=\"data-table_info\">";
        html +="<thead>";
        html +="<tr role=\"row\">";
        
        html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
        for (String columna : campostable) {
        	html += columna;
        } 
		        
		html += " </thead>";
		html += " <tbody>";
		        
		        
		try {

			if (Integer.valueOf(numreg) > 0) {
				while (rs.next()) {

					i++;

					html += "<tr class=\"gradeA odd\" role=\"row\">";

					html += "<td class=\"sorting_1\">" + i + "</td>";
					for (String campo : campos) {

						if (campo.equals("ROLE")) {

							if ((rs.getString("ROLE") == null)) {
								html += "<td class=\"sorting_1\">N/A</td>";
							} else {
								html += "<td class=\"sorting_1\">" + StringEscapeUtils.escapeHtml4(rs.getString(campo))
										+ "</td>";
							}
						} else {
							html += "<td class=\"sorting_1\">" + StringEscapeUtils.escapeHtml4(rs.getString(campo))
									+ "</td>";

						}

					}

					html += "<td class=\"sorting_1\">";   		
                  	    
                  	    html +="<div class=\"btn-group m-r-5 m-b-5 \"><a class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\" aria-expanded=\"false\">Acci&oacute;n"
                  	    	
                  	    	 + "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
                  	    	 + "<li><a href=\"EditUsuarios?idusuario="+rs.getString("PK1")+"\">Editar</a></li>"
                  	    	  + "<li><a href=\"javascript:CambiarPassword("+rs.getString("PK1")+");\">Cambiar Contrase&ntilde;a</a><li>"
                  	    	   + "<li><a href=\"javascript:AsignarRoles("+rs.getString("PK1")+");\">Asignar Roles</a></li>"
                  	    	 + "<li><a href=\"javascript:Borrar("+rs.getString("PK1")+");\">Borrar</a><li>"
                  	    	 + "<li class=\"divider\">"
                  	    	 + "</li><li>"
                  	    	 + "</ul></div>";
                  	    		   
                  	     html +="</td>";     
                          
                          
                          
                        
                html +="</tr>";
						
				
					   
				}
					
		        }else {			
					
					html += "<tr> <td colspan=\"5\">"
							+"<div style=\"width:100%;\" class=\"jumbotron m-b-0 text-center\"><h1>No existen Usuarios</h1><p>Empiece por asignar un usuario o realice otra busqueda.</p></div>"
							+ "</td></tr>";			

				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        		
                html +="</tbody>";
                html +="</table>";
                
                
                int numpag = Math.round(numreg/show);
                int denumpag = numpag+1;
                
                
                String paginado = "<div class=\"dataTables_info\" id=\"data-table_info\" role=\"status\" aria-live=\"polite\">Mostrando "+pg+" de "+denumpag+" total "+numreg+" elementos</div>";
                       paginado += "<div class=\"dataTables_paginate paging_simple_numbers\" id=\"data-table_paginate\">";
                       
                       if(pg>1){
                    	   //<a href="?pg=<%=pg-1 %>">Anterior</a>
                    	   
                    	   int pagante = pg-1;
                    	   paginado += "<a class=\"paginate_button previous\" href=\"javascript:void(0)\" onclick=\"getPag("+pagante+");\" aria-controls=\"data-table\" data-dt-idx=\"0\" tabindex=\"0\" id=\"data-table_previous\">Anterior</a>";

                    	   }else{
                    		   
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
                       	if(j<numpag+1){
                       		
                       		sumpag = j+1;
                       		
                       		if(sumpag==pg){
                       			paginado += "<a class=\"paginate_button current\" href=\"javascript:void(0)\" onclick=\"getPag("+sumpag+");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"+sumpag+"</a>";
                       		}else{
                       			paginado += "<a class=\"paginate_button\" href=\"javascript:void(0)\" onclick=\"getPag("+sumpag+");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\">"+sumpag+"</a>";
                       		}
                       		
                        }
                       }

                       paginado += "</span>";
                       
                       if(pg<=numpag){
                    	   int numeropag = pg+1;
                    	   
                    	   paginado += "<a class=\"paginate_button next\" href=\"javascript:void(0)\" onclick=\"getPag("+numeropag+");\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";
                    	   }else{
                    	   paginado += "<a class=\"paginate_button next disabled\" aria-controls=\"data-table\" data-dt-idx=\"2\" tabindex=\"0\" id=\"data-table_next\">Siguiente</a>";	   
                    	   }

                           paginado += "</div>";
                       
                       
                html = paginado+html;
                       
                                        
        
		       return html;
		
	}
	

}
