package com.sorteo.conciliacion.model;

import java.sql.ResultSet;

import com.core.SesionDatos;

import java.sql.SQLException;
import java.util.ArrayList;

import com.core.SuperModel;

public class mConciliacionElectronica extends SuperModel {
	
	private int id;
	private String clave;
	private int nicho;
	private int sector;
	private String rbancaria;
	private String colaborador;

	private String nombre;
	private String apellidop;
	private String apellidom;
	
	private int idusuario;
	private int idsorteo;
	
	private int Comision;
	
	
	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	public int getComision() {
		return Comision;
	}
	
	public void setComision(int comision) {
		Comision = comision;
	}
	
	public void init(){		
	
		 this.id = 0;
		 this.clave = "";
		 this.sector = 0;
		 this.rbancaria = "";		
		 this.nicho = 0;
		 
		 this.nombre = "";
		 this.apellidop = "";
		 this.apellidom = "";	
		
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
	
	public String getColaborador() {
		return colaborador;
	}

	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}

	public int getNicho() {
		return nicho;
	}

	public void setNicho(int nicho) {
		this.nicho = nicho;
	}
	
	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	
	public String getRbancaria() {
		return rbancaria;
	}

	public void setRbancaria(String rbancaria) {
		this.rbancaria = rbancaria;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidop() {
		return apellidop;
	}

	public void setApellidop(String apellidop) {
		this.apellidop = apellidop;
	}

	public String getApellidom() {
		return apellidom;
	}

	public void setApellidom(String apellidom) {
		this.apellidom = apellidom;
	}

	public mConciliacionElectronica() {
		init();
	}

	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdsorteo();

		ResultSet rs = db.getDatos(sql);

		return rs;

	}

	public ResultSet getSectoresUsuario(mConciliacionElectronica obj) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ obj.getSector() +"' ";
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public boolean isAdministrador() {

		String sql = "";

		int user = this.getIdusuario();
		//System.out.println(">>>>model(is isAdministrador) id usuario: " + user);

		sql = "SELECT PK1 FROM ROLES_USUARIO WHERE PK_USUARIO = '" + user + "' AND PK_ROLE = '2' ";
		//System.out.println(">>>>SQL: " + sql);
		int numero = db.ContarFilas(sql);

		if (numero > 0)
			return true;

		return false;
	}

	public void getSectorNicho(mConciliacionElectronica obj) {

		String sql = "SELECT PK_SECTOR FROM NICHOS WHERE PK1 ='" + obj.getNicho() + "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				obj.setSector(rs.getInt("PK_SECTOR"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getNichos(int idsector) {

		String sql = "SELECT * FROM NICHOS WHERE PK_SECTOR = '" + idsector + "'";

		ResultSet rs = db.getDatos(sql);

		return rs;

	}

	/*
	public ResultSet listar() {

		String sql = "SELECT * FROM COLABORADORES";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}
	//*/

	public int contar(String search, int metodo_pago) {
		// TODO - paginacion
		String filtro = "";
		
		if (!search.equals("")) {
			filtro += "(OC.FOLIO LIKE '%"+search+"%' OR OC.REFBANCARIA LIKE '%"+search+"%')";
		}
		if (metodo_pago != 0) {
			if(filtro!="")filtro += " AND ";
			filtro += " (PK_METODO_PAGO=" + metodo_pago + ")";
		}
		
		String sql = "SELECT COUNT(*) AS 'MAX'"
				+ " FROM ORDENDECOMPRA OC"
				+ " INNER JOIN METODO_PAGO MP ON MP.PK1 = OC.PK_METODO_PAGO"
				+ " INNER JOIN ESTADO_COMPRA EC ON EC.PK1 = OC.PK_ESTADO"
				+ (filtro == "" ? "" : (" WHERE " + filtro))
				;
		System.out.println("Conciliacion-elec,count: " + sql);

		return db.Count(sql);
	}

