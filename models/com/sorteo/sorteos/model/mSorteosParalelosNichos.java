package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.core.Factory;
import com.core.Parametros;
import com.core.SesionDatos;
import com.core.Factory.LOG;
import com.core.SuperModel;
import com.sorteo.poblacion.model.mNichos;

public class mSorteosParalelosNichos extends SuperModel {
	
	private long idParalelo;
	private long idParaleloNicho;
	private int idSorteo;
	private int idSector;
	private int idNicho;

	public long getIdParalelo() {
		return this.idParalelo;
	}

	public void setIdParalelo(long idParalelo) {
		this.idParalelo = idParalelo;
	}	
	
	
	//
	public long getIdParaleloNicho() {
		return this.idParaleloNicho;
	}

	public void setIdParaleloNicho(long idParaleloNicho) {
		this.idParaleloNicho = idParaleloNicho;
	}
	//
	
	
	
	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int id) {
		this.idNicho = id;
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
	}

	public mSorteosParalelosNichos() {
		// TODO Auto-generated constructor stub
	}

	
	public String consultaSorteo(int idsorteo) {
		return super.consultaClaveNombreSorteo(idsorteo);
	}
	
	/**/
	public String consultaParalelo(int idParalelo) {

		String sql = "SELECT NOMBRE AS 'VALUE' FROM SORTEOS_PARALELOS WHERE PK1=" + idParalelo;
		
		return db.getValue(sql, "");
	}
	
	/*
	public ResultSet listarModal() {

		String sql = "SELECT * FROM NICHOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	/*
	public int contarModal(int idsector,String search,int idsorteo) {

		String sql = "SELECT PK1 FROM NICHOS WHERE PK_SECTOR = '" + idsector
				+ "' AND PK_SORTEO = "+idsorteo;
		
		if (search != "") {
			sql += " AND ((NICHO LIKE '%" + search + "%') OR(CLAVE LIKE '%" + search + "%'))  ";
		}
		
		
		int numero = db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacionModal(int pg, int numreg, String search,	int idsector,int idsorteo) {

		
		String sql = "SELECT N.PK1,N.CLAVE,N.NICHO,N.DESCRIPCION,(select TOP 1 PK_NICHO from SORTEOS_ASIGNACION_NICHOS  where PK_NICHO = N.PK1 AND PK_SECTOR = '" + idsector + "' AND PK_SORTEO = '"+idsorteo+"' ) AS "
				+ "'PK_NICHO' FROM NICHOS N WHERE N.PK_SECTOR = '" + idsector + "'";


		if (search != "") {
			sql += " AND ((N.NICHO LIKE '%" + search + "%') OR (N.CLAVE LIKE '%" + search + "%'))  ";
		}

		sql += "ORDER BY N.PK1 ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	//*/

	//

	/*
	public ResultSet listar() {

		String sql = "SELECT * FROM SORTEOS_ASIGNACION_NICHOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}
	//*/

	public int contar(String search) {
		String sql = "SELECT COUNT(SPN.PK1) AS 'MAX'"
				+ " FROM SORTEOS_PARALELOS_NICHOS SPN, NICHOS N, SECTORES S"
				+ " WHERE SPN.PK_SECTOR=S.PK1 AND SPN.PK_NICHO=N.PK1"
				+ " AND PK_SORTEO_PARALELO=" + getIdParalelo();
		
		if (search != "") {
			sql += " AND ((N.NICHO LIKE '%" + search + "%') OR (N.CLAVE LIKE '%" + search + "%')) ";
		}
		System.out.println("contar:" + sql);
		return db.Count(sql);
	}

	public ResultSet paginacion(int pg, int numreg, String search) {
		
		String sql = "SELECT SPN.*,S.CLAVE AS 'S_CLAVE',S.SECTOR,N.CLAVE AS 'N_CLAVE',N.NICHO"
				+ " FROM SORTEOS_PARALELOS_NICHOS SPN, NICHOS N, SECTORES S"
				+ " WHERE SPN.PK_SECTOR=S.PK1 AND SPN.PK_NICHO=N.PK1"
				+ " AND PK_SORTEO_PARALELO=" + getIdParalelo();

		if (search != "") {
			sql += " AND ((N.NICHO LIKE '%" + search + "%') OR (N.CLAVE LIKE '%" + search + "%')) ";
		}
		
		sql += " ORDER BY S.SECTOR ASC, N.NICHO ASC";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS";
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		
		System.out.println("paginacion:"+sql);

		return db.getDatos(sql);
	}

	/*
	 * public void guardarAsignacionSorteoSector1(String[] sectores) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	/*
	public void guardarAsignacion(String[] nichos, mAsignacionNichos obj, SesionDatos sesion) {

		for (String s : nichos) {
			// Do your stuff here
			// System.out.println(s);

			String sql = "SELECT PK1 FROM SORTEOS_ASIGNACION_NICHOS"
					+ " WHERE PK_SECTOR ='" + obj.getIdSector()
					+ "' AND PK_NICHO = '" + s
					+ "' AND  PK_SORTEO ='" + obj.getIdSorteo() + "' ";
			ResultSet rs = db.getDatos(sql);

			try {
				if (!rs.next()) {

					String sql2 = "INSERT INTO SORTEOS_ASIGNACION_NICHOS (PK_SORTEO,PK_SECTOR,PK_NICHO) VALUES ('"
							+ obj.getIdSorteo()
							+ "','"
							+ obj.getIdSector()
							+ "','" + s + "')";
					System.out.println(sql2);
					db.execQuery(sql2);
				}
			} catch (SQLException e) { Factory.Error(e, sql); }

		}
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacion", sesion.toShortString() + ", nichos=" + Arrays.toString(nichos));
		}catch(Exception ex) { Factory.Error(ex, "Log"); }

		//db.desconectar();
	}

	public int numeroTalonarios(mAsignacionNichos obj) {

		db.con();
		String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_NICHOS_TALONARIOS"
				+ " WHERE PK_SORTEO = " + obj.getIdSorteo()
				+ " AND PK_SECTOR = '" + obj.getIdSector()
				+ "' AND PK_NICHO = '" + obj.getIdNicho() + "' ";
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return max;
	}

	public int numeroBoletos(mAsignacionNichos obj) {

		db.con();

		String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_NICHOS_BOLETOS"
				+ " WHERE PK_SORTEO = " + obj.getIdSorteo()
				+ " AND PK_SECTOR = '" + obj.getIdSector()
				+ "' AND PK_NICHO = '" + obj.getIdNicho() + "'";
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return max;
	}

	public int MontoNicho(mAsignacionNichos obj) {

		db.con();
		String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_NICHOS_TALONARIOS"
				+ " WHERE PK_SORTEO = " + obj.getIdSorteo()
				+ " AND PK_SECTOR = '" + obj.getIdSector()
				+ "' AND PK_NICHO = '" + obj.getIdNicho() + "' ";
		ResultSet rs = db.getDatos(sql);
		int total = 0;
		try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return total;
	}
	
	// Para configuracion
	public boolean ExisteCarga(){
		
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM [SORTEOS_NICHOS_TALONARIOS]"
				+ " WHERE PK_NICHO = " + getIdNicho()
				+ " AND PK_SECTOR = " + getIdSector()
				+ " AND PK_SORTEO = " + getIdSorteo();

		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next())
				return rs.getInt("MAX") > 0;

		} catch (SQLException e) { Factory.Error(e, sql); }
		return false;
	}

	public int eliminaCarga(SesionDatos sesion) {
		
		int val = borraTalonariosDeNicho(sesion.pkSorteo, sesion.pkSector, sesion.pkNicho);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "eliminaCarga", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return val;
	}

	public int borraTalonariosDeNicho(int pkSorteo, int pkSector, int pkNicho) {
		String sql="";

		// PASO 1: Se actualiza la columna de ASIGNADO EN EL BOLETO sector
		sql +=
				" UPDATE SORTEOS_SECTORES_TALONARIOS SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_SECTORES_TALONARIOS AS RS, SORTEOS_NICHOS_TALONARIOS AS RN WHERE RS.PK_SORTEO=" + pkSorteo + " AND RS.PK_SECTOR=" + pkSector + " AND RS.PK_TALONARIO=RN.PK_TALONARIO AND RN.PK_NICHO=" + pkNicho +
				" UPDATE SORTEOS_SECTORES_BOLETOS    SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_SECTORES_BOLETOS AS RS   , SORTEOS_NICHOS_BOLETOS AS RN    WHERE RS.PK_SORTEO=" + pkSorteo + " AND RS.PK_SECTOR=" + pkSector + " AND RS.PK_BOLETO=RN.PK_BOLETO       AND RN.PK_NICHO=" + pkNicho;

		// PASO 2) Se borran los boletos y talonarios asignados a los sub-niveles
		sql +=
				" DELETE [SORTEOS_COLABORADORES_TALONARIOS] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho +
				" DELETE [SORTEOS_COLABORADORES_BOLETOS]    WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho;
		// PASO 3) Se borran las relaciones de boletos y talonarios asignados al nicho
		sql +=
				" DELETE [SORTEOS_NICHOS_TALONARIOS]     WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho +
				" DELETE [SORTEOS_NICHOS_BOLETOS]        WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho;

		// PASO 4) Se borra tambien el seguimiento
		sql +=
				" DELETE [SEGUIMIENTO] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector + " AND PK_NICHO=" + pkNicho
				+ " AND NOT (NIVEL='Boveda' OR NIVEL='Sorteo' OR NIVEL='Sector')";

		return db.execQuery(sql);
	}
	
	public int eliminaNicho(SesionDatos sesion) {
		String sql =
				" DELETE [SORTEOS_ASIGNACION_NICHOS]"
				+ " WHERE PK_SORTEO=" + sesion.pkSorteo
				+ " AND PK_SECTOR=" + sesion.pkSector
				+ " AND PK_NICHO= " + sesion.pkNicho;
		int val = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaNicho", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return val;
	}
	//*/
	
	
	public ArrayList<mNichos> getNichosPorSector()
	{
		ArrayList<mNichos> list = new ArrayList<mNichos>();
		
		String sql_filtro = "";
		sql_filtro += " AND SPN.PK_SORTEO_PARALELO=" + getIdParalelo() + " AND SPN.PK_SECTOR=" + getIdSector();
		
		String sql = 
			"SELECT N.*"
			+ ",(SELECT COUNT(SPN.PK1) FROM SORTEOS_PARALELOS_NICHOS SPN WHERE SPN.PK_NICHO=N.PK1" + sql_filtro + ") AS 'CHECK'"
			+ " FROM SORTEOS_ASIGNACION_NICHOS SAN, NICHOS N"
			+ " WHERE SAN.PK_NICHO=N.PK1 AND SAN.PK_SORTEO=" + getIdSorteo() + " AND SAN.PK_SECTOR=" + getIdSector()
			+ " ORDER BY N.CLAVE";
		
		System.out.println("NichosAA:" + sql);
		
		ResultSet res = db.getDatos(sql);
		try{
			while (res.next()) {
				mNichos nicho = new mNichos();
				nicho.setId(res.getInt("PK1"));
				nicho.setClave(res.getString("CLAVE"));
				nicho.setNicho(res.getString("NICHO"));
				//nicho.setDescripcion(res.getString("CHECK"));
				list.add(nicho);
			}
			res.close();
		}catch(Exception ex) { }
		
		return list;
	}
	
	public int guardarNichosAsignados(String[] arrIdsNicho, SesionDatos sesion) {		
					
		
		Parametros pDB_sorteParalelo;
		Parametros pDB_sectorId;
		Parametros pDB_nichoId;
		Parametros pDB_usuarioId;
		List<Parametros> listaParams;
		
		if (arrIdsNicho != null) {			
		
			for (int i = 0; i < arrIdsNicho.length; i++) {					
				
			//	System.out.println("arrIdsNicho[i]=" + arrIdsNicho[i]);
				
				listaParams = new ArrayList<Parametros>();
				
				pDB_sorteParalelo = new Parametros();
				pDB_sorteParalelo.setParamName("sorteoParaleloId");
				pDB_sorteParalelo.setParamValue(String.valueOf(getIdParalelo()));
				listaParams.add(pDB_sorteParalelo);

				pDB_sectorId = new Parametros();
				pDB_sectorId.setParamName("sectorId");
				pDB_sectorId.setParamValue(String.valueOf(getIdSector()));
				listaParams.add(pDB_sectorId);

				pDB_nichoId = new Parametros();
				pDB_nichoId.setParamName("nichoId");
				pDB_nichoId.setParamValue(arrIdsNicho[i]);
				listaParams.add(pDB_nichoId);
				
				pDB_usuarioId = new Parametros();
				pDB_usuarioId.setParamName("usuarioId");
				pDB_usuarioId.setParamValue(String.valueOf(sesion.pkUsuario));
				listaParams.add(pDB_usuarioId);
				
				db.execStoreProcedure("spSorteosParalelosNichosColaborAsignar", listaParams);
			}		 
		}
		
		/*
		if (arrIdsNicho != null) {
			borraNichosAsignados();
			for (int i = 0; i < arrIdsNicho.length; i++) {
				int result = guardaNicho(arrIdsNicho[i], sesion);
				System.out.println("result=" + result);
			}
		}*/
		
		
		return 0;
	}
	
	
	
	
