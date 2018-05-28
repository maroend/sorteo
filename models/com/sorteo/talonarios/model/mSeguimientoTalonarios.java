package com.sorteo.talonarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mSeguimientoTalonarios extends SuperModel 
{
	private int idSorteo;
	private int idtalonario;	
	private String folio;
	
	//private String sorteo;
	//private String sector;
	

	public mSeguimientoTalonarios() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int contar(String search) {
		String sql
				= " SELECT PK1"
				+ " FROM SEGUIMIENTO SEG WHERE PK_SORTEO=" + getIdSorteo() + " AND PK_TALONARIO=" + getIdtalonario() + " AND PK_BOLETO=0 ";
		if (search != "") {
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}
		int numero = db.ContarFilas(sql);
		System.out.println(">>>>SQL TAL:"+sql);

		return numero;
	}

	public ResultSet paginacion(int pg, int numreg, String search) {
		String sql
				= " SELECT SEG.*"
				+ ",(SELECT TOP 1 FOLIO FROM TALONARIOS WHERE PK1 = SEG.PK_TALONARIO) AS 'FOLIO'"
				+ " FROM SEGUIMIENTO SEG WHERE SEG.PK_SORTEO=" + getIdSorteo() + " AND SEG.PK_TALONARIO=" + getIdtalonario() + " AND SEG.TIPO='T' ";
		if (search != "") {
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}

		sql += " ORDER BY SEG.FECHA_R DESC ";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);

		return rs;
	}


	public int getIdSorteo() {
		return idSorteo;
	}


	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}


	public int getIdtalonario() {
		return idtalonario;
	}


	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}


	public String getFolio() {
		return folio;
	}


	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	

	public String Sorteo(int idsorteo) {

		String sql = "SELECT TOP 1 SORTEO FROM SORTEOS WHERE PK1 = " + idsorteo;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {

				return rs.getString("SORTEO");
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return "";
	}
	
	public String Sector(int idsector) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = " + idsector;
		String sector = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					sector = rs.getString("SECTOR");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sector;
	}
	
	public String Nicho(int idnicho) {

		String sql = "SELECT * FROM NICHOS WHERE PK1 = " + idnicho + "";
		String nicho = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					nicho = rs.getString("NICHO");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nicho;
	}
	

	public String Colaborador(int idcolaborador) {
		String sql = "SELECT NOMBRE+' '+APATERNO+' '+AMATERNO AS COLABORADOR FROM COLABORADORES WHERE PK1 = "
				+ idcolaborador + "";
		String colaborador = "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				if (rs.next()) {

					colaborador = rs.getString("COLABORADOR");

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return colaborador;
	}

	public String folioTalonario(int idTalonario) {
		String sql = "SELECT FOLIO FROM TALONARIOS WHERE PK1 = " + idTalonario;
		String folio="";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				if (rs.next()) {
					folio = rs.getString("FOLIO");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return folio;
	}
	 

}
