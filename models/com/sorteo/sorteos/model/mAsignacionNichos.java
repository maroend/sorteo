package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;

public class mAsignacionNichos extends SuperModel {

	private int idSorteo;
	private int idSector;
	private int idNicho;
	private String clave;
	private String[] sectores;
	private String sorteo;
	private String descripcion;
	private int totalregistros = 0;

	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}


	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int id) {
		this.idNicho = id;
	}

	public String getClave() {
		return clave;
	}

	public String[] getSectores() {
		return sectores;
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idSorteo = idsorteo;
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


	public mAsignacionNichos() {
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

					sorteo = rs.getString("CLAVE") + "-"
							+ rs.getString("SORTEO");
				}

				rs.close();
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return sorteo;

	}
	
	
	public String Sector(int idsector) {

		db.con();
		String sql = "SELECT * FROM SECTORES WHERE PK1 = " + idsector + "";
		String sector = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					sector = rs.getString("CLAVE") + "-"
							+ rs.getString("SECTOR");
				}

				rs.close();
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return sector;

	}
	
	
	public ResultSet listarModal() {

		String sql = "SELECT * FROM NICHOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarModal(int idsector,String search,int idsorteo) {

		String sql = "SELECT PK1 FROM NICHOS WHERE PK_SECTOR = '" + idsector
				+ "' AND PK_SORTEO = "+idsorteo;
		
		if (search != "") {
			sql += " AND ((NICHO LIKE '%" + search + "%') OR(CLAVE LIKE '%" + search + "%'))  ";
		}
		
		
		int numero = db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacionModal(int pg, int numreg, String search,	int idsector,int idsorteo) {

		/*String sql = "SELECT * ";
		sql += "FROM NICHOS WHERE PK_SECTOR = '" + idsector + "'";*/
		
		String sql = "SELECT N.PK1,N.CLAVE,N.NICHO,N.DESCRIPCION,(select TOP 1 PK_NICHO from SORTEOS_ASIGNACION_NICHOS  where PK_NICHO = N.PK1 AND PK_SECTOR = '" + idsector + "' AND PK_SORTEO = '"+idsorteo+"' ) AS "
				+ "'PK_NICHO' FROM NICHOS N WHERE N.PK_SECTOR = '" + idsector + "'";


		if (search != "") {
			sql += " AND ((N.NICHO LIKE '%" + search + "%') OR (N.CLAVE LIKE '%" + search + "%'))  ";
		}

		sql += "ORDER BY N.PK1 ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	//

	public ResultSet listar() {

		String sql = "SELECT * FROM SORTEOS_ASIGNACION_NICHOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contar(SesionDatos sesion,String search) {
		String sql;
		if (sesion.misSorteos == 0)
			
			/* ---OLD---sql = "SELECT SA.PK1,N.NICHO,N.CLAVE FROM SORTEOS_ASIGNACION_NICHOS SA, NICHOS N WHERE SA.PK_SECTOR = '"
					+ getIdSector()
					+ "' AND SA.PK_NICHO = N.PK1 AND SA.PK_SORTEO = '"
					+ getIdSorteo() + "'  ";*/
			
			//TABLA ASIGNACIÓN			
			sql = "SELECT N.PK1,N.NICHO,N.CLAVE FROM NICHOS N WHERE N.PK_SECTOR = '"
					+ getIdSector()	+ "'";	
		
		
			
			
		else{
			/*sql = "SELECT N.PK1,N.NICHO,N.CLAVE FROM SORTEOS_USUARIOS_NICHOS SUN, NICHOS N"
					+ " WHERE SUN.PK_SORTEO = " + getIdSorteo()
					+ " AND SUN.PK_SECTOR = " + getIdSector()
					+ " AND SUN.PK_USUARIO=" + sesion.pkUsuario
					+ " AND SUN.PK_NICHO = N.PK1";*/
			
			// TABLA DE RELACION - USUARIOS
		/*	sql = "SELECT N.PK1,N.NICHO,N.CLAVE FROM SORTEOS_USUARIOS SU, NICHOS N"
					+ " WHERE SU.PK_SORTEO="+getIdSorteo()
					+ " AND SU.PK_SECTOR = " + getIdSector()
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario
			        + " AND SU.PK_NICHO = N.PK1";*/
			
			sql = "SELECT N.PK1 FROM SORTEOS_USUARIOS SU, NICHOS N"
					+ " WHERE N.PK_SECTOR ="+ getIdSector()
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario 
					//+ " AND S.PK_SORTEO = "+this.getIdSorteo()
			        + " AND SU.PK_NICHO = N.PK1";
			
			
			
			
		}	
		
		if (search != "") {
			sql += " AND ((N.NICHO LIKE '%" + search + "%') OR (N.CLAVE LIKE '%" + search + "%'))  ";
		}
		
		System.out.println(">>>>SQL COUNT NICHOS: " + sql); 
		
		int numero = db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion) {

		String sql;
		if (sesion.misSorteos == 0)
			
			
			// TABLA DE ASIGNACION
			/*sql = "SELECT N.PK1 AS PK1, N.NICHO, N.CLAVE, N.DESCRIPCION  FROM SORTEOS_ASIGNACION_NICHOS SA, NICHOS N"
					+ " WHERE SA.PK_SECTOR = " + getIdSector()
					+ " AND SA.PK_SORTEO = " + getIdSorteo()
					+ " AND SA.PK_NICHO = N.PK1 "
		            + " AND N.PK_SORTEO = "+getIdSorteo(); */		
			
			
			//TABLA ASIGNACIÓN			
			sql = "SELECT N.PK1,N.NICHO,N.CLAVE FROM NICHOS N WHERE N.PK_SECTOR = '"
					+ getIdSector()	+ "'";		
			
			
		else{
			
			// TABLA DE RELACION - USUARIOS
			/*sql = "SELECT N.* FROM SORTEOS_USUARIOS_NICHOS SUN, NICHOS N"
					+ " WHERE SUN.PK_SORTEO = " + getIdSorteo()
					+ " AND SUN.PK_SECTOR = " + getIdSector()
					+ " AND SUN.PK_USUARIO=" + sesion.pkUsuario
					+ " AND SUN.PK_NICHO = N.PK1"
					+ " AND N.PK_SORTEO = "+getIdSorteo();*/
			
			
			// TABLA DE RELACION - USUARIOS
			/*sql = "SELECT N.PK1,N.NICHO,N.CLAVE FROM SORTEOS_USUARIOS SU, NICHOS N"
					+ " WHERE SU.PK_SORTEO="+getIdSorteo()
					+ " AND SU.PK_SECTOR = " + getIdSector()
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario
			        + " AND SU.PK_NICHO = N.PK1";*/
			
			sql = "SELECT N.* FROM SORTEOS_USUARIOS SU, NICHOS N"
					+ " WHERE N.PK_SECTOR ="+ getIdSector()
					+ " AND SU.PK_USUARIO=" + sesion.pkUsuario 
					//+ " AND S.PK_SORTEO = "+this.getIdSorteo()
			        + " AND SU.PK_NICHO = N.PK1";
			
			
			
		}

		if (search != "") {
			sql += " AND ((N.NICHO LIKE '%" + search + "%') OR (N.CLAVE LIKE '%" + search + "%'))  ";
		}
		
		System.out.println(">>>>SQL NICHOS: " + sql); 
		
		String sqltotalreg =  sql;
		
		sql += " ORDER BY N.PK1 ASC";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS"; // -- not sure if you
															// need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);
		
		//total reg	
		System.out.println(">>>>SQL TOTAL REG:" + sqltotalreg);    	
    	this.setTotalregistros( db.ContarFilas(sqltotalreg));
    	
		return rs;
	}

	/*
	 * public void guardarAsignacionSorteoSector1(String[] sectores) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	public void guardarAsignacion(String[] nichos, mAsignacionNichos obj, SesionDatos sesion) {

		for (String s : nichos) {
			// Do your stuff here
			// System.out.println(s);

			String sql = "SELECT PK1 FROM SORTEOS_ASIGNACION_NICHOS"
					+ " WHERE PK_SECTOR ='" + obj.getIdSector()
					+ "' AND PK_NICHO = '" + s
					+ "' AND  PK_SORTEO ='" + obj.getIdSorteo() + "' ";
			ResultSet rs = db.getDatos(sql);

			try {
				if (!rs.next()) {

					String sql2 = "INSERT INTO SORTEOS_ASIGNACION_NICHOS (PK_SORTEO,PK_SECTOR,PK_NICHO) VALUES ('"
							+ obj.getIdSorteo()
							+ "','"
							+ obj.getIdSector()
							+ "','" + s + "')";
					System.out.println(sql2);
					db.execQuery(sql2);
				}
			} catch (SQLException e) { Factory.Error(e, sql); }

		}
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacion", sesion.toShortString() + ", nichos=" + Arrays.toString(nichos));
		}catch(Exception ex) { Factory.Error(ex, "Log"); }

		//db.desconectar();
	}

	public int numeroTalonarios(mAsignacionNichos obj) {

		db.con();
	/*	String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_NICHOS_TALONARIOS"
				+ " WHERE PK_SORTEO = " + obj.getIdSorteo()
				+ " AND PK_SECTOR = '" + obj.getIdSector()
				+ "' AND PK_NICHO = '" + obj.getIdNicho() + "' ";*/
		
		
		 String sql = "SELECT COUNT(*) AS MAX FROM VNICHOS_TALONARIOS WHERE"
		 		+ " PK_SECTOR = '" + obj.getIdSector()+"'  "
		        + " AND PK_NICHO = '" + obj.getIdNicho() + "' ";		
		
		
		
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return max;
	}

	public int numeroBoletos(mAsignacionNichos obj) {

		db.con();

		/*String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_NICHOS_BOLETOS"
				+ " WHERE PK_SORTEO = " + obj.getIdSorteo()
				+ " AND PK_SECTOR = '" + obj.getIdSector()
				+ "' AND PK_NICHO = '" + obj.getIdNicho() + "'";*/
		
		
		String sql = "SELECT COUNT(*) AS MAX FROM VNICHOS_BOLETOS"			
				+ " WHERE PK_SECTOR = '" + obj.getIdSector()+ "'"
				+ " AND PK_NICHO = '" + obj.getIdNicho() + "'";
		
		System.out.println("BOL NICHOS"+sql);
		
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return max;
	}

	public int MontoNicho(mAsignacionNichos obj) {

		db.con();
		/*String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS"
				+ " WHERE PK_SORTEO = " + obj.getIdSorteo()
				+ " AND PK_SECTOR = '" + obj.getIdSector()
				+ "' AND PK_NICHO = '" + obj.getIdNicho() + "' ";*/
		
		
		 String sql = "SELECT SUM(MONTO) AS TOTAL FROM VNICHOS_TALONARIOS WHERE PK_SECTOR = '"+obj.getIdSector()+"' "
					+ " AND PK_NICHO = '" + obj.getIdNicho() + "' ";
		
		 System.out.println("MONTO: "+sql);
		
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
		
		/*String sql = "SELECT COUNT(PK1) AS 'MAX' FROM [SORTEOS_NICHOS_TALONARIOS]"
				+ " WHERE PK_NICHO = " + getIdNicho()
				+ " AND PK_SECTOR = " + getIdSector()
				+ " AND PK_SORTEO = " + getIdSorteo();*/
		
		
		String sql = "SELECT COUNT(PK_NICHO) AS 'MAX' FROM [VNICHOS_TALONARIOS]"
				+ " WHERE PK_SECTOR = " + getIdSector()
				+ " AND PK_NICHO = " + getIdNicho();		
		
		

		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next())
				return rs.getInt("MAX") > 0;

		} catch (SQLException e) { Factory.Error(e, sql); }
		return false;
	}

	public int eliminaCarga(SesionDatos sesion) {
		
		int val = borraTalonariosDeNicho(sesion.pkSorteo, sesion.pkSector, sesion.pkNicho);

		// --- Se guarda un registro de seguimiento ---
		try {
			//this.Log(sesion, LOG.REGISTRO, this, "eliminaCarga", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return val;
	}

	public int borraTalonariosDeNicho(int pkSorteo, int pkSector, int pkNicho) {
		String sql="";

		// PASO 1: Se actualiza la columna de ASIGNADO EN EL BOLETO sector
		
	
		
				//" UPDATE SORTEOS_SECTORES_TALONARIOS SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_SECTORES_TALONARIOS AS RS, SORTEOS_NICHOS_TALONARIOS AS RN WHERE RS.PK_SORTEO=" + pkSorteo + " AND RS.PK_SECTOR=" + pkSector + " AND RS.PK_TALONARIO=RN.PK_TALONARIO AND RN.PK_NICHO=" + pkNicho +
				//" UPDATE SORTEOS_SECTORES_BOLETOS    SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_SECTORES_BOLETOS AS RS   , SORTEOS_NICHOS_BOLETOS AS RN    WHERE RS.PK_SORTEO=" + pkSorteo + " AND RS.PK_SECTOR=" + pkSector + " AND RS.PK_BOLETO=RN.PK_BOLETO       AND RN.PK_NICHO=" + pkNicho;

		// PASO 2) Se borran los boletos y talonarios asignados a los sub-niveles
		sql =
				//" DELETE [SORTEOS_COLABORADORES_TALONARIOS] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho +
				" DELETE [COLABORADORES_BOLETOS]    WHERE PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho;
		
		db.execQuery(sql);
		
		
		
		// PASO 3) Se borran las relaciones de boletos y talonarios asignados al nicho
		sql =
				//" DELETE [SORTEOS_NICHOS_TALONARIOS]     WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho +
				" DELETE [NICHOS_BOLETOS]   WHERE PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho;

		// PASO 4) Se borra tambien el seguimiento
		//sql +=
				//" DELETE [SEGUIMIENTO] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho
			//	+ " AND NOT (NIVEL='Boveda' OR NIVEL='Sorteo' OR NIVEL='Sector')";

		return db.execQuery(sql);
	}
	
	public int eliminaNicho(SesionDatos sesion) {
		String sql =
				" DELETE [SORTEOS_ASIGNACION_NICHOS]"
				+ " WHERE PK_SORTEO=" + sesion.pkSorteo
				+ " AND PK_SECTOR=" + sesion.pkSector
				+ " AND PK_NICHO= " + sesion.pkNicho;
		int val = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaNicho", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return val;
	}

}


