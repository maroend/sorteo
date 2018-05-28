package com.sorteo.poblacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mImportNichos extends SuperModel {
	private int idSorteo;
	private int idSector;
	private int idNicho;
	private int idUsuario;
	private String sorteo;
	
	private String claveNicho;
	private String Nicho;
	private String descripcion;
	private double comision;
	
	
	private String status;
	private String operacion;
	
	
	private int numregistrados;
	private int numwarning;
	private int numerrores;
	
	
	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public int getNumregistrados() {
		return numregistrados;
	}

	public void setNumregistrados(int numregistrados) {
		this.numregistrados = numregistrados;
	}

	public int getNumwarning() {
		return numwarning;
	}

	public void setNumwarning(int numwarning) {
		this.numwarning = numwarning;
	}

	public int getNumerrores() {
		return numerrores;
	}

	public void setNumerrores(int numerrores) {
		this.numerrores = numerrores;
	}

	public double getComision() {
		return comision;
	}

	public void setComision(double comision) {
		this.comision = comision;
	}

	public String getSorteo() {
		return sorteo;
	}

	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idnicho) {
		this.idNicho = idnicho;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNicho() {
		return Nicho;
	}

	public void setNicho(String nicho) {
		Nicho = nicho;
	}

	public String getClaveNicho() {
		return claveNicho;
	}

	public void setClaveNicho(String claveNicho) {
		this.claveNicho = claveNicho;
	}

	public mImportNichos() {
		// TODO Auto-generated constructor stub
	}

	
	public void consultaUsuarioSorteo()
	{
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		try {
			while (rs.next()) {
				this.setIdSorteo(rs.getInt("PK_SORTEO"));
				this.setIdSector(rs.getInt("PK_SECTOR"));
				this.setIdNicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ProcessRows(){
		
		
		String sql = "SELECT PK1 FROM NICHOS WHERE CLAVE = '" + this.getClaveNicho() +"' AND PK_SORTEO ="+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector();
		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs != null && rs.next()) {
				
			
				this.setNumwarning(this.getNumwarning()+1);
				this.setStatus("ACTUALIZADO: NICHO");
				this.setOperacion("warning");
			    
				    //UPDATE REVISAR QUE EL IMPORTE SEA EL MISMO
				   sql = "UPDATE NICHOS SET NICHO='"+this.getNicho()+"',DESCRIPCION = '"+this.getDescripcion()+"' WHERE CLAVE = '"+this.getClaveNicho()+"' AND PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector();
				   db.execQuery(sql);
			}else{
				//SE INSERTA EL NUEVO IMPORTE
				 sql = "INSERT INTO NICHOS (CLAVE,NICHO,DESCRIPCION,PK_SORTEO,PK_SECTOR,PK_USUARIO) VALUES ('"
						+ this.getClaveNicho() + "','"
						+ this.getNicho() + "','"
						+ this.getDescripcion() + "',"
						+ this.getIdSorteo() + ","
						+ this.getIdSector() + ","
						+ this.getIdUsuario() + ")";
				        System.out.println("ROWS INSERT:"+sql);
				         db.execQuery(sql);
				         
				this.setNumregistrados(this.getNumregistrados()+1);
                this.setStatus("REGISTRADO");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	

	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();

		return db.getDatos(sql);
	}

	public ResultSet getSectoresUsuario()
	{
		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ this.getIdSector() +"' ";

		return db.getDatos(sql);
	}
	

	public void consultaSorteo() {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + getIdSorteo();

		//System.out.println(" ---> "+sql);
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				this.setSorteo(rs.getString("SORTEO"));
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}



