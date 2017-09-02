package com.gong.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.gong.db.AbstractDAO;
import com.gong.db.MockScoreDAO;
import com.gong.db.NotifyDAO;
import com.gong.db.OperatStatisDAO;
import com.gong.db.PreApplyDAO;
import com.gong.db.PreTaskDAO;
import com.gong.db.ScoreDAO;
import com.gong.db.StrClassDAO;
import com.gong.db.StuInfoDAO;
import com.gong.db.TeacherDAO;
import com.gong.db.UserDAO;
import com.gong.domain.AbstractData;
import com.gong.domain.ArrangeClass;
import com.gong.domain.ItemInfo;
import com.gong.domain.MockScore;
import com.gong.domain.NotPay;
import com.gong.domain.Notify;
import com.gong.domain.PreApply;
import com.gong.domain.Score;
import com.gong.domain.StrengthClass;
import com.gong.domain.StuClass;
import com.gong.domain.StuInfo;
import com.gong.domain.User;
import com.gong.helper.ParserObject;

public class FrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			handler(req,resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			handler(req,resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void handler(HttpServletRequest req, HttpServletResponse resp) throws Exception, ServletException{
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		String operat = req.getParameter("operat");
		if(operat.equals("login")){
			this.loginHandler(req, resp);
		}else if(operat.equals("exit")){
			this.exitHandler(req, resp);
		}else if(operat.equals("preapply")){
			this.distrClass(req, resp);
		}
		else if(operat.equals("GETDATA"))
			this.getData(req, resp);
		else if(operat.equals("UPDATE"))
			this.updata(req, resp);
		else if(operat.equals("EXPORT"))
			this.exportData(req, resp);
		else if(operat.equals("alterpw"))
			this.alterPassword(req, resp);
		else if(operat.equals("COLEGEIMAGE")){
			this.getCollegeImage(req,resp);
		}else if(operat.equals("GETSTUINFO")){
			this.sureInfo(req, resp);
		}else if(operat.equals("PREAPPLYINFO")){
			this.preApplyInfo(req, resp);
		}else if(operat.equals("FINDSCORE")){
			findScore(req,resp);
		}else if(operat.equals("GETINFO")){
			this.getInfo(req, resp);
		}else if(operat.equals("UPDATEINFO")){
			this.updateInfo(req, resp);
		}else if(operat.equals("GETNOTIFYBYID")){
			this.getNotifyById(req, resp);
		}else if(operat.equals("GETFILE")){
			this.getNotifyFile(req,resp);
		}else if(operat.equals("GETALLNOTIFY")){
			this.getAllNotify(req, resp);
		}else if(operat.equals("DOWNLOADTEMPLATE")){
			this.handDownloadTemplate(req,resp);
		}else if(operat.equals("UPDATEMOBILE")){
			this.updateMobile(req, resp);
		}else if(operat.equals("EXPORTMOCKTEMPLAT")){
			this.exportMockScoreTemp(req, resp);
		}
	}
	/**
	 * 获得各个学院的图片
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void getCollegeImage(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession(true);
		ServletContext context = this.getServletContext();
		StuInfo info = (StuInfo)session.getAttribute("user");
		HashMap<String,String> colleges = (HashMap<String,String>)context.getAttribute("colleges");
		String img = colleges.get(info.getStudent_college());
		if(img == null)
			img = colleges.get("技能鉴定中心");
		out.print(img);
		out.flush();
		out.close();
	}
	/**
	 * 登录
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	public void loginHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
	
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession(true);
		StuInfo info = (StuInfo)session.getAttribute("user");
		User admin = (User)session.getAttribute("admin");
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		User user = new User();
		
		if(info != null){
			user.setUser_name(info.getStudent_number());
		}else if(admin != null)
			user.setUser_name(admin.getUser_name());
		else
			user.setUser_name(name);
		
		user.setPassword(password);
		
		UserDAO dao= new UserDAO();
		User u = dao.anthenticate(user);
		
		if(u != null){
			if (u.getRole().equalsIgnoreCase("学生") && info == null){
				StuInfoDAO infodao = new StuInfoDAO();
				StuInfo info2 = infodao.getRecord(u.getUser_name());
				session.setAttribute("user", info2);
			}else{
				session.setAttribute("admin", u);
			}
			out.print(u.getRole());
			
		}else{
			out.print("LOGINFAILL");
		}
		out.flush();
		out.close();
	}
	/**
	 * 注销
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void exitHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		session.removeAttribute("admin");
		req.getRequestDispatcher("jsp/index.jsp").forward(req, resp);
	}
	//分配教室
	public  void distrClass(HttpServletRequest req, HttpServletResponse resp) throws IOException, Exception{
		PrintWriter out = resp.getWriter();
		HttpSession session = (HttpSession)req.getSession();
		StuInfo info = (StuInfo)session.getAttribute("user");
		if(info == null){
			out.print("UNLOGIN");
		}
		String stu_number = info.getStudent_number();
		String result = "FULL";
		ServletContext context = this.getServletContext();	
		
		String start_time = (String)context.getAttribute("start_time");
		String end_time = (String)context.getAttribute("end_time");
		String cur_d = Calendar.getInstance().getTime().toLocaleString();
		cur_d = cur_d.substring(0,cur_d.indexOf(" "));
		if(start_time == null || end_time == null){
			PreTaskDAO task = new PreTaskDAO();
			HashMap<String,String> map = task.getRecord(cur_d);
			if(map == null){
				out.print("NOTAPPLYTIME");
				return;
			}
			start_time = map.get("start_time");
			end_time = map.get("end_time");
			context.setAttribute("start_time", start_time);
			context.setAttribute("end_time", end_time);
		}
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yyyy-MM-dd");
		Date sd = format.parse(start_time);
		Date ed = format.parse(end_time);
		
		Date cd =format.parse(cur_d);
		int cs = cd.compareTo(sd);
		int ce = cd.compareTo(ed);
		if(cs == -1 || ce == 1){
			out.print("NOTAPPLYTIME");
			return;
		}
		
		synchronized(this.getServletContext()){
			ArrayList<StrengthClass> strs = (ArrayList<StrengthClass>)this.getServletContext().getAttribute("StrengthClassList");
			System.out.println("str="+strs);
			if(strs == null){
				StrClassDAO strdao = new StrClassDAO();
				strs = strdao.getAllClass();
			}
			PreApplyDAO dao = new PreApplyDAO();
			for(int i = 0;i < strs.size();i++){
				StrengthClass strength = strs.get(i);
				int left = strength.getMax_num() + strength.getMax_expend() - strength.getCur_num();
				if(left > 0){
					PreApply apply = new PreApply();
					apply.setClass_id(Integer.valueOf(strength.getClass_id()));
					apply.setStu_number(stu_number);
					int rs = dao.addRecords(apply);
					if(rs >= 1){
						result = "SUCCESS";
						strength.addCur_num();
					}
					else
						result = "FAILED";
					break;
				}
			}
			context.setAttribute("StrengthClassList", strs);
		}
		out.print(result);
	}
	public void getData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int pagenum = Integer.parseInt(req.getParameter("page"));
		int showNum = Integer.parseInt(req.getParameter("num"));
		String name = req.getParameter("name");
		String value = req.getParameter("value");
		String source = req.getParameter("source");
		PrintWriter out = resp.getWriter();
		int allpage = 0;
		int total = 0;
		JSONObject jsonobj = new JSONObject();
		if(source.equals("arrangeclass") || source.equals("notpay") || source.equals("apply")){
			this.getSpecialData(req, resp, out);
		}else{
			HashMap<String,ItemInfo> itemInfos = (HashMap<String, ItemInfo>) getServletContext().getAttribute("edit");
			ItemInfo itemInfo = itemInfos.get(source);
			AbstractDAO dao = null;
			try{
				dao = (AbstractDAO) Class.forName(itemInfo.getDAOClassName()).newInstance();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			total = dao.getSelectRecordNum(name, value);
			float p = total / (showNum*1.0f);
			int p2=0;
			if(showNum!=0){
				p2 = total/showNum;
			}
			allpage = p > p2 ? (int)(p+1):p2;
			ArrayList<AbstractData> datas = dao.getSelectDatas(pagenum,showNum,name,value);
			//ArrayList<AbstractData> alldatas = dao.getAllData();
			
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[]{"operator"});
			JSONArray jsonarray = JSONArray.fromObject(datas,config);
			//JSONArray json = JSONArray.fromObject(alldatas,config);
			
			String[] titles = dao.getColumTitle();
			String[] columns = dao.getColoumNames();
			
			jsonobj.accumulate("titles",titles);
			jsonobj.accumulate("names", columns);
			jsonobj.accumulate("datas", jsonarray.toString());
			//jsonobj.accumulate("alldatas", json.toString());
			jsonobj.accumulate("total", total);
			jsonobj.accumulate("allpage", allpage);
			
			if(source.equals("mockscore")){
				String[] temp = new String[datas.size()];
				StuInfoDAO stuinfodao = new StuInfoDAO();
				for(int i=0;i<datas.size();i++){
					MockScore stuclass = (MockScore)datas.get(i);
					temp[i] = stuinfodao.getStudent_name(stuclass.getStu_number());
				}
			
				JSONArray jsont = JSONArray.fromObject(temp);
				jsonobj.accumulate("stunames", jsont);
			}
			if(source.equals("score")){
				String[] temp = new String[datas.size()];
				StuInfoDAO stuinfodao = new StuInfoDAO();
				for(int i=0;i<datas.size();i++){
					Score stuclass = (Score)datas.get(i);
					temp[i] = stuinfodao.getStudent_name(stuclass.getStu_id());
				}
			
				JSONArray jsont = JSONArray.fromObject(temp);
				jsonobj.accumulate("stunames", jsont);
			}
			if(source.equals("strclass")){
				TeacherDAO tdao = new TeacherDAO();
				ArrayList<AbstractData> temp = tdao.getAllData();
				JSONArray jsont = JSONArray.fromObject(temp);
				jsonobj.accumulate("teachers", jsont);
			}
			out.print(jsonobj.toString());
		}
		
	}
	public void updateMobile(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		String mobile = req.getParameter("data");
		StuInfo info = (StuInfo) req.getSession().getAttribute("user");
		if(info == null){
			out.print("UNLOGIN");
			return;
		}
		info.setStudent_mobile(mobile);
		StuInfoDAO dao = new StuInfoDAO();
		boolean rs = dao.updateRecord(info);
		req.getSession().setAttribute("user", info);
		if(rs)
			out.print("SUCCESS");
		else 
			out.print("FAIL");
		out.close();
	}
	//更改密码
	public void alterPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		PrintWriter out = resp.getWriter();
		HttpSession session = (HttpSession)req.getSession();
		StuInfo info = (StuInfo)session.getAttribute("user");
		User admin = (User)session.getAttribute("admin");
		
		String name = "";
		String password = req.getParameter("password");
		if(info != null){
			name = info.getStudent_number();	
		}else{
			name = admin.getUser_name();
		}

		User user = new User();
		user.setUser_name(name);
		user.setPassword(password);
		
		UserDAO dao= new UserDAO();
		int x = dao.updateRecord(user);
		if (x==1){
			out.print("SUCCESS");
		}else{
			out.print("ERROR");
		}
		out.flush();
		out.close();
		
	}
	//根据通知编号获得通知
	public void getNotifyById(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		String s = request.getParameter("notify_id");
		if(s != null && !s.equals("")){
			int id = Integer.valueOf(s);
			NotifyDAO dao = new NotifyDAO();
			Notify notify = dao.getNotifyById(id);
			JSONObject obj = JSONObject.fromObject(notify);
			out.print(obj.toString());
		}
	}
	//获得所有通知信息
	public void getAllNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		NotifyDAO dao = new NotifyDAO();
		PrintWriter out = resp.getWriter();
		String s = req.getParameter("limit");
		String s2 = req.getParameter("page");
		JSONObject obj = new JSONObject();
		if(s2 != null && s != null){
			int page = Integer.valueOf(s2);
			int num = Integer.valueOf(s);
			List<Notify> list = dao.getSpecialNotifyName(page, num);
			obj.accumulate("data", list);
			out.print(obj.toString());
		}else
		if(s != null){
			Integer limit = Integer.valueOf(s);
			List<Notify> list = dao.getNotifyName(limit);
			int num = dao.getRecordsNum();
			obj.accumulate("data", list);
			obj.accumulate("num", num);
			out.print(obj.toString());
		}
	}
	public void getSpeNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		NotifyDAO dao = new NotifyDAO();
		PrintWriter out = resp.getWriter();
		String s = req.getParameter("page");
		String s2 = req.getParameter("num");
		if(s != null || !s.equals("")){
			int page = Integer.valueOf(s);
			int num = Integer.valueOf(s2);
			List<Notify> list = dao.getSpecialNotifyName(page, num);
			JSONObject obj = new JSONObject();
			obj.accumulate("data", list);
			out.print(obj.toString());
		}
	}
	public void updata(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		HashMap<String,ItemInfo> items = (HashMap<String,ItemInfo>)this.getServletContext().getAttribute("edit");
		String table = req.getParameter("source");
		String jstxt = req.getParameter("data");
		ItemInfo info = items.get(table);
		String msg = null;
		try {
			JSONArray jsonarry = JSONArray.fromObject(jstxt);
			Class cls = Class.forName(info.getDomainClassName());
			ArrayList<AbstractData> datas = (ArrayList<AbstractData>)JSONArray.toCollection(jsonarry, cls);
			Class daoclass = Class.forName(info.getDAOClassName());
			AbstractDAO dao = (AbstractDAO)daoclass.newInstance();
			if(table.equals("strclass")){
				this.getServletContext().removeAttribute("StrengthClassList");
			}
			msg += "<p>";
			msg = dao.editRecords(datas);
			msg += "</p>";
			String err = dao.getErr_Message();	
			if(err.length() > 0){
				msg += "<p>失败原因:</p>";
				msg += "<p>"+err+"</p>";
			}	
		} catch (Exception e) {
			msg += "<p>失败原因:</p>";
			msg += "<p>"+e.getMessage()+"</p>";
			e.printStackTrace();
		}
		out.println(msg);
		out.flush();
		out.close();
	}
	public void exportMockScoreTemp(HttpServletRequest request, HttpServletResponse response){
		try{
			MockScoreDAO dao = new MockScoreDAO();
			ArrayList<HashMap<String,String>> datas = dao.getMockScoreInfo();
			String path = this.getServletContext().getRealPath("/download");
			Workbook wb = new HSSFWorkbook();
			HSSFFont font = (HSSFFont)wb.createFont(); 
		    Sheet sheet = wb.createSheet("所有强化班名册");
		
		    String[] colNames = {"序号","姓名","学号","性别","身份证","上课地点","班级","学院","模拟成绩","任课教师"};
		    String[] key = {"id","name","number","sex","identity","place","class","college","score","teacher"};
		    Row title = sheet.createRow(0);
		   
		    for(int i = 0;i < colNames.length;i++){
		    	
		    	if(i == 0 || i == 1 || i == 8 || i == 9)
		    		sheet.setColumnWidth(i, 10 * 256);
		    	else if(i == 3)
		    		sheet.setColumnWidth(i, 5 * 256);
		    	else{
		    		sheet.setColumnWidth(i, 25 * 256);
		    	}
		    	Cell cell = title.createCell(i);
		    	cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    	 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    	cell.getCellStyle().setFont(font);
		    	//cell.getCellStyle().set
		    	cell.setCellValue(colNames[i]);
		    }
		   // System.out.println(datas.size());
		    HSSFFont font2 = (HSSFFont)wb.createFont(); 
		    font2.setBoldweight(HSSFFont.DEFAULT_CHARSET);
		    for(int i = 0;datas != null && i < datas.size();i++){
		    	HashMap<String,String> map = datas.get(i);
		    	Row row = sheet.createRow(i+1);
		    	for(int j = 0;j < key.length;j++){
		    		Cell cell = row.createCell(j);
		    		cell.getCellStyle().setFont(font2);
		    		if(key[j].equals("id")){
		    			cell.setCellValue((i+1));
		    		}
		    		else if(key[j].equals("score")){
		    			cell.setCellType(Cell.CELL_TYPE_STRING);
		    			cell.setCellValue("");
		    		}else{
		    			cell.setCellType(Cell.CELL_TYPE_STRING);
		    			String value = map.get(key[j]);
		    			//System.out.println(value);
		    			if(value != null)
		    				cell.setCellValue(value);
		    		}
		    	}
		    }
		    String fileName = "模拟成绩导入表.xls";
		    FileOutputStream fileOut = new FileOutputStream(path+File.separator+fileName);
		    wb.write(fileOut);
		    fileOut.close();
		    wb.close();
		    response.setHeader( "Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) );
			RequestDispatcher disp = request.getRequestDispatcher("download/"+fileName);
			disp.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//导出数据
	private void exportData(HttpServletRequest req, HttpServletResponse resp)throws IOException{
		
		String source = req.getParameter("source");
		String fileName = "";
		String tip = null;
		PreApplyDAO pdao = new PreApplyDAO();
		StuInfoDAO stuinfodao = new StuInfoDAO();
		try{ 
			Workbook wb = new HSSFWorkbook();
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("/download");
		    Sheet sheet = wb.createSheet("new sheet");
		    sheet.setDefaultColumnWidth((short) 15);
		    Row rw = sheet.createRow(0);
			if(source.equals("notpay")){
				fileName = "未缴费学生名单";
				String[] titles = {"学号","姓名","缴费类型","缴费标准"};
				String[] columns = {"student_number","student_name","exam_result","cost"};
				ArrayList<NotPay> notpays = pdao.getDatas("", "");
				HSSFFont font = (HSSFFont)wb.createFont();        
				font.setFontHeightInPoints((short) 14);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				for(int i=0;i<titles.length;i++){
				    Cell cell = rw.createCell(i);
				    cell.setCellValue(titles[i]);
				    cell.getCellStyle().setFont(font);
				}
				ParserObject parser = new ParserObject("com.gong.domain.NotPay");
				HSSFFont font2 = (HSSFFont)wb.createFont();        
				font2.setFontHeightInPoints((short) 11);
				for(int i = 0;i < notpays.size();i++){
			    	Row row = sheet.createRow(i+1);
			    	NotPay data = notpays.get(i);
			    	parser.setObject(data);
			    	for(int index = 0;index<columns.length;index++){
			    		String value = String.valueOf(parser.getValue(columns[index]));
			    		Cell cell = row.createCell(index);
			    		cell.setCellValue(value);
			    		cell.getCellStyle().setFont(font2);
			    	}
			    }
			}
			if(source.equals("arrangeclass")){
				fileName = "强化班安排表";
				String[] titles = {"序号","姓名","学号","性别","身份证","上课地点","班级","学院","任课教师","开始时间","结束时间"};
				String[] columns = {"student_name","student_number","student_sex","student_identity","class_place","student_class","student_college","teacher_name","start_time","end_time"};
				sheet.setColumnWidth(0, 10 * 256);
				sheet.setColumnWidth(3, 10 * 256);
				sheet.setColumnWidth(4, 25 * 256);
				sheet.setColumnWidth(7, 25 * 256);
				ArrayList<ArrangeClass> arranges = stuinfodao.getDatas("", "");
				HSSFFont font = (HSSFFont)wb.createFont();        
				font.setFontHeightInPoints((short) 14);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				for(int i=0;i<titles.length;i++){
					
				    Cell cell = rw.createCell(i);
				    cell.setCellValue(titles[i]);
				    cell.getCellStyle().setFont(font);
				    cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
				}
				HSSFFont font2 = (HSSFFont)wb.createFont();        
				font2.setFontHeightInPoints((short) 11);
				ParserObject parser = new ParserObject("com.gong.domain.ArrangeClass");
				for(int i = 0;i < arranges.size();i++){
			    	Row row = sheet.createRow(i+1);
			    	ArrangeClass data = arranges.get(i);
			    	parser.setObject(data);
			    	Cell cell = row.createCell(0);
		    		cell.setCellValue((i+1));
			    	for(int index = 1;index<=columns.length;index++){
			    		String value = String.valueOf(parser.getValue(columns[index-1]));
			    		cell = row.createCell(index);
			    		cell.setCellValue(value);
			    		cell.getCellStyle().setFont(font2);
			    	}
			    }
			}
			if(source.equals("apply")){
				path = context.getRealPath("/download/计算机等级考试报名表.xls");
				fileName = "计算机等级考试报名表";
				FileInputStream fs=new FileInputStream(path);  //获取d://test.xls  
		        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
		        wb=new HSSFWorkbook(ps);    
		        sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表  
		        HSSFRow rows=(HSSFRow) sheet.getRow(0);  //获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值  
		     //   System.out.println(sheet.getLastRowNum()+" "+rows.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格  
		        for(int i=1;i<=sheet.getLastRowNum();i++){
		        	HSSFRow row = (HSSFRow) sheet.getRow(i);
		        	sheet.removeRow(row);
		        }
		        ArrayList<StuInfo> pres = stuinfodao.getRecord("","");
		        for(int i=0;i<pres.size();i++){
		        	 StuInfo stu = pres.get(i);
		        	 rows=(HSSFRow) sheet.createRow((short)(i+1)); //在现有行号后追加数据  
		        	 rows.createCell(0).setCellValue("1");
		        	 rows.createCell(1).setCellValue(stu.getStudent_identity());
		        	 rows.createCell(2).setCellValue(stu.getStudent_name());
		        	 rows.createCell(3).setCellValue("");
		        	 rows.createCell(4).setCellValue("");
		        	 Cell cell = rows.createCell(5);
		        	 cell.setCellValue(stu.getStudent_sex());
		        	 rows.createCell(6).setCellValue("");
		        	 rows.createCell(7).setCellValue("30");
		        	 rows.createCell(8).setCellValue("");
		        	 rows.createCell(9).setCellValue("1");
		        	 rows.createCell(10).setCellValue("");
		        	 rows.createCell(11).setCellValue("2050");
		        	 rows.createCell(12).setCellValue("");
		        	 rows.createCell(13).setCellValue("");
		        	 rows.createCell(14).setCellValue("01");
		        	 rows.createCell(15).setCellValue("02");
		        	 cell = rows.createCell(16);
		        	 cell.setCellValue("02568533128");
		        	 cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        	 rows.createCell(17).setCellValue("");
		        	 rows.createCell(18).setCellValue("");
		        	 rows.createCell(19).setCellValue("");
		        	 rows.createCell(20).setCellValue("");
		        	 rows.createCell(21).setCellValue("");
		        	 rows.createCell(22).setCellValue("");
		        	 rows.createCell(23).setCellValue("");
		        	 rows.createCell(24).setCellValue("");
		        	 rows.createCell(25).setCellValue("");
		        	 rows.createCell(26).setCellValue("");
		        	 cell = rows.createCell(27);
		        	 cell.setCellValue("20145");
		        	 cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        	 rows.createCell(28).setCellValue("4");
		        	 rows.createCell(29).setCellValue("10");
		        	 rows.createCell(30).setCellValue("11");
		        	 rows.createCell(31).setCellValue("");
		        	 rows.createCell(32).setCellValue("");
		        	 rows.createCell(33).setCellValue("");
		        	 rows.createCell(34).setCellValue("");
		        	 cell = rows.createCell(35);
		        	 cell.setCellValue("南京铁道");
		        	 cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        	 rows.createCell(36).setCellValue(stu.getStudent_class());
		        	 rows.createCell(37).setCellValue("");
		        	 rows.createCell(38).setCellValue("");
		        	 cell = rows.createCell(39);
		        	 cell.setCellValue("1");
		        	 cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        }
		        OperatStatisDAO statisDAO = new OperatStatisDAO();
		        statisDAO.updateRecord("ExportExam",1);
			}
			FileOutputStream fileOut=null;
			if(source.equals("apply")){
				fileOut = new FileOutputStream(path);
			}else{
				fileOut = new FileOutputStream(path+File.separator+fileName+".xls");
			}
			
		    wb.write(fileOut);
		    fileOut.close();
		    fileName += ".xls";
		    //tip = "/download/"+source+".xls";
		    resp.setHeader( "Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) );
			RequestDispatcher disp = req.getRequestDispatcher("download/"+fileName);
			disp.forward(req, resp);
		}catch(Exception e){
			PrintWriter out = resp.getWriter();
			tip = "文件导出失败";
			out.print(tip);
			out.flush();
			out.close();
			e.printStackTrace();
		}
		
	}
	//预报名信息确定
	public void sureInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		PrintWriter out = resp.getWriter();
		StuInfo info = (StuInfo)req.getSession().getAttribute("user");
		String rs = "";
		String examtype = null;
		JSONObject json = new JSONObject();
		if(info == null)
			json.accumulate("state", "UnLogin");
		else {
			StuInfoDAO infodao = new StuInfoDAO();
			examtype = infodao.getExamType(info.getStudent_number());
			//info.setExamType(examtype);
			PreApplyDAO dao = new PreApplyDAO();
			boolean applyed = dao.isApplyed(info.getStudent_number());
			if(applyed){
				json.accumulate("state", "APPLYED");
			}else{
				json.accumulate("state", "Login");
				json.accumulate("infos",info);
				json.accumulate("examType", examtype);
			}
		}
		//System.out.println(json.toString());
		out.print(json.toString());
		out.close();
	}
	//获得预报名信息
	public void preApplyInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		StuInfo info = (StuInfo)req.getSession().getAttribute("user");
		if(info == null){
			json.accumulate("state", "UnLogin");
		}else{
			PreApplyDAO dao = new PreApplyDAO();
			boolean applyed = dao.isApplyed(info.getStudent_number());
			if(!applyed){
				json.accumulate("state", "UnApply");
			}else
				json.accumulate("state", "Applyed");
		}
		out.print(json.toString());
	}
	//成绩查询
	public void findScore(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		StuInfo info = (StuInfo)req.getSession().getAttribute("user");
		JSONObject json = new JSONObject();
		if(info == null){
			json.accumulate("state", "UnLogin");
			out.print(json.toString());
			out.close();
			return;
		}
		MockScoreDAO mockDAO = new MockScoreDAO();
		HashMap<String,String> map = mockDAO.findMockScore(info.getStudent_number());
		String[] key = null;
		String[] title = null;
		if(map != null){
			key = new String[]{"student_name","student_number","student_identity","class__name"
					,"class_place","teacher","score"};
			title = new String[]{"姓名:","学号","身份证号","强化班名称:","强化班地点:","强化班任课教师:","模拟成绩:"};
			System.out.println("score:"+map.get("score"));
			json.accumulate("key", key);
			json.accumulate("head","计算机等级考试模拟成绩信息");
			json.accumulate("title", title);
			json.accumulate("data", map);
			json.accumulate("info", info);
		}else{
			ScoreDAO dao = new ScoreDAO();
			Score score = dao.findScore(info.getStudent_number());
			if(score != null){
				key = new String[]{"student_name","student_number","student_identity","exam_number","occupation","degree","auth_class","practice_score","eva_score","status"};
				title = new String[]{"姓名:","学号:","身份证号:","考号:","鉴定职业","级别","鉴定科目","实操成绩:","评估成绩:","考试状态:"};
				json.accumulate("key", key);
				json.accumulate("head","计算机等级考试成绩信息");
				json.accumulate("title", title);
				json.accumulate("data", score);
				json.accumulate("info", info);
			}else{
				json.accumulate("state", "No Find");
			}
		}
		
		out.print(json.toString());
		out.close();
	}
	//获取个人信息
	public void getInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		StuInfo info = (StuInfo)req.getSession().getAttribute("user");
		if(info == null){
			json.accumulate("state", "UnLogin");
		}else{
			json.accumulate("state", "Logined");
			json.accumulate("data", info);
		}
		out.print(json.toString());
	}
	//修改个人信息
	public void updateInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		String jsontext = req.getParameter("data");
		JSONObject obj = JSONObject.fromObject(jsontext);
		StuInfo info = (StuInfo)obj.toBean(obj, StuInfo.class);
		StuInfoDAO dao = new StuInfoDAO();
		boolean rs = dao.updateRecord(info);
		req.getSession().setAttribute("user", info);
		if(rs)
			out.print("SUCCESS");
		else 
			out.print("FAIL");
		out.close();
	}
	//获得通知附件
	public void getNotifyFile(HttpServletRequest req,HttpServletResponse resp) throws IOException, Exception{
		String s = req.getParameter("notify_id");
		int id = Integer.valueOf(s);
		NotifyDAO dao = new NotifyDAO();
		Notify notify = dao.getNotifyById(id);
		String name = notify.getAttach();
		resp.setHeader( "Content-Disposition", "attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1" ) );
		RequestDispatcher disp = req.getRequestDispatcher("uploaded/"+name);
		disp.forward(req, resp);
	}
	public void handDownloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String para = request.getParameter("fileName");
		String name = "";
		if(para.equals("apply"))
			name = "计算机等级考试预报名表.xls";
		else if(para.equals("pay"))
			name = "预报名学生缴费表.xls";
		else if(para.equals("dataedit"))
			name = "数据编辑模板.zip";
		response.setHeader( "Content-Disposition", "attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1" ) );
		RequestDispatcher disp = request.getRequestDispatcher("importTemplate/"+name);
		disp.forward(request, response);
	}
	private String find_class(ArrayList<StrengthClass> clazz,HashMap<String,Object> distrClass){
		String class_id = null;
		for(int index=0;index < clazz.size();index++){
			StrengthClass clas = clazz.get(index);
			int left = clas.getMax_num() + clas.getMax_expend() - clas.getCur_num();
			if(left > 0){
				class_id = clas.getClass_id();
				distrClass.put("exp_index",index);
				break;
			}
		}
		return class_id;
	}
	public void getSpecialData(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws UnsupportedEncodingException{
		int pagenum = Integer.parseInt(request.getParameter("page"));
		int showNum = Integer.parseInt(request.getParameter("num"));
		String source = request.getParameter("source");
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		int allpage = 0;
		int total = 0;
		JSONObject jsonobj = new JSONObject();
		PreApplyDAO pdao = new PreApplyDAO();
		StuInfoDAO stuinfodao = new StuInfoDAO();
		if(source.equals("notpay")){
			String[] titles = {"学号","姓名","缴费类型","缴费标准"};
			String[] columns = {"student_number","student_name","exam_result","cost"};
			ArrayList<NotPay> notpays = pdao.getDatas(name,value);
			total = notpays.size();
			
			float p = total / (showNum*1.0f);
			int p2=0;
			if(showNum!=0){
				p2 = total/showNum;
			}
			allpage = p > p2 ? (int)(p+1):p2;
			int begin = (pagenum-1)*showNum;
			begin = begin > 0 ? begin:0;
			ArrayList<NotPay> datas = new ArrayList<NotPay>();
			for(int i=begin;i<begin+showNum;i++){
				if(i<notpays.size()){
					datas.add(notpays.get(i));
				}else break;
			}
			JSONArray jsonarray = JSONArray.fromObject(datas);
			jsonobj.accumulate("titles",titles);
			jsonobj.accumulate("names", columns);
			jsonobj.accumulate("datas", jsonarray.toString());
			jsonobj.accumulate("total", total);
			jsonobj.accumulate("allpage", allpage);
		}
		if(source.equals("arrangeclass")){
			String[] titles = {"姓名","学号","性别","身份证","上课地点","班级","学院","任课教师","开始时间","结束时间"};
			String[] columns = {"student_name","student_number","student_sex","student_identity","class_place","student_class","student_college","teacher_name","start_time","end_time"};
			
			ArrayList<ArrangeClass> arranges = stuinfodao.getDatas(name, value);
			total = arranges.size();
			
			float p = total / (showNum*1.0f);
			int p2=0;
			if(showNum!=0){
				p2 = total/showNum;
			}
			allpage = p > p2 ? (int)(p+1):p2;
			int begin = (pagenum-1)*showNum;
			begin = begin > 0 ? begin:0;
			ArrayList<ArrangeClass> datas = new ArrayList<ArrangeClass>();
			for(int i=begin;i<begin+showNum;i++){
				if(i<arranges.size()){
					datas.add(arranges.get(i));
				}else break;
			}
			JSONArray jsonarray = JSONArray.fromObject(datas);
			jsonobj.accumulate("titles",titles);
			jsonobj.accumulate("names", columns);
			jsonobj.accumulate("datas", jsonarray.toString());
			jsonobj.accumulate("total", total);
			jsonobj.accumulate("allpage", allpage);
		}
		if(source.equals("apply")){
			String[] titles = {"姓名","学号","性别","身份证","班级"};
			String[] columns = {"student_name","student_number","student_sex","student_identity","student_class"};
			
			ArrayList<StuInfo> pres = stuinfodao.getRecord(name,value);
	
			total = pres.size();
			
			float p = total / (showNum*1.0f);
			int p2=0;
			if(showNum!=0){
				p2 = total/showNum;
			}
			allpage = p > p2 ? (int)(p+1):p2;
			int begin = (pagenum-1)*showNum;
			begin = begin > 0 ? begin:0;
			ArrayList<StuInfo> datas = new ArrayList<StuInfo>();
			for(int i=begin;i<begin+showNum;i++){
				if(i<pres.size()){
					datas.add(pres.get(i));
				}else break;
			}
			JSONArray jsonarray = JSONArray.fromObject(datas);
			jsonobj.accumulate("titles",titles);
			jsonobj.accumulate("names", columns);
			jsonobj.accumulate("datas", jsonarray.toString());
			jsonobj.accumulate("total", total);
			jsonobj.accumulate("allpage", allpage);
		}
		out.print(jsonobj.toString());
	}
	
}
