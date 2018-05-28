package com.sorteo.reportes.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.core.SuperModel;

public class mStatusDeBoletos extends SuperModel {
	
	//private int id;
	private int idsector;
	private int idnicho;
	private int icolaborador;
	private int idSorteo;
	private int idBoleto;
	private int folio;
	private char estatus;
	private double abono;
	private double costo;
	
	private String boleto;
	
	private int pktalonario;
	private int idtalonario;	
	private String sorteo;
	private String sector;
	private int numBoletos;
	private int numBoletosasignados;
	private int numBoletosExtraviados;
	private int numBoletosVendidos;
	private int numBoletosParcialmenteVendidos;
	
	
	
	private char incidencia;
	private String formatofc8;
	private String folioactamp;
	private String detallesincidencia;
	
	private String nombre;
	private String apellidos;
	private String telefonof;
	private String telefonom;
	private String correo;
	
	private String calle;
	private String numero;
	private String colonia;
	private String estado;
	private String municipio;
	
	private String usuario;
	
	private int idUsuario;

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public int getPktalonario() {
		return pktalonario;
	}


	public void setPktalonario(int pktalonario) {
		this.pktalonario = pktalonario;
	}


	public int getIdsector() {
		return idsector;
	}


	public void setIdsector(int idsector) {
		this.idsector = idsector;
	}


	public int getIcolaborador() {
		return icolaborador;
	}


	public void setIcolaborador(int icolaborador) {
		this.icolaborador = icolaborador;
	}


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public char getIncidencia() {
		return incidencia;
	}


	public void setIncidencia(char incidencia) {
		this.incidencia = incidencia;
	}


	public String getFormatofc8() {
		return formatofc8;
	}


	public void setFormatofc8(String formatofc8) {
		this.formatofc8 = formatofc8;
	}


	public String getFolioactamp() {
		return folioactamp;
	}


	public void setFolioactamp(String folioactamp) {
		this.folioactamp = folioactamp;
	}


	public String getDetallesincidencia() {
		return detallesincidencia;
	}


	public void setDetallesincidencia(String detallesincidencia) {
		this.detallesincidencia = detallesincidencia;
	}


	public String getBoleto() {
		return boleto;
	}


	public void setBoleto(String boleto) {
		this.boleto = boleto;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getTelefonof() {
		return telefonof;
	}


	public void setTelefonof(String telefonof) {
		this.telefonof = telefonof;
	}


	public String getTelefonom() {
		return telefonom;
	}


	public void setTelefonom(String telefonom) {
		this.telefonom = telefonom;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getColonia() {
		return colonia;
	}


	public void setColonia(String colonia) {
		this.colonia = colonia;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getMunicipio() {
		return municipio;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	public int getNumBoletosParcialmenteVendidos() {
		return numBoletosParcialmenteVendidos;
	}


	public void setNumBoletosParcialmenteVendidos(int numBoletosParcialmenteVendidos) {
		this.numBoletosParcialmenteVendidos = numBoletosParcialmenteVendidos;
	}


	public int getNumBoletosVendidos() {
		return numBoletosVendidos;
	}


	public void setNumBoletosVendidos(int numBoletosVendidos) {
		this.numBoletosVendidos = numBoletosVendidos;
	}


	public int getNumBoletosExtraviados() {
		return numBoletosExtraviados;
	}


	public void setNumBoletosExtraviados(int numBoletosExtraviados) {
		this.numBoletosExtraviados = numBoletosExtraviados;
	}


	public int getNumBoletos() {
		return numBoletos;
	}


	public void setNumBoletos(int numBoletos) {
		this.numBoletos = numBoletos;
	}


	public int getNumBoletosasignados() {
		return numBoletosasignados;
	}


	public void setNumBoletosasignados(int numBoletosasignados) {
		this.numBoletosasignados = numBoletosasignados;
	}

	
	public char getEstatus() {
		return estatus;
	}


	public double getAbono() {
		return abono;
	}


	public void setAbono(double abono) {
		this.abono = abono;
	}


	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}
	
	public int getIdnicho() {
		return idnicho;
	}


	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
	}


	public int getIdSorteo() {
		return idSorteo;
	}


	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}


	public int getIdBoleto() {
		return idBoleto;
	}


	public void setIdBoleto(int idBoleto) {
		this.idBoleto = idBoleto;
	}


	public int getFolio() {
		return folio;
	}


	public void setFolio(int folio) {
		this.folio = folio;
	}


	public int getIdtalonario() {
		return idtalonario;
	}


	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}


	public String getSorteo() {
		return sorteo;
	}


	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}

	
	
	public mStatusDeBoletos() {
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	public ResultSet consultaEstadisticas(long pkSorteo)
	{
		String sql =
				"SELECT "
				+ " (SELECT COUNT(PK1) FROM BOLETOS WHERE SORTEO='43') AS 'TOTAL_BOLETOS'"
				+ ",(SELECT COUNT(PK1) FROM BOLETOS WHERE SORTEO='43' AND INCIDENCIA<>'N') AS 'INCIDENCIAS'"
				+ ",(SELECT COUNT(PK1) FROM BOLETOS WHERE SORTEO='43' AND INCIDENCIA='N' AND VENDIDO<>'V') AS 'NO_VENDIDOS'"
				+ ",(SELECT COUNT(PK1) FROM BOLETOS WHERE SORTEO='43' AND INCIDENCIA='N' AND VENDIDO='V' AND RETORNADO=0) AS 'EN_TRANSITO'"
				+ ",(SELECT COUNT(PK1) FROM BOLETOS WHERE SORTEO='43' AND INCIDENCIA='N' AND VENDIDO='V' AND RETORNADO=1) AS 'VENDIDOS_Y_RETORNADOS'";
		return db.getDatos(sql);
	}
	//*/
	
	public int consultaTotalDeBoletos(long pkSorteo){
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM BOLETOS WHERE SORTEO='" + pkSorteo + "' ";
		ResultSet res = db.getDatos(sql);
		int max = 0;
		try{
			if (res.next()) {
				max = res.getInt("MAX");
			}
		}catch(SQLException ex) { }
		return max;
	}
	
	public ResultSet consultaBoletosVendidosYEntregados(long pkSorteo){
		String sql = "SELECT * FROM BOLETOS WHERE SORTEO='" + pkSorteo + "' AND INCIDENCIA='N' AND VENDIDO='V' AND RETORNADO=1";
		return db.getDatos(sql);
	}
	
	public ResultSet consultaBoletosEnTransito(long pkSorteo){
		String sql = "SELECT * FROM BOLETOS WHERE SORTEO='" + pkSorteo + "' AND INCIDENCIA='N' AND VENDIDO='V' AND RETORNADO=0";
		return db.getDatos(sql);
	}
	
	public ResultSet consultaBoletosNoVendidos(long pkSorteo){
		String sql = "SELECT * FROM BOLETOS WHERE SORTEO='" + pkSorteo + "' AND INCIDENCIA='N' AND VENDIDO<>'V'";
		return db.getDatos(sql);
	}
	
	public ResultSet consultaBoletosConIncidencia(long pkSorteo){
		String sql = "SELECT FOLIO, INCIDENCIA FROM BOLETOS WHERE SORTEO='" + pkSorteo + "' AND INCIDENCIA<>'N' ORDER BY FOLIO ASC";
		return db.getDatos(sql);
	}
	
}
