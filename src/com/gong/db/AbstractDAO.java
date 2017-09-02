package com.gong.db;
import java.util.ArrayList;
import java.util.List;

import com.gong.domain.AbstractData;
import com.gong.domain.User;
import com.gong.helper.SysConstants;

public abstract class AbstractDAO {
	protected StringBuffer err_msg = new StringBuffer();
	public String editRecords(ArrayList<AbstractData> datas){
		ArrayList<AbstractData> addList = new ArrayList<AbstractData>();
		ArrayList<AbstractData> deleteList = new ArrayList<AbstractData>();
		ArrayList<AbstractData> updateList = new ArrayList<AbstractData>();
		
		int count = datas.size();
		for(int index = 0; index < count; index++){
			AbstractData data = datas.get(index);
			byte operCode = data.getOperator();
			if(operCode == SysConstants.ADD){
				addList.add(data);
			}else if(operCode == SysConstants.DELETE){
				deleteList.add(data);
			}else if(operCode == SysConstants.UPDATA){
				updateList.add(data);
			}
		}
		int add = addRecords(addList);
		int delete = deleteRecords(deleteList);
		int update = updateRecords(updateList);
		
		return "成功添加" + add + "条记录，成功删除" + delete + "条记录，成功修改" + update + "条记录";
	}
	
	public String [] getColumTitle(String[] colums){
		
		String []columNames = null;
		int col = 0;
		int count = colums.length;
		columNames = new String[count + 3];
		for(;col<count;col++){
			columNames[col] = colums[col];
		}
		columNames[col] = new String("添加");
		columNames[col+1] = new String("删除");
		columNames[col+2] = new String("修改");
		return columNames;
	}
public String [] getColumNames(String[] colums){
		
		String []columNames = null;
		int col = 0;
		int count = colums.length;
		columNames = new String[count + 3];
		for(;col<count;col++){
			columNames[col] = colums[col];
		}
		columNames[col] = new String("add");
		columNames[col+1] = new String("delete");
		columNames[col+2] = new String("update");
		return columNames;
	}
	public String getErr_Message(){
		if(err_msg != null)
			return err_msg.toString();
		return null;
	}
	public abstract String[] getColumTitle();
	public abstract String[] getColoumNames();
	public abstract ArrayList<AbstractData> getAllData();
	public abstract int addRecords(List datas);
	public abstract int deleteRecords(ArrayList<AbstractData> datas);
	public abstract int updateRecords(ArrayList<AbstractData> datas);
	public abstract int getRecordNum();
	public abstract ArrayList<AbstractData> getSpecifiedDatas(int page,int num);
	public abstract ArrayList<AbstractData> getSelectDatas(int page,int num,String name,String value);
	public abstract int getSelectRecordNum(String name,String value);
}
