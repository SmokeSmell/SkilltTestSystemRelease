package com.gong.domain;

public class StuInfo extends AbstractData{
	private String student_number;
	private String student_name;
	private String student_sex;
	private String student_identity;
	private String student_birth;
	private String student_college;
	private String student_class;
	private String student_edu;
	private String student_mobile;
	
	public StuInfo(){
		student_edu = "¸ßÖ°";
	}
	public String getStudent_number() {
		return student_number;
	}
	public void setStudent_number(String student_number) {
		this.student_number = student_number;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getStudent_sex() {
		return student_sex;
	}
	public void setStudent_sex(String student_sex) {
		this.student_sex = student_sex;
	}
	public String getStudent_identity() {
		return student_identity;
	}
	public void setStudent_identity(String student_indentity) {
		if(student_indentity != null){
			//System.out.println(student_indentity);
			if(!student_indentity.equals("")){
				this.student_identity = student_indentity;
				this.student_birth = student_indentity.substring(6,14);
			}
		}
	}
	public String getStudent_birth() {
		return student_birth;
	}
	public void setStudent_birth(String student_birth) {
		this.student_birth = student_birth;
	}
	public String getStudent_college() {
		return student_college;
	}
	public void setStudent_college(String student_college) {
		this.student_college = student_college;
	}
	public String getStudent_class() {
		return student_class;
	}
	public void setStudent_class(String student_class) {
		this.student_class = student_class;
	}
	public String getStudent_edu() {
		return student_edu;
	}
	public void setStudent_edu(String student_edu) {
		this.student_edu = student_edu;
	}
	public String getStudent_mobile() {
		return student_mobile;
	}
	public void setStudent_mobile(String student_mobile) {
		this.student_mobile = student_mobile;
	}
	
}
