package com.sorteo.conciliacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.core.SesionDatos;
import com.core.SuperModel;
import com.sorteo.herramientas.controller.ProgressBarCalc;
import com.sorteo.poblacion.model.mNichos;
import com.sorteo.sorteos.model.mBoletosSorteo;

public class mVentaElectronica extends SuperModel {

	private int idSorteo;
	//private int idColaborador;
	private String usuario;

	public int count_venta = 0;
	public int count_fc4 = 0;
	public int count_talonarios = 0;

	public int getIdSorteo() {
		return this.idSorteo;
	}

	public void setIdSorteo(int idSorteo) {
		this.idSorteo = idSorteo;
	}
	

	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	class BoletoElectronico {
		int idBoleto;
		double costo;
		double abono;
		int folioDigital;
		int autogenerado; // folio digital generado forzadamente
		int nCompradores;
		
		char vendido;
		int retornado;
		public BoletoElectronico(ResultSet res) throws SQLException {
			this.idBoleto = res.getInt("PK_BOLETO");
			this.costo = res.getDouble("COSTO");
			this.folioDigital = res.getInt("FOLIODIGITAL");
			this.autogenerado = res.getInt("AUTOGENERADO");
			this.nCompradores = res.getInt("NCOMPRADORES");
			this.vendido = 'N';
			this.retornado = 0;
		}
		
		public boolean estaVendido() {
			return (this.folioDigital > 0 && this.nCompradores >= 1);
		}
		
		@Override
		public String toString() {
			return "[" + vendido + "],[" + (retornado == 1 ? 'R' : '_')
				+ "], pk:" + idBoleto + ", costo:" + costo
				+ ", FD:" + folioDigital + ", coms:" + nCompradores + ", auto:" + autogenerado;
		}
	}
	
	class TalonarioAsignado{
		final long idTalonario;
		private int numBoletos;
		//private double monto;
		public char vendido;
		public double abono;
		ArrayList<BoletoElectronico> boletos;
		
		boolean aplicarComision;
		boolean ventaCompleta;
		double ventaRequerida;
		int boletos_requeridos;
		
		public TalonarioAsignado(long id, int numBoletos) {
			this.idTalonario = id;
			this.numBoletos = numBoletos;
			//this.monto = 0.0;
			boletos = new ArrayList<mVentaElectronica.BoletoElectronico>();
		}
		public void addBoleto(BoletoElectronico boleto){
			//this.monto += boleto.costo;
			boletos.add(boleto);
		}
		public int getBoletosAsignados() {
			return boletos.size();
		}
		
		public void validar() {
			aplicarComision = (getBoletosAsignados() == numBoletos);
			
			boletos_requeridos = 0;
			if (aplicarComision)
				boletos_requeridos = boletos.size() - 1;
			else
				boletos_requeridos = boletos.size();
			
			int boletos_vendidos = 0;
			for (int i = 0; i < boletos_requeridos; i++) {
				BoletoElectronico boleto = boletos.get(i);
				// Si tiene folio digital y tiene por lo menos un comprador ...
				if (boleto.estaVendido())
					boletos_vendidos++;
			}
			
			// La venta estara completa cuando los boletos vendidos sean igual a los requeridos.
			ventaCompleta = (boletos_vendidos == boletos_requeridos);

			// Se calcula la venta requerida en base a la comision.
			ventaRequerida = 0.0;
			for (int i = 0; i < boletos_requeridos; i++)
				ventaRequerida += boletos.get(i).costo;
		}
	}

	public boolean Venta(ArrayList<TalonarioAsignado> talonariosOrdenados)
	{
		// Se inicia el abono a cero.
		for (TalonarioAsignado ta : talonariosOrdenados) {
			ta.vendido = 'N';
			ta.abono = 0.0;
			for (BoletoElectronico boleto : ta.boletos) {
				boleto.vendido = 'N';
				boleto.abono = 0.0;
			}
		}
		
		// 1) Se Venden los boletos 1x1 
		for (TalonarioAsignado ta : talonariosOrdenados)
		{
			if (ta.idTalonario == 17239)
				System.out.println(">>" + ta.idTalonario);
			for (int i=0; i<ta.boletos_requeridos; i++) {
				BoletoElectronico boleto = ta.boletos.get(i);
				if (boleto.estaVendido()) {
					boleto.vendido = 'V';
					if (boleto.autogenerado == 1)
						boleto.abono = 0.0;
					else
						boleto.abono = boleto.costo;
					ta.abono += boleto.abono;
				}
			}
			
		}
		
		validarTalonario(talonariosOrdenados);
		
		return false;
	}
	
