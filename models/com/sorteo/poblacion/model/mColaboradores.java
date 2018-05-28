package com.sorteo.poblacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.core.Global;
import com.core.Parametros;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mColaboradores extends SuperModel {
	
	private int id;
	
	private String clave;
	
	private int referencia;
	private String numReferencia;

	private String nombre;
	private String apellidop;
	private String apellidom;
	private double comision;
	private String rfc;
	private int edad;
	
	private String correo_p;
	private String correo_s;
	private String telefono_p;
	private String telefono_s;
	
	private String cp;
	private String estado;
	private String mundel;
	private String colonia;	
	private String calle;
	private String numExterior;
	private String numInterior;
	
	private int idSorteo;
	private int idSector;
	private int idNicho;
	
	private int idUsuario;
	private String usuario;
	
	
	//ArrayList<Integer> listNichos;
	public String str_asignaciones;
	
	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
	}

	public void setIdSorteo(String idsorteo) {
		this.idSorteo = Integer.valueOf(idsorteo);
	}
	
	public double getComision() {
		return comision;
	}
	
	public int setComision(String comision) {
		try{
			this.comision = Double.valueOf(comision);
			return 0;
		}catch(Exception ex){
			this.comision = 0;
			return -1;
		}
	}
	
	public int setComision(double comision) {
		this.comision = comision;
		if (this.comision == Double.NaN) {
			this.comision = 0;
			return -1;
		}
		return 0;
	}
	
	public void init() {
	
		 this.id = 0;
		 this.clave = "";
		 this.edad = 0;  
		 this.rfc = "";
		 
		 this.nombre = "";
		 this.apellidop = "";

		 this.setCorreoP("");
		 this.setCorreoS("");
		 this.telefono_p = "";
		 this.telefono_s = "";
		 
		 this.cp = "";
		 this.estado = "";
		 this.mundel = "";
		 this.colonia = "";
		 this.calle = "";
		 this.setNumExterior("");
		 this.setNumInterior("");
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
		this.clave = clave.trim();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = Global.toFrase(nombre);
	}

	public String getApellidop() {
		return apellidop;
	}

	public void setApellidop(String apellido) {
		this.apellidop = Global.toFrase(apellido);
	}

	public String getApellidom() {
		return apellidom;
	}

	public void setApellidom(String apellido) {
		this.apellidom = Global.toFrase(apellido);
	}

	public int getEdad() {
		return edad;
	}

	public int setEdad(String edad) {
		try {
			this.edad = Integer.parseInt(edad);
			if (this.edad > 0)
				return 0;
		} catch (Exception ex) { }
		this.edad = 0;
		return -1;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc.trim();
	}

	public String getTelefonoP() {
		return telefono_p;
	}

	public void setTelefonoP(String telefono) {
		this.telefono_p = (telefono == null) ? "" : telefono.trim();
	}

	public String getTelefonoS() {
		return telefono_s;
	}

	public void setTelefonoS(String telefono) {
		this.telefono_s = (telefono == null) ? "" : telefono.trim();
	}
	
	public String getCP() {
		return cp;
	}

	public void setCP(String cp) {
		cp = cp.replaceAll("'", "").trim();
		if (cp.length() > 5)
			cp = cp.substring(0, 5);
		else
			while (cp.length() < 5)
				cp = "0" + cp;
		this.cp = cp;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado.trim();
	}

	public String getMundel() {
		return mundel;
	}

	public void setMundel(String mundel) {
		this.mundel = mundel.trim();
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia.trim();
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle.trim();
		if(this.calle != null && this.calle.length()>50)
			this.calle = this.calle.substring(0, 50);
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		numExterior = numExterior.trim();
		if (numExterior.compareTo("0") == 0)
			numExterior = "";
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getCorreoP() {
		return correo_p;
	}

	public void setCorreoP(String correo) {
		this.correo_p = (correo == null) ? "" : correo.trim();
	}

	public String getCorreoS() {
		return correo_s;
	}

	public void setCorreoS(String correo) {
		this.correo_s = (correo == null) ? "" : correo.trim();
	}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int sector) {
		this.idSector = sector;
	}

	public void setIdSector(String sector) {
		try {
			this.idSector = Integer.parseInt(sector);
		} catch (Exception ex) { this.idSector = 0; }
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int nicho) {
		this.idNicho = nicho;
	}

	public void setIdNicho(String nicho) {
		try {
			this.idNicho = Integer.valueOf(nicho);
		} catch (Exception ex) { this.idNicho = 0; }
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		try {
			this.idUsuario = Integer.valueOf(idUsuario);
		} catch (Exception ex) { this.idUsuario = 0; }
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getReferencia() {
		return referencia;
	}

	public void setReferencia(int referencia) {
		this.referencia = referencia;
	}

	public String getNumReferencia() {
		return numReferencia;
	}

	public void setNumReferencia(String numReferencia) {
		this.numReferencia = numReferencia == null ? "" : numReferencia;
	}

	public mColaboradores() {
		init();
	}
	
	public void obtenerDireccionSEPOMEX(){

		String sql = "SELECT PAIS,d_estado,D_mnpio FROM SEPOMEX WHERE (d_codigo LIKE N'%" + getCP() + "%') GROUP BY PAIS,d_estado,D_mnpio";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				setEstado(rs.getString("d_estado"));
				setMundel(rs.getString("D_mnpio"));
				setColonia(this.Obtener_Colonias_HTML());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String Obtener_Colonias_HTML() {
		
		if ((getCP().trim()).compareTo("") == 0)
			return "";

		String sql = "SELECT d_asenta FROM SEPOMEX WHERE (d_codigo LIKE N'%" + this.getCP() + "%') GROUP BY d_asenta";
		ResultSet rs = db.getDatos(sql);
		StringBuilder sb = new StringBuilder();

		try {
			boolean first = true;
			
			while (rs.next()) {
				sb.append("<option ").append((first ? "selected" : "")).append(">")
					.append(rs.getString("d_asenta")).append("</option>");
				first = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	public void obtenerDireccionSEPOMEX(String colonia) {

		setEstado("");
		setMundel("");
		setColonia("");

		//String sql = "SELECT PAIS,d_estado,D_mnpio FROM SEPOMEX WHERE (d_codigo LIKE N'%" + getCP() + "%') GROUP BY PAIS,d_estado,D_mnpio";
		String sql = "SELECT PAIS,d_estado,D_mnpio FROM SEPOMEX WHERE (d_codigo = N'" + getCP() + "') GROUP BY PAIS,d_estado,D_mnpio";

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				setEstado(rs.getString("d_estado"));
				setMundel(rs.getString("D_mnpio"));
				setColonia(this.Obtener_Colonia(colonia));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String Obtener_Colonia(String coloniaDefault) {
		
		if ((getCP().trim()).compareTo("") == 0)
			return "";
		
		if (coloniaDefault!=null && coloniaDefault.equals("") == false) {
			String sql = "SELECT TOP 1 d_asenta AS 'VALUE' FROM SEPOMEX WHERE (d_codigo = N'" + this.getCP() + "' AND d_asenta LIKE '%" + coloniaDefault + "%')";
			
			coloniaDefault = db.getValue(sql, coloniaDefault);
		}
		return coloniaDefault;
	}

	public ResultSet getSectores() {

		String sql = "SELECT * FROM SECTORES WHERE PK_SORTEO=" + getIdSorteo();

		return db.getDatos(sql);
	}
	
	public boolean esResposableDeSector(){
		String sql = "SELECT COUNT(*) AS 'MAX'"
				+ " FROM SORTEOS_USUARIOS"
				+ " WHERE PK_SORTEO="+getIdSorteo()+" AND PK_SECTOR IS NULL AND PK_NICHO IS NULL AND PK_USUARIO=" + getIdUsuario();
		return db.Count(sql) > 0;
	}

	public ResultSet getSectoresUsuario()
	{
		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ this.getIdSector() +"' ";

		return db.getDatos(sql);
	}

	public ResultSet getSectoresUsuario(mColaboradores obj) {

		String sql = "SELECT * FROM SECTORES WHERE PK1 = '"+ obj.getIdSector() +"' ";
		System.out.println("getSectoresUsuario:"+sql);
		ResultSet rs = db.getDatos(sql);
		return rs;
	}


	/*
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
	*/

	/*
	public void getSectorNicho(mColaboradores obj) {

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
	*/

	public ResultSet getNichos() {

		String sql = "SELECT * FROM NICHOS WHERE PK_SECTOR=" + getIdSector();
		if (getIdNicho() != 0) {
			sql +=  " AND PK1="+getIdNicho(); 
		}

		return db.getDatos(sql);
	}

	/*
	public ResultSet getNichosModal() {


		System.out.println("Nichos modal: "+sql);
		return db.getDatos(sql);
	}
	*/
	public ArrayList<mNichos> getNichosPorSector()
	{
		ArrayList<mNichos> list = new ArrayList<mNichos>();

		/*
		String sql = "SELECT S.PK_SORTEO,N.*"
				+ ",(SELECT COUNT(*) FROM SORTEOS_ASIGNACION_COLABORADORES SAC WHERE SAC.PK_NICHO=N.PK1 AND SAC.PK_SORTEO=" + getIdSorteo() + " AND SAC.PK_SECTOR=" + getIdSector() + " AND SAC.PK_COLABORADOR=" + getId() + ") AS 'CHECK'"
				+ ",(SELECT COUNT(*) FROM SORTEOS_COLABORADORES_BOLETOS    SCB WHERE SCB.PK_NICHO=N.PK1 AND SCB.PK_SORTEO=" + getIdSorteo() + " AND SCB.PK_SECTOR=" + getIdSector() + " AND SCB.PK_COLABORADOR=" + getId() + ") AS 'BOLETOS'"
				+ " FROM NICHOS N WHERE PK_SORTEO=" + getIdSorteo() + " AND PK_SECTOR=" + getIdSector();
		*/
		String sql = "SELECT S.PK_SORTEO,N.*"
				+ ",(SELECT COUNT(*) FROM COLABORADORES_ASIGNACION SAC WHERE SAC.PK_NICHO=N.PK1 AND SAC.PK_SECTOR=" + getIdSector() + " AND SAC.PK_COLABORADOR=" + getId() + ") AS 'CHECK'"
				+ ",(SELECT COUNT(*) FROM COLABORADORES_BOLETOS CB, NICHOS_BOLETOS NB, SECTORES_BOLETOS SB"
				+ " WHERE CB.PK_BOLETO=NB.PK_BOLETO AND CB.PK_BOLETO=SB.PK_BOLETO AND SB.PK_SECTOR=" + getIdSector() + " AND NB.PK_NICHO=N.PK1 AND CB.PK_COLABORADOR=" + getId() + ") AS 'BOLETOS'"
				+ " FROM NICHOS N, SECTORES S WHERE N.PK_SECTOR=S.PK1 AND S.PK_SORTEO=" + getIdSorteo() + " AND N.PK_SECTOR=" + getIdSector();
		
		System.out.println("Nichos modal:" + sql);
		
		ResultSet res = db.getDatos(sql);
		try{
			while (res.next()) {
				mNichos nicho = new mNichos();
				nicho.setId(res.getInt("PK1"));
				nicho.setClave(res.getString("CLAVE"));
				nicho.setNicho(res.getString("NICHO"));
				nicho.setIdSector(res.getInt("PK_SECTOR"));
				nicho.setDato1(res.getString("CHECK"));
				nicho.setDato2(res.getString("BOLETOS"));
				list.add(nicho);
			}
			res.close();
		} catch (Exception ex) { }
		
		return list;
	}
	
	public boolean existenMasAsignaciones()
	{
		String sql = "SELECT COUNT(*) AS 'VALUE' FROM COLABORADORES_ASIGNACION WHERE"
				+ " PK_COLABORADOR=" + getId()
				+ " AND PK_SECTOR <> " + getIdSector();
		
		System.out.println("Nichos de otros Sectores:" + sql);
		
		int count = db.getValue(sql, 0);
		
		return count > 0;
	}

	/*
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
	//*/

	/*
	public ResultSet listar() {

		String sql = "SELECT * FROM COLABORADORES";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}
	//*/


	/*
	// TODO - contar
	public int contar(String search, SesionDatos sesion) {

		String sql = "SELECT COUNT(C.PK1) AS 'MAX'"
				+ " FROM COLABORADORES C, COLABORADORES_ASIGNACION CA, SECTORES S, NICHOS N"
				+ " WHERE S.PK_SORTEO=" + getIdSorteo() + " AND CA.PK_COLABORADOR=C.PK1 AND CA.PK_SECTOR=S.PK1 AND CA.PK_NICHO=N.PK1";
		
		if (getIdSector() != 0) {
			sql += " AND CA.PK_SECTOR = " + getIdSector();
		} else {
			if (sesion.permisos.esAdministrador() == false) {
				consultaPoblacionUsuarioActual();

				sql += " AND CA.PK_SECTOR = " + getIdSector();
			}
		}

		if (getIdNicho() != 0) {
			sql += " AND CA.PK_NICHO = " + getIdNicho();
		}

		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'";
			} else if (search.indexOf('@') >= 0) {
				sql += " AND (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				sql = sql
				+ " AND (C.NOMBRE LIKE '%" + search + "%'" 
				+ " OR C.APATERNO LIKE '%" + search + "%'"
				+ " OR C.AMATERNO LIKE '%" + search + "%'"
				+ ")";
			}
		}
		return db.Count(sql);
	}
	*/
	public int contar(String search, SesionDatos sesion) {
		String condicion = "";
		
		if (getIdSector() != 0) {
			condicion += " AND CA.PK_SECTOR = " + getIdSector();
		} else {
			if (sesion.permisos.esAdministrador() == false) {
				consultaPoblacionUsuarioActual();

				condicion += " AND CA.PK_SECTOR = " + getIdSector();
			}
		}
		if (getIdNicho() != 0) {
			condicion += " AND CA.PK_NICHO = " + getIdNicho();
		}
		
		String condicion_2 = "";
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion_2 = " WHERE (C.CLAVE LIKE '%" + search + "%')";
			} else if (search.indexOf("E-")==0) {
				condicion_2 = " WHERE (C.CLAVE LIKE '" + search + "%')";
			} else if (search.indexOf('@') >= 0) {
				condicion_2 = " WHERE (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				condicion_2
				= " WHERE (C.NOMBRE LIKE '%" + search + "%'" 
				+ " OR C.APATERNO LIKE '%" + search + "%'"
				+ " OR C.AMATERNO LIKE '%" + search + "%'"
				+ " OR C.NOM_COLABORADOR LIKE '%" + search + "%'"
				+ ")";
			}
		}
		
		String sql
			= "SELECT COUNT(*) AS 'MAX'\n"
			+ "FROM(\n"
			+ "  SELECT C.CLAVE,C.COMISION,C.CORREO_P,C.CORREO_S, C.NOMBRE, C.APATERNO, C.AMATERNO, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS 'NOM_COLABORADOR', S.SECTOR,N.NICHO\n"
			+ "  FROM COLABORADORES C \n"
			+ "  LEFT JOIN COLABORADORES_ASIGNACION CA ON CA.PK_COLABORADOR=C.PK1 \n"
			+ "  LEFT JOIN SECTORES S ON S.PK1=CA.PK_SECTOR \n"
			+ "  LEFT JOIN NICHOS   N ON N.PK1=CA.PK_NICHO \n"
			+ "  WHERE 1=1 " + condicion + " \n"
			+ ") AS C \n" + condicion_2 + " \n";
		
		return db.Count(sql);
	}
	
	// TODO - paginacion
	public ResultSet paginacion(int pg, int numreg, String search, SesionDatos sesion) {
		
		String condicion = "";
		
		if (getIdSector() != 0) {
			condicion += " AND CA.PK_SECTOR = " + getIdSector();
		} else {
			if (sesion.permisos.esAdministrador() == false) {
				consultaPoblacionUsuarioActual();

				condicion += " AND CA.PK_SECTOR = " + getIdSector();
			}
		}
		if (getIdNicho() != 0) {
			condicion += " AND CA.PK_NICHO = " + getIdNicho();
		}
		
		String condicion_2 = "";
		if (!search.equals("")) {
			if (Global.isNumeric(search)) {
				condicion_2 = " WHERE (C.CLAVE LIKE '%" + search + "%')";
			} else if (search.indexOf("E-")==0) {
				condicion_2 = " WHERE (C.CLAVE LIKE '" + search + "%')";
			} else if (search.indexOf('@') >= 0) {
				condicion_2 = " WHERE (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				condicion_2
				= " WHERE (C.NOMBRE LIKE '%" + search + "%'" 
				+ " OR C.APATERNO LIKE '%" + search + "%'"
				+ " OR C.AMATERNO LIKE '%" + search + "%'"
				+ " OR C.NOM_COLABORADOR LIKE '%" + search + "%'"
				+ ")";
			}
		}
		
		String sql
			= "SELECT *\n"
			+ "FROM(\n"
			+ "  SELECT C.PK1,C.CLAVE,C.COMISION,C.CORREO_P,C.CORREO_S, C.NOMBRE, C.APATERNO, C.AMATERNO, CONCAT(C.NOMBRE,' ',C.APATERNO,' ',C.AMATERNO) AS 'NOM_COLABORADOR', S.SECTOR,N.NICHO, C.REFERENCIA, CR.REFERENCIA AS 'NUM_REFERENCIA'\n"
			+ "  FROM COLABORADORES C \n"
			+ "  LEFT JOIN COLABORADORES_ASIGNACION CA  ON CA.PK_COLABORADOR=C.PK1 \n"
			+ "  LEFT JOIN SECTORES S                   ON S.PK1=CA.PK_SECTOR \n"
			+ "  LEFT JOIN NICHOS   N                   ON N.PK1=CA.PK_NICHO \n"
			+ "  LEFT JOIN COLABORADORES_REFERENCIAS CR ON CR.PK_COLABORADOR=C.PK1 \n"
			+ "  WHERE 1=1 " + condicion + " \n"
			+ ") AS C \n" + condicion_2 + " \n"
			+ "ORDER BY C.CLAVE, NOM_COLABORADOR, C.SECTOR, C.NICHO \n"
			+ "OFFSET (" + (pg - 1) * numreg + ") ROWS FETCH NEXT " + numreg + " ROWS ONLY";

		System.out.println("COLABORADORES: \n" + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	// TODO - paginacion
	public ResultSet consultaEXCEL(int pg, int numreg, String search, SesionDatos sesion) {

		/*
		String sql = "SELECT C.*,CD.*,S.SECTOR,N.NICHO"
				+ " FROM COLABORADORES C, COLABORADORES_ASIGNACION SAC, COLABORADORES_DIRECCION CD, SECTORES S, NICHOS N"
				+ " WHERE SAC.PK_COLABORADOR=C.PK1 AND CD.PK_COLABORADOR=C.PK1 AND SAC.PK_SECTOR=S.PK1 AND SAC.PK_NICHO=N.PK1";
		*/
		String sql = "SELECT C.*,CD.*,S.SECTOR,N.NICHO,CR.REFERENCIA AS 'NUM_REFERENCIA'"
				+ " FROM COLABORADORES C"
				+ " INNER JOIN COLABORADORES_ASIGNACION SAC ON SAC.PK_COLABORADOR=C.PK1"
				+ " INNER JOIN COLABORADORES_DIRECCION CD ON CD.PK_COLABORADOR=C.PK1"
				+ " INNER JOIN SECTORES S ON S.PK1=SAC.PK_SECTOR"
				+ " INNER JOIN NICHOS N ON N.PK1=SAC.PK_NICHO"
				+ " LEFT JOIN COLABORADORES_REFERENCIAS CR ON CR.PK_COLABORADOR=C.PK1";

		if (getIdSector() != 0) {
			sql += " AND SAC.PK_SECTOR = " + getIdSector();
		} else {
			if (sesion.permisos.esAdministrador() == false) {
				//setIdSorteo(sesion.pkSorteo);
				consultaPoblacionUsuarioActual();

				sql += " AND SAC.PK_SECTOR = " + getIdSector();
			}
		}

		if (getIdNicho() != 0) {
			sql += " AND SAC.PK_NICHO = " + getIdNicho();
		}

		if (search != "") {
			if (Global.isNumeric(search)) {
				sql += " AND C.CLAVE LIKE '%" + search + "%'";
			} else if (search.indexOf('@') >= 0) {
				sql += " AND (C.CORREO_P LIKE '%" + search + "%' OR C.CORREO_S LIKE '%" + search + "%')";
			} else {
				sql = sql
				+ " AND (C.NOMBRE LIKE '%" + search + "%'" 
				+ " OR C.APATERNO LIKE '%" + search + "%'"
				+ " OR C.AMATERNO LIKE '%" + search + "%'"
				+ ")";
			}
		}

		sql += " ORDER BY C.CLAVE, C.NOMBRE, S.SECTOR, N.NICHO";/*
			+ " OFFSET (" + (pg - 1) * numreg + ") ROWS "
			+ " FETCH NEXT " + numreg + " ROWS ONLY";*/

		System.out.println("EXPORT: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	
	public RESULT validarClaveYGuardar() {
		for (int intentos=0; intentos<10; intentos++)
		{
			int idColaborador = this.consultaIdColaboradorXClave();
			if (idColaborador == -1){
				return insertar();
			}
			else {
				if (getClave().charAt(0) == 'E')
					setClave(nuevaClave());
				else
					break;
			}
		}
		super._mensaje = "Error: El colaborador ya existe.";
		return RESULT.ERROR;
	}
	/*
	private boolean existeClave() {
		String sql = "SELECT COUNT(PK1) AS 'VALUE' FROM COLABORADORES"
				+ " WHERE PK_SORTEO=" + getIdsorteo()
				+ " AND CLAVE='" + getClave() + "'";
		
		return db.getValue(sql, 0) > 0;
	}
	*/
	public RESULT insertar() {
		// TODO - insertar
		List<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1", getId()));
		list.add(new Parametros("CLAVE", getClave()));
		list.add(new Parametros("REFERENCIA", getReferencia()));
		list.add(new Parametros("NUM_REFERENCIA", getNumReferencia()));
		list.add(new Parametros("NOMBRE", getNombre()));
		list.add(new Parametros("APATERNO", getApellidop()));
		list.add(new Parametros("AMATERNO", getApellidom()));
		list.add(new Parametros("COMISION", "9.090909090909091"));
		list.add(new Parametros("RFC", getRfc()));
		list.add(new Parametros("EDAD", getEdad()));
		list.add(new Parametros("CORREO_P", getCorreoP()));
		list.add(new Parametros("CORREO_S", getCorreoS()));
		list.add(new Parametros("TELEFONO_P", getTelefonoP()));
		list.add(new Parametros("TELEFONO_S", getTelefonoS()));
		list.add(new Parametros("USUARIO", getUsuario()));
		
		list.add(new Parametros("LIST", ""));
		list.add(new Parametros("StatementType", "Insert"));

		this.setId(db.execStoreProcedureIntId("spCOLABORADORES", list));

		if (getId() <= 0) {
			super._mensaje = "El colaborador no se ha guardado. ";
			return RESULT.ERROR;
		}
		
		return insertarDireccion();
		
		/*
		String sql
				= "INSERT INTO COLABORADORES (CLAVE,NOMBRE,APATERNO,AMATERNO,COMISION,RFC,EDAD,CORREO_P,CORREO_S,TELEFONO_P,TELEFONO_S,PK_SORTEO,ABONO,USUARIO,FECHA_R) VALUES ("
				+ "'" + this.getClave() + "'"
				+ ",'" + this.getNombre().replaceAll("'", "''") + "'"
				+ ",'" + this.getApellidop().replaceAll("'", "''") + "'"
				+ ",'" + this.getApellidom().replaceAll("'", "''") + "'"
				+ ",9.090909090909091"
				+ ",'" + this.getRfc() + "'"
				+ "," + this.getEdad() + ""
				
				+ ",'" + this.getCorreoP() + "'"
				+ ",'" + this.getCorreoS() + "'"
				+ ",'" + this.getTelefonoP() + "'"
				+ ",'" + this.getTelefonoS() + "'"
				
				+ "," + sesion.pkSorteo + ""
				+ ", 0.0"
				+ ",'" + sesion.nickName + "'"
				+ ",GETDATE() )";
		
		//System.out.println(sql);
		int id = db.execQuerySelectId(sql);

		if (id <= 0) {
			super._mensaje = "El colaborador no se ha registrado";
			return RESULT.ERROR;
		}
		
		// Verificar que solamente existe un colaborador con esa clave,
		sql = "SELECT COUNT(*) AS 'MAX' FROM COLABORADORES WHERE CLAVE='" + this.getClave() + "'";
		int count = db.Count(sql);
		if (count>1) {
			super._mensaje = "Ya existe un colaborador con la clave '" + getClave() + "'";
			return RESULT.ERROR;
		}

		this.setId(id);

		// DIRECCION
		int result = registrar_colaborador_Direccion(sesion);
		if (result <= 0) {
			super._mensaje = "La direccion no se ha registrado";
			return RESULT.ERROR;
		}
		super._mensaje = "";
		return RESULT.OK;
		*/
	}

	/*
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
	*/

	public RESULT insertarDireccion() {
		// TODO - registrar_colaborador_Direccion
		List<Parametros> list = new ArrayList<Parametros>();

		list.add(new Parametros("PK1", "0"));
		list.add(new Parametros("PK_COLABORADOR", getId()));
		list.add(new Parametros("CP", getCP()));
		list.add(new Parametros("ESTADO", getEstado()));
		list.add(new Parametros("MUNDEL", getMundel()));
		list.add(new Parametros("COLONIA", getColonia()));
		list.add(new Parametros("CALLE", getCalle()));
		list.add(new Parametros("NUMEXT", getNumExterior()));
		list.add(new Parametros("NUMINT", getNumInterior()));
		list.add(new Parametros("USUARIO", getUsuario()));
		list.add(new Parametros("StatementType", "Insert"));

		boolean result = db.execStoreProcedure("spCOLABORADORES_DIRECCION", list);
		System.out.println("address result:"+result);

		if (result == false) {
			super._mensaje = "La direccion no se ha guardado. ";
			return RESULT.ERROR;
		}
		super._mensaje = "";
		return RESULT.OK;

		/*
		String sql = "INSERT INTO COLABORADORES_DIRECCION (PK_COLABORADOR,CP,ESTADO,MUNDEL,COLONIA,CALLE,NUMEXT,NUMINT,USUARIO) VALUES ('"
				+ getId() + "','"
				+ getCP() + "','"
				+ getEstado() + "','"
				+ getMundel() + "','"
				+ getColonia() + "','"
				+ getCalle() + "','"
				+ getNumExterior() + "','"
				+ getNumInterior() + "','"
				+ getUsuario() + "')";
		
		return db.execQuery(sql);
		*/
	}

	// TODO actualizar
	public RESULT actualizar()
	{
		ArrayList<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1", getId()));
		list.add(new Parametros("CLAVE", getClave()));
		list.add(new Parametros("REFERENCIA", getReferencia()));
		list.add(new Parametros("NUM_REFERENCIA", getNumReferencia()));
		list.add(new Parametros("NOMBRE", getNombre()));
		list.add(new Parametros("APATERNO", getApellidop()));
		list.add(new Parametros("AMATERNO", getApellidom()));
		list.add(new Parametros("COMISION", getComision()));
		list.add(new Parametros("RFC", getRfc()));
		list.add(new Parametros("EDAD", getEdad()));
		list.add(new Parametros("CORREO_P", getCorreoP()));
		list.add(new Parametros("CORREO_S", getCorreoS()));
		list.add(new Parametros("TELEFONO_P", getTelefonoP()));
		list.add(new Parametros("TELEFONO_S", getTelefonoS()));
		list.add(new Parametros("USUARIO", getUsuario()));

		list.add(new Parametros("LIST", ""));
		list.add(new Parametros("StatementType", "Update"));
		
		// Return = { PK : On success,  -1 : On error }
		int result = db.execStoreProcedureIntId("spCOLABORADORES", list);
		
		if (result == -1) {
			super._mensaje = "El colaborador no se ha actualizado. ";
			return RESULT.ERROR;
		}
		
		return actualizarDireccion();

		//return result == 1 ? RESULT.OK : RESULT.ERROR;
	}

	public RESULT actualizarDireccion() {
		// TODO - registrar_colaborador_Direccion
		List<Parametros> list = new ArrayList<Parametros>();

		list.add(new Parametros("PK1", "0"));
		list.add(new Parametros("PK_COLABORADOR", getId()));
		list.add(new Parametros("CP", getCP()));
		list.add(new Parametros("ESTADO", getEstado()));
		list.add(new Parametros("MUNDEL", getMundel()));
		list.add(new Parametros("COLONIA", getColonia()));
		list.add(new Parametros("CALLE", getCalle()));
		list.add(new Parametros("NUMEXT", getNumExterior()));
		list.add(new Parametros("NUMINT", getNumInterior()));
		list.add(new Parametros("USUARIO", getUsuario()));
		list.add(new Parametros("StatementType", "Update"));

		boolean result = db.execStoreProcedure("spCOLABORADORES_DIRECCION", list);
		System.out.println("address result:"+result);

		if (result == false) {
			super._mensaje = "La direccion no se ha guardado. ";
			return RESULT.ERROR;
		}
		super._mensaje = "";
		return RESULT.OK;
	}
	/*
	public RESULT actualizar() {
		String sql = "UPDATE COLABORADORES SET "
				+ "CLAVE = '" + getClave() + "'"
				+ ", NOMBRE = '" + getNombre().replaceAll("'", "''") + "'"
				+ ", APATERNO = '" + getApellidop().replaceAll("'", "''") + "'"
				+ ", AMATERNO = '" + getApellidom().replaceAll("'", "''") + "'"
				+ ", COMISION = 9.090909090909091" //+ obj.getComision()
				+ ", RFC = '" + getRfc() + "'"
				+ ", EDAD = " + getEdad()
				+ ", CORREO_P = '" + getCorreoP() + "'"
				+ ", CORREO_S = '" + getCorreoS() + "'"
				+ ", TELEFONO_P = '" + getTelefonoP() + "'"
				+ ", TELEFONO_S = '" + getTelefonoS() + "'"
				+ ", USUARIO = '" + getUsuario() + "'"
				+ ", FECHA_M = GETDATE()"
				+ "  WHERE PK1 = " + getId();

		int res = db.execQuery(sql);

		if (res <= 0) {
			super._mensaje = "<strong>No te alarmes !!</strong> Al parecer no se guardo el colaborador, por favor intentalo nuevamente.";
			return RESULT.ERROR;
		}

		// DIRECCION
		sql = "DELETE FROM COLABORADORES_DIRECCION WHERE PK_COLABORADOR = " + getId();
		db.execQuery(sql);
		int result = registrar_colaborador_Direccion();
		
		if (result <= 0) {
			super._mensaje = "<strong>No te alarmes !!</strong> La direccion no se ha actualizado, por favor intentalo nuevamente.";
			return RESULT.ERROR;
		}
		
		super._mensaje = "";
		return RESULT.OK;
	}
	//*/
	
	/*
	// 
	public RESULT agregarRelacion() {
		
		
		
		String sql = "SELECT COUNT(*) AS 'VALUE'"
				+ " FROM SORTEOS_ASIGNACION_COLABORADORES"
				+ " WHERE PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ " AND PK_NICHO=" + getNicho()
				+ " AND PK_COLABORADOR=" + getId();
		
		int max = db.getValue(sql, 0);
		
		if (max == 0) {
			sql = "INSERT INTO SORTEOS_ASIGNACION_COLABORADORES (PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR) VALUES"
					+ "(" + getIdSorteo()
					+ "," + getIdSector()
					+ "," + getNicho()
					+ "," + getId() + ")";
			int result = db.execQuery(sql);
			if (result == 0) {
				super._mensaje = "No se pudo agregar la relacion: colaborador - nicho";
				return RESULT.ERROR;
			}
		}
		
		super._mensaje = "";
		return RESULT.OK;
	}
	*/

	public void BuscarEditar() {
		/* *
		String sql = "SELECT C.*"
				+ ",ISNULL((SELECT TOP 1 REFERENCIA FROM COLABORADORES_REFERENCIAS CR WHERE CR.PK_COLABORADOR=C.PK1),'0') AS 'NUM_REFERENCIA'"
				+ " FROM COLABORADORES C WHERE C.PK1 = " + getId();
		/**/
		String sql
			= "SELECT C.*, CR.REFERENCIA AS 'NUM_REFERENCIA'"
			+ " FROM COLABORADORES C LEFT JOIN COLABORADORES_REFERENCIAS CR ON CR.PK_COLABORADOR=C.PK1"
			+ " WHERE C.PK1 = " + getId();
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs.next()) {
				EditFrom(rs);
				consultaDireccion();
				if (getNumReferencia().equals(""))
					generarReferencia();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void consultaDireccion() {

		String sql = "SELECT * FROM COLABORADORES_DIRECCION WHERE PK_COLABORADOR = '" + getId() + "' ";
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs.next()) {
				EditAddressFrom(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public RESULT generarReferencia() {

		List<Parametros> list = new ArrayList<Parametros>();

		list.add(new Parametros("RESULT", "0"));
		list.add(new Parametros("CLAVE", getClave()));
		list.add(new Parametros("NUM_REFERENCIA", getNumReferencia()));
		list.add(new Parametros("StatementType", "Generate"));
		
		list.get(0).setOut(true, Types.INTEGER);
		list.get(2).setOut(true, Types.VARCHAR);
		
		if (db.execStoreProcedureList("spCOLABORADOR_REFERENCIA", list) == 0) {
			if ("1".equals(list.get(0).getParamValue())) {

				setNumReferencia(list.get(2).getParamValue());
				return RESULT.OK;
			}
		}
		return RESULT.ERROR;
	}
	
	public void EditFrom(ResultSet rs) throws SQLException{
		setId(rs.getInt("PK1"));
		setClave(rs.getString("CLAVE"));
		setReferencia(rs.getInt("REFERENCIA"));
		setNumReferencia(rs.getString("NUM_REFERENCIA"));
		setNombre(rs.getString("NOMBRE"));
		setApellidop(rs.getString("APATERNO"));
		setApellidom(rs.getString("AMATERNO"));
		setRfc(rs.getString("RFC"));
		setEdad(rs.getString("EDAD"));
		setCorreoP(rs.getString("CORREO_P"));
		setCorreoS(rs.getString("CORREO_S"));
		setTelefonoP(rs.getString("TELEFONO_P"));
		setTelefonoS(rs.getString("TELEFONO_S"));
		
		setComision(rs.getString("COMISION"));
	}
	
	public void EditAddressFrom(ResultSet rs) throws SQLException{
		setCP(rs.getString("CP"));
		setEstado(rs.getString("ESTADO"));
		setMundel(rs.getString("MUNDEL"));
		setColonia(rs.getString("COLONIA"));
		setCalle(rs.getString("CALLE"));
		setNumExterior(rs.getString("NUMEXT"));
		setNumInterior(rs.getString("NUMINT"));
	}
	
	public int consultaIdColaboradorXClave() {

		String sql = "SELECT TOP 1 PK1 AS 'VALUE' FROM COLABORADORES"
				+ " WHERE CLAVE = '" + this.getClave() + "'";

		return db.getValue(sql, -1);
	}
	
	public double consultaComision() {
		String sql = "SELECT COMISION AS 'VALUE' FROM COLABORADORES WHERE PK1=" + getId();
		return this.comision = db.getValue(sql, 0.0);
	}

	public HashMap<Integer,ArrayList<Integer>> consultaAsignaciones() {
		String sql = "SELECT * FROM COLABORADORES_ASIGNACION CA"
				+ " WHERE PK_COLABORADOR=" + getId();
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		
		try {
			ResultSet rs = db.getDatos(sql);
			while (rs.next()) {
				ArrayList<Integer> list;
				int idSector = rs.getInt("PK_SECTOR");
				int idNicho = rs.getInt("PK_NICHO");
				if (map.containsKey(idSector)) {
					list = map.get(idSector);
				}
				else {
					map.put(idSector, list = new ArrayList<Integer>());
				}
				if (list.contains(idNicho) == false)
					list.add(idNicho);
			}
		}
		catch(SQLException ex) { }
		
		return map;
	}
	
	public String asignacionesToJSON(HashMap<Integer,ArrayList<Integer>> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Integer idSector : map.keySet()) {
			sb
			.append("{\"sector\":\"").append(idSector).append("\"")
			.append(",\"nichos\":[");
			ArrayList<Integer> list = map.get(idSector);
			for (Integer idNicho : list) {
				sb.append("\"").append(idNicho).append("\",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]},");
		}
		if (map.size()>0)
			sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return str_asignaciones = sb.toString();
	}
	
	public RESULT guardaAsignaciones(String [] values)
	{
		// Se van agrupando los Nichos de acuerdo a su Sector
		HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		for (int i=0; i<values.length; i++)
		{
			String[] datos = values[i].split("[;]");
			try
			{
				int sector = Integer.valueOf(datos[0]);
				int nicho = Integer.valueOf(datos[1]);
				
				ArrayList<Integer> listNichos;
				if (map.containsKey(sector))
					listNichos = map.get(sector);
				else{
					listNichos = new ArrayList<Integer>();
					map.put(sector, listNichos);
				}
				listNichos.add(nicho);
			}
			catch(Exception ex) { }
		}
		
		boolean ok = true;
		if (getIdSector() != 0)
		{
			borrarAsignacionesPorSector();
			
			
			for (Integer sector: map.keySet())
			{
				String idnichos = map.get(sector).stream().map(Object::toString).collect(Collectors.joining(","));
				
				ok = (agregaAsignaciones(idnichos) == RESULT.OK) && ok;
				/*
				// Se borrar las asignaciones con base en el colaborador y sector, excepto aquellas que tengan talonarios.
				
				setIdSector(sector);
				borrarAsignacionesPorSector();
				
				ArrayList<Integer> listNichos = map.get(sector);
				
				for (Integer nicho : listNichos) {
					// Consulta para saber si ya existe la asignacion...
					
					String sql = "SELECT COUNT(*) AS 'VALUE'"
							+ " FROM SORTEOS_ASIGNACION_COLABORADORES"
							+ " WHERE PK_SORTEO=" + getIdSorteo()
							+ " AND PK_SECTOR=" + sector
							+ " AND PK_NICHO=" + nicho
							+ " AND PK_COLABORADOR=" + getId();
					
					boolean noExiste = db.getValue(sql, 0) == 0;
					if (noExiste) {
						sql = "INSERT INTO SORTEOS_ASIGNACION_COLABORADORES (PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,FECHA_R)"
							+ "VALUES(" + getIdSorteo()
							+ "," + sector
							+ "," + nicho
							+ "," + getId()
							+ ",GETDATE()"
							+ ")";
						int result = db.execQuery(sql);
						if (result != 1)
							super._mensaje = "El colaborador no se pudo asignar al nicho con id-interno=" + nicho + " .";
						
						// mientras se sigan insertando las asinaciones, todo estara correcto.
						ok = ok && (result == 1);
					}
				}*/
			}
		}
		
		return ok ? RESULT.OK : RESULT.ERROR;
	}
	
	public RESULT agregaAsignaciones(String joinedNichos) {
		List<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1", 0));
		list.add(new Parametros("PK_SORTEO", getIdSorteo()));
		list.add(new Parametros("PK_SECTOR", getIdSector()));
		list.add(new Parametros("PK_NICHO", 0));
		list.add(new Parametros("PK_COLABORADOR", getId()));
		list.add(new Parametros("USUARIO", getUsuario()));
		list.add(new Parametros("LIST", joinedNichos));
		list.add(new Parametros("StatementType", "InsertMultiple"));
		
		// En caso de asignar multiples nichos se trae un 1 si inserto algun dato, 0 en otro caso.
		int id = db.execStoreProcedureIntId("spCOLABORADORES_ASIGNACION", list);
		
		if (0 < id) {
			super._mensaje = "";
			return RESULT.OK;
		}
		else {
			super._mensaje = "La(s) asignacion(es) no se ha(n) guardado. ";
			return RESULT.ERROR;
		}
	}
	
	private int borrarAsignacionesPorSector()
	{
		List<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1", "0"));
		list.add(new Parametros("PK_SORTEO", getIdSorteo()));
		list.add(new Parametros("PK_SECTOR", getIdSector()));
		list.add(new Parametros("PK_NICHO", getIdNicho()));
		list.add(new Parametros("PK_COLABORADOR", getId()));
		list.add(new Parametros("USUARIO", "empty"));
		list.add(new Parametros("LIST", "empty"));
		list.add(new Parametros("StatementType", "DeleteNoTickets"));
		
		boolean result = db.execStoreProcedure("spCOLABORADORES_ASIGNACION", list);
		
		return result ? 1 : 0;
		/* *
		String sql = "DELETE SORTEOS_ASIGNACION_COLABORADORES"
				+ " WHERE PK_SORTEO=" + getIdSorteo()
				+ " AND PK_SECTOR=" + getIdSector()
				+ " AND PK_COLABORADOR=" + getId()
				+ " AND (SELECT COUNT(*) FROM SORTEOS_COLABORADORES_BOLETOS SCB"
				+ "  WHERE SCB.PK_SORTEO=" + getIdSorteo()
				+ "  AND SCB.PK_SECTOR=" + getIdSector()
				+ "  AND SCB.PK_COLABORADOR=" + getId()
				+ "  AND SCB.PK_NICHO=SORTEOS_ASIGNACION_COLABORADORES.PK_NICHO) = 0";
		return db.execQuery(sql);
		/**/
	}
	
	/*
	public void ObtenerTelefono(mColaboradores u) {

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
	*/

	/*
	public void ObtenerCorreo(mColaboradores u) {

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
	//*/

	/*
	public void ObtenerRedes(mColaboradores u) {

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
	//*/
	/*
	public int Borrar(mColaboradores obj) {

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
	//*/

	public void actualizarComision(SesionDatos sesion) {

		String sql
			= "UPDATE COLABORADORES SET COMISION = " + getComision() + ", FECHA_M = GETDATE()"
			+ ", USUARIO = '" + sesion.nickName + "'"
			+ " WHERE PK1 = " + getId();

		db.execQuery(sql);
	}

	public void consultaPoblacionUsuarioActual() {

		String sql = "SELECT PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();

		ResultSet rs = db.getDatos(sql);

		try {
			if (rs.next()) {
				this.setIdSector(rs.getInt("PK_SECTOR"));
				this.setIdNicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void BorrarColaboradores(String[] colaboradores, SesionDatos sesion)
	{
		_count_max = colaboradores.length;
		_count_process = 0;
		_count_success = 0;
		_count_excluded = 0;

		StringBuilder sbLIST = new StringBuilder();
		for (int i=0; i<colaboradores.length; i++)
			sbLIST.append(colaboradores[i]).append(",");
		
		ArrayList<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("PK1","0"));
		list.add(new Parametros("CLAVE","0"));
		list.add(new Parametros("REFERENCIA","0"));
		list.add(new Parametros("NUM_REFERENCIA","0"));
		list.add(new Parametros("NOMBRE","0"));
		list.add(new Parametros("APATERNO","0"));
		list.add(new Parametros("AMATERNO","0"));
		list.add(new Parametros("COMISION","0"));
		list.add(new Parametros("RFC","0"));
		list.add(new Parametros("EDAD","0"));
		list.add(new Parametros("CORREO_P","0"));
		list.add(new Parametros("CORREO_S","0"));
		list.add(new Parametros("TELEFONO_P","0"));
		list.add(new Parametros("TELEFONO_S","0"));
		list.add(new Parametros("USUARIO", getUsuario()));
		
		list.add(new Parametros("LIST", sbLIST));
		list.add(new Parametros("StatementType","DeleteMultiple"));
		
		_count_process = db.execStoreProcedureIntId("spCOLABORADORES", list);
		

		_count_success += _count_process;
		_count_excluded += _count_max - _count_process;
		
		/*
		for (String ID : colaboradores)
		{
			String sql = "SELECT COUNT(*) AS 'VALUE' FROM SORTEOS_COLABORADORES_BOLETOS SCB, BOLETOS B"
					+ " WHERE SCB.PK_BOLETO=B.PK1 AND SCB.PK_SORTEO=" + getIdSorteo()
					+ " AND SCB.PK_COLABORADOR=" + ID;
			
			int boletos = db.getValue(sql, 0);
			
			if (boletos == 0)
			{
				
				sql =
					" DELETE FROM COLABORADORES WHERE (PK1 = " + ID + ")" +
					//" DELETE FROM COLABORADORES_CORREOS WHERE (PK_COLABORADOR = " + ID + ")" +
					" DELETE FROM COLABORADORES_DIRECCION WHERE (PK_COLABORADOR = " + ID + ")" +
					//" DELETE FROM COLABORADORES_REDES_SOCIALES WHERE (PK_COLABORADOR = " + ID + ")" +
					//" DELETE FROM COLABORADORES_TELEFONOS WHERE (PK_COLABORADOR = " + ID + ")" +
					" DELETE FROM SORTEOS_ASIGNACION_COLABORADORES WHERE (PK_SORTEO=" + getIdSorteo() + " AND PK_COLABORADOR = " + ID + ")" +
					"";
				
				if (db.execQuery(sql)>0)
				{
					_count_success++;
					this.Log(sesion, LOG.REGISTRO, this, "COLABORADOR ELIMINADO",
						sesion.toShortString());
				}
			}
			else {
				_count_excluded++;
			}
			_count_process++;
		}*/
	}
	
	public ResultSet VerAsignaciones() {
		String sql = "SELECT S.CLAVE AS 'SECTOR_CLAVE', S.SECTOR,N.CLAVE AS 'NICHO_CLAVE', N.NICHO FROM COLABORADORES_ASIGNACION SAC, NICHOS N, SECTORES S WHERE SAC.PK_NICHO=N.PK1 AND SAC.PK_SECTOR=S.PK1 AND PK_COLABORADOR=" + getId();
		
		return db.getDatos(sql);
	}
	
	public String nuevaClave() {
		String sql = "SELECT TOP 1 CLAVE AS 'VALUE' FROM COLABORADORES WHERE LEN(CLAVE)>8 ORDER BY CLAVE DESC";
		
		String lastID = db.getValue(sql, "100000000"); // primer pseudovalor para las claves de colaboradores externos.
		//lastID = lastID.substring(2);
		long consecutivo = 1;
		try{
		//	consecutivo = 1 + Integer.parseInt(lastID);
			consecutivo = 1 + Long.parseLong(lastID);
		}
		catch(Exception ex) { }
		
		// Si ya existe una clave igual a la auto-generada ...
		String nuevaClave = "" + consecutivo;// String.format("%08d", consecutivo);
		sql = "SELECT COUNT(*) AS 'VALUE' FROM COLABORADORES WHERE CLAVE='" + nuevaClave + "'";
		if (db.getValue(sql, 0) > 0)
		{
			ArrayList<String> clavesExistentes = new ArrayList<String>();
			try {
				sql = "SELECT CLAVE FROM COLABORADORES WHERE LEN(CLAVE)>8 ORDER BY CLAVE";
				ResultSet rs = db.getDatos(sql);
				while (rs.next())
					clavesExistentes.add(rs.getString("CLAVE"));
			} catch (SQLException ex) { }
			
			while(true) {
				nuevaClave = "" + consecutivo;// String.format("%08d", consecutivo);
				if (clavesExistentes.contains(nuevaClave) == false)
					break;
				consecutivo++;
			}
		}
		
		/*
		while (true) {
			CLAVE = "E" + String.format("%010d", consecutivo);
			sql = "SELECT COUNT(*) AS 'VALUE' FROM COLABORADORES WHERE CLAVE='" + CLAVE + "'";
			if (db.getValue(sql, 0) == 0)
				break;
			consecutivo++;
		}
		*/
		return nuevaClave;
	}
	
	public String getJSON() {
		String colonias = Obtener_Colonias_HTML();// colonia de sepomex
		
		return new StringBuilder()
		.append("{'id':'").append(getId()).append("'")
		.append(",'clave':'").append(getClave()).append("'")
		.append(",'referencia':'").append(getReferencia()).append("'")
		.append(",'num_referencia':'").append(getNumReferencia()).append("'")
		.append(",'nombre':'").append(getNombre()).append("'")
		.append(",'apellidop':'").append(getApellidop()).append("'")
		.append(",'apellidom':'").append(getApellidom()).append("'")
		.append(",'comision':'").append(getComision()).append("'")
		.append(",'rfc':'").append(getRfc()).append("'")
		.append(",'edad':'").append(getEdad()==0 ? "" : getEdad()).append("'")
		.append(",'telefono_p':'").append(getTelefonoP()).append("'")
		.append(",'telefono_s':'").append(getTelefonoS()).append("'")
		.append(",'correo_p':'").append(getCorreoP()).append("'")
		.append(",'correo_s':'").append(getCorreoS()).append("'")
		
		.append(",'estado':'").append(getEstado()).append("'")
		.append(",'mundel':'").append(getMundel()).append("'")
		.append(",'calle':'").append(getCalle()).append("'")
		.append(",'numero_int':'").append(getNumInterior()).append("'")
		.append(",'numero_ext':'").append(getNumExterior()).append("'")
		.append(",'cp':'").append(getCP()).append("'")
		.append(",'colonia':'").append(getColonia()).append("'")
		.append(",'colonias_html':'").append(colonias).append("'")
		.append(",'asignaciones':").append(str_asignaciones).append("")
		.append("}")
		.toString().replaceAll("'", "\"");
	}
}
