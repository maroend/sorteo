package com.sorteo.reportes.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.Factory;
import com.core.SuperModel;

public class mReporteCompactoBoletos extends SuperModel {

	private long idSorteo;

	public long getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(long idSorteo) {
		this.idSorteo = idSorteo;
	}
	
	public mReporteCompactoBoletos() {
	}
	
	public String Sorteo()
	{
		String sql = "SELECT SORTEO FROM SORTEOS WHERE PK1 = " + getIdSorteo();
		String sorteo = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				sorteo = rs.getString("SORTEO");
				rs.close();
			}
		} catch (SQLException e) {
			Factory.Error(e, sql);
		}
		return sorteo;
	}
	// ------------ CUENTA -------------
	/*
	public int cuentaTotalDeBoletos(){
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' ";
		ResultSet res = db.getDatos(sql);
		int max = 0;
		try{
			if (res.next()) {
				max = res.getInt("MAX");
			}	
		}catch(SQLException ex) { }
		return max;
	}
	*/
	
	public int cuentaBoletos(String condicion){
		String sql
			= "SELECT COUNT(B.PK1) AS 'MAX' FROM BOLETOS B, SORTEOS_BOLETOS R"
			+ " WHERE R.PK_BOLETO=B.PK1 AND R.PK_SORTEO=" + getIdSorteo() + " AND " + condicion;
		try {
			ResultSet res = db.getDatos(sql);
			if (res.next())
				return res.getInt("MAX");
		} catch (SQLException ex) {
		}
		return 0;
	}
	
	public int cuentaBoletosVendidosYEntregados(){
		return cuentaBoletos(SuperModel.condicion_retornados);
	}
	
	public int cuentaBoletosSoloVendidos(){
		return cuentaBoletos(SuperModel.condicion_Vendidos);
	}
	
	public int cuentaBoletosEnTransito(){
		return cuentaBoletos(SuperModel.condicion_Transito);
	}
	
	public int cuentaBoletosConIncidencia(){
		return cuentaBoletos(SuperModel.condicion_Incidencias);
	}
	
	public int cuentaBoletosNoVendidos(){
		return cuentaBoletos(SuperModel.condicion_NoVendidos);
	}
	
	public int cuentaBoletosElectronicos(){
		String sql
			= "SELECT COUNT(B.PK1) AS 'VALUE' FROM BOLETOS B, TALONARIOS T, SORTEOS_BOLETOS R"
			+ " WHERE R.PK_BOLETO=B.PK1 AND R.PK_SORTEO=" + getIdSorteo() + " AND B.PK_TALONARIO=T.PK1 AND T.DIGITAL=1";
		System.out.println("SEGOB-->>" + sql);
		return db.getValue(sql, 0);
	}
	
	// ------------ CONSULTA DATOS ------------
	/*
	public int consultaTotalDeBoletos(){
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' ";
		ResultSet res = db.getDatos(sql);
		int max = 0;
		try{
			if (res.next()) {
				max = res.getInt("MAX");
			}	
		}catch(SQLException ex) { }
		return max;
	}
	//*/
	
	public ResultSet consultaBoletos(String condicion) {
		String sql
			= "SELECT B.FOLIO,ISNULL(B.FOLIODIGITAL,0) AS 'FOLIODIGITAL',T.DIGITAL"
			+ " FROM BOLETOS B, TALONARIOS T, SORTEOS_BOLETOS R"
			+ " WHERE R.PK_BOLETO=B.PK1 AND B.PK_TALONARIO=T.PK1 AND R.PK_SORTEO=" + getIdSorteo() + " AND " + condicion
			;
		System.out.println("SEGOB->"+sql);
		return db.getDatos(sql);
	}
	
	
	public ResultSet consultaBoletosVendidosYEntregados(){
		return consultaBoletos(condicion_retornados);
	}
	
	public ResultSet consultaBoletosEnTransito(){
		return consultaBoletos(condicion_Transito);
	}
	
	public ResultSet consultaBoletosNoVendidos(){
		return consultaBoletos(condicion_NoVendidos);
	}
	
	public ResultSet consultaBoletosConIncidencia(){
		return consultaBoletos(condicion_Incidencias);
	}
	
	
	public ResultSet consultaBoletosElectronicos(){
		String sql
			= "SELECT B.FOLIO,ISNULL(B.FOLIODIGITAL,0) AS 'FOLIODIGITAL',T.DIGITAL, B.VENDIDO, B.RETORNADO"
			+ " FROM BOLETOS B, TALONARIOS T, SORTEOS_BOLETOS R"
			+ " WHERE R.PK_BOLETO=B.PK1 AND B.PK_TALONARIO=T.PK1 AND R.PK_SORTEO=" + getIdSorteo() + " AND T.DIGITAL=1"
			+ " ORDER BY FOLIODIGITAL,FOLIO ";
		System.out.println(">>>>>>"+sql);
		return db.getDatos(sql);
	}
	
	/*
		//String sql = "SELECT * FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND VENDIDO = 'V' AND INCIDENCIA = 'N' AND RETORNADO = 1  ORDER BY FOLIO ASC";
		//return db.getDatos(sql);
		 
		 
	public ResultSet consultaBoletosEnTransito(){
		String sql = "SELECT * FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND ASIGNADO = 1 AND  RETORNADO = 0  ORDER BY FOLIO ASC";
		return db.getDatos(sql);
	}
	
	public ResultSet consultaBoletosNoVendidos(){
		String sql = "SELECT * FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND ASIGNADO = 0 AND RETORNADO = 0  ORDER BY FOLIO ASC";
		return db.getDatos(sql);
	}
	
	public ResultSet consultaBoletosConIncidencia(){
		String sql = "SELECT FOLIO, INCIDENCIA FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND INCIDENCIA <> 'N' AND RETORNADO = 1  ORDER BY FOLIO ASC";
		return db.getDatos(sql);
	}
	//*/
	
}
