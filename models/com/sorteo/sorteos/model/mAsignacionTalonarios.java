package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.core.Factory;
import com.core.Factory.LOG;
import com.core.Parametros;
import com.core.Seguimiento;
import com.core.Seguimiento.ASIGNACION;
import com.core.SesionDatos;
import com.core.SuperModel;

import java.util.List;


public class mAsignacionTalonarios extends SuperModel{
	private static final String pre_formato = "FC1 - ";
	private int idSorteo;
	private int idBoleto;	

	private int idtalonario;
	private int idSector;
	private String clave;
	private String sorteo;
	private String sector;
	private String[] arrTalonarios;
	private String descripcion;
	
	private int idnicho;	
	private int idColaborador;
	
	private String cadenaboletos;
	private String cadenatalonarios;
	
	
	private int regIdFormato;
	
	
	public int getRegIdFormato() {
		return regIdFormato;
	}

	public void setRegIdFormato(int regIdFormato) {
		this.regIdFormato = regIdFormato;
	}

	public int getIdnicho() {
		return idnicho;
	}

	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
	}

	public int getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(int idColaborador) {
		this.idColaborador = idColaborador;
	}

	
	
	// ________________________________
	private String folioTalonario;
	private String formato;
	
	int totalregistros = 0;

	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}

	public String getFolioTalonario() {
		return folioTalonario;
	}

	public void setFolioTalonario(String folioTalonario) {
		this.folioTalonario = folioTalonario;
	}

	public int getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(int idBoleto) {
		this.idBoleto = idBoleto;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public ResultSet consultaBoletos() {
		
	//	String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO,B.RETORNADO, B.FOLIODIGITAL, "
		//		+ "(select TOP 1 COUNT(PK1) FROM COMPRADORES WHERE  PK_BOLETO = S.PK_BOLETO) AS 'COMPRADOR' "
				/*
				+" (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
				+" (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
				+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
				+"(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
				+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
				+ "(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
				+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
				*/
			//	+ "FROM SORTEOS_SECTORES_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND S.PK_SORTEO = '"+getIdSorteo()+"' AND S.PK_SECTOR = "+getIdSector()+" AND S.PK_TALONARIO = "+getFolioTalonario()
			//	;
		
		
		
		
		String sql = "SELECT SB.PK_BOLETO,SB.PK_ESTADO AS VENDIDO, SB.ABONO, SB.FOLIO, SB.COSTO, SB.TALONARIO, SB.FORMATO, SB.PK_TALONARIO,SB.RETORNADO, SB.FOLIODIGITAL, "
				+ "(select TOP 1 COUNT(CP.PK1) FROM COMPRADORES CP, COMPRADORES_BOLETOS CPB  WHERE  CP.PK1 = CPB.PK_COMPRADOR  AND CPB.PK_BOLETO = SB.PK_BOLETO  ) AS 'COMPRADOR' "
				/*
				+" (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
				+" (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
				+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
				+"(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
				+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
				+ "(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
				+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
				*/
				+ "FROM VSECTORES_BOLETOS SB WHERE SB.PK_SORTEO = '"+getIdSorteo()+"' AND SB.PK_SECTOR = "+getIdSector()+" AND SB.PK_TALONARIO = "+getIdtalonario();
		
		
		
		

		System.out.println(">>>>SERIE "+sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	public void consultaNichoColaboradorDeTalonario() {
		String sql;
		ResultSet res;
		this.setIdnicho(0);
		this.setIdColaborador(0);
		try {
			sql = "select TOP 1 PK_NICHO from VNICHOS_TALONARIOS WHERE PK_SORTEO="+getIdSorteo()+" AND PK_SECTOR="+getIdSector()+" AND PK_TALONARIO = " + getIdtalonario();
			res = db.getDatos(sql);
			if (res != null && res.next()){
				this.setIdnicho(res.getInt("PK_NICHO"));
			}
			res.close();
			
			sql = "select TOP 1 PK_COLABORADOR from VCOLABORADORES_TALONARIOS WHERE PK_SORTEO="+getIdSorteo()+" AND PK_SECTOR="+getIdSector()+" AND PK_TALONARIO = " + getIdtalonario();
			res = db.getDatos(sql);
			if (res != null && res.next()){
				this.setIdColaborador(res.getInt("PK_COLABORADOR"));
			}
			res.close();
		}
		catch(Exception ex) { }
	}
	// ________________________________

	private String imagen;
	private int activo;

	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idSector) {
		this.idSector = idSector;
	}

	public String getClave() {
		return clave;
	}

	public String[] getTalonarios() {
		return arrTalonarios;
	}

	public void setTalonarios(String[] arrTalonarios) {
		this.arrTalonarios = arrTalonarios;
	}

	public int getIdtalonario() {
		return idtalonario;
	}

	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
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

	public String getSector() {
		return this.sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public void init() {

		idSorteo = 0;
		idBoleto = 0;
		idtalonario = 0;
		idSector = 0;
		clave = "";
		sorteo = "";
		sector = "";
		arrTalonarios = null;
		descripcion = "";
		activo = 1;
		imagen = "";
		folioTalonario = "";
		formato = "";

	}

	public mAsignacionTalonarios() {
		init();
	}
	

	/*
	public boolean estaBoletoAsignadoSector() {

		String sql = "SELECT * FROM SORTEOS_BOLETOS WHERE PK_SORTEO ='"
				+ getIdSorteo()
				+ "' AND PK_BOLETO ='"+ getIdBoleto()+ "' AND ASIGNADO=1";

		ResultSet rs = db.getDatos(sql);
		System.out.print(sql);

		try {

			return rs.next();

		} catch (SQLException e) { Factory.Error(e, sql); }

		return false;
	}
	//*/

	/*
	public ResultSet ObtenerBoletosTalonario() {
		String sql = "SELECT * FROM BOLETOS WHERE TALONARIO ='"
				+ getFolioTalonario() + "' AND  SORTEO='"
				+ getIdSorteo() + "' ";

		System.out.println(sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	*/

	public void consultaSorteo() {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 ='" + getIdSorteo()
				+ "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				this.setSorteo(rs.getString("SORTEO"));

			}
		} catch (SQLException e) { Factory.Error(e, sql); }

	}

	public String Sector(int idsector) {

		db.con();
		String sql = "SELECT * FROM SECTORES WHERE PK1 = " + idsector + "";
		String sector = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					sector = /*rs.getString("CLAVE") + "-"
							+ */rs.getString("SECTOR");
				}

				rs.close();
			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return sector;

	}

	public ResultSet listarModalBoletos() {

		String sql = "SELECT PK1 FROM BOLETOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarModalBoletos(int idsorteo, String search) {
		//String sql = "SELECT B.PK1 FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+idsorteo+"'";
		
		/*String sql = "SELECT COUNT(*) AS 'MAX'";
		sql += " FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+idsorteo+"'";*/
		
		
		String sql = "SELECT COUNT(*) AS 'MAX'";
		sql += " FROM BOLETOS B ";
		
		if (search != "") {
			sql += " WHERE ((B.FOLIO = '" + search + "') OR (B.PK_TALONARIO = '" + search + "'))   ";
		}
		
		System.out.println(">>>>SQL COUNT:" + sql);
		
		int numero = db.Count(sql);

		return numero;
	}

	public ResultSet paginacionModalBoletos(int pg, int numreg, String search,int idsorteo) {
		
		/*String sql = "SELECT B.PK1,B.FOLIO,B.COSTO, B.PK_TALONARIO, B.TALONARIO, B.FORMATO, B.SORTEO, SB.ASIGNADO AS ASIGNADO ";
		sql += "FROM SORTEOS_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '"+idsorteo+"'";*/
		
		
		String sql = "SELECT B.PK1,B.FOLIO,B.COSTO, B.PK_TALONARIO, B.FOLIO_TALONARIO, B.FORMATO, B.ASIGNADO_SECTOR AS ASIGNADO ";
		sql += " FROM  vBOLETOS B ";
		
		
		if (search != "") {
			sql += " WHERE ((B.FOLIO = '" + search + "') OR (B.FOLIO_TALONARIO = '" + search + "'))   ";
		}
	                 	  
		sql += "ORDER BY B.PK_TALONARIO ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println(">>>>SQL :" + sql);
		
		ResultSet rs = db.getDatos(sql);

		return rs;
	}

	/*
	 * public ResultSet listarModal() {
	 * 
	 * String sql = "SELECT PK1 FROM SORTEOS_TALONARIOS"; ResultSet rs =
	 * db.getDatos(sql); return rs;
	 * 
	 * } //
	 */

	public int contarModal(int idsorteo, String search) {
		/*String sql = "SELECT DISTINCT PK_TALONARIO FROM SORTEOS_TALONARIOS"
				+ " WHERE PK_SORTEO = " + idsorteo;*/
		
		/*String sql = "SELECT T.FOLIO AS FOLIO"				
				+ " FROM SORTEOS_TALONARIOS ST, TALONARIOS T"
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND  PK_SORTEO = '" + idsorteo+"'";*/
		
		
		String sql = "SELECT T.FOLIO AS FOLIO"				
				+ " FROM TALONARIOS T"
				//+ " WHERE PK_SORTEO = '" + idsorteo+"'"
				;
		
		if (search != "") {
			sql += " WHERE T.FOLIO LIKE '%" + search + "%'  ";
			
		//	sql += " AND T.FOLIO LIKE '%" + search + "%'  ";
		}		
		
		
		System.out.println(">>>>SQL CONTAR MODAL SECT:" + sql);

		int numero = db.ContarFilas(sql);
		return numero;
	}

	public ResultSet paginacionModal(int pg, int numreg, String search,int idsorteo) {
		
		/*String sql = "SELECT T.PK1 AS PK1,T.FOLIO AS FOLIO"
				+ ",ST.NUMBOLETOS AS NUMBOLETOS"
				+ ",T.SORTEO"
				+ ",T.FORMATO AS FORMATO"
				+ ",T.MONTO AS MONTO"
				+ ",ST.ASIGNADO AS ASIGNADO"
				+ ",ST.DISPONIBLES"
				+ ",T.DIGITAL"
				+ " FROM SORTEOS_TALONARIOS ST, TALONARIOS T"
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND  PK_SORTEO = '" + idsorteo+"'";*/
		
		
		
		
		String sql = "SELECT * FROM VTALONARIOS_BOLETOS";
	

		if (search != "") {
			//sql += " AND FOLIO LIKE '%" + search + "%'  ";
			sql += " WHERE FOLIO LIKE '%" + search + "%'  ";
			
		}

		sql += " ORDER BY CAST(FOLIO AS INT) ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println(">>>>SQL PAG MODAL SECT:" + sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public int contarTalonarios(int idSector,int idsorteo) {
		//String sql = "SELECT COUNT(RST.PK1) AS 'MAX' FROM SORTEOS_SECTORES_TALONARIOS RST, TALONARIOS T WHERE RST.PK_SECTOR = '"+ idSector + "' AND RST.PK_TALONARIO = T.PK1 AND RST.PK_SORTEO='"+idsorteo+"'";
		
		String sql = "SELECT COUNT(*) AS 'MAX' FROM VSECTORES_TALONARIOS WHERE PK_SECTOR = '"+ idSector + "' AND PK_SORTEO='"+idsorteo+"'";
		
		int numero = 0;
		try {
			ResultSet res = db.getDatos(sql);
			if (res != null && res.next())
				numero = res.getInt("MAX");
		} catch (SQLException e) { Factory.Error(e, sql); }
		
		return numero;
	}
	/*
	HashMap<Long, String> mapSectores;
	HashMap<Long, String> mapNichos;
	public void cargaSectores(){
		mapSectores = new HashMap<Long, String>();
		String sql = "SELECT PK1,SECTOR FROM SECTORES WHERE PK_SORTEO=" + this.idSorteo;
		ResultSet res = db.getDatos(sql);
		try{
			while(res.next()){
				mapSectores.put(res.getLong("PK1"), res.getString("SECTOR"));
			}
		}
		catch(Exception ex){}
	}
	public void cargaNichos(){
		mapNichos = new HashMap<Long, String>();
		String sql = "SELECT PK1,NICHO FROM NICHOS WHERE PK_SORTEO=" + this.idSorteo;
		ResultSet res = db.getDatos(sql);
		try{
			while(res.next()){
				mapNichos.put(res.getLong("PK1"), res.getString("NICHO"));
			}
		}
		catch(Exception ex){}
	}
	//*/

	public ResultSet paginacionTalonarios(int pg, int numreg, String search,int idSector,int idsorteo) {
		
		
		ResultSet rs = null;
		
		/*String sql = "SELECT T.PK1 AS PK1, T.FOLIO, RST.NUMBOLETOS, T.SORTEO, T.FORMATO, RST.MONTO, RST.PK_TALONARIO, T.VENDIDO, T.DIGITAL "
				+ " FROM SECTORES_TALONARIOS RST, TALONARIOS T WHERE RST.PK_SECTOR = '"
				+ idSector + "' AND RST.PK_TALONARIO = T.PK1 AND RST.PK_SORTEO='"+idsorteo+"'";*/
		
		String sql = "SELECT PK_TALONARIO AS PK1, FOLIO, NUMBOLETOS, PK_SORTEO, FORMATO_TALONARIO AS FORMATO, MONTO, PK_TALONARIO, DIGITAL "// T.VENDIDO
				+ " FROM VSECTORES_TALONARIOS"
				+ " WHERE PK_SECTOR = '" + idSector + "'"
				+ "  AND PK_SORTEO='"+idsorteo+"'";


		
		if (search != "") {
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}
		
		//
		String sqltotalreg =  sql;
		
		
		sql += " ORDER BY PK_TALONARIO ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

	        System.out.println(">>>>SQL:" + sql);
		
			rs = db.getDatos(sql);
			
			//total reg	
			System.out.println(">>>>SQL TOTAL REG:" + sqltotalreg);    	
	    	this.setTotalregistros( db.ContarFilas(sqltotalreg));		
		
		
		
		return rs;
	}
	
	
	public void guardarAsignacionSectorTalonario_SP(String talonescadena, SesionDatos sesion, String electronico) {
		
		db.con();
		String DIGITAL = (electronico != null && electronico.compareTo("ELECTRONICO")==0) ? "1" : "0";	
		
		
		System.out.println("TALONARIOS ");		
		
		// TODO - insertar
		  List<Parametros> list = new ArrayList<Parametros>();
		  list.add(new Parametros("COUNT", 0));
		  list.add(new Parametros("PK_SECTOR", getIdSector()));
		  list.add(new Parametros("FORMATO", getFormato()));
		  list.add(new Parametros("USUARIO", sesion.nickName));
		  list.add(new Parametros("DIGITAL", DIGITAL));
		  list.add(new Parametros("LIST", talonescadena));	
		  System.out.println("CADENA tal: "+talonescadena);	
		
		// list.add(new Parametros("StatementType", "InsertMultiple"));
		//  this.setId(db.execStoreProcedureIntId("sp_guardarAsignacionSectorTalonario", list));		
		
		  int result = db.execStoreProcedureIntId("sp_guardarAsignacionSectorTalonario", list);
		  System.out.println(" result:"+result);
		  
		  
		 /* try {
				//this.Log(sesion, LOG.REGISTRO, this, "AsignarTalonariosBoletosCompletos", sesion.toShortString() + ", bols=" + log_list_boletos.toString());
				this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorTalonario", sesion.toShortString() + ", tals=" + talonescadena.toString());
			}catch(Exception ex) { Factory.Error(ex, "Log"); }*/
		
		
	}
	
	
	

	// TODO 
	public void guardarAsignacionSectorTalonario(String[] arrTalonarios, SesionDatos sesion, String electronico) {
		db.con();
		String DIGITAL = (electronico != null && electronico.compareTo("ELECTRONICO")==0) ? "1" : "0";
		
		
		this.cadenaboletos = "";
		String cadenatalonarios = "";		
		double montototal = 0;		
		
		System.out.println("TALONARIOS ");		
		
		/*for (String talonario_id : arrTalonarios)			
		System.out.print(", " + talonario_id);*/
		
		String sql = "";
		ResultSet rs = null;

		for (String talonario_id : arrTalonarios) {
			
			sql = " SELECT * FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO ='"
					+ this.getIdSorteo()
					// + "' AND PK_SECTOR = '"
					// + this.getIdSector()
					+ "' AND PK_TALONARIO = '" + talonario_id + "'";
			System.out.println("BUSCA:     " + sql);
			rs = db.getDatos(sql);

			sql = "SELECT" + " T.PK1 AS PK1" + ",T.FOLIO AS FOLIO"
					+ ",ST.NUMBOLETOS AS NUMBOLETOS" + ",T.SORTEO"
					+ ",T.FORMATO AS FORMATO" + ",T.MONTO AS MONTO"
					+ ",ST.ASIGNADO AS ASIGNADO,ST.DISPONIBLES"
					+ " FROM SORTEOS_TALONARIOS ST, TALONARIOS T"
					+ " WHERE ST.PK_TALONARIO = T.PK1 AND  PK_SORTEO = '"
					+ this.getIdSorteo() + "' AND T.PK1 = '" + talonario_id
					+ "'  ";

			System.out.println("BUSCA TALONARIOS:     " + sql);
			ResultSet talonario = db.getDatos(sql);

			try {
				if (!rs.next()) {

					if (talonario.next()) {

						System.out.println(">>>>>AQUI ENTRA INSERTAR TALONARIO COMP 1");						
						

						System.out.println("SELECT TAL:     ");
						sql = "INSERT INTO SORTEOS_SECTORES_TALONARIOS"
								+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,FECHA_R,DISPONIBLES)"
								+ " VALUES ('"
								+ this.getIdSorteo() + "','"
								+ this.getIdSector() + "','"
								+ talonario_id + "',0,'"
								+ talonario.getInt("NUMBOLETOS") + "','"
								+ talonario.getInt("MONTO") + "','"
								+ pre_formato + this.getFormato() + "','"
								+ sesion.nickName + "',getdate(),'"
								+ talonario.getInt("NUMBOLETOS") + "')";

						System.out.println(">>>>>INSERTAR TALONARIO COMP" + sql);
						db.execQuery(sql);						
						
						

						// ASIGNAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(talonario.getString("FOLIO"), sesion);

						sql = "UPDATE [SORTEOS_TALONARIOS] SET DISPONIBLES=0, ASIGNADO=1"
								+ " WHERE PK_SORTEO="
								+ this.getIdSorteo()
								+ " AND PK_TALONARIO=" + talonario_id;

						db.execQuery(sql);						
						
					
						// --- FORMATOS SECTORES DATOS  ---			
						
						montototal += (double)talonario.getInt("MONTO");
						cadenatalonarios += talonario.getString("FOLIO")+",";
						
						
						
				
						// --- Se guarda un registro de seguimiento ---
						try {
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.SECTOR
									, this.getIdSorteo(), this.getIdSector(), 0, 0
									, ASIGNACION.TALONARIO
									, Long.valueOf(talonario_id), 0
									, 'N'
									, talonario.getInt("MONTO"), 0.0
									, talonario.getInt("NUMBOLETOS")
									, pre_formato + this.getFormato()
									, "sec-tals/bols-compls");
						} catch (Exception e) { Factory.Error(e, sql); }
					}

				} else {

					System.out.println(">>>>>AQUI COMP 2");

					sql = " SELECT * FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO ='"
							+ this.getIdSorteo()
							+ "' AND PK_SECTOR = '" + this.getIdSector()
							+ "' AND PK_TALONARIO = '" + talonario_id + "'";
					System.out.println("BUSCA:     " + sql);
					rs = db.getDatos(sql);

					

					if (talonario.next() && rs.next()) {

						System.out.println(">>>>>AQUI UPDATE");

						System.out.println("entro si: ");

						double costoboleto = (obtenerCostoBoletoTal(talonario.getString("FOLIO")))* talonario.getInt("DISPONIBLES");
						
						// CALCULA EL MONTO Y NUMERO DE BOLETOS SI EXISTE EL TALONARIO EN EL SECTOR
						double monto = rs.getInt("MONTO") + costoboleto;
						
						int conteoboleos = rs.getInt("NUMBOLETOS")+ talonario.getInt("DISPONIBLES");

						sql = " UPDATE SORTEOS_SECTORES_TALONARIOS SET DISPONIBLES='"
								+ conteoboleos
								+ "',NUMBOLETOS= '" + conteoboleos
								+ "',  MONTO= '" + monto + "'   "
								+ " WHERE PK_SORTEO=" + this.getIdSorteo()
								+ " AND PK_SECTOR=" + this.getIdSector()
								+ " AND PK_TALONARIO=" + talonario_id;

						System.out.println(">>>>>UPDATE TALONARIO REST" + sql);
						db.execQuery(sql);

						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(talonario.getString("FOLIO"), sesion);

						sql = "UPDATE [SORTEOS_TALONARIOS] SET DISPONIBLES=0, ASIGNADO=1"
								+ " WHERE PK_SORTEO="
								+ this.getIdSorteo()
								+ " AND PK_TALONARIO=" + talonario_id;

						db.execQuery(sql);
						
						
	                   // --- FORMATOS SECTORES DATOS  ---			
						
					
					  
						
												
						

					} else {

						System.out.println(">>>>>AQUI ENTRA ELSE(UP) INSERTAR TALONARIO ");

						double monto =       (obtenerCostoBoletoTal(talonario.getString("FOLIO")))* talonario.getInt("DISPONIBLES");
						
						montototal += monto;	
						
						// CALCULA EL MONTO Y NUMERO DE BOLETOS SI NO EXISTE EL TALONARIO EN EL SECTOR
						

						sql = " INSERT INTO SORTEOS_SECTORES_TALONARIOS"
								+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,FECHA_R,DISPONIBLES)"
								+ " VALUES ('"
								+ this.getIdSorteo() + "','"
								+ this.getIdSector() + "','"
								+ talonario_id + "',0,'"
								+ talonario.getInt("DISPONIBLES") + "','"
								+ monto + "','"
								+ pre_formato + this.getFormato() + "','"
								+ sesion.nickName + "',getdate(),'"
								+ talonario.getInt("DISPONIBLES") + "')";

						System.out.println(">>>>>INSERTAR TALONARIO COMP" + sql);
						db.execQuery(sql);

						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(talonario.getString("FOLIO"), sesion);

						// --- UPDATE --- para
						// guardarAsignacionSectorTalonario()
						sql = "UPDATE [SORTEOS_TALONARIOS] SET DISPONIBLES=0, ASIGNADO=1"
								+ " WHERE PK_SORTEO="
								+ this.getIdSorteo()
								+ " AND PK_TALONARIO=" + talonario_id;

						db.execQuery(sql);
						
						cadenatalonarios += talonario.getString("FOLIO")+",";

						// --- Se guarda un registro de seguimiento ---
						try {
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.SECTOR
									, this.getIdSorteo(), this.getIdSector(), 0, 0
									, ASIGNACION.TALONARIO
									, Long.valueOf(talonario_id), 0
									, 'N'
									, monto, 0.0
									, talonario.getInt("DISPONIBLES")  // coincide con el INSERT
									, pre_formato + this.getFormato()
									, "sec-tals/bols-dispbs");
						}
						catch (Exception e) { Factory.Error(e, sql); }
					}
				}

				sql = "UPDATE TALONARIOS SET DIGITAL=" + DIGITAL + " WHERE PK1=" + talonario_id;
				db.execQuery(sql);

			} catch (SQLException e) { Factory.Error(e, sql); }
		}//fin for
		
		
		
		System.out.println(">>>>>cadenatalonarios: "+cadenatalonarios);

		//FORMATOS SECTORES
		if(cadenatalonarios.length()>0)
			cadenatalonarios = cadenatalonarios.substring(0, cadenatalonarios.length()-1); 
		 this.cadenaboletos =  this.cadenaboletos.substring(0,  this.cadenaboletos.length()-1); 
		  
		 
		String[] cadenatal =  cadenatalonarios.split(",");	
		String[] cadenabol =  this.cadenaboletos.split(",");
		
		//***int numtalonarios = arrTalonarios.length;
		
										
		 sql = "INSERT INTO SORTEOS_FORMATOS_SECTORES"
				+ " (PK_SORTEO,PK_SECTOR,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
				+ " VALUES ('"
				+ this.getIdSorteo() + "','"
				+ this.getIdSector() + "','"
				+ pre_formato + this.getFormato() + "','"
				+ cadenatalonarios + "','"
				+ cadenatal.length + "','" 
			    + this.cadenaboletos + "','"													
				+ cadenabol.length + "','"
				+ montototal + "','"
				+ sesion.nickName + "')";
		

		  System.out.println(">>>>>REGISTRO DE FORMATO SECTOR fc1" + sql);						
		  db.execQuery(sql);	
		
	

		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorTalonario", sesion.toShortString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }

	}
	
	// TODO 
	public void AsignarTalonariosBoletosCompletos(
			String talonariofolio, SesionDatos sesion) {
		String sql = "";
		ArrayList<String> log_list_boletos = new ArrayList<String>();
		ArrayList<String> log_list_talonarios = new ArrayList<String>();
		
						
		db.con();
		String sqlINSERT = "";
		//System.out.println("ASIGNACION BOLETOS COMPLETOS");
		sql = "SELECT * FROM BOLETOS WHERE TALONARIO = '" + talonariofolio
				+ "' AND SORTEO = '" + this.getIdSorteo() + "'"; // HACE MATCH CON SORTEOS BOLETOS

		ResultSet boletos = db.getDatos(sql);
		//System.out.println("GUARDA BOLETOS:     " + sql);
		try {
			if (boletos != null) {

				while (boletos.next()) {

					sql = "SELECT * FROM SORTEOS_SECTORES_BOLETOS"
							+ " WHERE PK_BOLETO = '" + boletos.getString("PK1")
							+ "' AND  PK_SORTEO ='" + this.getIdSorteo()+"' ";
						//	+ "' AND PK_SECTOR = '" + this.getIdSector() + "' ";

					System.out.println("BUSCA SORTEOS_SECTORES_BOLETOS:     "
							+ sql);
					ResultSet rs = db.getDatos(sql);

					if (!rs.next()) {
						
						this.cadenaboletos += boletos.getString("FOLIO")+",";
						
						
						sqlINSERT = "INSERT INTO SORTEOS_SECTORES_BOLETOS (PK_BOLETO,PK_TALONARIO,PK_SORTEO,PK_SECTOR,FORMATO,USUARIO,FECHA_R,ASIGNADO) VALUES ('"
								+ boletos.getString("PK1") + "','"
								+ boletos.getString("TALONARIO") + "','"
								+ boletos.getString("SORTEO") + "','"
								+ this.getIdSector() + "','"
								+ pre_formato + this.getFormato() + "','"
								+ sesion.nickName + "',getdate(),0)";
						db.execQuery(sqlINSERT);
						//System.out.println("GUARDA BOLETOS:     " + sqlINSERT);

						// ------ UPDATE ------ para AsignarTalonariosBoletosCompletos()
						sql = "UPDATE [SORTEOS_BOLETOS]"
								+ " SET ASIGNADO=1" + " WHERE PK_SORTEO="
								+ this.getIdSorteo() + " AND PK_TALONARIO="
								+ talonariofolio;
						db.execQuery(sql);
						
												

						// --- Se guarda un registro de seguimiento ---
						try {
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.SECTOR
									, this.getIdSorteo(), this.getIdSector(), 0, 0
									, ASIGNACION.BOLETO
									, boletos.getInt("PK_TALONARIO"), boletos.getInt("PK1")
									, 'N'
									, boletos.getDouble("COSTO"), 0.0
									, 1
									, pre_formato + this.getFormato()
									, "sec-bols-compls");
						}catch (Exception e) { Factory.Error(e, sql); }
						
						// Para el log.
						log_list_boletos.add(boletos.getString("PK1"));
						log_list_talonarios.add(boletos.getString("TALONARIO"));
					}
				}

			}

		} catch (SQLException e) { Factory.Error(e, sql); }
		
		
		
		
		try {
			this.Log(sesion, LOG.REGISTRO, this, "AsignarTalonariosBoletosCompletos", sesion.toShortString() + ", bols=" + log_list_boletos.toString());
			this.Log(sesion, LOG.REGISTRO, this, "AsignarTalonariosBoletosCompletos", sesion.toShortString() + ", tals=" + log_list_talonarios.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		

	
		

	}
	
	
public String obtenerfolioBoleto(int idboleto){
		
	    String folioboleto = "";
		String sql = "SELECT TOP 1 FOLIO FROM BOLETOS WHERE PK1 ='"+idboleto+"' ";	
		System.out.println("obtener folio "+sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				folioboleto = rs.getString("FOLIO");	
	         
			}
		} catch (SQLException e) { Factory.Error(e, sql); }
		
		
		return folioboleto;
	}
	

