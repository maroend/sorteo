package com.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SesionDatos {
	public long PK1;
	public final long idSesion;
	public final int pkUsuario;
	public String nickName;
	public String nombreCompleto;
	public String role;
	private Date timeAcceso;
	public int pkSorteo;
	public int pkSector;
	public int pkNicho;
	public int pkColaborador;
	public int pkBoleto;
	//public int pkTalonario;
	public int misSorteos;
	public int data1; // Usado para guardar el porcentaje de avance de una carga
	public int data2; // NO USED
	public int data3; // NO USED
	public boolean sorteoActivo;
	public int idMenu;
	public int idSubMenu;
	public String servlet;
	public Privileges permisos;
	public HttpServletRequest request;
	public HttpServletResponse response;
	
	public static final int limite_dia = 0;
	public static final int limite_hora = 10; // 5
	public static final int limite_minuto = 0;
	public static final int limite_segundo = 0;
	
	public Database db = null;
	
	// TODO  Constructor auxiliar
	public SesionDatos(int p_pkUsuario, Database db, HttpServletRequest request, HttpServletResponse response) {
		this.PK1 = 0L;
		this.idSesion = SesionDatos.creaIdSession();
		this.pkUsuario = p_pkUsuario;
		
		// Datos de entorno
		this.pkSorteo = this.pkSector = this.pkNicho = this.pkColaborador = this.pkBoleto /*= this.pkTalonario*/ = -1;
		this.idMenu = 1; // Por default : el Dashboard
		this.idSubMenu = 0;
		this.misSorteos = 0;
		this.sorteoActivo = false;
		this.db = db;
		this.request = request;
		this.response = response;
		this.data1 = this.data2 = this.data3 = 0;
	}//<end>
	
	// TODO  Constructor por consulta.
	public SesionDatos(ResultSet res, Database db, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		this.PK1 = res.getLong("PK1");
		this.idSesion = res.getLong("ID");
		this.pkUsuario = res.getInt("PK_USUARIO");
		this.timeAcceso = SesionDatos.unionDateTime(res.getDate("HORA_ACCESO"), res.getTime("HORA_ACCESO"));
		this.pkSorteo = res.getInt("PK_SORTEO");
		this.pkSector = res.getInt("PK_SECTOR");
		this.pkNicho = res.getInt("PK_NICHO");
		this.pkColaborador = res.getInt("PK_COLABORADOR");
		this.idMenu = res.getInt("ID_MENU");
		this.idSubMenu = res.getInt("ID_SUBMENU");
		this.pkBoleto = res.getInt("PK_BOLETO");
		//this.pkTalonario = res.getInt("PK_TALONARIO");
		this.misSorteos = res.getInt("MIS_SORTEOS");
		this.sorteoActivo = (boolean)(res.getInt("SORTEO_ACTIVO") == 1);
		this.data1 = res.getInt("DATA1");
		this.data2 = res.getInt("DATA2");
		this.data3 = res.getInt("DATA3");
		// _______________________
		this.nickName = res.getString("NICKNAME");
		this.nombreCompleto = res.getString("NOMBRE_COMPLETO");
		this.role = res.getString("ROLE");
		this.db = db;
		this.request = request;
		this.response = response;
		res.close();
	}//<end>
	
	public void set(SesionDatos sesion) {
		this.PK1 = sesion.PK1;
		this.timeAcceso = sesion.timeAcceso;
		this.pkSorteo = sesion.pkSorteo;
		this.pkSector = sesion.pkSector;
		this.pkNicho = sesion.pkNicho;
		this.pkColaborador = sesion.pkColaborador;
		this.idMenu = sesion.idMenu;
		this.idSubMenu = sesion.idSubMenu;
		this.pkBoleto = sesion.pkBoleto;
		//this.pkTalonario = sesion.pkTalonario;
		this.servlet = sesion.servlet;
		this.nickName = sesion.nickName;
		this.nombreCompleto = sesion.nombreCompleto;
		this.role = sesion.role;
		this.misSorteos = sesion.misSorteos;
		this.sorteoActivo = sesion.sorteoActivo;
		this.data1 = sesion.data1;
		this.data2 = sesion.data2;
		this.data3 = sesion.data3;
		
		this.db = (this.db != null) ? this.db : sesion.db;
		this.request = sesion.request;
		this.response = sesion.response;
	}//<end>
	
	// TODO  guardaSesion
	public void guardaSesion() {
		// Primero se consulta si ya hay una sesion previa
		SesionDatos otraSesion = SesionDatos.getSesionPrevia(pkUsuario, db, this.request, this.response);
		if (otraSesion == null) {
			// Si NO hay sesion se inserta una nueva en la BD.
			this.insertaNuevaSesion();
			otraSesion = SesionDatos.getSesionPrevia(pkUsuario, db, this.request, this.response);
			if (otraSesion != null)
				// Se reasignan los datos de sesion debido a que hay valores automaticos en la BD.
				this.set(otraSesion);
		}
		else {
			this.actualizaSesion();
		}
	}//<end>
	
	// TODO  creaNuevaSesion
	public void creaNuevaSesion(HttpServletRequest request, HttpServletResponse response) {

		SesionDatos.limpiaSesiones(pkUsuario, db);
		
		// Al insertar nueva sesion se crea automaticamente el id, el cual es necesario consultar
		this.insertaNuevaSesion();
		
		SesionDatos otraSesion = SesionDatos.getSesionPrevia(pkUsuario, db, request, response);
		if (otraSesion != null) {
			// Se reasignan los datos de sesion debido a que hay valores automaticos en la BD.
			//this.set(otraSesion);
			this.PK1 = otraSesion.PK1;
		}
	}//<end>
	
	// TODO  insertaNuevaSesion
	private int insertaNuevaSesion()
	{
		String sql = new StringBuilder()
			.append("INSERT INTO [dbo].[SESION]")
			.append(" (ID,PK_USUARIO,PK_SORTEO,PK_SECTOR,PK_NICHO,PK_COLABORADOR,ID_MENU, ID_SUBMENU, PK_BOLETO, MIS_SORTEOS, DATA1,DATA2,DATA3)")
			.append(" VALUES (")
			.append(idSesion).append(",")
			.append(pkUsuario).append(",")
			.append(pkSorteo).append(",")
			.append(pkSector).append(",")
			.append(pkNicho).append(",")
			.append(pkColaborador).append(",")
			.append(idMenu).append(",")
			.append(idSubMenu).append(",")
			.append(pkBoleto).append(",")
		//	.append(pkTalonario).append(",")
			.append(misSorteos).append(",")
			.append(data1).append(",")
			.append(data2).append(",")
			.append(data3).append(")")
			.toString();
		
		return db.execQuery(sql);
	}//<end>
	
	// TODO  actualizaSesion
	public void actualizaSesion()
	{
		String sql = new StringBuilder()
			.append("UPDATE SESION")
			.append(" SET PK_SORTEO=").append(this.pkSorteo)
			.append(", PK_SECTOR=").append(this.pkSector)
			.append(", PK_NICHO=").append(this.pkNicho)
			.append(", PK_COLABORADOR=").append(this.pkColaborador)
			.append(", ID_MENU=").append(this.idMenu)
			.append(", ID_SUBMENU=").append(this.idSubMenu)
			.append(", PK_BOLETO=").append(this.pkBoleto)
		//	.append(", PK_TALONARIO=").append(this.pkTalonario)
			.append(", MIS_SORTEOS=").append(this.misSorteos)
			.append(", DATA1=").append(this.data1)
			.append(", DATA2=").append(this.data2)
			.append(", DATA3=").append(this.data3)
			.append(", FECHA_M=GETDATE()")
			
			.append(" WHERE (PK1=").append(this.PK1)
			.append(" AND PK_USUARIO=").append(this.pkUsuario)
			.append(" AND ID=").append(this.idSesion).append(")")
			.toString();
		
		db.execQuery(sql);
	}//<end>
	
	public boolean esFinDeSesion()
	{
		Date now = Calendar.getInstance().getTime();
		
		TimeSpan transcurrido = new TimeSpan(this.timeAcceso, now);
		TimeSpan limite = new TimeSpan(limite_dia, limite_hora, limite_minuto, limite_segundo);
		
		// Si el tiempo transcurrido es menor que el limite ... Aun no caduca la sesion.
		if(transcurrido.isShorterThat(limite))
			return false;
			
		return true;
	}//<end>
	
	public boolean checaParametros(String args_extra) {
		if (args_extra != null) {
			String[] array_args = args_extra.split("[,]");
			if (3 <= array_args.length)
				try {
					this.servlet = array_args[0];
					this.idMenu = Integer.parseInt(array_args[1]);
					this.idSubMenu = Integer.parseInt(array_args[2]);
					return true;
				} catch (Exception ex) { }
		}
		return false;
	}
	
	public void cierra(){
		SesionDatos.limpiaSesiones(pkUsuario, db);
	}
	
	public String toString() {
		return "sesion{SOR=" + this.pkSorteo + ",sec=" + this.pkSector + ",ni=" + this.pkNicho + ",co=" + this.pkColaborador
				+ ", bo=" + this.pkBoleto //+ ", ta=" + this.pkTalonario
				+ ", mis="+ (misSorteos!=0) + ", act="+sorteoActivo+", usr=" + pkUsuario + ", data1:" + data1
				+ ", menu=[" + this.idMenu + "," + this.idSubMenu + "]}";
	}
	
	public String toShortString() {
		return "{" + this.pkSorteo + "," + this.pkSector + "," + this.pkNicho + "," + this.pkColaborador + "}";
	}
	
	public String getUsuario() {
		return this.nickName;
	}
	
	
	
	
	// _______________________________________________________________
	//
	//                       Funciones_estaticas
	// _______________________________________________________________
	
	public static SesionDatos afterLogIn(String nickname, Database db, HttpServletRequest request, HttpServletResponse response) {
		SesionDatos sesion;

		db.con();
		// Se consulta el nombre.
		int pkUsuario = getPKUsuario(nickname, db);
		if (pkUsuario != -1) {
			// Se crea una sesion auxiliar en memoria
			sesion = new SesionDatos(pkUsuario, db, request, response);
			sesion.guardaSesion();
			return sesion;
		}
		return null;
	}//<end>

	// TODO  start
	public static SesionDatos start(HttpServletRequest request, HttpServletResponse response,boolean login, SuperModel model){
		// Se busca la cookie de inicio de sesion.
		Cookie[] arrCookie = request.getCookies();
		if (arrCookie != null){
			for(Cookie cookie : arrCookie) {
				if(cookie.getName().equals("datosdesesion")) {
					String datosdesesion = cookie.getValue();
					String[] array = datosdesesion.split("[_]");
					if (array != null && 3 <= array.length) {
						// Hasta aqui ya se obtubo la coookie con los datos de sesion
						long pkUsuario = Long.parseLong(array[2]);
						
						SesionDatos sesion = SesionDatos.getSesionPrevia(pkUsuario, model.db, request, response);
						
						// Si ya habia una sesion previa
						if (sesion != null) {
							// Se checa si la sesion NO ha caducado
							if (sesion.esFinDeSesion() == false) {
								sesion.permisos = new Privileges();
								sesion.permisos.getPermisosXRolesUsuario(sesion.pkUsuario, model.db);
								System.out.println(sesion.toString());
								return sesion;
							}
							else {
								//SesionDatos.limpiaSesiones(sesion.pkUsuario, null);
								break;
							}
						}
					}
					break;
				}
			}
		}
		if (login==false)
		{
			// En este punto no se encontro ningun dato para rescatar la sesion
			
			// Se solicita el servlet con los parametros
			String servletToInvoke = SesionDatos.getServletToInvoke(request, "ssoOffice365.html", model.db);
			if (servletToInvoke == null)
				//servletToInvoke = "Login.do?args=Dashboard.do,0,0&action=reset";
				servletToInvoke = "ssoOffice365.html";
			try {
				response.sendRedirect(servletToInvoke);
			}catch(Exception ex) { }
		}
		model.close();
		return null;
	}//<end>
	
	public static int getPKUsuario(String nickname, Database db) {
		try {
			String sql = "SELECT [PK1],CONCAT([NOMBRE],' ',[APATERNO],' ',[AMATERNO]) AS 'NOMBRE_COMPLETO' FROM [dbo].[USUARIOS] WHERE USUARIO='" + nickname + "'";
			ResultSet res = db.getDatos(sql);
			if (res != null && res.next()) {
				int pkUsuario = res.getInt("PK1");
				res.close();
				return pkUsuario;
			}
		}catch(SQLException ex) { }
		return -1;
	}//<end>
	
	public static String getCurrentServletName(HttpServletRequest request) {
		String servletInvoked = request.getServletPath();
		int indexDiagonal = servletInvoked.lastIndexOf("/");
		if(0 <= indexDiagonal)
			servletInvoked = servletInvoked.substring(indexDiagonal+1, servletInvoked.length());
		return servletInvoked;
	}//<end>
	
	private static ArrayList<String> consultarIdServlet(HttpServletRequest request, Database db) {
		String servletInvoked = getCurrentServletName(request);

		try {
			String sql = "SELECT [URL] AS 'SERVLET',[PADRE] AS 'MENU', [PK1] AS 'SUBMENU' from MENU WHERE URL='" + servletInvoked + "'";
			
			ResultSet res = db.getDatos(sql);
			if (res != null && res.next()) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(res.getString("SERVLET"));
				list.add(res.getString("MENU"));
				list.add(res.getString("SUBMENU"));
				return list;
			}
		}catch (SQLException ex) { }

		return null;
	}//<end>
	
	public static String getServletToInvoke(HttpServletRequest request, String otherServlet, Database db) {
		ArrayList<String> list = consultarIdServlet(request, db);
		if (list != null && 3 <= list.size())
			if (otherServlet == null)
				return list.get(0) + "?args=" + list.get(0) + "," + list.get(1) + "," + list.get(2);
			else
				return otherServlet + "?args=" + list.get(0) + "," + list.get(1) + "," + list.get(2);
		return null;
	}//<end>
	
	public static SesionDatos getSesionPrevia(long pkUsuario, Database db, HttpServletRequest request, HttpServletResponse response) {
		// SUPER CONSULTA
		String sql = new StringBuilder()
			.append("SELECT [PK1],[ID],[PK_USUARIO],[HORA_ACCESO],[PK_SORTEO],[PK_SECTOR],[PK_NICHO],[PK_COLABORADOR],[ID_MENU],[ID_SUBMENU],[PK_BOLETO],[MIS_SORTEOS],[DATA1],[DATA2],[DATA3]")
			.append(",(SELECT TOP 1 CONCAT([NOMBRE],' ',[APATERNO],' ',[AMATERNO]) FROM USUARIOS WHERE PK1=").append(pkUsuario).append(") AS 'NOMBRE_COMPLETO'")
			.append(",(SELECT [USUARIO] FROM USUARIOS WHERE PK1=").append(pkUsuario).append(") AS 'NICKNAME'")
			.append(",(SELECT TOP 1 ROLE FROM ROLES_USUARIO RU, ROLES R WHERE RU.PK_ROLE = R.PK1 AND RU.PK_USUARIO=").append(pkUsuario).append(") AS 'ROLE'")
			.append(",(SELECT SO.BLOQUEO FROM SORTEOS SO WHERE SO.PK1=PK_SORTEO) AS 'SORTEO_ACTIVO'")
			.append(" FROM SESION WHERE PK_USUARIO=").append(pkUsuario)
			.toString();
		
		ResultSet res = db.getDatos(sql);
		try {
			if (res != null && res.next())
				return new SesionDatos(res, db, request, response);
		} catch(Exception ex) { }
		return null;
	}//<end>
	
	public static int limpiaSesiones(long pkUsuario, Database db) {
		String sql = "DELETE FROM SESION WHERE PK_USUARIO = " + pkUsuario;
		return db.execQuery(sql);
	}//<end>
	
	public static Date unionDateTime(Date date, Date time) {
		Calendar cal_date = Calendar.getInstance();
		Calendar cal_time = Calendar.getInstance();
		cal_date.setTime(date);
		cal_time.setTime(time);
		Calendar cal_3 = Calendar.getInstance();
		cal_3.set(
				cal_date.get(Calendar.YEAR),
				cal_date.get(Calendar.MONTH),
				cal_date.get(Calendar.DAY_OF_MONTH),
				cal_time.get(Calendar.HOUR_OF_DAY),
				cal_time.get(Calendar.MINUTE),
				cal_time.get(Calendar.SECOND));
		return cal_3.getTime();
	}//<end>

	public static long creaIdSession() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT-06")).getTime().getTime();
	}//<end>
	
	public static int limiteDeTiempo() {
		return ((SesionDatos.limite_dia * 24 + SesionDatos.limite_hora) * 60 + SesionDatos.limite_minuto) * 60 + SesionDatos.limite_segundo;
	}//<end>

}//<end class>



