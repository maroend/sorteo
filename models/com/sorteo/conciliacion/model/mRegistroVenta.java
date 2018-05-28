package com.sorteo.conciliacion.model;

import java.sql.ResultSet;

import com.core.Factory;
import com.core.Seguimiento;
import com.core.SesionDatos;

import java.sql.SQLException;
import java.util.ArrayList;

import com.core.SuperModel;
import com.core.Factory.LOG;
import com.core.Seguimiento.ASIGNACION;
import com.core.SuperModel.OFFSET;
import com.sorteo.poblacion.model.mColaboradores;

public class mRegistroVenta extends SuperModel {
	
	private int id;
	
	private String clave;
	private int sector;
	private String rbancaria;
	private String colaborador;
	private int nicho;


	
	
	private int idusuario;
	private int idsorteo;
	
	private char sect;
	private int Comision;
	
	
	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	public int getComision() {
		return Comision;
	}
	
	public void setComision(int comision) {
		Comision = comision;
	}
	
	public void init(){		
	
		 this.id = 0;
		 this.clave = "";
		 this.sector = 0;
		 this.rbancaria = "";		
		 this.nicho = 0;
		
		
		 this.sect = 'I';
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public String getColaborador() {
		return colaborador;
	}

	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}

	public int getNicho() {
		return nicho;
	}

	public void setNicho(int nicho) {
		this.nicho = nicho;
	}

	
	
	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	
	public String getRbancaria() {
		return rbancaria;
	}

	public void setRbancaria(String rbancaria) {
		this.rbancaria = rbancaria;
	}
	
	
	
	
	public char getSect() {
		return sect;
	}

	public void setSect(char sect) {
		this.sect = sect;
	}

	public mRegistroVenta() {
		init();
	}
	
	
	
	public int contarModal(String search){   
		 
		 
		 String sql = "SELECT * FROM COMPRADORES ";
		 		   
		   if(search!=""){
			   sql += " WHERE ((NOMBRE LIKE '%"+search+"%') OR (APELLIDOS LIKE '%"+search+"%') OR (CORREO LIKE '%"+search+"%'))  ";
	        	 // sql += " AND ((NOMBRE LIKE '%"+search+"%') OR (APATERNO LIKE '%"+search+"%') OR (U.AMATERNO LIKE '%"+search+"%') OR (U.USUARIO LIKE '%"+search+"%'))  ";
	          }
		   int numero = db.ContarFilas(sql);
		   return numero;
		   
	   }
	 
	 
	 public ResultSet paginacionModal_(int pg, int numreg,String search, int idsorteo){
		   	
		   /*String sql = "SELECT U.PK1,U.USUARIO,U.NOMBRE,U.APATERNO,U.AMATERNO, CONCAT(U.NOMBRE,' ',U.APATERNO,' ',U.AMATERNO) AS NOMBREC,R.ROLE,(select TOP 1 PK_USUARIO from SORTEOS_USUARIOS  where PK_USUARIO = U.PK1 AND PK_SORTEO = '"+idsorteo+"' ) AS 'PK_USUARIO' ";
		          sql += "FROM USUARIOS U, ROLES R, ROLES_USUARIO RU  ";
		          sql += "WHERE U.PK1 = RU.PK_USUARIO AND R.PK1 = RU.PK_ROLE ";*/
		 
		 String sql = "SELECT * FROM COMPRADORES ";
		          
		          if(search!=""){
		        	  sql += " WHERE ((NOMBRE LIKE '%"+search+"%') OR (APELLIDOS LIKE '%"+search+"%') OR (CORREO LIKE '%"+search+"%'))  ";
		        	  //sql += " AND ((U.NOMBRE LIKE '%"+search+"%') OR (U.APATERNO LIKE '%"+search+"%') OR (U.AMATERNO LIKE '%"+search+"%') OR (U.USUARIO LIKE '%"+search+"%'))  ";
		          }
		          
		          sql += "ORDER BY NOMBRE ASC ";
		          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
		          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
		          
		          
	   	
		          
		          System.out.println(sql);
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	   }
	 
	 
	 public ResultSet paginacionModal(int pg, int numreg, String search)
		{
			return paginacionModal(pg, numreg, search, OFFSET.TRUE);
		}
		
