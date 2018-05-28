package com.core;

public class ResponseMessage {

	public String identificador;
	public String title;
	public String message;
	public ResponseMessage(String identificador, String title, String msg){
		this.identificador = identificador == null ? "" : identificador;
		this.title = title == null ? "" : title;
		this.message = msg==null ? "" : msg;
	}
	
	public String toJson() 
	{
		String Json = "{\"response\":\"" + this.identificador + "\", \"title\":\"" + this.title + "\", \"message\":\"" + this.message + "\"}";
		return Json;
	}
	
}
