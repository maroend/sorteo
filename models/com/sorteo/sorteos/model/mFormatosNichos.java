package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mFormatosNichos extends SuperModel 
{

	//private int id;
	private int idnicho;
	private String nicho;
	private int idSorteo;
	private int idBoleto;
	private int folio;
	private char estatus;
	private double abono;
	
	private int idtalonario;
	private String sorteo;
	private String sector;
	private int idsector;
	
	private int numBoletos;
	private int numBoletosasignados;
	private int numBoletosExtraviados;
	private int numBoletosVendidos;
	private int numBoletosParcialmenteVendidos;
	
	
	
	
	public int getNumBoletos() {
		return numBoletos;
	}


	public void setNumBoletos(int numBoletos) {
		this.numBoletos = numBoletos;
	}


	public int getNumBoletosasignados() {
		return numBoletosasignados;
	}


	public void setNumBoletosasignados(int numBoletosasignados) {
		this.numBoletosasignados = numBoletosasignados;
	}


	public int getNumBoletosExtraviados() {
		return numBoletosExtraviados;
	}


	public void setNumBoletosExtraviados(int numBoletosExtraviados) {
		this.numBoletosExtraviados = numBoletosExtraviados;
	}


	public int getNumBoletosVendidos() {
		return numBoletosVendidos;
	}


	public void setNumBoletosVendidos(int numBoletosVendidos) {
		this.numBoletosVendidos = numBoletosVendidos;
	}


	public int getNumBoletosParcialmenteVendidos() {
		return numBoletosParcialmenteVendidos;
	}


	public void setNumBoletosParcialmenteVendidos(int numBoletosParcialmenteVendidos) {
		this.numBoletosParcialmenteVendidos = numBoletosParcialmenteVendidos;
	}


	public double getAbono() {
		return abono;
	}


	public int getIdsector() {
		return idsector;
	}


	public void setIdsector(int idsector) {
		this.idsector = idsector;
	}


	public void setAbono(double abono) {
		this.abono = abono;
	}

	
	public char getEstatus() {
		return estatus;
	}


	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}
	
	public int getIdnicho() {
		return idnicho;
	}


	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
	}


	public String getNicho() {
		return nicho;
	}


	public void setNicho(String nicho) {
		this.nicho = nicho;
	}


	public int getIdSorteo() {
		return idSorteo;
	}


	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}


	public int getIdBoleto() {
		return idBoleto;
	}


	public void setIdBoleto(int idBoleto) {
		this.idBoleto = idBoleto;
	}


	public int getFolio() {
		return folio;
	}


	public void setFolio(int folio) {
		this.folio = folio;
	}


	public int getIdtalonario() {
		return idtalonario;
	}


	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}


	public String getSorteo() {
		return sorteo;
	}


	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}

	
	
	public mFormatosNichos() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public void obtenerNicho(mFormatosNichos obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT NICHO FROM NICHOS WHERE PK1 = "+obj.getIdnicho()+"";
	   	 
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 if(rs.next())
					   {
						
						 obj.setNicho(rs.getString("NICHO"));
						

					   }
					
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				com.core.Factory.Error(e, sql);
			}
	   	 
	   	 
	   	 
	    }
		
		
	
	



public int contar(int idsorteo,int idsector,int idnicho) {
	String sql = "SELECT PK1";
	sql += " FROM FORMATOS WHERE PK_SECTOR = '"+idsector+"' AND PK_NICHO = '"+idnicho+"' AND PK_COLABORADOR IS NULL ";
	int numero = db.ContarFilas(sql);
	System.out.println(">>>>SQL BOL:"+sql);

	return numero;
}

public ResultSet paginacion(int pg, int numreg, String search,int idsorteo,int idsector,int idnicho) {
	//String sql = "SELECT  B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO ";
	//sql += "FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+idsorteo+"'";
	
String sql = "SELECT * FROM FORMATOS WHERE  PK_SECTOR = '"+idsector+"' AND PK_NICHO = '"+idnicho+"' AND PK_COLABORADOR IS NULL ";


	if (search != "") {
		sql += " AND FOLIO LIKE '%" + search + "%'  ";
	}

	sql += "ORDER BY FOLIO ASC ";
	sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
														// need -1
	sql += "FETCH NEXT " + numreg + " ROWS ONLY";

	
	System.out.println(">>>sql: "+sql);
	ResultSet rs = db.getDatos(sql);

	return rs;
}

	
	

	 
}
