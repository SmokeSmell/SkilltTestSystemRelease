package com.gong.helper;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.gong.domain.ItemInfo;
import com.gong.domain.StuInfo;

public class ReadExcelData {
	protected List readList = null;
	protected String[] colName = null;
	protected String[] colAttr = null;
	protected ItemInfo items = null;
	protected int[] colNum = null;
	protected int startRow = 0;
	protected InputStream inputStream;
	protected String err_msg = null;
	
	public void readData(Class cls) throws Exception{
		readList = new ArrayList<Object>();
		Workbook wb = WorkbookFactory.create(inputStream);
		Sheet sheet1 = wb.getSheetAt(0);
		boolean check = getColIndex(sheet1);
		if(check){
			int i = 0;
		    for (Row row : sheet1) {
	   		int j = 0;
	   		int k = 0;
	   		if(i <= startRow){
	   			i++;
	   			continue;
	   		}
	   		StringBuffer json = new StringBuffer("{");
	        for (Cell cell : row) {	
	        	Object temp = null;
	        	j = cell.getColumnIndex();
	            switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                	temp = cell.getRichStringCellValue().getString();
	                 break;
	                case Cell.CELL_TYPE_NUMERIC:
	                	if (DateUtil.isCellDateFormatted(cell)) {
	                		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    	temp = sdf.format(cell.getDateCellValue());
	                	}else{
	                		cell.setCellType(Cell.CELL_TYPE_STRING);
	                		temp = cell.getRichStringCellValue().getString();
	                	}
	                	break;
		        }
	            if(temp != null)
	            temp = ((String)temp).replaceAll(" ", "");
	            if(k < colNum.length && j == colNum[k] && temp != null && ((String)temp).length() > 0){
	            	temp = handValue(colAttr[k],temp);
	            	if(temp != null)
	            	json.append("\""+colAttr[k]+"\":"+"\""+temp+"\",");
	            	k++;
	            }
	        }
	        json = json.deleteCharAt(json.length() - 1);
	        if(json.length() > 1){
	        	 json.append("}");
	        	// System.out.println(json);
	 		    JSONObject obj = JSONObject.fromObject(json.toString());
	 		    Object object = JSONObject.toBean(obj, cls);
	 		    object = handObject(object);
	 		    readList.add(object);
	        }
	        i++;
		   }  
		}else{
			err_msg = "表格格式有误,请检查列名是否对应!";
		}
	}
	protected Object handObject(Object obj){
		return obj;
	}
	protected Object handValue(String key,Object value){
		return value;
	}
	public boolean getColIndex(Sheet sheet){
		int r = 0;
		colNum = new int[colName.length];
		for (Row row : sheet) {
			int k = 0;
			int j = 0;
			for (Cell cell : row) {	
	        	Object temp = null;
	            switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                	temp = cell.getRichStringCellValue().getString();
	                 break;
		        }
	            if(temp != null && temp.equals(colName[k])){
	            	colNum[k] = j;
	            	k++;
	            }
	            if(k == colName.length){
					startRow = r;
					return true;
				}
	            j++;
	        }
			if(r > 5) return false;
			r++;
		}
		return false;
	}
	public String getErrMessage(){
		return this.err_msg;
	}
	public String[] getColAttr() {
		return colAttr;
	}
	public void setColAttr(String[] colAttr) {
		this.colAttr = colAttr;
	}
	public List getReadList() {
		return readList;
	}
	public void setReadList(ArrayList<Object> readList) {
		this.readList = readList;
	}
	public String[] getColName() {
		return colName;
	}
	public void setColName(String[] colName) {
		this.colName = colName;
	}
	public ItemInfo getItems() {
		return items;
	}
	public void setItems(ItemInfo items) {
		this.items = items;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
