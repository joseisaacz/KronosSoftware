package com.kronos.model;

public class Pdf {

	private String URL;
	private boolean finalResponse=false;
	private int Approved;
	private boolean canDelete=false;
	private boolean isAccord=true;
	
	public Pdf(String URL) {
		this.URL=URL;
	}
	
	public Pdf(String URL, boolean finalResponse) {
		this.URL=URL;
		this.finalResponse=finalResponse;
		
	}
	
	public Pdf(String URL, boolean finalResponse, int isApproved) {
		this.URL=URL;
		this.finalResponse=finalResponse;
		this.Approved=isApproved;
		
	}
	
	public Pdf(String URL, int isApproved) {
		this.URL=URL;
		this.Approved=isApproved;
		
	}
	
	public Pdf(String URL, boolean finalResponse, int isApproved,boolean canDelete) {
		this.URL=URL;
		this.finalResponse=finalResponse;
		this.Approved=isApproved;
		this.canDelete=canDelete;
		
	}
	
	public Pdf(String URL, boolean finalResponse, int isApproved,boolean canDelete, boolean isAccord) {
		this.URL=URL;
		this.finalResponse=finalResponse;
		this.Approved=isApproved;
		this.canDelete=canDelete;
		this.isAccord=isAccord;
		
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
	
	
	
	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	
	public int getIsApproved() {
		return Approved;
	}

	public void setIsApproved(int isApproved) {
		this.Approved = isApproved;
	}
	
	

	public boolean isAccord() {
		return isAccord;
	}

	public void setAccord(boolean isAccord) {
		this.isAccord = isAccord;
	}

	@Override
	public String toString() {
		return "Pdf [URL=" + URL + ", finalResponse=" + finalResponse + ", isApproved=" + Approved + ", canDelete="
				+ canDelete + "]";
	}



	public static int NO_PERMISSION=0;
	public static int PENDING=1;
	public static int APPROVED=2;
	public static int REJECTED=3;
	
}
