package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.core.Seguimiento;
import com.core.Factory.LOG;
import com.core.Seguimiento.ASIGNACION;
import com.core.Factory;
import com.core.Parametros;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mAsignacionTalonariosNichos extends SuperModel 
{
	private static final String pre_formato = "FC2 - ";
	private int id;	
	private int idnicho;
	private int idSorteo;
	private int idBoleto;
	
	private int idtalonario;
	private int idSector;
	private String clave;
	private String sorteo;
	private String sector;
	private String[] arrTalonarios;
	private String descripcion;
	
	private String cadenaboletos;		
	private int regIdFormato;		
		
	public int getRegIdFormato() {
		return regIdFormato;
	}

	public void setRegIdFormato(int regIdFormato) {
		this.regIdFormato = regIdFormato;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdnicho() {
		return idnicho;
	}

	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
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

	
	
	public ResultSet getBoletos(mAsignacionTalonariosNichos obj){
		
		/*String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO,B.RETORNADO, B.FOLIODIGITAL,"
				+ "(select TOP 1 COUNT(PK1) FROM COMPRADORES WHERE  PK_BOLETO = S.PK_BOLETO) AS 'COMPRADOR', "
				+" (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
				+" (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
				+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
				+"(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
				+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
				+ "(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
				+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
				+ "FROM SORTEOS_NICHOS_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND S.PK_SORTEO = '"+obj.getIdSorteo()+"' AND S.PK_SECTOR = "+obj.getIdSector()+" AND PK_NICHO ="+obj.getIdnicho()+" AND S.PK_TALONARIO = "+obj.getFolioTalonario()+" " ;*/
		
				
		String sql = "SELECT S.PK_BOLETO,S.PK_ESTADO AS VENDIDO,S.ABONO,S.FOLIO,S.COSTO, S.TALONARIO, S.FORMATO_T AS FORMATO_T, S.PK_SORTEO, S.PK_TALONARIO,S.RETORNADO, S.FOLIODIGITAL,"
				+ "(select TOP 1 COUNT(CP.PK1) FROM COMPRADORES CP, COMPRADORES_BOLETOS CPB  WHERE  CP.PK1 = CPB.PK_COMPRADOR  AND CPB.PK_BOLETO = S.PK_BOLETO  ) AS 'COMPRADOR', "
			//	+" (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
				+" (select TOP 1 PK_SECTOR  from SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
				+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SECTORES_BOLETOS   where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
				+"(select TOP 1 PK_NICHO       from NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
				+ " (select TOP 1 NICHO  FROM NICHOS    WHERE PK1=(select TOP 1 PK_NICHO  from NICHOS_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
				+ "(select TOP 1 PK_COLABORADOR from COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
				+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
				+ "FROM VNICHOS_BOLETOS S WHERE S.PK_SORTEO = '"+obj.getIdSorteo()+"' AND S.PK_SECTOR = "+obj.getIdSector()+" AND S.PK_NICHO ="+obj.getIdnicho()+" AND S.PK_TALONARIO = "+obj.getIdtalonario()+" " ;
		
		//System.out.println(">>>>>>boletos"+sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
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

	public int getIdSector()
	{
		return idSector;
	}

	public void setIdSector(int idSector)
	{
		this.idSector = idSector;
	}

	public String getClave()
	{
		return clave;
	}

	public String[] getTalonarios()
	{
		return arrTalonarios;
	}

	public void setTalonarios(String[] arrTalonarios)
	{
		this.arrTalonarios = arrTalonarios;
	}

	public int getIdtalonario() {
		return idtalonario;
	}

	public void setIdtalonario(int idtalonario) {
		this.idtalonario = idtalonario;
	}

	public void setClave(String clave)
	{
		this.clave = clave;
	}

	public String getSorteo()
	{
		return sorteo;
	}

	public void setSorteo(String sorteo)
	{
		this.sorteo = sorteo;
	}
	
	public String getSector(){
		return this.sector;
	}
	
	public void setSector(String sector){
		this.sector = sector;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public String getImagen()
	{
		return imagen;
	}

	public void setImagen(String imagen)
	{
		this.imagen = imagen;
	}

	public int getActivo()
	{
		return activo;
	}

	public void setActivo(int activo)
	{
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


	public mAsignacionTalonariosNichos()
	{
		init();
	}
	
	
	
public boolean estaBoletoAsignadoNicho(mAsignacionTalonariosNichos obj){
	   	
	   	
	String sql = "";
	ResultSet rs = null;
	boolean existe = false;
	   	
	   	try {
	   		
	   		
	   		//sql = "SELECT * FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO ='"+obj.getIdSorteo()+"' AND PK_BOLETO ='"+obj.getIdBoleto()+"' AND PK_NICHO ='"+obj.getIdnicho()+"' ";//
	   		sql = "SELECT * FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO ='"+obj.getIdSorteo()+"' AND PK_BOLETO ='"+obj.getIdBoleto()+"' AND ASIGNADO = 1";//AND PK_NICHO ='"+obj.getIdnicho()+"'
		   	
		    rs = db.getDatos(sql);
		   	System.out.print( sql);    		
		   	existe =  rs.next();
	   		
		   	if(existe){return existe;}
	   		
	   		
	   		
	   		
        /*   sql = "SELECT * FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO ='"+obj.getIdSorteo()+"' AND PK_BOLETO ='"+obj.getIdBoleto()+"'";//
		   	
		    rs = db.getDatos(sql);
		   	System.out.print( sql);    		
		   	existe =  rs.next();
	   		
		   	if(existe){return existe;}*/
	   		
	   		
	   		
		   	return existe;
	   		
	   			
	   		
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	   	
	   	return false;
	   	
	}
	
	
	public ResultSet ObtenerBoletosTalonario(mAsignacionTalonariosNichos obj){
		String sql = "SELECT * FROM BOLETOS WHERE TALONARIO ='"+obj.getFolioTalonario()+"' AND  SORTEO='"+obj.getIdSorteo()+"' ";
		
		//System.out.println(sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public void ObtenerSorteo(mAsignacionTalonariosNichos obj){
	   	
	   	String sql = "SELECT * FROM SORTEOS WHERE PK1 ='"+obj.getIdSorteo()+"' ";
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	
	   	try {
			if(rs.next()) {				
				obj.setSorteo(rs.getString("SORTEO"));	
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	}
	
	public String Sector(int idsector) {

		db.con();
		String sql = "SELECT * FROM SECTORES WHERE PK1 = " + idsector + "";
		String sector = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					sector = //rs.getString("CLAVE") + "-" +
							rs.getString("SECTOR");
				}

				//rs.close();
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		return sector;

	}

	public String Nicho(int idnicho) {

		db.con();
		String sql = "SELECT * FROM NICHOS WHERE PK1 = " + idnicho + "";
		String nicho = null;
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {

					nicho = //rs.getString("CLAVE") + "-" + 
							rs.getString("NICHO");
				}

				//rs.close();
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		return nicho;

	}
	
	public ResultSet obtenerTalonario(mAsignacionTalonariosNichos obj) {
		
		String sql= "";
		ResultSet rs= null;
		int idtalonario = getidTalonario(obj.getFolioTalonario(), obj.getIdSorteo());
		//System.out.println("idtalonario:     " + idtalonario);

		sql = "SELECT * FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO ='"
				+ obj.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ obj.getIdSector()
				+ "' AND PK_TALONARIO = '"
				+ idtalonario + "'"
				+ " AND PK_NICHO = "
				+ obj.getIdnicho();
		//System.out.println(">>>>BUSCA talonarios nich:     " + sql);
		rs = db.getDatos(sql);
	

		return rs;
	}

	
	public ResultSet listarModalBoletos()
	{

		String sql = "SELECT PK1 FROM SORTEOS_SECTORES_BOLETOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarModalBoletos(int idsorteo, int idsector,String search)
	{
		
		String sql = " ";
				
		//sql += "SELECT B.FOLIO FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '" + idsorteo + "' AND SB.PK_SECTOR = '"+idsector+"' ";
		sql += "SELECT FOLIO FROM VSECTORES_BOLETOS WHERE PK_SECTOR = '"+idsector+"' ";
		
		

		if (search != "") {
			sql += " AND ((FOLIO = '" + search + "') OR (TALONARIO = '" + search + "'))   ";//TRAER FOLIO TAL
		}
		
		int numero = db.ContarFilas(sql);
		
		return numero;
	}

	public ResultSet paginacionModalBoletos(int pg, int numreg, String search,int idsorteo, int idsector)
	{
		/*String sql = "SELECT B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, SB.ASIGNADO AS ASIGNADO  ";
		sql += "FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_SORTEO = '" + idsorteo + "' AND SB.PK_SECTOR = '"+idsector+"' ";*/
		
		String sql = "SELECT  PK_BOLETO ,FOLIO,COSTO, TALONARIO, FORMATO_T, ASIGNADO_NICHO AS ASIGNADO"
				+ " FROM VSECTORES_BOLETOS "
				+ " WHERE PK_SECTOR = '"+idsector+"' ";

		if (search != "") {
			sql += " AND ((FOLIO = '" + search + "') OR (TALONARIO = '" + search + "'))   ";
		}
		
		
		System.out.println(">>>>SQL modal BOL NICHOS:"+sql);

		sql += "ORDER BY CAST(TALONARIO AS INT)  ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; //-- not sure if you need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";
       
		ResultSet rs = db.getDatos(sql);
		
		return rs;
	}
	
	
	

	public ResultSet listarModal()
	{

		String sql = "SELECT PK1  FROM SORTEOS_SECTORES_TALONARIOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarModal(int idsorteo,int idsector, String search)
	{
		
		/*String sql = "SELECT T.FOLIO AS FOLIO";
		sql += " FROM SORTEOS_SECTORES_TALONARIOS ST, TALONARIOS T  WHERE ST.PK_TALONARIO = T.PK1 AND ST.PK_SECTOR = '"+idsector+"' AND ST.PK_SORTEO = '"+idsorteo+"' ";*/
				
		String sql = "SELECT FOLIO AS FOLIO";
		sql += " FROM VSECTORES_TALONARIOS  WHERE PK_SECTOR = '"+idsector+"'";		
		
		if (search != "")
		{
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}

		//System.out.println(">>>>SQL modal tal:"+sql);
		
		int numero = db.ContarFilas(sql);
		return numero;
	}
	
		

	public ResultSet paginacionModal(int pg, int numreg, String search,int idsorteo,int idsector)
	{
		/*String sql = "SELECT T.PK1 AS PK1"
				+ ",T.FOLIO AS FOLIO"
				+ ",ST.NUMBOLETOS AS NUMBOLETOS"
				+ ",T.SORTEO"
				+ ",T.FORMATO AS FORMATO"
				+ ",ST.MONTO AS MONTO"
				+ ",ST.ASIGNADO AS ASIGNADO"
				+ ",ST.DISPONIBLES"
				+ ",T.DIGITAL"
				+ " FROM SORTEOS_SECTORES_TALONARIOS ST, TALONARIOS T  WHERE ST.PK_TALONARIO = T.PK1 AND ST.PK_SECTOR = '"+idsector+"' AND ST.PK_SORTEO = '"+idsorteo+"' ";*/
	//	System.out.println(">>>>SQL modal tal:"+sql);
		
		String sql = "SELECT PK_TALONARIO AS PK1"
		+ ",FOLIO AS FOLIO"
		+ ",NUMBOLETOS AS NUMBOLETOS"		
		+ ",FORMATO_TALONARIO AS FORMATO"
		+ ",MONTO AS MONTO"
		//+ ",ASIGNADO AS ASIGNADO"
		+ ",DISPONIBLES"
		+ ",DIGITAL"
		+ " FROM VSECTORES_TALONARIOS WHERE PK_SECTOR = '"+idsector+"'";
			System.out.println(">>>>SQL modal tal:"+sql);		
		
		
		if (search != "")
		{
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}

		sql += " ORDER BY CAST(FOLIO AS INT) ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; //-- not sure if you need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";
		
		System.out.println(">>>>SQL modal tal VER:"+sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public ResultSet listarTalonarios()
	{
		String sql = "SELECT * FROM SORTEOS_NICHOS_TALONARIOS";
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public int contarTalonarios(int idnicho,int idsorteo,int idsector)
	{
		String sql = "SELECT COUNT(PK_TALONARIO) AS 'MAX' FROM VNICHOS_TALONARIOS WHERE PK_NICHO = '" + idnicho + "' AND PK_SECTOR = '" + idsector + "' AND PK_SORTEO = '" + idsorteo + "' ";
		
		
		int numero = 0;
		try {
			ResultSet res = db.getDatos(sql);
			if (res != null && res.next())
				numero = res.getInt("MAX");
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		return numero;
	}

	public ResultSet paginacionTalonarios(int pg, int numreg, String search, int idnicho,int idsorteo,int idsector)
	{
		String sql = "SELECT PK_TALONARIO AS PK1, FOLIO, NUMBOLETOS, PK_SORTEO, FORMATO_TALONARIO AS FORMATO, MONTO, PK_TALONARIO, DIGITAL AS 'ELECTRONICO' "
				+ " FROM VNICHOS_TALONARIOS "
			    	+ "WHERE PK_NICHO = '" + idnicho + "' "
			    	+ "AND PK_SECTOR = '" + idsector + "' "
			    	+ "AND PK_SORTEO = '" + idsorteo + "' ";

		if (search != "")
		{
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}
		
		String sqltotalreg =  sql;

		sql += " ORDER BY FOLIO ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; //-- not sure if you need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		  //System.out.println(">>>>SQL:" + sql);
			
		ResultSet rs = db.getDatos(sql);
		
		
		//total reg	
		//System.out.println(">>>>SQL TOTAL REG:" + sqltotalreg);    	
    	this.setTotalregistros( db.ContarFilas(sqltotalreg));	
		
		
		
		return rs;
	}
	
	
	
public void guardarAsignacionNichoTalonario_SP(String talonescadena, mAsignacionTalonariosNichos obj, SesionDatos sesion) {
		
		db.con();		
		
		System.out.println("TALONARIOS nichos ");		
		
		// TODO - insertar
		  List<Parametros> list = new ArrayList<Parametros>();
		  list.add(new Parametros("COUNT", 0));
		  list.add(new Parametros("PK_SECTOR", obj.getIdSector()));
		  list.add(new Parametros("PK_NICHO", obj.getIdnicho()));
		  list.add(new Parametros("FORMATO", obj.getFormato()));
		  list.add(new Parametros("USUARIO", sesion.nickName));		
		  list.add(new Parametros("LIST", talonescadena));	
		  System.out.println("CADENA tal: "+talonescadena);					
		
		  int result = db.execStoreProcedureIntId("sp_guardarAsignacionNichoTalonario", list);
		  System.out.println(" result:"+result); 		  
		
		
	}
	
	
	

	public void guardarAsignacionNichoTalonario(String[] arrTalonarios, mAsignacionTalonariosNichos obj, SesionDatos sesion) {
		db.con();
		ArrayList<String> log_list_tals = new ArrayList<String>();
		ArrayList<String> log_list_tals_2 = new ArrayList<String>();
		
		int numtalonarios = arrTalonarios.length;
		//System.out.println("numtalonarios: "+numtalonarios);
		
		this.cadenaboletos = "";
		String cadenatalonarios = "";
		boolean  bandera = true;
		boolean  bupdatetalcompletos = false;
		double montototal = 0;
		int numboletos = 0;
		
		
		//System.out.println("TAlONARIOS ");
		//for (String talonario_id : arrTalonarios) System.out.print(", " + talonario_id);
		//System.out.println("");
		String sql = "";
		ResultSet rs = null;

		for (String talonario_id : arrTalonarios) {
			
			sql = " SELECT * FROM SORTEOS_NICHOS_TALONARIOS WHERE "
					+ " PK_SORTEO ='" + obj.getIdSorteo() + "'"
					+ " AND PK_SECTOR = '" + obj.getIdSector() + "'"
					+ " AND PK_TALONARIO = '" + talonario_id + "'";
			       //no nicho

			//System.out.println("BUSCA:     " + sql);
			rs = db.getDatos(sql);

			

			sql = "SELECT T.FOLIO,ST.NUMBOLETOS,ST.MONTO,ST.DISPONIBLES FROM TALONARIOS T,SORTEOS_SECTORES_TALONARIOS ST "
					+ "WHERE  ST.PK_TALONARIO = T.PK1"
					+ " AND PK_SECTOR = '"+ obj.getIdSector()
					+ "' AND T.PK1 = '"	+ talonario_id + "'";
			//System.out.println("BUSCA TALONARIOS y talon sec:     " + sql);
			ResultSet talonario = db.getDatos(sql);

			try {
				if (!rs.next()) {

					if (talonario.next()) {
						sql = "INSERT INTO SORTEOS_NICHOS_TALONARIOS"
								+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,PK_NICHO,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,DISPONIBLES)"
								+ " VALUES ('"
								+ obj.getIdSorteo() + "','"
								+ obj.getIdSector() + "','"
								+ talonario_id + "','"
								+ obj.getIdnicho() + "',0,'"
								+ talonario.getInt("NUMBOLETOS") + "','"
								+ talonario.getInt("MONTO") + "','"
								+ pre_formato + obj.getFormato() + "','"
								+ sesion.nickName + "','"
								+ talonario.getInt("NUMBOLETOS") + "')";
						
						//System.out.println("INSERT:     " + sql);
						db.execQuery(sql);
						
						log_list_tals.add(talonario_id);

						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(obj, talonario.getString("FOLIO"), talonario_id, sesion);

						sql = "UPDATE [SORTEOS_SECTORES_TALONARIOS] SET DISPONIBLES=0,ASIGNADO=1"
								+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
								+ " AND PK_SECTOR=" + obj.getIdSector()
								+ " AND PK_TALONARIO=" + talonario_id;
						//System.out.println(">>>>>UPDATE SORTEOS_SECTORES_TALONARIOS REST" + sql);
						db.execQuery(sql);
						
                           // --- SE GUARDA EL REGISTRO DE FOTMATO NICHOS ()  ---
						
						//+ " (PK_SORTEO,PK_SECTOR,FOLIO,TALONARIO,BOLETOS,NUMTALONARIOS,NUMBOLETOS,MONTO,USUARIO)"
						bupdatetalcompletos = true;
						montototal += (double)talonario.getInt("MONTO");
						cadenatalonarios += talonario.getString("FOLIO")+",";
						numboletos += talonario.getInt("NUMBOLETOS");
						
						
						if(bandera){	
							
							
							sql = "INSERT INTO SORTEOS_FORMATOS_NICHOS"
									+ " (PK_SORTEO,PK_SECTOR,PK_NICHO,FOLIO,NUMTALONARIOS,USUARIO)"
									+ " VALUES ('"
									+ obj.getIdSorteo() + "','"
									+ obj.getIdSector() + "','"
									+ obj.getIdnicho() + "','"
									+ pre_formato + obj.getFormato() + "','"									
								//	+ talonescadena + "','"
								//	+ boletos + "','" //*
									+ numtalonarios + "','"//*							
								//	+ talonario.getInt("NUMBOLETOS") + "')";
								//	+ talonario.getInt("MONTO") + "','"
									+ sesion.nickName + "')";
									

							//System.out.println(">>>>>REGISTRO DE FORMATO NICHOS COMP" + sql);
							//db.execQuery(sql);	
							  int id = db.execQuerySelectId(sql); 
							    
								obj.setRegIdFormato(id);
							
							
							bandera = false;
							
							
						}		
						
						

						// --- Se guarda un registro de seguimiento ---
						try {
							
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.NICHO
									, obj.getIdSorteo(), obj.getIdSector(), obj.getIdnicho(), 0
									, ASIGNACION.TALONARIO
									, Long.valueOf(talonario_id), 0
									, 'N'
									, talonario.getInt("MONTO"), 0.0
									, talonario.getInt("NUMBOLETOS")
									, pre_formato + obj.getFormato()
									, "nic-tals/bols-compls");
						} catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					}

				} else {

					//System.out.println(">>>>>AQUI COMP 2");

					sql = " SELECT * FROM SORTEOS_NICHOS_TALONARIOS"
							+ " WHERE PK_SORTEO ='"
							+ obj.getIdSorteo() + "'"
							+ " AND PK_SECTOR = '" + obj.getIdSector() + "'"
							+ " AND PK_NICHO = '" + obj.getIdnicho() + "'"
							+ " AND PK_TALONARIO = '" + talonario_id + "'";

					//System.out.println("BUSCA:     " + sql);
					rs = db.getDatos(sql);

					if (talonario.next() && rs.next()) {

						//System.out.println(">>>>>AQUI UPDATE");

						//System.out.println("entro si: ");

						double costoboleto = (obtenerCostoBoletoTal(talonario.getString("FOLIO"))) * talonario.getInt("DISPONIBLES");
						// CALCULA EL MONTO Y NUMERO DE BOLETOS SI EXISTE EL
						// TALONARIO
						double monto = rs.getInt("MONTO") + costoboleto;
						int conteoboleos = rs.getInt("NUMBOLETOS")
								+ talonario.getInt("DISPONIBLES");

						sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS]"
								+ " SET DISPONIBLES ='"
								+ conteoboleos
								+ "', NUMBOLETOS="
								+ conteoboleos
								+ ", MONTO="
								+ monto
								+ " WHERE PK_SORTEO="
								+ obj.getIdSorteo()
								+ " AND PK_TALONARIO="
								+ talonario_id
								+ " AND PK_SECTOR = "
								+ obj.getIdSector()
								+ " AND PK_NICHO ="
								+ obj.getIdnicho();

						//System.out.println(">>>>>UPDATE TALONARIO REST" + sql);
						db.execQuery(sql);
						
						log_list_tals_2.add(talonario_id);

						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(obj, talonario.getString("FOLIO"), talonario_id, sesion);

						sql = "UPDATE [SORTEOS_SECTORES_TALONARIOS] SET DISPONIBLES=0,ASIGNADO=1"
								+ " WHERE PK_SORTEO="
								+ obj.getIdSorteo()
								+ " AND PK_SECTOR="
								+ obj.getIdSector()
								+ " AND PK_TALONARIO=" + talonario_id;
						//System.out.println(">>>>>UPDATE SORTEOS_SECTORES_TALONARIOS REST" + sql);

						db.execQuery(sql);

					} else {

						//System.out.println(">>>>>AQUI ENTRA ELSE(UP) INSERTAR TALONARIO ");

						double monto = (obtenerCostoBoletoTal(talonario.getString("FOLIO"))) * talonario.getInt("DISPONIBLES");
						// CALCULA EL MONTO Y NUMERO DE BOLETOS SI NO EXISTE EL
						// TALONARIO EN EL NICHO

						sql = " INSERT INTO SORTEOS_NICHOS_TALONARIOS"
								+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,PK_NICHO,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,DISPONIBLES)"
								+ " VALUES ('"
								+ obj.getIdSorteo() + "','"
								+ obj.getIdSector() + "','"
								+ talonario_id + "','"
								+ obj.getIdnicho() + "',0,'"
								+ talonario.getInt("DISPONIBLES") + "','"
								+ monto + "','"
								+ pre_formato + obj.getFormato() + "','"
								+ sesion.nickName + "','"
								+ talonario.getInt("DISPONIBLES") + "')";
						//System.out.println("INSERT:     " + sql);
						db.execQuery(sql);
						
						log_list_tals.add(talonario_id);

						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(obj, talonario.getString("FOLIO"), talonario_id, sesion);

						sql = "UPDATE SORTEOS_SECTORES_TALONARIOS SET DISPONIBLES=0,ASIGNADO=1"
								+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
								+ " AND PK_SECTOR=" + obj.getIdSector()
								+ " AND PK_TALONARIO=" + talonario_id;
						//System.out.println(">>>>>UPDATE SORTEOS_SECTORES_TALONARIOS REST" + sql);
						db.execQuery(sql);

						// --- Se guarda un registro de seguimiento ---
						try {
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.NICHO
									, obj.getIdSorteo(), obj.getIdSector(), obj.getIdnicho(), 0
									, ASIGNACION.TALONARIO
									, Long.valueOf(talonario_id), 0
									, 'N'
									, monto, 0.0
									, talonario.getInt("DISPONIBLES")  // Para que coincida con el insert
									, pre_formato + obj.getFormato()
									, "nic-tals/bols-dispbs");
						} catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					}

				}

			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		}		
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this,"guardarAsignacionNichoTalonario", sesion.toShortString() + ", tals=" + log_list_tals);
			this.Log(sesion, LOG.EDITADO, this,"guardarAsignacionNichoTalonario", sesion.toShortString() + ", tals=" + log_list_tals_2);
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		// --- SE ACTUALIZA EL REGISTRO DE FOTMATO NICHOS ()  ---
			
			if(bupdatetalcompletos)
			{
				
			// cadenatalonarios = cadenatalonarios.substring(0, cadenatalonarios.length()-1); 
			// this.cadenaboletos = this.cadenaboletos.substring(0, this.cadenaboletos.length()-1); 
			 
			sql = " UPDATE SORTEOS_FORMATOS_NICHOS SET"
					+ " TALONARIOS= '" + cadenatalonarios + "', "
					+ " MONTO= '" + montototal + "', "
					+ " NUMBOLETOS= '" + numboletos + "', "	
					+ " BOLETOS= '" + this.cadenaboletos + "'"
					+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
					+ " AND PK_SECTOR=" + obj.getIdSector()
					+ " AND PK1=" + obj.getRegIdFormato();			
			
			
			//System.out.println(">>>>>ACTUALIZAR REGISTRO DEL SORTEOS_FORMATOS_NICHOS" + sql);

			db.execQuery(sql);	
			
			
			}
		
		
		
		
	}
	
	
	public void AsignarTalonariosBoletosCompletos(mAsignacionTalonariosNichos obj, String talonariofolio, String talonario_id, SesionDatos sesion) {
		String sql = "";
		db.con();
		String sqlINSERT = "";
		//System.out.println("ASIGNACION BOLETOS COMPLETOS");
		
		ArrayList<Integer> log_list_bols = new ArrayList<Integer>();

		// sql = "SELECT * FROM BOLETOS WHERE TALONARIO = '" + talonariofolio +
		// "' AND SORTEO = '" + obj.getIdSorteo() + "'";//HACE MATCH CON SORTEOS
		// BOLETOS
		sql = "SELECT B.*  FROM SORTEOS_SECTORES_BOLETOS SB, BOLETOS B WHERE SB.PK_BOLETO = B.PK1"
				+ " AND SB.PK_SORTEO = '" + obj.getIdSorteo()
				+ "' AND SB.PK_SECTOR = '" + obj.getIdSector()
				+ "' AND SB.PK_TALONARIO = '" + talonariofolio + "' ";
		//System.out.println(">>SELECT SECTORES BOL: " + sql);
		ResultSet boletos = db.getDatos(sql);
		try {
			if (boletos != null) {

				while (boletos.next()) {
					
					    	sql = "SELECT * FROM SORTEOS_NICHOS_BOLETOS WHERE PK_BOLETO = '"
							+ boletos.getString("PK1")
							+ "' AND  PK_SORTEO ='"+ obj.getIdSorteo()
							+ "' AND PK_SECTOR = '" + obj.getIdSector()
							+ "' AND PK_TALONARIO = '" + talonariofolio + "'";
							// AND PK_NICHO ='" +
							// obj.getIdnicho() + "'
					//System.out.println("BUSCA SORTEOS_NICHOS_BOLETOS:     " + sql);
					// System.out.println("BUSCA SORTEOS_SECTORES_BOLETOS:     "+sql);
					ResultSet rs = db.getDatos(sql);

					if (!rs.next()) { 
						
						
						this.cadenaboletos += boletos.getString("FOLIO")+",";
						
						
						
						sqlINSERT = "INSERT INTO SORTEOS_NICHOS_BOLETOS (PK_BOLETO,PK_TALONARIO,PK_SORTEO,PK_SECTOR,PK_NICHO,FORMATO,USUARIO,ASIGNADO) VALUES ('"
								+ boletos.getString("PK1") + "','"
								+ boletos.getString("TALONARIO") + "','"
								+ boletos.getString("SORTEO") + "','"
								+ obj.getIdSector() + "','"
								+ obj.getIdnicho() + "','"
								+ pre_formato + obj.getFormato() + "','"
								+ sesion.nickName + "',0)";

						db.execQuery(sqlINSERT);
						
						log_list_bols.add(boletos.getInt("PK1"));
						//System.out.println("GUARDA BOLETOS:    " + sqlINSERT);

						sql = "UPDATE [SORTEOS_SECTORES_BOLETOS] SET ASIGNADO=1"
								+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
								+ " AND PK_SECTOR=" + obj.getIdSector()
								+ " AND PK_TALONARIO=" + talonariofolio;
						db.execQuery(sql);

						// --- Se guarda un registro de seguimiento ---
						try {
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.NICHO
									, obj.getIdSorteo(), obj.getIdSector(), obj.getIdnicho(), 0
									, ASIGNACION.BOLETO
									, boletos.getInt("PK_TALONARIO"), boletos.getInt("PK1")
									, 'N'
									, boletos.getInt("COSTO"), 0.0
									, 1
									, pre_formato + obj.getFormato()
									, "nic-bols-compls");
						} catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					}

				}

			}

		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "AsignarTalonariosBoletosCompletos", sesion.toShortString() + ", bols=" + log_list_bols.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	
	}
	
		
	public void guardarAsignacionNichoBoletos_SP(String Boletos, mAsignacionTalonariosNichos obj, SesionDatos sesion){		
		db.con();	
		
		System.out.println("BOLETOS NICHOS");		
		
		// TODO - insertar
		  List<Parametros> list = new ArrayList<Parametros>();
		  list.add(new Parametros("COUNT", 0));
		  list.add(new Parametros("PK_SECTOR", obj.getIdSector()));
		  list.add(new Parametros("PK_NICHO", obj.getIdnicho()));
		  list.add(new Parametros("FORMATO", obj.getFormato()));
		  list.add(new Parametros("USUARIO", sesion.nickName));	
		  list.add(new Parametros("LIST", Boletos));			
		
		  int result = db.execStoreProcedureIntId("sp_guardarAsignacionNichoBoletos", list);
		  System.out.println(" result BOL:"+result);
		  
		  
		  /*try {
				this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", bols=" + Boletos.toString());
				//this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", tals=" + log_list_talonarios.toString());
			}catch(Exception ex) { Factory.Error(ex, "Log"); }	*/
			
		
		
	}	
	
	
	
	public void guardarAsignacionNichoBoletos(String[] arrBoletos, mAsignacionTalonariosNichos obj, SesionDatos sesion)
	{
		db.con();
		//System.out.println( " BOLETOSi: ");
		ArrayList<Integer> log_list_bols = new ArrayList<Integer>();
		
		String[] arrBoletoscadena;
		int boletos_id = 0;
		String talonarios_folio = "";
		
		for(int i=0;i<arrBoletos.length;i++){
						
			arrBoletoscadena = arrBoletos[i].split("#%#");			
			boletos_id = Integer.valueOf(arrBoletoscadena[0]);
			//System.out.println( "id boleto: "+i+": " +boletos_id);
			talonarios_folio = arrBoletoscadena[1];
			//System.out.println( "folio tal: "+i+": " +talonarios_folio);
			
			String sql = " SELECT PK1 FROM SORTEOS_NICHOS_BOLETOS WHERE PK_NICHO = '" + obj.getIdnicho() + "' AND PK_SORTEO ='" + obj.getIdSorteo() + "' AND PK_SECTOR = '" + obj.getIdSector() + "' AND PK_BOLETO = '" + boletos_id + "'";
			//System.out.println("BUSCA:     "+sql);
			ResultSet rs = db.getDatos(sql);

			try
			{
				if (!rs.next())
				{
					////System.out.println("INSERT:     "+sql);
					String sql2 =
						"INSERT INTO SORTEOS_NICHOS_BOLETOS"+
						" (PK_SORTEO,PK_SECTOR,PK_BOLETO,ASIGNADO,PK_TALONARIO,PK_NICHO,FORMATO,USUARIO)"+
						" VALUES ('"
						+ obj.getIdSorteo() + "','"
						+ obj.getIdSector() + "','"
						+ boletos_id + "',0,'"
						+ talonarios_folio + "','"
						+ obj.getIdnicho()+"','"
						+ pre_formato + obj.getFormato() + "','"
						+ sesion.nickName + "')";
					//System.out.println(sql2);
					db.execQuery(sql2);
					
					log_list_bols.add(boletos_id);
					 
					guardarNichoTalonario(talonarios_folio,obj,boletos_id, sesion);
					 
					sql = "UPDATE [SORTEOS_SECTORES_BOLETOS]"
						+ " SET ASIGNADO=1"
						+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
						+ " AND PK_TALONARIO="+ talonarios_folio
						+ " AND PK_BOLETO=" + boletos_id;
					db.execQuery(sql);
					
					// --- Se guarda un registro de seguimiento ---
					try {
						int idTalonario = super.getIdTalonario(obj.getIdSorteo(), talonarios_folio);
						double boletoCosto = super.getCostoBoleto(boletos_id);

						Seguimiento.guardaAsignacion(db
								, ASIGNACION.NICHO
								, obj.getIdSorteo(), obj.getIdSector(), obj.getIdnicho(), 0
								, ASIGNACION.BOLETO
								, boletos_id, idTalonario 
								, 'N'
								, boletoCosto, 0.0
								, 1
								, pre_formato + obj.getFormato()
								, "nic-bols");
					} catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				}
			}
			catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		}

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionNichoBoletos", sesion.toShortString() + ", bol=" + log_list_bols.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	
	
	
	
	
	public void guardarNichoTalonario(String talonarios_folio, mAsignacionTalonariosNichos obj, int boletos_id, SesionDatos sesion) {
		db.con();
		//System.out.println(" TAONARIOSbol ");
		ArrayList<Integer> log_list_tals = new ArrayList<Integer>();

		String sql = "";
		ResultSet rs = null;
		int idtalonario = 0;

		double monto = 0;
		int conteoboleos = 0;
		double costoboleto = obtenerCostoBoleto(boletos_id);

		try {

			idtalonario = getidTalonario(talonarios_folio, obj.getIdSorteo());
			//System.out.println("idtalonario:     " + idtalonario);

			sql = "SELECT * FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO ='" + obj.getIdSorteo() + "'  AND PK_NICHO = '" + obj.getIdnicho() + "'   AND PK_SECTOR = '" + obj.getIdSector() + "' AND PK_TALONARIO = '" + idtalonario + "'";
			//System.out.println("BUSCA:     " + sql);
			rs = db.getDatos(sql);

			// sql = "SELECT * FROM TALONARIOS WHERE PK1 = '" + idtalonario +
			// "'";//hacer match sorteos tal
			// System.out.println("BUSCA TALONARIOS:     "+sql);
			sql = "SELECT T.PK1,T.FOLIO,ST.NUMBOLETOS,ST.MONTO,ST.DISPONIBLES FROM TALONARIOS T,SORTEOS_SECTORES_TALONARIOS ST WHERE  ST.PK_TALONARIO = T.PK1 AND T.PK1 = '" + idtalonario + "'  AND PK_SECTOR = '" + obj.getIdSector() + "'";
			//System.out.println("BUSCA TALONARIOS y talon sec:     " + sql);

			ResultSet talonario = db.getDatos(sql);

			if (!rs.next()) {

				if (talonario.next()) {

					// CALCULA EL MONTO Y NUMERO DE BOLETOS SI NO EXISTE EL
					// TALONARIO
					conteoboleos += 1;
					monto = costoboleto;

					
					sql = " INSERT INTO SORTEOS_NICHOS_TALONARIOS"
							+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,PK_NICHO,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,DISPONIBLES)"
							+ " VALUES ('" + obj.getIdSorteo() + "','"
							+ obj.getIdSector() + "','"
							+ talonario.getInt("PK1") + "','"
							+ obj.getIdnicho() + "',0,'"
							+ conteoboleos + "','"
							+ monto + "','"
							+ pre_formato + obj.getFormato() + "','"
							+ sesion.nickName + "','"
							+ conteoboleos + "')";

					//System.out.println(">>>>INSERT TALONARIOS BOL" + sql);
					db.execQuery(sql);
					
					log_list_tals.add(talonario.getInt("PK1"));

					int disponibles = talonario.getInt("DISPONIBLES") - conteoboleos;
					//System.out.println(">>>>>>DIFERENCIA BOL:     " + disponibles);

					sql = "UPDATE [SORTEOS_SECTORES_TALONARIOS] SET DISPONIBLES='" + disponibles
							+ "' ,ASIGNADO=1"
							+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
							+ " AND PK_SECTOR=" + obj.getIdSector()
							+ " AND PK_TALONARIO=" + talonario.getInt("PK1");

					db.execQuery(sql);

					// --- Se guarda un registro de seguimiento ---
					try{
						//int idTalonario = super.getIdTalonario(obj.getIdSorteo(), talonarios_folio);
						
						Seguimiento.guardaAsignacion(db
								, ASIGNACION.NICHO
								, obj.getIdSorteo(), obj.getIdSector(), obj.getIdnicho(), 0
								, ASIGNACION.TALONARIO
								, 0, talonario.getInt("PK1") 
								, 'N'
								, monto, 0.0
								, conteoboleos
								, pre_formato + obj.getFormato()
								, "nic-tals");
					} catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				}

			} else {

				// CALCULA EL MONTO Y NUMERO DE BOLETOS SI EXISTE EL TALONARIO
				monto = rs.getInt("MONTO") + costoboleto;
				conteoboleos = rs.getInt("NUMBOLETOS") + 1;

				talonario.next();

				sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES=" + conteoboleos
						+ ", NUMBOLETOS="+ conteoboleos
						+ ", MONTO=" + monto
						+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
						+ " AND PK_TALONARIO=" + idtalonario
						+ " AND PK_SECTOR = " + obj.getIdSector()
						+ " AND PK_NICHO =" + obj.getIdnicho();

				//System.out.println("UDATE NUMBOL Y MONTO" + sql);

				db.execQuery(sql);

				// TABLA SORTEOS_TALONARIOS
				int disponibles = talonario.getInt("DISPONIBLES") - 1;

				sql = "UPDATE [SORTEOS_SECTORES_TALONARIOS] SET DISPONIBLES='"
						+ disponibles
						+ "' ,ASIGNADO=1"
						+ " WHERE PK_SORTEO=" + obj.getIdSorteo()
						+ " AND PK_SECTOR=" + obj.getIdSector()
						+ " AND PK_TALONARIO=" + talonario.getInt("PK1");

				db.execQuery(sql);

			}

		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }


		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarNichoTalonario", sesion.toShortString() + ", tals=" + log_list_tals);
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	
	
	public double obtenerCostoBoletoTal(String idtalonario){
		
		double id = 0;
		String sql = "SELECT TOP 1 COSTO FROM BOLETOS WHERE TALONARIO ='"+idtalonario+"' ";	
		//System.out.println("obtener costoboleto "+sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				id = rs.getInt("COSTO");	
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return id;
	}
	
	public double obtenerCostoBoleto(int idboleto){
		
		double id = 0;
		String sql = "SELECT COSTO FROM BOLETOS WHERE PK1 ='"+idboleto+"' ";	
		//System.out.println("obtener costoboleto "+sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
				id = rs.getDouble("COSTO");	
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		return id;
	}
	
	
	public int getidTalonario(String folio,int sorteo){
		String sql = "SELECT PK1 FROM TALONARIOS WHERE FOLIO ='"+folio+"' AND  SORTEO='"+sorteo+"' ";
		int id = 0;
		
		//System.out.println(sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				id = rs.getInt("PK1");	
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		return id;
	}
	
	
	public int Borrar(mAsignacionTalonariosNichos obj) {

		db.con();
		String sql = "DELETE FROM SORTEOS_NICHOS_TALONARIOS WHERE PK1='" + obj.getId() + "' ";// AND PK_SORTEO = '"+obj.getIdsorteo()+"'
		// System.out.println(sql);

		int res = db.execQuery(sql);

		// sql =
		// "DELETE FROM BOLETOS WHERE TALONARIO='"+obj.getFolio()+"' AND SORTEO = '"+obj.getIdsorteo()+"'";
		// res = db.execQuery(sql);

		return res;

	}


	public void eliminaTalonario(int folioTalonario, SesionDatos sesion) {
		
		
		
		String sql = "";
		
		String sqlS = "SELECT NUMBOLETOS FROM SORTEOS_SECTORES_TALONARIOS "
				+ " WHERE PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
		        + " AND PK_SECTOR=" + getIdSector();
		

		//System.out.println("SORTEOS_TAL"+sqlS);
		ResultSet rs = db.getDatos(sqlS);
		
		
		
		try {
			if (rs.next()) {
				
				
			
				sql =	" UPDATE [SORTEOS_SECTORES_TALONARIOS] SET DISPONIBLES = '"+ rs.getInt("NUMBOLETOS") +"',  ASIGNADO=0"
						+ " WHERE (PK_TALONARIO=" + getIdtalonario()
						+ " AND PK_SORTEO=" + getIdSorteo()	
						+ " AND PK_SECTOR=" + getIdSector()
						+ ")\n";
				
				//System.out.println("UPDATE tal: " + sql);
				db.execQuery(sql);
				

			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
				
		
		
		
		 sql =
				" DELETE [SORTEOS_NICHOS_TALONARIOS]"
				+ " WHERE (PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ " AND PK_NICHO=" + getIdnicho()
				+ ")\n"
				
				+ " DELETE [SORTEOS_NICHOS_BOLETOS]"
				+ " WHERE (PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ " AND PK_NICHO=" + getIdnicho()
				+ ")\n"

				+ " DELETE [SORTEOS_COLABORADORES_TALONARIOS]"
				+ " WHERE (PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ " AND PK_NICHO=" + getIdnicho()
				+ ")\n"
				
				+ " DELETE [SORTEOS_COLABORADORES_BOLETOS]"
				+ " WHERE (PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ " AND PK_NICHO=" + getIdnicho()
				+ ")\n"
	
					
				+ " UPDATE [SORTEOS_SECTORES_BOLETOS] SET ASIGNADO=0"
				+ " WHERE (PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ ")\n"
				;
		
		db.execQuery(sql);
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminaTalonario", sesion.toShortString() + ", Elimina todos los talonarios del nicho");
		}catch(Exception ex) { Factory.Error(ex, "Log"); }
	}
	
	
	
	
	
	/* ********************EMPIEZA RETORNO Y DEVOLVER**********************************/
	
	//obtiene el num boletos del TALONARIOS
	public int getNumeroBoletos(){		
		
		String sql="SELECT NUMBOLETOS  FROM VNICHOS_TALONARIOS "				
				+ " WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdnicho()+"' "			 
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
		
				
		String sql="SELECT  COUNT(PK_TALONARIO) AS 'BVENDIDOSR' FROM VNICHOS_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdnicho()+"' "				 
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
		
		
		String sql="SELECT  COUNT(PK_TALONARIO) AS 'BVENDIDOS' FROM VNICHOS_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdnicho()+"' "					 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V'";	
		
		
		
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
		
		
		String sqlS="SELECT  FOLIO, PK_BOLETO, COSTO, PK_ESTADO FROM VNICHOS_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdnicho()+"' "					 
				+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V' ";			
		
		rs = db.getDatos(sqlS);	
		
		try {

			while (rs.next()) {			
					
					
					sql =   "UPDATE BOLETOS SET "
						    + "RETORNADO = 1"				
							+ " WHERE PK1 = "+rs.getInt("PK_BOLETO")+" "
							+ " AND PK_TALONARIO = '"+this.getIdtalonario()+"'";
						
						
						System.out.println(">>>UPDATE BOLETOS : "+sql);
						db.execQuery(sql);						
										

						sql = 	//FORMATOS NICHOS								
								"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR,PK_NICHO, PK_ESTADO,USUARIO,FECHA_R)"
								+ " VALUES ('"									
                                + "FC4 - "  + this.getFormato() + "','"	
                                + rs.getInt("PK_BOLETO") + "','"	
								+ this.getIdSector() + "','"
								+ this.getIdnicho() + "','"							
								+ rs.getString("PK_ESTADO") + "','"	
								+ sesion.nickName + "',"	
								+ "GETDATE())";							

						System.out.println(">>>>>REGISTRO DE FORMATO NICHOS fc4" + sql);						
						db.execQuery(sql);						
						
						
						sql =//SEGUIMIENTO NICHOS									
								"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_NICHO, PK_ESTADO, USUARIO,PK_TALONARIO)"
								+ " VALUES ("
								+ "'Nichos',"
								+ "'Retorno',"
								+ "'ret-nich-bols',"
								+ "'"									
                                + "FC4 - "  + this.getFormato() + "','"	
                                + rs.getInt("PK_BOLETO") + "','"	
								+ this.getIdSector() + "','"
								+ this.getIdnicho() + "','"							
								+ rs.getString("PK_ESTADO") + "','"	
								+ sesion.nickName + "','"
								+ this.getIdtalonario() + "')";							

						System.out.println(">>>>>SEGUIMIENTO NICHOS fc4" + sql);						
						db.execQuery(sql);	
						
						cadena = sesion.toShortString() + ", bol=" + rs.getInt("PK_BOLETO");					
						
						 sql =//LOG NICHOS									
									"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO,FECHA_R)"
									+ " VALUES ("
									+ "'mAsignacionTalonariosNichos',"
									+ "'retornotalonariosfc4',"
									+ "'Retorno','"                               
	                                + cadena + "','"									
	                            	+ sesion.nickName+ "',"
									+ "GETDATE())";							

							System.out.println(">>>>>LOG NICHOS fc4" + sql);						
							db.execQuery(sql);						
						
					

			}
		
				
		
		} catch (SQLException ex) {
			com.core.Factory.Error(ex, sql);
		}
	}	
	
	public boolean getTalonariosCompletamenteNoVendidos(){		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totalnovendidos = false;	
		
		
		
		String sql="SELECT  COUNT(*) AS 'BNOVENDIDOS' FROM VNICHOS_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdnicho()+"' "				
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N'";
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
	
public boolean getTalonariosDigitalesCompletamenteVendidos_sc(){		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totalnovendidos = false;		
	
		
		String sql="SELECT  COUNT(PK_TALONARIO) AS 'BVENDIDOSDIG_sc' FROM VNICHOS_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdnicho()+"' "				 
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


public boolean ExisteBoletosNichoNV(){
	
	String sql="SELECT  COUNT(*) AS 'MAX' FROM VNICHOS_BOLETOS "
			+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND PK_NICHO = '"+this.getIdnicho()+"' "				
			+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N'";
            //+ " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";
	
	System.out.println(" MAX Nichos boletos no vendidos Nichos: " + sql);
	ResultSet rs = db.getDatos(sql);
	
	try {
		if (rs.next())
			return rs.getInt("MAX") > 0;

	} catch (SQLException e) { Factory.Error(e, sql); }
	return false;
}
	
	
	
public boolean ExisteCargaColaborador(){
		
		String sql = "SELECT COUNT(*) AS 'MAX' FROM [VCOLABORADORES_TALONARIOS]"
				+ " WHERE PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SECTOR = " + getIdSector()
				+ " AND PK_SORTEO = " + getIdSorteo()				
				+ " AND PK_NICHO=" + getIdnicho();
		
		//checar
		
		System.out.println(" MAX COLABORADORES: " + sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next())
				return rs.getInt("MAX") > 0;

		} catch (SQLException e) { Factory.Error(e, sql); }
		return false;
	}
	 	
	
	
	//FC5
public String devolvertalonariosfc5(int folioTalonario, SesionDatos sesion) {
	
	String sql = "";
	String stringboletos = "";
	  String valida = "";
		String cadena = "";
	  

	
	/*boolean existecargacolaborador =  ExisteCargaColaborador();
	    
	if(existecargacolaborador){
	    	
	    	
		valida = "TRUE";
		
	    	
   }else{*/
   	
	
		
	      sql="SELECT   FOLIO, PK_BOLETO ,COSTO, PK_ESTADO FROM VNICHOS_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdnicho()+"' "					 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N' ";
				//+ " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";	
	 
	       System.out.println("BOL nich : "+sql);
	       ResultSet nichos = db.getDatos(sql);		 
		
		
		try {
			if (nichos.next()) {			
				
					
					do{
				
						sql =	 "DELETE [NICHOS_BOLETOS]"
								+ " WHERE (PK_BOLETO =" + nichos.getInt("PK_BOLETO")								
								+ " AND PK_SECTOR=" + getIdSector()
								+ " AND PK_NICHO=" + getIdnicho()									
								+ ")\n";
			
								System.out.println(" DELETE: " + sql);
								db.execQuery(sql);								
									
									
									sql = 	//FORMATOS NICHOS											
											"INSERT INTO FORMATOS(FOLIO, PK_BOLETO, PK_SECTOR, PK_NICHO, PK_ESTADO,USUARIO,FECHA_R)"
											+ " VALUES ('"									
			                                + "FC5 - "  + this.getFormato() + "','"	
			                                + nichos.getInt("PK_BOLETO") + "','"	
											+ this.getIdSector() + "','"
											+ this.getIdnicho() + "','"										
											+ nichos.getString("PK_ESTADO") + "','"	
											+ sesion.nickName + "',"	
											+ "GETDATE())";							

									System.out.println(">>>>>REGISTRO DE FORMATO NICHOS fc5" + sql);						
									db.execQuery(sql);	
									
									
									sql =//SEGUIMIENTO NICHOS									
											"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_NICHO, PK_ESTADO, USUARIO,PK_TALONARIO)"
											+ " VALUES ("
											+ "'Nichos',"
											+ "'Devolución',"
											+ "'devol-nich-bols',"
											+ "'"									
			                                + "FC5 - "  + this.getFormato() + "','"	
			                                + nichos.getInt("PK_BOLETO") + "','"	
											+ this.getIdSector() + "','"
											+ this.getIdnicho() + "','"											
											+ nichos.getString("PK_ESTADO") + "','"	
											+ sesion.nickName + "','"
											+ this.getIdtalonario() + "')";							

									System.out.println(">>>>>SEGUIMIENTO NICHOS fc5" + sql);						
									db.execQuery(sql);	
									
									cadena = sesion.toShortString() + ", bol=" + nichos.getInt("PK_BOLETO");
								//	cadena = "{sec=" + CAST(@PK_SECTOR AS nvarchar(20)) +",nich=" + CAST(@PK_NICHO AS nvarchar(20)) + ",col=" + CAST(@PK_COLABORADOR AS nvarchar(20)) + "},bol={" + CAST(@id_boleto AS nvarchar(20)) + "}";
									
									 sql =//LOG NICHOS									
												"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO,FECHA_R)"
												+ " VALUES ("
												+ "'mAsignacionTalonariosNichos',"
												+ "'devolvertalonariosfc5',"
												+ "'Borrado','"                               
				                                + cadena + "','"									
				                            	+ sesion.nickName+ "',"
												+ "GETDATE())";							

										System.out.println(">>>>>LOG NICHOS fc5" + sql);						
										db.execQuery(sql);									
										
												
						
					}while(nichos.next());					
				
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }	
		
		
		
     // }//fin else
	
	
	return valida;
		
    
	}


public boolean getTalParcialmenteVendidos(){		
	
	//int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
	boolean totaltretornados = false;
	
	
	String sql="SELECT  COUNT(*) AS 'BPARCIALES' FROM VNICHOS_BOLETOS "
			+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND PK_NICHO = '"+this.getIdnicho()+"' "				 
			+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'P'";		
	
	
	System.out.println(">>>SELECT BOLETOS  total Vendidos: "+sql);
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


	
public boolean ExisteCargaColaboradorBoletosNoV(){
	
	String sql="SELECT  COUNT(*) AS 'MAX' FROM VCOLABORADORES_BOLETOS "
			+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND PK_NICHO = '"+this.getIdnicho()+"' "				
			+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N'  ";
           // + " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";
	
	System.out.println(" MAX COLABORADORES boletos no vendidos: " + sql);
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
	String stringboletos = "";	
    String valida = "";
    String cadena = "";	    
    
	
	/*boolean existecargacolaboradorb =  ExisteCargaColaboradorBoletosNoV();
	    
	if(existecargacolaboradorb){
	    	
	    	
		valida = "TRUE";
		
	    	
   }else{*/	
		
	
	 sql="SELECT  FOLIO, PK_BOLETO,COSTO,PK_ESTADO FROM VNICHOS_BOLETOS "
			+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND PK_NICHO = '"+this.getIdnicho()+"' "				 
			+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N' ";
		   // + "AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";	
	
	ResultSet nichos = db.getDatos(sql);		
		
		
		try {			
			
			if (nichos.next()) {			
			
				
				do{					
									
					sql =	 "DELETE [NICHOS_BOLETOS]"
							+ " WHERE (PK_BOLETO =" + nichos.getInt("PK_BOLETO")								
							+ " AND PK_SECTOR=" + getIdSector()
							+ " AND PK_NICHO=" + getIdnicho()									
							+ ")\n";
		
							System.out.println(" DELETE: " + sql);
							db.execQuery(sql);						
												
								
								sql = 	//FORMATOS NICHOS
										
										"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR,PK_NICHO, PK_ESTADO,USUARIO,FECHA_R)"
										+ " VALUES ('"									
		                                + "FC5B - "  + this.getFormato() + "','"	
		                                + nichos.getInt("PK_BOLETO") + "','"	
										+ this.getIdSector() + "','"
										+ this.getIdnicho() + "','"										
										+ nichos.getString("PK_ESTADO") + "','"	
										+ sesion.nickName + "',"	
										+ "GETDATE())";							

								System.out.println(">>>>>REGISTRO DE FORMATO NICHOS fc5b" + sql);						
								db.execQuery(sql);	
								
								
								sql =//SEGUIMIENTO NICHOS									
										"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_NICHO, PK_ESTADO, USUARIO,PK_TALONARIO)"
										+ " VALUES ("
										+ "'Nichos',"
										+ "'Devolución',"
										+ "'devol-nich-bols',"
										+ "'"									
		                                + "FC5B - "  + this.getFormato() + "','"	
		                                + nichos.getInt("PK_BOLETO") + "','"	
										+ this.getIdSector() + "','"
										+ this.getIdnicho() + "','"											
										+ nichos.getString("PK_ESTADO") + "','"	
										+ sesion.nickName + "','"
										+ this.getIdtalonario() + "')";						

								System.out.println(">>>>>SEGUIMIENTO NICHOS fc5b" + sql);						
								db.execQuery(sql);	
								
								cadena = sesion.toShortString() + ", bol=" + nichos.getInt("PK_BOLETO");							
								
								 sql =//LOG NICHOS									
											"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO, FECHA_R)"
											+ " VALUES ("
											+ "'mAsignacionTalonariosNichos',"
											+ "'devolvertalonariosfc5B',"
											+ "'Borrado','"                               
			                                + cadena + "','"									
			                            	+ sesion.nickName+ "',"
											+ "GETDATE())";							

									System.out.println(">>>>>LOG NICHOS fc5b" + sql);						
									db.execQuery(sql);							
					
					
				}while(nichos.next());				
				
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }			
		
    // }//termina else
	
	   return valida;
	}



	//TERMINA RETORNAR Y DEVOLVER


//RETORNO MASIVO


public ResultSet ObtenerTalonarios(int idsorteo,int idsector,int idnicho)
{
	
String sql = "SELECT T.PK1 AS PK1, T.FOLIO, RNT.NUMBOLETOS, T.SORTEO, T.FORMATO, RNT.MONTO,RNT.PK1 AS IDTALON,RNT.PK_TALONARIO, T.DIGITAL AS 'ELECTRONICO',"
        + "(SELECT TOP 1 PK_COLABORADOR FROM SORTEOS_COLABORADORES_TALONARIOS "
		+ "WHERE PK_TALONARIO = RNT.PK_TALONARIO"
		+ " AND PK_NICHO = " + idnicho + " "		
		+ "AND PK_SECTOR = " + idsector + " "
		+ "AND PK_SORTEO = " + idsorteo + ") AS PK_COLABORADOR "
		+ " FROM SORTEOS_NICHOS_TALONARIOS RNT, TALONARIOS T"
		+ " WHERE RNT.PK_NICHO = '" + idnicho + "' AND "
	    + "RNT.PK_TALONARIO = T.PK1 "
		
		//+ " AND RNT.PK_TALONARIO = '17202' "//QUITARRRRRRRRRRRRRRRRRRR	
		
		+ "AND RNT.PK_SECTOR = '" + idsector + "' AND "
		+ "RNT.PK_SORTEO = '" + idsorteo + "' AND "
		+ " T.DIGITAL = 1 "
		+ "ORDER BY T.FOLIO ASC";	

	  System.out.println(">>>>SQL tal:" + sql);
		
	ResultSet rs = db.getDatos(sql);
	
	return rs;
}



public HashMap<Integer,Integer> buscaIntervaloTalonarios(int limiteInf, int limiteSup, int idsorteo, int idsector) {
				
	String sql = "SELECT PK_TALONARIO AS PK1, DISPONIBLES FROM VSECTORES_TALONARIOS"
	+ " WHERE PK_SORTEO = '"+idsorteo+ "' "
	+ "AND PK_SECTOR = '"+idsector+ "'"
	+ " AND "+ limiteInf + " <= FOLIO AND " + limiteSup + " >= FOLIO "
	+ "ORDER BY PK1 ASC";
	
	

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

	
	
	
	
	
	

}






