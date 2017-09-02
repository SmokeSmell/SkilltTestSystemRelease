package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gong.domain.AbstractData;
import com.gong.domain.StrengthClass;
import com.gong.domain.StuClass;
import com.gong.domain.User;
import com.gong.helper.ConnectFactory;

public class StrClassDAO extends AbstractDAO{

	public ArrayList<StrengthClass> getAllClass(){
		ArrayList<StrengthClass> tcs = new ArrayList<StrengthClass>();
		String sql = "select * from strclass order by class_id";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StrengthClass tc = new StrengthClass();
				tc.setClass_id(rs.getString(1));
				tc.setClass_name(rs.getString(2));
				tc.setStart_time(rs.getString(3));
				tc.setEnd_time(rs.getString(4));
				tc.setTea_id(rs.getString(5));
				tc.setClass_place(rs.getString(6));
				tc.setMax_num(rs.getInt(7));
				tc.setCur_num(rs.getInt(8));
				tc.setMax_expend(rs.getInt(9));
				tcs.add(tc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return tcs;
	}
	public HashMap<String,String> getStrClass(String student_number){
		String sql = "call find_class(?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		HashMap<String,String> classinfo = new HashMap<String,String>();
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, student_number);
			rs = pstm.executeQuery();
			while(rs.next()){
				classinfo.put("className",rs.getString(1));
				classinfo.put("startTime",rs.getString(2));
				classinfo.put("endTime", rs.getString(3));
				classinfo.put("place", rs.getString(4));
				classinfo.put("teacher", rs.getString(5));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return classinfo;
	}
	@Override
	public String[] getColumTitle() {
		// TODO Auto-generated method stub
		String[] title = new String[]{"班级编号","班级名称","开始时间","结束时间","教师编号","班级地点","标准容量","现有人数","允许超出人数"};
		
		return this.getColumTitle(title);
	}
	@Override
	public String[] getColoumNames() {
		// TODO Auto-generated method stub
		return this.getColumNames(new String[]{"class_id","class_name","start_time","end_time","tea_id","class_place","max_num","cur_num","max_expend"});
	}
	@Override
	public ArrayList<AbstractData> getAllData() {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> tcs = new ArrayList<AbstractData>();
		String sql = "select * from strclass order by class_id";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StrengthClass tc = new StrengthClass();
				tc.setClass_id(rs.getString(1));
				tc.setClass_name(rs.getString(2));
				tc.setStart_time(rs.getString(3));
				tc.setEnd_time(rs.getString(4));
				tc.setTea_id(rs.getString(5));
				tc.setClass_place(rs.getString(6));
				tc.setMax_num(rs.getInt(7));
				tc.setCur_num(rs.getInt(8));
				tc.setMax_expend(rs.getInt(9));
				tcs.add(tc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return tcs;
	}
	
	@Override
	public int addRecords(List datas) {
		// TODO Auto-generated method stub
		String sql = "insert into strclass(class_name,start_time,end_time,"
				+ "tea_id,class_place,max_num,cur_num,max_expend) values(?,?,?,?,?,?,?,?);";
		int check = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<datas.size();i++){
				StrengthClass strclass = (StrengthClass)datas.get(i);
				pstm.setString(1, strclass.getClass_name());
				pstm.setString(2, strclass.getStart_time());
				pstm.setString(3, strclass.getEnd_time());
				pstm.setString(4, strclass.getTea_id());
				pstm.setString(5, strclass.getClass_place());
				pstm.setInt(6, strclass.getMax_num());
				pstm.setInt(7, strclass.getCur_num());
				pstm.setInt(8, strclass.getMax_expend());
				pstm.addBatch();
			}
			int[] r = pstm.executeBatch();
			for(int i=0;i<r.length;i++){
				check += r[i];
			}
		}catch(Exception e){
			this.err_msg.append(e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return check;
	}
	@Override
	public int deleteRecords(ArrayList<AbstractData> datas) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int check = 0;
		try{
			conn = ConnectFactory.getConnection();
			boolean old = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String sql = "delete from strclass where class_id = ?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				StrengthClass strclass = (StrengthClass)datas.get(i);
				pstm.setString(1,strclass.getClass_id());
				pstm.addBatch();
			}
			conn.setAutoCommit(old);
			int[] r = pstm.executeBatch();
			for(int i=0;i<r.length;i++)
				check += r[i];
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null){
					pstm.close();
				}
				if(conn != null)
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return check;
	}
	@Override
	public int updateRecords(ArrayList<AbstractData> datas) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int check = 0;
		try{
			conn = ConnectFactory.getConnection();
			boolean old = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String sql = "update strclass set class_name=?,start_time=?,end_time=?,tea_id=?,class_place=?,max_num=?,cur_num=?,max_expend=? where class_id=?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				StrengthClass strclass = (StrengthClass)datas.get(i);
				
				pstm.setString(1, strclass.getClass_name());
				pstm.setString(2, strclass.getStart_time());
				pstm.setString(3, strclass.getEnd_time());
				pstm.setString(4, strclass.getTea_id());
				pstm.setString(5, strclass.getClass_place());
				pstm.setInt(6, strclass.getMax_num());
				pstm.setInt(7, strclass.getCur_num());
				pstm.setInt(8, strclass.getMax_expend());
				pstm.setString(9, strclass.getClass_id());

				pstm.addBatch();
			}
			conn.setAutoCommit(old);
			int[] r = pstm.executeBatch();
			for(int i=0;i<r.length;i++)
				check += r[i];
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null){
					pstm.close();
				}
				if(conn != null)
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return check;
	}
	public int updateTime(String start_time,String end_time) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		int rs = 0;
		try{
			conn = ConnectFactory.getConnection();
			String sql = "UPDATE strclass SET start_time = ?,end_time = ?;";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, start_time);
			pstm.setString(2, end_time);
			rs = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm != null){
					pstm.close();
				}
				if(conn != null)
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return rs;
	}
	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "select count(*) from strclass";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				pagenum = rs.getInt(1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return pagenum;
	}
	@Override
	public ArrayList<AbstractData> getSpecifiedDatas(int page, int num) {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> datas = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select class_id,class_name,start_time,end_time,tea_id,class_place,max_num,cur_num,max_expend from strclass limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StrengthClass tc = new StrengthClass();
				tc.setClass_id(rs.getString(1));
				tc.setClass_name(rs.getString(2));
				tc.setStart_time(rs.getString(3));
				tc.setEnd_time(rs.getString(4));
				tc.setTea_id(rs.getString(5));
				tc.setClass_place(rs.getString(6));
				tc.setMax_num(rs.getInt(7));
				tc.setCur_num(rs.getInt(8));
				tc.setMax_expend(rs.getInt(9));
				datas.add(tc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return datas;
	}
	public String getClass_place(int classid){
		String sql = "select class_place from strclass where class_id=?";
		String temp = "";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, classid);
			rs = pstm.executeQuery();
			while(rs.next()){
				temp = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return temp;
	}
	public String getStart_time(int classid){
		String sql = "select start_time from strclass where class_id=?";
		String temp = "";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, classid);
			rs = pstm.executeQuery();
			while(rs.next()){
				temp = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return temp;
	}
	public String getEnd_time(int classid){
		String sql = "select end_time from strclass where class_id=?";
		String temp = "";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, classid);
			rs = pstm.executeQuery();
			while(rs.next()){
				temp = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return temp;
	}
	public ArrayList<AbstractData> getSelectDatas(int page, int num,
			String name, String value) {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> datas = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		if(name.equals("tea_id")){
			TeacherDAO tdao = new TeacherDAO();
			value = tdao.getTeacherid(value);
		}
		String sql = "select * from strclass where "+name+" like '%"+value+"%'limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StrengthClass tc = new StrengthClass();
				tc.setClass_id(rs.getString(1));
				tc.setClass_name(rs.getString(2));
				tc.setStart_time(rs.getString(3));
				tc.setEnd_time(rs.getString(4));
				tc.setTea_id(rs.getString(5));
				tc.setClass_place(rs.getString(6));
				tc.setMax_num(rs.getInt(7));
				tc.setCur_num(rs.getInt(8));
				tc.setMax_expend(rs.getInt(9));
				datas.add(tc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return datas;
	}
	@Override
	public int getSelectRecordNum(String name, String value) {
		// TODO Auto-generated method stub
		int pagenum = 0;
		if(name.equals("tea_id")){
			TeacherDAO tdao = new TeacherDAO();
			value = tdao.getTeacherid(value);
		}
		String sql = "select count(*) from strclass where "+name+" like '%"+value+"%'";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				pagenum = rs.getInt(1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return pagenum;
	}
}
