package com.sorteo.poblacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;

public class mImportSectores extends SuperModel {
	private int idsorteo;
	private int idsector;
	private int idnicho;
	private int IdUsuario;
	private String sorteo;
	
	private String claveSector;
	private String Sector;
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

	


	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
	}

	
	
	
	
	public String getClaveSector() {
		return claveSector;
	}

	public void setClaveSector(String claveSector) {
		this.claveSector = claveSector;
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

	public int getIdsector() {
		return idsector;
	}

	public void setIdsector(int idsector) {
		this.idsector = idsector;
	}

	public int getIdnicho() {
		return idnicho;
	}

	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
	}

	public int getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getSector() {
		return Sector;
	}

	public void setSector(String sector) {
		Sector = sector;
	}

	public mImportSectores() {
		// TODO Auto-generated constructor stub
	}

	
	public void consultaUsuarioSorteo()
	{
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();
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

	public void ProcessRows(){
		
		
		String sql = "SELECT PK1 FROM SECTORES WHERE CLAVE = '" + this.getClaveSector() +"' AND PK_SORTEO ="+this.getIdsorteo();
		ResultSet rs = db.getDatos(sql);
		//double importe = 0;
		
		
		try {
			if (rs != null && rs.next()) {
				
				System.out.println("EXISTE SECTOR");
				this.setNumwarning(this.getNumwarning()+1);
				this.setStatus("ACTUALIZADO: SECTOR");
				this.setOperacion("warning");
			    
				    //UPDATE REVISAR QUE EL IMPORTE SEA EL MISMO
				   sql = "UPDATE SECTORES SET SECTOR='"+this.getSector()+"',DESCRIPCION = '"+this.getDescripcion()+"', COMISION = "+this.getComision()+" WHERE CLAVE = '"+this.getClaveSector()+"' AND PK_SORTEO = "+this.getIdsorteo();
				   db.execQuery(sql);
			}else{
				//SE INSERTA EL NUEVO IMPORTE
				 sql = "INSERT INTO SECTORES (CLAVE,SECTOR,DESCRIPCION,COMISION,PK_SORTEO,PK_USUARIO) VALUES ('"
						+ this.getClaveSector() + "','"
						+ this.getSector() + "','"
						+ this.getDescripcion() + "',"
						+ this.getComision() + ","
						+ this.getIdsorteo() + ","
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
	
	
	
	

	public void consultaSorteo() {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + getIdsorteo();

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



