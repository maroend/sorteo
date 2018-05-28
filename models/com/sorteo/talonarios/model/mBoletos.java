package com.sorteo.talonarios.model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.core.Parametros;
import com.core.Seguimiento;
import com.core.SuperModel;
import com.core.Seguimiento.ASIGNACION;
import com.core.SuperModel.RESULT;

public class mBoletos extends SuperModel {
	
	private int id;
	private String folio;
	private String talonario;
	private String idsorteo;
	private String formato;
	private double costo;
	private int idtalonario;
	
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

	public String getTalonario() {
		return talonario;
	}

	public void setTalonario(String talonario) {
		this.talonario = talonario;
	}

	public String getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(String idsorteo) {
		this.idsorteo = idsorteo;
	}
	
	
	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}


	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}


	public int getIdtalonario() {
		return idtalonario;
	}

	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}

	public mBoletos() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ResultSet listar() {

		String sql = "SELECT * FROM BOLETOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarVistaColumna(int idsorteo, String search) {

		String sql = "SELECT COUNT(*) AS 'MAX' FROM BOLETOS B, TALONARIOS T";
		sql += " WHERE B.PK_TALONARIO=T.PK1 AND T.PK_SORTEO=" + idsorteo;
		if (!search.equals("")) {
			sql += " AND B.FOLIO LIKE '%" + search + "%'";
		}
		int numero = db.Count(sql); //db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacionVistaColumna(int pg, int numreg, String search, int idsorteo) {

		String sql = "SELECT B.*,T.FOLIO AS 'TALONARIO'";
		sql += " FROM BOLETOS B, TALONARIOS T";
		sql += " WHERE B.PK_TALONARIO=T.PK1 AND T.PK_SORTEO=" + idsorteo;

		if (!search.equals("")) {
			sql += " AND B.FOLIO LIKE '%" + search + "%'  ";
		}

		sql += " ORDER BY B.PK_TALONARIO ASC ";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	
	public int contar(String search) {

		String sql = "SELECT COUNT(*) AS 'MAX' FROM BOLETOS B, TALONARIOS T WHERE B.PK_TALONARIO=T.PK1"
				+ " AND T.PK_SORTEO=" + this.getIdsorteo();
		if (!search.equals("")) {
			sql += " AND B.FOLIO LIKE '%" + search + "%'";
		}
		int numero = db.Count(sql);
		return numero;

	}
	

	public ResultSet paginacion(int pg, int numreg, String search) {
		String sql = "SELECT B.*,BE.ESTADO,T.PK_SORTEO,T.FOLIO AS 'TALONARIO' FROM BOLETOS B, TALONARIOS T, BOLETOS_ESTADOS BE"
				+ " WHERE B.PK_TALONARIO=T.PK1 AND B.PK_ESTADO=BE.PK1 AND T.PK_SORTEO="+this.getIdsorteo();

		if (!search.equals("")) {
			sql += " AND B.FOLIO LIKE '%" + search + "%'";
		}

		sql += " ORDER BY T.FOLIO ASC, B.FOLIO ASC";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS ";
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		
		
		
		
		/*  
		SELECT B.*,T.FOLIO AS 'TALONARIO' FROM BOLETOS B, TALONARIOS T
		WHERE B.SORTEO=43 AND B.PK_TALONARIO=T.PK1 
		AND B.FOLIO LIKE '%00%' ORDER BY B.PK1 ASC OFFSET (1) ROWS  FETCH NEXT 10 ROWS ONLY
		/*
		String sql = "SELECT *";
		sql += " FROM BOLETOS";
fuego contra fuego
el amor de mi vida
		sql += " WHERE SORTEO=" + this.getIdsorteo();
		if (search != "") {
			sql += " AND FOLIO LIKE '%" + search + "%'";
		}

		sql += " ORDER BY PK1 ASC";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if
															// you need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		//*/

		System.out.println("Boletos/SQL:" + sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	 
	 
	public RESULT Borrar(mBoletos obj){
		ArrayList<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1", getId()));
		list.add(new Parametros("FOLIO", "0"));
		list.add(new Parametros("FOLIODIGITAL", "0"));
		list.add(new Parametros("PK_TALONARIO", "0"));
		list.add(new Parametros("COSTO", "0"));
		list.add(new Parametros("FORMATO", "0"));
		list.add(new Parametros("ASIGNADO", "0"));
		list.add(new Parametros("PK_ESTADO", "0"));
		list.add(new Parametros("ABONO", "0"));
		list.add(new Parametros("RETORNADO", "0"));
		list.add(new Parametros("INCIDENCIA", "0"));
		list.add(new Parametros("USUARIO", "0"));

		list.add(new Parametros("LIST", "0"));
		list.add(new Parametros("StatementType", "Delete"));

		int result = db.execStoreProcedureIntId("spBOLETOS", list);
		 
		 
		String boleto_style = "<span style='color:#DDD'><strong>" + getFolio() + "</strong></span>";
		if(result == getId()) {
			super._mensaje = "El boleto "+boleto_style+" ha sido eliminado con exito";
			return RESULT.OK;
		}
		else if (result == -3) {
			super._mensaje = "El boleto " + boleto_style + " continua asignado.";
			return RESULT.ERROR;
		}
		else if (result == -2) {
			super._mensaje = "El boleto " + boleto_style + " no se puede borrar porque tiene orden de compra o esta relacionado a un comprador.";
			return RESULT.ERROR;
		}
		else {
			super._mensaje = "No se puede eliminar el boleto " + boleto_style;
			return RESULT.ERROR;
		}
	 }
		   	/*
		   	db.con();
		   	String sql = "DELETE FROM BOLETOS WHERE PK1='"+obj.getId()+"'";
		   	int res = db.execQuery(sql);
		   	
		   	if(res!=0){
		   		
		   		updateBoletostalonario(obj);
		   	}
		   	
			return res;
				
			}
	 */
	 /*
	 private void updateBoletostalonario(mBoletos obj){
		 
		 db.con();
		 String sql = "SELECT PK1 FROM BOLETOS WHERE TALONARIO='"+obj.getTalonario()+"' AND SORTEO = '"+obj.getIdsorteo()+"'";
	     int total = db.ContarFilas(sql);
	     
	     sql = "UPDATE TALONARIOS SET NUMBOLETOS='"+total+"' WHERE FOLIO='"+obj.getTalonario()+"' AND SORTEO='"+obj.getIdsorteo()+"'";
	     db.execQuery(sql);
	 }
	*/
	
	
	public int registrar(mBoletos obj){
		db.con();
	    String sql = "INSERT INTO BOLETOS (FOLIO,TALONARIO,SORTEO,FORMATO,COSTO) VALUES ('"+obj.getFolio()+"','"+obj.getTalonario()+"','"+obj.getIdsorteo()+"','FC0 - "+obj.getFormato()+"','"+obj.getCosto()+"')";
	    int pkBoleto = db.execQuerySelectId(sql);
	    //db.desconectar();
	    
		// --- Se guarda un registro de seguimiento ---
		try {
			Seguimiento.guardaAsignacion(db
					, ASIGNACION.BOVEDA
					, Long.valueOf(obj.getIdsorteo()),0,0,0
					, ASIGNACION.BOLETO, Long.valueOf(obj.getIdtalonario()) , pkBoleto
					, 'N'
					, obj.getCosto(), 0.0
					, 1
					, "FC0 - "+obj.getFormato()
					, "bov-bols-1");
		}
		catch (Exception ex) { }
		return pkBoleto;
	}
	
	
	public int updateBoletos(mBoletos obj){
		
		 db.con();
	     String sql = "UPDATE BOLETOS SET PK_TALONARIO = '"+obj.getIdtalonario()+"' WHERE TALONARIO = '"+obj.getTalonario()+"' AND SORTEO = '"+obj.getIdsorteo()+"'";
	     int res = db.execQuery(sql);
		 System.out.println(sql);
	     db.desconectar();
		 return res;
	     
	}
      
	
	
	public int revisarBoleto(mBoletos obj){
		db.con();
		String sql = "SELECT * FROM BOLETOS WHERE FOLIO = '"+obj.getFolio()+"' AND SORTEO = '"+obj.getIdsorteo()+"'";		
		System.out.println(sql);
		int numero = db.ContarFilas(sql);
		return numero;
	}
	
	
	public int RevisarTalonario(mBoletos obj){
		db.con();
		String sql = "SELECT * FROM TALONARIOS WHERE FOLIO = '"+obj.getTalonario()+"' AND SORTEO = '"+obj.getIdsorteo()+"'";
		System.out.println(sql);
		int numero = db.ContarFilas(sql);
		return numero;
	}


}
