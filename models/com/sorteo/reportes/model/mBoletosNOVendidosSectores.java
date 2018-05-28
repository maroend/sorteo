package com.sorteo.reportes.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.core.Seguimiento;
import com.core.SuperModel;
import com.core.Seguimiento.ASIGNACION;

public class mBoletosNOVendidosSectores extends SuperModel {

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

	public int getIcolaborador() {
		return icolaborador;
	}

	public void setIcolaborador(int icolaborador) {
		this.icolaborador = icolaborador;
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

	public int getPktalonario() {
		return pktalonario;
	}

	public void setPktalonario(int pktalonario) {
		this.pktalonario = pktalonario;
	}

	public int getIdtalonario() {
		return idtalonario;
	}

	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}

	private int idUsuario;

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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

	public void setNumBoletosParcialmenteVendidos(
			int numBoletosParcialmenteVendidos) {
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

	public mBoletosNOVendidosSectores() {
		// TODO Auto-generated constructor stub
	}

	public void getUsuarioSorteo() {

		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = "
				+ this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);

		try {
			while (rs.next()) {
				this.setIdSorteo(rs.getInt("PK_SORTEO"));
				this.setIdsector(rs.getInt("PK_SECTOR"));
				this.setIdnicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int contar(String search, int idsorteo, int idsector) {

		String sql = "SELECT B.PK1";
		sql += " FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"
				+ idsorteo
				+ "' AND B.VENDIDO ='N' AND SB.PK_SECTOR = '"
				+ idsector + "'";

		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '"
					+ search + "'))   ";
		}

		int numero = db.ContarFilas(sql);
		System.out.println(">>>>SQL BOL:" + sql);

		return numero;
	}

	public ResultSet paginacion(int pg, int numreg, String search,
			int idsorteo, int idsector) {
		// String sql =
		// "SELECT  B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO ";
		// sql +=
		// "FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+idsorteo+"'";

		String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO,B.INCIDENCIA, "
				+ " (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
				+ " (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
				+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
				+ "(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
				+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
				+ "(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
				+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
				+ "FROM SORTEOS_SECTORES_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND S.PK_SORTEO = '"
				+ idsorteo
				+ "' AND S.PK_SECTOR = '"
				+ idsector
				+ "'AND B.VENDIDO='N'";

		if (search != "") {
			sql += " AND ((B.FOLIO = '" + search + "') OR (B.TALONARIO = '"
					+ search + "'))   ";
		}

		sql += "ORDER BY CAST(B.TALONARIO AS INT) ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println(sql);
		ResultSet rs = db.getDatos(sql);

		return rs;
	}

	public void Sorteo() {

		db.con();
		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + this.getIdSorteo()
				+ "";

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					// this.setClave(rs.getString("CLAVE"));
					this.setSorteo(rs.getString("SORTEO"));
					// this.setDescripcion(rs.getString("DESCRIPCION"));

				}

				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getBoletosTalonariosColaborador() {

		db.con();
		String sql = "";
		String total = "";
		ResultSet rs = null;

		// TOTAL TALONARIOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= " + this.getIcolaborador() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				} /* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdsector()
				+ "' AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= "
				+ this.getIcolaborador()
				+ " AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS ST, TALONARIOS T "
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'P' AND ST.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND ST.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= " + this.getIcolaborador() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_TALONARIOS ST, TALONARIOS T "
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'V' AND ST.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND ST.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= " + this.getIcolaborador() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL DE BOLETOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdsector()
				+ "' AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= " + this.getIcolaborador() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "'  AND PK_SECTOR = '"
				+ this.getIdsector()
				+ "' AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= "
				+ this.getIcolaborador()
				+ " AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
				+ " WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'P' AND SB.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND SB.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND SB.PK_NICHO = "
				+ this.getIdnicho()
				+ " AND SB.PK_COLABORADOR= "
				+ this.getIcolaborador() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
				+ " WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'V' AND SB.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND SB.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND SB.PK_NICHO = "
				+ this.getIdnicho()
				+ " AND SB.PK_COLABORADOR= "
				+ this.getIcolaborador() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS EXTRAVIADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= "
				+ this.getIcolaborador()
				+ " AND INCIDENCIA = 'E'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ROBADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND PK_COLABORADOR= "
				+ this.getIcolaborador()
				+ " AND INCIDENCIA = 'R'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;

	}

	public String getBoletosTalonariosNicho() {

		db.con();
		String sql = "";
		String total = "";
		ResultSet rs = null;

		// TOTAL TALONARIOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = " + this.getIdnicho() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				} /* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdsector()
				+ "' AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS ST, TALONARIOS T "
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'P' AND ST.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND ST.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS ST, TALONARIOS T "
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'V' AND ST.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND ST.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL DE BOLETOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdsector()
				+ "' AND PK_NICHO = "
				+ this.getIdnicho()
				+ "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "'  AND PK_SECTOR = '"
				+ this.getIdsector()
				+ "' AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B "
				+ " WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'P' AND SB.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND SB.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND SB.PK_NICHO = "
				+ this.getIdnicho() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B "
				+ " WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'V' AND SB.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND SB.PK_SECTOR = "
				+ this.getIdsector()
				+ " AND SB.PK_NICHO = "
				+ this.getIdnicho() + "";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS EXTRAVIADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND INCIDENCIA = 'E'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ROBADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND PK_NICHO = "
				+ this.getIdnicho()
				+ " AND INCIDENCIA = 'R'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;

	}

	public String getBoletosTalonariosSector() {

		db.con();
		String sql = "";
		String total = "";
		ResultSet rs = null;

		// TOTAL TALONARIOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdsector() + "'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				} /* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdsector() + "' AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS ST, TALONARIOS T "
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'P' AND ST.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND ST.PK_SECTOR = "
				+ this.getIdsector() + " ";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS ST, TALONARIOS T "
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'V' AND ST.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND ST.PK_SECTOR = "
				+ this.getIdsector() + " ";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL DE BOLETOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdsector() + "'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo()
				+ "'  AND PK_SECTOR = '"
				+ this.getIdsector() + "' AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B "
				+ " WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'P' AND SB.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND SB.PK_SECTOR = "
				+ this.getIdsector() + " ";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B "
				+ " WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'V' AND SB.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND SB.PK_SECTOR = "
				+ this.getIdsector() + " ";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS EXTRAVIADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND INCIDENCIA = 'E'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ROBADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = "
				+ this.getIdsector()
				+ " AND INCIDENCIA = 'R'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS ST, TALONARIOS T "
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND T.VENDIDO = 'N' AND ST.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND ST.PK_SECTOR = "
				+ this.getIdsector() + " ";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B "
				+ " WHERE SB.PK_BOLETO = B.PK1 AND B.VENDIDO = 'N' AND SB.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND SB.PK_SECTOR = "
				+ this.getIdsector() + " ";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;

	}

	public String getBoletosTalonarios() {

		db.con();
		String sql = "";
		String total = "";
		ResultSet rs = null;

		// TOTAL TALONARIOS
		sql = "SELECT COUNT(*) AS TOTAL FROM TALONARIOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				} /* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_TALONARIOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo() + "' AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM TALONARIOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "' AND VENDIDO = 'P'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM TALONARIOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "' AND VENDIDO = 'V'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL DE BOLETOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ASIGNADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_BOLETOS WHERE PK_SORTEO = '"
				+ this.getIdSorteo() + "' AND ASIGNADO = 1";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS PARCIALMENTE VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "' AND VENDIDO = 'P'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "' AND VENDIDO = 'V'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS EXTRAVIADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE INCIDENCIA = 'E'  AND PK_SORTEO = '"
				+ this.getIdSorteo() + "'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS ROBADOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS_INCIDENCIAS WHERE INCIDENCIA = 'R'  AND PK_SORTEO = '"
				+ this.getIdSorteo() + "'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TALONARIOS NO VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM TALONARIOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "' AND VENDIDO = 'N'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// TOTAL BOLETOS NO VENDIDOS
		sql = "SELECT COUNT(*) AS TOTAL FROM BOLETOS WHERE SORTEO = '"
				+ this.getIdSorteo() + "' AND VENDIDO = 'N'";
		rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total += rs.getString("TOTAL") + "|";
				}/* rs.close(); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;

	}

	// //////////////////////////////////SEGUIMIENTO COMPRADORES
	// BOLETOS/////////////////////////////

	public ResultSet getComprador() {

		db.con();
		String sql = "SELECT * FROM COMPRADORES WHERE PK_SORTEO="
				+ this.getIdSorteo() + " AND BOLETO = '" + this.getBoleto()
				+ "'";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public ResultSet GetIncidenciaBoleto() {
		db.con();
		String sql = "SELECT * FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo() + " AND TALONARIO = '"
				+ this.getIdtalonario() + "' AND BOLETO=" + "'"
				+ this.getBoleto() + "'";
		ResultSet rs = db.getDatos(sql);
		System.out.println(sql);
		return rs;

	}

	public void setAbonoTalonario() {

		ResultSet rs = null;
		db.con();
		char estadotalonario = 'N';

		String sql = "SELECT ISNULL(SUM(ABONO),0) AS TOTAL, ISNULL(SUM(COSTO),0) AS MONTO FROM BOLETOS WHERE SORTEO = "
				+ this.getIdSorteo()
				+ " AND TALONARIO = '"
				+ this.getIdtalonario() + "'";

		rs = db.getDatos(sql);
		try {
			if (rs.next()) {

				System.out.println(rs.getDouble("TOTAL"));
				System.out.println("MONTO TALONARIO:" + rs.getDouble("MONTO"));

				if (rs.getDouble("MONTO") == rs.getDouble("TOTAL")) {
					estadotalonario = 'V';
				}
				if (rs.getDouble("MONTO") > rs.getDouble("TOTAL")) {
					estadotalonario = 'P';
				}
				if (rs.getDouble("TOTAL") == 0) {
					estadotalonario = 'N';
				}

				sql = "UPDATE TALONARIOS SET ABONO = " + rs.getDouble("TOTAL")
						+ " ," + "VENDIDO = '" + estadotalonario + "' ,"
						+ "USUARIO = '" + this.getUsuario() + "' "
						+ "WHERE SORTEO = " + this.getIdSorteo()
						+ " AND FOLIO = '" + this.getIdtalonario() + "'";

				db.execQuery(sql);

				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db, ASIGNACION.COMPRADOR,
							Long.valueOf(this.getIdSorteo()),
							this.getIdsector(), this.getIdnicho(),
							this.getIcolaborador(), ASIGNACION.TALONARIO,
							this.getPktalonario(), 0, estadotalonario,
							rs.getDouble("MONTO"), rs.getDouble("TOTAL"), 11,
							"-", "ABONO", "venta-bols-1");
				} catch (Exception ex) {
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setComprador() {

		db.con();
		String sql = "SELECT * FROM COMPRADORES WHERE PK_SORTEO = "
				+ this.getIdSorteo() + " AND TALONARIO = '"
				+ this.getIdtalonario() + "' AND BOLETO=" + "'"
				+ this.getBoleto() + "'";
		ResultSet rs = db.getDatos(sql);
		char etatusventa = 'N';

		try {
			if (rs.next()) {

				sql = "UPDATE COMPRADORES SET ABONO = " + this.getAbono()
						+ " ,NOMBRE = '" + this.getNombre() + "' ,"
						+ "APELLIDOS = '" + this.getApellidos() + "' ,"
						+ "TELEFONOF = '" + this.getTelefonof() + "' ,"
						+ "TELEFONOM = '" + this.getTelefonom() + "' ,"
						+ "CORREO = '" + this.getCorreo() + "' ," + "CALLE = '"
						+ this.getCalle() + "' ," + "NUMERO = '"
						+ this.getNumero() + "' ," + "COLONIA = '"
						+ this.getColonia() + "' ," + "ESTADO = '"
						+ this.getEstado() + "' ," + "MUNDEL = '"
						+ this.getMunicipio() + "' ," + "USUARIO = '"
						+ this.getUsuario() + "' " + "WHERE PK_SORTEO = "
						+ this.getIdSorteo() + " AND TALONARIO = '"
						+ this.getIdtalonario() + "' AND BOLETO=" + "'"
						+ this.getBoleto() + "'";

				db.execQuery(sql);

				if (this.getAbono() < this.getCosto()) {
					etatusventa = 'P';
				}
				if (this.getAbono() == this.getCosto()) {
					etatusventa = 'V';
				}
				if (this.getAbono() == 0) {
					etatusventa = 'N';
				}

				sql = "UPDATE BOLETOS SET ABONO = " + this.getAbono()
						+ ", VENDIDO = '" + etatusventa + "'  WHERE SORTEO = "
						+ this.getIdSorteo() + " AND TALONARIO = '"
						+ this.getIdtalonario() + "' AND FOLIO=" + "'"
						+ this.getBoleto() + "'";
				db.execQuery(sql);

				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db, ASIGNACION.COMPRADOR,
							Long.valueOf(this.getIdSorteo()),
							this.getIdsector(), this.getIdnicho(),
							this.getIcolaborador(), ASIGNACION.BOLETO, 0,
							this.getIdBoleto(), etatusventa, this.getCosto(),
							this.getAbono(), 1, "-", "VENTA", "venta-bols-1");
				} catch (Exception ex) {
				}

			} else {

				sql = "INSERT INTO COMPRADORES (PK_SORTEO,PK_TALONARIO,TALONARIO,PK_BOLETO,BOLETO,ABONO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,NOMBRE,APELLIDOS,TELEFONOF,TELEFONOM,CORREO,CALLE,NUMERO,COLONIA,ESTADO,MUNDEL,USUARIO) VALUES ("
						+ this.getIdSorteo()
						+ ","
						+ this.getPktalonario()
						+ ",'"
						+ this.getIdtalonario()
						+ "','"
						+ this.getIdBoleto()
						+ "','"
						+ this.getBoleto()
						+ "',"
						+ this.getAbono()
						+ ","
						+ this.getIdsector()
						+ ","
						+ this.getIdnicho()
						+ ","
						+ this.getIcolaborador()
						+ ",'"
						+ this.getNombre()
						+ "','"
						+ this.getApellidos()
						+ "','"
						+ this.getTelefonof()
						+ "','"
						+ this.getTelefonom()
						+ "','"
						+ this.getCorreo()
						+ "','"
						+ this.getCalle()
						+ "','"
						+ this.getNumero()
						+ "','"
						+ this.getColonia()
						+ "','"
						+ this.getEstado()
						+ "','"
						+ this.getMunicipio()
						+ "','"
						+ this.getUsuario()
						+ "')";
				db.execQuery(sql);

				if (this.getAbono() < this.getCosto()) {
					etatusventa = 'P';
				}
				if (this.getAbono() == this.getCosto()) {
					etatusventa = 'V';
				}
				if (this.getAbono() == 0) {
					etatusventa = 'N';
				}

				sql = "UPDATE BOLETOS SET ABONO = " + this.getAbono()
						+ " , VENDIDO = '" + etatusventa + "' WHERE SORTEO = "
						+ this.getIdSorteo() + " AND TALONARIO = '"
						+ this.getIdtalonario() + "' AND FOLIO=" + "'"
						+ this.getBoleto() + "'";
				db.execQuery(sql);

				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db, ASIGNACION.COMPRADOR,
							Long.valueOf(this.getIdSorteo()),
							this.getIdsector(), this.getIdnicho(),
							this.getIcolaborador(), ASIGNACION.BOLETO, 0,
							this.getIdBoleto(), etatusventa, this.getCosto(),
							this.getAbono(), 1, "-", "VENTA", "venta-bols-1");
				} catch (Exception ex) {
				}

			}

			// ABONAMOS AL TALONARIO LA VENTA DEL BOLETO
			setAbonoTalonario();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		db.desconectar();

	}

	public void deleteVenta() {

		db.con();
		String sql = "DELETE FROM COMPRADORES WHERE PK_SORTEO = "
				+ this.getIdSorteo() + " AND TALONARIO = '"
				+ this.getIdtalonario() + "' AND BOLETO=" + "'"
				+ this.getBoleto() + "'";
		db.execQuery(sql);

		sql = "UPDATE BOLETOS SET ABONO = 0 , VENDIDO ='N' WHERE SORTEO = "
				+ this.getIdSorteo() + " AND TALONARIO = '"
				+ this.getIdtalonario() + "' AND FOLIO=" + "'"
				+ this.getBoleto() + "'";
		db.execQuery(sql);

		// ABONAMOS AL TALONARIO LA VENTA DEL BOLETO
		setAbonoTalonario();

		System.out.println("entra delete venta-->");

		// --- Se guarda un registro de seguimiento ---
		try {
			Seguimiento.guardaVenta(db, ASIGNACION.COMPRADOR,
					Long.valueOf(this.getIdSorteo()), this.getIdsector(),
					this.getIdnicho(), this.getIcolaborador(),
					ASIGNACION.BOLETO, 0, this.getIdBoleto(), 'N',
					this.getCosto(), 0, 1, "-", "DELETE", "venta-bols-1");
		} catch (Exception ex) { }
	}

	public void RegistrarIncidenciaBoleto() {

		db.con();

		String sql = "SELECT * FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo() + " AND TALONARIO = '"
				+ this.getIdtalonario() + "' AND BOLETO=" + "'"
				+ this.getBoleto() + "'";
		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				sql = "UPDATE BOLETOS_INCIDENCIAS SET FORMATO = '"
						+ this.getFormatofc8() + "' ,INCIDENCIA = '"
						+ this.getIncidencia() + "' ," + "FOLIOMP = '"
						+ this.getFolioactamp() + "' ," + "DETALLES = '"
						+ this.getDetallesincidencia() + "' ," + "USUARIO = '"
						+ this.getUsuario() + "' " + "WHERE PK_SORTEO = "
						+ this.getIdSorteo() + " AND TALONARIO = '"
						+ this.getIdtalonario() + "' AND BOLETO=" + "'"
						+ this.getBoleto() + "'";

				db.execQuery(sql);

				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db, ASIGNACION.COMPRADOR,
							Long.valueOf(this.getIdSorteo()),
							this.getIdsector(), this.getIdnicho(),
							this.getIcolaborador(), ASIGNACION.BOLETO, 0,
							this.getIdBoleto(), this.getIncidencia(),
							this.getCosto(), this.getAbono(), 1, "-",
							"INCIDENCIA", "venta-bols-1");
				} catch (Exception ex) {
				}

			} else {

				sql = "INSERT INTO BOLETOS_INCIDENCIAS (PK_SORTEO,PK_TALONARIO,TALONARIO,PK_BOLETO,BOLETO,FORMATO,INCIDENCIA,FOLIOMP,DETALLES,PK_SECTOR,PK_NICHO,PK_COLABORADOR,USUARIO) VALUES ("
						+ this.getIdSorteo()
						+ ","
						+ this.getPktalonario()
						+ ",'"
						+ this.getIdtalonario()
						+ "',"
						+ this.getIdBoleto()
						+ ",'"
						+ this.getBoleto()
						+ "','"
						+ this.getFormatofc8()
						+ "','"
						+ this.getIncidencia()
						+ "','"
						+ this.getFolioactamp()
						+ "','"
						+ this.getDetallesincidencia()
						+ "',"
						+ this.getIdsector()
						+ ","
						+ this.getIdnicho()
						+ ","
						+ this.getIcolaborador()
						+ ",'"
						+ this.getUsuario() + "')";

				db.execQuery(sql);

				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db, ASIGNACION.COMPRADOR,
							Long.valueOf(this.getIdSorteo()),
							this.getIdsector(), this.getIdnicho(),
							this.getIcolaborador(), ASIGNACION.BOLETO, 0,
							this.getIdBoleto(), 'R', this.getCosto(),
							this.getAbono(), 1, "-", "INCIDENCIA",
							"venta-bols-1");
				} catch (Exception ex) {
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteIncidenciaBoleto() {

		db.con();
		String sql = "DELETE FROM BOLETOS_INCIDENCIAS WHERE PK_SORTEO = "
				+ this.getIdSorteo() + " AND TALONARIO = '"
				+ this.getIdtalonario() + "' AND BOLETO=" + "'"
				+ this.getBoleto() + "'";
		db.execQuery(sql);

	}

	// //////////////////////////////////////END SEGUIMIENTO COMPRADORES
	// BOLETOS////////////////////////////////////////

	// ////////////////////////////////////SEGUIMIENTO MODAL BOLETOS
	// TALONARIOS/////////////////////////////////////////

	public ResultSet BuscarBoletosTalonarios() {
		db.con();

		String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO, "
				+ " (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO', "
				+ " (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR', "
				+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR', "
				+ " (select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO', "
				+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO', "
				+ " (select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR', "
				+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR' "
				+ " FROM SORTEOS_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND  S.PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND TALONARIO = '"
				+ this.getIdtalonario() + "'";

		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public ResultSet BuscarMontoAbonoTalonario() {
		db.con();
		String sql = "SELECT MONTO,ABONO,NUMBOLETOS FROM TALONARIOS WHERE SORTEO = "
				+ this.getIdSorteo()
				+ " AND FOLIO = '"
				+ this.getIdtalonario()
				+ "'";
		ResultSet rs = db.getDatos(sql);
		System.out.println(sql);
		return rs;

	}

	// ///////////////////////////////////END SEGUIMIENTO MODAL BOLETOS
	// TALONARIOS/////////////////////////////////////

	public String Sector() {

		db.con();
		String sql = "SELECT * FROM SECTORES WHERE PK1 = " + this.getIdsector()
				+ "";
		String sector = "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					sector = rs.getString("CLAVE") + " - "
							+ rs.getString("SECTOR");
				}
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sector;
	}

}
