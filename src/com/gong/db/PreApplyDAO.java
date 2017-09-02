package com.gong.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gong.domain.AbstractData;
import com.gong.domain.NotPay;
import com.gong.domain.PreApply;
import com.gong.domain.StrengthClass;
import com.gong.domain.User;
import com.gong.helper.ConnectFactory;

public class PreApplyDAO extends AbstractDAO{
	
	public ArrayList<PreApply> getRecords(int ispay){
		ArrayList<PreApply> applys = new ArrayList<PreApply>();
		String sql = "select stu_number,class_id,apply_time from preapply where pay = ?;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setShort(1, (byte)ispay);
			rs = pstm.executeQuery();
			while(rs.next()){
				PreApply apply = new PreApply();
				apply.setStu_number(rs.getString(1));
				apply.setClass_id(rs.getInt(2));
				apply.setTime(rs.getString(3));
				applys.add(apply);
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
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return applys;
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
			String sql = "delete from preapply;";
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
	public boolean getFlag(String stu_number){
		boolean flag = false;
		String sql = "select pay from preapply where stu_number = ?;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stu_number);
			rs = pstm.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) == 0){
					flag = false;
				}else{
					flag = true;
				}
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
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	public boolean isApplyed(String stu_number){
		boolean flag = false;
		String sql = "select pay from preapply where stu_number = ?;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stu_number);
			rs = pstm.executeQuery();
			if(rs.next())
				flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
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
		return flag;
	}
	@Override
	public String[] getColumTitle() {
		// TODO Auto-generated method stub
		String[] titles = new String[]{"学号","班级编号","申请时间","是否缴费"};
		return this.getColumTitle(titles);
	}
	@Override
	public String[] getColoumNames() {
		// TODO Auto-generated method stub
		return this.getColumNames(new String[]{"stu_number","class_id","time","ispay"});
	}
	@Override
	public ArrayList<AbstractData> getAllData() {
		// TODO Auto-generated method stub
		ArrayList<AbstractData> tcs = new ArrayList<AbstractData>();
		String sql = "select * from preapply order by stu_number";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				PreApply tc = new PreApply();
				tc.setStu_number(rs.getString(1));
				tc.setClass_id(rs.getInt(2));
				tc.setTime(rs.getString(3));
				tc.setIspay(rs.getInt(4));
				tcs.add(tc);
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
	
	public int addRecords(List datas) {
		// TODO Auto-generated method stub
		String sql = "REPLACE INTO preapply(stu_number,class_id,pay) VALUES(?,?,?);";
		int check = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<datas.size();i++){
				PreApply pa = (PreApply)datas.get(i);
				pstm.setString(1, pa.getStu_number());
				pstm.setInt(2, pa.getClass_id());
				pstm.setInt(3,pa.getIspay());
				System.out.println(pa.getStu_number()+","+pa.getClass_id());
				pstm.addBatch();
			}
			int[] r = pstm.executeBatch();
			for(int i=0;i<r.length;i++){
				check += r[i];
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
		return check;
	}
	public int[] addPreApply(ArrayList<PreApply> datas) {
		// TODO Auto-generated method stub
		String sql = "SELECT preApply(?,?,?,?);";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int[] check = new int[datas.size()];
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<datas.size();i++){
				PreApply pa = (PreApply)datas.get(i);
				pstm.setString(1, pa.getStu_number());
				pstm.setString(2, pa.getStu_name());
				pstm.setInt(3, pa.getClass_id());
				pstm.setInt(4,pa.getIspay());
				rs = pstm.executeQuery();
				
				if(rs.next())
					check[i] = rs.getInt(1);
			}
			return check;
			//return pstm.executeBatch();
			
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
		return null;
	}
	public int addRecords(PreApply apply) {
		// TODO Auto-generated method stub
		String sql = "insert into preapply(stu_number,class_id) values(?,?);";
		int check = 0;
		PreparedStatement pstm = null;
		Connection conn = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, apply.getStu_number());
			pstm.setInt(2, apply.getClass_id());
			check = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
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
			String sql = "delete from preapply where stu_number = ?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				PreApply pa = (PreApply)datas.get(i);
				pstm.setString(1,pa.getStu_number());
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
			String sql = "update preapply set apply_time=now(),pay=? where stu_number=?;";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				PreApply pa = (PreApply)datas.get(i);
				pstm.setInt(1, pa.getIspay());
				pstm.setString(2, pa.getStu_number());
				pstm.addBatch();
			}
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

