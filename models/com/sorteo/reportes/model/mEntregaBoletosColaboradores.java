package com.sorteo.reportes.model;

import java.sql.ResultSet;
import com.core.SesionDatos;
import java.sql.SQLException;
import com.core.SuperModel;

public class mEntregaBoletosColaboradores extends SuperModel {
	
	private int id;
	
	private String clave;
	private int sector;
	private String rbancaria;
	private String colaborador;
	private int nicho;
	private String descripcion;

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
	
	private int idusuario;
	private int idsorteo;
	
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
	
	public void init(){

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
	
	
	
	

	public mEntregaBoletosColaboradores() {
		init();
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

	public int contar(int sector, int nicho, SesionDatos sesion) {
		// TODO - contar
		
		String sql
				= " SELECT COUNT(*) AS 'MAX' FROM COLABORADORES C, NICHOS N, SECTORES S "
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1 ";
		
		if (sector != 0) {
			sql += " AND N.PK_SECTOR = '" + sector + "'  ";
		}
		else {
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
		
		int max = 0;
		try {
			ResultSet res = db.getDatos(sql);
			if(res.next()){
				max = res.getInt("MAX");
			}
			res.close();
		}catch(SQLException ex) { }

		return max;
	}

	public ResultSet paginacion(int sector, int nicho, SesionDatos sesion) {
		// TODO - paginacion
		
		String sql
				= " SELECT C.*,CONCAT(C.NOMBRE,' ',C.APATERNO) AS 'NOM_COLABORADOR', N.NICHO AS 'NOM_NICHO', S.SECTOR AS 'NOM_SECTOR'"
				+ ",(SELECT COUNT(*)     FROM SORTEOS_COLABORADORES_TALONARIOS SCB, TALONARIOS T WHERE SCB.PK_TALONARIO=T.PK1 AND SCB.PK_COLABORADOR=C.PK1) AS 'ACUM_TALONARIOS'"
				+ ",(SELECT ISNULL(SUM(T.MONTO),0) FROM SORTEOS_COLABORADORES_TALONARIOS SCB, TALONARIOS T WHERE SCB.PK_TALONARIO=T.PK1 AND SCB.PK_COLABORADOR=C.PK1) AS 'ACUM_MONTO'"
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo
				+ " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1";
		
		/*
		String sql = " SELECT C.*,CONCAT(C.NOMBRE,' ',C.APATERNO) AS 'NOM_COLABORADOR', N.NICHO AS 'NOM_NICHO', S.SECTOR AS 'NOM_SECTOR'"
				+ " FROM COLABORADORES C, NICHOS N, SECTORES S"
				+ " WHERE S.PK_SORTEO=" + sesion.pkSorteo + " AND N.PK_SECTOR = S.PK1 AND C.PK_NICHO = N.PK1";
		//*/

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

		System.out.println("COLABORADORES: " + sql);

		ResultSet rs = db.getDatos(sql);
		return rs;
	}
	

	public void ObtenerTelefono() {

		String sql = "SELECT * FROM COLABORADORES_TELEFONOS WHERE PK_COLABORADOR = " + this.getId();

		ResultSet rs = db.getDatos(sql);

		try {

			while (rs.next()) {

				if (rs.getString("TIPO").equals("C")) {

					this.setTelefonocasa(rs.getString("TELEFONO"));

				} else if (rs.getString("TIPO").equals("T")) {

					this.setTelefonooficina(rs.getString("TELEFONO"));

				} else {

					this.setTelefonomovil(rs.getString("TELEFONO"));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ObtenerCorreo() {

		String sql = "SELECT * FROM COLABORADORES_CORREOS WHERE PK_COLABORADOR = "+ this.getId();

		ResultSet rs = db.getDatos(sql);
		try {

			while (rs.next()) {

				if (rs.getString("TIPO").equals("P")) {

					this.setCorreopersonal(rs.getString("CORREO"));

				} else if (rs.getString("TIPO").equals("T")) {

					this.setCorreotrabajo(rs.getString("CORREO"));

				} else {

					this.setCorreootro(rs.getString("CORREO"));

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ObtenerRedes() {

		String sql = "SELECT * FROM COLABORADORES_REDES_SOCIALES WHERE PK_COLABORADOR = " + this.getId();

		ResultSet rs = db.getDatos(sql);

		try {

			while (rs.next()) {

				if (rs.getInt("SECUENCIA") == 1) {

					this.setFacebook(rs.getString("RED"));

				} else if (rs.getInt("SECUENCIA") == 2) {

					this.setTwitter(rs.getString("RED"));

				} else {

					this.setRedotro(rs.getString("RED"));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

}
