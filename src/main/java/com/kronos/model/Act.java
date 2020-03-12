package com.kronos.model;

import java.util.Date;

public class Act {
private String sessionType;
private Date sessionDate;
private String url;
private Boolean publc;
private Boolean active;

public Act() {
	
}
public Act(String sessionType, Date sessionDate, String url, Boolean publc, Boolean active) {
	this.sessionType=sessionType;
	this.sessionDate=sessionDate;
	this.url=url;
	this.publc=publc;
	this.active=active;
} 
public String getSessionType() {
	return sessionType;
}
public void setSessionType(String sessionType) {
	this.sessionType = sessionType;
}
public Date getSessionDate() {
	return sessionDate;
}
public void setSessionDate(Date sessionDate) {
	this.sessionDate = sessionDate;
}
public Boolean getPublc() {
	return publc;
}
public void setPublc(Boolean publc) {
	this.publc = publc;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public Boolean getActive() {
	return active;
}
public void setActive(Boolean active) {
	this.active = active;
}
@Override
public String toString() {
	return "Acta: "+sessionType+", "+sessionDate;
}

}
