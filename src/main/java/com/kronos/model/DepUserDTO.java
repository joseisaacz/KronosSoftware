package com.kronos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class DepUserDTO {

	private String Departamento;
	private String Nombre;
	
	public DepUserDTO() {
		
	}
	
	
	public DepUserDTO(String department, String user) {
		super();
		this.Departamento = department;
		this.Nombre = user;
	}


	public String getDepartamento() {
		return Departamento;
	}


	public void setDepartamento(String departamento) {
		this.Departamento = departamento;
	}


	public String getNombre() {
		return this.Nombre;
	}


	public void setNombre(String nombre) {
		this.Nombre = nombre;
	}


	@Override
	public String toString() {
		return "DepUserDTO [departamento=" + Departamento + ", nombre=" + Nombre + "]";
	}
	
	
	
}
