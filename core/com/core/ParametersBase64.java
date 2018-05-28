package com.core;

import java.util.Base64;
import java.util.HashMap;

public class ParametersBase64 {

	public String key;
	public String value;
	public String[] arrayValues;

	public static String byteArrayToString(byte[] array) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++)
			sb.append((char) array[i]);
		return sb.toString();
	}

	public static byte[] stringToByteArray(String str) {
		byte[] array = new byte[str.length()];
		for(int i=0; i<str.length(); i++){
			array[i] = (byte)str.charAt(i);
		}
		return array;
	}
	
	public static String string255(String str) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length(); i++)
			sb.append((char)(((int)str.charAt(i)) & 0xFF));
		return sb.toString();
	}
	
	public static HashMap<String, ParametersBase64> parse(String q) {
		return ParametersBase64.parse(q, null, null, false);
	}
	
	public static HashMap<String, ParametersBase64> parse(String q, boolean str255) {
		return ParametersBase64.parse(q, null, null, str255);
	}
	
	public static HashMap<String, ParametersBase64> parse(String q, String paramSeparator, String pairSeparator, boolean str255) {
		if (paramSeparator == null) paramSeparator = "[;]";
		if (pairSeparator == null) pairSeparator = "[:]";
		HashMap<String, ParametersBase64> map = new HashMap<String, ParametersBase64>();
		if (q != null) {
			String result = byteArrayToString(Base64.getDecoder().decode(q.replace(" ", "+")));
			String[] arrParams = result.split(paramSeparator);
			for (int j = 0; j < arrParams.length; j++)
			{
				String[] par = arrParams[j].split(pairSeparator);
				
				ParametersBase64 parameter = new ParametersBase64();
				parameter.key = par[0].trim();
				parameter.value = "";
				parameter.arrayValues = new String[] { parameter.value };
				
				if (par.length == 2) {
					parameter.value = par[1].trim();
					parameter.arrayValues = par[1].split("[,]");
					for(int i=0; i<parameter.arrayValues.length; i++)
						parameter.arrayValues[i] = parameter.arrayValues[i].trim();

					if (str255) {
						parameter.value = ParametersBase64.string255(parameter.value);
						for(int i=0; i<parameter.arrayValues.length; i++)
							parameter.arrayValues[i] = ParametersBase64.string255(parameter.arrayValues[i]);
					}
				}

				if (!map.containsKey(parameter.key))
					map.put(parameter.key, parameter);
			}
		}
		return map;
	}
	
	public static String decode(String q, boolean str255) {
		String result = byteArrayToString(Base64.getDecoder().decode(q.replace(" ", "+")));
		if (str255)
			return ParametersBase64.string255(result);
		return result;
	}
}



