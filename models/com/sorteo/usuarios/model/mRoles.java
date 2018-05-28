package com.sorteo.usuarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mRoles extends SuperModel {
	
	private int id;
	private String role;
	private String descripcion;
	private char tipo;
	private int eliminado;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public int getEliminado() {
		return eliminado;
	}

	public void setEliminado(int eliminado) {
		this.eliminado = eliminado;
	}

	public mRoles() {
		// TODO Auto-generated constructor stub
	}
	
	
	 public ResultSet listar(){
	    	
	    	String sql = "SELECT * FROM ROLES";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	 public int contar_filas(String search){
		   
		   String sql = "SELECT * FROM ROLES";
		   
		   if(search!=""){
	        	  sql += " WHERE ROLE LIKE '%"+search+"%'  ";
	          }
		   
		   int numero = db.ContarFilas(sql);
		   return numero;
		   
	   }
	 
	 
	 public ResultSet paginacion(int pg, int numreg,String search){
		   	
		   String sql = "SELECT * ";
		          sql += "FROM ROLES ";
		          
		          if(search!=""){
		        	  sql += " WHERE ROLE LIKE '%"+search+"%'  ";
		          }
		          
		          sql += "ORDER BY PK1 ASC ";
		          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
		          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
	   	
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	   }
	 
	 
	 
	 
	 public void ObtenerRole(mRoles u){
		   	
		   	String sql = "SELECT * FROM ROLES WHERE PK1 =  '"+u.getId()+"'";
		   	
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if(rs.next()) {
					
					u.setRole(rs.getString("ROLE"));			  
					u.setDescripcion(rs.getString("DESCRIPCION"));
					
				  
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	
	}
	 
	 
	 public int registrar(mRoles r,String usuario){
			db.con();
			String sql = "INSERT INTO ROLES (ROLE,DESCRIPCION,TIPO,FECHA_R,USUARIO) VALUES ('"+r.getRole()+"','"+r.getDescripcion()+"','',GETDATE(),'"+usuario+"')";
			int id = db.execQuerySelectId(sql);		
		    System.out.println(">>>SQL ROLE: "+sql);
		   
			return id;
	}
	 
	 
	 
	 public int Editar(mRoles obj){
			db.con();	
			
						
			String sql = "UPDATE ROLES SET ROLE = '"+obj.getRole()+"', DESCRIPCION = '"+obj.getDescripcion()+"' WHERE PK1 =  '"+obj.getId()+"' ";
			
			
		    System.out.println(sql);
		    int res = db.execQuery(sql);    
		    db.desconectar();		  
		    return res;
		    
	   }
	   
	 
	 
	 
	 public int Borrar(mRoles r){
		   	
		   db.con();
		    
		   int res=0;
		    try{		    	
		    	
			   	String sql = "DELETE FROM ROLES WHERE PK1='"+r.getId()+"'";
			   	 res = db.execQuery2(sql);
			   	
		    } catch (SQLException e) {
				//System.out.println("Error de Ejecucion...!!" + e.getMessage());
		    	res=-1;
				//e.printStackTrace();
			} 	
		   	
		   	
			return res;
			
			
			
				
			}
	 
	 public void BorrarDependencias(mRoles obj) {
		   	
	       db.con(); 	      
	      
	       String sql = "DELETE FROM ROLES_PERMISOS WHERE PK_ROLE='"+obj.getId()+"'";
	       db.execQuery(sql); 
	       
	       sql = "DELETE FROM ROLES_USUARIO WHERE PK_ROLE='"+obj.getId()+"'";
	       db.execQuery(sql); 	       
	   
	       sql = "DELETE FROM ROLES WHERE PK1='"+obj.getId()+"'";
	       db.execQuery(sql); 	
	   	
		
			
}
	 
	

}
