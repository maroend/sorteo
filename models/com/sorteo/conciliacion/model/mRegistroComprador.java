package com.sorteo.conciliacion.model;

import java.sql.ResultSet;

import com.core.SesionDatos;

import java.sql.SQLException;

import com.core.SuperModel;
//import com.sorteo.poblacion.model.mSectores.OFFSET;
import com.core.SuperModel.OFFSET;
import com.core.SuperModel.RESULT;

public class mRegistroComprador extends SuperModel {
	
	private int id;
	
	private String clave;
	private int sector;
	private String rbancaria;
	private String colaborador;
	private int nicho;


	
	
	private int idusuario;
	private int idsorteo;
	
	private char sect;
	private int Comision;
	
	
	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
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
		 this.sector = 0;
		 this.rbancaria = "";		
		 this.nicho = 0;
		
		
		 this.sect = 'I';
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

	public int getNicho() {
		return nicho;
	}

	public void setNicho(int nicho) {
		this.nicho = nicho;
	}

	
	
	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	
	public String getRbancaria() {
		return rbancaria;
	}

	public void setRbancaria(String rbancaria) {
		this.rbancaria = rbancaria;
	}
	
	
	
	
	public char getSect() {
		return sect;
	}

	public void setSect(char sect) {
		this.sect = sect;
	}

	public mRegistroComprador() {
		init();
	}
	
	
	public int contar(String search)
	{
		String sql = "SELECT * FROM COMPRADORES ";
		if (search != "") {
			sql += " WHERE ((NOMBRE LIKE '%" + search + "%') OR (APELLIDOS LIKE '%" + search + "%')) OR (CORREO LIKE '%" + search + "%') ";
		}
		int numero = db.ContarFilas(sql);
		return numero;
	}
	
	public ResultSet paginacion(int pg, int numreg, String search)
	{
		return paginacion(pg, numreg, search, OFFSET.TRUE);
	}
	
