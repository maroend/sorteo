package com.sorteo.premios.model;

import java.sql.ResultSet;
import com.core.SuperModel;

public class mBuscarBoleto extends SuperModel {
	private long idsorteo;
	
	public mBuscarBoleto() {
		// TODO Auto-generated constructor stub
	}

	public long getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(long idsorteo) {
		this.idsorteo = idsorteo;
	}
	
	public ResultSet buscarTalonario(String folioBoleto) {

		String sql
			= "SELECT TOP 1 SB.ASIGNADO,B.VENDIDO,B.RETORNADO,B.INCIDENCIA,B.TALONARIO "
			+ ",(SELECT TOP 1 DIGITAL FROM TALONARIOS T WHERE T.PK1=B.PK_TALONARIO) AS 'ELECTRONICO' "
			+ " FROM SORTEOS_BOLETOS SB, BOLETOS B "
			+ " WHERE SB.PK_BOLETO=B.PK1 AND SB.PK_SORTEO='" + getIdsorteo() + "' AND B.FOLIO='" + folioBoleto + "' ";

		System.out.println("buscar:" + sql);
		return db.getDatos(sql);
	}
}

