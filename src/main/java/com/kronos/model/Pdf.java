package com.kronos.model;

public class Pdf {

	private String URL;
	private boolean finalResponse=false;
	
	public Pdf(String URL) {
		this.URL=URL;
	}
	
	public Pdf(String URL, boolean finalResponse) {
		this.URL=URL;
		this.finalResponse=finalResponse;
		
	}
	
	
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public boolean isFinalResponse() {
		return finalResponse;
	}
	public void setFinalResponse(boolean finalResponse) {
		this.finalResponse = finalResponse;
	}
	@Override
	public String toString() {
		return "Pdf [URL=" + URL + ", finalResponse=" + finalResponse + "]";
	}
	
	
}
