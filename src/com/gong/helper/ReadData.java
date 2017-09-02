package com.gong.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.gong.db.AbstractDAO;
import com.gong.db.MockScoreDAO;
import com.gong.db.PreApplyDAO;
import com.gong.db.StrClassDAO;
import com.gong.db.StuInfoDAO;
import com.gong.domain.AbstractData;
import com.gong.domain.ItemInfo;
import com.gong.domain.User;

public class ReadData {
	private ArrayList<AbstractData> datas;
	private HashMap<String,ItemInfo> das ;
	private String name;
	private int size;
	public void setMap(HashMap<String,ItemInfo> ds){
		das = ds;
	}
	public void setName(String n){
		name = n;
	}
	public void setFileName(InputStream inp){
		try{
			
			 ItemInfo info = das.get(name);
			 Workbook wb = WorkbookFactory.create(inp);
			 AbstractDAO dao = (AbstractDAO)Class.forName(info.getDAOClassName()).newInstance();
			 datas = new ArrayList<AbstractData>();
			 ParserObject paob = new ParserObject(info.getDomainClassName());
			 Sheet sheet1 = wb.getSheetAt(0);
			    for (Row row : sheet1) {
			    	int i = 0;
			    	AbstractData data = (AbstractData)Class.forName(info.getDomainClassName()).newInstance();
			        paob.setObject(data);
			        int size = 0;
			        if(name.equals("mockscore")) size =1;
			        boolean f = true;
			    	if (row.getRowNum() > size){
				        for (Cell cell : row) {
				        	int col = cell.getColumnIndex();
				        	if(name.equals("strclass")){
				        		if(i==0 || i==7)
				        			i++;
				        	}
				        	if(name.equals("mockscore")){
				        		if(col==0 || col==1 || col==3 || col==4 || col==5 || col==6 || col==7 || col==9)
				        			continue;
				        	}
				        	if(name.equals("score")){
				        		if(col==0 || col==3 || col==7 || col==8 || col==11 || col==12 || col==13 
				        			|| col==14)
				        			continue;
				        		if(col==16) break;
				        		if(i==0 && f) {
				        			i=1;
				        			f = false;
				        		}
				        	}
				     
				            switch (cell.getCellType()) {
				                case Cell.CELL_TYPE_STRING:
			//	                	System.out.println(cell.getRichStringCellValue().getString()+" "+i);
				                	String temp = cell.getRichStringCellValue().getString();
				                	if(name.equals("score") && i==0){
				                		StuInfoDAO stuinfodao = new StuInfoDAO();
				                		temp = stuinfodao.getStudent_number(temp);
				                	}
				                	paob.setValue(info.getPropertyNames()[i],temp,new Class[]{String.class});
				                	if(name.equals("score") && i==1){
				                		i=0;
				                	}else{
					                	if(name.equals("score") && i==0){
					                		i=2;
					                	}else{
					                		i++;
					                	}
				                	}
				                    break;
				                case Cell.CELL_TYPE_NUMERIC:
				                	
				                    if (DateUtil.isCellDateFormatted(cell)) {
				        
			                    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				                    	 temp = sdf.format(cell.getDateCellValue());
			//	                    	 System.out.println(temp+" "+i);
				                    	 paob.setValue(info.getPropertyNames()[i],temp,new Class[]{String.class});
				                    	 i++;
				                    	 
				                    	 break;
				                    } else {
				 //                   	System.out.println((int)cell.getNumericCellValue()+" "+i);
				                    	if(name.equals("mockscore")){
				                    		paob.setValue(info.getPropertyNames()[i],(float)cell.getNumericCellValue(),new Class[]{float.class});
				                    	}else{
				                    		paob.setValue(info.getPropertyNames()[i],(int)cell.getNumericCellValue(),new Class[]{int.class});
				                    	}
			                    		i++;
				                    	break;
				                    }
				                    
				                case Cell.CELL_TYPE_BOOLEAN:
				                	
				                    break;
				                case Cell.CELL_TYPE_FORMULA:
				                	 
				                    break;
				                default:
				       
				                    System.out.println();
				            }
				       
				        }
				    if (i!=0)
				        datas.add(data);
				    }
			    }
			 size = dao.addRecords(datas);
			 if(name.equals("score")){
				 PreApplyDAO predao = new PreApplyDAO();
				 predao.deleteAllRecords();
				 MockScoreDAO mcdao = new MockScoreDAO();
				 mcdao.deleteAllRecords();
			 }
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public int getSize(){
		return size;
	}
}
