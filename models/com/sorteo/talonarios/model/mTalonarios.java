package com.sorteo.talonarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.core.Seguimiento;
import com.core.SuperModel;
import com.core.Seguimiento.ASIGNACION;
import com.core.Parametros;

public class mTalonarios extends SuperModel {
	
	
	private int id;
	private String folio;  
	private int numeroboletos;  
	private String idsorteo;
	private String formato;
	private double monto;
	
	int totalregistros = 0;

	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public int getNumeroboletos() {
		return numeroboletos;
	}

	public void setNumeroboletos(int numeroboletos) {
		this.numeroboletos = numeroboletos;
	}

	public String getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(String idsorteo) {
		this.idsorteo = idsorteo;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public mTalonarios() {
		// TODO Auto-generated constructor stub
	}
	
	public int contarVistaColumna(int idsorteo,String search) {
	/*	String sql = "SELECT B.PK1";
		sql += " FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+idsorteo+"'";
		int numero = db.ContarFilas(sql);
		System.out.println(">>>>SQL BOL:"+sql);*/
		
		String sql = "SELECT COUNT(*) AS 'MAX' FROM TALONARIOS";
        sql += " WHERE PK_SORTEO="+idsorteo;
        if(!search.equals("")){
      	  sql += " AND FOLIO LIKE '%"+search+"%'";
        }
	   int numero = db.Count(sql);
	   return numero;
		

		
	}

	public ResultSet paginacionVistaColumna(int pg, int numreg, String search,int idsorteo) {
		//String sql = "SELECT  B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO ";
		//sql += "FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+idsorteo+"'";
		
	/*String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO,"
			+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
			+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
			+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
			+ "FROM SORTEOS_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND S.PK_SORTEO = '"+idsorteo+"'" ;*/

		  String sql = "SELECT *"
		  		+ ",(SELECT COUNT(*) FROM BOLETOS B WHERE B.PK_TALONARIO=T.PK1) AS 'NUMBOLETOS'"
		  		+ ",(SELECT SUM(B.COSTO) FROM BOLETOS B WHERE B.PK_TALONARIO=T.PK1) AS 'COSTO'"
		  		+ ",(SELECT COUNT(*) FROM BOLETOS B WHERE B.PK_TALONARIO=T.PK1 AND B.PK_ESTADO='V') AS 'VENDIDOS'"
		  		+ " FROM TALONARIOS T"
		  		+ " WHERE T.PK_SORTEO="+idsorteo;
		
		if (!search.equals("")) {
			sql += " AND T.FOLIO LIKE '%" + search + "%'  ";
		}

		sql += "ORDER BY T.FOLIO ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println(">>>TALONARIO: "+sql);
		
		return db.getDatos(sql);
	}
	
	public ResultSet listar() {

		String sql = "SELECT * FROM TALONARIOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}
	
	public ResultSet getBoletos(mTalonarios obj) {
		/*
		String sql = "SELECT * FROM BOLETOS WHERE TALONARIO ='"
				+ obj.getFolio() + "' AND  SORTEO='" + obj.getIdsorteo() + "' ";
		//*/
		String sql = "SELECT B.* FROM BOLETOS B, TALONARIOS T WHERE B.PK_TALONARIO=T.PK1 AND B.PK_TALONARIO=" + obj.getId()
				+ " AND T.PK_SORTEO=" + obj.getIdsorteo();
		ResultSet rs = db.getDatos(sql);
		return rs;

	}
	
	public int contar(String search){
		   
		   String sql = "SELECT COUNT(*) AS 'MAX' FROM TALONARIOS";
	          sql += " WHERE PK_SORTEO="+this.getIdsorteo();
	          if(!search.equals("")){
	        	  sql += " AND FOLIO LIKE '%"+search+"%'";
	          }
		   int numero = db.Count(sql);
		   return numero;

	}
	
	public ResultSet getSorteos(){
	     
	     String sql = "SELECT * FROM SORTEOS";
	     ResultSet rs = db.getDatos(sql);
	     return rs;

	}

	public ResultSet paginacion(int pg, int numreg,String search) {
		String sql = "SELECT *"
				+ ",(SELECT COUNT(*) FROM BOLETOS B WHERE B.PK_TALONARIO=T.PK1) AS 'NUMBOLETOS'"
				+ ",(SELECT SUM(B.COSTO) FROM BOLETOS B WHERE B.PK_TALONARIO=T.PK1) AS 'COSTO'"
				//+ ",(SELECT COUNT(*) FROM BOLETOS B WHERE B.PK_TALONARIO=T.PK1 AND B.PK_ESTADO='V') AS 'VENDIDOS'"
				+ "FROM("
				+ "SELECT * FROM TALONARIOS ";
		/*
		String sql = "SELECT *";
		sql += " FROM TALONARIOS";
		*/
		sql += " WHERE PK_SORTEO="+this.getIdsorteo();
		if (!search.equals("")) {
			sql += " AND FOLIO LIKE '%"+search+"%'";
		}
		
		sql += " ORDER BY PK1 ASC";
		sql += " OFFSET ("+(pg-1)*numreg+") ROWS"; //-- not sure if you need -1
		sql += " FETCH NEXT "+numreg+" ROWS ONLY";

		sql += ") T ORDER BY PK1 ASC";
		
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	}

	public RESULT Borrar(mTalonarios obj){
		ArrayList<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1", this.getId()));
		list.add(new Parametros("FOLIO","0"));
		list.add(new Parametros("PK_SORTEO","0"));
		list.add(new Parametros("FORMATO","0"));
		list.add(new Parametros("ASIGNADO","0"));
		list.add(new Parametros("DIGITAL","0"));
		list.add(new Parametros("USUARIO","0"));
		list.add(new Parametros("LIST","0"));
		list.add(new Parametros("StatementType","Delete"));
		
		int result = db.execStoreProcedureIntId("spTALONARIOS", list);
		
		String talonario_style = "<span style='color:#DDD'><strong>" + getFolio() + "</strong></span>";
		
		if (result == getId()) {
			super._mensaje = "El talonario "+talonario_style+" y sus boletos han sido eliminados con exito";
			return RESULT.OK;
		}
		else if (result == -3) {
			super._mensaje = "El talonario " + talonario_style + " continua asignado.";
			return RESULT.ERROR;
		}
		else if (result == -2) {
			super._mensaje = "El talonario " + talonario_style + " no se puede borrar porque tiene orden de compra o esta relacionado a un comprador.";
			return RESULT.ERROR;
		}
		else {
			super._mensaje = "No se puede eliminar el talonario " + talonario_style;
			return RESULT.ERROR;
		}
	}
		/*   	
		   	db.con();
		   	String sql = "DELETE FROM TALONARIOS WHERE PK1='"+obj.getId()+"'";
		   	int res = db.execQuery(sql);
		   	
		   	sql = "DELETE FROM BOLETOS WHERE TALONARIO='"+obj.getFolio()+"' AND SORTEO = '"+obj.getIdsorteo()+"'";
		   	res = db.execQuery(sql);
			return res;
				
			}
	 
	 */
	public int registrar(mTalonarios obj){
			db.con();
		    String sql = "INSERT INTO TALONARIOS (FOLIO,NUMBOLETOS,SORTEO,FORMATO,MONTO) VALUES ('"+obj.getFolio()+"','"+obj.getNumeroboletos()+"','"+obj.getIdsorteo()+"','FC0 - "+obj.getFormato()+"',"+obj.getMonto()+")";
		    System.out.println(sql);
		    int pkTalonario = db.execQuerySelectId(sql);
		    
			// --- Se guarda un registro de seguimiento ---
			try {
				Seguimiento.guardaAsignacion(db
						, ASIGNACION.BOVEDA
						, Long.valueOf(obj.getIdsorteo()),0,0,0
						, ASIGNACION.TALONARIO
						, pkTalonario
						, 0
						, 'N'
						, obj.getMonto(), 0.0
						, obj.getNumeroboletos()
						, "FC0 - "+obj.getFormato()
						, "bov-tals-1");
			}
			catch (Exception ex) { }
		    //db.desconectar();
			return pkTalonario;
	}
	
	public boolean Sorteo(int idSorteo)
	{
		boolean sorteoActivo = false;
		db.con();
		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + idSorteo + "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				if (rs.next()) {
					int activo = rs.getInt("ACTIVO");
					sorteoActivo = (boolean)(activo==1);
				}
				rs.close();
			}
		} catch (SQLException ex) {
			com.core.Factory.Error(ex, sql);
		}
		return sorteoActivo;
	}
	
}