	public void validarTalonario(ArrayList<TalonarioAsignado> talonariosOrdenados) {
		for (TalonarioAsignado ta : talonariosOrdenados) {
			if (ta.abono > 0.0) {
				ta.vendido = 'V';
				for (BoletoElectronico boleto : ta.boletos)
					if (boleto.vendido != 'V') {
						ta.vendido = 'P';
						break;
					}
			}
			else
				ta.vendido = 'N';
		}
		
	}
	
	public boolean FC4(ArrayList<TalonarioAsignado> talonariosOrdenados) {
		for (TalonarioAsignado ta : talonariosOrdenados) {
			
			int vendidos = 0;
			double talonario_abono = 0.0;
			for (BoletoElectronico boleto : ta.boletos)
				if (boleto.vendido == 'V') {
					vendidos++;
					talonario_abono += boleto.costo;
				}
			boolean ventaCompleta;
			if (vendidos == ta.boletos_requeridos)
				ventaCompleta = true;
			else
				ventaCompleta = false;
			
			if (ventaCompleta){
				for (BoletoElectronico boleto : ta.boletos){
					boleto.vendido = 'V';
					boleto.retornado = 1;
				}
				ta.vendido = 'V';
				ta.abono = talonario_abono;
			}
			else
				for (BoletoElectronico boleto : ta.boletos)
					boleto.retornado = 0;
		}
		
		validarTalonario(talonariosOrdenados);
		
		return false;
	}
	
	public void Registrar(ArrayList<TalonarioAsignado> talonarios, ProgressBarCalc pbar) {
		pbar.init(talonarios.size());
		count_venta = 0;
		count_fc4 = 0;
		super._count_max = 0;
		for (TalonarioAsignado talonario : talonarios)
			super._count_max += talonario.boletos.size();
		super._count_process = 0;
		
		for (TalonarioAsignado talonario : talonarios) {
			if (registrarVenta(talonario) > 0)
				super._count_process++;
			else
				super._count_error++;
			pbar.progress();
		}
	}
	
	public int registrarVenta(TalonarioAsignado talonario) {
		String sql = "";
		//sql += "UPDATE BOLETOS SET VENDIDO='N', ABONO=0.0, RETORNADO=0, FECHA_M=GETDATE(), USUARIO='"+getUsuario()+"' WHERE PK_TALONARIO=" + talonario.idTalonario + "\n";
		for (BoletoElectronico boleto : talonario.boletos) {
			//if (boleto.vendido == 'V')
			{
				sql += "UPDATE BOLETOS"
					+ " SET VENDIDO='" + boleto.vendido
					+ "', ABONO=" + boleto.abono
					+ ", RETORNADO=" + boleto.retornado
					+ ", FECHA_M=GETDATE()"
					+ ", USUARIO='" + getUsuario() + "'"
					+ " WHERE PK1=" + boleto.idBoleto + " \n";
				
				if (boleto.retornado==1)
					count_fc4++;
				else
					count_venta++;
			}
		}
		sql += "UPDATE TALONARIOS"
				+ " SET VENDIDO='" + talonario.vendido
				+ "', ABONO=" + talonario.abono + ", FECHA_M=GETDATE(), USUARIO='" + getUsuario() + "'"
				+ " WHERE PK1=" + talonario.idTalonario + " \n";
		
		//System.out.println("\n"+sql);
		return db.execQuery(sql);
	}
	
