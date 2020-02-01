package com.kronos.model;

public class AccordTempUser {

	private String accord;
	private String tempUser;
	
	
	public String getAccord() {
		return accord;
	}
	public void setAccord(String accord) {
		this.accord = accord;
	}
	public String getTempUser() {
		return tempUser;
	}
	public void setTempUser(String tempUser) {
		this.tempUser = tempUser;
	}
	@Override
	public String toString() {
		return "AccordTempUser [accord=" + accord + ", tempUser=" + tempUser + "]";
	}
	
	
	
}
