package com.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	String fileName;
	public Celda[][] matriz;
	public HashMap<String, Integer> headers;

	public String mensaje;
	public String process;
	public String inserted;
	public String content;
	/**/
	public int countInserts;
	public int countUpdates;
	public int countErrors;
	
	private boolean finish;
	//*/

	public ReadExcel(String fileName){
		this.fileName = fileName;
		this.mensaje = "";
		this.process = "";
		this.inserted = "";
		this.content = "";
		this.finish = false;
	}
	
	public boolean endOfBook(){
		return finish;
	}
	
	public void read(String sheetName)
	{
		read(sheetName, -1);
	}
	
	public void read(String sheetName, int sheetIndex)
	{
		System.out.println("Opening "+sheetName+"...");
		ArrayList<Celda[]> listArray = new ArrayList<Celda[]>();
		try {
			
			//___________________________________________  XLSX
			File excel =  new File (fileName);
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheet(sheetName);

			matriz = null;
			if (ws == null) {
				if (0 <= sheetIndex)
					ws = wb.getSheetAt(sheetIndex);
				if (ws == null) {
					this.finish = true;
					fis.close();
					wb.close();
					throw new Exception("No se puede leer la hoja : " + sheetName);
				}
			}

			int rowNum = ws.getLastRowNum() + 1;
			int colNum = ws.getRow(0).getLastCellNum();
			//String[][] data = new String[rowNum][colNum];
			
			System.out.println(" [x:"+colNum+", y:"+rowNum+"]");
			
			headers = null;
			for (int i = 0; i < rowNum; i++) {
				ArrayList<Celda> list = new ArrayList<Celda>();
				XSSFRow row = ws.getRow(i);
				for (int j = 0; j < colNum; j++) {
					
					XSSFCell cell = row.getCell(j);
					
					//cell = row.getCell((short) j);
					Celda celda = new Celda();
					if (cell != null) {
						// Your code here
						celda.set(cell);
						celda.rowIndex = i;
					}
					
					list.add(celda);
				}
				/**/
				boolean rowEmpty = true;
				for(Celda celda : list)
					if (celda.empty() == false) {
						rowEmpty = false;
						break;
					}
				if (rowEmpty == false)
				//*/
				{
					if (headers == null){
						makeHeaders(list.toArray( new Celda[list.size()] ));
					}
					else
						listArray.add( list.toArray( new Celda[list.size()] ) );
				}
			}
			
			fis.close();
			
			wb.close();
			
			System.out.println("Load complete ...");
			
			
			//___________________________________________
			
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		matriz = listArray.toArray( new Celda[listArray.size()][] );
		
	}
	
	public void makeHeaders(Celda[] array)
	{
		headers = new HashMap<String, Integer>();
		for(int i=0; i<array.length; i++)
			headers.put(array[i].strValue, i);
	}
	
	public String get(int row, String columnName){
		process = columnName;
		int column = headers.get(columnName);
		Celda cell = matriz[row][column];
		switch(cell.type){
		case XSSFCell.CELL_TYPE_NUMERIC: 
			if (cell.numValue - cell.intValue == 0)
				return "" + cell.intValue;
			else
				return "" + cell.numValue;
		case XSSFCell.CELL_TYPE_STRING:
			return cell.strValue;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			return "" + cell.boolValue;
		case XSSFCell.CELL_TYPE_BLANK:
		default: return "";
		}
	}
	
	public int getInt(int row, String columnName){
		process = columnName;
		int column = headers.get(columnName);
		return matriz[row][column].intValue;
	}
	
	public String getString(int row, String columnName){
		process = columnName;
		int column = headers.get(columnName).intValue();
		return matriz[row][column].strValue;
	}
	
	public double getDouble(int row, String columnName){
		process = columnName;
		int column = headers.get(columnName);
		return matriz[row][column].numValue;
	}

}