public void obtenerCadenaFolioTal(ArrayList<String> listfoliotal,String talonarios_folio){	
	
	if(listfoliotal.contains(talonarios_folio)){}
	else{		 
		 listfoliotal.add(talonarios_folio);
		 this.cadenatalonarios += talonarios_folio+",";		 
	 }	
	
	  
}


public void guardarAsignacionSectorBoletos_SP(String Boletos, SesionDatos sesion){		
	db.con();	
	
	System.out.println("BOLETOS ");		
	
	// TODO - insertar
	  List<Parametros> list = new ArrayList<Parametros>();
	  list.add(new Parametros("COUNT", 0));
	  list.add(new Parametros("PK_SECTOR", getIdSector()));
	  list.add(new Parametros("FORMATO", getFormato()));
	  list.add(new Parametros("USUARIO", sesion.nickName));	
	  list.add(new Parametros("LIST", Boletos));		
	
	// list.add(new Parametros("StatementType", "InsertMultiple"));
	//  this.setId(db.execStoreProcedureIntId("sp_guardarAsignacionSectorTalonario", list));		
	
	  int result = db.execStoreProcedureIntId("sp_guardarAsignacionSectorBoletos", list);
	  System.out.println(" result BOL:"+result);
	  
	  
	  /*try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", bols=" + Boletos.toString());
			//this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", tals=" + log_list_talonarios.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }	*/
		
	
	
}




	
	
	public void guardarAsignacionSectorBoletos(String[] arrBoletos, SesionDatos sesion) {
		
		//System.out.println(" BOLETOSi: ");
		ArrayList<String> log_list_boletos = new ArrayList<String>();
		ArrayList<String> log_list_talonarios = new ArrayList<String>();
		
		
		String[] arrBoletoscadena;
		int boletos_id = 0;			
		String talonarios_folio = "";
		
		
		int numboletos = 0;		
		String cadenaboletos = "";
		//global
		this.cadenatalonarios = "";	
		double montototal = 0;
		
		ArrayList<String> listfoliotal = new  ArrayList<String>();
		listfoliotal.add(0, "-1");
		
		
		
			
		for (int i = 0; i < arrBoletos.length; i++) {

			arrBoletoscadena = arrBoletos[i].split("#%#");
			boletos_id = Integer.valueOf(arrBoletoscadena[0]);
			//System.out.println("id boleto: " + i + ": " + boletos_id);
			talonarios_folio = arrBoletoscadena[1];
			//System.out.println("folio tal: " + i + ": " + talonarios_folio);

			String sql = "SELECT PK1 FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO ='"
					+ this.getIdSorteo()
					+ "' AND PK_SECTOR = '"
					+ this.getIdSector()
					+ "' AND PK_BOLETO = '"
					+ boletos_id
					+ "'";
			//System.out.println("BUSCA:     " + sql);
			ResultSet rs = db.getDatos(sql);

			try {
				if (!rs.next()) {
					System.out.println("INSERT:     " + sql);
					String sql2 = "INSERT INTO SORTEOS_SECTORES_BOLETOS"
							+ " (PK_SORTEO,PK_SECTOR,PK_BOLETO,ASIGNADO,PK_TALONARIO,FORMATO,USUARIO,FECHA_R)"
							+ " VALUES ('"
							+ this.getIdSorteo() + "','"
							+ this.getIdSector() + "','"
							+ boletos_id + "',0,'"
							+ talonarios_folio + "','"
							+ pre_formato + this.getFormato() + "','"
							+ sesion.nickName + "',getdate())";
					System.out.println(sql2);
					db.execQuery(sql2);

					guardarSectorTalonario(talonarios_folio, sesion,boletos_id);
					
					
					// ------ UPDATE ------ para guardarAsignacionSectorBoletos()
					sql = "UPDATE [SORTEOS_BOLETOS]"
							+ " SET ASIGNADO=1"
							+ " WHERE PK_SORTEO=" + this.getIdSorteo()
							+ " AND PK_TALONARIO="+ talonarios_folio
							+ " AND PK_BOLETO=" + boletos_id;
					db.execQuery(sql);
					
					
					
					//	FORMATOS SECTORES				
					cadenaboletos += obtenerfolioBoleto(boletos_id)+",";
					numboletos += 1;
					obtenerCadenaFolioTal(listfoliotal,talonarios_folio);
					
								
					
					//seg
					log_list_boletos.add(Integer.toString(boletos_id));
					log_list_talonarios.add(talonarios_folio);					
					

					// --- Se guarda un registro de seguimiento ---
					try {
						
						
						int idTalonario = super.getIdTalonario(this.getIdSorteo(), talonarios_folio);
						double costoBoleto = super.getCostoBoleto(boletos_id);
						
						//formatos
						montototal += costoBoleto;
						
						
						Seguimiento.guardaAsignacion(db
								, ASIGNACION.SECTOR
								, this.getIdSorteo(), this.getIdSector(), 0, 0
								, ASIGNACION.BOLETO
								, idTalonario, boletos_id
								, 'N'
								, costoBoleto, 0.0
								, 1
								, pre_formato + this.getFormato()
								, "sec-bols");
					}catch (Exception e) { Factory.Error(e, sql); }

				}
			} catch (SQLException e) { Factory.Error(e, sql); }
		}
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", bols=" + log_list_boletos.toString());
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", tals=" + log_list_talonarios.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }	
		
		
		//FORMATOS SECTORES
		 this.cadenatalonarios = this.cadenatalonarios.substring(0, this.cadenatalonarios.length()-1); 
		 cadenaboletos = cadenaboletos.substring(0, cadenaboletos.length()-1); 
		  
		String[] cadenatal =  this.cadenatalonarios.split(",");		  
		
										
		String sql = "INSERT INTO SORTEOS_FORMATOS_SECTORES"
				+ " (PK_SORTEO,PK_SECTOR,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
				+ " VALUES ('"
				+ this.getIdSorteo() + "','"
				+ this.getIdSector() + "','"													
				//+ "FC1 - "  + this.getFormato() + "','"
				+ pre_formato + this.getFormato() + "','"
				+ this.cadenatalonarios + "','"
				+ cadenatal.length + "','" 
			    + cadenaboletos + "','"													
				+ numboletos + "','"
				+ montototal + "','"
				+ sesion.nickName + "')";
		

		  System.out.println(">>>>>REGISTRO DE FORMATO SECTOR fc1" + sql);						
		  db.execQuery(sql);			
		
	}
	
	

	public void guardarSectorTalonario(String talonarios_folio,	SesionDatos sesion,int boletos_id) {

		String sql = "";
		ResultSet rs = null;
		int idtalonario = 0;
		
		double monto = 0;
		int conteoboleos = 0;
		double costoboleto = obtenerCostoBoleto(boletos_id);	

		try {

			idtalonario = getidTalonario(talonarios_folio, this.getIdSorteo());
			//System.out.println("idtalonario:     " + idtalonario);

			sql = "SELECT MONTO,NUMBOLETOS FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO ='"
					+ this.getIdSorteo()
					+ "' AND PK_SECTOR = '"
					+ this.getIdSector()
					+ "' AND PK_TALONARIO = '"
					+ idtalonario + "'";
			//System.out.println("BUSCA:     " + sql);
			rs = db.getDatos(sql);
			
			//sql = "SELECT * FROM TALONARIOS WHERE PK1 = '" + idtalonario	+ "'";// hacer match sorteos tal
			 sql = "SELECT" + " T.PK1 AS PK1" + ",T.FOLIO AS FOLIO"
						+ ",ST.NUMBOLETOS AS NUMBOLETOS" + ",T.SORTEO"
						+ ",T.FORMATO AS FORMATO" + ",T.MONTO AS MONTO"
						+ ",ST.ASIGNADO AS ASIGNADO,ST.DISPONIBLES"
						+ " FROM SORTEOS_TALONARIOS ST, TALONARIOS T"
						+ " WHERE ST.PK_TALONARIO = T.PK1 AND  PK_SORTEO = '" + this.getIdSorteo()+"' AND T.PK1 = '"+ idtalonario + "'  ";	
				
				//System.out.println("BUSCA TALONARIOS:     " + sql);
				ResultSet talonario = db.getDatos(sql);
				
				
				
			if (!rs.next()) {
				
											
				if (talonario.next()) {
					
					//System.out.println(" AQUI INSERTA ");
					
					
				
					
					
					//CALCULA EL MONTO Y NUMERO DE BOLETOS SI NO EXISTE EL TALONARIO
			    	  conteoboleos +=1; 
			    	  monto = costoboleto;
					
			  
					//System.out.println("CONTEO:     " + conteoboleos);
					sql = " INSERT INTO SORTEOS_SECTORES_TALONARIOS"
							+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,FECHA_R,DISPONIBLES)"
							+ " VALUES ('"
							+ this.getIdSorteo() + "','"
							+ this.getIdSector() + "','"
							+ talonario.getInt("PK1") + "',0,'"
							+ conteoboleos + "','"
							+ monto + "','"
							+ pre_formato + this.getFormato() + "','"
							+ sesion.nickName + "',getdate(),'"
							+ conteoboleos + "')";
					//System.out.println(">>>>INSERT TALONARIOS BOL"+sql);
					db.execQuery(sql);
					
					
					int disponibles = talonario.getInt("DISPONIBLES") - conteoboleos;
					//System.out.println(">>>>>>DIFERENCIA BOL:     " + disponibles);

					
					// --- UPDATE --- para guardarSectorTalonario()
					sql = "UPDATE [SORTEOS_TALONARIOS] SET DISPONIBLES='"+disponibles+"' ,ASIGNADO=1"
							+ " WHERE PK_SORTEO=" + this.getIdSorteo()
							+ " AND PK_TALONARIO=" + talonario.getInt("PK1");
					
					//System.out.println(">>>>UPDATE SORTEOS_TALONARIOS BOL"+sql);

					db.execQuery(sql);
					
					// --- Se guarda un registro de seguimiento ---
					try{
						Seguimiento.guardaAsignacion(db
								, ASIGNACION.SECTOR
								, this.getIdSorteo(), this.getIdSector(), 0, 0
								, ASIGNACION.TALONARIO
								, talonario.getInt("PK1"), boletos_id
								, 'N'
								, monto, 0.0
								, conteoboleos
								, pre_formato + this.getFormato()
								, "sec-tals");
					}catch (Exception e) { Factory.Error(e, sql); }
					
					// --- Se guarda un registro de seguimiento ---
					try {
						this.Log(sesion, LOG.REGISTRO, this,"guardarSectorTalonario", sesion.toShortString() + ", tal=" + talonario.getInt("PK1"));
					}catch(Exception ex) { Factory.Error(ex, "Log"); }
				}
				
				
			}else{
				
				//System.out.println(" AQUI UPDATE ");
				
				//CALCULA EL MONTO Y NUMERO DE BOLETOS SI EXISTE EL TALONARIO
				monto = rs.getInt("MONTO")+costoboleto;
				conteoboleos = rs.getInt("NUMBOLETOS")+1;
				
				talonario.next();
				
				
				  //System.out.println("NUM TAL: "+talonario.getInt("DISPONIBLES")+"- NUM ASIG"+conteoboleos);

						
				
				 sql = "UPDATE [SORTEOS_SECTORES_TALONARIOS] SET  DISPONIBLES="+conteoboleos+",  NUMBOLETOS="+conteoboleos+", MONTO="+monto+" WHERE PK_SORTEO=" + this.getIdSorteo()
		        	       + " AND PK_TALONARIO=" + idtalonario +"AND PK_SECTOR = "+ this.getIdSector();
				 
				  //System.out.println("UDATE NUMBOL Y MONTO"+sql);

		          db.execQuery(sql);  
		          
		          //TABLA SORTEOS_TALONARIOS
		          int disponibles = talonario.getInt("DISPONIBLES") - 1;
		      
					sql = "UPDATE [SORTEOS_TALONARIOS] SET DISPONIBLES='"+disponibles+"' ,ASIGNADO=1"
							+ " WHERE PK_SORTEO=" + this.getIdSorteo()
							+ " AND PK_TALONARIO=" + talonario.getInt("PK1");
					
					//System.out.println(">>>>UPDATE SORTEOS_TALONARIOS BOL"+sql);

					db.execQuery(sql);
					

					// --- Se guarda un registro de seguimiento ---
					try {
						this.Log(sesion, LOG.EDITADO, this, "guardarSectorTalonario", sesion.toShortString() + ", tal=" + talonario.getInt("PK1"));
					}catch(Exception ex) { Factory.Error(ex, "Log"); }
			}


		} catch (SQLException e) { Factory.Error(e, sql); }
		
	}
	
	
		
	
