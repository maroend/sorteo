package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mColaboradorDetalle extends SuperModel {
	
	private int id;
	private String clave;  
	private String sorteo;
	private int idcolaborador;
	private int idsector;
	private int idnicho;
	private int idtalonario;
	private String descripcion;  
	
	private String fechainico;
	private String fechatermino;
	
	private String imagen;
	
	private int activo;
	
	
	private int numTalonarios;
	private int numBoletos;
	private int numBoletosVendidos;
	
	private int numTalonariosasignados;
	private int numBoletosasignados;
	
	
	
	public int getIdcolaborador() {
		return idcolaborador;
	}

	public void setIdcolaborador(int idcolaborador) {
		this.idcolaborador = idcolaborador;
	}

	public int getIdnicho() {
		return idnicho;
	}

	public int getNumBoletosVendidos() {
		return numBoletosVendidos;
	}

	public void setNumBoletosVendidos(int numBoletosVendidos) {
		this.numBoletosVendidos = numBoletosVendidos;
	}

	public int getIdsector() {
		return idsector;
	}

	public int getIdtalonario() {
		return idtalonario;
	}

	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}

	public void setIdsector(int idsector) {
		this.idsector = idsector;
	}

	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getSorteo() {
		return sorteo;
	}

	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechainico() {
		return fechainico;
	}

	public void setFechainico(String fechainico) {
		this.fechainico = fechainico;
	}

	public String getFechatermino() {
		return fechatermino;
	}

	public void setFechatermino(String fechatermino) {
		this.fechatermino = fechatermino;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public int getNumTalonarios() {
		return numTalonarios;
	}

	public void setNumTalonarios(int numTalonarios) {
		this.numTalonarios = numTalonarios;
	}

	public int getNumBoletos() {
		return numBoletos;
	}

	public void setNumBoletos(int numBoletos) {
		this.numBoletos = numBoletos;
	}

	public int getNumTalonariosasignados() {
		return numTalonariosasignados;
	}

	public void setNumTalonariosasignados(int numTalonariosasignados) {
		this.numTalonariosasignados = numTalonariosasignados;
	}

	public int getNumBoletosasignados() {
		return numBoletosasignados;
	}

	public void setNumBoletosasignados(int numBoletosasignados) {
		this.numBoletosasignados = numBoletosasignados;
	}

	public mColaboradorDetalle() {
		// TODO Auto-generated constructor stub
	}
	
	public void Sorteo(mColaboradorDetalle obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT * FROM SORTEOS WHERE PK1 = "+obj.getId()+"";
	   	 
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						
						 this.setClave(rs.getString("CLAVE"));
						 this.setSorteo(rs.getString("SORTEO"));
						 this.setDescripcion(rs.getString("DESCRIPCION"));

					   }
					
					rs.close(); 			
				}
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	   	 
	   	 
	    }
	
	
	public String Sector(mColaboradorDetalle obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT * FROM SECTORES WHERE PK1 = "+obj.getIdsector()+"";
	   	 String dato = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 dato = rs.getString("SECTOR");
					   }
					
					rs.close(); 			
				}
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	   	 
	   	   return dato;
	   	 
	    }
		
	
	
	public String Nicho(mColaboradorDetalle obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT * FROM NICHOS WHERE PK1 = "+obj.getIdnicho()+"";
	   	 String dato = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 dato = rs.getString("NICHO");
					   }
					
					rs.close(); 			
				}
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	   	   return dato;
	   	 
	    }
	
	public String Colaborador(mColaboradorDetalle obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT concat(NOMBRE,' ',APATERNO,' ',AMATERNO) AS NOMBRE FROM COLABORADORES WHERE PK1 = "+obj.getIdcolaborador()+"";
	   	 String dato = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 dato = rs.getString("NOMBRE");
					   }
					
					rs.close(); 			
				}
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	   	   return dato;
	   	 
	    }
	
	
		
		
		public void Totaltalonarios(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+"";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 this.setNumTalonarios(Integer.parseInt(rs.getString("TOTAL")));
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		    }
		
		
		public double montoSorteo(mColaboradorDetalle obj){
			 
			 db.con();
			 String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+"";
			 ResultSet rs = db.getDatos(sql);
			 double total = 0;
			 try {
				if (rs != null && rs.next()) {
					total = (double)rs.getInt("TOTAL");
				    }
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

			 return total;
		  }
		
		
		public void TotaltalonariosAsignados(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND ASIGNADO = 1";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 this.setNumTalonariosasignados(Integer.parseInt(rs.getString("TOTAL")));
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		    }
		
		
		public void TotalboletosVendidos(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND SB.PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+" AND B.VENDIDO = 'T'";
		   	        
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
		
		
		
		public double TotalboletosVenta(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	double total = 0; 
		   	 String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND SB.PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+"";
		   	        
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = (double) rs.getInt("TOTAL");
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	         return total;
		    }
		
		
		public String TotalboletosSectorVendidos(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null; 
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND B.VENDIDO = 'T'";
		   	        
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	             return total;
		    }
		
		
		public double TotalboletosSectorVenta(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		    	double total = 0; 
		   	 String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+"";
		   	        
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = (double)rs.getInt("TOTAL");
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	            return total;
		    }
		
		
		public double TotalboletosNichoVenta(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		    	double total = 0; 
		   	 String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+"";
		   	        
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = (double)rs.getInt("TOTAL");
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	           return total;
		    }
		
		
		public double TotalboletosColaboradorVenta(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		     double total = 0; 
		   	 String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+"";
		   	        
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = (double)rs.getInt("TOTAL");
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	          return total;
		    }
		
		
		
		public String TotalboletosNichoVendidos(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null; 
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND B.VENDIDO = 'T'";
		   	        
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	            return total;
		    }
		
		
		public String TotalboletosColaboradorVendidos(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null; 
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B ";
		   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+"  AND B.VENDIDO = 'T'";
		   	        
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 			
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	           return total;
		    }
		
		
		public void Totalboletos(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+"";
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
		
		
		public void TotalboletosAsignados(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND ASIGNADO = 1";
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
		
		
		
		public String TotalboletosSector(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null;
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+"";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 	
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		   	         return total;
		    }
		
		
		public String TotalboletosSectorAsignados(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null;
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND ASIGNADO = 1";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 	
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		   	         return total;
		    }
		
		
		
		public String TotalboletosNicho(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null;
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+"";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 	
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		   	         return total;
		    }
		
		
		public String TotalboletosNichoAsignados(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null;
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND ASIGNADO = 1";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 	
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		   	         return total;
		    }
		
		
		
		public String TotalboletosColaborador(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null;
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+"";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 	
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		   	         return total;
		    }
		
		
		public String TotalboletosColaboradorAsignados(mColaboradorDetalle obj){
		   	 
		   	 db.con();
		   	 String total = null;
		   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = "+obj.getIdsector()+" AND PK_NICHO = "+obj.getIdnicho()+" AND PK_COLABORADOR = "+obj.getIdcolaborador()+" AND ASIGNADO = 1";
		   	 ResultSet rs = db.getDatos(sql);
		   	 try {
					if (rs != null) {
						
						 while(rs.next())
						   {
							 total = rs.getString("TOTAL");
						   }
						rs.close(); 	
						
					}
				} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		   	         return total;
		    }
		
		public ResultSet Sectores(mColaboradorDetalle obj){
	    	
	    	String sql = "SELECT * FROM SECTORES S, SORTEOS_ASIGNACION_SECTORES SA WHERE S.PK1 = SA.PK_SECTOR AND SA.PK_SORTEO = "+obj.getId()+"";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
		
		
	public ResultSet Nichos(mColaboradorDetalle obj){
	    	
	    	String sql = "SELECT N.PK1, N.CLAVE AS CLAVE, N.NICHO AS NICHO FROM SORTEOS_ASIGNACION_NICHOS AN, NICHOS N WHERE N.PK1 = AN.PK_NICHO AND AN.PK_SORTEO = "+obj.getId()+" AND AN.PK_SECTOR = "+obj.getIdsector()+"";
	    	System.out.println(sql);
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}


	public ResultSet Colaboradores(mColaboradorDetalle obj){
		
		String sql = "SELECT C.PK1 AS PK1, C.CLAVE AS CLAVE, CONCAT(C.NOMBRE,' ',C.APATERNO,'',C.AMATERNO) AS NOMBRE FROM COLABORADORES C,  SORTEOS_ASIGNACION_COLABORADORES AC ";
		       sql += "WHERE C.PK1 = AC.PK_COLABORADOR AND AC.PK_SORTEO = "+obj.getId()+" AND AC.PK_SECTOR = "+obj.getIdsector()+" AND AC.PK_NICHO = "+obj.getIdnicho()+";";
		ResultSet rs = db.getDatos(sql);
		return rs;
		
	}
	
	
public ResultSet Talonarios(mColaboradorDetalle obj){
		
		String sql = "SELECT T.PK1, T.FOLIO, CT.NUMBOLETOS, CT.MONTO FROM SORTEOS_COLABORADORES_TALONARIOS CT, TALONARIOS T  ";
		       sql += "WHERE  CT.PK_TALONARIO = T.PK1 AND CT.PK_SORTEO = "+obj.getId()+" AND CT.PK_SECTOR = "+obj.getIdsector()+" AND CT.PK_NICHO = "+obj.getIdnicho()+" AND CT.PK_COLABORADOR = "+obj.getIdcolaborador()+";";
		
		       ResultSet rs = db.getDatos(sql);
		return rs;
		
	}

public ResultSet Boletos(mColaboradorDetalle obj){
	
	String sql = "SELECT B.PK1, B.FOLIO, B.COSTO, B.ABONO FROM SORTEOS_COLABORADORES_BOLETOS CB, BOLETOS B  ";
	       sql += "WHERE  CB.PK_BOLETO = B.PK1 AND CB.PK_SORTEO = "+obj.getId()+" AND CB.PK_SECTOR = "+obj.getIdsector()+" AND CB.PK_NICHO = "+obj.getIdnicho()+" AND CB.PK_COLABORADOR = "+obj.getIdcolaborador()+";";   //AND CB.PK_TALONARIO = "+obj.getIdtalonario()+";";
	        System.out.println(sql);
	       ResultSet rs = db.getDatos(sql);
	return rs;
	
}

}
