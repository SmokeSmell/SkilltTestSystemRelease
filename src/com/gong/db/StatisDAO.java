package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.gong.helper.ConnectFactory;

public class StatisDAO {
	public ArrayList<HashMap<String,Object>> getTeacherStatis(){
		ArrayList<HashMap<String,Object>> list = null;
		String sql = "SELECT tea_id,teacher_name, exam_pass,exam_num,mock_pass,mock_num"
				+ " FROM `statistics`,"
				+ "teachers where statistics.tea_id = teacher_id;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				if(list == null)
					list = new ArrayList<HashMap<String,Object>>();
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("teacher_id", rs.getString(1));
				map.put("teacher_name",rs.getString(2));
				map.put("exam_pass", rs.getInt(3));
				map.put("exam_num", rs.getInt(4));
				map.put("mock_pass", rs.getInt(5));
				map.put("mock_num", rs.getInt(6));
				list.add(map);
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
		return list;
	}
	public ArrayList<HashMap<String,Object>> getStudentStatis(){
		ArrayList<HashMap<String,Object>> list = null;
		String sql = "CALL exam_statis;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			int count = 0;
			while(rs.next()){
				if(list == null)
					list = new ArrayList<HashMap<String,Object>>();
				HashMap<String,Object> map = new HashMap<String,Object>();
				if(count == 0){
					map.put("pass", rs.getInt(1));
					map.put("not",rs.getInt(2));
					map.put("total", rs.getInt(3));
					count++;
				}else{
					map.put("best", rs.getInt(1));
					map.put("great",rs.getInt(2));
					map.put("qualify", rs.getInt(3));
				}
				
				list.add(map);
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
		return list;
	}
}
