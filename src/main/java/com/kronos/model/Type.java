package com.kronos.model;

import java.util.Collections;
import java.util.List;

public class Type {

    private int id;
    private String description;
    
    public Type() {
    	
    }
    
    public Type(int id) {
    	this.id = id;
    }
    
    public Type(String description) {
    	
    	this.description=description;
    }
    public Type(int id, String description) {
    	this.id = id;
    	this.description = description; 
    }

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
		return "Type [id=" + id + ", description=" + description + "]";
	}
	
//	public static char getNextId(List<Character> list) {
//		
//		Collections.sort(list);
//		char last=list.get(list.size()-1);
//		if(last=='Z')
//			return 'a';
//		
//		return  (char) (last+1);
//
//	}
}
