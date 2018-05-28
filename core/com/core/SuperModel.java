package com.core;

import java.sql.ResultSet;
import java.util.HashMap;

import com.core.Factory.LOG;

public class SuperModel {
	public enum RESULT{
		OK,
		ERROR,
	}
	public enum OFFSET{
		TRUE,
		FALSE
	};
	
	public static int id_instancia;
	public Database db = new Database();
	//public final String _OK = "OK";
	//public final String _ERROR = "ERROR";
	public String _mensaje = "";
	public int _count_max;
	public int _count_process;
	public int _count_success;
	public int _count_excluded;
	public int _count_error;

	public static final String condicion_NoVendidos =
			" (R.ASIGNADO=0) ";
	public static final String condicion_Incidencias =
			" (R.ASIGNADO=1 AND B.INCIDENCIA<>'N') ";
	public static final String condicion_Transito =
			" (R.ASIGNADO=1 AND B.INCIDENCIA='N' AND B.VENDIDO<>'V') ";
	public static final String condicion_Vendidos =
			" (R.ASIGNADO=1 AND B.INCIDENCIA='N' AND B.VENDIDO='V' AND B.RETORNADO=0) ";
	public static final String condicion_retornados =
			" (R.ASIGNADO=1 AND B.INCIDENCIA='N' AND B.VENDIDO='V' AND B.RETORNADO=1) ";

	public SuperModel() {
		// TODO Auto-generated constructor stub
		id_instancia++;
		_count_max = 0;
		_count_process = 0;
		_count_error = 0;
	}
	
	public void close() {
		db.desconectar();
		System.out.println("------------ CLOSE connection -------------- <" + id_instancia + ">" + this.toString());
	}
	
	public int getIdTalonario(int idSorteo, String folio) {
		try {
			String sql = "SELECT TOP 1 PK1 FROM TALONARIOS WHERE SORTEO="+idSorteo+" AND FOLIO=" + folio;
			ResultSet rs = db.getDatos(sql);
			if (rs.next())
				return rs.getInt("PK1");
		} catch (Exception ex){ }
		return 0;
	}

	public double getCostoBoleto(int idSorteo, String folio) {
		try {
			String sql = "SELECT TOP 1 COSTO FROM BOLETOS WHERE SORTEO="+idSorteo+" AND FOLIO=" + folio;
			ResultSet rs = db.getDatos(sql);
			if (rs.next())
				return rs.getDouble("COSTO");
		} catch (Exception ex){ }
		return 0.0;
	}

	public double getCostoBoleto(int idBoleto) {
		try {
			String sql = "SELECT TOP 1 COSTO FROM BOLETOS WHERE PK1=" + idBoleto;
			ResultSet rs = db.getDatos(sql);
			if (rs.next())
				return rs.getDouble("COSTO");
		} catch (Exception ex){ }
		return 0.0;
	}
	
	public HashMap<Long,String> consultaTodosSectores(long pkSorteo){
		HashMap<Long,String> map = new HashMap<Long, String>();
		String sql = "SELECT PK1,CLAVE,SECTOR FROM SECTORES WHERE PK_SORTEO=" + pkSorteo;
		ResultSet res = db.getDatos(sql);
		try{
			if (res!=null)
				while(res.next())
					map.put(res.getLong("PK1"), res.getString("SECTOR"));
		} catch(Exception ex){ }
		
		return map;
	}
	
	public HashMap<Long,String> consultaTodosNichos(long pkSorteo){
		HashMap<Long,String> map = new HashMap<Long, String>();
		String sql = "SELECT PK1,CLAVE,NICHO FROM NICHOS WHERE PK_SORTEO=" + pkSorteo;
		ResultSet res = db.getDatos(sql);
		try{
			if (res!=null)
				while(res.next())
					map.put(res.getLong("PK1"), res.getString("NICHO"));
		} catch(Exception ex){ }
		
		return map;
	}
	
	public int Reportes_ContarBoletosXSorteo(String search, long idsorteo, String condicion){
		String sql = "SELECT COUNT(B.PK1) AS 'MAX'"
				+ " FROM SORTEOS_BOLETOS R, BOLETOS B"
				+ " WHERE R.PK_BOLETO = B.PK1 AND R.PK_SORTEO = "+idsorteo+" AND " + condicion;
		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))   ";
		}
		return db.Count(sql);
	}
	
	public int Reportes_ContarBoletosXSector(String search, long idsorteo, long idsector, String condicion){
		String sql = "SELECT COUNT(B.PK1) AS 'MAX'"
				+ " FROM SORTEOS_SECTORES_BOLETOS R, BOLETOS B"
				+ " WHERE R.PK_BOLETO = B.PK1 AND R.PK_SORTEO = " + idsorteo + " AND R.PK_SECTOR=" + idsector + " AND " + condicion;
		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))   ";
		}
		return db.Count(sql);
	}
	
	public int Reportes_ContarBoletosXNicho(String search, long idsorteo, long idsector, long idnicho, String condicion){
		String sql = "SELECT COUNT(B.PK1) AS 'MAX'"
				+ " FROM SORTEOS_NICHOS_BOLETOS R, BOLETOS B"
				+ " WHERE R.PK_BOLETO = B.PK1 AND R.PK_SORTEO = "+idsorteo+" AND R.PK_SECTOR=" + idsector + " AND R.PK_NICHO=" + idnicho + " AND " + condicion;
		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))   ";
		}
		return db.Count(sql);
	}
	
	public int Reportes_ContarBoletosXColaborador(String search, long idsorteo, long idsector, long idnicho, long idcolaborador, String condicion){
		String sql = "SELECT COUNT(B.PK1) AS 'MAX'"
				+ " FROM SORTEOS_COLABORADORES_BOLETOS R, BOLETOS B"
				+ " WHERE R.PK_BOLETO = B.PK1 AND R.PK_SORTEO = " + idsorteo + " AND R.PK_SECTOR=" + idsector + " AND R.PK_NICHO=" + idnicho + " AND R.PK_COLABORADOR=" + idcolaborador + " AND " + condicion;
		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))   ";
		}
		return db.Count(sql);
	}
	
	
	// ______________________________________________________________________
	
	public void Log(SesionDatos sesion, LOG actividad, Object sender, String nameFunction, String detalle){
		try{
			if (detalle != null) {
				//String ipAddress = sesion.request.getRemoteAddr();
				this.db.guardaLog(Long.toString(sesion.pkUsuario), actividad.toString(),
						sender.getClass().getSimpleName() + "." + nameFunction + ", " + detalle );
			}
		}catch(Exception ex) { System.out.println("ERROR EN LOG:"+ex.getMessage()); }
	}

	public String consultaClaveNombreSorteo(int idsorteo)
	{
		String sql = "SELECT CONCAT(CLAVE,'-',SORTEO) AS 'VALUE' FROM SORTEOS WHERE PK1=" + idsorteo;
		db.con();
		return db.getValue(sql, "");
	}

	public String getNombreSorteo(int idsorteo)
	{
		String sql = "SELECT SORTEO AS 'VALUE' FROM SORTEOS WHERE PK1=" + idsorteo;
		db.con();
		return db.getValue(sql, "");
	}
	
	// __________________

}
