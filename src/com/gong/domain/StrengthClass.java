package com.gong.domain;

public class StrengthClass extends AbstractData{
	private String class_id;
	private String class_name;
	private String start_time;
	private String end_time;
	private String tea_id;
	private String class_place;
	private int max_num;
	private int cur_num;
	private int max_expend;
	
	public StrengthClass(){
		this.cur_num = 0;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public String getTea_id() {
		return tea_id;
	}
	public void setTea_id(String tea_id) {
		this.tea_id = tea_id;
	}
	public String getClass_place() {
		return class_place;
	}
	
	public void setClass_place(String class_place) {
		this.class_place = class_place;
	}
	public int getMax_num() {
		return max_num;
	}
	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}
	public int getCur_num() {
		return cur_num;
	}
	public void setCur_num(int cur_num) {
		this.cur_num = cur_num;
	}
	public int getMax_expend() {
		return max_expend;
	}
	public void setMax_expend(int max_expend) {
		this.max_expend = max_expend;
	}
	
	public void addCur_num(){
		this.cur_num += 1;
	}
	
}