	public int refolearBoletosElectronicos(SesionDatos sesion) {
		ProgressBarCalc pbar = new ProgressBarCalc(sesion);
		pbar.prepare();
		
		String sql
				= "SELECT B.SORTEO, B.PK1, B.FOLIODIGITAL, T.DIGITAL FROM BOLETOS B, TALONARIOS T"
				+ " WHERE B.PK_TALONARIO=T.PK1 AND B.SORTEO=" + getIdSorteo() + " AND T.DIGITAL=1 AND B.FOLIODIGITAL IS NOT NULL"
				+ " ORDER BY FOLIODIGITAL";
		ResultSet res = db.getDatos(sql);
		
		ArrayList<Integer> listPK = new ArrayList<Integer>();
		boolean lectura_ok = false;
		boolean actualizar_ok = false;
		try {
			while (res.next()) {
				listPK.add(res.getInt("PK1"));
			}
			res.close();
			lectura_ok = true;
		} catch (Exception ex) {
		}
		if (lectura_ok) {

			int consecutivo = 0;
			actualizar_ok = true;
			super._count_max = listPK.size();
			super._count_process = 0;

			pbar.init(listPK.size());
			for (Integer pkBoleto : listPK) {
				++consecutivo;
				sql = "UPDATE BOLETOS SET FOLIODIGITAL=" + consecutivo + " WHERE PK1=" + pkBoleto;
				if (db.execQuery(sql) != 1) {
					actualizar_ok = false;
					break;
				}
				pbar.progress();
				super._count_process++;
			}
			pbar.complete();

			if (actualizar_ok)
				return 0;
			else
				return -2;
		}
		else
			return -1;
	}
	
	public void print(ArrayList<TalonarioAsignado> talonariosOrdenados) {
		//System.out.println("----------[pkColaborador:" + this.idColaborador + ", importe:"+this.importe+"]");
		for (TalonarioAsignado ta : talonariosOrdenados) {
			System.out.println("  "+ta.idTalonario+":["+ta.vendido+","+ta.abono+"]");
			for (BoletoElectronico bol : ta.boletos)
				System.out.println("    "+bol.idBoleto+":["+bol.vendido+","+bol.abono+","+((bol.retornado==1)?"R":"_")
						+", "+(bol.folioDigital>0?(""+bol.folioDigital):"null")
						+", "+bol.nCompradores+"]");
		}
	}
	
	public ArrayList<TalonarioAsignado> cargaTalonariosYBoletos() {
		ArrayList<TalonarioAsignado> listTals = new ArrayList<mVentaElectronica.TalonarioAsignado>();

		String sql
			= " SELECT T.PK1 AS 'PK_TALONARIO',T.NUMBOLETOS AS 'TAL_NUMBOLETOS',SCT.NUMBOLETOS AS 'BOLETOS_ASIGNADOS' \n"
			+ " ,B.PK1 AS PK_BOLETO,B.COSTO,ISNULL(B.FOLIODIGITAL,0) AS 'FOLIODIGITAL',B.AUTOGENERADO \n"
			+ " ,(SELECT COUNT(*) FROM COMPRADORES WHERE PK_BOLETO=B.PK1) AS 'NCOMPRADORES' \n"
			+ " FROM SORTEOS_COLABORADORES_BOLETOS SCB, SORTEOS_COLABORADORES_TALONARIOS SCT, BOLETOS B, TALONARIOS T \n"
			+ " WHERE SCB.PK_TALONARIO=SCT.PK_TALONARIO AND SCB.PK_BOLETO=B.PK1 AND SCB.PK_TALONARIO=T.PK1 AND T.DIGITAL=1 AND SCB.PK_SORTEO=" + getIdSorteo() + " \n"
		//	+ " ORDER BY PK_TALONARIO, SCB.PK_COLABORADOR, B.FOLIODIGITAL DESC \n"
			+ " ORDER BY PK_TALONARIO, SCB.PK_COLABORADOR, NCOMPRADORES DESC, AUTOGENERADO, FOLIODIGITAL \n";

		ResultSet res = db.getDatos(sql);
		try {
			long lastIdTalonario = -1;
			TalonarioAsignado ta = null;
			// Bucle para recorrer los boletos.
			while (res.next()) {
				long idTalonario = res.getLong("PK_TALONARIO");
				if (idTalonario != lastIdTalonario) {
					ta = new TalonarioAsignado(idTalonario, res.getInt("TAL_NUMBOLETOS"));
					listTals.add(ta);
					System.out.println("Talonario " + ta.idTalonario);
				}
				ta.addBoleto(new BoletoElectronico(res));
				System.out.println("  Boleto " + res.getString("PK_BOLETO"));
				lastIdTalonario = idTalonario;
			}
		} catch (SQLException ex) { }
		
		for (TalonarioAsignado tal : listTals)
			tal.validar();
		
		return listTals;
	}
	
