package com.kronos.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Accord {

	private String accNumber;
	private Date incorporatedDate;
	private LocalTime incorporatedTime;
	private Date deadline;
	private Date sessionDate;
	private Type type;
	private String observations;
	private boolean published;
	private boolean notified;
	private State state;
	private User user;
	List<Pdf> URL= new ArrayList<>();
	
	
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public Date getIncorporatedDate() {
		return incorporatedDate;
	}
	public void setIncorporatedDate(Date incorporatedDate) {
		this.incorporatedDate = incorporatedDate;
	}
	public LocalTime getIncorporatedTime() {
		return incorporatedTime;
	}
	public void setIncorporatedTime(LocalTime incorporatedTime) {
		this.incorporatedTime = incorporatedTime;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Date getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public boolean isNotified() {
		return notified;
	}
	public void setNotified(boolean notified) {
		this.notified = notified;
	}
	public State getState() {
		return this.state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public List<Pdf> getURL() {
		return URL;
	}
	public void setURL(List<Pdf> uRL) {
		URL = uRL;
	}


	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	@SuppressWarnings(value = { "deprecation" })
	public String toString() {
		return "Acuerdo [Número de Oficio= " + accNumber + ", Fecha de Incorporación= " + incorporatedDate.getDate() +"/"+(incorporatedDate.getMonth()+1)+"/"+(incorporatedDate.getYear()+1900)+"\n"
				+ ", Fecha de vencimiento= " + deadline.getDate() +"/"+(deadline.getMonth()+1)+"/"+(deadline.getYear()+1900)+  "]";
	}



	public static final char ADMIN_TYPE = 'A';
	public static final int PENDING_STATE=2;
	
	
	
	
	
	
}
