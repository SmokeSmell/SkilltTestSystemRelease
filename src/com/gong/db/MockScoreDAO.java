package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gong.domain.AbstractData;
import com.gong.domain.MockScore;
import com.gong.domain.PreApply;
import com.gong.helper.ConnectFactory;



public class MockScoreDAO extends AbstractDAO{
	
	public boolean add(MockScore data) {
		boolean result = false;
		String sql = "insert into mockscore (stu_number) values (?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			pstm.setString(1, data.getStu_number());
			
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
	public boolean deleteAllRecords() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean flag = true;
		try{
			conn = ConnectFactory.getConnection();
			boolean old = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String sql = "delete from mockscore;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			flag = pstm.execute(sql);
			
			conn.setAutoCommit(old);
			
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
		return flag;
	}
	@Override
	public String[] getColumTitle() {
		// TODO Auto-generated method stub
		String[] title = new String[]{"学号","姓名","模拟成绩"};
		return this.getColumTitle(title);
	}
	@Override
	public String[] getColoumNames() {
		// TODO Auto-generated method stub
		return this.getColumNames(new String[]{"stu_number","stu_name","mock_score"});
	}
	@Override
	public ArrayList<AbstractData> getAllData() {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> infos = new ArrayList<AbstractData>();
		String sql = "select * from mockscore";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				MockScore stu = new MockScore();
				stu.setStu_number(rs.getString(1));
				stu.setMock_score(rs.getFloat(2));
				infos.add(stu);
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
	@Override
	public int addRecords(List datas) {
		// TODO Auto-generated method stub
		String sql = "SELECT insert_mockscore(?,?);";
		int check = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<datas.size();i++){
				MockScore pa = (MockScore)datas.get(i);
				pstm.setString(1, pa.getStu_number());
				pstm.setFloat(2, pa.getMock_score());
				rs = pstm.executeQuery();	
				if(rs.next()){
					int temp = rs.getInt(1);
					if(temp == 0){
						this.err_msg.append("<"+pa.getStu_number()+","+pa.getStu_name()+">");
					}else{
						check++;
					}
				}	
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
			String sql = "delete from mockscore where stu_number = ?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				MockScore stu = (MockScore)datas.get(i);
				pstm.setString(1,stu.getStu_number());
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
			String sql = "update mockscore set mock_score=? where stu_number=?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				MockScore stu = (MockScore)datas.get(i);
				
				pstm.setFloat(1, stu.getMock_score());
				pstm.setString(2, stu.getStu_number());
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
	public int getRecordNum() {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "select count(*) from mockscore";
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
		ArrayList<AbstractData> stus = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select * from mockscore limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				MockScore stu = new MockScore();
				stu.setStu_number(rs.getString(1));
				stu.setMock_score(rs.getFloat(2));
				stus.add(stu);
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
		return stus;
	}
	@Override
	public ArrayList<AbstractData> getSelectDatas(int page, int num,
			String name, String value) {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> stus = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "";
		if(name.equals("stu_name")){
			sql ="select mockscore.* from mockscore,stuinfo where stuinfo.student_number=mockscore.stu_number and student_name like '%"+value+"%'limit "+begin+" ,"+num+';';
		}else{
			sql = "select * from mockscore where "+name+" like '%"+value+"%'limit "+begin+" ,"+num+';';
		}
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				MockScore stu = new MockScore();
				stu.setStu_number(rs.getString(1));
				stu.setMock_score(rs.getFloat(2));
				stus.add(stu);
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
		return stus;
	}

	@Override
	public int getSelectRecordNum(String name, String value) {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "";
		if(name.equals("stu_name")){
			sql ="select count(*) from mockscore,stuinfo where stuinfo.student_number=mockscore.stu_number and student_name like '%"+value+"%'";
		}else{
			sql = "select count(*) from mockscore where "+name+" like '%"+value+"%'";
		}
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
	public ArrayList<HashMap<String,String>> getMockScoreInfo() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String,String>> stus = null;
		
		String sql ="CALL find_mockinfo;";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				if(stus == null)
					stus = new ArrayList<HashMap<String,String>>();
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("name",rs.getString(1));
				map.put("number", rs.getString(2));
				map.put("sex",rs.getString(3));
				map.put("identity", rs.getString(4));
				map.put("place", rs.getString(5));
				map.put("class", rs.getString(6));
				map.put("college", rs.getString(7));
				map.put("teacher", rs.getString(8));
				stus.add(map);
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
		return stus;
	}
	public HashMap<String,String> findMockScore(String stu_id) {
		// TODO Auto-generated method stub
		HashMap<String,String> mock = null;
		
		String sql ="CALL find_stu_mockscore(?);";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stu_id);
			rs = pstm.executeQuery();
			while(rs.next()){
				if(mock == null)
					mock = new HashMap<String,String>();
				System.out.println(rs.getString(4));
				mock.put("class__name",rs.getString(1));
				mock.put("class_place", rs.getString(2));
				mock.put("teacher",rs.getString(3));
				mock.put("score", rs.getString(4));
				
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
		return mock;
	}


}
