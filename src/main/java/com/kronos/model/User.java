package com.kronos.model;

public class User {

    private String tempUser;
    private String password;
    private Department deparment;
    
    
	public String getTempUser() {
		return tempUser;
	}
	public void setTempUser(String tempUser) {
		this.tempUser = tempUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Department getDeparment() {
		return deparment;
	}
	public void setDeparment(Department deparment) {
		this.deparment = deparment;
	}
	@Override
	public String toString() {
		return "User [tempUser=" + tempUser + ", password=" + password + ", deparment=" + deparment + "]";
	}
    
    
	
}
