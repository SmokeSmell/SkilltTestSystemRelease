package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gong.domain.AbstractData;
import com.gong.domain.StuInfo;
import com.gong.domain.User;
import com.gong.helper.ConnectFactory;

public class UserDAO extends AbstractDAO{
	public User anthenticate(User user){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from users where user_name=? and password = ?;";
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, user.getUser_name());
			pstm.setString(2, user.getPassword());
			rs = pstm.executeQuery();
			if(!rs.next()){
				user = null;
			}else{
				user.setRole(rs.getString(3));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				if(pstm != null)
					pstm.close();
				if(conn != null){
					conn.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public String[] getColumTitle() {
		// TODO Auto-generated method stub
		String[] title = new String[]{"用户名","密码","角色"};
		return this.getColumTitle(title);
	}

	@Override
	public String[] getColoumNames() {
		// TODO Auto-generated method stub
		return this.getColumNames(new String[]{"user_name","password","role"});
	}
	public ArrayList<AbstractData> getSpecifiedDatas(int page,int num){
		ArrayList<AbstractData> users = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select user_name,password,role from users limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUser_name(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setRole(rs.getString(3));
				users.add(user);
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
		return users;
	}
	@Override
	public ArrayList<AbstractData> getAllData() {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> infos = new ArrayList<AbstractData>();
		String sql = "select * from users";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUser_name(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setRole(rs.getString(3));
				infos.add(user);
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
		return infos;
	}
	public int updateRecord(User user) {
		int x = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "update users set password=? where user_name=?;";
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, user.getPassword());
			pstm.setString(2, user.getUser_name());
			int amt = pstm.executeUpdate();
			if(amt != 1){
				x=0;
			}else{
				x=1;
			}
			
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
		return x;
	}
	public int getRecordNum() {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "select count(*) from users";
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
	public int addRecords(List datas) {
		// TODO Auto-generated method stub
		String sql = "insert into users values(?,?,?);";
		int check = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<datas.size();i++){
				User user = (User)datas.get(i);
				pstm.setString(1,user.getUser_name());
				pstm.setString(2,user.getPassword());
				pstm.setString(3,user.getRole());
				pstm.addBatch();
			}
			int[] r = pstm.executeBatch();
			for(int i=0;i<r.length;i++){
				check += r[i];
			}
		}catch(Exception e){
			this.err_msg.append(e.getMessage());
			System.out.println(this.err_msg.toString());
			//e.printStackTrace();
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
			String sql = "delete from users where user_name = ?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				User user = (User)datas.get(i);
				pstm.setString(1,user.getUser_name());
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
			String sql = "update users set password=?,role=? where user_name=?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				User user = (User)datas.get(i);
				
				pstm.setString(1, user.getPassword());
				pstm.setString(2, user.getRole());
				pstm.setString(3, user.getUser_name());
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
	
	public ArrayList<String> getList(String listname){
		 ArrayList<String> lists = new ArrayList<String>();
		 Connection conn = null;
		 PreparedStatement pstm = null;
		 ResultSet rs = null;
		 String sql = "select "+listname+" from users";
		 try{
			 conn = ConnectFactory.getConnection();
			 pstm = conn.prepareStatement(sql);
			
			 rs = pstm.executeQuery();
			 
			 while(rs.next()){
				 String n = rs.getString(1);
				 lists.add(n);
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 try{
				 if(rs != null)
					 rs.close();
				 if(pstm != null)
					 pstm.close();
				 if(conn != null){
					 conn.close();
				 }
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 return lists;
	}
	public ArrayList<AbstractData> getSelectDatas(int page,int num,String name,String value){
		ArrayList<AbstractData> users = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select * from users where "+name+" like '%"+value+"%'limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUser_name(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setRole(rs.getString(3));
				users.add(user);
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
		return users;
	}
	public int getSelectRecordNum(String name,String value) {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "select count(*) from users where "+name+" like '%"+value+"%'";
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
