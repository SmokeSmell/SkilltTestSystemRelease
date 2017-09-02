package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.gong.helper.ConnectFactory;

public class PreTaskDAO {
	public int addRecord(String title,String start_time,String end_time){
		Connection conn = null;
		PreparedStatement pstm = null;
		int rs = 0;
		try{
			String sql = "INSERT  INTO  pretask (title,start_time,end_time) VALUE(?,?,?);";
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, title);
			pstm.setString(2, start_time);
			pstm.setString(3, end_time);
			
			rs = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return rs;
	}
	public int updateRecord(int id,String title,String start_time,String end_time){
		Connection conn = null;
		PreparedStatement pstm = null;
		int rs = 0;
		try{
			String sql = "UPDATE pretask SET title = ?, start_time = ?,end_time = ? "
					+ "WHERE id = ?;";
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, title);
			pstm.setString(2, start_time);
			pstm.setString(3, end_time);
			pstm.setInt(4, id);
			rs = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return rs;
	}
	public int deletRecord(){
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "DELETE FROM pretask;";
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return rs;
	}
	public HashMap<String,String> getRecord(String time){
		HashMap<String,String> map = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = null;
		try{
			if(time == null){
				sql = "SELECT * FROM pretask;";
			}else{
				sql = "SELECT * FROM pretask where '"+time+"' BETWEEN start_time AND end_time;";
				//System.out.println(sql);
			}
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				map = new HashMap<String,String>();
				String id = rs.getInt(1) + "";
				String title = rs.getString(2);
				String start = rs.getString(3);
				String end = rs.getString(4);
				map.put("id", id);
				map.put("title", title);
				map.put("start_time", start);
				map.put("end_time", end);
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
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return map;
	}
	public boolean canPreApply(){
		boolean check = false;
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		String t = d.toLocaleString();
		int i = t.indexOf(' ');
		String time = t.substring(0,i);
		String sql = "SELECT id FROM `pretask` where end_time >= ?;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, time);
			rs = pstm.executeQuery();
			if(rs.next())
				check = true;
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
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return check;
	}
}
