package com.sorteo.sorteos.model;
import com.core.Factory;
import com.core.Parametros;
import com.core.Seguimiento;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;
import com.core.Seguimiento.ASIGNACION;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mAsignacionTalonariosColaboradores extends SuperModel {
	
	
	private static final String pre_formato = "FC3 - ";
	private static final String pre_formatoB = "FC3B - ";
	
	private int idSorteo;
	private int idSector;
	
	private int idNicho;
	private int idColaborador;
	
	private int idtalonario;
	private int idBoleto;
	
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

	public ResultSet getBoletos(mAsignacionTalonariosColaboradores obj) {
		
		
		
		/*String sql = "SELECT S.PK_BOLETO,B.VENDIDO,B.ABONO,B.PK1,B.FOLIO,B.COSTO, B.TALONARIO, B.FORMATO, B.SORTEO, B.PK_TALONARIO,B.RETORNADO, B.FOLIODIGITAL, "
				+ "(select TOP 1 COUNT(PK1) FROM COMPRADORES WHERE  PK_BOLETO = S.PK_BOLETO) AS 'COMPRADOR', "
				+" (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
				+" (select TOP 1 PK_SECTOR  from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
				+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SORTEOS_SECTORES_BOLETOS       where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
				+"(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
				+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from SORTEOS_NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
				+ "(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
				+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from SORTEOS_COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
				+ "FROM SORTEOS_COLABORADORES_BOLETOS S, BOLETOS B WHERE S.PK_BOLETO = B.PK1 AND S.PK_SORTEO = '"+obj.getIdSorteo()+"' AND S.PK_SECTOR = "+obj.getIdSector()+" AND PK_NICHO ="+obj.getIdNicho()+" AND PK_COLABORADOR = "+obj.getIdColaborador()+" AND S.PK_TALONARIO = "+obj.getFolioTalonario()+" " ;*/

		
		String sql = "SELECT S.PK_BOLETO,S.PK_ESTADO AS VENDIDO,S.ABONO,S.FOLIO,S.COSTO, S.TALONARIO,S.PK_SORTEO, S.PK_TALONARIO,S.RETORNADO, S.FOLIODIGITAL, "
		+ "(select TOP 1 COUNT(CP.PK1) FROM COMPRADORES CP, COMPRADORES_BOLETOS CPB  WHERE  CP.PK1 = CPB.PK_COMPRADOR  AND CPB.PK_BOLETO = S.PK_BOLETO  ) AS 'COMPRADOR', "
		//+" (select TOP 1 VENDIDO FROM TALONARIOS WHERE PK1 = B.PK_TALONARIO) AS 'VENDIDOTALONARIO',"
		+" (select TOP 1 PK_SECTOR  from SECTORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_SECTOR',"
		+ " (select TOP 1 SECTOR FROM SECTORES      WHERE PK1=(select TOP 1 PK_SECTOR      from SECTORES_BOLETOS   where PK_BOLETO = S.PK_BOLETO)) AS 'SECTOR',"
		+"(select TOP 1 PK_NICHO       from NICHOS_BOLETOS where PK_BOLETO = S.PK_BOLETO) AS 'PK_NICHO',"
		+ " (select TOP 1 NICHO  FROM NICHOS        WHERE PK1=(select TOP 1 PK_NICHO       from NICHOS_BOLETOS         where PK_BOLETO = S.PK_BOLETO)) AS 'NICHO',"
		+ "(select TOP 1 PK_COLABORADOR from COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO) AS 'PK_COLABORADOR',"
		+ " (select TOP 1 NOMBRE+' '+APATERNO  FROM COLABORADORES WHERE PK1=(select TOP 1 PK_COLABORADOR from COLABORADORES_BOLETOS  where PK_BOLETO = S.PK_BOLETO)) AS 'COLABORADOR'"
		+ "FROM VCOLABORADORES_BOLETOS S WHERE S.PK_SORTEO = '"+obj.getIdSorteo()+"' AND S.PK_SECTOR = "+obj.getIdSector()+" AND S.PK_NICHO ="+obj.getIdNicho()+" AND S.PK_COLABORADOR = "+obj.getIdColaborador()+" AND S.PK_TALONARIO = "+obj.getIdtalonario()+" " ;

		
		System.out.println("getBoletos:>>>>>>>>"+sql);
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

	public mAsignacionTalonariosColaboradores() {
		init();
	}
	
	
	public ResultSet obtenerTalonario(mAsignacionTalonariosColaboradores obj) {
		
		String sql= "";
		ResultSet rs= null;
		int idtalonario = getidTalonario(obj.getFolioTalonario(), obj.getIdSorteo());
		////System.out.println("idtalonario:     " + idtalonario);

		sql = "SELECT * FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO ='"
				+ obj.getIdSorteo()
				+ "' AND PK_SECTOR = '"
				+ obj.getIdSector()
				+ "' AND PK_TALONARIO = '"
				+ idtalonario + "'"
				+ " AND PK_NICHO = "
				+ obj.getIdNicho()
				+ " AND PK_COLABORADOR = "
				+ obj.getIdColaborador();
		
		////System.out.println(">>>>BUSCA talonarios :     " + sql);
		rs = db.getDatos(sql);
	

		return rs;
	}

	public boolean estaBoletoAsignadoColaborador(mAsignacionTalonariosColaboradores obj) {

		String sql = "SELECT * FROM VNICHOS_BOLETOS"
				+ " WHERE PK_BOLETO = " + obj.getIdBoleto()
				+ " AND PK_SORTEO = " + obj.getIdSorteo()
				+ " AND PK_SECTOR = " + obj.getIdSector()
				+ " AND PK_NICHO = " + obj.getIdNicho()
				+ " AND ASIGNADO = "+ 1;

		ResultSet rs = db.getDatos(sql);
		//System.out.print(sql);

		try {

			return rs.next();

		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		return false;

	}

	public void consultaSorteo(mAsignacionTalonariosColaboradores obj) {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 ='" + obj.getIdSorteo()
				+ "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				obj.setSorteo(rs.getString("SORTEO"));

			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	}
	
	public String consultaSector(int idsector) {

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
	
	public String consultaNicho(int idnicho) {

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

	public String consultaColaborador(int idcolaborador) {
		db.con();
		String sql = "SELECT NOMBRE+' '+APATERNO+' '+AMATERNO AS COLABORADOR FROM COLABORADORES WHERE PK1 = "
				+ idcolaborador + "";
		String colaborador = "";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				if (rs.next()) {

					colaborador = rs.getString("COLABORADOR");

				}

				//rs.close();
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

		return colaborador;
	}
	
	public ResultSet listarModalBoletos() {

		String sql = "SELECT PK1 FROM BOLETOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarModalBoletos(SesionDatos sesion, String search) {
		String sql = "SELECT FOLIO";
		sql += " FROM VNICHOS_BOLETOS "
				+ " WHERE PK_SORTEO = " + sesion.pkSorteo
				+ " AND PK_SECTOR = " + sesion.pkSector
				+ " AND PK_NICHO = " + sesion.pkNicho;
		
		
		System.out.println(">>> contarModalBoletos : "+sql);

		
		if (search != "") {
			sql += " AND ((FOLIO = '" + search + "') OR (PK_TALONARIO = '" + search + "'))   ";
		}
		
		
		
		int numero = db.ContarFilas(sql);

		return numero;
	}

	public ResultSet paginacionModalBoletos(int pg, int numreg, String search,SesionDatos sesion) {
		String sql = "SELECT PK_BOLETO AS PK1,FOLIO,COSTO, TALONARIO, FORMATO_T AS FORMATO, ASIGNADO_COLABORADOR AS ASIGNADO  ";
		sql += "FROM VNICHOS_BOLETOS "
				+ " WHERE PK_SORTEO = " + sesion.pkSorteo
				+ " AND PK_SECTOR = " + sesion.pkSector
				+ " AND PK_NICHO = " + sesion.pkNicho;

		if (search != "") {
			sql += " AND ((FOLIO = '" + search + "') OR (PK_TALONARIO = '" + search + "'))   ";
		}

		sql += "ORDER BY CAST(PK_TALONARIO AS INT) ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);

		return rs;
	}

	public ResultSet listarModal() {

		String sql = "SELECT PK1  FROM SORTEOS_NICHOS_TALONARIOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public int contarModal(SesionDatos sesion,String search) {
		
		String sql = "SELECT FOLIO "
				+ " FROM VNICHOS_TALONARIOS ST"
				+ " WHERE ST.PK_SORTEO = " + sesion.pkSorteo
				+ " AND ST.PK_SECTOR = " + sesion.pkSector
				+ " AND ST.PK_NICHO = " + sesion.pkNicho;
		
		if (search != "") {
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}			
				
		int numero = db.ContarFilas(sql);
		return numero;
	}

	public ResultSet paginacionModal(int pg, int numreg, String search,SesionDatos sesion) {
		
		String sql = "SELECT PK_TALONARIO AS PK1,FOLIO,NUMBOLETOS,PK_SORTEO,FORMATO_TALONARIO AS FORMATO,MONTO,DISPONIBLES,DIGITAL";
		sql += " FROM VNICHOS_TALONARIOS ST WHERE ST.PK_SORTEO = " + sesion.pkSorteo
				+ " AND ST.PK_SECTOR = " + sesion.pkSector
				+ " AND ST.PK_NICHO = " + sesion.pkNicho;
				
				  

		if (search != "") {
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}

		sql += " ORDER BY CAST(FOLIO AS INT) ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public ResultSet listarTalonarios() {
		String sql = "SELECT * FROM SORTEOS_COLABORADORES_TALONARIOS";
		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public int contarTalonariosXColaborador(SesionDatos sesion) {
		
		String sql = "SELECT COUNT(PK_TALONARIO) AS 'MAX'"
				+ " FROM VCOLABORADORES_TALONARIOS "
				+ " WHERE PK_SORTEO = " + sesion.pkSorteo
				+ " AND PK_SECTOR = " + sesion.pkSector
				+ " AND PK_NICHO = " + sesion.pkNicho
				+ " AND PK_COLABORADOR = " + sesion.pkColaborador;
		
		int numero = 0;
		try {
			ResultSet res = db.getDatos(sql);
			if (res != null && res.next())
				numero = res.getInt("MAX");
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		return numero;
	}

	public ResultSet paginacionTalonariosXColaborador(int pg, int numreg, String search,SesionDatos sesion) {
		
		String sql = "SELECT PK_TALONARIO AS PK1, FOLIO, NUMBOLETOS, PK_SORTEO, FORMATO, MONTO, DIGITAL AS 'ELECTRONICO' "
				+ " FROM VCOLABORADORES_TALONARIOS"
				+ " WHERE PK_SORTEO = " +  sesion.pkSorteo
				+ " AND PK_SECTOR = " +  sesion.pkSector
				+ " AND PK_NICHO = " + sesion.pkNicho
				+ " AND PK_COLABORADOR = " +sesion.pkColaborador;

		if (search != "") {
			sql += " AND FOLIO LIKE '%" + search + "%'  ";
		}
		
		
		String sqltotalreg =  sql;

		sql += " ORDER BY PK_TALONARIO ASC ";
		sql += "OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if you
															// need -1
		sql += "FETCH NEXT " + numreg + " ROWS ONLY";

		ResultSet rs = db.getDatos(sql);
		
		
		//total reg	
		System.out.println(">>>>SQL :" + sql);    	
		this.setTotalregistros( db.ContarFilas(sqltotalreg));		
		
		
		return rs;
	}
	
	
	
public String AsignacionTalonarioCompleto_Colaborador_SP(int tipo_talonario, int numtal_colab, mAsignacionTalonariosColaboradores obj, SesionDatos sesion) {
		
		db.con();		
		
		System.out.println("TALONARIOS completos por COLABORADOR ");		
		
		// TODO - insertar
		  List<Parametros> list = new ArrayList<Parametros>();
		  list.add(new Parametros("COUNT", 0));
		  list.add(new Parametros("countColab", 0));
		  list.add(new Parametros("PK_SORTEO", obj.getIdSorteo()));
		  list.add(new Parametros("PK_SECTOR", obj.getIdSector()));
		  list.add(new Parametros("PK_NICHO", obj.getIdNicho()));
		  list.add(new Parametros("TIPO_TALONARIO", tipo_talonario));
		  list.add(new Parametros("NUMTAL", numtal_colab));
		  list.add(new Parametros("FORMATO", obj.getFormato()));
		  list.add(new Parametros("USUARIO", sesion.nickName));					
		
		  String result = db.execStoreProcedureIntId2("sp_AsignacionTalonarioCompleto_Colaborador", list);		   
		  System.out.println(" result:"+result);	  
		  
		  //int numeroTalonarios = db.numero1;
		 // int numeroColaboradores = db.numero2;
		  
		  return result;
		 
		
		
	}
	
	
	
public void guardarAsignacionTalonarioColaborador_SP(String talonescadena, mAsignacionTalonariosColaboradores obj, SesionDatos sesion) {
		
		db.con();		
		
		System.out.println("TALONARIOS COLABORADORES ");		
		
		// TODO - insertar
		  List<Parametros> list = new ArrayList<Parametros>();
		  list.add(new Parametros("COUNT", 0));
		  list.add(new Parametros("PK_SECTOR", obj.getIdSector()));
		  list.add(new Parametros("PK_NICHO", obj.getIdNicho()));
		  list.add(new Parametros("PK_COLABORADOR", obj.getIdColaborador()));
		  list.add(new Parametros("FORMATO", obj.getFormato()));
		  list.add(new Parametros("USUARIO", sesion.nickName));		
		  list.add(new Parametros("LIST", talonescadena));	
		  System.out.println("CADENA tal: "+talonescadena);					
		
		  int result = db.execStoreProcedureIntId("sp_guardarAsignacionTalonarioColaborador", list);
		  System.out.println(" result:"+result); 		  
		
		
	}	
	
	
	

	public void guardarAsignacionTalonarioColaborador(String[] arrTalonarios, mAsignacionTalonariosColaboradores modelo, SesionDatos sesion) {
		db.con();
		
		int numtalonarios = arrTalonarios.length;
		//System.out.println("numtalonarios: "+numtalonarios);
		
		this.cadenaboletos = "";
		String cadenatalonarios = ""; 
		boolean  bandera = true;
		boolean  bupdatetalcompletos = false;
		double montototal = 0;
		int numboletos = 0;	
		
		//System.out.println("TALONARIOS ");
		for (String talonario_id : arrTalonarios)
			System.out.print(", " + talonario_id);
		//System.out.println("");
		String sql;
		ResultSet rs = null;

		for (String talonario_id : arrTalonarios) {
			
			sql = " SELECT * FROM SORTEOS_COLABORADORES_TALONARIOS"
				+ " WHERE PK_SORTEO = " + modelo.getIdSorteo()
				+ " AND PK_SECTOR = " + modelo.getIdSector()					
				+ " AND PK_NICHO = " +modelo.getIdNicho()
				//+ " AND PK_COLABORADOR = " + modelo.getIdColaborador()					
				+ " AND PK_TALONARIO = " + talonario_id;

			
			//System.out.println("BUSCA:     " + sql);
			rs = db.getDatos(sql);			
			
		
			
			sql = "SELECT T.FOLIO,ST.NUMBOLETOS,ST.MONTO,ST.DISPONIBLES FROM TALONARIOS T,SORTEOS_NICHOS_TALONARIOS ST "
					+ "WHERE  ST.PK_TALONARIO = T.PK1 "
					+ " AND  ST.PK_SORTEO = '"+ modelo.getIdSorteo() + "' "
					+ " AND ST.PK_SECTOR = '" + modelo.getIdSector() + "' "
					+ " AND ST.PK_NICHO = '" + modelo.getIdNicho()+ "'"
					+ " AND T.PK1 = '"+talonario_id+"' ";
			//System.out.println("BUSCA TALONARIOS y talon sec:     "+sql);
			System.out.println("BUSCA TALONARIOS:     " + sql);
			ResultSet talonario = db.getDatos(sql);

			try {
				if (!rs.next()) {

					if (talonario.next()) {

						//	//System.out.println("INSERT:     " + sql);
						sql = "INSERT INTO SORTEOS_COLABORADORES_TALONARIOS"
								+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,PK_NICHO,PK_COLABORADOR,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,DISPONIBLES)"
								+ " VALUES ('" + modelo.getIdSorteo() + "','"
								+ modelo.getIdSector() + "','"								
								+ talonario_id + "','"
								+ modelo.getIdNicho() + "','"
								+ modelo.getIdColaborador()
								+ "',0"
								//+ talonario.getInt("NUMBOLETOS") + "','"
								+ ",0,"								
								//+ talonario.getInt("MONTO") + "','"
								+ "0.0,'"	
								+ pre_formato + modelo.getFormato() + "','"
								+ sesion.nickName + "',"
								//+ talonario.getInt("NUMBOLETOS")+"')";
								+ "0)";	
								
						System.out.println("INSERT "+sql);
						db.execQuery(sql);

						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(modelo,talonario.getString("FOLIO"), sesion, talonario.getInt("NUMBOLETOS"),talonario.getInt("MONTO"),talonario_id );
						
						/*sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES=0,ASIGNADO=1"
					                + " WHERE PK_SORTEO=" + modelo.getIdSorteo()
					                + " AND PK_SECTOR = " + modelo.getIdSector()
				                    + " AND PK_NICHO = " +modelo.getIdNicho()
					                + " AND PK_TALONARIO=" + talonario_id;
						//System.out.println(">>>>>UPDATE SORTEOS_NICHOS_TALONARIOS REST"+sql);
					    db.execQuery(sql);*/
					    
					    
                       // --- SE GUARDA EL REGISTRO DE FOTMATO COLABORADOR ()  ---
						
						//+ " (PK_SORTEO,PK_SECTOR,FOLIO,TALONARIO,BOLETOS,NUMTALONARIOS,NUMBOLETOS,MONTO,USUARIO)"
						bupdatetalcompletos = true;
						montototal += (double)talonario.getInt("MONTO");
						cadenatalonarios += talonario.getString("FOLIO")+",";
						numboletos += talonario.getInt("NUMBOLETOS");
					    
					    
                       if(bandera){	
                    	   
							
							sql = "INSERT INTO SORTEOS_FORMATOS_COLABORADOR"
									+ " (PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,FOLIO,NUMTALONARIOS,USUARIO)"
									+ " VALUES ('"
									+ modelo.getIdSorteo() + "','"
									+ modelo.getIdSector() + "','"
									+ modelo.getIdNicho() + "','"
									+ modelo.getIdColaborador() + "','"
									+ pre_formato + modelo.getFormato() + "','"									
								//	+ talonescadena + "','"
								//	+ boletos + "','" //*
									+ numtalonarios + "','"//*							
								//	+ talonario.getInt("NUMBOLETOS") + "')";
								//	+ talonario.getInt("MONTO") + "','"
									+ sesion.nickName + "')";
									

							//System.out.println(">>>>>REGISTRO DE FORMATO COLABORADOR COMP" + sql);
							//db.execQuery(sql);	
							  int id = db.execQuerySelectId(sql); 
							    
							  modelo.setRegIdFormato(id);
							
							
							bandera = false;
							
							
						}		
					    
						

						// --- Se guarda un registro de seguimiento ---
						try {
							
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.COLABORADOR
									, modelo.getIdSorteo(), modelo.getIdSector(), modelo.getIdNicho(), modelo.getIdColaborador()
									, ASIGNACION.TALONARIO
									, Long.valueOf(talonario_id), 0
									, 'N'
									, talonario.getInt("MONTO"), 0.0
									, talonario.getInt("NUMBOLETOS")
									, pre_formato + modelo.getFormato()
									, "col-tals/bols-compls");
						}
						catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					}

				}else{
										
					
					//System.out.println(">>>>>AQUI COMP 2");
					
					sql = " SELECT * FROM SORTEOS_COLABORADORES_TALONARIOS"
							+ " WHERE PK_SORTEO = " + modelo.getIdSorteo()
							+ " AND PK_SECTOR = " + modelo.getIdSector()					
							+ " AND PK_NICHO = " +modelo.getIdNicho()
							+ " AND PK_COLABORADOR = " +modelo.getIdColaborador()					
							+ " AND PK_TALONARIO = " + talonario_id;

						
						//System.out.println("BUSCA:     " + sql);
						rs = db.getDatos(sql);
					
					
					
				 if (talonario.next()&&rs.next()) {						
					
					
						//System.out.println("entro si: " );
					
						
						double costoboleto = (obtenerCostoBoletoTal( talonario.getString("FOLIO"), modelo.getIdSorteo() ) ) * talonario.getInt("DISPONIBLES");
						//CALCULA EL MONTO Y NUMERO DE BOLETOS SI EXISTE EL TALONARIO
						double	monto = rs.getInt("MONTO")+costoboleto;
						int conteoboleos = rs.getInt("NUMBOLETOS")+talonario.getInt("DISPONIBLES");
						
									
						/* sql = "UPDATE [SORTEOS_COLABORADORES_TALONARIOS] "
						 		+ "SET "
						 		//+ "DISPONIBLES ='"+conteoboleos+"', "
						 		//+ "NUMBOLETOS=" +conteoboleos
						 	//	+" MONTO="+monto
						 		+" WHERE PK_SORTEO=" + modelo.getIdSorteo()
				        	    + " AND PK_TALONARIO=" + talonario_id +" "
				        	    + " AND PK_SECTOR = "+modelo.getIdSector()
				        	    +" AND PK_NICHO ="+modelo.getIdNicho()
				        	    +" AND PK_COLABORADOR ="+modelo.getIdColaborador();
						 
								
						
						//System.out.println(">>>>>UPDATE TALONARIO REST"+sql);
						db.execQuery(sql);*/
						
						

						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(modelo,talonario.getString("FOLIO"), sesion, conteoboleos, monto, talonario_id);

					              
					          /*    sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES=0,ASIGNADO=1"
							                + " WHERE PK_SORTEO=" + modelo.getIdSorteo()
							                + " AND PK_SECTOR = " + modelo.getIdSector()
						                    + " AND PK_NICHO = " +modelo.getIdNicho()
							                + " AND PK_TALONARIO=" + talonario_id;
								  System.out.println(">>>>>UPDATE SORTEOS_NICHOS_TALONARIOS REST"+sql);
							              db.execQuery(sql);*/
								

				  }else{
					  
					  //System.out.println(">>>>>AQUI ENTRA ELSE(UP) INSERTAR TALONARIO ");
					  
					  double monto = (obtenerCostoBoletoTal( talonario.getString("FOLIO") , modelo.getIdSorteo()) ) * talonario.getInt("DISPONIBLES");
						//CALCULA EL MONTO Y NUMERO DE BOLETOS SI NO EXISTE EL TALONARIO EN EL NICHO
									  
					   					  
					  
					  sql = "INSERT INTO SORTEOS_COLABORADORES_TALONARIOS"
								+ " (PK_SORTEO,PK_SECTOR,PK_TALONARIO,PK_NICHO,PK_COLABORADOR,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,DISPONIBLES)"
								+ " VALUES ('"
								+ modelo.getIdSorteo() + "','"
								+ modelo.getIdSector() + "','"								
								+ talonario_id + "','"
								+ modelo.getIdNicho() + "','"
								+ modelo.getIdColaborador()	
								+ "',0"
								+ ",0,"	
							//	+ talonario.getInt("DISPONIBLES") + "','"
							//+ monto 
								+ "0.0,'"	
								+ pre_formato + modelo.getFormato() + "','"
								+ sesion.nickName + "',"
								//+ talonario.getInt("DISPONIBLES")+"')";
								+ "0)";
					  System.out.println("INSERT:     "+sql);
						db.execQuery(sql);

						
						// ASINAR BOLETOS COMPLETOS
						AsignarTalonariosBoletosCompletos(modelo,talonario.getString("FOLIO"),sesion, talonario.getInt("DISPONIBLES"), monto, talonario_id);
						
						
						/*  sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES=0,ASIGNADO=1"
					                + " WHERE PK_SORTEO=" + modelo.getIdSorteo()
					                + " AND PK_SECTOR = " + modelo.getIdSector()					
				                    + " AND PK_NICHO = " + modelo.getIdNicho()				                 
					                + " AND PK_TALONARIO=" + talonario_id;
						  //System.out.println(">>>>>UPDATE SORTEOS_NICHOS_TALONARIOS REST"+sql);
					     db.execQuery(sql);*/
					  
					  
							// --- Se guarda un registro de seguimiento ---
							try {
								Seguimiento.guardaAsignacion(db
										, ASIGNACION.COLABORADOR
										, modelo.getIdSorteo(), modelo.getIdSector(), modelo.getIdNicho(), modelo.getIdColaborador()
										, ASIGNACION.TALONARIO
										, Long.valueOf(talonario_id), 0
										, 'N'
										, monto, 0.0
										, talonario.getInt("DISPONIBLES")  // Para que coincida con el insert
										, pre_formato + modelo.getFormato()
										, "col-tals/bols-dispbs");
							}
							catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					}
				}

			} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		}
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionTalonarioColaborador",sesion.toShortString() + ", tals=" + Arrays.toString(arrTalonarios));
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
		

		// --- SE ACTUALIZA EL REGISTRO DE FOTMATO COLABORADOR ()  ---
			
			
			if(bupdatetalcompletos)
			{
			
			//cadenatalonarios = cadenatalonarios.substring(0, cadenatalonarios.length()-1); 
			// this.cadenaboletos = this.cadenaboletos.substring(0, this.cadenaboletos.length()-1); 
			 
			sql = " UPDATE SORTEOS_FORMATOS_COLABORADOR SET"
					+ "  TALONARIOS= '" + cadenatalonarios + "', "
					+ "  MONTO= '" + montototal + "', "
					+ "  NUMBOLETOS= '" + numboletos + "', "	
					+ "  BOLETOS= '" + this.cadenaboletos + "'"
					+ " WHERE PK_SORTEO=" + modelo.getIdSorteo()
					+ " AND PK_SECTOR=" + modelo.getIdSector()
					+ " AND PK1=" + modelo.getRegIdFormato();			
			
			
			//System.out.println(">>>>>ACTUALIZAR REGISTRO DEL COLABORADOR" + sql);

			db.execQuery(sql);	
			
			
			}	
		
		
	}
	

	public void AsignarTalonariosBoletosCompletos(mAsignacionTalonariosColaboradores obj,String talonariofolio, SesionDatos sesion, int numboletos, double monto,  String talonario_id) {
		String sql = "";
		db.con();
		String sqlINSERT = "";
		ArrayList<Integer> log_list_boletos = new ArrayList<Integer>();
		//System.out.println("ASIGNACION BOLETOS COMPLETOS");
		
		
		sql="SELECT  B.*  FROM SORTEOS_NICHOS_BOLETOS SB, BOLETOS B "
				+ "WHERE SB.PK_BOLETO = B.PK1"
				+ " AND SB.PK_SORTEO = '"+obj.getIdSorteo()+"'"
				+ " AND SB.PK_SECTOR = '"+obj.getIdSector()+"'"			
				+ " AND SB.PK_NICHO = '"+obj.getIdNicho()+"' "
				+ "AND SB.PK_TALONARIO ='"+talonariofolio+"' ";	
		
		ResultSet boletos = db.getDatos(sql);
		////System.out.println("GUARDA BOLETOS:     " + sql);
		
		try {
			if (boletos != null) {

				while (boletos.next()) {

					sql = "SELECT * FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_BOLETO = '"
							+ boletos.getString("PK1")
							+ "' AND  PK_SORTEO ='"
							+ obj.getIdSorteo()
							+ "' AND PK_SECTOR = '"
							+ obj.getIdSector() + "' "
							+ " AND PK_NICHO = " +obj.getIdNicho()
				           // + " AND PK_COLABORADOR = " +obj.getIdColaborador()					
				            + " AND PK_TALONARIO = " + talonariofolio;
			 
														 // + talonario_id + "' checar
					//System.out.println("BUSCA SORTEOS_COLABORADORES_BOLETOS:     "+sql);		
					ResultSet rs = db.getDatos(sql);

					if (!rs.next()) {
						
						
						this.cadenaboletos += boletos.getString("FOLIO")+",";
						
						sqlINSERT = "INSERT INTO SORTEOS_COLABORADORES_BOLETOS (PK_BOLETO,PK_TALONARIO,PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,FORMATO,USUARIO,ASIGNADO) VALUES ('"
								+ boletos.getString("PK1") + "','"
								+ boletos.getString("TALONARIO") + "','"
								+ boletos.getString("SORTEO") + "','"
								+ obj.getIdSector() + "','"
								+ obj.getIdNicho() + "','"
								+ obj.getIdColaborador() + "','"
								+ pre_formato + obj.getFormato() + "','"
								+ sesion.nickName + "',0)";
						db.execQuery(sqlINSERT);
						////System.out.println("GUARDA BOLETOS:     " + sqlINSERT);
						log_list_boletos.add(boletos.getInt("PK1"));
						
						
						 sql = "UPDATE [SORTEOS_NICHOS_BOLETOS]"
		                	        + " SET ASIGNADO=1"
		                	        + " WHERE PK_SORTEO=" + obj.getIdSorteo() 
		                	        + " AND PK_SECTOR = '"+ obj.getIdSector() + "' "
							        + " AND PK_NICHO = " +obj.getIdNicho()
		                	        + " AND PK_TALONARIO=" + talonariofolio;
		                	      db.execQuery(sql);
						
  						// --- Se guarda un registro de seguimiento ---
  						try {
							Seguimiento.guardaAsignacion(db
									, ASIGNACION.COLABORADOR
									, obj.getIdSorteo(), obj.getIdSector(), obj.getIdNicho(), obj.getIdColaborador()
									, ASIGNACION.BOLETO
									, boletos.getInt("PK_TALONARIO"), boletos.getInt("PK1")
									, 'N'
									, boletos.getInt("COSTO"), 0.0
									, 1
									, pre_formato + obj.getFormato()
									, "col-bols-compls");
  						} catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					}

				}
				
				
				 sql = "UPDATE [SORTEOS_COLABORADORES_TALONARIOS] "
		 		+ "SET DISPONIBLES ='"+numboletos+"', "
		 		+ "NUMBOLETOS='" +numboletos+"', "
		 		+" MONTO="+monto
		 		+" WHERE PK_SORTEO=" + obj.getIdSorteo()
        	    + " AND PK_TALONARIO=" + talonario_id +" "
        	    + " AND PK_SECTOR = "+obj.getIdSector()
        	    +" AND PK_NICHO ="+obj.getIdNicho()
        	    +" AND PK_COLABORADOR ="+obj.getIdColaborador();				
		
		       System.out.println(">>>>>UPDATE TALONARIO REST"+sql);
		       db.execQuery(sql);
		       
		       //
		       sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES=0,ASIGNADO=1"
		                + " WHERE PK_SORTEO=" + obj.getIdSorteo()
		                + " AND PK_SECTOR = " + obj.getIdSector()
	                    + " AND PK_NICHO = " +obj.getIdNicho()
		                + " AND PK_TALONARIO=" + talonario_id;
			//System.out.println(">>>>>UPDATE SORTEOS_NICHOS_TALONARIOS REST"+sql);
		    db.execQuery(sql);					
				
				
				

			}
			
			// --- Se guarda un registro de seguimiento ---
			try {
				this.Log(sesion, LOG.REGISTRO, this, "AsignarTalonariosBoletosCompletos", sesion.toShortString() + ", bols=" + log_list_boletos.toString());
			}catch(Exception ex) { Factory.Error(ex, "Log"); }

		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }

	}
	
	
	
	
	public String validaFisico_Digital_Colaborador(int tipotalonario){		
		db.con();		
		
		String valida="TRUE";
		String sql="SELECT TOP 1 DIGITAL FROM VCOLABORADORES_TALONARIOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				+ " AND PK_COLABORADOR=" + getIdColaborador(); 		
		
		System.out.println(">>>total DIGITAL: "+sql);
		ResultSet taldigital = db.getDatos(sql);
		
		try {
			if(taldigital.next()) {				
				
				if( taldigital.getInt("DIGITAL") == 1  ){//colaborador tiene tal digitales						
					if( taldigital.getInt("DIGITAL") == tipotalonario  )
						valida = "TRUE";
					else
						valida = "DIGITAL";						
				}else//colaborador tiene tal fisicos
					if( taldigital.getInt("DIGITAL") == tipotalonario  )
						valida = "TRUE";
					else
						valida = "FISICO";		         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }		
		
		return valida;
		
		
	}
	
	
	
	
	public String mostrarTalonariosColaboradores(int tipotalonario){		
		db.con();		
		
		String valida="TRUE";
		
		int numtal_disponibles = 0;
		int num_colab = 0;
		
			
		String sql = "SELECT COUNT(*) AS NUMTALONARIOS_DISPONIBLES"
				+ " FROM VNICHOS_TALONARIOS "
				+ " WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"		
				+ " AND PK_NICHO = '"+this.getIdNicho()+"'"
				+ " AND DISPONIBLES = 11"
				+ "AND DIGITAL = "+ tipotalonario;
		
		System.out.println(">>> NUMTALONARIOS_DISPONIBLES: "+sql);
		ResultSet numtal = db.getDatos(sql);		
		

		String sql2 = "	SELECT COUNT(C.PK1) AS NUM_COLABORADORES"
				+ "	FROM COLABORADORES_ASIGNACION SA, COLABORADORES C"
				+ " WHERE SA.PK_COLABORADOR = C.PK1"
			//	+ " AND SA.PK_SORTEO=" + idsorteoi
				+ " AND SA.PK_SECTOR=" + this.getIdSector()
				+ " AND SA.PK_NICHO=" + this.getIdNicho();		
		
		
		System.out.println(">>>NUM COLAB: "+sql2);
		ResultSet numcolaboradores = db.getDatos(sql2);			
		
		
		try {
			if(numtal.next()) {			
				numtal_disponibles =  numtal.getInt("NUMTALONARIOS_DISPONIBLES");       
			}			
			
			if(numcolaboradores.next()) {			
				num_colab =  numcolaboradores.getInt("NUM_COLABORADORES");       
			}			
			
			
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }		
		
		return numtal_disponibles+"#%#"+num_colab;
		
		
	}
	
	
	
	public void guardarAsignacionColaboradoresBoletos_SP(String Boletos, mAsignacionTalonariosColaboradores obj, SesionDatos sesion){		
		db.con();	
		
		System.out.println("BOLETOS COLABORADORES");		
		
		// TODO - insertar
		  List<Parametros> list = new ArrayList<Parametros>();
		  list.add(new Parametros("COUNT", 0));
		  list.add(new Parametros("PK_SECTOR", obj.getIdSector()));
		  list.add(new Parametros("PK_NICHO", obj.getIdNicho()));
		  list.add(new Parametros("PK_COLABORADOR", obj.getIdColaborador()));		  
		  list.add(new Parametros("FORMATO", obj.getFormato()));
		  list.add(new Parametros("USUARIO", sesion.nickName));	
		  list.add(new Parametros("LIST", Boletos));			
		
		  int result = db.execStoreProcedureIntId("sp_guardarAsignacionColaboradoresBoletos", list);
		  System.out.println(" result BOL:"+result);
		  
		  
		  /*try {
				this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", bols=" + Boletos.toString());
				//this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionSectorBoletos", sesion.toShortString() + ", tals=" + log_list_talonarios.toString());
			}catch(Exception ex) { Factory.Error(ex, "Log"); }	*/
			
		
		
	}	
	
	

	public void guardarAsignacionBoletos(String[] arrBoletos, mAsignacionTalonariosColaboradores obj, SesionDatos sesion)
	{
		db.con();
		//System.out.println( " BOLETOSi: ");
		ArrayList<Integer> log_list_bols = new ArrayList<Integer>();
		
		String[] arrBoletoscadena;
		int boletos_id = 0;
		String talonarios_folio = "";

		//	for (String boletos_id: arrBoletos)
		for (int i=0;i<arrBoletos.length;i++) {
			
			arrBoletoscadena = arrBoletos[i].split("#%#");			
			boletos_id = Integer.valueOf(arrBoletoscadena[0]);
			//System.out.println( "id boleto: "+i+": " +boletos_id);
			talonarios_folio = arrBoletoscadena[1];
			//System.out.println( "folio tal: "+i+": " +talonarios_folio);
			
			String sql = "SELECT PK1 FROM SORTEOS_COLABORADORES_BOLETOS WHERE  PK_COLABORADOR = '"+ obj.getIdColaborador()+"' AND PK_NICHO = '" + obj.getIdNicho() + "' AND PK_SORTEO ='" + obj.getIdSorteo() + "' AND PK_SECTOR = '" + obj.getIdSector() + "' AND PK_BOLETO = '" + boletos_id + "'";
			//System.out.println("BUSCA:     "+sql);
			ResultSet rs = db.getDatos(sql);

			try
			{
				if (!rs.next())
				{
					//System.out.println("INSERT:     "+sql);
					String sql2 =
						"INSERT INTO SORTEOS_COLABORADORES_BOLETOS"+
						" (PK_SORTEO,PK_SECTOR,PK_BOLETO,ASIGNADO,PK_TALONARIO,PK_NICHO,PK_COLABORADOR,FORMATO,USUARIO)"+
						" VALUES ('"
						+ obj.getIdSorteo() + "','"
						+ obj.getIdSector() + "','"
						+ boletos_id + "',0,'"
						+ talonarios_folio + "','"
						+ obj.getIdNicho()+"','"
						+ obj.getIdColaborador()+"','"
						+ pre_formatoB + obj.getFormato() + "','"
						+ sesion.nickName + "')";
					 //System.out.println(sql2);
					db.execQuery(sql2);
					
					log_list_bols.add(boletos_id);
					
					
				   guardarTalonario(talonarios_folio,obj,boletos_id, sesion);
				   
				   sql = "UPDATE [SORTEOS_NICHOS_BOLETOS]"
					       + " SET ASIGNADO=1"
					       + " WHERE PK_SORTEO=" + obj.getIdSorteo()
					       + " AND PK_TALONARIO="+ talonarios_folio
					       + " AND PK_BOLETO=" + boletos_id
					       + " AND PK_SECTOR = '"+ obj.getIdSector() + "' ";
					  //     + " AND PK_NICHO = " +obj.getIdNicho();//checar
					     db.execQuery(sql);
					     
					  
					// --- Se guarda un registro de seguimiento ---
					try {
						int idTalonario = super.getIdTalonario(obj.getIdSorteo(), talonarios_folio);
						double boletoCosto = super.getCostoBoleto(boletos_id);

						Seguimiento.guardaAsignacion(db
								, ASIGNACION.COLABORADOR
								, obj.getIdSorteo(), obj.getIdSector(), obj.getIdNicho(), obj.getIdColaborador()
								, ASIGNACION.BOLETO
								, boletos_id, idTalonario 
								, 'N'
								, boletoCosto, 0.0
								, 1
								, pre_formatoB + obj.getFormato()
								, "col-bols");
					}
					catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				}
			}
			catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		}
       
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarAsignacionBoletos", sesion.toShortString() + ", bols=" + log_list_bols.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }

	}
	
	
	
	
	
	public void guardarTalonario(String talonarios_folio, mAsignacionTalonariosColaboradores obj,int boletos_id, SesionDatos sesion)
	{
		db.con();
		//System.out.println( " TAONARIOSbol ");
		ArrayList<Integer> log_list_tals = new ArrayList<Integer>();
		
		String sql = "";
		ResultSet rs = null;
		int idtalonario = 0;
		
		
		double monto = 0;
		int conteoboleos = 0;
		double costoboleto = obtenerCostoBoleto(boletos_id, obj.getIdSorteo());		
		
		
		
			try
			{
			
				idtalonario = getidTalonario(talonarios_folio,obj.getIdSorteo());
				//System.out.println("idtalonario:     "+idtalonario);
				
				sql = "SELECT * FROM SORTEOS_COLABORADORES_TALONARIOS WHERE  PK_COLABORADOR = '"+ obj.getIdColaborador()+"' AND PK_SORTEO ='" + obj.getIdSorteo() + "'  AND PK_NICHO = '" + obj.getIdNicho() + "'   AND PK_SECTOR = '" + obj.getIdSector() + "' AND PK_TALONARIO = '" + idtalonario + "'";
				//System.out.println("BUSCA:     "+sql);
				rs = db.getDatos(sql);	
				
				sql= "SELECT T.PK1,T.FOLIO,ST.NUMBOLETOS,ST.MONTO,ST.DISPONIBLES FROM TALONARIOS T, SORTEOS_NICHOS_TALONARIOS ST WHERE  ST.PK_TALONARIO = T.PK1 AND PK_NICHO = '" + obj.getIdNicho()+ "' AND T.PK1 = '"+idtalonario+"'";
				//System.out.println("BUSCA TALONARIOS y talon col:     "+sql);
				ResultSet talonario = db.getDatos(sql);		
				
				
				if (!rs.next())
				{					
					
					/*sql = "SELECT * FROM TALONARIOS WHERE PK1 = '" +  idtalonario + "'";//hacer match sorteos tal
					//System.out.println("BUSCA TALONARIOS:     "+sql);
					ResultSet talonario = db.getDatos(sql);*/					
					
					      if (talonario.next())
					      {	
					    	  
					    	//CALCULA EL MONTO Y NUMERO DE BOLETOS SI NO EXISTE EL TALONARIO
					    	  conteoboleos +=1; 
					    	  monto = costoboleto;
					    	  
					    	
										
					       // //System.out.println("INSERT:     "+sql);
					        sql =
						    	"INSERT INTO SORTEOS_COLABORADORES_TALONARIOS"+
						     	" (PK_SORTEO,PK_SECTOR,PK_TALONARIO,PK_NICHO,PK_COLABORADOR,ASIGNADO,NUMBOLETOS,MONTO,FORMATO,USUARIO,DISPONIBLES)"+
						    	" VALUES ('"
						     	+ obj.getIdSorteo() + "','"
						    	+ obj.getIdSector() + "','"
						    	+ talonario.getInt("PK1") + "','"
						    	+ obj.getIdNicho() + "' ,'"
						    	+ obj.getIdColaborador() + "',0,'"
						    	+ conteoboleos + "','"
						    	+ monto + "','"
						    	+ pre_formatoB + obj.getFormato() + "','"
						    	+ sesion.nickName + "','"
						    	+ conteoboleos+"')";
					        
					        //System.out.println(">>>>INSERT TALONARIOS BOL"+sql);
					        db.execQuery(sql);
					        
					        log_list_tals.add(talonario.getInt("PK1"));
					        
					        
					        					        
					        int disponibles = talonario.getInt("DISPONIBLES") - conteoboleos;
					    	  //System.out.println(">>>>>>DIFERENCIA BOL:     " + disponibles);
					        
					        sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES='"+disponibles+"' ,ASIGNADO=1"
					        	       + " WHERE PK_SORTEO=" + obj.getIdSorteo()
					        	       + " AND PK_SECTOR=" + obj.getIdSector()
					        	       + " AND PK_NICHO=" + obj.getIdNicho()
					        	       + " AND PK_TALONARIO=" + talonario.getInt("PK1");

		        	     db.execQuery(sql);		        	     
		        
							// --- Se guarda un registro de seguimiento ---
							try{
								int idTalonario = super.getIdTalonario(obj.getIdSorteo(), talonarios_folio);
								
								Seguimiento.guardaAsignacion(db
										, ASIGNACION.COLABORADOR
										, obj.getIdSorteo(), obj.getIdSector(), obj.getIdNicho(), obj.getIdColaborador()
										, ASIGNACION.TALONARIO
										, idTalonario, talonario.getInt("PK1") 
										, 'N'
										, monto, 0.0
										, conteoboleos
										, pre_formatoB + obj.getFormato()
										, "col-tals");
							}catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					 } 
					      
					      
					
				}else{
					
					//CALCULA EL MONTO Y NUMERO DE BOLETOS SI EXISTE EL TALONARIO
					monto = rs.getInt("MONTO")+costoboleto;
					conteoboleos = rs.getInt("NUMBOLETOS")+1;
					
					talonario.next();
					
				//	int disponibles = talonario.getInt("NUMBOLETOS") - conteoboleos;
				////System.out.println("NUM TAL: "+talonario.getInt("NUMBOLETOS")+"- NUM ASIG"+rs.getInt("NUMBOLETOS")+"= "+disponibles);
					
					
					 sql = "UPDATE [SORTEOS_COLABORADORES_TALONARIOS] SET DISPONIBLES="+conteoboleos+", NUMBOLETOS="+conteoboleos+", MONTO="+monto+" WHERE PK_SORTEO=" + obj.getIdSorteo()
			        	       + " AND PK_TALONARIO=" + idtalonario+" AND PK_SECTOR = "+obj.getIdSector()+" AND PK_NICHO ="+obj.getIdNicho() +" AND PK_COLABORADOR ="+obj.getIdColaborador();
					 
					 //System.out.println("UDATE NUMBOL Y MONTO"+sql);

			          db.execQuery(sql);     
			          
			          
			          
			          
			           //TABLA SORTEOS_TALONARIOS
			               int disponibles = talonario.getInt("DISPONIBLES") - 1;					
			          
			               //System.out.println(">>>>>>DIFERENCIA BOL:     " + disponibles);
					        
					        sql = "UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES='"+disponibles+"' ,ASIGNADO=1"
					        	       + " WHERE PK_SORTEO=" + obj.getIdSorteo()
					        	       + " AND PK_SECTOR=" + obj.getIdSector()
					        	       + " AND PK_NICHO=" + obj.getIdNicho()
					        	       + " AND PK_TALONARIO=" + talonario.getInt("PK1");

					        	     db.execQuery(sql);		        	     
					        
					
					
					
					
				}			
				
			}
			catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "guardarTalonario", sesion.toShortString() + ", bols=" + log_list_tals.toString());
		}catch(Exception ex) { Factory.Error(ex, "Log"); }

	}
	
	public double obtenerCostoBoletoTal(String idtalonario, int sorteo){
		
		double id = 0;
		String sql = "SELECT TOP 1 COSTO FROM BOLETOS WHERE TALONARIO ='"+idtalonario+"' AND SORTEO='"+sorteo+"' ";	
		//System.out.println("obtener costoboleto "+sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				id = rs.getInt("COSTO");	
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		return id;
	}
	
	
	public double obtenerCostoBoleto(int idboleto, int sorteo){
		
		double id = 0;
		String sql = "SELECT COSTO FROM BOLETOS WHERE PK1 ='"+idboleto+"' AND  SORTEO='"+sorteo+"' ";	
		//System.out.println("obtener costoboleto "+sql);
		ResultSet rs = db.getDatos(sql);
		
		try {
			if(rs.next()) {				
					
				id = rs.getInt("COSTO");	
	         
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
	
	
	
	public void eliminaBoleto(int folioTalonario, int pkBoleto, SesionDatos sesion) {
		String sql =
				"SELECT B.COSTO AS 'COSTO_BOLETO' FROM [SORTEOS_COLABORADORES_BOLETOS] SCB, [BOLETOS] B"
				+ " WHERE SCB.PK_BOLETO=B.PK1"
				+ " AND B.PK1=" + pkBoleto
				+ " AND PK_TALONARIO=" + folioTalonario
				+ " AND PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ " AND PK_NICHO=" + getIdNicho()
				+ " AND PK_COLABORADOR=" + getIdColaborador()
				;
		double costoBoleto = Double.NaN; // Valor por default si hay error.
		try {
			ResultSet res = db.getDatos(sql);
			if (res.next()) {
				costoBoleto = res.getDouble("COSTO_BOLETO");
				//res.close();
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		// Si no hay error en el costo del boleto
		if (Double.isNaN(costoBoleto) == false) {
			sql =
					" DELETE [SORTEOS_COLABORADORES_BOLETOS]"
					+ " WHERE (PK_BOLETO=" + pkBoleto
					+ " AND PK_TALONARIO=" + getIdtalonario()
					+ " AND PK_SORTEO=" + getIdSorteo()
					+ " AND PK_SECTOR=" + getIdSector()
					+ " AND PK_NICHO=" + getIdNicho()
					+ " AND PK_COLABORADOR=" + getIdColaborador()
					+ ")\n"
				
					;
			db.execQuery(sql);

			// --- Se guarda un registro de seguimiento ---
			try {
				this.Log(sesion, LOG.BORRADO, this, "retornotalonariosfc5", sesion.toShortString() + ", bol=" + pkBoleto);
			}catch(Exception ex) { Factory.Error(ex, "Log"); }
		}
	}
	
	
	

/* ********************TERMINA RETORNO Y DEVOLVER********************************* */
	
	//EMPIEZA RETORNO Y DEVOLVER
	
	//obtiene el num boletos del TALONARIOS
	public int getNumeroBoletos(){		
		
		String sql="SELECT NUMBOLETOS  FROM VCOLABORADORES_TALONARIOS "				
				+ " WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				 + " AND PK_COLABORADOR=" + getIdColaborador()		 
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
		
				
		String sql="SELECT  COUNT(PK_BOLETO) AS 'BVENDIDOSR' FROM VCOLABORADORES_BOLETOS "
				+ " WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				 + " AND PK_COLABORADOR=" + getIdColaborador()		 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V' AND RETORNADO=1 ";	
		
		
		
		System.out.println(">>>SELECT BOLETOS  total Vendidos y retornados: "+sql);
		ResultSet nbvendidos = db.getDatos(sql);		
	
		
		try {
			if(nbvendidos.next()) {					
				
				if( nbvendidos.getInt("BVENDIDOSR") == numeroboletos && numeroboletos != 0 ){						
									
					totaltretornados = true;				
				
				}
				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totaltretornados;
	}	
	
	
	public boolean getTalonariosCompletamenteVendidos(){		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totaltretornados = false;
		
		
		String sql="SELECT  COUNT(PK_BOLETO) AS 'BVENDIDOS' FROM VCOLABORADORES_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				 + " AND PK_COLABORADOR=" + getIdColaborador()		 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V'";	
		
		
		
		System.out.println(">>>SELECT BOLETOS  total Vendidos: "+sql);
		ResultSet nbvendidos = db.getDatos(sql);		
	
		
		try {
			if(nbvendidos.next()) {					
				
				if( nbvendidos.getInt("BVENDIDOS") == numeroboletos  && numeroboletos != 0){						
									
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
	
		
		String sqlS="SELECT  FOLIO, PK_BOLETO,COSTO, PK_ESTADO FROM VCOLABORADORES_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				 + " AND PK_COLABORADOR=" + getIdColaborador()		 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'V' ";
		
		 rs = db.getDatos(sqlS);	
		 
		
	try {
	 	
		while( rs.next() ){		
			        
			         
					sql =   "UPDATE BOLETOS SET "
						    + "RETORNADO = 1"				
							+ " WHERE PK1 = "+rs.getInt("PK_BOLETO")+" "
							+ "AND PK_TALONARIO = '"+this.getIdtalonario()+"'";
						
						
						System.out.println(">>>UPDATE BOLETOS : "+sql);
						db.execQuery(sql);						
						
						
						sql = 	//FORMATOS COLABORADOR								
								"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR,PK_NICHO, PK_COLABORADOR, PK_ESTADO,USUARIO,FECHA_R)"
								+ " VALUES ('"									
                                + "FC4 - "  + this.getFormato() + "','"	
                                + rs.getInt("PK_BOLETO") + "','"	
								+ this.getIdSector() + "','"
								+ this.getIdNicho() + "','"
								+ this.getIdColaborador() + "','"
								+ rs.getString("PK_ESTADO") + "','"	
								+ sesion.nickName + "',"	
								+ "GETDATE())";							

						System.out.println(">>>>>REGISTRO DE FORMATO COLABORADOR fc4" + sql);						
						db.execQuery(sql);	
						
						
						sql =//SEGUIMIENTO COLABORADORES									
								"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_NICHO, PK_COLABORADOR, PK_ESTADO, USUARIO,PK_TALONARIO)"
								+ " VALUES ("
								+ "'Colaborador',"
								+ "'Retorno',"
								+ "'ret-col-bols',"
								+ "'"									
                                + "FC4 - "  + this.getFormato() + "','"	
                                + rs.getInt("PK_BOLETO") + "','"	
								+ this.getIdSector() + "','"
								+ this.getIdNicho() + "','"
								+ this.getIdColaborador() + "','"
								+ rs.getString("PK_ESTADO") + "','"	
								+ sesion.nickName + "','"
								+ this.getIdtalonario() + "')";							

						System.out.println(">>>>>SEGUIMIENTO COLABORADOR fc4" + sql);						
						db.execQuery(sql);	
						
						cadena = sesion.toShortString() + ", bol=" + rs.getInt("PK_BOLETO");					
						
						 sql =//LOG COLABORADORES									
									"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO,FECHA_R)"
									+ " VALUES ("
									+ "'mAsignacionTalonariosColaboradores',"
									+ "'retornotalonariosfc4',"
									+ "'Retorno','"                               
	                                + cadena + "','"									
	                            	+ sesion.nickName+ "',"
									+ "GETDATE())";							

							System.out.println(">>>>>LOG COLABORADOR fc4" + sql);						
							db.execQuery(sql);					
						
						
						
						
		}			
						
						
		
		
	} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }	
	 
		
  }
	
	
	public boolean getTalonariosCompletamenteNoVendidos(){		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totalnovendidos = false;	
		
		
		
		String sql="SELECT  COUNT(PK_BOLETO) AS 'BNOVENDIDOS' FROM VCOLABORADORES_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				 + " AND PK_COLABORADOR=" + getIdColaborador()		 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N' ";
				//+ " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";//NUEVO";	
		
		
		
		System.out.println(">>>11SELECT BOLETOS  total No Vendidos: "+sql);
		ResultSet nbvendidos = db.getDatos(sql);		

		//System.out.println(">>> numeroboletos: "+numeroboletos);
		
		try {
			if(nbvendidos.next()) {	
				//System.out.println(">>> numeroboletosbd: "+nbvendidos.getInt("BNOVENDIDOS"));
				
				if( nbvendidos.getInt("BNOVENDIDOS") == numeroboletos && numeroboletos != 0 ){						
									
					totalnovendidos = true;	
				
				
				}
				
	         
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
		
		
		return totalnovendidos;
	}
	
	
	
public boolean getTalonariosDigitalesCompletamenteVendidos_sc(){		
		
		int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
		boolean totalnovendidos = false;	
		
		
		
		String sql="SELECT  COUNT(PK_BOLETO) AS 'BVENDIDOSDIG_sc' FROM VCOLABORADORES_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				 + " AND PK_COLABORADOR=" + getIdColaborador()		 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'P'";
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


public boolean ExisteBoletosColaboradoresNV(){
	
	String sql="SELECT  COUNT(PK_BOLETO) AS 'MAX' FROM VCOLABORADORES_BOLETOS "
			+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND PK_NICHO = '"+this.getIdNicho()+"' "	
			 + " AND PK_COLABORADOR=" + getIdColaborador()
			+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N'  ";
           // + " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";
	
	System.out.println(" MAX COLABORADORES boletos no vendidos Colaboradores: " + sql);
	ResultSet rs = db.getDatos(sql);
	
	try {
		if (rs.next())
			return rs.getInt("MAX") > 0;

	} catch (SQLException e) { Factory.Error(e, sql); }
	return false;
}
	 	
	


public void devolvertalonariosfc5(int folioTalonario, SesionDatos sesion) {
	
	String sql = "";
	String cadena = "";
		
	 sql="SELECT    FOLIO, PK_BOLETO, COSTO, PK_ESTADO FROM VCOLABORADORES_BOLETOS "
				+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
				+ " AND PK_COLABORADOR=" + getIdColaborador()		 
				+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N' ";
				//+ " AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";//*nuevo checar	
	 
	       System.out.println("BOL COLAB : "+sql);
	       ResultSet colaborador = db.getDatos(sql);		
		
		try {
			if (colaborador.next()) {				
					
					do{						
						
						sql =	 "DELETE [COLABORADORES_BOLETOS]"
								+ " WHERE (PK_BOLETO =" + colaborador.getInt("PK_BOLETO")				
								+ " AND PK_SECTOR=" + getIdSector()
								+ " AND PK_NICHO=" + getIdNicho()
								+ " AND PK_COLABORADOR=" + getIdColaborador()
								+ ")\n";
			
			              System.out.println(" DELETE: " + sql);
			              db.execQuery(sql);        
			              					
							
							sql = 	//FORMATOS COLABORADOR
									
									"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR,PK_NICHO, PK_COLABORADOR, PK_ESTADO,USUARIO,FECHA_R)"
									+ " VALUES ('"									
	                                + "FC5 - "  + this.getFormato() + "','"	
	                                + colaborador.getInt("PK_BOLETO") + "','"	
									+ this.getIdSector() + "','"
									+ this.getIdNicho() + "','"
									+ this.getIdColaborador() + "','"
									+ colaborador.getString("PK_ESTADO") + "','"	
									+ sesion.nickName + "',"	
									+ "GETDATE())";							

							System.out.println(">>>>>REGISTRO DE FORMATO COLABORADOR fc5" + sql);						
							db.execQuery(sql);								
							
							
                           sql =//SEGUIMIENTO COLABORADORES									
									"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_NICHO, PK_COLABORADOR, PK_ESTADO, USUARIO,PK_TALONARIO)"
									+ " VALUES ("
									+ "'Colaborador',"
									+ "'Devolución',"
									+ "'devol-col-bols',"
									+ "'"									
	                                + "FC5 - "  + this.getFormato() + "','"	
	                                + colaborador.getInt("PK_BOLETO") + "','"	
									+ this.getIdSector() + "','"
									+ this.getIdNicho() + "','"
									+ this.getIdColaborador() + "','"
									+ colaborador.getString("PK_ESTADO") + "','"	
									+ sesion.nickName + "','"
									+ this.getIdtalonario() + "')";		
                           

							System.out.println(">>>>>SEGUIMIENTO COLABORADOR fc5" + sql);						
							db.execQuery(sql);	
							
							cadena = sesion.toShortString() + ", bol=" + colaborador.getInt("PK_BOLETO");
						//	cadena = "{sec=" + CAST(@PK_SECTOR AS nvarchar(20)) +",nich=" + CAST(@PK_NICHO AS nvarchar(20)) + ",col=" + CAST(@PK_COLABORADOR AS nvarchar(20)) + "},bol={" + CAST(@id_boleto AS nvarchar(20)) + "}";
							
							 sql =//LOG COLABORADORES									
										"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO,FECHA_R)"
										+ " VALUES ("
										+ "'mAsignacionTalonariosColaboradores',"
										+ "'devolvertalonariosfc5',"
										+ "'Borrado','"                               
		                                + cadena + "','"									
		                            	+ sesion.nickName+ "',"
										+ "GETDATE())";							

								System.out.println(">>>>>LOG COLABORADOR fc5" + sql);						
								db.execQuery(sql);				
							
												
						
					}while(colaborador.next());			
				
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }		
		
		
			
	}





	
	//FC5
/*public void devolvertalonariosfc5(int folioTalonario, SesionDatos sesion) {
	
	String sql = "";
	String stringboletos = "";
	double monto = 0;
	double costo = 0;
	
		
	 sql="SELECT    B.FOLIO, SB.PK_BOLETO ,B.COSTO FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
				+ "WHERE SB.PK_BOLETO = B.PK1"				
				+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"'"
				+ " AND SB.PK_SECTOR = '"+this.getIdSector()+"'"			
				+ " AND SB.PK_NICHO = '"+this.getIdNicho()+"' "
				+ " AND SB.PK_COLABORADOR=" + getIdColaborador()		 
				+ " AND SB.PK_TALONARIO ='"+this.getIdtalonario()+"' AND B.VENDIDO = 'N' "
				+ " AND (B.FOLIODIGITAL IS NULL OR B.FOLIODIGITAL = '')";//*nuevo checar	
	 
	       System.out.println("BOL COLAB : "+sql);
	       ResultSet colaborador = db.getDatos(sql);
		
		
		  String sqlS = "SELECT DISPONIBLES FROM SORTEOS_NICHOS_TALONARIOS "
				+ " WHERE PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
		        + " AND PK_SECTOR=" + getIdSector()
		        + " AND PK_NICHO=" + getIdNicho();
		

		 //System.out.println("SORTEOS_TAL"+sqlS);
		 ResultSet rs = db.getDatos(sqlS);		
		
		
		try {
			if (rs.next()&&colaborador.next()) {
				
				int disponibles = rs.getInt("DISPONIBLES");				
				costo = colaborador.getInt("COSTO");
				
				
				sql =	
						
					     " DELETE [SORTEOS_COLABORADORES_TALONARIOS]"
				         + " WHERE (PK_TALONARIO=" + getIdtalonario()
				         + " AND PK_SORTEO=" + getIdSorteo()
				         + " AND PK_SECTOR=" + getIdSector()
				         + " AND PK_NICHO=" + getIdNicho()
			             + " AND PK_COLABORADOR=" + getIdColaborador()
				         + ")\n"
				
						
						 +"DELETE [SORTEOS_COLABORADORES_BOLETOS]"
									+ " WHERE (PK_TALONARIO=" + folioTalonario									
									+ " AND PK_SORTEO=" + getIdSorteo()
									+ " AND PK_SECTOR=" + getIdSector()
									+ " AND PK_NICHO=" + getIdNicho()
									+ " AND PK_COLABORADOR=" + getIdColaborador()
									+ ")\n";
				
				System.out.println(" DELETE: " + sql);
				db.execQuery(sql);			
				
			
					int boletosnv = 0;
					
					do{
						boletosnv++;	
						
						stringboletos += colaborador.getString("FOLIO")+",";		
						
						 sql =	   " UPDATE [SORTEOS_NICHOS_BOLETOS] SET ASIGNADO=0"
									+ " WHERE (PK_TALONARIO=" + folioTalonario
									+ " AND PK_BOLETO=" + colaborador.getInt("PK_BOLETO")
									+ " AND PK_SORTEO=" + getIdSorteo()
									+ " AND PK_SECTOR=" + getIdSector()
									+ " AND PK_NICHO=" + getIdNicho()
									+ ")\n"
									;
							
							db.execQuery(sql);							
												
						
					}while(colaborador.next());				    
					
					stringboletos = stringboletos.substring(0, stringboletos.length()-1); 
				
					monto = costo * boletosnv;					
					disponibles += boletosnv;	
					
				
				//checar ASIGNADO=0 o 1
				sql = 	" UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES = '"+ disponibles +"'"
						+ " WHERE (PK_TALONARIO=" + getIdtalonario()
						+ " AND PK_SORTEO=" + getIdSorteo()	
						+ " AND PK_SECTOR=" + getIdSector()
						+ " AND PK_NICHO=" + getIdNicho()
						+ ")\n"
						
						
						//FORMATOS COLABORADOR
						
						+"INSERT INTO SORTEOS_FORMATOS_COLABORADOR"
						+ " (PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
						+ " VALUES ('"
						+ this.getIdSorteo() + "','"
						+ this.getIdSector() + "','"
						+ this.getIdNicho() + "','"
						+ this.getIdColaborador() + "','"
						+ "FC5 - "  + this.getFormato() + "','"									
						+ folioTalonario + "','"
						+ 1 + "','" 
					    + stringboletos + "','"										
						+ boletosnv + "','"
						+ monto + "','"
						+ sesion.nickName + "')";
				

				System.out.println(">>>>>REGISTRO DE FORMATO COLABORADOR fc5" + sql);						
				db.execQuery(sql); 						    
				 // this.setRegIdFormato(id);				
				
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
				
		
		
		
				// --- Se guarda un registro de seguimiento ---
		
	}*/


public boolean getTalParcialmenteVendidos(){		
	
	int numeroboletos = this.getNumeroBoletos();//obtiene el num boletos del TALONARIOS
	boolean totaltretornados = false;
	
	
	String sql="SELECT  COUNT(PK_BOLETO) AS 'BPARCIALES' FROM VCOLABORADORES_BOLETOS "
			+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
			 + " AND PK_COLABORADOR=" + getIdColaborador()		 
			+ "AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'P'";	
	
	
	
	System.out.println(">>>SELECT BOLETOS  total parcial Vendidos: "+sql);
	ResultSet nbvendidos = db.getDatos(sql);		

	
	try {
		if(nbvendidos.next()) {					
			
			if( nbvendidos.getInt("BPARCIALES") >= 1 || numeroboletos == 0){						
								
				totaltretornados = true;				
			
			}
			
         
		}
	} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }
	
	
	return totaltretornados;
}

	
//FC5B
public void devolvertalonariosfc5B(int folioTalonario, SesionDatos sesion) {
		
	String sql = "";
	String cadena = "";	
	
	 sql="SELECT  FOLIO, PK_BOLETO,COSTO, PK_ESTADO FROM VCOLABORADORES_BOLETOS "
			+ "WHERE PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND PK_NICHO = '"+this.getIdNicho()+"' "
			 + " AND PK_COLABORADOR=" + getIdColaborador()		 
			+ " AND PK_TALONARIO ='"+this.getIdtalonario()+"' AND PK_ESTADO = 'N' ";
			//+ "AND (FOLIODIGITAL IS NULL OR FOLIODIGITAL = '')";//NUEVO		
	
	ResultSet colaborador = db.getDatos(sql);		
		
		try {
			if (colaborador.next()) {				
				
			
				do{	
					sql =	
							"DELETE [COLABORADORES_BOLETOS]"
							+ " WHERE (PK_BOLETO =" + colaborador.getInt("PK_BOLETO")				
							+ " AND PK_SECTOR=" + getIdSector()
							+ " AND PK_NICHO=" + getIdNicho()
							+ " AND PK_COLABORADOR=" + getIdColaborador()
							+ ")\n";
		
		              System.out.println(" DELETE: " + sql);
		              db.execQuery(sql);				
				
						
						
						sql = 	//FORMATOS COLABORADOR
								
								"INSERT INTO FORMATOS(FOLIO,PK_BOLETO,PK_SECTOR,PK_NICHO, PK_COLABORADOR, PK_ESTADO,USUARIO,FECHA_R)"
								+ " VALUES ('"									
                                + "FC5B - "  + this.getFormato() + "','"	
                                + colaborador.getInt("PK_BOLETO") + "','"	
								+ this.getIdSector() + "','"
								+ this.getIdNicho() + "','"
								+ this.getIdColaborador() + "','"
								+ colaborador.getString("PK_ESTADO") + "','"	
								+ sesion.nickName + "',"	
								+ "GETDATE())";							

						System.out.println(">>>>>REGISTRO DE FORMATO COLABORADOR fc5b" + sql);						
						db.execQuery(sql);
						
						
						 sql =//SEGUIMIENTO COLABORADORES									
									"INSERT INTO SEGUIMIENTO(NIVEL, ACCION, DETALLE, FORMATO, PK_BOLETO, PK_SECTOR, PK_NICHO, PK_COLABORADOR, PK_ESTADO, USUARIO,PK_TALONARIO)"
									+ " VALUES ("
									+ "'Colaborador',"
									+ "'Devolución',"
									+ "'devol-col-bols',"
									+ "'"									
	                                + "FC5B - "  + this.getFormato() + "','"	
	                                + colaborador.getInt("PK_BOLETO") + "','"	
									+ this.getIdSector() + "','"
									+ this.getIdNicho() + "','"
									+ this.getIdColaborador() + "','"
									+ colaborador.getString("PK_ESTADO") + "','"	
									+ sesion.nickName + "','"
									+ this.getIdtalonario() + "')";							

							System.out.println(">>>>>SEGUIMIENTO COLABORADOR fc5b" + sql);						
							db.execQuery(sql);	
							
							cadena = sesion.toShortString() + ", bol=" + colaborador.getInt("PK_BOLETO");						
							
							 sql =//LOG COLABORADORES									
										"INSERT INTO LOG(CONTROLLER, FUNCION, ACTIVIDAD, DETALLE, USUARIO, FECHA_R)"
										+ " VALUES ("
										+ "'mAsignacionTalonariosColaboradores',"
										+ "'devolvertalonariosfc5B',"
										+ "'Borrado','"                               
		                                + cadena + "','"									
		                            	+ sesion.nickName+ "',"
										+ "GETDATE())";							

								System.out.println(">>>>>LOG COLABORADOR fc5b" + sql);						
								db.execQuery(sql);							
						
					
					
				}while(colaborador.next());				
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }			
		
	}


//FC5B
/*public void devolvertalonariosfc5B(int folioTalonario, SesionDatos sesion) {
		
	String sql = "";		
	String stringboletos = "";
	double monto = 0;
	double costo = 0;
	
	 sql="SELECT  B.FOLIO, SB.PK_BOLETO,B.COSTO FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
			+ "WHERE SB.PK_BOLETO = B.PK1"				
			+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND SB.PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND SB.PK_NICHO = '"+this.getIdNicho()+"' "
			 + " AND SB.PK_COLABORADOR=" + getIdColaborador()		 
			+ " AND SB.PK_TALONARIO ='"+this.getIdtalonario()+"' AND B.VENDIDO = 'N' "
			+ "AND (B.FOLIODIGITAL IS NULL OR B.FOLIODIGITAL = '')";//NUEVO	
	
	
	ResultSet colaborador = db.getDatos(sql);
	
	
		 sql = "";
		 String sqlS = "";
		
		 sqlS = "SELECT DISPONIBLES FROM SORTEOS_NICHOS_TALONARIOS "
				+ " WHERE PK_TALONARIO=" + getIdtalonario()
				+ " AND PK_SORTEO=" + getIdSorteo()
		        + " AND PK_SECTOR=" + getIdSector()
		        + " AND PK_NICHO=" + getIdNicho();
		

		//System.out.println("SORTEOS_TAL"+sqlS);
		ResultSet rs = db.getDatos(sqlS);
		
		
		
		try {
			if (rs.next()&&colaborador.next()) {
				
				
				int disponibles = rs.getInt("DISPONIBLES");				
				costo = colaborador.getInt("COSTO");		
			
				
				int numboletosnv = 0;
				
				do{
					numboletosnv++;
					stringboletos += colaborador.getString("FOLIO")+",";		
					
					 sql =  	" UPDATE [SORTEOS_NICHOS_BOLETOS] SET ASIGNADO=0"
								+ " WHERE (PK_TALONARIO=" + folioTalonario
								+ " AND PK_BOLETO=" + colaborador.getInt("PK_BOLETO")
								+ " AND PK_SORTEO=" + getIdSorteo()
								+ " AND PK_SECTOR=" + getIdSector()
								+ " AND PK_NICHO=" + getIdNicho()
								+ ")\n"					 
							 
																							
								+ " DELETE [SORTEOS_COLABORADORES_BOLETOS]"
								+ " WHERE (PK_TALONARIO=" + folioTalonario
								+ " AND PK_BOLETO=" + colaborador.getInt("PK_BOLETO")
								+ " AND PK_SORTEO=" + getIdSorteo()
								+ " AND PK_SECTOR=" + getIdSector()
								+ " AND PK_NICHO=" + getIdNicho()
								+ " AND PK_COLABORADOR=" + getIdColaborador()
								+ ")\n";				
						
						db.execQuery(sql);					
					
					
				}while(colaborador.next());
				
				stringboletos = stringboletos.substring(0, stringboletos.length()-1); 
				
				monto = costo * numboletosnv;					
				disponibles += numboletosnv;			
				
				
				sqlS = "SELECT NUMBOLETOS,DISPONIBLES,MONTO FROM SORTEOS_COLABORADORES_TALONARIOS "
						+ " WHERE PK_TALONARIO=" + getIdtalonario()
						+ " AND PK_SORTEO=" + getIdSorteo()
				        + " AND PK_SECTOR=" + getIdSector()
				        + " AND PK_NICHO=" + getIdNicho()
				        + " AND PK_COLABORADOR=" + getIdColaborador();				
				
				System.out.println(">>>>>TALONARIO COLABORADOR fc5b" + sqlS);			
				ResultSet talonariocolab = db.getDatos(sqlS);	
				talonariocolab.next();
				
				int numboletoscolaborador = talonariocolab.getInt("NUMBOLETOS") - numboletosnv;
				int disponiblescolaborador = talonariocolab.getInt("DISPONIBLES") - numboletosnv;
				double montocolaborador = talonariocolab.getInt("MONTO") - (monto);		
				
			
			sql = 	" UPDATE [SORTEOS_NICHOS_TALONARIOS] SET DISPONIBLES = '"+ disponibles +"'"
					+ " WHERE (PK_TALONARIO=" + getIdtalonario()
					+ " AND PK_SORTEO=" + getIdSorteo()	
					+ " AND PK_SECTOR=" + getIdSector()
					+ " AND PK_NICHO=" + getIdNicho()
					+ ")\n"	
					
					+ " UPDATE [SORTEOS_COLABORADORES_TALONARIOS] SET DISPONIBLES = '"+ disponiblescolaborador +"',"
					+ " MONTO = '"+ montocolaborador +"',  NUMBOLETOS = '"+ numboletoscolaborador +"'"
					+ " WHERE (PK_TALONARIO=" + getIdtalonario()
					+ " AND PK_SORTEO=" + getIdSorteo()	
					+ " AND PK_SECTOR=" + getIdSector()
					+ " AND PK_NICHO=" + getIdNicho()
					+ " AND PK_COLABORADOR=" + getIdColaborador()
					+ ")\n"			
					
					
					//FORMATOS COLABORADOR
					
					+"INSERT INTO SORTEOS_FORMATOS_COLABORADOR"
					+ " (PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,FOLIO,TALONARIOS,NUMTALONARIOS,BOLETOS,NUMBOLETOS,MONTO,USUARIO)"
					+ " VALUES ('"
					+ this.getIdSorteo() + "','"
					+ this.getIdSector() + "','"
					+ this.getIdNicho() + "','"
					+ this.getIdColaborador() + "','"
					+ "FC5B - "  + this.getFormato() + "','"									
					+ folioTalonario + "','"
					+ 1 + "','" 
				    + stringboletos + "','"										
					+ numboletosnv + "','"
					+ monto + "','"
					+ sesion.nickName + "')";
				

				  System.out.println(">>>>>REGISTRO DE FORMATO COLABORADOR fc5b" + sql);						
				  db.execQuery(sql); 						    
							
				
				
			}
		} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }			
		
	}*/


	// RETORNAR Y DEVOLVER
	
	

/* ********************TERMINA RETORNO Y DEVOLVER**********************************/
	
	
	/*public String FormatoTalBoletosCompletos(int folioTalonario) {
	
	String sql = "";
	db.con();					
	String stringboletos = "";			
		
	
	sql="SELECT  B.FOLIO FROM SORTEOS_COLABORADORES_BOLETOS SB, BOLETOS B "
			+ "WHERE SB.PK_BOLETO = B.PK1"				
			+ " AND SB.PK_SORTEO = '"+this.getIdSorteo()+"'"
			+ " AND SB.PK_SECTOR = '"+this.getIdSector()+"'"			
			+ " AND SB.PK_NICHO = '"+this.getIdNicho()+"' "
			 + " AND SB.PK_COLABORADOR=" + getIdColaborador()		 
			+ "AND SB.PK_TALONARIO ='"+this.getIdtalonario()+"' ";			
	
	
	ResultSet boletos = db.getDatos(sql);
	System.out.println(" BOLETOS:  fc " + sql);
	
	try {
		if (boletos != null) {

			while (boletos.next()) {
				
				stringboletos += boletos.getString("FOLIO")+",";		
				
			}//FIN WHILE

		}			
		
	} catch (SQLException ex) { com.core.Factory.Error(ex, sql); }	
	
	
	stringboletos = stringboletos.substring(0, stringboletos.length()-1); 
	System.out.println(">>>>>cadena bol fc" + stringboletos);	
	return 	stringboletos;
		
		
}*/


	
	

}//<end class>
