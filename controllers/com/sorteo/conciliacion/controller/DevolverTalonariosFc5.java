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
import com.sorteo.conciliacion.model.mDevolverTalonariosFc5;

import com.sorteo.sorteos.model.mAsignacionTalonariosColaboradores;
import com.sorteo.sorteos.model.mAsignacionTalonariosNichos;
import com.sorteo.sorteos.model.mAsignacionTalonarios;

/**
 * Servlet implementation class 
 */
@WebServlet("/DevolverTalonarios")
public class DevolverTalonariosFc5 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DevolverTalonariosFc5() {
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
		
		mDevolverTalonariosFc5 model = new mDevolverTalonariosFc5();
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
		
		
		case "obtenerTalonarioFC5":
			obtenerTalonarioFC5(request, response, model,sesion);
			break;				

		
			
		default:
			
			String fullPath = "/WEB-INF/views/conciliacion/devolver_talonario_fc5.html";
			menu = vista.initMenu(0, false, 10, 110, sesion);
			notificaciones = vista.initNotificaciones(context, sesion.nombreCompleto);
			infouser = vista.initInfoUser(context, sesion.nombreCompleto,sesion.role);
			HTML = vista.CrearVista(context, fullPath, menu, notificaciones, infouser);
			
			System.out.println("id sorteo: "+sesion.pkSorteo);
			System.out.println("id usuario: "+sesion.pkUsuario);
			
			model.setIdSorteo((int)sesion.pkSorteo);
			model.setIdusuario((int)sesion.pkUsuario);
			
			/*model.getSectorUsuarioActual();
			HTML = HTML.replaceAll( "#SECTORUSUARIO#",   String.valueOf(model.getSector())); 	   
			HTML = HTML.replaceAll( "#USUARIO#",   String.valueOf( (int)sesion.pkUsuario));
			
			HTML = HTML.replaceAll("#DISPLAY_EDIT_CONTROLS#", sesion.sorteoActivo ? "display" : "none");*/
			
			break;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(HTML);
		model.close();
	}
	
	
	
	
	protected void obtenerTalonarioFC5(HttpServletRequest request,
			HttpServletResponse response, mDevolverTalonariosFc5 model, SesionDatos sesion) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result = null;
		model.setFolioTalonario(request.getParameter("folio"));
		result = model.obtenerTalonarioFC5(model,sesion.pkSorteo);
		
		PrintWriter out = response.getWriter();
		out.println(result);
		
	}
	

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		mDevolverTalonariosFc5 model = new mDevolverTalonariosFc5();
		
		SesionDatos sesion = SesionDatos.start(request, response, false, model);
		
		
		
		
		
		
		String formato = request.getParameter("formato");		
		int idTalonario = Integer.valueOf(request.getParameter("idtalonario"));
		int folioTalonario = Integer.valueOf(request.getParameter("folio"));
		int idsector = Integer.valueOf(request.getParameter("idsector"));
		int idnicho = Integer.valueOf(request.getParameter("idnicho"));
		int idcolaborador = Integer.valueOf(request.getParameter("idcolaborador"));
		
		model.setIdSorteo(sesion.pkSorteo);				
		model.setIdtalonario(idTalonario);
		model.setFormato(formato);
		
		model.setIdSector(idsector);
		model.setIdNicho(idnicho);
		model.setIdColaborador(idcolaborador);	
		
		mAsignacionTalonariosColaboradores modelTalColab = new mAsignacionTalonariosColaboradores();
		mAsignacionTalonariosNichos modelTalNichos = new mAsignacionTalonariosNichos();
		mAsignacionTalonarios modelTalSectores = new mAsignacionTalonarios();
		
		
		if(idcolaborador != 0 ){	
			
			//DEVOLUCION COLABORADORES		
			modelTalColab.setIdSorteo(sesion.pkSorteo);
			modelTalColab.setIdSector(idsector);
			modelTalColab.setIdNicho(idnicho);
			modelTalColab.setIdColaborador(idcolaborador);
			modelTalColab.setIdtalonario(idTalonario);	
			modelTalColab.setFormato(formato);
			modelTalColab.devolvertalonariosfc5(folioTalonario, sesion);			
			
			//DEVOLUCION NICHOS
			modelTalNichos.setIdSorteo(sesion.pkSorteo);
			modelTalNichos.setIdSector(idsector);
			modelTalNichos.setIdnicho(idnicho);		
			modelTalNichos.setIdtalonario(idTalonario);	
			modelTalNichos.setFormato(formato);
			modelTalNichos.devolvertalonariosfc5(folioTalonario, sesion);			
			
			
			//DEVOLUCION SECTORES
			modelTalSectores.setIdSorteo(sesion.pkSorteo);
			modelTalSectores.setIdSector(idsector);				
			modelTalSectores.setIdtalonario(idTalonario);	
			modelTalSectores.setFormato(formato);
			modelTalSectores.devolvertalonariosfc5(folioTalonario, sesion);			
			
			// model.devolvertalonariosColaboradorfc5(folioTalonario, sesion);			 
			 
		}else{			
			   if( idnicho != 0 ){			   
				   
					//DEVOLUCION NICHOS
					modelTalNichos.setIdSorteo(sesion.pkSorteo);
					modelTalNichos.setIdSector(idsector);
					modelTalNichos.setIdnicho(idnicho);		
					modelTalNichos.setIdtalonario(idTalonario);	
					modelTalNichos.setFormato(formato);
					modelTalNichos.devolvertalonariosfc5(folioTalonario, sesion);			
					
					
					//DEVOLUCION SECTORES
					modelTalSectores.setIdSorteo(sesion.pkSorteo);
					modelTalSectores.setIdSector(idsector);				
					modelTalSectores.setIdtalonario(idTalonario);	
					modelTalSectores.setFormato(formato);
					modelTalSectores.devolvertalonariosfc5(folioTalonario, sesion);				   			   
				   
				   
				     // model.devolvertalonariosNichosfc5(folioTalonario, sesion);				
			     }else{				
				        if( idsector != 0 ){
				        	
				        	//DEVOLUCION SECTORES
							modelTalSectores.setIdSorteo(sesion.pkSorteo);
							modelTalSectores.setIdSector(idsector);				
							modelTalSectores.setIdtalonario(idTalonario);	
							modelTalSectores.setFormato(formato);
							modelTalSectores.devolvertalonariosfc5(folioTalonario, sesion);					        	
				        	
								// model.devolvertalonariosSectoresfc5(folioTalonario, sesion);				
						}else{		
							// model.devolvertalonariosBovedafc5(folioTalonario, sesion);							
						}				
			      }			
		}			
	
		
	
		/*PrintWriter out = response.getWriter();
		String valida = "";
		out.println(valida);*/
		
		
	}
	
	

	/*protected void BuscarSector(HttpServletRequest request,
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

	}*/
	
	
	

}
