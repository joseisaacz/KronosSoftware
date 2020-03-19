package com.kronos.model;

import java.util.Collections;
import java.util.List;

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
	
	public static char getNextId(List<Character> list) {
		
		Collections.sort(list);
		char last=list.get(list.size()-1);
		if(last=='Z')
			return 'a';
		
		return  (char) (last+1);

	}
}