	public ResultSet paginacion(int pg, int numreg, String search, OFFSET offset)
	{
		String sql = "SELECT * FROM COMPRADORES ";

		if (search != "") {
			sql += " WHERE ((NOMBRE LIKE '%" + search + "%') OR (APELLIDOS LIKE '%" + search + "%')) OR (CORREO LIKE '%" + search + "%') ";
		}

		sql += " ORDER BY PK1 ASC";
		
		if (offset == OFFSET.TRUE) {
			sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS";
			sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		}
		
		ResultSet rs = db.getDatos(sql);
		
		return rs;
	}
	
	
	
	
	
	
	public String getTalonario(mRegistroComprador obj,int pk_sorteo){

		String sql =
				" SELECT B.TALONARIO"
				+ ", (SELECT NUMBOLETOS FROM TALONARIOS WHERE FOLIO = B.TALONARIO ) AS 'NUMBOLETOS'"
				+ ", (SELECT ABONO FROM TALONARIOS WHERE FOLIO = B.TALONARIO ) AS 'ABONO'"
				+ ", B.SORTEO, B.COSTO, B.PK_TALONARIO, B.PK1 AS 'PK_BOLETO'"
				+ ", (SELECT PK_SECTOR FROM SORTEOS_SECTORES_BOLETOS WHERE PK_BOLETO = B.PK1 AND PK_TALONARIO = B.TALONARIO) AS 'PK_SECTOR'"
				+ ", (SELECT PK_NICHO FROM SORTEOS_NICHOS_BOLETOS WHERE PK_BOLETO = B.PK1 AND PK_TALONARIO = B.TALONARIO) AS 'PK_NICHO'"
				+ ", (SELECT PK_COLABORADOR FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_BOLETO = B.PK1 AND PK_TALONARIO = B.TALONARIO) AS 'PK_COLABORADOR'"
				+ ", B.VENDIDO, B.ASIGNADO, B.INCIDENCIA"
				+ " FROM BOLETOS B WHERE B.FOLIO = '"+obj.getClave()+"' AND B.SORTEO = "+pk_sorteo+" ";
		System.out.println("getTalonario:"+sql);

		ResultSet rs = db.getDatos(sql);
        
		String cadena = null;
		
		try {
			if (rs.next()) {
				
				cadena = rs.getString("TALONARIO");
				cadena += "|";
				cadena += rs.getString("NUMBOLETOS");
				cadena += "|";
				cadena +=  rs.getString("ABONO");
				cadena += "|";
				cadena +=  rs.getString("SORTEO");
				cadena += "|";
				cadena +=  rs.getString("COSTO");
				cadena += "|";
				cadena +=  rs.getString("PK_TALONARIO");
				cadena += "|";
				cadena +=  rs.getString("PK_BOLETO");
				cadena += "|";
				cadena +=  rs.getString("PK_SECTOR");
				cadena += "|";
				cadena +=  rs.getString("PK_NICHO");
				cadena += "|";
				cadena +=  rs.getString("PK_COLABORADOR");
				cadena += "|";
				cadena +=  rs.getString("VENDIDO");
				cadena += "|";
				cadena +=  rs.getString("ASIGNADO");
				cadena += "|";
				cadena +=  rs.getString("INCIDENCIA");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cadena;
	}

	
	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdsorteo();

		ResultSet rs = db.getDatos(sql);

		return rs;

	}

	public ResultSet getSectoresUsuario(mRegistroComprador obj) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ obj.getSector() +"' ";
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

	public void getSectorNicho(mRegistroComprador obj) {

		String sql = "SELECT PK_SECTOR FROM NICHOS WHERE PK1 ='" + obj.getNicho() + "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				obj.setSector(rs.getInt("PK_SECTOR"));

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



	public int contar(String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - contar

		String sql = " SELECT C.PK1"
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1 ";

		if (search != "") {
			if (isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'  ";
			} else {
				sql += " AND C.NOMBRE LIKE '%" + search + "%'  ";
			}
		}

		if (sector != 0) {

			sql += " AND N.PK_SECTOR = '" + sector + "'  ";

		} else {

			setIdusuario(sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo(sesion.pkSorteo);
				getSectorUsuarioActual();

				sql += " AND N.PK_SECTOR = '" + this.getSector() + "'  ";
			}
		}

		if (nicho != 0) {

			sql += " AND C.PK_NICHO = '" + nicho + "'  ";

		}

		//System.out.println("COLABORADORES COUNT: " + sql);

		int numero = db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacion(int pg, int numreg, String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - paginacion

		String sql = " SELECT C.*,CONCAT(C.NOMBRE,' ',C.APATERNO) AS 'NOM_COLABORADOR', N.NICHO AS 'NOM_NICHO', S.SECTOR AS 'NOM_SECTOR',(SELECT SUM(MONTO) FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = " + sesion.pkSorteo + " and PK_SECTOR = S.PK1 AND PK_NICHO = N.PK1 AND PK_COLABORADOR = C.PK1) AS MONTO "
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1";

		if (search != "") {
			if (isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'  ";
			} else {
				sql += " AND C.NOMBRE LIKE '%" + search + "%'  ";
			}
		}

		if (sector != 0) {

			sql += " AND N.PK_SECTOR = '" + sector + "'  ";

		} else {

			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				sql += " AND N.PK_SECTOR = '" + this.getSector() + "'  ";
			}
		}

		if (nicho != 0) {

			sql += " AND C.PK_NICHO = '" + nicho + "'  ";
		}

		sql += " ORDER BY C.PK1 ASC ";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if
															// you
															// need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		//System.out.println("COLABORADORES: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	
	public int registrar_colaborador_Telefonos(int id, int secuencia,
			String telefono, char tipo, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO COLABORADORES_TELEFONOS (PK_COLABORADOR,SECUENCIA,TELEFONO,TIPO,PK_USUARIO) VALUES ('"
				+ id + "','"
				+ secuencia + "','"
				+ telefono + "','"
				+ tipo + "','"
				+ pkUsuario + "')";
		//System.out.println(sql);
		int res = db.execQuery(sql);
		return res;
	}

	public int registrar_colaborador_Correo(int id, int secuencia, String correo, char tipo, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO COLABORADORES_CORREOS (PK_COLABORADOR,SECUENCIA,CORREO,TIPO,PK_USUARIO) VALUES ('"
				+ id + "','"
				+ secuencia + "','"
				+ correo + "','"
				+ tipo + "','"
				+ pkUsuario + "')";
		//System.out.println(sql);
		int res = db.execQuery(sql);
		return res;
	}

	public int registrar_colaborador_RedSocial(int id, int secuencia, String red, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO COLABORADORES_REDES_SOCIALES (PK_COLABORADOR,SECUENCIA,RED,PK_USUARIO) VALUES ('"
				+ id + "','"
				+ secuencia + "','"
				+ red + "','"
				+ pkUsuario + "')";
		//System.out.println(sql);
		int res = db.execQuery(sql);
		return res;
	}

	public int Borrar(mRegistroComprador obj) {

		db.con();
		String sql = "DELETE FROM COLABORADORES WHERE PK1=" + obj.getId();
		int res = db.execQuery(sql);
		return res;

	}

	public void actualizarAbono(mRegistroComprador obj) {

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
				this.setSector(rs.getInt("PK_SECTOR"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}
	
public RESULT Borrar() {
		
		// SE VERIFICA QUE NO TENGA BOLETOS ASIGNADOS.
		String sql = "SELECT COUNT(*) AS 'MAX' FROM COMPRADORES_BOLETOS WHERE PK1=" + getId();
		if (db.Count(sql) > 0){
			super._mensaje = "No se puede eliminar el comprador por que tiene BOLETOS asignados.";
			return RESULT.ERROR;
		}
		
		
		sql = "DELETE FROM COMPRADORES WHERE PK1='" + getId() + "'";
		int res = db.execQuery(sql);

		if (res == 1) {
			super._mensaje = "El comprador se ha borrado con exito";
			return RESULT.OK;
		} else {
			super._mensaje = "Ocurrio un error al intentar borrar el comprador";
			return RESULT.ERROR;
		}
	}
	
	
	

}
