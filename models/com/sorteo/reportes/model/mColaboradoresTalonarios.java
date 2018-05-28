package com.sorteo.reportes.model;

import java.sql.ResultSet;

import com.core.SesionDatos;

import java.sql.SQLException;
import java.util.HashMap;

import com.core.SuperModel;
import com.core.Factory.LOG;

public class mColaboradoresTalonarios  extends SuperModel {
	
	private int id;
	
	private String clave;
	private int sector;
	private String rbancaria;
	private String colaborador;
	private int nicho;
	private String descripcion;
	boolean existe;

	public boolean isExcel() {
		return existe;
	}

	public void setExisteExcel(boolean existeExcel) {
		this.existe = existeExcel;
	}

	private String nombre; 
	private String apellidop;  
	private String apellidom;  
	
	private String edad;  
	private String rfc;
	
	private String telefonocasa;
	private String telefonooficina;
	private String telefonomovil;
	
	private String correopersonal;
	private String correotrabajo;
	private String correootro;
	
	private String facebook;
	private String twitter;
	private String redotro;
	
	private int cp;
	private String pais;
	private String estado;
	private String mundel;
	private String colonia;	
	private String calle;
	private String numExterior;
	private String numInterior;
	
	private int idusuario;
	private int idsorteo;
	
	private char sect;
	private double Comision;
	
	
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
	
	public double getComision() {
		return Comision;
	}
	
	public void setComision(double comision) {
		Comision = comision;
	}
	
