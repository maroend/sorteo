package com.core;


import java.util.Date;
//import java.sql.Date;

public class TimeSpan {
	private int days;
	public int hour;
	public int minute;
	public int second;
	
	public TimeSpan() {
		this.days = this.hour = this.minute = this.second = 0;
	}//<end>
	
	public TimeSpan(int days, int hour, int minute, int second) {
		this.days = days;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}//<end>
	
	public boolean isShorterThat(TimeSpan other) {
		if(this.days == other.days) {
			if (this.hour == other.hour) {
				if (this.minute == other.minute) {
					return this.second < other.second;
				}
				return this.minute < other.minute;
			}
			return this.hour < other.hour;
		}
		return this.days < other.days;
	}

	public TimeSpan(Date shorterDate, Date longerDate) {
		calc(shorterDate, longerDate);
	}//<end>
	
	public void calc(Date shorterTime, Date longerTime) {
		long diferencia = longerTime.getTime() - shorterTime.getTime();
		if (diferencia == 0) {
			this.days = this.hour = this.minute = this.second = 0;
			return;
		}
		int signo = (int)(diferencia / Math.abs(diferencia));
		if( diferencia < 0 )
			diferencia = -diferencia;
		
		// Obtenemos los segundos
		long segundos = diferencia / 1000;
		
		// Obtenemos los dias
		long dias = segundos / 86400;
		segundos -= dias * 86400;

		// Obtenemos las horas
		long horas = segundos / 3600;
		segundos -= horas*3600;
		
		// Obtenemos los minutos
		long minutos = segundos /60;
		segundos -= minutos*60;

		this.days = (int)(signo * dias);
		this.hour = (int)horas;
		this.minute = (int)minutos;
		this.second = (int)segundos;
	}//<end>

	public static String toFormat(int days, int hour, int minute, int second) {
		hour = hour%24;
		minute = minute%60;
		second = second%60;
		
		StringBuilder sb = new StringBuilder();

		sb.append(days).append(" ");

		if( hour<10 ) sb.append('0');
		sb.append(hour);

		sb.append(":");
		if( minute<10 ) sb.append('0');
		sb.append(minute);

		sb.append(":");
		if( second<10 ) sb.append('0');
		sb.append(second);

		return sb.toString();
	}//<end>
	
	
	@Override
	public String toString(){
		return TimeSpan.toFormat(days, hour, minute, second);
	}//<end>

}//<end class>
