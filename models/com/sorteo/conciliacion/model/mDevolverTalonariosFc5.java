package com.sorteo.conciliacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SesionDatos;
import com.core.SuperModel;

public class mDevolverTalonariosFc5  extends SuperModel {
	
	
	
	//private static final String pre_formato = "FC3 - ";
	//private static final String pre_formatoB = "FC3B - ";
	
	private int idSorteo;
	private int idusuario;
	private int idSector;	
	private int idNicho;
	private int idColaborador;	
	private int idtalonario;
	private int idBoleto;
	/*
	private String clave;
	private String sorteo;
	private String sector;
	private String[] arrTalonarios;
	private String descripcion;	
	private String cadenaboletos;		
	*/
	private int regIdFormato;	
	
	private String folioTalonario;
	private String formato;

	int totalregistros = 0;
	
	
	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}
		
	public int getRegIdFormato() {
		return regIdFormato;
	}

	public void setRegIdFormato(int regIdFormato) {
		this.regIdFormato = regIdFormato;
	}

	// ________________________________


	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}
	public String getFolioTalonario() {
		return folioTalonario;
	}

	public void setFolioTalonario(String folioTalonario) {
		this.folioTalonario = folioTalonario;
	}

	public int getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(int idBoleto) {
		this.idBoleto = idBoleto;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idNicho) {
		this.idNicho = idNicho;
	}

	public int getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(int idColaborador) {
		this.idColaborador = idColaborador;
	}
	
	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	

		public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idSector) {
		this.idSector = idSector;
	}

		public int getIdtalonario() {
		return idtalonario;
	}

	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}

		//Devolver talonario FC5 (desde registro)
		public String obtenerTalonarioFC5(mDevolverTalonariosFc5 obj,int pk_sorteo){
			
			
		/*String sql = "SELECT  T.PK1 AS 'TALONARIO',  T.NUMBOLETOS AS 'NUMBOLETOS', T.ABONO AS 'ABONO', T.SORTEO AS 'SORTEO', T.FOLIO AS 'PK_TALONARIO',"
					+ "( SELECT  COUNT(B.PK1) FROM BOLETOS B WHERE B.PK_TALONARIO = T.PK1 AND B.VENDIDO = 'N' )AS 'BNOVENDIDOS',"
					+ " (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_TALONARIOS       where PK_TALONARIO = S.PK_TALONARIO) AS 'PK_SECTOR',"
					+ " (select TOP 1 PK_NICHO    from SORTEOS_NICHOS_TALONARIOS where PK_TALONARIO = S.PK_TALONARIO) AS 'PK_NICHO',"
					+ " (select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_TALONARIOS  where PK_TALONARIO = S.PK_TALONARIO) AS 'PK_COLABORADOR'"
					+ " FROM SORTEOS_TALONARIOS S, TALONARIOS T WHERE S.PK_TALONARIO = T.PK1 AND S.PK_SORTEO = "+pk_sorteo+"  AND S.PK_TALONARIO = '"+obj.getFolioTalonario()+"' ";*/
			
				String sql = "SELECT  T.PK1 AS 'TALONARIO',  T.NUMBOLETOS AS 'NUMBOLETOS',  T.PK_SORTEO AS 'SORTEO', T.FOLIO, T.ABONO,"
					+ " ( SELECT  COUNT(B.PK1) FROM BOLETOS B WHERE B.PK_TALONARIO = T.PK1 AND B.PK_ESTADO = 'N' )AS 'BNOVENDIDOS',"
					+ " (select TOP 1 PK_SECTOR  from VSECTORES_TALONARIOS SB where SB.PK_TALONARIO = T.PK1) AS 'PK_SECTOR',"
					+ " (select TOP 1 PK_NICHO    from VNICHOS_TALONARIOS NB where NB.PK_TALONARIO = T.PK1) AS 'PK_NICHO', "
					+ "(select TOP 1 PK_COLABORADOR from VCOLABORADORES_TALONARIOS CB where CB.PK_TALONARIO = T.PK1) AS 'PK_COLABORADOR'"
					+ " FROM VTALONARIOS_BOLETOS T WHERE T.PK_SORTEO = "+pk_sorteo+"  AND T.FOLIO = '"+obj.getFolioTalonario()+"'";		
			
			
			
			System.out.println("obtenerTalonarioFC5 e:"+sql);
			ResultSet rs = db.getDatos(sql);
	        
			String cadena = null;
			
			try {
				if (rs.next()) {
					
					cadena = rs.getString("TALONARIO");//0 PK1
					cadena += "|";
					cadena += rs.getString("NUMBOLETOS");//1
					cadena += "|";
					cadena +=  rs.getString("ABONO");//2
					cadena += "|";
					cadena +=  rs.getString("SORTEO");//3
					cadena += "|";				
					cadena +=  rs.getString("FOLIO");//4 FOLIO
					cadena += "|";				
					cadena +=  rs.getString("BNOVENDIDOS");//5
					cadena += "|";				
					cadena +=  rs.getString("PK_SECTOR");//6				
					cadena += "|";				
					cadena +=  rs.getString("PK_NICHO");//7 
					cadena += "|";				
					cadena +=  rs.getString("PK_COLABORADOR");//8	
					
				
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return cadena;
		}
		
		
		//FC5
		public void devolvertalonariosColaboradorfc5(int folioTalonario, SesionDatos sesion) {
			
			String sql = "";
			String stringboletos = "";
			double monto = 0;
			double costo = 0;
			
				
			 sql="SELECT    B.FOLIO, SB.PK_BOLETO ,B.COSTO FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
						+ "WHERE SB.PK_BOLETO = B.PK1"				
						+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"'"
						+ " AND SB.PK_SECTOR = '"+this.getIdSector()+"'"			
						+ " AND SB.PK_NICHO = '"+this.getIdNicho()+"' "
						 + " AND SB.PK_COLABORADOR=" + getIdColaborador()		 
						+ "AND SB.PK_TALONARIO ='"+this.getIdtalonario()+"' AND B.VENDIDO = 'N'";	
			 
			       System.out.println("BOL COLAB : "+sql);
			       ResultSet colaborador = db.getDatos(sql);
				
				
				  String sqlS = "SELECT DISPONIBLES FROM SORTEOS_NICHOS_TALONARIOS "
						+ " WHERE PK_TALONARIO=" + getIdtalonario()
						+ " AND PK_SORTEO=" + getIdSorteo()
				        + " AND PK_SECTOR=" + getIdSector()
				        + " AND PK_NICHO=" + getIdNicho();
				

				 //System.out.println("SORTEOS_TAL"+sqlS);
				 ResultSet rs = db.getDatos(sqlS);		
				
				
				try {
					if (rs.next()&&colaborador.next()) {
						
						int disponibles = rs.getInt("DISPONIBLES");				
						costo = colaborador.getInt("COSTO");
						
						
						sql =	
								
							     " DELETE [SORTEOS_COLABORADORES_TALONARIOS]"
						         + " WHERE (PK_TALONARIO=" + getIdtalonario()
						         + " AND PK_SORTEO=" + getIdSorteo()
						         + " AND PK_SECTOR=" + getIdSector()
						         + " AND PK_NICHO=" + getIdNicho()
					             + " AND PK_COLABORADOR=" + getIdColaborador()
						         + ")\n"
						
								
								 +"DELETE [SORTEOS_COLABORADORES_BOLETOS]"
											+ " WHERE (PK_TALONARIO=" + folioTalonario									
											+ " AND PK_SORTEO=" + getIdSorteo()
											+ " AND PK_SECTOR=" + getIdSector()
											+ " AND PK_NICHO=" + getIdNicho()
											+ " AND PK_COLABORADOR=" + getIdColaborador()
											+ ")\n";
						
						System.out.println(" DELETE: " + sql);
						db.execQuery(sql);			
						
					
							int boletosnv = 0;
							
							do{
								boletosnv++;	
								
								stringboletos += colaborador.getString("FOLIO")+",";		
								
								 sql =	   " UPDATE [SORTEOS_NICHOS_BOLETOS] SET ASIGNADO=0"
											+ " WHERE (PK_TALONARIO=" + folioTalonario
											+ " AND PK_BOLETO=" + colaborador.getInt("PK_BOLETO")
											+ " AND PK_SORTEO=" + getIdSorteo()
											+ " AND PK_SECTOR=" + getIdSector()
											+ " AND PK_NICHO=" + getIdNicho()
											+ ")\n"
											;
									
									db.execQuery(sql);							
														
								
							}while(colaborador.next());				    
							
							stringboletos = stringboletos.substring(0, stringboletos.length()-1); 
						
							monto = costo * boletosnv;					
							disponibles += boletosnv;				
						
						//checar ASIGNADO=0 o 1
						sql = 	" UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES = '"+ disponibles +"'"
								+ " WHERE (PK_TALONARIO=" + getIdtalonario()
								+ " AND PK_SORTEO=" + getIdSorteo()	
								+ " AND PK_SECTOR=" + getIdSector()
								+ " AND PK_NICHO=" + getIdNicho()
								+ ")\n"
								
								
								//FORMATOS COLABORADOR
								
								+"INSERT INTO SORTEOS_FORMATOS_COLABORADOR"
								+ " (PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
								+ " VALUES ('"
								+ this.getIdSorteo() + "','"
								+ this.getIdSector() + "','"
								+ this.getIdNicho() + "','"
								+ this.getIdColaborador() + "','"
								+ "FC5 - "  + this.getFormato() + "','"									
								+ folioTalonario + "','"
								+ 1 + "','" 
							    + stringboletos + "','"										
								+ boletosnv + "','"
								+ monto + "','"
								+ sesion.nickName + "')";
						

						System.out.println(">>>>>REGISTRO DE FORMATO COLABORADOR fc5" + sql);						
						db.execQuery(sql); 						    
						 // this.setRegIdFormato(id);				
						
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }				
				
				
				//Devolver talonario Nicho
				 devolvertalonariosNichosfc5(folioTalonario,sesion);  				
				
			}
		
		
		
		//FC5
		public void devolvertalonariosNichosfc5(int folioTalonario, SesionDatos sesion) {
			
			String sql = "";
			String stringboletos = "";
			double monto = 0;
			double costo = 0;
			//String valida = "";			
			
		   	
			
				
			      sql="SELECT    B.FOLIO, SB.PK_BOLETO ,B.COSTO FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B "
						+ "WHERE SB.PK_BOLETO = B.PK1"				
						+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"'"
						+ " AND SB.PK_SECTOR = '"+this.getIdSector()+"'"			
						+ " AND SB.PK_NICHO = '"+this.getIdNicho()+"' "					 
						+ "AND SB.PK_TALONARIO ='"+this.getIdtalonario()+"' AND B.VENDIDO = 'N'";	
			 
			       System.out.println("BOL nich : "+sql);
			       ResultSet nichos = db.getDatos(sql);
				
				
				  String sqlS = "SELECT DISPONIBLES FROM SORTEOS_SECTORES_TALONARIOS "
						+ " WHERE PK_TALONARIO=" + getIdtalonario()
						+ " AND PK_SORTEO=" + getIdSorteo()
				        + " AND PK_SECTOR=" + getIdSector();
				  
				 //System.out.println("SORTEOS_TAL"+sqlS);
				 ResultSet rs = db.getDatos(sqlS);	
				 
				
				
				try {
					if (rs.next()&&nichos.next()) {
						
						int disponibles = rs.getInt("DISPONIBLES");				
						costo = nichos.getInt("COSTO");
						
						
						sql =	
								
							     " DELETE [SORTEOS_NICHOS_TALONARIOS]"
						         + " WHERE (PK_TALONARIO=" + getIdtalonario()
						         + " AND PK_SORTEO=" + getIdSorteo()
						         + " AND PK_SECTOR=" + getIdSector()
						         + " AND PK_NICHO=" + getIdNicho()	
						         + ")\n"
						
								
								 +"DELETE [SORTEOS_NICHOS_BOLETOS]"
											+ " WHERE (PK_TALONARIO=" + folioTalonario									
											+ " AND PK_SORTEO=" + getIdSorteo()
											+ " AND PK_SECTOR=" + getIdSector()
											+ " AND PK_NICHO=" + getIdNicho()									
											+ ")\n";
						
						System.out.println(" DELETE: " + sql);
						db.execQuery(sql);			
						
					
							int boletosnv = 0;
							
							do{
								boletosnv++;	
								
								stringboletos += nichos.getString("FOLIO")+",";		
								
								 sql =	   " UPDATE [SORTEOS_SECTORES_BOLETOS] SET ASIGNADO=0"
											+ " WHERE (PK_TALONARIO=" + folioTalonario
											+ " AND PK_BOLETO=" + nichos.getInt("PK_BOLETO")
											+ " AND PK_SORTEO=" + getIdSorteo()
											+ " AND PK_SECTOR=" + getIdSector()									
											+ ")\n"
											;
									
									db.execQuery(sql);							
														
								
							}while(nichos.next());				    
							
							stringboletos = stringboletos.substring(0, stringboletos.length()-1); 
						
							monto = costo * boletosnv;					
							disponibles += boletosnv;				
						
						//checar ASIGNADO=0 o 1
						sql = 	" UPDATE [SORTEOS_SECTORES_TALONARIOS] SET DISPONIBLES = '"+ disponibles +"'"
								+ " WHERE (PK_TALONARIO=" + getIdtalonario()
								+ " AND PK_SORTEO=" + getIdSorteo()	
								+ " AND PK_SECTOR=" + getIdSector()						
								+ ")\n"
								
								
								//FORMATOS NICHOS
								
								+"INSERT INTO SORTEOS_FORMATOS_NICHOS"
								+ " (PK_SORTEO,PK_SECTOR,PK_NICHO,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
								+ " VALUES ('"
								+ this.getIdSorteo() + "','"
								+ this.getIdSector() + "','"
								+ this.getIdNicho() + "','"					
								+ "FC5 - "  + this.getFormato() + "','"									
								+ folioTalonario + "','"
								+ 1 + "','" 
							    + stringboletos + "','"										
								+ boletosnv + "','"
								+ monto + "','"
								+ sesion.nickName + "')";
						

						System.out.println(">>>>>REGISTRO DE FORMATO NICHOS fc5" + sql);						
						db.execQuery(sql); 						    
						 // this.setRegIdFormato(id);				
						
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }	
				
				//Devuelve el talonario a Sector
				devolvertalonariosSectoresfc5( folioTalonario, sesion);					
		    
			}
		
		
		
		//FC5
		public void devolvertalonariosSectoresfc5(int folioTalonario, SesionDatos sesion) {
			
			String sql = "";
			String stringboletos = "";
			double monto = 0;
			double costo = 0;
			//String valida = "";	    
	
			
				
			      sql="SELECT    B.FOLIO, SB.PK_BOLETO ,B.COSTO FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B "
						+ "WHERE SB.PK_BOLETO = B.PK1"				
						+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"'"
						+ " AND SB.PK_SECTOR = '"+this.getIdSector()+"'"							 
						+ "AND SB.PK_TALONARIO ='"+this.getIdtalonario()+"' AND B.VENDIDO = 'N'";	
			 
			       System.out.println("BOL sectores : "+sql);
			       ResultSet sectores = db.getDatos(sql);
				
				
				  String sqlS = "SELECT DISPONIBLES FROM SORTEOS_TALONARIOS "
						+ " WHERE PK_TALONARIO=" + getIdtalonario()
						+ " AND PK_SORTEO=" + getIdSorteo();
				       
				  
				 //System.out.println("SORTEOS_TAL"+sqlS);
				 ResultSet rs = db.getDatos(sqlS);	
				 
				
				
				try {
					if (rs.next()&&sectores.next()) {
						
						int disponibles = rs.getInt("DISPONIBLES");				
						costo = sectores.getInt("COSTO");
						
						
						sql =	
								
							     " DELETE [SORTEOS_SECTORES_TALONARIOS]"
						         + " WHERE (PK_TALONARIO=" + getIdtalonario()
						         + " AND PK_SORTEO=" + getIdSorteo()
						         + " AND PK_SECTOR=" + getIdSector()				        
						         + ")\n"
						
								
								 +"DELETE [SORTEOS_SECTORES_BOLETOS]"
											+ " WHERE (PK_TALONARIO=" + folioTalonario									
											+ " AND PK_SORTEO=" + getIdSorteo()
											+ " AND PK_SECTOR=" + getIdSector()																	
											+ ")\n";
						
						System.out.println(" DELETE TAL Y BOL SECTORES: " + sql);
						db.execQuery(sql);			
						
					
							int boletosnv = 0;
							
							do{
								boletosnv++;	
								
								stringboletos += sectores.getString("FOLIO")+",";		
								
								 sql =	   " UPDATE [SORTEOS_BOLETOS] SET ASIGNADO=0"
											+ " WHERE (PK_TALONARIO=" + folioTalonario
											+ " AND PK_BOLETO=" + sectores.getInt("PK_BOLETO")
											+ " AND PK_SORTEO=" + getIdSorteo()																
											+ ")\n"
											;
									
									db.execQuery(sql);							
														
								
							}while(sectores.next());				    
							
							stringboletos = stringboletos.substring(0, stringboletos.length()-1); 
						
							monto = costo * boletosnv;					
							disponibles += boletosnv;				
						
						//checar ASIGNADO=0 o 1
						sql = 	" UPDATE [SORTEOS_TALONARIOS] SET DISPONIBLES = '"+ disponibles +"', ASIGNADO=0"
								+ " WHERE (PK_TALONARIO=" + getIdtalonario()
								+ " AND PK_SORTEO=" + getIdSorteo()											
								+ ")\n"
								
								
								//FORMATOS SECTORES
								
								+"INSERT INTO SORTEOS_FORMATOS_SECTORES"
								+ " (PK_SORTEO,PK_SECTOR,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
								+ " VALUES ('"
								+ this.getIdSorteo() + "','"
								+ this.getIdSector() + "','"								
								+ "FC5 - "  + this.getFormato() + "','"									
								+ folioTalonario + "','"
								+ 1 + "','" 
							    + stringboletos + "','"										
								+ boletosnv + "','"
								+ monto + "','"
								+ sesion.nickName + "')";
						

						System.out.println(">>>>>REGISTRO DE FORMATO SECTORES fc5" + sql);						
						db.execQuery(sql); 						    
						 // this.setRegIdFormato(id);				
						
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }	
				
				
				
				//REGRESO A BOVEDA
				 //devolvertalonariosBovedafc5( folioTalonario,  sesion);
				
		    
		 }
		
		
		
		//FC5
				public void devolvertalonariosBovedafc5(int folioTalonario, SesionDatos sesion) {
					
					String sql = "";
					String stringboletos = "";
					//double monto = 0;
					//double costo = 0;
					//String valida = "";	    
			
					
						
					      sql="SELECT    B.FOLIO, SB.PK_BOLETO ,B.COSTO FROM SORTEOS_BOLETOS SB, BOLETOS B "
								+ "WHERE SB.PK_BOLETO = B.PK1"				
								+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"'"													 
								+ " AND SB.PK_TALONARIO ='"+this.getIdtalonario()+"' AND B.VENDIDO = 'N'";	
					 
					       System.out.println("BOL  : "+sql);
					       ResultSet boletos = db.getDatos(sql);
						
						
						  /*String sqlS = "SELECT DISPONIBLES FROM TALONARIOS "
								+ " WHERE PK_TALONARIO=" + getIdtalonario()
								+ " AND PK_SORTEO=" + getIdSorteo();*/
						       
						  
						 //System.out.println("SORTEOS_TAL"+sqlS);
					//	 ResultSet rs = db.getDatos(sqlS);	
						 
						
						
						try {
							if (boletos.next()) {
								
							//	int disponibles = rs.getInt("DISPONIBLES");				
								//costo = sectores.getInt("COSTO");
								
								
								sql =	
										
									     " DELETE [SORTEOS_TALONARIOS]"
								         + " WHERE (PK_TALONARIO=" + getIdtalonario()
								         + " AND PK_SORTEO=" + getIdSorteo()								      			        
								         + ")\n"
								
										
										 +"DELETE [SORTEOS_BOLETOS]"
													+ " WHERE (PK_TALONARIO=" + folioTalonario									
													+ " AND PK_SORTEO=" + getIdSorteo()																								
													+ ")\n";
								
								System.out.println(" DELETE TAL Y BOL : " + sql);
								db.execQuery(sql);			
								
							
									//int boletosnv = 0;
									
									do{
										//boletosnv++;	
										
										stringboletos += boletos.getString("FOLIO")+",";		
										
										 sql =	   " UPDATE [BOLETOS] SET ASIGNADO=0"
													+ " WHERE (PK_TALONARIO=" + folioTalonario
													+ " AND PK1=" + boletos.getInt("PK_BOLETO")
													+ " AND SORTEO=" + getIdSorteo()																
													+ ")\n"
													;
											
											db.execQuery(sql);							
																
										
									}while(boletos.next());				    
									
									stringboletos = stringboletos.substring(0, stringboletos.length()-1); 
								
									//monto = costo * boletosnv;					
									//disponibles += boletosnv;				
								
								//checar ASIGNADO=0 o 1
								sql = 	" UPDATE [TALONARIOS] SET ASIGNADO=0"
										+ " WHERE (PK1=" + getIdtalonario()
										+ " AND SORTEO=" + getIdSorteo()											
										+ ")\n"
										
										
										//FORMATOS SECTORES
										
									/*	+"INSERT INTO SORTEOS_FORMATOS_SECTORES"
										+ " (PK_SORTEO,PK_SECTOR,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
										+ " VALUES ('"
										+ this.getIdSorteo() + "','"
										+ this.getIdSector() + "','"								
										+ "FC5 - "  + this.getFormato() + "','"									
										+ folioTalonario + "','"
										+ 1 + "','" 
									    + stringboletos + "','"										
										+ boletosnv + "','"
										+ monto + "','"
										+ sesion.nickName + "')"*/  ;
								

								System.out.println(">>>>>REGISTRO DE UPDATE TAL boveda fc5" + sql);						
								db.execQuery(sql); 						    
								 // this.setRegIdFormato(id);			
								
								
							}
						} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }						
						
					
						
				    
				 }

		
	
	
	
	
	
	

}
