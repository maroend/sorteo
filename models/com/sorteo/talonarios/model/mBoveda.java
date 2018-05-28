package com.sorteo.talonarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mBoveda extends SuperModel {

	private int id;
	private String clave;  
	private String sorteo;  
	private String descripcion;  
	
	private String fechainico;
	private String fechatermino;
	
	private String imagen;
	
	private int activo;
	
	
	private int numTalonarios;
	private int numBoletos;
	
	
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


	public int getNumTalonarios() {
		return numTalonarios;
	}


	public void setNumTalonarios(int numTalonarios) {
		this.numTalonarios = numTalonarios;
	}


	public int getNumBoletos() {
		return numBoletos;
	}


	public void setNumBoletos(int numBoletos) {
		this.numBoletos = numBoletos;
	}


	public mBoveda() {
		// TODO Auto-generated constructor stub
	}
	
	
	 public ResultSet listar(){
	    	
	    	String sql = "SELECT * FROM SORTEOS";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	 public int contar(SesionDatos sesion){
		   
		  /* String sql = "SELECT * FROM SORTEOS";*/
		 
		 String sql
			= "SELECT S.*"
			+ " FROM SORTEOS S, SORTEOS_USUARIOS SU"
			+ " WHERE SU.PK_SORTEO=S.PK1 AND SU.PK_USUARIO=" + sesion.pkUsuario;
		 
		 
		   int numero = db.ContarFilas(sql);
		   return numero;
		   
	   }
	 
	 
	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion) {
		
		String sql
				= "SELECT S.*"
				+ " FROM SORTEOS S, SORTEOS_USUARIOS SU"
				+ " WHERE SU.PK_SORTEO=S.PK1 AND SU.PK_USUARIO=" + sesion.pkUsuario;
		
		if (search != "") {
			sql += " WHERE S.SORTEO LIKE '%" + search + "%'  ";
		}
		
		sql += "ORDER BY S.FECHA_I DESC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";
		
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	 
	 
	public int numeroTalonarios(mBoveda obj){
		 String sql = "SELECT COUNT(*) AS 'MAX' FROM TALONARIOS WHERE PK_SORTEO="+obj.getId();
		 return db.Count(sql);
		 /*
		 db.con();
		 String sql = "SELECT COUNT(*) AS MAX FROM TALONARIOS WHERE SORTEO = '"+obj.getId()+"'";
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return max;
		 */
	 }
	 
	 
	public int numeroBoletos(mBoveda obj)
	{
		String sql = "SELECT COUNT(*) AS 'MAX' FROM BOLETOS B,TALONARIOS T WHERE B.PK_TALONARIO=T.PK1 AND T.PK_SORTEO="+obj.getId();

		return db.Count(sql);
    	 /*
		 db.con();
		 
		 String sql = "SELECT COUNT(*) AS MAX FROM BOLETOS WHERE SORTEO = '"+obj.getId()+"'";
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return max;
		 */
	}
     
     
     
     public void cargartalonarios(mBoveda obj){
    	 
    	 db.con();
    	 String sql = "SELECT * FROM TALONARIOS WHERE SORTEO = "+obj.getId()+"";
    	 String sqlINSERT = "";
    	 int numtalonarios = 0;
    	 
    	 ResultSet rs = db.getDatos(sql);
    	 try {
 			if (rs != null) {
 				
 				 while(rs.next())
 				   {
 					numtalonarios++;
 				    sqlINSERT = "INSERT INTO SORTEOS_TALONARIOS (PK_TALONARIO,PK_SORTEO,FOLIO,NUMBOLETOS,MONTO,FORMATO,USUARIO) VALUES ('"+rs.getString("PK1")+"','"+rs.getString("SORTEO")+"','"+rs.getString("FOLIO")+"','"+rs.getString("NUMBOLETOS")+"','"+rs.getString("MONTO")+"','"+rs.getString("FORMATO")+"','admin')";
 				    db.execQuery(sqlINSERT);

 				   }
 				this.setNumTalonarios(numtalonarios);
 				//rs.close(); 			
 			}
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	 
    	 
    	 
     }
   
     
     
	public int montoSorteo() {
		String sql = "SELECT SUM(COSTO) AS 'VALUE' FROM vBOLETOS ";
		
		return db.getValue(sql, 0);

		/*
		db.con();
		String sql = "SELECT SUM(MONTO) AS TOTAL FROM TALONARIOS WHERE SORTEO = " + getId() + "";
		ResultSet rs = db.getDatos(sql);
		int total = 0;
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return total;
		*/
	}
	 
	 
	 public boolean EliminarCarga(mBoveda obj){
		    db.con();
		    /*
		   	String sql = "DELETE FROM TALONARIOS WHERE PK_SORTEO='"+obj.getId()+"' AND ASIGNADO = 0 ";
		   	       sql += "DELETE FROM BOLETOS WHERE SORTEO='"+obj.getId()+"' AND ASIGNADO = 0 ";
		   	       sql += "DELETE SEGUIMIENTO WHERE PK_SORTEO="+obj.getId();
		   	       */
		   	String sql = "DELETE FROM BOLETOS"
		   				+ " DELETE FROM TALONARIOS";
		   	int res = db.execQuery(sql);
		   	
		 // --- Se guarda un registro de seguimiento ---
			try {
				//this.Log(sesion, LOG.REGISTRO, this, "eliminaCarga", sesion.toShortString());
			}catch(Exception ex) { Factory.Error(ex, "Log"); }
		   	
		   	
		    db.desconectar();
		    if(res==1){ return true; }else{ return false;}

	 }
	 
	 
	 
	 public boolean ExisteCarga(mBoveda obj){
		   	
		   	String sql = "SELECT PK1 FROM SORTEOS WHERE PK1 = '"+obj.getId()+"' AND CARGA = 1";
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if( rs.next()){
					 
					return true;
					
				}else{
					
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		   	
			}
	 
	 
	 

}
