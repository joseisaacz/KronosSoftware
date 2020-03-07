package com.kronos.schedule;

import org.springframework.stereotype.Component;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired 
	private JavaMailSender emailSender;
	
	@Value("concejomunicipal@sanpablo.go.cr")
    private String from;
	

	public void sendSimpleMail(String to, String subject, String message ) {
		try {
            SimpleMailMessage _message = new SimpleMailMessage();
            
            _message.setFrom(from);
            
            _message.setTo(to);
            _message.setSubject(subject);
            _message.setText(message);

            emailSender.send(_message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
	}
	@Override 
	public void sendMailUsingTemplate(String to, String subject, SimpleMailMessage template, String templateArgs) {
		String text = String.format(template.getText(), templateArgs);  
        sendSimpleMail(to, subject, text);
	}
	
	@Override 
	public void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment) {
		MimeMessage message = emailSender.createMimeMessage();
	      try {
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	     
	    helper.setFrom(from);
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(text);
	         
	    FileSystemResource file 
	      = new FileSystemResource(new File(pathToAttachment));
	    helper.addAttachment("Invoice", file);
	 
	    emailSender.send(message);
	      }catch(MessagingException e) {
	    	  e.printStackTrace();
	      }
	}
}
