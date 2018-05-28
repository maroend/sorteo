package com.sorteo.poblacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;
import com.core.SuperModel.RESULT;

public class mNichos extends SuperModel {
	
	
	private int id;
	private String clave;
	private String nicho;
	private int idSorteo;
	private int idSector;
	private int idusuario;
	private String usuario;
	
	private String limiteVenta;
	private String limiteDeposito;
	
	private String dato1;
	private String dato2;
	
	public String getLimiteVenta() {
		return limiteVenta;
	}

	public void setLimiteVenta(String limiteventa) {
		this.limiteVenta = limiteventa;
	}
	
	public String getLimiteDeposito() {
		return limiteDeposito;
	}

	public void setLimiteDeposito(String limitedeposito) {
		this.limiteDeposito = limitedeposito;
	}
	
	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
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

	public String getNicho() {
		return nicho;
	}

	public void setNicho(String nicho) {
		this.nicho = nicho;
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int sector) {
		this.idSector = sector;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDato1() {
		return dato1;
	}

	public void setDato1(String dato1) {
		this.dato1 = dato1;
	}

	public String getDato2() {
		return dato2;
	}

	public void setDato2(String dato2) {
		this.dato2 = dato2;
	}

	
	
	public mNichos() {
		// TODO Auto-generated constructor stub
	}
	
	public ResultSet listar() {

		String sql = "SELECT * FROM NICHOS";
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public ResultSet getSectores(SesionDatos sesion) {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO="+sesion.pkSorteo;

		ResultSet rs = db.getDatos(sql);

		return rs;
	}

	public ResultSet getSectoresUsuario(mNichos obj) {

		String sql = "";
		sql = "SELECT * FROM SECTORES WHERE PK1 = '" + obj.getIdSector() + "' ";

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public boolean isAdministrador() {

		String sql = "";

		int user = this.getIdusuario();
		//System.out.println(">>>>model(is isAdministrador) id usuario: "+user);
		
		sql = "SELECT PK1 FROM ROLES_USUARIO WHERE PK_USUARIO = '"+user+"' AND PK_ROLE = '2' ";
		//System.out.println(">>>>SQL: " + sql);
		int numero = db.ContarFilas(sql);

		if (numero > 0)
			return true;

		return false;
	}

	public int contar(String search)
	{
		String sql = "SELECT * FROM VNICHOS WHERE PK_SORTEO=" + getIdSorteo() + " ";
		
		if (search != "") {
			if(isNumeric(search)){
				sql += " AND CLAVE LIKE '%" + search + "%'  ";
			}else{
				sql += " AND NICHO LIKE '%" + search + "%'  ";
			}
		}
		
		if (getIdSector() != 0)
		{
			sql += " AND PK_SECTOR = '" + getIdSector() + "'  ";
		}
		else {
			if (this.isAdministrador()) {
			} else {
				// Si NO: se filtra por el sector que puede ver el usuario 
				this.getSectorUsuarioActual();
				sql += " AND PK_SECTOR = '" + this.getIdSector() + "'  ";
			}
		}

		//System.out.println("NICHOS COUNT: " + sql);

		int numero = db.ContarFilas(sql);
		return numero;
	}
	
	public ResultSet paginacion(int pg, int numreg, String search)
	{
		return paginacion(pg, numreg, search, true);
	}
	
	public ResultSet paginacion(int pg, int numreg, String search, boolean offset)
	{
		String sql = "SELECT PK1, CLAVE, NICHO, PK_SECTOR, SECTOR, DESCRIPCION, LIMITE_VENTA, LIMITE_DEPOSITO"
				+ " FROM VNICHOS "
				+ "WHERE PK_SORTEO=" + getIdSorteo();

		if (search.compareTo("") != 0) {

			if (isNumeric(search)) {
				sql += " AND CLAVE LIKE '%" + search + "%'  ";
			} else {
				sql += " AND NICHO LIKE '%" + search + "%'  ";
			
		     }
			
		}	

		if (getIdSector() != 0) {

			sql += " AND PK_SECTOR = '" + getIdSector() + "'  ";

		} else {

			if (this.isAdministrador()) {
			} else {
				this.getSectorUsuarioActual();
				sql += " AND PK_SECTOR = '" + this.getIdSector() + "'  ";
			}
		}

		sql += " ORDER BY CLAVE ASC ";
		
		if (offset) {
			sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS ";
			sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		}

		System.out.println("NICHOS: " + sql);

		return db.getDatos(sql);
	}

	
	public RESULT insertar() {
		String sql = "INSERT INTO NICHOS (CLAVE,NICHO,PK_SECTOR,LIMITE_VENTA,LIMITE_DEPOSITO,USUARIO,FECHA_R) VALUES ('"
				+ getClave() + "','"
				+ getNicho() + "','"
				+ getIdSector() + "','"
				+ getLimiteVenta() + "','"
				+ getLimiteDeposito() + "','"
				+ getUsuario() + "',"
				+ "GETDATE())";
		System.out.println(sql);
		int res = db.execQuerySelectId(sql);
		if (res>0) {
			super._mensaje = "Nicho guardado";
			return RESULT.OK;
		}
		super._mensaje = "No se ha podido guardar el nuevo nicho";
		return RESULT.ERROR;
	}

	public RESULT actualizar() {
		String sql = "UPDATE NICHOS SET"
				+ "  CLAVE = '" + getClave() + "'"
				+ ", NICHO = '" + getNicho() + "'"
			    + ", LIMITE_VENTA = '"+ getLimiteVenta() + "'"
			    + ", LIMITE_DEPOSITO = '"+ getLimiteDeposito() + "'"
			    + ", USUARIO='" + getUsuario() + "'"
			    + ", FECHA_M=GETDATE()"
				+ " WHERE PK1 ='" + getId()+ "'";

		int result = db.execQuery(sql);
		
		if (result == 1) {
			super._mensaje = "El nicho " + getClave() + " se ha actualizado con exito ";
			return RESULT.OK;
		}
		
		super._mensaje = "El nicho " + getClave() + " no se ha podido actualizar ";
		return RESULT.ERROR;
	}

	public void BuscarEditar(mNichos obj) {

		String sql = "SELECT PK1,NICHO,CLAVE,PK_SECTOR,LIMITE_VENTA,LIMITE_DEPOSITO FROM NICHOS WHERE PK1 ='"
				+ obj.getId() + "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				// obj.setId((rs.getString("PK1"));
				obj.setNicho(rs.getString("NICHO"));
				obj.setIdSector(rs.getInt("PK_SECTOR"));
				obj.setClave(rs.getString("CLAVE"));
				obj.setLimiteVenta(rs.getString("LIMITE_VENTA"));
				obj.setLimiteDeposito(rs.getString("LIMITE_DEPOSITO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public RESULT Borrar() {
		// Antes de eliminar el nicho se deberian de eliminar las asignaciones.
		String sql;
		
		sql = "SELECT COUNT(*) AS 'MAX' FROM COLABORADORES_BOLETOS WHERE PK_NICHO=" + getId();
		if (0 < db.Count(sql)) {
			super._mensaje = "No se puede borrar el nicho porque tiene BOLETOS de colaboradores asignados";
			return RESULT.ERROR;
		}
		
		sql = "SELECT COUNT(*) AS 'MAX' FROM COLABORADORES_ASIGNACION WHERE PK_NICHO=" + getId();
		if (0 < db.Count(sql)) {
			super._mensaje = "No se puede borrar el nicho porque tiene COLABORADORES asignados";
			return RESULT.ERROR;
		}
		
		sql = "SELECT COUNT(*) AS 'MAX' FROM NICHOS_BOLETOS WHERE PK_NICHO=" + getId();
		if (0 < db.Count(sql)) {
			super._mensaje = "No se puede borrar el nicho porque tiene BOLETOS de asignados";
			return RESULT.ERROR;
		}
		
		sql = "SELECT COUNT(*) AS 'MAX' FROM SORTEOS_USUARIOS WHERE PK_NICHO=" + getId();
		if (0 < db.Count(sql)) {
			super._mensaje = "No se puede borrar el nicho porque tiene RESPONSABLES asignados";
			return RESULT.ERROR;
		}
		
		sql = "SELECT COUNT(*) AS 'MAX' FROM SORTEOS_PARALELOS_NICHOS WHERE PK_NICHO=" + getId();
		if (0 < db.Count(sql)) {
			super._mensaje = "No se puede borrar el nicho porque se utiliza en un sorteo especial";
			return RESULT.ERROR;
		}
		
		sql = "SELECT COUNT(*) AS 'MAX' FROM DISPOSITIVOS WHERE PK_NICHO=" + getId();
		if (0 < db.Count(sql)) {
			super._mensaje = "No se puede borrar el nicho porque se esta utilizando en algun dispositivo";
			return RESULT.ERROR;
		}
		
		sql = "DELETE FROM NICHOS WHERE PK1='" + getId() + "'";
		if (1 == db.execQuery(sql)) {
			super._mensaje = "Nicho borrado satisfactoriamente";
			return RESULT.OK;
		} else {
			super._mensaje = "Ocurrio un error al momento de borrar el nicho";
			return RESULT.ERROR;
		}
	}	
	
	/*
	public RESULT BorrarNichos(String[] nichos, SesionDatos sesion) {
		
		String sql ="";

		for (String ID : nichos) {
			
			sql = "DELETE FROM NICHOS WHERE PK1 = "+ID;//PK_SORTEO =" + this.getIdsorteo() + " AND 
			db.execQuery(sql);
			
			//sql = "DELETE  FROM COLABORADORES WHERE PK_SORTEO =" + this.getIdsorteo() + " AND PK_NICHO = "+ID;
			//db.execQuery(sql);
			
		}
		
		this.Log(sesion, LOG.REGISTRO, this, "NICHO ELIMINADO", sesion.toShortString());
		
		return RESULT.OK;
	}
	*/
	public RESULT BorrarNichos(String[] nichos) {

		RESULT result = RESULT.OK;
		super._count_success = 0;
		super._count_max = 0;
		for (String ID : nichos) {
			try {
				
				this.setId(Integer.valueOf(ID));

				if (this.existeNicho()){
					
					super._count_max++;
					result = Borrar();
					if (result == RESULT.OK) {
						super._count_success++;
					}
				}
			} catch (Exception ex) { }
		}
		if (super._count_max == super._count_success) {
			super._mensaje = "" + super._count_success + " nicho(s) han sido borrado(s) con exito ";
			result = RESULT.OK;
		}
		else{
			if (super._count_success == 0)
				super._mensaje = "No se pueden eliminar los nichos marcados por que tienen colaboradores, responsables y/o boletos asignados ";
			else
				super._mensaje = "Nichos borrados: " + super._count_success + " de " + super._count_max + " ";
			result = RESULT.ERROR;
		}

		return result;
	}
	
	
	public void getSectorUsuarioActual() {

		String sql = "SELECT PK_SECTOR FROM USUARIOS WHERE PK1 =  '" + this.getIdusuario() + "'  ";

		ResultSet rs = db.getDatos(sql);

		try {
			
			if (rs.next()) {
				this.setIdSector(rs.getInt("PK_SECTOR"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}
	
	/*
	public static HashMap<Long, String> loadMapNichos(long idSorteo, long idSector, SuperModel superModel) {
		HashMap<Long, String> mapNichos = new HashMap<Long, String>();
		String sql = "SELECT N.PK1,N.CLAVE,N.NICHO FROM SORTEOS_ASIGNACION_NICHOS SAN, NICHOS N"
				+ " WHERE SAN.PK_NICHO=N.PK1 AND SAN.PK_SORTEO=" + idSorteo;
		if (idSector != -1)
			sql += " AND SAN.PK_SECTOR=" + idSector;

		try {
			ResultSet res = superModel.db.getDatos(sql);
			while (res.next()) {
				mapNichos.put(res.getLong("PK1"), res.getString("NICHO"));
			}
			res.close();
		} catch(Exception ex) { }
		return mapNichos;
	}
	*/
	public static ArrayList<mNichos> getListNichos(long idSorteo, long idSector, SuperModel model)
	{
		ArrayList<mNichos> list = new ArrayList<mNichos>();
		
		String sql = "SELECT N.PK1,N.CLAVE,N.NICHO FROM SORTEOS_ASIGNACION_NICHOS SAN, NICHOS N"
				+ " WHERE SAN.PK_NICHO=N.PK1 AND SAN.PK_SORTEO=" + idSorteo;
		if (idSector > 0)
			sql += " AND SAN.PK_SECTOR=" + idSector;
		sql	+= " ORDER BY N.CLAVE";

		System.out.println("Nichos:" + sql);
		ResultSet res = model.db.getDatos(sql);
		try{
			while (res.next()) {
				mNichos nicho = new mNichos();
				nicho.setId(res.getInt("PK1"));
				nicho.setClave(res.getString("CLAVE"));
				nicho.setNicho(res.getString("NICHO"));
				list.add(nicho);
			}
			res.close();
		}catch(Exception ex) { }
		
		return list;
	}
	
	public static HashMap<Long , mNichos> getMapNichos(long idSorteo, long idSector, SuperModel model) {
		ArrayList<mNichos> list = getListNichos(idSorteo, idSector, model);
		HashMap<Long , mNichos> map = new HashMap<Long, mNichos>();
		for (mNichos nicho : list)
			map.put((long)nicho.getId(), nicho);
		return map;
	}
	
	public int consultaIdXClave() {
		String sql = "SELECT TOP 1 PK1 AS 'VALUE' FROM NICHOS WHERE CLAVE='" + getClave() + "' AND PK_SECTOR=" + getIdSector();

		return db.getValue(sql, -1);
	}

	public boolean existeNicho() {
		String sql = "SELECT COUNT(*) AS 'MAX' FROM NICHOS WHERE PK1=" + getId();
		return db.Count(sql) == 1;
	}

}
