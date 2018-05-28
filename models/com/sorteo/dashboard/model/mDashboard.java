package com.sorteo.dashboard.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.core.SesionDatos;
import com.core.SuperModel;

public class mDashboard extends SuperModel {
	
	private int idSector;
	private int idSorteo;
	private int idNicho;
	private int idColaborador;
	private int idUsuario;
	
	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idSector) {
		this.idSector = idSector;
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idNicho) {
		this.idNicho = idNicho;
	}

	public int getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(int idColaborador) {
		this.idColaborador = idColaborador;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public mDashboard() {
	}

	public String Sorteo(int idsorteo) {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + idsorteo + "";
		String sorteo = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					sorteo = rs.getString("CLAVE") + "-"
							+ rs.getString("SORTEO");
				}

				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sorteo;

	}
	
	public String Sector(int idsector){
		
		String sql = "SELECT * FROM SECTORES WHERE PK1 = "+idsector+"";
		String sector = "-";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					sector = rs.getString("CLAVE") + "-"
							+ rs.getString("SECTOR");
				}

				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sector;
	}
	
	public String Nicho(int idnicho){
	   	 
	   	 String sql = "SELECT * FROM NICHOS WHERE PK1 = "+idnicho+"";
	   	 String nicho = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						
						 nicho = rs.getString("CLAVE")+"-"+rs.getString("NICHO");
					   }
					
					rs.close(); 			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	 
	   	 return nicho;
	   	 
	    }
	
	
	public String Colaborador(int idcolaborador){
	   	 
	   	 
	   	 String sql = "SELECT CLAVE,NOMBRE,APATERNO,AMATERNO FROM COLABORADORES WHERE PK1 = "+idcolaborador+"";
	   	 String colaborador = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						
						 colaborador = rs.getString("CLAVE")+"-"+rs.getString("NOMBRE")+" "+rs.getString("APATERNO")+" "+rs.getString("AMATERNO");
					   }
					
					rs.close(); 			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	 
	   	 return colaborador;
	   	 
	    }
	
	
	///////////////SORTEO/////////////
	
	public class Estadisticas {
		public int id;
		public String nombre;
		public boolean ok;
		/*
		public int boletos_total;
		public int boletos_entregados;
		public int boletos_vendidos;
		public int boletos_retornados;
		public double venta;
		*/
		
		public int total;
		public int emision;
		public int asignacion;
		public int FC2;
		public int FC4;
		public double bancos;
		public double monto_total;

		public Estadisticas() {
			id = 0;
			nombre = "";
			ok = false;
			total = emision = asignacion = FC2 = FC4 = 0;
			//boletos_total = boletos_entregados = boletos_vendidos = boletos_retornados = 0;
			bancos = monto_total = 0.0;
		}
		
		public Estadisticas(ResultSet rs) throws SQLException {
			/*
			id = rs.getInt("PK1");
			nombre = rs.getString("NOMBRE");
			boletos_total = rs.getInt("TOTAL_DE_BOLETOS");
			boletos_entregados = rs.getInt("ENTREGADOS");
			boletos_vendidos = rs.getInt("VENDIDOS");
			boletos_retornados = rs.getInt("RETORNADOS");
			venta = rs.getDouble("VENTA");
			monto_total = rs.getDouble("MONTO_TOTAL");
			ok = true;
			*/
			id = rs.getInt("PK1");
			nombre = rs.getString("NOMBRE");
			total = rs.getInt("TOTAL");
			emision = rs.getInt("EMISION");
			asignacion = rs.getInt("ASIGNACION");
			FC2 = rs.getInt("FC2");
			FC4 = rs.getInt("FC4");
			bancos = rs.getDouble("BANCOS");
			monto_total = rs.getDouble("MONTO_TOTAL");
			ok = true;
		}

		public void acumula(Estadisticas other) {
			this.total += other.total;
			this.emision += other.emision;
			this.asignacion += other.asignacion;
			this.FC2 += other.FC2;
			this.FC4 += other.FC4;
			this.bancos += other.bancos;
			this.monto_total += other.monto_total;
		}
	}

	public Estadisticas creaEstadisticas() {
		return new Estadisticas();
	}
	
	public ArrayList<Estadisticas> getEstadisticasDashboardSorteo() {
		return getEstadisticasDashboard("vDASHBOARD", "");
	}
	
	public ArrayList<Estadisticas> getEstadisticasDashboardSector() {
		return getEstadisticasDashboard("vDASHBOARD_SECTORES", "");
	}
	
	public ArrayList<Estadisticas> getEstadisticasDashboardNicho() {
		return getEstadisticasDashboard("vDASHBOARD_NICHOS", "PK_SECTOR=" + this.getIdSector());
	}
	
	public ArrayList<Estadisticas> getEstadisticasDashboard(String table, String condicion) {
		String sql = "SELECT * FROM " + table + (condicion.length() == 0 ? "" : " WHERE " + condicion) + " ORDER BY CLAVE";
		ResultSet rs = db.getDatos(sql);
		ArrayList<Estadisticas> list = new ArrayList<Estadisticas>();
		try {
			while (rs.next()) {
				list.add(new Estadisticas(rs));
			}
		} catch (Exception ex) { }
		return list;
	}
	
	public Estadisticas getEstadisticasXSORTEO() {
		ArrayList<Estadisticas> list = getEstadisticasDashboard("vDASHBOARD", "");
		return list.get(0);
	}
	
	public Estadisticas getEstadisticasXSector() {
		ArrayList<Estadisticas> list = getEstadisticasDashboard("vDASHBOARD_SECTORES", "PK_SECTOR=" + this.getIdSector());
		return list.get(0);
	}

	public Estadisticas getEstadisticasXNicho() {
		ArrayList<Estadisticas> list = getEstadisticasDashboard("vDASHBOARD_NICHOS", "PK_SECTOR=" + getIdSector() + " AND PK_NICHO=" + getIdNicho());
		return list.get(0);
	}
	/*
	public Estadisticas getEstadisticasXSector() {
		// TODO getEstadisticasXSector
		String sql = "SELECT * FROM vDASHBOARD_SECTORES WHERE PK_SECTOR=" + getIdSector();
		
		ResultSet rs = db.getDatos(sql);
		try {
			rs.next();
			return new Estadisticas(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return creaEstadisticas();
		}
	}
	/*
	public Estadisticas getEstadisticasXNicho() {
		// TODO getEstadisticasXNicho
		String sql = "SELECT * FROM vDASHBOARD_NICHOS WHERE PK_SECTOR=" + getIdSector() + " AND PK_NICHO=" + getIdNicho();

		ResultSet rs = db.getDatos(sql);
		try {
			rs.next();
			return new Estadisticas(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return creaEstadisticas();
		}
	}

	public Estadisticas getEstadisticasXColaborador() {
		// TODO getEstadisticasXColaborador
		String sql = "SELECT * FROM vDASHBOARD_COLABORADORES WHERE PK_SECTOR=" + getIdSector() + " AND PK_NICHO=" + getIdNicho() + " AND PK_COLABORADOR=" + getIdColaborador();

		ResultSet rs = db.getDatos(sql);
		try {
			rs.next();
			return new Estadisticas(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return creaEstadisticas();
		}
	}
	*/
	/*
	public String TotalboletosSorteo() {

		String sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_BOLETOS"
				+ " WHERE PK_SORTEO = " + this.idSorteo + "";
		ResultSet rs = db.getDatos(sql);
		int total = 0;
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
				rs.close();
			}

		} catch (SQLException e) { e.printStackTrace(); }
		return new Integer(total).toString();
	}

	public String TotalBoletosEntregados() {
		
		String sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_BOLETOS"
				+ " WHERE PK_SORTEO = " + this.getIdSorteo()
				+ " AND ASIGNADO = 1";
		ResultSet rs = db.getDatos(sql);
		int total = 0;
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return new Integer(total).toString();
	}
	
	public String TotalBoletosTotalmenteVendidos() {

		String sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO"
				+ " AND SB.PK_SORTEO = " + this.getIdSorteo()
				+ " AND B.VENDIDO = 'V'";
		ResultSet rs = db.getDatos(sql);
		int total = 0;
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		return formatoNumero.format(total);
	}
	
	public String TotalBoletosParcialmenteVendidos() {

		String sql = "SELECT COUNT(*) AS 'TOTAL' FROM SORTEOS_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO"
				+ " AND SB.PK_SORTEO = " + this.getIdSorteo()
				+ " AND B.VENDIDO = 'P'";
		ResultSet rs = db.getDatos(sql);
		int total = 0;
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		return formatoNumero.format(total);
	}
	
	public double TotalDeVenta() {
		double total = 0.0;
		String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS 'TOTAL' FROM SORTEOS_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO"
				+ " AND SB.PK_SORTEO = "
				+ this.getIdSorteo() + "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = rs.getDouble("TOTAL");
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return total;
	}

	public double TotalDeVentaParcial() {
		double total = 0.0;
		String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS 'TOTAL' FROM SORTEOS_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO AND B.VENDIDO='P'"
				+ " AND SB.PK_SORTEO = " + this.getIdSorteo();
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = rs.getDouble("TOTAL");
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return total;
	}
	
	public double MontoTotal() {
		double total = 0.0;
		String sql = "SELECT ISNULL(SUM(B.COSTO),0) AS 'TOTAL' FROM SORTEOS_BOLETOS SB, BOLETOS B"
				+ " WHERE B.PK1 = SB.PK_BOLETO"
				+ " AND SB.PK_SORTEO = " + this.getIdSorteo();
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				total = rs.getDouble("TOTAL");
				rs.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return total;
	}
	
    ///////////////SORTEO/////////////
   
	*/
	 ///////////////SECTOR///////////
	
	public String TotalboletosSector(){
	   	 
	   	 
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+"";
	   	 ResultSet rs = db.getDatos(sql);
	   	int total=0;
	   	 try {
				if (rs != null) {
					 while(rs.next())
					   {
						 total = Integer.parseInt(rs.getString("TOTAL"));
					   }
					rs.close(); 			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	 NumberFormat formatoNumero = NumberFormat.getNumberInstance();
	   	 return formatoNumero.format(total);
	    }
	
	
	public String TotalboletosAsignadosSector(){
		String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND ASIGNADO = 1";
		ResultSet rs = db.getDatos(sql);
		int total = 0;
		try {
			if (rs != null) {
				while (rs.next()) {
					total = (Integer.parseInt(rs.getString("TOTAL")));
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		return formatoNumero.format(total);
	}
	
	public String TotalboletosSectorVendidos(){
		String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B ";
	   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND B.VENDIDO = 'T'";
		int total = 0;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total = Integer.parseInt(rs.getString("TOTAL"));
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		return formatoNumero.format(total);
	}
	
	public double TotalboletosSectorVenta(){
		double total = 0;
		String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B ";
	   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+"";
	   	        
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {
					total = (double) rs.getInt("TOTAL");
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	/////////////END SECTOR/////////////
	
	
	////////////NICHOS////////////////
	
	public String TotalboletosNichos() {
		
		String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = "
				+ this.getIdSorteo()
				+ " AND PK_SECTOR = '"
				+ this.getIdSector()
				+ "' AND PK_NICHO = '" + this.getIdNicho() + "'";
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
	   	return formatoNumero.format(max);
	}
	
	public String TotalboletosAsignadosNichos() {
		
		String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND ASIGNADO = 1";
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null) {

				while (rs.next()) {
					max = (Integer.parseInt(rs.getString("TOTAL")));
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		return formatoNumero.format(max);
	}

	public String TotalboletosNichoVendidos(){
	   	 String total = null; 
	   	 String sql = "SELECT COUNT(*) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B ";
	   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+" AND B.VENDIDO = 'T'";

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					total = rs.getString("TOTAL");
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public double TotalboletosNichoVenta() {
		double total = 0; 
	   	 String sql = "SELECT ISNULL(SUM(B.ABONO),0) AS TOTAL FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B ";
	   	        sql += "WHERE B.PK1 = SB.PK_BOLETO AND SB.PK_SORTEO = "+this.getIdSorteo()+" AND PK_SECTOR = "+this.getIdSector()+" AND PK_NICHO = "+this.getIdNicho()+"";

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {
					total = (double) rs.getInt("TOTAL");
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	///////////NICHOS////////////////

	public ResultSet Sectores()
	{
		String sql = "SELECT * FROM SECTORES S WHERE S.PK_SORTEO = " + this.getIdSorteo();
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public ResultSet Nichos() {
    	//String sql = "SELECT N.PK1, N.CLAVE AS 'CLAVE', N.NICHO AS 'NICHO' FROM SORTEOS_ASIGNACION_NICHOS AN, NICHOS N WHERE N.PK1 = AN.PK_NICHO AND AN.PK_SORTEO = "+this.getIdSorteo()+" AND AN.PK_SECTOR = "+this.getIdSector()+"";
    	String sql = "SELECT N.PK1, N.CLAVE AS 'CLAVE', N.NICHO AS 'NICHO' FROM SECTORES S, NICHOS N WHERE N.PK_SECTOR=S.PK1 AND S.PK_SORTEO = " + this.getIdSorteo() + " AND S.PK1 = " + this.getIdSector();

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public ResultSet Colaboradores() {

		String sql
			= "SELECT C.PK1 AS PK1, C.CLAVE AS CLAVE, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS 'NOMBRE_COMPLETO' FROM COLABORADORES C,  SORTEOS_ASIGNACION_COLABORADORES AC "
			+ " WHERE C.PK1 = AC.PK_COLABORADOR AND AC.PK_SORTEO = "+this.getIdSorteo()+" AND AC.PK_SECTOR = "+this.getIdSector()+" AND AC.PK_NICHO = "+this.getIdNicho()
			+ " ORDER BY NOMBRE_COMPLETO";
		
		ResultSet rs = db.getDatos(sql);
		System.out.println("---->> "+sql);
		return rs;
	}
	
	public int contarColaboradores() {
		String sql = "SELECT COUNT(C.PK1) AS 'MAX'"
			+ " FROM COLABORADORES C,  COLABORADORES_ASIGNACION CA"
			+ " WHERE C.PK1 = CA.PK_COLABORADOR AND CA.PK_SECTOR = "+this.getIdSector()+" AND CA.PK_NICHO = "+this.getIdNicho();
		
		return db.Count(sql);
	}

	
	public ResultSet Colaboradores(int pg, int size) {
		String sql
			= "SELECT C.PK1 AS PK1, C.CLAVE AS CLAVE, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS 'NOMBRE_COMPLETO' FROM COLABORADORES C, COLABORADORES_ASIGNACION CA "
			+ " WHERE C.PK1 = CA.PK_COLABORADOR AND CA.PK_SECTOR = "+this.getIdSector()+" AND CA.PK_NICHO = "+this.getIdNicho()
			+ " ORDER BY NOMBRE_COMPLETO ASC"
			
	        + " OFFSET ("+(pg-1)*size+") ROWS"
	        + " FETCH NEXT "+size+" ROWS ONLY";
		
		
		ResultSet rs = db.getDatos(sql);
		//System.out.println("---->> "+sql);
		return rs;
	}
	
	public int ContarSorteosUsuarios() {

		String sql = "SELECT COUNT(*) AS 'VALUE' FROM SORTEOS_USUARIOS WHERE PK_USUARIO = "+this.getIdUsuario()
				+ " AND PK_SORTEO IS NOT NULL AND PK_SECTOR IS NULL AND PK_NICHO IS NULL";

		// System.out.println(sql);
		return db.getValue(sql, 0);
	}// <end>

	public int ContarSectoresUsuarios() {

		String sql = "SELECT COUNT(*) AS 'VALUE' FROM SORTEOS_USUARIOS WHERE PK_USUARIO = "+this.getIdUsuario()
				+ " AND PK_SORTEO IS NULL AND PK_SECTOR IS NOT NULL AND PK_NICHO IS NULL";

		// System.out.println(sql); dn
		return db.getValue(sql, 0);
	}// <end>


	public int ContarNichosUsuarios() {
		
		String sql = "SELECT COUNT(*) AS 'VALUE' FROM SORTEOS_USUARIOS WHERE PK_USUARIO = "+this.getIdUsuario()
				+ " AND PK_SORTEO IS NULL AND PK_SECTOR IS NULL AND PK_NICHO IS NOT NULL";

		// System.out.println(sql);
		return db.getValue(sql, 0);
	}// <end>
	
	public void cosultaPredeterminados(){
		
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = "+this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		System.out.println(sql);
		try {
			while (rs.next()) {
				this.setIdSorteo(rs.getInt("PK_SORTEO"));
				this.setIdSector(rs.getInt("PK_SECTOR"));
				this.setIdNicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getSorteosUsuario(){
		
		String sql = "SELECT S.PK1, S.CLAVE,S.SORTEO FROM SORTEOS S, SORTEOS_USUARIOS SU WHERE S.PK1 = SU.PK_SORTEO AND SU.PK_USUARIO = "+this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public ResultSet getSectoresUsuario(){
		
		//String sql = "SELECT SU.PK_SORTEO,S.SORTEO,SU.PK_SECTOR, SE.CLAVE,SE.SECTOR FROM SORTEOS S, SECTORES SE, SORTEOS_USUARIOS_SECTORES SU WHERE SE.PK1 = SU.PK_SECTOR AND S.PK1 = SU.PK_SORTEO  AND SU.PK_USUARIO = "+this.getIdUsuario();
		String sql
				= "SELECT SE.PK_SORTEO,SO.SORTEO, SU.PK_SECTOR, SE.CLAVE,SE.SECTOR"
				+ " FROM SORTEOS_USUARIOS SU, SECTORES SE, SORTEOS SO WHERE SU.PK_SECTOR=SE.PK1 AND SE.PK_SORTEO=SO.PK1 AND SU.PK_USUARIO=" + this.getIdUsuario();

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public ResultSet getNichosUsuario(){
		
		//String sql = "SELECT NU.PK_SORTEO,S.SORTEO,NU.PK_SECTOR,NU.PK_NICHO,N.CLAVE,N.NICHO FROM SORTEOS S, NICHOS N, SORTEOS_USUARIOS_NICHOS NU WHERE N.PK1 = NU.PK_SECTOR AND S.PK1 = NU.PK_SORTEO  AND NU.PK_USUARIO = "+this.getIdUsuario();
		
		// super consulta chingona
		String sql = "SELECT SE.PK_SORTEO,SO.SORTEO,N.PK_SECTOR,SU.PK_NICHO,N.CLAVE,N.NICHO"
				+ " FROM SORTEOS_USUARIOS SU, NICHOS N, SECTORES SE, SORTEOS SO"
				+ " WHERE SU.PK_USUARIO = " + this.getIdUsuario() + " AND SU.PK_SORTEO IS NULL AND SU.PK_SECTOR IS NULL AND SU.PK_NICHO = N.PK1 AND N.PK_SECTOR = SE.PK1 AND SE.PK_SORTEO = SO.PK1";
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	
	public void setPredeterminadoSorteo(){
		
		String sql = "UPDATE USUARIOS SET PK_SORTEO = "+this.getIdSorteo()+", PK_SECTOR = "+this.getIdSector()+",  PK_NICHO="+this.getIdNicho()+"  WHERE PK1 ="+this.getIdUsuario();
		db.execQuery(sql);
	}
	


	public int contarTotalDeBoletos(SesionDatos sesion, String nivel) {
		try {
			String sql;
			switch (nivel) {
			case "Sorteo":
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM BOLETOS B, SORTEOS_BOLETOS R WHERE R.PK_BOLETO=B.PK1"
					+ " AND R.PK_SORTEO=" + sesion.pkSorteo;
				break;
			case "Sector":
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM BOLETOS B, SORTEOS_SECTORES_BOLETOS R WHERE R.PK_BOLETO=B.PK1"
					+ " AND R.PK_SORTEO=" + sesion.pkSorteo
					+ " AND R.PK_SECTOR=" + sesion.pkSector
					;
				break;
			case "Nicho":
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM BOLETOS B, SORTEOS_NICHOS_BOLETOS R WHERE R.PK_BOLETO=B.PK1"
					+ " AND R.PK_SORTEO=" + sesion.pkSorteo
					+ " AND R.PK_SECTOR=" + sesion.pkSector
					+ " AND R.PK_NICHO=" + sesion.pkNicho
					;
				break;
			case "Colaborador":
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM BOLETOS B, SORTEOS_COLABORADORES_BOLETOS R WHERE R.PK_BOLETO=B.PK1"
					+ " AND R.PK_SORTEO=" + sesion.pkSorteo
					+ " AND R.PK_SECTOR=" + sesion.pkSector
					+ " AND R.PK_NICHO=" + sesion.pkNicho
					+ " AND R.PK_COLABORADOR=" + sesion.pkColaborador
					;
				break;
				default: return 0;
			}
			ResultSet rs = db.getDatos(sql);
			if (rs.next())
				return rs.getInt("MAX");
		}catch (Exception ex) { }
		return 0;
	}
	
	private int contarBoletos_Incidencias(SesionDatos sesion, String nivel) {
		return contarBoletos(sesion, nivel, " AND B.INCIDENCIA<>0");
	}

	private int contarBoletos_NOVendidos(SesionDatos sesion, String nivel) {
		return contarBoletos(sesion, nivel, " AND B.INCIDENCIA=0 AND B.ASIGNADO_SECTOR=0 ");
	}

	private int contarBoletos_Transito(SesionDatos sesion, String nivel) {
		return contarBoletos(sesion, nivel, " AND B.INCIDENCIA=0 AND B.ASIGNADO_SECTOR=1 AND B.PK_ESTADO<>'V'");
	}

	private int contarBoletos_Vendidos(SesionDatos sesion, String nivel) {
		return contarBoletos(sesion, nivel, " AND B.INCIDENCIA=0 AND B.ASIGNADO_SECTOR=1 AND B.PK_ESTADO='V' AND B.RETORNADO=0");
	}

	private int contarBoletos_Retornados(SesionDatos sesion, String nivel) {
		return contarBoletos(sesion, nivel, " AND B.INCIDENCIA=0 AND B.ASIGNADO_SECTOR=1 AND B.PK_ESTADO='V' AND B.RETORNADO=1");
	}

	private int contarBoletos(SesionDatos sesion, String nivel, String condicion) {
			String sql;
			switch (nivel) {
			case "Sorteo":
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM vBOLETOS B WHERE B.PK_SORTEO=" + sesion.pkSorteo + condicion;
				break;
			case "Sector":
				    //"SELECT COUNT(B.PK1) AS 'MAX' FROM vBOLETOS B, SECTORES_BOLETOS R WHERE R.PK_BOLETO=B.PK1  AND B.INCIDENCIA=0 AND B.ASIGNADO_SECTOR=1 AND B.PK_ESTADO='V' AND B.RETORNADO=0 AND R.PK_SECTOR=33"
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM vBOLETOS B, SECTORES_BOLETOS R WHERE R.PK_BOLETO=B.PK1 " + condicion
					+ " AND R.PK_SECTOR=" + sesion.pkSector
					;
				break;
			case "Nicho":
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM vBOLETOS B, NICHOS_BOLETOS R WHERE R.PK_BOLETO=B.PK1 " + condicion
					+ " AND R.PK_SECTOR=" + sesion.pkSector
					+ " AND R.PK_NICHO=" + sesion.pkNicho
					;
				break;
			case "Colaborador":
				sql = "SELECT COUNT(B.PK1) AS 'MAX' FROM vBOLETOS B, COLABORADORES_BOLETOS R WHERE R.PK_BOLETO=B.PK1 " + condicion
					+ " AND R.PK_SECTOR=" + sesion.pkSector
					+ " AND R.PK_NICHO=" + sesion.pkNicho
					+ " AND R.PK_COLABORADOR=" + sesion.pkColaborador
					;
				break;
				default: return 0;
			}
			
			return db.Count(sql);
		//return 0;
	}

	public Object[][] consultaEstadisticas(SesionDatos sesion, String nivel) {
		/*
		 * azul    - vendidos
		 * negro   - incidencias
		 * verde   - transito
		 * naranja - no vendidos
		 * morado  - retornados
		 */
		Object[][] arr_json =
			{
				{"Vendidos:",    new Integer(contarBoletos_Vendidos(sesion, nivel))},
				{"Incidencias:", new Integer(contarBoletos_Incidencias(sesion, nivel))},
				{"No concentrado:",    new Integer(contarBoletos_Transito(sesion, nivel))},
				{"No vendidos:", new Integer(contarBoletos_NOVendidos(sesion, nivel))},
				{"Retornados:",  new Integer(contarBoletos_Retornados(sesion, nivel))}
			};
		for (int i=0; i<arr_json.length; i++) {
			arr_json[i][0] = "\"" + arr_json[i][0] + " " + arr_json[i][1] + "\"";
		}
		return arr_json;
	}
	
	public Object[][] consultaBoletosVendidos(SesionDatos sesion, String nivel) {
		
		try {
			String sql = "";
			
			switch (nivel) {
			case "Sorteo":
				sql = "SELECT B.PK_ESTADO,C.FECHA_R"
					+ " FROM COMPRADORES_BOLETOS CB, BOLETOS B, COMPRADORES C "
					+ " WHERE CB.PK_COMPRADOR = C.PK1 AND CB.PK_BOLETO = B.PK1 AND B.PK_ESTADO='P'";
				break;
				
			case "Sector":
				sql = "SELECT B.PK_ESTADO,C.FECHA_R"
					+ " FROM COMPRADORES_BOLETOS CB, BOLETOS B, COMPRADORES C, SECTORES_BOLETOS SECB"
					+ " WHERE CB.PK_BOLETO = B.PK1 AND CB.PK_COMPRADOR = C.PK1 AND SECB.PK_BOLETO=B.PK1 AND B.PK_ESTADO='P'"
					+ " AND SECB.PK_SECTOR=" + sesion.pkSector;
				break;
				
			case "Nicho":
				sql = "SELECT B.PK_ESTADO,C.FECHA_R"
					+ " FROM COMPRADORES_BOLETOS CB, BOLETOS B, COMPRADORES C, NICHOS_BOLETOS NICB"
					+ " WHERE CB.PK_BOLETO = B.PK1 AND CB.PK_COMPRADOR = C.PK1 AND NICB.PK_BOLETO=B.PK1 AND B.PK_ESTADO='P'"
					+ " AND NICB.PK_SECTOR=" + sesion.pkSector
					+ " AND NICB.PK_NICHO=" + sesion.pkNicho;
				break;
				
			case "Colaborador":
				sql = "SELECT B.PK_ESTADO,C.FECHA_R"
					+ " FROM COMPRADORES_BOLETOS CB, BOLETOS B, COMPRADORES C, COLABORADORES_BOLETOS COLB"
					+ " WHERE CB.PK_BOLETO = B.PK1 AND CB.PK_COMPRADOR = C.PK1 AND COLB.PK_BOLETO=B.PK1 AND B.PK_ESTADO='P'"
					+ " AND SECB.PK_SECTOR=" + sesion.pkSector
					+ " AND SECB.PK_NICHO=" + sesion.pkNicho
					+ " AND COLB.PK_COLABORADOR=" + sesion.pkColaborador;
				break;
			}

			ResultSet rs = db.getDatos(sql);
	
			HashMap<Long, Integer> map = new HashMap<Long, Integer>();
			while (rs.next()) {
				Date date = rs.getDate("FECHA_R");
				long time = date.getTime();
				
				if (map.containsKey(time) == false)
					// Se guarda el par {key,value} como: {"tiempo", "contador de boletos"}.
					map.put(time, 1);
				else
					// Se actualiza el "contador de boletos" con el "tiempo" encontrado.
					map.replace(time, 1 + map.get(time));
			}
			
			return ordenaArreglo(map);
			
		} catch(Exception ex) { }
		
		return null;
	}
	
	public Object[][] ordenaArreglo(HashMap<Long, Integer> map) {
		NumberFormat formatter = new DecimalFormat("#0.00");
		ArrayList<Long> values = new ArrayList<Long>(map.keySet());
		java.util.Collections.sort(values);
		Iterator<Long> iterator = values.iterator();
		
		Object[][] matriz = new Object[values.size()][];
		int i=0;
		while (iterator.hasNext()) {
			Long key = (Long)iterator.next();
			Integer counter = map.get(key);
			String nBoletos = formatter.format((double)counter).replace(',', '.');

			matriz[i] = new Object[2];
			matriz[i][0] = key;
			matriz[i][1] = nBoletos;
			i++;
		}
		return matriz;
	}

	public String create_JSON(Object[][] map) {
		StringBuilder sb = new StringBuilder(1000);
		sb.append("[");
		if (map != null)
			for (int j=0; j<map.length; j++) {
				if (j!=0)
					sb.append(",");
				sb.append("[");
				for (int i=0; i<map[j].length; i++) {
					if (i!=0)
						sb.append(",");
					sb.append(map[j][i]);
				}
				sb.append("]");
			}
		sb.append("]");
		
		return sb.toString();
	}
	
}