	public void init(){		
	     
		 this.existe = false;
		 this.id = 0;
		 this.clave = "";
		 this.sector = 0;
		 this.rbancaria = "";		
		 this.nicho = 0;
		 this.descripcion = "";
		 this.edad = "";  
		 this.rfc = "";
			
		 
		 this.nombre = "";
		 this.apellidop = "";
		 this.apellidom = "";	
			
		 this.telefonocasa = "";
		 this.telefonomovil = "";
		 this.telefonooficina = "";
		
		 this.correopersonal = "";
		 this.correotrabajo = "";
		 this.correootro = "";	 
		 this.facebook = "";
		 this.twitter = "";
		 this.redotro = "";	
		 
		 this.cp = 0;
		 this.pais = "";	 
		 this.estado = "";
		 this.mundel = "";
		 this.colonia = "";
		 this.calle = "";
		 this.setNumExterior("");
		 this.setNumInterior("");
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidop() {
		return apellidop;
	}

	public void setApellidop(String apellidop) {
		this.apellidop = apellidop;
	}

	public String getApellidom() {
		return apellidom;
	}

	public void setApellidom(String apellidom) {
		this.apellidom = apellidom;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getTelefonocasa() {
		return telefonocasa;
	}

	public void setTelefonocasa(String telefonocasa) {
		this.telefonocasa = telefonocasa;
	}

	public String getTelefonooficina() {
		return telefonooficina;
	}

	public void setTelefonooficina(String telefonooficina) {
		this.telefonooficina = telefonooficina;
	}

	public String getTelefonomovil() {
		return telefonomovil;
	}

	public void setTelefonomovil(String telefonomovil) {
		this.telefonomovil = telefonomovil;
	}

	public String getCorreopersonal() {
		return correopersonal;
	}

	public void setCorreopersonal(String correopersonal) {
		this.correopersonal = correopersonal;
	}

	public String getCorreotrabajo() {
		return correotrabajo;
	}

	public void setCorreotrabajo(String correotrabajo) {
		this.correotrabajo = correotrabajo;
	}

	public String getCorreootro() {
		return correootro;
	}

	public void setCorreootro(String correootro) {
		this.correootro = correootro;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getRedotro() {
		return redotro;
	}

	public void setRedotro(String redotro) {
		this.redotro = redotro;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public void setCp(String cp) {
		if(cp==null || cp.trim()=="")
			this.cp = 0;
		else
			this.cp = Integer.valueOf(cp);
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMundel() {
		return mundel;
	}

	public void setMundel(String mundel) {
		this.mundel = mundel;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
		if(this.calle != null && this.calle.length()>50)
			this.calle = this.calle.substring(0, 50);
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}
	
	public char getSect() {
		return sect;
	}

	public void setSect(char sect) {
		this.sect = sect;
	}

	public mColaboradoresTalonarios () {
		init();
	}
	
	public void Obtener_Direccion(mColaboradoresTalonarios  obj){

		String sql = "SELECT PAIS,d_estado,D_mnpio FROM SEPOMEX WHERE (d_codigo LIKE N'%"+obj.getCp()+"%') GROUP BY PAIS,d_estado,D_mnpio";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				obj.setPais(rs.getString("PAIS"));
				obj.setEstado(rs.getString("d_estado"));
				obj.setMundel(rs.getString("D_mnpio"));
				obj.setColonia(this.Obtener_Colonia());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String Obtener_Colonia() {

		String sql = "SELECT d_asenta FROM SEPOMEX WHERE (d_codigo LIKE N'%"+this.getCp()+"%') GROUP BY d_asenta";
		ResultSet rs = db.getDatos(sql);
		String colonia = "";

		try {
			while (rs.next()) {
				colonia += "<option>" + rs.getString("d_asenta") + "</option>";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return colonia;
	}

	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdsorteo();

		ResultSet rs = db.getDatos(sql);

		return rs;

	}

	public ResultSet getSectoresUsuario(mColaboradoresTalonarios  obj) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ obj.getSector() +"' ";
		System.out.println("getSectoresUsuario:"+sql);
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

	public void getSectorNicho(mColaboradoresTalonarios  obj) {

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

	public HashMap<String, Integer> getNichosXSorteo(int pkSorteo) {
		
		HashMap<String, Integer> mapNichos = new HashMap<String, Integer>();

		String sql = "SELECT * FROM NICHOS WHERE PK_SORTEO = " + pkSorteo;

		try {
			ResultSet rs = db.getDatos(sql);
			while (rs.next()) {
				
				mapNichos.put(rs.getString("CLAVE"), rs.getInt("PK1")) ;
				
				//System.out.println("     clave:"+rs.getString("CLAVE")+", pk:"+rs.getInt("PK1"));
			}
		} catch(SQLException ex) { }

		return mapNichos;
	}

	/*
	public ResultSet listar() {

		String sql = "SELECT * FROM COLABORADORES";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}
	//*/

	public int contar(String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - contar

		/*String sql = " SELECT C.PK1"
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1 ";*/
		
		
		
		String sql = " SELECT (SELECT SECTOR FROM SECTORES WHERE PK1= SC.PK_SECTOR) AS NOM_SECTOR ,(SELECT NICHO FROM NICHOS WHERE PK1=SC.PK_NICHO) AS NOM_NICHO ,"
				+ " C.CLAVE, CONCAT(NOMBRE,' ',APATERNO,' ',AMATERNO)  AS NOM_COLABORADOR ,"
				+ "PK_TALONARIO,NUMBOLETOS,MONTO,(SELECT ABONO FROM TALONARIOS WHERE PK1 = SC.PK_TALONARIO) AS ABONO,"
				+ "(SELECT VENDIDO FROM TALONARIOS WHERE PK1 = SC.PK_TALONARIO) AS ESTADO FROM SORTEOS_COLABORADORES_TALONARIOS SC, COLABORADORES C"
				+ " WHERE  SC.PK_COLABORADOR = C.PK1 ";	
		
	
		

		if (search != "") {
			if (isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'  ";
			} else {
				sql += " AND C.NOMBRE LIKE '%" + search + "%'  ";
			}
		}

		if (sector != 0) {

			sql += " AND SC.PK_SECTOR = '" + sector + "'  ";

		} else {

			setIdusuario(sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo(sesion.pkSorteo);
				getSectorUsuarioActual();

				sql += " AND SC.PK_SECTOR = '" + this.getSector() + "'  ";
			}
		}

		if (nicho != 0) {

			sql += " AND SC.PK_NICHO = '" + nicho + "'  ";

		}

		System.out.println("COLABORADORES  TAL COUNT: " + sql);

		int numero = db.ContarFilas(sql);
		return numero;

	}

	public ResultSet paginacion(int pg, int numreg, String search, int sector, int nicho, SesionDatos sesion) {
		// TODO - paginacion

		
		
		
		/*String sql = " SELECT C.*,CONCAT(C.NOMBRE,' ',C.APATERNO) AS 'NOM_COLABORADOR', N.NICHO AS 'NOM_NICHO', S.SECTOR AS 'NOM_SECTOR'"
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1";*/
		
		
		String sql = " SELECT (SELECT SECTOR FROM SECTORES WHERE PK1= SC.PK_SECTOR) AS NOM_SECTOR ,(SELECT NICHO FROM NICHOS WHERE PK1=SC.PK_NICHO) AS NOM_NICHO ,"
				+ " C.CLAVE, CONCAT(NOMBRE,' ',APATERNO,' ',AMATERNO)  AS NOM_COLABORADOR ,"
				+ "PK_TALONARIO,NUMBOLETOS,MONTO,(SELECT ABONO FROM TALONARIOS WHERE PK1 = SC.PK_TALONARIO) AS ABONO,"
				+ "(SELECT VENDIDO FROM TALONARIOS WHERE PK1 = SC.PK_TALONARIO) AS ESTADO FROM SORTEOS_COLABORADORES_TALONARIOS SC, COLABORADORES C"
				+ " WHERE  SC.PK_COLABORADOR = C.PK1 ";
		

		if (search != "") {
			if (isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'  ";
			} else {
				sql += " AND C.NOMBRE LIKE '%" + search + "%'  ";
			}
		}

		if (sector != 0) {

			sql += " AND SC.PK_SECTOR = '" + sector + "'  ";

		} else {

			setIdusuario((int) sesion.pkUsuario);
			if (isAdministrador()) {
			} else {
				setIdsorteo((int) sesion.pkSorteo);
				getSectorUsuarioActual();

				sql += " AND SC.PK_SECTOR = '" + this.getSector() + "'  ";
			}
		}

		if (nicho != 0) {

			sql += " AND SC.PK_NICHO = '" + nicho + "'  ";
		}
		
		System.out.println("-->"+sql);

		
		sql += " ORDER BY C.PK1 ASC ";
		if(!isExcel()){		
	
		sql += " OFFSET (" + (pg - 1) * numreg + ") ROWS "; 
															
															
		sql += " FETCH NEXT " + numreg + " ROWS ONLY";
		}

		System.out.println("COLABORADORES: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}

	public int registrar(mColaboradoresTalonarios   obj, SesionDatos sesion) {
		// TODO - registrar
		String sql
				= "INSERT INTO COLABORADORES (CLAVE,REFBANCARIA,DESCRIPCION,PK_NICHO,NOMBRE,APATERNO,AMATERNO,RFC,EDAD,SECTOR,COMISION,PK_SORTEO,PK_USUARIO) VALUES ('"
				+ obj.getClave() + "','"
				+ obj.getRbancaria() + "','"
				+ obj.getDescripcion().replaceAll("'", "''") + "','"
				+ obj.getNicho() + "','"
				+ obj.getNombre().replaceAll("'", "''") + "','"
				+ obj.getApellidop().replaceAll("'", "''") + "','"
				+ obj.getApellidom().replaceAll("'", "''") + "','"
				+ obj.getRfc() + "','"
				+ obj.getEdad() + "' ,'"
				+ obj.getSect() + "' ,'"
				+ obj.getComision() + "', '"
				+ sesion.pkSorteo + "', '"
				+ sesion.pkUsuario + "' )";
		
		//System.out.println(sql);
		int id = db.execQuerySelectId(sql);
		this.results[0] = id;

		obj.setId(id);

		// TELEFONOS
		this.results[1] = registrar_colaborador_Telefonos(obj.getId(), 1, obj.getTelefonocasa(), 'C', sesion.pkUsuario);
		this.results[2] = registrar_colaborador_Telefonos(obj.getId(), 2, obj.getTelefonooficina(), 'T', sesion.pkUsuario);
		this.results[3] = registrar_colaborador_Telefonos(obj.getId(), 3, obj.getTelefonomovil(), 'M', sesion.pkUsuario);

		// DIRECCION
		this.results[4] = registrar_colaborador_Direccion(obj, sesion.pkUsuario);

		// CORREOS
		this.results[5] = registrar_colaborador_Correo(obj.getId(), 1, obj.getCorreopersonal(), 'P', sesion.pkUsuario);
		this.results[6] = registrar_colaborador_Correo(obj.getId(), 2, obj.getCorreotrabajo(), 'T', sesion.pkUsuario);
		if (obj.getCorreootro().trim() != "")
			this.results[7] = registrar_colaborador_Correo(obj.getId(), 3, obj.getCorreootro(), 'O', sesion.pkUsuario);
		else
			this.results[7] = 1;

		// REDES SOCIALES
		/**/
		this.results[8] = 1;
		this.results[9] = 1;
		this.results[10] = 1;
		//*/
		if (obj.getFacebook().trim() != "") {
			this.results[8] = registrar_colaborador_RedSocial(obj.getId(), 1, obj.getFacebook(), sesion.pkUsuario);
		}
		if (obj.getTwitter().trim() != "") {
			this.results[9] = registrar_colaborador_RedSocial(obj.getId(), 2, obj.getTwitter(), sesion.pkUsuario);
		}
		if (obj.getRedotro().trim() != "") {
			this.results[10] = registrar_colaborador_RedSocial(obj.getId(), 3, obj.getRedotro(), sesion.pkUsuario);
		}

		init();
		for(int i=0;i<this.results.length; i++)
			if (this.results[i] == 0)
				return 0;
		return id;
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
		//System.out.println("Redes Sociales: "+sql);
		int res = db.execQuery(sql);
		return res;
	}

	public int registrar_colaborador_Direccion(mColaboradoresTalonarios  obj, int pkUsuario) {
		db.con();
		String sql = "INSERT INTO COLABORADORES_DIRECCION (PK_COLABORADOR,PAIS,CP,ESTADO,MUNDEL,COLONIA,CALLE,NUMEXT,NUMINT,PK_USUARIO) VALUES ('"
				+ obj.getId() + "','"
				+ obj.getPais() + "','"
				+ obj.getCp() + "','"
				+ obj.getEstado() + "','"
				+ obj.getMundel() + "','"
				+ obj.getColonia() + "','"
				+ obj.getCalle() + "','"
				+ obj.getNumExterior() + "','"
				+ obj.getNumInterior() + "','"
				+ pkUsuario + "')";
		//System.out.println(sql);
		int res = db.execQuery(sql);
		return res;
	}
	
	public int[] results= new int[11];

	public int Editar(mColaboradoresTalonarios  obj, SesionDatos sesion) {

		String sql = "UPDATE COLABORADORES SET "
				+ "CLAVE = '" + obj.getClave()
				+ "', REFBANCARIA = '" + obj.getRbancaria()
				+ "', SECTOR = '" + obj.getSect()
				+ "', DESCRIPCION = '" + obj.getDescripcion().replaceAll("'", "''")
				+ "', EDAD = '" + obj.getEdad()
				+ "', RFC = '" + obj.getRfc()
				+ "', NOMBRE = '" + obj.getNombre().replaceAll("'", "''")
				+ "', APATERNO =  '" + obj.getApellidop().replaceAll("'", "''")
				+ "', AMATERNO = '" + obj.getApellidom().replaceAll("'", "''")
				+ "', PK_NICHO = '" + obj.getNicho()
				+ "', FECHA_M = GETDATE()"
				+ "   WHERE PK1 = " + obj.getId();

		int res = db.execQuery(sql);
		
		this.results[0] = res;

		// TELEFONOS
		String sql2 = "DELETE FROM COLABORADORES_TELEFONOS WHERE PK_COLABORADOR = " + obj.getId();
		db.execQuery(sql2);
		this.results[1] = registrar_colaborador_Telefonos(obj.getId(), 1, obj.getTelefonocasa(), 'C', sesion.pkUsuario);
		this.results[2] = registrar_colaborador_Telefonos(obj.getId(), 2, obj.getTelefonooficina(), 'T', sesion.pkUsuario);
		this.results[3] = registrar_colaborador_Telefonos(obj.getId(), 3, obj.getTelefonomovil(), 'M', sesion.pkUsuario);

		// DIRECCION
		String sqln = "DELETE FROM COLABORADORES_DIRECCION WHERE PK_COLABORADOR = " + obj.getId();
		db.execQuery(sqln);
		this.results[4] = registrar_colaborador_Direccion(obj, sesion.pkUsuario);

		// CORREOS
		String sql3 = "DELETE FROM COLABORADORES_CORREOS WHERE PK_COLABORADOR = " + obj.getId();
		db.execQuery(sql3);
		this.results[5] = registrar_colaborador_Correo(obj.getId(), 1, obj.getCorreopersonal(), 'P', sesion.pkUsuario);
		this.results[6] = registrar_colaborador_Correo(obj.getId(), 2, obj.getCorreotrabajo(), 'T', sesion.pkUsuario);
		if (obj.getCorreootro().trim() != "" && obj.getCorreootro().trim() != null) {
			this.results[7] = registrar_colaborador_Correo(obj.getId(), 3, obj.getCorreootro(), 'O', sesion.pkUsuario);
		}
		else
			this.results[7] = 1; // OK

		// REDES SOCIALES
		String sql4 = "DELETE FROM COLABORADORES_REDES_SOCIALES WHERE PK_COLABORADOR = " + obj.getId();
		db.execQuery(sql4);
		this.results[8]=1;
		this.results[9]=1;
		this.results[10]=1;
		if (obj.getFacebook() != null && obj.getFacebook().trim() != "") {
			//System.out.println("facebook: "+ obj.getFacebook());
			this.results[8] = registrar_colaborador_RedSocial(obj.getId(), 1, obj.getFacebook(), sesion.pkUsuario);
		}
		if (obj.getTwitter() != null && obj.getTwitter().trim() != "") {
			this.results[9] = registrar_colaborador_RedSocial(obj.getId(), 2, obj.getTwitter(), sesion.pkUsuario);
		}
		if (obj.getRedotro() != null && obj.getRedotro().trim() != "") {
			this.results[10] = registrar_colaborador_RedSocial(obj.getId(), 3, obj.getRedotro(), sesion.pkUsuario);
		}

		init();
		for(int i=0;i<this.results.length; i++)
			if (this.results[i] == 0)
				return 0;
		return res;
	}

	public void BuscarEditar(mColaboradoresTalonarios  obj) {

		String sql = "SELECT * FROM COLABORADORES WHERE PK1 = " + obj.getId();
		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				obj.setId(rs.getInt("PK1"));
				obj.setClave(rs.getString("CLAVE"));
				obj.setNombre(rs.getString("NOMBRE"));
				obj.setApellidop(rs.getString("APATERNO"));
				obj.setApellidom(rs.getString("AMATERNO"));
				obj.setRfc(rs.getString("RFC"));
				obj.setEdad(rs.getString("EDAD"));
				obj.setSect(rs.getString("SECTOR").charAt(0));
				obj.setDescripcion(rs.getString("DESCRIPCION"));
				obj.setNicho(rs.getInt("PK_NICHO"));
				obj.setRbancaria(rs.getString("REFBANCARIA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int consultaIdColaborador() {

		String sql = "SELECT TOP 1 PK1 FROM COLABORADORES"
				+ " WHERE PK_SORTEO="+this.getIdsorteo()
				+ " AND PK_NICHO = " + this.getNicho()
				+ " AND CLAVE = '" + this.getClave()+"'"
				;

		try {
			ResultSet rs = db.getDatos(sql);
			if (rs.next()) {
				int id = rs.getInt("PK1");
				rs.close();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void ObtenerTelefono(mColaboradoresTalonarios  u) {

		String sql = "SELECT * FROM COLABORADORES_TELEFONOS WHERE PK_COLABORADOR = " + u.getId();

		ResultSet rs = db.getDatos(sql);

		try {

			while (rs.next()) {

				if (rs.getString("TIPO").equals("C")) {

					u.setTelefonocasa(rs.getString("TELEFONO"));

				} else if (rs.getString("TIPO").equals("T")) {

					u.setTelefonooficina(rs.getString("TELEFONO"));

				} else {

					u.setTelefonomovil(rs.getString("TELEFONO"));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ObtenerDireccion(mColaboradoresTalonarios  u) {

		String sql = "SELECT * FROM COLABORADORES_DIRECCION WHERE PK_COLABORADOR = '"
				+ u.getId() + "' ";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {

				u.setCp(Integer.valueOf(rs.getString("CP")));
				u.setPais(rs.getString("PAIS"));
				u.setEstado(rs.getString("ESTADO"));
				u.setMundel(rs.getString("MUNDEL"));
				u.setColonia(rs.getString("COLONIA"));
				u.setCalle(rs.getString("CALLE"));
				u.setNumExterior(rs.getString("NUMEXT"));
				u.setNumInterior(rs.getString("NUMINT"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ObtenerCorreo(mColaboradoresTalonarios  u) {

		String sql = "SELECT * FROM COLABORADORES_CORREOS WHERE PK_COLABORADOR = "+ u.getId();

		ResultSet rs = db.getDatos(sql);
		try {

			while (rs.next()) {

				if (rs.getString("TIPO").equals("P")) {

					u.setCorreopersonal(rs.getString("CORREO"));

				} else if (rs.getString("TIPO").equals("T")) {

					u.setCorreotrabajo(rs.getString("CORREO"));

				} else {

					u.setCorreootro(rs.getString("CORREO"));

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ObtenerRedes(mColaboradoresTalonarios  u) {

		String sql = "SELECT * FROM COLABORADORES_REDES_SOCIALES WHERE PK_COLABORADOR = " + u.getId();

		ResultSet rs = db.getDatos(sql);

		try {

			while (rs.next()) {

				if (rs.getInt("SECUENCIA") == 1) {

					u.setFacebook(rs.getString("RED"));

				} else if (rs.getInt("SECUENCIA") == 2) {

					u.setTwitter(rs.getString("RED"));

				} else {

					u.setRedotro(rs.getString("RED"));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int Borrar(mColaboradoresTalonarios  obj) {

		int res = 0;//existe
		
		String sql = "SELECT C.PK1 FROM SORTEOS_ASIGNACION_COLABORADORES SAC, COLABORADORES C"
				    +" WHERE SAC.PK_COLABORADOR = C.PK1  AND C.PK1 = " + obj.getId();

		ResultSet rs = db.getDatos(sql);
		// System.out.println("SORTEOS_ASIGNACION_COLABORADORES: "+sql);

		try {
			if (!rs.next()) {

				sql = "DELETE FROM COLABORADORES WHERE PK1=" + obj.getId();
				db.execQuery(sql);
				res = 1;// no existe
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;

	}

	public void actualizarComision(mColaboradoresTalonarios  obj) {

		db.con();
		String sql = "";

		sql = "UPDATE COLABORADORES SET COMISION = " + obj.getComision() + ", FECHA_M = GETDATE()"
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
	
	
	 public void BorrarColaboradores(String[] colaboradores, SesionDatos sesion) {
			
			db.con();
			String sql ="";
			ResultSet rs=null;

			for (String ID : colaboradores) {
				
				 sql = "SELECT C.PK1 FROM SORTEOS_ASIGNACION_COLABORADORES SAC, COLABORADORES C"
					    +" WHERE SAC.PK_COLABORADOR = C.PK1  AND C.PK1 = " + ID;

				 rs = db.getDatos(sql);
				//System.out.println("SORTEOS_ASIGNACION_COLABORADORES: "+sql);		
			
			
				try {
					if(!rs.next()){	
					
					
						sql = "DELETE FROM COLABORADORES WHERE PK_SORTEO =" + this.getIdsorteo() + " AND PK1 = "+ID;
						db.execQuery(sql);					
						
						this.Log(sesion, LOG.REGISTRO, this, "COLABORADOR ELIMINADO", sesion.toShortString());
						
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		}
	 
	 
	 
	public boolean ExisteClaveColaborador(mColaboradoresTalonarios  obj){
		
		String sql = "SELECT PK1 FROM COLABORADORES WHERE CLAVE = '"+ obj.getClave()+"'";
		ResultSet rs = db.getDatos(sql);
		// System.out.println("sql: "+ sql);
		// return rs;

		try {
			if (rs.next()) {
				System.out.println("existe");

				return true;

			} else {

				System.out.println("no existe");
				return false;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	 
	 

}