public int guardarNichosAsignados_(String[] arrIdsNicho, SesionDatos sesion) {	
					
	String sql = "";	
	String sql2 = "";
	 ResultSet rs = null;
		//checar para que no lo agregue 2 veces
		if (arrIdsNicho != null) {			
		
			for (int i = 0; i < arrIdsNicho.length; i++) {	
				
				sql2 = "SELECT PK1 FROM SORTEOS_PARALELOS_NICHOS" 
			              +" WHERE PK_SORTEO_PARALELO = '"+getIdParalelo()+"' AND PK_SECTOR = '"+getIdSector()+"' AND PK_NICHO = '"+arrIdsNicho[i]+"'";
					 rs = db.getDatos(sql2);
				
				try {
					if(!rs.next()){
					
					 sql = "INSERT INTO SORTEOS_PARALELOS_NICHOS" +
							" (PK_SORTEO_PARALELO, PK_SECTOR, PK_NICHO, PK_USUARIO, FECHA_R)" +
							" VALUES ('" + getIdParalelo() + "'," + getIdSector() + ",'" + arrIdsNicho[i] + "'," + sesion.pkUsuario + ", GETDATE())";
					
					System.out.println("insert SORTEOS_PARALELOS_NICHOS: " + sql);
					
					 db.execQuerySelectId(sql);	
					 
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
		
			}		 
		}	
				
		
		return 0;
	}



public int EjecutarSPNichos(String flag,SesionDatos sesion) {		
	
	System.out.println("getIdParalelo():" + getIdParalelo());	
	
	System.out.println("sesion.pkUsuario:" + sesion.pkUsuario);	
	
	
	
			Parametros pDB_sorteParalelo;
			//Parametros pDB_sectorId;
			//Parametros pDB_nichoId;
			Parametros pDB_usuarioId;
			Parametros pDB_flag;
			List<Parametros> listaParams;		
		
			
			listaParams = new ArrayList<Parametros>();
			
			pDB_sorteParalelo = new Parametros();
			pDB_sorteParalelo.setParamName("sorteoParaleloId");
			pDB_sorteParalelo.setParamValue(String.valueOf(getIdParalelo()));
			listaParams.add(pDB_sorteParalelo);
			
			
			pDB_usuarioId = new Parametros();
			pDB_usuarioId.setParamName("usuarioId");
			pDB_usuarioId.setParamValue(String.valueOf(sesion.pkUsuario));
			listaParams.add(pDB_usuarioId);
			
			
			pDB_flag = new Parametros();
			pDB_flag.setParamName("flag");
			pDB_flag.setParamValue(flag);
			listaParams.add(pDB_flag);
			System.out.println("flag:" + flag);	
			
			db.execStoreProcedure("spSorteosParalelosNichosColaborAsignar", listaParams);
			
			
			return 0;
			
			
			
		}		 
	


	
	
	
	
	public int guardarNichosAsignados2(String strmnichos,String[] arrIdsNicho, SesionDatos sesion) {		
					
		
		//String strmnichoId =  "'" + strmnichos.replace(",", "','") + "'";
		
		/*String sql = "SELECT * FROM VCOLABORADORES_ABONOS_SALDOS"
		+ " WHERE SALDO = 0 AND MONTO > 0 	"
	    + " AND PK_SECTOR=" + getIdSector()			      
        + " AND PK_SORTEO=" + getIdSorteo()		       
        + " AND PK_NICHO IN(" + strmnichos2 + ")";
        System.out.println("SELECT: VCOLABORADORES_ABONOS_SALDOS:" + sql);
          ResultSet res = db.getDatos(sql);	*/	

		
	String strmNichos =  "'" + strmnichos + "'";
	System.out.println("strmNichos:" + strmNichos);	
	
		
		
	
		
		Parametros pDB_sorteParalelo;
		Parametros pDB_sectorId;
		Parametros pDB_nichoIdList;
		Parametros pDB_usuarioId;
		List<Parametros> listaParams;
		
				
		
		//try {
			
			//while (res.next()) {				
					
				
					
					listaParams = new ArrayList<Parametros>();
					
					pDB_sorteParalelo = new Parametros();
					pDB_sorteParalelo.setParamName("sorteoParaleloId");
					pDB_sorteParalelo.setParamValue(String.valueOf(getIdParalelo()));
					listaParams.add(pDB_sorteParalelo);

					pDB_sectorId = new Parametros();
					pDB_sectorId.setParamName("sectorId");
					pDB_sectorId.setParamValue(String.valueOf(getIdSector()));//CHECAR
					listaParams.add(pDB_sectorId);

					
				    pDB_nichoIdList= new Parametros();
					pDB_nichoIdList.setParamName("nichoIdList");					
					pDB_nichoIdList.setParamValue(strmNichos);
					listaParams.add(pDB_nichoIdList);
					
				/*	pDB_nichoId = new Parametros();
					pDB_nichoId.setParamName("nichoId");
					//pDB_nichoId.setParamValue(arrIdsNicho[i]);
					pDB_nichoId.setParamValue(strmnichos);
					listaParams.add(pDB_nichoId);*/
					
					pDB_usuarioId = new Parametros();
					pDB_usuarioId.setParamName("usuarioId");
					pDB_usuarioId.setParamValue(String.valueOf(sesion.pkUsuario));
					listaParams.add(pDB_usuarioId);
					
					db.execStoreProcedure("spSorteosParalelosNichosColaborAsignar2", listaParams);
				//}
			
		
		
		return 0;
	}
	
	
	
	
	
public int ActualizarColaboradores_SP(String[] arrIdsNicho, SesionDatos sesion) {
		
		
		Parametros pDB_sorteParalelo;
		Parametros pDB_sortParalNicho;
		Parametros pDB_sectorId;
		Parametros pDB_nichoId;
		Parametros pDB_usuarioId;
		List<Parametros> listaParams;
		
		if (arrIdsNicho != null) {
			for (int i = 0; i < arrIdsNicho.length; i++) {
				
				
				String sql = "DELETE SORTEOS_PARALELOS_NICHOS_COLABORADORES"
						+ " WHERE PK_SORTEO_PARALELO=" + getIdParalelo()
						+ " AND PK_SORTEO_PARALELO_NICHO=" + getIdParaleloNicho()		      
				        + " AND PK_SECTOR=" + getIdSector()
				        + " AND PK_NICHO=" + arrIdsNicho[i];
				System.out.println("delete: SORTEOS_PARALELOS_NICHOS_COLABORADORES:" + sql);
				 db.execQuery(sql);		
				
				
				//System.out.println("arrIdsNicho[i]=" + arrIdsNicho[i]);
				listaParams = new ArrayList<Parametros>();
				
				pDB_sorteParalelo = new Parametros();
				pDB_sorteParalelo.setParamName("sorteoParaleloId");
				pDB_sorteParalelo.setParamValue(String.valueOf(getIdParalelo()));
				listaParams.add(pDB_sorteParalelo);
				
				/*NUEVO*/
				pDB_sortParalNicho = new Parametros();
				pDB_sortParalNicho.setParamName("sortParalNicho");
				pDB_sortParalNicho.setParamValue(String.valueOf(getIdParaleloNicho()));
				listaParams.add(pDB_sortParalNicho);
				

				pDB_sectorId = new Parametros();
				pDB_sectorId.setParamName("sectorId");
				pDB_sectorId.setParamValue(String.valueOf(getIdSector()));
				listaParams.add(pDB_sectorId);

				pDB_nichoId = new Parametros();
				pDB_nichoId.setParamName("nichoId");
				pDB_nichoId.setParamValue(arrIdsNicho[i]);
				listaParams.add(pDB_nichoId);
				
				pDB_usuarioId = new Parametros();
				pDB_usuarioId.setParamName("usuarioId");
				pDB_usuarioId.setParamValue(String.valueOf(sesion.pkUsuario));
				listaParams.add(pDB_usuarioId);
				
				db.execStoreProcedure("spSorteosParalelosNichosColaborActualizar", listaParams);
			}		 
		}
		
	
		
		
		return 0;
	}
	
	public int borraNichosAsignados() {
		String sql = "DELETE SORTEOS_PARALELOS_NICHOS"
				+ " WHERE PK_SORTEO_PARALELO=" + getIdParalelo()
				+ " AND PK_SECTOR=" + getIdSector();
		System.out.println("sql:" + sql);
		return db.execQuery(sql);
		
		//db.execStoreProcedure(name_SP)
	}
	
	public int guardaNicho(String idNicho, SesionDatos sesion) {
		String sql = "INSERT INTO SORTEOS_PARALELOS_NICHOS"
				+ " (PK_SORTEO_PARALELO,PK_SECTOR,PK_NICHO,PK_USUARIO,FECHA_R)"
				+ " VALUES (" + getIdParalelo() + "," + getIdSector() + ","
				+ idNicho + "," + sesion.pkUsuario + ",GETDATE())";
		System.out.println("sql:" + sql);
		return db.execQuery(sql);
	}
	
	public int borrarParaleloNichos() {
		String sql = "DELETE SORTEOS_PARALELOS_NICHOS WHERE PK1=" + getIdParalelo();
		System.out.println("DELETE SORTEOS_PARALELOS_NICHO:" + sql);
		
		return db.execQuery(sql);
	}
	
	
public boolean getExistParaleloColaboradores(){			
		
		boolean totalbolcolab = false;		
		
		String sql="SELECT  COUNT(PK_SORTEO_PARALELO_NICHO) AS 'BCOLAB' FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES "
				+ "WHERE PK_SORTEO_PARALELO_NICHO = '"+this.getIdParalelo()+"'";		
		
		System.out.println(">>>SELECT SORTEOS_PARALELOS_NICHOS_COLABORADORES  total : "+sql);
		ResultSet rs = db.getDatos(sql);		
	
		
		try {
			if(rs.next()) {					
				
				if( rs.getInt("BCOLAB") > 0 )											
					totalbolcolab = true;				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totalbolcolab;
	}



public boolean getExistColaboradores(){			
	
	boolean totalbolcolab = false;		
	
	String sql="SELECT  COUNT(PK_SORTEO_PARALELO_NICHO) AS 'BCOLAB' FROM SORTEOS_PARALELOS_NICHOS_COLABORADORES "
			+ "WHERE PK_SORTEO_PARALELO = '"+this.getIdParalelo()+"'";		
	
	System.out.println(">>>SELECT SORTEOS_PARALELOS_NICHOS_COLABORADORES  total : "+sql);
	ResultSet rs = db.getDatos(sql);		

	
	try {
		if(rs.next()) {					
			
			if( rs.getInt("BCOLAB") > 0 )											
				totalbolcolab = true;				
         
		}
	} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	
	
	return totalbolcolab;
}

	
	
}
