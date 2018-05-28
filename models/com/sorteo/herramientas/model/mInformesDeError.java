package com.sorteo.herramientas.model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import com.core.Factory;
import com.core.SuperModel;

public class mInformesDeError extends SuperModel {
	

	private int id;
	private int idusuario;
	private String ip;
	private String actividad;
	private String fecha;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	public mInformesDeError() {
		// TODO Auto-generated constructor stub
	}

	public int contar(){

		return archivos.size();
	}
	
	public ArrayList<String> archivos;
	
	public void listarArchivos(HttpServletRequest request) {

		String directoryName = Factory.getPathErrors();
		File directory = new File(directoryName);
		String[] array = directory.list( new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				File file = new File(directoryName + name);
				return (file.exists() && file.isFile() && 0<file.length());
			}
		});
		ArrayList<String> list = new ArrayList<String>();
		if (array != null)
			for(int i=0; i<array.length; i++)
				list.add(array[i]);
		Collections.sort(list);
		
		this.archivos = list;
	}

	public ArrayList<String> paginacion(int pg, int numreg, String search)
	{
		ArrayList<String> sublist = new ArrayList<String>();
		int inicio = numreg*(pg-1);
		for (int i=inicio; i<inicio+numreg && i<archivos.size(); i++) {
			sublist.add(archivos.get(i));
		}
		return sublist;
	}

}