	// TODO RegistrarVentaFC4
	public void RegistrarVentaFC4(SesionDatos sesion)
	{
		ProgressBarCalc pbar = new ProgressBarCalc(sesion);
		pbar.prepare();
		
		ArrayList<TalonarioAsignado> talonarios = cargaTalonariosYBoletos();

		count_venta = count_fc4 = 0;
		
		Venta(talonarios);
		FC4(talonarios);
		Registrar(talonarios, pbar);
		
		pbar.complete();
	}
	
	public boolean limpiarNoasignados(SesionDatos sesion) {
		ProgressBarCalc pbar = new ProgressBarCalc(sesion);
		pbar.prepare();

		ArrayList<Long> idTalonarios = new ArrayList<Long>();
		String sql// = "SELECT PK1 AS 'PK_TALONARIO' FROM TALONARIOS WHERE SORTEO=" + this.idSorteo + " AND DIGITAL=1";
			= " SELECT * FROM( \n"
			+ "   SELECT T.PK1 AS 'PK_TALONARIO' \n"
			+ "   ,(SELECT COUNT(*) FROM SORTEOS_COLABORADORES_TALONARIOS SCT WHERE SCT.PK_TALONARIO=T.PK1) AS 'ASIGN' \n"
			+ "   FROM TALONARIOS T WHERE T.SORTEO=" + this.idSorteo + " AND T.DIGITAL=1 \n"
			+ " ) AS X WHERE ASIGN=0 \n";

		try {
			ResultSet res = db.getDatos(sql);
			while (res.next()) {
				idTalonarios.add(res.getLong("PK_TALONARIO"));
			}
			res.close();
		}
		catch (SQLException ex) { }
		catch (Exception ex) { }

		pbar.init(idTalonarios.size());
		
		sql = "UPDATE TALONARIOS SET VENDIDO='N', ABONO=0.0, FECHA_M=GETDATE(), USUARIO='"+getUsuario()+"' WHERE SORTEO=" + this.idSorteo + " AND DIGITAL=1";
		
		if (db.execQuery(sql) > 0) {
			for (Long id : idTalonarios) {
				sql = "UPDATE BOLETOS SET VENDIDO='N', ABONO=0.0, RETORNADO=0, FECHA_M=GETDATE(), USUARIO='"+getUsuario()+"' WHERE PK_TALONARIO=" + id;
				db.execQuery(sql);
				pbar.progress();
			}
			pbar.complete();
			return true;
		}
		pbar.complete();
		return false;
	}
	/*
	public ArrayList<String> buscaNichosXClave(String clave) {
		String sql = "SELECT PK1 FROM NICHOS WHERE PK_SORTEO=" + getIdSorteo() + " AND CLAVE='" + clave + "'";
		
		ArrayList<String> list = new ArrayList<String>();
		try {
			ResultSet rs = db.getDatos(sql);
			while (rs.next()) {
				list.add(rs.getString("PK1"));
			}
		}catch(SQLException ex) { }
		return list;
	}
	/*
	public ArrayList<Long> buscaTalonariosElectronicos() {
		ArrayList<String> idNichos = new ArrayList<String>();
		for (String item :  buscaNichosXClave("0001")) {
			idNichos.add("SNT.PK_NICHO="+item);
		}
		
		String condicion = idNichos.stream().map(Object::toString)
				.collect(Collectors.joining(" OR "));

		// Esta busqueda arroja todos los talonarios electronicos asignados a los nichos '0001' (Becario)
		String sql
			= "SELECT T.*"
			+ " FROM TALONARIOS T, SORTEOS_TALONARIOS ST, SORTEOS_NICHOS_TALONARIOS SNT \n"
			+ " WHERE ST.PK_TALONARIO=T.PK1 AND ST.PK_SORTEO=" + getIdSorteo() + " AND T.DIGITAL=1 AND SNT.PK_TALONARIO=T.PK1 \n"
			+ " AND (" + condicion + ")";
		ResultSet rs = db.getDatos(sql);
		
		ArrayList<Long> list = new ArrayList<Long>();
		try{
			while (rs.next()) {
				rs.getLong("PK1");
			}
		} catch(SQLException ex) { }
		return list;
	}
	*/
	
