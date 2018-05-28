package com.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Privileges {

	// private String roles[];	
	Database db = new Database();	
	private ArrayList<Long> list_pkPermisos;
	
	private long pkUsuario;
	private boolean administrador = false;
	

	public long getPkUsuario() {
		return pkUsuario;
	}

	public void setPkUsuario(long pkUsuario) {
		this.pkUsuario = pkUsuario;
	}

	public Privileges() {
		// TODO Auto-generated constructor stub
	}

	public void getPermisosXRolesUsuario(long pkUsuario, Database db) {

		
		this.setPkUsuario(pkUsuario);
		
		// Super_consulta_de_permisos
		String sql = "SELECT RU.PK_ROLE, RP.PK_PERMISO FROM ROLES_USUARIO RU, ROLES_PERMISOS RP WHERE RU.PK_USUARIO = " + pkUsuario + " AND RP.PK_ROLE = RU.PK_ROLE ";
		

		ResultSet rs = db.getDatos(sql);

		this.list_pkPermisos = new ArrayList<Long>();

		try {
			//ArrayList<Long> list_pkPermisos = new ArrayList<Long>(); 
			while (rs.next())
				list_pkPermisos.add(rs.getLong("PK_PERMISO"));
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.administrador = consultaEsAdministrador();

	}
	
	public boolean havePermiso(long pkPermiso) {

		if (this.administrador) {
			return true;
		}

		for (Long item : list_pkPermisos)
			if (item == pkPermiso)
				return true;

		return false;

	}// <end>
	
	public boolean esAdministrador() {
		return this.administrador;
	}
	
	private  boolean consultaEsAdministrador() {
		
		long user = this.getPkUsuario();				
		//System.out.println(">>>>id usuario: "+this.getPkUsuario());
				
		String sql = "SELECT PK1 FROM ROLES_USUARIO WHERE PK_USUARIO = '"+user+"' AND PK_ROLE = '2' ";			
		//System.out.println(">>>>SQL: "+sql);
		int numero = db.ContarFilas(sql);			
		
		if(numero > 0)				
		 return true;	
			

	return false;		
		
		
	}//<end>

}




