package com.sorteo.poblacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.core.Factory.LOG;
import com.core.SuperModel.RESULT;
import com.core.Parametros;
import com.core.SesionDatos;
import com.core.SuperModel;


public class mSectores extends SuperModel {
	
	
	private int id;
	private String clave;
	private String sector;
	private String descripcion;
	private int idSorteo;
	private String usuario;
	
	private int Comision;

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}

	public int getComision() {
		return Comision;
	}

	public void setComision(int comision) {
		Comision = comision;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	public mSectores() {
		// TODO Auto-generated constructor stub
	}
	
	public ResultSet listar() {
		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public int contar(String search)
	{
		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();
		if (search != "") {
			sql += " AND ((SECTOR LIKE '%" + search + "%') OR (CLAVE LIKE '%" + search + "%')) ";
		}
		int numero = db.ContarFilas(sql);
		return numero;
	}
	
	public ResultSet paginacion(int pg, int numreg, String search)
	{
		return paginacion(pg, numreg, search, OFFSET.TRUE);
	}
	
	public ResultSet paginacion(int pg, int numreg, String search, OFFSET offset)
	{
		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();

		if (search != "") {
			sql += " AND ((SECTOR LIKE '%" + search + "%') OR (CLAVE LIKE '%" + search + "%')) ";
		}

		sql += " ORDER BY PK1 ASC";
		
		if (offset == OFFSET.TRUE) {
			sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS";
			sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		}
		
		ResultSet rs = db.getDatos(sql);
		
		return rs;
	}
	
	public RESULT insertar() {
		String sql = "INSERT INTO SECTORES (CLAVE,PK_SORTEO,SECTOR,DESCRIPCION,USUARIO,FECHA_R) VALUES ('"
				+ getClave() + "','"
				+ getIdSorteo() + "','"
				+ getSector() + "','"
				+ getDescripcion() + "','"
				+ getUsuario() + "',getdate())";
		
		System.out.println(sql);
		
		int res = db.execQuerySelectId(sql);
		if (res>0) {
			super._mensaje = "Sector guardado";
			return RESULT.OK;
		}
		super._mensaje = "No se ha podido guardar el nuevo sector";
		return RESULT.ERROR;
	}
	

	public RESULT actualizar()
	{
		String sql = "UPDATE SECTORES SET"
			    + " CLAVE = '" + getClave() + "',"
				+ " SECTOR = '" + getSector() + "',"
			    + " DESCRIPCION = '" + getDescripcion() + "',"
			    + " USUARIO='" + getUsuario() + "',"
			    + " FECHA_M=GETDATE()"
			    + " WHERE PK1 = '" + getId() + "'";

		int result = db.execQuery(sql);
		
		if (result == 1) {
			super._mensaje = "El sector " + getClave() + " se ha actualizado con exito ";
			return RESULT.OK;
		}
		
		super._mensaje = "El sector " + getClave() + " no se ha podido actualizar ";
		return RESULT.ERROR;
	}
	
	public void BuscarEditar(mSectores obj)
	{
		String sql = "SELECT * FROM SECTORES WHERE PK1 ='" + obj.getId() + "' ";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs.next())
			{
				obj.setSector(rs.getString("SECTOR"));
				obj.setClave(rs.getString("CLAVE"));
				obj.setDescripcion(rs.getString("DESCRIPCION"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public RESULT Borrar() {
		
		// SE VERIFICA QUE NO TENGA BOLETOS ASIGNADOS.
		String sql = "SELECT COUNT(*) AS 'MAX' FROM SECTORES_BOLETOS WHERE PK_SECTOR=" + getId();
		if (db.Count(sql) > 0){
			super._mensaje = "No se puede eliminar el sector por que tiene BOLETOS asignados.";
			return RESULT.ERROR;
		}
		
		// Se verifica que no tenga boletos asignados.
		sql = "SELECT COUNT(*) AS 'MAX' FROM NICHOS WHERE PK_SECTOR=" + getId();
		if (db.Count(sql) > 0){
			super._mensaje = "No se puede eliminar el sector por que tiene NICHOS asignados.";
			return RESULT.ERROR;
		}
		
		sql = "DELETE FROM SECTORES WHERE PK1='" + getId() + "'";
		int res = db.execQuery(sql);

		if (res == 1) {
			super._mensaje = "El sector se ha borrado con exito";
			return RESULT.OK;
		} else {
			super._mensaje = "Ocurrio un error al intentar borrar el sector";
			return RESULT.ERROR;
		}
	}
	
	public RESULT BorrarSectores(String[] sectores, SesionDatos sesion) {

		RESULT result = RESULT.OK;
		super._count_success = 0;
		super._count_max = 0;
		for (String ID : sectores) {
			try {
				this.setId(Integer.valueOf(ID));

				if (this.existeSector()){
					
					super._count_max++;
					result = Borrar();
					if (result == RESULT.OK)
						super._count_success++;
				}
			} catch (Exception ex) { }
		}
		if (super._count_max == super._count_success) {
			super._mensaje = "" + super._count_success + " sector(es) han sido borrado(s) con exito ";
			result = RESULT.OK;
		}
		else{
			if (super._count_success == 0)
				super._mensaje = "No se pueden eliminar los sectores marcados por que tienen nichos y/o beletos asignados ";
			else
				super._mensaje = "Sectores borrados: " + super._count_success + " de " + super._count_max + " ";
			result = RESULT.ERROR;
		}
		return result;
	}

	/*
	public void actualizarComision(mSectores obj) {

			db.con();
			
			String sql ="";
			
					   	 
			   								
			  sql = "UPDATE [SECTORES]"	
			  + " SET COMISION = " + obj.getComision()	
			  + " WHERE PK1 = " + obj.getId();
								
					     
		       System.out.println(">>>UPDATE COMISIÓN SECTORES: "+sql);
							db.execQuery(sql);				
						
	}
	//*/
	
	public int consultaIdXClave() {
		String sql = "SELECT TOP 1 PK1 AS 'VALUE' FROM SECTORES WHERE CLAVE='" + getClave() + "'";

		return db.getValue(sql, -1);
	}

	public boolean existeSector() {
		String sql = "SELECT COUNT(*) AS 'MAX' FROM SECTORES WHERE PK1=" + getId();
		return db.Count(sql) == 1;
	}

	public static ArrayList<mSectores> getListSectores(int idSorteo, SuperModel model)
	{
		ArrayList<mSectores> list = new ArrayList<mSectores>();
		String sql =
			"SELECT S.*"
			+ " FROM SORTEOS_ASIGNACION_SECTORES SAS, SECTORES S"
			+ " WHERE SAS.PK_SECTOR=S.PK1 AND SAS.PK_SORTEO=" + idSorteo
			+ " ORDER BY S.CLAVE";
		
		System.out.println("Sectores:" + sql);
		
		ResultSet res = model.db.getDatos(sql);
		try{
			while (res.next()) {
				mSectores sector = new mSectores();
				sector.setId(res.getInt("PK1"));
				sector.setClave(res.getString("CLAVE"));
				sector.setSector(res.getString("SECTOR"));
				list.add(sector);
			}
			res.close();
		}catch(Exception ex) { }
		
		return list;
	}
	
	public static HashMap<Long, mSectores> getMapSectores(int idSorteo, SuperModel model)
	{
		ArrayList<mSectores> list = getListSectores(idSorteo, model);
		HashMap<Long, mSectores> map = new HashMap<Long, mSectores>();
		for (mSectores sector : list)
			map.put((long)sector.getId(), sector);
		return map;
	}
	
}
