package com.sorteo.dashboard.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.core.SuperModel;

public class mMapa extends SuperModel {
	
	public static class Estado {
		public String clave;
		public String idSepomex;
		public Estado(String clave, String idSepomex) {
			this.clave = clave;
			this.idSepomex = idSepomex;
		}

		public static Estado buscaXClave(String clave/*, Estado[] estados*/) {
			for (int i = 0; i < estados.length; i++) {
				if (estados[i].clave.equals(clave))
					return estados[i];
			}
			return null;
		}

		public static Estado buscaXIdSepomex(String idSepomex/*, Estado[] estados*/) {
			for (int i = 0; i < estados.length; i++) {
				if (estados[i].idSepomex.equals(idSepomex))
					return estados[i];
			}
			return null;
		}
		
		public static Estado[] estados = new Estado[] { 
			new Estado("mx-ag", "01"),
			new Estado("mx-bc", "02"),
			new Estado("mx-bs", "03"),
			new Estado("mx-cm", "04"),
			new Estado("mx-co", "05"),
			new Estado("mx-cl", "06"),
			new Estado("mx-cs", "07"),
			new Estado("mx-ch", "08"),
			new Estado("mx-df", "09"),
			new Estado("mx-dg", "10"),
			new Estado("mx-gj", "11"),
			new Estado("mx-gr", "12"),
			new Estado("mx-hg", "13"),
			new Estado("mx-ja", "14"),
			new Estado("mx-mx", "15"),
			new Estado("mx-mi", "16"),
			new Estado("mx-mo", "17"),
			new Estado("mx-na", "18"),
			new Estado("mx-nl", "19"),
			new Estado("mx-oa", "20"),
			new Estado("mx-pu", "21"),
			new Estado("mx-qt", "22"),
			new Estado("mx-qr", "23"),
			new Estado("mx-sl", "24"),
			new Estado("mx-si", "25"),
			new Estado("mx-so", "26"),
			new Estado("mx-tb", "27"),
			new Estado("mx-tm", "28"),
			new Estado("mx-tl", "29"),
			new Estado("mx-ve", "30"),
			new Estado("mx-yu", "31"),
			new Estado("mx-za", "32"),
		};
	}


	
	public mMapa() {
		// TODO Auto-generated constructor stub
	}
	
	public HashMap<String, Integer> consultaBoletosVendidos() {

		String sql
			= "SELECT *, (\n"
			+ "	SELECT COUNT(*) FROM COMPRADORES COM\n"
			+ "	INNER JOIN COMPRADORES_BOLETOS CB ON CB.PK_COMPRADOR=COM.PK1\n"
			+ "	INNER JOIN BOLETOS B ON B.PK1=CB.PK_BOLETO WHERE (B.PK_ESTADO='P' OR B.PK_ESTADO='V') AND COM.ESTADO=d_estado\n"
			+ ") AS 'BOLETOS'\n"
			+ "FROM(\n"
			+ "	SELECT DISTINCT(c_estado), d_estado FROM SEPOMEX\n"
			+ ") AS S\n"
			+ "ORDER BY BOLETOS, d_estado\n";
		
		System.out.println("MAPA: " + sql);
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		try {
			ResultSet rs = db.getDatos(sql);
			while (rs.next()) {
				map.put(rs.getString("c_estado"), rs.getInt("BOLETOS"));
			}
		}
		catch (SQLException ex) { }
		
		return map;
	/*
	var data = [
	            ['mx-3622', 0],
	            ['mx-bc', 1],
	            ['mx-bs', 2],
	            ['mx-gj', 30],
	            ['mx-sl', 31],
	            ['mx-hg', 32]
	        ];
		/**/
	}
}




