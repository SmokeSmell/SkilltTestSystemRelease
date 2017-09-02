package com.gong.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gong.db.AbstractDAO;
import com.gong.db.MockScoreDAO;
import com.gong.db.OperatStatisDAO;
import com.gong.db.PreApplyDAO;
import com.gong.db.PreTaskDAO;
import com.gong.db.ScoreDAO;
import com.gong.domain.ItemInfo;
import com.gong.domain.Score;
import com.gong.helper.ReadExcelData;

public class DataImport extends HttpServlet {
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
		
		try {
			handler(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			handler(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void handler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		HashMap<String,ItemInfo> map = (HashMap<String,ItemInfo>)this.getServletContext().getAttribute("edit");
		String fileName = request.getParameter("fileName");
		ItemInfo item = map.get(fileName);
		PrintWriter out = response.getWriter();
		AbstractDAO dao = (AbstractDAO)Class.forName(item.getDAOClassName()).newInstance();
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
				ReadExcelData read = null;
				final List identis  = new ArrayList<String>();;
				if(fileName.equals("score")){
					read = new ReadExcelData(){
						@Override
						protected Object handValue(String key, Object value) {
							// TODO Auto-generated method stub
							if(key.equals("identity")){
								identis.add((String)value);
							}
							return super.handValue(key, value);
						}
					};
				}else{
					read = new ReadExcelData();
				}
				String tip = "";
				while (i.hasNext()){
					FileItem fm = (FileItem)i.next();
					read.setInputStream(fm.getInputStream());
					read.setColName(item.getImportColNames());
					read.setColAttr(item.getPropertyNames());
					Class cls = Class.forName(item.getDomainClassName());
					read.readData(cls);	
					List reads = read.getReadList();
					if(fileName.equals("score")){
						ScoreDAO scoreDAO = new ScoreDAO();
						HashMap<String,String> stu_map = scoreDAO.getStuNumberByIndentity(identis);
						if(stu_map == null){
							out.print("<p>成绩导入失败!</p><p>失败原因</p><p>未能根据学生身份证号"
									+ "获得学生考试号，请确定文件中学生信息无误或者系统中学生信息无误</p>");
							return;
						}
						for(int k = 0;k < identis.size();k++){
							Score score = (Score)reads.get(k);
							String number = stu_map.get(score.getIdentity());
							score.setStu_id(number);
						}
					}
					if(read.getErrMessage() != null){
						tip = read.getErrMessage();
					}else{
						int rs = dao.addRecords(reads);
						OperatStatisDAO statisDAO = new OperatStatisDAO();
						if(rs > 0 && fileName.equals("mockscore")){
							statisDAO.updateRecord("importMock",1);
						}else if(rs > 0 && fileName.equals("score")){
							this.getServletContext().removeAttribute("StrengthClassList");
							PreApplyDAO predao = new PreApplyDAO();
							predao.deleteAllRecords();
							MockScoreDAO mcdao = new MockScoreDAO();
							mcdao.deleteAllRecords();
							statisDAO.updateRecord("importScore",1);
							PreTaskDAO task = new PreTaskDAO();
							task.deletRecord();
						}else if(rs > 0 && fileName.equals("strclass")){
							this.getServletContext().removeAttribute("StrengthClassList");
						}
						tip = "<p>成功导入 "+rs+" 条记录,失败 "+(reads.size() - rs) +" 条</p>";
						if(rs < reads.size()){
							tip += "<p>失败原因:以下数据信息有误</p>";
							tip += dao.getErr_Message();
						}
						
					}
				}
				out.print(tip);
			}
			out.flush();
			out.close();
		}catch(Exception e){
			out.print(e.getMessage());
			e.printStackTrace();
		}
	}
}

