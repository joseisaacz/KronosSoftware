package com.kronos.model;

public class User {

	private TempUser tempUser;
	private String password;
	private Department department;
	private Boolean status;

	public User() {
		
	}
	public User(TempUser temp, String password, Department dep) {
		this.department = dep;
		this.password = password;
		this.tempUser = temp;
		this.setStatus(true); 
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "User [tempUser=" + tempUser + ", password=" + password + ", department=" + department + "]";
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

}
