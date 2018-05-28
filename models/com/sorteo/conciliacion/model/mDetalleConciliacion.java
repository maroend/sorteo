package com.sorteo.conciliacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.core.SesionDatos;
import com.core.SuperModel;
//import com.sorteo.sorteos.model.mBoletosSorteo;

public class mDetalleConciliacion extends SuperModel {
	
	private int id;
	private String Folio;
	private String RefBancaria;
	private String Cuenta;
	private String Fecha;
	private String Hora;
	private String Sucursal;
	private String Descripcion;
	private String Importe;
	private String Referencia;
	private int idUsuario;
	


	public mDetalleConciliacion() {
	}

	/*
	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdsorteo();

		ResultSet rs = db.getDatos(sql);

		return rs;

	}

	public ResultSet getSectoresUsuario(mDetalleConciliacion obj) {

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

	public void getSectorNicho(mDetalleConciliacion obj) {

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
	*/

	public int contar(String search, String filtroFecha) {
		// TODO - contar
		String sql = "SELECT COUNT(L.PK1) AS 'MAX' FROM CONCILIACION L";

		System.out.println("CONCILIACION COUNT: " + sql);

		int numero = db.Count(sql);
		return numero;

	}

	/* */
	public ResultSet paginacion(int pg, int numreg, String search, String filtroFecha, boolean defaultOrden) {
		// TODO - paginacion
		
		int offset = (pg - 1) * numreg;

		String sql = "SELECT *"
				+ ", CONCAT(L.FECHA,L.HORA) AS 'FECHA_HORA'"
				+ ", FORMAT(L.FECHA_R,'dd/MMMM, hh:mm tt') AS 'FORMAT'"
				+ " FROM CONCILIACION L "
				+ " ORDER BY FECHA_R DESC"
				+ " OFFSET (" + offset + ") ROWS  FETCH NEXT " + numreg + " ROWS ONLY";
		
		System.out.println("CONCILIACION DETALLE:" + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	private class RollbackDatos {
		public int pkConciliacion;
		public int pkColaborador;
		public double importe;
		public double abono;
		public RollbackDatos(int pkConciliacion, int pkColaborador, double importe){
			this.pkConciliacion = pkConciliacion;
			this.pkColaborador = pkColaborador;
			this.importe = importe;
		}
		public String toString() {
			return "(con:" + pkConciliacion + ", col:" + pkColaborador + ", imp:" + importe + ", abono:" + abono + ")";
		}
	}

	/*
	//                                              no used
	public String Rollback(String search, String filtroFecha)
	{
		// Si NO se manda fecha, se cancela el proceso. 
		if (filtroFecha.length() == 0)
			return "El parametro de la fecha no es valido.";
		
		int count_error=0;
		try {
			ResultSet res = paginacion(1, Integer.MAX_VALUE, search, filtroFecha, false);
			ArrayList<RollbackDatos> listRollback = new ArrayList<mDetalleConciliacion.RollbackDatos>();
			while (res.next()) {
				RollbackDatos rb = new RollbackDatos(
						res.getInt("PK1"),
						res.getInt("PK_COLABORADOR"),
						res.getDouble("IMPORTE"));
				listRollback.add(rb);
			}
			res.close();
			
			
			for(RollbackDatos rollb : listRollback) {
				ArrayList<Colaborador> list = new ArrayList<Colaborador>();
				
				// consultamos los datos para la restauracion de los abonos.
				String sql = "SELECT * FROM CONCILIACION_COLABORADORES WHERE PK_CONCILIACION=" + rollb.pkConciliacion + " ORDER BY ORDEN DESC";
				try{
					res = db.getDatos(sql);
					while (res.next()) {
						Colaborador col = new Colaborador();
						col.pk = res.getLong("PK_COLABORADOR");
						col.abono = res.getDouble("ABONO");
						list.add(col);
					}
					res.close();
				}catch(Exception ex) {}
				
				// Se restauran los abonos.
				for (Colaborador col : list)
					updateAbonoColaborador(col.pk, col.abono);
				
				sql = "DELETE CONCILIACION_COLABORADORES WHERE PK_CONCILIACION=" + rollb.pkConciliacion;
				if (db.execQuery(sql)==0) count_error++;
				
				//sql = "DELETE CONCILIACION WHERE PK1=" + rollb.pkConciliacion;
				//if (db.execQuery(sql)==0) count_error++;
			}
			
			
		}
		catch(Exception ex) { }
		if (count_error>0) {
			return "" + count_error + " errores ocurrieron al actualizar el abono.";
		}
		
		String sql = " DELETE FROM CONCILIACION WHERE PK_SORTEO=" + sesion.pkSorteo;
		
		if (idSector != 0) {
			sql += " AND PK_SECTOR = '" + idSector + "'";
		} else {

			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				sql += " AND PK_SECTOR = '" + this.getSector() + "'";
			}
		}

		if (idNicho != 0) {
			sql += " AND PK_NICHO = '" + idNicho + "'";
		}
		
		sql += " AND FECHA_R='" + filtroFecha + "'";
		
		System.out.println("ROLLBACK:" + sql);
		
		int result = db.execQuery(sql);

		if (result>0)
			return "" + result + " registro(s) eliminado(s).";
		else
			return "ERROR: No se pudo borrar la conciliacion con los filtros agregados.";
	}
	*/
	public ResultSet paginacionFechas(String search) {

		String sql = "SELECT *"
				+ ", FORMAT(L.FECHA_R,'dd/MMMM, hh:mm tt') AS 'FORMAT'"
				+ " FROM("
				+ "   SELECT L.FECHA_R FROM CONCILIACION L GROUP BY FECHA_R"
				+ " ) L"
				+ " ORDER BY FECHA_R DESC";

		System.out.println("CONCILIACION FECHAS:" + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFolio() {
		return Folio;
	}

	public void setFolio(String folio) {
		Folio = folio;
	}

	public String getRefBancaria() {
		return RefBancaria;
	}

	public void setRefBancaria(String refBancaria) {
		RefBancaria = refBancaria;
	}

	public String getCuenta() {
		return Cuenta;
	}

	public void setCuenta(String cuenta) {
		Cuenta = cuenta;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

	public String getHora() {
		return Hora;
	}

	public void setHora(String hora) {
		Hora = hora;
	}

	public String getSucursal() {
		return Sucursal;
	}

	public void setSucursal(String sucursal) {
		Sucursal = sucursal;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getImporte() {
		return Importe;
	}

	public void setImporte(String importe) {
		Importe = importe;
	}

	public String getReferencia() {
		return Referencia;
	}

	public void setReferencia(String referencia) {
		Referencia = referencia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idusuario) {
		this.idUsuario = idusuario;
	}

	
}
