package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gong.domain.AbstractData;
import com.gong.domain.Score;
import com.gong.domain.User;
import com.gong.helper.ConnectFactory;

public class ScoreDAO extends AbstractDAO{
	public Score findScore(String stu_id){
		Score score = null;
		String sql = "select * from score where stu_id = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stu_id);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				score = new Score();
				score.setStu_id(stu_id);
				score.setExam_number(rs.getString(2));
				score.setOccupation(rs.getString(3));
				score.setAuth_class(rs.getString(4));
				score.setDegree(rs.getString(5));
				score.setPractice_score(String.valueOf(rs.getFloat(6)));
				score.setStatus(rs.getString(7));
				score.setEva_score(rs.getString(8));
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
		return score;
	}
	public HashMap<String,String> getStuNumberByIndentity(List list){
		HashMap<String,String> map = null;
		String sql = "SELECT student_number from stuinfo WHERE student_identity= ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			//pstm.setString(1, "320322199506025011");
			for(int i = 0;i < list.size();i++){
				String num = (String)list.get(i);
				pstm.setString(1,num);
				rs = pstm.executeQuery();
				while(rs.next()){
					if(map == null)
						map = new HashMap<String,String>();
					map.put((String)list.get(i), rs.getString(1));
				}
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
		return map;
	}
	@Override
	public String[] getColumTitle() {
		// TODO Auto-generated method stub
		String[] title = new String[]{"学号","姓名","准考证号","鉴定职业","级别","鉴定科目","实操成绩","实操考试情况","评定成绩"};
		return this.getColumTitle(title);
	}

	@Override
	public String[] getColoumNames() {
		// TODO Auto-generated method stub
		return this.getColumNames(new String[]{"stu_id","stu_name","exam_number","occupation","auth_class","degree","practice_score","status","eva_score"});
	}

	@Override
	public ArrayList<AbstractData> getAllData() {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> infos = new ArrayList<AbstractData>();
		String sql = "select * from score";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				Score sc = new Score();
				sc.setStu_id(rs.getString(1));
				sc.setExam_number(rs.getString(2));
				sc.setOccupation(rs.getString(3));
				sc.setAuth_class(rs.getString(4));
				sc.setDegree(rs.getString(5));
				sc.setPractice_score(String.valueOf(rs.getFloat(6)));
				sc.setStatus(rs.getString(7));
				sc.setEva_score(rs.getString(8));
				infos.add(sc);
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
		String sql = "REPLACE into score(stu_id,exam_number,auth_occupation,auth_class,exam_degree,practice_score,exam_status,evaluate_score) values(?,?,?,?,?,?,?,?);";
		int check = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<datas.size();i++){
				Score sc = (Score)datas.get(i);
				//System.out.println("dao:"+sc.getIdentity()+","+sc.getStu_id());
				if(sc.getStu_id() != null){
					pstm.setString(1,sc.getStu_id());
					pstm.setString(2,sc.getExam_number());
					pstm.setString(3,sc.getOccupation());
					pstm.setString(4,sc.getAuth_class());
					pstm.setString(5,sc.getDegree());
					pstm.setFloat(6,Float.valueOf(sc.getPractice_score()));
					pstm.setString(7,sc.getStatus());
					pstm.setString(8,sc.getEva_score());
					pstm.addBatch();
				}else{
					this.err_msg.append("<"+sc.getIdentity()+">");
				}
			}
			int[] r = pstm.executeBatch();
			for(int i=0;i<r.length;i++){
				if(r[i] == 2) r[i] = 1;
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
			String sql = "delete from score where stu_id = ?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				Score sc = (Score)datas.get(i);
				pstm.setString(1,sc.getStu_id());
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
			String sql = "update score set exam_number=?,auth_occupation=?,auth_class=?,exam_degree=?,practice_score=?,exam_status=?,evaluate_score=? where stu_id=?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				Score sc = (Score)datas.get(i);
				pstm.setString(1, sc.getExam_number());
				pstm.setString(2, sc.getOccupation());
				pstm.setString(3, sc.getAuth_class());
				pstm.setString(4, sc.getDegree());
				pstm.setFloat(5, Float.valueOf(sc.getPractice_score()));
				pstm.setString(6,sc.getStatus());
				pstm.setString(7,sc.getEva_score());
				pstm.setString(8, sc.getStu_id());
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
		String sql = "select count(*) from score";
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
		ArrayList<AbstractData> scs = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select * from score limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				Score sc = new Score();
				sc.setStu_id(rs.getString(1));
				sc.setExam_number(rs.getString(2));
				sc.setOccupation(rs.getString(3));
				sc.setAuth_class(rs.getString(4));
				sc.setDegree(rs.getString(5));
				sc.setPractice_score(String.valueOf(rs.getFloat(6)));
				sc.setStatus(rs.getString(7));
				sc.setEva_score(rs.getString(8));
				scs.add(sc);
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
		return scs;
	}
	public ArrayList<AbstractData> getSelectDatas(int page, int num,
			String name, String value) {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> scs = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql="";
		if(name.equals("stu_name")){
			sql ="select score.* from score,stuinfo where stuinfo.student_number=score.stu_id and student_name like '%"+value+"%'limit "+begin+" ,"+num+';';
		}else{
			sql = "select * from score where "+name+" like '%"+value+"%'limit "+begin+" ,"+num+';';
		}
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				Score sc = new Score();
				sc.setStu_id(rs.getString(1));
				sc.setExam_number(rs.getString(2));
				sc.setOccupation(rs.getString(3));
				sc.setAuth_class(rs.getString(4));
				sc.setDegree(rs.getString(5));
				sc.setPractice_score(String.valueOf(rs.getFloat(6)));
				sc.setStatus(rs.getString(7));
				sc.setEva_score(rs.getString(8));
				scs.add(sc);
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
		return scs;
	}

	@Override
	public int getSelectRecordNum(String name, String value) {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql="";
		if(name.equals("stu_name")){
			sql ="select count(*) from score,stuinfo where stuinfo.student_number=score.stu_id and student_name like '%"+value+"%'";
		}else{
			sql = "select count(*) from score where "+name+" like '%"+value+"%'";
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
}
