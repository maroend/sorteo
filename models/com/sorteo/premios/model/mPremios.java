package com.sorteo.premios.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.core.Factory;
import com.core.SesionDatos;
import com.core.SuperModel;
import com.core.Factory.LOG;

public class mPremios extends SuperModel {
	private int idsorteo;
	private int idsector;
	private int idnicho;
	private int idUsuario;
	
	private String sorteo;
	
	private String numeroPremio;
	private String clasificacion;
	private String nombre;
	private String valor;
	private String estrellas;
	private String descripcion;
	private String imagen;
	
	private final int clave_beneficiario = 1; // 1=compradores,  2=Colaboradores 

	public mPremios() {
		// TODO Auto-generated constructor stub
	}

	public int getIdsorteo() {
		return idsorteo;
	}

	public void setIdsorteo(int idsorteo) {
		this.idsorteo = idsorteo;
	}

	public int getIdsector() {
		return idsector;
	}

	public void setIdsector(int idsector) {
		this.idsector = idsector;
	}

	public int getIdnicho() {
		return idnicho;
	}

	public void setIdnicho(int idnicho) {
		this.idnicho = idnicho;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		try {
			Double.parseDouble(valor);
			this.valor = valor;
		}catch(Exception ex) { this.valor = "0.0"; }
	}

	public String getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(String estrellas) {
		try {
			int value = Integer.valueOf(estrellas);
			this.estrellas = "" + value;
		}catch(Exception ex) { this.estrellas = "0"; }
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSorteo() {
		return sorteo;
	}

	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}

	public String getNumeroPremio() {
		return numeroPremio;
	}

	public void setNumeroPremio(String numeroPremio) {
		try {
			int value = Integer.valueOf(numeroPremio);
			this.numeroPremio = "" + value;
		}catch(Exception ex) { this.numeroPremio = "0"; }
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	
	public int contarPremios() {
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM PREMIOS WHERE PK_SORTEO=" + this.getIdsorteo() + " AND CLAVE_BENEFICIARIO=" + clave_beneficiario;
		int max = 0;
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
			}
		}catch (SQLException e) { }
		
		return max;
	}
	
	public ResultSet paginacion(int pg, int numreg, String search) {
		
		String sql = "SELECT * FROM PREMIOS WHERE PK_SORTEO=" + this.getIdsorteo() + " AND CLAVE_BENEFICIARIO=" + clave_beneficiario;
		
		if (search != "") {
			sql += " AND NOMBRE LIKE '" + search + "'  ";
		}
		
		sql += " ORDER BY CLASIFICACION ASC, NUM_PREMIO ASC " 
				+ " OFFSET (" + (pg - 1) * numreg + ") ROWS "
				+ " FETCH NEXT " + numreg + " ROWS ONLY";
		
		return db.getDatos(sql);
	}
	
	public void consultaUsuarioSorteo()
	{
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		try {
			while (rs.next()) {
				this.setIdsorteo(rs.getInt("PK_SORTEO"));
				this.setIdsector(rs.getInt("PK_SECTOR"));
				this.setIdnicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int registrar(SesionDatos sesion) {
		String sql = "INSERT INTO PREMIOS (PK_SORTEO,NUM_PREMIO,CLASIFICACION,NOMBRE,VALOR,ESTRELLAS,DESCRIPCION,IMAGEN,FECHA_R,USUARIO,CLAVE_BENEFICIARIO)"
				+ " VALUES (" + sesion.pkSorteo
				+ ",  " + getNumeroPremio()
				+ ",  " + getClasificacion()
				+ ", '" + Factory.comillaSQL(getNombre()) + "'"
				+ ",  " + getValor()
				+ ",  " + getEstrellas()
				+ ", '" + Factory.comillaSQL(getDescripcion()) + "'"
				+ ", '" + Factory.comillaSQL(getImagen()) + "'"
				+ ", GETDATE()"
				+ ", '" + sesion.nickName + "'"
				+ ", " + clave_beneficiario
				+ ")";
		
		System.out.println(sql);
		
		int res = db.execQuery(sql);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.REGISTRO, this, "registrar",
					sesion.toShortString() + ", premio=" + getNombre());
		} catch (Exception ex) {
			Factory.Error(ex, "Log");
		}

		return res;
	}

	public void consultaSorteo() {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + getIdsorteo();

		//System.out.println(" ---> "+sql);
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				this.setSorteo(rs.getString("SORTEO"));
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int consultaSiguienteNumeroPremio() {
		String sql = "SELECT MAX(NUM_PREMIO) AS 'MAX' FROM PREMIOS WHERE PK_SORTEO=" + getIdsorteo();
		System.out.println("NUM PREMIO: "+sql);
		ResultSet rs = db.getDatos(sql);
		int next = 0;
		try {
			if (rs != null && rs.next()) {
				String tmp = rs.getString("MAX"); 
				rs.close();
				next = Integer.valueOf(tmp);
			}
		}catch(Exception ex) { }
		
		return ++next;
	}
	
	public int cuentaNumeroDePremio(int numeroPremio) {
		String sql = "SELECT COUNT(NUM_PREMIO) AS 'MAX' FROM PREMIOS WHERE PK_SORTEO=" + getIdsorteo() + " AND NUM_PREMIO=" + numeroPremio;
		ResultSet rs = db.getDatos(sql);
		int count = 0;
		try{
			if (rs != null && rs.next()) {
				count = Integer.valueOf(rs.getString("MAX"));
			}
		}catch(SQLException ex) { }

		return count;
	}
	
	public int borraPremio(int idPremio, SesionDatos sesion) {
		String sql = "DELETE FROM PREMIOS WHERE PK1=" + idPremio;
	   	int res = db.execQuery(sql);

		// --- Se guarda un registro de seguimiento ---
		try {
			this.Log(sesion, LOG.BORRADO, this, "eliminar", "idpremio=" + idPremio);
		} catch(Exception ex) { Factory.Error(ex, "Log"); }

		return res;
	}
	

}



