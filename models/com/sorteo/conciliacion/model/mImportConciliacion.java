package com.sorteo.conciliacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.core.SesionDatos;
import com.core.SuperModel;

public class mImportConciliacion extends SuperModel {
	private int idSorteo;
	private int idSector;
	private int idNicho;
	private int idUsuario;
	private long idColaborador;
	
	private String sorteo;
	
	private String cuenta;
	private String fecha;
	private String hora;
	private String sucursal;
	private String descripcion;
	private String cargo;
	private double importe;
	private double saldo;
	private String referencia;
	private String referenciaIB;
	private String concepto;
	
	
	private String claveSector;
	private String claveNicho;
	private String claveColaborador;
	private String fecha_abono;
	
	private String status;
	private String operacion;
	
	
	private int numRegistrados;
	private int numWarning;
	private int numErrores;
	
	private String FECHA_R;
	
	public int conciliacion_orden;
	public int conciliacion_boletos_orden;

	public mImportConciliacion() {
		conciliacion_orden = 0;
		conciliacion_boletos_orden = 0;
	}
	
	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta.trim();
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha.trim();
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora.trim();
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal.trim();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo.trim();
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia.trim();
	}

	public String getReferenciaIB() {
		return referenciaIB;
	}

	public void setReferenciaIB(String referenciaIB) {
		this.referenciaIB = referenciaIB.trim();
	}


	public int getIdSorteo() {
		return idSorteo;
	}

	public void setIdSorteo(int idsorteo) {
		this.idSorteo = idsorteo;
	}
	/* */
	public int getIdSector() {
		return idSector;
	}
	//*/
	public void setIdSector(int idsector) {
		this.idSector = idsector;
	}

	public int getIdNicho() {
		return idNicho;
	}

	public void setIdNicho(int idnicho) {
		this.idNicho = idnicho;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getSorteo() {
		return sorteo;
	}

	public void setSorteo(String sorteo) {
		this.sorteo = sorteo.trim();
	}
	
	
	
	public String getClaveSector() {
		return claveSector;
	}

	public void setClaveSector(String claveSector) {
		this.claveSector = claveSector.trim();
	}

	public String getClaveNicho() {
		return claveNicho;
	}

	public void setClaveNicho(String claveNicho) {
		this.claveNicho = claveNicho.trim();
	}

	public String getClaveColaborador() {
		return claveColaborador;
	}

	public void setClaveColaborador(String claveColaborador) {
		this.claveColaborador = claveColaborador.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status.trim();
	}
	
	public long getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(long idColaborador) {
		this.idColaborador = idColaborador;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto.trim();
	}
	
	

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion.trim();
	}

	public int getNumRegistrados() {
		return numRegistrados;
	}

	public void setNumRegistrados(int numregistrados) {
		this.numRegistrados = numregistrados;
	}

	public int getNumWarning() {
		return numWarning;
	}

	public void setNumWarning(int numwarning) {
		this.numWarning = numwarning;
	}

	public int getNumErrores() {
		return numErrores;
	}

	public void setNumErrores(int numerrores) {
		this.numErrores = numerrores;
	}

	public boolean processRow(SesionDatos sesion){
		// TODO processRow

		this.setOperacion("");
		
		boolean result = false;
		int longreference = 20;
		String referenciaIB = this.getReferenciaIB();

		if (StringUtils.isNumericSpace(referenciaIB)) {

			if (referenciaIB.length() == longreference) {
				this.setClaveColaborador(referenciaIB.substring(11, 19));
				this.setClaveNicho(referenciaIB.substring(7, 11));
				this.setClaveSector(referenciaIB.substring(4, 7));
				this.setReferenciaIB(referenciaIB.substring(4, 20));
				this.crearFechaAbono();

				System.out.println("COLABORADOR CLAVE:" + this.getClaveColaborador());
				System.out.println("NICHO CLAVE:" + this.getClaveNicho());
				System.out.println("SECTOR CLAVE:" + this.getClaveSector());

				if (buscarColaboradoresXReferenciaBancaria()) {
					//if (existeColaborador())
					if (validarColaborador())
					{
						//if (ObtenerSectorNicho())
						{

							this.InsertarRegistros(sesion);
							result = true;
						}
					}
				}
			} else {
				this.setNumErrores(this.getNumErrores() + 1);
				System.out.println("ERROR: CLAVE INTERBANCARIA FUERA DE RANGO");
				this.setStatus("ERROR: CLAVE INTERBANCARIA FUERA DE RANGO");
				this.setOperacion("error");
			}

		} else {
			this.setNumErrores(this.getNumErrores() + 1);
			this.setStatus("ERROR: CLAVE INTERBANCARIA NO ES NUMERICA");
			this.setOperacion("error");
			System.out.println("ERROR: CLAVE INTERBANCARIA NO ES NUMERICA");
		}

		return result;
	}
	
	/*
	private boolean ValidarReferenciaBancariaColaborador(){
		
		boolean result= false;
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM COLABORADORES WHERE PK_SORTEO=" + this.getIdsorteo() + " AND CLAVE = '" + this.getClaveColaborador()+"' AND REFBANCARIA = '"+this.getReferenciaIB()+"'";
		int max = 0;
		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
				if (max > 0) {
					result = true;
				} else {
					this.setNumErrores(this.getNumErrores() + 1);
					this.setStatus("ERROR: LA REFERENCIA BANCARIA NO COINCIDE CON EL COLABORADOR EN EL SISTEMA");
					System.out.println(this.getStatus());
					this.setOperacion("error");
				}
			}
		}catch (SQLException e) { }
		
		return result;
		
	}
	*/
	
	/*
	private boolean buscarReferenciaBancariaXColaborador(){

		boolean result = false;
		String sql = "SELECT PK1,CLAVE FROM COLABORADORES"
				+ " WHERE PK_SORTEO=" + this.getIdSorteo()
				+ " AND REFBANCARIA = '" + this.getReferenciaIB() + "'";
		try {
			ResultSet res = db.getDatos(sql);
			if (res != null && res.next())
			{
				this.setIdColaborador(res.getInt("PK1"));
				this.setClaveColaborador(res.getString("CLAVE"));
				
				if (res.next() == false)
					result = true;
				else {
					this.setNumErrores(this.getNumErrores() + 1);
					this.setStatus("ERROR: LA REFERENCIA BANCARIA ESTA DUPLICADA, NO ES POSIBLE IDENTIFICAR EL COLABORADOR.");
					System.out.println(this.getStatus());
					this.setOperacion("error");
				}
			}
			else {
				this.setNumErrores(this.getNumErrores() + 1);
				this.setStatus("ERROR: LA REFERENCIA NO SE ENCONTRO EN EL SISTEMA");
				System.out.println(this.getStatus());
				this.setOperacion("error");
			}
		}
		catch (Exception e) { }
		
		return result;
		
	}
	*/

	private ArrayList<Colaborador> listColaborador;
	
	// TODO buscarReferenciaBancariaXColaborador
	private boolean buscarColaboradoresXReferenciaBancaria() {
		/*
		 * 0030002321242026  - 
		 * 0080001002865005  - clave diferente
		 * 0040020000171553  - Normal, 'Becario y 'Bachillerato Altamira'
		 * */
		boolean result = false;
		
		String sql
				= "SELECT * \n"
				+ "  FROM( \n"
				+ "    SELECT C.PK1,C.CLAVE,CONCAT(C.NOMBRE,', ',C.APATERNO,' ',C.AMATERNO) AS 'NOMBRE',S.PK1 AS 'PK_SECTOR',S.CLAVE AS 'CLAVE_SECTOR',N.PK1 AS 'PK_NICHO',N.CLAVE AS 'CLAVE_NICHO', N.NICHO \n"
				+ "    ,(SELECT CAST((ISNULL(SUM(B.COSTO),0)*(1-C.COMISION/100.0)) as decimal(18,2)) AS 'COSTO' FROM SORTEOS_COLABORADORES_BOLETOS SCB, BOLETOS B WHERE SCB.PK_BOLETO=B.PK1 AND SCB.PK_COLABORADOR=C.PK1 AND SCB.PK_NICHO=N.PK1) AS 'COSTO' \n"
				+ "    ,C.ABONO \n"
				+ "    ,(SELECT COUNT(*) FROM SORTEOS_COLABORADORES_TALONARIOS SCT WHERE SCT.PK_COLABORADOR=C.PK1 AND SCT.PK_NICHO=N.PK1) AS 'TALONARIOS' \n"
				+ "    FROM COLABORADORES C, SORTEOS_ASIGNACION_COLABORADORES SAC, SECTORES S, NICHOS N \n"
				+ "    WHERE SAC.PK_COLABORADOR=C.PK1 AND SAC.PK_SECTOR=S.PK1 AND SAC.PK_NICHO=N.PK1 AND SAC.PK_SORTEO=" + this.getIdSorteo() + " AND C.REFBANCARIA='" + this.getReferenciaIB() + "' \n"
				+ "  ) AS X \n"
				+ "  ORDER BY CLAVE_NICHO ASC, TALONARIOS DESC \n";
		
		try {
			ResultSet res = db.getDatos(sql);
			listColaborador = new ArrayList<Colaborador>();
			if (res != null) {
				while (res.next()) {
					listColaborador.add(new Colaborador(res));
				}
			}
			
			// Si solo se encontro 1 colaborador ... todo correcto
			if (listColaborador.size() == 0){
				this.setNumErrores(this.getNumErrores() + 1);
				this.setStatus("ERROR: NO EXISTE UN COLABORADOR ASIGNADO CON LA REFERENCIA:" + getReferenciaIB());
				System.out.println(this.getStatus());
				this.setOperacion("error");
				
			}
			else if (listColaborador.size() == 1){
				//this.setIdColaborador((int)listColaborador.get(0).pk);
				//this.setClaveColaborador(listColaborador.get(0).clave);
				result = true;
			}
			// Si se encontro mas de un colaborador con la misma referencia ...
			else if (listColaborador.size() > 1)
			{
				boolean error = false;
				String msg_error = "";
				// Se busca si existe una clave diferente
				busca_claves:
				{
					for (int j = 0; j < listColaborador.size() - 1; j++) {
						long pk_j = listColaborador.get(j).pk;
						String clave_j = listColaborador.get(j).clave;
						for (int i = j + 1; i < listColaborador.size(); i++) {
							long pk_i = listColaborador.get(i).pk;
							String clave_i = listColaborador.get(i).clave;
							if (pk_i != pk_j || clave_i.compareTo(clave_j) != 0) {
								error = true;
								msg_error = clave_i + ", " + clave_j;
								break busca_claves;
							}
						}
					}
					/*
					for (int j = 0; j < listColaborador.size() - 1; j++) {
						String clave_j = listColaborador.get(j).clave;
						for (int i = j + 1; i < listColaborador.size(); i++) {
							String clave_i = listColaborador.get(i).clave;
							if (clave_i.compareTo(clave_j) != 0) {
								error = true;
								msg_error = clave_i + ", " + clave_j;
								break busca_claves;
							}
						}
					}*/
				}
				if (error) {
					this.setNumErrores(this.getNumErrores() + 1);
					this.setStatus("ERROR: LA REFERENCIA SE ENCUENTRA EN VARIOS COLABORADORES (" + msg_error + ")");
					System.out.println(this.getStatus());
					this.setOperacion("error");
				}
				else {
					// En este punto existen colaboradores con la misma clave y referencia.
					
					//this.setIdColaborador((int)listColaborador.get(0).pk);
					//this.setClaveColaborador(listColaborador.get(0).clave);
					
					// Quitamos los duplicados que no tienen talonarios.
					
					for (int i = listColaborador.size() - 1; i >= 0; i--) {
						if (listColaborador.get(i).talonarios == 0) {
							listColaborador.remove(i);
						}
					}
					result = true;
				}
			}
		}
		catch (Exception e) { }
		
		return result;
		
	}
	
	private boolean validarColaborador() {
		String claveEsperada = this.getClaveColaborador();
		// Se busca si las claves son diferentes de la esperada.
		for (Colaborador c : listColaborador)
			if (c.clave.compareTo(claveEsperada) != 0) {
				this.setNumErrores(this.getNumErrores() + 1);
				this.setStatus("ERROR: LA CLAVE " + c.clave + " no corresponde a la referencia " + this.getReferenciaIB() + ").");
				System.out.println(this.getStatus());
				this.setOperacion("error");
				return false;
			}
		return true;
	}
	
	/*
	private boolean existeColaborador(){
		// T ODO existeColaborador
		boolean result = false;
		String sql = "SELECT PK1 FROM COLABORADORES"
				+ " WHERE PK_SORTEO=" + this.getIdsorteo()
				+ " AND CLAVE = '" + this.getClaveColaborador() + "'"
				+ " AND REFBANCARIA = '" + this.getReferenciaIB() + "'";

		try {
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				this.setIdColaborador(rs.getInt("PK1"));
				result = true;
			} else {
				this.setNumErrores(this.getNumErrores() + 1);
				this.setStatus("ERROR: EL COLABORADOR NO ESTA REGISTRADO EN EL SISTEMA");
				System.out.println(this.getStatus());
				this.setOperacion("error");
			}

		} catch (SQLException e) {
		}

		return result;
	}
	//*/
	/* */
	private boolean ObtenerSectorNicho()
	{
		// TODO ObtenerSectorNicho
		boolean result = false;
		String sql = "SELECT PK1 FROM SECTORES WHERE CLAVE = '" + this.getClaveSector()+"' AND PK_SORTEO ="+this.getIdSorteo();
		System.out.println("SECTOR SQL:"+sql);
		ResultSet rs = db.getDatos(sql);
		try {
			if (rs != null && rs.next()) {
				this.setIdSector(rs.getInt("PK1"));
				
				sql = "SELECT PK1 FROM NICHOS WHERE CLAVE = '"+this.getClaveNicho()+"' AND PK_SECTOR = "+this.getIdSector();
				rs = db.getDatos(sql);
				if (rs != null && rs.next()) {
					this.setIdNicho(rs.getInt("PK1"));
					result = true;
				} else {
					// result = false;
					this.setNumErrores(this.getNumErrores() + 1);
					this.setStatus("ERROR: EL NICHO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA");
					System.out.println(this.getStatus());
					this.setOperacion("error");
				}
			}else{
				// result = false;
				this.setNumErrores(this.getNumErrores() + 1);
				this.setStatus("ERROR: EL SECTOR NO SE ENCUENTRA REGISTRADO EN EL SISTEMA");
				System.out.println(this.getStatus());
				this.setOperacion("error");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//*/
	/*
    private boolean validarAsignacionColaborador(){
		// T ODO ValidarSectorNichoColaborador
		boolean result = false;
		String sql = "SELECT COUNT(PK1) AS 'MAX' FROM SORTEOS_ASIGNACION_COLABORADORES WHERE PK_SORTEO=" + this.getIdsorteo() + " AND PK_COLABORADOR = " + this.getIdColaborador() +" AND PK_SECTOR ="+this.getIdsector()+" AND PK_NICHO = "+ this.getIdnicho();
		int max = 0;
		try {
			System.out.println("asignacion:"+sql);
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				max = rs.getInt("MAX");
				if (max > 0) {
					result = true;
				} else {
					this.setNumErrores(this.getNumErrores() + 1);
					this.setStatus("ERROR: EL COLABORADOR NO ESTA ASIGNADO AL NICHO O SORTEO CORRESPONDIENTE");
					System.out.println(this.getStatus());
					this.setOperacion("error");
				}
			}
		} catch (SQLException e) { }
		
		return result;
	}
    //*/
	
    private boolean validarAsignaciones(){
		// TODO ValidarSectorNichoColaborador
		boolean result = false;
		String sql = "SELECT * FROM SORTEOS_ASIGNACION_COLABORADORES"
				+ " WHERE PK_SORTEO=" + this.getIdSorteo()
				+ " AND PK_COLABORADOR=" + this.getIdColaborador()
				+ " ORDER BY PK1";
		try {
		//	System.out.println("asignacion:"+sql);
			ResultSet rs = db.getDatos(sql);
			if (rs != null && rs.next()) {
				this.setIdSector(rs.getInt("PK_SECTOR"));
				this.setIdNicho(rs.getInt("PK_NICHO"));
				if (rs.next() == false) {
					result = true;
				}
				else {
					this.setNumWarning(this.getNumWarning() + 1);
					this.setStatus("WARNING: EL COLABORADOR ESTA ASIGNADO A MAS DE UN NICHO");
					System.out.println(this.getStatus());
					this.setOperacion("warning");
				}
				result = true;
			}
			else {
				this.setNumErrores(this.getNumErrores() + 1);
				this.setStatus("ERROR: EL COLABORADOR NO ESTA ASIGNADO A NINGUN NICHO");
				System.out.println(this.getStatus());
				this.setOperacion("error");
			}
		} catch (SQLException e) { }
		
		return result;
	}
    
    private boolean llavePrimariaUnica() {
    	long pk = listColaborador.get(0).pk;
    	for (Colaborador colaborador : listColaborador) {
			if(colaborador.pk != pk)
				return false;
		}
    	return true;
    }
    
    private ArrayList<Colaborador> getColaboradores() {
    	ArrayList<Colaborador> list = new ArrayList<Colaborador>();
    	for (Colaborador source : listColaborador) {
			
    		boolean existe = false;
    		for (Colaborador target : list)
    			if (target.pk == source.pk) {
    				existe = true;
    				break;
    			}
    		if (!existe) {
    			list.add(new Colaborador(source));
    		}
		}
    	for (Colaborador target : list) {
    		target.costo = 0;
    		
    		for (Colaborador source : listColaborador)
    			if (target.pk == source.pk)
    				target.costo += source.costo;
    	}
    	return list;
    }
	
    
    
	private boolean InsertarRegistros(SesionDatos sesion)
	{
		if (llavePrimariaUnica())
			return InsertarRegistros_MultiplesAsignaciones(sesion);
		else
			return InsertarRegistros_Duplicados(sesion);
	}
    
	private boolean InsertarRegistros_MultiplesAsignaciones(SesionDatos sesion)
	{
		// TODO InsertarRegistros_MultiplesAsignaciones
		boolean result = true;
		
		Colaborador colaborador = listColaborador.get(0);
		this.setIdColaborador(colaborador.pk);
		//this.setIdNicho((int) c.pkNicho);
		//this.setIdSector((int) c.pkSector);
		this.setIdNicho(0);
		this.setIdSector(0);
		
		String sql = "SELECT COUNT(PK1) AS 'VALUE' FROM CONCILIACION WHERE PK_COLABORADOR="
				+ this.getIdColaborador() + " AND PK_SORTEO="
				+ this.getIdSorteo() + " AND REFERENCIA='"
				+ this.getReferencia() + "' AND FECHA='"
				+ this.getFecha() + "' AND HORA='" + this.getHora() + "'";
		int maxRegistros = db.getValue(sql, 0);

		System.out.println("EXISTE CONCILIACION: " + sql);

		if (0 < maxRegistros) {
			this.setNumWarning(this.getNumWarning() + 1);
			this.setStatus("WARNING: EL REGISTRO YA EXISTE");
			System.out.println(getStatus());
			this.setOperacion("warning");
		} else {
			
			// Obtenemos el maximo a depositar
			
			sql = "SELECT ISNULL(SUM(COSTO),0) AS 'VALUE'"
					+ " FROM SORTEOS_COLABORADORES_BOLETOS SCB, BOLETOS B"
					+ " WHERE SCB.PK_BOLETO=B.PK1"
					+ " AND SCB.PK_SORTEO=" + this.getIdSorteo()
					+ " AND SCB.PK_COLABORADOR=" + this.getIdColaborador();
			double importe_maximo = 0.0;
			importe_maximo = db.getValue(sql, importe_maximo);
			
			// Obtenemos el acumulado de lo ya depositado.

			sql = "SELECT ISNULL(SUM(IMPORTE),0) AS 'VALUE'"
					+ " FROM CONCILIACION"
					+ " WHERE PK_SORTEO=" + getIdSorteo()
					+ " AND PK_COLABORADOR=" + this.getIdColaborador();
			double importe_depositado = 0.0;
			importe_depositado = db.getValue(sql, importe_depositado);
			
			
			// CASO PARA MULTIPLES ASIGNACIONES
			
			int idConciliacion = insertConciliacion();
			
			if (importe_maximo < importe_depositado + getImporte()) {
				guardaAbono(colaborador.pk, colaborador.abono, idConciliacion);
				
				result = (updateAbonoColaborador(importe_depositado + this.getImporte()) > 0) && result;

				this.setNumWarning(this.getNumWarning() + 1);
				this.setStatus("WARNING: El acumulado de importes ("+(importe_depositado + this.getImporte())+") sobrepasan el costo de los talonarios (" + importe_maximo + ") para el colaborador '" + colaborador.clave + "'");
				this.setOperacion("warning");
			}
			else {
				guardaAbono(colaborador.pk, colaborador.abono, idConciliacion);
				
				result = (updateAbonoColaborador(importe_depositado + this.getImporte()) > 0) && result;

				this.setNumRegistrados(this.getNumRegistrados() + 1);
				this.setStatus("REGISTRADO");
				this.setOperacion("");
			}
		}
	
		return result;
	}
	
	private boolean InsertarRegistros_Duplicados(SesionDatos sesion)
	{
		//TODO InsertarRegistros
		boolean result = true;
		
		ArrayList<Colaborador> list = getColaboradores();
		this.setIdColaborador(list.get(0).pk);
		//this.setIdNicho((int) c.pkNicho);
		//this.setIdSector((int) c.pkSector);
		this.setIdNicho(0);
		this.setIdSector(0);
		
		String sql = "SELECT COUNT(PK1) AS 'VALUE' FROM CONCILIACION"
				+ " WHERE PK_COLABORADOR = " + this.getIdColaborador()
				+ " AND PK_SORTEO=" + this.getIdSorteo()
				+ " AND REFERENCIA='" + this.getReferencia() + "'"
				+ " AND FECHA='" + this.getFecha() + "'"
				+ " AND HORA='" + this.getHora() + "'";
		int maxRegistros = db.getValue(sql, 0);

		System.out.println("EXISTE CONCILIACION: " + sql);
	
		if (0 < maxRegistros) {
			this.setNumWarning(this.getNumWarning() + 1);
			this.setStatus("WARNING: EL REGISTRO YA EXISTE");
			System.out.println(getStatus());
			this.setOperacion("warning");
		} else {

			String condicion_idColaborador = "(";
			for (int i = 0; i < list.size(); i++) {
				if (i > 0)
					condicion_idColaborador += " OR ";
				condicion_idColaborador += "R.PK_COLABORADOR=" + list.get(i).pk;
			}
			condicion_idColaborador += ")";
			
			
			
			// Obtenemos el maximo a depositar
			/*
			sql = "SELECT ISNULL(SUM(B.COSTO),0) AS 'VALUE'"
					+ " FROM SORTEOS_COLABORADORES_BOLETOS R, BOLETOS B"
					+ " WHERE R.PK_BOLETO=B.PK1"
					+ " AND R.PK_SORTEO=" + this.getIdSorteo()
					+ " AND " + condicion_idColaborador;
			double importe_maximo = 0.0;
			importe_maximo = db.getValue(sql, importe_maximo);
			*/
			// Obtenemos el acumulado de lo ya depositado.

			sql = "SELECT ISNULL(SUM(R.IMPORTE),0) AS 'VALUE'"
					+ " FROM CONCILIACION R"
					+ " WHERE R.PK_SORTEO=" + getIdSorteo()
					+ " AND " + condicion_idColaborador;
			double importe_depositado = 0.0;
			importe_depositado = db.getValue(sql, importe_depositado);
			
			

			int idConciliacion = insertConciliacion();
			
			double importe = importe_depositado + this.getImporte();
			//ArrayList<Colaborador> list = getColaboradores();
			Colaborador ultimo = list.get(list.size()-1); // ultimo colaborador
			
			for (Colaborador c : list)
				c.importe = 0.0;
			
			for (Colaborador c : list) {
				double importe_maximo = c.costo; // costo de los boletos asignados.
				
				// Si el importe sobrepasa el costo de los boletos y NO es el ultimo colaborador
				if (importe_maximo < importe && c != ultimo) {
					c.importe = importe_maximo;
					importe -= importe_maximo;
				}
				else {
					c.importe = importe;
					importe = 0.0;
				}
				if (importe == 0.0)
					break;
			}
			for (Colaborador c : list) {
				this.setIdColaborador(c.pk);
				guardaAbono(c, idConciliacion);
				updateAbonoColaborador(c.importe);
			}
		}
		
		return result;
	}
	
	public boolean guardaAbono(Colaborador c, int idConciliacion)
	{
		return guardaAbono(c.pk, c.abono, idConciliacion);
	}
	
	public boolean guardaAbono(long pkColaborador, double abono, int idConciliacion) {
		String sql = "INSERT INTO CONCILIACION_COLABORADORES"
				+ " (PK_CONCILIACION, PK_COLABORADOR, ABONO, ORDEN, PK_USUARIO, FECHA_R)"
				+ " VALUES ("
				+ idConciliacion + ","
				+ pkColaborador + ","
				+ abono + ","
				+ (++this.conciliacion_boletos_orden) + ","
				+ getIdUsuario() + ",'"
				+ FECHA_R + "')";
		if (db.execQuery(sql) > 0)
			return true;
	
		return false;
	}
	
	public double consultaCostoBoletosElectronicosVendidos(){
		String sql = "SELECT SUM(B.COSTO) AS 'VALUE' FROM SORTEOS_COLABORADORES_TALONARIOS SCT, TALONARIOS T, BOLETOS B WHERE SCT.PK_TALONARIO = T.PK1 AND SCT.PK_SORTEO="+this.getIdSorteo()+" AND B.PK_TALONARIO=T.PK1 AND T.DIGITAL=1 AND SCT.PK_COLABORADOR="+this.getIdColaborador()+" AND B.FOLIODIGITAL IS NOT NULL";
		double defaultValue = 0.0;
		return db.getValue(sql, defaultValue);
	}
	
	public int insertConciliacion() {
		//SE INSERTA EL NUEVO IMPORTE
		String sql = "INSERT INTO CONCILIACION (PK_SORTEO,PK_COLABORADOR,PK_SECTOR,PK_NICHO,REFBANCARIA,CUENTA,FECHA,HORA,SUCURSAL,DESCRIPCION,CARGO,IMPORTE,SALDO,REFERENCIA,CONCEPTO,ORDEN,USUARIO,FECHA_R) VALUES ("
			+ this.getIdSorteo() + ","
			+ this.getIdColaborador() + ","
			+ this.getIdSector() + ","
			+ this.getIdNicho() + ",'"
			+ this.getReferenciaIB() + "','"
			+ this.getCuenta() + "','"
			+ this.getFecha() + "','"
			+ this.getHora() + "','"
			+ this.getSucursal() + "','"
			+ this.getDescripcion() + "','"
			+ this.getCargo() + "',"
			+ this.getImporte() + ","
			+ this.getSaldo() + ",'"
			+ this.getReferencia() + "','"
			+ this.getConcepto() + "','"
			+ (++this.conciliacion_orden) + "','"
			+ this.getIdUsuario() + "','"
			+ this.FECHA_R + "')";
		
		sql = sql.replace("\t", "");
		
		System.out.println("conciliacion INSERT:" + sql);
		return db.execQuerySelectId(sql);
	}
	
	public int updateAbonoColaborador(double importe_depositado) {
		String sql = "UPDATE COLABORADORES"
				+ " SET ABONO = " + importe_depositado
				+ ", FECHA_A='" + this.fecha_abono + "'"
				+ ", PK_USUARIO=" + getIdUsuario()
				+ ",FECHA_M=GETDATE()"
				+ " WHERE PK1=" + this.getIdColaborador()
				+ "  AND PK_SORTEO=" + this.getIdSorteo();

		System.out.println("col update:" + sql);
		
		return db.execQuery(sql);
	}

	

	/*
	public boolean marcarVentaBoleto(int pkBoleto, int idConciliacion, String vendido, boolean abono) {
		// Se marca el boleto como vendido con una "V".
		String str_abono = abono ? "COSTO":"0.0";
		String sql = "UPDATE BOLETOS SET VENDIDO='V', ABONO=" + str_abono + " WHERE PK1=" + pkBoleto
				+ " AND FOLIODIGITAL IS NOT NULL";
		if (db.execQuery(sql) > 0)
		{
			sql = "INSERT INTO CONCILIACION_BOLETOS"
				+ " (FC4,PK_CONCILIACION, PK_BOLETO, DATO, ORDEN, PK_USUARIO, FECHA_R)"
				+ " VALUES ('V',"
				+ idConciliacion + ","
				+ pkBoleto + ",'"
				+ vendido + "',"
				+ (++this.conciliacion_boletos_orden) + ","
				+ getIdUsuario() + ",GETDATE())";
			if (db.execQuery(sql) > 0)
				return true;
		}
		return false;
	}
	
	// TODO activarVenta
	public int activarVenta(ArrayList<Long> listPkTalonarios, int idConciliacion, double importe_depositado, mBoletosSorteo modelBoletos, SesionDatos sesion) throws SQLException{
		// Modelo auxiliar para evaluar talonariuos.
		
		String sql;
		int countRecords = 0;
		double resto = importe_depositado + this.getImporte();
		// Si el importe depositado alcanza a cubrir lo necesario para la venta de los talonarios ...
		for (Long pkTalonario : listPkTalonarios)
		{
			sql = "SELECT B.*"
					+ " FROM SORTEOS_COLABORADORES_BOLETOS SCB, BOLETOS B"
					+ " WHERE SCB.PK_BOLETO=B.PK1 AND SCB.PK_SORTEO="
					+ this.getIdSorteo() + " AND SCB.PK_TALONARIO="
					+ pkTalonario + " AND B.FOLIODIGITAL IS NOT NULL"
					+ " ORDER BY B.PK_TALONARIO ASC, B.PK1 ASC";
			ResultSet res_boletos = db.getDatos(sql);
			while (res_boletos.next()) {
				String vendido = res_boletos.getString("VENDIDO");
				String folioDigital = res_boletos.getString("FOLIODIGITAL");
				double costo = res_boletos.getDouble("COSTO");
				// Si el boleto no esta marcado como vendido y ya tiene folio digital ...
				if (folioDigital != null && costo<=resto)
				{
					if (vendido.compareTo("V") != 0)
					{
						if (marcarVentaBoleto(res_boletos.getInt("PK1"), idConciliacion, vendido, true))
							countRecords++;
					}
					resto -= costo;
					if(resto==0.0)
						break;
				}
			}
			modelBoletos.setIdtalonario(pkTalonario.intValue());
			modelBoletos.setAbonoTalonario(sesion);
			
			if(resto==0.0)
				break;
		}//<for>
		return countRecords;
	}//<end>
	*/
	/*
	private static class Boleto{
		public int PK1;
		public String vendido;
		public String retornado;
		public String folioDigital;
		public int compradores;
		public static ArrayList<Boleto> consulta(ResultSet res_boletos) throws SQLException{
			ArrayList<Boleto> bols = new ArrayList<mImportConciliacion.Boleto>();
			while (res_boletos.next()) {
				Boleto boleto = new Boleto();
				boleto.PK1 = res_boletos.getInt("PK1");
				boleto.vendido = res_boletos.getString("VENDIDO");
				boleto.retornado = res_boletos.getString("RETORNADO");
				boleto.folioDigital = res_boletos.getString("FOLIODIGITAL");
				boleto.compradores = res_boletos.getInt("COMPRADORES");
				bols.add(boleto);
			}
			return bols;
		}
	};
	/*
	public int activarFC4(ArrayList<Long> listPkTalonarios, int idConciliacion)
			throws SQLException {
		String sql;
		int countRecords = 0;
		
		for (Long pkTalonario : listPkTalonarios)
		{
			sql = "SELECT B.PK1, B.FOLIO, B.VENDIDO, B.RETORNADO, B.FOLIODIGITAL"
					+ ",(SELECT COUNT(C.PK1) FROM COMPRADORES C WHERE C.PK_BOLETO=B.PK1) AS 'COMPRADORES'"
					+ " FROM SORTEOS_COLABORADORES_BOLETOS SCB, BOLETOS B"
					+ " WHERE SCB.PK_BOLETO=B.PK1 AND SCB.PK_SORTEO="
					+ this.getIdSorteo() + " AND SCB.PK_TALONARIO="
					+ pkTalonario + " AND B.FOLIODIGITAL IS NOT NULL";
			
			ArrayList<Boleto> bols = Boleto.consulta(db.getDatos(sql));

			// Se consulta si todos los boletos electronicos tienen folio digital y comprador.
			boolean compradoresCompletos = true;
			for (Boleto boleto : bols) {
				if (boleto.compradores > 0 && boleto.folioDigital != null);
				else {
					compradoresCompletos = false;
					break;
				}
			}
			
			// si todo los folios y compradores estan completos ...
			if(compradoresCompletos)
			{
				for (Boleto boleto : bols) {
					// Si el boleto NO tiene la marca de vendido ...
					if (boleto.vendido.compareTo("V") != 0) {
						if (marcarVentaBoleto(boleto.PK1, idConciliacion, boleto.vendido, false))
							boleto.vendido = "V";
					}
				}
			
				for (Boleto boleto : bols) {
					// Si el boleto esta marcado como vendido y no esta retornado ...
					if (boleto.vendido.compareTo("V") == 0 && boleto.retornado.compareTo("1") != 0) {
						// Se marca el boleto como vendido con una "V".
						sql = "UPDATE BOLETOS SET RETORNADO='1' WHERE PK1=" + boleto.PK1 + " AND VENDIDO='V'";
						if (db.execQuery(sql) > 0)
						{
							sql = "INSERT INTO CONCILIACION_BOLETOS"
								+ " (FC4,PK_CONCILIACION, PK_BOLETO, DATO, ORDEN, PK_USUARIO, FECHA_R)"
								+ " VALUES ('R',"
								+ idConciliacion + ","
								+ boleto.PK1 + ",'"
								+ boleto.retornado + "',"
								+ (++this.conciliacion_boletos_orden) + ","
								+ getIdUsuario() + ",GETDATE())";
							if (db.execQuery(sql) > 0)
								countRecords++;
						}
					}
				}
			}
		}
		return countRecords;
	}
	//*/
	public void consultaUsuarioSorteo()
	{
		String sql = "SELECT PK_SORTEO,PK_SECTOR,PK_NICHO FROM USUARIOS WHERE PK1 = " + this.getIdUsuario();
		ResultSet rs = db.getDatos(sql);
		try {
			while (rs.next()) {
				this.setIdSorteo(rs.getInt("PK_SORTEO"));
				this.setIdSector(rs.getInt("PK_SECTOR"));
				this.setIdNicho(rs.getInt("PK_NICHO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void consultaSorteo() {

		String sql = "SELECT * FROM SORTEOS WHERE PK1 = " + getIdSorteo();

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
	
	public void resetFechaRegistro()
	{
		this.FECHA_R = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()); 
	}
	
	public void crearFechaAbono() {
		this.fecha_abono =
			fecha.substring(4, 8) + "-" + fecha.substring(2, 4) + "-" + fecha.substring(0, 2)
			//+ " " + hora.substring(0, 2) + ":" + hora.substring(2, 4) + ":00"
			;
	}

}



