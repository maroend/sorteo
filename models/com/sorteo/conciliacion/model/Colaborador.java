package com.sorteo.conciliacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Colaborador{
	public long pk;
	public long pkSector;
	public long pkNicho;
	public String clave;
	public String claveSector;
	public String claveNicho;
	public int talonarios;
	public double costo;
	public double abono;
	public double importe;
	
	public Colaborador() {
	}
	
	public Colaborador(ResultSet res) throws SQLException {
		this.pk = res.getLong("PK1");
		this.clave = res.getString("CLAVE");
		this.pkSector = res.getLong("PK_SECTOR");
		this.claveSector = res.getString("CLAVE_SECTOR");
		this.pkNicho = res.getLong("PK_NICHO");
		this.claveNicho = res.getString("CLAVE_NICHO");
		this.talonarios = res.getInt("TALONARIOS");
		this.costo = res.getDouble("COSTO");
		this.abono = res.getDouble("ABONO");
		this.importe = 0;
	}
	
	public Colaborador(Colaborador c) {
		this.pk = c.pk;
		this.pkSector = c.pkSector;
		this.pkNicho = c.pkNicho;
		this.clave = c.clave;
		this.claveSector = c.claveSector;
		this.claveNicho = c.claveNicho;
		this.talonarios = c.talonarios;
		this.costo = c.costo;
		this.abono = c.abono;
		this.importe = c.importe;
	}
	
	public long getId() {
		return this.pk;
	}
}
