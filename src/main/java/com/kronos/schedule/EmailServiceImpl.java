package com.kronos.schedule;

import org.springframework.stereotype.Component;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired 
	private JavaMailSender emailSender;
	
	@Value("${spring.mail.username}")
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
}
