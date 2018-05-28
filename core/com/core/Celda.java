package com.core;

//import org.apache.poi.hssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class Celda{
	public int type;
	public int intValue;
	public double numValue;
	public String strValue;
	public boolean boolValue;
	public int rowIndex;
	
	public Celda(){
		this.set("");
		this.type = XSSFCell.CELL_TYPE_BLANK;
	}
	
	public void set(int value){
		this.type = XSSFCell.CELL_TYPE_NUMERIC;
		this.numValue = value;
		this.intValue = value;
		this.boolValue = this.intValue != 0 ? true : false;
		this.strValue = "" + value ;
	}
	
	public void set(double value){
		this.type = XSSFCell.CELL_TYPE_NUMERIC;
		this.numValue = value;
		this.intValue = (int)value;
		this.boolValue = this.intValue != 0 ? true : false;
		this.strValue = "" + value ;
	}
	
	public void set(String value){
		this.type = XSSFCell.CELL_TYPE_STRING;
		this.numValue = 0.0;
		this.intValue = 0;
		this.boolValue = false;
		if (value == null) {
			this.type = XSSFCell.CELL_TYPE_BLANK;
			this.strValue = "";
		}
		else
			this.strValue = value.trim();
	}
	
	public void set(boolean value){
		this.type = XSSFCell.CELL_TYPE_BOOLEAN;
		this.numValue = 0.0;
		this.intValue = 0;
		this.boolValue = value;
		this.strValue = "";
	}
	
	public void set(XSSFCell cell){
		if (cell == null){
			this.set("");
			this.type = XSSFCell.CELL_TYPE_BLANK;
		}
		else{
			this.type = cell.getCellType();
			if (this.type == XSSFCell.CELL_TYPE_NUMERIC)
				this.set(cell.getNumericCellValue());
			else if (this.type == XSSFCell.CELL_TYPE_BOOLEAN)
				this.set(cell.getBooleanCellValue());
			else if (this.type == XSSFCell.CELL_TYPE_STRING)
				this.set(cell.getStringCellValue());
			else
				this.set("");
		}
	}
	
	public boolean empty() {
		return
				this.intValue == 0 &&
				this.numValue == 0.0 &&
				this.boolValue == false &&
				"".compareTo(this.strValue) == 0;
	}
	
	public String toString(){
		if (this.type == XSSFCell.CELL_TYPE_NUMERIC)
			return "" + this.intValue;
		else if (this.type == XSSFCell.CELL_TYPE_BOOLEAN)
			return "" + this.boolValue;
		else if (this.type == XSSFCell.CELL_TYPE_STRING)
			return "" + this.strValue;
		else if (this.type == XSSFCell.CELL_TYPE_BLANK)
			return "";
		else
			return "<f>";
	}
}
