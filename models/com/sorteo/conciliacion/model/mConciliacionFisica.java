package com.sorteo.conciliacion.model;

import java.sql.ResultSet;

import com.core.Global;
import com.core.SesionDatos;

import java.sql.SQLException;

import com.core.SuperModel;

public class mConciliacionFisica extends SuperModel {
	
	private int id;
	private String clave;
	private int idNicho;
	private int idSector;
	private String rbancaria;
	private String colaborador;

	private String nombre;
	private String apellidop;
	private String apellidom;
	
	private int idusuario;
	private int idSorteo;
	
	private int Comision;
	
	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	public int getComision() {
		return Comision;
	}
	
	public void setComision(int comision) {
		Comision = comision;
	}
	
	public void init(){		
	
		 this.id = 0;
		 this.clave = "";
		 this.rbancaria = "";
		 this.setIdNicho(0);
		 this.setIdSector(0);
		 
		 this.nombre = "";
		 this.apellidop = "";
		 this.apellidom = "";	
		
	}
	
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
	
	public String getColaborador() {
		return colaborador;
	}

	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}

	public String getRbancaria() {
		return rbancaria;
	}

	public void setRbancaria(String rbancaria) {
		this.rbancaria = rbancaria;
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

	public mConciliacionFisica() {
		init();
	}

	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();

		ResultSet rs = db.getDatos(sql);

		return rs;

	}

	public ResultSet getSectoresUsuario(mConciliacionFisica obj) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ obj.getIdSector() +"' ";
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public boolean isAdministrador() {

		String sql = "";

		int user = this.getIdusuario();
		//System.out.println(">>>>model(is isAdministrador) id usuario: " + user);

		sql = "SELECT PK1 FROM ROLES_USUARIO WHERE PK_USUARIO = '" + user + "' AND PK_ROLE = '2' ";
		//System.out.println(">>>>SQL: " + sql);
		int numero = db.ContarFilas(sql);

		if (numero > 0)
			return true;

		return false;
	}

	public void getSectorNicho(mConciliacionFisica obj) {

		String sql = "SELECT PK_SECTOR FROM NICHOS WHERE PK1 ='" + obj.getIdNicho() + "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				obj.setIdSector(rs.getInt("PK_SECTOR"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getNichos(int idsector) {

		String sql = "SELECT * FROM NICHOS WHERE PK_SECTOR = '" + idsector + "'";

		ResultSet rs = db.getDatos(sql);

		return rs;
	}

	/*
	public int contar(String search, SesionDatos sesion) {
		String condicion = "";
		
		if (getIdSector() != 0) {
			condicion += " AND CA.PK_SECTOR = " + getIdSector();
		} else {
			if (sesion.permisos.esAdministrador() == false) {
				consultaPoblacionUsuarioActual();

				condicion += " AND CA.PK_SECTOR = " + getIdSector();
			}
		}
		if (getIdNicho() != 0) {
			condicion += " AND CA.PK_NICHO = " + getIdNicho();
		}
		
		String condicion_2 = "";
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion_2 = " WHERE (C.CLAVE LIKE '%" + search + "%')";
			} else if (search.indexOf("E-")==0) {
				condicion_2 = " WHERE (C.CLAVE LIKE '" + search + "%')";
			} else if (search.indexOf('@') >= 0) {
				condicion_2 = " WHERE (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				condicion_2
				= " WHERE (C.NOMBRE LIKE '%" + search + "%'" 
				+ " OR C.APATERNO LIKE '%" + search + "%'"
				+ " OR C.AMATERNO LIKE '%" + search + "%'"
				+ " OR C.NOM_COLABORADOR LIKE '%" + search + "%'"
				+ ")";
			}
		}
		
		String sql
			= "SELECT COUNT(*) AS 'MAX'\n"
			+ "FROM(\n"
			+ "  SELECT C.CLAVE,C.COMISION,C.CORREO_P,C.CORREO_S, C.NOMBRE, C.APATERNO, C.AMATERNO, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS 'NOM_COLABORADOR', S.SECTOR,N.NICHO\n"
			+ "  FROM COLABORADORES C \n"
			+ "  INNER JOIN COLABORADORES_ASIGNACION CA ON CA.PK_COLABORADOR=C.PK1 \n"
			+ "  INNER JOIN SECTORES S ON S.PK1=CA.PK_SECTOR \n"
			+ "  INNER JOIN NICHOS   N ON N.PK1=CA.PK_NICHO \n"
			+ "  WHERE S.PK_SORTEO="+getIdSorteo() + condicion + " \n"
			+ ") AS C \n" + condicion_2 + " \n";
		
		return db.Count(sql);
	}
	
	// TODO - paginacion
	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion, OFFSET offset) {
		
		String condicion = "";
		
		if (getIdSector() != 0) {
			condicion += " AND CA.PK_SECTOR = " + getIdSector();
		} else {
			if (sesion.permisos.esAdministrador() == false) {
				consultaPoblacionUsuarioActual();

				condicion += " AND CA.PK_SECTOR = " + getIdSector();
			}
		}
		if (getIdNicho() != 0) {
			condicion += " AND CA.PK_NICHO = " + getIdNicho();
		}
		
		String condicion_2 = "";
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion_2 = " WHERE (C.CLAVE LIKE '%" + search + "%')";
			} else if (search.indexOf("E-")==0) {
				condicion_2 = " WHERE (C.CLAVE LIKE '" + search + "%')";
			} else if (search.indexOf('@') >= 0) {
				condicion_2 = " WHERE (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				condicion_2
				= " WHERE (C.NOMBRE LIKE '%" + search + "%'" 
				+ " OR C.APATERNO LIKE '%" + search + "%'"
				+ " OR C.AMATERNO LIKE '%" + search + "%'"
				+ " OR C.NOM_COLABORADOR LIKE '%" + search + "%'"
				+ ")";
			}
		}
		
		String sql
			= "SELECT *\n"
			+ "FROM(\n"
			+ "  SELECT C.PK1,C.CLAVE,C.COMISION,C.CORREO_P,C.CORREO_S, C.NOMBRE, C.APATERNO, C.AMATERNO, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS 'NOM_COLABORADOR', S.SECTOR,N.NICHO, C.REFERENCIA, CR.REFERENCIA AS 'NUM_REFERENCIA'\n"
			+ "  FROM COLABORADORES C \n"
			+ "  INNER JOIN COLABORADORES_ASIGNACION CA ON CA.PK_COLABORADOR=C.PK1 \n"
			+ "  INNER JOIN SECTORES S                  ON S.PK1=CA.PK_SECTOR \n"
			+ "  INNER JOIN NICHOS   N                  ON N.PK1=CA.PK_NICHO \n"
			+ "  LEFT JOIN COLABORADORES_REFERENCIAS CR ON CR.PK_COLABORADOR=C.PK1 \n"
			+ "  WHERE S.PK_SORTEO="+getIdSorteo() + condicion + " \n"
			+ ") AS C \n" + condicion_2 + " \n"
			+ "ORDER BY C.CLAVE, NOM_COLABORADOR, C.SECTOR, C.NICHO \n";
		if (offset == OFFSET.TRUE)
			sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println("CONCILIACION FISICA: \n" + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	*/

	public int contar(String search, SesionDatos sesion) {
		// TODO - contar
		
		String condicion_1 = "";
		
		if (getIdSector() != 0) {
			condicion_1 += " AND N.PK_SECTOR = " + getIdSector();
		} else {
			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdSorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				condicion_1 += " AND N.PK_SECTOR = '" + this.getIdSector();
			}
		}
		if (getIdNicho() != 0) {
			condicion_1 += " AND C.PK_NICHO = " + getIdNicho();
		}
		
		String condicion_2 = "";
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion_2 = " AND (C.CLAVE LIKE '%" + search + "%')";
			} else if (search.indexOf('@') >= 0) {
				condicion_2 = " AND (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				condicion_2
				= " AND C.NOM_COLABORADOR LIKE '%" + search + "%'";
			}
		}
		String sql
				= "	SELECT COUNT(*) AS 'MAX'\n"
				+ "	FROM(\n"
				+ "		SELECT C.*, SUM(ISNULL(B.COSTO,0)) AS 'MONTO'\n"
				+ "		FROM(\n"
				+ "			SELECT PK1, CLAVE, NOMBRE, APATERNO, AMATERNO, CORREO_P, CORREO_S, REFBANCARIA, CONCAT(NOMBRE,' ',APATERNO,' ',AMATERNO) AS 'NOM_COLABORADOR', COMISION, ABONO\n"
				+ "			FROM(\n"
				+ "				SELECT CA.PK_SECTOR, CA.PK_NICHO, C.PK1, C.CLAVE, C.NOMBRE, C.APATERNO, C.AMATERNO, CORREO_P, CORREO_S, C.COMISION, ISNULL(CR.ABONO,0.0) AS 'ABONO', CR.REFERENCIA AS 'REFBANCARIA'\n"
				+ "				FROM COLABORADORES C\n"
				+ "				INNER JOIN COLABORADORES_ASIGNACION CA ON CA.PK_COLABORADOR=C.PK1\n"
				+ "				INNER JOIN NICHOS N ON N.PK1=CA.PK_NICHO\n"
				+ "				INNER JOIN SECTORES S ON S.PK1=CA.PK_SECTOR\n"
				+ "				LEFT JOIN COLABORADORES_REFERENCIAS CR ON CR.PK_COLABORADOR=C.PK1\n"
				+ "				WHERE 1=1 "+condicion_1+"\n"
				+ "			) AS X\n"
				+ "			GROUP BY PK1, CLAVE, NOMBRE, APATERNO, AMATERNO, CORREO_P, CORREO_S, REFBANCARIA, COMISION, ABONO\n"
				+ "		) C\n"
				+ "		LEFT JOIN COLABORADORES_BOLETOS CB ON CB.PK_COLABORADOR=C.PK1\n"
				+ "		LEFT JOIN BOLETOS B ON B.PK1=CB.PK_BOLETO\n"
				+ "	 	WHERE  T.DIGITAL=1  "+condicion_2+"\n"
				+ "		GROUP BY C.PK1, CLAVE, NOMBRE, APATERNO, AMATERNO, CORREO_P, CORREO_S, REFBANCARIA, COMISION, C.ABONO, C.NOM_COLABORADOR\n"
				+ "	) AS C\n";
		
		System.out.println("Conciliacion-COUNT: " + sql);
		return db.Count(sql);
	}
	
	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion, OFFSET offset) {
		// TODO - paginacion
		
		String condicion_1 = "";
		
		if (getIdSector() != 0) {
			condicion_1 += " AND N.PK_SECTOR = " + getIdSector();
		} else {
			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdSorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				condicion_1 += " AND N.PK_SECTOR = '" + this.getIdSector();
			}
		}
		if (getIdNicho() != 0) {
			condicion_1 += " AND C.PK_NICHO = " + getIdNicho();
		}
		
		String condicion_2 = "";
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion_2 = " AND (C.CLAVE LIKE '%" + search + "%')";
			} else if (search.indexOf('@') >= 0) {
				condicion_2 = " AND (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				condicion_2
				= " AND C.NOM_COLABORADOR LIKE '%" + search + "%'";
			}
		}
		String sql
				= "	SELECT *, ((MONTO*COMISION/10)-ABONO) AS 'SALDO'\n"
				+ "	FROM(\n"
				+ "		SELECT C.*, SUM(ISNULL(B.COSTO,0)) AS 'MONTO'\n"
				+ "		FROM(\n"
				+ "			SELECT PK1, CLAVE, NOMBRE, APATERNO, AMATERNO, CORREO_P, CORREO_S, REFBANCARIA, CONCAT(NOMBRE,' ',APATERNO,' ',AMATERNO) AS 'NOM_COLABORADOR', COMISION, ABONO, COUNT(X.PK1) AS 'NICHOS'\n"
				+ "			FROM(\n"
				+ "				SELECT CA.PK_SECTOR, CA.PK_NICHO, C.PK1, C.CLAVE, C.NOMBRE, C.APATERNO, C.AMATERNO, CORREO_P, CORREO_S, C.COMISION, ISNULL(CR.ABONO,0.0) AS 'ABONO', CR.REFERENCIA AS 'REFBANCARIA'\n"
				+ "				FROM COLABORADORES C\n"
				+ "				INNER JOIN COLABORADORES_ASIGNACION CA ON CA.PK_COLABORADOR=C.PK1\n"
				+ "				INNER JOIN NICHOS N ON N.PK1=CA.PK_NICHO\n"
				+ "				INNER JOIN SECTORES S ON S.PK1=CA.PK_SECTOR\n"
				+ "				LEFT JOIN COLABORADORES_REFERENCIAS CR ON CR.PK_COLABORADOR=C.PK1\n"
				+ "				WHERE 1=1 "+condicion_1+"\n"
				+ "			) AS X\n"
				+ "			GROUP BY PK1, CLAVE, NOMBRE, APATERNO, AMATERNO, CORREO_P, CORREO_S, REFBANCARIA, COMISION, ABONO\n"
				+ "		) C\n"
				+ "		LEFT JOIN COLABORADORES_BOLETOS CB ON CB.PK_COLABORADOR=C.PK1\n"
				+ "		LEFT JOIN BOLETOS B ON B.PK1=CB.PK_BOLETO\n"
				+ "	 	WHERE 1=1 "+condicion_2+"\n"
				+ "		GROUP BY C.PK1, CLAVE, NOMBRE, APATERNO, AMATERNO, CORREO_P, CORREO_S, REFBANCARIA, COMISION, C.ABONO, C.NOM_COLABORADOR,NICHOS\n"
				+ "	) AS C\n"
				+ "	ORDER BY NOMBRE ASC,APATERNO ASC, AMATERNO ASC\n";
		
		if (offset == OFFSET.TRUE)
			sql += "	OFFSET (" + ((pg - 1) * numreg) + ") ROWS  FETCH NEXT 10 ROWS ONLY \n";

		System.out.println("Conciliacion: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public void consultaPoblacionUsuarioActual() {

		String sql = "SELECT PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdusuario();

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				this.setIdSector(rs.getInt("PK_SECTOR"));
				this.setIdNicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet paginacion_excel(String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - paginacion
		
		String sql;
		String filtro_search = " ";
		
		if (search != "") {
			if (isNumeric(search)) {
				filtro_search += "AND C.CLAVE LIKE '%" + search + "%'";
			} else {
				filtro_search += "AND C.NOMBRE LIKE '%" + search + "%'";
			}
		}
		
		String filtro_sector=" ";
		if (sector != 0) {
			filtro_sector += "AND N.PK_SECTOR = '" + sector + "'";
		} else {
			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdSorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				filtro_sector += "AND N.PK_SECTOR = '" + this.getIdSector() + "'";
			}
		}
		
		String filtro_nicho=" ";
		if (nicho != 0) {
			filtro_nicho += "AND C.PK_NICHO = '" + nicho + "'";
		}

		sql
				= "    SELECT C.PK1, C.CLAVE, C.REFBANCARIA, C.COMISION, C.ABONO, SAC.PK_SECTOR, SAC.PK_NICHO"
				+ "    ,LTRIM(RTRIM(CONCAT(C.NOMBRE,' ',C.APATERNO,' ', C.AMATERNO))) AS 'NOM_COLABORADOR'"
				+ "    ,(SELECT ISNULL(SUM(SCT.MONTO),0.0) FROM SORTEOS_COLABORADORES_TALONARIOS SCT WHERE SCT.PK_SORTEO = " + sesion.pkSorteo + " AND SCT.PK_SECTOR = S.PK1 AND SCT.PK_NICHO = N.PK1 AND SCT.PK_COLABORADOR = C.PK1) AS 'MONTO'"
				+ "    FROM SORTEOS_ASIGNACION_COLABORADORES SAC, COLABORADORES C, NICHOS N, SECTORES S"
				+ "    WHERE SAC.PK_COLABORADOR = C.PK1 AND SAC.PK_NICHO = N.PK1 AND SAC.PK_SECTOR = S.PK1 AND SAC.PK_SORTEO = " + sesion.pkSorteo + filtro_search + filtro_sector + filtro_nicho
				+ " ORDER BY NOM_COLABORADOR ASC";

		System.out.println("Conciliacion: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public void actualizarAbono(mConciliacionFisica obj) {

		db.con();
		String sql = "";

		sql = "UPDATE COLABORADORES SET ABONO = " + obj.getComision() + ", FECHA_M = GETDATE()"
			+ " WHERE PK1 = " + obj.getId();

		db.execQuery(sql);
	}

	public void getSectorUsuarioActual() {

		String sql = "SELECT PK_SECTOR FROM USUARIOS WHERE PK1 = " + this.getIdusuario();

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				this.setIdSector(rs.getInt("PK_SECTOR"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idNicho) {
		this.idNicho = idNicho;
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idSector) {
		this.idSector = idSector;
	}

}