public double obtenerCostoBoleto(int idboleto){
		
		double id = 0;
		String sql = "SELECT COSTO FROM BOLETOS WHERE PK1 ='"+idboleto+"' ";	
		System.out.println("obtener costoboleto "+sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				id = rs.getInt("COSTO");	
	         
			}
		} catch (SQLException e) { Factory.Error(e, sql); }
		
		
		return id;
	}
	

	public int getidTalonario(String folio, int sorteo) {
		String sql = "SELECT PK1 FROM TALONARIOS WHERE FOLIO ='" + folio
				+ "' AND  SORTEO='" + sorteo + "' ";
		int id = 0;

		System.out.println("pk1 de talonario"+sql);
		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				id = rs.getInt("PK1");

			}
		} catch (SQLException e) { Factory.Error(e, sql); }

		return id;
	}
	
	
public double obtenerCostoBoletoTal(String idtalonario){
		
		double id = 0;
		String sql = "SELECT TOP 1 COSTO FROM BOLETOS WHERE TALONARIO ='"+idtalonario+"' ";	
		System.out.println("obtener costoboleto "+sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				id = rs.getInt("COSTO");	
	         
			}
		} catch (SQLException e) { Factory.Error(e, sql); }
		
		
		return id;
	}
	
	
	
	public ResultSet obtenerTalonario() {
		
		String sql= "";
		ResultSet rs= null;
		idtalonario = getidTalonario(this.getFolioTalonario(), this.getIdSorteo());
		System.out.println("idtalonario:     " + idtalonario);

		sql = "SELECT * FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO ='"
				+ this.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ this.getIdSector()
				+ "' AND PK_TALONARIO = '"
				+ idtalonario + "'";
		System.out.println("BUSCA talonarios:     " + sql);
		rs = db.getDatos(sql);
	

		return rs;
	}
	
	public void eliminaTalonario(int folioTalonario) {
		
		String sql = "";
		
		String sqlS = "SELECT NUMBOLETOS FROM SORTEOS_TALONARIOS "
				+ " WHERE PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo();	
		

		System.out.println("SORTEOS_TAL"+sqlS);
		ResultSet rs = db.getDatos(sqlS);
		
		
		
		try {
			if (rs.next()) {

				sql =	" UPDATE [SORTEOS_TALONARIOS] SET DISPONIBLES = '"+ rs.getInt("NUMBOLETOS") +"',  ASIGNADO=0"
						+ " WHERE (PK_TALONARIO=" + getIdtalonario()
						+ " AND PK_SORTEO=" + getIdSorteo()				
						+ ")\n";
				
				System.out.println("UPDATE tal: " + sql);
				db.execQuery(sql);
				

			}
		} catch (SQLException e) { Factory.Error(e, sql); }
		
		
		
		
		 sql =	
				
				"DELETE [SORTEOS_SECTORES_TALONARIOS]"
				+ " WHERE (PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()			
				+ ")\n"
						
				+ " DELETE [SORTEOS_SECTORES_BOLETOS]"
				+ " WHERE (PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()			
				+ ")\n"			
				
				
	
				+ " UPDATE [SORTEOS_BOLETOS] SET ASIGNADO=0"
				+ " WHERE (PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()			
				+ ")\n"				
				
				
				+ " DELETE [SORTEOS_NICHOS_TALONARIOS]"
				+ " WHERE (PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				//+ " AND PK_NICHO=" + getIdnicho()
				+ ")\n"
				
				+ " DELETE [SORTEOS_NICHOS_BOLETOS]"
				+ " WHERE (PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				//+ " AND PK_NICHO=" + getIdnicho()
				+ ")\n"

				+ " DELETE [SORTEOS_COLABORADORES_TALONARIOS]"
				+ " WHERE (PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
			//	+ " AND PK_NICHO=" + getIdnicho()
			//	+ " AND PK_COLABORADOR=" + getIdColaborador()
				+ ")\n"
				
				+ " DELETE [SORTEOS_COLABORADORES_BOLETOS]"
				+ " WHERE (PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
			//	+ " AND PK_NICHO=" + getIdnicho()
			//	+ " AND PK_COLABORADOR=" + getIdColaborador()
				+ ")\n";
	
		System.out.println("ELIMINAR: " + sql);
		db.execQuery(sql);
	}
	
