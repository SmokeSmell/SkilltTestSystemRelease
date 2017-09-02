package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gong.domain.OperatStatis;
import com.gong.helper.ConnectFactory;

public class OperatStatisDAO {
	public int updateRecord(String col,int value){
		int rs = 0;
		String sql = "update operatStatis set "+col+" = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, value);
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
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		return rs;
	}
	public void deletRecord(){
		String sql = "UPDATE operatstatis SET configApply = 0,importPay = 0,"
				+ "importMock = 0,exportExam = 0,importScore = 0;";
		Connection conn = null;
		PreparedStatement pstm = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm != null){
					pstm.close();
				}
				if(conn != null)
					conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
	public OperatStatis getRecord(){
		OperatStatis statis = new OperatStatis();
		String sql = "SELECT COUNT(*) FROM stuinfo UNION SELECT COUNT(*) FROM teachers "+
					  "UNION ALL SELECT COUNT(*) FROM strclass;";
		String sql2 = "SELECT * FROM operatstatis;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			int i = 0;
			while(rs.next()){
				if(i == 0)
					statis.setStuInfo(rs.getInt(1));
				else if(i == 1)
					statis.setTeachInfo(rs.getInt(1));
				else
					statis.setClassInfo(rs.getInt(1));
				i++;
			}
			pstm = conn.prepareStatement(sql2);
			rs = pstm.executeQuery();
			while(rs.next()){
				statis.setConfigApply(rs.getInt(1));
				statis.setImportPay(rs.getInt(2));
				statis.setImportMock(rs.getInt(3));
				statis.setExportExam(rs.getInt(4));
				statis.setImportScore(rs.getInt(5));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm != null){
					pstm.close();
				}
				if(conn != null)
					conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return statis;
	}
}
