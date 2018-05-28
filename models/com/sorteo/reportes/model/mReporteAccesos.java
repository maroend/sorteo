package com.sorteo.reportes.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;
import com.core.SuperModel.OFFSET;


public class mReporteAccesos extends SuperModel {

	private int idSorteo;
	private int idSector;
	private int idNicho;

	private int idUsuario;
	
	private String fecha_ini;
	private String fecha_fin;
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idnicho) {
		this.idNicho = idnicho;
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}

	public String getFecha_ini() {
		return fecha_ini;
	}

	public void setFecha_ini(String fecha_ini) {
		this.fecha_ini = fecha_ini == null ? "" : fecha_ini;
	}

	public String getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin == null ? "" : fecha_fin;
	}
	
	
	public mReporteAccesos() {

	}

	public void getUsuarioSorteo() {

		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = "
				+ this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);

		try {
			while (rs.next()) {

				this.setIdSorteo(rs.getInt("PK_SORTEO"));
				this.setIdSector(rs.getInt("PK_SECTOR"));
				this.setIdNicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int contar(String search,int idsorteo) {
		String sql = "SELECT COUNT(*) AS 'MAX' FROM COLABORADORES C INNER JOIN COLABORADORES_ASIGNACION CA ON CA.PK_COLABORADOR=C.PK1 ";
		if (!"".equals(search)) {
			sql += " WHERE NOMBRE LIKE '%" + search + "%' ";
		}
		return db.Count(sql);
	}

	public ResultSet paginacion(int pg, int numreg, String search, OFFSET offset) {

		String condicion = "";
		if (getFecha_ini() != "")
			condicion += " AND CAST(CBA.FECHA_R AS DATE)>='" + getFecha_ini() + "'";
		if (getFecha_fin() != "")
			condicion += " AND CAST(CBA.FECHA_R AS DATE)<='" + getFecha_fin() + "'";
		
		String sql
				= " SELECT \n"
				+ "   ISNULL((SELECT TOP 1 PK_SECTOR FROM NICHOS WHERE PK1 = CA.PK_NICHO) , '') AS PK_SECTOR \n"
				+ "   ,ISNULL((SELECT TOP 1 SECTOR FROM SECTORES WHERE PK1 = (SELECT PK_SECTOR FROM NICHOS WHERE PK1 = CA.PK_NICHO)), '') AS SECTOR \n"
				+ "   ,CA.PK_NICHO \n"
				+ "   ,ISNULL((SELECT NICHO FROM NICHOS WHERE PK1 = CA.PK_NICHO), '') AS NICHO \n"
				+ "   ,C.PK1,C.CLAVE, C.NOMBRE, C.APATERNO, C.AMATERNO, CONCAT(C.NOMBRE,', ',C.APATERNO,' ',C.AMATERNO) AS 'NOM_COMPLETO' \n"
				+ "   ,(SELECT COUNT(SCT.PK_TALONARIO) FROM VCOLABORADORES_TALONARIOS SCT WHERE SCT.DIGITAL=1 AND SCT.PK_COLABORADOR = C.PK1 AND SCT.PK_NICHO = CA.PK_NICHO) AS 'TALS_ELEC' \n"
				+ "   ,(SELECT COUNT(CBA.PK1) FROM COLABORADORES_BITACORA_ACCESO CBA WHERE CBA.CORREO = C.CORREO_P " + condicion + ") AS ACCESOS \n"
				+ " FROM COLABORADORES C INNER JOIN COLABORADORES_ASIGNACION CA ON CA.PK_COLABORADOR=C.PK1 \n"
				;

		if (!"".equals(search)) {
			sql += " WHERE NOMBRE LIKE '%"+search+"%' \n";
		}

		sql += " ORDER BY SECTOR,NICHO,NOMBRE,APATERNO \n";
		
		if (offset == OFFSET.TRUE) {
			sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS ";
			sql += " FETCH NEXT " + numreg + " ROWS ONLY \n";
		}
		System.out.println("Accesos: "+sql);
		ResultSet rs = db.getDatos(sql);

		return rs;
	}

}
