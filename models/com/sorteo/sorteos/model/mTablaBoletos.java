package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.core.SuperModel;

public class mTablaBoletos {
	
	public int pkBoleto;
	public String folio;
	public String costo;
	public String abono;
	public String vendido;
	public String incidencia;
	public int retornado;
	
	public long pkTalonario;
	public long talonario;
	public String talonarioVendido;
	public boolean electronico;
	
	public long PK_SECTOR;
	public long PK_NICHO;
	public long PK_COLABORADOR;
	public String SECTOR;
	public String NICHO;
	public String COLABORADOR;

	public mTablaBoletos() {
		PK_SECTOR = 0;
		PK_NICHO = 0;
		PK_COLABORADOR = 0;
		SECTOR = "";
		NICHO = "";
		COLABORADOR = "";
	}

	public static ArrayList<mTablaBoletos> load(ResultSet rs, SuperModel model) throws SQLException {
		ArrayList<mTablaBoletos> list = new ArrayList<mTablaBoletos>();
		ArrayList<Long> listCols = new ArrayList<Long>();
		ArrayList<String> listCondition = new ArrayList<String>();
		while (rs.next())
		{
			mTablaBoletos boleto = new mTablaBoletos();
			
			boleto.pkBoleto = rs.getInt("PK_BOLETO");
			boleto.folio = rs.getString("FOLIO");
			boleto.vendido = rs.getString("VENDIDO");
			boleto.retornado = rs.getInt("RETORNADO");
			boleto.pkTalonario = rs.getLong("PK_TALONARIO");
			boleto.talonario = rs.getLong("TALONARIO");
			boleto.talonarioVendido = rs.getString("VENDIDOTALONARIO");
			boleto.abono = rs.getString("ABONO");
			boleto.costo = rs.getString("COSTO");
			boleto.incidencia=rs.getString("INCIDENCIA");
			boleto.electronico = rs.getInt("DIGITAL") == 1;
			
			boleto.PK_COLABORADOR = rs.getLong("PK_COLABORADOR");
			
			if (listCols.contains(boleto.PK_COLABORADOR) == false){
				listCols.add(boleto.PK_COLABORADOR);
				listCondition.add("R.PK_BOLETO="+boleto.pkBoleto);
			}
			list.add(boleto);
		}
		
		String condition = listCondition.stream().map(Object::toString)
				.collect(Collectors.joining(" OR "));
		
		String sql
			="SELECT R.PK_COLABORADOR,R.PK_NICHO,R.PK_SECTOR, C.NOMBRE,C.APATERNO,C.AMATERNO, N.NICHO,S.SECTOR \n"
			+ " FROM SORTEOS_COLABORADORES_BOLETOS R, COLABORADORES C, NICHOS N, SECTORES S \n"
			+ " WHERE R.PK_COLABORADOR=C.PK1 AND R.PK_NICHO=N.PK1 AND R.PK_SECTOR=S.PK1 AND ("+condition+") \n";

		model.db.getDatos(sql);
		

		return list;
	}
}





