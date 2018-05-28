package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.Factory;
import com.core.Factory.LOG;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mAsignacionSectores extends SuperModel {
	
	
	private int idSector;
	private int idSorteo;
	private String clave;  
	private String[] sectores;
	private String sorteo;  
	private String descripcion;  
	
	private String fechainico;
	private String fechatermino;
	
	
	private String imagen;
	
	private int activo;
	
	private int totalregistros = 0;

	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}
	
	
	public int getIdSector() {
		return idSector;
	}


	public void setIdSector(int id) {
		this.idSector = id;
	}


	public int getIdSorteo() {
		return idSorteo;
	}


	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}


	public String getClave() {
		return clave;
	}
    
	
	public String[] getSectores() {
		return sectores;
	}


	public void setSectores(String[] sectores) {
		this.sectores = sectores;
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


	

	public mAsignacionSectores() {
		// TODO Auto-generated constructor stub
	}
	
	

	public String Sorteo(int idsorteo){
	   	 
	   	 db.con();
	   	 String sql = "SELECT * FROM SORTEOS WHERE PK1 = "+idsorteo+"";
	   	String sorteo = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						
						 sorteo = rs.getString("CLAVE")+"-"+rs.getString("SORTEO");
					   }
					
					rs.close(); 			
				}
	   	} catch (SQLException e) { Factory.Error(e, sql); }
	   	 
	   	 return sorteo;
	   	 
	    }
	
 
	
	public ResultSet listarModal(){
	    	
	    	String sql = "SELECT PK1 FROM SECTORES";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
	}
	 
	 
	public int contarModal(String search, int idsorteo) {
		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO = " + idsorteo;

		if (search != "") {
			sql += " AND ((SECTOR LIKE '%" + search + "%') OR (CLAVE LIKE '%" + search + "%'))  ";
		}

		int numero = db.ContarFilas(sql);
		return numero;
	}
	 
	public ResultSet paginacionModal(int pg, int numreg, String search,
			int idsorteo) {
		
		String sql = "SELECT S.PK1, S.CLAVE, S.SECTOR, S.DESCRIPCION"
				+ ",(select TOP 1 PK_SECTOR from SORTEOS_ASIGNACION_SECTORES where PK_SECTOR = S.PK1 AND PK_SORTEO = '" + idsorteo + "') AS 'PK_SECTOR'"
				+ " FROM SECTORES S WHERE PK_SORTEO = " + idsorteo;

		if (search != "") {
			sql += " AND ((S.SECTOR LIKE '%" + search
					+ "%') OR (S.CLAVE LIKE '%" + search + "%'))  ";
		}

		sql += " ORDER BY S.PK1 ASC ";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if
															// you need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println("ASIGNACION SECTORES SQL:>>>" + sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	 
	
	
	
	 public ResultSet listar(){
	    	
	    	String sql = "SELECT * FROM SORTEOS_ASIGNACION_SECTORES";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	public int contar(SesionDatos sesion,String search) {
		/*
		String sql;
		if (sesion.misSorteos == 0) {
	
			// TABLA DE ASIGNACION
			sql = "SELECT SA.PK1 FROM SORTEOS_ASIGNACION_SECTORES SA, SECTORES S WHERE SA.PK_SORTEO = '"
					+ getIdsorteo() + "' AND SA.PK_SECTOR = S.PK1";
		}
		else {
			// TABLA DE RELACION - USUARIOS
			sql = "SELECT S.PK1 FROM SECTORES S, SORTEOS_USUARIOS_SECTORES SUS WHERE S.PK1 = SUS.PK_SECTOR AND SUS.PK_USUARIO=" + sesion.pkUsuario;
		}
		
		if(search!=""){
			sql += " AND ((S.SECTOR LIKE '%"+search+"%') OR (S.CLAVE LIKE '%" + search + "%')) ";
		}
		*/
		String sql;
		if (sesion.misSorteos == 0) {
			// TABLA DE ASIGNACION
			sql = "SELECT COUNT(*) AS 'VALUE' FROM SECTORES S WHERE S.PK_SORTEO=" + getIdSorteo();
			/* ---OLD---
			sql = "SELECT COUNT(S.PK1) AS 'VALUE'"
				+ " FROM SORTEOS_ASIGNACION_SECTORES SA, SECTORES S"
				+ " WHERE SA.PK_SORTEO = '"+this.getIdsorteo()+"'"
				+ " AND SA.PK_SECTOR = S.PK1"
			    + " AND S.PK_SORTEO = "+this.getIdsorteo();
			    */ 
		}
		else {
			// TABLA DE RELACION - USUARIOS
			/*sql = "SELECT COUNT(*) AS 'VALUE' FROM SORTEOS_USUARIOS SU, SECTORES S"
					+ " WHERE SU.PK_SORTEO=S.PK_SORTEO AND SU.PK_SORTEO="+getIdSorteo()
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario;*/			
			
			
			sql = "SELECT COUNT(*) AS 'VALUE' FROM SORTEOS_USUARIOS SU, SECTORES S"
					+ " WHERE SU.PK_SECTOR = S.PK1"
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario 
					+ " AND S.PK_SORTEO = "+this.getIdSorteo();
			
						
			
			
			/* *
			sql = "SELECT COUNT(S.PK1) AS 'VALUE'"
				+ " FROM SORTEOS_USUARIOS_SECTORES SUS, SECTORES S"
				+ " WHERE SUS.PK_SORTEO=" + getIdsorteo()
				+ " AND SUS.PK_USUARIO=" + sesion.pkUsuario
				+ " AND SUS.PK_SECTOR = S.PK1"
				+ " AND S.PK_SORTEO = "+this.getIdsorteo();
			/**/
		}

		if(!search.equals("")){ 
			sql += " AND ((S.SECTOR LIKE '%"+search+"%') OR (S.CLAVE LIKE '%" + search + "%')) ";
		}
		
		System.out.println(">>>>SQL COUNT SECTORES: " + sql);    	
		
		return db.getValue(sql, 0);
	}	 
	 
	public ResultSet paginacion(int pg, int numreg,String search, SesionDatos sesion){

		String sql;
		if (sesion.misSorteos == 0) {
			// TABLA DE ASIGNACION
			sql = "SELECT * FROM SECTORES S WHERE S.PK_SORTEO=" + getIdSorteo();
			/* *
			sql = "SELECT S.PK1 AS PK1, S.SECTOR, S.CLAVE, S.DESCRIPCION, SA.PK1 AS ID_AS"
				+ " FROM SORTEOS_ASIGNACION_SECTORES SA, SECTORES S"
				+ " WHERE SA.PK_SORTEO = '"+this.getIdsorteo()+"'"
				+ " AND SA.PK_SECTOR = S.PK1"
			    + " AND S.PK_SORTEO = "+this.getIdsorteo();
			/**/
		}
		else {
			
			// TABLA DE RELACION - USUARIOS			
			
			
			
			sql = "SELECT S.* FROM SORTEOS_USUARIOS SU, SECTORES S"
					+ " WHERE SU.PK_SECTOR = S.PK1"
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario 
					+ " AND S.PK_SORTEO = "+this.getIdSorteo();		
			
			
			
			
			/*sql = "SELECT SU.PK_SORTEO,SU.PK_SECTOR,SU.PK_NICHO,S.* FROM SORTEOS_USUARIOS SU, SECTORES S"
					+ " WHERE SU.PK_SORTEO=S.PK_SORTEO AND SU.PK_SORTEO="+getIdSorteo()
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario;*/		
			
			/* *
			sql = "SELECT S.PK1 AS PK1, S.SECTOR, S.CLAVE, S.DESCRIPCION, SUS.PK1 AS ID_AS"
				+ " FROM SORTEOS_USUARIOS_SECTORES SUS, SECTORES S"
				+ " WHERE SUS.PK_SORTEO=" + getIdsorteo()
				+ " AND SUS.PK_USUARIO=" + sesion.pkUsuario
				+ " AND SUS.PK_SECTOR = S.PK1"
				+ " AND S.PK_SORTEO = " + this.getIdsorteo();
			/**/
		}

		if(search!=""){
			sql += " AND ((S.SECTOR LIKE '%"+search+"%') OR (S.CLAVE LIKE '%" + search + "%')) ";
		}

		//String sqltotalreg =  sql;
		
		sql += " ORDER BY S.PK1 ASC";
		sql += " OFFSET ("+(pg-1)*numreg+") ROWS"; //-- not sure if you need -1
		sql += " FETCH NEXT "+numreg+" ROWS ONLY";
		
		
		System.out.println(">>>>SQL SECTORES: " + sql); 
		
		ResultSet rs = db.getDatos(sql);

		//total reg	
		//System.out.println(">>>>SQL TOTAL REG: " + sqltotalreg);    	
		//this.setTotalregistros( db.ContarFilas(sqltotalreg));
		
		return rs;
	}
	 
	 
	/* public void guardarAsignacionSorteoSector1(String[] sectores) {
			// TODO Auto-generated method stub
			
		}*/
	 
	 
	public void guardarAsignacionSorteoSector(String[] sectores, mAsignacionSectores obj, SesionDatos sesion) {
		
		db.con();

		for (String s : sectores) {

			String sql = "SELECT PK1 FROM SORTEOS_ASIGNACION_SECTORES WHERE PK_SORTEO ='" + obj.getIdSorteo() + "' AND PK_SECTOR = '" + s + "'";
			ResultSet rs = db.getDatos(sql);

			try {
				if (!rs.next()) {

					String sql2 = "INSERT INTO SORTEOS_ASIGNACION_SECTORES (PK_SORTEO,PK_SECTOR) VALUES ('" + obj.getIdSorteo() + "','" + s + "')";
					System.out.println(sql2);
					db.execQuery(sql2);

				}
			} catch (SQLException e) {
				Factory.Error(e, sql);
			}

		}

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSorteoSector", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	 
	 
	 
	public boolean ExisteSorteo(String usr){
		   	
		   	String sql = "SELECT PK1 FROM SORTEOS WHERE CLAVE = '"+usr+"'";
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if( rs.next()){
					 
					return true;
					
				}else{
					
					return false;
				}
				
		   	} catch (SQLException e) { Factory.Error(e, sql); }
			return false;
	}
	
	public int numeroTalonarios(mAsignacionSectores obj){
		 
		// String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = "+obj.getIdsorteo()+" AND PK_SECTOR = '"+obj.getIdSector()+"'  ";
		
		 String sql = "SELECT COUNT(*) AS MAX FROM VSECTORES_TALONARIOS WHERE PK_SECTOR = '"+obj.getIdSector()+"'  ";
		
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		 } catch (SQLException e) { Factory.Error(e, sql); }
		 
		 return max;
	}
	
	public int numeroBoletos(mAsignacionSectores obj){
		 
		 db.con();
		 
		// String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = "+obj.getIdsorteo()+" AND PK_SECTOR = '"+obj.getIdSector()+"' ";
		 String sql = "SELECT COUNT(*) AS MAX FROM SECTORES_BOLETOS WHERE PK_SECTOR = '"+obj.getIdSector()+"' ";
		 
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		 } catch (SQLException e) { Factory.Error(e, sql); }
		 
		 return max;
	}
	
	public int MontoSector(mAsignacionSectores obj){
		 
		 db.con();
		// String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = "+obj.getIdsorteo()+" AND PK_SECTOR = '"+obj.getIdSector()+"' ";
		 String sql = "SELECT SUM(MONTO) AS TOTAL FROM VSECTORES_TALONARIOS WHERE PK_SECTOR = '"+obj.getIdSector()+"' ";
		 
		 ResultSet rs = db.getDatos(sql);
		 int total = 0;
		 try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
			    }
		} catch (SQLException e) { Factory.Error(e, sql); }
		return total;
	}

	
	// Para configuracion
	public boolean ExisteCarga(){
		
		/*String sql = "SELECT COUNT(PK1) AS 'MAX' FROM [SORTEOS_SECTORES_TALONARIOS]"
				+ " WHERE PK_SECTOR = " + getIdSector()
				+ " AND PK_SORTEO = " + getIdsorteo();*/
		
		
		String sql = "SELECT COUNT(PK_SECTOR) AS 'MAX' FROM [VSECTORES_TALONARIOS]"
		+ " WHERE PK_SECTOR = " + getIdSector();
	

		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next())
				return rs.getInt("MAX") > 0;

		} catch (SQLException e) { Factory.Error(e, sql); }
		return false;
	}

	public int eliminaCarga(SesionDatos sesion) {
		
		int val = borraTalonariosDeSector(sesion.pkSorteo, sesion.pkSector);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			//this.Log(sesion, LOG.BORRADO, this, "eliminaCarga", sesion.toShortString());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }

		return val;
	}
	
	
	public int borraTalonariosDeSector(int pkSorteo, int pkSector) {
		String sql="";

		// PASO 1: Se actualiza la columna de ASIGNADO EN EL BOLETO de sorteo
		//sql +=
				//" UPDATE SORTEOS_TALONARIOS SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_TALONARIOS RSO, SORTEOS_SECTORES_TALONARIOS AS RSE WHERE RSO.PK_SORTEO=" + pkSorteo + " AND RSO.PK_TALONARIO=RSE.PK_TALONARIO AND RSE.PK_SECTOR=" + pkSector + 
			//	" UPDATE SORTEOS_BOLETOS    SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_BOLETOS RSO,    SORTEOS_SECTORES_BOLETOS AS RSE    WHERE RSO.PK_SORTEO=" + pkSorteo + " AND RSO.PK_BOLETO=RSE.PK_BOLETO       AND RSE.PK_SECTOR=" + pkSector;

		// PASO 2) Se borran los boletos y talonarios asignados a los sub-niveles
		sql =
			//	" DELETE [SORTEOS_COLABORADORES_TALONARIOS] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector +
				" DELETE [COLABORADORES_BOLETOS]    WHERE PK_SECTOR=" + pkSector +
				//" DELETE [SORTEOS_NICHOS_TALONARIOS]        WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector +
				" DELETE [NICHOS_BOLETOS]  WHERE  PK_SECTOR=" + pkSector;
		
		db.execQuery(sql);
		
		// PASO 3) Se borran las relaciones de boletos y talonarios asignados al sector
		sql =
				//" DELETE [SORTEOS_SECTORES_TALONARIOS]   WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector +
				" DELETE [SECTORES_BOLETOS]      WHERE  PK_SECTOR=" + pkSector;//PK_SORTEO=" + pkSorteo + " AND
		
		// PASO 4) Se borra tambien el seguimiento
	/*	sql +=
				" DELETE [SEGUIMIENTO] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector
				+ " AND NOT (NIVEL='Boveda' OR NIVEL='Sorteo')";*/

		return db.execQuery(sql);
	}
	
	public int eliminaSector(SesionDatos sesion) {
		String sql =
				" DELETE [SORTEOS_ASIGNACION_SECTORES]"
				+ " WHERE PK_SORTEO=" + sesion.pkSorteo
				+ " AND PK_SECTOR=" + sesion.pkSector;
		int val = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaSector", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return val;
	}

}


