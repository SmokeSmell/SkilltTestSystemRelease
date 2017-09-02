package com.gong.domain;

public class Score extends AbstractData{
	private String stu_id;
	private String exam_number;
	private String identity;
	private String occupation;
	private String auth_class;
	private String degree;
	private String practice_score;
	private String status;
	private String eva_score;
	public String getStu_id() {
		return stu_id;
	}
	
	
	public String getIdentity() {
		return identity;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getExam_number() {
		return exam_number;
	}
	public void setExam_number(String exam_number) {
		this.exam_number = exam_number;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	public String getAuth_class() {
		return auth_class;
	}
	public void setAuth_class(String auth_class) {
		this.auth_class = auth_class;
	}
	public String getPractice_score() {
		return practice_score;
	}
	public void setPractice_score(String practice_score) {
		this.practice_score = practice_score;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEva_score() {
		return eva_score;
	}
	public void setEva_score(String eva_score) {
		this.eva_score = eva_score;
	}
	
	
}
