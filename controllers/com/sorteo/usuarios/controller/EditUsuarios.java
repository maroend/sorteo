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

import com.core.Factory;
import com.core.SesionDatos;
import com.sorteo.usuarios.model.mEditUsuarios;

/**
 * Servlet implementation class EditUsuarios
 */
@WebServlet("/EditUsuarios")
public class EditUsuarios extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUsuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		mEditUsuarios model = new mEditUsuarios();
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

		case "getDireccion":
			getDireccion(request, response, model);
			break;

		case "BuscarUsuario":
			BuscarUsuario(request, response);
			break;

    	default:
    		fullPath = "/WEB-INF/views/usuarios/editusuarios.html";
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		mEditUsuarios model = new mEditUsuarios();
				
		
		//if( model.ExisteUsuario(request.getParameter("username"))){
		
		//	out.println("existe");

	//	}else{			
					
		       model.setId(Integer.valueOf(request.getParameter("idusuario")));
		       
		       model.setUsuario(model.ObtenerU(model.getId()));
		   //    model.setUsuario(model.ObtenerU(model));  //obtiene el usuario
			   model.setNombre(request.getParameter("nombre"));
			   model.setApellidop(request.getParameter("apaterno"));
			   model.setApellidom(request.getParameter("amaterno"));
			   model.setEdad(request.getParameter("edad"));
			   model.setRfc(request.getParameter("rfc"));
			   
			   //TELEFONOS
			   model.setTelefonocasa(request.getParameter("telefonocasa"));
			   model.setTelefonooficina(request.getParameter("telefonooficina"));
			 //  model.setTelefonomovil(request.getParameter("telefonomovil"));	
			   
			   
			   //DIRECCION
			   model.setCp(Integer.valueOf(request.getParameter("cp")));
			   model.setPais(request.getParameter("pais"));
			   model.setEstado(request.getParameter("estado"));
			   model.setMundel(request.getParameter("delmun"));		 
			   model.setColonia(request.getParameter("colonia"));
			   model.setCalle(request.getParameter("calle"));
			   model.setNumero(request.getParameter("numero"));	
			   
			   //CORREO
			   model.setCorreopersonal(request.getParameter("correopersonal"));
			   model.setCorreotrabajo(request.getParameter("correotrabajo"));
			  // model.setCorreootro(request.getParameter("correootro"));  
			   
			   
			   //REDES SOCIALES
			  // model.setFacebook(request.getParameter("facebook"));
			  // model.setTwitter(request.getParameter("twitter"));
			  // model.setRedotro(request.getParameter("otrared"));  
			   
			   
			   
			/*   model.setUsuario(request.getParameter("username"));
			   
			    
			   try {
				model.setPassword(Security.encrypt(request.getParameter("password")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
				

			 model.registrar(model);

			/*	PrintWriter out1 = response.getWriter();
				out1.println(request.getParameter("correotrabajo"));*/
			
			
	//	}
		
		
		
			
		model.close();
	}
	
	
	protected void BuscarUsuario(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 mEditUsuarios model = new mEditUsuarios();
		
		
		  model.setId(Integer.valueOf(request.getParameter("idusuario")));	
		//USUARIO
		  model.ObtenerUsuario(model);  		   
		 String datos = model.getUsuario()+"#%#"+model.getNombre()+"#%#"+model.getApellidop()+"#%#"+model.getApellidom()+"#%#"+model.getPassword();
		 
		 //TELEFONOS
		 model.ObtenerTelefono(model); 
		// datos += "#%#"+model.getTelefonocasa()+"#%#"+model.getTelefonooficina()+"#%#"+model.getTelefonomovil();
		 datos += "#%#"+model.getTelefonocasa()+"#%#"+model.getTelefonooficina();
		 
		//DIRECCION
		 model.ObtenerDireccion(model);
		 //base de datos
         String colonias = model.Obtener_Colonia();//colonia de sepomex
		 
		 datos += "#%#"+model.getPais()+"#%#"+model.getEstado()+"#%#"+model.getMundel()+"#%#"+colonias+"#%#"+model.getCalle()+"#%#"+model.getNumero()+"#%#"+model.getCp()+"#%#"+model.getColonia();
		 
		 //CORREOS
		 model.ObtenerCorreo(model); 
		 datos += "#%#"+model.getCorreopersonal()+"#%#"+model.getCorreotrabajo();
		 
	//	 datos += "#%#"+model.getCorreopersonal()+"#%#"+model.getCorreotrabajo()+"#%#"+model.getCorreootro();
		 
		//RESED SOCIALES
		// model.ObtenerRedes(model); 
		// datos += "#%#"+model.getFacebook()+"#%#"+model.getTwitter()+"#%#"+model.getRedotro()+"#%#"+model.getEdad()+"#%#"+model.getRfc();
		 
		
		 datos += "#%#"+model.getEdad()+"#%#"+model.getRfc();
	
		PrintWriter out = response.getWriter();
		out.println(datos);
	
	}
	
	
	
	protected void getDireccion(HttpServletRequest request,
			HttpServletResponse response, mEditUsuarios model)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		model.setCp(Integer.valueOf(request.getParameter("cp")));
		
		model.Obtener_Direccion(model);
		
		String direccion = model.getPais()+"#%#"+model.getEstado()+"#%#"+model.getMundel()+"#%#"+model.getColonia();
	
		PrintWriter out = response.getWriter();
		out.println(direccion);
	}
	

	protected String CrearTabla(int numreg,ResultSet rs,String[] columnas,String[] campos,int pg, int show){
		
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
		        
		        
		        html += "<th class=\"sorting\" tabindex=\"0\" aria-controls=\"data-table\" rowspan=\"1\" colspan=\"1\" style=\"width: 50px;\" aria-label=\"Browser: activate to sort column ascending\">No.</th>";
		    	for (String columna : campostable) {
		    		 
		    		 html += columna;
				  } 
		        
		        html +=" </thead>";
		        html +=" <tbody>";
		        
		        
		        try {
					while(rs.next()){
						
						i++;
				
				html +="<tr class=\"gradeA odd\" role=\"row\">";
		                
				html +="<td class=\"sorting_1\">"+i+"</td>";
                          for (String campo : campos) {
                        	  
                        	  html +="<td class=\"sorting_1\">"+rs.getString(campo)+"</td>";
                        	  
                		  } 
                          
                          i++;
                html +="</tr>";
						
				System.out.println(rs.getString("NOMBRE"));
					   
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
                      
                       
                     //Calcular el inicio del arreglo
                       int inipg = 0;
                       int r = (pg)/show;
                       int sumpag = 0;

                       if(r==0){
                       	
                       	inipg=pg-1;
                       }else{
                       	inipg = ((pg-1)/show)-show;
                       }

                       for(int j=inipg;j<10+inipg+1;j++){
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
