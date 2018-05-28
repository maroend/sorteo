package com.sorteo.usuarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.core.SuperModel;


public class mPermisos extends SuperModel {	

	private int id;	
	private String permiso;
	private String descripcion;
	private char tipo;
	private int idrole;
	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public int getIdrole() {
		return idrole;
	}

	public void setIdrole(int idrole) {
		this.idrole = idrole;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public mPermisos () {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	 public ResultSet listar(){
	    	
	    	String sql = "SELECT * FROM PERMISOS";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	 
	 public int contar(){
		   
		   String sql = "SELECT * FROM PERMISOS";
		   int numero = db.ContarFilas(sql);
		   return numero;
		   
	   }
	 
	 
	 
	 public ResultSet paginacion(int pg, int numreg,String search){
		   	
		   String sql = "SELECT * ";
		          sql += "FROM PERMISOS ";
		          
		          if(search!=""){
		        	  sql += " WHERE PERMISO LIKE '%"+search+"%'  ";
		          }
		          
		          sql += "ORDER BY PK1 ASC ";
		          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
		          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
	   	
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	   }
	 
	 
	 
	 public void ObtenerRole(mPermisos obj){
		   	
		   	String sql = "SELECT ROLE FROM ROLES WHERE PK1 =  '"+obj.getIdrole()+"'";
		   	
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if(rs.next()) {
					
					obj.setRole(rs.getString("ROLE"));			  
					//obj.setDescripcion(rs.getString("DESCRIPCION"));
					
				  
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	
	}
	 
	 
	 public ResultSet obtenerPermisos(char tipo){
		   	
		   String sql = "SELECT * FROM PERMISOS WHERE TIPO='"+tipo+"' ORDER BY PERMISO"; 	
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	   }
	 
	 
	 
	 public int registrar(mPermisos obj,String usuario){
			db.con();
		    String sql = "INSERT INTO PERMISOS (PERMISO,TIPO,DESCRIPCION,USUARIO,FECHA_R) VALUES ('"+obj.getPermiso()+"','"+obj.getTipo()+"','"+obj.getDescripcion()+"','"+usuario+"',GETDATE())";
		    System.out.println(sql);
		    int res = db.execQuery(sql);
		    db.desconectar();
			return res;
	}
	 
	 
	 
	 public void BuscarEditar(mPermisos obj){
		   	
		   	String sql = "SELECT PK1,PERMISO,TIPO,DESCRIPCION FROM PERMISOS WHERE PK1 ='"+obj.getId()+"' ";
		   	
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if(rs.next()) {
					
					//obj.setId((rs.getString("PK1"));
					obj.setPermiso(rs.getString("PERMISO"));
					obj.setTipo(rs.getString("TIPO").charAt(0));
					obj.setDescripcion(rs.getString("DESCRIPCION"));
		         
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	
	 }
		   
	 
	 
	 public int Editar(mPermisos obj){
			db.con();
		    String sql = "UPDATE PERMISOS SET PERMISO ='"+obj.getPermiso()+"', TIPO ='"+obj.getTipo()+"', DESCRIPCION = '"+obj.getDescripcion()+"' WHERE PK1 ='"+obj.getId()+"'    ";
		    System.out.println(sql);
		    int res = db.execQuery(sql);
		    db.desconectar();
			return res;
		}
	 
	 
	 
	 
	   public int Borrar(mPermisos obj){
	   	
	   	db.con();
	   	String sql = "DELETE FROM PERMISOS WHERE PK1='"+obj.getId()+"'";
	   	int res = db.execQuery(sql);
		return res;
			
		}
	
	   
	   
	   public void permitirPermisos(String rol, String permisos){
		   	
		   db.con();
		   String sql = "";
		   
		   String Str = new String(permisos);
		   
		   for (String permiso: Str.split("-")){
		         
		         this.eliminarPermiso(rol, permiso);
		         
		         sql ="INSERT INTO ROLES_PERMISOS (PK_ROLE,PK_PERMISO) VALUES('"+rol+"','"+permiso+"')";
				       db.execQuery(sql);
		      
		   }

		   }
	   
	   
	   public void restringirPermisos(String rol, String permisos){
		   
		   db.con();
		   String sql = "";
		   
		   String Str = new String(permisos);
		   
		   for (String permiso: Str.split("-")){
		         
		         sql ="DELETE FROM ROLES_PERMISOS WHERE PK_ROLE = '"+rol+"'  AND PK_PERMISO = '"+permiso+"' ";
				       db.execQuery(sql);
		      
		   }

		   }
	   
	   
	   
	     
	   private boolean eliminarPermiso(String rol,String permiso){
		    db.con();
			String sql = "DELETE FROM ROLES_PERMISOS WHERE PK_ROLE = '"+rol+"' AND PK_PERMISO = '"+permiso+"' ";
			int res = db.execQuery(sql);

		    if(res<0){
				return true;
			}else {
				return false;
			}
		   	
			}
	   
	   
	   
	   public boolean existePermiso(String rol,String permiso){
		    
		     int max = 0;
		   try{
		    String sql = "SELECT COUNT(PK1) AS 'MAX' FROM ROLES_PERMISOS WHERE PK_ROLE = '"+rol+"' AND PK_PERMISO = '"+permiso+"' ";
		    db.con();
		    ResultSet res = db.getDatos(sql);
		    if(res != null && res.next())
		     max = res.getInt("MAX");
		   }
		   catch(Exception ex) { }

		      if(max<=0){
		    return true;
		   }else {
		    return false;
		   }
		    
		  }
	
	
	
	
	

}
