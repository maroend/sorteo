package com.sorteo.herramientas.controller;

import com.core.SesionDatos;

public class ProgressBarCalc {
	public int maxItems;
	public int count;
	public int tanto;
	public int _1_porciento;
	private SesionDatos sesion;
	
	public ProgressBarCalc(SesionDatos sesion){
		
		this.sesion = sesion;
	}
	
	public void prepare(){
		this.sesion.data1 = 0;
		this.sesion.guardaSesion();
	}
	
	public void init(int maxItems){
		this.maxItems = maxItems;
		this.count = 0;
		this.tanto = -1;
		this._1_porciento = maxItems / 100;
		if (this._1_porciento < 20)
			this._1_porciento = 20; // 20 Instrucciones INSERT minimo.
	}
	
	public void progress(){
		count++;
		if (tanto != count/_1_porciento){
			tanto = count/_1_porciento;
			this.sesion.data1 = (int)(100.0 * count / maxItems);
			this.sesion.guardaSesion();
		}
	}
	
	public void complete(){
		this.sesion.data1 = 100;
		this.sesion.guardaSesion();
	}
}