	public ArrayList<mNichos> getClaveNichos() {
		ArrayList<mNichos> list = new ArrayList<mNichos>();
		String sql
				= " SELECT *, (SELECT TOP 1 N.NICHO FROM NICHOS N WHERE N.PK_SORTEO=" + getIdSorteo() + " AND N.CLAVE=X.CLAVE) AS 'NICHO'"
				+ " FROM("
				+ "   SELECT DISTINCT(CLAVE) FROM NICHOS WHERE PK_SORTEO=" + getIdSorteo()
				+ " ) AS X";
		ResultSet res = db.getDatos(sql);
		try {
			while (res.next()) {
				mNichos nicho = new mNichos();
				nicho.setClave(res.getString("CLAVE"));
				nicho.setNicho(res.getString("NICHO"));
				//nicho.setDescripcion("0");
				list.add(nicho);
			}
		} catch(SQLException ex) { }
		return list;
	}
	
	// TODO completarCompradores
	public boolean completarCompradores(SesionDatos sesion, String[] arrIdNichos) {
		ProgressBarCalc pbar = new ProgressBarCalc(sesion); pbar.prepare();

		
		ArrayList<String> idNichos = new ArrayList<String>();
		for (int i=0; i<arrIdNichos.length; i++)
			idNichos.add("N.CLAVE='" + arrIdNichos[i] + "'");
		String conditionSQL = idNichos.stream().map(Object::toString)
				.collect(Collectors.joining(" OR "));
		
		// 1.- Contamos los talonarios.
		String sql = "SELECT COUNT(*) AS 'VALUE'\n"
				+ " FROM SORTEOS_COLABORADORES_TALONARIOS SNT, TALONARIOS T, NICHOS N, COLABORADORES C\n"
				+ " WHERE SNT.PK_TALONARIO=T.PK1 AND SNT.PK_NICHO=N.PK1 AND SNT.PK_COLABORADOR=C.PK1 AND SNT.PK_SORTEO=" + getIdSorteo() + " AND T.DIGITAL=1 AND ("+conditionSQL+")\n";
		
		int count = db.getValue(sql, 0);
		
		// 2.- Consultamos los talonarios.
		sql = "SELECT SNT.*,T.FOLIO,C.NOMBRE,C.APATERNO,C.AMATERNO\n"
			+ " FROM SORTEOS_COLABORADORES_TALONARIOS SNT, TALONARIOS T, NICHOS N, COLABORADORES C\n"
			+ " WHERE SNT.PK_TALONARIO=T.PK1 AND SNT.PK_NICHO=N.PK1 AND SNT.PK_COLABORADOR=C.PK1 AND SNT.PK_SORTEO=" + getIdSorteo() + " AND T.DIGITAL=1 AND ("+conditionSQL+")\n";
		
		try{
			super._count_process=0;
			super._count_success=0;
			super._count_error=0;
			count_talonarios=0;
			
			pbar.init(count);
			mBoletosSorteo comprador = new mBoletosSorteo();
			comprador.setIdSorteo(getIdSorteo());
			
			ResultSet rs = db.getDatos(sql);
			while (rs.next()) {
				comprador.setPktalonario(rs.getInt("PK_TALONARIO"));
				comprador.setAbono(0.0);
				comprador.setAutogenerado(true);

				// 3.- Consultamos los boletos NO vendidos por el app movil.
				sql = "SELECT X.* FROM( \n"
					+ "   SELECT B.PK1 AS 'PK_BOLETO',B.FOLIO,VENDIDO,COSTO,ABONO,RETORNADO,FOLIODIGITAL,(SELECT COUNT(*) FROM COMPRADORES C WHERE C.PK_BOLETO=B.PK1) AS 'COMPRADOR' \n"
					+ "   FROM BOLETOS B WHERE B.PK_TALONARIO=" + comprador.getPktalonario() + " \n"
					+ " ) AS X  WHERE VENDIDO='N' AND RETORNADO=0 AND COMPRADOR=0 AND FOLIODIGITAL IS NULL \n";
				try {
					System.out.println("talonario:" + comprador.getPktalonario());
					boolean first = true;
					ResultSet resx = db.getDatos(sql);
					while (resx.next()) {
						if (first) {
							comprador.setIdtalonario(Integer.valueOf(rs.getString("FOLIO")));
							comprador.setIdSector(rs.getInt("PK_SECTOR"));
							comprador.setIdNicho(rs.getInt("PK_NICHO"));
							comprador.setIdColaborador(rs.getInt("PK_COLABORADOR"));
							comprador.setNombre(rs.getString("NOMBRE"));
							comprador.setApellidos(rs.getString("APATERNO") + " " + rs.getString("AMATERNO"));
							comprador.setUsuario(this.getUsuario());
							consultaDatosColaborador(comprador);
							first = false;
						}
						//int pkBoleto = resx.getInt("PK_BOLETO");
						//double costo = resx.getDouble("COSTO");
						comprador.setIdBoleto(resx.getInt("PK_BOLETO"));
						comprador.setBoleto(resx.getString("FOLIO"));
						if(comprador.registrarSoloComprador(sesion) > 0) {
							sql = "UPDATE BOLETOS SET"
									+ " FOLIODIGITAL=(SELECT ISNULL(MAX(FOLIODIGITAL)+1,1) FROM BOLETOS),"
									+ " AUTOGENERADO=1"
									+ " WHERE PK1=" + comprador.getIdBoleto();
							db.execQuery(sql);
							super._count_success++;
						}
						else
							super._count_error++;
						super._count_process++;
					}
					resx.close();
					
					//
					sql = "SELECT COUNT(*) AS 'VALUE' FROM( \n"
							+ "   SELECT B.PK1 AS 'PK_BOLETO',B.FOLIO,VENDIDO,COSTO,ABONO,RETORNADO,FOLIODIGITAL,(SELECT COUNT(*) FROM COMPRADORES C WHERE C.PK_BOLETO=B.PK1) AS 'COMPRADOR' \n"
							+ "   FROM BOLETOS B WHERE B.PK_TALONARIO=" + comprador.getPktalonario() + " \n"
							+ " ) AS X  WHERE COMPRADOR=0 \n";
					int val = db.getValue(sql, 0);
					if (val > 0) {
						System.out.println(">> Faltan compradores.");
					}
				} catch (SQLException ex) { }
				count_talonarios++;
				pbar.progress();
			}
		} catch(SQLException ex) { }
		pbar.complete();
		return true;
	}
	
