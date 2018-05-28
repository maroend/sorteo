package com.sorteo.sorteos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.core.Factory;
import com.core.Seguimiento;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;
import com.core.Seguimiento.ASIGNACION;
import com.sorteo.herramientas.controller.ProgressBarCalc;
import com.sorteo.usuarios.model.mRoles;


public class mSorteos extends SuperModel {
	
	
	private int id;
	private String clave;  
	private String sorteo;  
	private String descripcion;  
	
	private String fechainico;
	private String fechatermino;
	
	private String imagen;
	
	private int activo;
	
	
	private int numTalonarios;
	private int numBoletos;
	
	private int idUsuario; // solo para la opcion "Mis sorteos"
	
	
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


	public int getNumTalonarios() {
		return numTalonarios;
	}


	public void setNumTalonarios(int numTalonarios) {
		this.numTalonarios = numTalonarios;
	}


	public int getNumBoletos() {
		return numBoletos;
	}


	public void setNumBoletos(int numBoletos) {
		this.numBoletos = numBoletos;
	}


	public mSorteos() {
		// TODO Auto-generated constructor stub
	}
	
	
	 public ResultSet listar(){
	    	
	    	String sql = "SELECT * FROM SORTEOS";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	public int getProcentajeCargaBoletos(int pksorteo){
		
		int porcentaje= 0;
		String sql = "SELECT  (convert(numeric(5),(SELECT COUNT(*) FROM SORTEOS_BOLETOS WHERE PK_SORTEO = "+pksorteo+")) / convert(numeric(5),COUNT(*))) * 100 as PORCENTAJE FROM BOLETOS WHERE SORTEO = "+pksorteo;
		ResultSet rs = db.getDatos(sql);
		
		try {
			if (rs != null && rs.next()) {
				porcentaje = rs.getInt("PORCENTAJE");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return porcentaje;
	}
	 
	 
	public int contar() {
		if (this.getIdUsuario() != -1)
			return contarSorteosAsignados();
		
		String sql = "SELECT * FROM SORTEOS";
		
		return db.ContarFilas(sql);
	}

	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion) {
		String sql;
		boolean verHistorialSorteos = sesion.permisos.havePermiso(20110);

		if (sesion.misSorteos == 0) {
			sql = " SELECT * FROM SORTEOS";
			if (search != "") {
				sql += " WHERE SORTEO LIKE '%" + search + "%'";
			}
			sql += " ORDER BY FECHA_I DESC ";
		}
		else {
			if (verHistorialSorteos) {
				sql = " SELECT S.*"
						+ " FROM SORTEOS S, SORTEOS_USUARIOS SU"
						+ " WHERE S.PK1 = SU.PK_SORTEO AND SU.PK_USUARIO=" + this.getIdUsuario();
				
				if (search != "") {
					sql += " AND S.SORTEO LIKE '%" + search + "%'";
				}
				sql += " ORDER BY S.PK1 ASC ";
			}
			else{
				//sql = "SELECT S.* FROM SORTEOS S, USUARIOS U WHERE U.PK1=" + sesion.pkSorteo + " AND U.PK_SORTEO = S.PK1  ORDER BY S.PK1 DESC";
				//sql = "SELECT S.* FROM SORTEOS S, USUARIOS U WHERE U.PK1=" + this.getIdUsuario() + " AND U.PK_SORTEO = S.PK1  ORDER BY S.PK1 DESC";
				
				 sql = "SELECT S.*"
						+ " FROM SORTEOS S,SORTEOS_USUARIOS SU"
						+ " WHERE S.PK1=SU.PK_SORTEO AND SU.PK_USUARIO="
						+ this.getIdUsuario()+" ORDER BY S.PK1 DESC";
						
				
				
				
			}
		}

		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS"; // -- not sure if you need -1
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		
		System.out.println("\n======>"+sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	
	public int numeroTalonariosBoveda(mSorteos obj) {
		String sql = "SELECT COUNT(*) AS 'MAX' FROM TALONARIOS WHERE SORTEO = " + obj.getId() + " AND ASIGNADO = 0";
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next())
				max = rs.getInt("MAX");
		} catch (SQLException e) { com.core.Factory.Error(e, sql); }
		return max;
	}
	
	public int numeroBoletosBoveda(mSorteos obj) {
		String sql = "SELECT count(*) AS 'MAX' FROM BOLETOS WHERE SORTEO = " + obj.getId() + " AND ASIGNADO = 0";
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next())
				max = rs.getInt("MAX");
		} catch (SQLException e) { com.core.Factory.Error(e, sql); }
		return max;
	}
	
