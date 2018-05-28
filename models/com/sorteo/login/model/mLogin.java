package com.sorteo.login.model;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mLogin extends SuperModel {
	
	private int id;
	private String Username;
	private String Password;
	private String nombre;
	private String imagen;
	private int idSorteo;
	private int idSector;
	private int idNicho;
	
	

	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getImagen() {
		return imagen;
	}



	public void setImagen(String imagen) {
		this.imagen = imagen;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUsername() {
		return Username;
	}



	public void setUsername(String username) {
		Username = username;
	}



	public String getPassword() {
		return Password;
	}



	public void setPassword(String password) {
		Password = password;
	}

	public int getIdSorteo() {
		return idSorteo;
	}



	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}



	public int getIdSector() {
		return idSector;
	}



	public void setIdSector(int idSector) {
		this.idSector = idSector;
	}



	public int getIdNicho() {
		return idNicho;
	}



	public void setIdNicho(int idNicho) {
		this.idNicho = idNicho;
	}



	public mLogin() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public boolean ValidarUsuario(mLogin obj){
		
		if(contar(obj)>0){
			
			String sql = "SELECT PK1,USUARIO, PASSWORD,NOMBRE,PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE USUARIO ='"+obj.getUsername()+"'";
			ResultSet rs = db.getDatos(sql);
			
			try {
				while(rs.next()){
				    
					obj.setId(rs.getInt("PK1"));
					obj.setUsername(rs.getString("USUARIO"));
					obj.setNombre(rs.getString("NOMBRE"));
					obj.setPassword(rs.getString("PASSWORD"));
					obj.setIdSorteo(rs.getInt("PK_SORTEO"));
					obj.setIdSector(rs.getInt("PK_SECTOR"));
					obj.setIdNicho(rs.getInt("PK_NICHO"));
					
					this.validaSorteoUsuario();
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}else{
			return false;
		}
		
		
	}
	
	
	private void validaSorteoUsuario(){
		
		String sql = "SELECT PK1,ACTIVO FROM SORTEOS WHERE PK1 ="+this.getIdSorteo();
		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs != null && rs.next()) {
				
				  if(rs.getInt("ACTIVO")==0){
					  sql = "UPDATE USUARIOS SET PK_SORTEO = NULL, PK_SECTOR=NULL, PK_NICHO=NULL WHERE PK1="+ this.getId();
					  db.execQuery(sql);
				  }
				
				 
			}else{
				sql = "UPDATE USUARIOS SET PK_SORTEO = NULL, PK_SECTOR=NULL, PK_NICHO=NULL WHERE PK1="+ this.getId();
				db.execQuery(sql);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	 public int contar(mLogin obj){
		   String sql = "SELECT USUARIO FROM USUARIOS WHERE USUARIO ='"+obj.getUsername()+"'";
		   int numero = db.ContarFilas(sql);
		   return numero;
	 }
	 
	 /*
	 public String prueba(PrintWriter out){
		 String sql = "SELECT USUARIO FROM USUARIOS WHERE USUARIO ='admin'";
		 int numero = db.prueba(sql, out);
		 return "";//"nfilas=" + numero + ", " + db.database_exception;
	 }
	 //*/

}
