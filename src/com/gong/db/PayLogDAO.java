package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.gong.domain.PayLog;
import com.gong.helper.ConnectFactory;

public class PayLogDAO {
	public ArrayList<PayLog> getRecords(){
		ArrayList<PayLog> list = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT * FROM pay_log ORDER BY op_time DESC;";
			con = ConnectFactory.getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				if(list == null)
					list = new ArrayList<PayLog>();
				PayLog log = new PayLog();
				log.setId(rs.getInt(1));
				log.setLog(rs.getString(2));
				log.setFileName(rs.getString(3));
				log.setOperator(rs.getString(4));
				log.setTime(rs.getString(5));
				list.add(log);
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
				if(con != null){
					con.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return list;
	}
	public int addRecords(PayLog log){
		Connection con = null;
		int rs = 0;
		PreparedStatement pstm = null;
		try{
			String sql = "INSERT INTO pay_log (op_log,op_file,operator,op_time)"
					+ " VALUES(?,?,?,NOW())";
			con = ConnectFactory.getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, log.getLog());
			pstm.setString(2, log.getFileName());
			pstm.setString(3, log.getOperator());
			rs = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm != null){
					pstm.close();
				}
				if(con != null){
					con.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return rs;
	}
}
