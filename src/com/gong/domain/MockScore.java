package com.gong.domain;

public class MockScore extends AbstractData{
	private String stu_number;
	private String stu_name;
	private float mock_score;
	
	
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public float getMock_score() {
		return mock_score;
	}
	public void setMock_score(float mock_score) {
		this.mock_score = mock_score;
	}
	public String getStu_number() {
		return stu_number;
	}
	public void setStu_number(String stu_number) {
		this.stu_number = stu_number;
	}
	
}	
