package com.sorteo.conciliacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.core.SesionDatos;
import com.core.SuperModel;

public class mImportConciliacionElectronica extends SuperModel {
	
	private int idSorteo;
	private int idSector;
	private int idNicho;
	//private int idUsuario;
	private long idColaborador;
	
	
	private String referenciaIB;
	private String cuenta;
	private String fecha;
	private String hora;
	private String sucursal;
	private String descripcion;
	private String cargo;
	private double importe;
	private double saldo;
	private String referencia;
	private String concepto;
	

	private String usuario;
	private String FECHA_R;
	/*
	private String claveSector;
	private String claveNicho;
	private String claveColaborador;
	private String fecha_abono;
	
	private String status;
	*/
	private String operacion;
	
	
	public int conciliacion_orden;
	public int conciliacion_boletos_orden;

	
	public mImportConciliacionElectronica() {
		conciliacion_orden = 0;
		//conciliacion_boletos_orden = 0;
	}
	
	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta.trim();
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha.trim();
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora.trim();
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal.trim();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo.trim();
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia.replaceAll("\t", "").trim();
	}

	public String getReferenciaIB() {
		return referenciaIB;
	}

	public void setReferenciaIB(String referenciaIB) {
		this.referenciaIB = referenciaIB.replaceAll("\t", "").trim();
	}


	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
	}
	/* */
	public int getIdSector() {
		return idSector;
	}
	//*/
	public void setIdSector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idnicho) {
		this.idNicho = idnicho;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/* *
	public String getSorteo() {
		return sorteo;
	}

	public void setSorteo(String sorteo) {
		this.sorteo = sorteo.trim();
	}
	
	public String getClaveSector() {
		return claveSector;
	}

	public void setClaveSector(String claveSector) {
		this.claveSector = claveSector.trim();
	}

	public String getClaveNicho() {
		return claveNicho;
	}

	public void setClaveNicho(String claveNicho) {
		this.claveNicho = claveNicho.trim();
	}

	public String getClaveColaborador() {
		return claveColaborador;
	}

	public void setClaveColaborador(String claveColaborador) {
		this.claveColaborador = claveColaborador.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status.trim();
	}
	//*/
	
	public long getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(long idColaborador) {
		this.idColaborador = idColaborador;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto.trim();
	}
	
	

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion.trim();
	}

	// TODO processRow
	public RESULT processRow(SesionDatos sesion){

		super._count_process++;
		
		this.setOperacion("");
		String referenciaIB = this.getReferenciaIB();

		if (StringUtils.isNumericSpace(referenciaIB)) {

			RESULT result = BuscaOrdenDeCompra();
			
			if (result == RESULT.OK) {
				result = valida();
				
				if (result == RESULT.OK) {
					int pk = insertConciliacion();
					if (pk > 0) {
						if (orden.guarda(this, getUsuario()) == RESULT.OK) {
							super._count_success++;
							return RESULT.OK;
						}
						super._count_excluded++;
						return RESULT.OK;
					}
				}
			}
		} else {
			super._mensaje = "La clave interbancaria no es numerica";
		}

		super._count_error++;
		return RESULT.ERROR;
	}

	public RESULT valida() {
		
		//String sql = "SELECT COUNT(*) AS 'MAX' FROM CONCILIACION WHERE REFBANCARIA=";
		//orden.referencia
		
		
		double costoBoletos = orden.calcCostoBoletos();
		
		if (costoBoletos != orden.getImporte()) {
			super._mensaje = "El importe de la orden de compra no corresponde con el costo de los boletos";
			return RESULT.ERROR;
		}
		
		if (getImporte() < orden.getImporte()) {
			super._mensaje = "El importe es menor al costo de los boletos de la orden de compra : \"" + orden.getFolio() + "\"";
			return RESULT.ERROR;
		}
		else if (getImporte() > orden.getImporte()) {
			super._mensaje = "El importe es mayor al costo de los boletos de la orden de compra : \"" + orden.getFolio() + "\"";
			return RESULT.ERROR;
		}
		return RESULT.OK;
	}
	
	OrdenDeCompra orden;
	public RESULT BuscaOrdenDeCompra(){
		
		orden = new OrdenDeCompra();
		orden.setReferencia(getReferenciaIB());
		return orden.consultaOrden(this);
	}
	
	public int insertConciliacion() {
		//SE INSERTA EL NUEVO IMPORTE
		String sql = "INSERT INTO CONCILIACION (PK_FOLIO,REFBANCARIA,CUENTA,FECHA,HORA,SUCURSAL,DESCRIPCION,CARGO,IMPORTE,SALDO,REFERENCIA,CONCEPTO,ORDEN,ELECTRONICO,USUARIO,FECHA_R)"
				+ " VALUES ('"
				+ this.orden.getFolio() + "','"
				+ this.getReferenciaIB() + "','"
				+ this.getCuenta() + "','"
				+ this.getFecha() + "','"
				+ this.getHora() + "','"
				+ this.getSucursal() + "','"
				+ this.getDescripcion() + "','"
				+ this.getCargo() + "',"
				+ this.getImporte() + ","
				+ this.getSaldo() + ",'"
				+ this.getReferencia() + "','"
				+ this.getConcepto() + "','"
				+ (++this.conciliacion_orden) + "','"
				+ "1','"
				+ this.getUsuario() + "','"
				+ this.FECHA_R + "')";
		
		sql = sql.replace("\t", "");
		
		System.out.println("conciliacion INSERT:" + sql);
		return db.execQuerySelectId(sql);
	}
	
	
	public void resetFechaRegistro()
	{
		this.FECHA_R = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()); 
	}
	

}



