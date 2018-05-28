package com.core;

import java.util.Base64;

import org.apache.commons.lang3.math.NumberUtils;

public class Global {
	public static final String bkground_activo = "";
	public static final String bkground_cerrado = "background-color: hsl(55, 21%, 80%);";

	private static StringBuilder sb = new StringBuilder(200);
	public static String acentos(String str)
	{
		sb.setLength(0);
		for(int i=0; i<str.length(); i++)
		{
			char car = str.charAt(i);
			switch((int)car) {
			case 193: sb.append("&Aacute;"); break;
			case 201: sb.append("&Eacute;"); break;
			case 205: sb.append("&Iacute;"); break;
			case 211: sb.append("&Oacute;"); break;
			case 218: sb.append("&Uacute;"); break;
			
			case 225: sb.append("&aacute;"); break;
			case 233: sb.append("&eacute;"); break;
			case 237: sb.append("&iacute;"); break;
			case 243: sb.append("&oacute;"); break;
			case 250: sb.append("&uacute;"); break;
			
			case 209: sb.append("&Ntilde;"); break;
			case 241: sb.append("&ntilde;"); break;
			default: sb.append(car);
			}
		}
		
		return sb.toString();
	}
	
	public static int valid(String str, int Default){
		if (str != null)
			try {
				str = str.trim();
				if (NumberUtils.isDigits(str))
					return Integer.parseInt(str);
			}catch (Exception ex) { }
		return Default;
	}
	
	public static String valid(String str, String Default){
		if (str != null)
			return str.trim();
		return Default;
	}
	
	public static String decodeBase64(String q) {
		return Global.decodeBase64(q, true);
	}
	
	public static String decodeBase64(String q, boolean str255) {
	
		String result = ParametersBase64.byteArrayToString(Base64.getDecoder().decode(q.replaceAll(" ", "+")));
		if (str255) {
			result = ParametersBase64.string255(result.trim());
		}
		return result;
	}
	
	public static String encodeBase64(String q) {
		String result = ParametersBase64.byteArrayToString(Base64.getEncoder().encode(ParametersBase64.stringToByteArray(q)));
		return result;
	}

	public static String cut(String html) {
		if (html.indexOf(" ") < 0)
			return html;
		int medium = html.length() / 2;
		int pos = -1;
		for (int i = 0; i < medium; i++) {
			if (html.charAt(medium + i) == ' ') {
				pos = medium + i;
				break;
			}
			if (html.charAt(medium - i) == ' ') {
				pos = medium - i;
				break;
			}
		}
		return html.substring(0, pos).replaceAll(" ", "&nbsp;") + " "
			+ html.substring(pos + 1).replaceAll(" ", "&nbsp;");
	}
	
	public static String createRandomID() {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < 8; j++) {
			if (j > 0)
				sb.append("-");
			for (int i = 0; i < 4; i++)
				sb.append((char) (65 + (int) (Math.random() * 26.0)));
		}
		return sb.toString();
	}
	
	public static String extractError(Exception ex) {
		try{
			StackTraceElement[] arr = ex.getStackTrace();
			for(int i=0; i<arr.length; i++) {
				if(arr[i].getLineNumber()!=-1){
					return "Error en: " + arr[i].getFileName() + ", linea: " + arr[i].getLineNumber() + ", men: " + ex.toString().replaceAll("\"", "").replaceAll("'", "");
				}
			}
		}catch(Exception exc) { }
		return ex.toString().replaceAll("\"", "").replaceAll("'", "");
	}
	
	public static String toFrase(String str){
		StringBuilder sb = new StringBuilder();
		str = str.toLowerCase();
		char car;
		boolean first = true;
		for (int i = 0; i < str.length(); i++) {
			car = str.charAt(i);
			if (car == ' '){
				if (!first )
					sb.append(car);
			}
			else if (first)
				sb.append(Character.toUpperCase(car));
			else
				sb.append(car);
			first = (boolean)(car == ' ');
		}
		return sb.toString();
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}
	
}