		public ResultSet paginacionModal(int pg, int numreg, String search, OFFSET offset)
		{
			String sql = "SELECT * FROM COMPRADORES ";

			if (search != "") {
				sql += " WHERE ((NOMBRE LIKE '%" + search + "%') OR (APELLIDOS LIKE '%" + search + "%')) OR (CORREO LIKE '%" + search + "%') ";
			}

			sql += " ORDER BY PK1 ASC";
			
			if (offset == OFFSET.TRUE) {
				sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS";
				sql += " FETCH NEXT " + numreg + " ROWS ONLY";
			}
			
			ResultSet rs = db.getDatos(sql);
			
			return rs;
		}
	 
	
	public String getTalonario(mRegistroVenta obj,int pk_sorteo){

	/*	String sql =
				" SELECT B.TALONARIO"
				+ ", (SELECT NUMBOLETOS FROM TALONARIOS WHERE FOLIO = B.TALONARIO ) AS 'NUMBOLETOS'"
				+ ", (SELECT ABONO FROM TALONARIOS WHERE FOLIO = B.TALONARIO ) AS 'ABONO'"
				+ ", B.SORTEO, B.COSTO, B.ABONO AS 'BOLETO_ABONO', B.PK_TALONARIO, B.PK1 AS 'PK_BOLETO'"
				+ ", (SELECT PK_SECTOR FROM SORTEOS_SECTORES_BOLETOS WHERE PK_BOLETO = B.PK1 AND PK_TALONARIO = B.TALONARIO) AS 'PK_SECTOR'"
				+ ", (SELECT PK_NICHO FROM SORTEOS_NICHOS_BOLETOS WHERE PK_BOLETO = B.PK1 AND PK_TALONARIO = B.TALONARIO) AS 'PK_NICHO'"
				+ ", (SELECT PK_COLABORADOR FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_BOLETO = B.PK1 AND PK_TALONARIO = B.TALONARIO) AS 'PK_COLABORADOR'"
				+ ", B.VENDIDO, B.ASIGNADO, B.INCIDENCIA"
				+ " FROM BOLETOS B WHERE B.FOLIO = '"+obj.getClave()+"' AND B.SORTEO = "+pk_sorteo+" ";*/	
		
		
		String sql = " SELECT B.FOLIO_TALONARIO AS TALONARIO,  B.COSTO, B.ABONO AS 'BOLETO_ABONO',"
				+ "  B.PK_TALONARIO, B.PK1 AS 'PK_BOLETO',  B.PK_ESTADO AS VENDIDO, B.PK_SORTEO AS SORTEO, B.ELECTRONICO,"
				+ "  B.INCIDENCIA_CAT, B.ASIGNADO_SECTOR,"//--checar	B.INCIDENCIA_CAT, B.ASIGNADO		
				+ " (SELECT NUMBOLETOS FROM VTALONARIOS_BOLETOS T WHERE T.PK1 = B.PK_TALONARIO  ) AS 'NUMBOLETOS', "
				+ "(SELECT ABONO FROM TALONARIOS T WHERE T.PK1 = B.PK_TALONARIO ) AS 'ABONO',"//checar
				+ "(SELECT PK_SECTOR FROM VSECTORES_BOLETOS SB WHERE SB.PK_BOLETO = B.PK1 AND SB.PK_TALONARIO = B.PK_TALONARIO) AS 'PK_SECTOR', "
				+ "(SELECT PK_NICHO FROM VNICHOS_BOLETOS NB WHERE NB.PK_BOLETO = B.PK1 AND NB.PK_TALONARIO = B.PK_TALONARIO) AS 'PK_NICHO',"
				+ " (SELECT PK_COLABORADOR FROM VCOLABORADORES_BOLETOS CB WHERE CB.PK_BOLETO = B.PK1 AND CB.PK_TALONARIO = B.PK_TALONARIO) AS 'PK_COLABORADOR'"
				+ " FROM VBOLETOS B WHERE B.FOLIO = '"+obj.getClave()+"' AND B.PK_SORTEO = "+pk_sorteo;	
		
		
		System.out.println("getTalonario:"+sql);
		ResultSet rs = db.getDatos(sql);
        
		String cadena = null;
		
		try {
			if (rs.next()) {
				
				cadena = rs.getString("TALONARIO");//0
				cadena += "|";
				cadena += rs.getString("NUMBOLETOS");//1
				cadena += "|";
				cadena +=  rs.getString("ABONO");//2
				cadena += "|";
				cadena +=  rs.getString("SORTEO");//3
				cadena += "|";
				cadena +=  rs.getString("COSTO");//4
				cadena += "|";
				cadena +=  rs.getString("PK_TALONARIO");//5
				cadena += "|";
				cadena +=  rs.getString("PK_BOLETO");//6
				cadena += "|";
				cadena +=  rs.getString("PK_SECTOR");//7
				cadena += "|";
				cadena +=  rs.getString("PK_NICHO");//8
				cadena += "|";
				cadena +=  rs.getString("PK_COLABORADOR");//9
				cadena += "|";
				cadena +=  rs.getString("VENDIDO");//10
				cadena += "|";
				cadena +=  rs.getString("ASIGNADO_SECTOR");//11* ASIGNADO
				cadena += "|";
				cadena +=  rs.getString("INCIDENCIA_CAT");//12* INCIDENCIA
				cadena += "|";
				cadena +=  rs.getString("BOLETO_ABONO");//13
				cadena += "|";
				cadena +=  rs.getString("ELECTRONICO");//14
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cadena;
	}
	
	
	
	public String obtenerTalonario(mRegistroVenta obj,int pk_sorteo){

		String sql = 
		" SELECT T.PK1 AS 'TALONARIO',"
		+ " T.NUMBOLETOS AS 'NUMBOLETOS',"
		+ " T.ABONO AS 'ABONO', "
		+ " T.SORTEO AS 'SORTEO',"
		+ " T.FOLIO AS 'PK_TALONARIO',"
+ " ( SELECT  COUNT(B.PK1) AS 'BVENDIDOS' FROM BOLETOS B WHERE  SORTEO = "+pk_sorteo+" AND TALONARIO = '"+obj.getClave()+"' AND B.VENDIDO = 'V' ) AS 'BVENDIDOS' "
						
	    + " FROM TALONARIOS T "
		+ " WHERE T.FOLIO = '"+obj.getClave()+"' AND T.SORTEO = "+pk_sorteo+" ";		

		
		System.out.println("obtenerTalonario e:"+sql);
		ResultSet rs = db.getDatos(sql);
        
		String cadena = null;
		
		try {
			if (rs.next()) {
				
				cadena = rs.getString("TALONARIO");//0 PK1
				cadena += "|";
				cadena += rs.getString("NUMBOLETOS");//1
				cadena += "|";
				cadena +=  rs.getString("ABONO");//2
				cadena += "|";
				cadena +=  rs.getString("SORTEO");//3
				cadena += "|";				
				cadena +=  rs.getString("PK_TALONARIO");//4 FOLIO
				cadena += "|";				
				cadena +=  rs.getString("BVENDIDOS");//5
			
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cadena;
	}
	
	
	public String VenderTalonarioCompleto(mRegistroVenta obj,SesionDatos sesion){	
		
		
		ArrayList<String> log_list_bols = new ArrayList<String>();	
					
		    String sql = "";      
	       
	        sql = "SELECT PK1,COSTO,FOLIO FROM BOLETOS "
	        + "WHERE SORTEO = "+sesion.pkSorteo+" AND TALONARIO = '"+obj.getClave()+"'";
	    	 ResultSet boletos = null;
	    			
	    	 boletos = db.getDatos(sql); 	       		
		
		try {
			
			
			while( boletos.next() ){
				
				
				String sql2 = "SELECT PK1 FROM BOLETOS WHERE SORTEO = "+ sesion.pkSorteo+" AND VENDIDO = 'V' AND PK1 = '"+boletos.getInt("PK1")+"'  ";
				System.out.println(">>>SELECT BOLETOS Vendidos: "+sql2);
				ResultSet boleto = db.getDatos(sql2);
				
				
			if(!boleto.next()){	
				
			
				sql = ""
						+ " UPDATE BOLETOS SET "
						+ " ABONO = '"+boletos.getDouble("COSTO")+"' ,"
						+ " VENDIDO = 'V',"
						+ " FECHA_M = GETDATE() "
						+ " WHERE SORTEO = " + sesion.pkSorteo
						+ " AND TALONARIO = '" + obj.getClave() + "'"
						+ " AND PK1 = '" +  boletos.getInt("PK1")+ "'";
						
				  System.out.println("VenderTalonarioCompleto actualizar bol e:"+sql);
									
				  db.execQuery(sql);	 				 
				 
				  log_list_bols.add( boletos.getString("PK1"));
				 
				 
				// --- Se guarda un registro de seguimiento ---
					try {
						Seguimiento.guardaVenta(db
								, ASIGNACION.VENTA
								, Long.valueOf(sesion.pkSorteo),0,0,0
								, ASIGNACION.BOLETO, Long.valueOf(obj.getClave()) ,  boletos.getInt("PK1")
								, 'V'
								, boletos.getDouble("COSTO"), boletos.getDouble("COSTO")
								, 1
								, "-"
								, "VENTA"
								, "venta-bols-1"
								,sesion.nombreCompleto);//FALTA USUARIO(nombre completo)
					}
					catch (Exception ex) { com.core.Factory.Error(ex, sql); }				
					
			  }	
					
			}		
			
		} catch (Exception ex) { com.core.Factory.Error(ex, sql); }		
		
		
		
		try {
			this.Log(sesion, LOG.EDITADO, this, "VenderTalonarioCompleto",
					"bols="+log_list_bols.toString());
		} catch(Exception ex) { Factory.Error(ex, "Log"); } 		
		
		
		
		//ABONAMOS AL TALONARIO LA VENTA DEL BOLETO   
        sql = "SELECT ISNULL(SUM(ABONO),0) AS TOTAL, ISNULL(SUM(COSTO),0) AS MONTO FROM BOLETOS "
       + "WHERE SORTEO = "+sesion.pkSorteo+" AND TALONARIO = '"+obj.getClave()+"'";
       ResultSet rs = null;			
       rs = db.getDatos(sql); 	 
       
       String monto = "";
		
		 try {
			 
			 if(rs.next()) {	
			 
			 monto =	rs.getString("MONTO");
			 
			sql = "UPDATE TALONARIOS SET ABONO = "+rs.getDouble("MONTO")+" ,"
						+ "VENDIDO = 'V' ,"
						+ "USUARIO = '"+sesion.nickName+"' ,"
						+ "FECHA_M = GETDATE() "
				 		+ "WHERE SORTEO = "+sesion.pkSorteo+" AND FOLIO = '"+obj.getClave()+"'";
			
			
			 System.out.println("VenderTalonarioCompleto act talonario e:"+sql);
			 
			 db.execQuery(sql);		
			 
			 
				// --- Se guarda un registro de seguimiento ---
				try {
					Seguimiento.guardaVenta(db
							, ASIGNACION.VENTA
							, Long.valueOf(sesion.pkSorteo),0,0,0
							, ASIGNACION.TALONARIO,  Long.valueOf(obj.getClave()) , 0
							, 'V'
							, rs.getDouble("MONTO"), rs.getDouble("MONTO")
							, 11
							, "-"
							, "VENTA"
							, "venta-tal-11"
							,sesion.nombreCompleto);//CREAR usuario
				}
				catch (Exception ex) { com.core.Factory.Error(ex, sql); }
				 
				// --- Se guarda un registro de seguimiento ---
				try {
					this.Log(sesion, LOG.EDITADO, this, "VenderTalonarioCompleto",
							sesion.toShortString() + ", tal=" + obj.getClave());
				}catch(Exception ex) { Factory.Error(ex, "Log"); }			 
			 
			 }
			
			} catch (Exception ex) { com.core.Factory.Error(ex, sql); }	
		
		
		return monto;			
		
	}
	
	
	
	public String consultaSoloComprador(int pkcomprador){

		mColaboradores modelColab = new mColaboradores();		
		
		
		String sql = "SELECT * FROM COMPRADORES WHERE PK1 = "+pkcomprador;
		//String sql = " SELECT CP.* FROM COMPRADORES CP, COMPRADORES_BOLETOS CPB  WHERE  CP.PK1 = CPB.PK_COMPRADOR AND PK_BOLETO ='"+pkboleto+"'";				
		System.out.println("consultasoloComprador:"+sql);

		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next()) {				
				
				modelColab.setCP(rs.getString("CP"));//TRATAR *NULL*
				String colonias = modelColab.Obtener_Colonias_HTML();// colonia de sepomex
				
				StringBuilder sb = new StringBuilder();
				
				sb
				.append(rs.getString("NOMBRE")).append("|")
				.append(rs.getString("APELLIDOS")).append("|")
				.append(rs.getString("TELEFONO_F")).append("|")
				.append(rs.getString("TELEFONO_M")).append("|")
				.append(rs.getString("CORREO")).append("|")
				.append(rs.getString("CALLE")).append("|")
				.append(rs.getString("NUMERO")).append("|")
				.append(rs.getString("COLONIA")).append("|")//7
				.append(rs.getString("ESTADO")).append("|")//8
				.append(rs.getString("MUNDEL")).append("|")//9
				.append(rs.getString("CP")).append("|")//10
				.append(colonias).append("|")//11
				.append(rs.getString("PK1")).append("|")//12
				;
				
				return sb.toString();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	public String consultaComprador(int pk_sorteo,String boleto, int pkboleto){

		mColaboradores modelColab = new mColaboradores();		
		
		
		//String sql = "SELECT * FROM COMPRADORES WHERE PK_SORTEO="+pk_sorteo+" AND BOLETO='"+boleto+"'";

		String sql = " SELECT CP.* FROM COMPRADORES CP, COMPRADORES_BOLETOS CPB  WHERE  CP.PK1 = CPB.PK_COMPRADOR AND PK_BOLETO ='"+pkboleto+"'";				
		System.out.println("consultaComprador:"+sql);

		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs.next()) {				
				
				modelColab.setCP(rs.getString("CP"));//TRATAR *NULL*
				String colonias = modelColab.Obtener_Colonias_HTML();// colonia de sepomex
				
				StringBuilder sb = new StringBuilder();
				
				sb
				.append(rs.getString("NOMBRE")).append("|")
				.append(rs.getString("APELLIDOS")).append("|")
				.append(rs.getString("TELEFONO_F")).append("|")
				.append(rs.getString("TELEFONO_M")).append("|")
				.append(rs.getString("CORREO")).append("|")
				.append(rs.getString("CALLE")).append("|")
				.append(rs.getString("NUMERO")).append("|")
				.append(rs.getString("COLONIA")).append("|")//7
				.append(rs.getString("ESTADO")).append("|")//8
				.append(rs.getString("MUNDEL")).append("|")//9
				.append(rs.getString("CP")).append("|")//10
				.append(colonias).append("|")//11
				.append(rs.getString("PK1")).append("|")//12
				;
				
				return sb.toString();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdsorteo();

		ResultSet rs = db.getDatos(sql);

		return rs;

	}

	public ResultSet getSectoresUsuario(mRegistroVenta obj) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ obj.getSector() +"' ";
		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public boolean isAdministrador() {

		String sql = "";

		int user = this.getIdusuario();
		//System.out.println(">>>>model(is isAdministrador) id usuario: " + user);

		sql = "SELECT PK1 FROM ROLES_USUARIO WHERE PK_USUARIO = '" + user + "' AND PK_ROLE = '2' ";
		//System.out.println(">>>>SQL: " + sql);
		int numero = db.ContarFilas(sql);

		if (numero > 0)
			return true;

		return false;
	}

	public void getSectorNicho(mRegistroVenta obj) {

		String sql = "SELECT PK_SECTOR FROM NICHOS WHERE PK1 ='" + obj.getNicho() + "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				obj.setSector(rs.getInt("PK_SECTOR"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getNichos(int idsector) {

		String sql = "SELECT * FROM NICHOS WHERE PK_SECTOR = '" + idsector + "'";

		ResultSet rs = db.getDatos(sql);

		return rs;

	}



	public int contar(String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - contar

		String sql = " SELECT C.PK1"
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1 ";

		if (search != "") {
			if (isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'  ";
			} else {
				sql += " AND C.NOMBRE LIKE '%" + search + "%'  ";
			}
		}

		if (sector != 0) {

			sql += " AND N.PK_SECTOR = '" + sector + "'  ";

		} else {

			setIdusuario(sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo(sesion.pkSorteo);
				getSectorUsuarioActual();

				sql += " AND N.PK_SECTOR = '" + this.getSector() + "'  ";
			}
		}

		if (nicho != 0) {

			sql += " AND C.PK_NICHO = '" + nicho + "'  ";

		}

		//System.out.println("COLABORADORES COUNT: " + sql);

		int numero = db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacion(int pg, int numreg, String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - paginacion

		String sql = " SELECT C.*,CONCAT(C.NOMBRE,' ',C.APATERNO) AS 'NOM_COLABORADOR', N.NICHO AS 'NOM_NICHO', S.SECTOR AS 'NOM_SECTOR',(SELECT SUM(MONTO) FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO = " + sesion.pkSorteo + " and PK_SECTOR = S.PK1 AND PK_NICHO = N.PK1 AND PK_COLABORADOR = C.PK1) AS MONTO "
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1";

		if (search != "") {
			if (isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'  ";
			} else {
				sql += " AND C.NOMBRE LIKE '%" + search + "%'  ";
			}
		}

		if (sector != 0) {

			sql += " AND N.PK_SECTOR = '" + sector + "'  ";

		} else {

			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				sql += " AND N.PK_SECTOR = '" + this.getSector() + "'  ";
			}
		}

		if (nicho != 0) {

			sql += " AND C.PK_NICHO = '" + nicho + "'  ";
		}

		sql += " ORDER BY C.PK1 ASC ";
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; // -- not sure if
															// you
															// need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";

		//System.out.println("COLABORADORES: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	
	public int registrar_colaborador_Telefonos(int id, int secuencia,
			String telefono, char tipo, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO COLABORADORES_TELEFONOS (PK_COLABORADOR,SECUENCIA,TELEFONO,TIPO,PK_USUARIO) VALUES ('"
				+ id + "','"
				+ secuencia + "','"
				+ telefono + "','"
				+ tipo + "','"
				+ pkUsuario + "')";
		//System.out.println(sql);
		int res = db.execQuery(sql);
		return res;
	}

	public int registrar_colaborador_Correo(int id, int secuencia, String correo, char tipo, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO COLABORADORES_CORREOS (PK_COLABORADOR,SECUENCIA,CORREO,TIPO,PK_USUARIO) VALUES ('"
				+ id + "','"
				+ secuencia + "','"
				+ correo + "','"
				+ tipo + "','"
				+ pkUsuario + "')";
		//System.out.println(sql);
		int res = db.execQuery(sql);
		return res;
	}

	public int registrar_colaborador_RedSocial(int id, int secuencia, String red, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO COLABORADORES_REDES_SOCIALES (PK_COLABORADOR,SECUENCIA,RED,PK_USUARIO) VALUES ('"
				+ id + "','"
				+ secuencia + "','"
				+ red + "','"
				+ pkUsuario + "')";
		//System.out.println(sql);
		int res = db.execQuery(sql);
		return res;
	}

	public int Borrar(mRegistroVenta obj) {

		db.con();
		String sql = "DELETE FROM COLABORADORES WHERE PK1=" + obj.getId();
		int res = db.execQuery(sql);
		return res;

	}

	public void actualizarAbono(mRegistroVenta obj) {

		db.con();
		String sql = "";

		sql = "UPDATE COLABORADORES SET ABONO = " + obj.getComision() + ", FECHA_M = GETDATE()"
			+ " WHERE PK1 = " + obj.getId();

		db.execQuery(sql);
	}

	public void getSectorUsuarioActual() {

		String sql = "SELECT PK_SECTOR FROM USUARIOS WHERE PK1 = " + this.getIdusuario();

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				this.setSector(rs.getInt("PK_SECTOR"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}
	
	
	

}
