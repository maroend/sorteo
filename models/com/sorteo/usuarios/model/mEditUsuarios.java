package com.sorteo.usuarios.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.SuperModel;


public class mEditUsuarios extends SuperModel {

	//private static final boolean TRUE = false;

	private int id;      //PRIMARY KEY DEL USUARIO
	
	private String nombre;  //
	private String apellidop;  //
	private String apellidom;  //
	
	private String edad;  //
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
	
	
	public mEditUsuarios() {
		
		init();
	}
	
	public void init(){
		
		
		 this.usuario = "";
		 this.id = 0;
		 this.nombre = "";
		 this.apellidop = "";
		 this.apellidom = "";	
		 this.password = "";		
		 this.telefonocasa = "";
		 this.telefonomovil = "";
		 this.telefonooficina = "";
		 this.usuario = "";	 
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
		 this.numero = "";	
		
	}

	public ResultSet listar() {

		String sql = "SELECT * FROM USUARIOS";
		ResultSet rs = db.getDatos(sql);
		return rs;

	}

	public boolean ExisteUsuario(String usr) {
		String sql = "SELECT PK1 FROM USUARIOS WHERE USUARIO = '" + usr + "'";
		ResultSet rs = db.getDatos(sql);
		// return rs;

		try {
			if (rs.next()) {

				return true;

			} else {

				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String ObtenerU(int idusuario){
	   	
	   	String sql = "SELECT USUARIO FROM USUARIOS WHERE PK1 =  '"+idusuario+"'";
	   
	   	ResultSet rs = db.getDatos(sql);
		String usuario="";
	   	
	   	try {
	   		
			if(rs.next()) {				
				  
				usuario += rs.getString("USUARIO");
				  
			  
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	   	return usuario;
	   	
	}

	public void ObtenerUsuario(mEditUsuarios u){
	   	
	   	String sql = "SELECT * FROM USUARIOS WHERE PK1 =  '"+u.getId()+"'";
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	
	   	try {
			if(rs.next()) {
				
				   u.setNombre(rs.getString("NOMBRE"));
				   u.setUsuario(rs.getString("USUARIO"));
				   u.setApellidop(rs.getString("APATERNO"));
				   u.setApellidom(rs.getString("AMATERNO"));
				   u.setEdad(rs.getString("EDAD"));
				   u.setRfc(rs.getString("RFC"));	
				   u.setPassword(rs.getString("PASSWORD"));	
				
			  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ObtenerTelefono(mEditUsuarios u){
	   	
	   	//String sql = "SELECT * FROM USUARIOS_TELEFONOS WHERE USUARIO =  '"+u.getUsuario()+"' ";
		
		String sql = "SELECT * FROM USUARIOS WHERE USUARIO =  '"+u.getUsuario()+"' ";
		
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	
	   	try {
			
	   		  while(rs.next()) {	
				
				// if(rs.getString("TELEFONO_P").equals("C") ){
					
					 u.setTelefonocasa(rs.getString("TELEFONO_P"));
					
			   // }
				// else if(rs.getString("TIPO").equals("T")){
					 
					 u.setTelefonooficina(rs.getString("TELEFONO_S")); 
					 
				// }else{
					 
					// u.setTelefonomovil("");		
					 
				// } 			
			 
				
			  
			}
	   		  
	   		  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	}

	public void ObtenerDireccion(mEditUsuarios u){
	   	
	   	String sql = "SELECT * FROM USUARIOS_DIRECCION WHERE PK_USUARIO =  '"+u.getId()+"' ";
	   	
	   	ResultSet rs = db.getDatos(sql);
	   	
	   	try {
			if(rs.next()) {	
				
				   u.setCp(Integer.valueOf(rs.getString("CP")));
				   u.setPais(rs.getString("PAIS"));
				   u.setEstado(rs.getString("ESTADO"));
				   u.setMundel(rs.getString("MUNDEL"));		 
				   u.setColonia(rs.getString("COLONIA"));
				   u.setCalle(rs.getString("CALLE"));
				   u.setNumero(rs.getString("NUMEXT"));	
			  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
	public void ObtenerCorreo(mEditUsuarios u) {

	//	String sql = "SELECT * FROM USUARIOS_CORREOS WHERE USUARIO =  '"+ u.getUsuario() + "' ";
		
		
		String sql = "SELECT * FROM USUARIOS WHERE USUARIO =  '"+ u.getUsuario() + "' ";
		
		
		

		ResultSet rs = db.getDatos(sql);

		try {

			while (rs.next()) {

			//	if (rs.getString("TIPO").equals("P")) {

					u.setCorreopersonal(rs.getString("CORREO_P"));
				//} else if (rs.getString("TIPO").equals("T")) {
					u.setCorreotrabajo(rs.getString("CORREO_S"));
				//} else {
					u.setCorreootro("");
				//}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
   
   public void ObtenerRedes(mEditUsuarios u){
	   	
	   	String sql = "SELECT * FROM USUARIOS_REDES_SOCIALES WHERE USUARIO =  '"+u.getUsuario()+"' ";
	   	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
   
   
	public int registrar(mEditUsuarios obj){
		db.con();
		
		//  String sql = "INSERT INTO USUARIOS (USUARIO,PASSWORD,NOMBRE,APATERNO,AMATERNO,ACTIVO,PK_USUARIO,FECHA_R) VALUES ('"+obj.getUsuario()+"','"+obj.getPassword()+"','"+obj.getNombre()+"','"+obj.getApellidop()+"','"+obj.getApellidom()+"','1','admin','2015-02-11 12:36:09.000')";

		String sql = "UPDATE USUARIOS SET EDAD = '" + obj.getEdad()
				+ "', RFC = '" + obj.getRfc() + "', NOMBRE = '"
				+ obj.getNombre() + "', APATERNO =  '" + obj.getApellidop()
				+ "', AMATERNO = '" + obj.getApellidom() + "' WHERE PK1 =  '"
				+ obj.getId() + "' ";

		System.out.println(sql);
		int res = db.execQuery(sql);
		db.desconectar();
		
	    //OBTENER ID
	    
	  /*  sql = "SELECT @@IDENTITY AS NewSampleId";	
	    
	    
	    
	   	ResultSet rs = db.getDatos(sql);  
	   	
try {  
	    rs.next();	    
	    
        int id = rs.getInt(1);	//COLUMNA PK1   
	   // int id = rs.getInt("NewSampleId");
		u.setId(id);			 
   }
   catch (Exception e) {
       e.printStackTrace();}   */ 
	    
	  //TELEFONOS	

		//String sql2 = "DELETE FROM USUARIOS_TELEFONOS WHERE USUARIO =  '"+obj.getUsuario()+"' ";
		//db.execQuery(sql2);   
		
		registrar_usuario_Telefonos(obj.getUsuario(),obj.getTelefonocasa(),obj.getTelefonooficina()); 
	
		
	//	registrar_usuario_Telefonos(obj.getUsuario(),1,obj.getTelefonocasa(),'C'); 
		//registrar_usuario_Telefonos(obj.getUsuario(),2,obj.getTelefonooficina(),'T'); 
		//registrar_usuario_Telefonos(obj.getUsuario(),3,obj.getTelefonomovil(),'M');
		
		//DIRECCION     
		registrar_usuario_Direccion(obj);
		
		
		//CORREOS
		//String sql3 = "DELETE FROM USUARIOS_CORREOS WHERE USUARIO =  '"+obj.getUsuario()+"' ";
		//db.execQuery(sql3);
		
		registrar_usuario_Correo(obj.getUsuario(),obj.getCorreopersonal(),obj.getCorreotrabajo()); 
		//registrar_usuario_Correo(obj.getUsuario(),2,obj.getCorreotrabajo(),'T');     
		//if(obj.getCorreootro().trim() != "" && obj.getCorreootro().trim() != null  ){ registrar_usuario_Correo(obj.getUsuario(),3,obj.getCorreootro(),'O');}
		
		
		//REDES SOCIALES
		/*String sql4 = "DELETE FROM USUARIOS_REDES_SOCIALES WHERE USUARIO =  '"+obj.getUsuario()+"' ";
		db.execQuery(sql4);
		if(obj.getFacebook().trim() != "" && obj.getFacebook().trim() != null){  registrar_usuario_RedSocial(obj.getUsuario(),1,obj.getFacebook());}
		if(obj.getTwitter().trim() != "" && obj.getTwitter().trim() != null){  registrar_usuario_RedSocial(obj.getUsuario(),2,obj.getTwitter()); }
		if(obj.getRedotro().trim() != "" &&obj.getRedotro().trim() != null){  registrar_usuario_RedSocial(obj.getUsuario(),3,obj.getRedotro()); }*/
    
     
        init();		
		return res;
	}

	public int contar() {

		String sql = "SELECT * FROM USUARIOS";
		int numero = db.ContarFilas(sql);

		return numero;

	}

	public ResultSet paginacion(int pg, int numreg,String search){
	   	
	   String sql = "SELECT * ";
	          sql += "FROM USUARIOS ";
	          
	          if(search!=""){
	        	  sql += " WHERE NOMBRE LIKE '%"+search+"%'  ";
	          }
	          
	          sql += "ORDER BY PK1 ASC ";
	          sql += "OFFSET ("+(pg-1)*numreg+") ROWS "; //-- not sure if you need -1
	          sql += "FETCH NEXT "+numreg+" ROWS ONLY";

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


	/*public int registrar_usuario_Telefonos(String usr,int secuencia,String telefono,char tipo){
		db.con();
		
		String sql = "INSERT INTO USUARIOS_TELEFONOS (USUARIO,SECUENCIA,TELEFONO,TIPO,FECHA_R) VALUES ('"+usr+"','"+secuencia+"','"+telefono+"','"+tipo+"','2015-02-11 12:36:09.000')";
		//String sql = "UPDATE USUARIOS_TELEFONOS SET SECUENCIA = '"+secuencia+"', TELEFONO = '"+telefono+"', TIPO =  '"+tipo+"' WHERE USUARIO =  '"+usr+"'  ";
	    System.out.println(sql);
	    int res = db.execQuery(sql);
	    db.desconectar();
		return res;
	}*/

	public int registrar_usuario_Correo(String usr,String correop,String correosec){
		/*db.con();		
		
	    String sql = "INSERT INTO USUARIOS_CORREOS (USUARIO,SECUENCIA,CORREO,TIPO,FECHA_R) VALUES ('"+usr+"','"+secuencia+"','"+correo+"','"+tipo+"','2015-02-11 12:36:09.000')";
		//String sql = "UPDATE USUARIOS_CORREOS SET SECUENCIA = '"+secuencia+"', CORREO = '"+correo+"', TIPO =  '"+tipo+"'  WHERE USUARIO =  '"+usr+"'   ";
	    System.out.println(sql);
	    int res = db.execQuery(sql);
	    db.desconectar();
		return res;*/
		db.con();
		
		  
			String sql = "UPDATE USUARIOS SET CORREO_P = '"+correop+"', CORREO_S = '"+correosec+"'  WHERE USUARIO =  '"+usr+"' ";
		    System.out.println(sql);
		    int res = db.execQuery(sql);
		    db.desconectar();
			return res;
		
		
		
		
	}

	public int registrar_usuario_RedSocial(String usr,int secuencia,String red){
		db.con();		
		
	    String sql = "INSERT INTO USUARIOS_REDES_SOCIALES (USUARIO,SECUENCIA,RED,FECHA_R) VALUES ('"+usr+"','"+secuencia+"','"+red+"','2015-02-11 12:36:09.000')";
		//String sql = "UPDATE USUARIOS_REDES_SOCIALES SET RED = '"+red+"', SECUENCIA = '"+secuencia+"'  WHERE USUARIO =  '"+usr+"'  ";
	    System.out.println(sql);
	    int res = db.execQuery(sql);
	    db.desconectar();
		return res;
	}

	public int registrar_usuario_Direccion(mEditUsuarios u){
		db.con(); 
		
		
		String sql = "";
		int res=0;
		
		    sql = "SELECT PK1 FROM USUARIOS_DIRECCION WHERE PK_USUARIO ='"+u.getId()+"' ";		   	
		    ResultSet rs = db.getDatos(sql);

		try {

			if (rs.next()) {

			      sql = "UPDATE USUARIOS_DIRECCION SET PAIS = '"+u.getPais()+"', CP = '"+u.getCp()+"', ESTADO =  '"+u.getEstado()+"', MUNDEL = '"+u.getMundel()+"',  COLONIA = '"+u.getColonia()+"', CALLE = '"+u.getCalle()+"', NUMEXT = '"+u.getNumero()+"' WHERE PK_USUARIO =  '"+u.getId()+"' ";
	              System.out.println(sql);
	              res = db.execQuery(sql);

			} else {

				  sql = "INSERT INTO USUARIOS_DIRECCION (PK_USUARIO,PAIS,CP,ESTADO,MUNDEL,COLONIA,CALLE,NUMEXT) VALUES ('"+u.getId()+"','"+u.getPais()+"','"+u.getCp()+"','"+u.getEstado()+"','"+u.getMundel()+"','"+u.getColonia()+"','"+u.getCalle()+"','"+u.getNumero()+"')";
				  System.out.println(sql);
				  res = db.execQuery(sql);
						}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	    db.desconectar();
		return res;
	}

	public void Obtener_Direccion(mEditUsuarios u){
   	
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

	public String Obtener_Colonia(){
	   	
	 	String colonia="";
		if(this.getCp()!=0){
			
			
		 	String sql = "SELECT d_asenta FROM SEPOMEX WHERE  (d_codigo LIKE N'%"+this.getCp()+"%')  GROUP BY d_asenta";
		   	ResultSet rs = db.getDatos(sql);
		  
		   	
		   	try {
				while(rs.next()) {
					colonia += "<option>"+rs.getString("d_asenta")+"</option>";
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else{
			
			colonia = "<option></option>";
		}			
		
		
	  

		return colonia;
	}

	public int eliminar(mEditUsuarios obj) {

		db.con();
		String sql = "DELETE FROM USUARIOS WHERE PK1='" + obj.getId() + "'";
		int res = db.execQuery(sql);
		return res;

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

}
