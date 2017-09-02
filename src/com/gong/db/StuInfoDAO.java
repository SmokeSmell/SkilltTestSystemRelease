 package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.gong.domain.AbstractData;
import com.gong.domain.ArrangeClass;
import com.gong.domain.MockScore;
import com.gong.domain.StuInfo;
import com.gong.domain.User;
import com.gong.helper.ConnectFactory;

public class StuInfoDAO extends AbstractDAO{
	
	public String[] getColoumNames(){
		return this.getColumNames(new String[]{"student_number","student_name","student_sex","student_identity","student_birth","student_college","student_class","student_edu","student_mobile"});
	}
	public StuInfo getRecord(String stu_number){
		StuInfo info = new StuInfo();
		String sql = "select * from stuinfo where student_number = ?";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,stu_number);
			rs = pstm.executeQuery();
			while(rs.next()){
				info.setStudent_number(rs.getString(1));
				info.setStudent_name(rs.getString(2));
				info.setStudent_sex(rs.getString(3));
				info.setStudent_identity(rs.getString(4));
				info.setStudent_birth(rs.getString(5));
				info.setStudent_college(rs.getString(6));
				info.setStudent_class(rs.getString(7));
				info.setStudent_edu(rs.getString(8));
				info.setStudent_mobile(rs.getString(9));
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
		return info;
	}
	public ArrayList<StuInfo> getRecord(String name,String value){
		ArrayList<StuInfo> infos = new ArrayList<StuInfo>();
		String sql = "";
		if(value.equals("")){
			sql = "select stuinfo.* from stuinfo,mockscore,preapply "
					+ "where mockscore.stu_number= preapply.stu_number "
					+ "and preapply.stu_number=stuinfo.student_number "
					+ "and mockscore.mock_score >= \"60\" and preapply.pay=\"1\"";
		}else{
			sql = "select stuinfo.* from stuinfo,mockscore,preapply "
				+ "where mockscore.stu_number= preapply.stu_number "
				+ "and preapply.stu_number=stuinfo.student_number "
				+ "and mockscore.mock_score >= \"60\" and preapply.pay=\"1\" "
				+ "and "+name+" like '%"+value+"%'";
		}
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StuInfo info = new StuInfo();
				info.setStudent_number(rs.getString(1));
				info.setStudent_name(rs.getString(2));
				info.setStudent_sex(rs.getString(3));
				info.setStudent_identity(rs.getString(4));
				info.setStudent_birth(rs.getString(5));
				info.setStudent_college(rs.getString(6));
				info.setStudent_class(rs.getString(7));
				info.setStudent_edu(rs.getString(8));
				info.setStudent_mobile(rs.getString(9));
				infos.add(info);
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
	public ArrayList<StuInfo> getRecord(){
		ArrayList<StuInfo> infos = new ArrayList<StuInfo>();
		String sql = "select stuinfo.* from stuinfo,mockscore,preapply "
				+ "where mockscore.stu_number= preapply.stu_number "
				+ "and preapply.stu_number=stuinfo.student_number "
				+ "and mockscore.mock_score >= \"60\" and preapply.pay=\"1\"";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StuInfo info = new StuInfo();
				info.setStudent_number(rs.getString(1));
				info.setStudent_name(rs.getString(2));
				info.setStudent_sex(rs.getString(3));
				info.setStudent_identity(rs.getString(4));
				info.setStudent_birth(rs.getString(5));
				info.setStudent_college(rs.getString(6));
				info.setStudent_class(rs.getString(7));
				info.setStudent_edu(rs.getString(8));
				info.setStudent_mobile(rs.getString(9));
				infos.add(info);
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
	public String getStudent_number(String studentidentity){
		String sql = "select student_number from stuinfo where student_identity = ?";
		String temp ="";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,studentidentity);
			rs = pstm.executeQuery();
			while(rs.next()){
				temp = rs.getString(1);
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
		return temp;
	}
	public String getStudent_name(String stuNumber){
		String sql = "select student_name from stuinfo where student_number = ?";
		String temp = "";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);

			pstm.setString(1,stuNumber);
			rs = pstm.executeQuery();
			while(rs.next()){
				temp = rs.getString(1);
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
		return temp;
	}
	public String getTeacher_name(String stuNumber){
		String sql = "select teacher_name from teachers,strclass,preapply "
				+ "where teachers.teacher_id = strclass.tea_id and strclass.class_id = preapply.class_id"
				+ " and preapply.stu_number = ?";
		String temp = "";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);

			pstm.setString(1,stuNumber);
			rs = pstm.executeQuery();
			while(rs.next()){
				temp = rs.getString(1);
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
		return temp;
	}
	public boolean updateRecord(StuInfo info){
		boolean rss = false;
		Connection conn = null;
		PreparedStatement pstm = null;
		try{
			conn = ConnectFactory.getConnection();
			String sql = "UPDATE stuinfo SET student_mobile = ?,student_edu = ?,student_college = ?,student_class = ?"+
					",student_name = ?,student_identity = ? WHERE student_number = ?;";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, info.getStudent_mobile());
			pstm.setString(2, info.getStudent_edu());
			pstm.setString(3, info.getStudent_college());
			pstm.setString(4, info.getStudent_class());
			pstm.setString(5, info.getStudent_name());
			pstm.setString(6, info.getStudent_identity());
			pstm.setString(7, info.getStudent_number());
			
			int r = pstm.executeUpdate();
			if(r > 0) rss = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm != null){
					pstm.close();
				}
				if(conn != null)
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return rss;
	}
	public String getExamType(String stu_id){
		String result = "新考";
		String sql = "select result from extraexam where stu_id = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stu_id);
			rs = pstm.executeQuery();
			while(rs.next()){
				result  = rs.getString(1);
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
		
		return result;
	}
	@Override
	public String[] getColumTitle() {
		// TODO Auto-generated method stub
		
		String[] co = {"学号","姓名","性别","身份证","生日","院系","班级","学历","联系电话"};
		return this.getColumTitle(co);
		
	}
	@Override
	public ArrayList<AbstractData> getAllData() {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> infos = new ArrayList<AbstractData>();
		String sql = "select * from stuinfo";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StuInfo info = new StuInfo();
				info.setStudent_number(rs.getString(1));
				info.setStudent_name(rs.getString(2));
				info.setStudent_sex(rs.getString(3));
				info.setStudent_identity(rs.getString(4));
				info.setStudent_birth(rs.getString(5));
				info.setStudent_college(rs.getString(6));
				info.setStudent_class(rs.getString(7));
				info.setStudent_edu(rs.getString(8));
				info.setStudent_mobile(rs.getString(9));
				infos.add(info);
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
		String sql = "REPLACE into stuinfo values(?,?,?,?,?,?,?,?,?);";
		int check = 0;
		PreparedStatement pstm = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<datas.size();i++){
				StuInfo info = (StuInfo)datas.get(i);
				pstm.setString(1,info.getStudent_number());
				pstm.setString(2, info.getStudent_name());
				pstm.setString(3, info.getStudent_sex());
				pstm.setString(4, info.getStudent_identity());
				
				if(info.getStudent_birth()==null)
					pstm.setNull(5, Types.DATE);
				else{
					if(info.getStudent_birth().equals("")){
						pstm.setNull(5, Types.DATE);
					}else{
						pstm.setString(5, info.getStudent_birth());
					}
				}
				pstm.setString(6, info.getStudent_college());
				pstm.setString(7, info.getStudent_class());
				pstm.setString(8, info.getStudent_edu());
				pstm.setString(9, info.getStudent_mobile());
				pstm.addBatch();
			}
			int[] r = pstm.executeBatch();
			for(int i=0;i<r.length;i++){
				if(r[i] == 2) r[i] = 1;
				check += r[i];
			}
		}catch(Exception e){
			this.err_msg.append(e.getMessage());
			//e.printStackTrace();
		}finally{
			try{
				
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
			String sql = "delete from stuinfo where student_number = ?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				StuInfo student = (StuInfo)datas.get(i);
				pstm.setString(1, student.getStudent_number());
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
			String sql = "update stuinfo set student_name=?,student_sex=?,student_identity=?,student_birth=?"
					+ ",student_college=?,student_class=?,student_edu=?,student_mobile=? where student_number=?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				StuInfo student = (StuInfo)datas.get(i);
				
				pstm.setString(1, student.getStudent_name());
				pstm.setString(2, student.getStudent_sex());
				pstm.setString(3, student.getStudent_identity());
				pstm.setString(4, student.getStudent_birth());
				pstm.setString(5, student.getStudent_college());
				pstm.setString(6, student.getStudent_class());
				pstm.setString(7, student.getStudent_edu());
				pstm.setString(8, student.getStudent_mobile());
				pstm.setString(9, student.getStudent_number());
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
		String sql = "select count(*) from stuinfo";
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
		ArrayList<AbstractData> infos = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select * from stuinfo limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StuInfo info = new StuInfo();
				info.setStudent_number(rs.getString(1));
				info.setStudent_name(rs.getString(2));
				info.setStudent_sex(rs.getString(3));
				info.setStudent_identity(rs.getString(4));
				info.setStudent_birth(rs.getString(5));
				info.setStudent_college(rs.getString(6));
				info.setStudent_class(rs.getString(7));
				info.setStudent_edu(rs.getString(8));
				info.setStudent_mobile(rs.getString(9));
				infos.add(info);
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
		return infos;
	}
	public ArrayList<AbstractData> getSelectDatas(int page, int num,
			String name, String value) {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> infos = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select * from stuinfo where "+name+" like '%"+value+"%'limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				StuInfo info = new StuInfo();
				info.setStudent_number(rs.getString(1));
				info.setStudent_name(rs.getString(2));
				info.setStudent_sex(rs.getString(3));
				info.setStudent_identity(rs.getString(4));
				info.setStudent_birth(rs.getString(5));
				info.setStudent_college(rs.getString(6));
				info.setStudent_class(rs.getString(7));
				info.setStudent_edu(rs.getString(8));
				info.setStudent_mobile(rs.getString(9));
				infos.add(info);
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
		return infos;
	}
	@Override
	public int getSelectRecordNum(String name, String value) {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "select count(*) from stuinfo where "+name+" like '%"+value+"%'";
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
	public ArrayList<ArrangeClass> getDatas(String name,String value){
		ArrayList<ArrangeClass> tcs = new ArrayList<ArrangeClass>();
		String sql = "";
		if(value.equals("")){
			sql ="select stuinfo.student_name,stuinfo.student_number,stuinfo.student_sex,stuinfo.student_identity,strclass.class_place,stuinfo.student_class"+
					",stuinfo.student_college,teachers.teacher_name,strclass.start_time,strclass.end_time"+ 
					" from preapply,stuinfo,strclass,teachers where stuinfo.student_number=preapply.stu_number"+ 
					" and preapply.class_id=strclass.class_id and strclass.tea_id=teachers.teacher_id";
		}else{
			sql ="select stuinfo.student_name,stuinfo.student_number,stuinfo.student_sex,stuinfo.student_identity,strclass.class_place,stuinfo.student_class"+
				",stuinfo.student_college,teachers.teacher_name,strclass.start_time,strclass.end_time"+ 
				" from preapply,stuinfo,strclass,teachers where stuinfo.student_number=preapply.stu_number"+ 
				" and preapply.class_id=strclass.class_id and strclass.tea_id=teachers.teacher_id"+
				" and "+name+" like '%"+value+"%';";
		}
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				ArrangeClass arrange = new ArrangeClass();
				arrange.setStudent_name(rs.getString(1));
				arrange.setStudent_number(rs.getString(2));
				arrange.setStudent_sex(rs.getString(3));
				arrange.setStudent_identity(rs.getString(4));
				arrange.setClass_place(rs.getString(5));
				arrange.setStudent_class(rs.getString(6));
				arrange.setStudent_college(rs.getString(7));
				arrange.setTeacher_name(rs.getString(8));
				arrange.setStart_time(rs.getString(9));
				arrange.setEnd_time(rs.getString(10));
				tcs.add(arrange);
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
		return tcs;
	}
}
