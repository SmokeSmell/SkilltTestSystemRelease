package com.gong.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gong.db.OperatStatisDAO;
import com.gong.domain.AbstractData;
import com.gong.domain.ItemInfo;
import com.gong.domain.StuInfo;
import com.gong.helper.ParserObject;
import com.gong.helper.ReadData;

public class uploadservlet extends HttpServlet {
	private ItemInfo info = null;
	private HashMap<String, ItemInfo> itemInfos;
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		handler(request,response);
	}
	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handler(request,response);
	
	}

	public void handler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String filename = request.getParameter("fileName");
		itemInfos = (HashMap<String, ItemInfo>) getServletContext().getAttribute("edit");
		PrintWriter out = response.getWriter();
		try{
			if(ServletFileUpload.isMultipartContent(request)){
				DiskFileItemFactory disk = new DiskFileItemFactory();
				disk.setSizeThreshold(20*1024);
				disk.setRepository(disk.getRepository());
				ServletFileUpload upload = new ServletFileUpload(disk);
				
				int maxsize = 20 * 1024 * 1024;
				upload.setSizeMax(maxsize);
				List list = upload.parseRequest(request);
				Iterator i = list.iterator();
				while (i.hasNext()){
					FileItem fm = (FileItem)i.next();
					String fileName = fm.getName();
					int count = fileName.indexOf(".");
					fileName = fileName.substring(0,count);
					System.out.println(filename);
					ItemInfo iteminfo = itemInfos.get(filename);
					System.out.println(fileName+","+iteminfo.getTableName());
					if(fileName.equals(iteminfo.getTableName())){
						ReadData r = new ReadData();
						r.setName(filename);
						r.setMap(itemInfos);
						r.setFileName(fm.getInputStream());
						if (r.getSize()==0){
							out.println("上传数据有误，请检查数据准确性！");
						}else{
							if(fileName.equals("成绩表")){
								OperatStatisDAO statisDAO = new OperatStatisDAO();
								statisDAO.updateRecord("importScore",1);
							}
							out.println("上传成功，成功添加"+r.getSize()+"条记录");
						}
					}else{
						out.println("导入的数据类型与表格不符！");
					}
					
				}
			}
			out.flush();
			out.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
