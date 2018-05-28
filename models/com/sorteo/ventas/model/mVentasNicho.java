package com.sorteo.ventas.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mVentasNicho extends SuperModel {

	private String clave;  
	private String sorteo;
	private String sector;
	private String nicho;
	
	private int idsorteo;
	private int idsector;
	private int idnicho;
	private int idcolaborador;
	private String descripcion;  
	
	private String fechainico;
	private String fechatermino;
	
	private String imagen;
	
	private int activo;
	
	
	private int numTalonarios;
	private int numTalonariosVendidos;
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

	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
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
	
	public int getNumTalonariosVendidos() {
		return numTalonariosVendidos;
	}
	
	public void setNumTalonariosVendidos(int numTalonariosVendidos) {
		this.numTalonariosVendidos = numTalonariosVendidos;
	}
	
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}

	public mVentasNicho() {
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
			e.printStackTrace();
		}
		
		
	}
	
	
	public void Sorteo(mVentasNicho obj) {
		// TODO Auto-generated 

		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + obj.getIdsorteo();

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {

				this.setClave(rs.getString("CLAVE"));
				this.setSorteo(rs.getString("SORTEO"));
				this.setDescripcion(rs.getString("DESCRIPCION"));

				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String consultaSector(mVentasNicho obj) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = " + obj.getIdsector();
		String sector = "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {

				sector = rs.getString("CLAVE") + " " + rs.getString("SECTOR");

				setSector(rs.getString("SECTOR"));

				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }

		return sector;
	}
	
	public String consultaNicho(mVentasNicho obj) {

		String sql = "SELECT * FROM NICHOS WHERE PK1 = " + obj.getIdnicho();
		String nicho = "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {

				nicho = rs.getString("CLAVE") + " " + rs.getString("NICHO");

				setNicho(rs.getString("NICHO"));

				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }

		return nicho;
	}
	
	
	public void consultaTalonarios(mVentasNicho obj) {
		// TODO Auto-generated 

		String sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_NICHOS_TALONARIOS"
				+ " WHERE PK_SORTEO = " + obj.getIdsorteo()
				+ " AND PK_SECTOR = " + obj.getIdsector()
				+ " AND PK_NICHO = " + obj.getIdnicho();
		System.out.println("--> "+sql);
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				this.setNumTalonarios(rs.getInt("TOTAL"));
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void consultaTalonariosVendidos() {
		// TODO Auto-generated 
		String sql = "";
		ResultSet rs = null;

		sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_NICHOS_TALONARIOS R, TALONARIOS T"
			+ " WHERE R.PK_SORTEO = " + this.getIdsorteo()
			+ " AND R.PK_SECTOR = " + this.getIdsector()
			+ " AND R.PK_NICHO = " + this.getIdnicho()
			+ " AND R.PK_TALONARIO = T.PK1 AND T.VENDIDO='V'";

		rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				setNumTalonariosVendidos(rs.getInt("TOTAL"));
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void TotalboletosVendidos(mVentasNicho obj){
		// TODO Auto-generated 
	   	 
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B "
	   			 	+ " WHERE B.PK1 = SB.PK_BOLETO"
	   			 	+ " AND SB.PK_SORTEO = " + obj.getIdsorteo()
	   			 	+ " AND SB.PK_SECTOR = " + obj.getIdsector()
	   			 	+ " AND SB.PK_NICHO = " + obj.getIdnicho()
	   			 	+ " AND B.VENDIDO = 'V'";
	   	        
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null && rs.next()){
					this.setNumBoletosVendidos(Integer.parseInt(rs.getString("TOTAL")));
					rs.close(); 			
				}
		} catch (SQLException e) {
				e.printStackTrace();
		}

	}
	
	
	public int TotalBoletosVendidosNicho(mVentasNicho obj) {
		// TODO Auto-generated 
		int total = 0;
		String sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO AND B.VENDIDO = 'V'"
				+ " AND SB.PK_SORTEO = " + obj.getIdsorteo()
				+ " AND PK_SECTOR = " + obj.getIdsector()
				+ " AND PK_NICHO = " + obj.getIdnicho();
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
	
	public double TotalMontoVendidoXNicho(mVentasNicho obj) {
		// TODO Auto-generated 
		
		double total = 0;
		String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO"
				+ " AND SB.PK_SORTEO = " + obj.getIdsorteo()
				+ " AND PK_SECTOR = " + obj.getIdsector()
				+ " AND PK_NICHO = " + obj.getIdnicho()
				+ " AND B.VENDIDO='V'";

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

	public void Totalboletos(mVentasNicho obj) {
		// TODO Auto-generated 
		
		String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS"
	   	 			+ " WHERE PK_SORTEO = " + obj.getIdsorteo()
	   	 			+ " AND PK_SECTOR = " + obj.getIdsector()
	   	 			+ " AND PK_NICHO = " + obj.getIdnicho();
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				this.setNumBoletos(rs.getInt("TOTAL"));
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public int TotalboletosNicho(mVentasNicho obj){
		// TODO Auto-generated 
		
		int total = 0;
		String sql = " SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS"
				+ " WHERE PK_SORTEO = " + obj.getIdsorteo()
				+ " AND PK_SECTOR = " + obj.getIdsector()
				+ " AND PK_NICHO = " + obj.getIdnicho();
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
		return total;
	}
	
	public ResultSet Sectores(mVentasNicho obj){
    	
    	String sql = "SELECT * FROM SECTORES S, SORTEOS_ASIGNACION_SECTORES SA WHERE S.PK1 = SA.PK_SECTOR AND SA.PK_SORTEO = "+obj.getIdsorteo()+"";
    	ResultSet rs = db.getDatos(sql);
    	return rs;
    	
	}
	
	
	public ResultSet Nicho(mVentasNicho obj){
		// TODO Auto-generated 
    	String sql = "SELECT N.PK1, N.CLAVE AS CLAVE, N.NICHO AS NICHO FROM SORTEOS_ASIGNACION_NICHOS AN, NICHOS N"
    			+ " WHERE N.PK1 = AN.PK_NICHO"
    			+ " AND AN.PK_SORTEO = " + obj.getIdsorteo()
    			+ " AND AN.PK_SECTOR = "+obj.getIdsector()
    			+ " AND AN.PK_NICHO = "+obj.getIdnicho()
    			;
    	
    	ResultSet rs = db.getDatos(sql);
    	return rs;
	}

	/*
	public ResultSet Colaboradores(mVentasNicho obj){
		
		String sql = "SELECT C.PK1 AS PK1, C.CLAVE AS CLAVE, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS NOMBRE FROM COLABORADORES C,  SORTEOS_ASIGNACION_COLABORADORES AC ";
		       sql += "WHERE C.PK1 = AC.PK_COLABORADOR AND AC.PK_SORTEO = "+obj.getIdsorteo()+" AND AC.PK_SECTOR = "+obj.getIdsector()+" AND AC.PK_NICHO = "+obj.getIdnicho()+";";
		ResultSet rs = db.getDatos(sql);
		return rs;
		
	}
	*/

	public double MontoNicho(mVentasNicho obj){
		// TODO Auto-generated 
		 
		 String sql = "SELECT SUM(MONTO) AS 'TOTAL' FROM SORTEOS_NICHOS_TALONARIOS"
		 		+ " WHERE PK_SORTEO = " + obj.getIdsorteo()
		 		+ " AND PK_SECTOR = " + obj.getIdsector()
		 		+ " AND PK_NICHO = " + obj.getIdnicho();
		 ResultSet rs = db.getDatos(sql);
		 double total = 0;
		 try {
			if (rs != null && rs.next()) {
				total = (double)rs.getInt("TOTAL");
			    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public String getNicho() {
		return nicho;
	}

	public void setNicho(String nicho) {
		this.nicho = nicho;
	}

}
