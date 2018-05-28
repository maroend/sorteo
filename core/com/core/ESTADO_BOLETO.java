package com.core;

public class ESTADO_BOLETO {
	public static final String ASIGNADO = "ASIGNADO";
	public static final String DEVOLUCION_NO_VENDIDO = "DEVOLUCION NO VENDIDO";
	public static final String DEVOLUCION_NO_VENDIDOS_PARCIAL = "DEVOLUCION NO VENDIDOS PARCIAL";
	public static final String INCIDENTE_EXTRAVIADO = "INCIDENTE EXTRAVIADO";
	public static final String INCIDENTE_ROBADO = "INCIDENTE ROBADO";
	public static final String NO_VENDIDO = "NO VENDIDO";
	public static final String PENDIENTE_DE_PAGO = "PENDIENTE DE PAGO";
	public static final String RETORNADO = "RETORNADO";
	public static final String VENDIDO = "VENDIDO";
	
	public static final String ESTILO_ND = "badge-inverse";
	public static final String ESTILO_DP = "badge-inverse";
	public static final String ESTILO_IE = "badge-inverse";
	public static final String ESTILO_IR = "badge-inverse";
	public static final String ESTILO_N = "badge-inverse";
	public static final String ESTILO_P = "badge-inverse";
	public static final String ESTILO_R = "badge-inverse";
	public static final String ESTILO_V = "badge-success";
	
	public static String getEstilo(String id) {
		switch(id.trim()) {
		case "DN": return ESTILO_ND;
		case "DP": return ESTILO_DP;
		case "IE": return ESTILO_IE;
		case "IR": return ESTILO_IR;
		case "N": return ESTILO_N;
		case "P": return ESTILO_P;
		case "R": return ESTILO_R;
		case "V": return ESTILO_V;
		}
		return "";
	}
}
