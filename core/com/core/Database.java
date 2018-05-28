package com.core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;


public class Database {

	private Connection con = null;
	private Statement consulta = null;
	private ResultSet data = null;
	private String server = null;
	private String BD;
	private String userBD;
	private String passwBD;
	private String port;

	//this.server  = "claradb.database.windows.net";

	public Database() {
		
	}
	
	public void readProperties() {
		try {
			Context ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");

			this.server = (String) env.lookup("server");
			this.BD = (String) env.lookup("db");
			this.userBD = (String) env.lookup("userBD");
			this.passwBD = (String) env.lookup("passwBD");
			this.port = (String) env.lookup("port");

			// ___________________________________
			// LOCAL
			/* *
			this.server  = "localhost";
			this.BD      = "sorteo";
			this.userBD  = "sorteos";
			this.passwBD = "123456";
			this.port    = "1433";
			/**/
		} catch (Exception e) { e.printStackTrace(); }
	}

	// Metodo para abrir la conexion
	public void con() {
		// TODO conection
		try {
			if (con == null || con.isClosed()) {
				readProperties();
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://" + this.server + ":" + this.port + ";databaseName=" + this.BD;
				this.con = DriverManager.getConnection(url, this.userBD, this.passwBD);
			}
		} catch (Exception e) {
			con = null;
			System.out.println("Conexion failed...!! " + e.getMessage());
		}
	}

	public void desconectar() {
		// TODO cerrar la conexion

		try {
			if (con != null) {
				this.con.close();
			}
		} catch (Exception e) {
			System.out.println("Error de desconexion failed...!! " + e.getMessage());
		}
	}
	
	
	/*public void desconectar() {
		// TODO cerrar la conexion

		try {
			if (con != null) {
				this.con.close();
			}
		} catch (Exception e) {
			System.out.println("Error de desconexion failed...!! "
					+ e.getMessage());
		}
	}*/
	
	
	public ResultSet execStoreProcedure(String name_SP) {	//sp_ObtenerVentas
		try {
			this.con();

			this.consulta = con.createStatement();
		    data = this.consulta.executeQuery("{call " + name_SP + "}");	
			
		} catch (Exception e) {
			System.out.println("Error de Ejecucion StoreProcedure: " + name_SP + " ...!!" + e.getMessage());
			e.printStackTrace();
		}	
		
		return data;
	}
	
