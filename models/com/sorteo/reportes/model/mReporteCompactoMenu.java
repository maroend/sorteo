package com.sorteo.reportes.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.core.SuperModel;

public class mReporteCompactoMenu extends SuperModel {
	
	private long idSorteo;


	public long getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(long idSorteo) {
		this.idSorteo = idSorteo;
	}
	
	public mReporteCompactoMenu() {
		// TODO Auto-generated constructor stub
	}
	
	
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
	
	public int cuentaBoletosVendidosYEntregados(){
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND INCIDENCIA='N' AND VENDIDO='V' AND RETORNADO=1 ";
		try {
			ResultSet res = db.getDatos(sql);
			if(res.next())
				return res.getInt("MAX");
		}catch(SQLException ex) { }
		return 0;
	}
	
	public int cuentaBoletosEnTransito(){
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND INCIDENCIA='N' AND VENDIDO='V' AND RETORNADO=0 ";
		try {
			ResultSet res = db.getDatos(sql);
			if(res.next())
				return res.getInt("MAX");
		}catch(SQLException ex) { }
		return 0;
	}
	
	public int cuentaBoletosNoVendidos(){
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND INCIDENCIA='N' AND VENDIDO<>'V'";
		try {
			ResultSet res = db.getDatos(sql);
			if(res.next())
				return res.getInt("MAX");
		}catch(SQLException ex) { }
		return 0;
	}
	
	public int cuentaBoletosConIncidencia(){
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM BOLETOS WHERE SORTEO='" + getIdSorteo() + "' AND INCIDENCIA<>'N'";
		try {
			ResultSet res = db.getDatos(sql);
			if(res.next())
				return res.getInt("MAX");
		}catch(SQLException ex) { }
		return 0;
	}

	
}
