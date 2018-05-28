package com.sorteo.sorteos.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import com.core.Factory;
import com.core.Seguimiento;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;
import com.core.Seguimiento.ASIGNACION;

public class mBoletosSorteosParalelos extends SuperModel {
	
	
	
	private int getIdSorteoP;
	private int idsector;
	private int idnicho;
	private int idColaborador;
	private int idSorteo;
	private int idBoleto;	//private int folio;
	
	private char estatus;


	private double abono;
	private double costo;
	
	private String boleto;
	
	private int pktalonario;
	private int idtalonario;	// folio del talonario
	private String sorteo;
	private String sector;
	private int numBoletos;
	private int numBoletosasignados;
	private int numBoletosExtraviados;
	private int numBoletosVendidos;
	private int numBoletosParcialmenteVendidos;
	
	private char incidencia;
	private String formatofc8;
	private String folioactamp;
	private String detallesincidencia;
	
	private String nombre;
	private String apellidos;
	private String telefonof;
	private String telefonom;
	private String correo;
	
	private String calle;
	private String numero;
	private String colonia;
	private String estado;
	private String municipio;
	
	private String usuario;
	private int idUsuario;
	
	
	private int filtrovendidos;
	private int filtronovendidos;
	private int filtroparciales;
	private int filtroextraviados;
	private int filtrorobados;
	
	private boolean sorteoActivo;
	
	public mBoletosSorteosParalelos() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int getIdSorteoP() {
		return getIdSorteoP;
	}


