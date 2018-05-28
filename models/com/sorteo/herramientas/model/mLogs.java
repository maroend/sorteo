package com.sorteo.herramientas.model;

import java.sql.ResultSet;

import com.core.SuperModel;

public class mLogs extends SuperModel {
	

	private int id;
	private int idusuario;
	private String ip;
	private String actividad;
	private String fecha;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	public mLogs() {
		// TODO Auto-generated constructor stub
	}
	
	public ResultSet listar(){
    	
    	String sql = "SELECT * FROM LOG";
    	ResultSet rs = db.getDatos(sql);
    	return rs;
    	
	}
   

	public int contar(String search){
	   //String sql = "SELECT * FROM LOG";
	   //int numero = db.ContarFilas(sql);
		
		String condicion = "";

		if (search != "")
				{
					condicion = " WHERE PK_USUARIO LIKE '%"+search+"%' ";
				}
				
				
	   return db.ContarFilasDesdeCount("[LOG]",condicion);
	}


	public ResultSet paginacion(int pg, int numreg, String search)
	{
		StringBuilder sb_query = new StringBuilder();

		sb_query.append("SELECT * FROM LOG ");

		if (search != "")
		{
			sb_query.append(" WHERE PK_USUARIO LIKE '%").append(search).append("%' ");
		}

		sb_query.append("ORDER BY PK1 ASC ");
		sb_query.append("OFFSET (").append((pg - 1) * numreg).append(") ROWS "); //-- not sure if you need -1
		sb_query.append("FETCH NEXT ").append(numreg).append(" ROWS ONLY");

		ResultSet rs = db.getDatos(sb_query.toString());
		return rs;
	}
	/*
	public void f2() {
		ResultSet rs = db.getDatos("SELECT * FROM BOLETOS");
		try{
			int count=0;
			while(rs.next()) {
				String sql = "INSERT INTO BOLETOS_2 (FOLIO,COSTO,ABONO,SORTEO,FORMATO,PK_TALONARIO,TALONARIO,ASIGNADO,VENDIDO,RETORNADO,INCIDENCIA,FOLIODIGITAL,AUTOGENERADO,USUARIO,FECHA_R,FECHA_M)"
						+"VALUES("
						+" '"+rs.getString("FOLIO")+"'"
						+","+rs.getString("COSTO")+""
						+","+rs.getString("ABONO")+""
						+","+rs.getString("SORTEO")+""
						+",'"+rs.getString("FORMATO").trim()+"'"
						+","+rs.getString("PK_TALONARIO")+""
						+","+rs.getString("TALONARIO")+""
						+","+rs.getString("ASIGNADO")+""
						+",'"+rs.getString("VENDIDO")+"'"
						+","+rs.getString("RETORNADO")+""
						+",'"+rs.getString("INCIDENCIA")+"'"
						+","+rs.getString("FOLIODIGITAL")+""
						+","+rs.getString("AUTOGENERADO")+""
						+",'"+rs.getString("USUARIO")+"'"
						+",'"+rs.getString("FECHA_R")+"'"
						+",null"
						+")";
				
				//db.getDatos(sql);
				db.execQuery(sql);
				
				count++;
				if(count%500 == 0)
					System.out.println("" + count + " boletos");
			}
		}catch(Exception ex){}
	}
	*/

}
