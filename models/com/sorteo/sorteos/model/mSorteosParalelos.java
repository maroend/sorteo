package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.core.Factory;
import com.core.Factory.LOG;
import com.core.Parametros;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.sorteo.poblacion.model.mNichos;
import com.sorteo.poblacion.model.mSectores;

public class mSorteosParalelos extends SuperModel {
	

	private long idParalelo;
	private int idSector;
	private int idSorteo;
	private String nombre;
	private String fechaLimPago;
	private int totalregistros = 0;
	//private String clave;
	//private String[] sectores;
	//private String sorteo;  
	//private String descripcion;  
	//private String fechainico;
	//private String fechatermino;
	//private String imagen;
	//private String zona;
	//private int activo;

	public long getIdParalelo() {
		return this.idParalelo;
	}

	public void setIdParalelo(long idParalelo) {
		this.idParalelo = idParalelo;
	}
	
	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}
	
	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int id) {
		this.idSector = id;
	}

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}

	/*
	public String getClave() {
		return clave;
	}
    
	
	public String[] getSectores() {
		return sectores;
	}


	public void setSectores(String[] sectores) {
		this.sectores = sectores;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String getSorteo() {
		return sorteo;
	}


	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getFechainico() {
		return fechainico;
	}


	public void setFechainico(String fechainico) {
		this.fechainico = fechainico;
	}


	public String getFechatermino() {
		return fechatermino;
	}


	public void setFechatermino(String fechatermino) {
		this.fechatermino = fechatermino;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	//*/

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre == null ? "" : nombre;
	}
	
	public String getFechaLimPago() {
		return this.fechaLimPago;
	}
	
	public void setFechaLimPago(String fechaLimPago) {
		this.fechaLimPago = fechaLimPago == null ? "" : fechaLimPago;
	}
	
	/*
	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona == null ? "" : zona;
	}
	*/

	public mSorteosParalelos() {
		// TODO Auto-generated constructor stub
		this.nombre = "";
		//this.zona = "";
	}
	
	public String consultaSorteo(int idsorteo) {
		return super.consultaClaveNombreSorteo(idsorteo);
	}

	/*
	public String Sorteo(int idsorteo){
	   	 
	   	 db.con();
	   	 String sql = "SELECT * FROM SORTEOS WHERE PK1 = "+idsorteo+"";
	   	String sorteo = null;
	   	 ResultSet rs = db.getDatos(sql);
	   	 try {
				if (rs != null) {
					
					 while(rs.next())
					   {
						
						 sorteo = rs.getString("CLAVE")+"-"+rs.getString("SORTEO");
					   }
					
					rs.close(); 			
				}
	   	} catch (SQLException e) { Factory.Error(e, sql); }
	   	 
	   	 return sorteo;
	   	 
	    }
	
 
	
	public ResultSet listarModal(){
	    	
	    	String sql = "SELECT PK1 FROM SECTORES";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
	}
	 
	 
	public int contarModal(String search, int idsorteo) {
		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO = " + idsorteo;

		if (search != "") {
			sql += " AND ((SECTOR LIKE '%" + search + "%') OR (CLAVE LIKE '%" + search + "%'))  ";
		}

		int numero = db.ContarFilas(sql);
		return numero;
	}
	 
	public ResultSet paginacionModal(int pg, int numreg, String search,
			int idsorteo) {
		
		String sql = "SELECT S.PK1, S.CLAVE, S.SECTOR, S.DESCRIPCION"
				+ ",(select TOP 1 PK_SECTOR from SORTEOS_ASIGNACION_SECTORES where PK_SECTOR = S.PK1 AND PK_SORTEO = '" + idsorteo + "') AS 'PK_SECTOR'"
				+ " FROM SECTORES S WHERE PK_SORTEO = " + idsorteo;

		if (search != "") {
			sql += " AND ((S.SECTOR LIKE '%" + search
					+ "%') OR (S.CLAVE LIKE '%" + search + "%'))  ";
		}

		sql += " ORDER BY S.PK1 ASC ";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if
															// you need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println("ASIGNACION SECTORES SQL:>>>" + sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	 
	
	
	
	 public ResultSet listar(){
	    	
	    	String sql = "SELECT * FROM SORTEOS_ASIGNACION_SECTORES";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 */
	
	public int contar(SesionDatos sesion,String search) {
		String sql_search = " ";
		if (search != "")
			sql_search = " AND (SP.NOMBRE LIKE '%" + search + "%')";
		
		String sql = "SELECT COUNT(*) AS 'VALUE' FROM SORTEOS_PARALELOS SP"
				+ " WHERE SP.PK_SORTEO=" + sesion.pkSorteo + sql_search;

		this.setTotalregistros(db.getValue(sql, 0));
		return this.getTotalregistros() ;
	}

	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion){
		String sql_search = " ";
		if (search != "")
			sql_search = " AND (SP.NOMBRE LIKE '%" + search + "%')";
		
		String sql = "SELECT SP.* FROM SORTEOS_PARALELOS SP"
				+ " WHERE SP.PK_SORTEO=" + sesion.pkSorteo + sql_search;
		
		//System.out.println("paralelos:" + sql);

		return db.getDatos(sql);
	}
	
	/*
	public HashMap<Long, String> getNichosPorSector(int idSorteo, int idSector) {
		HashMap<Long, String> map = new HashMap<Long, String>();
		String sql = 
			"SELECT N.PK1, CONCAT(N.CLAVE,' - ', N.NICHO)"
			+ " FROM SORTEOS_ASIGNACION_NICHOS SAN, NICHOS N"
			+ " WHERE SAN.PK_NICHO=N.PK1 AND SAN.PK_SORTEO=" + idSorteo + " AND SAN.PK_SECTOR=" + idSector
			+ " ORDER BY N.CLAVE";
		
		ResultSet res = db.getDatos(sql);
		try{
			while (res.next()) {
				m
				map.put( res.getLong("PK1"), res.getString("NICHO"));
			}
			res.close();
		}catch(Exception ex) { }
		
		return map;
	}
	*/
	
	public int insertParalelo(SesionDatos sesion)
	{
		String sql = "INSERT INTO SORTEOS_PARALELOS" +
				" (NOMBRE, PK_SORTEO, PK_ZONA, FECHA_LIMITE, PK_USUARIO, FECHA_R)" +
				" VALUES ('" + getNombre() + "'," + sesion.pkSorteo + "," + 1 + ",'" + getFechaLimPago() + "'," + sesion.pkUsuario + ", GETDATE())";
		
		return db.execQuerySelectId(sql);
	}
	
	public int borrarParalelo() {
		String sql = "DELETE SORTEOS_PARALELOS WHERE PK1=" + getIdParalelo();
		
		return db.execQuery(sql);
	}
	
	public void editarParalelo() {
		// ,(SELECT SZ.ZONA FROM SORTEOS_ZONAS SZ WHERE SZ.PK1=SP.PK_ZONA) AS 'ZONA'
		String sql = "SELECT * FROM SORTEOS_PARALELOS SP WHERE PK1=" + getIdParalelo();
		
		ResultSet res = db.getDatos(sql);
		try{
			if (res.next()) {
				this.setNombre(res.getString("NOMBRE"));
				this.setIdSorteo(res.getInt("PK_SORTEO"));
				//this.setZona(res.getString("ZONA"));
			}
		} catch(Exception ex) { }
	}
	
	public String consultarSorteoParalelo() {

		String sql = "SELECT * FROM VSORTEOS_PARALELOS SP WHERE PK1=" + getIdParalelo();		
		ResultSet res = db.getDatos(sql);
		String resultado = "";
		try {
			if (res.next()) {
				this.setFechaLimPago(res.getString("FECHA_LIMITE"));
				this.setNombre(res.getString("NOMBRE"));
				this.setIdSorteo(res.getInt("PK1"));
				resultado = res.getString("FECHA_LIMITE") + "#%#"
						+ res.getString("NOMBRE");
			}
		} catch(Exception ex) { resultado = ""; }
		return resultado;
	}
	
	public int editarSorteoParalelo(SesionDatos sesion) {

		String sql = " UPDATE SORTEOS_PARALELOS "
		           + "    SET NOMBRE       = '" + getNombre()       + "',"
				   + "        FECHA_LIMITE = '" + getFechaLimPago() + "',"
				   + "        PK_USUARIO   =  " + sesion.pkUsuario   + ","
				   + "        FECHA_M      = GETDATE() "
		           + "  WHERE PK1 = " + getIdParalelo();
		
		System.out.println(sql);
		
		return db.execQuery(sql);
	}
	

	/**
	public ArrayList<mNichos> getNichosPorSector()
	{
		ArrayList<mNichos> list = new ArrayList<mNichos>();
		
		String sql_filtro = "";
		//if (idParalelo > 0)
		sql_filtro += " AND SPN.PK_SORTEO_PARALELO=" + getIdParalelo() + " AND SPN.PK_SECTOR=" + getIdSector();
		
		String sql = 
			"SELECT N.*"
			+ ",(SELECT COUNT(SPN.PK1) FROM SORTEOS_PARALELOS_NICHOS SPN WHERE SPN.PK_NICHO=N.PK1" + sql_filtro + ") AS 'CHECK'"
			+ " FROM SORTEOS_ASIGNACION_NICHOS SAN, NICHOS N"
			+ " WHERE SAN.PK_NICHO=N.PK1 AND SAN.PK_SORTEO=" + getIdSorteo() + " AND SAN.PK_SECTOR=" + getIdSector()
			+ " ORDER BY N.CLAVE";
		
		System.out.println("Nichos:" + sql);
		
		ResultSet res = db.getDatos(sql);
		try{
			while (res.next()) {
				mNichos nicho = new mNichos();
				nicho.setId(res.getInt("PK1"));
				nicho.setClave(res.getString("CLAVE"));
				nicho.setNicho(res.getString("NICHO"));
				nicho.setDescripcion(res.getString("CHECK"));
				list.add(nicho);
			}
			res.close();
		}catch(Exception ex) { }
		
		return list;
	}
	//*/
	/*
	public ArrayList<mSectores> getSectoresPorSorteo(int idSorteo)
	{
		ArrayList<mSectores> list = new ArrayList<mSectores>();
		String sql =
			"SELECT S.*"
			+ " FROM SORTEOS_ASIGNACION_SECTORES SAS, SECTORES S"
			+ " WHERE SAS.PK_SECTOR=S.PK1 AND SAS.PK_SORTEO=" + idSorteo
			+ " ORDER BY S.CLAVE";
		
		System.out.println("Sectores:" + sql);
		
		ResultSet res = db.getDatos(sql);
		try{
			while (res.next()) {
				mSectores sector = new mSectores();
				sector.setId(res.getInt("PK1"));
				sector.setClave(res.getString("CLAVE"));
				sector.setSector(res.getString("SECTOR"));
				
				list.add(sector);
			}
			res.close();
		}catch(Exception ex) { }
		
		return list;
	}
	*/
	/*
	public int guardarNichosAsignados(String[] arrIdsNicho, SesionDatos sesion) {
		if (arrIdsNicho != null) {
			borraNichosAsignados();
			for (int i = 0; i < arrIdsNicho.length; i++) {
				int result = guardaNicho(arrIdsNicho[i], sesion);
				System.out.println("result=" + result);
			}
		}
		return 0;
	}
	
	public int borraNichosAsignados() {
		String sql = "DELETE SORTEOS_PARALELOS_NICHOS"
				+ " WHERE PK_SORTEO_PARALELO=" + getIdParalelo()
				+ " AND PK_SECTOR=" + getIdSector();
		
		return db.execQuery(sql);
	}
	
	public int guardaNicho(String idNicho, SesionDatos sesion) {
		String sql = "INSERT INTO SORTEOS_PARALELOS_NICHOS"
				+ " (PK_SORTEO_PARALELO,PK_SECTOR,PK_NICHO,PK_USUARIO,FECHA_R)"
				+ " VALUES (" + getIdParalelo() + "," + getIdSector() + ","
				+ idNicho + "," + sesion.pkUsuario + ",GETDATE())";
		return db.execQuery(sql);
	}
	//*/
	
	public ArrayList<String> getNichosAgrupados() {
		String sql = "SELECT DISTINCT(CONCAT(N.CLAVE,' - ',N.NICHO)) AS 'NICHO', N.CLAVE"
				+ " FROM SORTEOS_PARALELOS_NICHOS SPN, NICHOS N"
				+ " WHERE SPN.PK_NICHO=N.PK1 AND SPN.PK_SORTEO_PARALELO=" + getIdParalelo();
		ResultSet res = db.getDatos(sql);
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (res.next()){
				list.add(res.getString("NICHO"));
			}
		}catch (Exception ex) { }
		return list;
	}
	
	
