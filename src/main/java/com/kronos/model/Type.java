package com.kronos.model;

public class Type {

    private char id;
    private String description;
    
    public Type() {
    	
    }
    
    public Type(char id) {
    	this.id = id;
    }
    
    public Type(char id, String description) {
    	this.id = id;
    	this.description = description; 
    }

	public char getId() {
		return id;
	}
	public void setId(char id) {
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
		return "Type [id=" + id + ", description=" + description + "]";
	}
	
}
