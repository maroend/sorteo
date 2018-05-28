package com.core;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXLSX {
	
	public void read(){
		
		System.out.println ("read()");
		try{
	        File excel =  new File ("C:\\Users\\Rogelio\\Desktop\\img\\BOLS.xlsx");
	        FileInputStream fis = new FileInputStream(excel);
	        XSSFWorkbook wb = new XSSFWorkbook(fis);
	        XSSFSheet ws = wb.getSheet("Sheet");
	        
	        System.out.println ("ws:"+ws);
	
	        int rowNum = ws.getLastRowNum() + 1;
	        int colNum = ws.getRow(0).getLastCellNum();
	        String [][] data = new String [rowNum] [colNum];
	
	        for(int i = 0; i <rowNum; i++){
	            XSSFRow row = ws.getRow(i);
	                for (int j = 0; j < colNum; j++){
	                    XSSFCell cell = row.getCell(j);
	                    String value = cell.toString();
	                    data[i][j] = value;
	                    System.out.println ("the value is " + value);
	                }
	        }
	        wb.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
