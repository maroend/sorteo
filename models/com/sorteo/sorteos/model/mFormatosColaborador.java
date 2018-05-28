package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mFormatosColaborador extends SuperModel 
{

	//private int id;	
	private int idnicho;
	private int idcolaborador;
	private String colaborador;
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


	public int getIdcolaborador() {
		return idcolaborador;
	}


	public void setIdcolaborador(int idcolaborador) {
		this.idcolaborador = idcolaborador;
	}


	public String getColaborador() {
		return colaborador;
	}


	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
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

	
	
	public mFormatosColaborador() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public void obtenerColaborador(mFormatosColaborador obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT NOMBRE+' '+APATERNO+' '+AMATERNO AS COLABORADOR FROM COLABORADORES WHERE PK1 = "+obj.getIdcolaborador()+"";
	   	 
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 if(rs.next())
					   {
						
						 obj.setColaborador(rs.getString("COLABORADOR"));
						

					   }
					
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				com.core.Factory.Error(e, sql);
			}
	   	 
	   	 
	   	 
	    }
		
		
	
	
	public int contar(int idsorteo,int idsector,int idnicho,int idcolaborador) {
		
		String sql = "SELECT PK1 FROM FORMATOS "//WHERE  PK_SORTEO = '"+idsorteo+"'
			    + "WHERE PK_SECTOR = '"+idsector+"' AND PK_NICHO = '"+idnicho+"'  "
				+ "AND PK_COLABORADOR = '"+idcolaborador+"'";
		
		int numero = db.ContarFilas(sql);
		System.out.println(">>>>SQL BOL:"+sql);

		return numero;
	}

	public ResultSet paginacion(int pg, int numreg, String search,int idsorteo,int idsector,int idnicho,int idcolaborador) {
		
	String sql = "SELECT * FROM FORMATOS"
			//+ " WHERE  PK_SORTEO = '"+idsorteo+"' "
		    + " WHERE PK_SECTOR = '"+idsector+"' AND PK_NICHO = '"+idnicho+"'  "
			+ "AND PK_COLABORADOR = '"+idcolaborador+"'";

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
