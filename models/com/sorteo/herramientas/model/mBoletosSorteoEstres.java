package com.sorteo.herramientas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.core.Factory;
import com.core.Factory.LOG;
import com.core.Seguimiento;
import com.core.Seguimiento.ASIGNACION;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mBoletosSorteoEstres extends SuperModel 
{
	private int filtrovendidos;
	private int filtronovendidos;
	private int filtroparciales;
	private int filtroextraviados;
	private int filtrorobados;
	
	public int getFiltrovendidos() {
		return filtrovendidos;
	}

	public void setFiltrovendidos(int filtrovendidos) {
		this.filtrovendidos = filtrovendidos;
	}


	public int getFiltronovendidos() {
		return filtronovendidos;
	}


	public void setFiltronovendidos(int filtronovendidos) {
		this.filtronovendidos = filtronovendidos;
	}


	public int getFiltroparciales() {
		return filtroparciales;
	}


	public void setFiltroparciales(int filtroparciales) {
		this.filtroparciales = filtroparciales;
	}


	public int getFiltroextraviados() {
		return filtroextraviados;
	}


	public void setFiltroextraviados(int filtroextraviados) {
		this.filtroextraviados = filtroextraviados;
	}


	public int getFiltrorobados() {
		return filtrorobados;
	}

	public void setFiltrorobados(int filtrorobados) {
		this.filtrorobados = filtrorobados;
	}
	
	public int contar(String search) {
		
		String sql = "SELECT COUNT(*) AS 'VALUE'";
		sql += " FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1";
		
		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))   ";
		}
		
		if(this.getFiltrovendidos()==1){ sql += " AND B.VENDIDO = 'V' "; }
		if(this.getFiltroparciales()==1){ sql += " AND B.VENDIDO = 'P' "; }
		if(this.getFiltronovendidos()==1){ sql += " AND B.VENDIDO = 'N' "; }
		if(this.getFiltroextraviados() ==1){ sql += " AND B.INCIDENCIA = 'E' "; }
		if(this.getFiltrorobados()==1){ sql += " AND B.INCIDENCIA = 'R' "; }
		
		return db.getValue(sql, 0);
	}

	public ResultSet paginacion(int pg, int numreg, String search) {
		/* */
		String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO, B.RETORNADO,B.INCIDENCIA,T.DIGITAL,T.VENDIDO AS 'VENDIDOTALONARIO' \n"
			+ ", (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR'\n"
			+ ", (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR'\n"
			+ ", (select TOP 1 PK_NICHO    from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO'\n"
			+ ", (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO'\n"
			+ ", (select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR'\n"
			+ ", (select TOP 1 CONCAT(NOMBRE,' ',APATERNO,' ',AMATERNO)  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'\n"
			+ " FROM SORTEOS_BOLETOS S, BOLETOS B, TALONARIOS T WHERE S.PK_BOLETO = B.PK1 AND B.PK_TALONARIO=T.PK1 \n" ;

		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))   ";
		}
		
		if(this.getFiltrovendidos()==1){ sql += " AND B.VENDIDO = 'V' "; }
		if(this.getFiltroparciales()==1){ sql += " AND B.VENDIDO = 'P' "; }
		if(this.getFiltronovendidos()==1){ sql += " AND B.VENDIDO = 'N' "; }
		if(this.getFiltroextraviados()==1){ sql += " AND B.INCIDENCIA = 'E' "; }
		if(this.getFiltrorobados()==1){ sql += " AND B.INCIDENCIA = 'R' "; }
		

		sql += " ORDER BY T.PK1 ASC,B.FOLIO ASC \n";
		sql += " OFFSET (" + ((pg - 1) * numreg) + ") ROWS \n"; // -- not sure if you
															  // need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY \n";

		
		System.out.println("paginacion: "+sql);
		ResultSet rs = db.getDatos(sql);

		return rs;
	}
	
	
}