	public int[] updatePayInfo(List datas) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			String sql = "UPDATE preapply SET preapply.pay = ? "+
					"WHERE stu_number = (SELECT student_number FROM stuinfo "
					+ "WHERE student_number = ?" 
					+"AND student_name = ?);";
			pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			for(int i=0;i<datas.size();i++){
				PreApply pa = (PreApply)datas.get(i);
				pstm.setInt(1, pa.getIspay());
				pstm.setString(2, pa.getStu_number());
				pstm.setString(3, pa.getStu_name());
				pstm.addBatch();
			}
			int[] r = pstm.executeBatch();
			return r;
		
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
		return null;
	}
	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "select count(*) from preapply";
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
		ArrayList<AbstractData> tcs = new ArrayList<AbstractData>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "select * from preapply limit "+begin+" ,"+num+';';
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				PreApply tc = new PreApply();
				tc.setStu_number(rs.getString(1));
				tc.setTime(rs.getString(2));
				tc.setIspay(rs.getInt(3));
				tcs.add(tc);
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
		return tcs;
	}
	
	public ArrayList<Map<String,String>> getPreApplyInfo(int page, int num){
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql = "CALL preApply_info(?,?);";
		//System.out.println(begin+","+num);
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, begin);
			pstm.setInt(2, num);
			rs = pstm.executeQuery();
			while(rs.next()){
				HashMap<String,String> map = new HashMap<String,String>();;
				map.put("stu_id", rs.getString(1));
				map.put("name", rs.getString(2));
				map.put("class_name", rs.getString(3));
				System.out.println(rs.getString(1));
				int pay = rs.getInt(4);
				if(pay == 0)
					map.put("ispay", "未缴费");
				else{
					map.put("ispay", "已缴费");
				}
				map.put("time", rs.getString(5).substring(0,10));
				list.add(map);
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
		return list;
	}
	public ArrayList<Map<String,String>> getSelectData(int page, int num,
			String name, String value) {
		// TODO Auto-generated method stub
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		int begin = (page-1)*num;
		begin = begin > 0 ? begin:0;
		String sql ="";
		if(name.equals("") || value.equals("")){
			sql = "SELECT preapply.stu_number,stuinfo.student_name,strclass.class_name,preapply.pay,preapply.apply_time"+ 
					" FROM  preapply JOIN strclass ON strclass.class_id = preapply.class_id"+
					" JOIN stuinfo ON stuinfo.student_number = preapply.stu_number"+
					" ORDER BY preapply.stu_number LIMIT "+begin+" ,"+num+";" ;
		}else{
			if(name.equals("pay")){
				sql = "SELECT preapply.stu_number,stuinfo.student_name,strclass.class_name,preapply.pay,preapply.apply_time"+ 
						" FROM  preapply JOIN strclass ON strclass.class_id = preapply.class_id"+
						" JOIN stuinfo ON stuinfo.student_number = preapply.stu_number where "+name+" = "+value+""+
						" ORDER BY preapply.stu_number LIMIT "+begin+" ,"+num+";" ;
			}else{
		    sql = "SELECT preapply.stu_number,stuinfo.student_name,strclass.class_name,preapply.pay,preapply.apply_time"+ 
				" FROM  preapply JOIN strclass ON strclass.class_id = preapply.class_id"+
				" JOIN stuinfo ON stuinfo.student_number = preapply.stu_number where "+name+" like '%"+value+"%'"+
				" ORDER BY preapply.stu_number LIMIT "+begin+" ,"+num+";" ;
			}
		}
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				HashMap<String,String> map = new HashMap<String,String>();;
				map.put("stu_id", rs.getString(1));
				map.put("name", rs.getString(2));
				map.put("class_name", rs.getString(3));
				int pay = rs.getInt(4);
				if(pay == 0)
					map.put("ispay", "未缴费");
				else{
					map.put("ispay", "已缴费");
				}
				map.put("time", rs.getString(5).substring(0,10));
				list.add(map);
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
		return list;
	}

	@Override
	public int getSelectRecordNum(String name, String value) {
		// TODO Auto-generated method stub
		int pagenum = 0;
		String sql = "";
		if(name.equals("") || value.equals("")){
			sql = "SELECT count(*)"+ 
					" FROM  preapply JOIN strclass ON strclass.class_id = preapply.class_id"+
					" JOIN stuinfo ON stuinfo.student_number = preapply.stu_number"+
					" ORDER BY preapply.apply_time DESC ;" ;
		}else{
			if(name.equals("pay")){
				sql = "SELECT count(*)"+ 
						" FROM  preapply JOIN strclass ON strclass.class_id = preapply.class_id"+
						" JOIN stuinfo ON stuinfo.student_number = preapply.stu_number where "+name+" = "+value+""+
						" ORDER BY preapply.apply_time DESC ;" ;
			}else{
				sql = "SELECT count(*)"+ 
					" FROM  preapply JOIN strclass ON strclass.class_id = preapply.class_id"+
					" JOIN stuinfo ON stuinfo.student_number = preapply.stu_number where "+name+" like '%"+value+"%'"+
					" ORDER BY preapply.apply_time DESC ;" ;
			}
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
	public ArrayList<NotPay> getDatas(String name,String value){
		ArrayList<NotPay> tcs = new ArrayList<NotPay>();
		String sql = "";
		if(name.equals("student_number") || name.equals("student_name")){
			sql = "select preapply.stu_number,stuinfo.student_name from preapply,stuinfo"+
			" where stuinfo.student_number=preapply.stu_number and preapply.pay='0'"+
			" and "+name+" like '%"+value+"%'";
		}else{
			sql = "select preapply.stu_number,stuinfo.student_name from preapply,stuinfo"+
					" where stuinfo.student_number=preapply.stu_number and preapply.pay='0';";
		}
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()){
				NotPay nop = new NotPay();
				nop.setStudent_number(rs.getString(1));
				nop.setStudent_name(rs.getString(2));
				tcs.add(nop);
			}
			getExamDatas(tcs);
			if(name.equals("exam_result")){
				if(value.equals("新")||value.equals("新考")){
					for(int i=0;i<tcs.size();i++){
						String temp = tcs.get(i).getExam_result();
						if(temp.equals("补考")){
							tcs.remove(i);
							i--;
						}
					}
				}else if(value.equals("补")||value.equals("补考")){
					for(int i=0;i<tcs.size();i++){
						String temp = tcs.get(i).getExam_result();
						if(temp.equals("新考")){
							tcs.remove(i);
							i--;
						}
					}
				}
			}
			if(name.equals("cost")){
				if(value.equals("2")||value.equals("12")||value.equals("120")){
					for(int i=0;i<tcs.size();i++){
						int temp = tcs.get(i).getCost();
						if(temp==160){
							tcs.remove(i);
							i--;
						}
					}
				}else if(value.equals("6")||value.equals("16")||value.equals("160")){
					for(int i=0;i<tcs.size();i++){
						int temp = tcs.get(i).getCost();
						if(temp==120){
							tcs.remove(i);
							i--;
						}
					}
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
		return tcs;
	}
	public ArrayList<NotPay> getExamDatas(ArrayList<NotPay> tcs){
		String result = "新考";
		String sql = "select result from extraexam where stu_id = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			conn = ConnectFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			for(int i=0;i<tcs.size();i++){
				result = "新考";
				NotPay pa = (NotPay)tcs.get(i);
				pstm.setString(1, pa.getStudent_number());
				pstm.addBatch();
				rs=pstm.executeQuery();
				while(rs.next()){
					result  = rs.getString(1);
				}
				tcs.get(i).setExam_result(result);
				if(result.equals("新考")){
					tcs.get(i).setCost(160);
				}else{
					tcs.get(i).setCost(120);
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
		return tcs;
	
	}
	@Override
	public ArrayList<AbstractData> getSelectDatas(int page, int num,
			String name, String value) {
		// TODO Auto-generated method stub
		return null;
	}	
}
