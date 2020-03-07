package com.kronos.schedule;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	public void sendSimpleMail(String to, String subject, String message);
	public void sendMailUsingTemplate(String to, String subject, SimpleMailMessage template, String templateArgs);
	public void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment); 
}
