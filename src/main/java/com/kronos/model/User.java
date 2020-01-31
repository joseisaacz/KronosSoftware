package com.kronos.model;

public class User {

	private TempUser tempUser;
	private String password;
	private Department deparment;

	public User() {
		
	}
	public User(TempUser temp, String password, Department dep) {
		this.deparment = dep;
		this.password = password;
		this.tempUser = temp;
	}

	public TempUser getTempUser() {
		return tempUser;
	}

	public void setTempUser(TempUser tempUser) {
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
