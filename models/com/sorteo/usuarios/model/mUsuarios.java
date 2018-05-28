package com.sorteo.usuarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.Security;
import com.core.SesionDatos;
import com.core.SuperModel;

public class mUsuarios extends SuperModel {
	
	
	//private static final boolean TRUE = false;

	private int id;      //PRIMARY KEY DEL USUARIO
	
	private String nombre;  //
	private String apellidop;  //
	private String apellidom;  //
	
	private int idrole;
	
	private int edad;  //
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
	private String numero;
	
	
	private String usuario;
	private String password;
	
	
	public mUsuarios() {
		// TODO Auto-generated constructor stub
		
		init();
	}
	
	public void init(){
		
		 this.id = 0;
		 this.usuario = "";
		 this.password = "";
		 this.idrole = 0;
		
		
	}
	
	
	
	 public ResultSet listarModal(){
	    	
	    	String sql = "SELECT PK1 FROM ROLES";
	    	ResultSet rs = db.getDatos(sql);
	    	return rs;
	    	
		}
	 
	 
	 public int contarModal(){
		   
		   String sql = "SELECT * FROM ROLES";
		   int numero = db.ContarFilas(sql);
		   return numero;
		   
	   }
	 
	 
	 public ResultSet paginacionModal(int pg, int numreg,String search){
		   	
		   String sql = "SELECT * ";
		          sql += "FROM ROLES ";
		          
		          if(search!=""){
		        	  sql += " WHERE ROLE LIKE '%"+search+"%'  ";
		          }
		          
		          sql += "ORDER BY PK1 ASC ";
		          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
		          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
	   	
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	return rs;
	   }
	 
	 
	 public void ExisteRoleUsuario(mUsuarios obj){
		   	
		   	String sql = "SELECT PK_ROLE FROM ROLES_USUARIO WHERE PK_USUARIO =  '"+obj.getId()+"'";
		   	
		   	ResultSet rs = db.getDatos(sql);
		   	
		   	try {
		   		
				if(rs.next()) {					
					
					obj.setIdrole(rs.getInt("PK_ROLE"));					
				  
				}
			
				 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		   	
	}

	public void AgregarRoleUsuario(mUsuarios obj) {
		db.con();

		String sql = "SELECT PK1 FROM ROLES_USUARIO WHERE PK_USUARIO ='"+obj.getId()+"' ";		   	
		ResultSet rs = db.getDatos(sql);

		try {

			if (rs.next()) {

				String sql1 = "UPDATE ROLES_USUARIO SET PK_ROLE = '"+obj.getIdrole()+"' WHERE PK_USUARIO =  '"+obj.getId()+"' ";					
				db.execQuery(sql1);

			} else {

				String sql2 = "INSERT INTO ROLES_USUARIO (PK_USUARIO,PK_ROLE) VALUES ('"+obj.getId()+"','"+obj.getIdrole()+"')";

				db.execQuery(sql2);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		init();

	}

	public ResultSet listar() {

    	String sql = "SELECT * FROM USUARIOS";
    	ResultSet rs = db.getDatos(sql);
    	return rs;
    	
	}
   
   
   
   public boolean ExisteUsuario(String usr){
   	
   	String sql = "SELECT PK1 FROM USUARIOS WHERE USUARIO = '"+usr+"'";
   	ResultSet rs = db.getDatos(sql);
   	System.out.println("EXISTE: "+ sql);
   //	return rs;
   	
   	try {
		if( rs.next()){
			 
			return true;
			
		}else{
			
			return false;
		}
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
   	
   	
	}
   
   
   public void ObtenerUsuario(mUsuarios u){
	   	
	   	String sql = "SELECT * FROM USUARIOS WHERE PK1 =  '"+u.getId()+"'";
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	
	   	try {
			if(rs.next()) {
				
				
				   u.setUsuario(rs.getString("USUARIO"));				
				 //  u.setPassword(rs.getString("PASSWORD"));
				   try {
						u.setPassword(Security.decrypt(rs.getString("PASSWORD")));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	}
   
   
   
   
   public int EditarPassword(mUsuarios obj){
		db.con();	
		
		
	 //  String sql = "INSERT INTO USUARIOS (USUARIO,PASSWORD,NOMBRE,APATERNO,AMATERNO,ACTIVO,PK_USUARIO,FECHA_R) VALUES ('"+obj.getUsuario()+"','"+obj.getPassword()+"','"+obj.getNombre()+"','"+obj.getApellidop()+"','"+obj.getApellidom()+"','1','admin','2015-02-11 12:36:09.000')";
		
		String sql = "UPDATE USUARIOS SET USUARIO = '"+obj.getUsuario()+"', PASSWORD = '"+obj.getPassword()+"' WHERE PK1 =  '"+obj.getId()+"' ";
		
		
	    System.out.println(sql);
	    int res = db.execQuery(sql);    
	   
	    init();
	    return res;
	    
   }
   
   
   public int registrar(mUsuarios obj, SesionDatos sesion){
		//db.con();
	    String sql
	    		= "INSERT INTO USUARIOS (USUARIO,PASSWORD,NOMBRE,APATERNO,AMATERNO,ACTIVO,FECHA_R,EDAD,RFC)"
	    		+ " VALUES ('"+obj.getUsuario()+"','"+obj.getPassword()+"','"+obj.getNombre()+"','"+obj.getApellidop()+"','"+obj.getApellidom()+"','1',GETDATE(),'"+obj.getEdad()+"','"+obj.getRfc()+"')";
	    System.out.println(sql);
	    int id = db.execQuerySelectId(sql); 
	    System.out.println("idusuario "+id);
		obj.setId(id);
	    
	    
	  //  int res = db.execQuery(sql);    
	  	   	    
	    
	  
	    
	  //TELEFONOS	
		
		registrar_usuario_Telefonos(obj.getUsuario(),obj.getTelefonocasa(),obj.getTelefonooficina()); 
   //  registrar_usuario_Telefonos(obj.getUsuario(),2,obj.getTelefonooficina(),'T'); 
  //  registrar_usuario_Telefonos(obj.getUsuario(),3,obj.getTelefonomovil(),'M');
	       
     //DIRECCION     
     registrar_usuario_Direccion(obj);
     
     
     //CORREOS
     
     registrar_usuario_Correo(obj.getUsuario(),obj.getCorreopersonal(),obj.getCorreotrabajo());
    /* registrar_usuario_Correo(obj.getUsuario(),1,obj.getCorreopersonal(),'P'); 
     registrar_usuario_Correo(obj.getUsuario(),2,obj.getCorreotrabajo(),'T');     
     if(obj.getCorreootro().trim() != ""){ registrar_usuario_Correo(obj.getUsuario(),3,obj.getCorreootro(),'O');}*/
    
	    
	 //REDES SOCIALES 
    /* if(obj.getFacebook().trim() != ""){  registrar_usuario_RedSocial(obj.getUsuario(),1,obj.getFacebook());}
     if(obj.getTwitter().trim() != ""){  registrar_usuario_RedSocial(obj.getUsuario(),2,obj.getTwitter()); }
     if(obj.getRedotro().trim() != ""){  registrar_usuario_RedSocial(obj.getUsuario(),3,obj.getRedotro()); }*/
     
     
    
     init();
	    
		return id;
	}
   
   
   public int contar(String search){
	   
	   String sql = "SELECT * FROM USUARIOS";
	   
	   if(search!=""){      	 
      	  
      	  sql += " WHERE ((USUARIO LIKE '%" + search + "%') OR (NOMBRE LIKE '%" + search + "%'))   ";      	  
      	  
        }
	   int numero = db.ContarFilas(sql);
	   return numero;
	   
   }
   
   
   
   public ResultSet paginacion(int pg, int numreg,String search){
	   	
	 /*  String sql = "SELECT * ";
	          sql += "FROM USUARIOS ";*/
	   
	   
	   String sql = "SELECT U.PK1,U.USUARIO, CONCAT(U.NOMBRE,' ',U.APATERNO,' ',U.AMATERNO) AS NOMBRE,(select R.ROLE from ROLES R, ROLES_USUARIO RU WHERE U.PK1 = RU.PK_USUARIO AND R.PK1 = RU.PK_ROLE ) AS 'ROLE' ";		       
       sql += "FROM USUARIOS U ";
       
	          
	          if(search!=""){
	        	 // sql += " WHERE NOMBRE LIKE '%"+search+"%'  ";
	        	  
	        	  sql += " WHERE ((U.USUARIO LIKE '%" + search + "%') OR (U.NOMBRE LIKE '%" + search + "%'))   ";
	        	  
	        	  
	          }
	          
	          
	          sql += "ORDER BY PK1 ASC ";
	          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
	          sql += "FETCH NEXT "+numreg+" ROWS ONLY";
	          
	          System.out.println(">>>USUARIOS "+sql);
   	
   	
   	ResultSet rs = db.getDatos(sql);
   	return rs;
   }
   
   
   public int registrar_usuario_Telefonos(String usr,String telprincipal,String telefonosecu){
		db.con();
		
		  //  String sql = "INSERT INTO USUARIOS_DIRECCION (USUARIO,PAIS,CP,ESTADO,MUNDEL,COLONIA,CALLE,NUMEXT) VALUES ('"+u.getUsuario()+"','"+u.getPais()+"','"+u.getCp()+"','"+u.getEstado()+"','"+u.getMundel()+"','"+u.getColonia()+"','"+u.getCalle()+"','"+u.getNumero()+"')";
			String sql = "UPDATE USUARIOS SET TELEFONO_P = '"+telprincipal+"', TELEFONO_S = '"+telefonosecu+"'  WHERE USUARIO =  '"+usr+"' ";
		    System.out.println(sql);
		    int res = db.execQuery(sql);
		    db.desconectar();
			return res;
	}
   
   public int registrar_usuario_Correo(String usr,String correop,String correosec){
		/*db.con();		
		
	    String sql = "INSERT INTO USUARIOS_CORREOS (USUARIO,SECUENCIA,CORREO,TIPO,FECHA_R) VALUES ('"+usr+"','"+secuencia+"','"+correo+"','"+tipo+"','2015-02-11 12:36:09.000')";
		//String sql = "UPDATE USUARIOS_CORREOS SET SECUENCIA = '"+secuencia+"', CORREO = '"+correo+"', TIPO =  '"+tipo+"'  WHERE USUARIO =  '"+usr+"'   ";
	    System.out.println(sql);
	    int res = db.execQuery(sql);
	    db.desconectar();
		return res;*/
		//db.con();
		
		  			String sql = "UPDATE USUARIOS SET CORREO_P = '"+correop+"', CORREO_S = '"+correosec+"'  WHERE USUARIO =  '"+usr+"' ";
		    System.out.println(sql);
		    int res = db.execQuery(sql);
		    //db.desconectar();
			return res;
		
		
		
		
	}
   
   public int registrar_usuario_RedSocial(String usr,int secuencia,String red){
		db.con();
	    String sql = "INSERT INTO USUARIOS_REDES_SOCIALES (USUARIO,SECUENCIA,RED,FECHA_R) VALUES ('"+usr+"','"+secuencia+"','"+red+"','2015-02-11 12:36:09.000')";
	    System.out.println(sql);
	    int res = db.execQuery(sql);
	   
		return res;
	}
   
   
   public int registrar_usuario_Direccion(mUsuarios u){
		db.con();
	    String sql = "INSERT INTO USUARIOS_DIRECCION (PK_USUARIO,PAIS,CP,ESTADO,MUNDEL,COLONIA,CALLE,NUMEXT) VALUES ('"+u.getId()+"','"+u.getPais()+"','"+u.getCp()+"','"+u.getEstado()+"','"+u.getMundel()+"','"+u.getColonia()+"','"+u.getCalle()+"','"+u.getNumero()+"')";
	    System.out.println(sql);
	    int res = db.execQuery(sql);
	 
		return res;
	}
   
   
   public void Obtener_Direccion(mUsuarios u){
   	
   	String sql = "SELECT PAIS,d_estado,D_mnpio FROM SEPOMEX WHERE (d_codigo LIKE N'%"+u.getCp()+"%') GROUP BY PAIS,d_estado,D_mnpio";
   	
   	ResultSet rs = db.getDatos(sql);
   	
   	try {
		if(rs.next()) {
		  u.setPais(rs.getString("PAIS"));
		  u.setEstado(rs.getString("d_estado"));
		  u.setMundel(rs.getString("D_mnpio"));
          u.setColonia(this.Obtener_Colonia());
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	
	}
   
   private String Obtener_Colonia(){
	   	
	   	String sql = "SELECT d_asenta FROM SEPOMEX WHERE (d_codigo LIKE N'%"+this.getCp()+"%') GROUP BY d_asenta";
	   	ResultSet rs = db.getDatos(sql);
	   	String colonia="";
	   	
	   	try {
			while(rs.next()) {
				colonia += "<option>"+rs.getString("d_asenta")+"</option>";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	 
		return colonia;
		}
   
   
   
   public int Borrar(mUsuarios obj) {
   	
   	db.con();
  	int res=0;
    try{
 
        String sql = "DELETE FROM USUARIOS WHERE PK1='"+obj.getId()+"'";
        res = db.execQuery2(sql);  
    
	    } catch (SQLException e) {
			//System.out.println("Error de Ejecucion...!!" + e.getMessage());
	    	res=-1;
			//e.printStackTrace();
		}
   	
   	
	  return res;
		
	}
   
   
   public void BorrarDependencias(mUsuarios obj) {
	   	
	       db.con(); 	      
	      
	       String sql = "DELETE FROM SORTEOS_USUARIOS WHERE PK_USUARIO='"+obj.getId()+"'";
	       db.execQuery(sql); 
	       
	       sql = "DELETE FROM ROLES_USUARIO WHERE PK_USUARIO='"+obj.getId()+"'";
	       db.execQuery(sql); 
	       
	       sql = "DELETE FROM USUARIOS_DIRECCION WHERE PK_USUARIO='"+obj.getId()+"'";
	       db.execQuery(sql); 
	 
	       sql = "DELETE FROM USUARIOS WHERE PK1='"+obj.getId()+"'";
	       db.execQuery(sql); 	   	
	   	
		
			
  }
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
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
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String string) {
		this.password = string;
	}

	public int getIdrole() {
		return idrole;
	}

	public void setIdrole(int idrole) {
		this.idrole = idrole;
	}

}