	public boolean execStoreProcedure(String name_SP, List<Parametros> listaParam) {	//sp_ObtenerVentas
		
		boolean resultado = false;
		CallableStatement cstmt = null;
		String cadenaParams = "";
		
		try {
			for(int i = 0; i < listaParam.size(); i++)
				cadenaParams += "?,";
			cadenaParams = cadenaParams.substring(0, cadenaParams.length() - 1);
			
			this.con();

			cstmt = con.prepareCall("{call " + name_SP + "(" + cadenaParams + ")}");
			for(Parametros objParams : listaParam)
				cstmt.setString(objParams.getParamName(), objParams.getParamValue());
			
			resultado = cstmt.execute();

			return true;
		} catch (Exception e) {
			System.out.println("Error de Ejecucion StoreProcedure '" + name_SP + "' ...!! " + e.getMessage());
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public int execStoreProcedureIntId(String name_SP, List<Parametros> listaParam) {	//sp_ObtenerVentas
		
		CallableStatement cstmt = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("{call ").append(name_SP).append("(");
			for(int i = 0; i < listaParam.size(); i++)
				sb.append(i == 0 ? "?" : ",?");
			sb.append(")}");
			
			this.con();

			cstmt = con.prepareCall(sb.toString());
			for(Parametros objParams : listaParam)
				cstmt.setString(objParams.getParamName(), objParams.getParamValue());
			
			cstmt.registerOutParameter(1,  Types.INTEGER);
		
			cstmt.execute();
			int returnValue = cstmt.getInt(1);
			return returnValue;
		} catch (Exception e) {
			System.out.println("Error de Ejecucion StoreProcedure (Id) '" + name_SP + "' ...!! " + e.getMessage());
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String execStoreProcedureIntId2(String name_SP, List<Parametros> listaParam) {	//sp_ObtenerVentas
		
		CallableStatement cstmt = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("{call ").append(name_SP).append("(");
			for(int i = 0; i < listaParam.size(); i++)
				sb.append(i == 0 ? "?" : ",?");
			sb.append(")}");
			
			this.con();

			cstmt = con.prepareCall(sb.toString());
			for(Parametros objParams : listaParam)
				cstmt.setString(objParams.getParamName(), objParams.getParamValue());
			
			cstmt.registerOutParameter(1,  Types.INTEGER);
			cstmt.registerOutParameter(2,  Types.INTEGER);
			cstmt.execute();			
			
			String returnValue = cstmt.getInt(1)+"#%#"+cstmt.getInt(2);
			return returnValue;
		} catch (Exception e) {
			System.out.println("Error de Ejecucion StoreProcedure (Id) '" + name_SP + "' ...!! " + e.getMessage());
			e.printStackTrace();
		}
		
		return "";
	}	
	public int execQuery(String sql) {
		// TODO execQuery : ejecutar SQL INSERT UPDATE DELETE
		int res = 0;
		try {
			this.con();
			// System.out.println(sql);
			this.consulta = con.createStatement();
			res = this.consulta.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("Error de Ejecucion...!!" + e.getMessage());
			e.printStackTrace();
		}

		return res;
	}

	public int execQuerySelectId(String sql) {
		// TODO execQuerySelectId
		int res = 0;
		try {
			this.con();
			// System.out.println(sql);
			this.consulta = con.createStatement();
			res = this.consulta.executeUpdate(sql);

			// OBTENER ID
			sql = "SELECT @@IDENTITY AS NewSampleId";
			ResultSet rs = this.consulta.executeQuery(sql);
			rs.next();
			res = rs.getInt("NewSampleId");

		} catch (Exception e) {
			System.out.println("Error de Ejecucion...!!" + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	
public int execStoreProcedureList(String name_SP, List<Parametros> listaParam) {
		
		CallableStatement cstmt = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("{call ").append(name_SP).append("(");
			for(int i = 0; i < listaParam.size(); i++)
				sb.append(i == 0 ? "?" : ",?");
			sb.append(")}");
			
			this.con();

			cstmt = con.prepareCall(sb.toString());
			for(Parametros objParams : listaParam)
				cstmt.setString(objParams.getParamName(), objParams.getParamValue());
			
			// Declarar registros de salida.
			for (int i = 0; i < listaParam.size(); i++) {
				Parametros prm = listaParam.get(i);
				if (prm.getOut()) {
					cstmt.registerOutParameter(i + 1, prm.getType());
				}
			}
			cstmt.execute();			
			
			int count = 1;
			for(Parametros prm : listaParam) {
				if (prm.getOut())
					switch(prm.getType()) {
					case Types.INTEGER : prm.setParamValue(String.valueOf(cstmt.getInt(count))); break;
					case Types.BIGINT : prm.setParamValue(String.valueOf(cstmt.getLong(count))); break;
					case Types.VARCHAR : prm.setParamValue(String.valueOf(cstmt.getString(count))); break;
					case Types.CHAR : prm.setParamValue(String.valueOf(cstmt.getString(count))); break;
					}
				count++;
			}
			
			return 0;
		} catch (Exception e) {
			System.out.println("Error de Ejecucion StoreProcedure (Id) '" + name_SP + "' ...!! " + e.getMessage());
			e.printStackTrace();
		}
		
		return -1;
	}

	public ResultSet getDatos(String sql) {
		// TODO  getDatos : Metodo para ejecutar SELECT
		try {
			this.con();
			// System.out.println("db.getDatos:  " + sql);
			this.consulta = con.createStatement();
			this.data = this.consulta.executeQuery(sql);
		} catch (Exception e) {
			System.out.println("Error de Ejecucion...!!" + e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	public String getValue(String sql, String defaultValue){
		try{
			ResultSet res = this.getDatos(sql);
			if(res!=null && res.next())
				return res.getString("VALUE");
		}catch(Exception ex){}
		return defaultValue;
	}
	
	public double getValue(String sql, double defaultValue){
		try{
			ResultSet res = this.getDatos(sql);
			if(res!=null && res.next())
				return res.getDouble("VALUE");
		}catch(Exception ex){}
		return defaultValue;
	}
	
	public int getValue(String sql, int defaultValue){
		try{
			ResultSet res = this.getDatos(sql);
			if(res!=null && res.next())
				return res.getInt("VALUE");
		}catch(Exception ex) { ex.printStackTrace(); }
		return defaultValue;
	}
	
	public long getValue(String sql, long defaultValue){
		try{
			ResultSet res = this.getDatos(sql);
			if(res!=null && res.next())
				return res.getLong("VALUE");
		}catch(Exception ex) { ex.printStackTrace(); }
		return defaultValue;
	}
	
	public int ContarFilas(String sql) {
		// TODO ContarFilas
		try {
			this.con();
			this.consulta = con.createStatement();
			this.data = this.consulta.executeQuery(sql);
			int contador = 0;
			while (data.next()) {
				contador++;
			}
			return contador;
		} catch (Exception e) {
			System.out.println("Error al contar registros...!!" + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	public int Count(String sql){
		int numero = 0;
		try {
			ResultSet res = this.getDatos(sql);
			if (res!=null && res.next()){
				numero = res.getInt("MAX");
			}
		} catch (Exception ex) { }
		return numero;
	}

	public int ContarFilasDesdeCount(String nombreTabla, String condicion) {
		// TODO ContarFilasDesdeCount
		int max = 0;
		try {
			String sql = "SELECT COUNT(*) AS MAX FROM " + nombreTabla
					+ condicion;
			this.con();
			this.consulta = con.createStatement();
			this.data = this.consulta.executeQuery(sql);
			if (this.data != null && this.data.next()) {
				max = this.data.getInt("MAX");
			}
			this.data.close();
		} catch (SQLException ex) { }
		return max;
	}// <end>

	public int guardaLog(String usuario, String actividad, String detalle) {
		// TODO guardaLog
		try {
			if (detalle.length() <= 160) {
				String query = new StringBuilder()
						.append(" INSERT LOG (USUARIO,ACTIVIDAD,DETALLE,FECHA_R)")
						.append("VALUES(").append(usuario).append(", '")
						.append(actividad).append("', '").append(detalle)
						.append("'").append(", GETDATE())")
						.toString();
				this.con();
				this.execQuery(query);
				 System.out.println("log usu: "+query);
				return 0;
			} else {
				ArrayList<String> list_detalle = cortaCadena(detalle, 160);
				guardaLog(usuario, actividad, list_detalle);
			}
		} catch (Exception ex) { }
		return -1;
	}// <end>

	public int guardaLog(String usuario, String actividad,
			ArrayList<String> list_detalle) {
		// TODO guardaLog
		try {
			this.con();
			for (String detalle : list_detalle) {
				String sql = new StringBuilder()
						.append(" INSERT LOG (USUARIO,ACTIVIDAD,DETALLE,FECHA_R)")
						.append("VALUES(").append(usuario).append(", '")
						.append(actividad).append("', '").append(detalle)
						.append("', '").append("', GETDATE())")
						.toString(); 

				this.execQuery(sql);
			}
			return 0;
		} catch (Exception ex) { }
		return -1;
	}

	public ArrayList<String> cortaCadena(String str, int tamanio) {
		// TODO cortaCadena
		ArrayList<String> list_detalle = new ArrayList<String>();
		int posToCut, current = 0;
		do {
			// condicion cuando procesa el ultimo bloque
			if (current + tamanio >= str.length())
				posToCut = str.length();
			else {
				posToCut = current + tamanio - 1;
				for (; posToCut > current; posToCut--)
					if (str.charAt(posToCut) == ' '
							|| str.charAt(posToCut) == ',')
						break;
				if (posToCut == current)
					posToCut = current + tamanio;
			}
			String substr = str.substring(current, posToCut).trim();
			current = posToCut;
			if (substr.length() > 0)
				list_detalle.add(substr);
		} while (current < str.length());
		return list_detalle;
	}
	
	
	public int execQuery2(String sql) throws SQLException {
		// TODO execQuery : ejecutar SQL INSERT UPDATE DELETE
		int res = 0;
		try {
			this.con();
			// System.out.println(sql);
			this.consulta = con.createStatement();
			res = this.consulta.executeUpdate(sql);
			
		} catch (SQLException e) {
			throw e;
			
			
		}	
			
		 catch (Exception e) {
			System.out.println("Error de Ejecucion...!!" + e.getMessage());
			e.printStackTrace();
		}

		return res;
	}
	
	
	
}