package com.gong.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParserObject {
	private String className;
	private Object object;
	private Class cls;
	public ParserObject(String className){
		this.className = className;
		try{
			cls = Class.forName(className);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setObject(Object object){
		this.object = object;
	}
	
	public Object getObject() {
		return object;
	}
	public Object getValue(String fieldName){
		Object obj = null;
		try{	
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method met = cls.getMethod(methodName);
			obj = met.invoke(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	public void setValue(String fieldName,Object value,Class[] paramTypes){
		
		try{
			String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method met = cls.getMethod(methodName,paramTypes);
			met.invoke(object,value);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public String[] getAllFieldNames(){
		Field[] fields  = cls.getDeclaredFields();
		String[] names = new String[fields.length];
		for(int i=0;i<fields.length;i++){
			Field field = fields[i];
			names[i] = field.getName();
		}
		return names;
	}
}
