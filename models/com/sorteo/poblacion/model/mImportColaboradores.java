package com.sorteo.poblacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SesionDatos;
import com.core.SuperModel;

public class mImportColaboradores extends SuperModel {
	private int idSorteo;
	private int idSector;
	private int idNicho;
	private int idUsuario;
	
	public mImportColaboradores() {
		// TODO Auto-generated constructor stub
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
	}

	public int getIdsector() {
		return idSector;
	}

	public void setIdsector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdnicho() {
		return idNicho;
	}

	public void setIdnicho(int idnicho) {
		this.idNicho = idnicho;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public void consultaUsuarioSorteo()
	{
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		try {
			while (rs.next()) {
				this.setIdSorteo(rs.getInt("PK_SORTEO"));
				this.setIdsector(rs.getInt("PK_SECTOR"));
				this.setIdnicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public void getSectorUsuarioActual(SesionDatos sesion) {

		String sql = "SELECT PK_SECTOR FROM USUARIOS WHERE PK1 = " + sesion.pkUsuario;

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				this.setIdsector(rs.getInt("PK_SECTOR"));
				sesion.pkSector = rs.getInt("PK_SECTOR");
				sesion.guardaSesion();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();

		return db.getDatos(sql);
	}

	public ResultSet getSectoresUsuario()
	{
		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ this.getIdsector() +"' ";

		return db.getDatos(sql);
	}

	public ResultSet getNichos() {

		String sql = "SELECT * FROM NICHOS WHERE PK_SECTOR = '" + getIdsector() + "'";

		return db.getDatos(sql);
	}
	
	
}