	public ResultSet paginacion(int pg, int numreg, String search, int metodo_pago) {
		// TODO - paginacion
		
		String filtro = "";
		
		if (!search.equals("")) {
			filtro += "(OC.FOLIO LIKE '%"+search+"%' OR OC.REFBANCARIA LIKE '%"+search+"%')";
		}
		if (metodo_pago != 0) {
			if(filtro!="")filtro += " AND ";
			filtro += " (PK_METODO_PAGO=" + metodo_pago + ")";
		}
		
		String offset = "" + ((pg - 1) * numreg);
		
		String sql = "SELECT OC.FOLIO, MP.METODO AS 'METODO_PAGO', OC.REFBANCARIA, OC.IMPORTE, OC.FECHA_VENCIMIENTO, EC.ESTADO, EC.DESCRIPCION"
				+ " FROM ORDENDECOMPRA OC"
				+ " INNER JOIN METODO_PAGO MP ON MP.PK1 = OC.PK_METODO_PAGO"
				+ " INNER JOIN ESTADO_COMPRA EC ON EC.PK1 = OC.PK_ESTADO"
				+ (filtro == "" ? "" : (" WHERE " + filtro))
				+ " ORDER BY FECHA_R DESC"
				+ " OFFSET (" + offset + ") ROWS  FETCH NEXT " + numreg + " ROWS ONLY \n";
		
		System.out.println("Conciliacion elec.: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public ResultSet paginacion_excel(String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - paginacion
		
		String sql;
		String filtro_search = " ";
		
		if (search != "") {
			if (isNumeric(search)) {
				filtro_search += "AND C.CLAVE LIKE '%" + search + "%'";
			} else {
				filtro_search += "AND C.NOMBRE LIKE '%" + search + "%'";
			}
		}
		
		String filtro_sector=" ";
		if (sector != 0) {
			filtro_sector += "AND N.PK_SECTOR = '" + sector + "'";
		} else {
			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				filtro_sector += "AND N.PK_SECTOR = '" + this.getSector() + "'";
			}
		}
		
		String filtro_nicho=" ";
		if (nicho != 0) {
			filtro_nicho += "AND C.PK_NICHO = '" + nicho + "'";
		}

		sql
				= "    SELECT C.PK1, C.CLAVE, C.REFBANCARIA, C.COMISION, C.ABONO, SAC.PK_SECTOR, SAC.PK_NICHO"
				+ "    ,LTRIM(RTRIM(CONCAT(C.NOMBRE,' ',C.APATERNO,' ', C.AMATERNO))) AS 'NOM_COLABORADOR'"
				+ "    ,(SELECT ISNULL(SUM(SCT.MONTO),0.0) FROM SORTEOS_COLABORADORES_TALONARIOS SCT WHERE SCT.PK_SORTEO = " + sesion.pkSorteo + " AND SCT.PK_SECTOR = S.PK1 AND SCT.PK_NICHO = N.PK1 AND SCT.PK_COLABORADOR = C.PK1) AS 'MONTO'"
				+ "    FROM SORTEOS_ASIGNACION_COLABORADORES SAC, COLABORADORES C, NICHOS N, SECTORES S"
				+ "    WHERE SAC.PK_COLABORADOR = C.PK1 AND SAC.PK_NICHO = N.PK1 AND SAC.PK_SECTOR = S.PK1 AND SAC.PK_SORTEO = " + sesion.pkSorteo + filtro_search + filtro_sector + filtro_nicho
				+ " ORDER BY NOM_COLABORADOR ASC";

		System.out.println("Conciliacion: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public void actualizarAbono(mConciliacionElectronica obj) {

		db.con();
		String sql = "";

		sql = "UPDATE COLABORADORES SET ABONO = " + obj.getComision() + ", FECHA_M = GETDATE()"
			+ " WHERE PK1 = " + obj.getId();

		db.execQuery(sql);
	}

	public void getSectorUsuarioActual() {

		String sql = "SELECT PK_SECTOR FROM USUARIOS WHERE PK1 = " + this.getIdusuario();

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				this.setSector(rs.getInt("PK_SECTOR"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}
	
	public ArrayList<MetodoPago> consultaMetodosPago(){
		String sql = "SELECT PK1,METODO FROM METODO_PAGO";
		
		ResultSet rs = db.getDatos(sql);
		
		ArrayList<MetodoPago> list = new ArrayList<MetodoPago>();
		try {
			while(rs.next()) {
				list.add(new MetodoPago(rs.getInt("PK1"), rs.getString("METODO")));
			}
		}catch(SQLException ex) { }
		return list;
	}

	public class MetodoPago{
		public int id;
		public String metodo;
		public MetodoPago(int id, String metodo){
			this.id = id;
			this.metodo = metodo;
		}
	}
}