public boolean getExistParaleloNicho(){			
		
		boolean totalbolNicho = false;		
		
		String sql="SELECT  COUNT(PK_SORTEO_PARALELO) AS 'BNICHO' FROM SORTEOS_PARALELOS_NICHOS "
				+ "WHERE PK_SORTEO_PARALELO = '"+this.getIdParalelo()+"'";		
		
		System.out.println(">>>SELECT SORTEOS_PARALELOS_NICHOS total : "+sql);
		ResultSet rs = db.getDatos(sql);		
	
		
		try {
			if(rs.next()) {					
				
				if( rs.getInt("BNICHO") > 0 )											
					totalbolNicho = true;				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totalbolNicho;
	}


public int ActualizarColaboradores_SP( SesionDatos sesion) {
	
	
	Parametros pDB_sorteParalelo;
	Parametros pDB_sortParalNicho;
	Parametros pDB_sectorId;
	Parametros pDB_nichoId;
	Parametros pDB_usuarioId;
	List<Parametros> listaParams;
	
	//ELIMINA LOS COLABORADORES DE todos los nichos del sorteo paralelo n
	String sql = "DELETE SORTEOS_PARALELOS_NICHOS_COLABORADORES"
			+ " WHERE PK_SORTEO_PARALELO=" + getIdParalelo();			
	System.out.println("delete: SORTEOS_PARALELOS_NICHOS_COLABORADORES:" + sql);
	 db.execQuery(sql);		
	
	
	String sql2 ="SELECT * FROM SORTEOS_PARALELOS_NICHOS "
			+ "WHERE PK_SORTEO_PARALELO = '"+this.getIdParalelo()+"'";		
	System.out.println(">>>SELECT SORTEOS_PARALELOS_NICHOS total : "+sql2);
	ResultSet rs = db.getDatos(sql2);	
	
	
	try {
	
	if (rs != null) {
		
		while (rs.next()) {				
		
			listaParams = new ArrayList<Parametros>();			
			pDB_sorteParalelo = new Parametros();
			pDB_sorteParalelo.setParamName("sorteoParaleloId");
			pDB_sorteParalelo.setParamValue(String.valueOf(getIdParalelo()));
			listaParams.add(pDB_sorteParalelo);
			
			/*NUEVO*/
			pDB_sortParalNicho = new Parametros();
			pDB_sortParalNicho.setParamName("sortParalNicho");		
			pDB_sortParalNicho.setParamValue(rs.getString("PK1"));			
			listaParams.add(pDB_sortParalNicho);
			

			pDB_sectorId = new Parametros();
			pDB_sectorId.setParamName("sectorId");
			pDB_sectorId.setParamValue(rs.getString("PK_SECTOR"));
			listaParams.add(pDB_sectorId);

			pDB_nichoId = new Parametros();
			pDB_nichoId.setParamName("nichoId");
			pDB_nichoId.setParamValue(rs.getString("PK_NICHO"));
			listaParams.add(pDB_nichoId);
			
			pDB_usuarioId = new Parametros();
			pDB_usuarioId.setParamName("usuarioId");
			pDB_usuarioId.setParamValue(String.valueOf(sesion.pkUsuario));
			listaParams.add(pDB_usuarioId);
			
			db.execStoreProcedure("spSorteosParalelosNichosColaborActualizar", listaParams);
		}		 
	}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return 0;
}



	
	
	
	 /*
	 
	 
	public boolean ExisteSorteo(String usr){
		   	
		   	String sql = "SELECT PK1 FROM SORTEOS WHERE CLAVE = '"+usr+"'";
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if( rs.next()){
					 
					return true;
					
				}else{
					
					return false;
				}
				
		   	} catch (SQLException e) { Factory.Error(e, sql); }
			return false;
	}
	
	public int numeroTalonarios(mSorteosParalelos obj){
		 
		 db.con();
		 String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = "+obj.getIdsorteo()+" AND PK_SECTOR = '"+obj.getIdSector()+"'  ";
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		 } catch (SQLException e) { Factory.Error(e, sql); }
		 
		 return max;
	}
	
	public int numeroBoletos(mSorteosParalelos obj){
		 
		 db.con();
		 
		 String sql = "SELECT COUNT(*) AS MAX FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO = "+obj.getIdsorteo()+" AND PK_SECTOR = '"+obj.getIdSector()+"' ";
		 ResultSet rs = db.getDatos(sql);
		 int max = 0;
		 try {
			if (rs != null && rs.next()) {
			    	max = rs.getInt("MAX");
			    }
		 } catch (SQLException e) { Factory.Error(e, sql); }
		 
		 return max;
	}
	
	public int MontoSector(mSorteosParalelos obj){
		 
		 db.con();
		 String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO = "+obj.getIdsorteo()+" AND PK_SECTOR = '"+obj.getIdSector()+"' ";
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
		
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM [SORTEOS_SECTORES_TALONARIOS]"
				+ " WHERE PK_SECTOR = " + getIdSector()
				+ " AND PK_SORTEO = " + getIdsorteo();

		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next())
				return rs.getInt("MAX") > 0;

		} catch (SQLException e) { Factory.Error(e, sql); }
		return false;
	}

	public int eliminaCarga(SesionDatos sesion) {
		
		int val = borraTalonariosDeSector(sesion.pkSorteo, sesion.pkSector);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaCarga", sesion.toShortString());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }

		return val;
	}
	
	
	public int borraTalonariosDeSector(int pkSorteo, int pkSector) {
		String sql="";

		// PASO 1: Se actualiza la columna de ASIGNADO EN EL BOLETO de sorteo
		sql +=
				" UPDATE SORTEOS_TALONARIOS SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_TALONARIOS RSO, SORTEOS_SECTORES_TALONARIOS AS RSE WHERE RSO.PK_SORTEO=" + pkSorteo + " AND RSO.PK_TALONARIO=RSE.PK_TALONARIO AND RSE.PK_SECTOR=" + pkSector + 
				" UPDATE SORTEOS_BOLETOS    SET ASIGNADO=0, FECHA_M=GETDATE() FROM SORTEOS_BOLETOS RSO,    SORTEOS_SECTORES_BOLETOS AS RSE    WHERE RSO.PK_SORTEO=" + pkSorteo + " AND RSO.PK_BOLETO=RSE.PK_BOLETO       AND RSE.PK_SECTOR=" + pkSector;

		// PASO 2) Se borran los boletos y talonarios asignados a los sub-niveles
		sql +=
				" DELETE [SORTEOS_COLABORADORES_TALONARIOS] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector +
				" DELETE [SORTEOS_COLABORADORES_BOLETOS]    WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector +
				" DELETE [SORTEOS_NICHOS_TALONARIOS]        WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector +
				" DELETE [SORTEOS_NICHOS_BOLETOS]           WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector;
		
		// PASO 3) Se borran las relaciones de boletos y talonarios asignados al sector
		sql +=
				" DELETE [SORTEOS_SECTORES_TALONARIOS]   WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector +
				" DELETE [SORTEOS_SECTORES_BOLETOS]      WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector;
		
		// PASO 4) Se borra tambien el seguimiento
		sql +=
				" DELETE [SEGUIMIENTO] WHERE PK_SORTEO=" + pkSorteo + " AND PK_SECTOR=" + pkSector
				+ " AND NOT (NIVEL='Boveda' OR NIVEL='Sorteo')";

		return db.execQuery(sql);
	}
	
	public int eliminaSector(SesionDatos sesion) {
		String sql =
				" DELETE [SORTEOS_ASIGNACION_SECTORES]"
				+ " WHERE PK_SORTEO=" + sesion.pkSorteo
				+ " AND PK_SECTOR=" + sesion.pkSector;
		int val = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaSector", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return val;
	}
	//*/
}


