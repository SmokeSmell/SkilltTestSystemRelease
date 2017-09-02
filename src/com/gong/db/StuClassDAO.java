package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.gong.domain.StuClass;
import com.gong.helper.ConnectFactory;



public class StuClassDAO {
	public int addRecords(ArrayList<StuClass> datas) {
		int x = 0;
		String sql = "insert into stuclass (class_id,stu_number) values (?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			boolean old = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			int count = datas.size();
			for (int index = 0; index < count; index++){
				StuClass sclass = (StuClass)datas.get(index);
				pstm.setString(1, sclass.getClass_id());
				pstm.setString(2, sclass.getStu_number());
				
				pstm.addBatch();
			}
			int[] amt = pstm.executeBatch();
			for (int i = 0; i < amt.length ;i++){ 
				x = amt[i] + x;
			}
			conn.commit();
			conn.setAutoCommit(old);
			
		}catch(Exception e){
			try{
				conn.rollback();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try{
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
		return x;
	}
	public boolean add(StuClass data) {
		boolean result = false;
		String sql = "insert into stuclass (class_id,stu_number) values (?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			pstm.setString(1, data.getClass_id());
			pstm.setString(2, data.getStu_number());
			
			pstm.addBatch();
			int[] i = pstm.executeBatch();
			if(i[0] == 1){
				return true;
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
		}finally{
			try{
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
		return result;
	}
	public String getStu_id(String stu_number) {
		String msg = new String();
		String sql = "select class_id from stuclass where stu_number=?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stu_number);
			rs = pstm.executeQuery();
			while(rs.next()){
				msg = rs.getString(1);
				
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
		return msg;
	}
}
