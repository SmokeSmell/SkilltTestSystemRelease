package com.gong.domain;

public class Teacher extends AbstractData {
	private String teacher_id;
	private String teacher_name;
	private String teacher_depart;
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getTeacher_depart() {
		return teacher_depart;
	}
	public void setTeacher_depart(String teacher_depart) {
		this.teacher_depart = teacher_depart;
	}
	
	
}
