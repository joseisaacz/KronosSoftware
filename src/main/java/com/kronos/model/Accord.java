package com.kronos.model;

import java.time.LocalTime;
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
	List<Pdf> URL;
	
	
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
	@Override
	public String toString() {
		return "Accord [accNumber=" + accNumber + ", incorporatedDate=" + incorporatedDate + ", incorporatedTime="
				+ incorporatedTime + ", deadline=" + deadline + ", sessionDate=" + sessionDate + ", type=" + type
				+ ", observations=" + observations + ", published=" + published + ", notified=" + notified + ", state="
				+ state + ", URL=" + URL + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