	public int numeroTalonarios(mSorteos obj) {
		//String sql = "SELECT COUNT(*) AS 'MAX' FROM SORTEOS_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+"";
		String sql = "SELECT COUNT(*) AS 'VALUE' FROM TALONARIOS WHERE PK_SORTEO = "+obj.getId()+"";
		
		return db.getValue(sql, 0);
		/*
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			com.core.Factory.Error(e, sql);
		}
		return max;
		*/
	}

	public int numeroBoletos(mSorteos obj) {
		String sql = "SELECT COUNT(*) AS 'MAX' FROM BOLETOS B, TALONARIOS T WHERE B.PK_TALONARIO=T.PK1 AND T.PK_SORTEO = "+obj.getId()+"";
		
		return db.Count(sql);
		/*
		ResultSet rs = db.getDatos(sql);
		int max = 0;
		try {
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			com.core.Factory.Error(e, sql);
		}

		return max;
		*/
	}

	public void cargartalonarios(mSorteos obj, SesionDatos sesion, ProgressBarCalc progress){
		
		ArrayList<String> log_list_tals = new ArrayList<String>();
		String sql = "SELECT * FROM TALONARIOS WHERE SORTEO = " + obj.getId() + " AND ASIGNADO = 0";
		String sqlINSERT = "";
		int numtalonarios = 0;

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {
				while (rs.next()) {
					numtalonarios++;
					sqlINSERT = "INSERT INTO SORTEOS_TALONARIOS (PK_TALONARIO,PK_SORTEO,FOLIO,NUMBOLETOS,MONTO,FORMATO,USUARIO,DISPONIBLES) VALUES ('"+rs.getString("PK1")+"','"+rs.getString("SORTEO")+"','"+rs.getString("FOLIO")+"','"+rs.getString("NUMBOLETOS")+"','"+rs.getString("MONTO")+"','"+rs.getString("FORMATO")+"','admin','"+rs.getString("NUMBOLETOS")+"') ";
					db.execQuery(sqlINSERT);
					
					log_list_tals.add(rs.getString("PK1"));
					
					// --- Se guarda un registro de seguimiento ---
					try {
						Seguimiento.guardaAsignacion(db
								, ASIGNACION.SORTEO, obj.getId(), 0, 0, 0
								, ASIGNACION.TALONARIO, Long.valueOf(rs.getString("PK1")) , 0
								, 'N'
								, rs.getInt("MONTO"), 0.0
								, rs.getInt("NUMBOLETOS")
								, rs.getString("FORMATO")
								, "sor-tals");
					}
					catch (Exception ex) { com.core.Factory.Error(ex, sql); }
					
					progress.progress();
				}
				this.setNumTalonarios(numtalonarios);
				
				// --- Se guarda un registro de seguimiento ---
				try {
					Calendar cal = Calendar.getInstance();
			        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			        System.out.println("Log...    " + sdf.format(cal.getTime()) );
			        
					this.Log(sesion, LOG.REGISTRO, this, "cargartalonarios", "tals=" + log_list_tals.toString());
					
			        System.out.println("Log ok... " + sdf.format(cal.getTime()) );
				} catch(Exception ex) { Factory.Error(ex, "Log"); }
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			com.core.Factory.Error(e, sql);
		}
	}
	
	
	public void cargarboletos(mSorteos obj, SesionDatos sesion, ProgressBarCalc progress) {
		
		ArrayList<String> log_list_bols = new ArrayList<String>();
		String sql = "SELECT * FROM BOLETOS WHERE SORTEO = " + obj.getId() + " AND ASIGNADO = 0";
		String sqlINSERT = "";
		int numboletos = 0;

		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null) {

				while (rs.next()) {
					numboletos++;
					sqlINSERT = "INSERT INTO SORTEOS_BOLETOS (PK_BOLETO,PK_TALONARIO,PK_SORTEO,FOLIO,COSTO,FORMATO,USUARIO) VALUES ('"
							+ rs.getString("PK1") + "','"
							+ rs.getString("TALONARIO") + "','"
							+ rs.getString("SORTEO") + "','"
							+ rs.getString("FOLIO") + "','"
							+ rs.getString("COSTO") + "','"
							+ rs.getString("FORMATO") + "','admin')";
					db.execQuery(sqlINSERT);
					
					log_list_bols.add( rs.getString("PK1"));

					// --- Se guarda un registro de seguimiento ---
					try {
						int idTalonario = super.getIdTalonario(Integer.valueOf(rs.getString("SORTEO")), rs.getString("TALONARIO"));
						Seguimiento.guardaAsignacion(db
								, ASIGNACION.SORTEO, Long.valueOf(rs.getString("SORTEO")), 0, 0, 0
								, ASIGNACION.BOLETO,  idTalonario, Long.valueOf(rs.getString("PK1"))
								, 'N'
								, rs.getDouble("COSTO"), 0.0
								, 1
								, rs.getString("FORMATO")
								, "sor-bols");
					}
					catch (Exception ex) { com.core.Factory.Error(ex, sql); }

					progress.progress();
				}
				this.setNumBoletos(numboletos);

				// --- Se guarda un registro de seguimiento ---
				try {
					this.Log(sesion, LOG.REGISTRO, this, "cargarboletos", "bols=" + log_list_bols.toString());
				} catch(Exception ex) { Factory.Error(ex, "Log"); }

				//rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			com.core.Factory.Error(e, sql);
		}

	}     
     
     
	public int setCarga(mSorteos obj, SesionDatos sesion){

		String sql = "UPDATE TALONARIOS SET ASIGNADO=1 WHERE SORTEO="+obj.getId()+" ";       
		       sql += "UPDATE BOLETOS SET ASIGNADO=1 WHERE SORTEO="+obj.getId()+" ";
		       
	    int res = db.execQuery(sql);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.EDITADO, this, "setCarga", "sor=" + obj.getId());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }

		return res;
	}    
 
 
 
	public int montoSorteo(mSorteos obj){
		String sql = "SELECT SUM(B.COSTO) AS 'VALUE' FROM BOLETOS B, TALONARIOS T WHERE B.PK_TALONARIO=T.PK1 AND T.PK_SORTEO = "+obj.getId()+"";

		return db.getValue(sql, 0);

		/*
		 String sql = "SELECT SUM(MONTO) AS TOTAL FROM SORTEOS_TALONARIOS WHERE PK_SORTEO = "+obj.getId()+"";
		 ResultSet rs = db.getDatos(sql);
		 int total = 0;
		 try {
			if (rs != null && rs.next()) {
				total = rs.getInt("TOTAL");
			    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			com.core.Factory.Error(e, sql);
		}
		 
		 return total;
		 */
	}

	public int registrar(mSorteos obj, SesionDatos sesion) {
		String sql =
				"INSERT INTO SORTEOS (CLAVE,SORTEO,DESCRIPCION,IMAGEN,FECHA_I,FECHA_T,BLOQUEO)"//ACTIVO
	    		+ " VALUES ('"+obj.getClave()+"','"+obj.getSorteo()+"','"+obj.getDescripcion()
	    		+ "','"+obj.getImagen()+"','"+obj.getFechainico()+"','"+obj.getFechatermino()+"',1)";
		int res = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "registrar", "sor=" + obj.getSorteo());
		} catch (Exception ex) { Factory.Error(ex, "Log"); }
		
		return res;
	}

	public int eliminar(mSorteos obj, SesionDatos sesion) {
		String sql = "DELETE FROM SORTEOS WHERE PK1='" + obj.getId() + "'";
		int res = db.execQuery(sql);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminar",
					"sor=" + obj.getId());
		} catch (Exception ex) {
			Factory.Error(ex, "Log");
		}
		return res;
	}

	public int activarSorteo(boolean activar, SesionDatos sesion) {
		String sql = "UPDATE SORTEOS SET BLOQUEO=" + (activar?1:0) + " WHERE PK1=" + this.getId();// ACTIVO=
		int res = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			String msg = "";
			if (activar)
				msg = (res==1) ? "Sorteo activado" : "Error activando sorteo";
			else
				msg = (res==1) ? "Sorteo cerrado" : "Error cerrando sorteo";
			this.Log(sesion, LOG.EDITADO, this, "activarSorteo", "so=" + this.getId() + ", " + msg);
		} catch (Exception ex) {
			Factory.Error(ex, "Log");
		}
		return res;
	}
	
	public boolean EliminarCarga(mSorteos obj, SesionDatos sesion) {

		String sql =   "DELETE FROM SORTEOS_TALONARIOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "DELETE FROM SORTEOS_BOLETOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "DELETE FROM SORTEOS_SECTORES_TALONARIOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "DELETE FROM SORTEOS_SECTORES_BOLETOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "DELETE FROM SORTEOS_NICHOS_TALONARIOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "DELETE FROM SORTEOS_NICHOS_BOLETOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "DELETE FROM SORTEOS_COLABORADORES_TALONARIOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "DELETE FROM SORTEOS_COLABORADORES_BOLETOS WHERE PK_SORTEO='"+obj.getId()+"' ";
				sql += "UPDATE TALONARIOS SET ASIGNADO=0 WHERE SORTEO="+obj.getId()+" ";
				sql += "DELETE SEGUIMIENTO WHERE PK_SORTEO="+obj.getId()+" AND NIVEL<>'Boveda'";
				sql += "UPDATE BOLETOS SET ASIGNADO=0 WHERE SORTEO="+obj.getId()+" ";
				sql += "UPDATE SORTEOS SET CARGA=0 WHERE PK1="+obj.getId()+" ";
		int res = db.execQuery(sql);
		
		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "EliminarCarga", "so=" + obj.getId());
		} catch(Exception ex) { Factory.Error(ex, "Log"); }
		
		return (res==1);
	}
	
	
	
	public boolean ExisteCarga(mSorteos obj){
		   	
		   	String sql = "SELECT PK1 FROM SORTEOS WHERE PK1 = '"+obj.getId()+"' AND CARGA = 1";
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if( rs.next()){
					 
					return true;
					
				}else{
					
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				com.core.Factory.Error(e, sql);
			}
			return false;
		   	
			}
	 
	 
	public boolean ExisteSorteo(String usr){
		   	
		   	String sql = "SELECT PK1 FROM SORTEOS WHERE CLAVE = '"+usr+"'";
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
				if( rs.next()){
					 
					return true;
					
				}else{
					
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				com.core.Factory.Error(e, sql);
			}
			return false;
		   	
	}

	//------------ Restriccion de sorteos por usuario -------------

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	//  Funciones para "Mis sorteos"
	public int contarSorteosAsignados() {
		/*String sql
				= "SELECT S.*"
				+ " FROM SORTEOS S,SORTEOS_USUARIOS SU"
				+ " WHERE S.PK1=SU.PK_SORTEO AND SU.PK_USUARIO="
				+ this.getIdUsuario();*/
		
		
		String sql = "SELECT S.*"
		+ " FROM SORTEOS S,SORTEOS_USUARIOS SU"
		+ " WHERE S.PK1=SU.PK_SORTEO AND SU.PK_USUARIO="
		+ this.getIdUsuario();
		
		System.out.println(">>>>SQL SORTEO: " + sql); 
		
		
		return db.ContarFilas(sql);
	}

	// -------------- Sectores
	public int contarSectoresAsignados() {
		/*String  sql
				= "SELECT S.*"
				+ " FROM SORTEOS S, SORTEOS_USUARIOS SUS"
				+ " WHERE S.PK1 = SUS.PK_SORTEO AND SUS.PK_USUARIO="
				+ this.getIdUsuario();*/		
		
		String  sql	= "SELECT * "
				+ "FROM SORTEOS_USUARIOS "
				+ "WHERE PK_USUARIO =  "+this.getIdUsuario() + 
				" AND (PK_SECTOR IS NOT NULL OR PK_SECTOR <> '')";				
		
		return db.ContarFilas(sql);
	}
	
	public int consultaIdSorteoDesdeSectores() {
		
	/*	String  sql
				= "SELECT TOP 1 SUS.PK_SORTEO"
				+ " FROM SORTEOS S, SORTEOS_USUARIOS_SECTORES SUS"
				+ " WHERE S.PK1 = SUS.PK_SORTEO AND SUS.PK_USUARIO="
				+ this.getIdUsuario();*/
		
		String  sql
		= "SELECT TOP 1 PK1"
		+ " FROM SORTEOS ";			
		
		try {
			ResultSet res = db.getDatos(sql);
			if (res.next()) {
				return Integer.valueOf(res.getInt("PK1"));
				//return Integer.valueOf(res.getInt("PK_SORTEO"));
			}
		}catch(Exception ex) { com.core.Factory.Error(ex, sql); }
		return -1;
	}

	// -------------- Nichos
	public int contarNichosAsignados() {
		
		/*String  sql
				= "SELECT S.*"
				+ " FROM SORTEOS S, SORTEOS_USUARIOS_NICHOS SUN"
				+ " WHERE S.PK1 = SUN.PK_SORTEO AND SUN.PK_USUARIO="
				+ this.getIdUsuario();*/
		
		String  sql	= "SELECT * "
				+ "FROM SORTEOS_USUARIOS "
				+ "WHERE PK_USUARIO =  "+this.getIdUsuario() + 
				" AND (PK_NICHO IS NOT NULL OR PK_NICHO <> '')";		
		
		
		return db.ContarFilas(sql);
	}

	public int consultaIdSorteoDesdeNichos() {
		/*String  sql
				= "SELECT TOP 1 SUN.PK_SORTEO"
				+ " FROM SORTEOS S, SORTEOS_USUARIOS_NICHOS SUN"
				+ " WHERE S.PK1 = SUN.PK_SORTEO AND SUN.PK_USUARIO="
				+ this.getIdUsuario();*/
		String  sql
		= "SELECT TOP 1 PK1"
		+ " FROM SORTEOS ";	
		try {
			ResultSet res = db.getDatos(sql);
			if (res.next()) {
				return Integer.valueOf(res.getInt("PK1"));
				//return Integer.valueOf(res.getInt("PK_SORTEO"));
			}
		}catch(Exception ex) { com.core.Factory.Error(ex, sql); }
		return -1;
	}

	public int consultaIdSectorDesdeNichos() {
		
		/*String  sql
				= "SELECT TOP 1 SUN.PK_SECTOR"
				+ " FROM SORTEOS S, SORTEOS_USUARIOS_NICHOS SUN"
				+ " WHERE S.PK1 = SUN.PK_SORTEO AND SUN.PK_USUARIO="
				+ this.getIdUsuario();*/	
		
		
		
		/*String  sql = "SELECT TOP 1 S.PK_SECTOR FROM SORTEOS_USUARIOS SU, NICHOS N, USUARIOS S"				
				+ " WHERE SU.PK_USUARIO=" + this.getIdUsuario() 
				+" AND SU.PK_USUARIO = S.PK1" 	
		        + " AND SU.PK_NICHO = N.PK1";*/	
		
		
		String  sql = "SELECT TOP 1 N.PK_SECTOR FROM SORTEOS_USUARIOS SU, NICHOS N"				
				+ " WHERE SU.PK_USUARIO=" + this.getIdUsuario() 
				//+" AND SU.PK_USUARIO = S.PK1" 	
		        + " AND SU.PK_NICHO = N.PK1";	
		
		
		System.out.println(">>>>SQL COUNT NICHOS: " + sql); 	
		
		try {
			ResultSet res = db.getDatos(sql);
			if (res.next()) {
				return Integer.valueOf(res.getInt("PK_SECTOR"));
			}
		}catch(Exception ex) { com.core.Factory.Error(ex, sql); }
		return -1;
	}
	
	
	
	 public int ObtenerRole(){
		   	
		   	String sql = "SELECT * FROM ROLES_USUARIO WHERE PK_USUARIO =  '"+this.getIdUsuario()+"'";		   	
		   	ResultSet res = db.getDatos(sql);
		   	
		   	try {
				 res = db.getDatos(sql);
				if (res.next()) {
					return Integer.valueOf(res.getInt("PK_ROLE"));
				}
			}catch(Exception ex) { com.core.Factory.Error(ex, sql); }
			return -1;
		}
	 
	 
	 public void setPredeterminadoSorteo(){
			
			String sql = "UPDATE USUARIOS SET PK_SORTEO = "+this.getId()+" WHERE PK1 ="+this.getIdUsuario();//, PK_SECTOR = "+this.getIdSector()+",  PK_NICHO="+this.getIdNicho()+"
			db.execQuery(sql);
		}
		   	
	
		public void cosultaPredeterminados(){
			
			String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = "+this.getIdUsuario();
			ResultSet rs = db.getDatos(sql);
			System.out.println(sql);
			try {
				while (rs.next()) {
					this.setId(rs.getInt("PK_SORTEO"));
					//this.setIdSector(rs.getInt("PK_SECTOR"));
				//	this.setIdNicho(rs.getInt("PK_NICHO"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	
	
	
}




