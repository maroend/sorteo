package com.sorteo.talonarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.core.Parametros;
import com.core.ReadExcel;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mSubirBoletos extends SuperModel {
	private int idsorteo;
	private int idUsuario;
	private String usuario;
	
	private String folioBoleto;
	
	private String folioTalonario;
	private int idTalonario;
	
	private String formato;
	private double costo;
	
	public int contarBoletos;
	public int contarTalonarios;
	
	public mSubirBoletos() {
	}

	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
	}

	public String getFolioBoleto() {
		return folioBoleto;
	}

	public void setFolioBoleto(String folioBoleto) {
		this.folioBoleto = folioBoleto;
	}

	public String getFolioTalonario() {
		return folioTalonario;
	}

	public void setFolioTalonario(String folioTalonario) {
		this.folioTalonario = folioTalonario;
	}

	public int getIdTalonario() {
		return idTalonario;
	}

	public void setIdTalonario(int idTalonario) {
		this.idTalonario = idTalonario;
	}
	
	public void consultaUsuarioSorteo()
	{
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		try {
			while (rs.next()) {
				this.setIdsorteo(rs.getInt("PK_SORTEO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean borraBoletosDeSorteo()
	{
		String sql = " DELETE FROM COLABORADORES_BOLETOS"
				+ " DELETE FROM NICHOS_BOLETOS"
				+ " DELETE FROM SECTORES_BOLETOS"
				+ " DELETE FROM BOLETOS_INCIDENCIAS"
				+ " DELETE FROM COMPRADORES_BOLETOS"
				+ " DELETE FROM ORDEN_DETALLE"
				+ " DELETE FROM FORMATOS"
				+ " DELETE FROM BOLETOS"
				+ " DELETE FROM TALONARIOS";
				
		int res = db.execQuery(sql);
		return res != 0;
	}
	
	public void insert(ReadExcel re, SesionDatos sesion, String formato, String costo){

		this.setIdsorteo(sesion.pkSorteo);
		this.setFormato(formato);
		this.setCosto( Double.valueOf(costo) );
		this.setUsuario(sesion.nickName);
		sesion.data1 = 0;
		sesion.guardaSesion();
		
		//borraBoletosDeSorteo();
		
		int row = 0;
		//int max = re.matriz.length/400;
		int tanto = -1;
		//StringBuilder content = new StringBuilder();
		while (row < re.matriz.length) {
			
			row = agrupaTalonarios(row, re);
			
			// Cada 500
			if (tanto != row/500){
				tanto = row/500;
				sesion.data1 = (int)(100.0 * (row+1) / (double)re.matriz.length);
				sesion.guardaSesion();
			}
		}
		re.content = "";//content.toString();
		
		sesion.data1 = 100;
		sesion.guardaSesion();
	}

	private int agrupaTalonarios(int row, ReadExcel re) {
		ArrayList<String> folios = new ArrayList<String>();
		// El primer paso sera asignar el talonario predeterminado.
		this.setFolioTalonario(re.get(row, "Talonario"));

		while (row < re.matriz.length) {
			String talonario = re.get(row, "Talonario");
			// Al encontrar un talonario diferente termina el ciclo
			if (talonario.compareTo(getFolioTalonario()) != 0)
				break;
			folios.add(re.getString(row, "Folio Boleto"));

			row++;
		}

		insertaTalonariosYBoletos(folios);
		return row;
	}
	
	private RESULT insertaTalonariosYBoletos(ArrayList<String> folioBoletos) {
		ArrayList<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1", 0));
		list.add(new Parametros("FOLIO", getFolioTalonario()));
		list.add(new Parametros("PK_SORTEO", getIdsorteo()));
		list.add(new Parametros("FORMATO", getFormato()));
		list.add(new Parametros("ASIGNADO", 0));
		list.add(new Parametros("DIGITAL", 0));
		list.add(new Parametros("USUARIO", getUsuario()));
		list.add(new Parametros("LIST", "-"));
		list.add(new Parametros("StatementType", "Insert"));
		
		int pkTalonario = db.execStoreProcedureIntId("spTALONARIOS", list);
		
		//content.append(getFolioTalonario()).append("#&#");
		if (pkTalonario != -1) {
			String varios_folios = folioBoletos.stream().map(Object::toString).collect(Collectors.joining(","));
			contarTalonarios++;
			list.clear();
			list.add(new Parametros("PK1", 0));
			list.add(new Parametros("FOLIO", "---"));
			list.add(new Parametros("FOLIODIGITAL"));
			list.add(new Parametros("PK_TALONARIO", pkTalonario));
			list.add(new Parametros("COSTO", getCosto()));
			list.add(new Parametros("FORMATO", getFormato()));
			list.add(new Parametros("ASIGNADO", 0));
			list.add(new Parametros("PK_ESTADO", "N"));
			list.add(new Parametros("ABONO", 0));
			list.add(new Parametros("RETORNADO", 0));
			list.add(new Parametros("INCIDENCIA", 0));
			list.add(new Parametros("USUARIO", getUsuario()));

			list.add(new Parametros("LIST", varios_folios));
			list.add(new Parametros("StatementType", "InsertMultiple"));
			
			int procesados = db.execStoreProcedureIntId("spBOLETOS", list);
			
			if (0 < procesados)
				this.contarBoletos += procesados;
			
			
			// Si el numero de procesados es igual al numero de folios ... ok
			if (procesados == folioBoletos.size())
				return RESULT.OK;
			
		}
		
		return RESULT.OK;
		/*
		String sql = "INSERT INTO TALONARIOS "
				+ " (FOLIO,NUMBOLETOS,SORTEO,FORMATO,MONTO,USUARIO,ASIGNADO,VENDIDO,ABONO)  VALUES("
				+ "'" + getFolioTalonario() + "',"
				+ folioBoletos.size() + ","
				+ getIdsorteo() + ","
				+ "'" + getFormato() + "',"
				+ (getCosto() * folioBoletos.size()) + ","
				+ "'" + getUsuario() + "',"
				+ "0, 'N', 0.0)"
				;
		//System.out.println("TAL -> " + sql);
		int pkTalonario = db.execQuerySelectId(sql);
		*/
		/*
		re.content += getFolioTalonario() + "#&#";
		if (0 < pkTalonario)
		{
			contarTalonarios++;
			for (String folio : folioBoletos)
			{
				String sql = " INSERT INTO BOLETOS "
						+ " (FOLIO,COSTO,SORTEO,FORMATO,USUARIO,ASIGNADO,VENDIDO,ABONO,PK_TALONARIO,TALONARIO,RETORNADO,INCIDENCIA) VALUES("
						+ "'" + folio + "',"
						+ getCosto() + ","
						+ getIdsorteo() + ","
						+ "'" + getFormato() + "',"
						+ "'" + getUsuario() + "',"
						+ "0, 'N', 0.0, "
						+ pkTalonario + ","
						+ getFolioTalonario()
						+ ", 0, 'N')"
						;
				//System.out.println("BOL -> " + sql);
				int res = db.execQuery(sql);
				if (res!=0)
					contarBoletos++;
					//re.content += folio + "#|#";
				else{
					;//re.content += "#~#";
					return -1;
				}
			}
		}
		//re.content += "#~#";
		return 0;
		*/
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

}


