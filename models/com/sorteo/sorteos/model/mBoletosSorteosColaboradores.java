package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.core.Factory;
import com.core.Global;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;

public class mBoletosSorteosColaboradores extends SuperModel 
{
	private int idSorteo;
	private int idSector;
	private int idNicho;
	private int idColaborador;
	private int idBoleto;
	private int folio;
	private char estatus;
	private double abono;
	
	private int idtalonario;	
	private String sorteo;
	private String sector;
	
	private int numBoletos;
	private int numBoletosasignados;
	private int numBoletosExtraviados;
	private int numBoletosVendidos;
	private int numBoletosParcialmenteVendidos;
	
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


	public int getIdSector() {
		return idSector;
	}


	public void setIdSector(int idsector) {
		this.idSector = idsector;
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
	
	public int getIdNicho() {
		return idNicho;
	}
	
	public void setIdNicho(int idnicho) {
		this.idNicho = idnicho;
	}


	public int getIdColaborador() {
		return idColaborador;
	}


	public void setIdColaborador(int idcolaborador) {
		this.idColaborador = idcolaborador;
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

	
	
	public String consultaSorteo()
	{
		String sql = "SELECT * FROM SORTEOS WHERE PK1 =" + this.getIdSorteo();
		
		try {
			ResultSet rs = db.getDatos(sql);
			
			if (rs != null && rs.next())
				return rs.getString("SORTEO");
			
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		return "";
	}
	
	public String consultaSector()
	{
		String sql = "SELECT * FROM SECTORES WHERE PK1 = " + idSector;
		
		try {
			ResultSet rs = db.getDatos(sql);
			
			if (rs != null && rs.next())
				return rs.getString("CLAVE") + "-" + rs.getString("SECTOR");
			
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		return "";
	}
	
	public String consultaNicho()
	{
		String sql = "SELECT * FROM NICHOS WHERE PK1 = " + idNicho;
		
		try {
			ResultSet rs = db.getDatos(sql);
			
			if (rs != null && rs.next())
				return rs.getString("CLAVE") + "-" + rs.getString("NICHO");
			
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		return "";

	}

	public String consultaColaborador() {
		String sql = "SELECT NOMBRE+' '+APATERNO+' '+AMATERNO AS COLABORADOR FROM COLABORADORES WHERE PK1=" + idColaborador;
		
		try {
			ResultSet rs = db.getDatos(sql);
			
			if (rs != null && rs.next())
					return rs.getString("COLABORADOR");

		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		return "";
	}



	
	
	public mBoletosSorteosColaboradores() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/*
	public void obtenerColaborador(mBoletosSorteosColaboradores obj){
	   	 
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
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	   	 
	    }
		//*/
	
	
	public String getBoletosTalonariosColaborador(){
	 	 
	 	 db.con();
	 	 String sql = "";
	 	 String total = "";
	 	 ResultSet rs = null;
	 	
	 	 //TOTAL TALONARIOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+"";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";} /*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	 	//TALONARIOS ASIGNADOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" AND ASIGNADO = 1";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
	 	 
	 	//TALONARIOS PARCIALMENTE VENDIDOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS ST, TALONARIOS T " 
              +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'P' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TALONARIOS VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS ST, TALONARIOS T " 
	                +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'V' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL DE BOLETOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TOTAL BOLETOS ASIGNADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"'  AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" AND ASIGNADO = 1";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL BOLETOS PARCIALMENTE VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
              +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'P' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
	    
		//TOTAL BOLETOS  VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
	                +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'V' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  EXTRAVIADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" AND INCIDENCIA = 'E'";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  ROBADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR = "+this.getIdColaborador()+" AND INCIDENCIA = 'R'";
			 rs = db.getDatos(sql);
			 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
	     return total;
		
	    }
		
	
	
	public int contar(String search) {
		boolean buscar_por_incidencia = false;
		String condicion = "B.PK_SORTEO = " + getIdSorteo() + " AND B.PK_SECTOR = " + getIdSector() + " AND B.PK_NICHO = " + getIdNicho() + " AND B.PK_COLABORADOR=" + getIdColaborador();
		
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion += " AND ((B.FOLIO LIKE '" + search + "') OR (B.FOLIO_TALONARIO = " + search + "))";
			} else {
				condicion += " AND (COLABORADOR LIKE '%" + search + "%')";
			}
		}
		
		if (this.getFiltrovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'V' "; }
		if (this.getFiltronovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'N' "; }
		if (this.getFiltroextraviados() == 1) { condicion += " AND B.I_INCIDENCIA = 'E' "; buscar_por_incidencia = true; }
		if (this.getFiltrorobados() == 1) { condicion += " AND B.I_INCIDENCIA = 'R' "; buscar_por_incidencia = true; }
		
		String sql;
		if (buscar_por_incidencia)
			sql = "SELECT COUNT(*) AS 'VALUE' FROM VBOLETOS_INCIDENCIAS B WHERE " + condicion + " \n";
		else
			sql = "SELECT COUNT(*) AS 'VALUE' FROM VBOLETOS_ASIGNACION_COLABORADOR B WHERE " + condicion + " \n";
		
		System.out.println("count: " + sql);
		return db.getValue(sql, 0);
	}
	
	public ResultSet paginacion(int pg, int numreg, String search) {
		boolean buscar_por_incidencia = false;
		String condicion = "B.PK_SORTEO = " + getIdSorteo() + " AND B.PK_SECTOR = " + getIdSector() + " AND B.PK_NICHO = " + getIdNicho() + " AND B.PK_COLABORADOR=" + getIdColaborador();
		
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion += " AND ((B.FOLIO LIKE '" + search + "') OR (B.FOLIO_TALONARIO = " + search + "))";
			} else {
				condicion += " AND (COLABORADOR LIKE '%" + search + "%')";
			}
		}
		
		if (this.getFiltrovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'V' "; }
		if (this.getFiltronovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'N' "; }
		if (this.getFiltroextraviados() == 1) { condicion += " AND B.I_INCIDENCIA = 'E' "; buscar_por_incidencia = true; }
		if (this.getFiltrorobados() == 1) { condicion += " AND B.I_INCIDENCIA = 'R' "; buscar_por_incidencia = true; }
		
		String sql;
		if (buscar_por_incidencia)
			sql = "SELECT * FROM VBOLETOS_INCIDENCIAS B WHERE " + condicion + " \n";
		else
			sql = "SELECT * FROM VBOLETOS_ASIGNACION_COLABORADOR B WHERE " + condicion + " \n";
		
		sql = sql
				+ " ORDER BY B.PK_TALONARIO ASC,B.FOLIO ASC \n"
				+ " OFFSET (" + ((pg - 1) * numreg) + ") ROWS \n"
				+ " FETCH NEXT " + numreg + " ROWS ONLY \n";

		System.out.println("paginacion: " + sql);
		return db.getDatos(sql);
	}
	
	
	

	 public void guardarEstatusBoleto(String[] idsboletos, mBoletosSorteosColaboradores obj, SesionDatos sesion){
			db.con();
			
			ArrayList<String> log_list_bols = new ArrayList<String>();
			
			String sql ="";
			
			double abono = 0;
			
			
			
			for (String idboleto: idsboletos)
		    {    
				
				
				
				 sql = "SELECT COSTO,ABONO FROM BOLETOS WHERE  PK1 = '"+idboleto+"' AND SORTEO ='"+obj.getIdSorteo()+"'  ";		   	
			   	 ResultSet rs = db.getDatos(sql);
			     System.out.println(">>>SELECT BOLETOS: "+sql);
			   	 
			   	try {
			   	 
			   			   	
					if(rs.next()) {
						
						 if( obj.getEstatus() == 'V'){ abono = (double) rs.getInt("COSTO"); }
						 else if( obj.getEstatus() == 'E'){ abono = 0; }
						 else{				 
							 abono = obj.getAbono(); 							 
							 if(abono == (double) rs.getInt("COSTO")){ obj.setEstatus('V');}							 
						}
						 
						
						  sql = "UPDATE [BOLETOS]"	
					    		    + " SET VENDIDO ='"+ obj.getEstatus()
					    		    + "', ABONO ="+ abono
									+ " WHERE SORTEO=" + obj.getIdSorteo()
									//+ " AND PK_TALONARIO="+ talonarios_folio
									+ " AND PK1=" + idboleto;
					     
					     System.out.println(">>>UPDATE ESTATUS BOLETOS: "+sql);
							db.execQuery(sql);				
						
							log_list_bols.add(idboleto);
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
				
		  }
			
			// --- Se guarda un registro de seguimiento ---
			try {
				this.Log(sesion, LOG.EDITADO, this, "guardarEstatusBoleto", "bols="+log_list_bols.toString());
			} catch(Exception ex) { Factory.Error(ex, "Log"); }
			
	}
	 
	 
public void Totalboletos(mBoletosSorteosColaboradores obj){
	   	 
	   	 db.con();
	   	String sql = "SELECT  COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SNB, BOLETOS B   WHERE SNB.PK_BOLETO = B.PK1 "
				+ " AND SNB.PK_SORTEO = " + obj.getIdSorteo()
				+ " AND SNB.PK_SECTOR = " + obj.getIdSector()
				+ " AND SNB.PK_NICHO = " + obj.getIdNicho()
	   	        + " AND SNB.PK_COLABORADOR = " + obj.getIdColaborador();
		
	   	 
	  
	   	 
	   	 System.out.println(">>>SELECT NUM BOLETOS: "+sql);
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 this.setNumBoletos(Integer.parseInt(rs.getString("TOTAL")));
					   }
					rs.close(); 			
				}
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	    }


public void TotalboletosAsignados(mBoletosSorteosColaboradores obj){
  	 
  	 db.con();
  	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = "+obj.getIdSorteo()+" AND PK_SECTOR = '"+obj.getIdSector()+"' AND ASIGNADO = 1"
  			+ " AND PK_SECTOR = " + obj.getIdSector()
			+ " AND PK_NICHO = " + obj.getIdNicho()
  	        + " AND PK_COLABORADOR = " + obj.getIdColaborador();
  	 
  	 System.out.println(">>>SELECT asiganados "+sql);
  	 ResultSet rs = db.getDatos(sql);
  	 try {
			if (rs != null) {
				
				 while(rs.next())
				   {
					 this.setNumBoletosasignados(Integer.parseInt(rs.getString("TOTAL")));
				   }
				rs.close(); 			
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

   }

public void TotalboletosParcialmenteVendidos(mBoletosSorteosColaboradores obj){
 	 
 	 db.con();
 	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_BOLETOS SB, BOLETOS B ";
 	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getIdSorteo()+" AND B.VENDIDO = 'A'";
 	        
 	 ResultSet rs = db.getDatos(sql);
 	 try {
			if (rs != null) {
				
				 while(rs.next())
				   {
					 this.setNumBoletosParcialmenteVendidos(Integer.parseInt(rs.getString("TOTAL")));
				   }
				rs.close(); 			
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

  }




public void TotalboletosVendidos(mBoletosSorteosColaboradores obj){
  	 
  	 db.con();
  	 
  	 
  	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_BOLETOS SB, BOLETOS B ";
  	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getIdSorteo()+" AND B.VENDIDO = 'V'";
  	        
  	 ResultSet rs = db.getDatos(sql);
  	 try {
			if (rs != null) {
				
				 while(rs.next())
				   {
					 this.setNumBoletosVendidos(Integer.parseInt(rs.getString("TOTAL")));
				   }
				rs.close(); 			
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

   }


	public void TotalboletoseExtraviados(mBoletosSorteosColaboradores obj) {

		db.con();
		String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND S.PK_SORTEO = '"
				+ obj.getIdSorteo() + "' AND B.VENDIDO='E' ";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {
					this.setNumBoletosExtraviados(Integer.parseInt(rs
							.getString("TOTAL")));
				}
				rs.close();
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	}

}
