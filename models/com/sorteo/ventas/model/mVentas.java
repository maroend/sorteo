package com.sorteo.ventas.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mVentas extends SuperModel {

	private int id;
	private String clave;  
	private String sorteo;
	private int idsorteo;
	private int idcolaborador;
	private int idsector;
	private int idnicho;
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
	
	private int idUsuario;

	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
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

	public mVentas() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void getUsuarioSorteo(){
		
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = "+this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		
		try {
			while (rs.next()) {
				
			 this.setIdsorteo(rs.getInt("PK_SORTEO"));	
			 this.setIdsector(rs.getInt("PK_SECTOR"));
			 this.setIdnicho(rs.getInt("PK_NICHO")); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void Sorteo(mVentas obj){
   	 
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	 
   	 
   	 
    }
	
	
	public void Totaltalonarios(mVentas obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+"";
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 this.setNumTalonarios(Integer.parseInt(rs.getString("TOTAL")));
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	
	
	public String getTalonariosVendidos(){
		 String sql = "";
	 	 String total = "";
	 	 ResultSet rs = null;
		
		 sql = "SELECT COUNT(*) AS TOTAL FROM TALONARIOS WHERE SORTEO = '"+this.getId()+"' AND VENDIDO = 'V'"; 
	                
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL");}/*rs.close();*/}} catch (SQLException e) {e.printStackTrace();}
		
		 
		 return total;
	}
	
	
	public double montoSorteo(mVentas obj){
		 
		 db.con();
		 String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+"";
		 ResultSet rs = db.getDatos(sql);
		 double total = 0;
		 try {
			if (rs != null && rs.next()) {
				total = (double)rs.getInt("TOTAL");
			    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return total;
	  }
	
	
	public void TotaltalonariosAsignados(mVentas obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+" AND ASIGNADO = 1";
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 this.setNumTalonariosasignados(Integer.parseInt(rs.getString("TOTAL")));
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	
	
	public void TotalboletosVendidos(mVentas obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_BOLETOS SB, BOLETOS B ";
	   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+" AND B.VENDIDO = 'V'";
	   	        
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 this.setNumBoletosVendidos(Integer.parseInt(rs.getString("TOTAL")));
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	
	
	
	public double TotalboletosVenta(mVentas obj){
	   	 
	   	 db.con();
	   	double total = 0; 
	   	 String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_BOLETOS SB, BOLETOS B ";
	   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+obj.getId()+"";
	   	        
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 total = (double) rs.getInt("TOTAL");
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         return total;
	    }
	
	
	public int TotalboletosSectorVendidos(mVentas obj) {
		int total = 0;
		db.con();
		String sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO AND B.VENDIDO = 'V'"
				+ " AND SB.PK_SORTEO = " + obj.getId()
				+ " AND PK_SECTOR = " + obj.getIdsector();
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public double TotalboletosSectorVenta(mVentas obj) {

		db.con();
		double total = 0;
		String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS 'TOTAL' FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO"
				+ " AND SB.PK_SORTEO = " + obj.getId()
				+ " AND PK_SECTOR = " + obj.getIdsector()
				+ " AND B.VENDIDO='V'";

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = (double) rs.getInt("TOTAL");
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	public double TotalVentaXSectorMenosComision() {
		double total = 0.0;
		String sql = "SELECT SUM(B.ABONO*(1.0-C.COMISION/100.0)) AS 'TOTAL'"
				+ " FROM SORTEOS_COLABORADORES_BOLETOS R, BOLETOS B, COLABORADORES C"
				+ " WHERE R.PK_BOLETO=B.PK1 AND R.PK_COLABORADOR=C.PK1 AND B.ABONO>0.0"
				+ " AND R.PK_SORTEO=" + this.getId()
				+ " AND R.PK_SECTOR=" + this.getIdsector();

		System.out.println("ABONO - COMISION: " + sql);

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = rs.getDouble("TOTAL");
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	
	public double TotalXVenderXSectorMenosComision() {
		double total = 0.0;
		String sql = "SELECT SUM(B.COSTO*(1.0-C.COMISION/100.0)) AS 'TOTAL'"
				+ " FROM SORTEOS_COLABORADORES_BOLETOS R, BOLETOS B, COLABORADORES C"
				+ " WHERE R.PK_BOLETO=B.PK1 AND R.PK_COLABORADOR=C.PK1 AND B.ABONO=0.0"
				+ " AND R.PK_SORTEO=" + this.getId()
				+ " AND R.PK_SECTOR=" + this.getIdsector();

		System.out.println("ABONO - COMISION: " + sql);

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = rs.getDouble("TOTAL");
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	
	public double TotalboletosNichoVenta(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           return total;
	    }
	
	
	public double TotalboletosColaboradorVenta(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          return total;
	    }
	
	
	
	public String TotalboletosNichoVendidos(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return total;
	    }
	
	
	public String TotalboletosColaboradorVendidos(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           return total;
	    }
	
	
	public void Totalboletos(mVentas obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_BOLETOS WHERE PK_SORTEO = "+obj.getId()+"";
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 this.setNumBoletos(Integer.parseInt(rs.getString("TOTAL")));
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	
	
	public void TotalboletosAsignados(mVentas obj){
	   	 
	   	 db.con();
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_BOLETOS WHERE PK_SORTEO = "+obj.getId()+" AND ASIGNADO = 1";
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						 this.setNumBoletosasignados(Integer.parseInt(rs.getString("TOTAL")));
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	
	
	
	public int TotalboletosSector(mVentas obj){
		db.con();
		int total = 0;
		String sql = " SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS"
				+ " WHERE PK_SORTEO = " + obj.getId()
				+ " AND PK_SECTOR = " + obj.getIdsector() + "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
		return total;
	}
	
	
	public String TotalboletosSectorAsignados(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	         return total;
	    }
	
	
	
	public String TotalboletosNicho(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	         return total;
	    }
	
	
	public String TotalboletosNichoAsignados(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	         return total;
	    }
	
	
	
	public String TotalboletosColaborador(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	         return total;
	    }
	
	
	public String TotalboletosColaboradorAsignados(mVentas obj){
	   	 
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	         return total;
	    }
	
	public ResultSet Sectores(mVentas obj){
    	
    	String sql = "SELECT * FROM SECTORES S, SORTEOS_ASIGNACION_SECTORES SA WHERE S.PK1 = SA.PK_SECTOR AND SA.PK_SORTEO = "+obj.getId()+"";
    	ResultSet rs = db.getDatos(sql);
    	return rs;
    	
	}
	
	
public ResultSet Nichos(mVentas obj){
    	
    	String sql = "SELECT N.PK1, N.CLAVE AS CLAVE, N.NICHO AS NICHO FROM SORTEOS_ASIGNACION_NICHOS AN, NICHOS N WHERE N.PK1 = AN.PK_NICHO AND AN.PK_SORTEO = "+obj.getId()+" AND AN.PK_SECTOR = "+obj.getIdsector()+"";
    	System.out.println(sql);
    	ResultSet rs = db.getDatos(sql);
    	return rs;
    	
	}


public ResultSet Colaboradores(mVentas obj){
	
	String sql = "SELECT C.PK1 AS PK1, C.CLAVE AS CLAVE, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS NOMBRE FROM COLABORADORES C,  SORTEOS_ASIGNACION_COLABORADORES AC ";
	       sql += "WHERE C.PK1 = AC.PK_COLABORADOR AND AC.PK_SORTEO = "+obj.getId()+" AND AC.PK_SECTOR = "+obj.getIdsector()+" AND AC.PK_NICHO = "+obj.getIdnicho()+";";
	ResultSet rs = db.getDatos(sql);
	return rs;
	
}


public double MontoSector(mVentas obj){
	 
	 db.con();
	 String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = '"+obj.getIdsector()+"' ";
	 ResultSet rs = db.getDatos(sql);
	 double total = 0;
	 try {
		if (rs != null && rs.next()) {
			total = (double)rs.getInt("TOTAL");
		    }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return total;
}


public double MontoNicho(mVentas obj) {

	db.con();
	String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS"
			+ " WHERE PK_SORTEO = " + obj.getId()
			+ " AND PK_SECTOR = '" + obj.getIdsector()
			+ "' AND PK_NICHO = '" + obj.getIdnicho() + "' ";
	ResultSet rs = db.getDatos(sql);
	double total = 0;
	try {
		if (rs != null && rs.next()) {
			total = (double)rs.getInt("TOTAL");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return total;
}


public double MontoColaboradores(mVentas obj){
	 
	 db.con();
	 String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+" AND PK_SECTOR = '"+obj.getIdsector()+"' AND PK_NICHO = '"+obj.getIdnicho()+"' AND PK_COLABORADOR = '"+obj.getIdcolaborador()+"'";
	 ResultSet rs = db.getDatos(sql);
	 double total = 0;
	 try {
		if (rs != null && rs.next()) {
			total = (double)rs.getInt("TOTAL");
		    }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 return total;
 }

}
