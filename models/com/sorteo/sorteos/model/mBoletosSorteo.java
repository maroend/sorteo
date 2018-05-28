package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.core.Factory;
import com.core.Factory.LOG;
import com.core.Seguimiento;
import com.core.Seguimiento.ASIGNACION;
import com.core.Global;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mBoletosSorteo extends SuperModel 
{
	private int idSorteo;
	private int idSector;
	private int idNicho;
	private int idColaborador;
	private int idBoleto;
	//private int folio;
	private char estatus;
	private double abono;
	private double costo;
	
	private String boleto;
	
	private int pktalonario;
	private int idtalonario;	// folio del talonario
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
	
	
	private int idCompradorA;
	private int idComprador;
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
	private String CP;
	
	private String usuario;
	private int idUsuario;
	private int autogenerado;
	
	
	private int filtrovendidos;
	private int filtronovendidos;
	private int filtroparciales;
	private int filtroextraviados;
	private int filtrorobados;
	
	private boolean sorteoActivo;
	
	
	public int getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(int idComprador) {
		this.idComprador = idComprador;
	}	
	
	
	public int getIdCompradorA() {
		return idCompradorA;
	}

	public void setIdCompradorA(int idCompradorA) {
		this.idCompradorA = idCompradorA;
	}
	
	
	
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	public int getFiltrovendidos() {
		return filtrovendidos;
	}


	public void setFiltrovendidos(int filtrovendidos) {
		this.filtrovendidos = filtrovendidos;
	}


	public int getFiltronovendidos() {
		return filtronovendidos;
	}


	public void setFiltronovendidos(int filtronovendidos) {
		this.filtronovendidos = filtronovendidos;
	}


	public int getFiltroparciales() {
		return filtroparciales;
	}


	public void setFiltroparciales(int filtroparciales) {
		this.filtroparciales = filtroparciales;
	}


	public int getFiltroextraviados() {
		return filtroextraviados;
	}


	public void setFiltroextraviados(int filtroextraviados) {
		this.filtroextraviados = filtroextraviados;
	}


	public int getFiltrorobados() {
		return filtrorobados;
	}


	public void setFiltrorobados(int filtrorobados) {
		this.filtrorobados = filtrorobados;
	}


	public int getPktalonario() {
		return pktalonario;
	}


	public void setPktalonario(int pktalonario) {
		this.pktalonario = pktalonario;
	}


	public int getIdSector() {
		return idSector;
	}


	public void setIdSector(int idsector) {
		this.idSector = idsector;
	}


	public int getIdColaborador() {
		return idColaborador;
	}


	public void setIdColaborador(int icolaborador) {
		this.idColaborador = icolaborador;
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

	
	public String getCP() {
		return CP;
	}
	
	
	public void setCP(String cp) {
		this.CP = cp;
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
	
	public int getIdNicho() {
		return idNicho;
	}


	public void setIdNicho(int idnicho) {
		this.idNicho = idnicho;
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
	

	public mBoletosSorteo() {
		// TODO Auto-generated constructor stub
		autogenerado = 0;
	}

	
	public boolean getSorteoActivo() {
		return sorteoActivo;
	}


	public void setSorteoActivo(boolean sorteoActivo) {
		this.sorteoActivo = sorteoActivo;
	}
	
	
	public void setAutogenerado(boolean auto) {
		this.autogenerado = auto ? 1 : 0;
	}
	
	public int getAutogenerado() {
		return this.autogenerado;
	}
	
	
	public ResultSet getMunicipios(int idestado) {
			
    	String sql = "SELECT DISTINCT(D_mnpio),c_mnpio FROM SEPOMEX WHERE c_estado ="+idestado;

		ResultSet rs = db.getDatos(sql);

		return rs;
	}
	
	public int contar(String search) {
		boolean buscar_por_incidencia = false;
		boolean buscar_por_colaborador = false;
		String condicion = "B.PK_SORTEO = " + getIdSorteo();
		
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion += " AND ((B.FOLIO LIKE '" + search + "') OR (B.FOLIO_TALONARIO = " + search + "))";
			} else {
				condicion += " AND (COLABORADOR LIKE '%" + search + "%')";
				buscar_por_colaborador = true;
			}
		}
		
		if (this.getFiltrovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'V' "; }
		if (this.getFiltronovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'N' "; }
		if (this.getFiltroextraviados() == 1) { condicion += " AND B.I_INCIDENCIA = 'E' "; buscar_por_incidencia = true; }
		if (this.getFiltrorobados() == 1) { condicion += " AND B.I_INCIDENCIA = 'R' "; buscar_por_incidencia = true; }
		
		String sql;
		if (buscar_por_incidencia)
			sql = "SELECT COUNT(*) AS 'VALUE' FROM VBOLETOS_INCIDENCIAS B WHERE " + condicion + " \n";
		else if (buscar_por_colaborador)
			sql = "SELECT COUNT(*) AS 'VALUE' FROM VBOLETOS_ASIGNACION_COLABORADOR B WHERE " + condicion + " \n";
		else
			sql = "SELECT COUNT(*) AS 'VALUE' FROM VBOLETOS_ASIGNACION_SORTEO B WHERE " + condicion + " \n";
		
		
		System.out.println("count: " + sql);
		return db.getValue(sql, 0);
	}

	public ResultSet paginacion(int pg, int numreg, String search) {
		boolean buscar_por_incidencia = false;
		boolean buscar_por_colaborador = false;
		String condicion = "B.PK_SORTEO = " + getIdSorteo();
		
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion += " AND ((B.FOLIO LIKE '" + search + "') OR (B.FOLIO_TALONARIO = " + search + "))";
			} else {
				condicion += " AND (COLABORADOR LIKE '%" + search + "%')";
				buscar_por_colaborador = true;
			}
		}
		
		if (this.getFiltrovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'V' "; }
		if (this.getFiltronovendidos() == 1) { condicion += " AND B.PK_ESTADO = 'N' "; }
		if (this.getFiltroextraviados() == 1) { condicion += " AND B.I_INCIDENCIA = 'E' "; buscar_por_incidencia = true; }
		if (this.getFiltrorobados() == 1) { condicion += " AND B.I_INCIDENCIA = 'R' "; buscar_por_incidencia = true; }
		
		String sql;
		if (buscar_por_incidencia)
			sql = "SELECT * FROM VBOLETOS_INCIDENCIAS B WHERE " + condicion + " \n";
		else if (buscar_por_colaborador)
			sql = "SELECT * FROM VBOLETOS_ASIGNACION_COLABORADOR B WHERE " + condicion + " \n";
		else
			sql = "SELECT * FROM VBOLETOS_ASIGNACION_SORTEO B WHERE " + condicion + " \n";
		
		sql = sql
				+ " ORDER BY B.PK_TALONARIO ASC,B.FOLIO ASC \n"
				+ " OFFSET (" + ((pg - 1) * numreg) + ") ROWS \n"
				+ " FETCH NEXT " + numreg + " ROWS ONLY \n";

		System.out.println("paginacion: " + sql);
		return db.getDatos(sql);
	}
	
	public String[] consultaPoblacionXBoleto(int pkBoleto){
		String[] values = { "", "", "", "", "", "" };
		String sql
				= "SELECT \n"
				+ " (select TOP 1 PK_SECTOR from SORTEOS_SECTORES_BOLETOS where PK_BOLETO = " + pkBoleto +") AS 'PK_SECTOR' \n"
				+ ",(select TOP 1 PK_NICHO from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = " + pkBoleto +") AS 'PK_NICHO' \n"
				+ ",(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS where PK_BOLETO = " + pkBoleto +") AS 'PK_COLABORADOR' \n"
				/*+ ",(select TOP 1 S.SECTOR FROM SORTEOS_SECTORES_BOLETOS SSB, SECTORES S WHERE SSB.PK_BOLETO = " + pkBoleto +") AS 'SECTOR' \n"
				+ ",(select TOP 1 NICHO FROM NICHOS WHERE PK1=(select TOP 1 PK_NICHO from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = " + pkBoleto +")) AS 'NICHO' \n"
				+ ",(select TOP 1 CONCAT(NOMBRE,', ',APATERNO,' ',AMATERNO)  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = " + pkBoleto +")) AS 'COLABORADOR' \n"
				*/
				;
		try {
			ResultSet res = db.getDatos(sql);
			if (res.next()) {
				values[0] = (res.getString("PK_SECTOR"));
				values[1] = (res.getString("PK_NICHO"));
				values[2] = (res.getString("PK_COLABORADOR"));
				/*values[3] = (res.getString("SECTOR"));
				values[4] = (res.getString("NICHO"));
				values[5] = (res.getString("COLABORADOR"));
				*/
				values[3] = "";
				values[4] = "";
				values[5] = "";
			}
		}catch (SQLException ex) { }
		return values;
	}
	
	public void consultaSorteo(mBoletosSorteo obj)
	{
		db.con();
		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + obj.getIdSorteo()+ "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				if (rs.next()) {
					this.setSorteo(rs.getString("SORTEO"));
					int activo = rs.getInt("ACTIVO");
					this.setSorteoActivo(activo==1);
				}
				rs.close();
			}
		} catch (SQLException ex) {
			com.core.Factory.Error(ex, sql);
		}
	}

	public String getBoletosTalonariosColaborador() {
		 
		 db.con();
	 	 String sql = "";
	 	 String total = "";
	 	 ResultSet rs = null;
	 	
	 	 //TOTAL TALONARIOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+"";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";} /*rs.close();*/}}
	 	 catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	 	//TALONARIOS ASIGNADOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+" AND ASIGNADO = 1";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
	 	 
	 	//TALONARIOS PARCIALMENTE VENDIDOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS ST, TALONARIOS T " 
                +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'P' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TALONARIOS VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS ST, TALONARIOS T " 
	                +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'V' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL DE BOLETOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TOTAL BOLETOS ASIGNADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"'  AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+" AND ASIGNADO = 1";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL BOLETOS PARCIALMENTE VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
                +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'P' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" AND SB.PK_NICHO = "+this.getIdNicho()+" AND SB.PK_COLABORADOR= "+this.getIdColaborador()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
	    
		//TOTAL BOLETOS  VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
	                +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'V' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" AND SB.PK_NICHO = "+this.getIdNicho()+" AND SB.PK_COLABORADOR= "+this.getIdColaborador()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  EXTRAVIADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+" AND INCIDENCIA = 'E'";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  ROBADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND PK_COLABORADOR= "+this.getIdColaborador()+" AND INCIDENCIA = 'R'";
			 rs = db.getDatos(sql);
			 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
	     return total;
		 
	 }
		
	 
	 public String getBoletosTalonariosNicho(){
		 
		 
		 db.con();
	 	 String sql = "";
	 	 String total = "";
	 	 ResultSet rs = null;
	 	
	 	 //TOTAL TALONARIOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+"";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";} /*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	 	//TALONARIOS ASIGNADOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND ASIGNADO = 1";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
	 	 
	 	//TALONARIOS PARCIALMENTE VENDIDOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS ST, TALONARIOS T " 
                +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'P' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TALONARIOS VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS ST, TALONARIOS T " 
	                +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'V' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL DE BOLETOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TOTAL BOLETOS ASIGNADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"'  AND PK_SECTOR = '"+this.getIdSector()+"' AND PK_NICHO = "+this.getIdNicho()+" AND ASIGNADO = 1";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL BOLETOS PARCIALMENTE VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B "
                +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'P' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" AND SB.PK_NICHO = "+this.getIdNicho()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
	    
		//TOTAL BOLETOS  VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B "
	                +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'V' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" AND SB.PK_NICHO = "+this.getIdNicho()+"";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  EXTRAVIADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND INCIDENCIA = 'E'";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  ROBADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND INCIDENCIA = 'R'";
			 rs = db.getDatos(sql);
			 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
	     return total;
		 
	 }
    
	 
	 
	 public String getBoletosTalonariosSector(){
	 	 
	 	 db.con();
	 	 String sql = "";
	 	 String total = "";
	 	 ResultSet rs = null;
	 	
	 	 //TOTAL TALONARIOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"'";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";} /*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	 	//TALONARIOS ASIGNADOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"' AND ASIGNADO = 1";
	 	 rs = db.getDatos(sql);
	 	 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
	 	 
	 	//TALONARIOS PARCIALMENTE VENDIDOS
	 	 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS ST, TALONARIOS T " 
                +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'P' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TALONARIOS VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS ST, TALONARIOS T " 
	                +" WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'V' AND ST.PK_SORTEO = "+this.getIdSorteo()+" AND ST.PK_SECTOR = "+this.getIdSector()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL DE BOLETOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_SECTOR = '"+this.getIdSector()+"'";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	 	 
		
		 //TOTAL BOLETOS ASIGNADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"'  AND PK_SECTOR = '"+this.getIdSector()+"' AND ASIGNADO = 1";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
		//TOTAL BOLETOS PARCIALMENTE VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B "
                +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'P' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
	    
		//TOTAL BOLETOS  VENDIDOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B "
	                +" WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'V' AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND SB.PK_SECTOR = "+this.getIdSector()+" ";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  EXTRAVIADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND INCIDENCIA = 'E'";
		 rs = db.getDatos(sql);
		 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	    
		 
		//TOTAL BOLETOS  ROBADOS
		 sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND INCIDENCIA = 'R'";
			 rs = db.getDatos(sql);
			 try {if (rs != null) { while(rs.next()){ total += rs.getString("TOTAL")+"|";}/*rs.close();*/}} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		 
		 
	     return total;
		
	}
	
	public int talonarios;
	public int talonarios_asignados;
	public int talonarios_parcial_vendidos;
	public int talonarios_vendidos;
	
	public int boletos;
	public int boletos_asignados;
	public int boletos_parcial_vendidos;
	public int boletos_vendidos;
	public int boletos_extraviados;
	public int boletos_robados;

	public void consultaBoletosTalonarios()
	{
		String sql = "";

		// TOTAL TALONARIOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"'";
		talonarios = db.Count(sql);

		//TALONARIOS ASIGNADOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VNICHOS_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND DISPONIBLES < NUMBOLETOS";
		talonarios_asignados = db.Count(sql);

		//TALONARIOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VNICHOS_TALONARIOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND VENDIDO = 'P'";
		talonarios_parcial_vendidos = db.Count(sql);

		//TALONARIOS VENDIDOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VNICHOS_TALONARIOS WHERE PK_SORTEO = '" + this.getIdSorteo() + "' AND VENDIDO = 'V'";
		talonarios_vendidos = db.Count(sql);


		
		//TOTAL DE BOLETOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VBOLETOS WHERE PK_SORTEO = "+this.getIdSorteo()+"";
		boletos = db.Count(sql);
		
		//TOTAL BOLETOS ASIGNADOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VSECTORES_BOLETOS WHERE PK_SORTEO = "+this.getIdSorteo()+"";
		boletos_asignados = db.Count(sql);
		
		//TOTAL BOLETOS  VENDIDOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VBOLETOS WHERE PK_SORTEO = '"+this.getIdSorteo()+"' AND PK_ESTADO = 'V'";
		boletos_vendidos = db.Count(sql);

		//TOTAL BOLETOS  EXTRAVIADOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VBOLETOS_INCIDENCIAS WHERE I_INCIDENCIA = 'E'  AND PK_SORTEO = '"+this.getIdSorteo()+"'";
		boletos_extraviados = db.Count(sql);

		//TOTAL BOLETOS  ROBADOS
		sql = "SELECT COUNT(*) AS 'MAX' FROM VBOLETOS_INCIDENCIAS WHERE I_INCIDENCIA = 'R'  AND PK_SORTEO = '"+this.getIdSorteo()+"'";
		boletos_robados = db.Count(sql);
	}


    ////////////////////////////////////SEGUIMIENTO COMPRADORES BOLETOS/////////////////////////////

    public ResultSet getComprador(){
    	
    	 db.con();
      	 //String sql = "SELECT * FROM COMPRADORES WHERE PK_SORTEO="+this.getIdSorteo()+" AND BOLETO = '"+this.getBoleto()+"'";
    	 String sql = "SELECT C.*,B.ABONO AS 'BOLETO_ABONO' FROM COMPRADORES C, BOLETOS B WHERE C.PK_BOLETO=B.PK1 AND C.PK_SORTEO="+this.getIdSorteo()+" AND C.BOLETO = '"+this.getBoleto()+"'";
      	 ResultSet rs = db.getDatos(sql);
		 return rs;

    }
	
	public ResultSet GetIncidenciaBoleto(){
		 db.con();
		// String sql = "SELECT * FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
		 
		String sql = "SELECT BI.* FROM BOLETOS B, BOLETOS_INCIDENCIAS BI WHERE BI.PK_BOLETO = B.PK1  AND B.FOLIO = '"+this.getBoleto()+"'";//AND B.PK_TALONARIO = '"+this.getIdtalonario()+"'

		  		 
	 	 ResultSet rs = db.getDatos(sql);
		 System.out.println(sql);
	 	 return rs;
		
	}
	

	public void setAbonoTalonario(SesionDatos sesion){

		ResultSet rs = null;
		db.con();
		char estadotalonario = 'N';
		
		//String sql = "SELECT ISNULL(SUM(ABONO),0) AS TOTAL, ISNULL(SUM(COSTO),0) AS MONTO FROM BOLETOS WHERE SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"'";
		
		String sql = "SELECT ISNULL(SUM(ABONO),0) AS TOTAL, ISNULL(SUM(COSTO),0) AS MONTO FROM VBOLETOS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_TALONARIO = '"+this.getIdtalonario()+"'";
		
		
		rs = db.getDatos(sql);
		try {
			if (rs.next()) {
				
				System.out.println(rs.getDouble("TOTAL"));
				System.out.println("MONTO TALONARIO:"+rs.getDouble("MONTO"));
				
				if(rs.getDouble("MONTO")==rs.getDouble("TOTAL")){ estadotalonario = 'V'; }
				if(rs.getDouble("MONTO")>rs.getDouble("TOTAL")){ estadotalonario = 'P'; }
				if(rs.getDouble("TOTAL")==0){ estadotalonario = 'N'; }
				
				
				 sql = "UPDATE TALONARIOS SET ABONO = "+rs.getDouble("TOTAL")+" ,"
						+ "VENDIDO = '"+estadotalonario+"' ,"
						+ "USUARIO = '"+this.getUsuario()+"' ,"
						+ "FECHA_M = GETDATE() "
				 		+ "WHERE SORTEO = "+this.getIdSorteo()+" AND FOLIO = '"+this.getIdtalonario()+"'";
				
				 db.execQuery(sql);
				 
				 
				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db
							, ASIGNACION.COMPRADOR
							, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
							, ASIGNACION.TALONARIO, this.getPktalonario() , 0
							, estadotalonario
							, rs.getDouble("MONTO"), rs.getDouble("TOTAL")
							, 11
							, "-"
							, "ABONO"
							, "venta-bols-1");
				}
				catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				 
				// --- Se guarda un registro de seguimiento ---
				try {
					this.Log(sesion, LOG.EDITADO, this, "setAbonoTalonario",
							sesion.toShortString() + ", tal=" + this.getIdtalonario());
				}catch(Exception ex) { Factory.Error(ex, "Log"); }
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	}
	
	public void setComprador(SesionDatos sesion){
		String sql = "SELECT * FROM COMPRADORES WHERE PK_SORTEO = "+this.getIdSorteo()
				+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
		ResultSet rs = db.getDatos(sql);
		char etatusventa = 'N';
		
		try {
			if (rs.next()) {
				sql = "UPDATE COMPRADORES SET "
						+ "ABONO = "+this.getAbono() + ","
						+ "NOMBRE = '"+this.getNombre()+"' ,"
						+ "APELLIDOS = '"+this.getApellidos()+"' ,"
						+ "TELEFONOF = '"+this.getTelefonof()+"' ,"
						+ "TELEFONOM = '"+this.getTelefonom()+"' ,"
						+ "CORREO = '"+this.getCorreo()+"' ,"
						+ "CALLE = '"+this.getCalle()+"' ,"
						+ "NUMERO = '"+this.getNumero()+"' ,"
						+ "COLONIA = '"+this.getColonia()+"' ,"
						+ "ESTADO = '"+this.getEstado()+"' ,"
						+ "MUNDEL = '"+this.getMunicipio()+"' ,"
						+ "USUARIO = '"+this.getUsuario()+"' ,"
						+ "FECHA_M = GETDATE() "
						+ "WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
				
				db.execQuery(sql);
				
				if(this.getAbono() < this.getCosto()){ etatusventa = 'P'; }
				if(this.getAbono() == this.getCosto()){ etatusventa = 'V'; }
				if(this.getAbono() == 0){ etatusventa = 'N'; }
				
			} else {
				
				sql = "INSERT INTO COMPRADORES (PK_SORTEO,PK_TALONARIO,TALONARIO,PK_BOLETO,BOLETO,ABONO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,NOMBRE,APELLIDOS,TELEFONOF,TELEFONOM,CORREO,CALLE,NUMERO,COLONIA,ESTADO,MUNDEL,USUARIO) VALUES ("+this.getIdSorteo()+","
						+ " "+this.getPktalonario()+","
						+ " '"+this.getIdtalonario()+"',"
						+ "'"+this.getIdBoleto()+"',"
						+ "'"+this.getBoleto()+"',"
						+ ""+this.getAbono()+","
						+ ""+this.getIdSector()+","
						+ ""+this.getIdNicho()+","
						+ ""+this.getIdColaborador()+","
						+ "'"+this.getNombre()+"',"
						+ "'"+this.getApellidos()+"',"
						+ "'"+this.getTelefonof()+"',"
						+ "'"+this.getTelefonom()+"',"
						+ "'"+this.getCorreo()+"',"
						+ "'"+this.getCalle()+"',"
						+ "'"+this.getNumero()+"',"
						+ "'"+this.getColonia()+"',"
						+ "'"+this.getEstado()+"',"
						+ "'"+this.getMunicipio()+"',"
						+ "'"+this.getUsuario()+"')";

				db.execQuery(sql);
				
				if(this.getAbono() < this.getCosto()){ etatusventa = 'P';  }
				if(this.getAbono() == this.getCosto()){ etatusventa = 'V'; }
				if(this.getAbono() == 0){ etatusventa = 'N'; }
			}
			
			// Se actualiza el boleto
			sql = "UPDATE BOLETOS SET"
					+ " ABONO = "+this.getAbono()+" ,"
					+ " VENDIDO = '"+etatusventa+"',"
					+ " FECHA_M = GETDATE(), "
					+ " USUARIO = '"+this.getUsuario()+"'"
					+ " WHERE SORTEO = "+this.getIdSorteo()
					+ " AND TALONARIO = '"+this.getIdtalonario()+"'"
					+ " AND FOLIO='"+this.getBoleto()+"'";
			db.execQuery(sql);
			
			// --- Se guarda un registro de seguimiento ---
			try {
				Seguimiento.guardaVenta(db
						, ASIGNACION.COMPRADOR
						, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
						, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
						, etatusventa
						, this.getCosto(), this.getAbono()
						, 1
						, "-"
						, "VENTA"
						, "venta-bols-1");
			}
			catch (Exception ex) { com.core.Factory.Error(ex, sql); }
			
			
			//ABONAMOS AL TALONARIO LA VENTA DEL BOLETO   
			//setAbonoTalonario(sesion); checar ******saca de vista******
			
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "setComprador",
					"tal=" + this.getIdtalonario() + ", bol="+this.getBoleto()+", abono="+this.getAbono());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	
	public void registrarSoloVenta(SesionDatos sesion){
		String sql;
		char etatusventa = 'N';
		
		//if(this.getAbono() < this.getCosto()){ etatusventa = 'P'; }//CHECAR
		if(this.getAbono() == this.getCosto()){ etatusventa = 'V'; }
		if(this.getAbono() == 0){ etatusventa = 'N'; }
		
		/*sql = "UPDATE BOLETOS SET "
				+ "ABONO = " + this.getAbono() + ", "
				+ " VENDIDO = '" + etatusventa + "',"
				+ " FECHA_M = GETDATE() "
				+ " WHERE "
				+ "SORTEO = " + this.getIdSorteo()
				+ " AND TALONARIO = '" + this.getIdtalonario()+ "'"
				+ " AND FOLIO=" + "'" + this.getBoleto() + "'";*/
		
		
		
		sql = "UPDATE BOLETOS SET "
				+ "ABONO = " + this.getAbono() + ", "
				+ " PK_ESTADO = '" + etatusventa + "',"
				+ " FECHA_M = GETDATE() "
				+ " WHERE  PK_TALONARIO = '" + this.getPktalonario()+ "'"
				+ " AND FOLIO=" + "'" + this.getBoleto() + "'";
		db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			Seguimiento.guardaVenta(db
					, ASIGNACION.COMPRADOR
					, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
					, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
					, etatusventa
					, this.getCosto(), this.getAbono()
					, 1
					, "-"
					, "VENTA"
					, "solo-venta-bols-1"
					,sesion.nombreCompleto);
		}
		catch (Exception ex) { com.core.Factory.Error(ex, sql); }
			
		//ABONAMOS AL TALONARIO LA VENTA DEL BOLETO   
		//setAbonoTalonario(sesion);  checar ******saca de vista******

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "registraSoloVenta",
					"tal=" + this.getIdtalonario() + ", bol="+this.getBoleto()+", abono="+this.getAbono());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	
	
	
	public int registrarSoloComprador(SesionDatos sesion) {
		int result = 0;		
		
		String sql = " DELETE FROM COMPRADORES_BOLETOS WHERE PK_COMPRADOR = "+this.getIdCompradorA()+""
		+ " AND PK_BOLETO = "+this.getIdBoleto();		
		db.execQuery(sql);		
			
	
		sql = "INSERT INTO COMPRADORES_BOLETOS (PK_COMPRADOR,PK_BOLETO) VALUES ("						
				+ "'"+this.getIdComprador()+"',"
				+ "'"+this.getIdBoleto()+"')";			

		System.out.println(sql);
		db.execQuery(sql);						
		
		
		// --- Se guarda un registro de seguimiento ---
		try {
			Seguimiento.guardaVenta(db
					, ASIGNACION.COMPRADOR
					, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
					, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
					, '-'
					, this.getCosto(), this.getAbono()
					, 1
					, "-"
					, "VENTA"
					, "ins-comprador-1");
		}
		catch (Exception ex) { Factory.Error(ex, sql); }

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "registrarSoloComprador", "bol="+this.getBoleto()+", nom=" + this.getNombre().replace("'", "") + " "+this.getApellidos().replace("'", "")+", dir="+this.getCalle().replace("'", "")+" "+this.getNumero().replace("'", "")+","+this.getColonia().replace("'", ""));
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return result;
	}
	
	
	public int registrar_Solo_Comprador(SesionDatos sesion) {
		int result = 0;			
			
		
		String sql = " SELECT PK1 FROM COMPRADORES WHERE PK1 = "+this.getIdComprador();
		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next()) {
				
				sql = "UPDATE COMPRADORES SET "					
						+ "NOMBRE = '"+this.getNombre().replace("'", "''")+"' ,"
						+ "APELLIDOS = '"+this.getApellidos().replace("'", "''")+"' ,"
						+ "TELEFONO_F = '"+this.getTelefonof().replace("'", "")+"' ,"
						+ "TELEFONO_M = '"+this.getTelefonom().replace("'", "")+"' ,"
						+ "CORREO = '"+this.getCorreo().replace("'", "")+"' ,"
						+ "CALLE = '"+this.getCalle().replace("'", "''")+"' ,"
						+ "NUMERO = '"+this.getNumero().replace("'", "''")+"' ,"
						+ "COLONIA = '"+this.getColonia().replace("'", "''")+"' ,"
						+ "ESTADO = '"+this.getEstado()+"' ,"
						+ "MUNDEL = '"+this.getMunicipio()+"' ,"
						+ "CP = '"+this.getCP()+"' ,"
						+ "USUARIO = '"+this.getUsuario()+"' ,"
						+ "FECHA_M = GETDATE() "
						+ " WHERE  PK1="+ "'"+rs.getString("PK1")+"'";						
				
				System.out.println(sql);				
				result = db.execQuery(sql);		
				
				
				// --- Se guarda un registro de seguimiento ---
				/*try {
					Seguimiento.guardaVenta(db
							, ASIGNACION.COMPRADOR
							, Long.valueOf(sesion.pkSorteo),0,0,0
							, ASIGNACION.COMPRADOR, 0 , 0
							, '-'
							, 0, 0
							, 1
							, "-"
							, "VENTA"
							, "upd-comprador-1");
				}
				catch (Exception ex) { com.core.Factory.Error(ex, sql); }*/
				
			} else {
				
			
				
	sql = "INSERT INTO COMPRADORES (NOMBRE,APELLIDOS,TELEFONO_F,TELEFONO_M,CORREO,CALLE,NUMERO,COLONIA,ESTADO,MUNDEL,CP,USUARIO) VALUES ("						
						+ "'"+this.getNombre().replace("'", "''")+"',"
						+ "'"+this.getApellidos().replace("'", "''")+"',"
						+ "'"+this.getTelefonof().replace("'", "")+"',"
						+ "'"+this.getTelefonom().replace("'", "")+"',"
						+ "'"+this.getCorreo().replace("'", "")+"',"
						+ "'"+this.getCalle().replace("'", "''")+"',"
						+ "'"+this.getNumero().replace("'", "''")+"',"
						+ "'"+this.getColonia().replace("'", "''")+"',"
						+ "'"+this.getEstado()+"',"
						+ "'"+this.getMunicipio()+"',"
						+ "'"+this.getCP()+"',"
						+ "'"+this.getUsuario()+"')";				

				System.out.println(sql);
				int idComprador = db.execQuerySelectId(sql);			
				
	
				
				
				// --- Se guarda un registro de seguimiento ---
				/*try {
					Seguimiento.guardaVenta(db
							, ASIGNACION.COMPRADOR
							, Long.valueOf(sesion.pkSorteo),0,0,0
							, ASIGNACION.COMPRADOR, 0 ,0
							, '-'
							, 0, 0
							, 1
							, "-"
							, "VENTA"
							, "ins-comprador-1");
				}
				catch (Exception ex) { Factory.Error(ex, sql); }*/
			
			}//
			
		} catch (SQLException ex) { Factory.Error(ex, sql); }

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "registrarSoloComprador", "bol=-, nom=" + this.getNombre().replace("'", "") + " "+this.getApellidos().replace("'", "")+", dir="+this.getCalle().replace("'", "")+" "+this.getNumero().replace("'", "")+","+this.getColonia().replace("'", ""));
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return result;
	}
	
	
	
	
	
	
	
	public int registrarSoloComprador1(SesionDatos sesion) {
		int result = 0;		
		
		/*String sql = "SELECT * FROM COMPRADORES WHERE PK_SORTEO = "+this.getIdSorteo()
				+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";*/		
		
		String sql = " SELECT CP.PK1 FROM COMPRADORES CP, COMPRADORES_BOLETOS CPB "
				+ " WHERE  CP.PK1 = CPB.PK_COMPRADOR "
				+ "AND PK_BOLETO ='"+this.getIdBoleto()+"'";		
		
		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next()) {
				
				sql = "UPDATE COMPRADORES SET "
						//+ "ABONO = "+this.getAbono() + ","
						+ "NOMBRE = '"+this.getNombre().replace("'", "''")+"' ,"
						+ "APELLIDOS = '"+this.getApellidos().replace("'", "''")+"' ,"
						+ "TELEFONO_F = '"+this.getTelefonof().replace("'", "")+"' ,"
						+ "TELEFONO_M = '"+this.getTelefonom().replace("'", "")+"' ,"
						+ "CORREO = '"+this.getCorreo().replace("'", "")+"' ,"
						+ "CALLE = '"+this.getCalle().replace("'", "''")+"' ,"
						+ "NUMERO = '"+this.getNumero().replace("'", "''")+"' ,"
						+ "COLONIA = '"+this.getColonia().replace("'", "''")+"' ,"
						+ "ESTADO = '"+this.getEstado()+"' ,"
						+ "MUNDEL = '"+this.getMunicipio()+"' ,"
						+ "CP = '"+this.getCP()+"' ,"
						+ "USUARIO = '"+this.getUsuario()+"' ,"
						+ "FECHA_M = GETDATE() "
						+ " WHERE  PK1="+ "'"+rs.getString("PK1")+"'";
						//+ " WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
				
				System.out.println(sql);				
				result = db.execQuery(sql);				
				
				
				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db
							, ASIGNACION.COMPRADOR
							, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
							, ASIGNACION.COMPRADOR, 0 , this.getIdBoleto()
							, '-'
							, this.getCosto(), this.getAbono()
							, 1
							, "-"
							, "VENTA"
							, "upd-comprador-1");
				}
				catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				
			} else {
				
				/*sql = "INSERT INTO COMPRADORES (PK_SORTEO,PK_TALONARIO,TALONARIO,PK_BOLETO,BOLETO,ABONO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,NOMBRE,APELLIDOS,TELEFONOF,TELEFONOM,CORREO,CALLE,NUMERO,COLONIA,ESTADO,MUNDEL,USUARIO,AUTOGENERADO) VALUES ("
						+ this.getIdSorteo()+","
						+ " "+this.getPktalonario()+","
						+ " '"+this.getIdtalonario()+"',"
						+ "'"+this.getIdBoleto()+"',"
						+ "'"+this.getBoleto()+"',"
						+ ""+this.getAbono()+","
						+ ""+this.getIdSector()+","
						+ ""+this.getIdNicho()+","
						+ ""+this.getIdColaborador()+","
						+ "'"+this.getNombre().replace("'", "''")+"',"
						+ "'"+this.getApellidos().replace("'", "''")+"',"
						+ "'"+this.getTelefonof().replace("'", "")+"',"
						+ "'"+this.getTelefonom().replace("'", "")+"',"
						+ "'"+this.getCorreo().replace("'", "")+"',"
						+ "'"+this.getCalle().replace("'", "''")+"',"
						+ "'"+this.getNumero().replace("'", "''")+"',"
						+ "'"+this.getColonia().replace("'", "''")+"',"
						+ "'"+this.getEstado()+"',"
						+ "'"+this.getMunicipio()+"',"
						+ "'"+this.getUsuario()+"',"
						+ this.autogenerado+")";*/			
				
	sql = "INSERT INTO COMPRADORES (NOMBRE,APELLIDOS,TELEFONO_F,TELEFONO_M,CORREO,CALLE,NUMERO,COLONIA,ESTADO,MUNDEL,CP,USUARIO) VALUES ("						
						+ "'"+this.getNombre().replace("'", "''")+"',"
						+ "'"+this.getApellidos().replace("'", "''")+"',"
						+ "'"+this.getTelefonof().replace("'", "")+"',"
						+ "'"+this.getTelefonom().replace("'", "")+"',"
						+ "'"+this.getCorreo().replace("'", "")+"',"
						+ "'"+this.getCalle().replace("'", "''")+"',"
						+ "'"+this.getNumero().replace("'", "''")+"',"
						+ "'"+this.getColonia().replace("'", "''")+"',"
						+ "'"+this.getEstado()+"',"
						+ "'"+this.getMunicipio()+"',"
						+ "'"+this.getCP()+"',"
						+ "'"+this.getUsuario()+"')";				

				System.out.println(sql);
				int idComprador = db.execQuerySelectId(sql);			
				

				sql = "INSERT INTO COMPRADORES_BOLETOS (PK_COMPRADOR,PK_BOLETO) VALUES ("						
						+ "'"+idComprador+"',"
						+ "'"+this.getIdBoleto()+"')";			

				System.out.println(sql);
				db.execQuery(sql);			
								
				
				
				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db
							, ASIGNACION.COMPRADOR
							, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
							, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
							, '-'
							, this.getCosto(), this.getAbono()
							, 1
							, "-"
							, "VENTA"
							, "ins-comprador-1");
				}
				catch (Exception ex) { Factory.Error(ex, sql); }
			
			}//
			
		} catch (SQLException ex) { Factory.Error(ex, sql); }

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "registrarSoloComprador", "bol="+this.getBoleto()+", nom=" + this.getNombre().replace("'", "") + " "+this.getApellidos().replace("'", "")+", dir="+this.getCalle().replace("'", "")+" "+this.getNumero().replace("'", "")+","+this.getColonia().replace("'", ""));
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return result;
	}
	
	
	
	/*public void deleteComprador_Boleto(SesionDatos sesion){
		
		db.con();
		//String sql = "DELETE FROM COMPRADORES WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
		
		db.execQuery(sql);
		
		sql = "UPDATE BOLETOS SET ABONO = 0 , VENDIDO ='N', FECHA_M = GETDATE() WHERE SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND FOLIO="+ "'"+this.getBoleto()+"'";
		db.execQuery(sql);
		
		//ABONAMOS AL TALONARIO LA VENTA DEL BOLETO   
		setAbonoTalonario(sesion);
		
		
		//System.out.println("entra delete venta-->");
		
		// --- Se guarda un registro de seguimiento ---
		try {
			Seguimiento.guardaVenta(db
					, ASIGNACION.COMPRADOR
					, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
					, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
					, 'N'
					, this.getCosto(), 0
					, 1
					, "-"
					, "DELETE"
					, "venta-bols-1");
		}
		catch (Exception ex) { com.core.Factory.Error(ex, sql); }
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "deleteVenta",
					"tal="+this.getIdtalonario()+", bol="+this.getBoleto());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
	}*/
	
	
	
	public void deleteVenta(SesionDatos sesion){
		
		db.con();
		String sql = "DELETE FROM COMPRADORES WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
		db.execQuery(sql);
		
		sql = "UPDATE BOLETOS SET ABONO = 0 , VENDIDO ='N', FECHA_M = GETDATE() WHERE SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND FOLIO="+ "'"+this.getBoleto()+"'";
		db.execQuery(sql);
		
		//ABONAMOS AL TALONARIO LA VENTA DEL BOLETO   
		setAbonoTalonario(sesion);
		
		
		//System.out.println("entra delete venta-->");
		
		// --- Se guarda un registro de seguimiento ---
		try {
			Seguimiento.guardaVenta(db
					, ASIGNACION.COMPRADOR
					, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
					, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
					, 'N'
					, this.getCosto(), 0
					, 1
					, "-"
					, "DELETE"
					, "venta-bols-1");
		}
		catch (Exception ex) { com.core.Factory.Error(ex, sql); }
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "deleteVenta",
					"tal="+this.getIdtalonario()+", bol="+this.getBoleto());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	

	public void RegistrarIncidenciaBoleto(SesionDatos sesion) {		
		db.con();
		
		//String sql = "SELECT * FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
		
		String sql = "SELECT BI.* FROM BOLETOS B, BOLETOS_INCIDENCIAS BI WHERE BI.PK_BOLETO = B.PK1  AND BI.PK_BOLETO = '"+this.getIdBoleto()+"' AND B.PK_TALONARIO = '"+this.getIdtalonario()+"' ";//
		
		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				
				/*sql = "UPDATE BOLETOS_INCIDENCIAS SET FORMATO = '"+this.getFormatofc8()+"' ,INCIDENCIA = '"+this.getIncidencia()+"' ,"
						+ "FOLIOMP = '"+this.getFolioactamp()+"' ,"
						+ "DETALLES = '"+this.getDetallesincidencia()+"', "
						+ "USUARIO = '"+this.getUsuario()+"', "
						+ "FECHA_M = GETDATE() "
				 		+ "WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";*/
				
				
				
				sql = "UPDATE BOLETOS_INCIDENCIAS SET FORMATO = '"+this.getFormatofc8()+"' ,INCIDENCIA = '"+this.getIncidencia()+"' ,"
						+ "FOLIOMP = '"+this.getFolioactamp()+"' ,"
						+ "DETALLES = '"+this.getDetallesincidencia()+"', "
						+ "USUARIO = '"+this.getUsuario()+"', "
						+ "FECHA_M = GETDATE() "
				 		+ "WHERE PK_BOLETO ="+ "'"+this.getIdBoleto()+"'";
				
				
				db.execQuery(sql);
				
				sql = "UPDATE BOLETOS SET  INCIDENCIA = 1, FECHA_M = GETDATE()"
						+ " WHERE  PK_TALONARIO = '"+this.getIdtalonario()+"'"
						+ " AND FOLIO="+ "'"+this.getBoleto()+"'";
				db.execQuery(sql);
				
				
				// --- Se guarda un registro de seguimiento ---
					try {
						Seguimiento.guardaVenta(db
								, ASIGNACION.COMPRADOR
								, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
								, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
								, this.getIncidencia()
								, this.getCosto(), this.getAbono()
								, 1
								, "-"
								, "INCIDENCIA"
								, "venta-bols-1");
					}
					catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				
				
			}else{
				
				/*sql = "INSERT INTO BOLETOS_INCIDENCIAS (PK_SORTEO,PK_TALONARIO,TALONARIO,PK_BOLETO,BOLETO,FORMATO,INCIDENCIA,FOLIOMP,DETALLES,PK_SECTOR,PK_NICHO,PK_COLABORADOR,USUARIO) VALUES ("+this.getIdSorteo()+","
					+ "  "+this.getPktalonario()+","
					+ " '"+this.getIdtalonario()+"',"
					+ "  "+this.getIdBoleto()+","
					+ " '"+this.getBoleto()+"',"
					 
					+ "'"+this.getFormatofc8()+"',"
					+ "'"+this.getIncidencia()+"',"
					+ "'"+this.getFolioactamp()+"',"
					+ "'"+this.getDetallesincidencia()+"',"
					+ ""+this.getIdSector()+","
					+ ""+this.getIdNicho()+","
					+ ""+this.getIdColaborador()+","
					+ "'"+this.getUsuario()+"')";*/
				
				
				sql = "INSERT INTO BOLETOS_INCIDENCIAS (PK_BOLETO,FORMATO,INCIDENCIA,FOLIOMP,DETALLES,USUARIO) VALUES ("
				+"'"+this.getIdBoleto()+"',"				 
				+ "'"+this.getFormatofc8()+"',"
				+ "'"+this.getIncidencia()+"',"
				+ "'"+this.getFolioactamp()+"',"
				+ "'"+this.getDetallesincidencia()+"',"
				+ "'"+this.getUsuario()+"')";			
				db.execQuery(sql);
				
				//sql = "UPDATE BOLETOS SET  INCIDENCIA =1, FECHA_M = GETDATE() WHERE SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND FOLIO="+ "'"+this.getBoleto()+"'";
				sql = "UPDATE BOLETOS SET  INCIDENCIA =1, FECHA_M = GETDATE() WHERE  PK_TALONARIO = '"+this.getIdtalonario()+"' AND FOLIO="+ "'"+this.getBoleto()+"'";
				db.execQuery(sql);
				 
				 
				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db
							, ASIGNACION.COMPRADOR
							, Long.valueOf(this.getIdSorteo()),this.getIdSector(),this.getIdNicho(),this.getIdColaborador()
							, ASIGNACION.BOLETO, 0 , this.getIdBoleto()
							, this.getIncidencia()
							, this.getCosto(), this.getAbono()
							, 1
							, "-"
							, "INCIDENCIA"
							, "venta-bols-1");
				}
				catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this,"RegistrarIncidenciaBoleto", " tal="+this.getIdtalonario()+", bol="+this.getBoleto());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
	}


	public void deleteIncidenciaBoleto(SesionDatos sesion){
		
		
		//String sql = "DELETE FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND BOLETO="+ "'"+this.getBoleto()+"'";
		String sql = "DELETE FROM BOLETOS_INCIDENCIAS WHERE PK_BOLETO = '"+this.getIdBoleto()+"'";
		db.execQuery(sql);
		
		//sql = "UPDATE BOLETOS  SET INCIDENCIA = 'N', FECHA_M = GETDATE()  WHERE SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"' AND FOLIO="+ "'"+this.getBoleto()+"'";
		sql = "UPDATE BOLETOS  SET INCIDENCIA = 0, FECHA_M = GETDATE() "
				+ " WHERE PK_TALONARIO= '"+this.getIdtalonario()+"' "
				+ "AND PK1 ="+ "'"+this.getIdBoleto()+"'";
		
		System.out.println("deleteIncidenciaBoleto: "+sql);
		db.execQuery(sql);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "deleteIncidenciaBoleto",
					"tal="+this.getIdtalonario()+", bol="+this.getBoleto());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
    
    ////////////////////////////////////////END SEGUIMIENTO COMPRADORES BOLETOS////////////////////////////////////////
    
    
    
    
    //////////////////////////////////////SEGUIMIENTO MODAL BOLETOS TALONARIOS/////////////////////////////////////////
    
    
	public ResultSet BuscarBoletosTalonarios(){
		db.con();
		
		/*String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.RETORNADO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO, "
			+ " (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO', "
			+ " (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR', "
			+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR', "
			+ " (select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO', "
			+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO', "
			+ " (select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR', "
			+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR' "
			+ " FROM SORTEOS_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND  S.PK_SORTEO = "+this.getIdSorteo()+" AND TALONARIO = '"+this.getIdtalonario()+"'";*/
		
				
		String sql = "SELECT B.PK1 AS PK_BOLETO,B.PK_ESTADO AS VENDIDO,B.RETORNADO,B.ABONO,B.FOLIO,B.COSTO, B.FOLIO_TALONARIO AS TALONARIO, B.FORMATO, B.PK_SORTEO AS SORTEO, B.PK_TALONARIO,"
				+ " (select TOP 1 PK_SECTOR  from SECTORES_BOLETOS SB where SB.PK_BOLETO = B.PK1) AS 'PK_SECTOR', "
				+ "  (select TOP 1 SECTOR FROM SECTORES   WHERE PK1=(select TOP 1 PK_SECTOR  from SECTORES_BOLETOS  SB where SB.PK_BOLETO = B.PK1)) AS 'SECTOR',"
				+ " (select TOP 1 PK_NICHO  from NICHOS_BOLETOS NB where NB.PK_BOLETO = B.PK1) AS 'PK_NICHO',"
				+ " (select TOP 1 NICHO  FROM NICHOS  WHERE PK1=(select TOP 1 PK_NICHO    from NICHOS_BOLETOS NB where NB.PK_BOLETO = B.PK1)) AS 'NICHO', "
				+ "(select TOP 1 PK_COLABORADOR from COLABORADORES_BOLETOS CB where CB.PK_BOLETO = B.PK1) AS 'PK_COLABORADOR', "
				+ "(select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from COLABORADORES_BOLETOS CB where CB.PK_BOLETO = B.PK1)) AS 'COLABORADOR' "
				+ "FROM  VBOLETOS B WHERE B.PK_SORTEO = "+this.getIdSorteo()+" AND FOLIO_TALONARIO = '"+this.getIdtalonario()+"' ";

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	
	
	
	public ResultSet BuscarMontoAbonoTalonario(){
      	 db.con();
      	 String sql = "SELECT MONTO,ABONO,NUMBOLETOS FROM TALONARIOS WHERE SORTEO = "+this.getIdSorteo()+" AND FOLIO = '"+this.getIdtalonario()+"'";
       	 ResultSet rs = db.getDatos(sql);
   		 System.out.println(sql);
       	 return rs;
      	
      }
    
    
    /////////////////////////////////////END SEGUIMIENTO MODAL BOLETOS TALONARIOS/////////////////////////////////////
    
	public void setPredeterminadoSorteo(){
	//, PK_SECTOR = "+this.getIdSector()+",  PK_NICHO="+this.getIdNicho()+"
		
		String sql = "UPDATE USUARIOS SET PK_SORTEO = "+this.getIdSorteo()+" WHERE PK1 ="+this.getIdUsuario();
		db.execQuery(sql);
	}
    
    
	public void guardarRetornoBoletos(String[] arrBoletos, mBoletosSorteo obj, SesionDatos sesion){
		db.con();
		ArrayList<String> log_list_bols = new ArrayList<String>();
		
		String sql = "";		
		ResultSet rs = null;
		ResultSet retornado = null;
		for (String idboleto: arrBoletos)
		{
			try {			
				
				
				sql = "SELECT PK1,COSTO,PK_TALONARIO FROM BOLETOS WHERE  PK_ESTADO = 'V' AND PK1 = '"+idboleto+"'";//SORTEO = "+this.getIdSorteo()+" AND
				System.out.println(">>>SELECT BOLETOS Vendidos: "+sql);
				rs = db.getDatos(sql);
				
				
				if (rs.next()) {
					
						sql = "SELECT PK1,COSTO,PK_TALONARIO FROM BOLETOS WHERE PK_ESTADO = 'V' AND RETORNADO = 1  AND PK1 = '"+idboleto+"'  ";//SORTEO = "+this.getIdSorteo()+" AND
						System.out.println(">>>SELECT BOLETOS retornados: "+sql);
						retornado = db.getDatos(sql);
					
					
							   if(!retornado.next()){ 							
									
									sql = " UPDATE BOLETOS SET"
										+ " RETORNADO = 1, FECHA_M = GETDATE()"
										+ " WHERE PK1 = '"+idboleto+"'";//SORTEO = "+this.getIdSorteo()+" AND 
									//System.out.println(">>>UPDATE BOLETOS : "+sql);
									db.execQuery(sql);
									
									
									// --- Se guarda un registro de seguimiento ---
									try {
										Seguimiento.guardaVenta(db
												, ASIGNACION.VENTA
												, Long.valueOf(this.getIdSorteo()),0,0,0
												, ASIGNACION.BOLETO, Long.valueOf(rs.getInt("PK_TALONARIO")) , Long.valueOf(idboleto)
												, 'V'
												, rs.getDouble("COSTO"), rs.getDouble("COSTO")
												, 1
												, "-"
												, "RETORNADO"
												, "venta-bols-1"
												,sesion.nombreCompleto);
									}
									catch (Exception ex) { com.core.Factory.Error(ex, sql); }					
									
							
							     log_list_bols.add(idboleto);	
							
						   }	
					
					
			 
				}
				
			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
			 
		}
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.EDITADO, this, "guardarRetornoBoletos",
					"bols="+log_list_bols.toString());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	
	
	public void guardarRetornoBoletosTalonario(mBoletosSorteo obj, SesionDatos sesion){		
		db.con();		
		
		ArrayList<String> log_list_bols = new ArrayList<String>();		
		
	    String sql = "";  
	    //int count = 0;
	       
        sql = "SELECT PK1,COSTO,FOLIO FROM BOLETOS "
        + "WHERE SORTEO = "+sesion.pkSorteo+" AND TALONARIO = '"+obj.getIdtalonario()+"'";
    	 ResultSet boletos = null;
    			
    	 boletos = db.getDatos(sql); 	       		
	
	try {		
		
		
		while( boletos.next() ){	//CONDICION TODOS VENDIDOS(EN ESTE PUNTO YA TODOS ESTAN VENDIDOS)
			
			String sql2 = "SELECT PK1 FROM BOLETOS WHERE SORTEO = "+ sesion.pkSorteo+" AND VENDIDO = 'V' AND RETORNADO = 1 AND PK1 = '"+boletos.getInt("PK1")+"'  ";
			System.out.println(">>>SELECT BOLETO retonado: "+sql2);
			ResultSet boleto = db.getDatos(sql2);
			
			
			if(!boleto.next()){			
			
		
				
				 sql  = 
						"UPDATE BOLETOS SET "
					    + "RETORNADO = 1"				
						+ " WHERE SORTEO = "+obj.getIdSorteo()+" "			
						+ "AND TALONARIO = '"+obj.getIdtalonario()+"'"
						+ " AND PK1 = '" +  boletos.getInt("PK1")+ "'";
					
					
					System.out.println(">>>UPDATE BOLETOS : "+sql);
					db.execQuery(sql);			 
				
					
					//count +=1;
					 
						log_list_bols.add( boletos.getString("PK1"));
					 
					 
						// --- Se guarda un registro de seguimiento ---
						try {
							Seguimiento.guardaVenta(db     
									, ASIGNACION.VENTA
									, Long.valueOf(sesion.pkSorteo),0,0,0
									, ASIGNACION.BOLETO, Long.valueOf(obj.getIdtalonario()) ,  boletos.getInt("PK1")
									, 'V'
									, boletos.getDouble("COSTO"), boletos.getDouble("COSTO")
									, 1
									, "-"
									, "RETORNADO"
									, "retorno-bols-1"
									,sesion.nombreCompleto);
						}
						catch (Exception ex) { com.core.Factory.Error(ex, sql); }
			}			
		}
		
		
	} catch (Exception ex) { com.core.Factory.Error(ex, sql); }		
	
	
	//ABONAMOS AL TALONARIO LA VENTA DEL BOLETO   
    sql = "SELECT ISNULL(SUM(ABONO),0) AS TOTAL, ISNULL(SUM(COSTO),0) AS MONTO,(SELECT ACCION FROM SEGUIMIENTO WHERE TIPO = 'T' AND NUMERO_BOLETOS = 11 AND PK_TALONARIO = '"+obj.getIdtalonario()+"' AND ACCION = 'RETORNADOS' ) AS VALIDARETORNO FROM BOLETOS "
   + "WHERE SORTEO = "+sesion.pkSorteo+" AND TALONARIO = '"+obj.getIdtalonario()+"' AND VENDIDO = 'V' AND RETORNADO = 1";
   ResultSet rs = null;			
   rs = db.getDatos(sql); 
	
	// --- Se guarda un registro de seguimiento ---
	try {
		
		 if(rs.next()) {
			 
			 System.out.println(">>>valida ret : "+rs.getString("VALIDARETORNO"));			 
			
			 
				if( rs.getString("VALIDARETORNO") == null ){			 
					// if( !rs.getString("VALIDARETORNO").equals("RETORNADOS") ){
						 
						 
							 System.out.println(">>>entro seg tal : ");
							 
							 Seguimiento.guardaVenta(db
										, ASIGNACION.VENTA
										, Long.valueOf(sesion.pkSorteo),0,0,0
										, ASIGNACION.TALONARIO,  Long.valueOf(obj.getIdtalonario()) , 0
										, 'V'
										, rs.getDouble("MONTO"), rs.getDouble("MONTO")
										, 11
										, "-"
										, "RETORNADOS"
										, "retorno-tal-11"
										,sesion.nombreCompleto);//CREAR usuario
								
							
							
						//}			 
				}				 
		}
		
		
		
		
	}
	catch (Exception ex) { com.core.Factory.Error(ex, sql); }
	
			 
			 
			try {
				this.Log(sesion, LOG.EDITADO, this, "guardarRetornoBoletosTalonario",
						"bols="+log_list_bols.toString());
			} catch(Exception ex) { Factory.Error(ex, "Log"); } 		
			
     	
     }
	
	public void consultaBoleto() {
		String sql = "SELECT * FROM VBOLETOS_ASIGNACION WHERE PK1=" + getIdBoleto();
		
		try {
			ResultSet res = db.getDatos(sql);
			if (res != null && res.next()) {
				setPktalonario(res.getInt("PK_TALONARIO"));
				setIdtalonario(res.getInt("FOLIO_TALONARIO"));
				setBoleto(res.getString("FOLIO"));
				setIdSector(res.getInt("PK_SECTOR"));
				setIdNicho(res.getInt("PK_NICHO"));
				setIdColaborador(res.getInt("PK_COLABORADOR"));
				setCosto(res.getDouble("COSTO"));
			}
			
		}catch(SQLException ex) {}
	}
	

}





