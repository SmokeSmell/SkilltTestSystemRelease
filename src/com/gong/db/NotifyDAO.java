package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.gong.domain.Notify;
import com.gong.helper.ConnectFactory;

public class NotifyDAO {
	public int publishNotify(Notify notify){
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into notifications (title,content,attch,publisher,time)"
				+ " values(?,?,?,?,?)";
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, notify.getTitle());
			pstm.setString(2, notify.getContent());
			pstm.setString(3, notify.getAttach());
			pstm.setString(4, notify.getPublisher());
			pstm.setString(5, notify.getTime());
			
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
	public int delNotify(int id){
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "delete from notifications where notif_id = ?";
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
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
	public int alterNotify(Notify notify){
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql1 = "UPDATE notifications SET title = ?,content = ?,attch = ? WHERE notif_id = ?";
		String sql2 = "UPDATE notifications SET title = ?,content = ? WHERE notif_id = ?";
		try{
			conn = ConnectFactory.getConnection();
			
			if(notify.getAttach() == null || notify.getAttach().equals("")){
				pstm = conn.prepareStatement(sql2);
				pstm.setString(1, notify.getTitle());
				pstm.setString(2, notify.getContent());
				pstm.setInt(3, notify.getNotify_id());
			}else{
				pstm = conn.prepareStatement(sql1);
				pstm.setString(1, notify.getTitle());
				pstm.setString(2, notify.getContent());
				pstm.setString(3, notify.getAttach());
				pstm.setInt(4, notify.getNotify_id());;
			}
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
	public Notify getNotifyById(int id){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Notify notify = null;
		String sql = "select * "
				+ "from notifications where notif_id = ?";
		try{
			con = ConnectFactory.getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				notify = new Notify();
				notify.setNotify_id(rs.getInt(1));
				notify.setTitle(rs.getString(2));
				notify.setContent(rs.getString(3));
				notify.setAttach(rs.getString(4));
				notify.setPublisher(rs.getString(5));
				notify.setTime(rs.getString(6).substring(0,10));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(con != null)
					con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return notify;
	}
	public int getRecordsNum(){
		int rss = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select count(*) from notifications";
		try{
			con = ConnectFactory.getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				rss = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(con != null)
					con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return rss;
	}
	public ArrayList<Notify> getNotifyName(int limit){
		ArrayList<Notify> lists = new ArrayList<Notify>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select notif_id,title,publisher,time from notifications ORDER BY time DESC limit ?";
		try{
			con = ConnectFactory.getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, limit);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				Notify notify = new Notify();
				notify.setNotify_id(rs.getInt(1));
				notify.setTitle(rs.getString(2));
				notify.setPublisher(rs.getString(3));
				notify.setTime(rs.getString(4).substring(0,10));
				lists.add(notify);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(con != null)
					con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return lists;
	}
	public ArrayList<Notify> getSpecialNotifyName(int page,int num){
		ArrayList<Notify> lists = new ArrayList<Notify>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select notif_id,title,publisher,time from notifications ORDER BY time DESC limit ?,?;";
		try{
			con = ConnectFactory.getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, begin);
			pstm.setInt(2, num);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				Notify notify = new Notify();
				notify.setNotify_id(rs.getInt(1));
				notify.setTitle(rs.getString(2));
				notify.setPublisher(rs.getString(3));
				notify.setTime(rs.getString(4).substring(0,10));
				lists.add(notify);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(con != null)
					con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return lists;
	}
}
