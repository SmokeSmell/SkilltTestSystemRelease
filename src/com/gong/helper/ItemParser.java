package com.gong.helper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gong.domain.ItemInfo;

public class ItemParser {
	private Element root;
	
	public void init(String filename){
		try{
			InputStream is = new FileInputStream(filename);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			
			root = doc.getDocumentElement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public HashMap<String,ItemInfo> getConfigs(){
		HashMap<String,ItemInfo> infos = new HashMap<String,ItemInfo>();
		NodeList list = root.getElementsByTagName("Config");
		for(int index=0;index < list.getLength(); index++){
			Element parent = (Element)list.item(index);
			String id = parent.getAttribute("id");
			String dao = getValue(parent,"DAOClassName");
			String data = getValue(parent,"DomainClassName");
			String tablename = getValue(parent,"TableName");
			String[] propertyNames = this.getPropertyNames(parent, "propertyNames");
			String[] importColNames = this.getPropertyNames(parent, "importColNames");
			
			ItemInfo info = new ItemInfo();
			info.setItemName(id);
			info.setDAOClassName(dao);
			info.setDomainClassName(data);
			info.setTableName(tablename);
			info.setPropertyNames(propertyNames);
			info.setImportColNames(importColNames);
			//System.out.println(Arrays.toString(importColNames));
			infos.put(id, info);
		}
		this.clear();
		return infos;
	}
	//获得每个数据类的属性名
	private String[] getPropertyNames(Element parent,String tagName){
			String[] names = null;
			NodeList nodeList = parent.getElementsByTagName(tagName);
			if(nodeList.getLength() == 0) return null;
			Element ele = (Element)nodeList.item(0);
			NodeList nodes = ele.getElementsByTagName("propertyName");
			names = new String[nodes.getLength()];
			for(int index = 0;index < nodes.getLength();index++){
				Element element = (Element)nodes.item(index);
				int idx = Integer.valueOf(element.getAttribute("index"));
				Node node = element.getChildNodes().item(0);
				String value = node.getNodeValue();
				names[idx] = value;
			}
			return names;
	}
	public String getValue(Element parent,String tagName){
		Element child = (Element)parent.getElementsByTagName(tagName).item(0);
		NodeList list = child.getChildNodes();
		Node node = list.item(0);
		return node.getNodeValue();
	}
	public void clear(){
		root = null;
	}
}
