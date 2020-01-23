package com.kronos.model;

public class State {
	private int id;
	private String description;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "State [id=" + id + ", description=" + description + "]";
	}
	
	
	
	

}
