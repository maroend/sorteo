package com.sorteo.premios.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;

public class mGanadores extends SuperModel {

	private int idsorteo;
	private int idsector;
	private int idnicho;
	private int idUsuario;
	
	private int idPremio;
	private int idPremioDeColaborador;
	private int idComprador;
	private int idColaborador;
	private String sorteo;
	
	public mGanadores() {
		// TODO Auto-generated constructor stub
	}

	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
	}

	public int getIdsector() {
		return idsector;
	}

	public void setIdsector(int idsector) {
		this.idsector = idsector;
	}

	public int getIdnicho() {
		return idnicho;
	}

	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getSorteo() {
		return sorteo;
	}

	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}
	
	public ArrayList<Integer> getListCompradoresGanadores() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT PK_COMPRADOR FROM GANADORES WHERE PK_SORTEO=" + this.getIdsorteo();

		try {
			ResultSet rs = db.getDatos(sql);
			while (rs.next())
				list.add(rs.getInt("PK_COMPRADOR"));
		}catch (SQLException ex) { }
		
		return list;
	}

	public int contarGanadores() {
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM GANADORES WHERE PK_SORTEO=" + this.getIdsorteo();
		int max = 0;
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException ex) { Factory.Error(ex, sql); }
		
		return max;
	}

	public ResultSet paginacionCompradoresGanadores(int pg, int numreg, String search) {
		
		String sql
			= "SELECT P.*, C.*, G.PK1 AS 'PK_GANADOR', P.NOMBRE AS 'PREMIO', CONCAT(C.NOMBRE,' ',C.APELLIDOS) AS 'COMPRADOR', B.FOLIO"
			+ " FROM GANADORES G, PREMIOS P, COMPRADORES C, BOLETOS B"
			+ " WHERE G.PK_SORTEO = " + this.getIdsorteo() + " AND G.PK_PREMIO = P.PK1 AND G.PK_COMPRADOR = C.PK1 AND C.PK_BOLETO = B.PK1";
		
		if (search != "") {
			sql += " AND (P.NOMBRE LIKE '%" + search + "%' OR C.NOMBRE LIKE '%" + search + "%')";
		}
		
		sql += " ORDER BY P.CLASIFICACION ASC "
			+ " OFFSET (" + (pg - 1) * numreg + ") ROWS "
			+ " FETCH NEXT " + numreg + " ROWS ONLY";
		
		System.out.println("pag comp ===> " + sql);

		return db.getDatos(sql);
	}

	public ResultSet paginacionColaboradoresGanadores(int pg, int numreg, String search) {

		String sql
			= "SELECT *,G.PK1 AS 'PK_GANADOR',"
			+ " P.NOMBRE AS 'PREMIO',"
			+ " CONCAT(COL.NOMBRE,' ',COL.APATERNO,' ',COL.AMATERNO) AS 'COLABORADOR',"
			+ " (SELECT COM.BOLETO FROM COMPRADORES COM WHERE COM.PK1=G.PK_COMPRADOR) AS 'FOLIO',"
			+ " (SELECT CONCAT(CALLE,' ',NUMEXT,', int ',NUMINT,', Col. ',COLONIA,', ',ESTADO,', ',PAIS) FROM COLABORADORES_DIRECCION WHERE PK_COLABORADOR=G.PK_COLABORADOR) AS 'DIRECCION',"
			+ " (SELECT TELEFONO FROM COLABORADORES_TELEFONOS WHERE PK_COLABORADOR=G.PK_COLABORADOR AND TIPO='C') AS 'TELEFONOF',"
			+ " (SELECT TELEFONO FROM COLABORADORES_TELEFONOS WHERE PK_COLABORADOR=G.PK_COLABORADOR AND TIPO='T') AS 'TELEFONOM',"
			+ " (SELECT TOP 1 CORREO FROM COLABORADORES_CORREOS WHERE PK_COLABORADOR=G.PK_COLABORADOR) AS 'CORREO'"
			+ " FROM GANADORES G, COLABORADORES COL, PREMIOS P"
			+ " WHERE G.PK_SORTEO=43 AND G.PK_COLABORADOR<>0 AND G.PK_COLABORADOR = COL.PK1 AND G.PK_PREMIO=P.PK1"
			;
		
		if (search != "") {
			sql += " AND (C.NOMBRE LIKE '%" + search + "%' OR C.NOMBRE LIKE '%" + search + "%')";
		}
		
		sql += " ORDER BY P.CLASIFICACION ASC "
			+ " OFFSET (" + (pg - 1) * numreg + ") ROWS "
			+ " FETCH NEXT " + numreg + " ROWS ONLY";
		
		System.out.println("pag colb ===> " + sql);

		return db.getDatos(sql);
	}
	
	public int contarPremios() {
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM PREMIOS WHERE PK_SORTEO=" + this.getIdsorteo();
		int max = 0;
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException ex) { Factory.Error(ex, sql); }
		
		return max;
	}
	
	public ResultSet paginacionPremios(int pg, int numreg, String search) {
		/*
		String sql = "SELECT * FROM PREMIOS P WHERE PK_SORTEO = " + this.getIdsorteo() + " AND"
				+ " PK1 NOT IN (SELECT P.PK1 FROM PREMIOS P, GANADORES G WHERE P.PK_SORTEO = " + this.getIdsorteo() + " AND P.PK1 = G.PK_PREMIO)";
		
		String sql
			= "SELECT (SELECT COUNT(PK1) FROM PREMIOS WHERE PK_SORTEO=43 AND CLAVE_BENEFICIARIO=2 AND PK1=P.PK1) AS 'COMPLETO',P.*"
			+ " FROM PREMIOS P WHERE PK_SORTEO = 43"
			+ " AND PK1 NOT IN (SELECT P.PK1 FROM PREMIOS P, GANADORES G WHERE P.PK_SORTEO = 43 AND P.PK1 = G.PK_PREMIO)";
		*/
		String sql
			= "SELECT P.*, (SELECT Q.PK1 FROM PREMIOS Q WHERE Q.PK_SORTEO=" + this.getIdsorteo() + " AND Q.CLAVE_BENEFICIARIO=2 AND Q.NUM_PREMIO=P.NUM_PREMIO) AS 'PK_PREMIO_COLABORADOR'"
			+ " FROM PREMIOS P WHERE PK_SORTEO = " + this.getIdsorteo() + " AND P.CLAVE_BENEFICIARIO=1"
			+ " AND PK1 NOT IN (SELECT P.PK1 FROM PREMIOS P, GANADORES G WHERE P.PK_SORTEO = " + this.getIdsorteo() + " AND P.PK1 = G.PK_PREMIO)";

		/*
		if (search != "") {
			sql += " AND P.NOMBRE LIKE '" + search + "'  ";
		}*/
		
		
		sql += " ORDER BY P.CLASIFICACION ASC "
				+ " OFFSET (" + (pg - 1) * numreg + ") ROWS "
				+ " FETCH NEXT " + numreg + " ROWS ONLY";
		
		System.out.println("PREMIOS===>>>>>  " + sql);
		
		return db.getDatos(sql);
	}
	
	public int contarCompradores(String search) {
		String sql
			= "SELECT COUNT(*) AS 'TOTAL'"
			+ " FROM SORTEOS_BOLETOS SB, BOLETOS B"
			+ " WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+getIdsorteo()+"' AND (B.VENDIDO='V')";
		int numero = 0;

		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '"
					+ search + "'))   ";
		}

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				numero = rs.getInt("TOTAL");
				rs.close();
			}
		} catch (SQLException ex) { Factory.Error(ex, sql); }

		return numero;
	}
	
	public ResultSet paginacionCompradores(int pg, int numreg, String search) {
		
		String sql
			= "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO,B.INCIDENCIA, CONCAT(C.NOMBRE,' ',C.APELLIDOS) AS 'COMPRADOR', C.PK1 AS 'PK_COMPRADOR', "
			+ " (select TOP 1 VENDIDO               FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
			+ " (select TOP 1 PK_SECTOR             from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
			+ " (select TOP 1 PK_NICHO              from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
			+ " (select TOP 1 PK_COLABORADOR        from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
			+ " (select TOP 1 SECTOR                FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR        from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
			+ " (select TOP 1 NICHO                 FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO         from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
			+ " (select TOP 1 NOMBRE+' '+APATERNO   FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR   from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
			+ " FROM SORTEOS_BOLETOS S, BOLETOS B, COMPRADORES C WHERE S.PK_BOLETO = B.PK1 AND S.PK_SORTEO = '"+idsorteo+"' AND (B.VENDIDO='V') AND C.PK_BOLETO=B.PK1" ;

		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))";
		}

		sql += " ORDER BY CAST(B.TALONARIO AS INT) ASC";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS"; // -- not sure if you
															// need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		
		//System.out.println("VENTAS: "+sql);
		ResultSet rs = db.getDatos(sql);

		return rs;
	}
	
	public void consultaUsuarioSorteo()
	{
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs.next()) {
				this.setIdsorteo(rs.getInt("PK_SORTEO"));
				this.setIdsector(rs.getInt("PK_SECTOR"));
				this.setIdnicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException ex) { Factory.Error(ex, sql); }
	}
	
	public int contarRegistros(String sql) {
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if(rs != null && rs.next()) {
				String tmp = rs.getString("MAX");
				rs.close();
				max = Integer.valueOf(tmp);
			}
		} catch(Exception ex) { }
		
		return max;
	}
	
	public int buscaIdColaborador(SesionDatos sesion) {
		
		int pkBoleto = -1;
		int pkColaborador = 0;
		
		// --- Se obtiene el "boleto ganador" atravez del comprador.
		
		String sql = "SELECT TOP 1 PK_BOLETO FROM COMPRADORES WHERE PK1=" + getIdComprador();
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				pkBoleto = rs.getInt("PK_BOLETO");
				rs.close();
			}
		}catch(SQLException ex) { Factory.Error(ex, sql); }
		
		if (pkBoleto == -1)
			return -1;
		
		// --- Se obtiene el colaborador atravez del "boleto ganador"
		
		sql = "SELECT PK_COLABORADOR FROM SORTEOS_COLABORADORES_BOLETOS"
			+ " WHERE PK_SORTEO = " + sesion.pkSorteo
			+ " AND PK_BOLETO = " + pkBoleto;
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				pkColaborador = rs.getInt("PK_COLABORADOR");
				rs.close();
			}
		} catch(SQLException ex) { Factory.Error(ex, sql); }
		
		if (pkColaborador == -1)
			return -2;
		
		setIdColaborador(pkColaborador);
		return 0;
	}
	
	public int registrar(SesionDatos sesion) {
		
		String sql
			= "SELECT COUNT(PK1) AS 'MAX' FROM GANADORES"
			+ " WHERE PK_SORTEO=" + sesion.pkSorteo
			+ " AND PK_PREMIO=" + getIdPremio()
			+ " AND PK_COMPRADOR=" + getIdComprador();
	
		int max = contarRegistros(sql);
		// Si ya existe el registro ... no hace nada.
		if (max > 0)
			return 1;
		
		if (buscaIdColaborador(sesion) == 0) {
		
			sql
				= " INSERT INTO GANADORES (PK_SORTEO,PK_PREMIO,PK_COMPRADOR,PK_COLABORADOR,USUARIO)"
				+ " VALUES (" + sesion.pkSorteo + ", " + getIdPremio() + ", " + getIdComprador() + ", 0, '" + sesion.nickName + "')"
				
				+ " INSERT INTO GANADORES (PK_SORTEO,PK_PREMIO,PK_COMPRADOR,PK_COLABORADOR,USUARIO)"
				+ " VALUES (" + sesion.pkSorteo + ", " + getIdPremioDeColaborador() + ", " + getIdComprador() + ", "  + getIdColaborador() + ", '" + sesion.nickName + "')";
			
			//System.out.println(sql);
			
			int res = db.execQuery(sql);
	
			// --- Se guarda un registro de seguimiento ---
			try {
				this.Log(sesion, LOG.REGISTRO, this, "registrar", sesion.toShortString() + ", idPremio=" + idPremio + ", idComprador=" + idComprador);
			} catch (Exception ex) { Factory.Error(ex, "Log"); }
			return res;
		}
		return -1;
	}

	public void consultaSorteo() {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + getIdsorteo();

		//System.out.println(" ---> "+sql);
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				this.setSorteo(rs.getString("SORTEO"));
				rs.close();
			}
		} catch (SQLException ex) { Factory.Error(ex, sql); }
	}
	
	public int borraGanador(int idGanador, SesionDatos sesion) {
		// --- Se obtiene el id de ganador - colaborador
		String sql
			= " SELECT PK1 FROM GANADORES"
			+ " WHERE PK_COMPRADOR = (SELECT PK_COMPRADOR FROM GANADORES WHERE PK1=" + idGanador + ")"
			+ " AND PK_COLABORADOR<>0"; 
		int idGanadorColaborador = -1;
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next())
				idGanadorColaborador = rs.getInt("PK1");
		}catch(SQLException ex) { }
		
		if (idGanadorColaborador == -1) return -1;

		
		sql
			= " DELETE FROM GANADORES WHERE PK1=" + idGanador
			+ " DELETE FROM GANADORES WHERE PK1=" + idGanadorColaborador;
	   	int res = db.execQuery(sql);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminar", "idGanador=" + idGanador + ", idColaborador=" + idGanadorColaborador);
		} catch(Exception ex) { Factory.Error(ex, "Log"); }

		return res;
	}

	public int getIdPremio() {
		return idPremio;
	}

	public void setIdPremio(int idPremio) {
		this.idPremio = idPremio;
	}

	public int getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(int idComprador) {
		this.idComprador = idComprador;
	}

	public int getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(int idColaborador) {
		this.idColaborador = idColaborador;
	}

	public int getIdPremioDeColaborador() {
		return idPremioDeColaborador;
	}

	public void setIdPremioDeColaborador(int idPremioDeColaborador) {
		this.idPremioDeColaborador = idPremioDeColaborador;
	}

}



