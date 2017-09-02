package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.gong.helper.ConnectFactory;
public class ExtraExamDAO {
	public HashMap<String,String> getAllRecord(){
		HashMap<String,String> map = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM extraexam";
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				if(map == null)
					map = new HashMap<String,String>();
				map.put(rs.getString(1), rs.getString(2));
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
}
