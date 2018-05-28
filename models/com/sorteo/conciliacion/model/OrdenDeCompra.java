package com.sorteo.conciliacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.core.Parametros;
import com.core.SuperModel;
import com.core.SuperModel.RESULT;

public class OrdenDeCompra {
	class Boleto{
		public int pk;
		public String pkEstado;
		public double costo;
		
		public boolean update;
		public Boleto(ResultSet rs) throws SQLException {
			this.pk = rs.getInt("PK_BOLETO");
			this.pkEstado = rs.getString("PK_ESTADO");
			this.costo = rs.getDouble("COSTO");
			this.update = false;
		}
	}
	
	public String folio;
	public int idMetodoPago;
	public String referencia;
	public double importe;
	public String fechaVencimiento;
	public String pkEstado;
	
	public ArrayList<Boleto> listBoletos;

	public OrdenDeCompra() {
		listBoletos = null;
	}
	
	public RESULT consultaOrden(SuperModel model) {
		
		String sql = "SELECT * FROM ORDENDECOMPRA WHERE REFBANCARIA='" + getReferencia() + "'";
		try {
			ResultSet rs = model.db.getDatos(sql);
			if (rs.next()) {
				setFolio(rs.getString("FOLIO"));
				setIdMetodoPago(rs.getInt("PK_METODO_PAGO"));
				setImporte(rs.getDouble("IMPORTE"));
				setFechaVencimiento(rs.getString("FECHA_VENCIMIENTO"));
				setPkEstado(rs.getString("PK_ESTADO"));
				rs.close();

				sql = "SELECT OD.*, B.PK_ESTADO, B.COSTO FROM ORDEN_DETALLE OD INNER JOIN BOLETOS B ON B.PK1 = OD.PK_BOLETO WHERE OD.PK_FOLIO=" + getFolio();
				rs = model.db.getDatos(sql);
				listBoletos = new ArrayList<Boleto>();
				while (rs.next()) {
					listBoletos.add(new Boleto(rs));
				}
				
				if (listBoletos.size() > 0) {
					return RESULT.OK;
				}
				else {
					model._mensaje = "La orden de compra no tiene ningun boleto asociado";
					return RESULT.ERROR;
				}
			}
			else{
				setFolio(null);
				model._mensaje = "No se encontro una orden de compra con la referencia: " + getReferencia();
				return RESULT.ERROR;
			}
		} catch (SQLException ex) { }
		
		model._mensaje = "Ocurrio un error al momento de consultar la orden de compra.";
		return RESULT.ERROR;
	}
	
	public double calcCostoBoletos() {
		double costoDeBoletos = 0;
		for (Boleto boleto : listBoletos)
			costoDeBoletos += boleto.costo;
		
		return costoDeBoletos;
	}
	
	// TODO - GUARDA
	public RESULT guarda(SuperModel model, String usuario) {
		StringBuilder sbLIST = new StringBuilder();
		for (Boleto boleto : listBoletos)
			sbLIST.append(boleto.pk).append(",");
		
		// parametros
		List<Parametros> list = new ArrayList<Parametros>();
		list.add(new Parametros("RESULT", 0));
		list.add(new Parametros("CONTADOR_OK", 0));
		list.add(new Parametros("_MENSAJE", ""));
		list.add(new Parametros("FOLIO_ORDEN", getFolio()));
		list.add(new Parametros("USUARIO", usuario));
		list.add(new Parametros("LIST", sbLIST.toString()));
		list.add(new Parametros("StatementType", "Record"));
		
		// registros de salida
		list.get(0).setOut(true, Types.INTEGER);
		list.get(1).setOut(true, Types.INTEGER);
		list.get(2).setOut(true, Types.VARCHAR);

		// 
		if (model.db.execStoreProcedureList("spCONCILIACION_ELECTRONICA_ORDEN", list) == 0) {
			
			int result = Integer.valueOf(list.get(0).getParamValue());
			if (result == 1) {
				model._mensaje = "Correcto";
				return RESULT.OK;
			}
			else if (result < 0){
				model._mensaje = list.get(2).getParamValue();
				return RESULT.ERROR;
			}
		}
		
		model._mensaje = "Ocurrio un error en la conexion con la Base de datos ";
		return RESULT.ERROR;
	}
	
	
	// --------------------------------------------------

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public int getIdMetodoPago() {
		return idMetodoPago;
	}

	public void setIdMetodoPago(int idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getPkEstado() {
		return pkEstado;
	}

	public void setPkEstado(String pkEstado) {
		this.pkEstado = pkEstado;
	}
	
}