/* ********************EMPIEZA RETORNO Y DEVOLVER**********************************/
	
	//obtiene el num boletos del TALONARIOS
	public int getNumeroBoletos(){		
		
		String sql="SELECT NUMBOLETOS  FROM VSECTORES_TALONARIOS "				
				+ " WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"					 
				+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' ";	
		
		int numboletos = 0;		
	
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				numboletos = rs.getInt("NUMBOLETOS");	
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return numboletos;
	}
	
	

	public boolean getTalCompletoVendidosRetornados(){		
	
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totaltretornados = false;	
		
				
		String sql="SELECT  COUNT(PK1) AS 'BVENDIDOSR' FROM VSECTORES_BOLETOS  "
				+ " WHERE "
			//	+ "PK_SORTEO = '"+this.getIdSorteo()+"' AND "
				+ " PK_SECTOR = '"+this.getIdSector()+"'"						 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V' AND RETORNADO=1 ";	
		
		
		
		System.out.println(">>>SELECT BOLETOS  total Vendidos y retornados: "+sql);
		ResultSet nbvendidos = db.getDatos(sql);		
	
		
		try {
			if(nbvendidos.next()) {					
				
				if( nbvendidos.getInt("BVENDIDOSR") == numeroboletos  ){						
									
					totaltretornados = true;				
				
				}
				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totaltretornados;
	}	
	
	
	public boolean getTalonariosCompletamenteVendidos(){		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totaltretornados = false;
		
		
		String sql="SELECT  COUNT(PK1) AS 'BVENDIDOS' FROM VSECTORES_BOLETOS "
				+ " WHERE"				
				//+ " PK_SORTEO = '"+this.getIdSorteo()+"' AND "
				+ "  PK_SECTOR = '"+this.getIdSector()+"'"						 
				+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V'";	
		
		
		
		System.out.println(">>>SELECT BOLETOS  total Vendidos: "+sql);
		ResultSet nbvendidos = db.getDatos(sql);		
	
		
		try {
			if(nbvendidos.next()) {					
				
				if( nbvendidos.getInt("BVENDIDOS") == numeroboletos  ){						
									
					totaltretornados = true;				
				
				}
				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totaltretornados;
	}
	
	
	
	//FC4
	public void retornotalonariosfc4(int folioTalonario, SesionDatos sesion) {
		
		String sql = "";
		ResultSet rs = null;
		String cadena = "";
		
		
	
		//String valida = "";
		
		//boolean totalmenteVendidostal =  getTalonariosCompletamenteVendidos();		
		
		
		/*String sql = "SELECT T.PK1 AS PK1, T.FOLIO, RST.NUMBOLETOS, T.SORTEO, T.FORMATO, RST.MONTO, RST.PK_TALONARIO, T.VENDIDO, T.DIGITAL "
				+ " FROM SORTEOS_SECTORES_TALONARIOS RST, TALONARIOS T WHERE RST.PK_SECTOR = '"
				+ idSector + "' AND RST.PK_TALONARIO = T.PK1 AND RST.PK_SORTEO='"+this.getIdSorteo()+"'";		
		 if(rs.getString("ELECTRONICO").compareTo("1") == 0 && boletodigComprador == true)
    	 {//el talonario es dig y tiene por lo menos 1 boleto con comprador y folio dig			 
			 valida = "ELECTRONICO";   
         }else{	*/		
		
	    
			/*	if(!totalmenteVendidostal){
					valida = "TRUE";
				}
				else{*/
					
					String sqlS="SELECT  FOLIO,PK_BOLETO,COSTO, PK_ESTADO FROM VSECTORES_BOLETOS "
							+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
							+ " AND PK_SECTOR = '"+this.getIdSector()+"'"
							+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V' ";
					
					rs = db.getDatos(sqlS);
				
				 
					try {
						
						while (rs.next()) {							
							
							
							sql =   "UPDATE BOLETOS SET RETORNADO = 1"				
									+ " WHERE PK1 = "+rs.getInt("PK_BOLETO")+" "
									+ "AND PK_TALONARIO = '"+this.getIdtalonario()+"'";
								
								
								System.out.println(">>>UPDATE BOLETOS : "+sql);
								db.execQuery(sql);						
												

								sql = 	//FORMATOS SECTORES
										
										"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR, PK_ESTADO,USUARIO,FECHA_R)"
										+ " VALUES ('"									
		                                + "FC4 - "  + this.getFormato() + "','"	
		                                + rs.getInt("PK_BOLETO") + "','"	
										+ this.getIdSector() + "','"																
										+ rs.getString("PK_ESTADO") + "','"	
										+ sesion.nickName + "',"	
										+ "GETDATE())";							

								System.out.println(">>>>>REGISTRO DE FORMATO SECTORES fc4" + sql);						
								db.execQuery(sql);									
								
								
								sql =//SEGUIMIENTO SECTORES									
										"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_ESTADO, USUARIO, PK_TALONARIO)"
										+ " VALUES ("
										+ "'Sectores',"
										+ "'Retorno',"
										+ "'ret-sec-bols',"
										+ "'"									
		                                + "FC4 - "  + this.getFormato() + "','"	
		                                + rs.getInt("PK_BOLETO") + "','"	
										+ this.getIdSector() + "','"										
										+ rs.getString("PK_ESTADO") + "','"	
										+ sesion.nickName + "','"
										+ this.getIdtalonario() + "')";							

								System.out.println(">>>>>SEGUIMIENTO SECTORES fc4" + sql);						
								db.execQuery(sql);	
								
								cadena = sesion.toShortString() + ", bol=" + rs.getInt("PK_BOLETO");					
								
								 sql =//LOG SECTORES									
											"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO,FECHA_R)"
											+ " VALUES ("
											+ "'mAsignacionTalonarios',"
											+ "'retornotalonariosfc4',"
											+ "'Retorno','"                               
			                                + cadena + "','"									
			                            	+ sesion.nickName+ "',"
											+ "GETDATE())";							

									System.out.println(">>>>>LOG SECTORES fc4" + sql);						
									db.execQuery(sql);				
								
								
								
								
								
		
							
						}
		
					
					
		
					} catch (SQLException ex) {
						com.core.Factory.Error(ex, sql);
					}
				//}
				
	        //}	
				
		//return valida;
	}
	
	
	public boolean getTalonariosCompletamenteNoVendidos(){		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totalnovendidos = false;	
		
		
		
		String sql="SELECT  COUNT(PK1) AS 'BNOVENDIDOS' FROM VSECTORES_BOLETOS "
				+ "WHERE "				
				//+ " PK_SORTEO = '"+this.getIdSorteo()+"' AND"
				+ " PK_SECTOR = '"+this.getIdSector()+"' "						
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' "
				+ " AND PK_ESTADO = 'N'";
				//+ " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";	
		
		
		
		System.out.println(">>>SELECT BOLETOS  total No Vendidos: "+sql);
		ResultSet nbvendidos = db.getDatos(sql);		

	
		
		try {
			if(nbvendidos.next()) {				
				
				if( nbvendidos.getInt("BNOVENDIDOS") == numeroboletos  ){						
									
					totalnovendidos = true;	
				
				
				}
				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totalnovendidos;
	}
	
public boolean getTalonariosDigitalesCompletamenteVendidos_sc(){//boletos digitales con folio y sin V		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totalnovendidos = false;		
	
		
		String sql="SELECT  COUNT(PK1) AS 'BVENDIDOSDIG_sc' FROM VSECTORES_BOLETOS "
				+ "WHERE"				
				//+ "  SB.PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " PK_SECTOR = '"+this.getIdSector()+"'"			 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'P' ";
				//+ " AND (FOLIODIGITAL IS NOT NULL OR FOLIODIGITAL <> '')";
		
		
		
		System.out.println(">>>total BVENDIDOSDIG_sc: "+sql);
		ResultSet nbvendidos = db.getDatos(sql);		

		//System.out.println(">>> numeroboletos: "+numeroboletos);
		
		try {
			if(nbvendidos.next()) {	
				//System.out.println(">>> numeroboletosbd: "+nbvendidos.getInt("BNOVENDIDOS"));
				
				if( nbvendidos.getInt("BVENDIDOSDIG_sc") == numeroboletos && numeroboletos != 0 ){						
									
					totalnovendidos = true;	
				
				
				}
				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totalnovendidos;
	}


public boolean ExisteBoletosSectoresNV(){
	
	String sql="SELECT  COUNT(PK1) AS 'MAX' FROM VSECTORES_BOLETOS "
			+ "WHERE "				
			//+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"' AND"
			+ " PK_SECTOR = '"+this.getIdSector()+"'"				
			+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N'";
           // + " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";
	
	System.out.println(" MAX sectores boletos no vendidos sectores: " + sql);
	ResultSet rs = db.getDatos(sql);
	
	try {
		if (rs.next())
			return rs.getInt("MAX") > 0;

	} catch (SQLException e) { Factory.Error(e, sql); }
	return false;
}
	
	
	
	
public boolean ExisteCargaNichos(){
		
		String sql = "SELECT COUNT(PK_TALONARIO) AS 'MAX' FROM [VNICHOS_TALONARIOS]"
				+ " WHERE PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SECTOR = " + getIdSector()
				+ " AND PK_SORTEO = " + getIdSorteo();				
				
		
		System.out.println(" MAX NICHOS: " + sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next())
				return rs.getInt("MAX") > 0;

		} catch (SQLException e) { Factory.Error(e, sql); }
		return false;
	}
	 	
	
	
	// FC5
	public void devolvertalonariosfc5(int folioTalonario, SesionDatos sesion) {

		String sql = "";		
		String cadena = "";
		
		//String valida = "";

		/*if (getTalonariosCompletamenteNoVendidos()) {

			if (ExisteCargaNichos()) {

				valida = "EXISTECNICHOS";

			} else {*/
				sql = "SELECT FOLIO, PK_BOLETO ,COSTO, PK_ESTADO FROM VSECTORES_BOLETOS "
					+ " WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
					+ " AND PK_SECTOR = '"+this.getIdSector()+"'"
					+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N'";
					//+ " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";
				
				System.out.println("BOL sectores : " + sql);
				ResultSet boletos = db.getDatos(sql);				
					
		
				try {
					if (boletos.next())
					{			
						

						sql = "";
						do {					
							
							sql =	 "DELETE [SECTORES_BOLETOS]"
									+ " WHERE (PK_BOLETO =" + boletos.getInt("PK_BOLETO")								
									+ " AND PK_SECTOR=" + getIdSector()																	
									+ ")\n";
				
									System.out.println(" DELETE: " + sql);
									db.execQuery(sql);						
													
										
										sql = 	//FORMATOS SECTORES
												
												"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR, PK_ESTADO,USUARIO,FECHA_R)"
												+ " VALUES ('"									
				                                + "FC5 - "  + this.getFormato() + "','"	
				                                + boletos.getInt("PK_BOLETO") + "','"	
												+ this.getIdSector() + "','"																				
												+ boletos.getString("PK_ESTADO") + "','"	
												+ sesion.nickName + "',"	
												+ "GETDATE())";							

										System.out.println(">>>>>REGISTRO DE FORMATO SECTORES fc5" + sql);						
										db.execQuery(sql);	
										
										 sql =//SEGUIMIENTO SECTORES									
													"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_ESTADO, USUARIO, PK_TALONARIO)"
													+ " VALUES ("
													+ "'Sectores',"
													+ "'Devolución',"
													+ "'devol-sec-bols',"
													+ "'"									
					                                + "FC5 - "  + this.getFormato() + "','"	
					                                + boletos.getInt("PK_BOLETO") + "','"	
													+ this.getIdSector() + "','"												
													+ boletos.getString("PK_ESTADO") + "','"	
													+ sesion.nickName + "','"
													+ this.getIdtalonario() + "')";								

											System.out.println(">>>>>SEGUIMIENTO SECTORES fc5" + sql);						
											db.execQuery(sql);	
											
											cadena = sesion.toShortString() + ", bol=" + boletos.getInt("PK_BOLETO");										
											
											 sql =//LOG SECTORES									
														"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO, FECHA_R)"
														+ " VALUES ("
														+ "'mAsignacionTalonarios',"
														+ "'devolvertalonariosfc5',"
														+ "'Borrado','"                               
						                                + cadena + "','"									
						                            	+ sesion.nickName+ "',"
														+ "GETDATE())";							

												System.out.println(">>>>>LOG SECTORES fc5" + sql);						
												db.execQuery(sql);										
										
							
							
						}while(boletos.next());					
						
					
					}
				} catch (SQLException ex) {
					com.core.Factory.Error(ex, sql);
				}

			/*}//
			
		} else {
			valida = "NTNOVENDIDOS";
		}// Fin else

		return valida;*/
	}


public boolean getTalParcialmenteVendidos(){		
	
	//int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
	boolean totaltretornados = false;
	
	
	String sql="SELECT  COUNT(PK1) AS 'BPARCIALES' FROM VSECTORES_BOLETOS "
			+ "WHERE "				
			//+ "  SB.PK_SORTEO = '"+this.getIdSorteo()+"' AND"
			+ " PK_SECTOR = '"+this.getIdSector()+"'"						 
			+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'P'";	
	
	
	
	System.out.println(">>>SELECT BOLETOS  BPARCIALES: "+sql);
	ResultSet nbvendidos = db.getDatos(sql);		

	
	try {
		if(nbvendidos.next()) {					
			
			if( nbvendidos.getInt("BPARCIALES") >= 1  ){						
								
				totaltretornados = true;				
			
			}
			
         
		}
	} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	
	
	return totaltretornados;
}


	
public boolean ExisteCargaNichosBoletosNoV(){
	
	String sql="SELECT  COUNT(PK1) AS 'MAX' FROM VNICHOS_BOLETOS "
			+ "WHERE "				
			//+ " PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " PK_SECTOR = '"+this.getIdSector()+"'"			
			+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N' ";
			//+ "AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";	
	
	System.out.println(" MAX NICHOS boletos no vendidos: " + sql);
	ResultSet rs = db.getDatos(sql);
	
	try {
		if (rs.next())
			return rs.getInt("MAX") > 0;

	} catch (SQLException e) { Factory.Error(e, sql); }
	return false;
}
 	


//FC5B
public String devolvertalonariosfc5B(int folioTalonario, SesionDatos sesion) {
		
	String sql = "";	
    String valida = "";
    String cadena = "";
   
    

	/*boolean totalmenteVendidostal = getTalonariosCompletamenteVendidos();
	boolean totalmenteNoVendidostal = getTalonariosCompletamenteNoVendidos();
	boolean totalmenteVendidostalDig_sc = getTalonariosDigitalesCompletamenteVendidos_sc();//nuevo
	boolean algunBoletoNoVendidoSectores = ExisteBoletosSectoresNV();//nuevo
	boolean talParcialmenteVendidos = getTalParcialmenteVendidos();	
    
  if( ((!totalmenteVendidostal) && (!totalmenteNoVendidostal)&&(!totalmenteVendidostalDig_sc)&&(algunBoletoNoVendidoSectores)) && !talParcialmenteVendidos ){
    
	  boolean existecarganichosb =  ExisteCargaNichosBoletosNoV();
	    
	   if(existecarganichosb){
	    	
		   
		      valida = "EXISTECNICHOS";
		
	    	
      }else{*/	
		
	
        	  sql="SELECT  FOLIO, PK_BOLETO,COSTO, PK_ESTADO FROM VSECTORES_BOLETOS "
			  + "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			  + " AND PK_SECTOR = '"+this.getIdSector()+"'"					 
			  + "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N'";
			 // + "AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";	
	
        	  ResultSet sectores = db.getDatos(sql);				
		
		 try {
			
			
			if (sectores.next()) {				
				
				
				do{					
					
					
					
					sql =	 "DELETE [SECTORES_BOLETOS]"
							+ " WHERE (PK_BOLETO =" + sectores.getInt("PK_BOLETO")								
							+ " AND PK_SECTOR=" + getIdSector()																	
							+ ")\n";
		
							System.out.println(" DELETE: " + sql);
							db.execQuery(sql);					
												
								
							 sql = 	//FORMATOS SECTORES
										
										"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR, PK_ESTADO,USUARIO,FECHA_R)"
										+ " VALUES ('"									
		                                + "FC5 - "  + this.getFormato() + "','"	
		                                + sectores.getInt("PK_BOLETO") + "','"	
										+ this.getIdSector() + "','"																				
										+ sectores.getString("PK_ESTADO") + "','"	
										+ sesion.nickName + "',"	
										+ "GETDATE())";							

								System.out.println(">>>>>REGISTRO DE FORMATO SECTORES fc5B" + sql);						
								db.execQuery(sql);	
								
								 sql =//SEGUIMIENTO SECTORES									
											"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_ESTADO, USUARIO,PK_TALONARIO)"
											+ " VALUES ("
											+ "'Sectores',"
											+ "'Devolución',"
											+ "'devol-sec-bols',"
											+ "'"									
			                                + "FC5B - "  + this.getFormato() + "','"	
			                                + sectores.getInt("PK_BOLETO") + "','"	
											+ this.getIdSector() + "','"												
											+ sectores.getString("PK_ESTADO") + "','"	
											+ sesion.nickName + "','"
											+ this.getIdtalonario() + "')";								

									System.out.println(">>>>>SEGUIMIENTO SECTORES fc5B" + sql);						
									db.execQuery(sql);	
									
									cadena = sesion.toShortString() + ", bol=" + sectores.getInt("PK_BOLETO");										
									
									 sql =//LOG SECTORES									
												"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO, FECHA_R)"
												+ " VALUES ("
												+ "'mAsignacionTalonarios',"
												+ "'devolvertalonariosfc5B',"
												+ "'Borrado','"                               
				                                + cadena + "','"									
				                            	+ sesion.nickName+ "',"
												+ "GETDATE())";							

										System.out.println(">>>>>LOG SECTORES fc5B" + sql);						
										db.execQuery(sql);		
								
								
					
					
				}while(sectores.next());	
					
				
				
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }			
		
   /* }
	   
	   
	   
	   
  		}else{ valida = "NOPARCIALES";}*/
  
  		return valida;
	}

	public HashMap<Integer,Integer> buscaIntervaloTalonarios(int limiteInf, int limiteSup, int idsorteo) {
		/*String sql = "SELECT T.PK1 AS 'PK1', ST.ASIGNADO AS 'ASIGNADO'"
				+ " FROM SORTEOS_TALONARIOS ST, TALONARIOS T"
				+ " WHERE ST.PK_TALONARIO = T.PK1 AND PK_SORTEO = " + idsorteo
				+ " AND " + limiteInf + "<=T.FOLIO AND " + limiteSup + ">=T.FOLIO";*/
		
				
		String sql = "SELECT PK1,DISPONIBLES FROM VTALONARIOS_BOLETOS"
		+ " WHERE  "+ limiteInf + " <= FOLIO AND " + limiteSup + " >= FOLIO ORDER BY PK1 ASC";
		
		

		if (limiteInf > limiteSup){
			int aux = limiteInf;
			limiteInf = limiteSup;
			limiteSup = aux;
		}
		HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
		try {
			ResultSet res = db.getDatos(sql);
			while(res.next())
				map.put(res.getInt("PK1"), res.getInt("DISPONIBLES"));
				//map.put(res.getInt("PK1"), res.getInt("ASIGNADO"));
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	
	public ResultSet ObtenerTalonarios(int idsorteo,int idsector)
	{
		
	String sql = "SELECT T.PK1 AS PK1, T.FOLIO, RNT.NUMBOLETOS, T.SORTEO, T.FORMATO, RNT.MONTO,RNT.PK1 AS IDTALON,RNT.PK_TALONARIO, T.DIGITAL AS 'ELECTRONICO',"
	        + "(SELECT TOP 1 PK_COLABORADOR FROM SORTEOS_COLABORADORES_TALONARIOS "
			+ "WHERE PK_TALONARIO = RNT.PK_TALONARIO"				
			+ " AND PK_SECTOR = " + idsector + " "
			+ " AND PK_SORTEO = " + idsorteo + ") AS PK_COLABORADOR,"
			+ "(SELECT TOP 1 PK_NICHO FROM SORTEOS_NICHOS_TALONARIOS "
			+ " WHERE PK_TALONARIO = RNT.PK_TALONARIO AND PK_SECTOR = " + idsector + ""
			+ " AND PK_SORTEO = " + idsorteo + ") AS PK_NICHO "
			+ " FROM SORTEOS_SECTORES_TALONARIOS RNT, TALONARIOS T"
			+ " WHERE RNT.PK_TALONARIO = T.PK1 "
			
			//+ " AND RNT.PK_TALONARIO = '17200' "//QUITARRRRRRRRRRRRRRRRRRR	
			
			+ " AND RNT.PK_SECTOR = '" + idsector + "' AND "
			+ "RNT.PK_SORTEO = '" + idsorteo + "' AND "
			+ " T.DIGITAL = 1 "
			+ "ORDER BY T.FOLIO ASC";	

		  System.out.println(">>>>SQL tal sectores:" + sql);
			
		ResultSet rs = db.getDatos(sql);
		
		return rs;
	}
		
	
	

	//TERMINA RETORNAR Y DEVOLVER
	
	
	

	
	
	
	

}