	public void consultaDatosColaborador(mBoletosSorteo comprador) {
		
		// TELEFONOS
		comprador.setTelefonof("-");
		comprador.setTelefonom("-");
		
		String sql = "SELECT * FROM COLABORADORES_TELEFONOS WHERE PK_COLABORADOR=" + comprador.getIdColaborador() + " AND TELEFONO<>''";
		try{
			ResultSet res = db.getDatos(sql);
			while (res.next()) {
				switch(res.getString("TIPO")) {
					case "C" : comprador.setTelefonof(res.getString("TELEFONO")); break;
					case "M" : comprador.setTelefonom(res.getString("TELEFONO")); break;
				}
			}
		} catch(SQLException ex) { }
		
		// CORREOS
		comprador.setCorreo("-");
		sql = "SELECT * FROM COLABORADORES_CORREOS WHERE PK_COLABORADOR=" + comprador.getIdColaborador() + " AND CORREO<>'' ORDER BY SECUENCIA DESC";
		try{
			ResultSet res = db.getDatos(sql);
			if (res.next()) {
				comprador.setTelefonof(res.getString("CORREO"));
			}
		} catch(SQLException ex) { }
		
		// DIRECCION
		comprador.setCalle("-");
		comprador.setNumero("-");
		comprador.setColonia("-");
		comprador.setMunicipio("-");
		comprador.setEstado("-");
		comprador.setCP("-");
		sql = "SELECT * FROM COLABORADORES_DIRECCION WHERE PK_COLABORADOR=" + comprador.getIdColaborador();
		try{
			ResultSet res = db.getDatos(sql);
			if (res.next()) {
				comprador.setCalle(res.getString("CALLE"));
				comprador.setNumero(res.getString("NUMEXT"));
				comprador.setColonia(res.getString("COLONIA"));
				comprador.setEstado(res.getString("ESTADO"));
				comprador.setMunicipio(res.getString("MUNDEL"));
				comprador.setCP(res.getString("CP"));
			}
		} catch(SQLException ex) { }
		
	}
	
}
















