package com.sorteo.premios.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mBuscarBoletoElectronico extends SuperModel {
	private long idsorteo;
	
	public String cuenta_deposito;
	public String referencia;
	
	public String URL_comprador;
	
	public mBuscarBoletoElectronico() {
		// TODO Auto-generated constructor stub
	}

	public long getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(long idsorteo) {
		this.idsorteo = idsorteo;
	}
	
	public ResultSet buscarComprador(String folioBoleto) {

		String sql
			= "SELECT B.PK1 AS 'PK_BOLETO', T.FOLIO AS 'TALONARIO',C.NOMBRE,C.APELLIDOS,C.TELEFONOF,C.TELEFONOM,C.CORREO,C.CALLE,C.NUMERO,C.COLONIA,C.ESTADO,C.MUNDEL,C.AUTOGENERADO"
			+ ",T.DIGITAL AS 'ELECTRONICO',B.VENDIDO,B.RETORNADO,B.INCIDENCIA,SB.ASIGNADO"
			+ " FROM COMPRADORES C, BOLETOS B, TALONARIOS T, SORTEOS_BOLETOS SB"
			+ " WHERE C.PK_SORTEO=" + getIdsorteo() + " AND C.PK_BOLETO=B.PK1 AND C.PK_TALONARIO=T.PK1 AND C.PK_BOLETO=SB.PK_BOLETO AND B.PK_TALONARIO=T.PK1 AND B.FOLIO='" + folioBoleto + "'"
			;

		System.out.println("comprador:" + sql);
		return db.getDatos(sql);
	}
	
	public void consultaDatos(int pkBoleto) {
		String sql = "SELECT"
				+ " (SELECT CUENTA_DEPOSITO FROM SORTEOS WHERE PK1=" + getIdsorteo() + ") AS 'CUENTA_DEPOSITO'"
				+ ",(SELECT C.REFBANCARIA FROM SORTEOS_COLABORADORES_BOLETOS SCB, COLABORADORES C"
				+ " WHERE SCB.PK_COLABORADOR=C.PK1 AND SCB.PK_SORTEO=" + getIdsorteo() + " AND SCB.PK_BOLETO=" + pkBoleto + ") AS 'REFBANCARIA'"
				+ ",(SELECT URL_5 FROM SORTEOS WHERE PK1=" + getIdsorteo() + ") AS 'URL_COMPRADOR'"
				;
		ResultSet res = db.getDatos(sql);
		try {
			if(res.next()) {
				this.cuenta_deposito = res.getString("CUENTA_DEPOSITO");
				this.referencia = res.getString("REFBANCARIA");
				this.URL_comprador = res.getString("URL_COMPRADOR");
			}
		}catch (SQLException ex) { }
	}
}