	public void setIdSorteoP(int getIdSorteoP) {
		this.getIdSorteoP = getIdSorteoP;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	
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


	public int getPktalonario() {
		return pktalonario;
	}


	public void setPktalonario(int pktalonario) {
		this.pktalonario = pktalonario;
	}


	public int getIdsector() {
		return idsector;
	}


	public void setIdsector(int idsector) {
		this.idsector = idsector;
	}


	public int getIdColaborador() {
		return idColaborador;
	}


	public void setIdColaborador(int icolaborador) {
		this.idColaborador = icolaborador;
	}


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public char getIncidencia() {
		return incidencia;
	}


	public void setIncidencia(char incidencia) {
		this.incidencia = incidencia;
	}


	public String getFormatofc8() {
		return formatofc8;
	}


	public void setFormatofc8(String formatofc8) {
		this.formatofc8 = formatofc8;
	}


	public String getFolioactamp() {
		return folioactamp;
	}


	public void setFolioactamp(String folioactamp) {
		this.folioactamp = folioactamp;
	}


	public String getDetallesincidencia() {
		return detallesincidencia;
	}


	public void setDetallesincidencia(String detallesincidencia) {
		this.detallesincidencia = detallesincidencia;
	}


	public String getBoleto() {
		return boleto;
	}


	public void setBoleto(String boleto) {
		this.boleto = boleto;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getTelefonof() {
		return telefonof;
	}


	public void setTelefonof(String telefonof) {
		this.telefonof = telefonof;
	}


	public String getTelefonom() {
		return telefonom;
	}


	public void setTelefonom(String telefonom) {
		this.telefonom = telefonom;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getColonia() {
		return colonia;
	}


	public void setColonia(String colonia) {
		this.colonia = colonia;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getMunicipio() {
		return municipio;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	public int getNumBoletosParcialmenteVendidos() {
		return numBoletosParcialmenteVendidos;
	}


	public void setNumBoletosParcialmenteVendidos(int numBoletosParcialmenteVendidos) {
		this.numBoletosParcialmenteVendidos = numBoletosParcialmenteVendidos;
	}


	public int getNumBoletosVendidos() {
		return numBoletosVendidos;
	}


	public void setNumBoletosVendidos(int numBoletosVendidos) {
		this.numBoletosVendidos = numBoletosVendidos;
	}


	public int getNumBoletosExtraviados() {
		return numBoletosExtraviados;
	}


	public void setNumBoletosExtraviados(int numBoletosExtraviados) {
		this.numBoletosExtraviados = numBoletosExtraviados;
	}


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

	
	public char getEstatus() {
		return estatus;
	}


	public double getAbono() {
		return abono;
	}


	public void setAbono(double abono) {
		this.abono = abono;
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


	/*
	public int getFolio() {
		return folio;
	}


	public void setFolio(int folio) {
		this.folio = folio;
	}
	*/


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

	
	public boolean getSorteoActivo() {
		return sorteoActivo;
	}


	public void setSorteoActivo(boolean sorteoActivo) {
		this.sorteoActivo = sorteoActivo;
	}
	
	
	public ResultSet getMunicipios(int idestado) {
			
    	String sql = "SELECT DISTINCT(D_mnpio),c_mnpio FROM SEPOMEX WHERE c_estado ="+idestado;

		ResultSet rs = db.getDatos(sql);

		return rs;
	}
	
	
	
	
	public int contar(int idsorteo,String search,int idsorteoparalelo) {
		
		
		String sql = "SELECT COUNT(*) AS TOTAL"
				+ " FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SPN, SORTEOS_PARALELOS SP , COLABORADORES C"
				+ " WHERE SPN.PK_COLABORADOR = C.PK1 AND SP.PK1 = SPN.PK_SORTEO_PARALELO AND SP.PK1 ="+idsorteoparalelo;
		
		
		  // Regex isnumber = new Regex("[^0-9]");
		  // if(!search.matches(search)){
			   
		 //  }
		
	
		
		
		
		if (search != "") {
			
			if(isNumeric(search)){sql += " AND (SPN.FOLIO = '" + search + "')";}
			else{sql += " AND  ((C.NOMBRE LIKE'%" + search + "%') OR (C.APATERNO LIKE '%" + search + "%') )    ";}	
			
		}
		
		System.out.println(">>>>count: "+sql);
		
		int numero = 0;		
		
		/*if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '" + search + "'))   ";
		}		
	    */
		
		ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						
						 numero = rs.getInt("TOTAL");

					   }
					
					rs.close(); 			
				}
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 

		return numero;
	}

	public ResultSet paginacion(int pg, int numreg, String search,int idsorteo,int idsorteoparalelo) {
		 int max = 0;
			String sql2 = "SELECT LEN(MAX(SA.FOLIO)) AS MAX FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SA, COLABORADORES C "
					+ "WHERE  SA.PK_COLABORADOR = C.PK1 "			
					+ " AND SA.PK_SORTEO_PARALELO = " + idsorteoparalelo;
					//+ " AND PK_SECTOR = '"+obj.getIdsector()+"'"
					//+ " AND SA.PK_NICHO = '" + obj.getIdNicho() + "'";
			
			 ResultSet rsm = db.getDatos(sql2);
			
			 try {
					if (rsm != null && rsm.next()) {
					    	max = rsm.getInt("MAX");					    	
					    	if(max<4){max = 4;}
					    	
					    }
				 } catch (SQLException e) { Factory.Error(e, sql2); }	
	
		
		
		String sql = "SELECT SPN.*,(C.NOMBRE+' '+C.APATERNO) AS  'COLABORADOR' ,"
				+ " (RIGHT(REPLICATE('0', "+max+") + Ltrim(Rtrim(SPN.FOLIO)),"+max+")) AS FOLIOCEROS"
				+ ", (select TOP 1 SECTOR  FROM SECTORES  WHERE PK1 = SPN.PK_SECTOR) AS 'SECTOR'"
				
				+ ",(SELECT TOP 1 NICHO  FROM NICHOS  WHERE PK1=(SELECT  TOP 1 PK_NICHO FROM SORTEOS_PARALELOS_NICHOS WHERE PK_NICHO = SPN.PK_NICHO   ) ) AS 'NICHO'"
				//+ ",(SELECT TOP 1 NOMBRE+' '+APATERNO   FROM COLABORADORES  WHERE PK1 = SPN.PK_COLABORADOR ) AS 'COLABORADOR'"
				
				+ " FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SPN, SORTEOS_PARALELOS SP, COLABORADORES C WHERE SPN.PK_COLABORADOR = C.PK1 AND SP.PK1 = SPN.PK_SORTEO_PARALELO AND SP.PK1 ="+idsorteoparalelo;
		
		if (search != "") {

			if(isNumeric(search)){sql += " AND (SPN.FOLIO = '" + search + "')";}
			else{sql += " AND  ((C.NOMBRE LIKE'%" + search + "%') OR (C.APATERNO LIKE '%" + search + "%') )    ";}	
			
			}
		

		sql += " ORDER BY FOLIOCEROS ASC ";
		sql += " OFFSET (" + ((pg - 1) * numreg) + ") ROWS "; // -- not sure if you
															  // need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		
		System.out.println(">>>>paginacion: "+sql);
		ResultSet rs = db.getDatos(sql);

		return rs;
	}
	
	
	
public ResultSet exportSorteoParalelos( SesionDatos sesion,int idsorteoparalelo) {//String search,int idsorteo,
		
	
	 int max = 0;
		String sql2 = "SELECT LEN(MAX(SA.FOLIO)) AS MAX FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SA, COLABORADORES C "
				+ "WHERE  SA.PK_COLABORADOR = C.PK1 "			
				+ " AND SA.PK_SORTEO_PARALELO = " + idsorteoparalelo;
				//+ " AND PK_SECTOR = '"+obj.getIdsector()+"'"
				//+ " AND SA.PK_NICHO = '" + obj.getIdNicho() + "'";
		
		 ResultSet rsm = db.getDatos(sql2);
		
		 try {
				if (rsm != null && rsm.next()) {
				    	max = rsm.getInt("MAX");					    	
				    	if(max<4){max = 4;}
				    	
				    }
			 } catch (SQLException e) { Factory.Error(e, sql2); }	
	
	
				
		
		String sql = "SELECT SPN.*,"
				+ " (RIGHT(REPLICATE('0', "+max+") + Ltrim(Rtrim(SPN.FOLIO)),"+max+")) AS FOLIOCEROS"
				+ ",(select TOP 1 SECTOR  FROM SECTORES  WHERE PK1 = SPN.PK_SECTOR) AS 'SECTOR'"
				+ ",(SELECT TOP 1 NICHO  FROM NICHOS  WHERE PK1=(SELECT  TOP 1 PK_NICHO FROM SORTEOS_PARALELOS_NICHOS WHERE PK_NICHO = SPN.PK_NICHO   ) ) AS 'NICHO'"
				+ ",(SELECT TOP 1 NOMBRE+' '+APATERNO   FROM COLABORADORES  WHERE PK1 = SPN.PK_COLABORADOR ) AS 'COLABORADOR'"
				+ " FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SPN, SORTEOS_PARALELOS SP WHERE SP.PK1 = SPN.PK_SORTEO_PARALELO AND SP.PK1 ="+idsorteoparalelo;
		/*if (search != "") {
			sql += " AND (SPN.FOLIO = '" + search + "')    ";*/	
	

		sql += " ORDER BY CAST(SPN.FOLIO AS INT) ASC ";
	
		
		System.out.println("sorteoparalelo excel: "+sql);
		ResultSet rs = db.getDatos(sql);

		return rs;
	}

public ResultSet exportSorteoParalelos_all( SesionDatos sesion) {//String search,int idsorteo,
	
	
	
	String sql = "SELECT SPN.*"		
			+ ",(SELECT TOP 1 NOMBRE+' '+APATERNO   FROM COLABORADORES  WHERE PK1 = SPN.PK_COLABORADOR ) AS 'COLABORADOR' "
			+ ", (RIGHT(REPLICATE('0', ("
			+ "SELECT"
			+ " CASE"
			+ " WHEN LEN(MAX(FOLIO))<4 "
			+ " THEN 4 "
			+ " ELSE LEN(MAX(FOLIO)) "
			+ " END "
			+ "FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES WHERE PK_SORTEO_PARALELO = SPN.PK_SORTEO_PARALELO) ) + Ltrim(Rtrim(SPN.FOLIO)),("
			+ "SELECT"
			+ " CASE "
			+ " WHEN LEN(MAX(FOLIO)) < 4 "
			+ " THEN 4 "
			+ " ELSE LEN(MAX(FOLIO)) "
			+ " END FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES WHERE PK_SORTEO_PARALELO = SPN.PK_SORTEO_PARALELO) )) AS FOLIOCEROS"			
			+ ", SP.NOMBRE"
			+ ", SPN.FOLIO"			
			+ " FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SPN, SORTEOS_PARALELOS SP WHERE SP.PK1 = SPN.PK_SORTEO_PARALELO  " ;
	/*if (search != "") {
		sql += " AND (SPN.FOLIO = '" + search + "')    ";*/	


	sql += " ORDER BY COLABORADOR ";

	
	System.out.println("sorteoparalelo_ALL excel: "+sql);
	ResultSet rs = db.getDatos(sql);

	return rs;
}
	
	
	
	public void Sorteo(mBoletosSorteosParalelos obj)
	{
		db.con();
		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + obj.getIdSorteo()+ "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				if (rs.next()) {
					// this.setClave(rs.getString("CLAVE"));
					this.setSorteo(rs.getString("SORTEO"));
					// this.setDescripcion(rs.getString("DESCRIPCION"));
					int activo = rs.getInt("ACTIVO");
					this.setSorteoActivo(activo==1);
				}
				rs.close();
			}
		} catch (SQLException ex) {
			com.core.Factory.Error(ex, sql);
		}
	}
	
	
	public void SorteoParalelo(mBoletosSorteosParalelos obj)
	{
		db.con();
		String sql = "SELECT * FROM SORTEOS_PARALELOS WHERE PK1 = " + obj.getIdSorteoP()+ "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				if (rs.next()) {
					// this.setClave(rs.getString("CLAVE"));
					this.setSorteo(rs.getString("NOMBRE"));
					// this.setDescripcion(rs.getString("DESCRIPCION"));
					//int activo = rs.getInt("ACTIVO");
				//	this.setSorteoActivo(activo==1);
				}
				rs.close();
			}
		} catch (SQLException ex) {
			com.core.Factory.Error(ex, sql);
		}
	}


    



    public String getBoletosSorteosColaborador(){
 	 
 	 db.con();
 	 String sql = "";
 	 String total = "";
 	 ResultSet rs = null;
 	
 	 //TOTAL COLABORADORES
 	 sql = "SELECT  COUNT(DISTINCT PK_COLABORADOR) AS TOTAL FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SPN, SORTEOS_PARALELOS SP WHERE SP.PK1 = SPN.PK_SORTEO_PARALELO AND SP.PK1 = '"+this.getIdSorteoP()+"'";
 	 rs = db.getDatos(sql);
 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";} /*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

 	 
	//TOTAL DE BOLETOS
 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES SPN, SORTEOS_PARALELOS SP WHERE SP.PK1 = SPN.PK_SORTEO_PARALELO AND SP.PK1 = '"+this.getIdSorteoP()+"'";
	 rs = db.getDatos(sql);
	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
 	
		 System.out.println(total);
		 
		
		 
		 return total;
		 
    }
    
    
    public void setPredeterminadoSorteo(){
    	//, PK_SECTOR = "+this.getIdSector()+",  PK_NICHO="+this.getIdNicho()+"
    		
    		String sql = "UPDATE USUARIOS SET PK_SORTEO = "+this.getIdSorteo()+", FECHA_M = GETDATE()  WHERE PK1 ="+this.getIdUsuario();
    		db.execQuery(sql);
    	}
    
    private static boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }


   
	
	

}
