package com.kronos.schedule;



public interface EmailService {
	public void sendSimpleMail(String to, String subject, String message); 
	public void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment); 
}
