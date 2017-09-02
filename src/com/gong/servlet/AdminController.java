package com.gong.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.gong.db.AbstractDAO;
import com.gong.db.ExtraExamDAO;
import com.gong.db.NotifyDAO;
import com.gong.db.OperatStatisDAO;
import com.gong.db.PayLogDAO;
import com.gong.db.PreApplyDAO;
import com.gong.db.PreTaskDAO;
import com.gong.db.StatisDAO;
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
import com.gong.domain.OperatStatis;
import com.gong.domain.PayLog;
import com.gong.domain.PreApply;
import com.gong.domain.Score;
import com.gong.domain.StrengthClass;
import com.gong.domain.StuInfo;
import com.gong.domain.User;
import com.gong.helper.ReadExcelData;

public class AdminController extends HttpServlet {
	private HashMap<String, ItemInfo> itemInfos;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			this.handRequest(request, response,out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			this.handRequest(request, response,out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();
		
	}

	public void handRequest(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws Exception{
		
		String operat = request.getParameter("operat");
		if(operat.equals("GETDATA"))
			this.getData(request,response,out);
		else if(operat.equals("getlist"))
			this.getList(request, response, out);
		else if(operat.equals("UPLOAD")){
			this.upload(request, response);
		}else if(operat.equals("PUBLISHNOTIFY"))
			this.pulishNotify(request,response,out);
		else if(operat.equals("GETNOTIFY")){
			this.getNotify(request, response, out);
		}else if(operat.equals("DELNOTIFY")){
			this.delNotify(request, response, out);
		}else if(operat.equals("ALTERNOTIFY")){
			this.alterNotify(request,response,out);
		}else if(operat.equals("PREAPPLYTASK")){
			this.preApplyTask(request,response,out);
		}else if(operat.equals("GETPRETASK")){
			this.getPreTask(request, response, out);
		}else if(operat.equals("GETAPPLYED")){
			this.getPreApplyInfo(request, response, out);
		}else if(operat.equals("HANDBATCHAPPLY")){
			this.handlerBatchPreApply(request, response);
		}else if(operat.equals("GETALLCLASS")){
			this.getAllClass(request,response,out);
		}else if(operat.equals("PREAPPLYMANAGER")){
			this.PreApplyManager(request, response, out);
		}else if(operat.equals("IMPORTPAYED")){
			this.importPayed(request, response);
		}else if(operat.equals("GETPAYLOG")){
			this.getPayLog(request, response, out);
		}else if(operat.equals("GETSTATIS")){
			this.getStatis(request,response,out);
		}else if(operat.equals("AUTHUSER")){
			this.authUser(request, response, out);
		}else if (operat.equals("GETOPERATSTATIS")){
			this.getOperatStatis(request, response, out);
		}else if(operat.equals("GETSElE")){
			this.getSelectDatas(request,response,out);
		}
	}
	public void authUser(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("admin");
		if(user == null)
			out.print("FAILL");
		else{
			out.print(user.getUser_name());
		}
	}
	public void getList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String listname = request.getParameter("listname");
		UserDAO dao = new UserDAO();
		ArrayList<String> lists = dao.getList(listname);
		JSONArray json = JSONArray.fromObject(lists);
		out.print(json.toString());
	}
	//获得编辑数据信息
public void getData(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws UnsupportedEncodingException{
		
		int pagenum = Integer.parseInt(request.getParameter("page"));
		int showNum = Integer.parseInt(request.getParameter("num"));
		String source = request.getParameter("source");
		int allpage = 0;
		int total = 0;
		JSONObject jsonobj = new JSONObject();
		if(source.equals("arrangeclass") || source.equals("notpay") || source.equals("apply")){
			this.getSpecialData(request, response, out);
		}else{
			itemInfos = (HashMap<String, ItemInfo>) getServletContext().getAttribute("edit");
			ItemInfo itemInfo = itemInfos.get(source);
			AbstractDAO dao = null;
			try{
				dao = (AbstractDAO) Class.forName(itemInfo.getDAOClassName()).newInstance();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			total = dao.getRecordNum();
			
			float p = total / (showNum*1.0f);
			int p2=0;
			if(showNum!=0){
				p2 = total/showNum;
			}
			allpage = p > p2 ? (int)(p+1):p2;
			 
			ArrayList<AbstractData> datas = dao.getSpecifiedDatas(pagenum,showNum);
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
	//发布通知
	public void pulishNotify(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		NotifyDAO dao = new NotifyDAO();
		String data = request.getParameter("data");
		String content = request.getParameter("content");
		JSONObject obj = JSONObject.fromObject(data);
		Notify notify = (Notify)JSONObject.toBean(obj, Notify.class);
		notify.setContent(content);
		System.out.println(notify.getContent());
		Calendar cl = Calendar.getInstance(Locale.CHINA);
		String time = cl.getTime().toLocaleString();
		notify.setTime(time);
		int rs = dao.publishNotify(notify);
		if(rs == 0)
			out.print("FAILL");
		else
			out.print("SUCCESS");
		out.close();
	}
	//获得通知
	public void getNotify(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		NotifyDAO dao = new NotifyDAO();
		String s = request.getParameter("limit");
		int limit = Integer.valueOf(s);
		List<Notify> list = dao.getNotifyName(limit);
		JSONArray array = JSONArray.fromObject(list);
		out.print(array.toString());
	}
	//删除通知
	public void delNotify(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		NotifyDAO dao = new NotifyDAO();
		String s = request.getParameter("notify_id");
		int id = Integer.valueOf(s);
		int rs = dao.delNotify(id);
		out.print(rs);
	}
	//修改通知
	public void alterNotify(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		NotifyDAO dao = new NotifyDAO();
		String s = request.getParameter("notify_id");
		int notify_id = Integer.valueOf(s);
		String data = request.getParameter("data");
		String content = request.getParameter("content");
		JSONObject obj = JSONObject.fromObject(data);
		Notify notify = (Notify)obj.toBean(obj, Notify.class);
		notify.setNotify_id(notify_id);
		notify.setContent(content);
		int rs = dao.alterNotify(notify);
		if(rs == 1)
			out.print("SUCCESS");
		else{
			out.print("FAILL");
		}
		out.close();
	}
	public void preApplyTask(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String s = request.getParameter("id");
		String data = request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		String title = json.getString("title");
		String pre_start = formatTime(json.getString("pre_start"));
		String pre_end = formatTime(json.getString("pre_end"));
		String str_start = formatTime(json.getString("str_start"));
		String str_end = formatTime(json.getString("str_end"));
		PreTaskDAO tsdao = new PreTaskDAO();
		int rs1 = 0;
		if(s != null && !s.equals("")){
			int id = Integer.valueOf(s);
			rs1 = tsdao.updateRecord(id, title, pre_start, pre_end);
		}else{
			rs1 = tsdao.addRecord(title, pre_start, pre_end);
		}
		StrClassDAO strdao = new StrClassDAO();
		int rs2 = strdao.updateTime(str_start, str_end);
		
		if(rs1 >= 1 && rs2 >= 1){
			//System.out.println(pre_start+","+pre_end);
			this.getServletContext().setAttribute("start_time", pre_start);
			this.getServletContext().setAttribute("end_time", pre_end);
			OperatStatisDAO statisDAO = new OperatStatisDAO();
			statisDAO.deletRecord();
			statisDAO.updateRecord("configApply",1);
			out.print("SUCCESS");
		}else{
			out.print("FAILL");
		}
	}
	//管理员批量报名
	public void batchApply(List list,PrintWriter out){
		synchronized(this.getServletContext()){
			ArrayList<StrengthClass> strs = (ArrayList<StrengthClass>)this.getServletContext().getAttribute("StrengthClassList");
			System.out.println("admin:str-"+strs);
			if(strs == null){
				StrClassDAO strdao = new StrClassDAO();
				strs = strdao.getAllClass();
			}
			if(strs.size() == 0){
				out.print("<p>批量预报名失败</p><p>失败原因:</p><p>未发现强化班信息，请检查强化班信息是否配置</p>");
				return;
			}
			String rs = "";
			int count = 0;
			int size = list.size();
			int repeat = removeApplyed(list);
			ArrayList<PreApply> prelist = new ArrayList<PreApply>();
		
			for(int i = 0;i < strs.size();i++){
				StrengthClass strength = strs.get(i);
				int left = strength.getMax_num() + strength.getMax_expend() - strength.getCur_num();
				for(int j = 0;j < left;j++){
					if(count < list.size()){
						PreApply apply = (PreApply)list.get(count);
						apply.setClass_id(Integer.valueOf(strength.getClass_id()));
						prelist.add(apply);
						count++;
					}else{
						rs = submitBatchApply(list,prelist,size,repeat,false);
						this.getServletContext().removeAttribute("StrengthClassList");
						out.print(rs);
						return;
					}
				}
			}
			if(count <= list.size()){
				rs = submitBatchApply(list,prelist,size,repeat,true);
				this.getServletContext().removeAttribute("StrengthClassList");
				out.print(rs);
			}
		}
	}
	private String submitBatchApply(List list,ArrayList<PreApply> prelist,int size,int repeat,boolean isFull){
		StringBuffer err = new StringBuffer();
		PreApplyDAO dao = new PreApplyDAO();
		int rs[] = null;
		
		rs = dao.addPreApply(prelist);
		if(rs != null){
			int c = 0;
			for(int k = 0;k < rs.length;k++){
				if(rs[k] == 0){
					PreApply apply = (PreApply)list.get(k);
					String tip = "<" + apply.getStu_number()+
							","+apply.getStu_name()+">";
					err.append(tip);
					c++;
				}
			}
			String tip = "";
			tip = "<p>成功预报名"+(rs.length - c)+"人(共"+size+"人),失败"+(size - rs.length + c)+"人</p>";
			if(c > 0 || repeat > 0 || isFull){
				tip += "<div class=\"err_tip\"><p>失败原因:</p>";
				if(c > 0){
					tip += "<p>未发现以下学生信息,请检查数据正确性" + err.toString()+"</p>";
				}
				if(repeat > 0){
					tip += "重复报名"+repeat+"人";
				}
				if(isFull){
					tip += "<p>未有足够位置容纳分配的学生</p>";
				}
				tip += "</div>";
			}	
			
			return tip;
		}
		return "数据异常";
	}
	//
	private int removeApplyed(List list){
		int rs = 0;
		PreApplyDAO apply = new PreApplyDAO();
		ArrayList<Map<String,String>> applyed = apply.getPreApplyInfo(0, 9999);
		for(int i = 0;i < list.size();i++){
			PreApply ap = (PreApply)list.get(i);
			String number1 = ap.getStu_number();
			String name1 = ap.getStu_name();
			if(number1 == null)
				number1 = "null";
			if(name1 == null)
				name1 = "null";
			boolean find = false;
			for(int j = 0;j < applyed.size();j++){
				Map<String,String> map = applyed.get(j);
				String number2 = map.get("stu_id");
				String name2 = map.get("name");
				if(number1.equals(number2) && name1.equals(name2)){
					find = true;
					rs++;
					break;
				}
			}
			if(find){
				list.remove(i);
				i = i-1;
			}
		}
		return rs;
	}
	public void getPreTask(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		PreTaskDAO dao = new PreTaskDAO();
		HashMap<String,String> map = dao.getRecord(null);
		JSONObject obj = JSONObject.fromObject(map);
		if(obj == null){
			out.print("null");
		}else{
			out.print(obj.toString());
		}
	}
	public void getPreApplyInfo(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String sp = request.getParameter("page");
		String sn = request.getParameter("num");
		if(!sp.equals("") && !sn.equals("")){
			PreApplyDAO dao  = new PreApplyDAO();
			int page = Integer.valueOf(sp);
			int num = Integer.valueOf(sn);
			int records = dao.getRecordNum();
			ArrayList<Map<String,String>> list = dao.getPreApplyInfo(page, num);
			JSONObject obj = new JSONObject();
			String[] titles = {"stu_id","name","class_name","ispay","time","add","remove","change"};
			String[] colNames = {"学号","姓名","强化班名","是否交费","报名时间","添加","删除","修改"};
			obj.put("titles", titles);
			obj.put("colNames", colNames);
			obj.put("data", list);
			obj.put("records", records);
			out.print(obj.toString());
		}else{
			out.print("Parameter Erro");
		}
		out.close();
	}
	public void getSelectDatas(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String sp = request.getParameter("page");
		String sn = request.getParameter("num");
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		if(!sp.equals("") && !sn.equals("")){
			PreApplyDAO dao  = new PreApplyDAO();
			int page = Integer.valueOf(sp);
			int num = Integer.valueOf(sn);
			if(name.equals("pay")){
				if(value.equals("已")||value.equals("已缴")||value.equals("已缴费")) {
					value = "1";
				}else{
					if(value.equals("未")||value.equals("未缴")||value.equals("未缴费")) value = "0";
					else value="";
				}
			}
			int records = dao.getSelectRecordNum(name, value);
			ArrayList<Map<String,String>> list = dao.getSelectData(page, num, name, value);
			JSONObject obj = new JSONObject();
			String[] titles = {"stu_id","name","class_name","ispay","time","add","remove","change"};
			String[] colNames = {"学号","姓名","强化班名","是否交费","报名时间","添加","删除","修改"};
			obj.put("titles", titles);
			obj.put("colNames", colNames);
			obj.put("data", list);
			obj.put("records", records);
			out.print(obj.toString());
		}else{
			out.print("Parameter Erro");
		}
		out.close();
	}
	private String formatTime(String s){
		if(s.indexOf('-') > 0) return s;
		String year = s.substring(6);
		s = s.substring(0,5);
		s = year + "/" + s;
		s = s.replaceAll("/", "-");
		return s;
	}
	public void getAllClass(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		StrClassDAO dao = new StrClassDAO();
		List list = dao.getAllClass();
		JSONArray array = JSONArray.fromObject(list);
		out.print(array.toString());
	}
	public void PreApplyManager(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String data = request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		JSONArray add = json.getJSONArray("add");
		JSONArray del = json.getJSONArray("del");
		JSONArray update = json.getJSONArray("update");
		
		ArrayList<PreApply> addlist = new ArrayList<PreApply>();
		ArrayList<AbstractData> updlist = new ArrayList<AbstractData>();
		ArrayList<AbstractData> delist = new ArrayList<AbstractData>();
		ArrayList<Integer> addPayIndex = new ArrayList<Integer>();
		ArrayList<Integer> updatePayIndex = new ArrayList<Integer>();
		for(int i = 0;i < add.size();i++){
			JSONObject obj = add.getJSONObject(i);
			PreApply apply = (PreApply)obj.toBean(obj, PreApply.class);
			if(apply.getIspay() == 1)
				addPayIndex.add(i);
			addlist.add(apply);
		}
		for(int i = 0;i < update.size();i++){
			JSONObject obj = update.getJSONObject(i);
			PreApply apply = (PreApply)obj.toBean(obj, PreApply.class);
			if(apply.getIspay() == 1)
				updatePayIndex.add(i);
			updlist.add(apply);
		}
		for(int i = 0;i < del.size();i++){
			String stu_id = del.getString(i);
			PreApply apply = new PreApply();
			apply.setStu_number(stu_id);
			delist.add(apply);
		}
		int[] rs1 = null;
		int rs2 = 0;
		int rs3 = 0;
		int repeat = 0;
		PreApplyDAO dao = new PreApplyDAO();
		int size = addlist.size();
		synchronized(this.getServletContext()){
			this.getServletContext().removeAttribute("StrengthClassList");
			repeat = removeApplyed(addlist);
			rs1 = dao.addPreApply(addlist);
			rs2 = dao.addRecords(updlist);
			rs3 = dao.deleteRecords(delist);
		}
		JSONObject rs = new JSONObject();
		
		rs.accumulate("update", rs2 / 2);
		rs.accumulate("del", rs3);
		rs.accumulate("repeat", repeat);
		StringBuffer err = new StringBuffer();
		String paySuccess = null;
		if(rs1 != null){
			int c = 0;
			for(int k = 0;k < rs1.length;k++){
				if(rs1[k] == 0){
					String tip = "<" + addlist.get(k).getStu_number()+
							","+addlist.get(k).getStu_name()+">";
					err.append(tip);
					c++;
				}
				
			}
			for(int i = 0;i < addPayIndex.size();i++){
				int t = addPayIndex.get(i);
				if(t < rs1.length && rs1[t] == 0){
				}else if(t < addlist.size()){
					if(paySuccess == null)
						paySuccess = "<p>成功修改以下学生缴费信息</p>";
					paySuccess += "<"+addlist.get(t).getStu_number()+
							","+addlist.get(t).getStu_name()+">";
				}
			}
			rs.accumulate("err", err.toString());
			rs.accumulate("add-fail",c);
			
			rs.accumulate("add", rs1.length - c);
		}else{
			rs.accumulate("add",0);
		}
		for(int i = 0;i < updatePayIndex.size();i++){
			int t = updatePayIndex.get(i);
			PreApply app = (PreApply)updlist.get(t);
			if(paySuccess == null)
				paySuccess = "<p>成功修改以下学生缴费信息</p>";
			paySuccess += "<"+app.getStu_number()+
					","+app.getStu_name()+">";
		}
		rs.accumulate("add-total", size);
		if(paySuccess != null){
			PayLog log = new PayLog();
			log.setLog(paySuccess);
			log.setOperator("1433010228");
			PayLogDAO logdao = new PayLogDAO();
			logdao.addRecords(log);
		}
		
		out.print(rs.toString());
	}
	public void getPayLog(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		PayLogDAO paylog = new PayLogDAO();
		ArrayList<PayLog> list = paylog.getRecords();
		JSONObject obj = new JSONObject();
		String[] title = {"编号","操作日志","文件","操作人","时间"};
		String[] colName = {"id","log","fileName","operator","time"};
		obj.accumulate("title", title);
		obj.accumulate("colName", colName);
		obj.accumulate("data", list);
		out.print(obj.toString());
	}
	public void handlerBatchPreApply(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		ExtraExamDAO extraDAO = new ExtraExamDAO();
		final HashMap<String,String> map = extraDAO.getAllRecord();
		
		ReadExcelData read = new ReadExcelData(){

			@Override
			protected Object handValue(String key, Object value) {
				// TODO Auto-generated method stub
				return super.handValue(key, value);
			}
			@Override
			protected Object handObject(Object obj) {
				// TODO Auto-generated method stub
				PreApply apply = (PreApply)obj;
				String pay = null;
				if(map != null && apply != null)
					pay = map.get(apply.getStu_number());
				if(pay != null && pay.equals("未考"))
					apply.setIspay(1);
				return apply;
			}
		};
		String[] colName = {"学号","姓名"};
		String[] colAttr = {"stu_number","stu_name"};
		read.setColName(colName);
		read.setColAttr(colAttr);
		List applys = readApplyData(request,read,null);
		String tip = "";
		if(read.getErrMessage() != null){
			tip += "<div class=\"err_tip\"><p>数据读取失败</p><p>失败原因:</p>";
			tip += "<p>"+ read.getErrMessage()+"</p><p>如需获取解决方案请查看 首页 -> 帮助</p>";
			tip += "</div>";
			out.print(tip);
			out.close();
			return;
		}
		this.batchApply(applys, out);	
	}
	public void importPayed(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String[] colNames = {"号码","姓名","付款状态"};
		String[] colAttrs = {"stu_number","stu_name","ispay"};
		
		ReadExcelData read = new ReadExcelData(){

			@Override
			protected Object handValue(String key, Object value) {
				// TODO Auto-generated method stub
				
				if(key.equals("ispay")){
					String v = (String)value;
					if(v.equals("已缴费"))
						return 1;
					else
						return 0;
				}
				return super.handValue(key, value);
			}
		};
		read.setColName(colNames);
		read.setColAttr(colAttrs);
		
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		String fileName = d.toLocaleString();
		fileName = fileName.replaceAll("[-,\\s,:]", "");
		List applys = readApplyData(request,read,fileName+".xls");
		String tip = "";
		if(read.getErrMessage() != null){
			tip += "<div class=\"err_tip\"><p>数据读取失败</p><p>失败原因:</p>";
			tip += "<p>"+ read.getErrMessage()+"</p><p>如需获取解决方案请查看 首页 -> 帮助</p>";
			tip += "</div>";
			out.print(tip);
			out.close();
			return;
		}
		PayLogDAO logDAO = new PayLogDAO();
		PayLog log = new PayLog();
		log.setFileName(fileName+".xls");
		
		PreApplyDAO dao = new PreApplyDAO();
		
		int rs[] = dao.updatePayInfo(applys);
		int suc = 0;
		StringBuffer err = new StringBuffer();
		for(int i = 0;i < rs.length;i++){
			if(rs[i] == 1)
				suc++;
			else{
				PreApply apply = (PreApply)applys.get(i);
				err.append("<"+apply.getStu_number()+","+apply.getStu_name()+">");
			}
		}
		 OperatStatisDAO statisDAO = new OperatStatisDAO();
	     statisDAO.updateRecord("importPay",1);
		tip = "<p>成功导入"+(suc)+"条记录(共"+rs.length+"条记录),失败"+(rs.length - suc)+"条</p>";
		
		if(suc < rs.length){
			tip += "<div class=\"err_tip\"><p>失败原因:</p>";
			tip += "<p>未发现以下学生信息,请检查数据正确性" + err.toString()+"</p>";	
			tip += "</div>";
		}
		log.setLog(tip);
		log.setOperator("1433010228");
		logDAO.addRecords(log);
		out.print(tip);
		out.close();
	}
	
	public List readApplyData(HttpServletRequest request,ReadExcelData read,String fileName){
		try{
			if(ServletFileUpload.isMultipartContent(request)){
				DiskFileItemFactory disk = new DiskFileItemFactory();
				disk.setSizeThreshold(20 * 1024);
				disk.setRepository(disk.getRepository());
				ServletFileUpload upload = new ServletFileUpload(disk);
				int maxsize = 100 * 1024 * 1024;
				upload.setSizeMax(maxsize);
				List applys = null;
				List list = upload.parseRequest(request);
				Iterator i = list.iterator();
				while (i.hasNext()){
					FileItem fm = (FileItem)i.next();
					read.setInputStream(fm.getInputStream());
					read.readData(PreApply.class);
					applys =  read.getReadList();
					if(fileName != null){
						String filePath = getServletContext().getRealPath("/")+"payed\\";
						InputStream is=fm.getInputStream();
	                    int buffer=1024;     //定义缓冲区的大小
	                    int length=0;
	                    byte[] b=new byte[buffer];
	                    File file = null;
	                    file = new File( filePath, 
					            fileName);
	                    if (!file.exists()) {
			                if (!file.getParentFile().exists()) {
			                    file.getParentFile().mkdirs();
			                }
			                file.createNewFile();
			            }
	                    FileOutputStream fos=new FileOutputStream(file);
	                    while((length=is.read(b))!=-1){
	                         fos.write(b,0,length);                      //向文件输出流写读取的数据
	                    }
	                    fos.close();
					}
				}
				return applys;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void getOperatStatis(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		OperatStatisDAO dao = new OperatStatisDAO();
		OperatStatis statis = dao.getRecord();
		JSONObject json = JSONObject.fromObject(statis);
		out.print(json.toString());
	}
	//上传附件
	public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		File file ;
		   int maxMemSize = 1024 * 60000;
		   request.setCharacterEncoding("utf-8");
		   
		   String filePath = getServletContext().getRealPath("/")+"uploaded\\";
		 //  String filePath = "d:\\uploaded";
		   PrintWriter out = response.getWriter();
		   // 验证上传内容了类型
		   String contentType = request.getContentType();
		   if ((contentType.indexOf("multipart/form-data") >= 0)) {

		      DiskFileItemFactory factory = new DiskFileItemFactory();
		      // 设置内存中存储文件的最大值
		      factory.setSizeThreshold(maxMemSize);
		      // 本地存储的数据大于 maxMemSize.
		      factory.setRepository(new File("d:\\temp"));

		      // 创建一个新的文件上传处理程序
		      ServletFileUpload upload = new ServletFileUpload(factory);
		      // 设置最大上传的文件大小
		      upload.setSizeMax(1024*2000);
		      try{ 
		         // 解析获取的文件
		         List fileItems = upload.parseRequest(request);

		         // 处理上传的文件
		         Iterator i = fileItems.iterator();

		         while ( i.hasNext () ) 
		         {
		            FileItem fi = (FileItem)i.next();
		            if ( !fi.isFormField () )	
		            {
			            String fileName = fi.getName();
			            // 写入文件
			            if( fileName.lastIndexOf("\\") >= 0 ){
				            file = new File( filePath , 
				            fileName.substring(fileName.lastIndexOf("\\")));
			            }else{
				            file = new File( filePath ,
				            fileName.substring(fileName.lastIndexOf("\\")+1)) ;
			            }
			            if (!file.exists()) {
			                if (!file.getParentFile().exists()) {
			                    file.getParentFile().mkdirs();
			                }
			                file.createNewFile();
			            }
			            InputStream is=fi.getInputStream();
	                    int buffer=1024;     //定义缓冲区的大小
	                    int length=0;
	                    byte[] b=new byte[buffer];
	                    FileOutputStream fos=new FileOutputStream(file);
	                    while((length=is.read(b))!=-1){
	                         fos.write(b,0,length);                      //向文件输出流写读取的数据
	                    }
	                    fos.close();
	                    out.print("SUCCESS");
		            }
		         }
		         
		      }catch(Exception ex) {
		    	  out.println("FAIL");
		    	  ex.printStackTrace();
		      }
		   }else{
		      out.println("<p>No file uploaded</p>"); 
		   }
	}
	public void getStatis(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws UnsupportedEncodingException{
		StatisDAO dao = new StatisDAO();
		ArrayList<HashMap<String,Object>> list = dao.getTeacherStatis();
		ArrayList<HashMap<String,Object>> list2 = dao.getStudentStatis();
		JSONObject obj = new JSONObject();
		obj.accumulate("teacher", list);
		obj.accumulate("student", list2);
		out.print(obj.toString());
	}
	
	public void getSpecialData(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws UnsupportedEncodingException{
		int pagenum = Integer.parseInt(request.getParameter("page"));
		int showNum = Integer.parseInt(request.getParameter("num"));
		String source = request.getParameter("source");
		int allpage = 0;
		int total = 0;
		JSONObject jsonobj = new JSONObject();
		PreApplyDAO pdao = new PreApplyDAO();
		StuInfoDAO stuinfodao = new StuInfoDAO();
		if(source.equals("notpay")){
			String[] titles = {"学号","姓名","缴费类型","缴费标准"};
			String[] columns = {"student_number","student_name","exam_result","cost"};
			ArrayList<NotPay> notpays = pdao.getDatas("","");
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
			
			ArrayList<ArrangeClass> arranges = stuinfodao.getDatas("", "");
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
			
			ArrayList<StuInfo> pres = stuinfodao.getRecord("","");
	
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
