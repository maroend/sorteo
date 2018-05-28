package com.core;

public class Seguimiento {

	public enum ASIGNACION {
		BOVEDA,
		SORTEO,
		SECTOR,
		NICHO,
		COLABORADOR,
		COMPRADOR,
		VENTA,
		
		TALONARIO,
		BOLETO;
		
		public String toString() {
			switch(this) {
				// Niveles
				case BOVEDA: return "Boveda";
				case SORTEO: return "Sorteo";
				case SECTOR: return "Sector";
				case NICHO: return "Nicho";
				case COLABORADOR: return "Colaborador";
				case COMPRADOR: return "Comprador";
                
				//ACCION
				case VENTA: return "Venta";
				
				
				// Objetos
				case TALONARIO: return "Talonario";
				case BOLETO: return "Boleto";
				default: return "";
			}
		}
	}
	
	private Seguimiento() { }
	
	public static void guardaAsignacion(
			Database db, 
			ASIGNACION nivel, long idSorteo, long idSector, long idNicho, long idColaborador,
			ASIGNACION objeto, long idTalonario, long idBoleto,
			char status, double costo, double abono, int num_boletos, String formato, String detalle) {

		// Paso 2) guarda un registro
		String sql =
				"INSERT SEGUIMIENTO (TIPO,PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,PK_TALONARIO,PK_BOLETO,"
				+ " ESTATUS,COSTO,ABONO,NUMERO_BOLETOS,FORMATO,ACCION,NIVEL,DETALLE)"
				+ " VALUES ("
				+ (objeto == ASIGNACION.BOLETO ? "'B'" : "'T'") + ","
				+ idSorteo + ","
				+ idSector + ","
				+ idNicho + ","
				+ idColaborador + ","
				+ idTalonario + ","
				+ idBoleto + ",'"

				+ status + "',"
				+ costo + ","
				+ abono + ","
				+ num_boletos + ",'"
				+ formato + "',"
				+ "'Asignacion','"
				+ nivel.toString() + "','"
				+ detalle + "'"
				+ ")";
		//System.out.println("  --- SEG:  "+ sql);
		db.execQuery(sql);
	}
	
	
	public static void guardaVenta(
			Database db, 
			ASIGNACION nivel, long idSorteo, long idSector, long idNicho, long idColaborador,
			ASIGNACION objeto, long idTalonario, long idBoleto,
			char status, double costo, double abono, int num_boletos, String formato, String accion, String detalle) {

		// Paso 2) guarda un registro
		String sql =
				"INSERT SEGUIMIENTO (TIPO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,PK_TALONARIO,PK_BOLETO,"
				+ " PK_ESTADO,COSTO,ABONO,FORMATO,ACCION,NIVEL,DETALLE)"
				+ " VALUES ("
				+ (objeto == ASIGNACION.BOLETO ? "'B'" : "'T'") + ","
			//	+ idSorteo + ","
				+ idSector + ","
				+ idNicho + ","
				+ idColaborador + ","
				+ idTalonario + ","
				+ idBoleto + ",'"
				+ status + "',"
				+ costo + ","
				+ abono + ",'"
				//+ num_boletos + ",'"
				+ formato + "','"
				+ accion + "','"
				+ nivel.toString() + "','"
				+ detalle + "'"
				+ ")";
		System.out.println("  --- SEG:  "+ sql);
		db.execQuery(sql);
	}
	
	
	
	public static void guardaVenta(
			Database db, 
			ASIGNACION nivel, long idSorteo, long idSector, long idNicho, long idColaborador,
			ASIGNACION objeto, long idTalonario, long idBoleto,
			char status, double costo, double abono, int num_boletos, String formato, String accion, String detalle, String nombre) {

		// Paso 2) guarda un registro
		String sql =
				"INSERT SEGUIMIENTO (TIPO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,PK_TALONARIO,PK_BOLETO,"
				+ " PK_ESTADO,COSTO,ABONO,FORMATO,ACCION,NIVEL,DETALLE,NOMBREC)"
				+ " VALUES ("
			//	+ (objeto == ASIGNACION.BOLETO ? "'B'" : "'T'") + ","
				+"'B'," 
				//+ idSorteo + ","
				+ idSector + ","
				+ idNicho + ","
				+ idColaborador + ","
				+ idTalonario + ","
				+ idBoleto + ",'"
				+ status + "',"
				+ costo + ","
				+ abono + ",'"
				//+ num_boletos + ",'"
				+ formato + "','"
				+ accion + "','"
				+ nivel.toString() + "','"
				+ detalle + "','"				
                + nombre + "'"               
				+ ")";
		System.out.println("  --- SEG SOBRECARGA:  "+ sql);
		db.execQuery(sql);
	}
	
	
	
	
	
}















