package com.sorteo.usuarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;


public class mPassword extends SuperModel {
	
	
	long idusuario;
	String usuario; 
	



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String obtenerUsuario() {
		return usuario;
	}




	public long getIdusuario() {
		return idusuario;
	}



	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}
   
	
	


	public mPassword() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public boolean ValidaPassword(String password){
		
		System.out.println(">>>usr: "+this.obtenerUsuario());
		System.out.println(">>>password: "+password);
		boolean validado = false;
		 db.con();
		 String sql = "SELECT * FROM USUARIOS WHERE USUARIO = '"+this.obtenerUsuario()+"' AND PASSWORD = '"+password+"'";
		 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					 while(rs.next())
					   {
						 validado = true;
					   }
					 
					rs.close(); 			
				}else{
					 validado = false;
				   }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
		
		return validado;
	}
	
	
	public void ActualizaPassword(String password, String passwordactual){
		System.out.println(">>>model ActualizaPassword ");
		 db.con();
		 //String sql = "UPDATE USUARIOS SET PASSWORD = '"+password+"' WHERE PK1 ="+this.getIdusuario();
		 String sql = "UPDATE USUARIOS SET PASSWORD = '"+password+"' WHERE USUARIO = '"+this.obtenerUsuario()+"' AND PASSWORD = '"+passwordactual+"'";
			System.out.println(">>>sql act pass: "+sql);
		 db.execQuery(sql);
	}
	
	
	
	
	public String getUsuario(){
	   	 
	   	 db.con();
	   	 String usuario = null; 
	   	 String sql = "SELECT * FROM USUARIOS WHERE PK1 = "+this.getIdusuario();
	   	        
	   	        
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 usuario = rs.getString("NOMBRE") + " "+rs.getString("APATERNO")+ " "+rs.getString("AMATERNO");
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return usuario;
	    }

}
