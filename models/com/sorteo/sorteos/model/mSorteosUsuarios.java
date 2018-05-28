package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;

public class mSorteosUsuarios extends SuperModel {
	
	//private static final boolean TRUE = false;

		private int id;      //PRIMARY KEY
		private int idsorteo;
		private int idusuario;
		


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}
		
		
		public int getIdsorteo() {
			return idsorteo;
		}

		public void setIdsorteo(int idsorteo) {
			this.idsorteo = idsorteo;
		}

		public int getIdusuario() {
			return idusuario;
		}

		public void setIdusuario(int idusuario) {
			this.idusuario = idusuario;
		}



	public mSorteosUsuarios() {
		// TODO Auto-generated constructor stub
		
	
	}
	

	
	
	 public ResultSet listarModal(){
	    	
	    	String sql = "SELECT PK1 FROM ROLES";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	 public int contarModal(String search){
		   
		 String sql = "SELECT U.PK1,U.USUARIO,U.NOMBRE,U.APATERNO,U.AMATERNO, CONCAT(U.NOMBRE,' ',U.APATERNO,' ',U.AMATERNO) AS NOMBREC,R.ROLE,(select TOP 1 PK_USUARIO from SORTEOS_USUARIOS  where PK_USUARIO = U.PK1 AND PK_SORTEO = '"+idsorteo+"' ) AS 'PK_USUARIO' ";
         sql += "FROM USUARIOS U, ROLES R, ROLES_USUARIO RU  ";
         sql += "WHERE U.PK1 = RU.PK_USUARIO AND R.PK1 = RU.PK_ROLE ";
		   
		   if(search!=""){
	        	  sql += " AND ((U.NOMBRE LIKE '%"+search+"%') OR (U.APATERNO LIKE '%"+search+"%') OR (U.AMATERNO LIKE '%"+search+"%') OR (U.USUARIO LIKE '%"+search+"%'))  ";
	          }
		   int numero = db.ContarFilas(sql);
		   return numero;
		   
	   }
	 
	 
	 public ResultSet paginacionModal(int pg, int numreg,String search, int idsorteo){
		   	
		   String sql = "SELECT U.PK1,U.USUARIO,U.NOMBRE,U.APATERNO,U.AMATERNO, CONCAT(U.NOMBRE,' ',U.APATERNO,' ',U.AMATERNO) AS NOMBREC,R.ROLE,(select TOP 1 PK_USUARIO from SORTEOS_USUARIOS  where PK_USUARIO = U.PK1 AND PK_SORTEO = '"+idsorteo+"' ) AS 'PK_USUARIO' ";
		          sql += "FROM USUARIOS U, ROLES R, ROLES_USUARIO RU  ";
		          sql += "WHERE U.PK1 = RU.PK_USUARIO AND R.PK1 = RU.PK_ROLE ";
		          
		          if(search!=""){
		        	  sql += " AND ((U.NOMBRE LIKE '%"+search+"%') OR (U.APATERNO LIKE '%"+search+"%') OR (U.AMATERNO LIKE '%"+search+"%') OR (U.USUARIO LIKE '%"+search+"%'))  ";
		          }
		          
		          sql += "ORDER BY NOMBRE ASC ";
		          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
		          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
	   	
		          
		          System.out.println(sql);
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	   }
	 
	 
	
	
	public void AgregarUsuarioSorteo(mSorteosUsuarios obj, SesionDatos sesion) {
		
		String sql = "SELECT PK1 FROM SORTEOS_USUARIOS WHERE PK_USUARIO ="+obj.getIdusuario()+" AND PK_SORTEO = "+obj.getIdsorteo()+" ";
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {
			/*	String sql1 = "UPDATE ROLES_USUARIO SET PK_ROLE = '"+obj.getIdrole()+"' WHERE PK_USUARIO =  '"+obj.getId()+"' ";					
				int res1 = db.execQuery(sql1); */
			}else{

				sql = "INSERT INTO SORTEOS_USUARIOS (PK_USUARIO,PK_SORTEO,USUARIO) VALUES ('"+obj.getIdusuario()+"','"+obj.getIdsorteo()+"','"+sesion.nickName+"')";
				 System.out.println("INSERT usu: "+sql);
				int res = db.execQuery(sql);

				// --- Se guarda un registro de seguimiento ---
				try {
					this.Log(sesion, LOG.REGISTRO, this, "AgregarUsuarioSorteo", "sor=" + obj.getIdsorteo() + ", usr=" + obj.getIdusuario() + ", res="+res);
				} catch(Exception ex) { Factory.Error(ex, "Log"); }
				
				
				/* --- ASIGNAR SORTEO POR DEFAULT --- */
				
				sql = "UPDATE USUARIOS SET PK_SORTEO="+obj.getIdsorteo()+" WHERE PK1="+obj.getIdusuario();
				db.execQuery(sql);
				// --- Se guarda un registro de seguimiento ---
				try {
					this.Log(sesion, LOG.EDITADO, this, "AgregarUsuarioSorteo", "sor=" + obj.getIdsorteo() + ", usr=" + obj.getIdusuario() + ", sorteo inicial");
				} catch(Exception ex) { Factory.Error(ex, "Log"); }
				
				/* -----------------------------------*/
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			com.core.Factory.Error(e, sql);
		}
	}
	
	public ResultSet listar() {
   	
	   	String sql = "SELECT * FROM USUARIOS";
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
   	
	}
  
  
  
 
  public int contar(int idsorteo,String search){
	   
	   String sql = "SELECT U.USUARIO,U.NOMBRE,U.APATERNO,U.AMATERNO FROM SORTEOS_USUARIOS SU,USUARIOS U WHERE U.PK1 = SU.PK_USUARIO AND SU.PK_SORTEO = '"+idsorteo+"' ";
	   
	   if(search!=""){
     	  sql += " AND ((U.NOMBRE LIKE '%"+search+"%') OR (U.APATERNO LIKE '%"+search+"%') OR (U.AMATERNO LIKE '%"+search+"%') OR (U.USUARIO LIKE '%"+search+"%'))  ";
       }
	   
	   int numero = db.ContarFilas(sql);
	   return numero;
	   
  }
  
  
  
  public ResultSet paginacion(int pg, int numreg,String search,int idsorteo){
	   	
	   String sql = "SELECT U.PK1, U.USUARIO,U.NOMBRE,U.APATERNO,U.AMATERNO, CONCAT(U.NOMBRE,' ',U.APATERNO,' ',U.AMATERNO) AS NOMBREC,R.ROLE ";
	          sql += "FROM USUARIOS U, ROLES R, ROLES_USUARIO RU, SORTEOS_USUARIOS SU  ";
	            sql += "WHERE U.PK1 = RU.PK_USUARIO AND R.PK1 = RU.PK_ROLE AND U.PK1 = SU.PK_USUARIO AND SU.PK_SORTEO = '"+idsorteo+"' ";
	          
	            
	            
	          if(search!=""){
	        	  sql += " AND ((U.NOMBRE LIKE '%"+search+"%') OR (U.APATERNO LIKE '%"+search+"%') OR (U.AMATERNO LIKE '%"+search+"%') OR (U.USUARIO LIKE '%"+search+"%'))  ";
	          }
	          
	          sql += "ORDER BY PK1 ASC ";
	          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
	          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
  	
	          System.out.println(">>>>>pag"+sql);
  	ResultSet rs = db.getDatos(sql);
  	return rs;
  }
  
  
  
  public int BorrarUsuario(mSorteosUsuarios obj, SesionDatos sesion){
	   	
	   	db.con();
	   
	   	
	   	String sql = "UPDATE USUARIOS SET PK_SORTEO = NULL, PK_SECTOR = NULL, PK_NICHO = NULL WHERE PK1='"+obj.getIdusuario()+"'";
	   	db.execQuery(sql); 		
	   	
	     sql = "DELETE FROM SORTEOS_USUARIOS WHERE PK_USUARIO='"+obj.getIdusuario()+"' AND PK_SORTEO = '"+obj.getIdsorteo()+ "' ";
		int  res =  db.execQuery(sql);
		
		sql = "DELETE FROM SESION WHERE PK_USUARIO='"+obj.getIdusuario()+"'";
	   	db.execQuery(sql);
	   	
	   	

	   	// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "BorrarUsuario", "sor=" + obj.getIdsorteo()+", usr="+obj.getIdusuario());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }

	   	return res;
	}
  
  
 
  
  
	public int Borrar(mSorteosUsuarios model, SesionDatos sesion){

		db.con();
		String sql = "DELETE FROM ROLES_USUARIOS WHERE PK1='"+model.getIdusuario()+"'";
		int res = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "Borrar", "usr="+model.getIdusuario());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
	
		return res;
	}
	
	


}
