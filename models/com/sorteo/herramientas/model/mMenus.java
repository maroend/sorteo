package com.sorteo.herramientas.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.core.Factory.LOG;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.sorteo.herramientas.model.mMenus;

public class mMenus extends SuperModel {
	
	
	private int id;	
	private int idSorteo;	

	private String nombre;
	private String url;
	private int padre;
	private int orden;
	private int permiso;	
	private int disponible;	
	
	
	public int getDisponible() {
		return disponible;
	}

	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}
	
	
	
	public String getUrl() {
		return url;
	}
	

	public void setUrl(String url) {
		this.url = url;
	}
	
	

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}	
	
	
	public int getPadre() {
		return padre;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	
	public int getOrden() {
		return orden;
	}

	public void setPadre(int padre) {
		this.padre = padre;
	}
	
	public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}	
	
	
	

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;	}


	
	public mMenus() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ResultSet listar() {
		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public int contar(String search)
	{
		String sql = "SELECT * FROM MENU";
		if (search != "") {
			sql += " WHERE ((MENU LIKE '%" + search + "%') OR (URL LIKE '%" + search + "%')) ";
		}
		int numero = db.ContarFilas(sql);
		return numero;
	}
	
	public ResultSet paginacion(int pg, int numreg, String search)
	{
		String sql = "SELECT * FROM MENU ";

		if (search != "") {
			sql += " WHERE ((MENU LIKE '%" + search + "%') OR (URL LIKE '%" + search + "%')) ";
		}

		sql += " ORDER BY PK1 ASC";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS"; // -- not sure if you
														// need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public int registrar(mMenus obj, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO MENU (MENU,URL,PADRE,ORDEN,DISPONIBLE,PK_PERMISO) VALUES ('"
				+ obj.getNombre() + "','"
				+ obj.getUrl() + "','"
				+ obj.getPadre() + "','"
				+ obj.getOrden() + "',"
				+ obj.getDisponible() + "',"
				+ obj.getPermiso() + ")";
		System.out.println(sql);
		int res = db.execQuery(sql);
		db.desconectar();
		return res;
	}
	 
	public void BuscarEditar(mMenus obj)
	{
		String sql = "SELECT * FROM MENU WHERE PK1 ='" + obj.getId() + "' ";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs.next())
			{
				obj.setNombre(rs.getString("MENU"));
				obj.setUrl(rs.getString("URL"));
				obj.setPadre(rs.getInt("PADRE"));
				obj.setOrden(rs.getInt("ORDEN"));
				obj.setDisponible(rs.getInt("DISPONIBLE"));
				obj.setPermiso(rs.getInt("PK_PERMISO"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 
	 public int Editar(mMenus obj){
			db.con();
		    String sql = "UPDATE MENU SET"
		    + " MENU ='"+obj.getNombre()+"',"
		    + " URL ='"+obj.getUrl()+"',"
		    + " PADRE = '"+obj.getPadre()+"',"
		    + " ORDEN = '"+obj.getOrden()+"',"
		    + " DISPONIBLE = '"+obj.getDisponible()+"',"
		    + " PK_PERMISO = '"+obj.getPermiso()+"'"		    
		    + " WHERE PK1 ='"+obj.getId()+"'";
		    
		    System.out.println(sql);
		    int res = db.execQuery(sql);
		    db.desconectar();
			return res;
		}
	 
	 
	 
	 
	   public int Borrar(mMenus obj){
	   	
	   	db.con();
	   	String sql = "DELETE FROM MENU WHERE PK1='"+obj.getId()+"'";
	   	int res = db.execQuery(sql);
		return res;
			
		}
	   
	   
	 /* public void BorrarSectores(String[] sectores, SesionDatos sesion) {
			
			db.con();
			String sql ="";
			ResultSet rs=null;

			for (String ID : sectores) {
				
				sql = "DELETE FROM SECTORES WHERE PK_SORTEO =" + this.getIdSorteo() + " AND PK1 = "+ID;
				db.execQuery(sql);
				
				sql = "SELECT PK1 FROM NICHOS WHERE PK_SORTEO =" + this.getIdSorteo() + " AND PK_SECTOR = "+ID;
				rs = db.getDatos(sql);
				
				try {
					if (rs != null && rs.next()) {
					while (rs.next())
					{
						sql = "DELETE FROM COLABORADORES WHERE PK_SORTEO =" + this.getIdSorteo() + " AND PK_NICHO = "+rs.getInt("PK1");
						db.execQuery(sql);
					}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				sql = "DELETE FROM NICHOS WHERE PK_SORTEO =" + this.getIdSorteo() + " AND PK_SECTOR = "+ID;
				db.execQuery(sql);
				
				
				
				
				this.Log(sesion, LOG.REGISTRO, this, "SECTOR ELIMINADO", sesion.toShortString());
				
			}

			
		}*/
	   
	   
	   public String Obtener_Padre(){
		   	
		 	String padre="";		
				
				
			 	String sql = "SELECT * FROM MENU WHERE PADRE = 0";
			   	ResultSet rs = db.getDatos(sql);
			  
			   	padre += "<option value=\"0\" >Principal</option>";
			   	try {
					while(rs.next()) {
						padre += "<option value=\""+rs.getString("PK1")+"\" >"+rs.getString("MENU")+"</option>";
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			
			
		  

			return padre;
		}
	   
	   
	   public String Obtener_Permisos(){
		   	
		 	String permisos="";					
				
			 	String sql = "SELECT * FROM PERMISOS ORDER BY PK1 ASC";
			   	ResultSet rs = db.getDatos(sql);			  
			   	
			  // 	permisos += "<option value='0'>Sin permiso</option>";
			   	
			   	try {
					while(rs.next()) {
						permisos += "<option value=\""+rs.getString("PK1")+"\" >"+rs.getString("PK1")+" - "+rs.getString("PERMISO")+"</option>";
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			
			
		  

			return permisos;
		}
	
	
	
	
	

}
