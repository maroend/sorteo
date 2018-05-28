package com.core;
import java.sql.Types;

public class Parametros
{
	boolean out = false;
	int type;
	String paramName;
	String paramValue;
	
	public Parametros(){
		
	}
	
	public Parametros(String paramName, String paramValue){
		setParamName(paramName);
		setParamValue(paramValue);
	}
	
	public Parametros(String paramName, Integer paramValue){
		setParamName(paramName);
		setParamValue(paramValue.toString());
	}
	
	public Parametros(String paramName, Long paramValue){
		setParamName(paramName);
		setParamValue(paramValue.toString());
	}
	
	public Parametros(String paramName, Double paramValue){
		setParamName(paramName);
		setParamValue(paramValue.toString());
	}
	
	public Parametros(String paramName, StringBuilder paramValue){
		setParamName(paramName);
		setParamValue(paramValue.toString());
	}
	
	public Parametros(String paramName){
		setParamName(paramName);
		setParamValue(null);
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public String getParamName() {
		return this.paramName;
	}
	
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	public String getParamValue() {
		return this.paramValue;
	}
	
	public void setOut(boolean out, int type) {
		this.out = out;
		this.type = type;
	}
	
	public boolean getOut() {
		return this.out;
	}
	
	public int getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(getParamName()).append(":").append(getParamValue()).append("").toString();
	}
}
