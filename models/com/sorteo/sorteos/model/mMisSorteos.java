package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import com.core.SuperModel;

public class mMisSorteos extends SuperModel {
	
	private int id;
	private String clave;  
	private String sorteo;  
	private String descripcion;  
	
	private String fechainico;
	private String fechatermino;
	
	private String imagen;
	
	private int activo;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String getSorteo() {
		return sorteo;
	}


	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getFechainico() {
		return fechainico;
	}


	public void setFechainico(String fechainico) {
		this.fechainico = fechainico;
	}


	public String getFechatermino() {
		return fechatermino;
	}


	public void setFechatermino(String fechatermino) {
		this.fechatermino = fechatermino;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	public int getActivo() {
		return activo;
	}


	public void setActivo(int activo) {
		this.activo = activo;
	}


	public mMisSorteos() {
		// TODO Auto-generated constructor stub
	}
	
	
	 public ResultSet listar(){
	    	
	    	String sql = "SELECT * FROM SORTEOS";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	 public int contar(String ID){
		   
		 String sql = "SELECT S.PK1, S.CLAVE,S.SORTEO, S.DESCRIPCION,S.IMAGEN, S.FECHA_I, S.FECHA_M FROM ";
         sql += "SORTEOS S, USUARIOS U, SORTEOS_USUARIOS SU  ";
         sql += "WHERE SU.PK_USUARIO = U.PK1 AND S.PK1 = SU.PK_SORTEO AND U.PK1 = '"+ID+"'  ";
		   int numero = db.ContarFilas(sql);
		   return numero;
		   
	   }
	 
	 
	 public ResultSet paginacion(int pg, int numreg,String search,String ID){
		 
		 
		   	
		   String sql = "SELECT S.PK1, S.CLAVE,S.SORTEO, S.DESCRIPCION,S.IMAGEN, S.FECHA_I, S.FECHA_M FROM ";
		          sql += "SORTEOS S, USUARIOS U, SORTEOS_USUARIOS SU  ";
		          sql += "WHERE SU.PK_USUARIO = U.PK1 AND S.PK1 = SU.PK_SORTEO AND U.PK1 = '"+ID+"'  ";
		          
		          if(search!=""){
		        	  sql += " AND SORTEO LIKE '%"+search+"%'  ";
		          }
		          
		          sql += "ORDER BY PK1 ASC ";
		          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
		          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
	   	
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	   }
	 
	 
	 
	 
	

}
