package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.core.Factory;
import com.core.Factory.LOG;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mSorteosParalelosColaboradores extends SuperModel {

	private int idSector;
	private int idSorteo;
	private int idNicho;
	private int idColaborador;
	private int idSorteoParalelo;

	private String clave;
	
	private String rbancaria;
	private String colaborador;
	private String descripcion;

	private String nombre;
	private String apellidop;
	private String apellidom;
	int totalregistros = 0;

	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}

	

	public int getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(int idColaborador) {
		this.idColaborador = idColaborador;
	}
	
	public int getIdSorteoParalelo() {
		return idSorteoParalelo;
	}

	public void setIdSorteoParalelo(int idSorteoParalelo) {
		this.idSorteoParalelo = idSorteoParalelo;
	}
	
	
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getIdsector() {
		return idSector;
	}

	public void setIdsector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idNicho) {
		this.idNicho = idNicho;
	}

	public String getRbancaria() {
		return rbancaria;
	}

	public void setRbancaria(String rbancaria) {
		this.rbancaria = rbancaria;
	}

	public String getColaborador() {
		return colaborador;
	}

	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidop() {
		return apellidop;
	}

	public void setApellidop(String apellidop) {
		this.apellidop = apellidop;
	}

	public String getApellidom() {
		return apellidom;
	}

	public void setApellidom(String apellidom) {
		this.apellidom = apellidom;
	}

	public mSorteosParalelosColaboradores() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String Sorteo(int idsorteo) {

		db.con();
		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + idsorteo + "";
		String sorteo = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					sorteo = /*rs.getString("CLAVE") + "-" +*/ rs.getString("SORTEO");
				}

				rs.close();
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return sorteo;

	}
	
	
	public String Sector(int idsector){
	   	 
	   	 db.con();
	   	 String sql = "SELECT * FROM SECTORES WHERE PK1 = "+idsector+"";
	   	String sector = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						
						 sector = /*rs.getString("CLAVE")+"-"+*/rs.getString("SECTOR");
					   }
					
					rs.close(); 			
				}
	   	} catch (SQLException e) { Factory.Error(e, sql); }
	   	 
	   	 return sector;
	   	 
	    }
	
	public String Nicho(int idnicho) {

		db.con();
		String sql = "SELECT * FROM NICHOS WHERE PK1 = " + idnicho + "";
		String nicho = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					nicho = /*rs.getString("CLAVE") + "-" + */ rs.getString("NICHO");
				}

				rs.close();
			}
		} catch (SQLException e) {
			Factory.Error(e, sql);
		}

		return nicho;

	}
	

	public ResultSet listarModal() {

		String sql = "SELECT * FROM [COLABORADORES]";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarModal(int idnicho,String search) {

		String sql = "SELECT PK1,NOMBRE,CLAVE,APATERNO FROM COLABORADORES WHERE PK_NICHO = '"
				+ idnicho + "'";
		
		if (search != "") {
			sql += " AND ((NOMBRE LIKE '%" + search + "%') OR (CLAVE LIKE '%" + search + "%') OR (APATERNO LIKE '%" + search + "%')) ";
		}
		
		int numero = db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacionModal(int pg, int numreg, String search,	int idnicho, int idsector,int idsorteo) {

		/*String sql = "SELECT * ";
		sql += "FROM COLABORADORES WHERE PK_NICHO = '" + idnicho + "'";*/
		// cursos1rx
		

		String sql = "SELECT C.PK1,C.CLAVE,C.NOMBRE,C.APATERNO,(select TOP 1 PK_COLABORADOR from SORTEOS_ASIGNACION_COLABORADORES  where PK_COLABORADOR = C.PK1 AND PK_SORTEO = '"+idsorteo+"' AND PK_SECTOR = '" + idsector + "'  AND PK_NICHO = '" + idnicho + "') AS "
				+ "'PK_COLABORADOR' FROM COLABORADORES C WHERE C.PK_NICHO = '" + idnicho + "'";

		if (search != "") {
			sql += " AND ((C.NOMBRE LIKE '%" + search + "%') OR (C.CLAVE LIKE '%" + search + "%') OR (C.APATERNO LIKE '%" + search + "%'))  ";
		}

		sql += "ORDER BY C.PK1 ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";
		
        System.out.println("ASIGNACION COLABORADORES SQL:>>>"+sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	//

	public ResultSet listar() {

		String sql = "SELECT * FROM SORTEOS_ASIGNACION_COLABORADORES";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}
	
	
	
	public int contarRegistros(int idsorteoparaleloi) {

		
		String sql = "SELECT C.PK1 AS PK1, C.NOMBRE, C.CLAVE, C.DESCRIPCION, C.APATERNO, C.AMATERNO, C.COMISION, C.REFBANCARIA  FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SA, COLABORADORES C WHERE " +
				" SA.PK_COLABORADOR = C.PK1 and SA.PK_SORTEO_PARALELO = " + idsorteoparaleloi;
		
		
		/*String sql = "SELECT SAC.PK1,C.NOMBRE, C.CLAVE,C.APATERNO, C.AMATERNO FROM SORTEOS_ASIGNACION_COLABORADORES SAC, COLABORADORES C"
				+ " WHERE SAC.PK_COLABORADOR = C.PK1"
				+ " AND SAC.PK_SORTEO = " + idsorteoi
				+ " AND SAC.PK_SECTOR = " + idsectori
				+ " AND SAC.PK_NICHO = " + idnichoi
				;*/
		
		/*if (search != "") {
			//
			sql += " AND ((C.NOMBRE LIKE'%" + search
					+ "%') OR (C.APATERNO LIKE '%" + search + "%') OR (C.CLAVE LIKE '%" + search + "%')) ";

		}*/
		
		int numero = db.ContarFilas(sql);
		return numero;//numero;
	}
	

	public int contar(String search,int idsorteoparaleloi,int idnichoi,int idsector) {

			
		String sql = "SELECT  C.NOMBRE, C.CLAVE, C.DESCRIPCION, C.APATERNO, C.AMATERNO, C.COMISION, C.REFBANCARIA  FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SA, COLABORADORES C WHERE " +
				" SA.PK_COLABORADOR = C.PK1 and SA.PK_SORTEO_PARALELO = " + idsorteoparaleloi + ""
				+ " AND SA.PK_SECTOR = '" + idsector + "' "
				+ " AND SA.PK_NICHO = '" + idnichoi + "' ";
		
		if (search != "") {			//
			sql += " AND ((C.NOMBRE LIKE'%" + search
					+ "%') OR (C.APATERNO LIKE '%" + search + "%') OR (C.CLAVE LIKE '%" + search + "%')) ";
		}
		
		sql += " GROUP BY C.PK1, C.NOMBRE, C.CLAVE, C.DESCRIPCION, C.APATERNO, C.AMATERNO, C.COMISION, C.REFBANCARIA";
		
		
		int numero = db.ContarFilas(sql);
		return numero;//numero;
	}

	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion, int idsorteoi,int idsorteoparaleloi, int idnichoi,int idsector) {

		String sql = "SELECT C.PK1 AS PK1, C.NOMBRE, C.CLAVE, C.DESCRIPCION, C.APATERNO, C.AMATERNO, C.COMISION, C.REFBANCARIA,SA.PK_SORTEO_PARALELO,SA.PK_SORTEO_PARALELO_NICHO, SA.PK_SECTOR, SA.PK_NICHO  FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SA, COLABORADORES C WHERE " +
				" SA.PK_COLABORADOR = C.PK1 and SA.PK_SORTEO_PARALELO = " + idsorteoparaleloi + ""
					+ " AND SA.PK_SECTOR = '" + idsector + "' "
						+ "AND SA.PK_NICHO = '" + idnichoi + "'";
		
		
		if (search != "") {			
			sql += " AND ((C.NOMBRE LIKE'%" + search
					+ "%') OR (C.APATERNO LIKE '%" + search + "%') OR (C.CLAVE LIKE '%" + search + "%')) ";
		}
		
		
		//String sqltotalreg =  sql;

		
		sql += " GROUP BY C.PK1, C.NOMBRE, C.CLAVE, C.DESCRIPCION, C.APATERNO, C.AMATERNO, C.COMISION, C.REFBANCARIA, SA.PK_SORTEO_PARALELO, PK_SORTEO_PARALELO_NICHO, SA.PK_SECTOR,SA.PK_NICHO";
		sql += " ORDER BY C.PK1 ASC ";
		
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		//System.out.println("offset:"+sql);
		
		
		
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println("colaboradores:"+sql);
		
		ResultSet rs = db.getDatos(sql);
		
		//total reg	
	//	System.out.println(">>>>SQL TOTAL REG:" + sqltotalreg);
		//this.setTotalregistros( db.ContarFilas(sqltotalreg));		
		
		return rs;
	}

	/*
	 * public void guardarAsignacionSorteoSector1(String[] sectores) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	public void guardarAsignacion(String[] arrIdsColaboradores, SesionDatos sesion) {
		ArrayList<String> colsInserted = new ArrayList<String>();

		for (String idColaborador : arrIdsColaboradores) {

			String sql = "SELECT PK1 FROM SORTEOS_ASIGNACION_COLABORADORES"
					+ " WHERE PK_NICHO = " + getIdNicho()
					+ " AND PK_COLABORADOR = " + idColaborador
					+ " AND PK_SECTOR = " + getIdsector()
					+ " AND PK_SORTEO = " + getIdSorteo();
			ResultSet rs = db.getDatos(sql);

			try {
				if (!rs.next()) {

					sql = "INSERT INTO SORTEOS_ASIGNACION_COLABORADORES (PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR)"
							+ " VALUES (" + getIdSorteo() + "," + getIdsector() + ","+ getIdNicho() + "," + idColaborador + ")";
					db.execQuery(sql);
					colsInserted.add(idColaborador);
				}
			} catch (SQLException e) { Factory.Error(e, sql); }

		}
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacion",
					sesion.toShortString() + ", Colaboradores=" + colsInserted.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	
	
	public int numeroTalonarios(mSorteosParalelosColaboradores obj){
		 
		 db.con();
		 String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = "+obj.getIdSorteo()+" AND PK_SECTOR = '"+obj.getIdsector()+"' AND PK_NICHO = '"+obj.getIdNicho()+"' AND PK_COLABORADOR = '"+obj.getIdColaborador()+"'";
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		 } catch (SQLException e) { Factory.Error(e, sql); }
		 
		 return max;
	 }
	 
	 public int numeroBoletos(mSorteosParalelosColaboradores obj){
		 
		 db.con();
		 
		 //String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = "+obj.getIdSorteo()+" AND PK_SECTOR = '"+obj.getIdsector()+"' AND PK_NICHO = '"+obj.getIdNicho()+"' AND PK_COLABORADOR = '"+obj.getIdColaborador()+"'";
		 
		 String sql = "select COUNT(*) AS MAX from SORTEOS_PARALELOS_NICHOS_COLABORADORES"
		 		+ " where  PK_SORTEO_PARALELO = "+obj.getIdSorteoParalelo()+" AND  "
		 				+ "PK_COLABORADOR = '"+obj.getIdColaborador()+"'  "
		 						+ "AND PK_SECTOR = '"+obj.getIdsector()+"'  AND PK_NICHO = '"+obj.getIdNicho()+"'";
		 
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		 } catch (SQLException e) { Factory.Error(e, sql); }
		 
		 return max;
	  }
	 
	 
	 public ResultSet getBoletos(mSorteosParalelosColaboradores obj) {	 
		 
		 
		 int max = 0;
			String sql2 = "SELECT LEN(MAX(SA.FOLIO)) AS MAX FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SA, COLABORADORES C "
					+ "WHERE  SA.PK_COLABORADOR = C.PK1 "			
					+ " AND SA.PK_SORTEO_PARALELO = " + obj.getIdSorteoParalelo();
					//+ " AND PK_SECTOR = '"+obj.getIdsector()+"'"
					//+ " AND SA.PK_NICHO = '" + obj.getIdNicho() + "'";
			
			 ResultSet rsm = db.getDatos(sql2);
			
			 try {
					if (rsm != null && rsm.next()) {
					    	max = rsm.getInt("MAX");					    	
					    	if(max<4){max = 4;}
					    	
					    }
				 } catch (SQLException e) { Factory.Error(e, sql2); }
					
			
		 String sql = "SELECT C.PK1 AS PK1, C.NOMBRE, C.CLAVE, C.DESCRIPCION, C.APATERNO, C.AMATERNO, C.COMISION, C.REFBANCARIA, (RIGHT(REPLICATE('0', "+max+") + Ltrim(Rtrim(SA.FOLIO)),"+max+")) AS FOLIO FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SA, COLABORADORES C WHERE " +
					" SA.PK_COLABORADOR = C.PK1 and SA.PK_SORTEO_PARALELO = " + obj.getIdSorteoParalelo() + " "
				    + " AND PK_SECTOR = '"+obj.getIdsector()+"'"
					+ " AND SA.PK_NICHO = '" + obj.getIdNicho() + "' AND PK_COLABORADOR = '"+obj.getIdColaborador()+"' ";

			System.out.println("getBoletos:>>>>>>>>"+sql);
			ResultSet rs = db.getDatos(sql);
			return rs;
		}
	 
	 
 
 
 public int MontoColaboradores(mSorteosParalelosColaboradores obj){
		 
		 db.con();
		 String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = "+obj.getIdSorteo()+" AND PK_SECTOR = '"+obj.getIdsector()+"' AND PK_NICHO = '"+obj.getIdNicho()+"' AND PK_COLABORADOR = '"+obj.getIdColaborador()+"'";
		 ResultSet rs = db.getDatos(sql);
		 int total = 0;
		 try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
			    }
		 } catch (SQLException e) { Factory.Error(e, sql); }
		 
		 return total;
	  }
 
  
	
	

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}



	// Para configuracion
	public boolean ExisteCarga(){
		
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM [SORTEOS_COLABORADORES_TALONARIOS]"
				+ " WHERE PK_COLABORADOR = " + getIdColaborador()
				+ " AND PK_NICHO = " + getIdNicho()
				+ " AND PK_SECTOR = " + getIdsector()
				+ " AND PK_SORTEO = " + getIdSorteo();

		ResultSet rs = db.getDatos(sql);
		
		try {
			if( rs.next())
				return rs.getInt("MAX") > 0;

		} catch (SQLException e) { Factory.Error(e, sql); }
		return false;
	}
	

	public int eliminaCarga(SesionDatos sesion) {

		int val = borraTalonariosDeColaborador(sesion.pkSorteo, sesion.pkSector, sesion.pkNicho, sesion.pkColaborador);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaCarga", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }

		return val;
	}
	
	public int borraTalonariosDeColaborador(int pkSorteo, int pkSector, int pkNicho, int pkColaborador) {
		String sql="";

		// PASO 1: Se actualiza la columna de ASIGNADO EN EL BOLETO nicho
		sql +=
				" UPDATE SORTEOS_NICHOS_TALONARIOS SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_NICHOS_TALONARIOS AS RN, SORTEOS_COLABORADORES_TALONARIOS AS RC WHERE RN.PK_SORTEO=" + pkSorteo + " AND RN.PK_SECTOR=" + pkSector + " AND RN.PK_NICHO=" + pkNicho + " AND RN.PK_TALONARIO=RC.PK_TALONARIO AND RC.PK_COLABORADOR=" + pkColaborador +
				" UPDATE SORTEOS_NICHOS_BOLETOS    SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_NICHOS_BOLETOS AS RN,    SORTEOS_COLABORADORES_BOLETOS    AS RC WHERE RN.PK_SORTEO=" + pkSorteo + " AND RN.PK_SECTOR=" + pkSector + " AND RN.PK_NICHO=" + pkNicho + " AND RN.PK_BOLETO=   RC.PK_BOLETO    AND RC.PK_COLABORADOR=" + pkColaborador;


		// PASO 2) Se borran las relaciones de boletos asignados al colaborador
		sql +=
				" DELETE [SORTEOS_COLABORADORES_TALONARIOS] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho + " AND PK_COLABORADOR=" + pkColaborador +
				" DELETE [SORTEOS_COLABORADORES_BOLETOS]    WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho + " AND PK_COLABORADOR=" + pkColaborador;
		

		// PASO 4) Se borra tambien el seguimiento
		sql +=
				" DELETE [SEGUIMIENTO] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho + " AND PK_COLABORADOR=" + pkColaborador
				+ " AND NOT (NIVEL='Boveda' OR NIVEL='Sorteo' OR NIVEL='Sector' OR NIVEL='Nicho')";
		
		return db.execQuery(sql);
	}
	
	public int eliminaColaborador(SesionDatos sesion) {
		String sql =
				" DELETE [SORTEOS_ASIGNACION_COLABORADORES]"
				+ " WHERE PK_SORTEO=" + sesion.pkSorteo
				+ " AND PK_SECTOR=" + sesion.pkSector
				+ " AND PK_NICHO= " + sesion.pkNicho
				+ " AND PK_COLABORADOR=" + sesion.pkColaborador;
		int val = db.execQuery(sql);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaColaborador", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return val;
	}
	
	
	public String consultaSorteo(int idsorteo) {
		return super.consultaClaveNombreSorteo(idsorteo);
	}
	
	/**/
	public String consultaParalelo(int idParalelo) {

		String sql = "SELECT NOMBRE AS 'VALUE' FROM SORTEOS_PARALELOS WHERE PK1=" + idParalelo;
		
		return db.getValue(sql, "");
	}
	
	
	public String consultaParaleloNichos(int idParalelo,int idnichoi) {

		String sql = "SELECT C.NICHO AS 'VALUE' FROM SORTEOS_PARALELOS_NICHOS SA, NICHOS C WHERE " +
				" SA.PK_NICHO = C.PK1 and SA.PK_SORTEO_PARALELO = " + idParalelo + " AND SA.PK_NICHO = '" + idnichoi + "'";
		
		return db.getValue(sql, "");
	}
	
	public int deleteColaboradorBoletos(int idColabortador,int idsortoparalelo,int idsorteonicho,int idsector,int idnicho) {
		String sql = "DELETE SORTEOS_PARALELOS_NICHOS_COLABORADORES"
				+ " WHERE PK_SORTEO_PARALELO=" + idsortoparalelo
				+ " AND PK_SORTEO_PARALELO_NICHO=" + idsorteonicho
		        + " AND PK_COLABORADOR=" + idColabortador
		        + " AND PK_SECTOR=" + idsector
		        + " AND PK_NICHO=" + idnicho;
		System.out.println("delete: SORTEOS_PARALELOS_NICHOS_COLABORADORES:" + sql);
		return db.execQuery(sql);		
		
	}
	
	
	
	
	

}

